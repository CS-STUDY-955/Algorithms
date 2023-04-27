package platinum5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_10217_KCMTravel {
	
	static class Node implements Comparable<Node> {
		int index, distance, cost;
		
		public Node(int index, int distance, int cost) {
			this.index = index;
			this.distance = distance;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
			return this.distance - o.distance; // 거리 순 오름차순
		}
	}
	
	static int N, M, K;
	static ArrayList<ArrayList<Node>> graph;
	static int[][] distance;
	
	static final int INF = 1000000000;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		for(int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken()); // 공항 수
			M = Integer.parseInt(st.nextToken()); // 지원금
			K = Integer.parseInt(st.nextToken()); // 티켓 수

			graph = new ArrayList<ArrayList<Node>>(); // 인접 리스트
			for(int i = 0; i <= N; i++) {
				graph.add(new ArrayList<Node>()); // 리스트 초기화
			}
			
			for(int i = 0; i < K; i++) {
				st = new StringTokenizer(br.readLine());
				int u = Integer.parseInt(st.nextToken()); // 출발지
				int v = Integer.parseInt(st.nextToken()); // 목적지
				int c = Integer.parseInt(st.nextToken()); // 비용
				int d = Integer.parseInt(st.nextToken()); // 거리(소요시간)
				graph.get(u).add(new Node(v, d, c)); // u번 노드에 v, d, c 노드 추가
			}
			
			distance = new int[N+1][M+1]; // 거리, 비용 테이블
			for(int i = 1; i <= N; i++) {
				for(int j = 0; j <= M; j++) {
					distance[i][j] = INF; // 테이블 초기화
				}
			}
			
			dijkstra(1); // 1번 노드에서 시작하는 다익스트라 수행
			
			int min = INF;
			for(int i = 0; i <= M; i++) {
				// N번 노드로 도착하는 모든 경우의 수에서 가장 적은 비용 계산
				min = min < distance[N][i] ? min : distance[N][i];
			}
			sb.append(min==INF?"Poor KCM":min).append("\n"); // 출력
		}
		System.out.println(sb); // 출력
	}

	private static void dijkstra(int start) {
		PriorityQueue<Node> queue = new PriorityQueue<>();
		
		queue.offer(new Node(start, 0, 0)); // 시작노드, 거리 0, 비용 0
		distance[start][0] = 0; // 시작 노드 초기화
		
		while(!queue.isEmpty()) {
			Node node = queue.poll();
			int now = node.index; // 현재 노드
			int cost = node.cost; // 현재 비용
			int dist = node.distance; // 현재 거리
			
			// 현재 노드로 현재 비용만큼으로 도착한 거리가 현재 거리보다 작으면 건너뜀
			if(distance[now][cost] < dist) continue;
			
			for(int i = 0; i < graph.get(now).size(); i++) {
				// 다음 거리 ndist : 현재노드까지의 거리 + 현재 노드부터 다음 노드까지의 거리
				int ndist = distance[now][cost] + graph.get(now).get(i).distance;
				// 다음 노드 nidx : 현재 노드에서 이동할 다음 노드의 번호
				int nidx = graph.get(now).get(i).index;
				// 다음 비용 ncost : 현재 노드까지의 비용 + 현재 노드부터 다음 노드까지의 비용
				int ncost = cost + graph.get(now).get(i).cost;
				
				if(ncost > M) continue; // 최대 비용을 초과하는 경우 건너뜀
				if(ndist < distance[nidx][ncost]) { // 다음 거리가 다음 노드에 다음 비용으로 도착한 거리보다 작다면
					distance[nidx][ncost] = ndist; // 다음 거리 갱신
					queue.offer(new Node(nidx, ndist, ncost)); // 큐에 넣기
					while(++ncost <= M) { // 다음 비용 이상, 전체 비용 이하인 테이블에서
						if(distance[nidx][ncost] <= ndist) break; // 그 값이 다음 거리 이하이면 탈출
						distance[nidx][ncost] = ndist; // 그 값이 다음 거리 이상이면 갱신
					}
				}
			}
		}
	}
}
