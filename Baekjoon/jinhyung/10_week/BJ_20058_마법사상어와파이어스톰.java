package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_20058_마법사상어와파이어스톰 {
	
	static int N, max = 0; // N: 얼음팔 길이, max: 가장 큰 덩어리
	static int[][] A, B; // A: 얼음판, B:
	static boolean[][] visited; // 덩어리 계산시 사용할 visited
	
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = 1<<Integer.parseInt(st.nextToken());
		int Q = Integer.parseInt(st.nextToken()); // Q: 회전 반복 수
		
		A = new int[N][N];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < Q; i++) { // Q번 L을 입력받아 회전시키기
			firestorm(1<<Integer.parseInt(st.nextToken()));
		}
		
		int sum = 0; // 남은 얼음 덩어리 합
		visited = new boolean[N][N]; // 가장 큰 덩어리 조사용
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(A[i][j] == 0) continue;
				sum += A[i][j]; // 얼음 양 더해주기
				if(!visited[i][j]) // 미방문한 얼음인 경우
					cluster(i, j); // 덩어리 크기 조사
			}
		}
		
		System.out.println(sum); // 남은 얼음 출력
		System.out.println(max); // 가장 큰 덩어리 출력
	}
	
	public static void firestorm(int L) {
		int[][] rA = new int[N][N]; // rA => 돌리고 난 뒤 A
		
		// 돌리기
		for(int i = 0; i < N; i+=L) { // i: 돌릴 2^L x 2^L 격자의 첫번째 원소의 x 좌표
			for(int j = 0; j < N; j+=L) { // y: 돌릴 2^L x 2^L 격자의 첫번째 원소의  y 좌표
				for(int rd = 0; rd < L; rd++) { // 돌릴 원소의 x좌표 증가량
					for(int cd = 0; cd < L; cd++) { // 돌릴 원소의 y좌표 증가량
						rA[i+cd][L-rd-1+j] = A[i+rd][j+cd]; // 돌린 곳에 대입
					}
				}
			}
		}

		A = rA; // A 바꾸기
		B = new int[N][N]; // B : 주변 4방에 얼음이 몇덩이 있는지
		for(int i = 0; i < N; i++) { // 전체 노드에
			for(int j = 0; j < N; j++) {
				for(int d = 0; d < 4; d++) { // 4방 탐색
					int nx = i + dx[d];
					int ny = j + dy[d];
					
					if(nx < 0 || nx >= N || ny < 0 || ny >= N || A[nx][ny] == 0) continue;
					B[i][j]++; // 얼음덩이가 있다면 + 1
				}
			}
		}
		
		for(int i = 0; i < N; i++) { // 전체 노드에
			for(int j = 0; j < N; j++) {
				// 주변 얼음 덩이가 2 이하이고 얼음 덩이가 있는 칸은 - 1
				if(B[i][j] > 2 || A[i][j] == 0) continue;
				A[i][j]--;
			}
		}
	}
	
	// BFS 덩이 크기 구하기
	public static void cluster(int x, int y) {
		Queue<int[]> queue = new ArrayDeque<>();
		queue.add(new int[] {x, y});
		visited[x][y] = true;
		int cnt = 1;
		while(!queue.isEmpty()) {
			int[] p = queue.poll();
			
			for(int d = 0; d < 4; d++) {
				int nx = p[0] + dx[d];
				int ny = p[1] + dy[d];
				
				if(nx < 0 || nx >= N || ny < 0 || ny >= N || A[nx][ny] == 0 || visited[nx][ny]) continue;
				
				queue.offer(new int[] {nx, ny});
				visited[nx][ny] = true;
				cnt++;
			}
		}
		
		max = max > cnt ? max : cnt;
	}
}
