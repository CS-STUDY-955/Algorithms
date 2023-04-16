import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_20057_마법사_상어와_토네이도 {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.parseInt(br.readLine());
    int[][] map = new int[n + 4][n + 4];
    for (int i = 2; i < n + 2; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int j = 2; j < n + 2; j++) {
        map[i][j] = Integer.parseInt(st.nextToken());
      }
    }

    int x = 2 + n / 2;
    int y = 2 + n / 2;
    // 방향
    int dx = 0;
    int dy = -1;
    // 흩날린 방향
    // 1% 1% 2% 2% 5% 7% 7% 10% 10% a%
    int[] scatterX = { -1, 1, -2, 2, 0, -1, 1, -1, 1, 0 };
    int[] scatterY = { 1, 1, 0, 0, -2, 0, 0, -1, -1, -1 };
    int[] rate = { 1, 1, 2, 2, 5, 7, 7, 10, 10 };
    // 방향 전환 후 몇번을 갔는지
    int count = 0;
    // 이동해야하는 거리
    int distance = 1;
    // 방향 전환 횟수
    int turned = 0;

    while (true) {
      x += dx;
      y += dy;
      if (x < 2 || n + 2 <= x || y < 2 || n + 2 <= y) {
        break;
      }
      // scatter에 따라 모래 날림
      int alpha = map[x][y];
      int moved = 0;
      for (int i = 0; i < 9; i++) {
        map[x + scatterX[i]][y + scatterY[i]] += alpha * rate[i] / 100;
        moved += alpha * rate[i] / 100;
      }
      
      // 남은 양만큼은 a로 이동
      map[x + scatterX[9]][y + scatterY[9]] += alpha - moved;
      map[x][y] = 0;

      count++;
      if (count == distance) {
        turned++;
        count = 0;

        // dx, dy, scatterX, scatterY 전부 90도 회전
        int temp = -dy;
        dy = dx;
        dx = temp;
        for (int i = 0; i < 10; i++) {
          temp = -scatterY[i];
          scatterY[i] = scatterX[i];
          scatterX[i] = temp;
        }

        if (turned % 2 == 0) {
          distance++;
        }
      }
    }

    int sum = 0;
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < n + 4; j++) {
        sum += map[i][j];
        sum += map[i + n + 2][j];
      }
    }
    for (int i = 2; i < n + 2; i++) {
      for (int j = 0; j < 2; j++) {
        sum += map[i][j];
        sum += map[i][j + n + 2];
      }
    }
    System.out.println(sum);
  }
}
