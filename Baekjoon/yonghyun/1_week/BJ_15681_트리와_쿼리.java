import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * 1. N이 최대 10,000이므로 O(N)의 시간복잡도를 가지는 dfs를 실행할 수 있다.
 * 2. N이 해시충돌의 위험이 있는 10,000,000에 한참 못미치므로 해시맵을 사용할 수 있다.
 * 3. 각 쿼리마다 dfs를 실행하면 O(N*Q)로 시간 초과가 발생할 수 있으므로 dfs로 트리의 구조를 확인하면서 서브트리의 크기를 기록한다.
 * 4. 기록한 것을 바탕으로 쿼리로 주어지는 루트 노드의 서브트리의 크기를 리턴한다.
 * */
public class BJ_15681_트리와_쿼리 {
	static HashMap<Integer, ArrayList<Integer>> map;
	static boolean[] visited;
	static int[] subTreeSize;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] input = br.readLine().split(" ");
		int N = Integer.parseInt(input[0]);
		int R = Integer.parseInt(input[1]);
		int Q = Integer.parseInt(input[2]);
		map = new HashMap<>();
		visited = new boolean[N+1];
		subTreeSize = new int[N+1];
		for(int i=0; i<N-1; i++) {
			input = br.readLine().split(" ");
			int U = Integer.parseInt(input[0]);
			int V = Integer.parseInt(input[1]);
			if(!map.containsKey(U))
				map.put(U, new ArrayList<>());
			if(!map.containsKey(V))
				map.put(V, new ArrayList<>());
			map.get(U).add(V);
			map.get(V).add(U);
		}

		subTreeSize[R] = dfs(R);
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<Q; i++) {
			sb.append(subTreeSize[Integer.parseInt(br.readLine())]).append('\n');
		}
		System.out.print(sb);
	}

	private static int dfs(int p) {
		subTreeSize[p] = 1;
		visited[p] = true;

		for(int c: map.get(p)) {
			if(!visited[c])
				subTreeSize[p] += dfs(c);
		}

		return subTreeSize[p];
	}
}