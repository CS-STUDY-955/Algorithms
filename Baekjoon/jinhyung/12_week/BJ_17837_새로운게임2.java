package gold2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class BJ_17837_새로운게임2 {
	
	static int N, K, turn; // 체스판 크기, 말의 수, 턴
	static int[][][] map; // 체스판
	static int[][] dir; // 각 말들이 바라보는 방향
	
	static int[] dx = {0, 0, -1, 1};
	static int[] dy = {1, -1, 0, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		map = new int[N][N][4];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j][0] = Integer.parseInt(st.nextToken());
			}
		}
		
		turn = 0;
		dir = new int[K+1][3];
		for(int i = 1; i <= K; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken()) - 1;
			
			if(put(i, x, y)) { // 말 놓기
				System.out.println(turn);
				return;
			};
			dir[i] = new int[] {d, x, y}; // 방향 초기화
		}
		
		while(turn++ <= 1000) { // turn이 1000 이하인 동안
			for(int i = 1; i <= K; i++) {
				if(move(i)) { // 말들 움직이기
					System.out.println(turn); // 한 칸에 말이 넷 이상이라면 turn 출력 후 종료
					return;
				}
			}
		}
		
		System.out.println(-1); // turn이 1000을 초과하는 경우 -1
	}
	
	// 말 놓기
	public static boolean put(int id, int x, int y) {
		int number = 1; // 순서
		while(true) {
			if(map[x][y][number] == 0) { // 해당 순서에 말이 없는 경우
				map[x][y][number] = id; // 말 놓기
				dir[id][1] = x; // x, y 초기화
				dir[id][2] = y;
				return false;
			}
			
			if(++number == 4) return true; // 말이 이미 3개 있는 경우 종료
		}
	}
	
	public static boolean move(int id) { // 움직이기
		int x = dir[id][1];
		int y = dir[id][2];
		int d = dir[id][0];
		
		int nx = x + dx[d]; // 다음 x
		int ny = y + dy[d]; // 다음 y
		
		// 움직일 칸이 체스판 밖이거나 파란색인 경우
		if(isOut(nx, ny) || map[nx][ny][0] == 2) {
			// 방향 바꿔주기
			if(d % 2 == 0) d = ++dir[id][0]; 
			else d = --dir[id][0];
			
			nx = x + dx[d]; // 다음 칸 구하기
			ny = y + dy[d];
			
			// 방향을 바꿔준 다음 칸도 체스판 밖이거나 파란색인 경우
			if(isOut(nx, ny) || map[nx][ny][0] == 2) {
				return false; // 움직이지 못함
			}
		}
		
		// 움직일 말의 순서 구하기
		for(int i = 1; i <= 3; i++) {
			if(map[x][y][i] != id) continue;
			for(int j = i; j <= 3; j++) { // 순서를 구했다면 그 순서부터 위에있는 말까지 모두 함께 이동
				if(map[x][y][j] == 0) break; // 말이 없다면 탈출
				if(put(map[x][y][j], nx, ny)) return true; // 말이 있다면 움직이기
				map[x][y][j] = 0; // 움직인 말은 0으로 바꿔주기
			}
			break;
		}
		
		// 움직인 곳이 빨간색이고 말이 2개이상 있는 경우 순서 바꿔주기
		if(map[nx][ny][0] == 1 && map[nx][ny][2] != 0) {
			int idx = -1;
			Stack<Integer> stack = new Stack<>();
			// 움직인 말의 순서 구하기
			for(int i = 1; i <= 3; i++) {
				if(map[nx][ny][i] != id) continue;
				idx = i;
				// 그 말과 그 위에 있는 말들 스택에 넣기
				for(int j = i; j <= 3; j++) {
					if(map[nx][ny][j] == 0) break;
					stack.add(map[nx][ny][j]);
				}
				break;
			}
			
			// 순서 바꿔주기
			while(!stack.isEmpty()) {
				map[nx][ny][idx++] = stack.pop();
			}
		}
		
		return false; // 끝나지 않음
	}
	
	public static boolean isOut(int x, int y) {
		return x < 0 || x >= N || y < 0 || y >= N;
	}
}
