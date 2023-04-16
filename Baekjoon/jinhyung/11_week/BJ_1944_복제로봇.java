package com.baekjoon.p5g12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_1944_복제로봇 {
	
	static final int INF = Integer.MAX_VALUE;

	static int[][] map; // 맵
	static int N, M, linked; // 맵 길이, 열쇠 수, 가져온 열쇠 수
	static int[] parent; // 서로소 집합
	static PriorityQueue<int[]> adjlist; // 열쇠위치, 시작점을 노드로 하고 가중치를 거리로 한 간선 리스트(가중치순 정렬)
	
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, -1, 0, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		int count = 1; // count : 전체 노드 수
		parent = new int[M+2]; // 서로소 집합(0번은 안쓰는 인덱스)
		Queue<int[]> queue = new ArrayDeque<>();
		for(int i = 0; i < N; i++) {
			String str = br.readLine();
			for(int j = 0; j < N; j++) {
				// 시작점이거나 열쇠인 경우 서로소 집합에 넣어주고 해당 위치 아이디(count)로 바꿔주기
				if(str.charAt(j) == 'S' || str.charAt(j) == 'K') {
					parent[count] = count;
					map[i][j] = count;
					queue.offer(new int[] {i, j, count++, 0}); // 큐에 넣기
				}
				else map[i][j] = str.charAt(j) == '1' ? -1 : 0; // 벽은 -1로 표기
			}
		}
		
		// 간선 리스트 초기화(가중치 순 오름차순)
		adjlist = new PriorityQueue<int[]>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[2] - o2[2];
			}
		});
		
		// 모든 시작 위치에 대해 BFS로 각 노드마다 최소 거리 구하기
		while(!queue.isEmpty()) {
			bfs(queue.poll());
		}
		
		int distance = kruskal(); // 크루스칼로 최소 거리 구하기
		
		for(int i = 1; i <= M+1; i++) { // 모든 노드의 부모가 1이 아니면 연결되지 않음
			if(find(i) == 1) continue;
			distance = -1;
			break;
		}
		
		System.out.println(distance); // 출력
	}
	
	private static int kruskal() {
		linked = 0; // 현재 연결된 간선 수
		int sum = 0; // 길이 총 합
		while(!adjlist.isEmpty() && linked != M+1) {
			int[] edge = adjlist.poll(); // 가중치 순으로 간선 뽑아서
			
			int pfrom = find(edge[0]);
			int pto = find(edge[1]);
			if(pfrom == pto) continue; // 이미 연결되어 있으면 패스
			
			union(pfrom, pto); // 연결
			linked++; // 연결 수 + 1
			sum += edge[2]; // 거리+
		}
		return sum;
	}

	private static void bfs(int[] start) { // 각 노드들까지의 거리 구하기
		Queue<int[]> queue = new ArrayDeque<>();
		queue.offer(start);
		boolean[][] visited = new boolean[N][N]; // 방문 배열
		visited[start[0]][start[1]] = true;
		
		while(!queue.isEmpty()) {
			int[] robot = queue.poll();
			int x = robot[0];
			int y = robot[1];
			
			for(int i = 0; i < 4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				
				if(map[nx][ny] == -1 || visited[nx][ny]) continue; // 벽이거나 방문했다면 패스
				if(map[nx][ny] != 0) { // 빈공간이 아니라면(열쇠거나 시작점)
					adjlist.add(new int[] {robot[2], map[nx][ny], robot[3] + 1}); // 간선 리스트에 넣기
				}
				queue.offer(new int[] {nx, ny, robot[2], robot[3]+1}); // 큐에 넣고
				visited[nx][ny] = true; // 방문처리
			}
		}			
	}
	
	private static int find(int x) { // 서로소 집합 find
		if(parent[x] == x) return x;
		else return parent[x] = find(parent[x]);
	}
	
	private static void union(int x, int y) { // 서로소 집합 union
		int px = find(x);
		int py = find(y);
		if(px < py) parent[py] = px;
		else parent[px] = py;
	}
}
