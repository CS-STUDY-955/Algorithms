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
 *
 * @author 배용현
 *
 */
class BJ_1102_발전소 {

	static int N, M, K;
	static long[][] dp;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		input();
		solution();
		print();
	}

	private static void input() throws IOException {

	}

	private static void solution() {

	}

	private static void print() {

	}

}