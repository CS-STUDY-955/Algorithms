package gold3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_14391_종이조각 {
	
	static int N, M, max; // 행, 열, 최댓값
	static int[][] paper; // 종이
	static boolean[][] selected; // 선택했는지 여부
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 입력받기
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		paper = new int[N][M];
		for(int i = 0; i < N; i++) {
			String str = br.readLine();
			for(int j = 0; j < M; j++) {
				paper[i][j] = str.charAt(j) - '0';
			}
		}
		
		// 각 원소에 대해 가로/세로를 정해주며 최대값 갱신
		max = Integer.MIN_VALUE;
		for(int i = 0; i < (1<<(N*M)); i++) {
			cut(i);
		}
		
		System.out.println(max); // 출력
	}

	private static void cut(int selected) {
		int sum = 0; // 이번 모양에서 수들의 합
		boolean[][] visited = new boolean[N][M]; // 방문 배열
		for(int i = 0; i < N; i++) { // 행
			for(int j = 0; j < M; j++) { // 열
				if(visited[i][j]) continue; // 이미 방문한 수라면 패스
				int num = paper[i][j]; // num 초기화
				visited[i][j] = true; // 방문 표시
				if((selected & (1<<(i*M+j))) == 0) { // 선택한 수가 세로라면
					for(int k = i+1; k < N; k++) { // 행을 증가시키면서
						if((selected & (1<<(k*M+j))) == 0) { // 다음 행의 수도 세로인 경우
							num *= 10; // num 자리 올려주기
							num += paper[k][j]; // 그 값 더해주기
							visited[k][j] = true; // 방문표시
						} else break; // 다음 행의 수가 가로인 경우 탈출
					}
				} else { // 선택한 수가 가로라면
					for(int k = j+1; k < M; k++) { // 열을 증가시키면서
						if((selected & (1<<(i*M+k))) != 0) { // 다음 열의 수도 가로인 경우
							num *= 10; // 자리 올려주기
							num += paper[i][k]; // 그 값 더해주기
							visited[i][k] = true; // 방문 표시
						} else break; // 다음 열의 수가 세로인 경우 탈출
					}
				}
				sum += num; // 구한 수 더해주기
			}
		}
		max = max > sum ? max : sum; // 최대값 갱신
	}
}
