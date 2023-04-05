import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 테트로미노
 * https://www.acmicpc.net/problem/14500
 * 
 * 1. 기준 회전하거나 대칭해서 만들수 있는 도형을 모두 만들면 4칸으로 도달할 수 있는 모든 경우의 수가 된다.  -> 아이디어 미쳤다
 * 2. 따라서 깊이가 4일때까지 dfs를 구하여 합을 구해주면 되는데, ㅜ모양의 도형은 dfs로 탐색할 수 없으므로 따로 처리한다.
 * 3. 모든 좌표들에서 최대의 경로합을 구하고, ㅜ모양 도형의 합을 구해 큰 값을 저장한다.
 * 4. 반복문이 끝나 가장 큰 값을 구하면 해당 값을 출력한다.
 * 
 * @author 배용현
 *
 */
public class BJ_14500_테트로미노 {
	static int N, M, answer;		// 행 N, 열 M, 정답 answer
	static int[][] map;			// 맵 정보
	static boolean[][] visited;		// dfs용 방문배열
	static int[] dx = {-1, 0, 1, 0};		// x 좌표 이동용
	static int[] dy = {0, -1, 0, 1};		// y 좌표 이동용
	static int[][][] annoyingShapes = {
				{{0, -1}, {0, 1}, {1, 0}},		// ㅏ
				{{0, -1}, {0, 1}, {-1, 0}},		// ㅓ
				{{0, 1}, {-1, 0}, {1, 0}},		// ㅗ
				{{0, -1}, {-1, 0}, {1, 0}}		// ㅜ
			};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		visited = new boolean[N][M];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++)
				map[i][j] = Integer.parseInt(st.nextToken());
		}
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				visited[i][j] = true;		// 방문처리
				dfs(i, j, 0, 0);		// 현재 좌표에서 dfs호출
				visited[i][j] = false;		// 방문처리 취소
				checkAnnoyingShape(i, j);		// ㅜ 모양 처리
			}
		}
		
		System.out.println(answer);
	}

	private static void checkAnnoyingShape(int y, int x) {		// ㅜ 모양 도형 처리
		for(int i=0; i<4; i++) {		// ㅏ, ㅓ, ㅗ ,ㅜ
			int sum = map[y][x];		// 각 도형의 합. 초기값은 좌표의 값
			
			for(int j=0; j<3; j++) {		// 각 도형이 뻗은 부분의 좌표값 더함
				int nextX = x + annoyingShapes[i][j][0];		// 다음 X값
				int nextY = y + annoyingShapes[i][j][1];		// 다음 Y값
				
				if(nextX<0 || nextY<0 || nextX>M-1 || nextY>N-1) {		// 좌표가 하나라도 유효하지 않으면
					sum = -1;		// 도형이 존재할 수 없음
					break;		// 현재 도형 탐색 중지
				}
				
				sum += map[nextY][nextX];		// 좌표가 유효하면 sum에 더함
			}

			if(sum>answer)		// 완성된 sum이 answer보다 크면
				answer = sum;		// 갱신
		}
	}

	private static void dfs(int y, int x, int depth, int sum) {		// dfs를 이용해 가능한 테트로미노 경우 모두 탐색
		if(depth==4) {		// 깊이가 4까지 탐색되면
			if(sum>answer)		// 탐색하면서 저장된 값이 현재 최대값이면
				answer = sum;		// 갱신
			
			return;
		}
		
		for(int i=0; i<4; i++) {		// 사방탐색
			int nextX = x + dx[i];		// 다음 x좌표
			int nextY = y + dy[i];		// 다음 y좌표
			
			if(nextX<0 || nextY<0 || nextX>M-1 || nextY>N-1 || visited[nextY][nextX])		// 이동할 수 있는 곳이면
				continue;		// 다음 반복
			
			visited[nextY][nextX] = true;		// 방문 처리
			dfs(nextY, nextX, depth+1, sum+map[nextY][nextX]);		// sum 갱신하면서 재귀호출
			visited[nextY][nextX] = false;		// 방문 처리 취소
		}
	}
}

