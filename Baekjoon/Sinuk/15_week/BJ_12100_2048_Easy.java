import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_12100_2048_Easy {
  private static int max = 0;
  private static int n;
  private static int[][] board;
  private static int[][] tempBoard;

  private static void move(int step) {
    if (step == 5) {
      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
          if (max < board[i][j]) {
            max = board[i][j];
          }
        }
      }
      return;
    }
    // 0. 초기상태를 저장한다. 
    int[][] savedBoard = new int[n + 2][n + 2];
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        savedBoard[i][j] = board[i][j];
      }
    }

    // 1. 0~3의 방향에 대해 반복문을 돌린다
    for (int d = 0; d < 4; d++) {
      // 2. 해당하는 방향에 맞게 블록을 이동시킨다.
      tempBoard = new int[n + 2][n + 2];
      for(int i = 0; i<n+2; i++){
        tempBoard[0][i] = -1;
        tempBoard[n+1][i] = -1;
        tempBoard[i][0] = -1;
        tempBoard[i][n+1] = -1;
      }
      if (d == 0) {
        up();
      } else if (d == 1) {
        left();
      } else if (d == 2) {
        down();
      } else {
        right();
      }
      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
          board[i][j] = tempBoard[i][j];
        }
      }

      // 3. 재귀를 통해 다음 step으로 넘어간다.
      move(step + 1);

      // 4. 재귀에서 돌아오면 초기상태로 되돌린다.
      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
          board[i][j] = savedBoard[i][j];
        }
      }
    }
  }

  // 1. 옮길 위치를 찾는다
  // 2. 옮길 위치 다음이 자신과 같은 값이며, 합쳐진 곳이 아니라면 둘을 더한다.
  // 3. 아니라면 찾은 위치에 놓는다.
  private static void up() {
    boolean[][] visited = new boolean[n+2][n+2];
    for (int j = 1; j <= n; j++) {
      for (int i = 1; i <= n; i++) {
        if (board[i][j] == 0) {
          continue;
        }
        int k = i;
        while (tempBoard[k - 1][j] == 0) {
          k--;
        }
        if (tempBoard[k - 1][j] == board[i][j] && !visited[k-1][j]) {
          tempBoard[k - 1][j] += board[i][j];
          visited[k-1][j] = true;
        } else {
          tempBoard[k][j] = board[i][j];
        }
      }
    }
  }

  private static void down() {
    boolean[][] visited = new boolean[n+2][n+2];
    for (int j = 1; j <= n; j++) {
      for (int i = n; i >= 1; i--) {
        if (board[i][j] == 0) {
          continue;
        }
        int k = i;
        while (tempBoard[k + 1][j] == 0) {
          k++;
        }
        if (tempBoard[k + 1][j] == board[i][j] && !visited[k+1][j]) {
          tempBoard[k + 1][j] += board[i][j];
          visited[k+1][j] = true;
        } else {
          tempBoard[k][j] = board[i][j];
        }
      }
    }
  }

  private static void left() {
    boolean[][] visited = new boolean[n+2][n+2];
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= n; j++) {
        if (board[i][j] == 0) {
          continue;
        }
        int k = j;
        while (tempBoard[i][k-1] == 0) {
          k--;
        }
        if (tempBoard[i][k-1] == board[i][j] && !visited[i][k-1]) {
          tempBoard[i][k-1] += board[i][j];
          visited[i][k-1] = true;
        } else {
          tempBoard[i][k] = board[i][j];
        }
      }
    }
  }

  private static void right() {
    boolean[][] visited = new boolean[n+2][n+2];
    for (int i = 1; i <= n; i++) {
      for (int j = n; j >= 1; j--) {
        if (board[i][j] == 0) {
          continue;
        }
        int k = j;
        while (tempBoard[i][k+1] == 0) {
          k++;
        }
        if (tempBoard[i][k+1] == board[i][j] && !visited[i][k+1]) {
          tempBoard[i][k+1] += board[i][j];
          visited[i][k+1] = true;
        } else {
          tempBoard[i][k] = board[i][j];
        }
      }
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    n = Integer.parseInt(br.readLine());
    board = new int[n + 2][n + 2];
    for (int i = 1; i <= n; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int j = 1; j <= n; j++) {
        board[i][j] = Integer.parseInt(st.nextToken());
      }
    }

    move(0);
    System.out.println(max);
  }
}
