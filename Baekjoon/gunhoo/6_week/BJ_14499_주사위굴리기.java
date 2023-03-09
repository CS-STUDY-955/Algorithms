package Gold.Gold4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_14499_주사위굴리기 {
	static int N, M;
	static int x, y;
	static int[][] map;
	static int[] dice = new int[7];
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		y = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		for(int i =0  ; i < N ; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M ; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		st = new StringTokenizer(br.readLine());
		for(int i = 0 ; i < K ; i++) {
			execute(Integer.parseInt(st.nextToken()));
		}
	}
	static int[] dy = {1, -1, 0, 0};
	static int[] dx = {0, 0, -1, 1};
	
	private static void execute(int command) {
		int nx = x + dx[command-1];
		int ny = y + dy[command-1];
		if(nx <0 || ny < 0 || nx > N-1 || ny > M-1) return;
		roll(command, nx, ny);
		x = nx; y = ny;
	}
	
	private static void roll(int num, int x, int y) {
		int tmp = dice[3];
		switch(num) {
		case 1:
			dice[3] = dice[4];
			dice[4] = dice[6];
			dice[6] = dice[2];
			dice[2] = tmp;
			break;
		case 2:
			dice[3] = dice[2];
			dice[2] = dice[6];
			dice[6] = dice[4];
			dice[4] = tmp;
			break;
		case 3:
			dice[3] = dice[5];
			dice[5] = dice[6];
			dice[6] = dice[1];
			dice[1] = tmp;
			break;
		case 4:
			dice[3] = dice[1];
			dice[1] = dice[6];
			dice[6] = dice[5];
			dice[5] = tmp;
			break;
		}
		if(map[x][y] == 0) {
			map[x][y] = dice[6];
		} else {
			dice[6] = map[x][y];
			map[x][y] =0;
		}
		System.out.println(dice[3]);
	}
}
