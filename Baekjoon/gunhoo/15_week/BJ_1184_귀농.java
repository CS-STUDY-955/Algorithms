package Platinum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BJ_1184_귀농 {
	
	static int[][] map, dp;
	static ArrayList<Integer> sum1, sum2;
	static int N, ans;
	
	public static void main(String[] args) throws Exception {
		init();
		cal();
		System.out.println(ans);
	}
	private static void cal() {
		for(int i = 0 ; i < N - 1 ; ++i) {
			for(int j = 0 ; j < N - 1 ; ++j) {
				sumUpperLeft(i, j);
				sumUnderRight(i + 1, j + 1);
				count();
				clean();
				sumUpperRight(i, j + 1);
				sumUnderLeft(i + 1, j);
				count();
				clean();
			}
		}
	}

	private static void sumUnderLeft(int R, int C) {
		for(int i = R ; i < N ; ++i) {
			int sum = 0;
			for(int j = C ; j >= 0 ; --j) {
				sum += map[i][j];
				
				if(i == R) {
					dp[i][j] = sum;
				} else {
					dp[i][j] = dp[i - 1][j] + sum;
				}
				
				sum2.add(dp[i][j]);
			}
		}
	}

	private static void sumUpperRight(int R, int C) {
		for(int i = R ; i >= 0 ; --i) {
			int sum = 0;
			for(int j = C ; j < N ; ++j) {
				sum += map[i][j];
				if(i == R)dp[i][j] = sum;
				else dp[i][j] = dp[i + 1][j] + sum;
				sum1.add(dp[i][j]);
			}
		}
	}

	private static void sumUnderRight(int R, int C) {
		for(int i = R ; i < N ; ++i) {
			int sum = 0;
			for(int j = C ; j < N ; ++j) {
				sum += map[i][j];
				if(i == R)dp[i][j] = sum;
				else dp[i][j] = dp[i - 1][j] + sum;
				sum2.add(dp[i][j]);
			}
		}
	}

	private static void sumUpperLeft(int R, int C) {
		for(int i = R ; i >= 0 ; --i) {
			int sum = 0;
			for(int j = C ; j >= 0 ; --j) {
				sum += map[i][j];
				if(i == R) dp[i][j] = sum; 
				else dp[i][j] = dp[i + 1][j] + sum;
				sum1.add(dp[i][j]);
			}
		}
	}

	private static void count() {
		for(int i : sum1) {
			for(int j : sum2) {
				if(i == j) ans++;
			}
		}
	}

	private static void clean() {
		sum1.clear();
		sum2.clear();
		for(int r = 0 ; r < N ; ++r) {
			for(int c = 0 ; c < N ; ++c) {
				dp[r][c] = 0;
			}
		}
	}
	
	private static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		dp = new int[N][N];
		sum1 = new ArrayList<>();
		sum2 = new ArrayList<>();
		ans = 0;

		for(int i = 0 ; i < N ; ++i) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0 ; j < N ; ++j) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	}
}