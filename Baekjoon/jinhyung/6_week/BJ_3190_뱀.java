package com.baekjoon.g345;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BJ_3190_뱀 {
	
	static int[] dx = {-1, 0, 1, 0}; // 북동남서
	static int[] dy = {0, 1, 0, -1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int N = Integer.parseInt(br.readLine());
		int K = Integer.parseInt(br.readLine());
		
		int[][] map = new int[N+2][N+2];
		for(int i = 0; i < N+2; i++) { // 맵 테두리 1로 패딩하기
			map[i][0] = 1;
			map[i][N+1] = 1;
		}
		for(int i = 0; i < N+2; i++) { // 맵 테두리 1로 패딩하기
			map[0][i] = 1;
			map[N+1][i] = 1;
		}
		
		for(int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			map[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = 2; // 사과의 위치
		}

		int L = Integer.parseInt(br.readLine()); // 회전 수
		ArrayList<Integer> changetime = new ArrayList<>(); // 회전 시간
		ArrayList<Character> changedirection = new ArrayList<>(); // 회전 방향
		for(int i = 0; i < L; i++) { // 입력 받기
			st = new StringTokenizer(br.readLine());
			changetime.add(Integer.parseInt(st.nextToken()));
			changedirection.add(st.nextToken().charAt(0));
		}
		
		LinkedList<int[]> snake = new LinkedList<>(); // 뱀
		snake.offer(new int[] {1, 1}); // 뱀은 1, 1에서 시작
		int d = 1; // 방향
		int time = 0; // 시간
		int nchange = 0; // 방향 바꾼 수
		while(true) {
			time++;
			int nx = snake.peek()[0] + dx[d]; // 뱀 머리 추가
			int ny = snake.peek()[1] + dy[d];
			snake.addFirst(new int[] {nx, ny});
			if(map[nx][ny] == 1) break; // 벽 혹은 뱀 몸체에 닿으면 종료
			if(map[nx][ny] == 0) { // 빈 공간이라면
				map[snake.getLast()[0]][snake.getLast()[1]] = 0; // 뱀 꼬리 위치 0으로 만들고
				snake.pollLast(); // 꼬리 줄어들기
			}
			// 사과인 경우 줄어들지 않는다
			
			map[nx][ny] = 1; // 머리 위치 1로 만들기
			if(nchange < changetime.size() && time == changetime.get(nchange)) { // 아직 다 바뀌지 않았고 바뀔 시간이라면
				if(changedirection.get(nchange) == 'D') d = (d+1)%4; // 방향 바꾸기
				else d = (d+3)%4;			
				nchange++; // 바꾼 수 + 1
			}
		}
		
		System.out.println(time);
	}
}
