package gold3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_1865_웜홀 {
	
	static class Edge {
		int e, t;

		public Edge(int e, int t) {
			this.e = e;
			this.t = t;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		int T = Integer.parseInt(st.nextToken()); // 테스트케이스
		for(int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken()); // 지점 수
			int M = Integer.parseInt(st.nextToken()); // 도로 수
			int W = Integer.parseInt(st.nextToken()); // 웜홀 수
			
			// 간선 리스트
			ArrayList<ArrayList<Edge>> edgelist = new ArrayList<ArrayList<Edge>>();
			for(int i = 0; i <= N; i++) edgelist.add(new ArrayList<Edge>());
			for(int i = 0; i < M; i++) { // 도로 입력
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken()); // 한 지점
				int e = Integer.parseInt(st.nextToken()); // 다른 지점
				int t = Integer.parseInt(st.nextToken()); // 걸리는 시간

				// 도로는 양방향
				edgelist.get(s).add(new Edge(e, t));
				edgelist.get(e).add(new Edge(s, t));
			}
			
			for(int i = 0; i < W; i++) { // 웜홀 입력
				st = new StringTokenizer(br.readLine());
				int s = Integer.parseInt(st.nextToken()); // 시작 지점
				int e = Integer.parseInt(st.nextToken()); // 도착 지점
				int t = Integer.parseInt(st.nextToken()); // 줄어드는 시간

				// 웜홀은 단방향
				edgelist.get(s).add(new Edge(e, -t));
			}
			
			// 벨만 포드
			int[] distance = new int[N+1];
//			Arrays.fill(distance, Integer.MAX_VALUE);
			distance[1] = 0;
			for(int i = 0; i < N-1; i++) {
				for(int s = 1; s <= N; s++) {
					for(Edge e: edgelist.get(s)) {
						if(distance[s] + e.t < distance[e.e]) {
							distance[e.e] = distance[s] + e.t; 
						}
					}
				}
			}

			// 사이클 여부 판단
			int[] cycle = distance.clone();
			for(int s = 1; s <= N; s++) {
				for(Edge e: edgelist.get(s)) {
					if(cycle[s] + e.t < cycle[e.e]) {
						cycle[e.e]= cycle[s] + e.t; 
					}
				}
			}
			
			String ans = "NO";
			for(int i = 1; i <= N; i++) {
				if(cycle[i] != distance[i]) { // 한번 더 갱신됐다면 줄어드는 사이클이 있음
					ans = "YES";
					break;
				}
			}
			
			sb.append(ans).append("\n");
		}
		System.out.println(sb); // 출력
	}
}
