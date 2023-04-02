package com.baekjoon.p5g12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeSet;

public class BJ_10775_공항 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int G = Integer.parseInt(br.readLine()); // 게이트 수
		int P = Integer.parseInt(br.readLine()); // 비행기 수
		
		TreeSet<Integer> airport = new TreeSet<>(); // 공항
		for(int i = 1; i <= G; i++) airport.add(i); // 게이트 초기화
		
		int plane = 0; // 들어온 비행기 수
		for(int i = 0; i < P; i++) {
			int g = Integer.parseInt(br.readLine()); // 비행기 번호
			if(airport.first() > g) break; // 남은 게이트 중 가장 작은 번호가 비행기 번호보다 크다면 탈출
			airport.remove(airport.floor(g)); // 그렇지 않다면 비행기 번호보다 작은 수 중 가장 큰 수 삭제(게이트 도킹) 
			plane++; // 비행기 수 +1
			if(airport.size() == 0) break; // 남은 게이트가 없으면 탈출
		}
		System.out.println(plane); // 출력
	}
}
