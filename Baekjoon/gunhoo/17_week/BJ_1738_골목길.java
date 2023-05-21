package Gold.Gold2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_1738_골목길 {
	private static int N, M, parents[], dist[];
	private static ArrayList<Integer> path = new ArrayList<>(); // 방문경로를 담기 위한 변수
	private static ArrayList<Node> graph;
	private static class Node{
		int from, to, weight;
		public Node(int from, int to, int weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
	}
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		parents = new int[N+1];
		graph = new ArrayList<Node>();
		for(int i =0; i < M ; i++) {
			st= new StringTokenizer(br.readLine());
			int U = Integer.parseInt(st.nextToken());
			int V = Integer.parseInt(st.nextToken());
			int W = Integer.parseInt(st.nextToken());
			graph.add(new Node(U, V, W));
		}
		bellmanFord();
		if(dist[N] == Integer.MIN_VALUE) {
			System.out.println(-1); return;
		}
		for(Node node: graph) {
			if(dist[node.from] == Integer.MIN_VALUE) continue;
			if(dist[node.to] < dist[node.from]+node.weight && // 음수 가중치가 존재하고,  
					hasCycle(node.to)) { // 그 길에 도착지가 존재하는지 확인
				System.out.println(-1); return;
			}
		}
		for (int i = N; i != 0; i = parents[i]) {
            path.add(i); // 경로추가
        }
		for(int i = path.size()-1 ;i >= 0 ; i--) { // 방문경로 출력
			System.out.print(path.get(i)+" ");
		}
	}
	
	private static boolean hasCycle(int mid) {
		boolean visit[] = new boolean[N+1];
		Queue<Integer> q = new LinkedList<>();
		q.offer(mid);
		while(!q.isEmpty()) {
			int curNode = q.poll();
			if(visit[curNode]) continue;
			visit[curNode] = true;
			
			for(Node node : graph) {
				if(node.from == curNode && !visit[node.to]) {
					q.offer(node.to);
				}
			}
		}
		return visit[N];
	}
	
	private static void bellmanFord() {
		dist = new int[N+1];
		Arrays.fill(dist, Integer.MIN_VALUE);
		dist[1] = 0;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				Node node = graph.get(j);
				if (dist[node.from] != Integer.MIN_VALUE &&
						dist[node.to] < dist[node.from] + node.weight 
						) {
					dist[node.to] = dist[node.from] + node.weight;
					parents[node.to] = node.from; // 경로추적을 위한 부모배열에 추가
				}
			}
		}
	}

}
