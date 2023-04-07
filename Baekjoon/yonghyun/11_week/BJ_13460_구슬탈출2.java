import java.io.*;
import java.util.*;

/**
 * 구슬 탈출 2
 * https://www.acmicpc.net/problem/13460
 *
 * 1. 네 방향으로 기울이는 메서드가 필요하다.
 * 2. 최대 10번까지 움직이는 bfs를 구현하되, 파란 구슬이 빠지면 백트래킹하고, 빨간 구슬만 빠지면 종료한다.
 * 3. 공이 겹칠 수는 없으므로 기울였을때 따로 처리해줘야 한다.
 *
 * @author 배용현
 *
 */
class Location {
	int ry, rx, by, bx, cnt;
	
	public Location(int ry, int rx, int by, int bx, int cnt) {
		this.ry = ry;
		this.rx = rx;
		this.by = by;
		this.bx = bx;
		this.cnt = cnt;
	}
}

public class BJ_13460_구슬탈출2 {
	static int N, M, firstRY, firstRX, firstBY, firstBX, answer;
	static char[][] map;
	static boolean[][][][] visited;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	
	public static void main(String[] args) throws Exception {
		input();
		solution();
		System.out.print(answer>10 ? -1 : answer);		// 10회를 넘어버리면 -1 출력
	}

	private static void solution() {
		Queue<Location> q = new LinkedList<>();
		q.add(new Location(firstRY, firstRX, firstBY, firstBX, 1));
		visited[firstRY][firstRX][firstBY][firstBX] = true;
		while(!q.isEmpty()) {
			Location cur = q.poll();
			int ry = cur.ry;
			int rx = cur.rx;
			int by = cur.by;
			int bx = cur.bx;
			int cnt = cur.cnt;

			for (int i = 0; i < 4; i++) {		// 모든 방향에 대해 진행
				int newRy = ry;
				int newRx = rx;
				int newBy = by;
				int newBx = bx;

				boolean redIn = false;		// 빨간공이 들어갔으면 true
				boolean blueIn = false;		// 파란공이 들어갔으면 true

				while (map[newRy + dy[i]][newRx + dx[i]] != '#') {		// 벽을 만나기 전엔 멈추지 않아
					newRy += dy[i];
					newRx += dx[i];
					if (map[newRy][newRx] == 'O') {
						redIn = true;
						break;
					}
				}

				while (map[newBy + dy[i]][newBx + dx[i]] != '#') {		// 파란 공도 마찬가지
					newBy += dy[i];
					newBx += dx[i];
					if (map[newBy][newBx] == 'O') {
						blueIn = true;
						break;
					}
				}

				if (blueIn) {		// 파란공 들어가면 실패
					continue;
				} else if (redIn) {		// 파란공 안들어가고 빨간공만 들어갔으면 성공
					answer = cnt;
					q.clear();
					break;
				} else if (newRy == newBy && newRx == newBx) {		// 두 공이 겹쳤으면
					if (i == 0) {		// 기울인 방향에 따라 공을 다르게 놔줌 (먼저 간 공이 자리를 차지함)
						if (rx > bx)
							newRx -= dx[i];
						else
							newBx -= dx[i];
					} else if (i == 1) {
						if (rx > bx)
							newBx -= dx[i];
						else
							newRx -= dx[i];
					} else if (i == 2) {
						if (ry > by)
							newRy -= dy[i];
						else
							newBy -= dy[i];
					} else {
						if (ry > by)
							newBy -= dy[i];
						else
							newRy -= dy[i];
					}
				}

				if (!visited[newRy][newRx][newBy][newBx]) {
					q.add(new Location(newRy, newRx, newBy, newBx, cnt + 1));
					visited[newRy][newRx][newBy][newBx] = true;
				}
			}
		}
	}

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new char[N][M];
		visited = new boolean[N][M][N][M];		// 한 시점의 Ry, Rx, By, Bx의 위치에 따라 방문처리

		for(int i=0; i<N; i++) {
			String input = br.readLine();
			for(int j=0; j<M; j++) {
				switch(input.charAt(j)) {
					case '#': map[i][j] = '#'; break;
					case 'O': map[i][j] = 'O'; break;
					case 'R': map[i][j] = '.'; firstRY = i; firstRX = j; break;
					case 'B': map[i][j] = '.'; firstBY = i; firstBX = j; break;
					default: map[i][j] = '.';
				}
			}
		}

		firstRY = firstRX = firstBY = firstBX = 0;
		answer = -1;
	}
}