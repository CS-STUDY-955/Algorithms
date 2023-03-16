import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

/**
 * 원판 돌리기
 * https://www.acmicpc.net/problem/17822
 *
 * 1. 원판을 ArrayDeque의 배열로 만들면 회전을 구현하기 유리하다.
 *  - 대신 근처 원소에 접근하기 어려우므로 그냥 2차원 배열로 만든다.
 *  - 대신 회전은 따로 메서드로 구현해줘야한다.
 * 2. 회전은 M단위로 한바퀴이므로 M으로 나눈 나머지만큼만 수행한다.
 *  - 회전 메서드에 방향과 양을 매개변수로 넘기고 시계방향 회전으로 통일해서 구현한다.
 *  - 예) M=4일 때, 반시계 방향으로 7만큼 회전 -> 시계 방향으로 1만큼 회전
 * 3. 회전이 끝난 뒤 근처 행과 열에 접근하여 주어진대로 연산한다.
 * 4. 주어진 횟수만큼의 회전이 끝난 뒤 모든 원판 원소의 합을 구한다.
 *
 * @author 배용현
 *
 */
public class BJ_17822_원판돌리기 {

    static int N, M, T;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] disk, rotation;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        input();
        solution();
        print();
    }

    private static void print() {       // 배열을 순회하여 원판의 남은 숫자의 합을 구한다.
        int answer = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                answer += disk[i][j];
            }
        }

        System.out.print(answer);
    }

    private static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        N = parseInt(st.nextToken());
        M = parseInt(st.nextToken());
        T = parseInt(st.nextToken());

        disk = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                disk[i][j] = parseInt(st.nextToken());
            }
        }

        rotation = new int[T][3];
        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            rotation[i][0] = parseInt(st.nextToken());
            rotation[i][1] = parseInt(st.nextToken());
            rotation[i][2] = parseInt(st.nextToken());
        }
    }

    private static void solution() {
        for (int i = 0; i < rotation.length; i++) {
            int x = rotation[i][0];     // 회전시킬 원판의 번호
            int d = rotation[i][1];     // 회전 방향
            int k = rotation[i][2];     // 회전 수

            for (int j = x; j <= N; j += x) {       // x의 배수에 해당하는 원판을 회전시킨다.
                rotate(j-1, d, k);      // 실제 인덱스는 -1하여 호출
            }

            if(!isExist()) {        // 원판에 수가 없으면 더이상 뭘 할 필요가 없음
                break;
            }

            boolean activated = false;      // 인접하는 수가 있는지 확인
            for (int j = 0; j < N; j++) {
                for (int l = 0; l < M; l++) {
                    if(disk[j][l]==0)       // 지워진 칸은 패스
                        continue;

                    if(clear(l, j)>0)       // 지우는 행동을 했으면
                        activated = true;       // 체크
                }
            }

            if (!activated) {       // 인접하면서 수가 같은 것이 없으면
                adjust();       // 평균을 계산하고 각 숫자를 조정
            }
        }

    }

    private static void adjust() {
        double avg = getAvg();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if(disk[i][j]==0 || disk[i][j]==avg)
                    continue;

                if(disk[i][j]<avg)
                    disk[i][j]++;
                else
                    disk[i][j]--;
            }
        }

    }

    private static double getAvg() {
        int sum = 0;
        int num = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if(disk[i][j]==0)
                    continue;

                sum += disk[i][j];
                num++;
            }
        }

        return (double)sum / num;
    }

    private static boolean isExist() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if(disk[i][j]!=0)
                    return true;
            }
        }
        return false;
    }

    private static int clear(int sx, int sy) {      // 시작 위치에서부터 지울 수 있다면 지우는 메서드
        int value = disk[sy][sx];       // 인접칸과 비교하기 위해 저장
        int cnt = 0;        // 자신을 제외하고 삭제된 원소의 개수
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{sx, sy});
        disk[sy][sx] = 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];

            for (int k = 0; k < 4; k++) {
                int nx = (x + dx[k] + M) % M;       // M을 더한뒤 나머지연산하면 음수 처리 가능
                int ny = y + dy[k];

                if(isOut(nx, ny) || disk[ny][nx]!=value)
                    continue;

                q.add(new int[]{nx, ny});
                disk[ny][nx] = 0;
                cnt++;
            }
        }

        if(cnt==0)      // 인접하면서 같은 숫자가 없으면
            disk[sy][sx] = value;       //원상복구

        return cnt;       // 바뀐 개수 리턴
    }

    private static boolean isOut(int nx, int ny) {
        return nx < 0 || ny < 0 || nx > M - 1 || ny > N - 1;
    }

    private static void rotate(int x, int d, int k) {
        k %= M;     // k번 회전하면 동일한 상태가 되므로 공회전 제거
        if(d==1) {      // 반시계방향 회전을 시계 방향 회전으로 변환
            k = (M - k) % M;
        }

        int[] temp = new int[M];        // 회전결과가 저장될 배열
        for (int i = 0; i < M; i++) {        // 회전하고
            temp[i] = disk[x][(i+M-k)%M];
        }
        disk[x] = temp;     // 배열 갈아끼우기
    }
}