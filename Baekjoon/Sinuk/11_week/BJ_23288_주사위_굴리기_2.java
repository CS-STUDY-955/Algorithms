import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_23288_주사위_굴리기_2 {
    // 동 남 서 북
    private static int[] dx = { 0, 1, 0, -1 };
    private static int[] dy = { 1, 0, -1, 0 };
    private static int[][] map;
    private static int[][] scores;
    private static boolean[][] visited;

    // 재귀를 사용하여 연속해서 위치한 숫자들의 갯수를 구하는 메서드
    private static int getScore(int x, int y, int target) {
        int sum = 1;
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (!visited[nx][ny] && map[nx][ny] == target) {
                visited[nx][ny] = true;
                sum += getScore(nx, ny, target);
            }
        }
        return sum;
    }

    // 재귀를 사용하여 위에서 구한 점수를 연속해서 위치한 숫자들에 넣는 메서드
    private static void setScore(int x, int y, int value) {
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (scores[nx][ny] == 0 && map[nx][ny] == map[x][y]) {
                scores[nx][ny] = value;
                setScore(nx, ny, value);
            }
        }
    }

    // 주사위 상태: 윗면, 남쪽, 아랫면, 북쪽, 서쪽, 동쪽
    // 시계방향으로 방향 숫자 증가함
    private static void roll(int[] dice, int dir) {
        // 동쪽 <- 윗면 <- 서쪽 <- 아랫면 <- 동쪽
        if (dir == 0) {
            int temp = dice[5];
            dice[5] = dice[0];
            dice[0] = dice[4];
            dice[4] = dice[2];
            dice[2] = temp;
            // 남쪽 <- 윗면 <- 북쪽 <- 아랫면 <- 남쪽
        } else if (dir == 1) {
            int temp = dice[1];
            dice[1] = dice[0];
            dice[0] = dice[3];
            dice[3] = dice[2];
            dice[2] = temp;
            // 서쪽 <- 윗면 <- 동쪽 <- 아랫면 <- 남쪽
        } else if (dir == 2) {
            int temp = dice[4];
            dice[4] = dice[0];
            dice[0] = dice[5];
            dice[5] = dice[2];
            dice[2] = temp;
            // 북쪽 <- 윗면 <- 남쪽 <- 아랫면 <- 남쪽
        } else if (dir == 3) {
            int temp = dice[3];
            dice[3] = dice[0];
            dice[0] = dice[1];
            dice[1] = dice[2];
            dice[2] = temp;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        map = new int[n + 2][m + 2];
        scores = new int[n + 2][m + 2];
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        visited = new boolean[n + 2][m + 2];

        // 들어가기 앞서서 미리 각 칸의 점수들을 구해둔다
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (!visited[i][j]) {
                    visited[i][j] = true;
                    scores[i][j] = getScore(i, j, map[i][j]);
                    setScore(i, j, scores[i][j]);
                }
            }
        }
        // 주사위 셋팅: 윗면, 남쪽, 아랫면, 북쪽, 서쪽, 동쪽
        int[] dice = { 1, 5, 6, 2, 4, 3 };
        int dir = 0;
        int sum = 0;
        int x = 1;
        int y = 1;
        // 횟수에 맞게 주사위 굴리기
        while (k-- > 0) {
            // 다음 이동 장소가 범위 밖이면 반대 방향으로 돌림
            if (map[x + dx[dir]][y + dy[dir]] == 0) {
                dir = (dir + 2) % 4;
            }
            x += dx[dir];
            y += dy[dir];
            // 방향에 따라 주사위 굴림고 점수 획득
            roll(dice, dir);
            sum += map[x][y] * scores[x][y];
            // 밑면과 지도상의 값의 관계에 따라 방향 변경
            if (dice[2] > map[x][y]) {
                dir = (dir + 1) % 4;
            } else if (dice[2] < map[x][y]) {
                dir = (dir + 3) % 4;
            }
        }
        System.out.println(sum);
    }
}
