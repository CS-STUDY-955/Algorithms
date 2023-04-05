package com.baekjoon.g345;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 조합
public class BJ_15684_사다리조작 {
	
	static int N, M, H, min = -1;
	static boolean[][] rowlineOrigin, tmprowline;
	static boolean end = false;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // 세로선 개수
		M = Integer.parseInt(st.nextToken()); // 가로선 개수
		H = Integer.parseInt(st.nextToken()); // 가로선을 놓을 수 있는 위치의 개수
		
		rowlineOrigin = new boolean[H][N-1]; // 원래 놓여있던 가로선 배열 
		for(int i = 0; i < M; i++) { // 가로선 입력 받기
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			rowlineOrigin[r][c] = true;
		}
		
		for(int i = 0; i <= 3; i++) { // 가로선 추가하기 (i : 추가할 가로선 개수)
			tmprowline = new boolean[H][N-1]; // 사용할 가로선 배열
			for(int r = 0; r < H; r++) {
				tmprowline[r] = rowlineOrigin[r].clone(); // 각 행 복사
			}
			makeline(i, 0, 0, 0); // 사다리 조작하기
			if(end) break; // 조건 완료 하면 탈출
		}
		System.out.println(min); // 최소값 출력
	}
	
	// 조합(근데 중복이 좀 있는 것 같음)
	public static void makeline(int n, int cnt, int is, int js) {
		if(cnt == n) {
			end = result(n); // n개 골랐다면 사다리 타기
			return;
		}
		
		for(int i = is; i < H; i++) { // 2차원 배열 돌면서 i랑 j 돌아주기
			for(int j = js; j < N-1; j++) {
				if(tmprowline[i][j]) continue; // 이미 가로선이 있다면 패스
				tmprowline[i][j] = true; // 가로선 놓기
				makeline(n, cnt+1, is, js+1); // 가로선 놓은 수+1, 탐색 시작 방향 열+1해서 사다리 만들기
				tmprowline[i][j] = false; // 가로선 빼기
				if(end) break; // 끝났다면 탈출
			}
			if(end) break; // 끝났다면 탈출
			js = 0; // 열을 다 탐색한 경우 열 0으로 만들기
		}
	}
	
	public static boolean result(int n) {
		for(int start = 0; start < N; start++) { // 각 열 시작
			int r = 0; // 0행
			int c = start; // start열에서 시작
			while(r < H) { // 마지막 행까지
				if(c < N - 1 && tmprowline[r][c]) { // 마지막 열이 아니고 오른쪽 가로선이 있다면
					c++; // 오른쪽으로 가기 >>
				}
				else if(c > 0 && tmprowline[r][c-1]) { // 첫 열이 아니고 왼쪽 가로선이 있다면
					c--; // << 왼쪽으로 가기
				}
				
				r++; // 다음행
			}
			if(c != start) return false; // 도착열이 시작열이 아니라면 false 반환
		}
		// 모든 열이 같은 열로 내려온 경우
		min = n; // 최소값 갱신
		return true; // true 반환 후 종료하기
	}
}