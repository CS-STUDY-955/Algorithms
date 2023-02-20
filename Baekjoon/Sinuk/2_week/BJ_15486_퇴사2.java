import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// Q15486 퇴사 2
public class BJ_15486_퇴사2 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int[] t = new int[n + 1]; // 걸리는 시간
		int[] p = new int[n + 1]; // 받는 금액
		StringTokenizer st;
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			t[i] = Integer.parseInt(st.nextToken());
			p[i] = Integer.parseInt(st.nextToken());
		}

		int[] memo = new int[n + 1];
		for (int i = 0; i < n; i++) {
			// System.out.println(i);
			// System.out.println(memo[i][0]+", "+ memo[i][1]+", "+ memo[i + 1][0]);
			memo[i + 1] = Math.max(memo[i], memo[i + 1]);
			if (i + t[i] <= n) {
				// System.out.println(memo[i + t[i]][0]+", "+ (memo[i][0] + p[i]));
				memo[i + t[i]] = Math.max(memo[i + t[i]], memo[i] + p[i]);
			}

			// 확인용 코드
			// for (int j = 0; j <= n; j++) {
			// 	System.out.print(memo[j][0] + " ");
			// }
			// System.out.println();
		}
		System.out.println(memo[n]);
	}
}