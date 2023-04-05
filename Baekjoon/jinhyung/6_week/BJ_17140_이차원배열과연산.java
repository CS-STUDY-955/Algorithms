package com.baekjoon.g345;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class BJ_17140_이차원배열과연산 {
	
	static int[][] arr; // 배열
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int r = Integer.parseInt(st.nextToken()); // 목표 값 행
		int c = Integer.parseInt(st.nextToken()); // 목표 값 열
		int k = Integer.parseInt(st.nextToken()); // 목표 숫자
		
		arr = new int[3][3]; // 3x3 배열 입력
		for(int i = 0; i < 3; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < 3; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int time = 0; // 걸린 시간
		while(true) {
			if(arr.length >= r && arr[0].length >= c && arr[r-1][c-1] == k) break; // 크기가 r이상, c이상이고 목표값을 찾았다면 탈출
			time++; // 시간 +1
			if(time > 100) { // 시간이 100 초과라면
				time = -1; // 시간 -1로 하고 탈출
				break;
			}
			
			if(arr.length >= arr[0].length) rCalc(); // 행 길이가 열 길이 이상이라면 행 정렬 연산
			else cCalc(); // 열 길이가 더 크다면 열 정렬 연산
		}
		
		System.out.println(time); // 출력
//		print();
	}
	
	public static void rCalc() {
		ArrayList<ArrayList<int[]>> lists = new ArrayList<>(); // 임시 리스트
		
		int len = 0; // 정렬 후 최대 길이
		for(int i = 0; i < arr.length; i++) { // 각 행에 대해 반복
			ArrayList<int[]> list = new ArrayList<>(); // 임시 리스트
			HashMap<Integer, Integer> tmp = new HashMap<>(); // 숫자와 등장 횟수를 저장할 임시 맵
			for(int j = 0; j < arr[0].length; j++) {
				if(arr[i][j] == 0) continue; // 0은 정렬 대상에서 제외한다
				if(!tmp.containsKey(arr[i][j])) // 처음 등장하는 숫자라면
					tmp.put(arr[i][j], 1); // 맵에 넣고 밸류는 1로 초기화
				else // 이미 등장했던 숫자라면
					tmp.put(arr[i][j], tmp.get(arr[i][j]) + 1); // 밸류+1 해서 맵에 넣기
			}
			for(Entry<Integer, Integer> e : tmp.entrySet()) { // 모든 숫자와 등장 횟수를
				list.add(new int[] {e.getKey(), e.getValue()}); // 임시 리스트에 추가하기
			}
			
			list.sort(new Comparator<int[]>() { // 임시 리스트를 등장 횟수로 오름차순, 같으면 숫자 순으로 오름차순
				@Override
				public int compare(int[] o1, int[] o2) {
					if(o1[1] == o2[1]) return o1[0] - o2[0];
					return o1[1] - o2[1];
				}
			});
			
			len = len > list.size() ? len : list.size(); // 최대 길이 갱신
			lists.add(list); // 임시 리스트에 추가
		}

		len = len <= 50 ? len : 50; // 최대 길이가 50 초과라면 50으로 한다
		arr = new int[arr.length][len*2]; // 현재 행x(최대길이*2) 배열 생성
		for(int i = 0; i < arr.length; i++) { // 모든 임시 리스트에 반복
			for(int j = 0; j < len; j++) { // 최대 길이만큼 반복하면서
				if(j < lists.get(i).size()) { // j가 현재 리스트의 길이보다 작다면
					arr[i][j*2] = lists.get(i).get(j)[0]; // 숫자 넣기
					arr[i][j*2+1] = lists.get(i).get(j)[1]; // 등장 횟수 넣기
				}
				else { // 현재 리스트의 길이를 초과한다면(원소가 없는 경우)
					arr[i][j*2] = 0; // 0을 넣어줌
					arr[i][j*2+1] = 0;
				}
			}
		}
	}
	
	public static void cCalc() { // 열 정렬 연산
		ArrayList<ArrayList<int[]>> lists = new ArrayList<>();
		
		int len = 0;
		for(int i = 0; i < arr[0].length; i++) {
			ArrayList<int[]> list = new ArrayList<>();
			HashMap<Integer, Integer> tmp = new HashMap<>();
			for(int j = 0; j < arr.length; j++) {
				if(arr[j][i] == 0) continue;
				if(!tmp.containsKey(arr[j][i]))
					tmp.put(arr[j][i], 1);
				else
					tmp.put(arr[j][i], tmp.get(arr[j][i]) + 1);
			}
			for(Entry<Integer, Integer> e : tmp.entrySet()) {
				list.add(new int[] {e.getKey(), e.getValue()});
			}
			
			list.sort(new Comparator<int[]>() {
				@Override
				public int compare(int[] o1, int[] o2) {
					if(o1[1] == o2[1]) return o1[0] - o2[0];
					return o1[1] - o2[1];
				}
			});
			
			len = len > list.size() ? len : list.size();
			lists.add(list);
		}

		len = len <= 50 ? len : 50;
		arr = new int[len*2][arr[0].length];
		for(int i = 0; i < arr[0].length; i++) {
			for(int j = 0; j < len; j++) {
				if(j < lists.get(i).size()) {
					arr[j*2][i] = lists.get(i).get(j)[0];
					arr[j*2+1][i] = lists.get(i).get(j)[1];
				}
				else {
					arr[j*2][i] = 0;
					arr[j*2+1][i] = 0;
				}
			}
		}
	}
	
	public static void print() { // 배열 출력
		for(int[] ar: arr) {
			for(int a: ar) {
				System.out.print(String.format("%2d ", a));
			}
			System.out.println();
		}
	}
}
