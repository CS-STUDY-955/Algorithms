import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 보석 도둑
 * https://www.acmicpc.net/problem/1202
 * 
 * 1. 넣을 수 있는 최대 가격 보석을 가방에 하나씩 넣으면 될 것 같지만 가방의 허용 무게가 각각 다르고, 가방을 정렬할 수 없으므로 어렵다.
 * 2. 최대한 무거운 물건을 허용 무게가 높은 가방에 담아야한다.
 * 3. 그런데 무게만 무거운 물건을 담으면 최적을 구할 수 없으므로 가격순으로 담아야한다.
 * 3. 최대 무게를 기준으로 가방을 내림차순 정렬한다.
 *
 * --------------------------------
 * 1. 보석을 무게 순으로 오름차순, 가격 순으로 내림차순 정렬한다.
 * 2. 가방을 허용 무게 오름차순으로 정렬한 뒤 가방을 순회하며 현재 가방에 들어갈 수 있는 보석을 모두 우선순위큐에 넣는다.
 * 3. 우선순위큐가 가격 기준 내림차순으로 정렬되어 있으면 뽑았을때 현재 가방에 넣을 수 있는 보석 중 가격이 가장 비싼 보석이 나온다.
 * 4. 큐가 비어있으면 넘어가고, 비어있지 않으면 해당 물건을 가방에 할당한다.
 * 5. 모든 가방에 대해 연산을 마치면 총 가격을 출력한다.
 *
 * -----------------------------------
 * 1. 보석 300,000개가 모두 최대 1,000,000의 가격을 가질 수 있으므로 최대 300,000,000,000의 연산 결과가 나올 수 있어 long 타입을 사용해야 한다.
 *
 * @author 배용현
 *
 */
class BJ_1202_보석도둑_배용현 {

	static int N, K;
	static long answer = 0;
	static Integer[][] jewels;		// 0: 무게, 1: 가격
	static int[] allowWeights;		// 가방의 허용 무게
	static PriorityQueue<Integer> candidates;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		input();
		solution();
		System.out.println(answer);
	}

	private static void solution() {
		int idx = 0;		// 보석을 가리키는 인덱스
		for (int allowWeight : allowWeights) {		// 물건을 담기 까다로운(적은 무게만 담을 수 있는) 가방부터 접근
			while (idx < N) {		// 가지고 있는 보석을 전부 확인
				if (jewels[idx][0] > allowWeight) {
					break;
				}

				candidates.add(jewels[idx++][1]);		// 현재 가방에 넣을 수 있는 보석을 전부 큐에 추가. 자동으로 가격 내림차순으로 정렬됨.
			}

			if (!candidates.isEmpty()) {		// 가방에 넣을 수 있는 보석이 존재하면
				answer += candidates.poll();		// 정답에 더함
			}
		}
	}

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());			// 보석의 개수
		K = parseInt(st.nextToken());			// 가방의 개수

		jewels = new Integer[N][2];		// 보석의 정보를 저장할 배열
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			jewels[i][0] = parseInt(st.nextToken());		// 무게 정보
			jewels[i][1] = parseInt(st.nextToken());		// 가격 정보
		}
		Arrays.sort(jewels, new Comparator<Integer[]>() {		// 보석은 기본적으로 무게 오름차순로 정렬
			@Override
			public int compare(Integer[] o1, Integer[] o2) {
				if (o1[0] == o2[0]) {
					return o2[1] - o1[1];
				} else {
					return o1[0] - o2[0];
				}
			}
		});

		allowWeights = new int[K];		// 각 가방의 허용 무게
		for (int i = 0; i < K; i++) {
			allowWeights[i] = parseInt(br.readLine());
		}
		Arrays.sort(allowWeights);		// 가방도 무게 오름차순으로 정렬

		candidates = new PriorityQueue<>(Collections.reverseOrder());		// 가방에 넣을 수 있는 보석이 저장될 큐. 가격 내림차순으로 정렬
	}
}