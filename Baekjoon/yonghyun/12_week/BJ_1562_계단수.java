import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 계단 수
 * https://www.acmicpc.net/problem/1562
 *
 * 1. 0~9까지의 숫자 사용 여부를 체크할 때 비트마스킹을 사용할 수 있다.
 * 2. 경우의 수가 매우 많으므로 dp를 사용해야 한다.
 * 3. 수의 길이, 계단 수의 특징을 이용할 끝 숫자, 비트마스킹 총 3가지를 체크해야 하므로 3차원 배열이 필요하다.
 *
 * @author 배용현
 *
 */
class BJ_1562_계단수 {

	static final int MOD = 1_000_000_000;
	static int N;
	static int[][][] dp;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException {
		input();
		solution();
		print();
	}
	
	private static void input() throws IOException {
		N = Integer.parseInt(br.readLine());
		dp = new int[N+1][10][1 << 10];		// dp[i][j][k] = k비트를 방문한 i번째 숫자가 j인 계단수를 만드는 경우의 수
		for (int i = 1; i < 10; i++) {		// 1자리 숫자의 계단수는 1가지 (0 제외)
			dp[1][i][1<<i] = 1;		// 방문처리도 자기 자신만
		}
	}

	private static void solution() {
		for (int i = 2; i <= N; i++) {		// i: 자리 수
			for (int j = 0; j < 10; j++) {		// j: i자리의 숫자
				for (int k = 0; k < (1 << 10); k++) {		// k: 숫자 사용 여부
					int newBit = k | (1<<j);		// j를 포함한 방문처리 비트

					if (j == 0) {		// j가 0일땐 이전 수가 반드시 1이어야 함
						dp[i][j][newBit] += dp[i-1][j+1][k];
					} else if (j == 9) {		// j가 9일땐 이전 수가 반드시 8이어야 함
						dp[i][j][newBit] += dp[i-1][j-1][k];
					} else {		// 나머지는 양쪽 모두 가능
						dp[i][j][newBit] += (dp[i-1][j-1][k] + dp[i-1][j+1][k]) % MOD;
					}

					dp[i][j][newBit] %= MOD;		// 더했을때 MOD를 넘어갈 수 있으므로 나머지 연산해서 저장
				}
			}
		}
	}

	private static void print() {
		int answer = 0;

		for (int i = 0; i < 10; i++) {		// i: 완성된 수의 끝 숫자
			answer += dp[N][i][(1<<10)-1];		// 모든 숫자를 사용한 경우의 수를 더함
			answer %= MOD;		// 더했을때 MOD를 넘어갈 수 있으므로 나머지 연산해서 저장
		}

		System.out.print(answer);
	}

}