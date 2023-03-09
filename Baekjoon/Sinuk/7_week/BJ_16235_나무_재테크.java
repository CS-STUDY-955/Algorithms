import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_16235_나무_재테크 {

	private static class Tree implements Comparable<Tree> {
		int x, y, old;

		public Tree(int x, int y, int old) {
			super();
			this.x = x;
			this.y = y;
			this.old = old;
		}

		@Override
		public int compareTo(Tree o) {
//			if (this.old == o.old) {
//				if (this.x == o.x) {
//					return this.y - o.y;
//				} else {
//					return this.x - o.x;
//				}
//			} else {
//				return this.old - o.old;
//			}
			return this.old - o.old;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int[][] land = new int[n + 2][n + 2];
		int[][] A = new int[n + 2][n + 2];
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
				land[i][j] = 5;
			}
		}

		for (int i = 0; i < n + 2; i++) {
			land[0][i] = -1;
			land[n + 1][i] = -1;
			land[i][0] = -1;
			land[i][n + 1] = -1;
		}

		// 12시방향에서 시계방향으로
		int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
		int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1 };

		PriorityQueue<Tree> trees = new PriorityQueue<>();
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int old = Integer.parseInt(st.nextToken());
			trees.add(new Tree(x, y, old));
		}

		while (k-- > 0) {
			// 봄
			ArrayList<Tree> tempTrees = new ArrayList<>();
			ArrayList<Tree> deadTrees = new ArrayList<>();
			while (!trees.isEmpty()) {
				Tree temp = trees.poll();
				if (land[temp.x][temp.y] >= temp.old) {
					land[temp.x][temp.y] -= temp.old;
					temp.old++;
					tempTrees.add(temp);
				} else {
					deadTrees.add(temp);
				}
			}

			// 여름
			for (int i = 0; i < deadTrees.size(); i++) {
				Tree temp = deadTrees.get(i);
				land[temp.x][temp.y] += temp.old / 2;
				
			}

			// 가을
			trees = new PriorityQueue<>();
			for (int i = 0; i < tempTrees.size(); i++) {
				Tree temp = tempTrees.get(i);
				trees.add(temp);
				if (temp.old % 5 == 0) {
					for (int j = 0; j < 8; j++) {
						if (land[temp.x + dx[j]][temp.y + dy[j]] == -1)
							continue;
						trees.add(new Tree(temp.x + dx[j], temp.y + dy[j], 1));
					}
				}

			}

			// 겨울
			for (int i = 1; i <= n; i++) {
				for (int j = 1; j <= n; j++) {
					land[i][j] += A[i][j];
				}
			}

			// 테스트용 출력
			// trees 출력
//			for (Tree t : trees) {
//				System.out.printf("%d, %d, %d\n", t.x, t.y, t.old);
//			}
//			// land 출력
//			for (int i = 1; i <= n; i++) {
//				System.out.println(Arrays.toString(land[i]));
//			}
		}

		System.out.println(trees.size());
	}
}
