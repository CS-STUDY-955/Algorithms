package com.baekjoon.g345;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class BJ_16235_나무재테크 {
	
	static class Tree implements Comparable<Tree> {
		int age, num; // 나이, 그 나이인 나무의 개수

		public Tree(int age, int num) {
			this.age = age;
			this.num = num;
		}

		@Override
		public int compareTo(Tree o) { // TreeSet에서 나이순 오름차순으로 정렬 가능
			return this.age - o.age;
		}
	}
	
	static class Point {
		// 행, 열, 현재 양분, 매년 추가될 양분, 죽은 나무에서 추가될 양분, 번식할 나무 수
		int x, y, soil, A, dead, reproduct;
		TreeSet<Tree> tree; // 나무들의 정보
		
		public Point(int x, int y, int A) {
			this.x = x;
			this.y = y;
			this.soil = 5; // 초기에 양분은 5로 초기화 되어있다
			this.A = A;
			tree = new TreeSet<Tree>();
		}
		
		// 봄
		public void spring() {
			this.dead = 0; // 죽은 나무가 남길 양분
			this.reproduct = 0; // 번식할 나무의 수
			if(this.tree.size() == 0) return; // 이 좌표에 나무가 없다면 패스
			TreeSet<Tree> after = new TreeSet<Tree>(); // 봄이 지난 뒤에 남아있을 나무들의 정보
			for(Tree t : tree) { // 모든 나무 반복(나이 기준 오름차순)
				if(this.soil < t.age) { // 양분보다 나이가 많다면
					dead += t.num * (t.age / 2); // 나무의 수 * 나이/2 만큼 양분을 남긴다
					continue;
				}
				Tree nt = new Tree(t.age+1, 0); // 나무는 한살 더 먹는다.
				while(t.num > 0) { // 기존 나무의 수가 0 이상인 동안
					if(this.soil < t.age) { // 만약 양분이 부족해지면
						dead += t.num * (t.age / 2); // 남은 나무 수 * 나이/2 만큼 양분을 남긴다.
						break; // 반복 탈출
					}
					t.num--; // 나무 하나 줄어들고
					this.soil -= t.age; // 양분이 현재 나이만큼 줄어들고
					nt.num += 1; // 한살 더 먹은 나무는 하나 늘어난다.
				}
				if(nt.num != 0) {
					after.add(nt); // 한살 더 먹고 살아남은 나무들 추가
				}
				if(nt.age % 5 == 0) { // 나이가 5의 배수가 됐다면
					reproduct += nt.num; // 그 나무들의 수 만큼 번식한다.
				}
			}
			this.tree = after; // 나무 정보 갱신
			this.soil += dead; // summer()
		}
		
		// 여름
		public void summer() {
			this.soil += dead; // 죽은 나무가 양분이 된다
		}
		
		// 가을
		public void fall() {
			this.soil += A; // winter()
			if(this.reproduct == 0) return; // 재생산할 나무가 없으면 return
			for(int d = 0; d < 8; d++) { // 8방향에 퍼지기
				int nx = this.x + dx[d];
				int ny = this.y + dy[d];
				
				// 범위 밖이라면 패스
				if(nx < 0 || nx >= N || ny < 0 || ny >= N) continue;
				
				Point p = field.get(nx*N + ny); // 새 나무가 자랄 Point 가져오기

				if(p.tree.size() > 0) { // 그 좌표에 나무가 1개 이상 있는 경우
					if(p.tree.first().age != 1) // 제일 어린 나무가 1이 아니라면
						p.tree.add(new Tree(1, reproduct)); // 나이가 1인 나무를 reproduct만큼 추가
					else // 제일 어린 나무가 1이라면
						p.tree.first().num += reproduct; // 나이가 1인 나무에 reproduct만큼 추가
				}
				else { // 그 좌표에 나무가 없는 경우
					p.tree.add(new Tree(1, reproduct)); // 나이가 1인 나무를 reproduct만큼 추가
				}
			}
		}
		
		// 겨울
		public void winter() {
			this.soil += A; // 기계가 양분을 추가한다
		}
	}
	
	static int N, M, K;
	static ArrayList<Point> field;
	
	static int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
	static int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 땅의 크기
		M = Integer.parseInt(st.nextToken()); // 처음 심을 나무의 개수
		K = Integer.parseInt(st.nextToken()); // K년간 4계절 반복
		
		field = new ArrayList<Point>(N*N); // 각 좌표의 정보
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				field.add(new Point(i, j, Integer.parseInt(st.nextToken()))); // 땅의 x, y좌표, 추가될 양분
			}
		}
		
		for(int i = 0; i < M; i++) { // 처음 심을 나무 추가
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()) - 1; // 나무의 x 좌표
			int y = Integer.parseInt(st.nextToken()) - 1; // 나무의 y 좌표
			int age = Integer.parseInt(st.nextToken()); // 나무의 나이
			
			field.get(x*N+y).tree.add(new Tree(age, 1)); // 해당하는 좌표에 나무 저장(Tree(나이, 개수))
		}
		
		for(int i = 0; i < K; i++) { // K년 동안
			for(int j = 0; j < field.size(); j++) {
				field.get(j).spring(); // 봄
			}
//			for(int j = 0; j < field.size(); j++) {
//				if(field.get(j).dead > 0) // 봄에 죽은 나무가 있는 좌표만 여름
//					field.get(j).summer();
//			}
			for(int j = 0; j < field.size(); j++) {
//				if(field.get(j).reproduct > 0) // 번식할 나무가 있는 좌표만 가을
//					field.get(j).fall();
				field.get(j).fall();
			}
//			for(int j = 0; j < field.size(); j++) {
//				field.get(j).winter(); // 겨울
//			}
		}
		
		int num = 0;
		for(int i = 0; i < field.size(); i++) {
			for(Tree t : field.get(i).tree) { // 모든 땅의 나무들 세주기
				num += t.num;
			}
		}
		
		System.out.println(num); // 출력
	}
}