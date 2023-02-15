package com.baekjoon.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * https://www.acmicpc.net/problem/14500
 * BJ 14500. 테트로미노
 * 
 * 누적합을 이용해야 할 것 같다.
 * 최대 500 * 500 => 250,000 / 어떤 수 하나가 포함되는 경우의 수는 최대 8+16+16... 좀 많음;;;
 * 완전탐색인데 백트래킹 조건을 잘 구현해야 할듯
 * 4*4로 쪼갠 뒤 구하는 방법 => 4*4당 8+9+48+... 너무 많음
 * 노드 하나를 잡고 그리디 알고리즘으로 하면 될듯(4방탐색)
 * ---------------------------------------------
 * 노드 하나 끝나면 visited = true로 바꿔서 다음 탐색에서 제외하기
 * 갈 수 있는 노드들 중 최댓값들을 큐에 저장하고 너비 우선 탐색으로 구현하기
 * ---------------------------------------------
 * 그리디 안됨(값이 낮은 수들에 의해 최대값으로 가는 경로가 막힐 수 있음)
 * 결국 BFS 완전탐색인듯
 * ---------------------------------------------
 * 깊이우선탐색인듯함(BFS로 구현하려니 중복 가능 해서 방문 처리가 복잡한것같음)
 * 
 * * 조합인듯함
 * 
 * @author 양진형
 */
public class BJ_14500_테트로미노 {
	
	static int[] dx = {1, 0, -1, 0};
	static int[] dy = {0, 1, 0, -1};
	static boolean[][] selected;
	static int[][] board;
	static int max;
	static int N, M;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		board = new int[N][M];
		selected = new boolean[N][M];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		max = Integer.MIN_VALUE;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				selected[i][j] = true;
				getMax2(i, j, 0, 0);
				selected[i][j] = false;
				
				int sum = 0;
				if(j < M - 1) {
					if(j > 0) {
						if(i > 0) {
							sum = board[i][j] + board[i][j+1] + board[i][j-1] + board[i-1][j];
							max = max > sum ? max : sum;
						}
						if(i < N - 1) {
							sum = board[i][j] + board[i][j+1] + board[i][j-1] + board[i+1][j];
							max = max > sum ? max : sum;
						}
					}
					if(i > 0 && i < N - 1) {
						sum = board[i][j] + board[i-1][j] + board[i+1][j] + board[i][j+1];
						max = max > sum ? max : sum;
					}
				}
				if(j > 0 && i > 0 && i < N - 1) {
					sum = board[i][j] + board[i-1][j] + board[i][j-1] + board[i+1][j];
					max = max > sum ? max : sum;
				}
				
			}
		}
		System.out.println(max);
	}

	static void getMax2(int x, int y, int cnt, int sum) {
		sum += board[x][y];
		if(cnt == 3) {
			max = max > sum ? max : sum;
			return;
		}

		for(int i = 0; i < 4; i++) {
			int tmpX = x + dx[i];
			int tmpY = y + dy[i];
			
			if(tmpX < 0 || tmpX >= N || tmpY < 0 || tmpY >= M || selected[tmpX][tmpY]) {
				continue;
			}

			selected[tmpX][tmpY] = true;
			getMax2(tmpX, tmpY, cnt+1, sum);
			selected[tmpX][tmpY] = false;
		}
	}
}
