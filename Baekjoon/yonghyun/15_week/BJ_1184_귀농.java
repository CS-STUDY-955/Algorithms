import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 귀농
 * https://www.acmicpc.net/problem/1184
 *
 * 1. 모든 가능한 직사각형을 구한다.
 * 2. 두 직사각형을 골라 해당하는 직사각형들이 한 꼭지점으로 이어져 있는지 검사한다.
 * 3. 2번을 만족했다면 같은 수익을 가지는지 검사한다.
 * --------------------------------------
 * 1. 구현이 너무 오래걸려서 답을 보니 다른 사람들은 꼭지점을 기준으로 풀이하고 있었다.
 * 2. 코드를 다시 보면서 이해해야 할 것 같다.
 *
 * @author 배용현
 *
 */
public class BJ_1184_귀농 {

    static int[][] map;
    static int[][] cache;
    static ArrayList<Integer> sum1, sum2;
    static int N, ans;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    public static void main(String[] args) throws Exception {
        input();
        solution();
        System.out.println(ans);
    }

    private static void solution() {
        // 내부 꼭지점들에 대해서 수행
        for(int r = 0 ; r < N - 1 ; ++r) {
            for(int c = 0 ; c < N - 1 ; ++c) {
                // 왼쪽 위와 오른쪽 아래 비교
                sumUpperLeft(r, c);
                sumUnderRight(r + 1, c + 1);
                count();
                clean();

                // 오른쪽 위와 왼쪽 아래 비교
                sumUpperRight(r, c + 1);
                sumUnderLeft(r + 1, c);
                count();
                clean();
            }
        }
    }

    private static void input() throws IOException {
        N = Integer.parseInt(br.readLine());

        map = new int[N][N];
        cache = new int[N][N];
        sum1 = new ArrayList<>();
        sum2 = new ArrayList<>();
        ans = 0;

        for(int r = 0 ; r < N ; ++r) {
            st = new StringTokenizer(br.readLine());
            for(int c = 0 ; c < N ; ++c) {
                map[r][c] = Integer.parseInt(st.nextToken());
            }
        }
    }

    private static void sumUnderLeft(int R, int C) {
        for(int r = R ; r < N ; ++r) {
            int sum = 0;
            for(int c = C ; c >= 0 ; --c) {
                sum += map[r][c];

                if(r == R) {
                    cache[r][c] = sum;
                } else {
                    cache[r][c] = cache[r - 1][c] + sum;
                }

                sum2.add(cache[r][c]);
            }
        }
    }

    private static void sumUpperRight(int R, int C) {
        for(int r = R ; r >= 0 ; --r) {
            int sum = 0;
            for(int c = C ; c < N ; ++c) {
                sum += map[r][c];

                if(r == R) {
                    cache[r][c] = sum;
                } else {
                    cache[r][c] = cache[r + 1][c] + sum;
                }

                sum1.add(cache[r][c]);
            }
        }
    }

    private static void sumUnderRight(int R, int C) {
        for(int r = R ; r < N ; ++r) {
            int sum = 0;
            for(int c = C ; c < N ; ++c) {
                sum += map[r][c];

                if(r == R) {
                    cache[r][c] = sum;
                } else {
                    cache[r][c] = cache[r - 1][c] + sum;
                }

                sum2.add(cache[r][c]);
            }
        }
    }

    private static void sumUpperLeft(int R, int C) {
        for(int r = R ; r >= 0 ; --r) {
            int sum = 0;
            for(int c = C ; c >= 0 ; --c) {
                sum += map[r][c];

                if(r == R) {
                    cache[r][c] = sum;
                } else {
                    cache[r][c] = cache[r + 1][c] + sum;
                }

                sum1.add(cache[r][c]);
            }
        }
    }

    private static void count() {
        for(int i : sum1) {
            for(int j : sum2) {
                if(i == j) ans++;
            }
        }
    }

    private static void clean() {
        sum1.clear();
        sum2.clear();

        for(int r = 0 ; r < N ; ++r) {
            for(int c = 0 ; c < N ; ++c) {
                cache[r][c] = 0;
            }
        }
    }
}
