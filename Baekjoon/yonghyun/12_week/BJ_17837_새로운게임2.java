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
 *  - 흰색인 경우, 자신의 차례까지 pollFirst하여 스택에 저장한 후 이동한 칸의 Deque에 하나씩 addFirst한다.
 *  - 빨간색인 경우, 흰색처럼 수행하되 스택이 아닌 큐를 이용한다.
 *  - 체스판을 벗어나거나 파란색인 경우, 방향을 바꾸고 이동한다. 반대쪽도 이동할 수 없는 경우엔 방항만 바꾼다.
 *  - 체스판 모든 가장자리를 파란색 칸으로 패딩처리하면 좀 더 쉽게 구현할 수 있다.
 * 4. 말이 4개 이상 겹치거나 턴이 1000을 초과한 경우 게임이 종료된다.
 *  - 이동이 끝난 후 해당 칸의 Deque의 크기를 확인하여 4이상이면 종료한다.
 * 5. 말의 위치는 맵에 패딩이 적용되었으므로 그대로 두고, 방향과 인덱스는 입력받을때 -1하여 적용한다.
 *
 * @author 배용현
 *
 */
class BJ_17837_새로운게임2 {

	static int N, K, answer = 0;
	static int[][] location;		// i번 말의 x, y좌표, 방향
	static int[][] map;		// 맵의 정보 (0:흰색, 1:빨간색, 2:파란색)
	static Deque<Integer>[][] horse;		// 해당 위치에 말이 쌓인 구조
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int[] dx = {1, -1, 0, 0};		// →, ←, ↑, ↓
	static int[] dy = {0, 0, -1, 1};

	public static void main(String[] args) throws IOException {
		input();
		solution();
		System.out.println(answer<=1000 ? answer : -1);
	}

	private static void solution() {
		while (true) {
			boolean end = move();		// 말 이동
			answer++;		// 시간 증가
            if (end || answer>1000) {       // 게임이 종료되었다면 탈출
                break;
            }
		}
	}

    private static boolean gameEnd(int x, int y) {      // 말이 4개 이상 겹쳐서 게임이 끝났는지 확인하는 메서드
        return horse[y][x].size()>=4;
    }

    private static boolean move() {     // 한 턴에 말을 모두 이동시키는 메서드
        boolean end;        // 게임이 종료되었는지 확인하는 변수

        for (int curIdx = 0; curIdx < K; curIdx++) {        // curIdx: 현재 움직일 말의 인덱스
            int curX = location[curIdx][0];
            int curY = location[curIdx][1];
            int curD = location[curIdx][2];

            int nextX = curX + dx[curD];
            int nextY = curY + dy[curD];
            int color = map[nextY][nextX];

            switch (color) {        // 다음 칸의 색깔에 따라 다른 메서드 호출
                case 0: end = moveToWhite(curX, curY, curIdx, nextX, nextY); break;
                case 1: end = moveToRed(curX, curY, curIdx, nextX, nextY); break;
                default: end = moveToBlue(curX, curY, curD, curIdx);
            }

            if (end) {       // 게임 종료시 true 리턴
                return true;
            }
        }

        return false;       // 게임이 종료되지 않았다면 false 리턴
	}

    private static boolean moveToWhite(int curX, int curY, int curIdx, int nextX, int nextY) {      // 다음 칸이 하얀색일때 말의 움직임을 처리하는 메서드
        Deque<Integer> stack = new ArrayDeque<>();      // 순서가 유지되어야하므로 스택을 사용
        while (true) {          // 맨 위부터 움직이는 말까지 전부 스택에 쌓음
            stack.addFirst(horse[curY][curX].pollFirst());
            if (stack.peekFirst() == curIdx) {
                break;
            }
        }

        while (!stack.isEmpty()) {      // 스택에 쌓은 말들 하나씩 다음 칸으로 옮김
            int moveIdx = stack.pollFirst();
            horse[nextY][nextX].addFirst(moveIdx);      // horse 갱신
            location[moveIdx][0] = nextX;       // location 갱신
            location[moveIdx][1] = nextY;
        }


        return gameEnd(nextX, nextY);
    }

    private static boolean moveToRed(int curX, int curY, int curIdx, int nextX, int nextY) {      // 다음 칸이 빨간색일때 말의 움직임을 처리하는 메서드
        Deque<Integer> queue = new ArrayDeque<>();      // 순서가 바뀌어야하므로 큐를 사용
        while (true) {          // 맨 위부터 움직이는 말까지 전부 큐에 넣음
            queue.addFirst(horse[curY][curX].pollFirst());
            if (queue.peekFirst() == curIdx) {
                break;
            }
        }

        while (!queue.isEmpty()) {      // 큐에 넣은 말들 하나씩 다음 칸으로 옮김
            int moveIdx = queue.pollLast();
            horse[nextY][nextX].addFirst(moveIdx);      // horse 갱신
            location[moveIdx][0] = nextX;       // location 갱신
            location[moveIdx][1] = nextY;
        }

        return gameEnd(nextX, nextY);
    }

    private static boolean moveToBlue(int curX, int curY, int curD, int curIdx) {      // 다음 칸이 파란색일때 말의 움직임을 처리하는 메서드
        int nextD = getOppositeDirection(curD);     // 이동 방향을 반대로 바꾸고
        location[curIdx][2] = nextD;
        int nextX = curX + dx[nextD];
        int nextY = curY + dy[nextD];

        if (map[nextY][nextX] == 0) {       // 반대 방향의 칸이 하얀색인 경우
            return moveToWhite(curX, curY, curIdx, nextX, nextY);         // 반대로 움직임
        } else if (map[nextY][nextX] == 1) {       // 반대 방향의 칸이 빨간색인 경우
            return moveToRed(curX, curY, curIdx, nextX, nextY);         // 반대로 움직임
        } else {        // 반대로도 못 움직이는 경우
            return false;
        }

    }

    private static int getOppositeDirection(int d) {        // 반대 방향을 리턴하는 메서드
        switch (d) {
            case 0: return 1;
            case 1: return 0;
            case 2: return 3;
            default: return 2;
        }
    }

    private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		horse = new Deque[N+2][N+2];        // 말이 쌓인 상태를 저장
        map = new int[N+2][N+2];        // 맵의 색깔을 저장
		location = new int[K][3];       // 각 말의 위치를 저장

		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				horse[i][j+1] = new ArrayDeque<>();
				map[i][j+1] = Integer.parseInt(st.nextToken());
			}
		}

        for (int i = 0; i <= N+1; i++) {        // 가장자리 파란색으로 패딩
            map[i][0] = map[0][i] = map[i][N+1] = map[N+1][i] = 2;
        }

		for (int i = 0; i < K; i++) {       // 말의 위치 초기화
			st = new StringTokenizer(br.readLine());
			location[i][1] = Integer.parseInt(st.nextToken());		// y
			location[i][0] = Integer.parseInt(st.nextToken());		// x
			location[i][2] = Integer.parseInt(st.nextToken()) - 1;		// d
			horse[location[i][1]][location[i][0]].add(i);
		}
	}
}