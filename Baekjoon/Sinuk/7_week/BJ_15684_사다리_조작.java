import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_15684_사다리_조작 {
	private static boolean[][] ladder;

  // 재귀를 이용한 조합으로 사다리 설치
	private static boolean addLadder(int step, int max, int n, int h, int startIdx) {
		if (step == max) {
			return isPossible(n, h);
		}

		boolean flag = false;
		for (int i = startIdx; i < n * h; i++) {
			int x = i / n + 1;
			int y = i % n + 1;
      // 현재 위치, 그 양 옆에 사다리가 없어야 사다리가 설치될 수 있음
			if (!ladder[x][y] && !ladder[x][y - 1] && !ladder[x][y + 1]) {
				ladder[x][y] = true;
				flag = addLadder(step + 1, max, n, h, i + 1);
				ladder[x][y] = false;
			}
			if (flag) {
				break;
			}
		}

		return flag;
	}

  // 1~n번까지 사다리 타면서 전부 자기 자신으로 돌아오는지 확인
	private static boolean isPossible(int n, int h) {
		for (int i = 1; i <= n; i++) {
			int x = i;
			for (int j = 1; j <= h; j++) {
				if (ladder[j][x-1]) {
					x--;
				} else if (ladder[j][x]) {
					x++;
				}
			}
			if (x != i) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int h = Integer.parseInt(st.nextToken());
		ladder = new boolean[h + 2][n + 2];
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			ladder[a][b] = true;
		}

		int count = -1;
    // 사다리를 0~3개 놓아 조건을 만족하는지 순서대로 판별
		for (int i = 0; i <= 3; i++) {
			if (addLadder(0, i, n, h, 0)) {
        // 만약 조건을 만족하면 놓은 수를 출력
				count = i;
				break;
			}
		}
    // 조건을 만족하지 못하면 -1이 출력
		System.out.println(count);
	}
}
