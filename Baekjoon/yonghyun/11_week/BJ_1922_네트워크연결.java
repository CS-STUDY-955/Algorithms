import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.decode;
import static java.lang.Integer.parseInt;

/**
 * 네트워크 연결
 * https://www.acmicpc.net/problem/1922
 *
 * 1. 연결선의 수가 최대 100,000이므로 E^2의 시간복잡도를 가져서는 안된다.
 * 2. 간선을 가중치가 낮은 순으로 정렬하고, 양 옆을 방문하지 않았으면 연결한다.
 *
 * -------------------------------
 * 1. 단순 방문 배열을 이용하면 두 그룹이 생겨 서로 연결되지 않을 수 있다.
 * 2. 따라서 유니온 파인드 알고리즘을 사용해야 한다.
 * 3.
 *
 * @author 배용현
 */
class BJ_1922_네트워크연결 {

	static int N, M, answer = 0;
	static int[][] edges;
	static int[] parent;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		input();
		solution();
		System.out.println(answer);
	}

	private static void solution() {
		for (int i = 0; i < M; i++) {
			if (find(edges[i][0]) != find(edges[i][1])) {
				answer += edges[i][2];
				union(edges[i][0], edges[i][1]);
			}
		}
	}

	private static void union(int node1, int node2) {
		node1 = parent[node1];
		node2 = parent[node2];

		if (node1 != node2) {
			parent[node1] = node2;
		}
	}

	private static int find(int idx) {
		if (idx == parent[idx]) {
			return idx;
		}

		return parent[idx] = find(parent[idx]);
	}

	private static void input() throws IOException {
		N = parseInt(br.readLine());
		M = parseInt(br.readLine());
		edges = new int[M][3];

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			edges[i][0] = parseInt(st.nextToken());
			edges[i][1] = parseInt(st.nextToken());
			edges[i][2] = parseInt(st.nextToken());
		}

		Arrays.sort(edges, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[2] - o2[2];
			}
		});

		parent = new int[N+1];
		for (int i = 1; i <= N; i++) {
			parent[i] = i;
		}
	}
}