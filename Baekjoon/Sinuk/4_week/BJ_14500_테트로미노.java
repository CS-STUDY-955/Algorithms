import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 14500번 테트로미노
// https://www.acmicpc.net/problem/14500
public class BJ_14500_테트로미노 {
	// 각각의 테트로미노의 배열
	// 인덱스 0~4번인 네모, ㅣ, ㅡ, ㅗ, ㅜ은 좌우대칭임
	// 인덱스 5~11번인 ㅓ, ㄴ, ㄱ, 상하로 긴 ㄴ, 상하로 긴 ㄱ, 누은 내려가는 계단, 일어선 내려가는 계단은 좌우대칭이 아님
	// 총 5+7*2 = 19 가지
	private static int[][] tetromino_x = {
			{0, 0, 1, 1}, // 네모
			{0, 1, 2, 3}, // ㅣ
			{0, 0, 0, 0}, // ㅡ
			{-1, 0, 0, 0}, // ㅗ
			{0, 0, 0, 1}, // ㅜ
			{-1, 0, 0, 1}, // ㅓ
			{0, 1, 1, 1}, // ㄴ
			{0, 0, 0, 1}, // ㄱ
			{0, 1, 2, 2}, // 상하로 긴 ㄴ
			{0, 0, 1, 2}, // 상하로 긴 ㄱ
			{0, 0, 1, 1}, // 누은 내려가는 계단
			{0, 1, 1, 2} // 일어선 내려가는 계단
	};
	private static int[][] tetromino_y = {
			{0, 1, 0, 1}, // 네모
			{0, 0, 0, 0}, // ㅣ
			{0, 1, 2, 3}, // ㅡ
			{1, 0, 1, 2}, // ㅗ
			{0, 1, 2, 1}, // ㅜ
			{1, 0, 1, 1}, // ㅓ
			{0, 0, 1, 2}, // ㄴ
			{0, 1, 2, 2}, // ㄱ
			{0, 0, 0, 1}, // 상하로 긴 ㄴ
			{0, 1, 1, 1}, // 상하로 긴 ㄱ
			{0, 1, 1, 2}, // 누은 내려가는 계단
			{0, 0, 1, 1} // 일어선 내려가는 계단
	};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[][] paper = new int[N + 6][M + 6]; // 사용할 인덱스: 3~N+2/3~M+2

		for (int i = 3; i <= N+2; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 3; j <= M+2; j++) {
				paper[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int max = 0;
		// paper 각각의 인덱스에 대해
		for (int i = 3; i <= N+2; i++) {
			for (int j = 3; j <= M+2; j++) {
				// 좌우대칭인 것들에 대해 각각의 경우의 합 구하기
				for (int k = 0; k < 5; k++) {
					int sum = 0;
					for (int l = 0; l < 4; l++) {
						sum += paper[i + tetromino_x[k][l]][j + tetromino_y[k][l]];
					}
					max = Math.max(max, sum);
				}

				// 좌우 대칭이 아닌 것들에 대해 좌우대칭 시킨 결과로도 각각의 경우의 합 구하기
				for (int k = 5; k < 12; k++) {
					int sum = 0;
					int sum2 = 0;
					for (int l = 0; l < 4; l++) {
						sum += paper[i + tetromino_x[k][l]][j + tetromino_y[k][l]];
						sum2 += paper[i + tetromino_x[k][l]][j - tetromino_y[k][l]];
					}
					max = Math.max(max, sum);
					max = Math.max(max, sum2);
				}
			}
		}

		System.out.println(max);
	}
}
