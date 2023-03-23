import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 연구소 3
 * https://www.acmicpc.net/problem/17142
 *
 * 1. 맵에 존재하는 바이러스 중 M개를 뽑아 활성상태로 변경한다. (조합)
 * 2. BFS로 바이러스를 퍼뜨린다.
 * 3. 모든 칸에 바이러스가 퍼지는 최소 시간을 리턴한다.
 * 4. 맵을 체크해서 바이러스 확산 이후에도 빈 칸이 있다면 -1을 리턴한다.
 * 5. 바이러스가 존재하는 칸의 위치는 행과 열을 합쳐 따로 관리한다.
 * - 비활성 바이러스는 활성으로 안만들어도 결과로 친다
 * 
 * @author 배용현
 *
 */
public class BJ_17142_연구소3 {

	static int N, M, answer;
	static boolean gotAnswer;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int[][] map;
	static ArrayList<int[]> virus;
	static boolean[] selected;
	static boolean[][] visited;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, -1, 0, 1};

	public static void main(String[] args) throws IOException {
		input();
		solution();
		System.out.print(gotAnswer ? answer : -1);
	}

	private static void solution() {
		combination(0, 0);
	}

	private static void combination(int depth, int start) {
		if (depth == M) {
			answer = Math.min(answer, spread());
			return;
		}

		for (int i = start; i < virus.size(); i++) {
			if(selected[i])
				continue;

			selected[i] = true;
			combination(depth + 1, i);
			selected[i] = false;
		}
	}

	private static int spread() {		// 바이러스 확산시키는 메서드
		visited = new boolean[N][N];		// 방문배열 초기화
		Queue<int[]> q = new ArrayDeque<>();
		for (int i = 0; i < virus.size(); i++) {		// 고른 바이러스만 큐에 넣고 시작
			if(!selected[i])
				continue;

			int[] cur = virus.get(i);
			q.add(cur);
			visited[cur[1]][cur[0]] = true;
		}
		int sec = 0;		// 퍼지는데 소요된 시간

		while (true) {		// bfs 실행

			if(isInfested()) {		// 맵이 전부 감염되었으면
				gotAnswer = true;		// 찾았다고 표시하고 소요시간 리턴
				return sec;
			}

			int size = q.size();		// 큐 레벨별로 진입
			for (int i = 0; i < size; i++) {
				int[] cur = q.poll();

				for (int j = 0; j < 4; j++) {
					int nx = cur[0] + dx[j];
					int ny = cur[1] + dy[j];

					if(isOut(nx, ny) || visited[ny][nx] || map[ny][nx]==1)
						continue;

					q.add(new int[] {nx, ny});
					visited[ny][nx] = true;
				}

			}

			if(q.isEmpty())			// 큐 비었으면 리턴
				break;

			sec++;		// 시간 증가
		}

		return Integer.MAX_VALUE;		// 맵이 감염 안되었으면 시간 업데이트 x
	}

	private static boolean isInfested() {		// 맵이 전체적으로 감염되었는지 확인하는 메서드
		for (int i = 0; i < N; i++) {		// 맵 순회
			for (int j = 0; j < N; j++) {
				if(map[i][j]==0 && !visited[i][j])		// 빈칸인데 바이러스 안퍼졌으면
					return false;		// 감염 못시킴
			}
		}
		return true;		// 감염 다 시킴
	}

	private static boolean isOut(int nx, int ny) {
		return nx<0 || ny<0 || nx>N-1 || ny>N-1;
	}

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());
		M = parseInt(st.nextToken());

		map = new int[N][N];
		virus = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = parseInt(st.nextToken());
				if(map[i][j]==2)		// 바이러스 위치 저장
					virus.add(new int[]{j, i});
			}
		}

		selected = new boolean[virus.size()];		// 바이러스를 조합으로 선택하기 위한 배열
		answer = Integer.MAX_VALUE;
		gotAnswer = false;
	}
}