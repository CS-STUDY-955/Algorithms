import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 찾기
 * https://www.acmicpc.net/problem/1786
 *
 * 1. T와 P의 길이가 최대 100만이므로 단순비교시 시간 초과가 발생한다.
 * 2. 따라서 시간복잡도가 O(N+M)인 KMP 알고리즘을 사용해야 하고, 이 문제가 KMP 알고리즘의 대표적인 문제이다.
 * 3. KMP 알고리즘은 getPI 메서드만 잘 이해해도 KMP 알고리즘 전체를 이해할 수 있다.
 *
 * @author 배용현
 *
 */
public class BJ_1786_찾기 {

    static int cnt = 0;
    static ArrayList<Integer> li = new ArrayList<>();
    static BufferedReader br;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception{
        br = new BufferedReader(new InputStreamReader(System.in));
        String source = br.readLine();
        String pattern = br.readLine();

        KMP(source, pattern);

        sb.append(cnt).append('\n');
        for (int elem: li) {
            sb.append(elem).append('\n');
        }

        System.out.print(sb);
    }

    static int[] getPi(String ptn) {
        int[] pi = new int[ptn.length()];       // 접두사와 접미사가 같은 부분 문자열의 최대 길이
        int j = 0;      // j: 비교할 위치

        for (int i = 1; i < ptn.length(); i++) {        // i: 현재 위치
            while (j > 0 && ptn.charAt(i) != ptn.charAt(j)) {       // 현재 위치의 문자와 비교할 위치의 문자가 다른 경우
                j = pi[j - 1];      // j를 한칸 이전의 최대 부분 문자열
            }
            if (ptn.charAt(i) == ptn.charAt(j))     // 현재 위치의 문자와 비교할 위치의 문자가 같은 경우
                pi[i] = ++j;        // 부분 문자열의 길이 갱신
        }

        return pi;
    }

    static void KMP(String src, String ptn) {
        int[] pi = getPi(ptn);
        int j = 0;
        for (int i = 0; i < src.length(); i++) {
            while (j > 0 && src.charAt(i) != ptn.charAt(j)) {
                j = pi[j - 1];
            }
            if (src.charAt(i) == ptn.charAt(j)) {
                if (j == ptn.length() - 1) {        // 패턴 전체를 비교해서 다 똑같으면 매칭 성공
                    cnt++;      // 패턴 등장 횟수 + 1
                    li.add(i - j + 1);      // 패턴 등장 위치 저장
                    j = pi[j];
                } else
                    j++;
            }
        }
    }

}