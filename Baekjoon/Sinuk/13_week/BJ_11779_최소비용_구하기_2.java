import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_11779_최소비용_구하기_2 {
  private static class Vertex implements Comparable<Vertex> {
    int v;
    int cost;

    public Vertex(int v, int cost) {
      this.v = v;
      this.cost = cost;
    }

    @Override
    public int compareTo(Vertex o) {
      return this.cost - o.cost;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.parseInt(br.readLine());
    int m = Integer.parseInt(br.readLine());
    ArrayList<ArrayList<Vertex>> adList = new ArrayList<>();
    for (int i = 0; i <= n; i++) {
      adList.add(new ArrayList<>());
    }
    for (int i = 0; i < m; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int from = Integer.parseInt(st.nextToken());
      int to = Integer.parseInt(st.nextToken());
      int cost = Integer.parseInt(st.nextToken());
      adList.get(from).add(new Vertex(to, cost));
    }
    StringTokenizer st = new StringTokenizer(br.readLine());
    int start = Integer.parseInt(st.nextToken());
    int end = Integer.parseInt(st.nextToken());

    // 다익스트라 준비
    PriorityQueue<Vertex> pq = new PriorityQueue<>();
    pq.add(new Vertex(start, 0));
    int[] cost = new int[n + 1];
    // 자신에게 도착한 이전 노드의 인덱스를 기록
    int[] path = new int[n + 1];
    boolean[] visited = new boolean[n + 1];
    for (int i = 0; i <= n; i++) {
      cost[i] = Integer.MAX_VALUE;
      path[i] = -1;
    }
    cost[start] = 0;
    path[start] = 0;

    while (!pq.isEmpty()) {
      // end 처리 해야하는 곳에 도착하면 종료
      if (visited[end]) {
        break;
      }
      Vertex cur = pq.poll();
      // 이미 처리된 곳이라면 패스
      if (visited[cur.v]) {
        continue;
      }
      visited[cur.v] = true;
      // 현재 조사중인 곳과 연결된 곳들에 대해
      for (Vertex next : adList.get(cur.v)) {
        if (!visited[next.v] && cur.cost + next.cost < cost[next.v]) {
          cost[next.v] = cur.cost + next.cost;
          path[next.v] = cur.v;
          pq.add(new Vertex(next.v, cur.cost + next.cost));
        }
      }
    }

    // 역순으로 만들기
    int idx = end;
    ArrayList<Integer> tempList = new ArrayList<>();
    while (path[idx] != -1) {
      tempList.add(idx);
      idx = path[idx];
    }

    StringBuilder sb = new StringBuilder();
    // 역순으로 StringBuilder에 삽입
    for (int i = tempList.size() - 1; i >= 0; i--) {
      sb.append(tempList.get(i) + " ");
    }
    System.out.println(cost[end]);
    System.out.println(tempList.size());
    System.out.println(sb.toString());
  }
}
