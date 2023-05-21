import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 어른 상어
 * https://www.acmicpc.net/problem/19237
 *
 * 1. 필요한 변수
 * 	- 냄새가 남아있는지 확인할 int형 2차원 배열
 * 	- 위, 아래, 왼쪽, 오른쪽을 가리키는 방향 배열
 * 	- 상어의 위치와 방향를 저장할 List<SharkInfo>
 * 	- 각 상어의 이동 우선순위를 저장할 3차원 int형 배열
 * 2. 로직
 *  - 각 상어가 냄새를 뿌린다.
 *  - 상어가 이동한다.
 *  - 냄새가 옅어지고, 동일한 칸의 상어가 먹힌다.
 * 3. 주의할 점
 * - 상어의 번호는 계산하기 쉽도록 -1하여 적용한다.
 * - 처음 위치에서 각 상어는 자신의 냄새를 뿌리고 움직이기 시작한다.
 *
 * @author 배용현
 *
 */
class BJ_19237_어른상어 {

	static class SmellInfo {		// 냄새의 정보
		int sharkNo, remain;

		public SmellInfo(int sharkNo, int remain) {
			this.sharkNo = sharkNo;
			this.remain = remain;
		}

	}

	static class SharkInfo {		// 상어의 정보
		int x, y, d;

		public SharkInfo(int x, int y, int d) {
			this.x = x;		// 열
			this.y = y;		// 행
			this.d = d;		// 방향
		}

	}

	static int N, M, k;
	static SmellInfo[][] smell;
	static int[][][] directionPriority;
	static SharkInfo[] sharkInfos;
	static Set<Integer> livingShark;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static final int[] dx = {0, 0, -1, 1};		// 위쪽, 아래쪽, 왼쪽, 오른쪽
	static final int[] dy = {-1, 1, 0, 0};

	public static void main(String[] args) throws IOException {
		input();
		System.out.println(solution());
	}

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());		// 맵 크기
		M = parseInt(st.nextToken());		// 상어 수
		k = parseInt(st.nextToken());		// 냄새 지속 시간

		smell = new SmellInfo[N][N];		// 냄새가 남아있는 정보를 저장할 배열
		directionPriority = new int[M][4][4];		// 각 상어의 방향 우선순위
		sharkInfos = new SharkInfo[M];		// 상어의 위치, 방향 정보
		livingShark = new HashSet<>();		// 현재 살아있는 상어의 번호

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				int sharkNo = parseInt(st.nextToken()) - 1;
				if (sharkNo >= 0) {
					sharkInfos[sharkNo] = new SharkInfo(j, i, 0);
					livingShark.add(sharkNo);
				}
			}
		}

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < M; i++) {
			sharkInfos[i].d = parseInt(st.nextToken()) - 1;
		}

		for (int i = 0; i < M; i++) {
			for (int j = 0; j < 4; j++) {
				st = new StringTokenizer(br.readLine());
				for (int l = 0; l < 4; l++) {
					directionPriority[i][j][l] = parseInt(st.nextToken()) - 1;
				}
			}
		}
	}

	private static int solution() {
		leaveSmell();		// 처음 위치에 냄새를 남김
		for (int i = 1; i <= 1000; i++) {		// 1000초까지 수행
			move();		// 상어가 움직임
			weakenSmell();		// 냄새가 약해짐
			leaveSmell();		// 현재 위치에 냄새를 남김

			if (livingShark.size() == 1) {		// 남은 상어의 수가 1개이면 현재 초 리턴
				return i;
			}
		}

		return -1;		// 1000초까지 상어가 여러마리 살아있으면 -1 리턴
	}

	private static void weakenSmell() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (smell[i][j] != null) {		// 현재 칸에 냄새가 있을때
					if (smell[i][j].remain > 1) {		// remain이 2이상이면 1 감소
						smell[i][j].remain--;
					} else {		// 1이면 null로 초기화
						smell[i][j] = null;
					}
				}
			}
		}
	}

	private static void leaveSmell() {
		for (int shark : livingShark) {
			SharkInfo sharkInfo = sharkInfos[shark];		// 상어의 위치에
			smell[sharkInfo.y][sharkInfo.x] = new SmellInfo(shark, k);		// smell을 k로 초기화
		}
	}

	private static void move() {
		ArrayList<Integer>[][] movedShark = new ArrayList[N][N];		// 겹친 상어를 판단하기 위한 임시 배열
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				movedShark[i][j] = new ArrayList<>();
			}
		}

		for (int shark : livingShark) {
			sharkInfos[shark].d = setDirection(shark);		// 다음 진행방향 설정하고
			int x = sharkInfos[shark].x += dx[sharkInfos[shark].d];
			int y = sharkInfos[shark].y += dy[sharkInfos[shark].d];
			movedShark[y][x].add(shark);		// 이동한 좌표의 임시 배열에 저장
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (movedShark[i][j].size() > 1) {		// 상어가 2개 이상 겹쳤으면
					movedShark[i][j].sort(null);		// 오름차순 정렬하고
					for (int l = 1; l < movedShark[i][j].size(); l++) {		// 가장 번호가 작은 상어를 제외하고 전부 삭제
						livingShark.remove(movedShark[i][j].get(l));
					}
				}
			}
		}
	}

	private static int setDirection(int shark) {
		int curX = sharkInfos[shark].x;
		int curY = sharkInfos[shark].y;
		int curD = sharkInfos[shark].d;

		for (int i = 0; i < 4; i++) {		// 주위에 냄새가 없는 곳이 있으면 해당 방향으로 이동
			int nd = directionPriority[shark][curD][i];		// 이동 우선순위부터 탐색
			int nx = curX + dx[nd];
			int ny = curY + dy[nd];
			if (!isOut(nx, ny) && (smell[ny][nx] == null || smell[ny][nx].remain == 0)) {		// 우선순위순으로 탐색했으므로 냄새가 없다면 해당 방향으로 이동
				return nd;
			}
		}

		for (int i = 0; i < 4; i++) {		// 모든 방향에 냄새가 있다면 주위의 자신이 지나온 방향으로 이동
			int nd = directionPriority[shark][curD][i];		// 이동 우선순위부터 탐색
			int nx = curX + dx[nd];
			int ny = curY + dy[nd];
			if (!isOut(nx, ny) && (smell[ny][nx] == null || smell[ny][nx].sharkNo == shark)) {		// 우선순위순으로 탐색했으므로 내가 이동했던 길이라면 해당 방향으로 이동
				return nd;
			}
		}

		return -1;
	}

	private static boolean isOut(int x, int y) {
		return x < 0 || y < 0 || x > N - 1 || y > N - 1;
	}

}