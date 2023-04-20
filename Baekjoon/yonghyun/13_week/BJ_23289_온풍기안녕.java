import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 온풍기 안녕!
 * https://www.acmicpc.net/problem/23289
 *
 * 1. (x, y)에서 x가 행, y가 열을 의미함에 주의한다.
 * 2. 온풍기와 확인할 칸은 따로 저장하는게 좋을것 같다.
 * 3. 네 방향으로 퍼질때 부채꼴로 퍼지므로 2차원 배열의 방향이 필요할 것 같다.
 * -----------------------------------------------------------
 * - 벽을 만들고 체크하는 로직이 4차원 boolean 배열로 가능했다.
 * - 각 차원의 의미를 잘 이해하고 세팅해야 한다.
 *
 * @author 배용현
 *
 */
class BJ_23289_온풍기안녕 {

	static int R, C, K, W;
	static int[][] map;     // 각 칸의 온도 저장
    static boolean[][][][] wall;        // 0행 1열에서 2행 3열로의 이동이 벽으로 막혀있는지
    static ArrayList<int[]> hotFan = new ArrayList<>();
    static ArrayList<int[]> checkLocation = new ArrayList<>();
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
    static int[] dy = {0, 0, 0, -1, 1};     // 우, 좌, 상, 하
    static int[] dx = {0, 1, -1, 0, 0};     // 0번은 더미
    static int[][] windY = {{}, {0, -1, 1}, {0, -1, 1}, {-1, -1, -1}, {1, 1, 1}};      // 우, 좌, 상, 하
    static int[][] windX = {{}, {1, 1, 1}, {-1, -1, -1}, {0, -1, 1}, {0, -1, 1}};      // 0번은 더미

	public static void main(String[] args) throws IOException {
		input();
        System.out.print(solution());
	}

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		R = parseInt(st.nextToken());
		C = parseInt(st.nextToken());
		K = parseInt(st.nextToken());

		map = new int[R][C];
        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < C; j++) {
                int input = Integer.parseInt(st.nextToken());
                if (input == 5) {       // 체크할 위치 저장
                    checkLocation.add(new int[]{j, i});
                } else if (input != 0) {        // 온풍기 위치 저장
                    hotFan.add(new int[]{j, i, input});
                }
            }
        }

        wall = new boolean[R][C][R][C];     // 벽 정보
        W = parseInt(br.readLine());
        for (int i = 0; i < W; i++) {
            st = new StringTokenizer(br.readLine());
            int y = parseInt(st.nextToken()) - 1;       // 행
            int x = parseInt(st.nextToken()) - 1;       // 열
            int t = parseInt(st.nextToken());       // 방향

            if (t == 0) {       // 위쪽이 막힌 경우
                wall[y][x][y-1][x] = true;        // y축 양방향 모두 wall 설치
                wall[y-1][x][y][x] = true;
            } else {        // 아래쪽이 막힌 경우
                wall[y][x][y][x+1] = true;        // x축 양방향 모두 wall 설치
                wall[y][x+1][y][x] = true;
            }
        }
	}

	private static int solution() {
        for (int i = 0; i <= 100; i++) {        // 먹은 초콜릿 수
            if(check()) {       // 조사하는 칸의 온도가 모두 K이상이면 먹은 초콜릿의 수 리턴
                return i;
            }

            wind();     // 온풍기에서 바람 배출
            adjust();       // 온도 조절
        }

        return 101;
	}

    private static boolean check() {        // 조사하는 칸의 온도가 모두 K이상인지 확인하는 메서드
        for (int[] location : checkLocation) {
            if (map[location[1]][location[0]] < K) {        // 하나라도 K보다 작으면 false
                return false;
            }
        }

        return true;
    }

    private static void wind() {
        int[][] newMap = new int[R][C];

        for (int[] fan : hotFan) {      // 모든 온풍기에 대해 바람을 내보냄
            boolean[][] visited = new boolean[R][C];        // bfs용 방문 배열
            Queue<int[]> q = new ArrayDeque<>();
            int d = fan[2];
            int sx = fan[0] + dx[d];
            int sy = fan[1] + dy[d];

            visited[sy][sx] = true;
            newMap[sy][sx] += 5;
            q.add(new int[]{sx, sy, 4});
            while (!q.isEmpty()) {
                int[] cur = q.poll();
                int x = cur[0];
                int y = cur[1];
                int heat = cur[2];

                for (int i = 0; i < 3; i++) {       // 3방향으로 확산
                    int nx = x + windX[d][i];
                    int ny = y + windY[d][i];

                    if (isOut(nx, ny) || visited[ny][nx] || isWall(d, x, y, nx, ny) || heat==0) {       // 격자를 벗어나거나 방문했거나
                        continue;
                    }

                    newMap[ny][nx] += heat;
                    visited[ny][nx] = true;
                    q.add(new int[]{nx, ny, heat-1});
                }
            }
        }

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                map[i][j] += newMap[i][j];
            }
        }
    }

    private static boolean isWall(int d, int x, int y, int nx, int ny) {
        if (x==nx || y==ny) {       // 상하좌우로의 바람 이동
            if (wall[y][x][ny][nx]) {       // 해당 방향이 벽으로 막혔으면
                return true;        // 벽임을 리턴
            }
        } else {        // 대각선으로의 바람 이동
            if(d==1 || d==2) {      // 온풍기 바람이 좌우 방향이면
                if (wall[y][x][ny][x] || wall[ny][x][ny][nx]) {
                    return true;
                }

            } else {        // 상하 방향이면
                if (wall[y][x][y][nx] || wall[y][nx][ny][nx]) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isOut(int x, int y) {
        return x < 0 || y < 0 || x > C - 1 || y > R - 1;
    }

    private static void adjust() {
        int[][] newMap = new int[R][C];
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                for (int d = 1; d < 5; d++) {       // 근처 네 방향의 칸에 대해 조절이 일어남
                    int nx = j + dx[d];
                    int ny = i + dy[d];

                    if (isOut(nx, ny) || wall[i][j][ny][nx]) {      // 조절되지 않는 칸이면 패스
                        continue;
                    }

                    if (map[ny][nx] < map[i][j]) {      // 근방이 작으면
                        int temp = (map[i][j] - map[ny][nx]) / 4;       // 온도차이 / 4 만큼 온도가 이동
                        newMap[i][j] -= temp;
                        newMap[ny][nx] += temp;
                    }
                }
            }
        }

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                map[i][j] += newMap[i][j];      // 조절된 온도를 map에 적용
                if (i==0 || j==0 || i == R-1 || j == C-1) {     // 가장자리는 -1도
                    if (map[i][j] > 0) {        // 0이면 변하지 않음
                        map[i][j]--;
                    }
                }
            }
        }
    }

}