package com.baekjoon.g345;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_20056_마법사상어와파이어볼 {
	
	static int N, M, K;
	static Queue<int[]> fireballs = new LinkedList<>();
	
	static int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
	static int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // 격자의 크기
		M = Integer.parseInt(st.nextToken()); // 파이어볼의 개수
		K = Integer.parseInt(st.nextToken()); // 명령 횟수
		
		for(int i = 0; i < M; i++) { // 파이어볼 입력 받기
			int[] fb = new int[5];
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < 5; j++) {
				fb[j] = Integer.parseInt(st.nextToken());
			}
			
			fireballs.add(fb);
		}
		
		for(int i = 0; i < K; i++) { // K 횟수만큼 움직이기
			move();
		}
		
		int total = 0;
		while(!fireballs.isEmpty()) { // 모든 파이어볼의 질량 더하기
			total += fireballs.poll()[2];
		}
		
		System.out.println(total); // 출력
	}
	
	public static void move() {
		HashMap<Integer, int[]> aftermove = new HashMap<>(); // 움직인 이후 파이어볼 정보
		HashSet<Integer> overlap = new HashSet<>(); // 파이어볼이 중복될 경우 위치 저장
		while(!fireballs.isEmpty()) { // 맵에 있는 모든 파이어볼에 대해
			int[] fb = fireballs.poll();
			// fb: x, y, 질량, 속도, 방향
			int nx = fb[0] + dx[fb[4]] * fb[3]; // 파이어볼의 다음 행 위치 구하기
			int ny = fb[1] + dy[fb[4]] * fb[3]; // 파이어볼의 다음 열 위치 구하기
			
			// 맵을 벗어난 경우 위치 보정
			if(nx < 0) nx = (N + nx % N) % N;
			else if(nx >= N) nx = nx % N;
			if(ny < 0) ny = (N + ny % N) % N;
			else if(ny >= N) ny = ny % N;
			
			int next = nx * N + ny; // 다음 위치 int로 표시
			if(aftermove.containsKey(next)) { // 움직인 위치에 파이어볼이 있다면
				int[] tmp = aftermove.get(next); // tmp: 
				for(int i = 0; i < 3; i++) { // 질량, 속도, 방향 더해주기 (방향은 더해줄 필요X)
					tmp[i] += fb[i+2];
				}
				if(tmp[3] != fb[4] % 2) { // 이전 방향과 홀짝이 다르다면
					tmp[3] = -1; // -1로
				}
				tmp[4]++; // 카운트 증가
				aftermove.put(next, tmp); // aftermove에 넣어주기
				overlap.add(next); // 중복 좌표에 넣어주기
			}
			else {
				// aftermove: Key - x*N+y, Value - 질량, 속도, 방향, 방향 홀짝, 갯수
				aftermove.put(next, new int[] {fb[2], fb[3], fb[4], fb[4] % 2, 1});
			}
		}
		
		for(Entry<Integer, int[]> e : aftermove.entrySet()) {
			int point = e.getKey();
			int[] status = e.getValue();
			if(overlap.contains(point)) {
				if(status[0]/5 == 0) continue; // 질량이 0이라면 제거
				int[] directions = null;
				if(status[3] == -1) { // 방향이 홀짝 섞여서 들어온 경우
					directions = new int[] {1, 3, 5, 7};
				}
				else { // 방향이 홀 혹은 짝 하나로만 들어온 경우
					directions = new int[] {0, 2, 4, 6};
				}
				for(int d: directions) { // 각 방향마다 나눠진 파이어볼 추가
					fireballs.add(new int[] {point/N, point%N, status[0]/5, status[1]/status[4], d});
				}
			}
			else {
				fireballs.add(new int[] {point/N, point%N, status[0], status[1], status[2]});
			}
		}
	}
}
