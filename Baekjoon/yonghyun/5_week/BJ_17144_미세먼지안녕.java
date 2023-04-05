
import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 미세먼지 안녕!
 * https://www.acmicpc.net/problem/17144
 *
 * 1. 확산이 일어난다.
 *  - 먼저 새로운 배열을 만들어 현재 확산에서 변화하는 값을 기록한다. (순회는 기존 배열에서 진행한다.)
 *  - 미세먼지가 퍼질 수 있는 구간이 몇개인지 구한다.
 *  - 퍼지는 양*구간의 수 만큼 원래 값에서 빼준 것을 해당 위치에 더한다.
 *  - 퍼진 곳에는 퍼진 양만큼 더한다.
 *  - 확산이 끝나면 기존 변수가 새로운 배열을 가리키도록 한다.
 * 2. 바람으로 먼지가 움직인다.
 *  - 먼지가 움직인다.
 *  - 공기청정기의 위쪽과 아래쪽의 공기 순환이 다르므로 따로 구현한다. (dy만 음수로 바꿔서 구현하면 될지도??)
 *  - 공기청정기의 위치에 존재하는 미세먼지는 0으로 바꾼다.
 *  - 공기청정기의 값과 위치는 바뀌지 않으므로 따로 관리하면 좋을 것 같다.
 * 3. 맵의 미세먼지 양을 출력한다.
 * 
 * @author 배용현
 *
 */
public class BJ_17144_미세먼지안녕 {

	static int R, C, cleanerY;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int[][] map;
	static int[] dx = {1, 0, -1, 0};
	static int[] dy = {0, -1, 0, 1};
	
	public static void main(String[] args) throws Exception {
		st = new StringTokenizer(br.readLine());
		R = parseInt(st.nextToken());
		C = parseInt(st.nextToken());
		int T = parseInt(st.nextToken());
		int answer = 0;		// 맵에 존재하는 미세먼지 개수를 저장

		map = new int[R][C];
		for(int i=0; i<R; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<C; j++) {
				map[i][j] = parseInt(st.nextToken());
				if(map[i][j]==-1) {		// 공청기 위치는 항상 1번열이며 위아래로 붙어있음
					cleanerY = i;		// 따라서 마지막 y인덱스만 알아도 공청기 위치 정보를 모두 알 수 있음
					map[i][j] = 0;		// 공청기 위치는 따로 관리하고 평범한 맵의 필드로 저장
				} else {
					answer += map[i][j];
				}
			}
		}

		for(int i=0; i<T; i++) {		// 매초마다 확산, 이동 반복
			diffuse();
			answer -= move();		// 공청기가 먹은 미세먼지 개수를 받아와서 미세먼지 수 갱신
		}
		
		System.out.println(answer);
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
	
	private static int move() {		// 공기 순환하는 메서드
		return overCycle() + underCycle();		// 위쪽에서 빨아들인 미세먼지와 아래쪽에서 빨아들인 미세먼지의 합
	}

	private static int underCycle() {		// 아래쪽 공기 순환
		int clean = map[cleanerY+1][0];		// 사라지는 미세먼지

		for(int i=cleanerY+1; i<R-1; i++)		// 공기청정기 아래쪽부터 ↑ 방향으로 땡기기
			map[i][0] = map[i+1][0];
		for(int i=0; i<C-1; i++)		// ← 방향으로 땡기기
			map[R-1][i] = map[R-1][i+1];
		for(int i=R-1; i>cleanerY; i--)		// ↓ 방향으로 땡기기
			map[i][C-1] = map[i-1][C-1];
		for(int i=C-1; i>0; i--)		// → 방향으로 땡기기
			map[cleanerY][i] = map[cleanerY][i-1];
		
		return clean;
	}

	private static int overCycle() {		// 위쪽 공기 순환
		int clean = map[cleanerY-2][0];		// 사라지는 미세먼지
		
		for(int i=cleanerY-2; i>0; i--)		// 공기청정기 위쪽부터 ↓ 방향으로 땡기기
			map[i][0] = map[i-1][0];
		for(int i=0; i<C-1; i++)		// ← 방향으로 땡기기
			map[0][i] = map[0][i+1];
		for(int i=0; i<=cleanerY-2; i++)		// ↑ 방향으로 땡기기
			map[i][C-1] = map[i+1][C-1];
		for(int i=C-1; i>0; i--)		// → 방향으로 땡기기
			map[cleanerY-1][i] = map[cleanerY-1][i-1];
		
		return clean;
	}
}