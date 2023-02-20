package com.baekjoon.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

// CCTV 방향 조합
public class BJ_15683_감시 {
	
	static int N, M; // 행, 열
	static int[][] office; // 사무실
	static ArrayList<int[]> cctvs = new ArrayList<>(); // cctv의 x좌표, y좌표, 종류
	static ArrayList<int[]> directionorder = new ArrayList<>(); // cctv가 가능한 방향 조합
	static int space; // 빈 공간 갯수
	static int[] direction; // 방향 조합 임시 저장용
	static boolean[][] visited;
	static int blindspot = Integer.MAX_VALUE; // 사각지대
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		office = new int[N][M];
		space = 0;
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				office[i][j] = Integer.parseInt(st.nextToken());
				if(office[i][j] == 0) space++; // 입력하면서 빈 공간 개수 세주기
				else if(office[i][j] < 6) { // 빈 공간이고 6 이하인 경우 cctv 정보(x,y좌표, 종류) 저장
					cctvs.add(new int[] {i, j, office[i][j]});
				}
			}
		}
		direction = new int[cctvs.size()]; // 각 cctv의 방향을 정해서 저장할 배열
		
		combination(0); // cctv의 모든 방향 조합 계산
		
		for(int i = 0; i < directionorder.size(); i++) {
			visited = new boolean[N][M];
			count(i); // 각 cctv 방향 조합마다 사각지대 갱신
		}
		
		System.out.println(blindspot); // 출력
	}
	
	public static void combination(int index) {
		if(index == cctvs.size()) { // 모든 cctv의 방향을 결정한 경우
			directionorder.add(direction.clone()); // 방향 조합 저장
			return;
		}

		int k = 0; // k : 가능한 방향 수
		switch(cctvs.get(index)[2]) {
		case 5: // 5번 cctv는 4방향을 다 보는 1가지 경우만 있음
			k = 1;
			break;
		case 2: // 2번 cctv는 수평 or 수직 2가지 경우가 있음
			k = 2;
			break;
		case 1:
		case 3:
		case 4: // 1, 3, 4번 cctv는 4가지 경우가 있음
			k = 4;
			break;
		}
		
		for(int i = 0; i < k; i++) {
			direction[index] = i; // 방향 정하고 다음 cctv 방향 정하러 재귀호출
			combination(index+1);
		}
	}
	
	public static void count(int index) {
		int[] d = directionorder.get(index); // 각 cctv들의 방향 조합 중 1가지 가져옴

		int sum = 0;
		for(int i = 0; i < cctvs.size(); i++) {
			int[] cctv = cctvs.get(i); // 각 cctv의 x, y좌표와 종류 가져오기
			int x = cctv[0];
			int y = cctv[1];
			int spec = cctv[2];
			
			int[] tmp = null; // 방향 지정할 배열
			switch(spec) {
			case 1: // 1번   한 방향
				tmp = new int[] {d[i]};
				break;
			case 2: // 2번  양방향
				tmp = new int[] {d[i], d[i]+2};
				break;
			case 3: // 3번  수직방향
				tmp = new int[] {d[i], (d[i]+1)%4};
				break;
			case 4: // 4번 3방향
				tmp = new int[] {d[i], (d[i]+1)%4, (d[i]+2)%4};
				break;
			case 5: // 5번은 전체
				tmp = new int[] {0, 1, 2, 3};
				break;
			}
			
			for(int e : tmp) {
				switch(e) { // 각 방향따라 감시가 가능한 공간 계산
				case 0:
					sum += countUp(x, y);
					break;
				case 1:
					sum += countLeft(x, y);
					break;
				case 2:
					sum += countDown(x, y);
					break;
				case 3:
					sum += countRight(x, y);
					break;
				}
			}
		}
		// 사각지대 갱신
		blindspot = blindspot < space - sum ? blindspot : space - sum;
	}
	
	public static int countUp(int x, int y) {
		int cnt = 0;
		
		while(true) {
			x--;
			
			if(0 > x || office[x][y] == 6) { // 범위 밖이거나 벽이면 cnt 리턴
				return cnt;
			}

			if(!visited[x][y] && office[x][y] == 0) { // 감시 가능한 빈공간이면 cnt+1
				cnt++;
				visited[x][y] = true;
			}
		}
	}
	
	public static int countLeft(int x, int y) {
		int cnt = 0;
		
		while(true) {
			y--;
			
			if(0 > y || office[x][y] == 6) {
				return cnt;
			}

			if(!visited[x][y] && office[x][y] == 0) {
				cnt++;
				visited[x][y] = true;
			}
		}
	}
	
	public static int countDown(int x, int y) {
		int cnt = 0;
		
		while(true) {
			x++;
			
			if(N == x || office[x][y] == 6) {
				return cnt;
			}

			if(!visited[x][y] && office[x][y] == 0) {
				cnt++;
				visited[x][y] = true;
			}
		}
	}
	
	public static int countRight(int x, int y) {
		int cnt = 0;
		
		while(true) {
			y++;
			
			if(M == y || office[x][y] == 6) {
				return cnt;
			}

			if(!visited[x][y] && office[x][y] == 0) {
				cnt++;
				visited[x][y] = true;
			}
		}
	}
	
}
