package Gold;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 14502 연구소
 * 1. 풀이방법 : 벽을 어디에 세울지
 * 	- 그래프 탐색 : BFS?
 * 	- 2의 개수는 2~10자연수
 * 	- 0. 2의 총 개수 알아야 해
 * 	- 1. 2 주변 3면이 막혀있으면 막아
 * 		- 만약 그게 3개 초과면?
 *  - 완탐?
 *  	- 벽을 모든 0에 대해 3군데 쌓고 (n^3) 2에서부터 bfs > 남은 0개수 return  
 *  	- 3 <= n,m <= 8 이므로 n^3 계산 가능할 듯
 * 벽을 3개 만들 수 있음, 바이러스는 계속 퍼짐(BFS)
 * @author gunhoo
 *
 */

public class BJ_14502_연구소 {
	static int n, m;
	static int[][] map, tmpMap;
	static int totalVirus = 0, wall = 0;
	static int[] dx = {1,-1,0,0};
	static int[] dy = {0,0,-1,1};
	static int ans = 0;
	static Queue<Node> virusQ = new LinkedList<>();
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		map = new int[n][m];
		tmpMap = new int[n][m];
		for(int i = 0; i< n ; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j< m ; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 2) {
					totalVirus++; // 총 바이러스 수 
					virusQ.add(new Node(i,j));
				}
			}
		}
		/* ----- 입력 끝 ----- */
		buildWall(); // 벽을 세워보자
		System.out.println(ans);
		
	}
	private static void buildWall() { // 모든 케이스 탐색하는 메서드
		for(int i = 0; i< n*m-2 ; i++) {
			if(map[i/m][i%m] != 0) continue; // 빈공간이 아니면 벽을 세울 수 없다.
			for(int j = i+1; j<n*m-1; j++) {
				if(map[j/m][j%m] != 0) continue;
				for(int k = j+1; k<n*m ; k++) {
					if(map[k/m][k%m] != 0) continue;
					map[i/m][i%m] = 1; // 벽을 1개 세우고
					map[j/m][j%m] = 1; // 두번째 벽
					map[k/m][k%m] = 1; // 세번째 벽
					bfs(); // bfs돌아서 max값 구해보자
					map[i/m][i%m] = 0; // 벽 세운거 다시 해제하기
					map[j/m][j%m] = 0;
					map[k/m][k%m] = 0;
				}
			}
		}
	}
	
	private static void bfs() {
		for(int i = 0 ; i< map.length ; i++) { // 현재 map을 복사한다 
			tmpMap[i] = Arrays.copyOf(map[i], map[i].length);
		}
		for(int v = 0; v< totalVirus; v++) { // 모든 바이러스에 대해 
			Node virus = virusQ.poll();
			Queue<Node> q = new LinkedList<>(); // 2로 감염시키기 위한 탐색 q
			q.add(virus);
			while(!q.isEmpty()) {
				Node newVirus = q.poll(); // 다음 virus를 뽑고
				for(int i = 0 ; i< 4; i++) { // 4방향 탐색
					int nx = newVirus.x + dx[i]; 
					int ny = newVirus.y + dy[i];
					if(0<= nx && nx<n && 0<=ny && ny<m && tmpMap[nx][ny] == 0) { // 빈공간 있으면
						tmpMap[nx][ny] = 2; // 바이러스 감염시키고
						q.add(new Node(nx, ny)); // 큐에 넣어주기
					}
				}
			}
			virusQ.add(virus); // 반복해야하니까 다시 넣어줘
		}
		countSafetyZone(); // 전부 감염시키고 0개수 세줘
	}
	private static void countSafetyZone() { // 현재 map에서 0개수 세주고 max값이랑 비교해서 큰 값 저장
		int sum = 0;
		for(int i = 0; i< n; i++) {
			for(int j = 0 ; j< m ; j++) {
				if(tmpMap[i][j] == 0) { // 모든 0 개수 세고 sum에 넣어준다
					sum++;
				}
			}
		}
		ans = Math.max(ans, sum); // 가지고 있는 ans와 비교해서 최대값 갱신
	}
	static class Node{
		int x, y;
		public Node(int a, int b) {
			this.x = a;
			this.y = b;
		}
	}

}
