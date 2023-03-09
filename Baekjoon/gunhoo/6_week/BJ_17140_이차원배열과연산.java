package Gold.Gold4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * 이차원배열과 연산
 * R : 모든 행에 대해 정렬(행>=열일때)
 * C : 모든 열에 대해 정렬(행<열일때)
 * ArrayList로 만들어
 *  
 * @author Gunhoo
 *
 */
public class BJ_17140_이차원배열과연산 {
	static class Node implements Comparable<Node>{
		int num;
		int cnt;
		public Node(int a, int b) {
			this.num = a;
			this.cnt = b;
		}
		@Override
		public int compareTo(Node o) {
			if(this.cnt == o.cnt) return this.num-o.num;
			return this.cnt-o.cnt;
		}
	}
	static int[][] map = new int[3][3];
	static int[][] tmpMap = new int[101][101];
	static ArrayList<Node> list = new ArrayList<>();
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int ans = -1;
		int r = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		for(int i = 0; i<3 ; i++) {
			st = new StringTokenizer(br.readLine());
			map[i][0] = Integer.parseInt(st.nextToken());
			map[i][1] = Integer.parseInt(st.nextToken());
			map[i][2] = Integer.parseInt(st.nextToken());
		}
		for(int i = 0; i < 101 ; i++) {
			if(r-1 < map.length && c-1<map[0].length &&map[r-1][c-1] == k) {
				ans = i;
				break;
			}
			sort();
		}
		System.out.println(ans);
	}
	private static void sort() {
		tmpMap = new int[101][101];
		if(map.length >= map[0].length) { // R연산
			int max = 0;
			for(int i =0 ; i < map.length; i++) {
				int[] visited = new int[101];
				for(int j = 0; j < map[0].length;j++) {
					if(map[i][j] == 0)continue;
					visited[map[i][j]] += 1;
				}
				list = new ArrayList<>();
				for(int j =1 ; j<101; j++) {
					if(visited[j] != 0) list.add(new Node(j, visited[j]));
				}
				Collections.sort(list);
				int k =0;
				for(int j = 0 ; j < list.size(); j++) {
					tmpMap[i][k++] = list.get(j).num;
					tmpMap[i][k++] = list.get(j).cnt;
				}
				if(max < list.size()*2) max = list.size()*2;
			}
			if(max > 100) max = 100;
			map = new int[map.length][max];
			for(int j = 0; j < map.length; j++) {
				for(int l = 0 ; l < map[0].length; l++) {
					map[j][l] = tmpMap[j][l];
				}
			}
		}else { // C 연산
			int max =0;
			for(int i =0 ; i < map[0].length; i++) {
				int[] visited = new int[101];
				for(int j =0; j < map.length; j++) {
					if(map[j][i] == 0) continue;
					visited[map[j][i]] += 1;
				}
				list = new ArrayList<>();
				for(int j = 1; j < 101; j++) {
					if(visited[j] != 0) list.add(new Node(j, visited[j]));
				}
				Collections.sort(list);
				int k = 0;
				for(int j =0; j<list.size(); j++) {
					tmpMap[k++][i] = list.get(j).num;
					tmpMap[k++][i] = list.get(j).cnt;
				}
				if(max <list.size()*2) max = list.size()*2;
			}
			if(max > 100) max = 100;
			map = new int[max][map[0].length];
			for(int j = 0 ; j < map.length; j++) {
				for(int l = 0; l < map[0].length;l++) {
					map[j][l] = tmpMap[j][l];
				}
			}
			
		}
	}

}
