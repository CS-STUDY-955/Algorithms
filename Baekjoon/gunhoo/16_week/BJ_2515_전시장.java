package Gold.Gold2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_2515_전시장 {
	static int N, S, drawings[][];
	static int index[], dp[];
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		drawings = new int[N+1][2];
		dp = new int[N+1]; index = new int[N+1];
		
		for(int i = 1 ; i <= N ; i++) {
			st = new StringTokenizer(br.readLine());
			drawings[i][0] = Integer.parseInt(st.nextToken());
			drawings[i][1] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(drawings, 1, N+1, (o1, o2) -> o1[0] - o2[0]);
		
		for(int i = 1 ; i <= N ; i++) {
			if(drawings[i][0] < S) continue;
			for(index[i] = index[i-1] ; index[i] < i ; index[i]++) {
				if(drawings[i][0] - drawings[index[i]][0] < S) break;
			}
			index[i]--;
		}
		
		for(int i = 1; i <= N; i++) {
			dp[i] = drawings[i][1]+dp[index[i]];
			dp[i] = Math.max(dp[i], dp[i-1]);
		}
		
		System.out.println(dp[N]);
	}

}
