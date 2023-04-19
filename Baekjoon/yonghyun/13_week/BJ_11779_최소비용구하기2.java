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
 *  - 누적 비용이 적은 순으로 정렬되게 하면 효율적인 경로부터 처리하기 때문에 도착지를 만났을때 탈출할 수 있다.
 * 3. 비용 출력을 위해 각 노드까지의 최소 비용을 저장하되, 경로를 출력해야 하므로 이전 노드의 번호를 따로 저장해야 한다.
 *  - 누적 비용을 저장할 배열을 dp, 이전 노드의 번호를 저장할 배열을 prev로 생성하면 된다.
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

	private static void input() throws IOException {
		n = Integer.parseInt(br.readLine());
		m = Integer.parseInt(br.readLine());

		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());

			if(!edges.containsKey(start))		// 해시맵으로 인접 리스트를 구현
				edges.put(start, new ArrayList<>());		// 경로가 존재하는 키에 대해서만 저장되게 할 수 있음
			edges.get(start).add(new int[]{end, cost});		// 해시맵의 키가 시작노드이고 값은 끝노드, 비용을 나타내는 배열의 리스트
		}

		st = new StringTokenizer(br.readLine());
		src = Integer.parseInt(st.nextToken());		// 출발지 노드
		dest = Integer.parseInt(st.nextToken());		// 도착지 노드

		prev = new int[n+1];		// 각 노드의 이전 노드
		dp = new int[n+1];		// 출발지 노드부터 각 노드까지의 최소 비용
		Arrays.fill(dp, Integer.MAX_VALUE);		// 최소 비용이므로 최대값으로 초기화
	}

	private static void solution() {
		PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {		// 노드까지의 누적 비용이 낮은 것을 우선으로 선택하도록 정의
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[1] - o2[1];
			}
		});
		pq.add(new int[]{src, 0});		// 시작 노드까지의 비용은 0
		dp[src] = 0;

		while(!pq.isEmpty()) {
			int[] cur = pq.poll();
			int from = cur[0];
			int costAcc = cur[1];

			if (from == dest) {		// 도착했으면 리턴
				return;
			}

			if (!edges.containsKey(from)) {		// 연결된 간선이 존재하지 않으면 리턴
				continue;
			}

			for (int[] edge : edges.get(from)) {		// 연결된 간선으로
				int nextNode = edge[0];
				int nextCostAcc = costAcc + edge[1];

				if (from == dest) {		// 도착했으면 리턴
					return;
				}

				if (nextCostAcc >= dp[nextNode]) {		// 최소 비용이 갱신되는 간선이 존재하면
					continue;
				}

				pq.add(new int[]{nextNode, nextCostAcc});		// 해당 간선을 큐에 추가하고 최소 비용 갱신
				prev[nextNode] = from;
				dp[nextNode] = nextCostAcc;
			}
		}

	}

	private static void print() {
		ArrayDeque<Integer> stack = new ArrayDeque<>();		// ArrayDeque로 스택 구현
		int cur = dest;		// dest에서 시작하여
		while (cur!=src) {		// src까지 트래킹하며 스택에 삽입
			stack.addFirst(cur);
			cur = prev[cur];
		}
		stack.addFirst(src);

		sb.append(dp[dest]).append('\n');		// 도착지까지의 최소 비용
		sb.append(stack.size()).append('\n');		// 경로의 길이
		Iterator<Integer> it = stack.iterator();		// iterator를 이용하여 경로 출력
		while (it.hasNext()) {
			sb.append(it.next()).append(' ');
		}

		System.out.print(sb);
	}

}