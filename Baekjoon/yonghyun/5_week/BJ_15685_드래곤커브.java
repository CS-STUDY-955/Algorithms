
import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 드래곤 커브
 * https://www.acmicpc.net/problem/15685
 *
 * 1. 드래곤 커브는 이전 세대를 이어붙여 만들 수 있고, 이는 재귀나 반복으로 구현할 수 있다.
 *  - 이전 방향을 그대로 계승하고, 뒤집어서 1씩 더해준 방향을 붙이면 현재 드래곤 커브의 방향이 된다.
 *  - 이동은 총 2^G만큼 존재한다. (G는 세대)
 *  - 각 세대를 반복하면서 2^(g-1)부터 2^g-1까지 새로 계산해주면 된다. (g는 현재 계산중인 세대)
 *  - 계산할 때 창고하는 인덱스는 각 인덱스를 2^g-1에서 빼주면 된다.
 * 2. 면이 아닌 점을 기준으로 하면서 0번 인덱스를 사용하지 않고 있으므로 N+2를 한 변의 크기로 하는 2차원 배열을 생성한다.
 * 3. 드래곤 커브가 지나간 부분을 체크하고, 2x2인 사각형이 모든 드래곤 커브에 찍혀있다면 개수를 세어 출력한다.
 * 
 * @author 배용현
 *
 */
public class BJ_15685_드래곤커브 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static boolean[][] map;
	static int[] dx = {1, 0, -1, 0};
	static int[] dy = {0, -1, 0, 1};
	
	public static void main(String[] args) throws Exception {
		int N = parseInt(br.readLine());
		map = new boolean[102][102];		// 지나갔는지 체크만 하면 되므로 boolean 타입
		for(int i=0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int x = parseInt(st.nextToken());
			int y = parseInt(st.nextToken());
			int d = parseInt(st.nextToken());
			int g = parseInt(st.nextToken());
			
			fireDragonCurve(x, y, d, g);		// 현재 입력된 드래곤 커브 수행
		}
		
		System.out.println(getNumOfSurrounded());
	}

	private static int getNumOfSurrounded() {		// 드래곤 커브가 지나간 길로 둘러쌓인 사각형의 개수 리턴
		int num = 0;		// 개수 세기 위한 변수
		
		for(int i=0; i<map.length-1; i++) {		// 시작 인덱스 기준 2x2 사각형 검사
			for(int j=0; j<map.length-1; j++) {		// 2x2 사각형을 검사하기 때문에 마지막 인덱스는 접근 X
				if(map[i][j] && map[i+1][j] && map[i][j+1] && map[i+1][j+1])		// 사각형 모두 1이면 개수+1
					num++;
			}
		}
		
		return num;
	}

	private static void fireDragonCurve(int x, int y, int d, int g) {		// 드래곤 커브를 수행하여 맵에 찍는 메서드
		int[] move = new int[(int)Math.pow(2, g)];		// 이동은 총 2^G만큼 이루어짐
		move[0] = d;		// 0세대 드래곤 커브 초기화
		int numOfCase = 1;		// 매번 제곱 계산하면 효율 안좋으니까 따로 관리
		
		for(int i=1; i<=g; i++) {		// 나머지 세대 계산
			numOfCase *= 2;		// 2를 곱하면서 경우의 수 계산
			for(int j=numOfCase/2; j<numOfCase; j++)		// 새로 추가되는 이동방향 계산
				move[j] = (move[numOfCase-1-j] + 1) % 4;		// 중간 기준으로 뒤집은 인덱스의 값에서 1을 더하되 4를 넘어가면 0으로 변환
		}
		
		map[y][x] = true;		// 초기 위치 세팅
		for(int i=0; i<move.length; i++) {
			y += dy[move[i]];		// y 위치 갱신
			x += dx[move[i]];		// x 위치 갱신
			map[y][x] = true;		// 이동한 위치 표시
		}
	}
}