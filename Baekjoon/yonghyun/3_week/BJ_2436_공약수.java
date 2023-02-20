import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 공약수
 * https://www.acmicpc.net/problem/2436
 *
 * 1. 두 수의 합이 가장 작으려면 두 수의 차가 가장 작아야함.
 * 2. 최대공약수를 소인수를 모두 가지고 있어야하고, 최소공배수의 소인수 이내로 가지고 있어야함.
 * 3. 최소공배수의 소인수에서 최대공약수의 소인수를 빼고 이들을 곱해서 만들 수 있는 수를 모두 구하고 정렬한다.
 * 4. 중앙에 있는 값 두 개를 최대공약수를 곱해서 출력한다.
 * 5. 최소공배수가 최대 1억이므로 소인수는 최대 30개까지 나올 수 있는데, 이를 조합으로 뽑는 것이 곱해서 만들 수 있는 경우이다.
 * 6. 조합은 O(2^n)이라 1억이 되므로 실패할 것 같다.
 * -------------------------
 * 1. x와 y의 최대공약수 * x와 y의 최소공배수 = x * y
 * 2. x와 y의 후보군을 찾아 정답이 될 수 있나 비교한다.
 * 3. x와 y는 최대공약수의 배수이다.
 * 4. x * y의 중앙값과 가장 가까운 최대공약수의 배수를 구한 뒤 x로 두고 두 수의 곱과 나누어 떨어지는지, GCD는 맞는지 확인한다.
 * 5. 두 조건중 하나라도 아니라면 최대공약수만큼 x값을 줄이면서 다시 확인한다.
 * 6. 제곱근부터 최대공약수까지 반복하므로 O(logN), 유클리드 호제법으로 O(logN)을 사용하므로 총 O((logN)^2)으로 통과할 수 있다.
 * 7. 각 입력의 최댓값이 1억이므로 곱했을때 int형의 범위를 넘어가는 것에 주의한다.
 */
public class BJ_2436_공약수 {
    public static void main(String[] args) throws Exception {
        // 입력 처리 & 지역 변수 선언
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int gcd = Integer.parseInt(st.nextToken());     // 최대공약수
        int lcm = Integer.parseInt(st.nextToken());     // 최소공배수

        // 로직 구현
        long mul = (long)gcd * lcm;     // 둘의 곱
        double sqrt = Math.sqrt(mul);   // mul의 중앙값
        int nearestCandidate = (int)(sqrt / gcd) * gcd;     // 중앙값과 가장 가까운 gcd의 배수
        int answer = 0;
        for(int i=nearestCandidate; i>=gcd; i-=gcd) {       // 중앙값에서부터 작아지면서 정답을 찾음
            if(mul%i==0 && getGCD((int)(mul/i), i)==gcd) {      // mul이 나누어 떨어지고, mul/i과 i의 최대공약수가 문제에서 주어진 gcd라면 정답
                answer = i;     // 정답 저장하고
                break;      // 중앙값부터 탐색했으므로 더 반복할 필요가 없음
            }
        }

        // 출력 처리
        System.out.println(answer + " " + mul/answer);
    }

    private static int getGCD(int big, int small) {
        return big%small==0 ? small : getGCD(small, big%small);     // 유클리드 호제법으로 GCD 리턴
    }
}
