import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

// 백준 3190 : 뱀
public class BJ_3190_뱀 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		int k = Integer.parseInt(br.readLine());
		char[][] board = new char[n + 2][n + 2];
		
		// 패딩 초기화
		for (int i = 0; i < n + 2; i++) {
			board[0][i] = '#';
			board[i][0] = '#';
			board[n + 1][i] = '#';
			board[i][n + 1] = '#';
		}
		// 사과가 있는 곳에 A를 표시
		for (int i = 0; i < k; i++) {
			st = new StringTokenizer(br.readLine());
			board[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = 'A';
		}

		// Point에 회전 정보를 담아 ArrayList로 저장
		// x: 시간, y: D-> 1 L-> -1
		ArrayList<Point> turn = new ArrayList<>();
		int l = Integer.parseInt(br.readLine());
		for (int i = 0; i < l; i++) {
			st = new StringTokenizer(br.readLine());
			int time = Integer.parseInt(st.nextToken());
			char direc = st.nextToken().charAt(0);
			if (direc == 'D') {
				turn.add(new Point(time, -1));
			} else {
				turn.add(new Point(time, 1));
			}
		}

		// 상 좌 하 우
		int[] dx = { -1, 0, 1, 0 };
		int[] dy = { 0, -1, 0, 1 };
		// 뱀의 몸의 각각의 위치가 순서대로 담긴 덱과 초기화
		// 위와 동일하게 Point 클래스를 객체로 사용하지만, 담기는 정보는 다름
		ArrayDeque<Point> snake = new ArrayDeque<>();
		snake.addFirst(new Point(1, 1));
		// 덱만으로는 부딫쳤는지 확인하려면 전체를 순회해야 하므로 보드에도 표시해줌
		board[1][1] = 'S';
		// 지난 시간
		int count = 0;
		// 회전한 횟수
		int turnCount = 0;
		// 현재 진행중인 방향
		int direction = 3;
		while (true) {
			count++;
			// 먼저 늘이기만 하므로 poll대신 peek 사용
			Point head = snake.peek();
			int x = head.x + dx[direction];
			int y = head.y + dy[direction];
			// 벽이거나 뱀 몸통이면 종료
			if (board[x][y] == '#' || board[x][y] == 'S') {
				break;
			}
			// 사과가 있으면 사과 없애고 꼬리 그대로, 이동한 머리위치 저장
			if (board[x][y] == 'A') {
				board[x][y] = 'S';
				snake.addFirst(new Point(x, y));
			// 사과가 없으면 꼬리 1칸 지우고, 이동한 머리위치 저장
			} else {
				board[x][y] = 'S';
				snake.addFirst(new Point(x, y));
				Point tail = snake.pollLast();
				board[tail.x][tail.y] = 0;
			}

			// 다 끝난 뒤 방향 전환
			// 방향전환이 끝났다면 그냥 넘어가야 함
			if (turnCount < turn.size() && count == turn.get(turnCount).x) {
				direction = ((direction + turn.get(turnCount).y) % 4 + 4) % 4;
				turnCount++;
			}
		}
		System.out.println(count);
	}

}
