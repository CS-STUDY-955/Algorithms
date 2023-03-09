import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 사다리 조작
 * https://www.acmicpc.net/problem/15684
 *
 * 1. N-1*H 크기의 2차원 배열로 사다리를 저장한다.
 * 2. 전체 사다리 중 0, 1, 2, 3개를 뽑는 조합을 각각 수행한다.
 * 3. 사다리 조합이 완료되면 시뮬레이션하여 모두 자신의 칸으로 이동하는지 확인한다.
 * 4. 자신의 칸으로 이동하면 놓은 사다리 수를 리턴하고, 3개까지 리턴에 실패하면 -1을 리턴하여 출력한다.
 *
 * @author 배용현
 *
 */
public class BJ_15684_사다리조작 {

    static int N, M, H, answer;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static boolean[] ladder;        // 사다리가 존재하는지 정보를 담은 배열

    public static void main(String[] args) throws IOException {
        input();
        solution();
        System.out.println(answer);
    }

    private static void solution() {
        for (int i = 0; i < 4; i++) {       // 0~3개의 사다리를 추가한 경우 확인
            combination(i, 0, 0);
            if(answer!=-1)      // 정답을 찾았으면 탈출하고 정답 출력
                return;
        }
    }

    private static void combination(int maxDepth, int depth, int start) {       // 새로 놓을 사다리를 뽑는 조합 메서드
        if (depth == maxDepth) {        // 다 뽑았으면
            if(simulate())      // 조건을 만족하는지 확인하고
                answer = maxDepth;      // 만족하면 정답 갱신

            return;
        }

        for (int i = start; i < ladder.length; i++) {       // 사다리 조합
            if(ladder[i] || (i>0 && (i/(N-1)==i-1/(N-1)) && ladder[i-1]) || (i<N-1 && (i/(N-1)==i+1/(N-1)) && ladder[i+1]))     // 이미 놓아진 사다리거나 같은행의 앞뒤에 사다리가 존재하면 패스
                continue;

            ladder[i] = true;       // 사다리 놓고
            combination(maxDepth, depth + 1, i);        // 재귀 호출
            ladder[i] = false;
        }
    }

    private static boolean simulate() {     // 시작과 끝이 i로 같은지 확인하는 메서드
        for (int i = 0; i < N; i++) {       // 각 시작에 대해 확인
            if(fall(0, i)!=i)       // 떨어뜨렸을때 리턴값이 시작값과 다르면
                return false;       // 실패한 사다리 조합임
        }

        return true;        // 여기에 오면 성공
    }

    private static int fall(int depth, int colIdx) {        // 각 시작점에서 하나씩 떨어뜨려보는 재귀 메서드
        if (depth == H)       // 끝까지 떨어졌으면 현재 열값 리턴
            return colIdx;

        int idx = depth*(N-1) + colIdx;     // 현재 방문중인 위치

        if(colIdx>0 && ladder[idx-1])       // 왼쪽에 사다리가 있으면 왼쪽으로 떨어짐
            return fall(depth+1, colIdx-1);
        else if(colIdx<N-1 && ladder[idx])      // 오른쪽에 사다리가 있으면 오른쪽으로 떨어짐
            return fall(depth+1, colIdx+1);
        else        // 둘다 없으면 아래로 떨어짐
            return fall(depth+1, colIdx);
    }


    private static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        N = parseInt(st.nextToken());
        M = parseInt(st.nextToken());
        H = parseInt(st.nextToken());
        ladder = new boolean[(N-1)*H];       // 조합 탐색을 편하게 하기 위해 행과 열을 합쳐 일차원 배열로 변환

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            ladder[(parseInt(st.nextToken())-1) * (N-1) + parseInt(st.nextToken())-1] = true;
        }

        answer = -1;
    }
}