package com.baekjoon.g345;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_17779_게리맨더링2 {
	
	static int N, ans = Integer.MAX_VALUE, total = 0;
	static int[][] map;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine()); // 맵 길이
		map = new int[N][N]; // 인구 지도
		
		// 입력 받기
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				total += map[i][j]; // 전체 인구수
			}
		}
		
		// 1열 ~ N-2열, 0행 ~ N-3행에서 가능한 d1, d2 조합에 대해 divide 메서드 호출
		for(int c = 1; c < N - 1; c++) {
			for(int r = 0; r < N - 2; r++) {
				for(int d1 = 1; d1 <= c; d1++) {
					for(int d2 = 1; d2 < N-c; d2++) {
						if(r + d1 + d2 < N)
							divide(r, c, d1, d2);
					}
				}
			}
		}
		
		System.out.println(ans);
	}
	
	public static void divide(int x, int y, int d1, int d2) {
		// 각 구역의 인구수
		int[] counts = {0, 0, 0, 0, 0};
		
		// 5 구역보다 상단의 1, 2 구역 인구수 구하기
		for(int r = 0; r < x; r++) {
			for(int c = 0; c <= y; c++) {
				counts[0] += map[r][c];
			}
			for(int c = y+1; c < N; c++) {
				counts[1] += map[r][c];
			}
		}
		
		// 5 구역보다 하단의 3, 4 구역 인구수 구하기 
		for(int r = x + d1 + d2 + 1; r < N; r++) {
			for(int c = 0; c < y - d1 + d2; c++) {
				counts[2] += map[r][c];
			}
			for(int c = y - d1 + d2; c < N; c++) {
				counts[3] += map[r][c];
			}
		}
		
		// 5구역 좌측의 1, 3 구역 인구수 구하기 
		for(int c = 0; c < y - d1; c++) {
			for(int r = x; r < x + d1; r++) {
				counts[0] += map[r][c];
			}
			for(int r = x + d1; r <= x + d1 + d2; r++) {
				counts[2] += map[r][c];
			}
		}
		
		// 5구역 우측의 2, 4 구역 인구수 구하기
		for(int c = y + d2 + 1; c < N; c++) {
			for(int r = x; r <= x + d2; r++) {
				counts[1] += map[r][c];
			}
			for(int r = x + d2 + 1; r <= x + d1 + d2; r++) {
				counts[3] += map[r][c];
			}
		}
		
		// 5 구역 좌측 상단의 1 구역 인구수 구하기
		for(int r = x; r < x + d1; r++) {
			for(int c = y - d1; c < y + x - r; c++) {
				counts[0] += map[r][c];
			}
		}

		// 5 구역 우측 상단의 2 구역 인구수 구하기
		for(int r = x; r < x + d2; r++) {
			for(int c = y + r - x + 1; c <= y + d2; c++) {
				counts[1] += map[r][c];
			}
		}

		// 5 구역 좌측 하단의 3 구역 인구수 구하기
		for(int r = x + d1 + 1; r < x + d1 + d2 + 1; r++) {
			for(int c = y - d1; c <= y - d1 + r - (x + d1 + 1); c++) {
				counts[2] += map[r][c];
			}
		}

		// 5 구역 우측 하단의 4 구역 인구수 구하기
		for(int r = x + d2 + 1; r < x + d1 + d2 + 1; r++) {
			for(int c = y + d2 - (r - (x + d2 + 1)); c <= y + d2; c++) {
				counts[3] += map[r][c];
			}
		}
		
		// 5 구역의 인구수 구하기
		counts[4] = total;
		for(int i = 0; i < 4; i++) {
			counts[4] -= counts[i];
		}

		// 최대값, 최소값 구하기
		int max = counts[0];
		int min = counts[0];
		for(int i = 1; i < 5; i++) {
			if(max < counts[i]) max = counts[i];
			else if(min > counts[i]) min = counts[i];
		}
		
		// 최소 인구 차이 갱신
		ans = ans < max - min ? ans : max - min;
	}
}
