import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 마법사 상어와 파이어스톰
 * https://www.acmicpc.net/problem/20058
 * 
 * 1. 구역을 나눠 회전시키는 메서드가 필요하다.
 *  - 구역을 나누는 메서드를 만들고, 해당 구역을 파라미터로 받아 회전시키는 메서드를 만들면 될 것 같다.
 * 2. 얼음의 양이 줄어드는 메서드가 필요하다.
 *  - 사방을 탐색한 뒤 2칸 이상 0이라면 얼음의 양을 1 줄인다.
 *  - 맵을 넘어간 곳을 검사하기 위해 맵 각 변에 1만큼 패딩을 주면 좋을 것 같다.
 * 3. 1과 2를 Q번 실행한 뒤 모든 칸에 대해 bfs로 덩어리를 확인하며 몇 칸을 차지하는지 구한다.
 * 4. 3번을 할 때 남아있는 얼음의 양을 저장하여 합을 구한다.
 *
 * @author 배용현
 *
 */
class BJ_20058_마법사상어와파이어스톰_배용현 {

	static int N, Q, sum = 0, size = 0;
	static int[][] map;
	static int[] fsLevel;
	static boolean[][] visited;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, -1, 0, 1};
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		input();
		solution();
		print();
	}

	private static void print() {
		System.out.println(sum);
		System.out.println(size);
	}

	private static void solution() {
		for (int i = 0; i < Q; i++) {		// 모든 파이어스톰에 대해 수행
			int length = (int) Math.pow(2, fsLevel[i]);		// 현재 레벨 격자의 한 변의 길이
			for (int j = 1; j <= N; j+=length) {		// 2중 for문으로 격자의 시작부분 설정
				for (int k = 1; k <= N; k+=length) {
					rotate(j, k, length);		// 회전
				}
			}
			melt();		// 다 돌렸으면 얼음의 상태 변화
		}

		for (int i = 1; i <= N; i++) {		// 모든 파이어스톰을 시전했으면
			for (int j = 1; j <= N; j++) {		// 각 칸에서 bfs를 수행하여 정답 확인
				if (visited[i][j] || map[i][j]==0) {		// 이미 방문했거나 얼음이 남아있지 않은 칸은 패스
					continue;
				}

				bfs(i, j);
			}
		}
	}

	private static void bfs(int sy, int sx) {
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[]{sx, sy});
		visited[sy][sx] = true;
		sum += map[sy][sx];		// 남아있는 얼음의 합에 현재 얼음의 양 더함
		int tempSize = 1;		// 현재 덩어리가 차지하는 칸 수

		while (!q.isEmpty()) {
			int[] cur = q.poll();
			int x = cur[0];
			int y = cur[1];

			for (int i = 0; i < 4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				if (visited[ny][nx] || map[ny][nx]==0) {		// 방문했거나 얼음의 양이 0이면 패스
					continue;
				}

				q.add(new int[]{nx, ny});
				visited[ny][nx] = true;
				tempSize++;
				sum += map[ny][nx];		// 남아있는 얼음의 합에 현재 얼음의 양 더함
			}
		}

		size = Math.max(size, tempSize);		// 차지하는 칸 수의 최댓값 저장
	}

	private static void melt() {		// 얼음이 녹는 메서드
		int[][] originMap = new int[N+2][N+2];		// 연쇄적으로 얼음이 녹으면 안되므로 원본 배열 저장
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				originMap[i][j] = map[i][j];
			}
		}

		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (originMap[i][j] == 0) {		// 얼음이 없는 칸이면 패스
					continue;
				}

				int aroundIce = 0;		// 주위 얼음 개수
				for (int k = 0; k < 4; k++) {		// 사방탐색
					int nx = j + dx[k];
					int ny = i + dy[k];

					if(originMap[ny][nx]!=0)		// 주위에 얼음이 있으면 개수 +1
						aroundIce++;
				}

				if(aroundIce<3)		// 주위에 얼음이 3개 미만이면 얼임이 녹음
					map[i][j]--;
			}
		}
	}

	private static void rotate(int y, int x, int length) {
		int[][] temp = new int[length][length];		// 임시로 격자를 저장할 배열
		for (int i = 0; i < length; i++) {		// 배열 회전
			for (int j = 0; j < length; j++) {
				temp[i][j] = map[y+length-1-j][x+i];		// temp 배열은 패딩이 없으므로 인덱스 조정
			}
		}
		for (int i = 0; i < length; i++) {		// 회전이 끝난 배열 덮어쓰기
			for (int j = 0; j < length; j++) {
				map[i+y][j+x] = temp[i][j];
			}
		}
	}

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());			// 맵의 크기 (지수)
		Q = parseInt(st.nextToken());			// 파이어스톰 시전횟수

		N = (int) Math.pow(2, N);		// 한 변의 크기로 변경
		map = new int[N+2][N+2];		// 각 변에 패딩

		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		fsLevel = new int[Q];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < Q; i++) {
			fsLevel[i] = Integer.parseInt(st.nextToken());
		}

		visited = new boolean[N+2][N+2];
		for (int i = 0; i < N + 2; i++) {		// 패딩을 방문체크해두면 사방탐색때 편함
			visited[i][0] = visited[i][N+1] = visited[0][i] = visited[N+1][i] = true;
		}
	}
}