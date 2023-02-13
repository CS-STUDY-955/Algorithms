import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 치킨 배달
public class BJ_15686_치킨_배달 {
	// 선택한 치킨집들을 저장하는 배열
	static int[] visited;
	// 치킨집과 집 간의 거리를 모두 저장하는 배열
	static int[][] distance;

	/**
	 * dfs로 백트래킹
	 * @param m : 입력받은 m
	 * @param step : 현재 스탭 
	 * @param startIdx :현재까지 확인한 인덱스 
	 * @param hCount : 집의 갯수 
	 * @param cCount : 치킨집의 갯수
	 * @return 최소 치킨거리
	 */
	public static int dfs(int m, int step, int startIdx, int hCount, int cCount) {
		// 치킨집을 다 골랐다면 해당 배열의 치킨거리를 계산하여 반환
		if (m == step) {
			return getChiDist(hCount, cCount);
		}
		// m - step: 현재 남은 스탭 수
		// cCount - startIdx: 남아있는 인덱스 수
		// 남아있는 인덱스가 부족하면 엄청 큰값을 반환하여 탐색 중지 
		if (m - step > cCount - startIdx) {
			System.out.println("test!");
			return 10_000_000;
		}

		// 매우 큰값으로 초기화
		int min = 10_000_000;

		// 조합이므로 순서가 상관 없으니 i의 초깃값을 startIdx로 줌
		for (int i = startIdx; i < cCount; i++) {
			// 값이 덮어씌우질 것이므로 해제 안해도 됨
			visited[step] = i;
			// dfs 재귀
			min = Math.min(min, dfs(m, step + 1, i + 1, hCount, cCount));
		}

		return min;
	}

	// 치킨거리를 구하는 메서드
	public static int getChiDist(int hCount, int cCount) {
		int sum = 0;
		// 현재 주어진 치킨집들 상황에서 각 집의 치킨거리를 저장하는 배열
		int[] minDist = new int[hCount];
		// 매우 큰 값으로 수동 초기화
		for (int i = 0; i < hCount; i++) {
			minDist[i] = 10000000;
		}

		// 최소 치킨거리 구하기
		for (int j = 0; j < visited.length; j++) {
			for (int i = 0; i < hCount; i++) {
				minDist[i] = Math.min(minDist[i], distance[i][visited[j]]);
			}
		}

		// 치킨거리의 합 구하기
		for (int i = 0; i < hCount; i++) {
			sum += minDist[i];
		}
		return sum;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());

		// 2 <= N <= 50, 1 <= M <= 13, hCount <= 2*N
		// 집의 갯수
		int hCount = 0;
		// 집의 위치를 x, y 좌표로 각각 저장
		int[][] house = new int[n * 2][2];
		// 치킨집의 갯수
		int cCount = 0;
		// 치킨 집의 위치를 x, y 좌표로 각각 저장
		int[][] chick = new int[13][2];
		// 입력 받기
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++) {
				int kind = Integer.parseInt(st.nextToken());
				if (kind == 1) {
					house[hCount][0] = i;
					house[hCount++][1] = j;
				} else if (kind == 2) {
					chick[cCount][0] = i;
					chick[cCount++][1] = j;
				}
			}
		}


		distance = new int[hCount][cCount];
		visited = new int[m];
		// 각각의 치킨거리를 저장
		for (int i = 0; i < hCount; i++) {
			for (int j = 0; j < cCount; j++) {
				distance[i][j] = Math.abs(house[i][0] - chick[j][0]) + Math.abs(house[i][1] - chick[j][1]);
			}
		}

		// dfs로 해결
		System.out.println(dfs(m, 0, 0, hCount, cCount));
	}

	// 이하의 코드는 그리디로 작성됨
	// 그리디로 풀 경우, 이하의 반례가 존재함
//	5 1
//	2 1 0 1 2
//	0 0 0 0 0
//	0 0 2 0 0
//	0 0 0 0 0
//	2 1 0 1 2
//
//	ans: 12
//	이 경우는 답이 제대로 나오지만
//
//
//	5 4
//	2 1 0 1 2
//	0 0 0 0 0
//	0 0 2 0 0
//	0 0 0 0 0
//	2 1 0 1 2
//
//	ans: 4
//	output: 6
// 	이 경우 오답이 나옴

	public void wrong(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());

		// 2 <= N <= 50, 1 <= M <= 13, hCount <= 2*N
		int hCount = 0;
		int[][] house = new int[n * 2][2];
		int cCount = 0;
		int[][] chick = new int[13][2];
		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++) {
				int kind = Integer.parseInt(st.nextToken());
				if (kind == 1) {
					house[hCount][0] = i;
					house[hCount++][1] = j;
				} else if (kind == 2) {
					chick[cCount][0] = i;
					chick[cCount++][1] = j;
				}
			}
		}

		int[][] distance = new int[hCount][cCount];
		for (int i = 0; i < hCount; i++) {
			for (int j = 0; j < cCount; j++) {
				distance[i][j] = Math.abs(house[i][0] - chick[j][0]) + Math.abs(house[i][1] - chick[j][1]);
			}
		}

		// 현재 각각의 집의 최소 치킨거리를 저장하는 배열
		int[] min = new int[hCount];
		for (int i = 0; i < hCount; i++) {
			min[i] = 1_000_000;
		}
		// 현재 그 치킨집을 선택했는지 저장하는 배열
		boolean[] selected = new boolean[cCount];
		// 현재의 최종 치킨거리를 저장하는 배열
		int chiDist = 1_000_000;
		for (int i = 0; i < m; i++) {
			// 새로 선택할 치킨집의 인덱스
			int minIdx = 0;
			// 해당 치킨집이 선택되었을때의 치킨거리
			int tempDist = 1_000_000;
			// 최대한 치킨거리를 감소시키는 치킨집을 탐색하고 해당 인덱스를 minInx에 저장
			for (int j = 0; j < cCount; j++) {
				if (selected[j]) {
					continue;
				}
				int tempSum = 0;
				for (int k = 0; k < hCount; k++) {
					if (distance[k][j] > min[k]) {
						tempSum += min[k];
					} else {
						tempSum += distance[k][j];
					}
//					System.out.println("k: " + k + ", tempSum: " + tempSum);
				}

//				System.out.println("tempSum: " + tempSum + ", tempDist: " + tempDist);
				if (tempSum < tempDist) {
					tempDist = tempSum;
					minIdx = j;
				}
			}

			// 더이상 치킨거리가 감소하지 않는다면 종료
			if (chiDist <= tempDist) {
				break;
			}
			// 감소했다면 치킨거리 업데이트
//			System.out.println("chiDist: " + chiDist + ", tempDist: " + tempDist);
			chiDist = tempDist;
			selected[minIdx] = true;

			for (int j = 0; j < hCount; j++) {
				min[j] = Math.min(min[j], distance[j][minIdx]);
			}
		}

		System.out.println(chiDist);
	}
}
