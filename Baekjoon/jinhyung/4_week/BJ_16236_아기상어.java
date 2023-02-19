package com.baekjoon.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 아기상어가 먹을수 있는 먹이를 가까운 순으로, 위에서부터, 왼쪽에서 오른쪽으로 탐색
// 먹을 수 있는 먹이를 아기상어 기준으로 잡는다..
// 갈수 있는 거리를 아기상어 기준으로 계산
// 너비우선 탐색?

/**
 * BJ 16236. 아기상어
 * 
 * @author 양진형
 */
public class BJ_16236_아기상어 {

	static int N; // 맵의 크기 (N * N)
	static int[][] map; // 보드
	static int sharkSize = 2; // 아기 상어의 몸무게
	static int sharkSatisfying = 0; // 아기 상어가 먹은 물고기 수
	static int[] cur = new int[3]; // 아기 상어의 현재 위치(0, 1), 이동 거리(2)
	static final int[] dx = {-1, 0, 0, 1}; // 북 서 동 남
	static final int[] dy = {0, -1, 1, 0};
	static PriorityQueue<int[]> moveQ = new PriorityQueue<int[]>(new Comparator<int[]>() {
		@Override
		public int compare(int[] o1, int[] o2) { // 오름차순으로 큐 정렬
			if(o1[2] != o2[2]) return o1[2] - o2[2]; // 거리로 비교
			else if (o1[0] != o2[0]) return o1[0] - o2[0]; // 행으로 비교
			else return o1[1] - o2[1]; // 열로 비교
		}
	}); // 너비 우선 탐색을 위한 큐
	static int totalTime = 0; // 총 걸린 시간
	static int[] fishes = new int[6]; // 물고기들의 크기별 숫자
	static boolean[][] visited; // 방문한 경로 표시
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine()); // 맵의 크기, 2 <= N <= 20
		map = new int[N][N]; // 맵
		visited = new boolean[N][N]; // 방문 여부
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken()); // 맵 입력
				switch(map[i][j]) {
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
					fishes[map[i][j] - 1]++; // 물고기인경우 해당 인덱스에 + 1
					break;
				case 9: // 아기 상어의 처음 위치 초기화
					cur[0] = i;
					cur[1] = j;
					break;
				case 0:
					break;
				}
			}
		}
		cur[2] = 0; // 이동 거리 초기화
		map[cur[0]][cur[1]] = 0; // 아기 상어의 위치 0으로
		searchFish(cur); // 탐색 시작
		
		System.out.println(totalTime); // 총 걸린 시간 출력
	}
	
	// BFS
	static void searchFish(int[] position) {
		visited[position[0]][position[1]] = true; // 현재 위치 방문 표시
		position[2]++; // 거리 1 증가
		int[][] nextPositions = new int[4][3]; // 북서동남 큐에 저장
		for(int i = 0; i < 4; i++) {
			int[] nextPosition = position.clone();
			nextPosition[0] += dx[i];
			nextPosition[1] += dy[i];
			nextPositions[i] = nextPosition;
			// 위치가 갈 수 있고 아직 방문하지 않았다면 큐에 추가 후 방문 표시
			if(movable(nextPosition) && !visited[nextPosition[0]][nextPosition[1]]) {
				moveQ.add(nextPosition);
				visited[nextPosition[0]][nextPosition[1]] = true;
			}
		}
		
		// 큐에 원소가 있는동안
		while(!moveQ.isEmpty()) {
			int[] nextPosition = moveQ.poll(); // 탐색할 위치
			if(eatable(nextPosition)) { // 위치에 먹을 수 있는 물고기가 있다면
				cur[0] = nextPosition[0]; // 아기 상어의 위치를 그 위치로 바꿔줌
				cur[1] = nextPosition[1];
				if(++sharkSatisfying == sharkSize) { // 상어가 먹은 물고기 + 1, 성장 가능하면 성장
					sharkSize++;
					sharkSatisfying = 0;
				}
				totalTime += nextPosition[2]; // 총 걸린 시간에 이동 시간 더하기
				cur[2] = 0; // 이동 시간 초기화
				fishes[map[cur[0]][cur[1]] - 1]--; // 먹은 물고기 초기화
				map[cur[0]][cur[1]] = 0; // 물고기가 있던 위치 0으로 초기화
				moveQ.clear(); // Q 초기화
				visited = new boolean[N][N]; // visited 초기화
				boolean eatFlag = true; // 맵에 먹을 수 있는 물고기가 남아 있는지 체크
				for(int fs = 0; fs < sharkSize - 1; fs++) {
					if(fs == fishes.length) { // 먹을 수 있는 물고기가 없는 경우
						eatFlag = false;
						break;
					}
					if(fishes[fs] != 0) { // 먹을 수 있는 물고기가 있는 경우
						break;
					}
				}
				
				if(!eatFlag) return; // 먹을수 있는 물고기가 없는 경우 종료
				else searchFish(cur); // 있다면 현재 위치에서 다시 탐색
			}
			else {
				searchFish(nextPosition); // 먹을수 있는 물고기가 없다면 탐색
			}
		}
	}
	
	static boolean movable(int[] nextPos) { // nextPos가 갈수 있는 위치인지?
		if(0 <= nextPos[0] && nextPos[0] < N && 0 <= nextPos[1] && nextPos[1] < N &&
				map[nextPos[0]][nextPos[1]] <= sharkSize)
			return true;
		return false;
	}
	
	static boolean eatable(int[] nextPos) { // nextPos에 있는 물고기를 먹을 수 있는지?
		if(0 < map[nextPos[0]][nextPos[1]] && map[nextPos[0]][nextPos[1]] < sharkSize) 
			return true;
		return false;
	}

}
