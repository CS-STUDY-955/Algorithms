package Platinum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
/**
 * 
 * @author 박건후
 *
 */
public class BJ_1102_발전소{
	static final int INF = 1000000;
	static int N, P, map[][], dp[][], answer;
	
	public static void main(String[] args) throws Exception{
		if(init()) System.out.println(0);
		else{
			System.out.println(answer==INF?-1:answer);
		}
	}
	
	private static int turnOn(int cnt, int bit) {
		if(cnt >= P) return 0;
		if(dp[cnt][bit] >= 0) return dp[cnt][bit];
		dp[cnt][bit] = INF;
		for(int i = 0; i < N ; i++) {
			if((bit & (1<<i)) != 0) {
				for(int j = 0 ; j < N ; j++) {
					if(i == j || (bit & (1<<j)) != 0 ) continue;
					dp[cnt][bit] = Math.min(dp[cnt][bit], turnOn(cnt+1, bit | (1<<j)) + map[i][j]);
				}
			}
		}
		return dp[cnt][bit];
	}

	private static boolean init() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		dp = new int[N+1][1<<16];
		int cnt = 0;
		for(int i=0; i<N;i++) {
			Arrays.fill(dp[i], -1);
			st= new StringTokenizer(br.readLine());
			for(int j=0; j<N;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		Arrays.fill(dp[N], -1);
		String input = br.readLine();
		int bit =0;
		for(int i =0 ; i < N; i++) {
			if(input.charAt(i) == 'Y') {
				bit |= (1<<i);
				cnt++;
			}
		}
		P = Integer.parseInt(br.readLine());
		if(cnt >= P || P == 0) return true;
		answer = turnOn(cnt, bit);
		return false;
	}
}
