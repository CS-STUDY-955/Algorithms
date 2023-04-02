package com.baekjoon.p5g12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class BJ_21611_마법사상어와블리자드 {
	
	static class Group { // 연속되는 숫자의 개수와 숫자 번호 저장
		int count, number;

		public Group(int count, int number) {
			this.count = count;
			this.number = number;
		}

		@Override
		public String toString() {
			return "Group [count=" + count + ", number=" + number + "]";
		}
	}
	
	static int N; // 맵 길이
	static int[][] map; // 맵
	static ArrayDeque<Group> list; // 그룹 리스트
	static int[] exploded; // 폭발한 1, 2, 3번 개수 저장
	
	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {-1, 0, 1, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // 맵 길이
		int M = Integer.parseInt(st.nextToken()); // 블리자드 쓸 횟수
		
		map = new int[N][N]; // 맵 입력
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		init(); // 초기화 (그룹화)
		
		exploded = new int[4];
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			
			switch(d) { // 상하좌우를 좌하우상 순서로 맞춰주기 (파괴용)
			case 1:
				d = 3;
				break;
			case 2:
				d = 1;
				break;
			case 3:
				d = 0;
				break;
			case 4:
				d = 2;
				break;
			}
			
			destroy(d, s); // 파괴
			explosion(); // 폭발
			makelist(); // 리스트 만들기
		}
		
		int ans = 0;
		for(int i = 1; i <= 3; i++) {
			ans += i * exploded[i];
		}
		System.out.println(ans);
	}
	
	public static void init() {
		list = new ArrayDeque<>();
		int x = N/2; // 상어의 위치부터
		int y = N/2;
		
		int d = 0;
		int k = 1;
		int left = 1;
		Group b = new Group(-1, -1); // b: 직전 그룹 (= list.peekLast())
		for(int i = 1; i < N*N; i++) {
			int nx = x + dx[d];
			int ny = y + dy[d];
			
			// 0을 만나면 탈출
			if(map[nx][ny] == 0) break;
			
			// 직전 그룹과 숫자가 같다면
			if(map[nx][ny] == b.number) {
				b.count++; // 직전 그룹 개수 +1
			} else {
				list.add(b); // 아니라면 넣어주고
				b = new Group(1, map[nx][ny]); // b 갱신
			}
			
			// 나선으로 돌아주기
			if(--left == 0) {
				if(d % 2 == 0) d++;
				else {
					d = (d+1) % 4;
					k++;
				}
				left = k;
			}
			
			x = nx;
			y = ny;
		}
		list.add(b); // 마지막 넣어주기
		list.pollFirst(); // 초기화를 위해 넣었던 -1, -1 빼주기
	}
	
	public static void destroy(int d, int s) {
		int count = 0; // 여태 지나온 구슬 개수
		int k = 1; // 거리계산용
		int next = k+d*(k+1); // 다음에 파괴할 구슬 번호
		for(Group g : list) {
			count += g.count; // 구슬 개수 추가
			
			if(next <= count && s > 0) { // 파괴할 구슬을 마주치고 && 아직 파괴해야한다면
				k += 2; // 거리계산용 갱신
				next += (3-d)*(k-1)+k+d*(k+1); // 다음에 파괴할 구슬 번호 갱신
				s--; // 파괴할 구슬 수 -1
				g.count--; // 파괴
			}
		}
	}
	
	public static void explosion() {
		while(true) {
			int blowed = 0; // 폭발한 그룹 수
			ArrayDeque<Group> tmp = new ArrayDeque<>();
			tmp.add(new Group(-1, -1));
			for(Group g : list) {
				if(g.count == 0) continue; // 0이면 넘어가기
				
				// 그게 아니면 그룹화 해주면서 tmp에 넣기
				if(tmp.peekLast().number == g.number) {
					tmp.peekLast().count += g.count;
				} else {
					tmp.add(g);
				}
			}
			tmp.pollFirst();
			list = tmp; // list 갱신
			
			for(Group g : list) {
				// 카운트가 4 이상인 애들 폭발
				if(g.count >= 4) {
					exploded[g.number] += g.count;
					g.count = 0;
					blowed++;
				}
			}
			
			// 폭발한 그룹이 없다면 반복문 탈출
			if(blowed == 0) break;
		}
	}
	
	public static void makelist() {
		ArrayDeque<Group> tmp = new ArrayDeque<>();
		
		for(Group g : list) { // 개수, 숫자 순서대로 넣어주기
			tmp.add(new Group(1, g.count));
			tmp.add(new Group(1, g.number));
			if(tmp.size() == N*N - 1) break; // 맵이 다 차면 탈출
		}
		
		list = tmp;
		tmp = new ArrayDeque<>();
		tmp.add(new Group(-1, -1));
		// 그룹화
		for(Group g : list) {
			if(g.count == 0) continue;
			
			if(tmp.peekLast().number == g.number) {
				tmp.peekLast().count += g.count;
			} else {
				tmp.add(g);
			}
		}
		
		tmp.pollFirst();
		list = tmp;
	}
	
	public static void print(String head, int i) {
		System.out.println(i+"======="+head+"=======");
		for(Group g : list) {
			System.out.println(g);
		}
		System.out.println("==============");
	}
}