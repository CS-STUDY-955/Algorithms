import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 마법사 상어와 토네이도
 * https://www.acmicpc.net/problem/20057
 *
 * 1. 시작 위치는 [N/2][N/2]이다.
 * 2. 이동 거리는 1부터 시작해서 2번 이동한 뒤 1씩 늘어난다.
 * 3. 이동 방향은 좌하우상으로 반복된다.
 * 4. 이동하는 칸의 먼지가 일정 비율로 근처에 흩날린다.
 * 5. 다른 칸에 흩날린 먼지는 더해지고, 맵 밖으로 나가면 정답으로 계산된다.
 *
 *
 * @author 배용현
 *
 */
public class BJ_20057_마법사상어와토네이도 {

    static int N, answer = 0;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] map;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};


    public static void main(String[] args) throws IOException {
        input();
        solution();
        print();
    }

    private static void solution() {
        int x = N / 2;
        int y = N / 2;
        int d = 0;
        int step = 1;
        int straightCnt = 1;
        int rotateCnt = 2;

        while(x!=0 || y!=0) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            int rd = (d + 3) % 4;
            int ld = (d + 1) % 4;
            int remain = map[ny][nx];
            remain -= spread(x+dx[rd], y+dy[rd], map[ny][nx], 1);
            remain -= spread(x+dx[ld], y+dy[ld], map[ny][nx], 1);
            remain -= spread(nx+(dx[rd]*2), ny+(dy[rd]*2), map[ny][nx], 2);
            remain -= spread(nx+(dx[ld]*2), ny+(dy[ld]*2), map[ny][nx], 2);
            remain -= spread(nx+(dx[d]*2), ny+(dy[d]*2), map[ny][nx], 5);
            remain -= spread(nx+dx[rd], ny+dy[rd], map[ny][nx], 7);
            remain -= spread(nx+dx[ld], ny+dy[ld], map[ny][nx], 7);
            remain -= spread(nx+dx[d]+dx[rd], ny+dy[d]+dy[rd], map[ny][nx], 10);
            remain -= spread(nx+dx[d]+dx[ld], ny+dy[d]+dy[ld], map[ny][nx], 10);
            spread(nx+dx[d], ny+dy[d], remain, 100);
            map[ny][nx] = 0;

            if (--straightCnt == 0) {
                if (--rotateCnt == 0) {
                    rotateCnt = 2;
                    step++;
                }
                d = (d + 1) % 4;
                straightCnt = step;
            }
            x = nx;
            y = ny;
        }
    }

    private static int spread(int x, int y, int originAmount, int percent) {
        int newAmount = originAmount * percent / 100;

        if(isOut(x, y))
            answer += newAmount;
        else
            map[y][x] += newAmount;

        return newAmount;
    }

    private static boolean isOut(int x, int y) {
        return x<0 || y<0 || x>N-1 || y>N-1;
    }

    private static void print() {
        System.out.println(answer);
    }

    private static void input() throws IOException {
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

}