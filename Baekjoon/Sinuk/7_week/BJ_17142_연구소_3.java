import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_17142_연구소_3 {
	private static int min = Integer.MAX_VALUE;
	private static int count;
	// 상 좌 하 우
	private static int[] dx = { -1, 0, 1, 0 };
	private static int[] dy = { 0, -1, 0, 1 };
	private static int[] activated;
	private static int[][] lab;
	private static ArrayList<Point> virus;

	// bfs용 객체
	private static class Virus {
		int x, y, depth;

		public Virus(int x, int y, int depth) {
			this.x = x;
			this.y = y;
			this.depth = depth;
		}

		public Virus(Point p, int depth) {
			this(p.x, p.y, depth);
		}
	}

	// 0번에서 virus.size() - 1번까지 조합을 만든 뒤 spread 호출
	private static void activate(int step, int m, int startIdx) {
		if (step == m) {
			min = Math.min(min, spread(m));
			return;
		}

		for (int i = startIdx; i < virus.size(); i++) {
			activated[step] = i;
			activate(step + 1, m, i + 1);
		}
	}

	// bfs를 이용하여 퍼트림
	private static int spread(int m) {
		int infected = 0;
		int depth = 0;
		boolean[][] visited = new boolean[lab.length][lab.length];
		Queue<Virus> queue = new LinkedList<>();
		for (int i = 0; i < m; i++) {
			queue.add(new Virus(virus.get(activated[i]), 0));
			visited[virus.get(activated[i]).x][virus.get(activated[i]).y] = true;
		}

		while (!queue.isEmpty()) {
			Virus v = queue.poll();
			// 모든 방이 감염되었다면 종료
			if (infected == count) {
				break;
			}
			// 기존에 구한 값보다 오래 걸린다면 미리 종료하여 백트래킹
			if (min <= depth) {
				break;
			}
			//이하는 여느 bfs와 동일
			if (v.depth == depth) {
				depth++;
			}
			for (int i = 0; i < 4; i++) {
				int nx = v.x + dx[i];
				int ny = v.y + dy[i];
				if (lab[nx][ny] == 1) {
					continue;
				} else if (!visited[nx][ny]) {
					if (lab[nx][ny] == 0)
						infected++;
					visited[nx][ny] = true;
					queue.add(new Virus(nx, ny, depth));
				}
			}
		}

		// 종료되었는데 만약 감염수와 처음 빈칸수가 동일하면 퍼진 시간 반환
		// 다르다면 모든 방에 감염이 안되는 경우
		if (infected == count) {
			return depth;
		} else {
			return Integer.MAX_VALUE;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		lab = new int[n + 2][n + 2];
		virus = new ArrayList<>();

		// 빈 칸의 갯수
		count = 0;
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++) {
				lab[i][j] = Integer.parseInt(st.nextToken());
				if (lab[i][j] == 0) {
					count++;
				// 바이러스가 있는 곳이면 list에 입력
				} else if (lab[i][j] == 2) {
					virus.add(new Point(i, j));
				}
			}
		}

		// 패딩 설정
		for (int i = 0; i < n + 2; i++) {
			lab[0][i] = 1;
			lab[n + 1][i] = 1;
			lab[i][0] = 1;
			lab[i][n + 1] = 1;
		}

		// 조합 호출
		activated = new int[m];
		activate(0, m, 0);
		// 결과값 출력
		if (min == Integer.MAX_VALUE) {
			System.out.println(-1);
		} else {
			System.out.println(min);
		}
	}
}
