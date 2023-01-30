import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 맥주 마시면서 걸어가기
 * https://www.acmicpc.net/problem/9205
 *
 * 1. 맥주가 어쩌고 박스가 어쩌고 하지만 요약하면 한 번에 1000미터를 갈 수 있다는 얘기이다.
 * 2. 편의점 좌표가 페스티벌 좌표보다 가까이 있다거나 편의점 좌표가 순서대로 주어진다는 보장이 없다.
 * 3. t가 최대 50, n이 최대 100이므로 브루트포스로는 접근할 수 없다. -> 좌표의 최대가 32767 이므로 가능할지도?
 * 4. bfs로 목적지까지 움직이며 도착하기 전에 큐가 빈다면 sad, 도착한다면 happy를 출력한다.
 * 5. 자료는 Point 클래스를 만들어서 1차원 배열로 만드는 게 가장 효율적일 것 같다.
 */
class Point {
	int x;
	int y;
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

public class BJ_9205_맥주_마시면서_걸어가기 {
	static int n;      // 편의점 개수 n
	static Point[] stores;      // 편의점 위치 정보 배열
	static boolean[] visited;       // 편의점 방문 체크 배열
	static Point dest;      // 페스티벌 도착지 좌표

	public static void main(String[] args) throws Exception {
		// 입력 처리
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int t = Integer.parseInt(br.readLine());        // 테스트 케이스 개수 t
		for(int test_case=0; test_case<t; test_case++) {       // 테스트 케이스만큼 반복
			n = Integer.parseInt(br.readLine());        // n 초기화
			int[] input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();        // 시작지점 좌표 입력
			Point src = new Point(input[0], input[1]);      // 시작지점 좌표 초기화
			stores = new Point[n];      // 편의점 위치 정보 배열 초기화
			visited = new boolean[n];       // 편의점 방문 체크 배열 초기화
			for(int i=0; i<n; i++) {
				input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();        // 편의점 좌표 입력
				stores[i] = new Point(input[0], input[1]);      // 편의점 위치 정보 초기화
			}
			input = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();        // 도착지 좌표 입력
			dest = new Point(input[0], input[1]);       // 도착지 초기화

			// 로직 구현 & 출력 처리
			System.out.println(reachable(src) ? "happy" : "sad");
		}
	}

	private static boolean reachable(Point src) {
		Queue<Point> q = new LinkedList<>();        // bfs 대기열 큐
		q.add(src);     // 시작지점 삽입

		while(!q.isEmpty()) {       // 갈 곳이 없을 때까지 반복
			Point p = q.poll();     // 하나 뽑아서 수행
			if((Math.abs(dest.x-p.x) + Math.abs(dest.y-p.y)) <= 1000)       // 도착 지점에 갈 수 있으면 true 리턴
				return true;

			for(int i=0; i<n; i++) {
				if(visited[i])      // 이미 방문 했던 곳은 이후에 가도 같은 동작을 수행하므로 패스
					continue;

				if((Math.abs(stores[i].x-p.x) + Math.abs(stores[i].y-p.y)) <= 1000) {       // 맨해튼 거리가 1000이하이면
					q.add(stores[i]);       // 도달할 수 있는 곳이므로 큐에 삽입
					visited[i] = true;      // 방문체크
				}
			}
		}

		return false;       // 도착 지점에 도달하지 못했으므로 false 리턴
	}
}