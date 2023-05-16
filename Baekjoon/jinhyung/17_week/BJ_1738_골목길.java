package gold2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_1738_골목길 {
	
	static class Edge implements Comparable<Edge> {
		int v, w;

		public Edge(int v, int w) {
			this.v = v;
			this.w = w;
		}

		@Override
		public int compareTo(Edge o) {
			return this.v - o.v;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // 교차점 수
		int M = Integer.parseInt(st.nextToken()); // 골목길 수
		
		// 인접 행렬 사용시 메모리초과
		ArrayList<ArrayList<Edge>> edgelist = new ArrayList<>();
		for(int i = 0; i <= N; i++) edgelist.add(new ArrayList<Edge>());
		for(int i = 0; i < M; i++) { // 골목길 입력받기
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			Edge edge = new Edge(v, w);
			int index = edgelist.get(u).indexOf(edge);
			if(index != -1) {
				// 시작과 도착지점이 같은 노드는 가중치가 더 큰 간선을 저장
				if(edgelist.get(u).get(index).w < w) {
					edgelist.get(u).get(index).w = w;
				}
				continue;
			}
			edgelist.get(u).add(edge);
		}
		
		int[] parent = new int[N+1]; // 부모 노드 저장(경로 탐색용)
		int[] distance = new int[N+1];
		Arrays.fill(distance, Integer.MIN_VALUE/2);
		distance[1] = 0;
		parent[1] = 1;
		
		// N-1번 업데이트 (벨만 포드)
		for(int i = 1; i < N; i++) {
			for(int u = 1; u <= N; u++) {
				for(Edge edge : edgelist.get(u)) {
					if(distance[u] + edge.w > distance[edge.v]) {
						distance[edge.v] = distance[u] + edge.w; 
						parent[edge.v] = u;
					}
				}
			}
		}
		
		// 사이클 존재 여부 판단을 위해 한번 더 돌리기
		int[] cycle = distance.clone();
		for(int u = 1; u <= N; u++) {
			for(Edge edge : edgelist.get(u)) {
				if(cycle[u] + edge.w > cycle[edge.v]) {
					cycle[edge.v] = cycle[u] + edge.w; 
					parent[edge.v] = u;
				}
			}
		}
		
		// 사이클 존재 여부 판단
		boolean loop = false;
		for(int i = 1; i <= N; i++) {
			if(distance[i] < cycle[i]) { // 한번 더 돌렸을때 값이 증가되는 노드가 있다면 사이클 존재
				distance[i] = Integer.MAX_VALUE/2; // 증가된 노드의 거리를 최대값으로 저장
				loop = true;
			}
		}
		
		// 사이클이 존재한다면 경로에 영향을 주는지 검사
		if(loop) {
			cycle = distance.clone();
			// N번 돌리기
			for(int i = 0; i < N; i++) {
				for(int u = 1; u <= N; u++) {
					for(Edge edge : edgelist.get(u)) {
						if(cycle[u] + edge.w > cycle[edge.v]) {
							cycle[edge.v] = cycle[u] + edge.w; 
							parent[edge.v] = u;
						}
					}
				}
			}
			
			// 도착 지점까지 비용이 증가했다면 무한 루프 존재
			if(cycle[N] > distance[N]) {
				System.out.println(-1);
				return;
			}
		}
		
		// 경로 계산
		ArrayDeque<Integer> stack = new ArrayDeque<Integer>();
		int k = N; // 역순으로 스택에 담기
		stack.add(k);
		while(k != 1) {
			stack.push(parent[k]);
			k = parent[k];
		}
		
		while(!stack.isEmpty()) { // 경로 출력
			int next = stack.pop();
			sb.append(next).append(" ");
		}
		System.out.println(sb); // 출력
	}
}
