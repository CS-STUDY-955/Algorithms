package gold1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_23290_마법사상어와복제 {
	
	static class Board { // 각 격자의 정보
		int[] fish, duplicate, moved; // 물고기 수, 복제할 물고기 수, 움직일 물고기 수
		int stink, total; // 냄새, 현재 격자의 물고기 총합
		boolean visited; // 물고기 먹은 여부
		
		public Board() {
			fish = new int[8];
			duplicate = new int[8];
			moved = new int[8];
			stink = 0;
			total = 0;
			visited = false;
		}
		
		// 물고기 추가하기 (입력 받기)
		public void add(int dir) {
			fish[dir]++;
			total++;
		}
		
		// 복제 마법 시전
		public void casting() {
			this.duplicate = this.fish.clone();
		}
		
		// 물고기 움직이기(moved에 저장된 물고기들)
		public void move() {
			for(int i = 0; i < 8; i++) {
				fish[i] += moved[i];
				total += moved[i];
			}
			moved = new int[8];
		}

		// 상어가 지나갔다면 물고기가 없어지고 냄새를 남긴다
		public void eaten() {
			if(total == 0) return;
			fish = new int[8];
			total = 0;
			stink = 3;
		}
		
		// 복제 마법 완료
		public void duplicate() {
			stink--; // 냄새 줄어들기
			for(int i = 0; i < 8; i++) {
				fish[i] += duplicate[i];
				total += duplicate[i];
			}
		}
	}
	
	static Board[][] board; // 격자 정보
	static int sx, sy; // 상어 위치
	static int[] max; // 상어가 움직일때 먹을 수 있는 최대 정보 저장
	
	static int[] dx = {0, -1, -1, -1, 0, 1, 1, 1}; // 물고기는 8방으로 움직임
	static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
	static int[] sdx = {-1, 0, 1, 0}; // 상어는 4방으로 움직임
	static int[] sdy = {0, -1, 0, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int M = Integer.parseInt(st.nextToken()); // 물고기 수
		int S = Integer.parseInt(st.nextToken()); // 복제 마법 시전 수
		
		// 격자 초기화
		board = new Board[4][4];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				board[i][j] = new Board();
			}
		}
		
		// 물고기 입력받아 추가하기
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken()) - 1;
			board[x][y].add(d);
		}
		
		// 상어 초기 위치 입력받기
		st = new StringTokenizer(br.readLine());
		sx = Integer.parseInt(st.nextToken()) - 1;
		sy = Integer.parseInt(st.nextToken()) - 1;

		// 복제마법 시전하기
		for(int i = 0; i < S; i++) {
			duplication();
		}
		
		// 살아남은 물고기 수 총합 구하기
		int sum = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				for(int k = 0; k < 8; k++) {
					sum += board[i][j].fish[k];
				}
			}
		}
		
		System.out.println(sum); // 출력
	}

	private static void duplication() {
		// 모든 격자에 복제마법 시전, 물고기도 움직이기
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				board[i][j].casting();
				move(i, j);
			}
		}
		
		// 물고기 움직임 반영
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				board[i][j].move();
			}
		}
		
		int[] route = new int[3]; // 상어가 이동할 순서
		max = new int[4]; // 상어가 이동할 순서, 먹은 최대 물고기 수
		max[3] = -1; // 물고기 수 초기화
		dfs(sx, sy, 0, 0, route); // 깊이우선으로 물고기 먹기
		
		// 상어가 움직이며 먹이 먹기
		for(int i = 0; i < 3; i++) {
			int d = max[i];
			int nsx = sx + sdx[d];
			int nsy = sy + sdy[d];
			board[nsx][nsy].eaten();
			sx = nsx;
			sy = nsy;
		}
		
		// 복제마법 완료
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				board[i][j].duplicate();
			}
		}
	}

	// 물고기 움직이기
	private static void move(int x, int y) {
		for(int d = 0; d < 8; d++) {
			if(board[x][y].fish[d] == 0) continue;
			for(int j = 0; j < 8; j++) {
				int nd = (8+d-j)%8;
				int nx = x + dx[nd];
				int ny = y + dy[nd];
				if(isOut(nx, ny) || board[nx][ny].stink > 0
						|| (sx == nx && sy == ny)) continue;

				board[nx][ny].moved[nd] += board[x][y].fish[d];
				board[x][y].total -= board[x][y].fish[d];
				board[x][y].fish[d] = 0;
				
				break;
			}
		}
	}

	// 상어가 먹을 수 있는 최대 물고기 수 계산하기
	private static void dfs(int x, int y, int depth, int sum, int[] route) {
		if(depth == 3) { // 3번 움직이고 난 뒤
			if(sum > max[3]) { // 먹은 수가 최대값보다 크다면 갱신
				max[3] = sum;
				for(int i = 0; i < 3; i++) max[i] = route[i];
			}
			return;
		}
		
		for(int d = 0; d < 4; d++) {
			int nx = x + sdx[d];
			int ny = y + sdy[d];
		
			if(isOut(nx, ny)) continue;
			route[depth] = d;
			if(!board[nx][ny].visited) { // 먹지 않은 경우
				board[nx][ny].visited = true;
				dfs(nx, ny, depth+1, sum+board[nx][ny].total, route);
				board[nx][ny].visited = false;
			} else { // 먹은 경우
				dfs(nx, ny, depth+1, sum, route);
			}
		}
	}

	private static boolean isOut(int nx, int ny) {
		return nx < 0 || nx >= 4 || ny < 0 || ny >= 4;
	}
}
