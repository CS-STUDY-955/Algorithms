package com.baekjoon.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class P3020 {

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		/** 가로 길이 */
		int N = Integer.parseInt(st.nextToken());
		/** 높이 */
		int H = Integer.parseInt(st.nextToken());
		
		/** 위에서 올라오는 석순 */
		int[] countd = new int[H];
		/** 아래에서 내려오는 종유석 */
		int[] countu = new int[H];
		/** 해당 높이의 장애물 개수 */
		int[] count = new int[H];
		/** 장애물의 길이 */
		int dis;
		/** 최소값 */
		int min = N;
		/** 최소값 중복 개수 */
		int overlap = 1;
		for(int i = 0; i < N; i++) {
			dis = Integer.parseInt(br.readLine());
			/** 짝수번째 장애물은 석순, 홀수번째 장애물은 종유석 */
			/** 해당 장애물의 높이를 인덱스로 count + 1 */
			if(i % 2 == 0) {
				countd[H - dis]++;
			} else {
				countu[dis - 1]++;
			}
		}
		
		/** 각 라인별 장애물의 개수(count)를 계산 */
		/** 석순 계산 */
		int tmp = 0;
		for(int i = 0; i < H; i++) {
			/** 가장 낮은 구간부터 장애물을 누적으로 제외하는 방식 */
			/** 예) 높이가 1, 3인 석순이라면 가장 낮은 구간은 전체 석순 개수에서 -0, 그 다음은 높이가 1인 석순을 누적시켜 -1 */
			count[H - i - 1] = N / 2 + N % 2 - tmp;
			tmp += countd[H - i - 1];
		}
		
		/** 종유석 계산 */
		tmp = 0;
		for(int i = 0; i < H; i++) {
			/** 석순과 동일한 방식이지만 가장 높은 구간부터 시작 */
			count[i] += N / 2 - tmp;
			tmp += countu[i];
		}
		
		/** 저장된 장애물의 개수를 for문으로 돌면서 최소값과 중복 개수 탐색 */
		for(int c : count) {
			if(c <= min) {
				if(c == min) {
					overlap++;
				} else {
					overlap = 1;
					min = c;
				}
			}
		}
		
		/** 이분 탐색 -> 범위를 반절씩 좁혀가며 탐색하는 거? */
		System.out.println(min+" "+overlap);
	}
}
