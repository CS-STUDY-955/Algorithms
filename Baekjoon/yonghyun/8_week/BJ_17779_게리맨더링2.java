import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

/**
 * 게리맨더링 2
 * https://www.acmicpc.net/problem/17779
 *
 * 1. x, y, d1, d2를 정해서 모든 경우의 수에 대해 각 구역의 인구수를 계산, 최솟값을 저장하면 된다.
 * 2. 칸을 정하는 경우의 수 20*20=400, d1과 d2를 정하는 경우의 수 20*20=400으로 총 경우의 수는 160,000개이다.
 * 3. 모든 값을 정했으면 완전탐색을 수행해서 각 구역의 인구수를 구할 때 역시 400번의 연산이 필요하다.
 * 4. 총 연산량 64,000,000으로 아슬아슬하게 될 것 같다.
 *
 * - x를 행, y를 열로 정의했으므로 주의한다.
 * - 네 변수를 중복순열로 선택하되 x, y의 범위는 0~N-1까지이고 d1, d2의 범위는 1~N-1까지로 다르므로 주의한다.
 *
 * @author 배용현
 *
 */
public class BJ_17779_게리맨더링2 {

    static int N, totalPopulation = 0, answer = Integer.MAX_VALUE;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[] selected;      // 0: x, 1: y, 2: d1, 3: d2
    static int[][] map;
    static boolean[][] border;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        print();
    }

    private static void solution() {
        rePermutation(0);
    }

    private static void rePermutation(int depth) {      // 중복 순열
        if(depth==4) {      // x, y, d1, d2 다 뽑았으면
            if(isValid())       // 정상적인 마름모가 만들어지는지 확인하고
                answer = Math.min(answer, divideAndCount());        // 만들어지면 각 구역의 인구를 계산하고 answer 갱신

            return;
        }

        for (int i = (depth<=1 ? 0 : 1); i <= N-1; i++) {       // x, y는 0부터, d1, d2는 1부터
            selected[depth] = i;
            rePermutation(depth + 1);
        }
    }

    private static boolean isValid() {      // 마름모가 정상적으로 만들어지는지 확인
        int x = selected[0];
        int y = selected[1];
        int d1 = selected[2];
        int d2 = selected[3];

        return x + d1 + d2 < N &&
                y - d1 > 0 &&
                y + d2 < N;
    }

    private static int divideAndCount() {       // 구역을 나누고 인구를 계산
        int x = selected[0];
        int y = selected[1];
        int d1 = selected[2];
        int d2 = selected[3];
        int[] population = new int[5];      // 총 5구역이 존재함

        setBorder();        // 마름모 설정

        for (int i = 0; i < x + d1; i++) {      // 1구역
            for (int j = 0; j <= y; j++) {
                if(border[i][j])
                    break;

                population[0] += map[i][j];
            }
        }

        for (int i = 0; i <= x + d2; i++) {     // 2구역
            for (int j = N-1; j > y ; j--) {
                if(border[i][j])
                    break;

                population[1] += map[i][j];
            }
        }

        for (int i = x + d1; i <= N-1; i++) {       // 3구역
            for (int j = 0; j < y - d1 + d2; j++) {
                if(border[i][j])
                    break;

                population[2] += map[i][j];
            }
        }

        for (int i = x + d2 + 1; i <= N-1; i++) {       // 4구역
            for (int j = N-1; j >= y - d1 + d2; j--) {
                if(border[i][j])
                    break;

                population[3] += map[i][j];
            }
        }

        population[4] = totalPopulation;        // 5구역은 전체 인구에서 1~4구역의 인구를 빼면 계산 가능
        for (int i = 0; i < 4; i++)
            population[4] -= population[i];

        int min = Integer.MAX_VALUE;        // 최소 인구
        int max = Integer.MIN_VALUE;        // 최대 인구

        for (int i = 0; i < 5; i++) {
            min = Math.min(min, population[i]);
            max = Math.max(max, population[i]);
        }

        return max - min;
    }

    private static void setBorder() {       // 마름모(구역 구분선) 설정
        int x = selected[0];
        int y = selected[1];
        int d1 = selected[2];
        int d2 = selected[3];
        border = new boolean[N][N];

        for (int i = 0; i <= d1; i++)
            border[x+i][y-i] = border[x+d2+i][y+d2-i] = true;

        for (int i = 0; i <= d2; i++)
            border[x+i][y+i] = border[x+d1+i][y-d1+i] = true;
    }

    private static void input() throws IOException {
        N = parseInt(br.readLine());

        selected = new int[4];
        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = parseInt(st.nextToken());
                totalPopulation += map[i][j];
            }
        }
    }

    private static void print() {
        System.out.println(answer);
    }

}