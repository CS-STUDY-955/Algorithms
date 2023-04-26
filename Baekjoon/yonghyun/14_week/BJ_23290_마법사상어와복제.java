import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

/**
 * 마법사 상어와 복제
 * https://www.acmicpc.net/problem/23290
 *
 * 1. 격자의 각 칸을 가리키는 2차원 배열을 생성하되 물고기가 여러마리 들어갈 수 있으므로 ArrayList 2차원 배열 형태로 구현한다.
 * 2. 각 물고기는 방향만 가지고 있으면 되므로 ArrayList의 원소는 Integer로 선언한다.
 * 3. 상어의 위치와 방향은 따로 저장한다.
 * 4. 상어는 3칸을 사전순으로 이동하되 방향을 바꿀 수 있으므로 모든 방향으로 이동해보고 잡아먹는 물고기의 수를 구하여 비교해야 한다.
 * 5. 물고기 냄새는 남은 지속시간을 저장하는 2차원 int형 배열을 사용하여 표현한다.
 *
 * @author 배용현
 *
 */
class BJ_23290_마법사상어와복제 {

	static int M, S, y, x;
	static int maxEaten = 0;		// 이동 완료시 총 먹은 물고기 양
	static ArrayList<Integer>[][] fish, copy;
	static int[][] smell;
	static int[] maxMove;
	static int[][] visited;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static final int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
	static final int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};
	static final int[] sd = {6, 4, 2, 0};		// 우선순위가 낮은 것부터 기술하여 덮어씌우도록

	public static void main(String[] args) throws IOException {
		input();
		solution();
		print();
	}

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		M = parseInt(st.nextToken());
		S = parseInt(st.nextToken());

		fish = new ArrayList[4][4];
		smell = new int[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				fish[i][j] = new ArrayList<>();
			}
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int y = parseInt(st.nextToken()) - 1;
			int x = parseInt(st.nextToken()) - 1;
			int d = parseInt(st.nextToken()) - 1;

			fish[y][x].add(d);
		}

		st = new StringTokenizer(br.readLine());
		y = parseInt(st.nextToken()) - 1;
		x = parseInt(st.nextToken()) - 1;
	}

	private static void solution() {
		for (int i = 0; i < S; i++) {
			save();
			moveFish();
			moveShark();
			removeSmell();
			copy();
		}
	}

	private static void save() {
		copy = new ArrayList[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				copy[i][j] = new ArrayList<>(fish[i][j]);
			}
		}
	}

	private static void moveFish() {
		ArrayList<Integer>[][] newFish = new ArrayList[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				newFish[i][j] = new ArrayList<>();
			}
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int d : fish[i][j]) {
					boolean moved = false;

					for (int k = 0; k < 8 && !moved; k++) {
						int ny = i + dy[d];
						int nx = j + dx[d];

						if (isOut(nx, ny) || isShark(nx, ny) || smell[ny][nx] != 0) {
							continue;
						}

						newFish[i][j].add(d);
						moved = true;
						if (--d < 0) {
							d += 8;
						}
					}

					if (!moved) {
						newFish[i][j].add(d);
					}
				}
			}
		}

		fish = newFish;
	}

	private static boolean isOut(int x, int y) {
		return x < 0 || y < 0 || x > 3 || y > 3;
	}

	private static boolean isShark(int nx, int ny) {
		return nx==x && ny==y;
	}

	private static void moveShark() {
		maxEaten = 0;
		maxMove = new int[3];
		visited = new int[4][4];
		dfs(0, 0, x, y, new int[]{-1, -1, -1});
		boolean[][] visited = new boolean[4][4];
		for(int i = 0 ; i < 3; i++) {
			x += dx[maxMove[i]];
			y += dy[maxMove[i]];
			if(fish[y][x].size() != 0 && !visited[y][x]) {
				visited[y][x] = true;
				fish[y][x].clear();
				smell[y][x] = 3;
			}
		}
	}

	private static void dfs(int idx, int eaten, int x, int y, int[] curMove) {
		if (idx == 3) {
			if (eaten >= maxEaten) {
				maxEaten = eaten;
				System.arraycopy(curMove, 0, maxMove, 0, 3);
			}

			return;
		}

		for (int i = 6; i >= 0; i -= 2) {
			int nx = x + dx[idx];
			int ny = y + dy[idx];
			if (isOut(nx, ny)) {
				continue;
			}

			curMove[idx] = i;
			if(visited[ny][nx] == 0) {
				visited[ny][nx] += 1;
				dfs(idx + 1, eaten + fish[ny][nx].size(), nx, ny, curMove);
			} else {
				visited[ny][nx] += 1;
				dfs(idx + 1, eaten, nx, ny, curMove);
			}

			visited[ny][nx] -= 1;
		}
	}

	private static void removeSmell() {
		for(int i = 0 ; i < 4;  i++) {
			for(int j = 0 ; j < 4; j++) {
				if (smell[i][j] != 0) {
					smell[i][j]--;
				}
			}
		}
	}

	private static void copy() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int d : copy[i][j]) {
					fish[i][j].add(d);
				}
			}
		}
	}

	private static void print() {
		int answer = 0;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				answer += fish[i][j].size();
			}
		}

		System.out.print(answer);
	}

}