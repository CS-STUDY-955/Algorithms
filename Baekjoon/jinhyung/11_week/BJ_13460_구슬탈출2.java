package com.baekjoon.p5g12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_13460_구슬탈출2 {
	
	static class Marble {
		int rx, ry, bx, by, k, before; // 빨간 구슬 x,y, 파란 구슬 x,y, 이동한 횟수, 이전에 이동한 방향

		public Marble() {};
		public Marble(int rx, int ry, int bx, int by, int k, int before) {
			this.rx = rx;
			this.ry = ry;
			this.bx = bx;
			this.by = by;
			this.k = k;
			this.before = before;
		}
	}
	
	static int N, M, ans = -1; // 행길이, 열길이, 정답
	static char[][] map; // 지도
	static int[] rp, bp; // 초기 빨간 구슬 위치, 파란 구슬 위치
	
	static int[] dx = {1, 0, -1, 0}; // 하우상좌
	static int[] dy = {0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new char[N][M];
		for(int i = 0; i < N; i++) {
			String str = br.readLine();
			for(int j = 0; j < M; j++) {
				char c = str.charAt(j);
				switch(c) {
				case 'R': // R 인경우 빨간 구슬 위치 저장, 지도 .
					rp = new int[] {i, j};
					map[i][j] = '.';
					break;
				case 'B': // B 인경우 파란 구슬 위치 저장, 지도 .
					bp = new int[] {i, j};
					map[i][j] = '.';
					break;
				default:
					map[i][j] = c;
					break;
				}
			}
		}
		
		bfs(); // 구슬 굴리기
		System.out.println(ans); // 답 출력
	}

	private static void bfs() {
		Queue<Marble> queue = new ArrayDeque<>();
		queue.add(new Marble(rp[0], rp[1], bp[0], bp[1], 0, -1)); // 초기화
		
		while(!queue.isEmpty()) { // 큐가 빌때까지
			Marble m = queue.poll();
			
			for(int i = 0; i < 4; i++) { // 4방으로 기울이기
				if(m.before == (i+2)%4) continue; // i가 이전에 기울였던 방향 반대라면 패스

				int rxx = m.rx; // 빨간 구슬, 파란 구슬 위치 입력 받기
				int ryy = m.ry;
				int bxx = m.bx;
				int byy = m.by;
				boolean moved = false; // 이번 기울이기에서 구슬이 둘 중 하나라도 움직였는가?
				boolean end = false; // 파란 구슬이 들어가지 않고 빨간 구슬이 들어갔는가? (성공 했는지)
				while(true) { // 반복(한칸씩 움직임)
					int nrx = rxx + dx[i]; // 빨간 구슬, 파란 구슬 한칸씩 움직이기
					int nry = ryy + dy[i];
					int nbx = bxx + dx[i];
					int nby = byy + dy[i];
					
					if(map[nrx][nry] == '#') { // 빨간 구슬이 움직인 위치가 벽이라면
						nrx -= dx[i]; // 한 칸 뒤로 돌아가기
						nry -= dy[i];
					}
					if(map[nbx][nby] == '#' || (!end && nbx == nrx && nby == nry)) {
						// 파란 구슬이 움직인 위치가 벽이거나 빨간 구슬이 있다면
						nbx -= dx[i]; // 한 칸 뒤로 돌아가기
						nby -= dy[i];
					}
					if(nrx == nbx && nry == nby) { // 빨간 구슬이 움직인 위치에 파란 구슬이 있다면
						nrx -= dx[i]; // 한 칸 뒤로 돌아가기
						nry -= dy[i];
					}
				
					// 두 구슬 모두 움직이지 않은 경우
					if(nrx == rxx && nry == ryy && nbx == bxx && nby == byy) {
						if(moved && m.k+1 < 10) { // 이번 기울이기에서 움직였고, 아직 9번 이하로 굴렸다면
							queue.add(new Marble(nrx, nry, nbx, nby, m.k+1, i)); // 그 위치에서 굴리기위해 큐에 넣기
						}
						break; // 반복 탈출
					}
					
					if(map[nrx][nry] == 'O') { // 빨간 구슬이 구멍에 들어간 경우
						ans = m.k+1; // 정답 저장하고
						end = true; // 끝남 표시
					}
					else if(map[nbx][nby] == 'O') { // 파란 구슬이 구멍에 들어간 경우
						end = false; // 끝나지 않았음
						ans = -1; // 정답 -1
						break; // 탈출
					}
					
					moved = true; // 움직임
					rxx = nrx; // 좌표 바꿔주기
					ryy = nry;
					bxx = nbx;
					byy = nby;
				}
				if(end) return; // 끝났다면 종료
			}
		}
	}
}
