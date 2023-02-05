package Gold.bj15686;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;


/**
 * 풀이시간 14:40 ~ 16:20 (1시간 40분)
 * 1. 풀이방법
 * 	- bfs?
 * - 모든 2에대해 자신부터 모든 1까지의 합 계산 
 * - 합이 가장 짧은 m개 선택함
 * - 각 1에 대해 2를 만나는 거리의 합 출력
 * > 예제 2번에서 [0][1]이 아니라 [4][1]을 선택하는 반례 
 * - 거리 별로 2랑 가까운게 많은 순서 정렬 추가하여 반례 극복(20분) > 메모리 초과
 * > 알고리즘 유형 확인 : 구현, 백트래킹, 브루트포스 
 *  > 솔루션 확인
 * 2. 보완사항
 *  - backtracking 공부 필요!!(N-queen 문제 공부)
 * @author Gunhoo
 *
 */

public class BJ_15686_BFS {
	static int[][] city;
	static int[] dx =  {0,0,1,-1};
	static int[] dy =  {1,-1,0,0};
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		city = new int[n][n];
		
		for(int i = 0; i < n ; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<n ; j++) {
				city[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		List<Node> chickenDistance = new ArrayList<Node>();
		for(int i = 0; i< n ; i++) {
			for(int j = 0; j< n ; j++) {
				if(city[i][j] == 2) {
					chickenDistance.add(new Node(i, j, calDis(i, j,n)));
				}
			}
		}
		int ans = 0;
		if( chickenDistance.size() > m) { // 예제 1이 아닌경우
			Collections.sort(chickenDistance, new Comparator<Node>() {
				@Override
				public int compare(Node o1, Node o2) {
					if (o2.allSumDistance == o1.allSumDistance) {
						return Integer.compare(countOne(o1.i, o1.j, n), countOne(o2.i, o2.j, n));
					}
					return Integer.compare(o2.allSumDistance, o1.allSumDistance);
				}
				
			});
			// 폐업시킬 점포들의 값을 0으로 바꿔준다
			for(int i = 0; i< chickenDistance.size()-m ; i++) {
				city[chickenDistance.get(i).i][chickenDistance.get(i).j] = 0;
			}
			
			
		}
		for(int i = 0; i< n; i++) {
			for(int j = 0; j<n;j++) {
				if(city[i][j] == 1) {
					ans += bfs(i, j, n);
//					System.out.println("i : "+i+" j : "+j + " bfs : "+bfs(i,j,n));
				}
			}
		}
		
		System.out.println(ans);
		
	}
	
	private static int calDis(int x, int y, int n) {
		int sum = 0;
		for(int i = 0; i< n; i++) {
			for(int j = 0; j<n;j++) {
				if(city[i][j] == 1) {
					sum += Math.abs(x-i)+Math.abs(y-j);
				}
			}
		}
		return sum;
	}
	
	private static int bfs(int x, int y, int n) {
		Queue<Node> q = new LinkedList<>();
		q.add(new Node(x, y));
		while(!(q.isEmpty()) ) {
			Node node = q.poll();
			for(int i = 0; i<4 ; i++) {
				int nx = node.i + dx[i];
				int ny = node.j + dy[i];
				if( nx < n && 0<= nx && 0<= ny && ny<n) {
					if(city[nx][ny] == 2)
						return Math.abs(nx-x)+Math.abs(ny-y);
					else q.add(new Node(nx, ny));
				}
			}
		}
		return 0;
	}
	
	private static int countOne(int x, int y, int n) {
		int ans = 0;
		Queue<Node> q = new LinkedList<>();
		boolean tf = false;
		q.add(new Node(x, y));
		
		while(!(q.isEmpty()) && !tf) {
			Node node = q.poll();
			for(int i = 0; i<4 ; i++) {
				int nx = node.i + dx[i];
				int ny = node.j + dy[i];
				if( nx < n && 0<= nx && 0<= ny && ny<n) {
					if(city[nx][ny] == 1) {
						ans += 1;
						tf = true;
					}
					else q.add(new Node(nx, ny));
				}
			}
		}
		return ans;
	}
	public static class Node{
		int i, j;
		int allSumDistance;
		public Node() {
			this.allSumDistance = Integer.MAX_VALUE;
		}
		
		public Node(int i, int j) {
			this();
			this.i = i;
			this.j = j;
		}
		public Node(int i, int j, int distance) {
			this(i, j);
			this.allSumDistance = distance;
		}	
	}

}
