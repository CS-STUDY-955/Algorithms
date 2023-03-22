import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

/**
 * 주사위 윷놀이
 * https://www.acmicpc.net/problem/17825
 *
 * 1. 
 *
 * @author 배용현
 *
 */
public class BJ_17825_주사위윷놀이 {

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

    }
}