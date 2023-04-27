import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_19236_청소년_상어 {
  private static int max = 0;
  private static int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
  private static int[] dy = { 0, -1, -1, -1, 0, 1, 1, 1 };
  private static int[][] map;
  private static int[][] position;
  private static int[] dir;
  private static boolean[] dead;

  // 1. 물고기들을 이동시킨다
  // 2. 이동이 끝난 상태의 맵정보, 위치정보, 방향정보를 저장한다
  // 3. 상어를 이동시킨다
  // 4. 이동시엔 새로운 정보를 가지고 재귀를 시작한다.
  // 5. 물고기를 처음 위치로 이동시킨다.
  private static void move(int count, int sx, int sy, int sd, int depth) {
    // 물고기 이동시키기
    for (int i = 0; i < 16; i++) {
      // 만약 이미 죽은 물고기면 패스
      if (dead[i]) {
        continue;
      }
      // 이동할 수 있는 방향을 찾는다
      for (int d = 0; d < 8; d++) {
        int nx = position[i][0] + dx[dir[i]];
        int ny = position[i][1] + dy[dir[i]];
        // 이동할 수 있는 곳이라면(바깥이 아니고, 상어가 있는 곳이 아니라면) 교환하고 종료
        if (map[nx][ny] != -1 && !(nx == sx && ny == sy)) {
          swap(i, map[nx][ny]);
          break;
        }
        dir[i] = (dir[i] + 1) % 8;
      }
    }

    // 임시로 저장하기
    int[][] tempMap = new int[6][6];
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        tempMap[i][j] = map[i][j];
      }
    }
    int[][] tempPosi = new int[16][2];
    for (int i = 0; i < 16; i++) {
      tempPosi[i][0] = position[i][0];
      tempPosi[i][1] = position[i][1];
    }
    int[] tempDir = new int[16];
    for (int i = 0; i < 16; i++) {
      tempDir[i] = dir[i];
    }
    // 모든 물고기가 이동했다면 상어를 이동
    boolean canMove = false;
    while (true) {
      sx += dx[sd];
      sy += dy[sd];
      if (map[sx][sy] == -1) {
        break;
      }
      if (dead[map[sx][sy]]) {
        continue;
      }
      canMove = true;
      dead[map[sx][sy]] = true;
      move(count + map[sx][sy] + 1, sx, sy, dir[map[sx][sy]], depth + 1);
      dead[map[sx][sy]] = false;

      // 물고기 위치 원복
      for (int i = 0; i < 6; i++) {
        for (int j = 0; j < 6; j++) {
          map[i][j] = tempMap[i][j];
        }
      }
      for (int i = 0; i < 16; i++) {
        position[i][0] = tempPosi[i][0];
        position[i][1] = tempPosi[i][1];
      }
      for (int i = 0; i < 16; i++) {
        dir[i] = tempDir[i];
      }
    }
    if (!canMove) {
      max = Math.max(max, count);
    }
  }

  // 바꿀 두 물고기의 인덱스를 받아
  // 포지션, map상의 포지션을 교환
  private static void swap(int a, int b) {
    // x좌표 교환
    int temp = position[a][0];
    position[a][0] = position[b][0];
    position[b][0] = temp;
    // y 좌표 교환
    temp = position[a][1];
    position[a][1] = position[b][1];
    position[b][1] = temp;
    // map상의 포지션 교환
    map[position[a][0]][position[a][1]] = a;
    map[position[b][0]][position[b][1]] = b;
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    map = new int[6][6];
    position = new int[16][2];
    dir = new int[16];
    dead = new boolean[16];
    for (int i = 1; i < 5; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int j = 1; j < 5; j++) {
        map[i][j] = Integer.parseInt(st.nextToken()) - 1;
        dir[map[i][j]] = Integer.parseInt(st.nextToken()) - 1;
        position[map[i][j]][0] = i;
        position[map[i][j]][1] = j;
      }
    }

    for (int i = 0; i < 6; i++) {
      map[0][i] = -1;
      map[i][0] = -1;
      map[5][i] = -1;
      map[i][5] = -1;
    }

    dead[map[1][1]] = true;
    move(map[1][1] + 1, 1, 1, dir[map[1][1]], 0);
    System.out.println(max);
  }
}
