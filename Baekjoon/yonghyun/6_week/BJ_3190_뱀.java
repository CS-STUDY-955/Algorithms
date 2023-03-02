import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 뱀
 * https://www.acmicpc.net/problem/3190
 *
 * 1. 뱀은 머리한칸, 꼬리한칸 움직이므로 Deque로 구현한다.
 * 2. 방향 회전 명령은 회전하는 시간이 따로 존재하므로 Queue로 저장한다.
 * 3. 사과의 유무 정보가 저장된 맵은 boolean 2차원 배열로 저장한다.
 * 4. 뱀의 머리를 움직여놓고, 사과가 존재하지 않으면 꼬리를 한칸 뗀다.
 * 5. 뱀의 머리가 벽에 부딪히거나 꼬리에 부딪히면 그때의 시간을 출력한다.
 * 
 * @author 배용현
 *
 */
public class BJ_3190_뱀 {
	static class Turn {		// 이동 명령 저장
		int sec;
		boolean turnR;
		public Turn(int sec, boolean turnR) {
			this.sec = sec;
			this.turnR = turnR;
		}
	}

	static int N, K, L;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static boolean[][] map;
	static int[] dx = {-1, 0, 1, 0};		// 0: left, 1: up, 2: right, 3: down
	static int[] dy = {0, -1, 0, 1};
	
	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		map = new boolean[N][N];		// false: 빈칸, true: 사과
		K = Integer.parseInt(br.readLine());		// 사과의 개수
		for(int i=0; i<K; i++) {
			st = new StringTokenizer(br.readLine());
			map[Integer.parseInt(st.nextToken())-1][Integer.parseInt(st.nextToken())-1] = true;
		}

		L = Integer.parseInt(br.readLine());		// 뱀의 움직임 정보
		Queue<Turn> turns = new ArrayDeque<>();		// 움직임이 언제 일어나는지 정보가 같이 있으므로 배열 대신 큐로 저장
		for(int i=0; i<L; i++) {
			st = new StringTokenizer(br.readLine());
			turns.add(new Turn(Integer.parseInt(st.nextToken()), st.nextToken().equals("D")));
		}

		int answer = 0;		// 경과한 시간
		int direction = 2;		// 처음 머리는 오른쪽 방향
		Turn cur = turns.poll();
		int nextTurnSec = cur.sec;		// 다음에 회전할 시간
		boolean nextTurnR = cur.turnR;		// 다음에 회전할 방향
		Deque<int[]> snake = new LinkedList<>();		// 뱀이 존재하는 좌표 전부 저장
		snake.add(new int[]{0, 0});		// 시작은 (0, 0)
		while(true) {
			int[] head = snake.peekFirst();
			int nextX = head[0] + dx[direction];		// 다음 x좌표
			int nextY = head[1] + dy[direction];		// 다음 y좌표
			answer++;		// 머리가 움직였으므로 총 소요시간 +1초
			if (isEnd(snake, nextX, nextY))		// 부딪히면 게임 종료
				break;

			if(map[nextY][nextX])		// 움직인곳에 사과가 있으면
				map[nextY][nextX] = false;		// 사과가 사라지고
			else		// 사과가 없으면
				snake.pollLast();		// 꼬리부분이 움직임
			snake.addFirst(new int[] {nextX, nextY});		// 머리는 항상 움직임

			if(nextTurnSec==answer) {		// 회전할 시간이면
				direction += nextTurnR ? 1 : -1;
				if(direction == -1)		// 방향은 0~3이므로 보정
					direction = 3;
				else if(direction == 4)		// 방향은 0~3이므로 보정
					direction = 0;

				if(!turns.isEmpty()) {		// 다음 회전 명령이 남아있으면
					cur = turns.poll();		// 해당 회전 정보로 업데이트
					nextTurnSec = cur.sec;
					nextTurnR = cur.turnR;
				}
			}
		}

		System.out.print(answer);
	}

	private static boolean isEnd(Deque<int[]> snake, int nx, int ny) {		// 게임이 끝나는지 확인하는 메서드
		if(nx<0 || ny<0 || nx>=map.length || ny>=map.length || eatTail(snake, nx, ny))
			return true;		// 머리가 벽에 부딪히거나 꼬리를 먹으면 게임 종료

		return false;		// 안전하면 게임 속행
	}

	static boolean eatTail(Deque<int[]> snake, int nx, int ny) {		// 머리가 이동해서 꼬리를 먹는지 확인하는 메서드
		for(int[] p: snake) {		// 뱀의 모든 부분을 확인해서
			if(p[0]==nx && p[1]==ny)		// 하나라도 머리와 부딪히면
				return true;
		}

		return false;
	}
}