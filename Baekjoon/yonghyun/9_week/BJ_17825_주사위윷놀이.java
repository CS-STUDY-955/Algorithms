import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

/**
 * 주사위 윷놀이
 * https://www.acmicpc.net/problem/17825
 *
 * 1. 각 점수가 어떤 말에 배정될지 정하면 최종적으로 얻을 수 있는 점수를 구할 수 있으므로 이를 구하고, 최대 점수를 구한다.
 * 2. 점수가 말에 부여되도록 하는 것은 4개 중 하나를 열 번 뽑는 중복 순열이므로 2^20=약 1,000,000번의 연산을 한다.
 * 3. 각 경우의 수는 말을 움직여 점수를 구해야 하므로 최대 5칸을 10번 움직이는 50번의 연산을 한다. -> 배열로 한번에 접근하면 10번으로 줄일 수 있다.
 * 4. 최종적으로 약 50,000,000의 연산으로 아슬아슬하게 통과 가능할 것 같다.
 * - 맵은 노드 배열로 표현하되, 중복되는 값이 있으므로 안쪽 노드의 값을 다르게 저장하고, 계산할때 보정하여 계산한다.
 *
 * @author 배용현
 *
 */
public class BJ_17825_주사위윷놀이 {

    static class Node {
        int value;
        int nextIdx;
        int anotherNextIdx;
        boolean switchable;

        public Node(int value, int nextIdx, boolean switchable) {
            this.value = value;
            this.nextIdx = nextIdx;
            this.switchable = switchable;
        }

    }

    static int maxScore = 0;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static Node[] map;
    static int[] dice, selected;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        print();
    }

    private static void solution() {
        permutation(0);
    }

    private static void permutation(int depth) {
        if (depth == 10) {
            simulate();
            return;
        }

        for (int i = 0; i < 4; i++) {
            selected[depth] = i;
            permutation(depth + 1);
        }
    }

    private static void simulate() {
        int[] horseLocation = new int[4];
        boolean[] blocked = new boolean[4];

        int score = 0;
        for (int i = 0; i < 10; i++) {      // 주사위 눈만큼 선택된 말을 움직임
            int horse = selected[i];        // 현재 나온 수만큼 이동할 말
            if(blocked[horse] || horseLocation[horse]==32)
                continue;

            Node cur = map[horseLocation[horse]];
            int next;
            if(cur.switchable) {      // 현재 위치가 파란 동그라미면
                next = move(cur.anotherNextIdx, dice[i] - 1);       // 파란 화살표를 따라가고
            } else {        // 검은 동그라미면
                next = move(horseLocation[horse], dice[i]);       // 빨간 화살표를 따라가고
            }

            if (next!=32 && conflict(horseLocation, next)) {
                break;
            }

            horseLocation[horse] = next;
            score += map[horseLocation[horse]].value;       // 도착 못했으면 이동한 곳의 값을 더함
        }
        maxScore = Math.max(maxScore, score);
    }

    private static boolean conflict(int[] horseLocation, int next) {
        for (int i = 0; i < 4; i++) {
            if (horseLocation[i] == next) {
                return true;
            }
        }

        return false;
    }

    private static int move(int idx, int distance) {        // 맵을 따라가면서 최종 도착지의 인덱스 리턴
        if(distance==0 || idx==32)      // 말이 도착했거나 주어진 이동을 마쳤으면
            return idx;     // 현재 위치 리턴
        return move(map[idx].nextIdx, distance - 1);        // 덜 이동했으면 이동
    }

    private static void print() {
        System.out.println(maxScore);
    }

    private static void input() throws IOException {
        map = new Node[33];     // 0: 출발, 1~20: 바깥 노드, 21~31: 안쪽 노드, 32: 도착
        for (int i = 0; i < 21; i++) {
            map[i] = new Node(i*2, i+1, false);
        }

        for (int i = 5; i < 16; i+=5) {
            map[i].switchable = true;
        }
        map[5].anotherNextIdx = 21;
        map[10].anotherNextIdx = 24;
        map[15].anotherNextIdx = 26;
        map[20].nextIdx = 32;

        map[21] = new Node(13, 22, false);
        map[22] = new Node(16, 23, false);
        map[23] = new Node(19, 29, false);
        map[24] = new Node(22, 25, false);
        map[25] = new Node(24, 29, false);
        map[26] = new Node(28, 27, false);
        map[27] = new Node(27, 28, false);
        map[28] = new Node(26, 29, false);
        map[29] = new Node(25, 30, false);
        map[30] = new Node(30, 31, false);
        map[31] = new Node(35, 20, false);
        map[32] = new Node(0, -1, false);

        dice = new int[10];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 10; i++) {
            dice[i] = Integer.parseInt(st.nextToken());
        }

        selected = new int[10];
    }

}