package com.baekjoon.p5g12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class BJ_1202_보석도둑 {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // 보석 개수
		int K = Integer.parseInt(st.nextToken()); // 가방 개수
		
		// queue : 보석 무게, 보석 가치 // 보석 가치 기준 내림차순 정렬
		PriorityQueue<int[]> queue = new PriorityQueue<int[]>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				if(o1[1] == o2[1]) return o2[0] - o1[0];
				return o2[1] - o1[1];
			}
		});
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			// 무게, 가치 입력
			queue.offer(new int[] {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())});
		}
		
		// capacity : 가방 용량 저장 // 가방 용량 기준 오름차순 정렬
		TreeSet<int[]> capacity = new TreeSet<int[]>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				if(o1[0] == o2[0]) return o1[1] - o2[1];
				return o1[0] - o2[0];
			}
		});
		for(int i = 0; i < K; i++) {
			// 가방 용량, 가방 id (id: 가방 용량 중복되는 가방 저장 위함)
			capacity.add(new int[] {Integer.parseInt(br.readLine()), i});
		}
		
		long sum = 0; // 챙긴 보석 가치 합
		while(!queue.isEmpty()) {
			int[] gem = queue.poll(); // gem : 보석 무게, 보석 가치
			if(gem[0] > capacity.last()[0]) continue; // 용량이 가장 큰 배낭보다 무겁다면 건너뛰기
			else if(gem[0] <= capacity.first()[0]) { // 용량이 가장 작은 배낭보다 작다면 가방에 넣기
				capacity.pollFirst(); // 보석을 넣은 가방은 제외
			}
			else {
				// 그 외 보석 무게보다 큰 용량을 가진 가방 중 가장 작은 가방에 넣기
				capacity.remove(capacity.higher(new int[] {gem[0], 0}));
			}
			sum += gem[1]; // 보석 가치 더해주기
			if(capacity.size() == 0) break; // 남은 가방이 없다면 탈출
		}
		System.out.println(sum); // 출력
	}
}
