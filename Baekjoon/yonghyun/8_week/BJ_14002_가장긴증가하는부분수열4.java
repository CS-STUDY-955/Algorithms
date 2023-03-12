import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 가장 긴 증가하는 부분 수열 4
 * https://www.acmicpc.net/problem/14002
 *
 * 1. 같은 종류의 기초 DP문제인데 수열을 출력해야하는 부분이 다르다.
 * 2. DP 배열은 기존처럼 구하고, 가장 큰 값을 출력한다.
 * 3. 구한 뒤에 DP배열을 역순으로 확인하면서 이전 단계의 값을 하나씩 StringBuilder에 넣고, 역순으로 출력한다.
 *
 * -------------------------------------------
 * - StringBuilder에 넣고 역순으로 출력하면 01 02 03 05와 같이 출력되므로 스택을 이용한다.
 *
 * @author 배용현
 *
 */
public class BJ_14002_가장긴증가하는부분수열4 {

    static int N;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[] arr, dp;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        input();
        solution();
        System.out.print(sb);
    }

    private static void solution() {
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if(arr[i]>arr[j] && dp[i]<dp[j]+1)
                    dp[i] = dp[j] + 1;
            }
        }

        int max = 0;
        for (int cur : dp) {
            if(max<cur)
                max = cur;
        }
        System.out.println(max);

        Stack<Integer> stack = new Stack<>();
        for (int i = N-1; i >= 0; i--) {
            if(dp[i]==max) {
                stack.push(arr[i]);
                max--;
            }
        }

        while (!stack.isEmpty())
            sb.append(stack.pop()).append(' ');
    }

    private static void input() throws IOException {
        N = parseInt(br.readLine());
        arr = new int[N];
        dp = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = parseInt(st.nextToken());
            dp[i] = 1;
        }
    }
}