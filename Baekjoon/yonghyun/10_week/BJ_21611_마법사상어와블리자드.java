import java.util.*;
import java.io.*;

import static java.lang.Integer.parseInt;

/**
 * 마법사 상어와 블리자드
 * https://www.acmicpc.net/problem/21611
 *
 * 1. 얼음을 떨어뜨려 일자의 구슬을 파괴하는 메서드가 필요하다.
 * 2. 뒤의 구슬을 땡겨서 구슬이 파괴되어 생긴 빈칸을 채우는 메서드가 필요하다.
 * 3. 땡긴 이후 구슬이 4개 이상일 때 파괴하는 메서드가 필요하다.
 * 4. 구슬이 배치된 상태에 따라 변하는 메서드가 필요하다.
 * 5. 1~4를 M번 반복한 뒤 폭발한 구슬 개수를 가중치를 곱해 더한 값을 출력한다.
 *
 * @author 배용현
 */
public class BJ_21611_마법사상어와블리자드 {
	static HashMap<Integer, int[]> indexes = new HashMap<>();		// 각 칸이 몇 번째 칸인지
	static int N, M, center;
	static int bead1 = 0, bead2 = 0, bead3 = 0;		// 구슬이 파괴된 개수
	static int[][] map, skills;
	static int[] dx = { 0, 0, -1, 1 };	// up, down, left, right
	static int[] dy = { -1, 1, 0, 0 };
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	public static void main(String[] args) throws Exception {
		input();
		solution();
		System.out.print(bead1+bead2*2+bead3*3);
	}

	private static void solution() {
		for(int[] skill: skills) {		// 모든 스킬에 대해 수행
			blizzard(skill[0], skill[1]);		// 블리자드 수행
			pull();		// 구슬 땡김
			while(explosion())		// 겹친 애들 터트리고 무언가 터졌으면
				pull();		// 또 땡김
			change();		// 구슬이 변화됨
		}
	}

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());
		M = parseInt(st.nextToken());
		center = N / 2;

		map = new int[N][N];
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = parseInt(st.nextToken());
			}
		}
		map[center][center] = -1;		// 센터는 상어가 위치
		setIndex();		// 구슬에 인덱스 부여

		skills = new int[M][2];		// 0: 방향, 1: 범위
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			skills[i][0] = parseInt(st.nextToken());
			skills[i][1] = parseInt(st.nextToken());
		}
	}

	private static void setIndex() {		// 각 구슬에 인덱스를 부여
		int direction = 1;		// 현재 방향
		int x = center - 1;		// 현재 열 위치
		int y = center;			// 현재 행 위치
		int turnUpX = center + 1;		// 위쪽으로 방향을 바꾸는 X값
		int turnDownX = center - 1;		// 아래쪽으로 방향을 바꾸는 X값
		int turnLeftY = center - 1;		// 왼쪽으로 방향을 바꾸는 Y값
		int turnRightY = center + 1;		// 오른쪽으로 방향을 바꾸는 Y값
		int idx = 1;		// 현재 구슬의 인덱스
		
		while(idx<N*N) {		// 모든 구슬에 대해 반복
			if(x==turnUpX) {		// 방향 전환
				direction = 0;
				turnUpX++;
			} else if(x==turnDownX) {
				direction = 1;
				turnDownX--;
			}
			if(y==turnLeftY) {
				direction = 2;
				turnLeftY--;
			} else if(y==turnRightY) {
				direction = 3;
				turnRightY++;
			}
			
			indexes.put(idx++, new int[]{x, y});		// 현재 구슬에 인덱스 부여
			y += dy[direction];		// y값 갱신
			x += dx[direction];		// x값 갱신
		}
	}
	
	private static void change() {		// 구슬이 변함
		int putIdx = 1;		// 삽입할 인덱스
		int checkIdx = 1;		// 확인할 인덱스
		int[][] newMap = new int[map.length][map.length];		// 새롭게 갱신될 맵
		int prev = map[indexes.get(checkIdx)[1]][indexes.get(checkIdx)[0]];		// 이전 구슬의 숫자 저장
		int token = 0;		// 해당 구슬이 몇번 나왔는지

		while(putIdx<N*N && checkIdx<N*N) {		// 존재하는 구슬을 전부 확인
			int checkX = indexes.get(checkIdx)[0];		// 확인할 x 위치
			int checkY = indexes.get(checkIdx)[1];		// 확인할 y 위치
			int putX = indexes.get(putIdx)[0];		// 확인할 x 위치
			int putY = indexes.get(putIdx)[1];		// 확인할 y 위치
			
			if(map[checkY][checkX]!=prev) {		// 현재 확인하는 구슬의 숫자가 이전 숫자와 다르면
				newMap[putY][putX] = token;		// 구슬의 개수 삽입
				if(putIdx==N*N-1)		// 삽입했는데 모든 맵이 가득차면 리턴
					break;

				putX = indexes.get(++putIdx)[0];		// 숫자 놓을 x 위치 변경
				putY = indexes.get(putIdx)[1];		// 숫자 놓을 y 위치 변경
				newMap[putY][putX] = prev;		// 구슬의 번호 삽입
				putIdx++;		// 삽입할 번호 늘리고
				token = 0;		// 누적 개수 초기화
			}
			prev = map[checkY][checkX];		// 현재값 prev에 저장
			token++;		// 누적 개수 증가
			checkIdx++;		// 확인할 인덱스 증가
		}
		
		for(int i=0; i<N; i++) {		// 갱신된 맵으로 변경
			for(int j=0; j<N; j++)
				map[i][j] = newMap[i][j];
		}
	}

	private static boolean explosion() {		// 폭발
		int idx = 1;		// 확인할 인덱스
		int token = 0;		// 같은 숫자가 연속으로 몇번 나왔는지 체크할 변수
		int prev = -1;		// 이전 숫자 정보
		boolean activated = false;		// 뭐라도 터졌는지 체크할 변수

		while(idx<N*N) {
			int[] p = indexes.get(idx);
			int x = p[0];
			int y = p[1];

			if(map[y][x]!=prev) {		// 숫자가 달라지면
				if(token>=4) {		// 4개 이상 뭉쳤을 때
					activated = true;		// 터졌음을 표시하고
					switch(prev) {		// 숫자에따라 터진 구슬을 저장
					case 1: bead1 += token; break;
					case 2: bead2 += token; break;
					case 3: bead3 += token; break;
					}
					for(int i=idx-1; i>=idx-token; i--) {		// 폭발시키기
						int[] temp = indexes.get(i);
						map[temp[1]][temp[0]] = 0;
					}
				}
				prev = map[y][x];		// 현재 칸의 구슬 정보로 업데이트
				token = 0;		// 개수 초기화
			}

			token++;
			idx++;
		}

		return activated;
	}

	private static void pull() {		// 중앙으로 땡김
		int checkIdx = 0, putIdx = 1;
		
		while(++checkIdx<N*N) {
			int checkX = indexes.get(checkIdx)[0];		// 확인할 x 위치
			int checkY = indexes.get(checkIdx)[1];		// 확인할 y 위치
			int putX = indexes.get(putIdx)[0];		// 확인할 x 위치
			int putY = indexes.get(putIdx)[1];		// 확인할 y 위치
			
			if(map[checkY][checkX]!=0) {		// 구슬이 파괴된 칸이 아니면
				map[putY][putX] = map[checkY][checkX];		// 한칸 땡김
				if(checkIdx!=putIdx)		// 땡겼으면
					map[checkY][checkX] = 0;		// 원래있던 자리는 0으로 갱신
				putIdx++;		// 땡기는 위치 갱신
			}
		}
	}

	private static void blizzard(int direction, int range) {		// 블리자드 수행
		int dx = 0, dy = 0;
		
		for(int i=0; i<range; i++) {
			switch(direction) {
			case 1: dy--; break;
			case 2: dy++; break;
			case 3: dx--; break;
			case 4: dx++; break;
			}
			
			map[center+dy][center+dx] = 0;		// 센터 기준 일직선으로 구슬 파괴
		}
	}
}