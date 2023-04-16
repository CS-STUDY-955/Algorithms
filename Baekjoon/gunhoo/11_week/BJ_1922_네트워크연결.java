package Gold.Gold4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
/**
 * MST 알고리즘 : 주어진 모든 그래프의 모든 정점들을 연결하는 부분 그래프 중에서 그 가중치의 합이 최소인 트리
 * - 간적크 / 간만프 
 * - 간선 100,000개로 제한 > 크루스칼
 * 크루스칼
 * 	1. Edge class 생성(comparable)
 *  2. edgeList 정렬(오름차순)
 *  3-0. union/find 메서드 생성( union : root찾아서 다르면 parents[aRoot] = bRoot; (합치기))
 *  3. union을 하면서 간선이 연결되어있지않다면, union해서 합치기
 *  4. 합쳐진 edge의 weight를 더해주기
 *  
 * @author Gunhoo
 *
 */
public class BJ_1922_네트워크연결 {
	
	static int V, E, parents[];
	static Edge[] edgeList;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		V = Integer.parseInt(br.readLine());
		E = Integer.parseInt(br.readLine());
		edgeList = new Edge[E];
		parents = new int[V+1];
		for(int i =1; i <= V; i++) {
			parents[i] = i; // 초기화
		}
		for(int i =0 ; i < E; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			edgeList[i] = new Edge(a,b,c); // 엣지리스트에 추가
		}
		System.out.println(kruskal());
	}
	
	private static int kruskal() {
		int res =0, cnt = 0; 
		Arrays.sort(edgeList); // 정렬되어있어야함
		for(Edge e : edgeList) { // 모든 연결 선에 대해
			if(union(e.from, e.to)) {  // 만약 연결되어 있지 않다면, 연결하고 아래 실행
				res += e.weight; // 비용추가
				if(++cnt == V-1) return res; // 모두 골랐으면 return
			}
		}
		return 0;
	}
	
	private static boolean union(int a, int b) {
		int aRoot = find(a);
		int bRoot = find(b);
		if(aRoot == bRoot) return false; // 연결되어있으면 false
		parents[aRoot] = bRoot; // 연결되어있지 않다면 연결하고, 
		return true;  // true 반환
	}
	
	private static int find(int a) {
		if(a == parents[a]) return a;
		return parents[a] = find(parents[a]);
	}
	
	static class Edge implements Comparable<Edge>{
		int from, to, weight;
		public Edge(int from, int to, int weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
		@Override
		public int compareTo(Edge o) {
			return this.weight-o.weight;
		}
	}

}
