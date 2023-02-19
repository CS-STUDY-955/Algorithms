package com.baekjoon.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * BJ 2436. 공약수
 * 
 * 1. 최대 공약수, 최소 공배수 공식 참고
 * 2. 최대 공약수 코드 참고(유클리드 호제법)
 * 
 * 첫 제출 후 시간이 너무 많이 걸림... (488ms)
 * 
 * 바꾼 점
 * 1. 서로소 비교 전에 a > b 라면 반복문 탈출
 * 2. 최대공약수 : 유클리드 호제법 -> 역순 탐색
 * > 시간 단축 (152ms)
 * 
 * @author 양진형
 */
public class P2436_second_sol {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int G = Integer.parseInt(st.nextToken()); // 최대 공약수
		int L = Integer.parseInt(st.nextToken()); // 최소 공배수
		
		int[] min = getMin(G, L);
		
		System.out.print(min[0]+" "+min[1]);
	}

	public static int[] getMin(int G, int L) {
		// numA = G * a = L / b
		// numB = G * b = L / a
		// L = G * a * b
		// L * G = numA * numB = G G a b
		int numA;
		int numB;
		int minA = L;
		int minB = L;
		
		for(int a = 1; a < L; a++) {
			numA = G * a;
			int b;
			
			if(L % numA == 0) b = L / numA; // 최소 공배수가 numA의 약수라면 b 초기화
			else continue;
			
			if(a < b)
				if (getGcd(a, b) == 1) numB = G * b; // a와 b가 서로소라면 numB 초기화
				else continue;
			else break; // a가 b보다 같거나 큰 경우 반복문 탈출
			
			// 최소합인 두 수를 구하는 조건
			if(numA + numB < minA + minB) {
				minA = numA;
				minB = numB;
			} else if(numA > numB) { // A가 B보다 커지는 순간 탈출
				break;
			}
		}
		int[] result = {minA, minB};
		
		return result;
	}
	
	// 역순 탐색, a < b
	static int getGcd(int a, int b) {
		for(int i = a; i >= 1; i--) {
			if(a % i == 0 && b % i == 0)
				return i;
		}
		return 1;
	}
	
	// 유클리드 호제법, a < b
//	static int getGcd(int a, int b) {
//		int r = b % a;
//		if (r == 0) return a;
//		return getGcd(r, a);
//	}
}
