import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 최소비용 구하기 2
 * https://www.acmicpc.net/problem/17837
 *
 * 1. 시작점과 도착점이 정해져있고, 간선의 비용이 양수이므로 다익스트라를 사용하면 된다.
 * 2. bfs 방식에 큐 대신 우선순위큐를 사용하여 다익스트라를 구현할 수 있다.
 * 3. 경로를 출력해야 하므로 각 노드까지의 최소 비용을 저장하되, 이전 노드의 번호를 따로 저장해야 한다.
 *
 * @author 배용현
 *
 */
class BJ_11779_최소비용구하기2 {

	static int n, m, src, dest;
	static HashMap<Integer, ArrayList<int[]>> edges = new HashMap<>();
	static int[] prev, dp;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		input();
		solution();
		print();
	}

	private static void print() {
		ArrayDeque<Integer> stack = new ArrayDeque<>();
		int cur = n;
		while (prev[cur]!=src) {
			stack.addFirst(cur);
			cur = prev[cur];
		}

		sb.append(dp[dest]).append('\n');
		sb.append(stack.size()).append('\n');
		for (int i = 0; i < stack.size(); i++) {
			sb.append(stack.pollLast()).append(' ');
		}

		System.out.print(sb);
	}

	private static void solution() {
		PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[1] - o2[1];
			}
		});
		pq.add(new int[]{src, 0});

		while(!pq.isEmpty()) {
			int[] cur = pq.poll();
			int to = cur[0];
			int cost = cur[1];

			if (to == dest) {
				return;
			}

			if (!edges.containsKey(to)) {
				continue;
			}

			for (int[] edge : edges.get(to)) {
				int nextNode = edge[0];
				int nextCost = cost + edge[1];
				if (nextCost >= dp[to]) {
					continue;
				}

				pq.add(new int[]{nextNode, nextCost});
				prev[nextNode] = to;
				dp[nextNode] = nextCost;
			}
		}

	}

    private static void input() throws IOException {
		n = Integer.parseInt(br.readLine());
		m = Integer.parseInt(br.readLine());

		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());

			if(!edges.containsKey(start))
				edges.put(start, new ArrayList<>());
			edges.get(start).add(new int[]{end, cost});
		}

		st = new StringTokenizer(br.readLine());
		src = Integer.parseInt(st.nextToken());
		dest = Integer.parseInt(st.nextToken());

		prev = new int[n+1];
		dp = new int[n+1];
		Arrays.fill(dp, Integer.MAX_VALUE);
	}
}