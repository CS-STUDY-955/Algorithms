import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 좋다
 * https://www.acmicpc.net/problem/1253
 *
 * 1. 현재 값이 집합에 존재하면 좋은 수, 존재하지 않으면 안좋은 수이다.
 * 2. 1을 판단한 이후 배열의 원소를 순서대로 접근하면서 이전의 수와 더한 값을 해시맵에 저장한다.
 * 3. 자기 자신은 합으로 사용할 수 없으므로 해시맵의 값으로 예외 인덱스를 저장한다.
 * 4. 모든 원소에 대해 반복하고, 좋은 수의 개수를 출력한다.
 * ---------------------------------------------
 * 1. 메모리 초과가 발생한다. (Map의 값으로 Set을 줬기 때문인듯)
 * 2. 따라서 Map을 배열에 존재하는 숫자와 등장횟수로 사용해야 한다.
 * 3. 0이 주어져 자기 자신과의 연산을 처리하는 경우를 예외처리 해주어야 한다.
 * - 사실상 투포인터 문제지만, 관점을 잘 바꾸면 해시로 풀 수 있는 문제였다.
 *
 * @author 배용현
 *
 */
public class BJ_1253_좋다 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        int N = parseInt(br.readLine());
        int answer = 0;
        HashMap<Integer, Integer> map = new HashMap<>();        // 특정 수가 arr에 몇 개 존재하는지 저장
        int[] arr = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = parseInt(st.nextToken());
            map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
        }

        for (int i = 0; i < N - 1; i++) {       // 서로 다른 두 인덱스 i, j 선택
            for (int j = i + 1; j < N; j++) {
                int sum = arr[i] + arr[j];      // 두 원소의 합

                if (map.containsKey(sum)) {     // 두 원소의 합으로 이루어진 수가 존재하면
                    int cnt = map.get(sum);     // 등장 횟수에 따라 분기

                    if (arr[i] == 0 && arr[j] == 0) {       // 현재 탐색중인 두 원소가 둘 다 0일때는
                        if (cnt >= 3) {     // 0이 3개 이상 주어졌을시 모두 만들 수 있으므로
                            answer += cnt;      // 정답에 cnt만큼 저장하고
                            map.remove(sum);        // 중복을 방지하기 위해 remove
                        }
                    } else if (arr[i] == 0 || arr[j] == 0) {      // 탐색중인 원소 둘 중 1개만 0일때는
                        if (cnt >= 2) {     // 0이 아닌 나머지 원소가 2번 이상 주어졌을시 만들 수 있음
                            answer += cnt;
                            map.remove(sum);
                        }
                    } else {        // 둘 다 0이 아니면 무조건 만들 수 있음
                        answer += cnt;
                        map.remove(sum);
                    }
                }
            }
        }

        System.out.print(answer);
    }

}
