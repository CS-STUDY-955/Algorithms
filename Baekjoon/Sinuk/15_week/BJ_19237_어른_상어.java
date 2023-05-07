import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_19237_어른_상어 {
  // 위, 아래, 왼쪽, 오른쪽
  private static int[] dx = { -1, 1, 0, 0 };
  private static int[] dy = { 0, 0, -1, 1 };

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(st.nextToken());
    int m = Integer.parseInt(st.nextToken());
    int k = Integer.parseInt(st.nextToken());
    int[][] map = new int[n + 2][n + 2];
    // 격자의 상황을 입력받는다
    for (int i = 1; i <= n; i++) {
      st = new StringTokenizer(br.readLine());
      for (int j = 1; j <= n; j++) {
        map[i][j] = Integer.parseInt(st.nextToken());
      }
    }

    // 상어의 초기 방향을 받는다
    st = new StringTokenizer(br.readLine());
    int[] dirs = new int[m + 1];
    for (int i = 1; i <= m; i++) {
      dirs[i] = Integer.parseInt(st.nextToken()) - 1;
    }

    // 각 상어의 방향별 우선순위를 받는다
    int[][][] priority = new int[m+1][4][4];
    for (int i = 1; i <= m; i++) {
      for (int j = 0; j < 4; j++) {
        st = new StringTokenizer(br.readLine());
        for (int l = 0; l < 4; l++) {
          priority[i][j][l] = Integer.parseInt(st.nextToken()) - 1;
        }
      }
    }

    int turn = 0;
    int cnt = 0;
    boolean over = false;
    int[][] owner = new int[n + 2][n + 2];
    for (int i = 0; i < n + 2; i++) {
      map[0][i] = -1;
      map[n + 1][i] = -1;
      map[i][0] = -1;
      map[i][n + 1] = -1;
    }
    int[][] smell = new int[n + 2][n + 2];
    // 초기 냄새 뿌리기
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        if (map[i][j] != 0) {
          smell[i][j] = k;
          owner[i][j] = map[i][j];
        }
      }
    }

    int[][] tempMap;
    while (turn < 1000) {
      if (cnt == m - 1) {
        over = true;
        break;
      }
      turn++;
      tempMap = new int[n + 2][n + 2];
      // 상어가 이동한다.
      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
          if (map[i][j] != 0) {
            int x = i;
            int y = j;
            boolean moved = false;
            int idx = map[i][j];
            // 냄새가 없는 곳으로 이동할 수 있는지 체크
            for (int d = 0; d < 4; d++) {
              int nx = i + dx[priority[idx][dirs[idx]][d]];
              int ny = j + dy[priority[idx][dirs[idx]][d]];
              if (map[nx][ny] != -1 && smell[nx][ny] == 0) {
                x = nx;
                y = ny;
                dirs[idx] = priority[idx][dirs[idx]][d];
                moved = true;
                break;
              }
            }
            // 냄새가 없는 곳이 없다면, 자신의 냄새인 곳으로 이동
            if (!moved) {
              for (int d = 0; d < 4; d++) {
                int nx = i + dx[priority[idx][dirs[idx]][d]];
                int ny = j + dy[priority[idx][dirs[idx]][d]];
                if (map[nx][ny] != -1 && owner[nx][ny] == idx) {
                  x = nx;
                  y = ny;
                  dirs[idx] = priority[idx][dirs[idx]][d];
                  break;
                }
              }
            }

            // 찾은 위치가 비어있었다면 그냥 이동
            if (tempMap[x][y] == 0) {
              tempMap[x][y] = idx;
            }
            // 나보다 큰 상어가 있었다면 쫓겨남
            else if (tempMap[x][y] < idx) {
              cnt++;
            }
            // 작은 상어라면 쫒아냄
            else {
              tempMap[x][y] = idx;
              cnt++;
            }
          }
        }
      }
      // 원래 map으로 정보 이동
      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
          map[i][j] = tempMap[i][j];
        }
      }

      // 기존 냄새가 1 옅어진다.
      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
          if (smell[i][j] != 0) {
            smell[i][j]--;
            if (smell[i][j] == 0)
              owner[i][j] = 0;
          }
        }
      }

      // 상어의 새로운 위치에 냄새를 뿌린다.
      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
          if (map[i][j] != 0) {
            smell[i][j] = k;
            owner[i][j] = map[i][j];
          }
        }
      }
    }

    if (over) {
      System.out.println(turn);
    } else {
      System.out.println(-1);
    }
  }
}
