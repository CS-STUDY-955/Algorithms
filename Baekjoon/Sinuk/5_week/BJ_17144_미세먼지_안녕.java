import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 17144번: 미세먼지 안녕! https://www.acmicpc.net/problem/17144
 */
public class BJ_17144_미세먼지_안녕 {
	private static int[][] spread(int[][] room, int[] cleaner) {
		// 상 좌 하 우
		int[] dx = { -1, 0, 1, 0 };
		int[] dy = { 0, -1, 0, 1 };

		// 패딩과 공기청정기만 있는 맵으로 초기화
		int r = room.length;
		int c = room[0].length;
		int[][] copiedRoom = new int[r][c];
		for (int i = 0; i < r; i++) {
			copiedRoom[i][0] = -2;
			copiedRoom[i][c - 1] = -2;
		}
		for (int i = 0; i < c; i++) {
			copiedRoom[0][i] = -2;
			copiedRoom[r - 1][i] = -2;
		}
		copiedRoom[cleaner[0]][cleaner[1]] = -1;
		copiedRoom[cleaner[0] + 1][cleaner[1]] = -1;

		// 미세먼지 확산
		for (int i = 1; i < r - 1; i++) {
			for (int j = 1; j < c - 1; j++) {
				if (room[i][j] > 0) {
					int value = room[i][j];
					int spreaded = room[i][j] / 5;
					for (int k = 0; k < 4; k++) {
						if (copiedRoom[i + dx[k]][j + dy[k]] >= 0) {
							copiedRoom[i + dx[k]][j + dy[k]] += spreaded;
							value -= spreaded;
						}
					}
					copiedRoom[i][j] += value;
				}
			}
		}
		return copiedRoom;
	}

	private static void airCleaner(int[][] room, int x, int y) {
		// 위쪽 순환
		// 왼쪽
		for (int i = x - 1; i > 0; i--) {
			room[i][1] = room[i - 1][1];
		}
		// 위쪽
		for (int i = 1; i < room[0].length - 1; i++) {
			room[1][i] = room[1][i + 1];
		}
		// 오른쪽
		for (int i = 1; i < x; i++) {
			room[i][room[0].length - 2] = room[i + 1][room[0].length - 2];
		}
		// 아래
		for (int i = room[0].length - 2; i > 2; i--) {
			room[x][i] = room[x][i - 1];
		}
		room[x][y + 1] = 0;
		// 아래쪽 순환
		// 왼쪽
		for (int i = x + 2; i < room.length - 1; i++) {
			room[i][y] = room[i + 1][y];
		}
		// 아래
		for (int i = 1; i < room[0].length - 1; i++) {
			room[room.length - 2][i] = room[room.length - 2][i + 1];
		}
		// 오른쪽
		for (int i = room.length - 2; i > x + 1; i--) {
			room[i][room[0].length - 2] = room[i - 1][room[0].length - 2];
		}
		// 위
		for (int i = room[0].length - 2; i > 2; i--) {
			room[x + 1][i] = room[x + 1][i - 1];
		}
		room[x + 1][y + 1] = 0;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int r = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		int t = Integer.parseInt(st.nextToken());
		int[][] room = new int[r + 2][c + 2];
		int[][] copiedRoom;
		int[] cleaner = new int[2];
		cleaner[0] = -1;
		for (int i = 1; i <= r; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= c; j++) {
				room[i][j] = Integer.parseInt(st.nextToken());
				if (room[i][j] == -1 && cleaner[0] == -1) {
					cleaner[0] = i;
					cleaner[1] = j;
				}
			}
		}

		for (int time = 0; time < t; time++) {
			copiedRoom = spread(room, cleaner);

			// 공기청정기 작동
			airCleaner(copiedRoom, cleaner[0], cleaner[1]);

			// room 업데이트
			for (int i = 1; i < r + 1; i++) {
				room[i] = copiedRoom[i].clone();
			}
		}

		int sum = 2;
		for (int i = 1; i <= r; i++) {
			for (int j = 1; j <= c; j++) {
				sum += room[i][j];
			}
		}
		System.out.println(sum);
	}
}