package gold2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_21609_상어중학교 {
	
	static int N, M, bx, by, score;
	static int[][] map;
	
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, -1, 0, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 격자의 크기
		M = Integer.parseInt(st.nextToken()); // 색상의 개수
		map = new int[N][N]; // 전체 격자
		
		for(int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for(int c = 0; c < N; c++) { // 숫자 입력 받기
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 블록 그룹이 있는 동안
		while(find()) {
			autoplay(); // 오토 플레이
		}
		
		System.out.println(score); // 출력
	}

	// 블록 그룹 찾기
	private static boolean find() {
		boolean[][] visited = new boolean[N][N];
		bx = -1; // 기준 블록의 x좌표
		by = -1; // 기준 블록의 y좌표
		int maxr = 0; // 블록 그룹이 포함하는 무지개 블록 최대값
		int maxi = 0; // 블록 그룹의 블록 수 최대값
		for(int r = 0; r < N; r++) { // 모든 블록에 대해
			for(int c = 0; c < N; c++) {
				if(map[r][c] <= 0 || visited[r][c]) continue; // 검은 블록이거나 무지개블록이거나 빈공간(-2)이거나 방문한경우 건너뜀
				boolean[][] rainbowvisited = new boolean[N][N]; // 현재 블록그룹용 무지개블록 방문처리배열
				Queue<int[]> queue = new ArrayDeque<>();
				queue.offer(new int[] {r, c});
				visited[r][c] = true; // 방문 처리
				int bc = map[r][c]; // 기준 블록 번호
				int chunk = 1; // 블록 수
				int rainbow = 0; // 무지개 블록 수
				while(!queue.isEmpty()) {
					int[] p = queue.poll();
					int x = p[0];
					int y = p[1];
					
					// 4방탐색
					for(int i = 0; i < 4; i++) {
						int nx = x + dx[i];
						int ny = y + dy[i];
						
						// 격자밖이거나 검은 블록(-1)이거나 기준블록과 다른 종류거나 방문한 블록인 경우 건너뜀
						if(nx < 0 || nx >= N || ny < 0 || ny >= N || map[nx][ny] <= -1 || (map[nx][ny] > 0 && map[nx][ny] != bc)
								|| (map[nx][ny] == bc && visited[nx][ny]) || (map[nx][ny] == 0 && rainbowvisited[nx][ny])) continue;
						
						if(map[nx][ny] == 0) { // 무지개 블록이라면
							rainbow++; // 무지개 ++
							rainbowvisited[nx][ny] = true; // 무지개 방문표시
						}
						else visited[nx][ny] = true; // 방문표시
						chunk++; // 덩어리 추가
						queue.offer(new int[] {nx, ny});
					}
				}
				
				// 4방 탐색 후 블록수가 최대값 이상인 경우
				if(maxi <= chunk) {
					// 최대값과 같으면 무지개 블록 수가 더 커야한다
					if(maxi == chunk && maxr > rainbow) continue;
					bx = r; // 기준블록 x, y 갱신
					by = c;
					maxi = chunk; // 최대값 갱신
					maxr = rainbow;
				}
			}
		}
		
		// 최대 블록 수가 2 미만이라면 종료한다
		if(maxi < 2) return false;
		score += maxi * maxi; // 점수 획득
		
		// 블록 지우기
		Queue<int[]> queue = new ArrayDeque<>();
		queue.offer(new int[] {bx, by});
		int bc = map[bx][by];
		map[bx][by] = -2; // -2 -> 지움 표시
		while(!queue.isEmpty()) {
			int[] p = queue.poll();
			int x = p[0];
			int y = p[1];
			
			for(int i = 0; i < 4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				
				if(nx < 0 || nx >= N || ny < 0 || ny >= N || map[nx][ny] <= -1 || (map[nx][ny] != bc && map[nx][ny] != 0)) continue;
				map[nx][ny] = -2;
				queue.offer(new int[] {nx, ny});
			}
		}
		
		return true;
	}

	private static void autoplay() {
		gravity(); // 중력 작용
		
		int[][] tmp = new int[N][N];
		for(int r = 0; r < N; r++) {
			tmp[r] = map[r].clone();
		}
		
		// 반시계 회전
		for(int r = 0; r < N; r++) {
			for(int c = 0; c < N; c++) {
				map[r][c] = tmp[c][N-1-r];
			}
		}
		
		gravity(); // 중력 작용
	}
	
	private static void gravity() {
		for(int c = 0; c < N; c++) { // 각 열에 중력 작용
			int r = -1;
			ArrayDeque<Integer> stack = new ArrayDeque<>();
			while(r < N) {
				r++;
				if(r == N || map[r][c] == -1) { // 검은 블록이거나 바닥이라면 스택의 모든 블록 쌓기
					int k = r - 1;
					while(!stack.isEmpty()) {
						map[k--][c] = stack.pop();
					}
				}
				else if(map[r][c] == -2) continue; // 빈공간은 스택에 넣지 않는다
				else { // 블록 스택에 넣고 빈공간(-2) 처리
					stack.push(map[r][c]);
					map[r][c] = -2;
				}
			}
		}
	}
}
