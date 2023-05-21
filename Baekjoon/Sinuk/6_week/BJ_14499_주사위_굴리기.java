import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class BJ_14499_주사위_굴리기 {
	private static int n;
	private static int m;
	private static int x;
	private static int y;
	private static int[] dice = new int[7];
	// 동 서 북 남
	private static int[] dx = { 0, 0, 0, -1, 1 };
	private static int[] dy = { 0, 1, -1, 0, 0 };
	private static int[][] map;

	private static boolean move(int command) {
		int nx = x + dx[command];
		int ny = y + dy[command];
		// 이동한 위치가 범위 밖이면 false 반환
		if (0 > nx || nx >= n || 0 > ny || ny >= m) {
			return false;
		}
		// 이동한 방향에 따라 주사위의 값 바꿔주는 하드코딩
		int temp = dice[1];
		switch (command) {
		case 1:
			dice[1] = dice[3];
			dice[3] = dice[6];
			dice[6] = dice[4];
			dice[4] = temp;
			break;
		case 2:
			dice[1] = dice[4];
			dice[4] = dice[6];
			dice[6] = dice[3];
			dice[3] = temp;
			break;
		case 3:
			dice[1] = dice[2];
			dice[2] = dice[6];
			dice[6] = dice[5];
			dice[5] = temp;
			break;
		case 4:
			dice[1] = dice[5];
			dice[5] = dice[6];
			dice[6] = dice[2];
			dice[2] = temp;
		}

		// 이동한 위치의 지도값이 0이면 주사위 밑면의 값을 찍음
		if (map[nx][ny] == 0) {
			map[nx][ny] = dice[6];
		// 아니라면 주사위에 지도에 있던 값을 옮김
		} else {
			dice[6] = map[nx][ny];
			map[nx][ny] = 0;
		}

		// x, y 업데이트 후 true 반환
		x = nx;
		y = ny;
		return true;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		y = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		
		// map 초기화 후 입력 받기
		map = new int[n][m];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < m; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 이동명령 하나씩 실행 후 유효한 명령이면 출력
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < k; i++) {
			if (move(Integer.parseInt(st.nextToken())))
				System.out.println(dice[1]);
		}
	}
}
