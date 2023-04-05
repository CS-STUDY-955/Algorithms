package Gold.Gold3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * dfs로 깊이 탐색, backtracking? 안될듯
 * > 당연 시간초과겠지
 * > memoization 
 * @author Gunhoo
 *
 */
public class BJ_1937_욕심쟁이판다 {
	static int N;
	static int[][] map, dp;
	static int answer = 0;
	static int maxIdx = 0;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		dp = new int[N][N];
		for(int i =0; i < N ; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j =0 ; j < N ; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				dp[i][j] = 1;
			}
		}
		for(int i =0 ;i < N ;i++) {
			for(int j = 0 ; j < N ;j++) {
//				dfs(i, j, 1);
				 memo(i, j,1); // i.j가 가장 큰 곳이라고 생각했을 때 memoization 시작
			}
		}
		print();
		System.out.println(answer);
	}
	static void print() { // 최댓값 갱신
		for(int i =0; i < N ;i++) {
			for(int j =0; j < N ;j++) {
				if(answer < dp[i][j]) answer = dp[i][j];
			}
		}
	}
	static int[] dx = {1,-1,0,0};
	static int[] dy = {0,0,1,-1};
	private static int memo(int a, int b, int depth) { // 메모이제이션(큰 곳부터 작은곳으로가)
		for(int i = 0; i< 4; i++) { // 4방향으로
			int nx = a + dx[i];
			int ny = b + dy[i];
			if(0<=nx && nx< N && 0<=ny && ny <N && map[nx][ny] < map[a][b]) { // 이동한 곳이 더작으면,
				if(dp[nx][ny] !=1 ) { // 그곳이 방문한 곳이라면, 
					dp[a][b] = Math.max(dp[nx][ny]+1, dp[a][b]); // 그곳에서거리+1과 현재 내가 가진 값이랑 비교 
				}else {  // 이동한 곳이 방문하지 않은 곳이라면,
					dp[a][b] = Math.max(memo(nx, ny, depth+1)+1, dp[a][b]); // 이동한 곳에서+1증가한 거리와 현재 내 값이랑 비교 
				}
			}
		}
		return dp[a][b]; // 그곳의 최대 거리값 리턴
	}
	private static void dfs(int a, int b, int depth) {
		for(int i =0; i < 4; i++) {
			int nx = a + dx[i];
			int ny = b + dy[i];
			if(0<=nx && nx< N && 0<=ny && ny <N && map[nx][ny] > map[a][b]) {
				dfs(nx, ny, depth+1);
			}
		}
		answer = Math.max(answer, depth);
	}
}
