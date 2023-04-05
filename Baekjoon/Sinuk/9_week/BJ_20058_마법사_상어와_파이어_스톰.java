import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_20058_마법사_상어와_파이어_스톰 {
    private static int len;
    // 우 하 좌 상
    private static int[] dx = { 0, 1, 0, -1 };
    private static int[] dy = { 1, 0, -1, 0 };
    // ice 출처 dx/dy, 상 우 하 좌?
    private static int[] fdx = { -1, 0, 1, 0 };
    private static int[] fdy = { 0, 1, 0, -1 };
    private static boolean[][] visited;
    private static int[][] ice;

    private static int dfs(int x, int y) {
        int sum = 1;

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (!visited[nx][ny] && ice[nx][ny] > 0) {
                visited[nx][ny] = true;
                sum += dfs(nx, ny);
            }
        }

        return sum;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        int[] power = { 1, 2, 4, 8, 16, 32, 64 };
        len = power[n];
        ice = new int[len + 2][len + 2];
        for (int i = 1; i <= len; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= len; j++) {
                ice[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[] magic = new int[q];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < q; i++) {
            magic[i] = Integer.parseInt(st.nextToken());
        }

        // System.out.println();
        // q 만큼 파이어스톰 시전
        for (int storm = 0; storm < q; storm++) {
            // 회전
            int l = power[magic[storm]];
            int[][] tempIce = new int[len + 2][len + 2];
            if (l != 1) {
                for (int i = 1; i <= len; i += l) {
                    for (int j = 1; j <= len; j += l) {
                        // 각각의 격자별로 반복
                        int x = i;
                        int y = j;
                        // System.out.printf("==%d, %d==\n", x, y);
                        for (int k = 0; k < power[magic[storm]] - 1; k++) {
                            int nx = x + k;
                            int ny = y + k;
                            // System.out.printf("%d, %d & ", nx, ny);
                            int layer = l - k * 2 - 1;
                            int fx = nx + layer;
                            int fy = ny;
                            // System.out.printf("%d, %d\n", fx, fy);
                            // 4방향에 대해
                            for (int d = 0; d < 4; d++) {
                                // 각각 layer 만큼
                                for (int a = 0; a < layer; a++) {
                                    nx += dx[d];
                                    ny += dy[d];
                                    fx += fdx[d];
                                    fy += fdy[d];
                                    tempIce[nx][ny] = ice[fx][fy];
                                    // System.out.printf("%d, %d => %d, %d\n", nx, ny, fx, fy);
                                }
                            }
                        }
                    }
                }
            } else {
                for (int i = 1; i <= len; i++) {
                    for (int j = 1; j <= len; j++) {
                        tempIce[i][j] = ice[i][j];
                    }
                }
            }

            // 녹음
            for (int i = 1; i <= len; i++) {
                for (int j = 1; j <= len; j++) {
                    int count = 0;
                    if (tempIce[i][j] == 0) {
                        ice[i][j] = 0;
                        continue;
                    }
                    for (int d = 0; d < 4; d++) {
                        if (tempIce[i + dx[d]][j + dy[d]] > 0) {
                            count++;
                        }
                    }
                    if (count < 3) {
                        ice[i][j] = tempIce[i][j] - 1;
                    } else {
                        ice[i][j] = tempIce[i][j];
                    }
                }
            }
            // 템프값 옮기기
            // for (int i = 1; i <= len; i++) {
            // for (int j = 1; j <= len; j++) {
            // ice[i][j] = tempIce[i][j];
            // }
            // }

            // System.out.println(power[magic[storm]]);
            // for (int i = 1; i <= len; i++) {
            // for (int j = 1; j <= len; j++) {
            // System.out.printf("%2d ", ice[i][j]);
            // }
            // System.out.println();
            // }
        }

        // 총 합 구하기 & dfs로 덩어리 구하기
        int sum = 0;
        int max = 0;
        visited = new boolean[len + 2][len + 2];
        for (int i = 1; i <= len; i++) {
            for (int j = 1; j <= len; j++) {
                sum += ice[i][j];
                if (!visited[i][j] && ice[i][j] > 0) {
                    visited[i][j] = true;
                    max = Math.max(max, dfs(i, j));
                }
            }
        }
        System.out.println(sum);
        System.out.println(max);
    }
}
