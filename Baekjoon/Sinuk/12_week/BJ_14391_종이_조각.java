import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 각 칸을 0 또는 1로 마스킹
// 0이라면 행으로 이어지는 종이조각으로 취급하여 계산
// 1이라면 열로 이어지는 종이조각으로 취급하여 계산
public class BJ_14391_종이_조각 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] paper = new int[n][m];
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            for (int j = 0; j < m; j++) {
                paper[i][j] = str.charAt(j) - '0';
            }
        }

        int max = 0;
        for (int k = 0; k < (1 << (n * m)); k++) {
            int sum = 0;
            // 행 단위로 0인 경우 체크
            for (int i = 0; i < n; i++) {
                int cur = 0;
                if ((k & (1 << (i * m))) == 0)
                    cur = paper[i][0];
                sum += cur;
                for (int j = 1; j < m; j++) {
                    if ((k & (1 << (i * m + j))) == 0) {
                        sum += 9 * cur + paper[i][j];
                        cur = 10 * cur + paper[i][j];
                    } else {
                        cur = 0;
                    }
                }
            }
            // 열 단위로 1인 경우 체크
            for (int j = 0; j < m; j++) {
                int cur = 0;
                if ((k & (1 << j)) != 0)
                    cur = paper[0][j];
                sum += cur;
                for (int i = 1; i < n; i++) {
                    if ((k & (1 << (i * m + j))) != 0) {
                        sum += 9 * cur + paper[i][j];
                        cur = 10 * cur + paper[i][j];
                    } else {
                        cur = 0;
                    }
                }
            }
            // 최댓값이면 갱신
            if (max < sum) {
                max = sum;
            }
        }

        System.out.println(max);
    }
}
