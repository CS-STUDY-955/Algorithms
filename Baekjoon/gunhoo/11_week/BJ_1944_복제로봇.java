package Gold.Gold1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_1944_복제로봇 {
	static int N, M, answer = 0, parents[];
	static boolean[][] visited;
	static char[][] map; 
	static int direction[][] = {{1,0}, {-1,0}, {0,1}, {0,-1}};
	static ArrayList<Point> list;
	static PriorityQueue<Node> pq;

	public static void main(String[] args) throws Exception{
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new char[N][N];
		
		list = new ArrayList<>();
		pq = new PriorityQueue<>();
		parents = new int[M+1];
		for(int i =0;i < M+1; i++) {
			parents[i] = i;
		}
		for(int i =0 ;i  < N;i++) {
			String input = br.readLine();
			for(int j = 0; j < N ;j++) {
				map[i][j] = input.charAt(j);
				if(map[i][j] == 'S' || map[i][j] == 'K') {
					list.add(new Point(i,j,0));
				}
			}
		}
		
		for(int i =0; i < M+1 ;i++) {
			visited = new boolean[N][N];
			move(i);
		}
		System.out.println(kruscal());
	}
	private static int kruscal() {
		int cost = 0; int edgeCount = 0;
		while(!pq.isEmpty()) {
			Node current = pq.poll();
			int p1 = find(current.from);
			int p2 = find(current.to);
			if(p1 != p2) {
				union(p1,p2);
				cost += current.weight;
				edgeCount++;
			}
		}
		if(edgeCount != M) return -1;
		return cost;
	}
	private static void union(int p1, int p2) {
		parents[p1] = p2;
	}
	private static int find(int node) {
		if(parents[node] == node) return node;
		return parents[node] = find(parents[node]);
	}
	private static void move(int num) {
		ArrayDeque<Point> q = new ArrayDeque<>();
		Point p = list.get(num);
		q.offer(p);
		visited[p.x][p.y] = true;
		while(!q.isEmpty()) {
			Point point = q.poll();
			for(int i =0;i < 4; i++) {
				int nx = point.x + direction[i][0];
				int ny = point.y + direction[i][1];
				if( 0>nx || N<= nx || 0>ny || ny>= N || map[nx][ny] == '1'|| visited[nx][ny] ) continue;
				visited[nx][ny] = true;
				if(map[nx][ny] == 'K' || map[nx][ny] == 'S') {
					for(int j =0 ; j < M+1; j++) {
						if(list.get(j).x == nx && list.get(j).y == ny ) {
							pq.offer(new Node(num, j, point.dis+1));
						}
					}
				}
				q.offer(new Point(nx, ny, point.dis+1));
			}
		}
	}

	static class Node implements Comparable<Node>{
		int from, to, weight;
		
		public Node(int from, int to, int weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
		
		@Override
		public int compareTo(Node o) {
			return this.weight-o.weight;
		}
	}
	static class Point{
		int x, y, dis;
		public Point(int x, int y , int dis) {
			this.x = x;
			this.y = y;
			this.dis = dis;
		}
	}

}
