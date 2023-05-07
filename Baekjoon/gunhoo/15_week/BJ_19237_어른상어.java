package Gold.Gold2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.*;

public class BJ_19237_어른상어 {
	static int N, M ;
	static int t, k;
	static int[][] water;
	static int[][][] dir;
	static int[][][] smell;
	static int[] D;
	static int[] dx = {0, -1, 1, 0, 0};
	static int[] dy = {0, 0, 0, -1, 1};
	
	public static void main(String[] args) throws Exception {
		init();
		for(t=1; t<=1000; t++) {
			move(t);
			if(M == 1) break;
		}
		System.out.println(t==1001?-1:t);
	}
	private static void move(int t) {
		int[][] tmp = new int[N][N];
		ArrayDeque<int[]> newsmell = new ArrayDeque<>();
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(water[i][j] == 0) continue;
				int s = water[i][j]; 
				int mx = N; 
				int my = N; 
				int md = 0;
				for(int d = 1; d<=4; d++) {
					int ni = i + dx[dir[s][D[s]][d]];
					int nj = j + dy[dir[s][D[s]][d]];
					if(ni < 0 || nj < 0 || ni >= N || nj >= N) continue;
					if(smell[ni][nj][0] != s && smell[ni][nj][1] >= t) continue;
					if(smell[ni][nj][1] < t) {
						mx = ni; 
						my = nj; 
						md = dir[s][D[s]][d]; 
						break;
					}
					else if(smell[ni][nj][0] == s && mx == N){ 
						mx = ni; my = nj;
						md = dir[s][D[s]][d];
					}
				}
				if(tmp[mx][my] == 0) {
					tmp[mx][my] = water[i][j];
					newsmell.offer(new int[] {mx, my, s, t+k});
				} else if(tmp[mx][my] > water[i][j]) {
					tmp[mx][my] = water[i][j];
					M--;
					newsmell.offer(new int[] {mx, my, s, t+k});
				} else M--;
				D[s] = md;
			}
		}
		while(!newsmell.isEmpty()) {
			int[] n = newsmell.poll();
			smell[n[0]][n[1]][0] = n[2];
			smell[n[0]][n[1]][1] = n[3];
		}
		water = tmp;
	}
	private static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		water = new int[N][N];
		smell = new int[N][N][2];
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				water[i][j] = Integer.parseInt(st.nextToken());
				if(water[i][j] > 0) {
					smell[i][j][0] = water[i][j];
					smell[i][j][1] = k;
				}
			}
		}
		D = new int[M+1];
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i<=M; i++) {
			D[i] = Integer.parseInt(st.nextToken());
		}
		dir = new int[M+1][5][5];
		for(int i=1; i<=M; i++) {
			for(int d = 1; d<=4; d++) {
				st = new StringTokenizer(br.readLine());
				for(int u=1; u<=4; u++) {
					dir[i][d][u] = Integer.parseInt(st.nextToken());
				}
			}
		}
	}
}
