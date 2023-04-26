import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 발전소
 * https://www.acmicpc.net/problem/1102
 *
 * 1. 외판원 순회와 비슷한 문제인 것 같다.
 * 2. 단, 발전소가 켜져있는 곳에서 시작할 수 있고 P개 이상의 발전소가 켜져있으면 멈춘다.
 * 3. N이 최대 16이므로 비트마스킹을 통해 방문체크할 수 있을 것 같다.
 * 4. 각 발전소를 순회하며 해당 발전소가 켜져있는 상태의 최솟값을 구해나간다.
 *
 * @author 배용현
 *
 */
class BJ_1102_발전소 {

	static int N, P;
	static boolean[] turnedOn;
	static int[][] map, dp;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		input();
		solution();
	}

	private static void input() throws IOException {
		N = Integer.parseInt(br.readLine());		// 발전소의 개수
		map = new int[N][N];		// i 발전소를 이용하여 j 발전소를 켜는 비용
		dp = new int[N+1][(1<<N)];		// i개의 발전소를 j 상태로 켜는 최소 비용
		turnedOn = new boolean[N];		// 처음에 켜진 발전소의 정보

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < N + 1; i++) {
			Arrays.fill(dp[i], -1);
		}

		String input = br.readLine();
		for (int i = 0; i < N; i++) {
			turnedOn[i] = input.charAt(i) == 'Y';
		}
		P = Integer.parseInt(br.readLine());		// 켜져있어야 하는 최소 발전소 수
	}

	private static void solution() {
		int cnt = 0;		// 켜진 개수
		int status = 0;		// 켜진 상태
		for (int i = 0; i < N; i++) {		// 켜진 발전소 처리
			if (turnedOn[i]) {
				cnt++;
				status |= (1 << i);
			}
		}

		int answer = dfs(cnt, status);
		System.out.print(answer == Integer.MAX_VALUE ? -1 : answer);
	}

	private static int dfs(int cnt, int status) {
		if (cnt >= P) {		// 켜져야하는 최소 발전소 수를 채운 경우 더이상 발전소를 켤 필요가 없고 리턴값은 더해질 것이므로 0을 리턴
			return 0;
		}

		if (dp[cnt][status] != -1) {		// 이미 다른 경로로 현재 상태를 방문한 적 있는 경우 먼저 저장된 값이 최소임
			return dp[cnt][status];
		}

		dp[cnt][status] = Integer.MAX_VALUE;		// 최솟값을 저장하기 위해 최댓값으로 초기화
		for (int i = 0; i < N; i++) {		// 모든 발전소에 대해 순회
			if ((status & (1 << i)) == 0) {		// 꺼진 발전소는 다음 발전소를 켤 수 없음
				continue;
			}

			for (int j = 0; j < N; j++) {		// 켜진 발전소는 다음 발전소를 켤 수 있음
				if ((status & (1 << j)) != 0 || i == j) {        // 다음 발전소가 이미 켜져있거나 자기 자신이면 패스
					continue;
				}

				dp[cnt][status] = Math.min(dp[cnt][status], dfs(cnt + 1, status | (1 << j)) + map[i][j]);		// j 발전소를 켠 경우와 안켠 경우 중 비용이 작은 값을 저장
			}
		}

		return dp[cnt][status];		// 현재 상태의 발전소를 만드는 데 가장 적게 든 비용 리턴
	}

}