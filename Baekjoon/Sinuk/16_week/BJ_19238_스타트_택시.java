import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// import java.util.ArrayDeque;
// import java.util.Arrays;
import java.util.PriorityQueue;
// import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_19238_스타트_택시 {
  private static int[] dx = { -1, 0, 0, 1 };
  private static int[] dy = { 0, -1, 1, 0 };

  private static class Point implements Comparable<Point> {
    int x, y, depth;

    public Point(int x, int y, int depth) {
      this.x = x;
      this.y = y;
      this.depth = depth;
    }

    @Override
    public int compareTo(Point o) {
      if (this.depth == o.depth) {
        if (this.x == o.x) {
          return this.y - o.y;
        }
        return this.x - o.x;
      }
      return this.depth - o.depth;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(st.nextToken());
    int m = Integer.parseInt(st.nextToken());
    int fuel = Integer.parseInt(st.nextToken());

    int[][] map = new int[n + 2][n + 2];
    for (int i = 1; i <= n; i++) {
      st = new StringTokenizer(br.readLine());
      for (int j = 1; j <= n; j++) {
        map[i][j] = -Integer.parseInt(st.nextToken());
      }
    }
    st = new StringTokenizer(br.readLine());
    int x = Integer.parseInt(st.nextToken());
    int y = Integer.parseInt(st.nextToken());

    // 패딩 설정
    for (int i = 0; i < n + 2; i++) {
      map[0][i] = -1;
      map[n + 1][i] = -1;
      map[i][0] = -1;
      map[i][n + 1] = -1;
    }

    int[][] passengers = new int[m + 1][4];
    for (int i = 1; i <= m; i++) {
      st = new StringTokenizer(br.readLine());
      for (int j = 0; j < 4; j++) {
        passengers[i][j] = Integer.parseInt(st.nextToken());
      }
      map[passengers[i][0]][passengers[i][1]] = i;
    }

    int count = 0;
    boolean[][] arrived = new boolean[n + 2][n + 2];
    while (true) {
      // 가장 가까운 승객을 찾는다
      boolean[][] visited = new boolean[n + 2][n + 2];
      PriorityQueue<Point> queue = new PriorityQueue<>();
      queue.add(new Point(x, y, 0));
      PriorityQueue<Point> candidate = new PriorityQueue<>();
      int depth = 0;
      visited[x][y] = true;

      while (!queue.isEmpty()) {
        Point cur = queue.poll();
        if (map[cur.x][cur.y] > 0 && !arrived[cur.x][cur.y]) {
          candidate.add(cur);
          // break;
        }
        if (depth == cur.depth) {
          // if (candidate.size() != 0) {
          // break;
          // }
          depth++;
          // if (fuel <= depth) {
          //   break;
          // }
        }

        // 현재 위치에 대해 4방탐색
        for (int d = 0; d < 4; d++) {
          int nx = cur.x + dx[d];
          int ny = cur.y + dy[d];
          // 벽이라면 패스
          if (map[nx][ny] < 0) {
            continue;
          }
          // 이미 방문 했다면 패스
          if (!visited[nx][ny]) {
            visited[nx][ny] = true;
            queue.add(new Point(nx, ny, depth));
          }
        }
      }
      if (!candidate.isEmpty()) {
        Point picked = candidate.peek();
        x = picked.x;
        y = picked.y;
        depth = picked.depth;
      }

      // 산출된 거리에 따라 기름 소모
      fuel -= depth;

      // 만약 기름이 바닥났거나 승객을 찾지 못했다면 종료
      if (fuel <= 0 || candidate.isEmpty()) {
        fuel = -1;
        break;
      }

      // 해당 승객의 목적지의 최단 경로로 이동한다
      visited = new boolean[n + 2][n + 2];
      queue = new PriorityQueue<>();
      queue.add(new Point(x, y, 0));
      depth = 0;
      visited[x][y] = true;
      int targetX = passengers[map[x][y]][2];
      int targetY = passengers[map[x][y]][3];
      int startX = x;
      int startY = y;
      boolean found = false;

      while (!queue.isEmpty()) {
        Point cur = queue.poll();
        if (cur.x == targetX && cur.y == targetY) {
          x = cur.x;
          y = cur.y;
          depth = cur.depth;
          found = true;
          break;
        }
        if (depth == cur.depth) {
          depth++;
          // if (fuel < depth) {
          //   break;
          // }
        }

        // 현재 위치에 대해 4방탐색
        for (int d = 0; d < 4; d++) {
          int nx = cur.x + dx[d];
          int ny = cur.y + dy[d];
          // 벽이라면 패스
          if (map[nx][ny] < 0) {
            continue;
          }
          // 이미 방문 했다면 패스
          if (!visited[nx][ny]) {
            visited[nx][ny] = true;
            queue.add(new Point(nx, ny, depth));
          }
        }
      }

      // 산출된 거리에 따라 기름 소모
      fuel -= depth;

      // 만약 기름이 바닥났거나 목적지를 찾지 못했다면 종료
      if (fuel < 0 || !found) {
        fuel = -1;
        break;
      }
      // 기름 충전, 대려다준 손님 수 증가, 손님 삭제
      fuel += depth * 2;
      count++;
      arrived[startX][startY] = true;

      // 모든 손님을 대려다 줬다면 종료
      if (count == m) {
        break;
      }
    }
    System.out.println(fuel);
  }
}