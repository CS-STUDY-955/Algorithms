package Gold.Gold3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
/**
 * 2에 대해 4방향으로 1씩 증가
 * @author SSAFY
 *
 */
public class BJ_17142_연구소3 {
	static int N, M;
	static int[][] map,tmap;
	static int dx[] = {1,-1,0,0};
	static int dy[] = {0,0,1,-1};
	static int[] numbers;
	static boolean[] visited;
	static boolean[][] visited2;
	static ArrayList<Node> virus;
	static int answer = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		virus = new ArrayList<>();
		int totalWall = 0;
		for(int i =0; i < N; i++) {
			st= new StringTokenizer(br.readLine());
			for(int j = 0 ; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 2) {
					virus.add(new Node(i, j, 2));
				}else if(map[i][j] == 0) {
					totalWall++;
				}
			}
		}
		if(totalWall == 0 && virus.size() != 0) System.out.println(0);
		else {
			numbers = new int[virus.size()];
			visited = new boolean[virus.size()];
			for(int i =0; i < virus.size();i++) {
				numbers[i] = i;
			}
			// 순서 상관 없이 골라 (조합) totalVirusCm
			combination(numbers, visited, 0, virus.size(), M);
			
			if(answer == Integer.MAX_VALUE) System.out.println(-1);
			else System.out.println(answer);
		}
		
	}
	static void printMap() {
		for(int i=0; i < N; i++) {
			for(int j = 0; j < N ;j++) {
				System.out.print(tmap[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	static void combination(int[] arr, boolean[] visited, int start, int n, int r) {
        if (r == 0) {
            infection(arr, visited, n);
            return;
        }

        for (int i = start; i < n; i++) {
            visited[i] = true;
            combination(arr, visited, i + 1, n, r - 1);
            visited[i] = false;
        }
    }
	
	static void infection(int[] arr, boolean[] visited, int n) {
		tmap = new int[N][N];
		visited2 = new boolean[N][N];
		int totalE = 0;
		for(int i =0 ;i < N ;i++) {
			for(int j = 0; j< N ;j++) {
				tmap[i][j] = map[i][j];
				if(tmap[i][j] == 0) totalE++;
			}
		}
		Queue<Node> q = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                // arr[i]번째 감염시켜
            	Node node = virus.get(arr[i]);
            	q.add(new Node(node.x, node.y, 2));
            }
        }
        while(!q.isEmpty()) {
        	Node node = q.poll();
        	for(int i =0 ;i < 4; i++) {
        		int nx = node.x + dx[i];
        		int ny = node.y + dy[i];
        		if( 0<= nx && nx < N && 0<= ny && ny < N && !visited2[nx][ny]) {
        			visited2[nx][ny] = true;
        			if(tmap[nx][ny] == 0) {
        				tmap[nx][ny] = node.time+1;
        				totalE--;
        				q.add(new Node(nx, ny, node.time+1));
        			}else if(tmap[nx][ny] == 2) { // 비활성 바이러스면
        				tmap[nx][ny] = node.time+1;
        				q.add(new Node(nx, ny, node.time+1));
        			}
        		}
        		if(totalE ==0 ) break;
        	}
        	if(totalE ==0) break;
        }
        int max =0;
        for(int i =0 ; i < N ; i++) {
        	for(int j = 0; j < N ; j++) {
        		if(tmap[i][j] == 0) { // 감염 못시킨거니까
        			return;
        		}
        		if(max < tmap[i][j]) { // 최댓값 갱신
    				max = tmap[i][j];
    			}
        	}
        }
        max -= 2;
//        printMap();
        answer = Math.min(max, answer);
    }
	static class Node{
		int x, y, time;

		public Node(int x, int y, int time) {
			super();
			this.x = x;
			this.y = y;
			this.time =time;
		}
		
	}
}
