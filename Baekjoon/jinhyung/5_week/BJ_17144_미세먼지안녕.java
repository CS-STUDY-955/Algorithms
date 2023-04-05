package com.baekjoon.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

// 단순 구현 문제인것 같다.
// 시간이 최대 1000인게 약간 걸리지만 일단 풀고 생각
public class BJ_17144_미세먼지안녕 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int[] dx = {-1, 0, 1, 0};
		int[] dy = {0, 1, 0, -1};
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int R = Integer.parseInt(st.nextToken()); // 행
		int C = Integer.parseInt(st.nextToken()); // 열
		int T = Integer.parseInt(st.nextToken()); // 시간
	
		int[][] map = new int[R][C];
		int[] purifier = null; // 공기 청정기의 row 좌표
		for(int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < C; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(purifier == null && map[i][j] == -1)
					purifier = new int[] {i, i+1}; // 공기 청정기의 row 좌표 저장
			}
		}
		
		while(T > 0) { // 남은 시간이 있는 동안
			HashMap<int[], int[]> spread = new HashMap<int[], int[]>(); // 각 좌표의 먼지 변화량 저장
			
			for(int i = 0; i < R; i++) { // 전체 좌표 탐색
				for(int j = 0; j < C; j++) {
					if(map[i][j] >= 5) { // 해당 좌표의 먼지가 5 이상인 경우
						int[] point = {i, j}; // 해당 좌표
						int[] change = new int[5]; // 현재, 위, 오른쪽, 아래, 왼쪽 변동값
						
						int spreaddust = map[i][j] / 5; // 4방으로 퍼질 먼지량
						int spreaded = 0; // 먼지가 퍼질 방향 수
						for(int k = 0; k < 4; k++) {
							int nextR = i + dx[k]; // 상하좌우 탐색
							int nextC = j + dy[k];
							
							// 범위 밖이거나 공기청정기인 경우
							if(0 > nextR || R <= nextR || 0 > nextC || C <= nextC || map[nextR][nextC] == -1) {
								change[k+1] = 0; // 변화량 0
								continue;
							}
							
							change[k+1] = spreaddust; // 범위 안이라면 그쪽 방향에 먼지가 퍼짐
							spreaded++; // 방향 +1
						}
						
						change[0] = -spreaddust * spreaded; // 4방으로 퍼진 먼지만큼 해당 좌표의 먼지 마이너스
						spread.put(point, change); // 좌표와 변화량 저장
					}
				}
			}
			
			for(Entry<int[], int[]> s : spread.entrySet()) { // 먼지가 5 이상인 각 좌표마다
				int[] point = s.getKey();
				int[] change = s.getValue();
				map[point[0]][point[1]] += change[0]; // 현재 좌표에서 뺄만큼 빼주기
				for(int i = 0; i < 4; i++) { // 4방에 더할만큼 더해주기
					if(change[i+1] == 0) continue;
					map[point[0] + dx[i]][point[1] + dy[i]] += change[i+1];
				}
			}
			
			int d = 0; // 위쪽 사이클
			int r = purifier[0] + dx[d]; // 윗쪽 공기청정기의 x좌표
			int c = 0 + dy[d]; // 윗쪽 공기청정기의 y좌표
			while(true) {
				int nextR = r + dx[d]; // 해당 방향으로 이동
				int nextC = c + dy[d];
				if(0 > nextR || C <= nextC || nextR > purifier[0]) { // 다음좌표가 범위 밖이라면
					d++; // 방향 전환
					continue;
				}
				if(map[nextR][nextC] == -1) { // 다음 좌표가 공기청정기라면
					map[r][c] = 0; //해당 위치 먼지 0으로 하고 반복 탈출
					break;
				}
				map[r][c] = map[nextR][nextC]; // 다음 좌표의 먼지를 현재 좌표로 옮김
				r = nextR; // 좌표 갱신
				c = nextC;
			}
			
			d = 2; // 아래쪽 사이클
			r = purifier[1] + dx[d];
			c = 0 + dy[d]; 
			while(true) {
				int nextR = r + dx[d];
				int nextC = c + dy[d];
				if(R <= nextR || C <= nextC || nextR < purifier[1]) {
					d--;
					if(d == -1) d = 3;
					continue;
				}
				if(map[nextR][nextC] == -1) {
					map[r][c] = 0;
					break;
				}
				map[r][c] = map[nextR][nextC];
				r = nextR;
				c = nextC;
			}

			T--; // 시간 - 1
		}
		
		// 맵에 남은 먼지 세주기
		int leftdust = 0;
		for(int i = 0; i < R; i++) {
			for(int j = 0; j < C; j++) {
				if(map[i][j] > 0) leftdust += map[i][j];
			}
		}
		
		System.out.println(leftdust);
	}

}
