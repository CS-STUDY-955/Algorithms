package com.baekjoon.g345;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_14002_가장긴증가하는부분수열4 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] nums = new int[N]; // 숫자 저장
		int[] memo = new int[N]; // 현재까지 이어진 증가하는 부분 수열의 수
		nums[0] = Integer.parseInt(st.nextToken()); // 0번 인덱스 초기화
		memo[0] = 1; // 0번 인덱스 초기화
		int max = 1; // 가장 긴 증가하는 부분 수열의 길이
		int maxidx = 0; // 마지막 부분수열의 인덱스
		for(int i = 1; i < N; i++) {
			int n = Integer.parseInt(st.nextToken()); // 숫자 입력
			
			int k = 1; // 현재까지 이어지는 부분 수열
			for(int j = 0; j < i; j++) { // 현재까지 입력받은 수에 대해
				if(n > nums[j] && k <= memo[j]) k++; // 현재 입력받은 수보다 작고, 길이가 크거나 같다면 1 증가
			}
			
			nums[i] = n; // num 배열에 저장
			memo[i] = k; // 입력받은 수까지 이어지는 부분 수열의 길이
			if(k > max) { // 수가 가장 크다면
				max = k; // 갱신
				maxidx = i;
			}
		}
		System.out.println(max); // 가장 긴 부분 수열 길이 출력
		
		int[] ans = new int[max]; // 가장 긴 부분 수열을 저장할 배열
		int k = max - 1; // 마지막 원소 초기화
		ans[k] = nums[maxidx]; // 마지막 원소 초기화
		for(int i = maxidx - 1; i >= 0; i--) { // 역순으로 탐색하면서
			// 이어지는 부분 수열이라면 저장
			if(memo[i] == k && nums[i] < ans[k]) ans[--k] = nums[i];
		}

		// 부분수열 출력
		for(int i = 0; i < max; i++) {
			System.out.printf("%d ", ans[i]);
		}
	}
}
