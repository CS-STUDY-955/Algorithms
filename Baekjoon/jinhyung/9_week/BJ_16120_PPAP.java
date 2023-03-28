package com.baekjoon.g345;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BJ_16120_PPAP {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String ppap = br.readLine().trim(); // 문자열 입력
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < ppap.length(); i++) {
			char c = ppap.charAt(i); // 순서대로 문자 뽑아오기
			
			sb.append(c); // 스트링 빌더에 문자 추가
			// 문자가 P이고 스트링 빌더의 길이가 4 이상이고 마지막 4문자가 PPAP라면
			if(c == 'P' && sb.length() >= 4 && sb.subSequence(sb.length() - 4, sb.length()).equals("PPAP"))
				sb.replace(sb.length() - 4, sb.length(), "P"); // PPAP => P로 바꾸기
		}
		if(sb.toString().equals("P")) System.out.println("PPAP"); // 남은 문자가 P라면 PPAP 문자
		else System.out.println("NP"); // 아니면 아님
	}
}
