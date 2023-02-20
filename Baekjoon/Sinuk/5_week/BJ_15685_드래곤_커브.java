import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BJ_15685_드래곤_커브 {
	// 우, 상, 좌, 하
	public static int[] dx = { 1, 0, -1, 0 };
	public static int[] dy = { 0, -1, 0, 1 };
	public static boolean[][] map = new boolean[101][101];

	private static void makeCurve(int x, int y, int d, int g) {
		// 주어진 세대만큼 커브 만들기
		ArrayList<Integer> curve = new ArrayList<>();
		curve.add(x * 101 + y);
		curve.add((x + dx[d]) * 101 + y + dy[d]);
		for (int generation = 1; generation <= g; generation++) {
			int size = curve.size();
			int axizX = curve.get(size - 1) / 101;
			int axizY = curve.get(size - 1) % 101;
			for (int i = size - 2; i >= 0; i--) {
				int targetX = curve.get(i) / 101;
				int targetY = curve.get(i) % 101;
				int newX = axizY - targetY + axizX;
				int newY = targetX - axizX + axizY;
				curve.add(newX * 101 + newY);
			}
		}
		// 만든 커브를 map에 표시하기
		for (Integer c : curve) {
			map[c / 101][c % 101] = true;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int[][] curves = new int[n][4];
		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 4; j++)
				curves[i][j] = Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < n; i++) {
			makeCurve(curves[i][0], curves[i][1], curves[i][2], curves[i][3]);
		}

		int count = 0;
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if (map[i][j] && map[i][j + 1] && map[i + 1][j] && map[i + 1][j + 1])
					count++;
			}
		}
		System.out.println(count);
	}
}