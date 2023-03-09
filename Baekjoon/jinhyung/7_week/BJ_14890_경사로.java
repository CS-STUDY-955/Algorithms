package com.baekjoon.g345;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 단순 구현
public class BJ_14890_경사로 {
	
	static int N, L, left, before; // 지도 크기, 경사로 길이, 놓고 있는 경사로의 남은 길이, 직전 좌표의 높이
	static int[][] map; // 지도
	static boolean[] slope; // 경사로 놓은 여부
	static boolean asc, desc; // 올라가는 경사로인지, 내려가는 경사로인지
	static int total = 0; // 갈 수 있는 길 총합
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 지도의 크기
		L = Integer.parseInt(st.nextToken()); // 경사로의 길이
		
		map = new int[N][N]; // 지도
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		cmove(); // 이동 가능한 열 세주기
		rmove(); // 이동 가능한 행 세주기
		
		System.out.println(total); // 출력
	}
	
	// 이동 가능한 열 세주기
	public static void cmove() {
		for(int c = 0; c < N; c++) { // 모든 열에 대해서
			init(); // 각 열마다 쓰일 변수들 초기화
			int before = map[0][c]; // 탐색중인 좌표 바로 직전의 높이를 알기 위해 before 변수 초기화
			for(int r = 1; r < N; r++) { // 1행부터 N행까지
				// 만약 직전의 높이와 현재 높이의 차이가 1 이상이면 갈 수 없다
				if(Math.abs(before - map[r][c]) > 1) break;
				
				// 직전의 높이와 현재 높이가 같은 경우
				if(before == map[r][c]) {
					if(asc || desc) { // 올라가는 중이거나 내려가는 중인 경우 => 경사로 이어서 설치
						int res = put(r); // r행에 경사로 놓기
						if(res == -1) break; // 경사로가 이미 놓여져 있는 경우 => 경사로 설치 불가
						else r += res; // 올라가는 경사로를 다 놓은 경우(res=1) 다음 행을 건너 뛰기 위해 r에 +1
					}
				}
				else { // 직전의 높이와 현재 높이가 1 차이 나는 경우
					if(left != L) break; // 만약 경사로를 놓고 있는 중인 경우 => 경사로 설치 불가
					if(before > map[r][c]) desc = true; // 직전 높이가 현재 높이보다 높은 경우 => 내려가는 경사로 설치
					else {
						asc = true; // 직전 높이가 현재 높이보다 낮은 경우 => 올라가는 경사로 설치
						r -= L; // 올라가는 경사로를 설치하기 위해 경사로 길이 만큼 돌아가기
						if(r < 0) break; // 맵 밖으로 나가야 한다면 => 경사로 설치 불가
					}
					int res = put(r); // 경사로 놓기
					if(res == -1) break;
					else r += res;
				}
				
				before = map[r][c]; // 직전 높이 갱신
				if(r == N - 1 && left == L) total++; // 끝까지 갔고 경사로를 놓는 중이 아니라면 => 갈 수 있다
			}
		}
	}
	
	// 이동 가능한 행 세주기
	public static void rmove() {
		for(int r = 0; r < N; r++) {
			init();
			int before = map[r][0];
			for(int c = 1; c < N; c++) {
				if(Math.abs(before - map[r][c]) > 1) break;
				
				if(before == map[r][c]) {
					if(asc || desc) {
						int res = put(c);
						if(res == -1) break;
						else c += res;
					}
				}
				else {
					if(left != L) break;
					if(before > map[r][c]) desc = true;
					else {
						asc = true;
						c -= L;
						if(c < 0) break;
					}
					int res = put(c);
					if(res == -1) break;
					else c += res;
				}
				
				before = map[r][c];
				if(c == N - 1 && left == L) total++;
			}
		}
	}
	
	static public void init() {
		slope = new boolean[N]; // 경사로가 놓였는지 여부
		asc = false; // 올라가는 경사로를 놓는 중인지?
		desc = false; // 내려가는 경사로를 놓는 중인지?
		left = L; // 놓고있는 경사로의 남은 길이
	}
	
	// 경사로 놓기
	static public int put(int x) {
		if(slope[x]) return -1; // 이미 경사로가 설치된 경우 놓을 수 없음 => -1 리턴

		slope[x] = true; // 경사로 놓아주기
		if(--left == 0) { // 경사로의 남은 길이를 빼주는데 0인 경우 => 경사로 다 놓았음
			left = L; // 남은 경사로 초기화
			desc = false; // desc 초기화
			if(asc) { // 올라가는 경사로였다면
				asc = false; // asc 초기화
				return 1; // 1 리턴
			}
		}
		return 0; // 경사로가 남았거나 내려가는 경사로를 다 놓은 경우는 0 리턴
	}
}
