import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class BJ_4195_친구_네트워크 {
  private static int[] parent;
  private static int[] total;

  private static int findParent(int a) {
    if (parent[a] == a) {
      return a;
    } else {
      parent[a] = findParent(parent[a]);
      return parent[a];
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int t = Integer.parseInt(br.readLine());
    while (t-- > 0) {
      int f = Integer.parseInt(br.readLine());
      int idx = 0;
      Map<String, Integer> map = new HashMap<>();
      parent = new int[2 * f];
      total = new int[2 * f];
      for (int i = 0; i < f; i++) {
        StringTokenizer st = new StringTokenizer(br.readLine());
        String A = st.nextToken();
        // 이름을 받아 이미 존재했던 적이 있는 이름이라면, 기존 인덱스를 사용한다.
        // 아니라면 새 인덱스를 발급받고, map에 저장한다.
        int idxA;
        if (map.containsKey(A)) {
          idxA = map.get(A);
        } else {
          map.put(A, idx);
          parent[idx] = idx;
          idxA = idx;
          total[idx++] = 1;
        }
        int idxB;
        String B = st.nextToken();
        if (map.containsKey(B)) {
          idxB = map.get(B);
        } else {
          map.put(B, idx);
          parent[idx] = idx;
          idxB = idx;
          total[idx++] = 1;
        }

        // 1. 둘의 parent를 구한다.
        int pA = findParent(idxA);
        int pB = findParent(idxB);
        // 2. parent가 다르다면 합친다.
        if (pA != pB) {
          total[pA] += total[pB];
          parent[pB] = pA;
        }
        // 3. 앞쪽의 parent의 total값을 출력한다.
        System.out.println(total[pA]);
      }
    }
  }
}
