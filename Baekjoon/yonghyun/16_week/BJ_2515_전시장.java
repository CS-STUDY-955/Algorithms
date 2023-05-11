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
 * 3. dp[n] = MAX(dp[n-1]+C[n] (H[n]), dp[n-1] (H[n-1]))인데 H에 대한 정보는 입력값으로 대체 가능하다
 * 4. N이 최대 300,000이기 때문에 이분탐색으로 탐색 시간을 줄여야한다.
 *
 * @author 배용현
 *
 */
public class BJ_2515_전시장 {

    static int N, S;
    static int[][] arr;
    static int[] dp;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    public static void main(String[] args) throws Exception {
        input();
        solution();
        System.out.print(dp[N]);
    }

    private static void solution() {
        for (int i = 2; i <= N; i++) {
            // 현재 그림을 선택하지 않은 경우 이전 최대 가치
            dp[i] = dp[i - 1];

            // 현재 그림을 선택한 경우 허용 높이 내의 최대 가치 + 현재 값
            int maxIdx = binarySearch(i);       // 허용하는 높이 값: arr[?][0] - S -> ?를 찾기 위해 이분탐색 수행
            if (maxIdx == i && dp[i] < arr[i][1]) {     // 찾은 인덱스가 현재 인덱스면 넣을 공간이 없는 경우이므로
                dp[i] = arr[i][1];      // 입력값의 가치 그대로 삽입
            } else {        // 인덱스를 찾았으면
                dp[i] = Math.max(dp[i], dp[maxIdx] + arr[i][1]);        // 이전 dp값과 비교하여 갱신
            }
        }
    }

    private static int binarySearch(int idx) {      // 허용하는 높이 중 가장 큰 인덱스를 구하는 메서드
        int maxHeight = arr[idx][0] - S;        // 최대 허용 높이: 현재 인덱스의 높이 - S
        int low = 1;       // 최저 높이 인덱스
        int high = idx;     // 최고 높이 인덱스
        int mid;        // 검사할 인덱스

        while (low < high) {        // 인덱스가 같은 경우도 검
            mid = (low + high) / 2;
            if (arr[mid][0] > maxHeight) {        // 검사하는 인덱스의 그림 높이가 허용 높이를 초과한 경우
                high = mid;     // high를 줄여 높이를 낮춤 (high를 가능한 한 높여야하므로 mid로 갱신)
            } else {       // 검사중인 높이가 허용 높이보다 작거나 같은 경우
                low = mid + 1;     // low를 늘려 높이를 높임
            }
        }

        return high - 1;        // high-1을 리턴하여 허용하는 높이 중 마지막 인덱스 리턴
    }

    private static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        N = parseInt(st.nextToken());
        S = parseInt(st.nextToken());
        arr = new int[N + 1][2];        // 0: 높이, 1: 가격
        for (int i = 1; i < N + 1; i++) {
            st = new StringTokenizer(br.readLine());
            arr[i][0] = parseInt(st.nextToken());
            arr[i][1] = parseInt(st.nextToken());
        }
        Arrays.sort(arr, Comparator.comparingInt(o -> o[0]));       // 높이 순으로 정렬

        dp = new int[N + 1];     // i번째 그림까지 고려했을때 최대 가격의 합
        dp[1] = arr[1][1];      // 첫 그림은 자기 자신
    }
}
