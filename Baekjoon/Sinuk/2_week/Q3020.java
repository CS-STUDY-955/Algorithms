import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 2<= N <= 200,000 , 2<= H <= 500,000이므로 
 * 실행 횟수가 최대 10억이 되는 정렬이나 일반적인 이중for문 탐색으로는 해결할 수 없다
 * 누적합 알고리즘이 필요
 */

// 3020번: 개똥벌레
public class Q3020 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stringTokenizer = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(stringTokenizer.nextToken());
		int h = Integer.parseInt(stringTokenizer.nextToken());
		int[] stalagmite = new int[h + 1]; // 석순
		int[] stalactite = new int[h + 1]; // 종유석
		int[] prefixStalag = new int[h + 1]; // 석순의 누적합
		int[] prefixStalac = new int[h + 1]; // 종유석의 누적합
		for (int i = 0; i < n / 2; i++) {
			// 석순과 종유석 배열에 각각 입력을 저장
			// 인덱스를 길이로 하여, 각 길이를 가지는 석순과 종유석이 몇개인지 배열에 저장 
			stalagmite[Integer.parseInt(br.readLine())]++;
			stalactite[Integer.parseInt(br.readLine())]++;
		}

		// 역방향으로 누적합을 계산하기 위해 첫 인덱스인 h를 초기화
		prefixStalag[h] = stalagmite[h];
		prefixStalac[h] = stalactite[h];

		// 역방향으로 누적합을 계산하여 누적합 배열에 저장
		for (int j = h - 1; j >= 0; j--) {
			prefixStalag[j] = stalagmite[j] + prefixStalag[j + 1];
			prefixStalac[j] = stalactite[j] + prefixStalac[j + 1];
		}

		// 개똥벌래가 최소한으로 파괴해야 하는 수를 구하기
		// 현재의 최솟값을 가지는 갯수를 저장
		int minCount = 0;
		// 현재의 최솟값
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < h; i++) {
			// 파괴해야 하는 종유석과 석순의 합
			int broken = prefixStalag[i + 1] + prefixStalac[h - i];
			// 최솟값이면 카운트 초기화 하고 최솟값 변경
			if (broken < min) {
				min = broken;
				minCount = 1;
			// 최솟값이 중복이면 카운트 증가
			} else if (broken == min) {
				minCount++;
			}
		}

		System.out.printf("%d %d\n", min, minCount);
	}
}