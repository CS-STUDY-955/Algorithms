package Gold;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * BJ 15685 드래곤 커브
 * 100x100 배열 만들고, 드래곤 커브 정보 받아서 입력
 * - 드래곤 커브는 어케 입력? 
 * 		- 재귀로 불러서 이어 붙이기?
 * 	0세대 : 0 
 *  1세대 : 01 // 0세대 뒤에서부터 뽑고 1증가해서 뒤에 넣어줘
 *  2세대 : 0121 // 1세데 뒤에서부터 뽑고 1증가해서 뒤에 넣어줘
 *  3세대 : 01212321 // 2세대 뒤에서부터 뽑고 1증가해서 뒤에 넣어줘
 *  ...
 * 정답 - 0,0부터 99,99까지 보면서 [i][j],[i][j+1],[i+1][j],[i+1][j+1] 모두 1이면 정답++
 * @author Gunhoo
 *
 */
public class BJ_15685_드래곤커브 {
	static int[][] map = new int[101][101]; // map정보
	// 주의 !!! : x는 오른쪽으로 증가, y 는 아래쪽으로 증가	
	static int[] dx = {1,0,-1,0}; // 우상좌하(0123)
	static int[] dy = {0,-1,0,1};
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int[] dragonCurve = new int[4];
		for(int i = 0 ; i<n ; i++) {
			st = new StringTokenizer(br.readLine());
			dragonCurve[0]= Integer.parseInt(st.nextToken());
			dragonCurve[1]= Integer.parseInt(st.nextToken());
			dragonCurve[2]= Integer.parseInt(st.nextToken());
			dragonCurve[3]= Integer.parseInt(st.nextToken());
			makeCurve(dragonCurve);
		}
		
		System.out.println(count()); // 정답 출력
	}
	
	private static void makeCurve(int[] dragonCurve) { // 입력에 대해
		ArrayDeque<Integer> stack = new ArrayDeque<>();
		stack.add(dragonCurve[2]); // 방향을 넣어줘
		 
		/*  스택에 있는거를 뒤에서부터 꺼내서 (+1)%4 해주고 기존스택 뒤에 더해줘 */
		for(int i = 0; i< dragonCurve[3]; i++) { // 세대수까지 반복
			ArrayDeque<Integer> newStack = new ArrayDeque<>(); // 새로운 세대
			while(!stack.isEmpty()) { // 기존 세대 다 빼서
				int tmp = stack.removeLast(); // 뒤에서부터 뽑아서
				newStack.offerLast((tmp+1)%4); // 새로운 세대 뒤에 넣어주고
				newStack.offerFirst(tmp); // 기존세대 정보 저장 위해 앞에서부터 넣어줘
			}
			stack = newStack; // stack 업데이트
		}
		int size = stack.size(); // 그리는 횟수
		int nx = dragonCurve[0]; // x좌표
		int ny = dragonCurve[1]; // y좌표
		map[ny][nx] = 1;
		for(int i = 0 ; i< size ; i++) { 
			int dir = stack.removeFirst(); // 앞에서부터 그려야지
			nx += dx[dir];
			ny += dy[dir];
			map[ny][nx] = 1;
		}
	}
	
	private static int count() { // dragon curve 세주는 함수
		int sum = 0;
		for(int i = 0; i< 100; i++) { // 모든 곳 탐색하면서
			for(int j = 0; j< 100 ; j++) { // 크기가 1인 정사각형에 모든 값이 1이면 정답 증가
				if(map[i][j] == 1&& map[i][j+1] == 1&& map[i+1][j] == 1 && map[i+1][j+1] == 1) {
					sum++;
				}
			}
		}
		return sum;
	}

}
