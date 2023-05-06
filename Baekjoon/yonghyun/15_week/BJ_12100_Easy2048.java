import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 2048 (Easy)
 * https://www.acmicpc.net/problem/12100
 *
 * 1. 필요한 변수
 * 	- 맵을 저장할 int형 2차원 배열
 * 	- 이동 방향을 저장할 크기 5의 int형 1차원 배열
 *
 * 2. 로직
 *  - 이동할 방향을 중복순열로 5개 고른다.
 *  - 순서대로 이동시키며 블럭을 움직인다.
 *  - 맵 전체를 확인하여 가장 큰 수의 블럭을 구한다.
 *
 * 3. 주의할 점
 * - 미는 쪽 블럭부터 큐에 삽입하여 연산하면 쉽게 구할 수 있다.
 *
 * @author 배용현
 *
 */
class BJ_12100_Easy2048 {

	static int N, answer;
	static int[] direction;
	static int[][] map;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {
		input();
		dfs(0, map);
		System.out.println(answer);
	}

	private static void input() throws IOException {
		N = parseInt(br.readLine());
		direction = new int[5];
		map = new int[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = parseInt(st.nextToken());
			}
		}

		answer = 0;
	}

	private static void dfs(int depth, int[][] map) {
		if(depth==5) {		// 최대 5번 움직임
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					answer = Math.max(answer, map[i][j]);
				}
			}

			return;
		}

		// left
		int[][] newMap = new int[N][N];		// 밀어서 새롭게 생성될 맵
		for (int i = 0; i < N; i++) {
			Queue<Integer> q = new LinkedList<>();
			int head = 0, index = 0;		// 저장된 가장 최근 숫자, 저장될 배열의 인덱스
			for (int j = 0; j < N; j++) {		// 왼쪽 원소부터 큐에 삽입
				q.add(map[i][j]);
				newMap[i][j] = 0;
			}

			while (!q.isEmpty()) {
				int cur = q.poll();
				if (cur == 0) {		// 0이면 패스
					continue;
				} else if (head == 0) {		// 최근 숫자가 0이고 현재 숫자는 0이 아니면 head에 현재 숫자 저장
					head = cur;
				} else if (cur == head) {		// 최근 숫자와 현재 숫자가 같으면 *2를 index에 삽입
					newMap[i][index++] = head * 2;
					head = 0;		// 두 번 합쳐지지 않도록 head는 0으로 초기화
				} else {		// 이외의 경우, index에 최근 숫자를 삽입
					newMap[i][index++] = head;
					head = cur;		// head는 현재 숫자로 업데이트
				}
			}

			if (head != 0)		// 마지막에 head까지 삽입
				newMap[i][index] = head;
		}
		dfs(depth+1, newMap);		// 다음 이동 수행

		// right
		newMap = new int[N][N];
		for(int i=0; i<N; i++) {
			Queue<Integer> q = new LinkedList<>();
			int head = 0, index = N-1;
			for (int j = N - 1; j >= 0; j--) {
				q.add(map[i][j]);
				newMap[i][j] = 0;
			}

			while(!q.isEmpty()) {
				int cur = q.poll();
				if(cur==0) { continue; }
				else if(head==0) {
					head = cur;
				}
				else if(cur==head) {
					newMap[i][index--] = head * 2;
					head = 0;
				}
				else {
					newMap[i][index--] = head;
					head = cur;
				}
			}

			if(head!=0)
				newMap[i][index] = head;
		}
		dfs(depth+1, newMap);

		// up
		newMap = new int[N][N];
		for (int i = 0; i < N; i++) {
			Queue<Integer> q = new LinkedList<>();
			int head = 0, index = 0;
			for (int j = 0; j < N; j++) {
				q.add(map[j][i]);
				newMap[j][i] = 0;
			}

			while (!q.isEmpty()) {
				int cur = q.poll();
				if (cur == 0) {
					continue;
				} else if (head == 0) {
					head = cur;
				} else if (cur == head) {
					newMap[index++][i] = head * 2;
					head = 0;
				} else {
					newMap[index++][i] = head;
					head = cur;
				}
			}

			if (head != 0)
				newMap[index][i] = head;
		}
		dfs(depth+1, newMap);

		// down
		newMap = new int[N][N];
		for (int i = 0; i < N; i++) {
			Queue<Integer> q = new LinkedList<>();
			int head = 0, index = N - 1;
			for (int j = N - 1; j >= 0; j--) {
				q.add(map[j][i]);
				newMap[j][i] = 0;
			}

			while (!q.isEmpty()) {
				int cur = q.poll();
				if (cur == 0) {
					continue;
				} else if (head == 0) {
					head = cur;
				} else if (cur == head) {
					newMap[index--][i] = head * 2;
					head = 0;
				} else {
					newMap[index--][i] = head;
					head = cur;
				}
			}

			if (head != 0)
				newMap[index][i] = head;
		}
		dfs(depth+1, newMap);
	}
}