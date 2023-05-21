import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_23290_마법사_상어와_복제 {
    // 상어용 4방탐색
    private static int[] sdx = { -1, 0, 1, 0 };
    private static int[] sdy = { 0, -1, 0, 1 };
    // 물고기용 8방탐색
    private static int[] fdx = { 0, -1, -1, -1, 0, 1, 1, 1 };
    private static int[] fdy = { -1, -1, 0, 1, 1, 1, 0, -1 };

    private static class Fishes {
        int[] num;
        int smell;
        boolean border;

        public Fishes() {
            num = new int[8];
            smell = 0;
            border = false;
        }
        
        public Fishes(int smell, boolean border) {
            num = new int[8];
            this.smell = smell;
            this.border = border;
        }

        public Fishes(int[] num) {
            this.num = new int[8];
            for(int i = 0; i<8; i++){
                this.num[i] = num[i];
            }
            smell = 0;
            border = false;
        }

        public int getTotal() {
            int sum = 0;
            for (int i = 0; i < 8; i++) {
                sum += num[i];
            }
            return sum;
        }

        public void eaten() {
            if (getTotal() > 0) {
                smell = 3;
            }
            for (int i = 0; i < 8; i++) {
                num[i] = 0;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Fishes[][] fishes = new Fishes[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                fishes[i][j] = new Fishes();
                if (i == 0 || i == 5 || j == 0 || j == 5) {
                    fishes[i][j].border = true;
                }
            }
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken()) - 1;
            fishes[x][y].num[d]++;
        }
        st = new StringTokenizer(br.readLine());
        int sx = Integer.parseInt(st.nextToken());
        int sy = Integer.parseInt(st.nextToken());

        while (s-- > 0) {
            // 상어가 복제 마법을 사용
            // 현재의 fishes의 내용을 복사해둔다

            // 자리를 옮긴 후의 물고기들이 들어가는 곳
            // 냄새와 경계판정도 함께 기록해야함
            Fishes[][] tempFishes = new Fishes[6][6];
            // 복제된 물고기들이 들어가는 곳
            // 각 방향의 물고기 수만 기록하면 됨
            Fishes[][] cloneFishes = new Fishes[6][6];
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    tempFishes[i][j] = new Fishes(fishes[i][j].smell, fishes[i][j].border);
                    cloneFishes[i][j] = new Fishes(fishes[i][j].num);
                }
            }

            // 모든 물고기가 한칸씩 이동한다.
            // 각 칸에 대해 8가지 방향에 대해 이동시켜야 하므로, 4*4*8번 실행한다
            for (int i = 1; i <= 4; i++) {
                for (int j = 1; j <= 4; j++) {
                    for (int k = 0; k < 8; k++) {
                        boolean cantMove = true;
                        // 해당방향의 물고기가 없다면 패스
                        if (fishes[i][j].num[k] == 0) {
                            continue;
                        }
                        // 이동할 수 있는 방향을 찾는다
                        for (int d = 8; d > 0; d--) {
                            int nx = i + fdx[(k + d) % 8];
                            int ny = j + fdy[(k + d) % 8];
                            // 물고기 냄새가 없어야 하고, 외곽이 아니고, 상어가 있으면 안된다
                            if (fishes[nx][ny].smell <= 0 && !fishes[nx][ny].border && !(nx == sx && ny == sy)) {
                                tempFishes[nx][ny].num[(k + d) % 8] += fishes[i][j].num[k];
                                cantMove = false;
                                break;
                            }
                        }
                        if (cantMove) {
                            tempFishes[i][j].num[k] += fishes[i][j].num[k];
                        }
                    }
                }
            }

            // 상어가 연속해서 3칸 이동할 경로를 정한다
            // 4*4*4가지 경우중 기존 먹은 양보다 큰 경우에만 업데이트 시킴
            // 도중에 이동할 수 없는 곳으로 간다면, 중단시킨다
            int max = -1;
            boolean[][] visited = new boolean[6][6];
            int[] route = new int[3];
            for (int i = 0; i < 4; i++) {
                int ix = sx + sdx[i];
                int iy = sy + sdy[i];
                // 만약 중간이 외곽이면 패스
                if (fishes[ix][iy].border) {
                    continue;
                }
                int isum = tempFishes[ix][iy].getTotal();
                visited[ix][iy] = true;
                for (int j = 0; j < 4; j++) {
                    int jx = ix + sdx[j];
                    int jy = iy + sdy[j];
                    // 만약 중간이 외곽이면 패스
                    if (fishes[jx][jy].border) {
                        continue;
                    }
                    int jsum = tempFishes[jx][jy].getTotal();
                    if (visited[jx][jy]){
                        jsum = 0;
                    }
                    visited[jx][jy] = true;
                    for (int k = 0; k < 4; k++) {
                        int kx = jx + sdx[k];
                        int ky = jy + sdy[k];
                        // 만약 외곽이면 패스
                        if (fishes[kx][ky].border) {
                            continue;
                        }
                        int ksum = tempFishes[kx][ky].getTotal();
                        if (visited[kx][ky]){
                            ksum = 0;
                        }
                        // 최댓값 갱신이면 기록
                        if (max < isum + jsum + ksum) {
                            max = isum + jsum + ksum;
                            route[0] = i;
                            route[1] = j;
                            route[2] = k;
                        }
                    }
                    visited[jx][jy] = false;
                }
                visited[ix][iy] = false;
            }

            // 이동할 경로가 정해졌다면 그 경로로 이동시킨다
            // 먹은 위치엔 물고기 냄새를 3으로 만든다
            for (int i = 0; i < 3; i++) {
                sx += sdx[route[i]];
                sy += sdy[route[i]];
                tempFishes[sx][sy].eaten();
            }

            // 냄새값을 -1 시킨다
            for (int i = 1; i <= 4; i++) {
                for (int j = 1; j <= 4; j++) {
                    tempFishes[i][j].smell--;
                }
            }

            // 결과와 복제된 물고기 정보를 합쳐 원래의 배열에 넣는다
            for (int i = 1; i <= 4; i++) {
                for (int j = 1; j <= 4; j++) {
                    fishes[i][j] = tempFishes[i][j];
                    for(int k = 0; k<8; k++){
                        fishes[i][j].num[k] += cloneFishes[i][j].num[k];
                    }
                }
            }
        }

        // 총 합 구하기
        int sum = 0;
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                sum += fishes[i][j].getTotal();
            }
        }
        System.out.println(sum);
    }
}
