import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

// 14502번 연구소
// https://www.acmicpc.net/problem/14502
public class BJ_14502_연구소_2번째_풀이 {
	private static int n;
	private static int m;
	// 감염된 칸 수의 최소
	private static int minInfected;
	// 벽을 세우기로 선택한 빈 칸의 인덱스
	private static int[] walls = new int[3];
	// 상/좌/우/하
	private static int[] dx = { -1, 0, 1, 0 };
	private static int[] dy = { 0, -1, 0, 1 };
	private static int[][] map;
	private static int[][] copiedMap;
	// 비어있는 칸의 위치를 리스트로
	private static ArrayList<Integer> empties = new ArrayList<>();
	// 바이러스가 있는 칸의 위치를 리스트로
	private static ArrayList<Integer> viruses = new ArrayList<>();

	// 조합을 이용하여 벽의 위치를 정하기
	private static void makeWall(int step, int startIdx) {
		// 3개를 골랐다면 감염 시뮬레이션 시작하여 감염된 칸수를 획득
		if (step == 3) {
			minInfected = Math.min(minInfected, infection());
			return;
		}

		// 조합을 이용하여 벽의 위치 배열 제작
		for (int i = startIdx; i < empties.size(); i++) {
			walls[step] = i;
			makeWall(step + 1, i + 1);
		}
	}

	// bfs를 이용해서 바이러스를 확산시킴
	private static int infection() {
		// 감염된 수
		int infected = 0;
		// 맵 복사
		for (int i = 0; i < map.length; i++) {
			copiedMap[i] = map[i].clone();
		}

		// 복사한 맵에 선택한 위치에 벽을 설치
		for (int i = 0; i < 3; i++) {
			copiedMap[empties.get(walls[i]) / (m + 2)][empties.get(walls[i]) % (m + 2)] = 1;
		}

		// 큐를 생성하고 바이러스를 삽입
		Queue<Integer> queue = new LinkedList<>();
		for (int i = 0; i < viruses.size(); i++) {
			queue.add(viruses.get(i));
		}
		// 처음 들어간 바이러스들은 새로 감염된 수로 치지 않으므로 감소시켜줌
		infected -= viruses.size();

		// 큐가 빌때까지
		// 다른 탈출조건을 필요 없음
		while (!queue.isEmpty()) {
			// 하나 꺼내고
			int temp = queue.poll();
			int x = temp / (m + 2);
			int y = temp % (m + 2);
			// 4방 탐색
			for (int i = 0; i < 4; i++) {
				// 빈칸 아니면 패스
				if (copiedMap[x+dx[i]][y + dy[i]] != 0) {
					continue;
				}
				// 빈칸이면 큐에 넣고 감염시킴
				queue.add((x + dx[i]) * (m + 2) + y + dy[i]);
				copiedMap[x + dx[i]][y + dy[i]] = 2;
			}
			// 감염수 증가
			infected++;
		}

		return infected;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		// 패딩을 이용해서 범위 내인지 체크 안해도 되도록 함
		map = new int[n + 2][m + 2];
		copiedMap = new int[n + 2][m + 2];

		// 맵에 정보를 받는데
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= m; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				// 빈칸이면 해당 위치 정보를 리스트에 담아둠
				if (map[i][j] == 0) {
					empties.add(i * (m + 2) + j);
					// 바이러스가 있는 곳이면 해당 위치 정보를 리스트에 담아둠
				} else if (map[i][j] == 2) {
					viruses.add(i * (m + 2) + j);
				}
			}
		}

		// 패딩에 벽을 넣어주고
		for (int i = 0; i < n + 2; i++) {
			map[i][0] = 1;
			map[i][m + 1] = 1;
		}
		for (int i = 0; i < m + 2; i++) {
			map[0][i] = 1;
			map[n + 1][i] = 1;
		}

		// 최소 감염값 초기화 후 벽세우기 호출
		minInfected = empties.size();
		makeWall(0, 0);
		// 빈칸에서 벽 세운 3칸도 제외해야하므로 -3 하여 출력
		System.out.println(empties.size() - 3 - minInfected);
	}
}
