import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

/**
 * 종이조각
 * https://www.acmicpc.net/problem/14391
 *
 * 1. 4x4의 각 칸이 가로를 이루는지, 세로를 이루는지 결정한다.
 * 2. 각 칸을 가로와 세로로 순회하며 알맞게 연속된 칸인지 검사한다.
 * 3. 알맞게 연속된 칸이라면 그만큼 값을 갱신하고, 끊겼다면 이전 값을 더하여 최종 사각형의 값을 구한다.
 * 4. 모든 경우의 수에대해 최종 사각형의 값 중 최댓값을 출력한다.
 *
 * @author 배용현
 *
 */
class BJ_14391_종이조각 {

	static int N, M, answer;
	static int[][] map;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		input();
		solution();
		System.out.println(answer);
	}

	private static void solution() {
		for (int i = 0; i < (1 << (N * M)); i++) {
			int totalSum = 0;
			for (int j = 0; j < N; j++) {		// 가로 값 합산
				int rowSum = 0;
				for (int k = 0; k < M; k++) {
					if ((i & (1 << (j * M + k))) == 0) {        // 현재 탐색중인 칸의 방향이 가로라면
						rowSum *= 10;        // 값 갱신
						rowSum += map[j][k];
					} else {        // 세로라면
						totalSum += rowSum;        // 연속이 끊겼으므로 최종 사각형의 값에 더하고
						rowSum = 0;        // 0으로 초기화
					}
				}
				totalSum += rowSum;		// 행에서 마지막으로 연속된 값 처리
			}

			for (int j = 0; j < M; j++) {		// 세로 값 합산
				int colSum = 0;
				for (int k = 0; k < N; k++) {
					if ((i & (1 << (k * M + j))) != 0) {        // 현재 탐색중인 칸의 방향이 세로라면
						colSum *= 10;        // 값 갱신
						colSum += map[k][j];
					} else {        // 가로라면
						totalSum += colSum;        // 연속이 끊겼으므로 최종 사각형의 값에 더하고
						colSum = 0;        // 0으로 초기화
					}
				}
				totalSum += colSum;		// 열에서 마지막으로 연속된 값 처리
			}

			answer = Math.max(answer, totalSum);
		}
	}

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			String input = br.readLine();
			for (int j = 0; j < M; j++) {
				map[i][j] = input.charAt(j) - '0';
			}
		}

		answer = 0;
	}
}