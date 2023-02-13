import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_16236_아기상어 {
	static int[][] map;
	static int[] dx = {-1, 0, 0, 1};
	static int[] dy = {0, -1, 1, 0};
	static int n;
	static int size =2;
	static int sizeCond = 0;
	static int distance = 0, tmpDistance = 0; // 총 거리를 저장하기 위한 
	static int[][] visited;
	static int totalPrey = 0;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		map = new int[n][n];
		int babyX = 0, babyY = 0; // 아기상어 시작위치 저장을 위한 변수
		
		StringTokenizer st;
		for(int i = 0 ; i < n ; i++) { //  입력
			st = new StringTokenizer(br.readLine());
			for(int j = 0 ; j < n ; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 9) {
					babyX = i;
					babyY = j;
					map[babyX][babyY] = 0;
				}else if( map[i][j] != 0) {
					totalPrey++;
				}
			}
		}
		if(totalPrey == 0 ) System.out.println(0);
		else {
			bfs(babyX, babyY, 0);
			System.out.println(distance);		
		}
	}
	

	private static void bfs(int a, int b, int d) {
		ArrayList<Node> candidates = new ArrayList<Node>();
		Queue<Node> queue = new LinkedList<Node>();
		queue.offer(new Node(a, b, d));
		visited = new int[n][n]; 
		while(!queue.isEmpty() || totalPrey != 0) {
			Node node = queue.poll();
			if(node == null) break; // 탈출조건
			for(int i = 0 ; i< 4; i++) {
				int nx = node.x+dx[i];
				int ny = node.y+dy[i];
				if( 0<= nx && nx< n && 0<= ny && ny<n && visited[nx][ny] == 0) { // 범위 내고 갈 수 있으면,
					if(map[nx][ny] >=0 && map[nx][ny] <= size){ // 지나갈 수 있으면
						visited[nx][ny] = visited[node.x][node.y]+1; // 현재노드에서 +1 만큼 거리 측정한다
						queue.offer(new Node(nx, ny, visited[nx][ny])); // 현재 위치 넣어준다
						// System.out.println("q에 더하기 "+nx+" "+ny+" "+visited[nx][ny]);
						if( map[nx][ny]>0 && map[nx][ny] < size) { // 근데 그게 먹을 수 있는 물고기면,
							candidates.add(new Node(nx, ny, visited[nx][ny]));
						}
					}
					
				}
			}
		}
		if( candidates.size() == 0){ // 현재위치에서 큐를 다 먹고 더이상 먹을 수 있는 후보가 없으면
			return; // 종료
		}
		Node nextFish = candidates.get(0);
		for(int i = 1 ; i< candidates.size(); i++){ // 후보 fish중 가장짧은 놈 선택
			if (candidates.get(i).distanceFromShark <= nextFish.distanceFromShark){  // 거리짧거나 같은 놈중에
				if( candidates.get(i).x < nextFish.x)nextFish = candidates.get(i); // x좌표(높이)가 더 작으면 무조건 교체
				else if( candidates.get(i).x == nextFish.x) { // x좌표(높이)가 같으면
					if(candidates.get(i).y < nextFish.y) nextFish = candidates.get(i); //y좌표가 작은(왼쪽) 놈 선택
				}
			}
		}
		// 먹는 로직
		map[nextFish.x][nextFish.y] = 0; // 먹었으니 0으로 바꿈
		distance += nextFish.distanceFromShark; // 거리 더해준다. 
//		System.out.println("냠냠 : "+nextFish.x+" "+nextFish.y+" "+nextFish.distanceFromShark+" "+distance);
		if(++sizeCond == size) { // 먹었으니 cond 1올려주고, size만큼 먹으면 
			size++; // 사이즈 키운다.
			sizeCond = 0; // 초기와
		}
		bfs(nextFish.x, nextFish.y, 0); // 먹은 놈부터 다시 bfs시작

	}
	static class Node {
		int x, y, distanceFromShark;
		public Node(int x, int y, int dis) {
			this.x = x;
			this.y = y;
			this.distanceFromShark = dis;
		}
	}

}
