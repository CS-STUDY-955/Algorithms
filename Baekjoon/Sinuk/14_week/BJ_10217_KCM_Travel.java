import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_10217_KCM_Travel {
    public static class Edge implements Comparable<Edge> {
        int to;
        int cost;
        int time;

        public Edge(int to, int cost, int time) {
            this.to = to;
            this.cost = cost;
            this.time = time;
        }

        @Override
        public int compareTo(Edge o) {
            if (this.time == o.time) {
                return this.cost - o.cost;
            }
            return this.time - o.time;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());
            @SuppressWarnings("unchecked")
            List<Edge>[] adjList = new ArrayList[n + 1];
            for (int i = 0; i <= n; i++) {
                adjList[i] = new ArrayList<Edge>();
            }

            for (int i = 0; i < k; i++) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                int cost = Integer.parseInt(st.nextToken());
                int time = Integer.parseInt(st.nextToken());
                adjList[from].add(new Edge(to, cost, time));
            }

            // 다익스트라 준비
            boolean arrived = false;
            int minTime = Integer.MAX_VALUE;
            // 얼마의 비용으로 방문했는지 기록
            boolean[][] visited = new boolean[n + 1][m + 1];
            // 얼마의 비용으로 얼마의 시간이 걸리는지 기록
            int[][] times = new int[n + 1][m + 1];
            for (int i = 1; i <= n; i++) {
                for (int j = 0; j <= m; j++) {
                    times[i][j] = 10_000_000;
                }
            }
            PriorityQueue<Edge> pq = new PriorityQueue<>();
            pq.add(new Edge(1, 0, 0));
            times[1][0] = 0;

            while (!pq.isEmpty()) {
                Edge cur = pq.poll();
                // 어떻게든 가장 빠르게 도착한 방식이 최소비용이니 기록하고 탈충
                if (cur.to == n) {
                    arrived = true;
                    minTime = cur.time;
                    break;
                }
                // 이미 방문한 방식이라면 스킵
                if (visited[cur.to][cur.cost]) {
                    continue;
                }
                visited[cur.to][cur.cost] = true;
                // 현재 처리중인 곳과 연결된 곳들에 대해 
                for (Edge e : adjList[cur.to]) {
                    int totalCost = cur.cost + e.cost;
                    // 비용 초과하면 패스
                    if (totalCost > m) {
                        continue;
                    }
                    // 시간 단축되면 업데이트
                    if (cur.time + e.time < times[e.to][totalCost]) {
                        times[e.to][totalCost] = cur.time + e.time;
                        pq.add(new Edge(e.to, totalCost, cur.time + e.time));
                    }
                }
            }

            // 출력
            if (arrived) {
                sb.append(minTime + "\n");
            } else {
                sb.append("Poor KCM\n");
            }
        }
        System.out.println(sb.toString());
    }
}