import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 네트워크 연결
 * https://www.acmicpc.net/problem/1922
 * 
 * 1. 연결선의 수가 최대 100,000이므로 E^2의 시간복잡도를 가져서는 안된다.
 * 2. 간선을 가중치가 낮은 순으로 정렬하고, 양 옆을 방문하지 않았으면 연결한다.
 * 3. 
 *
 * @author 배용현
 *
 */
class BJ_1922_네트워크연결 {

	static int N, M, answer = 0;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		input();
		solution();
		System.out.println(answer);
	}

	private static void solution() {

	}

	private static void input() throws IOException {
		N = parseInt(br.readLine());
		M = parseInt(br.readLine());


	}
}