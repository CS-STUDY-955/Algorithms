package Gold.Gold3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_1865_웜홀 {
	private static int N, M, W, dist[], cnt[];
	private static ArrayList<Node> graph;
	
	private static class Node {
		int from, to, weight;
		public Node(int from, int to, int weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.parseInt(br.readLine());
		tc : for(int t = 1; t <= tc; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			graph = new ArrayList<Node>();
			cnt = new int[N+1];
			
			for(int i =0; i < M ; i++) {
				st = new StringTokenizer(br.readLine());
				int S = Integer.parseInt(st.nextToken());
				int E = Integer.parseInt(st.nextToken());
				int T = Integer.parseInt(st.nextToken());
				graph.add(new Node(S, E, T));
				graph.add(new Node(E, S, T));
			}
			
			worm: for(int i =0 ;i < W ; i++) {
				st = new StringTokenizer(br.readLine());
				int S = Integer.parseInt(st.nextToken());
				int E = Integer.parseInt(st.nextToken());
				int T = Integer.parseInt(st.nextToken());
				graph.add(new Node(S, E, -T));
				cnt[S]++;
			}
			for(int i = 1 ;i <= N ; i++) {
				if(cnt[i] > 0 && bellmanFord(i)) {
					System.out.println("YES");
					continue tc;
				}
			}
			System.out.println("NO");
		}
	}
	
	private static boolean bellmanFord(int start) {
		dist = new int[N+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[start] = 0;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < 2*M+W; j++) {
				Node node = graph.get(j);
				if (dist[node.from] != Integer.MAX_VALUE &&
						dist[node.to] > dist[node.from] + node.weight) {
					dist[node.to] = dist[node.from] + node.weight;
				}
			}
		}
		
		//음수 싸이클 확인
		for (int i = 0; i < 2*M+W; i++) {
			Node node = graph.get(i); 
			if (dist[node.from] != Integer.MAX_VALUE && dist[node.to] > dist[node.from] + node.weight) {
				return true;
			}
		}
		return false;
	}
}
