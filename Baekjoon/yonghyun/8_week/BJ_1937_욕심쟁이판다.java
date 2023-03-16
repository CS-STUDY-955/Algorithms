import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

/**
 * 욕심쟁이 판다
 * https://www.acmicpc.net/problem/1937
 *
 * 1. 맵이 최대 500x500이므로 완전탐색을 이용하면 모든 시작 시점에 대해 전체를 탐색할 수 있어야 한다.
 * 2. 이 때 시간복잡도는 O(n^4)로 약 625억의 연산이 발생해 시간 초과가 발생한다.
 * 3. 판다가 어떤 칸에 도달했을때 그 칸에서 이동할 수 있는 칸은 항상 동일하다.
 * 4. 따라서 모든 칸에 대해 DFS로 탐색하고, 탐색을 완료한 칸은 다시 접근할 때 저장된 최대 이동 횟수를 사용할 수 있다.
 * 5. DFS로 탐색시 호출했던 칸의 이동 횟수를 갱신해야 하므로 현재 칸의 최대 이동 횟수를 리턴해야 한다.
 *
 * @author 배용현
 *
 */
public class BJ_1937_욕심쟁이판다 {

    static int N, answer;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] map, dp;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        input();
        solution();
        System.out.print(answer);
    }

    private static void solution() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(dp[i][j]!=0)     // 이미 최대 이동거리를 아는 곳은 탐색할 필요 없음
                    continue;

                answer = Math.max(answer, dfs(j, i));       // 탐색하고 결과의 최댓값을 저장
            }
        }
    }

    private static int dfs(int x, int y) {
        if(dp[y][x]!=0)         // 이전에 방문했던 칸이면
            return dp[y][x];        // 최대 현재 값만큼 이동 가능

        dp[y][x] = 1;       // 현재 칸도 이동했다고 보기 때문에 1로 초기화
        int max = 0;        // 사방의 칸 중 가장 멀리 이동할 수 있는 거리를 저장할 변수
        for (int i = 0; i < 4; i++) {       // 사방 탐색
            int nx = x + dx[i];
            int ny = y + dy[i];

            if(isOut(nx, ny) || map[ny][nx]<=map[y][x])     // 맵 밖으로 나갔거나 판다의 욕심을 채우지 못하는 칸이면 패스
                continue;

            max = Math.max(max, dfs(nx, ny));       // 다음 칸을 탐색하고 최댓값을 갱신
        }

        return dp[y][x] += max;     // 최댓값을 현재 칸의 이동거리에 더하고 이전에 호출한 칸에 전달하기 위해 값을 리턴
    }

    private static boolean isOut(int x, int y) {
        return x < 0 || y < 0 || x > N - 1 || y > N - 1;
    }

    private static void input() throws IOException {
        N = parseInt(br.readLine());
        map = new int[N][N];
        dp = new int[N][N];     // 판다가 해당 위치에서 얼마나 이동할 수 있는지 저장할 DP 배열

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++)
                map[i][j] = parseInt(st.nextToken());
        }

        answer = 0;
    }
}