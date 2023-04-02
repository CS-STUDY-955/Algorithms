package Gold.Gold1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class BJ_21611_마법사상어와블리자드 {
	static int N, M;
	static int[][] map;
	static int answer = 0;
	static int[] marble = new int[4]; // 구슬의 갯수
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		
		for(int i =0; i < N ;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j =0; j < N ;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		for(int i =0 ; i < M ; i++){
			st = new StringTokenizer(br.readLine());
			int dir = Integer.parseInt(st.nextToken());// 방향(상하좌우1234)
			int size = Integer.parseInt(st.nextToken());
			destroy(dir, size);
			pull(N/2, N/2-1);
			while(true) {
				if(!bomb(N/2, N/2-1)) break;
			}
			change(N/2, N/2-1);
//			print();
		}
		for(int i =1; i < 4; i++) {
			answer += i*marble[i];
		}
		System.out.println(answer);
	}
	
	static int direction[][] = {{-1,0}, {1,0}, {0,-1}, {0,1}};
	private static void destroy(int dir, int size) { // 폭파시키는 메서드
		for(int i =1; i<= size ; i++) {
			int nx = N/2 + direction[dir-1][0]*i;
			int ny = N/2 + direction[dir-1][1]*i;
			if( nx < 0 || nx >= N || ny<0 || ny>= N) break;
			map[nx][ny] = 0;
		}
	}

	static int pullDir[][] = {{0,-1}, {1,0}, {0,1}, {-1,0}}; // 좌하우상
	private static void pull(int a, int b) {
		ArrayDeque<Integer> q = new ArrayDeque<>();
		int size = 1; // 변의 길이, 1부터 시작
		int dirCond = 0; // 2번 이동하면 0으로 바꿔주고 size up시키고, (dir +1)%4 해줘야해
		int sizeCond =1;
		int dir = 1; // 좌하우상 나타내는 방향,  
		while( true) {
			if(map[a][b] != 0) {
				q.add(map[a][b]);
			}
			a += pullDir[dir][0];
			b += pullDir[dir][1];
			if(b == -1) break;
			
			dirCond++;
			if(dirCond == size) {
				sizeCond +=1;
				dir = (dir+1)%4;
				dirCond =0;
			}
			if(sizeCond == 2) {
				size +=1;
				sizeCond =0;
			}
			
		}
		map = new int[N][N];
		size = 1; // 변의 길이, 1부터 시작
		dirCond = 0; // 2번 이동하면 0으로 바꿔주고 size up시키고, (dir +1)%4 해줘야해
		sizeCond =1;
		dir = 1; // 좌하우상 나타내는 방향,
		a = N/2; b = N/2 -1;
		while( true) {
			if(q.isEmpty()) map[a][b] = 0;
			else map[a][b] = q.removeFirst();
			a += pullDir[dir][0];
			b += pullDir[dir][1];
			if(b == -1) break;
			
			dirCond++;
			if(dirCond == size) {
				sizeCond +=1;
				dir = (dir+1)%4;
				dirCond =0;
			}
			if(sizeCond == 2) {
				size +=1;
				sizeCond =0;
			}
		}
	}
	
	private static boolean bomb(int a, int b) {
		boolean bombed = false;
		ArrayDeque<Integer> q = new ArrayDeque<>();
		int size = 1; // 변의 길이, 1부터 시작
		int dirCond = 0; // 2번 이동하면 0으로 바꿔주고 size up시키고, (dir +1)%4 해줘야해
		int sizeCond =1;
		int dir = 1; // 좌하우상 나타내는 방향,
		int before = 0;
		int sameCond =1;
		while(true) {
			int val = map[a][b]; // 새로운값
			if( val != before) { // 이전값과 다르다면
				if(sameCond >= 4) { // 근데 지금껏 쌓인 구슬이 4개 이상이면
					bombed = true; // 한번이라도 폭발 발생하면 true 반환
					for(int i = 0; i < sameCond;i++) { // 같은구술수만큼
						marble[q.pollLast()]++;
					}
				}
				sameCond = 1; // 조건 초기화
				before = val; // 이전 값 갱신
			}else { // 이전값과 같으면
				sameCond++; // 조건 추가
			}
			q.add(val); // 큐에 넣어줘
			a += pullDir[dir][0];
			b += pullDir[dir][1];
			if(b == -1) break;
			
			dirCond++;
			if(dirCond == size) {
				sizeCond +=1;
				dir = (dir+1)%4;
				dirCond =0;
			}
			if(sizeCond == 2) {
				size +=1;
				sizeCond =0;
			}
		}
		
		map = new int[N][N];
		size = 1; // 변의 길이, 1부터 시작
		dirCond = 0; // 2번 이동하면 0으로 바꿔주고 size up시키고, (dir +1)%4 해줘야해
		sizeCond =1;
		dir = 1; // 좌하우상 나타내는 방향,
		a = N/2; b = N/2 -1;
		while( true) {
			if(q.isEmpty()) map[a][b] = 0;
			else map[a][b] = q.removeFirst();
			a += pullDir[dir][0];
			b += pullDir[dir][1];
			if(b == -1) break;
			
			dirCond++;
			if(dirCond == size) {
				sizeCond +=1;
				dir = (dir+1)%4;
				dirCond =0;
			}
			if(sizeCond == 2) {
				size +=1;
				sizeCond =0;
			}
		}
		return bombed;
	}

	private static void change(int a, int b) {
		ArrayDeque<Integer> q = new ArrayDeque<>();
		int size = 1; // 변의 길이, 1부터 시작
		int dirCond = 0; // 2번 이동하면 0으로 바꿔주고 size up시키고, (dir +1)%4 해줘야해
		int sizeCond =1;
		int dir = 1; // 좌하우상 나타내는 방향,
		int before = map[a][b];
		int sameCond = 0;
		while(true) { //&& map[a][b]!=0) {
			int val = map[a][b];
			if( val != before) { // 이전값과 다르다면
				q.add(sameCond);
				q.add(before);
				sameCond =1;
				before = val;
			}else { // 이전값과 같으면
				sameCond++; // 조건 추가
			}
			a += pullDir[dir][0];
			b += pullDir[dir][1];
			if(b == -1) break;
			
			dirCond++;
			if(dirCond == size) {
				sizeCond +=1;
				dir = (dir+1)%4;
				dirCond =0;
			}
			if(sizeCond == 2) {
				size +=1;
				sizeCond =0;
			}
		}
		map = new int[N][N];
		size = 1; // 변의 길이, 1부터 시작
		dirCond = 0; // 2번 이동하면 0으로 바꿔주고 size up시키고, (dir +1)%4 해줘야해
		sizeCond =1;
		dir = 1; // 좌하우상 나타내는 방향,
		a = N/2; b = N/2 -1;
		while( true) {
			if(q.isEmpty()) map[a][b] = 0;
			else map[a][b] = q.removeFirst();
			a += pullDir[dir][0];
			b += pullDir[dir][1];
			if(b == -1) break;
			
			dirCond++;
			if(dirCond == size) {
				sizeCond +=1;
				dir = (dir+1)%4;
				dirCond =0;
			}
			if(sizeCond == 2) {
				size +=1;
				sizeCond =0;
			}
		}
	}
	
	private static void print() {
		for(int i = 0; i < N; i++) {
			for(int j =0 ;j < N ; j++) {
				System.out.print(map[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
