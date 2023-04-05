package com.baekjoon.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

// 단순 구현 문제인 것 같다.
public class BJ_15685_드래곤커브 {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int N = Integer.parseInt(br.readLine()); // 드래곤 커브의 수
		
		int[][] curves = new int[N][4]; // 드래곤 커브의 시작점, 시작 방향, 세대
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < 4; j++) {
				curves[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		boolean[][] field = new boolean[101][101]; // 드래곤 커브들에 속한 점을 표시
		
		for(int[] curve : curves) { // 각 커브마다 반복
			ArrayList<int[]> points = new ArrayList<>(); // 현재 드래곤 커브의 점들 저장
			int x = curve[0]; // x좌표
			int y = curve[1]; // y좌표
			int direction = curve[2]; // 시작 방향
			int left = curve[3]; // 세대
			
			field[x][y] = true; // 시작 점 표시
			points.add(new int[] {x, y}); // 점 추가
			switch(direction) { // 시작 방향 별 좌표 조작
			case 0:
				x++;
				break;
			case 1:
				y--;
				break;
			case 2:
				x--;
				break;
			case 3:
				y++;
				break;
			}
			
			field[x][y] = true; // 다음 점 표시 (0세대)
			points.add(new int[] {x, y}); // 다음 점 추가
			left--; // 세대 - 1
			while(left >= 0) { // 남은 세대가 0 이상인 동안
				int[] end = points.get(points.size() - 1); // 기준이 되는 끝 점은 마지막에 배열에 넣은 점
				for(int i = points.size() - 2; i >= 0; i--) { // 끝 점은 변동 없으므로 빼고 끝에서 두번째 점부터 역순으로 (항상 시작 점을 돌린 점이 끝 점)
					int tx = points.get(i)[0] - end[0]; // x, y의 기준을 끝 점으로 맞춰줌
					int ty = points.get(i)[1] - end[1];
					
					// 반시계로 돌리기
					// 문제에선 시계방향이지만, 코드에선 x, y가 문제와 반대이므로(x가 세로, y가 가로) 반시계로 돌려줘야함
					x = -ty;
					y = tx;
					
					x += end[0]; // 원래대로 좌표 복원
					y += end[1];
					
					field[x][y] = true; // 돌린 점 표시
					points.add(new int[] {x, y}); //배열에 넣어줌
				}
				
				left--; // 남은 세대 - 1
			}
		}
		int ans = 0;
		for(int i = 0; i < 100; i++) { // 배열 전체 탐색
			for(int j = 0; j < 100; j++) {
				if(field[i][j]) { // i, j 좌표가 true로 표시 되어 있다면 
					if(field[i+1][j] && field[i][j+1] && field[i+1][j+1]) { // 1x1 정사각형을 이루는 나머지 점도 드래곤 커브의 일부인지 검사
 						ans++; // 맞다면 ans+1
					}
				}
			}
		}
		
		System.out.println(ans);
	}
}
