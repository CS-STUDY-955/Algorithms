package Gold.Gold2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_12100_2048easy {
	static int N, map[][], answer = Integer.MIN_VALUE;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		for(int i =0 ; i< N ;i++) {
			st= new StringTokenizer(br.readLine());
			for(int j =0 ; j < N ; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		dfs(map, 0);
		System.out.println(answer);
	}
	
	private static void dfs(int[][] map, int cnt) {
		if(cnt == 5) {
			answer = Math.max(answer, cal(map));
			return;
		}
		// 상하좌우로 움직였을 떄 각각 dfs 돌려
		int[][] tmpUp = new int[N][N];
		int[][] tmpBot = new int[N][N];
		int[][] tmpLeft = new int[N][N];
		int[][] tmpRight = new int[N][N];
		for(int i =0; i < N ;i++) {
			tmpUp[i] = Arrays.copyOf(map[i], map[i].length);
			tmpBot[i] = Arrays.copyOf(map[i], map[i].length);
			tmpLeft[i] = Arrays.copyOf(map[i], map[i].length);
			tmpRight[i] = Arrays.copyOf(map[i], map[i].length);
		}
		
		up(tmpUp);
		bot(tmpBot);
		left(tmpLeft);
		right(tmpRight);
		dfs(tmpUp, cnt+1);
		dfs(tmpBot, cnt+1);
		dfs(tmpLeft, cnt+1);
		dfs(tmpRight, cnt+1);
	}
	
	private static void up(int[][] map) {
		for(int i = 0; i < N; i++) {
            int idx = 0;
            int tmp = 0;
            for(int j = 0; j < N; j++) {
                if(map[j][i] != 0) {
                    if(tmp == map[j][i]) {
                        map[idx-1][i] = tmp * 2;
                        tmp = 0;
                        map[j][i] = 0;
                    }
                    else {
                    	tmp = map[j][i];
                        map[j][i] = 0;
                        map[idx][i] = tmp;
                        idx++;
                    }
                }
            }
        }
	}
	
	private static void bot(int[][] map) {
		 for(int i = 0; i < N; i++) {
             int idx = N - 1;
             int tmp = 0;
             for(int j = N - 1; j >= 0; j--) {
                 if(map[j][i] != 0) {
                     if(tmp == map[j][i]) {
                         map[idx+1][i] = tmp*2;
                         tmp = 0;
                         map[j][i] = 0;
                     }
                     else {
                    	 tmp = map[j][i];
                         map[j][i] = 0;
                         map[idx][i] = tmp;
                         idx--;
                     }
                 }
             }
		 }
	}
	
	private static void left(int[][] map) {
		for(int i = 0; i < N; i++) {
            int idx = 0;
            int tmp = 0;
            for(int j = 0; j < N; j++) {
                if(map[i][j] != 0) {
                    if(tmp == map[i][j]) {
                        map[i][idx-1] = tmp*2;
                        tmp = 0;
                        map[i][j] = 0;
                    }
                    else {
                    	tmp = map[i][j];
                        map[i][j] = 0;
                        map[i][idx] = tmp;
                        idx++;
                    }
                }
            }
        }
	}

	private static void right(int[][] map) {
		for(int i = 0; i < N; i++) {
            int idx = N - 1;
            int tmp = 0;
            for(int j = N - 1; j >= 0; j--) {
                if(map[i][j] != 0) {
                    if(tmp == map[i][j]) {
                        map[i][idx + 1] = tmp * 2;
                        tmp = 0;
                        map[i][j] = 0;
                    }
                    else {
                    	tmp = map[i][j];
                        map[i][j] = 0;
                        map[i][idx] = tmp;
                        idx--;
                    }
                }
            }
        }
	}
	
	private static int cal(int[][] map) {
		int max = 0;
		for(int i =0; i < N; i++) {
			for(int j = 0; j<N;j++) {
				if(max < map[i][j]) max = map[i][j];
			}
		}
		return max;
	}

}
