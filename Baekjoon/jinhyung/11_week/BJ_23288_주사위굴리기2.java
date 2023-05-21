package com.baekjoon.g345;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BJ_23288_주사위굴리기2 {
	
	static int N, M, K, x, y, d, s; // N, M, K, 주사위 x, 주사위 y, 방향, 점수
	static int[] dice = {6, 2, 3}; // 아래, 북, 동
	static int[][] map, scoremap; // 지도, 점수 지도 
	static boolean[][] scorevisited; // 점수 지도 init용
	
	static int[] dx = {0, 1, 0, -1}; // 동남서북
	static int[] dy = {1, 0, -1, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 행크기
		M = Integer.parseInt(st.nextToken()); // 열크기
		K = Integer.parseInt(st.nextToken()); // 굴릴 횟수
		
		map = new int[N][M]; // 지도
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		scoremap = new int[N][M]; // 점수 지도
		scorevisited = new boolean[N][M]; // 방문처리용
		scoreinit(); // 점수 지도 초기화

		x = 0; y = 0; d = 0; s = 0; // 주사위 위치, 방향, 점수 초기화
		for(int i = 0; i < K; i++) {
			roll(); // 굴리기
		}
		
		System.out.println(s); // 출력
	}

	private static void scoreinit() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(scorevisited[i][j]) continue; // 이미 방문했다면 패스
				scorevisited[i][j] = true; // 방문 처리
				ArrayList<int[]> list = new ArrayList<>(); // 연속된 좌표 담을 리스트
				list.add(new int[] {i, j}); // 시작 좌표 담기
				int idx = 0; // 인덱스 초기화
				int sum = 0; // 점수 초기화
				
				// 숫자가 같은 연속된 칸들 구하기
				while(idx != list.size()) { // 인덱스가 리스트 사이즈 이하인 동안
					int[] p = list.get(idx++);
					sum += map[i][j]; // sum에 점수 하나 더 더하기
					
					for(int k = 0; k < 4; k++) { // 4방 탐색
						int nx = p[0] + dx[k];
						int ny = p[1] + dy[k];
						
						// 맵 밖이거나 이미 방문했거나 지도의 숫자가 다른 경우 패스
						if(isOut(nx, ny) || scorevisited[nx][ny] || map[nx][ny] != map[i][j]) continue;
						
						scorevisited[nx][ny] = true; // 방문 처리
						list.add(new int[] {nx, ny}); // 리스트에 넣기 
					}
				}
				
				for(int[] p : list) { // 연속된 각 좌표들에 대해
					scoremap[p[0]][p[1]] = sum; // 점수 정해주기
				}
			}
		}
	}

	private static void roll() {
		int nx = x + dx[d]; // 다음 좌표 구하기
		int ny = y + dy[d];
		
		if(isOut(nx, ny)) { // 맵 밖이라면
			d = (d+2)%4; // 반대 방향으로 전환
			nx = x + dx[d];
			ny = y + dy[d];
		}
		
		int tmp = -1;
		switch(d) { // 주사위 굴리기
		case 0: // 동쪽으로
			tmp = dice[0]; // 0: 아래, 1: 북쪽, 2: 동쪽을 가리키는 숫자
			dice[0] = dice[2];
			dice[2] = 7 - tmp;
			break;
		case 1: // 남쪽으로
			tmp = dice[1];
			dice[1] = dice[0];
			dice[0] = 7 - tmp;
			break;
		case 2: // 서쪽으로
			tmp = dice[2];
			dice[2] = dice[0];
			dice[0] = 7 - tmp;
			break;
		case 3: // 북쪽으로
			tmp = dice[0];
			dice[0] = dice[1];
			dice[1] = 7 - tmp;
			break;
		}
		
		x = nx; y = ny; // x, y 갱신
		s += scoremap[x][y]; // 그 좌표의 점수 더해주기
		
		if(dice[0] > map[x][y]) { // 주사위 숫자가 지도 숫자보다 크다면 시계방향 90도
			d = (d+1)%4;
		} else if(dice[0] < map[x][y]) { // 주사위 숫자가 작다면 반시계 90도
			d = d==0?3:d-1;
		}
		// 같은 경우는 방향 그대로
	}
	
	private static boolean isOut(int r, int c) {
		return r < 0 || r >= N || c < 0 || c >= M;
	}
}
