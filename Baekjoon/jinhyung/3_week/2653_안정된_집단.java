package com.baekjoon.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

/**
 * 풀기전
 * 1. n은 최대 100 -> 왠만해선 시간초과 안나겠다.
 * 2. 모든 경우의 수 탐색하기..
 * 3. 소집단을 출력해야함 -> 소집단으로 묶어놓기
 * 
 * @author 양진형
 */
public class BJ_2653_안정된_집단 {
	/* ** 풀기 전 풀이법
	 * 0부터 n-1까지 완전 탐색
	 * visited로 체크
	 * 각 노드 별 0인 노드들을 확인 => 일단 같은 소집단으로 묶음
	 * 소집단 내 다른 노드를 순회하며 다른 노드와 0이 있으면 불안정
	 * 모두 안정한 소집단이라면 그룹내 노드들 visited = true로 변경
	 */

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine()); // 사람들의 수
		
		int[][] map = new int[n][n]; // 관계 그래프
		for(int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		ArrayList<ArrayList<Integer>> group = new ArrayList<>(); // 집단 내 모든 소집단
		
		boolean onlyOne = false; // 안정된 그룹이면 true
		// 집단 만들기 알고리즘
		for(int i = 0; i < n; i++) {
			ArrayList<Integer> smallGroup = new ArrayList<>();
			for(int j = 0; j < n; j++) {
				if(map[i][j] == 1) continue; // 서로 싫어하면 패스
				smallGroup.add(j); // 서로 좋아하면 소집단에 추가
			}
			if(smallGroup.size() == 1) {
				onlyOne = true; // 규모가 한 명인 소집단이 있는 경우 불안정한 집단
				break;
			}
			if(!group.contains(smallGroup)) // 현재 집단 안에 동일한 소집단이 없다면
				group.add(smallGroup); // 생성한 소집단 추가
		}
		
		int peopleCnt = 0; // 각 소집단에 속한 사람 수
		for(ArrayList<Integer> smallGroup : group) {
			peopleCnt += smallGroup.size();
		}
		// 사람들이 각각 독립된 집단에 속해있고 한 명인 소집단이 없는 경우
		if(peopleCnt == n && !onlyOne) {
			sb.append(group.size()).append("\n");
			for(ArrayList<Integer> smallGroup : group) {
				for(int people : smallGroup) {
					sb.append(people+1).append(" ");
				}
				sb.append("\n");
			}
			System.out.println(sb.toString());
		}
		else
			System.out.println(0);
	}
}
