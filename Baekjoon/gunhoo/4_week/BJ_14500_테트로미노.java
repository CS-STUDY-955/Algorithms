package Gold;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * BJ 14500 테트로미노
 * 정사각형 4개를 이어붙인 모양 : 테트로미노(5종)
 * 1. 풀이방법
 * bfs인데 조건을 상하죄우 아니고 각모양별로dxdy따로 만들어서
 * 1,1 ~ n,m까지 탐색하여(완전탐색)max값 업데이트하기
 *  > 각 모양별 dxdy 조건 만들기
 *   	> 너무 많은 경우의 수(모양 뒤집어도 되니깐)
 * 1-2 풀이방법2
 * 그냥 본인 위치에서 dfs 돌리는데 깊이 4가 되면 종료
 * 2. 풀이시간
 * 10:38~11:46 1시간 (1 bfs 구현 시간낭비)
 * 9:11~9:50 40분 (1-2 ㅗ 모양 탐색 못해)
 * 10:40~11:40 1시간 (1.2에서 ㅗ 모양 추가)
 * @author Gunhoo
 *
 */
public class BJ_14500_테트로미노 {
	static int n,m;
	static int[][] map;
	static boolean[][] visited;
	static int max = 0;
	static int tmpMax = 0;
	static int[] dx = {1,0,0,-1}; // 하 우 좌 상
	static int[] dy = {0,1,-1,0};
	
 	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		map = new int[n+2][m+2]; // 테두리 둘러쌈
		visited = new boolean[n+2][m+2];
		for(int i = 1 ; i<=n ; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 1 ; j <= m ; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		for(int i = 1 ; i <= n ;i++)
			for(int j = 1; j <= m ; j++) {
				visited[i][j] = true; // 본인은 선택하고
				tmpMax = map[i][j]; // max후보에 더해줌
				dfs(i,j, 1); // x, y, depth(본인은무조건 선택하고 시작하므로 1부터 시작)
				visited[i][j] = false; // 본인차례 끝나면 다시 방문할 수 있으므로 false
			}
		System.out.println(max);
	}
	
	private static void dfs(int a, int b, int depth) {
		if(depth == 1) {// 상좌우(ㅗ), 상하좌(ㅓ), 상하우(ㅏ), 하좌우(ㅜ) 탐색 후 max값 업데이트
			max = Math.max(max, (map[a][b]+map[a-1][b]+map[a][b-1]+map[a][b+1])); // 상좌우(ㅗ)
			max = Math.max(max, (map[a][b]+map[a-1][b]+map[a+1][b]+map[a][b-1])); // 상하좌(ㅓ)
			max = Math.max(max, (map[a][b]+map[a-1][b]+map[a+1][b]+map[a][b+1])); // 상하우(ㅏ)
			max = Math.max(max, (map[a][b]+map[a+1][b]+map[a][b-1]+map[a][b+1])); // 하좌우(ㅜ)
		}
		if( depth == 4) { // 종료조건
			max = Math.max(tmpMax, max); // 비교후 업데이트
			return; // 종료
		}
		for(int i = 0 ; i< 4 ; i++){ // 4방탐색
			int nx = a+ dx[i]; 
			int ny = b + dy[i];
			if( 0< nx && nx<=n && 0<ny && ny<=m && visited[nx][ny] == false){ // 조건만족하면
				visited[nx][ny] = true; // 1개 탐색하고 그걸 dfs(재귀)에서 다시방문하지 않기 위해 트루
				tmpMax += map[nx][ny]; // 그 값을 max후보(tmpMax)에 누적해서 더해주고
				dfs(nx, ny, depth+1); // 깊이 1추가해서 dfs(재귀) 
				visited[nx][ny] = false; // 1개 탐색종료하고 그걸 나중에 다시 방문해야하니 false로 초기화
				tmpMax -= map[nx][ny]; // max값 업데이트 됐으니 tmpMax는 방문노드의 값 빼줌
			}
		}
	}
	static class Node{
		int x, y;
		public Node(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
}
