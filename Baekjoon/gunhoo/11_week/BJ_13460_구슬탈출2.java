package Gold.Gold1;


import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_13460_구슬탈출2 {
	static int N, M, answer = -1;
	static char[][] map;
	static Point redBall, blueBall;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new char[N][M];
		for(int i = 0 ; i < N ; i++) {
			String input = br.readLine();
			for(int j = 0; j < M ; j++) {
				map[i][j] = input.charAt(j);
				if(map[i][j] == 'R') {
					redBall = new Point(i, j);
				} else if (map[i][j] == 'B'){
					blueBall = new Point(i,j);
				}
			}
		}
		
		escape(map, 1, redBall, blueBall);
		System.out.println(answer);
	}
	static int[][] direction = {{-1,0}, {1,0}, {0,-1}, {0,1}}; // 상하좌우
	private static boolean escape(char[][] map, int cnt, Point redBall, Point blueBall) {
		if(cnt > 10) {
			answer = -1;
			return false;
		}
		int originRedX = redBall.x, originRedY = redBall.y;
		int originBlueX = blueBall.x, originBlueY = blueBall.y;
		boolean redWin=false, blueWin=false;
		for(int i = 0 ; i < 4 ; i++) { // 4방향으로
			// 굴려
			// 자신위치~벽까지 탐색하면서 0있으면 return
			// # 발견하면 그 전까지로 이동
			// 이동 완료하면 escape()재귀
			int rx = originRedX, ry = originRedY;
			int bx = originBlueX, by = originBlueY;
			boolean dup = false;
			while(true) {
				rx += direction[i][0];
				ry += direction[i][1];
				if(map[rx][ry] == 'B') { // 가는길에 BlueBall이 있으면,
					dup = true;
				}
				if(map[rx][ry] == '#' || map[rx][ry] == 'B') {
					map[redBall.x][redBall.y] = '.';
					map[rx-direction[i][0]][ry-direction[i][1]] = 'R';
					redBall.x = rx-direction[i][0]; redBall.y = ry-direction[i][1];
					break;
				}else if(map[rx][ry] == 'O') {
					map[redBall.x][redBall.y] = '.';
					redWin = true;
					break;
				}
			}
			while(true) {
				bx += direction[i][0];
				by += direction[i][1];
				if(map[bx][by] == '#' || map[bx][by] == 'R') {
					map[blueBall.x][blueBall.y] = '.';
					map[bx-direction[i][0]][by-direction[i][1]] = 'B';
					blueBall.x = bx-direction[i][0]; blueBall.y = by-direction[i][1];
					break;
				}else if(map[bx][by] == 'O') {
					map[blueBall.x][blueBall.y] = '.';
					blueWin = true;
					break;
				}
			}
			if(dup) {
				redBall.x -= direction[i][0];
				redBall.y -= direction[i][1];
			}
//			print(map);
			if(redWin && blueWin) {
				return true;
			}else if(redWin) {
				answer = cnt;
				return true;
			}else if(!redWin && !blueWin){
				print(map);
				escape(map, cnt+1, redBall, blueBall);
			}
		}
		return false;
	}

	private static void print(char[][] map) {
		for(int i=0;i < N;i++) {
			for(int j =0 ; j <M; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

}
