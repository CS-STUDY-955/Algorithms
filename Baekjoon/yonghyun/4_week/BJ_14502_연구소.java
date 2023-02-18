import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 연구소
 * https://www.acmicpc.net/problem/14502
 *
 * 1. 맵에서 세 개의 좌표를 선택한다. -> O((N*M)^3)
 * 2. 해당 좌표를 1로 바꾸고 바이러스를 퍼트린다. -> O(NM)
 * 3. 맵에 남은 0의 좌표를 센다. -> O(NM)
 * 4. 가장 0이 많이 남았을 때의 0의 값을 출력한다.
 * 5. 문제의 시간복잡도는 O((N*M)^3)이므로 2^18 = 약 260,000의 연산으로 통과할 수 있다.
 * ----------------------------------------------
 * 1. 분명 중복이 없는 조합인데 start 파라미터를 주면 에러가 발생한다.
 * 2. 처음부터 다시 계산하는건 효율이 안나올것 같아 찾아봤다.
 * 3. modular 연산을 사용하고 인덱스를 하나로 관리하면 구현할 수 있다.
 *
 * @author 배용현
 *
 */
class Main {
	static class Point {
		int x;
		int y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	static int N, M, answer = Integer.MIN_VALUE;		// 안전한 공간의 최댓값 answer
	static Point[] selected;		// 조합으로 뽑은 좌표를 저장할 배열
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, -1, 0, 1};
	static int[][] map;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());
		M = parseInt(st.nextToken());
		selected = new Point[3];		// 벽은 총 3개 세운다.
		map = new int[N][M];
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++)
				map[i][j] = parseInt(st.nextToken());
		}

		combination(0, 0);

		System.out.println(answer);
	}

	private static void combination(int depth, int start) {		// 벽 세울 위치를 뽑는 메서드
		if(depth==3) {		// 좌표 3개 뽑았으면
			for(int i=0; i<3; i++)		// 해당 위치에 벽 세움
				map[selected[i].y][selected[i].x] = 1;

			answer = Math.max(answer, spreadVirus());		// 현재 맵에 바이러스가 퍼질때 정답 계산

			for(int i=0; i<3; i++)		// 맵 복구
				map[selected[i].y][selected[i].x] = 0;

			return;
		}

		for(int i=start; i<N*M; i++) {		// 모든 칸을 한 인덱스로 순회
			int y = i / M;		// 인덱스를 열 수로 나눠주면 행
			int x = i % M;		// 인덱스를 열 수로 나눈 나머지는 열

			if(map[y][x]!=0)		// 벽을 세울 수 없는 곳이면 패스
				continue;

			selected[depth] = new Point(x, y);		// 벽을 세울 좌표 저장
			combination(depth+1, i+1);
		}
	}

	private static int spreadVirus() {		// 바이러스가 퍼진다면 맵에 생존 공간이 얼마나 될지
		boolean[][] visited = new boolean[N][M];		// bfs용 방문 체크 배열
		int sum = 0;		// 바이러스와 벽 개수의 합
		Queue<Point> q = new ArrayDeque<>();
		for(int i=0; i<N; i++) {		// 시작 위치 및 벽 개수 계산
			for(int j=0; j<M; j++) {
				if(map[i][j]==2) {		// 바이러스의 초기위치는 q에 저장
					q.add(new Point(j, i));
					visited[i][j] = true;
				} else if(map[i][j]==1) {		// 벽의 위치는 세어주기만 한다.
					sum++;
				}
			}
		}

		while(!q.isEmpty()) {
			Point p = q.poll();
			sum++;		// 바이러스 위치도 세어준다.

			for(int i=0; i<4; i++) {		// 사방탐색
				int nextX = p.x + dx[i];
				int nextY = p.y + dy[i];

				if(nextX<0 || nextY<0 || nextX>M-1 || nextY>N-1 || visited[nextY][nextX] || map[nextY][nextX]==1)
					continue;

				q.add(new Point(nextX, nextY));
				visited[nextY][nextX] = true;
			}
		}

		return N*M-sum;		// 안전한 공간의 개수 = 전체크기 - 바이러스와 벽의 개수
	}
}