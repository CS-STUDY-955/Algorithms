import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_17140_이차원_배열과_연산 {
	// 해당 수가 몇번 나왔는지 기록하는 객체
	// 우선순위 큐에 들어갈 객체이니 comparable을 구현해야 한다.
	private static class Counter implements Comparable<Counter> {
		int num;
		int count;

		public Counter(int num, int count) {
			this.num = num;
			this.count = count;
		}

		@Override
		public int compareTo(Counter o) {
			if (this.count == o.count)
				return this.num - o.num;
			return this.count - o.count;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[][] A = new int[100][100];
		StringTokenizer st = new StringTokenizer(br.readLine());
		int r = Integer.parseInt(st.nextToken()) - 1;
		int c = Integer.parseInt(st.nextToken()) - 1;
		int k = Integer.parseInt(st.nextToken());
		for (int i = 0; i < 3; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 3; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		// 초깃값 설정. 각각 행의 갯수, 열의 갯수, 연산을 몇번했는지
		int rLen = 3;
		int cLen = 3;
		int count = 0;
		
		// 각각의 수가 몇번 나왔는지를 정렬하기 위한 우선순위 큐
		PriorityQueue<Counter> queue;
		// 원하는 좌표에 원하는 값이 나오거나 100번 초과면 종료
		while (A[r][c] != k && count++ < 100) {
			// tempA를 사용하지 않으면 행이나 열이 줄어들었을때 쓰레기값이 남아있게 된다
			int[][] tempA = new int[100][100];
			
			// R연산
			if (rLen >= cLen) {
				// 연산 후의 cLen값
				int tempCLen = 0;
				// 각각의 행에 대해
				for (int i = 0; i < rLen; i++) {
					// 우선순위 큐를 초기화하고
					queue = new PriorityQueue<>();
					// 0이 아닌 값이 각각 몇번 나오는지 체크
					int[] rowCounter = new int[101];
					for (int j = 0; j < cLen; j++) {
						if (A[i][j] != 0) {
							rowCounter[A[i][j]]++;
						}
					}
					// 나온 결과들을 큐에 삽입
					for (int j = 1; j < 101; j++) {
						if (rowCounter[j] > 0) {
							queue.add(new Counter(j, rowCounter[j]));
						}
					}
					
					// 최대 사이즈는 100이므로 둘중 최소값으로 잡아준다
					int minSize = Math.min(50, queue.size());
					// 수와 해당값의 빈도수의 짝이므로 50번이면 됨
					tempCLen = Math.max(tempCLen, minSize * 2);
					// 하나씩 꺼내서 tempA 업데이트
					for (int j = 0; j < minSize; j++) {
						Counter counter = queue.poll();
						tempA[i][j * 2] = counter.num;
						tempA[i][j * 2 + 1] = counter.count;
					}
				}
				// 바뀐 cLen 업데이트
				cLen = tempCLen;
				
			// C연산
			} else {
				// 연산후의 rLen값
				int tempRLen = 0;
				// R연산과 동일하지만, i, j의 인덱스 순서만 바뀐다
				for (int i = 0; i < cLen; i++) {
					queue = new PriorityQueue<>();
					int[] colCounter = new int[101];
					for (int j = 0; j < rLen; j++) {
						if (A[j][i] != 0) {
							colCounter[A[j][i]]++;
						}
					}
					for (int j = 1; j < 101; j++) {
						if (colCounter[j] > 0) {
							queue.add(new Counter(j, colCounter[j]));
						}
					}
					
					int minSize = Math.min(50, queue.size());
					tempRLen = Math.max(tempRLen, minSize * 2);
					for (int j = 0; j < minSize; j++) {
						Counter counter = queue.poll();
						tempA[j * 2][i] = counter.num;
						tempA[j * 2 + 1][i] = counter.count;
					}
				}
				rLen = tempRLen;
			}

			// 연산이 1회 끝났으면 tempA를 A로 복제
			for (int i = 0; i < rLen; i++) {
				A[i] = tempA[i].clone();
			}
		}
		// 100회 이상 연산 했다면 -1로
		if (count > 100)
			count = -1;
		System.out.println(count);
	}

}
