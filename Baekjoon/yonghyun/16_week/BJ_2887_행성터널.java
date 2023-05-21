import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 행성 터널
 * https://www.acmicpc.net/problem/2887
 *
 * 1. 얼핏 생각하면 외판원 순회와 비슷한 문제인 것 같지만, N이 최대 100,000이기 때문에 불가능하다.
 * 2. 모든 노드를 연결하는 가장 짧은 경로를 구하는 문제인데 외판원 순회와 다른 점을 찾아야한다.
 * 3. 거리가 아닌 좌표가 주어졌다는 점이 다르므로 이를 이용해야 한다.
 *
 * -------------------------------------------------------
 * 1. 출발 지점으로 다시 돌아가지 않으므로 외판원 순회와 다르다.
 * 2. 크루스칼 알고리즘을 이용하면 모든 행성을 순회할 수 있다.
 * 3. 단, 한 노드를 연결할 수 있는 간선이 3개 존재하므로 간선 클래스를 따로 선언하여 사용해야한다.
 * 4. 또한 N이 최대 100,000이므로 모든 간선을 구할 수 없다.
 * 5. 따라서 좌표를 정렬한뒤 인접한 노드끼리의 간선 길이만 계산하여 간선 정보를 생성한다.
 * 6. 생성된 간선을 길이순으로 오름차순 정렬하여 최단 거리를 구하는 크루스칼 알고리즘을 구현한다.
 *
 * @author 배용현
 *
 */
public class BJ_2887_행성터널 {

    static class Point {
        int num, x, y, z;

        public Point(int num, int x, int y, int z) {
            this.num = num;
            this.x = x;
            this.y = y;
            this.z = z;
        }

    }

    static class Edge implements Comparable<Edge> {
        int node1, node2, weight;

        public Edge(int node1, int node2, int weight) {
            this.node1 = node1;
            this.node2 = node2;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge e) {
            return weight - e.weight;
        }
    }

    static int N;
    static Point[] points;
    static int[] parent;
    static ArrayList<Edge> edgeList = new ArrayList<>();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        N = parseInt(br.readLine());        // 행성의 수
        points = new Point[N];      // 좌표 정보를 저장할 배열
        parent = new int[N];        // 부모 노드 저장할 배열

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            points[i] = new Point(i,
                    parseInt(st.nextToken()),
                    parseInt(st.nextToken()),
                    parseInt(st.nextToken()));
            parent[i] = i;
        }

        Arrays.sort(points, new Comparator<Point>() {       // Comparator로 비교
            @Override
            public int compare(Point p1, Point p2) {
                return p1.x - p2.x;
            }
        });
        for (int i = 0; i < N - 1; i++) {
            edgeList.add(new Edge(
                    points[i].num,
                    points[i + 1].num,
                    Math.abs(points[i].x - points[i + 1].x)
            ));
        }

        Arrays.sort(points, (p1, p2) -> p1.y - p2.y);       // Lambda로 비교
        for (int i = 0; i < N - 1; i++) {
            edgeList.add(new Edge(points[i].num, points[i + 1].num, Math.abs(points[i].y - points[i + 1].y)));
        }

        Arrays.sort(points, Comparator.comparingInt(p -> p.z));     // comparingInt로 비교
        for (int i = 0; i < N - 1; i++) {
            edgeList.add(new Edge(points[i].num, points[i + 1].num, Math.abs(points[i].z - points[i + 1].z)));
        }

        edgeList.sort(null);

        int answer = 0;
        for (Edge edge : edgeList) {
            if (find(edge.node1) == find(edge.node2)) {     // 이미 연결된 행성이면 패스
                continue;
            }

            answer += edge.weight;      // 현재 선택된 간선 길이 더하고
            union(edge.node1, edge.node2);      // 연결
        }

        System.out.print(answer);
    }

    private static void union(int node1, int node2) {
        node1 = find(node1);
        node2 = find(node2);

        if (node1 < node2) {
            parent[node2] = node1;
        } else {
            parent[node1] = node2;
        }
    }

    private static int find(int node) {
        if (parent[node] == node) {
            return node;
        }

        return parent[node] = find(parent[node]);
    }

}
