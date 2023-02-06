package Gold.bj15686;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BJ_15686 {
	private static int[][] city;
	private static ArrayList<Node> chickens = new ArrayList<Node>();
	private static ArrayList<Node> homes = new ArrayList<Node>();
	private static boolean[] visited;
	private static int m;
	private static int min = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		city = new int[n][n];
		
		for(int i = 0; i < n ; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<n ; j++) {
				city[i][j] = Integer.parseInt(st.nextToken());
				if (city[i][j] == 2)
					chickens.add(new Node(i, j));
				else if (city[i][j] == 1)
					homes.add(new Node(i, j));
			}
		}
		
		visited = new boolean[chickens.size()];
		backtracking(0,0);
		System.out.println(min);
		
	}
	private static void backtracking(int depth, int count) {
		if(depth == m) {
			int chickenDistance = 0;
			for(int i = 0; i< homes.size();i++) {// 모든 집에 대해
				int sum = Integer.MAX_VALUE;
				for(int j = 0; j< chickens.size(); j++) {
					if(visited[j]) {
						int distance = Math.abs(homes.get(i).x-chickens.get(j).x)	
								+ Math.abs(homes.get(i).y-chickens.get(j).y);
						sum = Math.min(sum, distance);
					}
				}
				chickenDistance += sum;
			}
			min = Math.min(chickenDistance, min);
			return;
		}
		for(int i = count; i< chickens.size(); i++) {
			if(visited[i] == false) {
				visited[i] = true;
				backtracking(depth + 1, i + 1);
				visited[i] = false;
			}
		}
	}
	public static class Node{
		int x, y;
		
		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}
