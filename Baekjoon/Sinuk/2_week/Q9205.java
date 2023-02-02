import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

//9205번: 맥주 마시면서 걸어가기
class Node {
	int x;
	int y;
	int[] dists;

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// 노드와 연결된 다른 노드까지의 거리를 계산하여 dist에 기록
	// 맥주마시면서 갈 수 있으면 그 거리를, 못가면 int 최댓값으로 설정
	public void setReachable(Node[] nodes, int currentIdx, int n) {
		dists = new int[n + 2];
		for (int i = 0; i < n + 2; i++) {
			if (i == currentIdx) {
				dists[i] = 0;
			} else {
				dists[i] = clacDistance(nodes[i]);
				if (dists[i] > 1000) {
					dists[i] = Integer.MAX_VALUE;
				}
			}
		}
	}

	// 자신과 주어진 node 간의 거리를 계산하는 메서드
	public int clacDistance(Node node) {
		return Math.abs(x - node.x) + Math.abs(y - node.y);
	}
}

public class Q9205 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int t = Integer.parseInt(br.readLine());
		for (int tc = 0; tc < t; tc++) {
			int n = Integer.parseInt(br.readLine());
			// node 정보들을 받아두는 배열
			Node[] nodes = new Node[n + 2];
			// 해당 번째 node를 방문 했는지 기록하는 배열
			boolean[] visited = new boolean[n + 2];
			// root에서 해당번째 node까지의 최단 거리를 기록하는 배열
			int[] dist = new int[n + 2];

			// nodes에 각각의 정보를 담고, root인 0번까지의 거리를 0으로 설정
			for (int i = 0; i < n + 2; i++) {
				st = new StringTokenizer(br.readLine());
				nodes[i] = new Node(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
				dist[i] = Integer.MAX_VALUE;
			}
			dist[0] = 0;

			// 각각의 노드에 대해 다른 노드들까지의 거리를 계산
			for (int i = 0; i < n + 2; i++) {
				nodes[i].setReachable(nodes, i, n);
			}

			// 다익스트라 시작
			for (int i = 0; i < n + 2; i++) {
				// 1. 최소거리인 노드 찾기
				int currentDist = Integer.MAX_VALUE;
				int currentIdx = 0;
				for (int j = 1; j < n + 2; j++) {
					// 해당번째 노드가 아직 방문하지 않았고, 지금까지 조사한것중 최소거리라면
					if (!visited[j] && dist[j] < currentDist) {
						// 최소거리를 업데이트 하고, 그 인덱스를 기록
						currentDist = dist[j];
						currentIdx = j;
					}
				}
				
				// 2. 찾은 노드를 방문처리
				visited[currentIdx] = true;

				// 3. 찾은 노드와 연결된 내용대로 dist를 업데이트
				for (int j = 0; j < n + 2; j++) {
					// 아직 방문하지 않았고, 현재 선택된 최소거리인 노드로부터 j번째 노드까지의 거리가 도달 가능이며
					// 그 거리와 루트에서 현재 선택된 최소거리인 노드까지의 거리의 합이 현재의 최솟값보다 낮으면
					if (!visited[j] && nodes[currentIdx].dists[j] != Integer.MAX_VALUE
							&& dist[j] > nodes[currentIdx].dists[j] + dist[currentIdx])
						// 그 거리로 최솟값을 업데이트
						dist[j] = nodes[currentIdx].dists[j] + dist[currentIdx];
				}

				// 4. 만약 페스티벌에 도착하면 종료
				if (dist[n + 1] != Integer.MAX_VALUE) {
					break;
				}
			}
			// 페스티벌까지의 거리가 도달 불가능이면 sad, 아니라면 happy
			if (dist[n + 1] == Integer.MAX_VALUE)
				System.out.println("sad");
			else
				System.out.println("happy");
		}
	}
}