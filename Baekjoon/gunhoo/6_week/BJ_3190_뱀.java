package Gold.Gold4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

/**
 * NxN 
 * 뱀은 머리를 늘리고, 사과가 있으면 꼬리그대로, 사과없으면 꼬리 짧아져
 * @author 박건후
 *
 */
public class BJ_3190_뱀 {
	static int N, K, L;
	static int[][] map;
	static boolean[][] body;
	
	static String[][] command;
	static int[][] direction = {{0,1}, {1,0}, {0,-1},{-1,0}};
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());
		map = new int[N+1][N+1];
		body = new boolean[N+1][N+1];
		for(int i =0  ; i < K;  i++) {
			st = new StringTokenizer(br.readLine());
			map[Integer.parseInt(st.nextToken())][Integer.parseInt(st.nextToken())] = 1;
		}
		L = Integer.parseInt(br.readLine());
		command = new String[L][2];
		for(int i = 0 ; i < L ; i++) {
			st = new StringTokenizer(br.readLine());
			command[i][0] = st.nextToken();
			command[i][1] = st.nextToken();
		}
		
		System.out.println(execute());
		
	}
	private static int execute() {
		int time = 0;
		int snakeX = 1, snakeY = 1; // 뱀의 머리
		int commandIdx = 0; // 이동명령 몇개 처리했는지 나타내는 idx
		int dirIdx = 0; // 방향 어디로 가야하는지
		body[snakeX][snakeY] = true; // 몸 위치를 방문처리
		ArrayDeque<Node> snake =  new ArrayDeque<>();
		snake.add(new Node(snakeX, snakeY));
		
		while(true) {
			time++;
			
			// 움직여야해
			snakeX += direction[dirIdx][0];
			snakeY += direction[dirIdx][1];
			if(snakeX < 1|| snakeY < 1 || snakeX > N || snakeY > N || // 벽에 부딪히거나
					body[snakeX][snakeY]) break;  // 몸에 부딪히면 게임 끝나
			
			body[snakeX][snakeY] = true; // 몸을 방문처리
			snake.add(new Node(snakeX, snakeY)); // 스택에 추가
			
			if(map[snakeX][snakeY]  == 1) { // 이동한 곳에 사과가 있으면
				map[snakeX][snakeY] = 0; // 먹어서 없애
			}else { // 사과가 없다면 꼬리 한칸 줄여야해
				Node tail = snake.pollFirst(); // 꼬리 없애
				body[tail.x][tail.y] = false; // 꼬리 해제
			}
			
			// x초가 끝나고 방향 회전
			if(commandIdx < L) {
				if(Integer.parseInt(command[commandIdx][0]) == time) { // 방향을 바꿔야하는 시간이면,
					if(command[commandIdx][1].equals("D")) { // 아래로 한칸
						dirIdx = (dirIdx+1)%4;
					}else { // L연산이니까 위로 한칸
						dirIdx--;
						if(dirIdx == -1) dirIdx = 3;
					}
					commandIdx++;
				}
			}
			
		}
		return time;
	}
	
	static class Node{
		int x, y;

		public Node(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
	}


}
