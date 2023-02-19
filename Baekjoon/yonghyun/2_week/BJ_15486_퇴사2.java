import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * 퇴사2
 * https://www.acmicpc.net/problem/15486
 *
 * - 약 1시간 소요됨
 * 
 * 1. 퇴사이후에 상담이 끝나서 못받는 상담을 제외하고 나머지를 금액/기간으로 정렬한다.
 * 2. 가성비가 좋은 상담부터 주어진 기간을 채우고 받을 수 있는 금액을 계산한다.
 * 3. 한 상담을 받았을 때 몇일간 다른 상담을 받을 수 없는 경우를 계산할 수 없다.
 * -------------------------------
 * 1. 해당 날짜까지 상담해서 받을 수 있는 최대 금액을 기록하는 dp배열을 만든다.
 * 2. 상담리스트를 순회하면서 상담을 수행하면 받을 수 있는 값을 dp배열에 기록한다.
 * 3. 2번에서 기록해놓은 값과 직전 상담의 값중 큰 값을 dp에 기록한다.
 * 4. 배열을 순회하면서 기록된 값을 참고하는 DP이므로 O(n)으로 통과할 수 있다.
 */
public class BJ_15486_퇴사2 {
    public static void main(String[] args) throws Exception {
        // 입력 처리 & 지역 변수 선언
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());     // 상담의 개수 N
        int[] time = new int[N+2];		// 상담 소요 기간 배열 (0과 마지막 인덱스는 편리성을 위해 추가)
        int[] price = new int[N+2];		// 상담 가격 배열 (0과 마지막 인덱스는 편리성을 위해 추가)
        int[] dp = new int[N+2];		// 해당 날짜까지 상담해서 받을 수 있는 최대 금액 (0과 마지막 인덱스는 편리성을 위해 추가)
        for(int i=1; i<N+1; i++) {		// 1부터 N까지 입력을 받음
            String[] input = br.readLine().split(" ");
        	time[i] = Integer.parseInt(input[0]);		// time 초기화
        	price[i] = Integer.parseInt(input[1]);		// price 초기화
        }
        
        // 로직 구현
        for(int i=1; i<N+2; i++) {		// N+1까지 순회해서 퇴사했을때까지 받을 수 있는 금액까지 계산
    		dp[i] = Math.max(dp[i-1], dp[i]);		// i일에 받을 수 있는 최대 금액 = 오늘 하루 논 금액과 상담이 막 끝난 경우의 금액 중 큰 값
        	int nextIdx = i + time[i];		// 현재 상담 정보로 갱신할 수 있는 날짜 계산
        	if(nextIdx<dp.length)		// 일하는 기간 안에 상담이 끝나는 경우
        		dp[nextIdx] = Math.max(dp[nextIdx], dp[i]+price[i]);		// 상담을 한 금액이 이전의 최대금액보다 크면 갱신
        }
        
        // 출력 처리
        System.out.println(dp[N+1]);		// 퇴사했을때 받을 수 있는 금액
    }
}