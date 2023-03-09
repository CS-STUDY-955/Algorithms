package com.baekjoon.g345;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

// 조합과 BFS
public class BJ_17142_연구소3 {
	
	static int N, M, v = 0, space, min = Integer.MAX_VALUE;
	static int[][] map, virus = new int[10][2];
	static int[] selected = new int[10];
	static boolean[][] visited;
	
	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {1, 0, -1, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // 연구소 가로세로 길이
		M = Integer.parseInt(st.nextToken()); // 선택할 바이러스 수
		
		map = new int[N][N]; // 연구소 지도
		space = N*N; // 빈 공간
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 2) { // 값이 2라면 바이러스
					virus[v++] = new int[] {i, j}; // 바이러스 추가하기
				}
				else if(map[i][j] == 1) { // 값이 1이라면 벽
					space--; // 빈 공간 개수에서 벽 개수 빼주기
				}
			}
		}
		space -= v; // 빈 공간 개수에서 바이러스 개수 빼주기
		
		selectVirus(0, 0); // 바이러스 고르기(조합)
		
		System.out.println(min==Integer.MAX_VALUE?-1:min);
	}
	
	public static void selectVirus(int cnt, int start) {
		if(cnt == M) { // M개를 골랐다면
			visited = new boolean[N][N]; // visited 초기화
			spread(); // 퍼뜨리기(BFS)
			return;
		}
		
		for(int i = start; i < v; i++) {
			selected[cnt] = i;
			selectVirus(cnt+1, i+1);
		}
	}
	
	// BFS
	public static void spread() {
		ArrayDeque<int[]> deque = new ArrayDeque<>(); // 큐 선언
		for(int i = 0; i < M; i++) { // 고른 바이러스들 큐에 넣어주기
			int[] vv = virus[selected[i]];
			int x = vv[0];
			int y = vv[1];
			deque.addLast(new int[] {x, y, 0}); // 좌표와 걸린 시간
			visited[x][y] = true; // 방문 표시
		}
		int left = space; // 퍼뜨려야하는 남은 빈공간
		
		if(left == 0) { // 빈공간이 처음부터 없는 경우 처리
			min = 0;
			return;
		}
		while(!deque.isEmpty()) { // BFS
			int[] vv = deque.poll();
			int x = vv[0];
			int y = vv[1];
			
			for(int i = 0; i < 4; i++) { // 4방 탐색
				int nx = x + dx[i];
				int ny = y + dy[i];
				
				if(immovable(nx, ny)) continue; // 갈 수 없는 곳이라면 패스
				if(min < vv[2]+1) return; // 최소값보다 크다면 종료
				if(map[nx][ny] == 0) left--; // 빈 공간이라면 빈공간-1
				deque.addLast(new int[] {nx, ny, vv[2]+1}); // 큐에 넣어주기
				if(left == 0) { // 빈 공간이 없다면
					min = min < deque.getLast()[2] ? min : deque.getLast()[2]; // 최소값 갱신
					return; // 종료
				}
				visited[nx][ny] = true; // 방문 표시
			}
		}
	}
	
	public static boolean immovable(int x, int y) {
		return (x < 0 || x >= N || y < 0 || y >= N || visited[x][y] || map[x][y] == 1);
	}
}