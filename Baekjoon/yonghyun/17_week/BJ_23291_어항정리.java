import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 어항 정리
 * https://www.acmicpc.net/problem/23291
 *
 * 1. 물고기가 가장 적은 어항에 물고기를 넣는 메서드
 *  - 미리 저장해둔 가장 적은 물고기의 수를 가진 어항들에 물고기를 1마리 추가한다.
 * 2. 어항을 공중부양 시키는 메서드
 *  - 공중부양 시킬 행과 열의 개수는 공중부양이 일어날 때마다 번갈아가며 1씩 증가한다.
 *  - (현재 열 인덱스 + 공중부양 시킬 행 수 + 공중부양 시킬 열 수 - 1)이 N이상이면 탈출한다.
 *  - 열을 순서대로 접근하여 행이 큰 순서대로 큐에 넣고 0으로 만든다.
 *  - (N - 공중부양 시킬 열 수 - 1)행 ~ (N-2)행까지 (현재 열 인덱스 + 공중부양 시킬 열 수)열 ~ (현재 열 인덱스 + 공중부양 시킬 행 수 + 공중부양 시킬 열 수 - 1)열을 순회하며 큐에 넣은 수를 입력한다.
 *  - 현재 열 인덱스에 공중부양 시킬 열 수를 더하여 열의 시작을 가리키도록 한다.
 * 3. 물고기 수를 조절하는 메서드
 *  - 이동한 물고기 수를 저장할 맵을 만든다.
 *  - 현재 맵을 기준으로 행과 열을 순회하며 오른쪽과 아래쪽 칸을 비교한다.
 *  - 차이를 5로 나눈 몫이 1이상일 경우, 해당 몫만큼 물고기의 이동량을 저장한다.
 *  - 모든 칸에 대해 이동량 조사를 마쳤다면 물고기의 이동을 수행한다.
 *  - 이 때 가장 많은 물고기 수와 가장 적은 물고기의 수를 저장해둔다.
 * 4. 어항을 바닥에 내려놓는 메서드
 *  - 현재 열 인덱스부터 행이 1인 열이 나올 때까지 순회하며 행이 큰 순서대로 큐에 넣고 0으로 만든다.
 *  - 큐에서 수를 꺼내 0열부터 채운다.
 * 5. 공중부양 메서드를 180도로 변경하여 2번 더 수행한다.
 *  - 현재 열 인덱스를 0으로, 공중부양 시킬 행 수를 1로, 공중부양 시킬 열 수를 N/2로 설정하여 1번 수행한다.
 *  - 현재 열 인덱스를 N/2+1로, 공중부양 시킬 행 수를 2로, 공중부양 시킬 열 수를 N/4로 설정하여 1번 수행한다.
 * 6. 물고기 수 조절 메서드를 다시 수행한다.
 * 7. 가장 많은 물고기 수와 가장 적은 물고기 수의 차이가 K이하가 될 때까지 1~6번을 반복하고, 그 횟수를 출력한다.
 *
 * @author 배용현
 *
 */
public class BJ_23291_어항정리 {

    static int N, K, max, min, curColIdx = 0;
    static int[][] map;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    public static void main(String[] args) throws Exception {
        int answer = 0;
        input();

        while (max > min + K) {
            curColIdx = 0;
            addFish();
            firstLevitation();
            adjust();
            putDown();
            secondLevitationSetting();
            adjust();
            putDown();
            answer++;
        }

        System.out.print(answer);
    }

    private static void secondLevitationSetting() {     // 공중 부양 메서드를 두번째 단계에서 실행하기 위해 변수를 세팅하는 메서드
        curColIdx = 0;
        secondLevitation(1, N/2);       // 현재 열 인덱스를 0으로, 공중부양 시킬 행 수를 1로, 공중부양 시킬 열 수를 N/2로 설정하여 1번 수행
        curColIdx = N / 2;
        secondLevitation(2, N/4);       // 현재 열 인덱스를 N/2로, 공중부양 시킬 행 수를 2로, 공중부양 시킬 열 수를 N/4로 설정하여 1번 수행
    }

    private static void secondLevitation(int leviRow, int leviCol) {      // 어항을 두번째 공중 부양 시키는 메서드
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for (int j = N - leviRow; j < N; j++) {       // 행을 순서대로 접근
            for (int i = curColIdx; i < curColIdx + leviCol; i++) {     // 열을 순서대로 접근
                if (map[j][i] == 0) {       // 빈 칸이면 다음 열로 이동
                    break;
                }

                stack.addFirst(map[j][i]);       // 순서대로 스택에 삽입
                map[j][i] = 0;
            }
        }

        for (int i = N - leviRow * 2; i < N - leviRow; i++) {     // (N - 공중부양 시킬 행 수 * 2)행 ~ (N - 공중부양 시킬 행 수)행까지
            for (int j = curColIdx + leviCol; j < N; j++) {     // (현재 열 인덱스)열 ~ (N)열을 순회하며
                map[i][j] = stack.pollFirst();       // 스택에 넣은 수를 차례로 입력
            }
        }
    }

    private static void putDown() {     // 어항을 바닥에 내려놓는 메서드
        Queue<Integer> q = new ArrayDeque<>();
        for (int i = curColIdx; i < N; i++) {     // 현재 열 인덱스부터 행 수가 1인 열이 나올 때까지 순회
            for (int j = N - 1; j >= 0; j--) {       // 행을 역순으로 접근
                if (map[j][i] == 0) {       // 빈 칸이면 다음 열로 이동
                    if (j == N - 2) {       // 행 수가 1인 열이면
                        i = N;      // i를 N으로 바꿔 열 순회 종료
                    }

                    break;
                }

                q.add(map[j][i]);       // 빈칸이 아니면 큐에 삽입하고
                map[j][i] = 0;      // 해당 칸은 초기화
            }
        }

        int idx = 0;
        while (!q.isEmpty()) {      // 큐에서 수를 꺼내 0열부터 채운다.
            map[N - 1][idx++] = q.poll();
        }
    }

    private static void adjust() {
        int[][] temp = new int[N][N];       // 이동한 물고기 수를 저장할 맵

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 0) {       // 현재 칸이 어항이 존재하지 않는 칸이면 패스
                    continue;
                }

                if (i != N - 1 && map[i + 1][j] != 0) {       // 아래쪽 칸이 존재할 때
                    int moveNum = Math.abs(map[i][j] - map[i + 1][j]) / 5;      // 두 칸의 물고기 수 차이 / 5
                    if (moveNum != 0) {     // 차이가 5 미만이면 패스
                        if (map[i][j] > map[i + 1][j]) {        // 현재 칸이 크면
                            temp[i][j] -= moveNum;
                            temp[i+1][j] += moveNum;
                        } else {        // 아래 칸이 크면
                            temp[i][j] += moveNum;
                            temp[i+1][j] -= moveNum;
                        }
                    }
                }

                if (j != N - 1 && map[i][j + 1] != 0) {       // 오른쪽 칸이 존재할 때
                    int moveNum = Math.abs(map[i][j] - map[i][j + 1]) / 5;      // 두 칸의 물고기 수 차이 / 5
                    if (moveNum != 0) {     // 차이가 5 미만이면 패스
                        if (map[i][j] > map[i][j + 1]) {        // 현재 칸이 크면
                            temp[i][j] -= moveNum;
                            temp[i][j + 1] += moveNum;
                        } else {        // 오른쪽 칸이 크면
                            temp[i][j] += moveNum;
                            temp[i][j + 1] -= moveNum;
                        }
                    }
                }
            }
        }

        setMaxAndMin();     // max와 min을 구하기 위해 초기화
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 0) {       // 현재 칸이 어항이 존재하지 않는 칸이면 패스
                    continue;
                }

                map[i][j] += temp[i][j];
                max = Math.max(max, map[i][j]);
                min = Math.min(min, map[i][j]);
            }
        }
    }

    private static void firstLevitation() {      // 어항을 첫번째 공중 부양 시키는 메서드
        int leviRow = 1;        // 공중부양 시킬 열 수
        int leviCol = 1;        // 공중부양 시킬 행 수
        boolean rowUpTurn = true;       // 행 수를 올릴 차례인지 확인하는 변수
        Queue<Integer> q = new ArrayDeque<>();

        while (curColIdx + leviRow + leviCol - 1 < N) {     // 공중 부양시킨 어항의 바닥에 어항이 없을 경우 (마지막 열이 맵의 범위를 벗어나게 될 경우) 탈출
            for (int i = curColIdx; i < curColIdx + leviCol; i++) {     // 열을 순서대로 접근
                for (int j = N - 1; j >= 0; j--) {       // 행을 역순으로 접근
                    if (map[j][i] == 0) {       // 빈 칸이면 다음 열로 이동
                        break;
                    }

                    q.add(map[j][i]);       // 순서대로 큐에 삽입
                    map[j][i] = 0;
                }
            }

            for (int i = N - leviCol - 1; i < N - 1; i++) {     // (N - 공중부양 시킬 열 수 - 1)행 ~ (N-2)행까지
                for (int j = curColIdx + leviCol; j < curColIdx + leviRow + leviCol; j++) {     // (현재 열 인덱스 + 공중부양 시킬 열 수)열 ~ (현재 열 인덱스 + 공중부양 시킬 행 수 + 공중부양 시킬 열 수 - 1)열을 순회하며
                    map[i][j] = q.poll();       // 큐에 넣은 수를 차례로 입력
                }
            }

            curColIdx += leviCol;      // 현재 열 인덱스에 공중부양 시킬 열 수를 더한다.
            if (rowUpTurn) {        // 행 수를 올릴 차례
                rowUpTurn = false;
                leviRow++;
            } else {        // 열 수를 올릴 차례
                rowUpTurn = true;
                leviCol++;
            }
        }
    }

    private static void addFish() {     // 어항 중 물고기의 수가 가장 적은 경우 + 1
        for (int i = 0; i < N; i++) {
            if (map[N - 1][i] == min) {
                map[N - 1][i]++;
            }
        }
    }

    private static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        N = parseInt(st.nextToken());
        K = parseInt(st.nextToken());

        map = new int[N][N];
        setMaxAndMin();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            map[N - 1][i] = parseInt(st.nextToken());
            max = Math.max(max, map[N - 1][i]);
            min = Math.min(min, map[N - 1][i]);
        }
    }

    private static void setMaxAndMin() {
        max = 0;
        min = 10001;
    }

}
