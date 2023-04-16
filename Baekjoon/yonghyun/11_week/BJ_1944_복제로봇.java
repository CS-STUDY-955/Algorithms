import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 복제 로봇
 * https://www.acmicpc.net/problem/1944
 *
 * 1. S나 K에서 다른 K로 이동하는 거리가 가장 가까운 곳에서 복제를 생성하고 움직이면 된다.
 * 2. 따라서 지나간 복제 스팟에는 항상 예비 로봇을 생성해 둔다고 가정한다.
 * 3. 방문한 복제 스팟의 위치를 저장해두고, 각 위치에서 가장 가까운 K에 방문한다.
 *  - K가 존재하는 개수만큼 bfs를 호출한다.
 *  - 현재 로봇이 존재하는 위치에서 가장 가까운 K는 bfs로 구한다.
 *  - 가까운 K의 위치를 방문한 복제 스팟에 저장하고, K를 0으로 바꾸고, 움직인 거리를 저장한다.
 * 4. 맵에 존재하는 모든 K를 방문했으면 움직인 횟수를, 방문할 수 없으면 -1을 출력하고 종료한다.
 *  - 모두 방문했는지 확인하기 위해 spot 변수를 사용하여 K의 개수를 관리한다.
 *
 * @author 배용현
 *
 */
public class BJ_1944_복제로봇 {

    static int N, spot = 0, answer = 0;
    static char[][] map;
    static ArrayList<int[]> visitedLocation;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        input();
        solution();
        System.out.print(spot==0 ? answer : -1);
    }

    private static void solution() {
        int numOfSpot = spot;
        for (int i = 0; i < numOfSpot; i++) {       // 초기 K의 개수만큼 반복
            bfs();
        }
    }

    private static void bfs() {     // 현재 로봇이 존재하는 위치에서 각 K까지의 최단거리 구하는 bfs 메서드
        Queue<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[N][N];
        for (int[] location : visitedLocation) {        // 현재 존재하는 로봇의 위치 모두 저장
            q.add(new int[]{location[0], location[1], 0});
            visited[location[1]][location[0]] = true;
        }

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];
            int d = cur[2];

            if (map[y][x]=='K') {       // 가장 가까운 K에 도착하면
                visitedLocation.add(new int[]{x, y});       // 로봇 복제하고
                map[y][x] = '0';        // 방문처리 해주고
                answer += d;        // 거리 정답에 더해주고
                spot--;         // 남은 K의 개수 세주고
                return;     // 리턴
            }

            for (int i = 0; i < 4; i++) {
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];

                if (isOut(nx, ny) || visited[ny][nx] || map[ny][nx] == '1') {
                    continue;
                }

                q.add(new int[]{nx, ny, d+1});
                visited[ny][nx] = true;
            }
        }
    }

    private static boolean isOut(int x, int y) {
        return x < 0 || y < 0 || x > N - 1 || y > N - 1;
    }

    private static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());

        visitedLocation = new ArrayList<>();

        map = new char[N][N];
        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            for (int j = 0; j < N; j++) {
                map[i][j] = input.charAt(j);
                if (map[i][j] == 'S') {
                    visitedLocation.add(new int[]{j, i});
                } else if (map[i][j] == 'K') {
                    spot++;
                }
            }
        }

    }
}
