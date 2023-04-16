package Gold.Gold1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * bit-masking사용하면 
 * 10자리 비트 0은 1 1은 10 2는 100 3은 1000 4는 10000 이런식으로 작성한다면,
 * << 또는 >> 연산으로 계단수인지 확인 가능
 * 3차원으로 1차원은 N자리수, 2차원은 자리수, 3차원은 정보 bit
 * 
 * @author 박건후
 *
 */
public class BJ_1562_계단수 {
	static final int MOD = 1000000000;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		long[][][] dp = new long[N+1][10][1<<10];
		for(int i = 1;i < 10 ; i++) {
			dp[1][i][1<<i] = 1;
		}
		for(int i = 2 ; i <= N ; i++) {
			for(int j = 0; j < 10 ; j++) {
				for(int visit = 0; visit < (1<<10) ; visit++) {
					int newVisit = visit | (1 << j);
					if( j == 0) {
						dp[i][j][newVisit] += dp[i-1][j+1][visit] % MOD;
					}else if( j == 9) {
						dp[i][j][newVisit] += dp[i-1][j-1][visit] % MOD;
					}else {
						dp[i][j][newVisit] += dp[i-1][j-1][visit] % MOD + dp[i-1][j+1][visit] % MOD;
					}
				}
			}
		}
		long sum = 0;
		for(int i = 0; i < 10 ; i++) {
			sum += dp[N][i][(1<<10)-1] % MOD;
			sum %= MOD;
		}
		System.out.println(sum);
	}

}
