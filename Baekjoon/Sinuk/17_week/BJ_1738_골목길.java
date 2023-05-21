import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

// 1. 단순히 사이클이 있다고 -1인 것은 아니다. 사이클을 거쳐서 도착지점에 도착할 수 있어야 한다.
// 2. 사이클을 찾아서 사이클의 요소들을 저장한다.
// 3. 사이클 정점으로 이동할 수 있어야 한다(cost가 INF가 아님)
// 4. 구한 최단 경로중에서 사이클 요소에서 도달할 수 있는 정점을 찾는다.
public class BJ_1738_골목길 {
  private static class Edge {
    int from;
    int to;
    int cost;

    public Edge(int from, int to, int cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(st.nextToken());
    int m = Integer.parseInt(st.nextToken());
    ArrayList<Edge> adjList = new ArrayList<>();
    for (int i = 0; i < m; i++) {
      st = new StringTokenizer(br.readLine());
      int u = Integer.parseInt(st.nextToken());
      int v = Integer.parseInt(st.nextToken());
      int w = Integer.parseInt(st.nextToken());
      adjList.add(new Edge(u, v, -w));
    }

    int[] pre = new int[n + 1];
    int[] costs = new int[n + 1];
    for (int i = 2; i <= n; i++) {
      costs[i] = Integer.MAX_VALUE;
    }

    // 벨만 포드 시작
    for (int i = 1; i <= n; i++) {
      for (Edge e : adjList) {
        if (costs[e.from] == Integer.MAX_VALUE) {
          continue;
        }
        if (costs[e.from] + e.cost < costs[e.to]) {
          costs[e.to] = costs[e.from] + e.cost;
          pre[e.to] = e.from;
        }
      }
    }

    // 음수 사이클 확인
    Set<Integer> cycle = new HashSet<>();
    for (Edge e : adjList) {
      if (costs[e.from] != Integer.MAX_VALUE && costs[e.from] + e.cost < costs[e.to]) {
        cycle.add(e.from);
        cycle.add(e.to);
      }
    }
    // 도달하지 못한다면 -1 출력
    if (costs[n] == Integer.MAX_VALUE) {
      System.out.println(-1);
      return;
    }

    // 패스 역으로 추적하기
    ArrayList<Integer> path = new ArrayList<>();
    Set<Integer> pathElements = new HashSet<>();
    int idx = n;
    path.add(n);
    pathElements.add(n);
    while (idx != 0) {
      if (cycle.contains(idx)) {
        System.out.println(-1);
        return;
      }
      path.add(pre[idx]);
      pathElements.add(pre[idx]);
      idx = pre[idx];
    }

    // 이동 가능한 사이클 요소에서 도달할 수 있는 경우인지 체크
    for (Edge e : adjList) {
      if (cycle.contains(e.from) && costs[e.from] != Integer.MAX_VALUE && pathElements.contains(e.to)) {
        System.out.println(-1);
        System.exit(0);
      }
    }

    // 역순으로 출력하기
    StringBuilder sb = new StringBuilder();
    for (int i = path.size() - 2; i >= 0; i--) {
      sb.append(path.get(i) + " ");
    }
    System.out.println(sb.toString());
  }
}
