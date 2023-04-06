package com.baekjoon.g345;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * BJ 1922. 네트워크 연결
 * 
 * @author 양진형
 * 23. 4. 3.
 */
public class BJ_1922_네트워크연결 {
	
	static int[] p; // 서로소 집합 용
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // 컴퓨터의 수
		st = new StringTokenizer(br.readLine());
		int M = Integer.parseInt(st.nextToken()); // 연결할 수 있는 선의 수
		
		// pqueue : 선 입력(from, to, weight) // 가중치 순 오름차순 정렬
		PriorityQueue<int[]> pqueue = new PriorityQueue<>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[2] - o2[2];
			}
		});
		
		for(int i = 0; i < M; i++) {
			int[] edge = new int[3];
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < 3; j++) {
				// 선 입력받기
				edge[j] = Integer.parseInt(st.nextToken());
			}
			
			pqueue.offer(edge);
		}
		
		p = new int[N+1]; // 사이클 검사용 서로소 집합
		for(int i = 1; i <= N; i++) p[i] = i; // 서로소 집합 초기화
		
		// KRUSKAL 알고리즘 - 가중치가 최소인 간선부터 차례대로 선택, 사이클이 발생하는 경우는 건너뛰기
		int cost = 0; // 총 비용
		while(!pqueue.isEmpty()) {
			int[] edge = pqueue.poll();
			
			if(find(edge[0]) == find(edge[1])) continue; // 정점 둘이 같은 집합인 경우 패스
			union(edge[0], edge[1]); // 정점 합치기
			cost += edge[2]; // 비용 더해주기
		}

		System.out.println(cost); // 출력
	}
	
	static int find(int x) { // 서로소 집합 루트 노드 찾기
		if(p[x] == x) return x;
		else return p[x] = find(p[x]);
	}
	
	static void union(int x, int y) { // 서로소 집합 합치기
		p[find(y)] = p[find(x)];
		
//		int xp = find(x);
//		int yp = find(y);
//		if(xp < yp) p[yp] = xp;
//		else p[xp] = yp;
	}
}
