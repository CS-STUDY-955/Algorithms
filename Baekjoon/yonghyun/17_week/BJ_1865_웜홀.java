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
 * 2. 한 지점에서 출발하여 다른 지점에 도착했을때 시간이 되돌아가 있는 경우 = 음의 사이클이 있는 경우이므로 벨만 포드 알고리즘을 사용할 수 있다.
 * 3. 벨만 포드 알고리즘은 n번째 연산을 추가로 수행하여 변화가 있으면 음의 사이클이 존재함을 확인할 수 있다.
 * -------------------------------------------------------------
 * - 시작 지점을 1로 놓았기 때문에 모든 노드가 연결되지 않았을시 정상적으로 수행되지 않는다.
 * - 이 문제는 음의 사이클 존재 여부만 구하면 되고 최단 거리를 구할 필요가 없으므로 무한으로 초기화하지 않고 간선이 음수로 갱신되는지만 확인하면 된다.
 *
 * @author 배용현
 *
 */
public class BJ_1865_웜홀 {

    static class Edge {
        int end, weight;

        public Edge(int end, int weight) {
            this.end = end;
            this.weight = weight;
        }

    }

    static int T, N, M, W;
    static final int INF = 987654321;
    static ArrayList<ArrayList<Edge>> edges;
    static int[] dist;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    public static void main(String[] args) throws Exception {
        T = parseInt(br.readLine());
        for (int tc = 0; tc < T; tc++) {
            input();
            sb.append(hasMinusCycle() ? "YES" : "NO").append('\n');
        }
        System.out.print(sb);
    }

    private static boolean hasMinusCycle() {
        for (int i = 0; i < N - 1; i++) {       // 모든 엣지 탐색을 N-1번 반복
            for (int j = 1; j <= N; j++) {        // 모든 노드 탐색
                for (Edge edge : edges.get(j)) {        // 각 노드의 엣지 탐색
                    if (dist[edge.end] > dist[j] + edge.weight) {       // 새로운 경로가 더 짧은 경로면
                        dist[edge.end] = dist[j] + edge.weight;     // 최단 경로 갱신
                    }
                }
            }
        }

        for (int j = 1; j <= N; j++) {        // 마지막으로 모든 노드 탐색
            for (Edge edge : edges.get(j)) {        // 각 노드의 엣지 탐색
                if (dist[edge.end] > dist[j] + edge.weight) {       // 경로가 또 갱신된다면
                    return true;       // 음의 사이클이 존재
                }
            }
        }

        return false;
    }

    private static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        N = parseInt(st.nextToken());
        M = parseInt(st.nextToken());
        W = parseInt(st.nextToken());

        dist = new int[N + 1];
        edges = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < M; i++) {       // 도로 입력
            st = new StringTokenizer(br.readLine());
            int start = parseInt(st.nextToken());
            int end = parseInt(st.nextToken());
            int weight = parseInt(st.nextToken());
            edges.get(start).add(new Edge(end, weight));
            edges.get(end).add(new Edge(start, weight));
        }
        for (int i = 0; i < W; i++) {       // 웜홀 입력
            st = new StringTokenizer(br.readLine());
            edges.get(parseInt(st.nextToken())).add(new Edge(parseInt(st.nextToken()), -parseInt(st.nextToken())));
        }

    }
}
