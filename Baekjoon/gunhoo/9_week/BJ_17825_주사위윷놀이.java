package Gold.Gold2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 배열 다 만들고 특정 숫자 나올 시 다른 배열로가
 * 
 * @author Gunhoo
 *
 */
public class BJ_17825_주사위윷놀이 {
	static int[][] route = new int[4][30];
	static int[] dice;
	static int answer =0;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		route[0] = new int[]{0, 2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,-1,-1,-1,-1,-1,-1,-1};
		route[1] = new int[]{0,0,0,0,0,10, 13, 16,19,25,30,35,40,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		route[2] = new int[]{0,0,0,0,0, 0, 0, 0, 0, 0,20,22,24,25,30,35,40,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
		route[3] = new int[]{0,0,0,0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,30,28,27,26,25,30,35,40,-1,-1,-1,-1,-1};
		dice = new int[10];
		for(int i =0 ; i < 10;i++) {
			dice[i] = Integer.parseInt(st.nextToken()); 
		}
		boolean[][] visited = new boolean[4][30]; // 4개말, 30까지
		visited[0][0] =true;
		play(0, new int[4], new int[4], route, visited, 0);
		System.out.println(answer);
	}
	private static void play(int cnt, int[] x, int[] y, int[][] map, boolean[][] visited, int score) {
		if (cnt == 10) {
			if(score > answer) answer = score;
			return;
		}
		for(int i = 0; i < 4; i++) {
			int dx = x[i];
			int dy = y[i];
			if(map[x[i]][y[i]] == -1) continue;
			visited[x[i]][y[i]]= false;
			int nx = 0;
			if(x[i] == 0) {
				switch(y[i]) {
				case 5 : nx = 1; break;
				case 10: nx = 2; break;
				case 15: nx = 3; break;
				}
			}
			x[i] += nx;
			y[i] += dice[cnt];
			int m = map[x[i]][y[i]];
			if(x[i]!=0) {
				switch(m) {
				case 40:
					x[i] = 0;y[i]=20;
					break;
				case 25:
					x[i]=1;y[i]=9;break;
				case 30:
					x[i] = 1;y[i]=10;break;
				case 35 : 
					x[i]=1;y[i]=11;break;
				}
			}
			if(!visited[x[i]][y[i]]) {
				if(m!=-1) {
					visited[x[i]][y[i]] = true;
					play(cnt+1,x,y,map,visited,score+m);
					visited[x[i]][y[i]] = false;
				}else {
					play(cnt+1,x,y,map,visited,score);
				}
				
			}
			x[i]=dx;
			y[i]=dy;
			visited[x[i]][y[i]]= true;
		}
	}
}
