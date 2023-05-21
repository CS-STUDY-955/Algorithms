import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BJ_1865_웜홀 {
  private static class Edge {
    int from, to;
    int cost;

    public Edge(int from, int to, int cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int tc = Integer.parseInt(br.readLine());
    while (tc-- > 0) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int n = Integer.parseInt(st.nextToken());
      int m = Integer.parseInt(st.nextToken());
      int w = Integer.parseInt(st.nextToken());
      ArrayList<Edge> adjList = new ArrayList<>();
      for (int i = 0; i < m; i++) {
        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());
        adjList.add(new Edge(s, e, t));
        adjList.add(new Edge(e, s, t));
      }
      for (int i = 0; i < w; i++) {
        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        int t = -Integer.parseInt(st.nextToken());
        adjList.add(new Edge(s, e, t));
      }

      // 벨만포드 시작
      boolean cycle = false;
      int[] costs = new int[n + 1];
      for (int i = 1; i < n; i++) {
        for (Edge e : adjList) {
          if (costs[e.to] > costs[e.from] + e.cost) {
            costs[e.to] = costs[e.from] + e.cost;
          }
        }
      }
      for (Edge e : adjList) {
        if (costs[e.to] > costs[e.from] + e.cost) {
          cycle = true;
          break;
        }
      }

      if (cycle)
        System.out.println("YES");
      else
        System.out.println("NO");
    }
  }
}