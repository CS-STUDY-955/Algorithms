import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_1162_도로포장 {
    private static class Vertex implements Comparable<Vertex> {
        int v;
        int paved;
        long cost;

        public Vertex(int v, int paved, long cost) {
            this.v = v;
            this.paved = paved;
            this.cost = cost;
        }

        // cost가 long 타입이므로, compareTo가 일반적인 방식으론 정상작동 하지 않는다
        @Override
        public int compareTo(Vertex o) {
            if (this.cost > o.cost) {
                return 1;
            } else if (this.cost == o.cost) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        ArrayList<ArrayList<Vertex>> adList = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adList.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());
            // 양방향 리스트이므로, 양쪽에 대해 설정
            adList.get(from).add(new Vertex(to, 0, cost));
            adList.get(to).add(new Vertex(from, 0, cost));
        }

        // 다익스트라 준비
        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(new Vertex(1, 0, 0));
        // 현재 지점까지의 비용/방문처리를 포장한 도로수와 함께 기록
        long[][] cost = new long[k + 1][n + 1];
        boolean[][] visited = new boolean[k + 1][n + 1];
        for (int i = 0; i <= k; i++) {
            for (int j = 0; j <= n; j++) {
                cost[i][j] = Long.MAX_VALUE;
            }
        }
        // 1번 인덱스에서 0번 도로를 포장한 상태로 시작
        cost[0][1] = 0;

        while (!pq.isEmpty()) {
            Vertex cur = pq.poll();
            // 만약 이미 처리된 곳이라면 패스
            if (visited[cur.paved][cur.v]) {
                continue;
            }
            visited[cur.paved][cur.v] = true;
            // 가장 먼저 도착한 방법이 다익스트라의 특성상 최소의 비용으로 간 것이므로 여기서 탈출
            if (cur.v == n) {
                System.out.println(cur.cost);
                return;
            }
            // 현재 조사중인 노드와 연결된 곳들에 대해 
            for (Vertex next : adList.get(cur.v)) {
                // 포장을 하지 않고 이동
                if (!visited[cur.paved][next.v] && cur.cost + next.cost < cost[cur.paved][next.v]) {
                    cost[cur.paved][next.v] = cur.cost + next.cost;
                    pq.add(new Vertex(next.v, cur.paved, cur.cost + next.cost));
                }
                // 포장을 하여 이동
                if (cur.paved < k && !visited[cur.paved + 1][next.v] && cur.cost < cost[cur.paved + 1][next.v]) {
                    cost[cur.paved + 1][next.v] = cur.cost;
                    pq.add(new Vertex(next.v, cur.paved + 1, cur.cost));
                }
            }
        }
    }
}
