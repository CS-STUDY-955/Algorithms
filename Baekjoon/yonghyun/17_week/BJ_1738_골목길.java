import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 골목길
 * https://www.acmicpc.net/problem/1738
 *
 * 1. 최소 비용을 찾는 벨만 포드 알고리즘을 최대 비용을 찾도록 변경하면 된다.
 * 2. 최적의 경로가 존재하지 않는 경우는 양의 사이클이 존재하는 경우이다.
 * 3. 최적의 경로가 존재한다면 그 경로를 출력해야 하므로 사이클만 확인하는 것이 아닌 실제로 최적 경로를 찾아야 하고, 이는 이전 노드를 저장하면서 해결할 수 있다.
 *
 * @author 배용현
 *
 */
public class BJ_1738_골목길 {

    static class Edge {
        int start, end, weight;

        public Edge(int start, int end, int weight) {
            this.start = start;
            this.end = end;
            this.weight = weight;
        }
    }

    static int n, m;
    static int[] dp, from;
    static Edge[] edges;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    public static void main(String[] args) throws Exception {
        input();
        bellmanFord();
        List<Integer> cycledNodes = getCycledNodes();

        if (dp[n] == Integer.MIN_VALUE) {
            System.out.println(-1);
            return;
        }

        if (cycledNodes.size() == 0 || !cycleContainsDest(cycledNodes)) {      // 양의 사이클이 존재하지 않거나 존재해도 그 사이클이 목적지를 포함하지 않는다면 성공
            int idx = n;

            sb.append(n);
            while (from[idx] != 0) {
                sb.insert(0, from[idx] + " ");
                idx = from[idx];
            }

            System.out.print(sb);
        } else {        // 양의 사이클이 존재함
            System.out.print(-1);
        }
    }

    private static boolean cycleContainsDest(List<Integer> cycledNodes) {       // bfs를 이용하여 도착지점 n이 사이클에 포함되는지 탐색
        boolean[] visited = new boolean[n + 1];     // 사이클을 구성하는 노드

        for (int cycledNode : cycledNodes) {        // 사이클을 구성하는 노드를 모두 탐색
            if (visited[cycledNode]) {      // 이미 방문된 노드면 패스
                continue;
            }

            Queue<Integer> q = new LinkedList<>();
            visited[cycledNode] = true;
            q.add(cycledNode);

            while (!q.isEmpty()) {
                int curNode = q.poll();     // 사이클을 구성하는 노드마다

                for (Edge edge : edges) {       // 모든 간선을 탐색하여
                    if (!visited[edge.end] && curNode == edge.start) {        // 현재 노드와 연결된 노드를 사이클 노드로 변경
                        visited[edge.end] = true;
                        q.add(edge.end);
                    }
                }
            }
        }

        return visited[n];      // n이 사이클을 구성하는 노드인지 리턴
    }

    private static List<Integer> getCycledNodes() {     // 사이클에 포함된 노드를 찾는 메서드
        ArrayList<Integer> cycledNodes = new ArrayList<>();

        for (Edge edge : edges) {
            if (dp[edge.start] != Integer.MIN_VALUE && dp[edge.end] < dp[edge.start] + edge.weight) {
                cycledNodes.add(edge.end);
            }
        }

        return cycledNodes;
    }

    private static void bellmanFord() {
        for (int i = 0; i < n - 1; i++) {
            for (Edge edge : edges) {
                if (dp[edge.start] != Integer.MIN_VALUE && dp[edge.end] < dp[edge.start] + edge.weight) {
                    dp[edge.end] = dp[edge.start] + edge.weight;
                    from[edge.end] = edge.start;
                }
            }
        }
    }

    private static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        n = parseInt(st.nextToken());
        m = parseInt(st.nextToken());

        edges = new Edge[m];
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int start = parseInt(st.nextToken());
            int end = parseInt(st.nextToken());
            int weight = parseInt(st.nextToken());
            edges[i] = new Edge(start, end, weight);
        }

        dp = new int[n + 1];
        from = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            dp[i] = Integer.MIN_VALUE;
        }

        dp[1] = 0;

    }
}