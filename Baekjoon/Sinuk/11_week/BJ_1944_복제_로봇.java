import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_1944_복제_로봇 {
    private static int[] dx = { -1, 0, 1, 0 };
    private static int[] dy = { 0, -1, 0, 1 };
    private static Point[] list;
    private static char[][] maze;
    private static List<XYValue> vertexs;

    // bfs에선 좌표와 탐색의 깊이를 나타냄
    // 크루스칼에선 정점의 번호 2개와 그 거리를 나타냄
    private static class XYValue implements Comparable<XYValue> {
        int x, y;
        int value;

        public XYValue(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        @Override
        public int compareTo(XYValue o) {
            return this.value - o.value;
        }
    }

    // 한 정점에서부터 모든 정점들까지의 거리를 구하는 BFS
    private static void getDistance(Point from, int idx) {
        int depth = 0;
        // bfs 시작
        Queue<XYValue> queue = new ArrayDeque<>();
        queue.add(new XYValue(from.x, from.y, depth));
        boolean[][] visited = new boolean[maze.length][maze.length];
        visited[from.x][from.y] = true;

        while (!queue.isEmpty()) {
            XYValue cur = queue.poll();
            // 현재 조사중인 위치가 S나 K라면 두 정점간의 간선정보는 vertexs에 넣음
            if (maze[cur.x][cur.y] == 'S' || maze[cur.x][cur.y] == 'K') {
                int targetIdx = getIndex(cur.x, cur.y);
                // 다만, idx가 targetIdx인 경우만 넣어서 중복되지 않게 함
                if (idx < targetIdx) {
                    vertexs.add(new XYValue(idx, targetIdx, cur.value));
                }
            }
            if (cur.value == depth) {
                depth++;
            }
            // 4방 탐색으로 뻗어나감
            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];
                if (maze[nx][ny] == '1') {
                    continue;
                }
                if (!visited[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.add(new XYValue(nx, ny, cur.value + 1));
                }
            }
        }
    }

    // list를 뒤져서 좌표에 해당하는 정점의 인덱스를 구하는 메서드
    private static int getIndex(int x, int y) {
        int idx = 0;
        int size = list.length;
        for (; idx < size; idx++) {
            Point temp = list[idx];
            if (x == temp.x && y == temp.y) {
                break;
            }
        }
        return idx;
    }

    // UnionFind+최적화
    private static int find(int[] parents, int a) {
        if (parents[a] == a) {
            return a;
        }
        int root = find(parents, parents[a]);
        parents[a] = root;
        return root;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        // 시작지점과 키의 위치를 담은 arraylist
        list = new Point[m + 1];
        int idx = 0;
        maze = new char[n][n];
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            for (int j = 0; j < n; j++) {
                maze[i][j] = str.charAt(j);
                // S 또는 K라면 정점의 리스트에 넣음
                if (maze[i][j] == 'S' || maze[i][j] == 'K') {
                    list[idx++] = new Point(i, j);
                }
            }
        }

        vertexs = new ArrayList<>();
        for (int i = 0; i < m + 1; i++) {
            getDistance(list[i], i);
        }

        // 모든 정점과 정점사이의 거리를 구했다면 MST를 구하기 위해 크루스칼 시작
        // Union Find 시작
        int[] parents = new int[m + 1];
        for (int i = 1; i <= m; i++) {
            parents[i] = i;
        }
        int sum = 0;
        int count = 0;
        // 간선들을 그 길이로 정렬
        Collections.sort(vertexs);
        for (XYValue cur : vertexs) {
            // 다 골랐으면 중단
            if (count == m) {
                break;
            }
            int xPar = find(parents, cur.x);
            int yPar = find(parents, cur.y);
            // 두 정점의 root가 동일하지 않다면 Union
            if (xPar != yPar) {
                sum += cur.value;
                count++;
                // root의 번호가 작은 쪽으로 결합
                if (xPar < yPar) {
                    parents[yPar] = xPar;
                } else {
                    parents[xPar] = yPar;
                }
            }
        }
        // 키를 전부 얻을 수 있었다면 sum 출력, 없었다면 -1 출력
        if (count == m) {
            System.out.println(sum);
        } else {
            System.out.println(-1);
        }
    }
}
