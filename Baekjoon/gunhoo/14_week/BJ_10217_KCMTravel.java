package Platinum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
/**
 * 다익스트라인데...
 * 비용처리를 어케한담? 2차원 dp배열
 * 
 * @author SSAFY
 *
 */
public class BJ_10217_KCMTravel {
	private static int N, M, K;
	static class Node implements Comparable<Node>{
		int to, cost, distance;

		public Node(int to, int cost, int distance) {
			super();
			this.to = to;
			this.cost = cost;
			this.distance = distance;
		}

		@Override
		public int compareTo(Node o) {
			if(this.distance < o.distance) return -1;
			else if(this.distance == o.distance) {
				if(this.cost < o.cost) return -1;
				return 0;
			}
			return 1;
		}
		
	}
	private static ArrayList<Node> graph[];
	private static int[][] cost;
	
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= new StringTokenizer(br.readLine());
		int t = Integer.parseInt(st.nextToken());
		for(int tc = 1; tc<=t ; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			cost = new int[N+1][M+1];
			graph = new ArrayList[N+1];
			for(int i = 0; i < N+1; i++) {
				graph[i] = new ArrayList<>();
				Arrays.fill(cost[i], Integer.MAX_VALUE);
			}
			
			for(int i = 0; i < K ;i++) {
				st= new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());
				graph[u].add(new Node(v, c, d));
			}
			
			dijkstra();
			int min = Integer.MAX_VALUE;
			for(int i = 0; i <= M; i++) min = Math.min(min, cost[N][i]);
			System.out.println(min==Integer.MAX_VALUE?"Poor KCM":min); // 
		}
	}
	
	private static void dijkstra() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.offer(new Node(1, 0, 0));
		cost[1][0] = 0;
		while(!pq.isEmpty()) {
			Node curNode = pq.poll();
			if(curNode.to == N) break;
			for(Node next : graph[curNode.to]) {
				if(curNode.cost + next.cost > M) continue;
				if(cost[next.to][curNode.cost + next.cost] <= next.distance+curNode.distance) {
					continue;
				}
				for(int j = curNode.cost + next.cost; j<= M ; j++) {
					if(cost[next.to][j] > next.distance+curNode.distance) {
						cost[next.to][j] = next.distance+curNode.distance;
					}
				}
				cost[curNode.to][curNode.cost + next.cost] = next.distance+curNode.distance;
				pq.offer(new Node(next.to, curNode.cost + next.cost, next.distance+curNode.distance));
			}
		}
	}

}
