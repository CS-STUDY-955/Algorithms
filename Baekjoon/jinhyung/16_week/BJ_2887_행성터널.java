package platinum5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_2887_행성터널 {
	
	static class Planet implements Comparable<Planet> {
		int index, p; // p : position
		
		public Planet(int index, int p) {
			this.index = index;
			this.p = p;
		}

		@Override
		public int compareTo(Planet o) {
			return this.p - o.p;
		}
	}

	static class Edge implements Comparable<Edge> {
		int u, v, cost;

		public Edge(int u, int v, int cost) {
			this.u = u;
			this.v = v;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge o) {
			return this.cost - o.cost;
		}
	}
	
	static int N, total, unioned;
	static Planet[][] planets;
	static Edge[][] edges;
	static int[] parent;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		planets = new Planet[3][N]; // x, y, z 기준 행성들의 좌표
		parent = new int[N]; // 각 행성이 속한 집합의 부모노드
		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(Planet[] planet : planets) { // 입력받기
				planet[i] = new Planet(i, Integer.parseInt(st.nextToken()));
			}
			parent[i] = i; // 부모는 자기자신으로 초기화
		}
		
		for(Planet[] planet : planets) {
			Arrays.sort(planet); // 오름차순 정렬
		}
		
		edges = new Edge[3][N-1]; // x, y, z 기준 최소 간선들의 정보
		for(int i = 0; i < 3; i++) {
			edges[i] = new Edge[N-1]; // 간선 배열 초기화
		}
		for(int i = 0; i < N-1; i++) {
			for(int j = 0; j < 3; j++) { // j => 0: x, 1: y, 2: z
				Planet[] planet = planets[j]; // 각 방향 기준 행성들의 좌표 정보
				Edge[] edge = edges[j]; // 초기화된 간선 배열
				int cost = planet[i+1].p - planet[i].p; // 인접한 행성간의 비용 계산
				edge[i] = new Edge(planet[i].index, planet[i+1].index, cost); // 간선 정보 저장
			}
		}
		
		for(Edge[] edge : edges) {
			Arrays.sort(edge); // 간선 비용순 오름차순 정렬
		}
		unioned = 1; // 연결된 정점 수
		int[] idxs = new int[3];
		while(unioned < N) { // 모든 정점이 연결될때까지
			int k = -1; // 최소값이 x, y, z 중 어느 방향 기준인지 저장
			int min = Integer.MAX_VALUE; // 최소값
			for(int i = 0; i < 3; i++) { // 각 방향에 대해
				if(idxs[i] == N-1) continue; // 끝까지 갔다면 패스
				// 같은 집합이면 다음 인덱스 보기
				while(find(edges[i][idxs[i]].u) == find(edges[i][idxs[i]].v) && idxs[i] < N-1) idxs[i]++;
				if(min > edges[i][idxs[i]].cost) { // 최소값보다 작다면
					min = edges[i][idxs[i]].cost; // 값 갱신
					k = i; // 방향 저장
				}
			}
			
			total += edges[k][idxs[k]].cost; // 비용 더해주기
			union(edges[k][idxs[k]].u, edges[k][idxs[k]].v); // 유니온
		}
		
		System.out.println(total); // 출력
	}
	
	static int find(int x) {
		if(parent[x] == x) return x;
		else return parent[x] = find(parent[x]);
	}
	
	static void union(int x, int y) {
		int px = find(x);
		int py = find(y);
		if(px < py) parent[py] = px;
		else parent[px] = py;
		unioned++;
	}
}
