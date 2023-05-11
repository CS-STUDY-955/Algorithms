import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_21609_상어_중학교 {
    private static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static class Group implements Comparable<Group> {
        int sx;
        int sy;
        int rainbow;
        int size;

        public Group(int sx, int sy, int rainbow, int size) {
            this.sx = sx;
            this.sy = sy;
            this.rainbow = rainbow;
            this.size = size;
        }

        @Override
        public int compareTo(Group o) {
            if (this.size == o.size) {
                if (this.rainbow == o.rainbow) {
                    if (this.sx == o.sx) {
                        return -Integer.compare(this.sy, o.sy);
                    }
                    return -Integer.compare(this.sx, o.sx);
                }
                return -Integer.compare(this.rainbow, o.rainbow);
            }
            return -Integer.compare(this.size, o.size);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n + 2][n + 2];
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                // 무지개색 블럭은 0 대신 m+1로 표시
                if (map[i][j] == 0) {
                    map[i][j] = m + 1;
                }
            }
        }

        // 패딩 설정
        for (int i = 0; i < n + 2; i++) {
            map[0][i] = -1;
            map[n + 1][i] = -1;
            map[i][0] = -1;
            map[i][n + 1] = -1;
        }

        int[] dx = { -1, 0, 1, 0 };
        int[] dy = { 0, -1, 0, 1 };

        int score = 0;
        while (true) {
            // 무지개 블록의 위치를 저장한다.
            List<Point> rainbowList = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (map[i][j] == m + 1) {
                        rainbowList.add(new Point(i, j));
                    }
                }
            }

            // 블록을 그룹화 시킨다
            PriorityQueue<Group> pq = new PriorityQueue<>();
            boolean[][] visited = new boolean[n + 2][n + 2];
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    // 기존에 그룹에 들어간 곳이 아니고, 무지개블록이나 검은 블록이 아니고, 빈곳이 아니면 시작
                    if (map[i][j] != m + 1 && map[i][j] > 0 && !visited[i][j]) {
                        int size = 0;
                        int rainbow = 0;
                        Queue<Point> queue = new ArrayDeque<>();
                        queue.add(new Point(i, j));
                        visited[i][j] = true;

                        while (!queue.isEmpty()) {
                            Point cur = queue.poll();
                            size++;
                            if (map[cur.x][cur.y] == m + 1) {
                                rainbow++;
                            }

                            for (int d = 0; d < 4; d++) {
                                int nx = cur.x + dx[d];
                                int ny = cur.y + dy[d];
                                // 검은 블록이나 빈공간이거나 기존 색과 다르다면 패스
                                if (map[nx][ny] <= 0 || (map[nx][ny] != map[i][j] && map[nx][ny] != m + 1)) {
                                    continue;
                                }
                                // 이미 방문한 곳이 아니라면 진행
                                if (!visited[nx][ny]) {
                                    visited[nx][ny] = true;
                                    queue.add(new Point(nx, ny));
                                }
                            }
                        }
                        pq.add(new Group(i, j, rainbow, size));
                        // 무지개 블록은 다른 블록들도 써야하니 방문처리 해제
                        for (Point p : rainbowList) {
                            visited[p.x][p.y] = false;
                        }
                    }
                }
            }

            // 그룹이 만들어지지 않는다면 종료시킨다
            if (pq.isEmpty() || pq.peek().size == 1) {
                break;
            }

            // 1. 크기가 가장 큰 블록 그룹을 찾는다
            Group cur = pq.poll();

            // 2. 1에서 찾은 블록 그룹의 모든 블록을 제거한다
            int x = cur.sx;
            int y = cur.sy;
            Queue<Point> q = new ArrayDeque<>();
            q.add(new Point(x, y));
            visited = new boolean[n + 2][n + 2];
            visited[x][y] = true;
            int color = map[x][y];

            while (!q.isEmpty()) {
                Point p = q.poll();
                map[p.x][p.y] = 0;

                for (int d = 0; d < 4; d++) {
                    int nx = p.x + dx[d];
                    int ny = p.y + dy[d];
                    if (map[nx][ny] <= 0 || (map[nx][ny] != color && map[nx][ny] != m + 1)) {
                        continue;
                    }
                    if (!visited[nx][ny]) {
                        visited[nx][ny] = true;
                        q.add(new Point(nx, ny));
                    }
                }
            }

            // 2.1. 점수를 획득한다
            score += cur.size * cur.size;

            // 3. 격자에 중력을 작용시킨다
            for (int j = 1; j <= n; j++) {
                for (int i = n - 1; i > 0; i--) {
                    if (map[i][j] < 1) {
                        continue;
                    }
                    int k = i + 1;
                    while (map[k][j] == 0) {
                        k++;
                    }
                    k--;
                    if (k != i) {
                        map[k][j] = map[i][j];
                        map[i][j] = 0;
                    }
                }
            }

            // 4. 격자를 90도 반시계로 회전시킨다
            int[][] tempMap = new int[n + 2][n + 2];
            for (int i = 0; i < n + 2; i++) {
                for (int j = 0; j < n + 2; j++) {
                    tempMap[n + 1 - j][i] = map[i][j];
                }
            }
            for (int i = 0; i < n + 2; i++) {
                for (int j = 0; j < n + 2; j++) {
                    map[i][j] = tempMap[i][j];
                }
            }

            // 5. 격자에 중력을 작용시킨다
            for (int j = 1; j <= n; j++) {
                for (int i = n - 1; i > 0; i--) {
                    if (map[i][j] < 1) {
                        continue;
                    }
                    int k = i + 1;
                    while (map[k][j] == 0) {
                        k++;
                    }
                    k--;
                    if (k != i) {
                        map[k][j] = map[i][j];
                        map[i][j] = 0;
                    }
                }
            }
        }
        System.out.println(score);
    }
}
