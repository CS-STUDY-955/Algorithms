import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

/**
 * 종이조각
 * https://www.acmicpc.net/problem/14391
 *
 * 1.
 *
 * @author 배용현
 */
class BJ_14391_종이조각 {

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
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());


	}
}