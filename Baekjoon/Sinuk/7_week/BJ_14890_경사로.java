import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_14890_경사로 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int l = Integer.parseInt(st.nextToken());
		int[][] map = new int[n][n];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int rowCount = 0;
		boolean[][] deployed = new boolean[n][n];
		// 행 단위로 조사
		for (int i = 0; i < n; i++) {
			boolean flag = true;
			int combo = 1;
			// 정방향 체크
			// 인덱스 0은 이미 조사된 상태로 시작
			for (int j = 1; j < n; j++) {
				// 이전 번호와 같으면 콤보 증가
				if (map[i][j - 1] == map[i][j]) {
					combo++;
				// 더 클 경우
				} else if (map[i][j - 1] < map[i][j]) {
					// 콤보가 충분히 길지 않았거나 차이가 1이 아니라면 설치 불가 판정
					if (combo < l || map[i][j] - map[i][j-1] != 1) {
						flag = false;
						break;
					}
					// 충분히 길었고 차이가 1이라면 경사로 설치
					for (int k = j - l; k < j; k++) {
						deployed[i][k] = true;
					}
					combo = 1;
				// 작은 경우는 역방향에서 체크하므로 그냥 콤보만 초기화
				} else {
					combo = 1;
				}
			}
			// 역방향 체크
			// 로직은 거의 정방향과 동일
			combo = 1;
			for (int j = n - 2; j >= 0; j--) {
				if (map[i][j + 1] == map[i][j]) {
					combo++;
				} else if (map[i][j + 1] < map[i][j]) {
					if (combo < l || map[i][j] - map[i][j+1] != 1) {
						flag = false;
						break;
					}
					// 정방향에선 경사로가 겹칠 일이 없지만, 역방향의 경우 정방향에서 설치한 경사로가 남아 있을 수 있으니 체크
					// 이미 경사로가 있다면 설치 불가 판정
					for (int k = j + l; k > j; k--) {
						if (deployed[i][k]) {
							flag = false;
							break;
						}
						deployed[i][k] = true;
					}
					if (!flag) {
						break;
					}
					combo = 1;
				} else {
					combo = 1;
				}
			}

			// 경사로 설치가 가능하면 count 증가
			if (flag) {
				rowCount++;
			}
		}

		int colCount = 0;
		deployed = new boolean[n][n];
		// 열 단위로 조사
		// 로직은 행단위 조사와 거의 동일
		for (int i = 0; i < n; i++) {
			boolean flag = true;
			int combo = 1;
			// 정방향 체크
			for (int j = 1; j < n; j++) {
				if (map[j - 1][i] == map[j][i]) {
					combo++;
				} else if (map[j - 1][i] < map[j][i]) {
					if (combo < l || map[j][i] - map[j-1][i] != 1) {
						flag = false;
						break;
					}
					for (int k = j - l; k < j; k++) {
						deployed[k][i] = true;
					}
					combo = 1;
				} else {
					combo = 1;
				}
			}
			// 역방향 체크
			combo = 1;
			for (int j = n - 2; j >= 0; j--) {
				if (map[j + 1][i] == map[j][i]) {
					combo++;
				} else if (map[j + 1][i] < map[j][i]) {
					if (combo < l || map[j][i] - map[j+1][i] != 1) {
						flag = false;
						break;
					}
					for (int k = j + l; k > j; k--) {
						if (deployed[k][i]) {
							flag = false;
							break;
						}
						deployed[k][i] = true;
					}
					if (!flag) {
						break;
					}
					combo = 1;
				} else {
					combo = 1;
				}
			}

			if (flag) {
				colCount++;
			}
		}
		//출력
		System.out.println(rowCount + colCount);
	}
}