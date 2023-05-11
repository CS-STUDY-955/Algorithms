import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 상어 중학교
 * https://www.acmicpc.net/problem/21609
 *
 * 1. 필요한 변수
 * 	- 무지개 블록의 수와 블록의 리스트가 속한 블록 그룹
 * 	- 블록의 색깔이 기록된 맵
 * 	- 블록의 방문 여부를 체크할 방문 배열
 *
 * 2. 로직
 *  - 주어진 조건대로 블록 그룹을 찾는다.
 *  - 모든 칸에 대해 블록 그룹을 찾은 뒤 비교하여 가장 우선순위가 높은 블록 그룹만 남긴다.
 *  - 해당 블록 그룹에 대한 처리 연산을 수행한다.
 *
 * 3. 주의할 점
 * - 블록 그룹의 비교 연산시 행과 열을 순서대로 접근하면 비교 연산에 따로 기술할 필요가 없다.
 * - 무지개 블록은 여러 그룹에서 사용할 수 있으므로 무지개 블록을 체크하는 방문 배열과 체크하지 않는 방문 배열을 따로 사용해야 한다.
 *
 * @author 배용현
 *
 */
class BJ_21609_상어중학교 {

	static class Point {
		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static class BlockGroup implements Comparable<BlockGroup> {
		int rainbowNum;
		ArrayList<Point> blocks;

		public BlockGroup(int rainbowNum, ArrayList<Point> blocks) {
			this.rainbowNum = rainbowNum;
			this.blocks = blocks;
		}

		@Override
		public int compareTo(BlockGroup blockGroup) {
			if (blocks.size() != blockGroup.blocks.size()) {		// 크기가 다르면 크기가 큰 그룹이 우선
				return blocks.size() - blockGroup.blocks.size();
			} else if (rainbowNum != blockGroup.rainbowNum) {		// 크기가 같으면 무지개 블록이 많은 그룹이 우선
				return rainbowNum - blockGroup.rainbowNum;
			} else {		// 둘 다 같으면 행, 열이 앞서는 순서인데 이미 bfs 접근을 해당 순서로 하므로 먼저 등록된 그룹이 우선순위가 높음
				return -1;
			}
		}
	}

	static int N, M, answer;
	static int[][] map;
	static boolean[][] colorVisited;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static final int[] dx = {0, 0, -1, 1};		// 위쪽, 아래쪽, 왼쪽, 오른쪽
	static final int[] dy = {-1, 1, 0, 0};

	public static void main(String[] args) throws IOException {
		input();
		solution();
		System.out.println(answer);
	}

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());		// 맵 크기
		M = parseInt(st.nextToken());		// 색깔 수 (사용 안함)
		answer = 0;

		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	}

	private static void solution() {
		while (true) {
			colorVisited = new boolean[N][N];		// 방문 체크 배열 (무지개 블록 제외)
			BlockGroup bestGroup = null;

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (!colorVisited[i][j] && map[i][j] > 0) {
						BlockGroup curBlockGroup = bfs(i, j);
						if (curBlockGroup.blocks.size() > 1 && (bestGroup == null || bestGroup.compareTo(curBlockGroup) < 0)) {		// 첫 블록 그룹이거나 우선순위가 현재 블록 그룹이 높은 블록 그룹이면 교체
							bestGroup = curBlockGroup;
						}
					}
				}
			}

			if (bestGroup == null) {		// 모든 맵을 찾았는데도 그룹이 없으면 종료
				break;
			}

			for (Point p : bestGroup.blocks) {		// 가장 우선순위가 높은 그룹의 블록들을 제거
				map[p.y][p.x] = -2;
			}

			answer += bestGroup.blocks.size() * bestGroup.blocks.size();		// 블록 수의 제곱만큼 점수 증가
			fall();
			rotate();
			fall();
		}
	}

	private static BlockGroup bfs(int sy, int sx) {
		Queue<Point> q = new LinkedList<>();
		int color = map[sy][sx];		// 현재 블록 색깔
		int rainbowNum = 0;		// 무지개 블록 포함 개수
		boolean[][] visited = new boolean[N][N];		// 방문 체크 배열 (무지개 블록 포함)
		ArrayList<Point> blocks = new ArrayList<>();		// 그룹에 속한 블록의 위치 정보
		q.add(new Point(sx, sy));
		visited[sy][sx] = true;
		blocks.add(new Point(sx, sy));
		if (map[sy][sx] == 0) {		// 무지개 블록이면 개수를 세고
			rainbowNum++;
		} else {		// 아니면 방문 처리
			colorVisited[sy][sx] = true;
		}

		while(!q.isEmpty()) {
			Point cur = q.poll();
			int x = cur.x;
			int y = cur.y;

			for (int i = 0; i < 4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];

				if (isOut(nx, ny) || visited[ny][nx] || (map[ny][nx] != color && map[ny][nx] != 0)) {
					continue;
				}

				q.add(new Point(nx, ny));
				visited[ny][nx] = true;
				blocks.add(new Point(nx, ny));
				if (map[ny][nx] == 0) {
					rainbowNum++;
				} else {
					colorVisited[ny][nx] = true;
				}

			}
		}

		return new BlockGroup(rainbowNum, blocks);
	}

	private static boolean isOut(int x, int y) {
		return x < 0 || y < 0 || x > N - 1 || y > N - 1;
	}

	private static void fall() {		// 중력이 작용하는 메서드
		for (int i = N - 2; i > -1; i--) {		// 검사할 행 좌표를 아래쪽부터 접근
			for (int x = 0; x < N; x++) {		// 검사할 열 좌표는 순서대로 접근
				if (map[i][x] < 0) {		// 중력이 작용하지 않는 블럭은 패스
					continue;
				}

				int k = i + 1;		// 이동할 행 좌표
				for (; k < N; k++) {		// 최종적으로 블럭이 떨어질 위치를 구함
					if (map[k][x] != -2)		// 빈칸이 아니면 바닥임
						break;
				}

				if (--k == i) {        // 떨어질 최종 위치가 제자리인 경우 패스
					continue;
				}

				map[k][x] = map[i][x];		// 블록을 떨어뜨리고
				map[i][x] = -2;		// 원래 칸은 빈칸으로 수정
			}
		}
	}

	private static void rotate() {		// 반시계방향으로 90도 회전하는 메서드
		int[][] newMap = new int[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				newMap[N - 1 - j][i] = map[i][j];
			}
		}

		map = newMap;
	}

}