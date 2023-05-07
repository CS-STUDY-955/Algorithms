import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


public class BJ_1184_귀농 {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.parseInt(br.readLine());
    int[][] land = new int[n + 1][n + 1];
    for (int i = 1; i <= n; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int j = 1; j <= n; j++) {
        land[i][j] = Integer.parseInt(st.nextToken());
      }
    }

    // 2차원 누적합을 구한다.
    int[][] sum = new int[n + 1][n + 1];
    // 행방향으로 누적시키기
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        sum[i][j] = sum[i][j - 1] + land[i][j];
      }
    }
    // 열방향으로 누적시키기
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        sum[i][j] = sum[i - 1][j] + sum[i][j];
      }
    }

    int count = 0;
    // 2중 for문으로 꼭짓점 삼을 점을 고른다.
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        Map<Integer, Integer> map = new HashMap<>();
        // 내부의 첫 2중for문으로 그 꼭짓점을 기준으로 왼쪽 위에서 만들어지는 모든 경우를 map에 저장한다
        for (int k = i; k > 0; k--) {
          for (int l = j; l > 0; l--) {
            int temp = sum[i][j] - sum[k - 1][j] - sum[i][l - 1] + sum[k - 1][l - 1];
            if (map.containsKey(temp)){
              map.put(temp, map.get(temp) + 1);
            } else {
              map.put(temp, 1);
            }
          }
        }
        // 그 후 그 점을 기준으로 오른쪽 아래에서 만들어지는 모든 경우중 map에 값이 있는 경우 count+1한다
        // 시작점: i+1, j+1
        for (int k = i + 1; k <= n; k++) {
          for (int l = j + 1; l <= n; l++) {
            int temp = sum[k][l] - sum[i][l] - sum[k][j] + sum[i][j];
            if (map.containsKey(temp))
              count += map.get(temp);
          }
        }

        map = new HashMap<>();
        // 내부의 2번째 2중for문으로 그 꼭짓점을 기준으로 왼쪽 아래에서 만들어지는 모든 경우를 map에 저장한다
        for (int k = i; k <= n; k++) {
          for (int l = j; l > 0; l--) {
            int temp = sum[k][j] - sum[i - 1][j] - sum[k][l - 1] + sum[i - 1][l - 1];
            if (map.containsKey(temp)){
              map.put(temp, map.get(temp) + 1);
            } else {
              map.put(temp, 1);
            }
          }
        }
        // 그 후 그 점을 기준으로 오른쪽 위에서 만들어지는 모든 경우중 map에 값이 있는 경우 count+1한다
        // 시작점: i-1, j+1
        for (int k = i - 1; k > 0; k--) {
          for (int l = j + 1; l <= n; l++) {
            int temp = sum[i - 1][l] - sum[i - 1][j] - sum[k - 1][l] + sum[k - 1][j];
            if (map.containsKey(temp))
              count += map.get(temp);
          }
        }
      }
    }

    System.out.println(count);
  }
}
