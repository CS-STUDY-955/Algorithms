package Gold.Gold1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
/**
 * K개의 weight를 0으로 만들 수 있다
 * 그때 1에서 N까지 가는 dist[N]의 최솟값 구해야해
 * 
 * @author Gunhoo
 *
 */
public class BJ_1162_도로포장 {
	private static int N, M, K;
	private static final int INF=Integer.MAX_VALUE;
	private static ArrayList<Node> graph[];
	static long[][] dp;
	
	static class Node{
		int node, count;
		long weight;
		
		public Node(int n, long w, int c) {
			this.node = n;
			this.weight = w;
			this.count = c;
		}
	}
	public static void main(String[] args) throws Exception {
		init();
		dijkstra();
		long min = Long.MAX_VALUE;
        for (long a : dp[N]) {
            min = Math.min(a, min);
        }
        System.out.println(min);
	}
	
	private static void init() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		graph = new ArrayList[N+1];
		visited = new boolean[N+1];
		dp = new long[N+1][K+1];

		for(int i =0 ; i < N+1; i++) {
			graph[i] = new ArrayList<>();
			Arrays.fill(dp[i], Long.MAX_VALUE);
		}
		
		for(int i =0 ;i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			graph[a].add(new Node(b, c, 0));
			graph[b].add(new Node(a, c, 0));
		}
	}
	
	private static boolean[] visited; 
	private static void dijkstra() {
		PriorityQueue<Node> pq = new PriorityQueue<Node>(Comparator.comparingLong(o -> o.weight));
		pq.offer(new Node(1, 0, 0));
		dp[1][0] = 0;
		
		while(!pq.isEmpty()) {
			Node curNode = pq.poll();
			if(curNode.weight > dp[curNode.node][curNode.count]) continue;
			
			for(Node next : graph[curNode.node]) {
				long nextDistance = next.weight + curNode.weight;
				if(nextDistance < dp[next.node][curNode.count]) {
					dp[next.node][curNode.count] = nextDistance;
					pq.offer(new Node(next.node, nextDistance, curNode.count));
				}
				
				if(curNode.count < K && curNode.weight < dp[next.node][curNode.count+1]) {
					dp[next.node][curNode.count+1] = curNode.weight;
					pq.offer(new Node(next.node, curNode.weight, curNode.count+1));
				}
			}
			
		}
	}

}
