package com.baekjoon.g345;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class BJ_1937_욕심쟁이판다 {
	
	static int N, max;
	static int[][] map, maxmap;
	
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, -1, 0, 1};
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		
		map = new int[N][N]; // 대나무 지도
		// 대나무 내림차순 정렬한 트리
		TreeSet<int[]> tree = new TreeSet<int[]>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				if(o1[2] == o2[2]) return o1[0]==o2[0]?o1[1]-o2[1]:o1[0]-o2[0];
				return o2[2] - o1[2];
			}
		});
		for(int i = 0; i < N; i++) { // 입력받기
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				tree.add(new int[] {i, j, map[i][j]});
			}
		}
		max = 0;
		maxmap = new int[N][N]; // 각 좌표에서 출발시 도달할 수 있는 최대 거리 저장
		while(!tree.isEmpty()) { // 모든 좌표에 대해 최대 거리 구하기
			int[] p = tree.pollFirst();
			getdist(p[0], p[1]);
		}
		
		System.out.println(max);
	}
	
	public static void getdist(int x, int y) {
		int maxt = 0; // 4방 중 최대거리 구하기
		for(int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			
			if(nx < 0 || nx >= N || ny < 0 || ny >= N || map[x][y] >= map[nx][ny])
				continue;
			
			maxt = maxt > maxmap[nx][ny] ? maxt : maxmap[nx][ny];
		}
		
		maxmap[x][y] = maxt + 1; // 값 대입
		max = max > maxmap[x][y] ? max : maxmap[x][y]; // 갱신
	}
}
