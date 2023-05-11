import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class BJ_2887_행성_터널 {
    private static int[] parent;

    private static class Planet {
        int idx;
        int x, y, z;
    }

    private static class Edge implements Comparable<Edge> {
        int from, to;
        int cost;

        public Edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge o) {
            return this.cost - o.cost;
        }
    }

    private static int find(int p){
        if (parent[p] == p){
            return p;
        }
        parent[p] = find(parent[p]);
        return parent[p];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        ArrayList<Planet> planets = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            Planet p = new Planet();
            p.idx = i;
            p.x = Integer.parseInt(st.nextToken());
            p.y = Integer.parseInt(st.nextToken());
            p.z = Integer.parseInt(st.nextToken());
            planets.add(p);
        }
        ArrayList<Edge> adjList = new ArrayList<>();

        // x에 대해서 정렬
        Collections.sort(planets, new Comparator<Planet>() {
            @Override
            public int compare(Planet o1, Planet o2) {
                return o1.x - o2.x;
            }
        });

        // x에 대해 간선 생성하여 저장
        for (int i = 0; i < n - 1; i++) {
            Planet p1 = planets.get(i);
            Planet p2 = planets.get(i + 1);
            adjList.add(new Edge(p1.idx, p2.idx, Math.abs(p1.x - p2.x)));
        }

        // y에 대해서 정렬
        Collections.sort(planets, new Comparator<Planet>() {
            @Override
            public int compare(Planet o1, Planet o2) {
                return o1.y - o2.y;
            }
        });

        // y에 대해 간선 생성하여 저장
        for (int i = 0; i < n - 1; i++) {
            Planet p1 = planets.get(i);
            Planet p2 = planets.get(i + 1);
            adjList.add(new Edge(p1.idx, p2.idx, Math.abs(p1.y - p2.y)));
        }

        // z에 대해서 정렬
        Collections.sort(planets, new Comparator<Planet>() {
            @Override
            public int compare(Planet o1, Planet o2) {
                return o1.z - o2.z;
            }
        });

        // z에 대해 간선 생성하여 저장
        for (int i = 0; i < n - 1; i++) {
            Planet p1 = planets.get(i);
            Planet p2 = planets.get(i + 1);
            adjList.add(new Edge(p1.idx, p2.idx, Math.abs(p1.z - p2.z)));
        }

        long total = 0;
        // adjList 정렬
        Collections.sort(adjList);
        int count = 0;
        parent = new int[n];
        for(int i = 0; i<n; i++){
            parent[i] = i;
        }
        for(int i = 0; i<adjList.size(); i++) {
            if (count == n-1){
                break;
            }
            // 두 점의 정보를 받는다
            Edge e = adjList.get(i);
            int a = e.from;
            int b = e.to;
            // 두 점의 부모가 같은지 확인한다.
            // 다르다면 둘을 union 시키고, total을 증가시킨다
            int pa = find(a);
            int pb = find(b);
            if (pa != pb){
                parent[pb] = pa;
                total += e.cost;
                count++;
            }
        }

        System.out.println(total);
    }
}
