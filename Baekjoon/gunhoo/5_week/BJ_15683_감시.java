package Gold;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * BJ 15683 감시(G4)
 * 1. 풀이방법
 * 		- 완탐
 *  	- switch문작성
 * 바로 바꾸면 안되고 모든 경우의 수 따져봐야해
 * 5번같은 경우는 바로 바꿀 수 있어.
 *  1은 3방향이상 막혀있으면 바로 끝낼 수 있어
 *  2는 2방향이상 막혀있으면 바로 끝낼 수 있어
 *  3은 2방향이상 막혀있으면 바로 끝낼 수 있어
 *  4는 1방향이상 막혀있으면 바로 끝낼 수 있어
 *  
 * @author gunhoo
 *
 */
public class BJ_15683_감시 {
	static int n, m;
//	static int map[][];
	static ArrayList<Node> no5, cctv;
	static int answer = Integer.MAX_VALUE;
	static class Node{
		int x, y, num;
		public Node(int x, int y, int num) {
			this.x = x;
			this.y = y;
			this.num = num;
		}
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		int[][] map = new int[n][m];
		no5 = new ArrayList<>();
		cctv = new ArrayList<>();
		for(int i = 0 ; i < n ; i++) {
			st =  new StringTokenizer(br.readLine());
			for(int j = 0 ; j < m ; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 5) no5.add(new Node(i, j, 5)); // 5번 CCTV는 따로 빼줌
				else if(map[i][j] != 0 && map[i][j] != 6) cctv.add(new Node(i, j, map[i][j])); // 
			}
		}
		map = cctvNo5(map); // 5번은 무조건 4방향이므로 먼저 처리해준다.
		dfs(0, map); // 모든 경우의 수에 대해 탐색
		System.out.println(answer);
	}
	
	private static void dfs(int cnt, int[][] map) {
		if( cnt == cctv.size()){ // cctv개수만큼 다 골랐으면
			answer = Math.min(answer, sum(map)); // 현재 map에서 사각지대와 가지고있는 answer값과 비교
			return; // 종료조건
		}
		Node node = cctv.get(cnt);
		switch(node.num) {
		case 1: // 한방향으로만 탐색
			// 위
			int[][] tmp = new int[map.length][map[0].length];
			tmp = up(node.x, node.y, map); // 위쪽을 모두 봤다고 만들고
			dfs(cnt+1, tmp); // 한 방향 골랐으니 그 map을 가지고 다시 dfs
//			upMinus(node.x, node.y);
			
			// 오른쪽
			tmp = right(node.x, node.y, map);
			dfs(cnt+1, tmp);
//			rightMinus(node.x, node.y);
			
			// 아래
			tmp  = bottom(node.x, node.y, map);
			dfs(cnt+1, tmp);
//			bottomMinus(node.x, node.y);
			
			// 왼쪽
			tmp = left(node.x, node.y, map);
			dfs(cnt+1, tmp);
//			leftMinus(node.x, node.y);
			break;
			
		case 2:
			// 위아래
			tmp = up(node.x, node.y, map);
			tmp = bottom(node.x, node.y, tmp);
			dfs(cnt+1, tmp);
//			upMinus(node.x, node.y);
//			bottomMinus(node.x, node.y);
			
			// 좌우
			tmp = left(node.x, node.y, map);
			tmp = right(node.x, node.y, tmp);
			dfs(cnt+1, tmp);
//			leftMinus(node.x, node.y);
//			rightMinus(node.x, node.y);
			break;
			
		case 3:
			// 상우
			tmp = up(node.x, node.y, map);
			tmp = right(node.x, node.y,tmp);
			dfs(cnt+1, tmp);
//			upMinus(node.x, node.y);
//			rightMinus(node.x, node.y);
			
			// 우하
			tmp = right(node.x, node.y, map);
			tmp = bottom(node.x, node.y, tmp);
			dfs(cnt+1, tmp);
//			rightMinus(node.x, node.y);
//			bottomMinus(node.x, node.y);
			
			// 하좌
			tmp = bottom(node.x, node.y, map);
			tmp = left(node.x, node.y, tmp);
			dfs(cnt+1, tmp);
//			bottomMinus(node.x, node.y);
//			leftMinus(node.x, node.y);
			
			// 좌상
			tmp = left(node.x, node.y, map);
			tmp = up(node.x, node.y, tmp);
			dfs(cnt+1, tmp);
//			leftMinus(node.x, node.y);
//			upMinus(node.x, node.y);
			break;
			
		case 4:
			// 상좌우
			tmp = up(node.x, node.y, map);
			tmp = left(node.x, node.y, tmp);
			tmp = right(node.x, node.y, tmp);
			dfs(cnt+1, tmp);
//			upMinus(node.x, node.y);
//			leftMinus(node.x, node.y);
//			rightMinus(node.x, node.y);
			
			// 상우하
			tmp = up(node.x, node.y, map);
			tmp = right(node.x, node.y, tmp);
			tmp = bottom(node.x, node.y, tmp);
			dfs(cnt+1, tmp);
//			upMinus(node.x, node.y);
//			rightMinus(node.x, node.y);
//			bottomMinus(node.x, node.y);
			
			// 상좌하
			tmp = up(node.x, node.y, map);
			tmp = left(node.x, node.y, tmp);
			tmp = bottom(node.x, node.y, tmp);
			dfs(cnt+1, tmp);
//			upMinus(node.x, node.y);
//			leftMinus(node.x, node.y);
//			bottomMinus(node.x, node.y);
			
			// 좌우하
			tmp = left(node.x, node.y, map);
			tmp = right(node.x, node.y, tmp);
			tmp = bottom(node.x, node.y, tmp);
			dfs(cnt+1, tmp);
//			leftMinus(node.x, node.y);
//			rightMinus(node.x, node.y);
//			bottomMinus(node.x, node.y);
			break;
		}
	}
	
	private static int[][] up(int x, int y, int[][] map) { // 자신의 좌표로부터 윗부분으로 가면서 벽을만날때까지 모두 -1로 만드는 메서드
		int[][] tmp = new int[map.length][map[0].length];
		for(int i = 0; i < map.length; i++) {
			tmp[i] = Arrays.copyOf(map[i], map[0].length);
		}
		for(int i = x; i>=0; i--) {
			if(tmp[i][y] == 6 ) break;  
			if(tmp[i][y]==0) tmp[i][y] = -1;
		}
		return tmp;
	}
	/*
	private static void upMinus(int x, int y) { // 자신의 좌표로부터 윗부분으로 가면서 벽을만날때까지 모두 -1로 만드는 메서드
		for(int i = x; i>=0; i--) {
			if(map[i][y] == 6 ) break;  
			if(map[i][y]==-1) map[i][y] = 0;
		}
	}*/
	private static int[][] right(int x, int y, int[][] map) {
		int[][] tmp = new int[map.length][map[0].length];
		for(int i = 0; i < map.length; i++) {
			tmp[i] = Arrays.copyOf(map[i], map[0].length);
		}
		for(int i = y; i<m ; i++) {
			if(tmp[x][i] == 6 ) break;  
			if(tmp[x][i]==0) tmp[x][i] = -1;
		}
		return tmp;
	}/*
	private static void rightMinus(int x, int y) {
		for(int i = y; i<m ; i++) {
			if(map[x][i] == 6 ) break;  
			if(map[x][i]==-1) map[x][i] = 0;
		}
	}*/
	private static int[][] left(int x, int y, int[][] map) {
		int[][] tmp = new int[map.length][map[0].length];
		for(int i = 0; i < map.length; i++) {
			tmp[i] = Arrays.copyOf(map[i], map[0].length);
		}
		for(int i = y; i>=0 ; i--) {
			if(tmp[x][i] == 6 ) break;  
			if(tmp[x][i]==0) tmp[x][i] = -1;
		}
		return tmp;
	}/*
	private static void leftMinus(int x, int y) {
		for(int i = y; i>=0 ; i--) {
			if(map[x][i] == 6 ) break;  
			if(map[x][i]==-1) map[x][i] = 0;
		}
	}*/
	private static int[][] bottom(int x, int y, int[][] map) {
		int[][] tmp = new int[map.length][map[0].length];
		for(int i = 0; i < map.length; i++) {
			tmp[i] = Arrays.copyOf(map[i], map[0].length);
		}
		for(int i = x; i<n; i++) {
			if(tmp[i][y] == 6 ) break;  
			if(tmp[i][y]==0) tmp[i][y] = -1;
		}
		return tmp;
	}/*
	private static void bottomMinus(int x, int y) {
		for(int i = x; i<n; i++) {
			if(map[i][y] == 6 ) break;  
			if(map[i][y]==-1) map[i][y] = 0;
		}
	}*/
	private static int[][] cctvNo5(int[][] map) {// cctv 5번 녀석들 처리 로직
		while(no5.size() > 0 ) {
			Node five = no5.remove(0);
			for(int i = five.x ; i>= 0 ; i--) { // 위쪽탐색
				if( map[i][five.y] == 6 ) break;
				else if(map[i][five.y] == 0) map[i][five.y] = -1;
			}
			for(int i = five.x+1 ; i< n ; i++) { // 아래쪽탐색
				if( map[i][five.y] == 6 ) break;
				else if(map[i][five.y] == 0) map[i][five.y] = -1;
			}
			for(int i = five.y ; i>= 0 ; i--) { // 왼쪽탐색
				if( map[five.x][i] == 6 ) break;
				else if(map[five.x][i] == 0) map[five.x][i] = -1;
			}
			for(int i = five.y+1 ; i<m ; i++) { // 오른쪽탐색
				if( map[five.x][i] == 6 ) break;
				else if(map[five.x][i] == 0) map[five.x][i] = -1;
			}
		}
		return map;
	}
	 /** 0인구역 모두 더해서 return해주는 메서드  @return sum*/
	private static int sum(int[][] map) { // 
		int sum = 0;
		for(int i = 0 ; i< n ; i++) {
			for(int j = 0 ; j< m ; j++) {
				if(map[i][j] == 0 ) sum++;
			}
		}
		return sum;
	}
	private static void printMap(int[][] map) { // for debugging
		for(int i = 0 ; i<n;i++) {
			for(int j = 0 ; j<m ; j++) {
				System.out.print(map[i][j]+"\t");
			}
			System.out.println();
		}
	}

}
