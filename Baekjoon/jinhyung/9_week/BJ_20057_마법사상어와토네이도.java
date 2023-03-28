package com.baekjoon.g345;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_20057_마법사상어와토네이도 {
	
	static int N, out;
	static int[][] A;
	
	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {-1, 0, 1, 0};
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 입력
		N = Integer.parseInt(br.readLine()); // 맵 길이
		A = new int[N][N]; // 모래 지도
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		tornado(); // 토네이도
		System.out.println(out); // 출력
	}
	
	public static void tornado() {
		int x = N / 2; // 시작 x 좌표
		int y = N / 2; // 시작 y 좌표
		
		int d = 0; // 시작 방향 오른쪽
		int k = 1; // 현재 방향으로 전진할 칸 수
		int left = k; // 전진할 남은 칸 수
		while(x != 0 || y != 0) { // 토네이도가 0, 0에 도달할 때까지
			int nx = x + dx[d]; // 다음 x
			int ny = y + dy[d]; // 다음 y
			
			int rd = (d+1) % 4; // 오른쪽 방향
			int ld = d-1 == -1 ? 3 : d-1; // 왼쪽 방향
			int s = A[nx][ny]; // 밀어낼 모래 양
			int alpha = s; // alpha 칸으로 밀어내질 모래 양
			alpha -= spread(x+dx[rd], y+dy[rd], s, 1); // 모래가 흩날린다..
			alpha -= spread(x+dx[ld], y+dy[ld], s, 1); // 움직인 모래만큼 alpha에서 빼준다
			alpha -= spread(nx+dx[rd], ny+dy[rd], s, 7);
			alpha -= spread(nx+dx[ld], ny+dy[ld], s, 7);
			alpha -= spread(nx+2*dx[rd], ny+2*dy[rd], s, 2);
			alpha -= spread(nx+2*dx[ld], ny+2*dy[ld], s, 2);
			alpha -= spread(nx+dx[d]+dx[rd], ny+dy[d]+dy[rd], s, 10);
			alpha -= spread(nx+dx[d]+dx[ld], ny+dy[d]+dy[ld], s, 10);
			alpha -= spread(nx+2*dx[d], ny+2*dy[d], s, 5); // 다 움직였으면 남은 모래(alpha)가 alpha칸으로 간다
			
			if(!isOut(nx+dx[d], ny+dy[d])) A[nx+dx[d]][ny+dy[d]] += alpha; // 남은 모래는 alpha칸으로 밀어진다
			else out += alpha; // alpha칸이 격자 밖인 경우
			
			if(--left == 0) { // 다 전진 했다면
				if(d % 2 == 0) d++; // 오른쪽 또는 왼쪽인 경우 방향만 틀어주기
				else { // 아래쪽 또는 위쪽인 경우 방향 틀고 전진할 칸 수 + 1
					d = (d+1)%4;
					k++;
				}
				left = k; // 전진할 남은 칸 수 초기화
			}
			
			A[nx][ny] = 0; // 모래가 다 날아갔다
			x = nx; // x 전진
			y = ny; // y 전진
		}
	}
	
	public static int spread(int x, int y, int sand, int percent) {
		int moved = sand * percent / 100; // 움직일 모래 양
		if(!isOut(x, y)) A[x][y] += moved; // 격자 안이라면 해당하는 칸에 더해주기
		else out += moved; // 격자 밖이라면 나간 모래에 더해주기
		return moved; // 움직인 모래 반환
	}
	
	public static boolean isOut(int x, int y) {
		return x < 0 || x >= N || y < 0 || y >= N;
	}
}
