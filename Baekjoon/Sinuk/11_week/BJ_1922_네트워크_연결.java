import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_1922_네트워크_연결 {
    private static int[] parent;

    private static class Vertex implements Comparable<Vertex> {
        int a, b;
        int cost;

        public Vertex(int a, int b, int cost) {
            this.a = a;
            this.b = b;
            this.cost = cost;
        }

        @Override
        public int compareTo(Vertex o) {
            return this.cost - o.cost;
        }
    }

    private static int find(int a) {
        if (parent[a] == a) {
            return a;
        }
        return find(parent[a]);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());
        Vertex[] vertexs = new Vertex[m];
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            vertexs[i] = new Vertex(a, b, c);
        }

        // 평범하게 MST를 만들기 위해 크루스칼 적용
        Arrays.sort(vertexs);
        parent = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            parent[i] = i;
        }
        int count = 0;
        int idx = -1;
        int sum = 0;
        while (count < n - 1) {
            Vertex cur = vertexs[++idx];
            int aPar = find(cur.a);
            int bPar = find(cur.b);
            if (aPar != bPar) {
                if (aPar < bPar) {
                    parent[bPar] = aPar;
                } else {
                    parent[aPar] = bPar;
                }
                sum += cur.cost;
                count++;
            }
        }
        System.out.println(sum);
    }
}
