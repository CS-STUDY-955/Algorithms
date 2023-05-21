import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Stack;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

/**
 * PPAP
 * https://www.acmicpc.net/problem/16120
 *
 * 1. PPAP 문자열은 P를 PPAP로 변환한 개수만큼 A가 존재해야 한다.
 * 2. 그러므로 A앞에는 P가 2개, 뒤에는 P가 1개 반드시 위치해야 한다.
 * 3. 주어진 문자열을 순회하면서 P는 스택에 넣고, A가 나오면 처리를 시작한다.
 *   - A가 나왔을 때 스택에 P가 2개 미만이면 PPAP문자열이 아니다.
 *   - 또는 뒤의 문자가 A거나 마지막 문자일 경우, PPAP문자열이 아니다.
 * 4. PPAP문자열이라면 스택에서 P를 1개 pop하고, 탐색 인덱스를 2증가시킨다.
 * 5. 모든 문자에 대해 위의 과정을 무사히 통과했다면 PPAP 문자열이다.
 *
 * - 굳이 stack이 필요할까? -> 정수형 변수로 저장된 P의 개수 관리
 *
 * @author 배용현
 *
 */
public class BJ_16120_PPAP {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        String input = br.readLine();
        System.out.println(isPPAP(input) ? "PPAP" : "NP");
    }

    private static boolean isPPAP(String input) {
        if(input.equals("P"))
            return true;

        int storedP = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == 'P') {
                storedP++;
                continue;
            }

            if(storedP<2)
                return false;

            if(i==input.length()-1)
                return false;

            if(input.charAt(i+1)=='A')
                return false;

            storedP--;
            i++;
        }

        return storedP==1;
    }
}