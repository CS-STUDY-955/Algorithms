import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 새로운 게임 2
 * https://www.acmicpc.net/problem/17837
 *
 * 1. 같은 칸의 말을 동시에 이동시키기 위해서는 같은 칸에 어떤 말이 있는지 알기 쉽게 저장해 놓는 것이 좋다.
 *  - 말의 정보(위치, 방향)를 Deque에 저장해놓으면 될 것 같다.
 * 2. 다음 순서에 이동할 말을 알기 위해서는 말을 순서대로 접근하기 위한 자료구조가 있는 것이 좋다.
 *  - 말의 순서가 변하는 경우는 없으므로 배열로 저장해두고 하나씩 접근하면 될 것 같다.
 * 3. 이동하는 메서드가 필요하다.
 *  - 이동할 칸의 색깔에 따라 분기해야한다.
 *  - 흰색인 경우, 자신의 차례까지 pollFirst하고 이동한 칸의 Deque에 addFirst한다.
 *  - 빨간색인 경우, 흰색을 수행하되 poll후 reverse하고 add한다.
 *  - 체스판을 벗어나거나 파란색인 경우, 방향을 바꾸고 이동한다. 반대쪽도 이동할 수 없는 경우엔 방항만 바꾼다.
 * 4. 말이 4개 이상 겹친 경우 게임이 종료된다.
 *  - 이동이 끝난 후 Deque의 크기를 확인하여 4이상이면 종료한다.
 * 5. 말과 방향의 인덱스, 위치 모두 입력시 -1하여 적용한다.
 *
 * @author 배용현
 */
class BJ_17837_새로운게임2 {

	static int N, K, answer = 0;
	static int[][] location;		// i번 말의 x, y좌표
	static int[][] map;		// 맵의 정보 (0:흰색, 1:빨간색, 2:파란색)
	static Deque<Integer>[][] horse;		// 해당 위치에 말이 쌓인 구조
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int[] dx = {1, -1, 0, 0};		// →, ←, ↑, ↓
	static int[] dy = {0, 0, -1, 1};

	public static void main(String[] args) throws IOException {
		input();
		solution();
		System.out.println(answer);
	}

	private static void solution() {
		while (true) {
			move();		// 말 이동
			answer++;		// 시간 증가
			if(check())		// 게임 종료 확인
				break;
		}
	}

	private static void move() {
		int curIdx = answer % K;
	}

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		horse = new Deque[N][N];
		location = new int[K][2];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				horse[i][j] = new ArrayDeque<>();
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			location[i][0] = Integer.parseInt(st.nextToken()) - 1;		// y
			location[i][1] = Integer.parseInt(st.nextToken()) - 1;		// x
			location[i][2] = Integer.parseInt(st.nextToken()) - 1;		// d
			horse[location[i][0]][location[i][1]].add(i);
		}
	}
}