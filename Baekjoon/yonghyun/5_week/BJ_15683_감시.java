
import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 감시
 * https://www.acmicpc.net/problem/15683
 * 
 * 1. 각 cctv가 바라보고 있는 방향을 정한다. (1, 3, 4는 4가지, 2는 2가지, 5는 1가지 경우가 있다.) - 조합
 * 2. cctv가 감시하고 있는 영역의 크기를 구한다.
 * 3. 최솟값을 저장했다가 출력한다.
 * --------------------------------------
 * 1. 3차원 배열이 등장하고, 이를 다른 배열의 인덱스로 사용하다보니 실수가 발생함.
 * 2. 주석을 달면서 코딩하는 습관을 들여야 할 것 같음
 * 
 * @author 배용현
 *
 */
public class BJ_15683_감시 {
	static int N, M, answer = Integer.MAX_VALUE;
	static char[][] map;		// 맵 정보
	static List<List<Integer>>[] direction;		// 5종류의 cctv가 각각 감시할 수 있는 방향 (0번은 더미)
	static int[] observingDirection;		// 각 cctv가 감시중인 방향
	static List<int[]> cctv;		// cctv의 좌표 정보 (0: x좌표, 1: y좌표, 2: cctv종류)
	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {-1, 0, 1, 0};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new char[N][M];
		cctv = new ArrayList<>();

		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M; j++) {
				map[i][j] = st.nextToken().charAt(0);
				if(map[i][j]!='0' && map[i][j]!='6')
					cctv.add(new int[]{j, i, map[i][j]-'0'});		// 조합 선정시 맵을 돌면서 cctv를 찾으면 느리니까 미리 저장
			}
		}

		observingDirection = new int[cctv.size()];		// 각 cctv가 감시중인 방향을 저장해야 하므로 cctv 개수만큼 초기화

		setDirections();		// cctv가 감시할 수 있는 방향 정의
		combination(0);		// cctv가 감시하는 방향 조합

		System.out.println(answer);
	}

	private static void combination(int depth) {		// cctv가 감시하는 방향을 조합하는 재귀 메서드
		if(depth==cctv.size()) {		// 모든 cctv의 방향을 골랐으면
			answer = Math.min(answer, calSafetyZone());		// 안전영역을 계산하고 최솟값을 저장

			return;
		}

		for(int i=0; i<direction[cctv.get(depth)[2]].size(); i++) {		// cctv는 종류에 따라 감시 방향과 그 개수가 다르므로 direction의 정보를 이용한다.
			observingDirection[depth] = i;		// depth번째 cctv가 감시하는 방향 설정
			combination(depth+1);		// 다음 재귀 실행
		}
	}

	private static int calSafetyZone() {		// cctv에 의해 감시되는 영역을 표시하고, 안전 영역의 개수를 계산하여 리턴하는 메서드
		char[][] tempMap = new char[N][M];		// 감시 영역을 #으로 변경하므로 새로운 배열에 수행
		for(int i=0; i<N; i++)		// 배열 복사
			tempMap[i] = Arrays.copyOf(map[i], M);

		for(int i=0; i<cctv.size(); i++) {		// 각 cctv는
			int x = cctv.get(i)[0];
			int y = cctv.get(i)[1];
			int kind = cctv.get(i)[2];
			for(int j=0; j<direction[kind].get(observingDirection[i]).size(); j++) {		// 감시하는 영역을 순회하며 모두 #으로 표시
				int nx = x;		// 첫 x좌표
				int ny = y;		// 첫 y좌표

				while(true) {
					nx += dx[direction[kind].get(observingDirection[i]).get(j)];		// 감시하는 x방향으로 한 칸이동
					ny += dy[direction[kind].get(observingDirection[i]).get(j)];		// 감시하는 y방향으로 한 칸이동
					
					if(nx<0 || ny<0 || nx>=M || ny>=N || map[ny][nx]=='6')		// 유효하지 않은 범위면 탈출
						break;

					if(map[ny][nx]=='0')		// 감시되는 영역이면 #으로 변경하여 표시
						tempMap[ny][nx] = '#';
				}
			}
		}

		int sum = 0;		// 안전 영역의 수를 기록할 변수

		for(int i=0; i<N; i++) {		// 배열을 순회하며
			for(int j=0; j<M; j++) {
				if(tempMap[i][j]=='0')		// 안전 영역이면
					sum++;		// 개수 기록
			}
		}

		return sum;		// 안전 영역의 수 리턴
	}

	private static void setDirections() {		// 각 cctv가 바라볼 수 있는 방향을 초기화
		direction = new List[6];		// 인덱스 접근의 편의를 위해 6개 생성. 0은 접근하면 안됨.
		direction[1] = Arrays.asList(Arrays.asList(0), Arrays.asList(1), Arrays.asList(2), Arrays.asList(3));		// 각 인덱스를 dx를 통해 접근하면 해당 방향으로 움직일 수 있음
		direction[2] = Arrays.asList(Arrays.asList(0, 2), Arrays.asList(1, 3));
		direction[3] = Arrays.asList(Arrays.asList(0, 1), Arrays.asList(1, 2), Arrays.asList(2, 3), Arrays.asList(3, 0));
		direction[4] = Arrays.asList(Arrays.asList(0, 1, 2), Arrays.asList(1, 2, 3), Arrays.asList(0, 1, 3), Arrays.asList(0, 2, 3));
		direction[5] = Arrays.asList(Arrays.asList(0, 1, 2, 3));
	}
}