import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

// 1번 → 2번 ←→ 3번 ↑→ 4번 ←↑→ 5번 전방향

/**
 * 백준 15683번 감시 https://www.acmicpc.net/problem/15683
 */
public class BJ_15683_감시 {
	// 사각지대의 최솟값
	private static int minBlindSpot = Integer.MAX_VALUE;
	// i번째 카메라의 방향
	private static int[] directions;
	// 사무실의 배치상황
	private static int[][] office;
	// 지정된 카메라의 방향에 따른 감시 상황
	private static int[][] copiedOffice;
	// 지도상의 0이 아닌 값이 뭐뭐 있는가
	private static Set<Integer> cantChange = new HashSet<>();
	// 카메라의 위치들
	private static ArrayList<Integer> position = new ArrayList<>();

	// 카메라 기준으로 주어진 dx, dy방향으로 감시구역을 표시
	private static void marking(int[][] target, int x, int y, int dx, int dy) {
		int i = x + dx;
		int j = y + dy;
		while (true) {
			// 밖으로 나가면 중단
			if (0 > i || i >= target.length || 0 > j || j >= target[0].length) {
				break;
			}
			// 벽을 만나면 중단
			if (target[i][j] == 6) {
				break;
				// 카메라가 있는 곳이라면 패스
			} else if (cantChange.contains(target[i][j])) {
				i += dx;
				j += dy;
				continue;
			}
			// 감시중인 곳이라면 표시
			target[i][j] = 9;
			i += dx;
			j += dy;
		}
	}

	// n개의 카메라에 대해 각각 4개의 방향중 하나를 정해주는 완전탐색
	private static void makeDirection(int step, int n) {
		// 모든 카메라의 방향을 골랐다면 사각지대의 크기를 구함
		if (step == n) {
			minBlindSpot = Math.min(minBlindSpot, getBlindArea());
			return;
		}
		int x = position.get(step) / office[0].length;
		int y = position.get(step) % office[0].length;

		// 카메라가 취할 수 있는 방향이 2종류인 경우
		if (office[x][y] == 2) {
			for (int i = 0; i < 2; i++) {
				directions[step] = i;
				makeDirection(step + 1, n);
			}
		}
		// 카메라가 취할 수 있는 방향이 1종류인 경우
		else if (office[x][y] == 5) {
			directions[step] = 0;
			makeDirection(step + 1, n);
		}
		// 카메라가 취할 수 있는 방향이 4종류인 경우
		else if (office[x][y] == 1 || office[x][y] == 3 || office[x][y] == 4) {
			for (int i = 0; i < 4; i++) {
				directions[step] = i;
				makeDirection(step + 1, n);
			}
		}
	}

	// 주어진 방향들에 대해 사각지대가 얼마나 되는지 계산하는 메서드
	private static int getBlindArea() {
		int count = 0;
		// 하 좌 상 우
		int[] dx = { 1, 0, -1, 0 };
		int[] dy = { 0, -1, 0, 1 };
		// 사무실 지도 복사
		for (int i = 0; i < copiedOffice.length; i++) {
			copiedOffice[i] = office[i].clone();
		}

		// directions에 따라 색칠
		for (int i = 0; i < position.size(); i++) {
			int tx = position.get(i) / copiedOffice[0].length;
			int ty = position.get(i) % copiedOffice[0].length;
			// 1번 카메라면
			if (copiedOffice[tx][ty] == 1) {
				// 방향에 따라 맞는 메서드를 호출해 감시구역 색칠
				marking(copiedOffice, tx, ty, dx[directions[i]], dy[directions[i]]);
				// 2번 카메라면 방향이 2가지이니 2번 호출
			} else if (copiedOffice[tx][ty] == 2) {
				marking(copiedOffice, tx, ty, dx[directions[i]], dy[directions[i]]);
				marking(copiedOffice, tx, ty, dx[(directions[i] + 2) % 4], dy[(directions[i] + 2) % 4]);
				// 3번 카메라도 2종류로 호출
			} else if (copiedOffice[tx][ty] == 3) {
				marking(copiedOffice, tx, ty, dx[directions[i]], dy[directions[i]]);
				marking(copiedOffice, tx, ty, dx[(directions[i] + 1) % 4], dy[(directions[i] + 1) % 4]);
				// 4번 카메라면 한방향 빼고 3개씩 호출해야함
			} else if (copiedOffice[tx][ty] == 4) {
				marking(copiedOffice, tx, ty, dx[directions[i]], dy[directions[i]]);
				marking(copiedOffice, tx, ty, dx[(directions[i] + 1) % 4], dy[(directions[i] + 1) % 4]);
				marking(copiedOffice, tx, ty, dx[(directions[i] + 2) % 4], dy[(directions[i] + 2) % 4]);
				// 마지막으로 5번 카메라면 전방향을 모두 호출
			} else if (copiedOffice[tx][ty] == 5) {
				marking(copiedOffice, tx, ty, dx[0], dy[0]);
				marking(copiedOffice, tx, ty, dx[1], dy[1]);
				marking(copiedOffice, tx, ty, dx[2], dy[2]);
				marking(copiedOffice, tx, ty, dx[3], dy[3]);
			}
		}

		// 감시구역 표시가 끝났다면 사각지대마다 count+1
		for (int i = 0; i < copiedOffice.length; i++) {
			for (int j = 0; j < copiedOffice[0].length; j++) {
				if (copiedOffice[i][j] == 0) {
					count++;
				}
			}
		}
		return count;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		office = new int[n][m];
		copiedOffice = new int[n][m];
		// 1~5는 지도상에서 바꿀 수 없는 값이니 미리 set에 넣어둬서 사용
		cantChange.add(1);
		cantChange.add(2);
		cantChange.add(3);
		cantChange.add(4);
		cantChange.add(5);

		// 사무실의 지도를 받는데 해당위치에 카메라가 있으면 position에 넣어둠
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < m; j++) {
				office[i][j] = Integer.parseInt(st.nextToken());
				if (cantChange.contains(office[i][j])) {
					position.add(i * m + j);
				}
			}
		}
		directions = new int[position.size()];

		// 완전 탐색 호출하고 결과값 출력
		makeDirection(0, position.size());
		System.out.println(minBlindSpot);
	}
}