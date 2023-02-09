import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 안정된 집단
 * https://www.acmicpc.net/problem/2653
 * 
 * 1. 두 조건을 만족하면 집단이 안정적이다: 그룹원끼리는 사람에 대한 호감이 같아야 한다 / 모든 사람이 싫어하는 사람은 없다
 * 2. 각 그룹원의 모두에 대한 호감이 같은지 검사한다.
 * 3. 모두에게 미움받는 사람은 없는지 검사한다.
 * 4. 두 조건 모두 통과했다면 안정적인 집단이므로 주어진 형식대로 출력한다.
 * 5. 각 그룹원의 모두에 대한 호감이 같은지 검사하는 로직은 O(N^3), 모두에게 미움받는 사람은 없는지 검사하는 로직은 O(N)이 소요된다.
 * 6. 둘은 독립적이므로 문제의 시간복잡도는 O(N^3)인데, N이 최대 100이므로 약 1,000,000의 연산으로 통과할 수 있다.
 *
 * @author 배용현
 *
 */
public class BJ_2653_안정된집단 {
	static StringBuilder sb = new StringBuilder();
	static int n, answer = 0;
	static int[][] map;
	static boolean[] visited;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = parseInt(br.readLine());
		map = new int[n][n];
		visited = new boolean[n];

		for(int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0; j<n; j++) {
				map[i][j] = parseInt(st.nextToken());
			}
		}

		if(checkStableGroup()) {		// 그룹이 안정적이면
			System.out.println(answer);		// 그룹의 수 먼저 출력하고
			System.out.print(sb);		// 저장해두었던 그룹원 정보 출력
		} else {		// 불안정하면
			System.out.print(0);		// 0 출력
		}

	}

	private static boolean checkStableGroup() {		// 그룹이 안정적인지 검사하는 메서드
		for(int i=0; i<n; i++) {		// 각 행에 대해 검사
			if(visited[i])		// 이미 검사했던 행은 패스
				continue;

			for(int j=0; j<n; j++) {		// 각 행의 사람에 대한 호감이
				if(map[i][j]==0) {		// 좋은 사람(그룹원)은 아래를 체크
					int lover = 0;		// 이사람을 좋아하는 사람이 몇명인지 체크할 필드
					for(int k=0; k<n; k++) {		// 같은 그룹원의 행을 검사
						if(map[i][k]!=map[j][k]) {		// 하나라도 호감도가 다르면
							return false;		// 그룹은 불안정함
						}
						if(map[j][k]==0)		// 이사람을 좋아하는 사람이 있으면
							lover++;		// 수 체크
					}
					if(lover<=1)		// 이사람을 좋아하는 사람이 자신을 제외하고 아무도 없으면
						return false;		// 그룹은 불안정함

					visited[j] = true;		// 여기까지 도달하면 i와 j그룹원과 호감이 같은 것이므로 다음에 검사 안함
					sb.append(j+1).append(' ');		// 그룹원 확정하고 출력 저장
				}
			}
			sb.append('\n');		// 한 그룹을 전부 조사했으므로 한줄 넘김
			answer++;		// 마찬가지로 한 그룹을 전부 조사했으므로 그룹 수 1 추가
		}

		return true;		// 여기까지 도달하면 안정적인 그룹임
	}
}
