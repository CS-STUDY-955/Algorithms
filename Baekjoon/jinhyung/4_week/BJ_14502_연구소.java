package com.baekjoon.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

// 최대 62C3 => 37820

public class BJ_14502_연구소 {
	
	static int N, M; // 연구소의 행과 열
	static int[][] map; // 연구소 지도
	static ArrayList<int[]> space; // 빈 공간의 좌표 
	static ArrayList<int[]> virus; // 바이러스의 좌표
	static ArrayList<int[][]> comb; // 벽 3개를 세우는 가능한 모든 조합
	static Stack<Integer> selected; // 선택한 벽 3개를 저장할 스택
	static int maxsafe = Integer.MIN_VALUE; // 안전 구역의 최대값
	
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, -1, 0, 1};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N+2][M+2]; // 1만큼 패딩해서 값 입력 받기
		space = new ArrayList<>();
		virus = new ArrayList<>();
		for(int i = 0; i < N + 2; i++) {
			if(i == 0 || i == N + 1) {
				for(int j = 0; j < M + 2; j++) {
					map[i][j] = 1; // 패딩한 위치 1로 초기화
				}
				continue;
			}
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M + 2; j++) {
				if(j == 0 || j == M + 1) {
					map[i][j] = 1; // 패딩한 위치 1로 초기화
					continue;
				}
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 0) {
					int[] point = {i, j}; 
					space.add(point); // 빈 공간의 좌표 저장
				}
				else if(map[i][j] == 2) {
					int[] point = {i ,j};
					virus.add(point); // 바이러스의 좌표 저장
				}
			}
		}
		comb = new ArrayList<>();
		selected = new Stack<>();
		
		getC(0); // 모든 가능한 조합 뽑기
		
		for(int i = 0; i < comb.size(); i++) {
			spread(i); // 각 경우의 수에서 바이러스 퍼지기 BFS
		}
		
		System.out.println(maxsafe); // 출력
	}
	
	// 가능한 조합 뽑기
	static void getC(int start) {
		if(selected.size() == 3) {
			int[][] res = {space.get(selected.get(0)), space.get(selected.get(1)), space.get(selected.get(2))};
			comb.add(res); // 벽들의 좌표 조합을 만들어 저장
			return;
		}
		
		for(int i = start; i < space.size(); i++) {
			selected.push(i); // 인덱스 저장
			getC(i+1);
			selected.pop(); // 인덱스 사용 후 제거
		}
	}
	
	// 가능한 벽 조합들에 대해 BFS
	static void spread(int idx) {
		for(int[] point : comb.get(idx)) {
			map[point[0]][point[1]] = 1; // 선택한 빈 공간을 1로 바꿔줌
		}
		
		Queue<int[]> vqueue = new LinkedList<>();
		boolean[][] visited = new boolean[N+2][M+2];
		int count = space.size() + virus.size() - 3; // 안전 구역 카운트
		for(int[] vpoint : virus) { // 맵의 모든 좌표에 있는 바이러스마다 반복
			vqueue.add(vpoint);
			while(!vqueue.isEmpty()) {
				int[] point = vqueue.poll();
				count--; // 바이러스 하나당 안전구역 -1
				for(int i = 0; i < 4; i++) { // 4방 탐색
					int tmpX = point[0] + dx[i];
					int tmpY = point[1] + dy[i];
					
					// 빈 공간이고 방문하지 않았다면 visited=true로 하고 공간 저장
					if(map[tmpX][tmpY] == 0 && !visited[tmpX][tmpY]) {
						visited[tmpX][tmpY] = true;
						int[] tmp = {tmpX, tmpY};
						vqueue.add(tmp);
					}
				}
			}
		}
		maxsafe = maxsafe > count ? maxsafe : count; // 최대값 갱신
		
		for(int[] point : comb.get(idx)) {
			map[point[0]][point[1]] = 0; // 벽 해제
		}
	}
	
}
