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
 * ------------------------------------------------------------------
 * - 입력에 주어진 순서가 사전순이 아니고 상좌하우가 사전순이다. 따로 노트라는 항목이 있었음..
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
	static final int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};		// ←, ↖, ↑, ↗, →, ↘, ↓, ↙
	static final int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};
	static final int[] sd = {4, 6, 0, 2};		// 우선순위가 낮은 것부터 기술하여 덮어씌우도록 (우하좌상)

	public static void main(String[] args) throws IOException {
		input();
		solution();
		print();
	}

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		M = parseInt(st.nextToken());		// 물고기 수
		S = parseInt(st.nextToken());		// 마멉 시전 횟수

		fish = new ArrayList[4][4];		// 각 칸에 저장된 물고기(방향)
		smell = new int[4][4];		// 각 칸에 남아있는 냄새의 농도
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

	private static void save() {		// 마법을 시전해서 복사될 물고기 상태를 캡쳐하는 메서드
		copy = new ArrayList[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				copy[i][j] = new ArrayList<>(fish[i][j]);
			}
		}
	}

	private static void moveFish() {		// 물고기가 이동하는 메서드
		ArrayList<Integer>[][] newFish = new ArrayList[4][4];		// 중복 이동이 일어나지 않도록 이동한 맵은 따로 생성
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				newFish[i][j] = new ArrayList<>();
			}
		}

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int d : fish[i][j]) {		// 모든 좌표의 물고기에 대해 수행
					boolean moved = false;		// 움직였는지 확인할 변수

					for (int k = 0; k < 8 && !moved; k++) {		// 최대 8번 방향을 바꾸고 중간에 움직였다면 탈출
						int nd = ((d - k) + 8) % 8;		// -1이 되면 7로 변환
						int ny = i + dy[nd];
						int nx = j + dx[nd];

						if (isOut(nx, ny) || isShark(nx, ny) || smell[ny][nx] != 0) {		// 격자 밖이거나 상어의 위치거나 피냄새가 나는 곳은 못감
							continue;
						}

						newFish[ny][nx].add(nd);		// 물고기 이동
						moved = true;		// 체크
					}

					if (!moved) {		// 이동안했으면 현재 위치와 방향 고수
						newFish[i][j].add(d);
					}
				}
			}
		}

		fish = newFish;		// fish 갱신
	}

	private static boolean isOut(int x, int y) {
		return x < 0 || y < 0 || x > 3 || y > 3;
	}

	private static boolean isShark(int nx, int ny) {
		return nx==x && ny==y;
	}

	private static void moveShark() {		// 상어가 이동하는 메서드
		maxEaten = 0;		// 최대로 먹을 수 있는 물고기 수
		maxMove = new int[3];		// maxEaten만큼 먹는 상어의 움직임을 저장
		visited = new int[4][4];		// 이미 움직인 곳은 먹이를 먹었으므로 중복 섭취 방지
		dfs(0, 0, x, y, new int[]{-1, -1, -1});		// 3번 dfs 수행해서 가장 맛있는 경로 탐색
		for(int i = 0; i < 3; i++) {		// 상어를 이동
			x += dx[maxMove[i]];
			y += dy[maxMove[i]];
			if(fish[y][x].size() != 0) {		// 먹이가 있으면 섭취
				fish[y][x].clear();
				smell[y][x] = 3;
			}
		}
	}

	private static void dfs(int idx, int eaten, int x, int y, int[] curMove) {		// 가장 맛있는 경로를 탐색하는 메서드
		if (idx == 3) {
			if (eaten >= maxEaten) {
				maxEaten = eaten;
				System.arraycopy(curMove, 0, maxMove, 0, 3);
			}

			return;
		}

		for (int i = 0; i < 4; i++) {		// 우, 하, 좌, 상으로 탐색해서 동일한 개수일때 덮어씌워질 수 있도록
			int nx = x + dx[sd[i]];
			int ny = y + dy[sd[i]];
			if (isOut(nx, ny)) {
				continue;
			}

			curMove[idx] = sd[i];		// 현재 탐색중인 경로 저장
			if(visited[ny][nx] == 0) {		// 방문한적 없는 칸이면 물고기 섭취
				visited[ny][nx]++;
				dfs(idx + 1, eaten + fish[ny][nx].size(), nx, ny, curMove);
			} else {		// 방문했던 칸이면 물고기 재섭취 X
				visited[ny][nx]++;
				dfs(idx + 1, eaten, nx, ny, curMove);
			}

			visited[ny][nx]--;		// 리턴한 뒤 방문체크 해제
		}
	}

	private static void removeSmell() {		// 냄새가 옅어지는 메서드
		for(int i = 0 ; i < 4;  i++) {
			for(int j = 0 ; j < 4; j++) {
				if (smell[i][j] != 0) {
					smell[i][j]--;
				}
			}
		}
	}

	private static void copy() {		// 캡쳐해놧던 복제가 실제로 일어나는 메서드
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