package gold2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_19238_스타트택시 {
	
	static int N, M, K, tx, ty;
	static int[][] map, dest;
	
	static int[] dx = {-1, 0, 0, 1};
	static int[] dy = {0, -1, 1, 0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 격자 크기
		M = Integer.parseInt(st.nextToken()); // 태울 승객 수
		K = Integer.parseInt(st.nextToken()); // 초기 연료
		
		map = new int[N][N]; // 맵
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				int n = Integer.parseInt(st.nextToken());
				if(n == 0) map[i][j] = 0; // 0은 0
				else map[i][j] = -1; // 벽은 -1로 저장
			}
		}
		st = new StringTokenizer(br.readLine()); // 택시 초기 위치 입력받기
		tx = Integer.parseInt(st.nextToken()) - 1;
		ty = Integer.parseInt(st.nextToken()) - 1;

		dest = new int[M+1][2]; // 각 승객의 목적지
		for(int i = 1; i <= M; i++) { // i : 승객 번호
			st = new StringTokenizer(br.readLine());
			int cx = Integer.parseInt(st.nextToken()) - 1; // 승객 행위치
			int cy = Integer.parseInt(st.nextToken()) - 1; // 승객 열위치
			int cdx = Integer.parseInt(st.nextToken()) - 1; // 도착지 행위치
			int cdy = Integer.parseInt(st.nextToken()) - 1; // 도착지 열위치
		
			map[cx][cy] = i; // 맵에 번호로 승객 표시
			dest[i][0] = cdx; // 목적지 행 저장
			dest[i][1] = cdy; // 목적지 열 저장
		}
		
		// 모든 손님을 태우기 위해 M번 반복
		for(int i = 0; i < M; i++) {
			if(K < 0) break; // 연료가 다 떨어지면 종료
			drive(find()); // find() -> 승객 찾기, drive() -> 승객 태우고 목적지 가기
		}
		
		System.out.println(K); // 연료 출력
	}

	private static void drive(int num) {
		// num : 승객 번호
		if(num == -1 || K < 0) { // 승객을 찾을 수 없거나 연료가 없다면
			K = -1; // 운행 종료
			return;
		}
		map[tx][ty] = 0; // 승객 태우기
		if(tx == dest[num][0] && ty == dest[num][1]) {
			return; // 승객 위치가 도착지와 같다면 종료
		}
		boolean[][] visited = new boolean[N][N];
		Queue<int[]> taxi = new ArrayDeque<>();
		taxi.offer(new int[] {tx, ty, 0}); // 택시위치, 이동거리 저장
		while(!taxi.isEmpty()) {
			int[] t = taxi.poll();
			int x = t[0];
			int y = t[1];
			int d = t[2];
			if(K < d) { // 연료를 다 썼다면 종료
				K = -1;
				return;
			}
			
			for(int i = 0; i < 4; i++) { // 4방 탐색
				int nx = x + dx[i];
				int ny = y + dy[i];

				// 맵밖이거나 벽이거나 방문한 경우 건너뛰기
				if(nx < 0 || nx >= N || ny < 0 || ny >= N || map[nx][ny] == -1 || visited[nx][ny]) continue;
				visited[nx][ny] = true; // 방문처리
				if(nx == dest[num][0] && ny == dest[num][1]) {
					// 도착지라면
					tx = nx; // 택시 위치 변경
					ty = ny;
					K -= d+1; // 이동거리만큼 연료 빼주기
					if(K < 0) return; // 다썼다면 종료
					K += (d+1)*2; // 아니면 연료 채우기
					return;
				}
				taxi.offer(new int[] {nx, ny, d+1});
			}
		}
		
		K = -1; // 모든 칸을 탐색했는데 승객을 못찾은 경우
	}

	private static int find() {
		boolean[][] visited = new boolean[N][N];
		// 승객은 거리가까운순, 행번호 작은순, 열번호 작은순으로 태워야함
		PriorityQueue<int[]> taxi = new PriorityQueue<int[]>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				if(o1[2] == o2[2]) {
					if(o1[0] == o2[0]) return o1[1] - o2[1];
					return o1[0] - o2[0];
				}
				return o1[2] - o2[2];
			}
		});
		taxi.offer(new int[] {tx, ty, 0});
		while(!taxi.isEmpty()) {
			int[] t = taxi.poll();
			int x = t[0];
			int y = t[1];
			int d = t[2];
			
			// 승객을 찾은 경우 택시위치 바꿔주고 연료 빼주기
			if(map[x][y] != 0 || K < d) {
				tx = x;
				ty = y;
				K -= d;
				return map[x][y];
			}
			for(int i = 0; i < 4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];

				// 맵밖이거나 벽이거나 방문한 경우 건너뛰기
				if(nx < 0 || nx >= N || ny < 0 || ny >= N || map[nx][ny] == -1 || visited[nx][ny]) continue;
				visited[nx][ny] = true;
				taxi.offer(new int[] {nx, ny, d+1});
			}
		}
		
		return -1;
	}
}
