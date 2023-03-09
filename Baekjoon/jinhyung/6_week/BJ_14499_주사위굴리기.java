package com.baekjoon.g345;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_14499_주사위굴리기 {
	
	static StringBuilder sb = new StringBuilder();
	static int N, M, x, y, K;
	static int[][] map;

	static int[] dice = {6, 3, 2}; // down, east, north
	static int[] dicenum = {0, 0, 0, 0, 0, 0, 0}; // 주사위에 쓰여져 있는 숫자들
	
	static int[] dx = {0, 0, -1, 1}; // 동서북남
	static int[] dy = {1, -1, 0, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		y = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		map = new int[N][M]; // 맵 입력
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < K; i++) { // 굴릴만큼 굴리기
			move(Integer.parseInt(st.nextToken()));
		}
		
		System.out.println(sb); // 출력
	}
	
	public static void move(int to) {
		int nx = x + dx[to - 1];
		int ny = y + dy[to - 1];
		
		if(isOut(nx, ny)) return;
		
		x = nx;
		y = ny;
		
		int tmp;
		switch(to) {
		case 1: // 동쪽으로
			tmp = dice[0];
			dice[0] = dice[1]; // 동쪽을 가리키던 번호가 아래쪽이 된다.
			dice[1] = 7-tmp; // 위쪽을 가리키던 번호가 동쪽이 된다.
			break;
		case 2: // 서쪽으로
			tmp = dice[1];
			dice[1] = dice[0]; // 아래쪽을 가리키던 번호가 동쪽이 된다.
			dice[0] = 7-tmp; // 서쪽을 가리키던 번호가 아래쪽이 된다.
			break;
		case 3: // 북쪽으로
			tmp = dice[0];
			dice[0] = dice[2]; // 북쪽을 가리키던 번호가 아래쪽이 된다.
			dice[2] = 7-tmp; // 위쪽을 가리키던 번호가 북쪽이 된다.
			break;
		case 4: // 남쪽으로
			tmp = dice[2];
			dice[2] = dice[0]; // 아래쪽을 가리키던 번호가 북쪽이 된다.
			dice[0] = 7-tmp; // 남쪽을 가리키던 번호가 아래쪽이 된다.
			break;
		}
		if(map[x][y] != 0) {
			dicenum[dice[0]] = map[x][y]; // 0이 아닌 경우 아래쪽의 번호에 복사
			map[x][y] = 0; // 복사된 곳은 0이 된다.
		}
		else {
			map[x][y] = dicenum[dice[0]]; // 0인 경우 맵에 아래쪽의 번호를 복사
		}
		sb.append(dicenum[7-dice[0]]).append("\n"); // 위쪽에 쓰여있는 값 출력
	}
	
	public static boolean isOut(int x, int y) {
		if(x < 0 || x >= N || y < 0 || y >= M) return true;
		return false;
	}
}
