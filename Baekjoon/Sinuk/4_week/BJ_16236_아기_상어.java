import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_16236_아기_상어 {
	private static int N; // 맵의 사이즈
	private static int[][] map; // 맵의 정보
	private static int size = 2; // 아기 상어의 크기
	private static int moveCount = 0; // 아기상어가 움직인 횟수
	// 상:0 우:1 하:2 좌:3
	private static int[] dx = { -1, 0, 1, 0 };
	private static int[] dy = { 0, 1, 0, -1 };

	// 위치의 정보를 담는 객체
	static class Position {
		int x;
		int y;
		int depth; // 탐색의 깊이

		public Position(int x, int y, int depth) {
			this.x = x;
			this.y = y;
			this.depth = depth;
		}
	}

	private static void babyShark(Position p) {
		int eaten = 0; // 아기 상어가 먹은 수
		while (true) {
			// 가장 가까우면서 조건에 맞는 위치를 구함
			Position position = getClosest(p.x, p.y);
			// 만약 갈 수 있는 곳이 없다면 종료
			if (position == null) {
				break;
			}
			
			// 갈 수 있는 곳이 있다면 그에 따라 진행
			// 이동횟수 증가
			moveCount += position.depth - 1;
			// 먹은 위치를 0으로
			map[position.x][position.y] = 0;
			
			
			// 먹음 카운트 + 1
			eaten++;
			// 충분히 먹었다면 사이즈 증가
			if (eaten == size) {
				eaten = 0;
				size++;
			}
			
			// 다음 위치를 이동한 위치로 변경
			p = position;
		}
	}

	// 주어진 좌표에서 시작해서 가장 가깝고, 갈 수 있는 위치를 구하는 메서드
	private static Position getClosest(int x, int y) {
		// 방문 기록용
		boolean[][] visited = new boolean[N][N];
		// bfs 탐색의 깊이
		int depth = 1;
		// 이동의 후보가 들어가는 객체
		Position candi = null;
		// 조사중인 위치들이 들어가는 큐
		Queue<Position> queue = new LinkedList<>();
		// 주어진 좌표부터 시작
		queue.add(new Position(x, y, 0));
		// 큐가 빌때까지(더이상 갈 수 있는 곳이 없을 때 까지) 반복
		while (!queue.isEmpty()) {
			// 큐에서 하나 꺼내고
			Position temp = queue.poll();
			// temp.depth == depth 이면 깊이가 한단계 깊어진 것이므로 기존 탐색 깊이내의 검색이 완료된 것임
			// 같은 탐색 깊이내의 검색이 완료되었다면 candi가 null이냐 아니냐에 따라 행동결정
			// 아직 null이면 깊이면 업데이트
			if (temp.depth == depth) {
				if (candi != null) {
					break;
				}
				depth++;
			}
			// 4방탐색을 하는데 조건이 맞지 않으면 그냥 날림
			// 1. 공간의 범위를 벗어나거나
			// 2. 나보다 큰 물고기가 있는 곳이거나
			// 3. 이미 방문한 곳이거나
			for (int i = 0; i < 4; i++) {
				if (temp.x + dx[i] < 0 || temp.x + dx[i] > N - 1 || temp.y + dy[i] < 0 || temp.y + dy[i] > N - 1) {
					continue;
				}
				if (map[temp.x + dx[i]][temp.y + dy[i]] > size) {
					continue;
				}
				// 방문한 곳이 아니라면 큐에 넣고 방문처리
				if (!visited[temp.x + dx[i]][temp.y + dy[i]]) {
					queue.add(new Position(temp.x + dx[i], temp.y + dy[i], depth));
					visited[temp.x + dx[i]][temp.y + dy[i]] = true;
				}
			}

			// candi의 업데이트
			// 해당 위치에 물고기가 있고 먹을 수 있는 곳이면 업데이트 하는데
			// null이었다면 새 객체 부여
			// null이 아니라면 문제 조건상 우선순위가 높은 것으로 업데이트
			if (map[temp.x][temp.y] != 0 && map[temp.x][temp.y] < size) {
				if (candi == null) {
					candi = new Position(temp.x, temp.y, depth);
				} else {
					if (candi.x > temp.x) {
						candi = new Position(temp.x, temp.y, depth);
					} else if (candi.x == temp.x) {
						if (candi.y > temp.y) {
							candi = new Position(temp.x, temp.y, depth);
						}
					}
					
				}
			}
		}

		// candi의 초깃값이 null이므로, 업데이트가 안되었다면 null이 반환될 것임
		return candi;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st;
		map = new int[N][N];

		// 아기 상어의 좌표
		int x = 0;
		int y = 0;

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				// 아기상어의 위치를 받으면, 해당 위치엔 0을 넣고, 좌표만 따옴
				if (map[i][j] == 9) {
					map[i][j] = 0;
					x = i;
					y = j;
				}
			}
		}

		// 아기상어 시작 후 움직인 횟수 출력
		babyShark(new Position(x, y, 0));
		System.out.println(moveCount);
	}

}
