import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 도로포장
 * https://www.acmicpc.net/problem/1162
 *
 * 1.
 *
 * @author 배용현
 *
 */
class BJ_1162_도로포장 {
	static class Edge {
		int end;
		long cost;
		int paveCount;

		public Edge(int end, long cost, int paveCount) {
			this.end = end;
			this.cost = cost;
			this.paveCount = paveCount;
		}
	}

	static int N, M, K;
	static HashMap<Integer, ArrayList<Edge>> edges = new HashMap<>();
	static long[][] dp;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		input();
		solution();
		print();
	}

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			long cost = Long.parseLong(st.nextToken());

			if(!edges.containsKey(start))        // 해시맵으로 인접 리스트를 구현
				edges.put(start, new ArrayList<>());        // 경로가 존재하는 키에 대해서만 저장되게 할 수 있음
			edges.get(start).add(new Edge(end, cost, 1));        // 해시맵의 키가 시작노드이고 값은 끝노드, 비용을 나타내는 배열의 리스트
		}

		dp = new long[N+1][K+1];        // 포장 기회 k개를 사용했을때 출발지 노드부터 각 노드까지의 최소 비용
		for (int i = 0; i <= N; i++) {
			Arrays.fill(dp[i], Long.MAX_VALUE);
		}
	}

	private static void solution() {
		PriorityQueue<Edge> pq = new PriorityQueue<>(new Comparator<Edge>() {        // 노드까지의 누적 비용이 낮은 것을 우선으로 선택하도록 정의
			@Override
			public int compare(Edge o1, Edge o2) {
				return (int) (o1.cost - o2.cost);
			}
		});
		pq.add(new Edge(1, 0, 0));        // end, cost, paveCount
		dp[1][0] = 0;

		while(!pq.isEmpty()) {
			Edge cur = pq.poll();
			int from = cur.end;
			long costAcc = cur.cost;
			int paveCount = cur.paveCount;

			if (!edges.containsKey(from)) {        // 연결된 간선이 존재하지 않으면 리턴
				continue;
			}

			for (Edge edge : edges.get(from)) {        // 연결된 간선으로
				int nextNode = edge.end;
				long nextCostAcc = costAcc + edge.cost;

				if (from == N) {        // 도착했으면 리턴
					return;
				}

				if (paveCount < K && dp[nextNode][paveCount+1] > dp[from][paveCount]) {        // 이 도로 포장
					dp[nextNode][paveCount+1] = costAcc;
					pq.add(new Edge(nextNode, costAcc, paveCount+1));        // 해당 간선을 큐에 추가하고 최소 비용 갱신
				}

				if (dp[nextNode][paveCount] > nextCostAcc) {        // 이 도로 포장 X
					dp[nextNode][paveCount] = nextCostAcc;
					pq.add(new Edge(nextNode, nextCostAcc, paveCount));        // 해당 간선을 큐에 추가하고 최소 비용 갱신
				}

			}
		}

	}

	private static void print() {
		long min = Long.MAX_VALUE;
		for (int i = 0; i <= K; i++) {
			min = Math.min(min, dp[N][i]);
		}
		System.out.println(min);
	}

}