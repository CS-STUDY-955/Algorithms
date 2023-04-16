import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 큐빙
 * https://www.acmicpc.net/problem/5373
 *
 * 1. 큐브는 3x3의 면이 6개 존재하므로 3차원 배열로 표현한다.
 * 2. 큐브의 한쪽 면을 돌리면 해당 면은 전부 변화가 일어나고, 맞닿아있는 4개의 면은 한 변씩 변화가 일어난다.
 * 3. 큐브를 바라보는 관점을 다르게하면 어느 면을 기준으로 회전시키든 같은 동작이므로 메서드 하나로 표현할 수 있을 것 같다.
 * ------------------------------------------
 * - 메서드가 재활용되려면 어느 방향을 기준으로 바라보더라도 2차원 배열의 작성 방향이 같아야한다.
 * - 또한 메서드를 호출할때 up, left, down, right도 맞춰주어야 한다.
 * - 이 부분이 도저히 뭔지 모르겠다..
 *
 * @author 배용현
 *
 */
class BJ_5373_큐빙 {

	static int n;
	static char[][][] cube;
	static char[] sideColor = {'w', 'y', 'r', 'o', 'g', 'b'};
	static String[] orders;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		int T = Integer.parseInt(br.readLine());
		for (int tc = 0; tc < T; tc++) {
			input();
			solution();
			print();
		}
		System.out.print(sb);
	}
	
	private static void input() throws IOException {
		n = Integer.parseInt(br.readLine());
		cube = new char[6][3][3];		// 6x3x3의 큐브가 가진 각 칸의 색깔 저장
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					cube[i][j][k] = sideColor[i];
				}
			}
		}

		orders = new String[n];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			orders[i] = st.nextToken();
		}
	}

	private static void solution() {
		for (String order : orders) {
			char side = order.charAt(0);
			boolean clockwise = order.charAt(1) == '+';

			switch (side) {
				case 'U': rotate(0, 4, 2, 5, 3, clockwise); break;
				case 'D': rotate(1, 3, 5, 2, 4, clockwise); break;
				case 'F': rotate(2, 0, 4, 1, 5, clockwise); break;
				case 'B': rotate(3, 5, 1, 4, 0, clockwise); break;
				case 'L': rotate(4, 2, 0, 3, 1, clockwise); break;
				case 'R': rotate(5, 1, 3, 0, 2, clockwise); break;
			}
		}
	}

	private static void rotate(int front, int up, int left, int down, int right, boolean clockwise) {
		if (clockwise) {
			rotateClockwise(front, up, left, down, right);
		} else {
			rotateCounterClockwise(front, up, left, down, right);
		}
	}

	private static void rotateClockwise(int front, int up, int left, int down, int right) {
		char[][] newFront = new char[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				newFront[i][j] = cube[front][2-j][i];
			}
		}
		cube[front] = newFront;

		char[] oldUp = new char[3];
		for (int i = 0; i < 3; i++) {
			oldUp[i] = cube[up][i][0];
			cube[up][i][0] = cube[left][0][2-i];
			cube[left][0][2-i] = cube[down][2][i];
			cube[down][2][i] = cube[right][2-i][2];
			cube[right][2-i][2] = oldUp[i];
		}
	}

	private static void rotateCounterClockwise(int front, int up, int left, int down, int right) {
		char[][] newFront = new char[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				newFront[i][j] = cube[front][j][2-i];
			}
		}
		cube[front] = newFront;

		char[] oldUp = new char[3];
		for (int i = 0; i < 3; i++) {
			oldUp[i] = cube[up][i][0];
			cube[up][i][0] = cube[right][2-i][2];
			cube[right][2-i][2] = cube[down][2][i];
			cube[down][2][i] = cube[left][0][2-i];
			cube[left][0][2-i] = oldUp[i];
		}
	}

	private static void print() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				sb.append(cube[0][j][2-i]);
			}
			sb.append('\n');
		}
	}

}