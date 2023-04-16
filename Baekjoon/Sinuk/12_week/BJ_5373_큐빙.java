import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_5373_큐빙 {
  private static char[][][] cube;

  // 돌아간 면을 회전 시키는 메서드
  private static void rotate(int plane, int dir) {
    int[][] idx = { { 6, 3, 0, 7, 4, 1, 8, 5, 2 }, // 시계방향 인덱스
        { 2, 5, 8, 1, 4, 7, 0, 3, 6 } }; // 반시계방향 인덱스
    // 임시 배열을 만들어 옮긴 위치에 저장하고
    char temp[][] = new char[3][3];
    for (int i = 0; i < 9; i++) {
      temp[i / 3][i % 3] = cube[plane][idx[dir][i] / 3][idx[dir][i] % 3];
    }
    // 그 값을 다시 원래의 배열에 넣는다
    for (int i = 0; i < 9; i++) {
      cube[plane][i / 3][i % 3] = temp[i / 3][i % 3];
    }
  }

  // 회전시키는 과정은 이하의 과정을 거친다
  // 1. 12칸짜리 임시배열을 만든다
  // 2. 움직여야 하는 요소들을 배열에 순서대로 넣는다
  // 3. 시계방향 또는 반시계방향인것에 따라 3 또는 9의 값을 offset으로 하여 저장한다
  private static void rotateU(int dir) {
    char[] temp = new char[12];
    // 앞면 0/012-> 왼쪽0/012 -> 뒷면0/012 -> 오른쪽0/012
    int[] idx0 = { 2, 2, 2, 4, 4, 4, 3, 3, 3, 5, 5, 5 };
    int[] idx1 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    int[] idx2 = { 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2 };
    for (int i = 0; i < 12; i++) {
      temp[i] = cube[idx0[i]][idx1[i]][idx2[i]];
    }
    int offset = dir == 0 ? 9 : 3;
    for (int i = 0; i < 12; i++) {
      cube[idx0[i]][idx1[i]][idx2[i]] = temp[(offset + i) % 12];
    }
  }

  private static void rotateD(int dir) {
    char[] temp = new char[12];
    // 앞면 2/012-> 오른쪽2/012 -> 뒷면2/012 -> 왼쪽2/012
    int[] idx0 = { 2, 2, 2, 5, 5, 5, 3, 3, 3, 4, 4, 4, };
    int[] idx1 = { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 };
    int[] idx2 = { 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2 };
    for (int i = 0; i < 12; i++) {
      temp[i] = cube[idx0[i]][idx1[i]][idx2[i]];
    }
    int offset = dir == 0 ? 9 : 3;
    for (int i = 0; i < 12; i++) {
      cube[idx0[i]][idx1[i]][idx2[i]] = temp[(offset + i) % 12];
    }
  }

  private static void rotateF(int dir) {
    char[] temp = new char[12];
    // 윗면 2/012-> 오른쪽012/0 -> 아랫면0/210 -> 왼쪽210/2
    int[] idx0 = { 0, 0, 0, 5, 5, 5, 1, 1, 1, 4, 4, 4 };
    int[] idx1 = { 2, 2, 2, 0, 1, 2, 0, 0, 0, 2, 1, 0 };
    int[] idx2 = { 0, 1, 2, 0, 0, 0, 2, 1, 0, 2, 2, 2 };
    for (int i = 0; i < 12; i++) {
      temp[i] = cube[idx0[i]][idx1[i]][idx2[i]];
    }
    int offset = dir == 0 ? 9 : 3;
    for (int i = 0; i < 12; i++) {
      cube[idx0[i]][idx1[i]][idx2[i]] = temp[(offset + i) % 12];
    }
  }

  private static void rotateB(int dir) {
    char[] temp = new char[12];
    // 윗면 0/210-> 왼쪽012/0 -> 아랫면2/012 -> 오른쪽210/2
    int[] idx0 = { 0, 0, 0, 4, 4, 4, 1, 1, 1, 5, 5, 5 };
    int[] idx1 = { 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 1, 0 };
    int[] idx2 = { 2, 1, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2 };
    for (int i = 0; i < 12; i++) {
      temp[i] = cube[idx0[i]][idx1[i]][idx2[i]];
    }
    int offset = dir == 0 ? 9 : 3;
    for (int i = 0; i < 12; i++) {
      cube[idx0[i]][idx1[i]][idx2[i]] = temp[(offset + i) % 12];
    }
  }

  private static void rotateL(int dir) {
    char[] temp = new char[12];
    // 윗면 012/0-> 앞면012/0 -> 아랫면012/0 -> 뒷면210/2
    int[] idx0 = { 0, 0, 0, 2, 2, 2, 1, 1, 1, 3, 3, 3 };
    int[] idx1 = { 0, 1, 2, 0, 1, 2, 0, 1, 2, 2, 1, 0 };
    int[] idx2 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2 };
    for (int i = 0; i < 12; i++) {
      temp[i] = cube[idx0[i]][idx1[i]][idx2[i]];
    }
    int offset = dir == 0 ? 9 : 3;
    for (int i = 0; i < 12; i++) {
      cube[idx0[i]][idx1[i]][idx2[i]] = temp[(offset + i) % 12];
    }
  }

  private static void rotateR(int dir) {
    char[] temp = new char[12];
    // 윗면 210/2-> 뒷면012/0 -> 아랫면210/2 -> 앞면210/2
    int[] idx0 = { 0, 0, 0, 3, 3, 3, 1, 1, 1, 2, 2, 2 };
    int[] idx1 = { 2, 1, 0, 0, 1, 2, 2, 1, 0, 2, 1, 0 };
    int[] idx2 = { 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 2 };
    for (int i = 0; i < 12; i++) {
      temp[i] = cube[idx0[i]][idx1[i]][idx2[i]];
    }
    int offset = dir == 0 ? 9 : 3;
    for (int i = 0; i < 12; i++) {
      cube[idx0[i]][idx1[i]][idx2[i]] = temp[(offset + i) % 12];
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    int t = Integer.parseInt(br.readLine());
    // 큐브 구조
    // ..3
    // 4 0 5
    // ..2
    // ..1
    // 3, 4, 5, 2는 0을 바라보는 쪽이 0번 인덱스의 열
    // 1번은 0->2->1로 이어지는 방향으로 바라보도록 0번 인덱스가 존재
    // 윗면: 0, 아랫면: 1, 앞면: 2, 뒷면: 3, 왼쪽면: 4, 오른쪽면: 5
    // 윗면: w, 아랫면: y, 앞면: r, 뒷면: o, 왼쪽면: g, 오른쪽면: b
    char[] color = { 'w', 'y', 'r', 'o', 'g', 'b' };
    while (t-- > 0) {
      // 큐브 초기화
      cube = new char[6][3][3];
      for (int i = 0; i < 6; i++) {
        for (int j = 0; j < 3; j++) {
          for (int k = 0; k < 3; k++) {
            cube[i][j][k] = color[i];
          }
        }
      }

      int n = Integer.parseInt(br.readLine());
      StringTokenizer st = new StringTokenizer(br.readLine());
      while (n-- > 0) {
        String str = st.nextToken();
        // +면 0으로 시계방향, -면 1로 반시계방향
        int dir = str.charAt(1) == '+' ? 0 : 1;
        // UDFBLR에 따라 해당하는 메서드를 호출
        switch (str.charAt(0)) {
          case 'U':
            rotateU(dir);
            rotate(0, dir);
            break;
          case 'D':
            rotateD(dir);
            rotate(1, dir);
            break;
          case 'F':
            rotateF(dir);
            rotate(2, dir);
            break;
          case 'B':
            rotateB(dir);
            rotate(3, dir);
            break;
          case 'L':
            rotateL(dir);
            rotate(4, dir);
            break;
          case 'R':
            rotateR(dir);
            rotate(5, dir);
            break;
        }
      }
      // 큐브 회전 끝난 뒤 출력
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          sb.append(cube[0][i][j]);
        }
        sb.append("\n");
      }
    }
    System.out.println(sb.toString());
  }
}
