import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

// 1. 4방 탐색으로 끝까지 보낸다. 이때, 둘중 방향에 대해 앞선걸 먼저 보내야한다
// 2. 4방탐색을 하는 메서드에선 이미 간적 있는 곳이거나 둘다 구멍에 들어가는 경우라면 null을 반환한다
// 3. 그 이외의 경우라면 이동한 후의 위치정보를 반환하여 방문처리하고 큐에 삽입한다.
public class BJ_13460_구슬_탈출_2 {
    private static Point hole;
    private static int[] dx = { -1, 0, 1, 0 };
    private static int[] dy = { 0, -1, 0, 1 };
    private static char[][] board;
    private static boolean[][][][] visited;

    // 두 구슬의 위치정보와 bfs에서 탐색의 깊이를 저장하는 클래스
    private static class Marbles {
        int bx, by;
        int rx, ry;
        int depth;

        public Marbles() {
        }

        public Marbles(int bx, int by, int rx, int ry, int depth) {
            this.bx = bx;
            this.by = by;
            this.rx = rx;
            this.ry = ry;
            this.depth = depth;
        }

        // 해당 방향으로 움직일 수 있는지 확인하는 메서드
        // 이동할 수 있다면 이동한 후의 위치를 담은 객체를
        // 할 수 없다면 null을 반환한다
        public Marbles movable(int dir) {
            Point blue = new Point(bx, by);
            Point red = new Point(rx, ry);
            // 이동방향에 대해 더 앞선 구슬이 먼저 이동해야한다
            // x,y 좌표와 dx,dy를 곱한 값을 비교하면, 그 값이 큰 쪽이 먼저 움직이는 쪽이다
            // 이렇게 하면 dir에 따라 4가지를 모두 만들지 않아도 된다
            if (red.x * dx[dir] == blue.x * dx[dir]) {
                // 먼저 이동시켜야 하는 구슬을 이동시키고
                // 이동이 끝난 위치가 구멍이 아니라면 임시로 막아두고
                // 다른 구슬을 이동시킨뒤 임시로 막아둔걸 풀어준다
                if (red.y * dy[dir] > blue.y * dy[dir]) {
                    move(dir, red);
                    // 구멍이라면 막으면 안된다
                    if (board[red.x][red.y] != 'O')
                        board[red.x][red.y] = 'R';
                    move(dir, blue);
                    if (board[red.x][red.y] != 'O')
                        board[red.x][red.y] = '.';
                } else {
                    move(dir, blue);
                    if (board[blue.x][blue.y] != 'O')
                        board[blue.x][blue.y] = 'B';
                    move(dir, red);
                    if (board[blue.x][blue.y] != 'O')
                        board[blue.x][blue.y] = '.';
                }
            } else {
                if (red.x * dx[dir] > blue.x * dx[dir]) {
                    move(dir, red);
                    if (board[red.x][red.y] != 'O')
                        board[red.x][red.y] = 'R';
                    move(dir, blue);
                    if (board[red.x][red.y] != 'O')
                        board[red.x][red.y] = '.';
                } else {
                    move(dir, blue);
                    if (board[blue.x][blue.y] != 'O')
                        board[blue.x][blue.y] = 'B';
                    move(dir, red);
                    if (board[blue.x][blue.y] != 'O')
                        board[blue.x][blue.y] = '.';
                }
            }
            // 만약 이미 방문한 위치라면 null
            if (visited[blue.x][blue.y][red.x][red.y]) {
                return null;
            }
            // 파란 구슬이 구멍에 들어갔다면 null
            if (blue.x == hole.x && blue.y == hole.y) {
                return null;
            }
            // 둘다 아니라면 다음 구슬 위치정보를 리턴
            return new Marbles(blue.x, blue.y, red.x, red.y, this.depth + 1);
        }

        // 주어진 좌표를 주어진 방향으로 끝까지 이동시키는 매서드
        public void move(int dir, Point p) {
            while (true) {
                char temp = board[p.x + dx[dir]][p.y + dy[dir]];
                // 이동이 가능한 곳이라면 이동
                if (temp == '.' || temp == 'O') {
                    p.x += dx[dir];
                    p.y += dy[dir];
                    // 이동이 끝난 위치가 구멍이라면 종료
                    if (board[p.x][p.y] == 'O') {
                        break;
                    }
                    // 다음 장소가 이동할 수 없는 곳이라면 종료
                } else {
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        board = new char[n][m];
        visited = new boolean[n][m][n][m];
        Marbles start = new Marbles();
        hole = new Point();

        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            for (int j = 0; j < m; j++) {
                board[i][j] = str.charAt(j);
                // 구슬이 있는 곳은 위치정보만 저장하고 보드정보에선 비워둔다
                if (board[i][j] == 'B') {
                    start.bx = i;
                    start.by = j;
                    board[i][j] = '.';
                }
                if (board[i][j] == 'R') {
                    start.rx = i;
                    start.ry = j;
                    board[i][j] = '.';
                }
                // 구멍 위치정보 저장
                if (board[i][j] == 'O') {
                    hole.x = i;
                    hole.y = j;
                }
            }
        }

        // bfs 준비
        Queue<Marbles> queue = new ArrayDeque<>();
        start.depth = 0;
        int depth = 0;
        boolean canDoThat = false;
        queue.add(start);
        visited[start.bx][start.by][start.rx][start.ry] = true;

        // bfs 시작
        while (!queue.isEmpty()) {
            Marbles cur = queue.poll();
            // 새로운 단계의 탐색을 시작하면 탐색 깊이 업데이트
            if (cur.depth == depth) {
                depth++;
                if (cur.depth > 10) {
                    break;
                }
            }
            // 만약 빨간 구슬이 구멍에 위치해 있다면 종료
            if (cur.rx == hole.x && cur.ry == hole.y) {
                canDoThat = true;
                depth = cur.depth;
                break;
            }
            for (int i = 0; i < 4; i++) {
                Marbles temp = cur.movable(i);
                // 해당 방향으로 구슬을 기울일 수 없다면 패스
                if (temp == null) {
                    continue;
                }
                visited[temp.bx][temp.by][temp.rx][temp.ry] = true;
                queue.add(temp);
            }
        }

        // 가능한 경우가 있었다면 횟수를, 아니라면 -1 출력
        if (canDoThat) {
            System.out.println(depth);
        } else {
            System.out.println(-1);
        }
    }
}