import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 아기 상어
 * https://www.acmicpc.net/problem/16236
 * 
 * 1. 아기 상어가 있는 위치에서 bfs를 수행하여 가장 가까운 물고기를 찾는다.
 * 2. bfs 진행할 때 위쪽, 왼쪽 순서로 탐색한다.
 * 3. 물고기를 먹는데 성공하면 경험치를 올리고 크기를 계산한 뒤 위치를 수정하고 시간을 1올린다.
 * 4. 위치를 수정한 뒤엔 다시 1번부터 반복한다.
 * 5. 더이상 먹을수 있는 물고기가 없으면, 시간을 출력한다.
 * 
 * @author 배용현
 *
 */
public class BJ_16236_아기상어 {
	static class Point {		// bfs 탐색을 위해 좌표와 거리를 저장하는 클래스
		int x;		// 열 좌표
		int y;		// 행 좌표
		int d;		// 거리
		
		public Point(int x, int y, int d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}
	
	static int x, y;		// 아기 상어의 위치
	static int level, exp;		// 아기 상어의 크기, 다음 크기로 성장하는 경험치
	static int second, N;		// 아기 상어가 버틴 시간, 맵의 크기 N
	static int[][] map;		// 맵 정보
	static boolean[][] visited;		// bfs용 방문 배열
	static final int[] dx = {0, -1, 1, 0};		// x좌표 이동 방향
	static final int[] dy = {-1, 0, 0, 1};		// y좌표 이동 방향
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N+2][N+2];		// 벽을 표현하기 위해 N+2로 생성
		
		for(int i=1; i<N+1; i++) {		// 데이터는 1~N까지 입력
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=1; j<N+1; j++) {		// 데이터는 1~N까지 입력
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j]==9) {		// 아기 상어가 존재하면
					x = j;		// x 좌표 저장
					y = i;		// y 좌표 저장
					map[i][j] = 0;		// 맵에는 빈칸으로 표시
				}
			}
		}

		for(int i=0; i<N+2; i++)		// 모서리 못넘어가도록 큰 값으로 초기화
			map[i][0] = map[i][N+1] = map[0][i] = map[N+1][i] = Integer.MAX_VALUE;
		
		level = 2;		// 레벨 초기화
		exp = 0;		// 경험치 초기화
		second = 0;		// 시간 초기화
		
		while(bfs());		// 먹을 먹이가 없을때까지 bfs() 호출
		
		System.out.println(second);		// 엄마 호출되면 버틴 시간 출력
	}

	private static boolean bfs() {		// 먹이가 있는지 확인하는 메서드
		visited = new boolean[N+2][N+2];		// 방문배열 초기화
		Queue<Point> q = new LinkedList<>();		// bfs용 큐
		q.add(new Point(x, y, 0));		// 좌표 추가
		visited[y][x] = true;		// 초기 위치 방문체크
		int tempY = Integer.MAX_VALUE;
		int tempX = Integer.MAX_VALUE;
		int tempD = 0;		// 찾은 최소 거리 저장

		while(!q.isEmpty()) {
			Point p = q.poll();
			if(tempD!=0 && tempD<p.d)		// 이동할 최종 위치 선정이 완료되면
				break;		// bfs 탈출

			if(map[p.y][p.x]!=0 && map[p.y][p.x]<level && (p.y<tempY || (p.y==tempY && p.x<tempX))) {		// 먹이를 찾으면 이동 후보로 저장, 우선순위인 좌표가 등장하면 후보 갱신
				tempY = p.y;		// y 좌표 후보로 저장
				tempX = p.x;		// x 좌표 후보로 저장
				tempD = p.d;		// d값 후보로 저장
			}
			
			for(int i=0; i<4; i++) {		// 각 방향으로 움직일 수 있는지 확인
				int newX = p.x + dx[i];
				int newY = p.y + dy[i];
				if(!visited[newY][newX] && map[newY][newX]<=level) {		// 이동할 수 있으면 이동
					visited[newY][newX] = true;		// 방문 체크하고
					q.add(new Point(newX, newY, p.d+1));		// 이동
				}
			}
		}
		
		if(tempD!=0) {		// 이동 후보 선정이 완료되면
			y = tempY;		// y좌표 이동
			x = tempX;		// x좌표 이동
			map[y][x] = 0;		// 해당 위치의 먹이 제거
			second += tempD;		// 움직인 거리만큼 상어는 버텼다
			exp++;		// 경험치 1 상승
			if(exp==level) {		// 경험치가 다 쌓였으면
				exp = 0;		// 경험치 초기화하고
				level++;		// 크기 1 상승
			}
			
			return true;		// 먹이 찾기 성공
		}
		
		return false;		// 먹이를 못찾으면 엄마 호출
	}
}