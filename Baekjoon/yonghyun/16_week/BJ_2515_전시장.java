import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

/**
 * 전시장
 * https://www.acmicpc.net/problem/2515
 *
 * 1. 높이를 기준으로 오름차순 정렬한다.
 * 2. dp[n] = 0 ~ n까지의 그림을 고려했을때 최대 가격
 * 3. dp[n] = MAX(dp[n-1]+C[n] (H[n]), dp[n-1] (H[n-1]))
 *
 * @author 배용현
 *
 */
public class BJ_2515_전시장 {

    static int N, S;
    static int[][] arr;
    static int[][] dp;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    public static void main(String[] args) throws Exception {
        input();
        solution();
        System.out.print(Math.max(dp[N][0], dp[N][1]));
    }

    private static void solution() {
        for (int i = 1; i <= N; i++) {
            // 현재 그림을 선택하지 않은 경우 이전 최대 가치 중 큰 값
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1]);

            // 현재 그림을 선택한 경우 높이가 허용하는 가장 큰 값 + 현재 값
            // 허용하는 높이 값: arr[?][0] - S -> ?를 찾기 위해 이분탐색 수행
            int maxIdx = binarySearch(i);
            if (maxIdx == -1) {
                dp[i][1] = arr[i][1];
            } else {
                dp[i][1] = Math.max(dp[maxIdx][0], dp[maxIdx][1]) + arr[i][1];
            }
        }
    }

    private static int binarySearch(int idx) {      // 허용하는 높이 중 가장 큰 값을 구하는 메서드
        int maxHeight = arr[idx][0] - S;        // 최대 허용 높이: 현재 인덱스의 높이 - S
        if (maxHeight < arr[1][0]) {        // 제일 낮은 높이도 못들어가면 아무데도 못들어감
            return -1;
        }

        int head = 1;       // 최저 높이 인덱스
        int tail = idx - 1;     // 최고 높이 인덱스
        int mid = 0;        // 검사할 인덱스

        while (head < tail) {
            mid = (head + tail) / 2;
            System.out.println(arr[mid][0]);
            if (arr[mid][0] > maxHeight) {        // 검사중인 높이가 허용높이를 초과한 경우
                tail = mid;     // tail을 줄여 높이를 낮춤
            } else {       // 검사중인 높이가 허용 높이보다 작거나 같은 경우
                head = mid + 1;     // head를 늘려 높이를 높임
            }
        }

        return mid;
    }

    private static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        N = parseInt(st.nextToken());
        S = parseInt(st.nextToken());
        arr = new int[N + 1][2];        // 0: 높이, 1: 가격
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            arr[i][0] = parseInt(st.nextToken());
            arr[i][1] = parseInt(st.nextToken());
        }
        Arrays.sort(arr, (o1, o2) -> {
            return o1[0] - o2[0];
        });

        dp = new int[N + 1][2];     // 0: i번째 그림을 선택하지 않았을 때의 최대 가격, 1: 선택했을 때의 최대 가격
        dp[0][0] = dp[0][1] = 0;
    }
}
