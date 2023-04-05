import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_21611_마법사_상어와_블리자드 {
    private static int n;
    // 구슬용 4방탐색 좌 하 우 상
    private static int[] dx = { 0, 1, 0, -1 };
    private static int[] dy = { -1, 0, 1, 0 };
    private static int[] explodeCount = new int[4];
    private static int[][] map;

    private static class Index {
        int x, y, dir;
        int distance;
        int count;
        int turn;

        public Index() {
            this(n / 2, n / 2, 0, 1, 0, 0);
        }

        public Index(int x, int y, int dir, int distance, int count, int turn) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.distance = distance;
            this.count = count;
            this.turn = turn;
            this.move();
        }

        public void move() {
            x += dx[dir];
            y += dy[dir];
            if (++count == distance) {
                count = 0;
                dir = (dir + 1) % 4;
                if (++turn % 2 == 0) {
                    distance++;
                }
            }
        }
    }

    // 1. 나선형에 따라 탐색을 진행한다
    // 2. 0을 발견하면 인덱스가 탐색을 계속하는 인덱스와 0을 가리키는 인덱스로 나뉜다
    // 3. 탐색을 계속하는 인덱스가 0아닌 곳을 찾으면 0이 가리키는 인덱스에 넣는다.
    private static void pulling() {
        Index zeroIdx = new Index();
        Index searchIdx = new Index();
        while (true) {
            if (searchIdx.x == 0 && searchIdx.y == 0) {
                break;
            }
            searchIdx.move();
            if (map[zeroIdx.x][zeroIdx.y] != 0) {
                zeroIdx.move();
            } else if (map[searchIdx.x][searchIdx.y] != 0) {
                map[zeroIdx.x][zeroIdx.y] = map[searchIdx.x][searchIdx.y];
                map[searchIdx.x][searchIdx.y] = 0;
                zeroIdx.move();
            }
        }
    }

    private static boolean explosion() {
        boolean explodedAny = false;
        Index zeroIdx = new Index();
        Index searchIdx = new Index();
        int combo = 1;
        searchIdx.move();

        while (true) {
            if ((searchIdx.x == 0 && searchIdx.y == 0) || map[searchIdx.x][searchIdx.y] == 0) {
                break;
            }
            // System.out.printf("zero: %d %d, search:%d %d\n", zeroIdx.x, zeroIdx.y,
            // searchIdx.x, searchIdx.y);
            if (map[zeroIdx.x][zeroIdx.y] == map[searchIdx.x][searchIdx.y]) {
                // System.out.println("같다!");
                searchIdx.move();
                combo++;
            } else {
                // System.out.println("not same!");
                boolean isExplode = false;
                if (combo > 3) {
                    isExplode = true;
                    explodedAny = true;
                }
                while (combo-- > 0) {
                    if (isExplode) {
                        explodeCount[map[zeroIdx.x][zeroIdx.y]]++;
                        map[zeroIdx.x][zeroIdx.y] = 0;
                    }
                    zeroIdx.move();
                }
                combo = 1;
                searchIdx.move();
            }
        }
        // 마지막 연속은 처리되어 있지 않을테니 별도로 해줘야 함
        boolean isExplode = false;
        if (combo > 3) {
            isExplode = true;
            explodedAny = true;
        }
        if (isExplode) {
            while (combo-- > 0) {
                explodeCount[map[zeroIdx.x][zeroIdx.y]]++;
                map[zeroIdx.x][zeroIdx.y] = 0;
                zeroIdx.move();
            }
        }

        // System.out.printf("============폭발 후=============\n");
        // for (int[] line : map) {
        // for (int i : line) {
        // System.out.print(i + " ");
        // }
        // System.out.println();
        // }
        // System.out.println("현재 점수: "+ (explodeCount[1] + 2 * explodeCount[2] + 3 *
        // explodeCount[3]));

        return explodedAny;
    }

    private static void transform() {
        int[][] tempMap = new int[n][n];
        Index insertIdx = new Index();
        Index tailIdx = new Index();
        Index searchIdx = new Index();
        int combo = 1;
        searchIdx.move();

        while (true) {
            // insertIdx가 범위 밖이면 종료
            if (insertIdx.x == 0 && insertIdx.y == -1) {
                break;
            }
            // searchIdx가 범위 밖이면 종료
            if (searchIdx.x == 0 && searchIdx.y == -1) {
                break;
            }
            if (map[tailIdx.x][tailIdx.y] != map[searchIdx.x][searchIdx.y]) {
                tempMap[insertIdx.x][insertIdx.y] = combo;
                insertIdx.move();
                tempMap[insertIdx.x][insertIdx.y] = map[tailIdx.x][tailIdx.y];
                insertIdx.move();
                combo = 0;
            }

            searchIdx.move();
            tailIdx.move();
            combo++;
        }

        if (!(insertIdx.x == 0 && insertIdx.y == -1)) {
            if (map[tailIdx.x][tailIdx.y] != 0) {
                tempMap[insertIdx.x][insertIdx.y] = combo;
                insertIdx.move();
                if (!(insertIdx.x == 0 && insertIdx.y == -1)) {
                    tempMap[insertIdx.x][insertIdx.y] = map[tailIdx.x][tailIdx.y];
                }
            }
        }
        map = tempMap;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int[] dir = new int[m];
        int[] range = new int[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            dir[i] = Integer.parseInt(st.nextToken());
            range[i] = Integer.parseInt(st.nextToken());
        }

        // 마법용 4방탐색 더미, 상 하 좌 우
        int[] mdx = { 0, -1, 1, 0, 0 };
        int[] mdy = { 0, 0, 0, -1, 1 };

        // 상어의 마법쑈 시작
        for (int superDuperMagic = 0; superDuperMagic < m; superDuperMagic++) {
            // 마법 시전
            int x = n / 2;
            int y = n / 2;
            for (int r = 0; r < range[superDuperMagic]; r++) {
                x += mdx[dir[superDuperMagic]];
                y += mdy[dir[superDuperMagic]];
                map[x][y] = 0;
            }
            // 테스트용 코드
            // System.out.printf("=======마법 %d회 사용 후=======\n", superDuperMagic + 1);
            // for (int[] line : map) {
            // for (int i : line) {
            // System.out.print(i + " ");
            // }
            // System.out.println();
            // }
            // 마법 시전 후 빈공간 당기기
            pulling();

            // System.out.printf("=======마법 사용 후 당김=======\n");
            // for (int[] line : map) {
            // for (int i : line) {
            // System.out.print(i + " ");
            // }
            // System.out.println();
            // }

            // 연쇄 폭발
            while (explosion()) {
                pulling();
            }

            // 테스트용 코드
            // System.out.printf("=======폭발이 끝난 후=======\n");
            // for (int[] line : map) {
            // for (int i : line) {
            // System.out.print(i + " ");
            // }
            // System.out.println();
            // }

            // 변화
            transform();

            // 테스트용 코드
            // System.out.printf("=======변화가 끝난 후=======\n");
            // for (int[] line : map) {
            // for (int i : line) {
            // System.out.print(i + " ");
            // }
            // System.out.println();
            // }
        }

        System.out.println(explodeCount[1] + 2 * explodeCount[2] + 3 * explodeCount[3]);
    }
}
