import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BJ_15685_드래곤_커브 {
	// 우, 상, 좌, 하
	// 일반적인 프로그래밍적 사고방식과 다르게 
	// 수학의 좌표계처럼 좌우 방향이 x고, 상하 방향이 y이므로 주의가 필요함 
	public static int[] dx = { 1, 0, -1, 0 };
	public static int[] dy = { 0, -1, 0, 1 };
	// 드래곤 커브가 찍힌 곳을 표시할 map
	public static boolean[][] map = new boolean[101][101];

	// x, y, d, g를 보내면 드래곤 커브를 만들고, map에 표시하는 메서드
	private static void makeCurve(int x, int y, int d, int g) {
		// 주어진 세대만큼 커브 만들기
		ArrayList<Integer> curve = new ArrayList<>();
		// 정수값 하나로 좌표를 표시할 것이므로 0세대 드래곤 커브의 두 꼭짓점 좌표를 변환해서 넣어줌
		curve.add(x * 101 + y);
		curve.add((x + dx[d]) * 101 + y + dy[d]);
		// 세대의 수 만큼 반복
		for (int generation = 1; generation <= g; generation++) {
			// 축이 될 좌표는 curve의 맨 마지막 값임
			int size = curve.size();
			int axizX = curve.get(size - 1) / 101;
			int axizY = curve.get(size - 1) % 101;
			// 드래곤 커브에 들어오는 순서는 역순으로 변환되어 들어오므로 size-2 부터 시작
			for (int i = size - 2; i >= 0; i--) {
				// 점 (x,y)를 축으로 점 (a, b)를 반시계방향으로 R도 회전시킬때의 공식
				// a' = (a-x) * cos(R) - (b-y) * sin(R) + x;
				// b' = (a-x) * sin(R) + (b-y) * cos(R) + y;
				// 이 문제에선 R = -90이므로 cosR = 0, sinR = -1
				// a' = y - b + x
				// b' = x - a + y 가 된다
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
		// 각각의 커브에 대해 makeCurve를 호출
		for (int i = 0; i < n; i++) {
			int[] curves = new int[4];
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 4; j++) {
				curves[j] = Integer.parseInt(st.nextToken());
			}
			makeCurve(curves[0], curves[1], curves[2], curves[3]);
		}

		// 0~99까지 돌려서 네 꼭짓점이 모두 드래곤 커브인 점이 있으면 count 증가
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
