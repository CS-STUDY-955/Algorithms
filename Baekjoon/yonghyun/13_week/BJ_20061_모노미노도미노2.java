import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

/**
 * 모노미노도미노 2
 * https://www.acmicpc.net/problem/20061
 *
 * 1. (x, y)에서 x가 행, y가 열을 의미함에 주의한다.
 * 2. 총 4개의 메서드가 필요하다.
 *  - 주어진 위치의 블록이 행과 열로 얼만큼 움직일 수 있는지 계산하는 메서드
 *  - 빨간색 칸의 오른쪽과 아래쪽으로 블록을 최대한 이동시키는 메서드
 *  - 테트리스가 일어나는 메서드(초록색: 행 기준, 파란색: 열 기준)
 *  - 연한 부분에 블록이 존재할 경우 한칸씩 미는 메서드
 * 3. 출력은 테트리스로 얻은 점수와 함께 각 칸에 존재하는 블록의 수 합을 출력하면 된다.
 * 4. 자료구조는 10x10 크기의 boolean 배열을 사용하면 될 것 같다.
 *
 * @author 배용현
 *
 */
class BJ_20061_모노미노도미노2 {

	static int N, score;
	static boolean[][] map;
	static int[][] order;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		input();
		solution();
		print();
	}

	private static void input() throws IOException {
		N = parseInt(br.readLine());
		order = new int[N][3];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			order[i][0] = parseInt(st.nextToken());        // t(.ㅡ|)
			order[i][1] = parseInt(st.nextToken());        // x(행)
			order[i][2] = parseInt(st.nextToken());        // y(열)
		}

		map = new boolean[10][10];
	}

	private static void solution() {
		for (int i = 0; i < N; i++) {
			putInGreen(i);
			putInBlue(i);
			popInGreen();
			popInBlue();
			pushToGreen();
			pushToBlue();
		}
	}

	private static void putInGreen(int orderIdx) {
		int[] cur = order[orderIdx];
		int t = cur[0];
		int y = cur[2];

		switch (t) {
			case 1:
				map[getNextRow(y)][y] = true;
				break;
			case 2:
				int minRow = Math.min(getNextRow(y), getNextRow(y+1));
				map[minRow][y] = map[minRow][y+1] = true;
				break;
			case 3:
				map[getNextRow(y)][y] = map[getNextRow(y)-1][y] = true;
				break;
		}
	}

	private static int getNextRow(int colIdx) {
		for (int rowIdx = 4; rowIdx <= 9; rowIdx++) {
			if (map[rowIdx][colIdx]) {
				return rowIdx-1;
			}
		}

		return 9;
	}

	private static int getNextCol(int rowIdx) {
		for (int colIdx = 4; colIdx <= 9; colIdx++) {
			if (map[rowIdx][colIdx]) {
				return colIdx-1;
			}
		}

		return 9;
	}

	private static void putInBlue(int orderIdx) {
		int[] cur = order[orderIdx];
		int t = cur[0];
		int x = cur[1];

		switch (t) {
			case 1:
				map[x][getNextCol(x)] = true;
				break;
			case 2:
				map[x][getNextCol(x)] = map[x][getNextCol(x)-1] = true;
				break;
			case 3:
				int minCol = Math.min(getNextCol(x), getNextCol(x+1));
				map[x][minCol] = map[x+1][minCol] = true;
				break;
		}
	}

	private static void popInGreen() {
		for (int i = 9; i >= 6; i--) {
			boolean bingo = true;
			for (int j = 0; j < 4; j++) {
				if (!map[i][j]) {
					bingo = false;
					break;
				}
			}
			if (bingo) {
				score++;
				pullRow(i);
				i++;
			}
		}
	}

	private static void pullRow(int idx) {        // idx위의 row를 한칸씩 땡기는 메서드
		for (int i = idx; i > 3; i--) {
			for (int j = 0; j < 4; j++) {
				map[i][j] = map[i-1][j];
			}
		}
	}

	private static void popInBlue() {
		for (int i = 9; i >= 6; i--) {        // 열 역순으로 접근
			boolean bingo = true;        // 열이 빙고인지
			for (int j = 0; j < 4; j++) {        // 모든 행에 대해 검사
				if (!map[j][i]) {        // 하나라도 비어있으면 빙고아님
					bingo = false;
					break;
				}
			}
			if (bingo) {        // 현재 열이 빙고이면
				score++;        // 점수 획득
				pullCol(i);        // 열 오른쪽으로 땡기고
				i++;        // 현재 열부터 다시 검사
			}
		}
	}

	private static void pullCol(int idx) {        // idx왼쪽의 col을 한칸씩 땡기는 메서드
		for (int i = idx; i > 3; i--) {        // 시작된 열부터 파란색 칸의 열까지
			for (int j = 0; j < 4; j++) {        // 모든 열을 땡김
				map[j][i] = map[j][i-1];
			}
		}
	}

	private static void pushToGreen() {
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 4; i++) {
				if (map[5][i]) {        // 연한 칸에 블럭이 존재하면
					pullRow(9);        // 한칸씩 다 땡김
				}
			}
		}
	}

	private static void pushToBlue() {
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 4; i++) {
				if (map[i][5]) {        // 연한 칸에 블럭이 존재하면
					pullCol(9);        // 한칸씩 다 땡김
				}
			}
		}
	}

	private static void print() {
		int answer = 0;
		for (int i = 6; i < 10; i++) {
			for (int j = 0; j < 4; j++) {
				if(map[i][j])
					answer++;
				if(map[j][i])
					answer++;
			}
		}

		sb.append(score).append('\n').append(answer);
		System.out.println(sb);
	}

}