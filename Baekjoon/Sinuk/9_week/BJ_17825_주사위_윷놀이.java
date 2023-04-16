import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_17825_주사위_윷놀이 {
  private static int MAX_STEP = 10;
  private static int max = 0;
  private static int[] rollResult;
  private static int[] whichMove;
  private static int[][] board;
  private static boolean[][] exist;

  private static void makePlan(int step) {
    if (step == MAX_STEP) {
      // 시뮬레이션 호출
      int result = simulation();
      if (max < result) {
        max = result;
      }
      return;
    }

    for (int i = 0; i < 4; i++) {
      whichMove[step] = i;
      makePlan(step + 1);
    }
  }

  private static int simulation() {
    int score = 0;
    int[] positionX = { 0, 0, 0, 0 };
    int[] positionY = { 0, 0, 0, 0 };
    exist = new boolean[4][22];

    for (int t = 0; t < MAX_STEP; t++) {
      int x = positionX[whichMove[t]];
      int y = positionY[whichMove[t]];
      exist[x][y] = false;
      y += rollResult[t];
      if (x == 0) {
        if (y > 20) {
          y = 21;
        } else if (y == 5) {
          x = 1;
          y = 0;
        } else if (y == 10) {
          x = 2;
          y = 0;
        } else if (y == 15) {
          x = 3;
          y = 0;
        }
      } else if (x == 1) {
        // 9시 방향
        if (y > 3) {
          x = 2;
          y = y - 1;
        }
      } else if (x == 2) {
        // 6시~12시 방향
        // 밑에서 한꺼번에 할거라 여기선 아무것도 안함
      } else if (x == 3) {
        // 3시 방향
        if (y > 3) {
          x = 2;
          y = y - 1;
        }
      }

      if (x == 2) {
        if (y == 6) {
          x = 0;
          y = 20;
        } else if (y > 6) {
          x = 0;
          y = 21;
        }
      }

      if (exist[x][y]) {
        return 0;
      }
      exist[x][y] = true;
      exist[0][21] = false;
      score += board[x][y];
      positionX[whichMove[t]] = x;
      positionY[whichMove[t]] = y;
    }

    return score;
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    rollResult = new int[MAX_STEP];
    for (int i = 0; i < MAX_STEP; i++) {
      rollResult[i] = Integer.parseInt(st.nextToken());
    }
    whichMove = new int[10];
    board = new int[4][22];
    // 보드의 외곽지역 초기화
    for (int i = 1; i < 21; i++) {
      board[0][i] = 2 * i;
    }
    // 보드의 안쪽의 9시방향
    board[1][0] = 10;
    board[1][1] = 13;
    board[1][2] = 16;
    board[1][3] = 19;
    // 보드 안쪽의 6시~12시 방향
    board[2][0] = 20;
    board[2][1] = 22;
    board[2][2] = 24;
    board[2][3] = 25;
    board[2][4] = 30;
    board[2][5] = 35;
    // 보드 안쪽의 3시 방향
    board[3][0] = 30;
    board[3][1] = 28;
    board[3][2] = 27;
    board[3][3] = 26;

    makePlan(0);
    System.out.println(max);
  }
}
