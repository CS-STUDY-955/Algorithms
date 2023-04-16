package Gold.Gold3;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_23288_주사위굴리기2 {
	static int N, M, K, map[][], dirIdx=1;//0:북 1:동 2:남 3:서
	static int direction[][] = {{-1,0}, {0,1}, {1,0}, {0,-1}};
	static int dice[] = {2,3,5,4,1,6}; // 북동남서상하
	static int answer = 0;
	public static void main(String[] args) throws Exception{
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		for(int i =0 ; i < N ;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j <M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		play();
		System.out.println(answer);
	}
	
	private static void play() {
		int curX=0,curY=0;
		for(int i = 0; i < K; i++) {
			int nx = curX+direction[dirIdx][0];
			int ny = curY+direction[dirIdx][1];
			if(0>nx || nx>=N || ny<0 || ny>=M ) {// 범위밖으로 나가면 반대방향으로이동
				dirIdx = blocked(dirIdx);
				nx = curX+direction[dirIdx][0];
				ny = curY+direction[dirIdx][1];
			}
			rotate();
			if( dice[5] > map[nx][ny]) {
				dirIdx = (dirIdx+1)%4;
			}else if( dice[5] < map[nx][ny]) {
				dirIdx = dirIdx-1>=0?dirIdx-1:3;
			}
			answer += map[nx][ny]*bfs(nx, ny, map[nx][ny]);
			curX = nx;
			curY = ny;
		}
	}
	private static void rotate() {
		int tmp;
		switch(dirIdx) {
		case 0:
			tmp = dice[0];
			dice[0] = dice[4];
			dice[4] = dice[2];
			dice[2] = dice[5];
			dice[5] = tmp;
			break;
		case 1:
			tmp = dice[5];
			dice[5] = dice[1];
			dice[1] = dice[4];
			dice[4] = dice[3];
			dice[3] = tmp;
			break;
		case 2:
			tmp = dice[4];
			dice[4] = dice[0];
			dice[0] = dice[5];
			dice[5] = dice[2];
			dice[2] = tmp;
			break;
		case 3:
			tmp = dice[3];
			dice[3] = dice[4];
			dice[4] = dice[1];
			dice[1] = dice[5];
			dice[5] = tmp;
			break;
		} 
	}
	private static boolean[][] visited; 
	private static int bfs(int a, int b, int val) {
		visited = new boolean[N][M];
		int count = 1;
		Queue<Point> q = new ArrayDeque<>();
		visited[a][b]= true;
		q.offer(new Point(a, b));
		while(!q.isEmpty()) {
			Point point = q.poll();
			for(int i =0 ;i < 4; i++) {
				int nx = point.x+direction[i][0];
				int ny =point.y+direction[i][1];
				if( nx<0 || nx>=N || ny<0 || ny>=M || visited[nx][ny] || map[nx][ny] != val)continue;
				visited[nx][ny]=true;
				count++;
				q.offer(new Point(nx, ny));
			}
		}
		return count;
	}
	private static int blocked(int dir) {
		switch(dir) {
		case 0:
			return 2;
		case 1:
			return 3;
		case 2:
			return 0;
		case 3:
			return 1;
		}
		return -1;
	}
}
