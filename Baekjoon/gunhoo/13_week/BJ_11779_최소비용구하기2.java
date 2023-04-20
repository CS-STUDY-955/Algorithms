package Gold.Gold3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_11779_최소비용구하기2 {
	private static int n, m, start, end, dist[];
	private static final int INF = Integer.MAX_VALUE;
	private static ArrayList<Integer> path = new ArrayList<>(); // 방문경로를 담기 위한 변수
	
	private static ArrayList<Node> graph[]; // dijkstra 위한 graph
	private static class Node implements Comparable<Node>{ // PQ 사용을 위한 Node class 선언
		int node, weight;
		
		public Node(int n, int w) {
			this.node = n;
			this.weight = w;
		}
		@Override
		public int compareTo(Node o) {
			return this.weight-o.weight;
		}
	}
	
	public static void main(String[] args) throws Exception{
		init(); // 입력 메서드
		dijkstra(); // 다익스트라 알고리즘
		System.out.println(dist[end]); // 종료지점까지 거리 출력
		System.out.println(path.size()); // 방문경로의 사이즈 출력
		for(int i = path.size()-1 ;i >= 0 ; i--) { // 방문경로 출력
			System.out.print(path.get(i)+" ");
		}
	}
	
	private static void init() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		graph = new ArrayList[n+1];
		for (int i = 0; i <= n; i++)  graph[i] = new ArrayList<>();
		
		st= new StringTokenizer(br.readLine());
		m = Integer.parseInt(st.nextToken());
		
		for(int i =0 ;i < m ; i++) {
			st= new StringTokenizer(br.readLine());
			graph[Integer.parseInt(st.nextToken())].add(new Node(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
		}
		
		st= new StringTokenizer(br.readLine());
		start = Integer.parseInt(st.nextToken());
		end = Integer.parseInt(st.nextToken());
	}
	
	private static void dijkstra() {
		boolean[] check = new boolean[n+1]; // 방문여부 체크 
		int[] parents = new int[n+1]; // 방문 경로 설정
		dist = new int[n+1]; // 출발지에서 각 노드까지의 거리 값 담고있는 변수
		for(int i = 0; i < n+1; i++) dist[i] = INF; // 최대값으로 초기화
		dist[start] = 0; // 본인은 0 이다
		
		PriorityQueue<Node> pq = new PriorityQueue<>(); // PQ선언
		pq.offer(new Node(start, 0)); 

		while(!pq.isEmpty()) {
			int curNode = pq.poll().node; // 현재노드,
			
			if(check[curNode]) continue; // 반약 방문처리된 곳이면 pass
			check[curNode] = true; // 방문처리
			
			for(Node next : graph[curNode]) { // 연결되어있는 모든 Node들에 대해
				if(dist[next.node] > dist[curNode] + next.weight) { // 만약 거리 갱신이 가능하다면
					dist[next.node] = dist[curNode] + next.weight;  // 거리 갱신
					pq.offer(new Node(next.node, dist[next.node])); // pq삽입
					parents[next.node] = curNode; // 경로추적을 위한 부모배열에 추가
				}
			}
		}
		for (int i = end; i != 0; i = parents[i]) {
            path.add(i); // 경로추가
        }
	}

}
