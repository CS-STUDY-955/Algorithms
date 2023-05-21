package gold1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BJ_1562_계단수 {
	
	static int N, count; // 자리수, 계단수 갯수
	static int mod = 1000000000; // 나눠주기
	static int[][][] memo; // DP
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		memo = new int[N+1][10][1<<10];
		for(int i = 1; i <= 9; i++) { // i부터 시작하는 계단수 찾기
			count += dfs(1, i, 1 << i);
			count %= mod; // 나눠주기
		}
		
		System.out.println(count); // 출력
	}

	private static int dfs(int len, int num, int flag) {
		if(memo[len][num][flag] != 0) return memo[len][num][flag]; // 이미 수를 구했다면 그 값 리턴
		if(len == N) return flag == (1 << 10) - 1 ? 1 : 0; // 길이가 N일때 0부터 9까지의 수가 모두 포함되었다면 1, 아니면 0
		
		int cnt = 0; // 갯수
		if(num > 0) cnt += dfs(len+1, num-1, flag | (1<<(num-1))); // 내려가는 계단수 구하기
		if(num < 9) cnt += dfs(len+1, num+1, flag | (1<<(num+1))); // 올라가는 계단수 구하기
		
		return memo[len][num][flag] = cnt % mod; // 다 구했다면 memo 갱신하고 갯수 반환
	}
}
