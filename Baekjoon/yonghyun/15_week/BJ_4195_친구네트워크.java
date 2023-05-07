import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

/**
 * 친구 네크워크
 * https://www.acmicpc.net/problem/4195
 *
 * 1. 친구 관계를 확인하는 방법으로 유니온 파인드 알고리즘을 사용하면 될 것 같다.
 * 2. 그런데 저장해야하는 값이 문자열이고, 친구의 수가 주어지지 않으므로 기본적인 int형 배열 대신 Map<String, String>을 사용해야 한다.
 * 3. 가장 대표로 저장되는 친구가 그 친구관계의 수를 저장하고, 이를 별도의 맵으로 저장한다.
 * -------------------------------------------------------
 * 1. find() 메서드에서 map.put()의 리턴값을 그대로 리턴하도록 한 줄로 기술하니까 메모리 초과로 통과할 수 없었다.
 * 2. 알아보니 map의 put() 메서드는 넣은 값을 리턴하는 것이 아닌 수정되기 전의 값을 리턴한다.
 * 3. 이로 인해 의도한대로 동작하지 않았던 것으로 보인다.
 *
 * @author 배용현
 *
 */
public class BJ_4195_친구네트워크 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static Map<String, String> parent;
    static Map<String, Integer> numOfFriends;


    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        int T = parseInt(br.readLine());
        for (int tc = 0; tc < T; tc++) {
            int F = parseInt(br.readLine());
            parent = new HashMap<>();
            numOfFriends = new HashMap<>();

            for (int i = 0; i < F; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                String first = st.nextToken();
                String second = st.nextToken();

                if (!parent.containsKey(first)) {
                    parent.put(first, first);
                    numOfFriends.put(first, 1);
                }

                if (!parent.containsKey(second)) {
                    parent.put(second, second);
                    numOfFriends.put(second, 1);
                }

                sb.append(numOfFriends.get(union(first, second))).append('\n');
            }
        }

        System.out.print(sb);
    }

    private static String union(String first, String second) {
        String firstGP = find(first);
        String secondGP = find(second);

        if (!firstGP.equals(secondGP)) {
            parent.put(secondGP, firstGP);

            numOfFriends.put(firstGP, numOfFriends.get(firstGP) + numOfFriends.get(secondGP));
        }

        return firstGP;
    }

    private static String find(String cur) {
        if (parent.get(cur).equals(cur)) {
            return cur;
        }

        String curGP = find(parent.get(cur));
        parent.put(cur, curGP);
        return curGP;
    }

}
