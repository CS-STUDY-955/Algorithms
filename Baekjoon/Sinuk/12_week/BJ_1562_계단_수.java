import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BJ_1562_계단_수 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int MOD = 1_000_000_000;
        int n = Integer.parseInt(br.readLine());
        int[][] pre = new int[10][1 << 10];
        int[][] cur;
        for (int i = 1; i < 10; i++) {
            pre[i][1 << i] = 1;
        }
        // 여기에서 DP 시작
        // 각 경우별로 계산해줘야함
        // 1. 0보다 큰 수에서 시작해서 원래 그 수를 사용한 경우
        // 2. 0보다 큰 수에서 시작해서 원래 그 수를 사용한 적이 없는 경우
        // 3. 9보다 작은 수에서 시작해서 원래 그 수를 사용한 경우
        // 4. 9보다 작은 수에서 시작해서 원래 그 수를 사용한 적이 없는 경우
        for (int cnt = 1; cnt < n; cnt++) {
            cur = new int[10][1 << 10];
            for (int j = 1; j < (1 << 10); j++) {
                for (int i = 0; i < 10; i++) {
                    if ((j & (1 << i)) == 0) {
                        continue;
                    }
                    if (i != 0 && (j & (1 << (i - 1))) != 0) {
                        cur[i][j] += pre[i - 1][j ^ (1 << i)] % MOD;
                        cur[i][j] += pre[i - 1][j] % MOD;
                    }
                    if (i != 9 && (j & (1 << (i + 1))) != 0) {
                        cur[i][j] += pre[i + 1][j ^ (1 << i)] % MOD;
                        cur[i][j] += pre[i + 1][j] % MOD;
                    }
                    cur[i][j] %= MOD;
                }
            }
            for (int i = 0; i < 10; i++) {
                pre[i] = cur[i].clone();
            }
        }

        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum = (sum + pre[i][(1 << 10) - 1]) % MOD;
        }
        System.out.println(sum);
    }
}
