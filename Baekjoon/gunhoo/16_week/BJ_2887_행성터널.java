package Platinum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class BJ_2887_행성터널 {
	private static int N, parents[], answer = 0;
	private static Planet[] planets;
	private static ArrayList<Edge> edgeList = new ArrayList<>();
	
	private static class Planet{
		int node;
		int x, y, z;
		public Planet(int node, int x, int y, int z) {
			this.node = node;
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
	private static class Edge {
		int from, to, distance;

		public Edge(int from, int to, int distance) {
			this.from = from;
			this.to = to;
			this.distance = distance;
		}

		
	}
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		planets = new Planet[N];
		parents = new int[N];
		for(int i = 0 ; i < N; i++) {
			st= new StringTokenizer(br.readLine());
			planets[i] = new Planet(i, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
			parents[i] = i;
		}
		
		// x 에 대해
		Arrays.sort(planets,  new Comparator<Planet>() {
			@Override
			public int compare(Planet o1, Planet o2) {
				return o1.x-o2.x;
			}
		});
		for(int i = 1 ;i < N; i++) {
			edgeList.add(new Edge(planets[i].node, planets[i-1].node, Math.abs(planets[i].x-planets[i-1].x)));
		}
		
		// y에 대해
		Arrays.sort(planets,  new Comparator<Planet>() {
			@Override
			public int compare(Planet o1, Planet o2) {
				return o1.y-o2.y;
			}
		});
		for(int i = 1 ;i < N; i++) {
			edgeList.add(new Edge(planets[i].node, planets[i-1].node, Math.abs(planets[i].y-planets[i-1].y)));
		}
		
		// z에 대해
		Arrays.sort(planets,  new Comparator<Planet>() {
			@Override
			public int compare(Planet o1, Planet o2) {
				return o1.z-o2.z;
			}
		});
		for(int i = 1 ;i < N; i++) {
			edgeList.add(new Edge(planets[i].node, planets[i-1].node, Math.abs(planets[i].z-planets[i-1].z)));
		}
		
		// 연결된 정보를 distance짧은 순서대로 정렬
		Collections.sort(edgeList, (o1, o2) -> o1.distance-o2.distance);
		
		for(int i =0 ; i < edgeList.size() ; i++) {
			Edge edge = edgeList.get(i);
			if(find(edge.from) != find(edge.to)) {
				answer += edge.distance;
				union(edge.from, edge.to);
			}
		}
		
		System.out.println(answer);
		
	}
	
	private static void union(int a, int b) {
		a = find(a);
		b = find(b);
		if(a != b) parents[a] = b;
	}
	
	private static int find(int x) {
		if(parents[x] == x) return x;
		return parents[x] = find(parents[x]);
	}

}
