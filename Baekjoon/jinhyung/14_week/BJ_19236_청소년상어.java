package gold2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_19236_청소년상어 {
	
	static class Fish {
		int num, x, y, dir; // 번호, 위치, 방향
		boolean visited; // 상어가 먹었는지 여부

		public Fish(int num, int x, int y, int dir) {
			this.num = num;
			this.x = x;
			this.y = y;
			this.dir = dir;
			this.visited = false;
		}
		
		// 복사용 생성자
		public Fish(Fish o) {
			this.num = o.num;
			this.x = o.x;
			this.y = o.y;
			this.dir = o.dir;
			this.visited = o.visited;
		}
	}
	
	static int max = 0;
	static int[][] fmap;
	static Fish[] fishes;
	
	static int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
	static int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		fmap = new int[4][4]; // 각 위치에 존재하는 물고기 번호
		fishes = new Fish[17]; // 각 물고기의 정보
		for(int i = 0; i < 4; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < 4; j++) {
				int n = Integer.parseInt(st.nextToken());
				int d = Integer.parseInt(st.nextToken());
				fishes[n] = new Fish(n, i, j, d-1);
				fmap[i][j] = n;
			}
		}

		// 상어 위치, 처음 먹은 물고기, 방향 초기화
		int sx = 0, sy = 0;
		int sum = fmap[sx][sy];
		fishes[fmap[sx][sy]].visited = true;
		int sd = fishes[fmap[sx][sy]].dir;
		
		dfs(sx, sy, sd, sum);
		
		System.out.println(max);
	}

	private static void dfs(int sx, int sy, int sd, int sum) {
		for(int i = 1; i <= 16; i++) { // 모든 물고기가 움직인다
			if(fishes[i].visited) continue; // 먹힌 물고기는 패스
			int x = fishes[i].x;
			int y = fishes[i].y;
			int d = fishes[i].dir;
			for(int j = 0; j < 8; j++) { // 반시계로 45도씩 돌며 8방 탐색
				int nd = (d+j)%8;
				int nx = x + dx[nd];
				int ny = y + dy[nd];
				
				if(isOut(nx, ny) || (nx == sx && ny == sy)) continue;
				
				int onum = fmap[nx][ny]; // 움직일 위치의 물고기 번호
				// 현재 물고기 위치, 방향 바꿔주기
				fishes[i].dir = nd;
				fishes[i].x = nx;
				fishes[i].y = ny;
				fmap[nx][ny] = i; // 물고기 번호 대입

				// 현재 물고기가 움직일 칸에 있는 물고기의 위치 바꿔주기
				fishes[onum].x = x;
				fishes[onum].y = y;
				fmap[x][y] = onum;
				
				break; // 움직였으면 다음 물고기로
			}
		}
		while(true) { // 상어 움직이기
			sx = sx+dx[sd];
			sy = sy+dy[sd];
			
			// 다음 칸이 격자 밖이라면
			if(isOut(sx, sy)) {
				// 현재까지 먹은 물고기 번호 합 갱신 후 dfs 탈출
				max = max > sum ? max : sum;
				return;
			};
			// 이미 먹은 물고기라면 (빈칸인 경우) 건너뛰기
			if(fishes[fmap[sx][sy]].visited) continue; 

			fishes[fmap[sx][sy]].visited = true; // 물고기 먹음 표시
			
			// 현재 물고기의 정보 저장
			Fish[] savefishes = new Fish[17];
			for(int i = 1; i <= 16; i++) {
				savefishes[i] = new Fish(fishes[i]);
			}
			// 현재 물고기의 위치 정보 저장
			int[][] savefmap = new int[4][4];
			for(int i = 0; i < 4; i++) {
				savefmap[i] = fmap[i].clone();
			}

			// 재귀로 dfs 탐색
			dfs(sx, sy, fishes[fmap[sx][sy]].dir, sum+fmap[sx][sy]);
			
			// 재귀 끝났으면 저장한 정보 불러오기
			fishes = new Fish[17];
			for(int i = 1; i <= 16; i++) {
				fishes[i] = new Fish(savefishes[i]);
			}
			fmap = new int[4][4];
			for(int i = 0; i < 4; i++) {
				fmap[i] = savefmap[i].clone();
			}
			
			// 물고기 다시 뱉기
			fishes[fmap[sx][sy]].visited = false;
		}
	}

	private static boolean isOut(int x, int y) {
		return x < 0 || x >= 4 || y < 0 || y >= 4;
	}
	
//	public static void aprint(int sx, int sy) {
//		System.out.println("========");
//		System.out.println("sx: "+sx+", sy: "+sy);
//		print();
//		System.out.println("d");
//		dprint();
//		System.out.println("v");
//		vprint();
//	}
//	
//	public static void print() {
//		for(int[] row : fmap) {
//			for(int e: row) {
//				System.out.print(e+" ");
//			}
//			System.out.println();
//		}
//		System.out.println();
//	}
//	
//	public static void dprint() {
//		for(int[] row : fmap) {
//			for(int e: row) {
//				System.out.print(fishes[e].dir+" ");
//			}
//			System.out.println();
//		}
//		System.out.println();
//	}
//	
//	public static void vprint() {
//		for(int[] row : fmap) {
//			for(int e: row) {
//				System.out.print(fishes[e].visited?"T ":"F ");
//			}
//			System.out.println();
//		}
//		System.out.println();
//	}
}
