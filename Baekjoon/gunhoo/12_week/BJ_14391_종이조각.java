package Gold.Gold3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_14391_종이조각 {

	static int N; // 세로
	static int M; // 가로
	static int[][] map; // 수를 입력할 종이
	static boolean[][] visited; // 방문 관리
	static int maxVal; // 최댓값

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		visited = new boolean[N][M];
		maxVal = 0;

		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < M; j++) {
				map[i][j] = str.charAt(j) - '0';
			}
		}

		dfs(0, 0);
		System.out.println(maxVal);

	}

	private static void dfs(int row, int col) {
		// 탐색 종료
		if (row >= N) {
			sum();
			return;
		}

		// 하나의 행의 체크 종료 -> 다음 행으로 이동
		if (col >= M) {
			dfs(row + 1, 0);
			return;
		}

		// 가로 숫자로 사용
		visited[row][col] = true;
		dfs(row, col + 1);

		// 세로 숫자로 사용
		visited[row][col] = false;
		dfs(row, col + 1);

	}

	private static void sum() {
		int ret = 0;
		int temp = 0;

		// 가로 숫자 계산
		for (int i = 0; i < N; i++) {
			temp = 0;
			for (int j = 0; j < M; j++) {
				// 가로 덧셈 -> true인 경우
				if (visited[i][j]) {
					temp *= 10; // 자릿수 이동 ex) 1 -> 10
					temp += map[i][j]; // ex) 10 -> 13
				} else {
					ret += temp;
					temp = 0; // temp 변수 초기화
				}
			}

			ret += temp;
		}

		// 세로 숫자 계산
		for (int i = 0; i < M; i++) {
			temp = 0;
			for (int j = 0; j < N; j++) {
				// 세로 덧셈 -> false인 경우
				if (!visited[j][i]) {
					temp *= 10; // 자릿수 이동 ex) 1 -> 10
					temp += map[j][i]; // ex) 10 -> 13
				} else {
					ret += temp;
					temp = 0; // temp 변수 초기화
				}
			}
			ret += temp;
		}
		maxVal = Math.max(maxVal, ret);

	}

}