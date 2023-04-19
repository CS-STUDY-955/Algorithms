import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 온풍기 안녕!
 * https://www.acmicpc.net/problem/23289
 *
 * 1. (x, y)에서 x가 행, y가 열을 의미함에 주의한다.
 * 2.
 *
 * @author 배용현
 *
 */
class BJ_23289_온풍기안녕 {

	static int R, C, K;
	static int[][] map;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		input();
		solution();
		print();
	}

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		R = parseInt(st.nextToken());
		C = parseInt(st.nextToken());
		K = parseInt(st.nextToken());

		map = new int[R][C];
	}

	private static void solution() {

	}

	private static void diffuse() {		// 미세먼지 확산되는 메서드
		int[][] newMap = new int[R][C];

		for(int i=0; i<R; i++) {
			for(int j=0; j<C; j++) {
				if(map[i][j]==0)		// 맵은
					continue;

				int sum = 0;		// 확산돼서 원래 칸에서 뺄 미세먼지 수
				int scatter = map[i][j]/5;		// 확산은 5로 나눈 만큼 일어남
				for(int k=0; k<4; k++) {		// 4방향 순회
					int nx = j + dx[k];
					int ny = i + dy[k];
					if(ny<0 || nx<0 || ny>R-1 || nx>C-1 || (ny==cleanerY && nx==0) || (ny==cleanerY-1 && nx==0))
						continue;		// 못 퍼지는 곳이면 패스

					sum += scatter;		// 퍼지는 곳이면 뺄 합에 더해주고
					newMap[ny][nx] += scatter;		// 퍼뜨림
				}
				newMap[i][j] += map[i][j] - sum;		// 현재 위치에서 퍼진 미세먼지 빼서 갱신
			}
		}

		map = newMap;		// 헌맵줄게 새맵다오
	}

	private static void print() {
		ArrayDeque<Integer> stack = new ArrayDeque<>();		// ArrayDeque로 스택 구현
		int cur = dest;		// dest에서 시작하여
		while (cur!=src) {		// src까지 트래킹하며 스택에 삽입
			stack.addFirst(cur);
			cur = prev[cur];
		}
		stack.addFirst(src);

		sb.append(dp[dest]).append('\n');		// 도착지까지의 최소 비용
		sb.append(stack.size()).append('\n');		// 경로의 길이
		Iterator<Integer> it = stack.iterator();		// iterator를 이용하여 경로 출력
		while (it.hasNext()) {
			sb.append(it.next()).append(' ');
		}

		System.out.print(sb);
	}

}