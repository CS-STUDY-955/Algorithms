import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

/**
 * 경사로
 * https://www.acmicpc.net/problem/14890
 *
 * 1. 높이가 높아지는 경우는 큐를 이용해 확인한다.
 *  - 이전 칸의 높이를 큐에 넣어놓는다.
 *  - 큐의 수보다 높은 높이의 칸이 나왔을 때 두 칸이상 높으면 지나갈 수 없는 길이다.
 *  - 한칸 높을 경우, 큐의 사이즈가 L이상이면 가능하므로 큐를 갱신하고 다음으로 진행한다.
 * 2. 높이가 낮아지는 경우는 메서드를 정의해 확인한다.
 *  - 큐의 수보다 낮은 높이의 칸이 나왔을 때 두 칸이상 낮으면 지나갈 수 없는 길이다.
 *  - 한칸 낮을 경우, 현재 칸을 포함해 L개의 칸이 존재하는지, 존재한다면 모두 한칸 낮은지 확인한다.
 *  - 위의 조건을 만족한다면 큐를 갱신하고 다음으로 진행한다.
 * 3. 마지막 원소까지 진행했다면 지나갈 수 있는길이므로 정답의 개수에 포함시킨다.
 *
 * - 큐 안쓰고 높이랑 사이즈로 체크할 수 있다.
 *
 * @author 배용현
 *
 */
public class BJ_14890_경사로 {

    static int N, L, answer;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] map;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        System.out.print(answer);
    }

    private static void solution() {
        for (int i = 0; i < N; i++) {
            answer += checkRow(i) ? 1 : 0;      // 행 가능하면 +1
            answer += checkCol(i) ? 1 : 0;      // 열 가능하면 +1
        }
    }

    private static boolean checkRow(int idx) {      // 지나갈 수 있는 행인지 검사하는 메서드
        int prev = map[idx][0];      // 이전 칸의 높이
        int cnt = 1;        // 이전 칸이 반복된 수

        for (int i = 1; i < N; i++) {
            int cur = map[idx][i];      // 현재 높이

            if(prev==cur-1) {       // 높이가 한칸 높아졌을때
                if(cnt<L)       // 이전에 경사로 못놓으면
                    return false;       // 지나갈 수 없는 길임

                prev = cur;     // 경사로 놓으면 상태 업데이트
                cnt = 1;
            } else if(prev==cur+1) {        // 높이가 한칸 낮아졌을때
                if(!isRowPlaceable(prev, idx, i))      // 뒤에 경사로 못놓으면
                    return false;       // 지나갈 수 없는 길임

                prev = cur;      // 경사로 놓으면 상태 업데이트
                cnt = 0;
                i += L-1;
            } else if(prev==cur) {        // 높이 차이가 없으면
                cnt++;      // 이전 칸 수만 증가
            } else {        // 높이 차이가 2이상이면
                return false;       // 지나갈 수 없는 길임
            }
        }

        return true;        // 마지막 인덱스까지 통과하면 지나갈 수 있는 길임
    }

    private static boolean checkCol(int idx) {      // 지나갈 수 있는 열인지 검사하는 메서드
        int prev = map[0][idx];      // 이전 칸의 높이
        int cnt = 1;        // 이전 칸이 반복된 수

        for (int i = 1; i < N; i++) {
            int cur = map[i][idx];      // 현재 높이

            if(prev==cur-1) {       // 높이가 한칸 높아졌을때
                if(cnt<L)       // 이전에 경사로 못놓으면
                    return false;       // 지나갈 수 없는 길임

                prev = cur;     // 경사로 놓으면 상태 업데이트
                cnt = 1;
            } else if(prev==cur+1) {        // 높이가 한칸 낮아졌을때
                if(!isColPlaceable(prev, i, idx))      // 뒤에 경사로 못놓으면
                    return false;       // 지나갈 수 없는 길임

                prev = cur;      // 경사로 놓으면 상태 업데이트
                cnt = 0;
                i += L-1;
            } else if(prev==cur) {        // 높이 차이가 없으면
                cnt++;      // 이전 칸 수만 증가
            } else {        // 높이 차이가 2이상이면
                return false;       // 지나갈 수 없는 길임
            }
        }

        return true;        // 마지막 인덱스까지 통과하면 지나갈 수 있는 길임
    }

    private static boolean isRowPlaceable(int prev, int row, int col) {        // 행 검사중 높은곳 이후 칸의 조건을 확인하는 메서드
        if(col+L>N)     // 이후의 칸 수가 충분하지 않으면 경사로 못 놓음
            return false;

        for (int i = col; i < col+L; i++) {     // col부터 L만큼은
            if(map[row][i]!=prev-1) {       // 반드시 값이 prev-1이어야 함
                return false;       // 아니면 경사로 못 놓음
            }
        }

        return true;        // 두 조건을 통과하면 경사로 놓을 수 있음
    }

    private static boolean isColPlaceable(int prev, int row, int col) {        // 열 검사중 높은곳 이후 칸의 조건을 확인하는 메서드
        if(row+L>N)     // 이후의 칸 수가 충분하지 않으면 경사로 못 놓음
            return false;

        for (int i = row; i < row+L; i++) {     // row부터 L만큼은
            if(map[i][col]!=prev-1) {       // 반드시 값이 prev-1이어야 함
                return false;       // 아니면 경사로 못 놓음
            }
        }

        return true;        // 두 조건을 통과하면 경사로 놓을 수 있음
    }

    private static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        N = parseInt(st.nextToken());
        L = parseInt(st.nextToken());

        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++)
                map[i][j] = parseInt(st.nextToken());
        }

        answer = 0;
    }
}