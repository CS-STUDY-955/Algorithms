import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_1937_욕심쟁이_판다 {
	private static int n;
	// 상 좌 하 우
	private static int[] dx = { -1, 0, 1, 0 };
	private static int[] dy = { 0, -1, 0, 1 };
	private static int[][] bamboo;
	private static int[][] memo;
	private static boolean[][] visited;

	private static int dfs(int x, int y) {
		boolean lastFlag = false;

    // 4방 탐색
		for (int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
      // 범위 밖이면 패스
			if (0 > nx || nx >= n || 0 > ny || ny >= n) {
				continue;
			}
      // 아직 방문하지 않았고, 여기보다 대나무가 적은 곳이 있다면
			if (!visited[nx][ny] && bamboo[x][y] > bamboo[nx][ny]) {
        // 일단 종점은 아니니 플래그 올리고
        lastFlag = true;
        // 이미 dfs로 간적 있는곳이면
				if (memo[nx][ny] != 0) {
          // 그런데 거기가 현재 자신의 값보다 더 길다면
					if (memo[x][y] <= memo[nx][ny]) {
						memo[x][y] = memo[nx][ny] + 1;
					}
				}
				else {
          // 방문처리후 업데이트 하고 방문처리 해제
					visited[nx][ny] = true;
					memo[x][y] = Math.max(memo[x][y], dfs(nx, ny) + 1);
					visited[nx][ny] = false;
				}
			}
		}
		
    // 종점이면 1 저장
		if (!lastFlag) {
			memo[x][y] = 1;
		}

		return memo[x][y];
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		StringTokenizer stringTokenizer;
		bamboo = new int[n][n];
		visited = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			stringTokenizer = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				bamboo[i][j] = Integer.parseInt(stringTokenizer.nextToken());
			}
		}

		memo = new int[n][n];

    // memo[i][j]이 0이면 = 아직 dfs로 방문한 적이 없으면 거기서 dfs 시작
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (memo[i][j] == 0) {
					visited[i][j] = true;
					memo[i][j] = dfs(i, j);
					visited[i][j] = false;
				}
			}
		}

    // 최댓값 찾아서 출력
		int max = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				max = Math.max(max, memo[i][j]);
			}
		}

		System.out.println(max);
	}
}
