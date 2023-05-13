import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

/**
 * 웜홀
 * https://www.acmicpc.net/problem/1865
 *
 * 1. 도로의 정보는 양의 가중치를 가지는 간선, 웜홀의 정보는 음의 가중치를 가지는 간선이라고 생각하면 된다.
 * 2. 따라서 플로이드 워셜이나 벨만 포드를 사용하여 구할 수 있다.
 *
 * @author 배용현
 *
 */
public class BJ_1865_웜홀 {

    static class Edge {
        int start, end, weight;

        public Edge(int start, int end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }

    }

    static int T, N, M, W;
    static ArrayList<Edge> edges;
    static int[] dist;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    public static void main(String[] args) throws Exception {
        input();
        for (int tc = 0; tc < T; tc++) {

            sb.append();
        }
        System.out.print(sb);
    }

    private static void solution() {
    }

    private static void input() throws IOException {
        T = parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        N = parseInt(br.readLine());
        M = parseInt(br.readLine());
        W = parseInt(br.readLine());

        dist = new int[N];
        edges = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            edges.add(new Edge(parseInt(st.nextToken()), parseInt(st.nextToken()), parseInt(st.nextToken())));
        }
        for (int i = 0; i < W; i++) {
            st = new StringTokenizer(br.readLine());
            edges.add(new Edge(parseInt(st.nextToken()), parseInt(st.nextToken()), -parseInt(st.nextToken())));
        }

    }
}
