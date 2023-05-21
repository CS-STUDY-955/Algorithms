package com.baekjoon.g345;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class BJ_17822_원판돌리기 {
	
	static int N, M, T, count;
	static int[][] disks;
	static int[] heads;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 원판 개수
		M = Integer.parseInt(st.nextToken()); // 각 원판 당 숫자 개수
		T = Integer.parseInt(st.nextToken()); // 돌릴 횟수
		
		disks = new int[N+1][M]; // 원판들
		heads = new int[N+1]; // 12시 방향을 가리키는 숫자의 인덱스
		for(int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				disks[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		count = N * M; // 총 숫자 개수
		for(int i = 0; i < T; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			
			if(count != 0) // 남은 숫자가 0이 될때까지 회전
				rotate(x, d, k);
//			print();
		}
		
		System.out.println(getSum()); // 다 돌리고 숫자 총합 출력
	}
	
	public static void rotate(int x, int d, int k) { // 돌리기
		int idx = x;
		while(idx <= N) { // x의 배수인 원판 돌려주기
			if(d == 0) heads[idx] = (heads[idx] + M - k) % M; // 시계
			else heads[idx] = (heads[idx] + k) % M; // 반시계

			idx += x;
		}
		
		boolean deleted = false; // 삭제 여부
		TreeSet<int[]> delete = new TreeSet<>(new Comparator<int[]>() { // 중복 인덱스 제거를 위한 TreeSet
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0];
			}
		});
		for(int i = 1; i <= N; i++) {
			for(int j = heads[i]; j < heads[i] + M; j++) { // 헤드부터 시계방향으로 탐색
				int p = j % M; // 현재 숫자 인덱스
				if(disks[i][p] == 0) continue; // 0이면 넘어가기
				if(disks[i][p] == disks[i][(p+1)%M]) { // 오른쪽에 있는 숫자와 같다면
					delete.add(new int[] {i, p}); // 삭제
					delete.add(new int[] {i, (p+1)%M});
					deleted = true;
				}
				if(i == N) continue; // 마지막 원판은 비교할 바깥 원판이 없으므로 건너뛰기
				
				int op = (j - heads[i] + heads[i+1]) % M; // 비교할 바깥 원판의 숫자 인덱스 구하기
				if(disks[i][p] == disks[i+1][op]) { // 비교
					delete.add(new int[] {i, p}); // 삭제
					delete.add(new int[] {i+1, op});
					deleted = true;
				}
			}
		}
		
		if(!deleted) { // 삭제되지 않았다면
			double avg = getSum() / (double)count;
			for(int i = 1; i <= N; i++) {
				for(int j = 0; j < M; j++) {
					if(disks[i][j] == 0) continue;
					if((double)disks[i][j] > avg) disks[i][j]--; // 평균보다 크면 1 빼기
					else if((double)disks[i][j] < avg) disks[i][j]++; // 평균보다 작으면 1 더하기
				}
			}
		}
		else { // 삭제 됐다면
			for(int[] p : delete) {
				disks[p[0]][p[1]] = 0; // 0으로 만들고
				count--; // 카운트 - 1
			}
		}
	}
	
	public static int getSum() { // 합 구하기
		int sum = 0;
		for(int i = 1; i <= N; i++) {
			for(int j = 0; j < M; j++) {
				sum += disks[i][j];
			}
		}
		
		return sum;
	}
	
	public static void print() { // heads, disks 출력
		System.out.println(Arrays.toString(heads));
		for(int[] row : disks) {
			System.out.println(Arrays.toString(row));
		}
		System.out.println();
	}
}
