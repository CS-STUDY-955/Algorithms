import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 주사위 굴리기 2
 * https://www.acmicpc.net/problem/23288
 *
 * 1. 주사위의 상태를 저장할 크기 6의 1차원 배열이 필요하다.
 * 2. N, M에 비해 K가 크그모 주어지는 맵과 별개로 얻을 수 있는 점수가 계산된 맵을 만들면 좋을 것 같다.
 * 3. 주사위를 동서남북으로 굴리는 메서드를 만들어야 한다.
 *
 * @author 배용현
 *
 */
public class BJ_23288_주사위굴리기2 {
    static int N, M, K, x, y, answer, direction;
    static int[] dice;
    static int[][] map, scoreMap;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[] dx = {0, -1, 0, 1};        // 북, 서, 남, 동
    static int[] dy = {-1, 0, 1, 0};

    public static void main(String[] args) throws IOException {
        input();
        solution();
        print();
    }

    private static void print() {
        System.out.print(answer);
    }

    private static void solution() {
        setScoreMap();
        for (int i = 0; i < K; i++) {
            roll();
            setDirection();
        }
    }

    private static void setDirection() {        // 방향 조정
        if (dice[5] > map[y][x]) {      // 주사위 아랫면이 맵보다 크면 시계방향으로 회전
            direction = (direction + 3) % 4;
        } else if (dice[5] < map[y][x]) {      // 맵보다 작으면 반시계방향으로 회전
            direction = (direction + 1) % 4;
        }

        if (isOut(x + dx[direction], y + dy[direction])) {      // 정한 direction으로 이동하지 못할 경우
            direction = (direction + 2) % 4;        // 반대로 지정
        }
    }

    private static void roll() {        // 굴리고 점수 더하는 메서드
        switch (direction) {
            case 0: rollNorth(); break;
            case 1: rollWest(); break;
            case 2: rollSouth(); break;
            case 3: rollEast(); break;
        }

        answer += scoreMap[y][x];
    }

    private static void rollNorth() {       // 북쪽으로 구름
        int temp = dice[1];
        dice[1] = dice[0];
        dice[0] = dice[4];
        dice[4] = dice[5];
        dice[5] = temp;
        y--;
    }

    private static void rollWest() {        // 서쪽으로 구름
        int temp = dice[3];
        dice[3] = dice[0];
        dice[0] = dice[2];
        dice[2] = dice[5];
        dice[5] = temp;
        x--;
    }

    private static void rollSouth() {        // 남쪽으로 구름
        int temp = dice[5];
        dice[5] = dice[4];
        dice[4] = dice[0];
        dice[0] = dice[1];
        dice[1] = temp;
        y++;
    }

    private static void rollEast() {        // 동쪽으로 구름
        int temp = dice[0];
        dice[0] = dice[3];
        dice[3] = dice[5];
        dice[5] = dice[2];
        dice[2] = temp;
        x++;
    }

    private static void setScoreMap() {     // 점수 맵을 bfs를 이용하여 저장
        boolean[][] visited = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (visited[i][j]) {
                    continue;
                }

                ArrayList<int[]> locations = bfs(i, j, visited);
                int score = locations.size() * map[i][j];       // 뽑은 위치들의 통합 점수
                for (int[] location : locations) {      // 뽑은 위치에 score 저장
                    scoreMap[location[1]][location[0]] = score;
                }
            }
        }
    }

    private static ArrayList<int[]> bfs(int y, int x, boolean[][] visited) {        // 같은 점수의 위치와 개수를 세기위해 bfs 사용
        ArrayList<int[]> locations = new ArrayList<>();     // 위치를 저장할 리스트
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{x, y});
        locations.add(new int[]{x, y});     // 동일한 값의 위치는 여기에 저장하여 리턴
        visited[y][x] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            for (int i = 0; i < 4; i++) {
                int nx = cur[0] + dx[i];
                int ny = cur[1] + dy[i];

                if(isOut(nx, ny) || visited[ny][nx] || map[ny][nx]!=map[y][x]) {
                    continue;
                }

                q.add(new int[]{nx, ny});
                locations.add(new int[]{nx, ny});
                visited[ny][nx] = true;
            }
        }

        return locations;
    }

    private static boolean isOut(int x, int y) {
        return x < 0 || y < 0 || x > M - 1 || y > N - 1;
    }

    private static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        scoreMap = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        answer = x = y = 0;
        direction = 3;      // 시작 방향은 동쪽
        dice = new int[6];      // 위, 북, 동, 서, 남, 아래
        for (int i = 0; i < 6; i++) {
            dice[i] = i + 1;
        }
    }

}
