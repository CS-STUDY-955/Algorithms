package Gold.Gold4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * https://www.acmicpc.net/problem/20056
 * 1. 풀이방법
 * 배열 임시 복사 > 임시복사 칸에 원본 이동해서 넣어줘
 * 2차원배열 > 배열안에 배열 넣어줘야 여러개 파이어볼 합쳐졌는지 알 수 있어
 * 2차원 ArrayList<FireBall>생성 > 입력 받고
 * 	k번 반복
 * 		tmp생성(원본복사x) // 원본 2차원 arrayList<FireBall> 복사
 * 		arrayList[i][j].size() >0 이면 
 * 		arrayList[i][j] 에서 정보 뽑아서 tmp에 넣어줘
 * 		if( tmp[i][j].size() > 1 ) 중복되었으면
 * 			r, c, m, d,s 꺼내서 
 *			m += m/5; // 다 더해서 5로 나눠
 *			s += s; // 다 더해주고 
 *			s / tmp[i][j].size(); // 사이즈로 나누어줘
 *			if d 가 모두 짝수 || d가 모두 홀수 => d = 0246 
 *			else 1357
 
 * FireBall은  m, d, s 정보 저장
 * 
 * 2. 풀이시간
 * 09:30~09:50(20분) : 생각, 입력받기
 * 11:30~12:20(50분) : 로직 완성 (터짐 : 다차원ArrayList생성, IndexOutOfBounds)
 * 13:15 ~ 13:45 (30분) : 로직 수정 
 * 총 : 1시간 40분
 * 
 * 3. 헤맸던 부분
 *  	- 3차원(?) ArrayList 생성 
 * 
 * @author Gunhoo
 *
 */
public class BJ_20056_마법사상어와파이어볼 {
	static int N,K;
	static ArrayList<FireBall>[][] map, tmp;
	static int[] dx = {-1,-1,0,1,1,1,0,-1};
	static int[] dy = {0,1,1,1,0,-1,-1,-1};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());

		map = new ArrayList[N][N];
		for(int i =0 ; i < N; i++) {
			for(int j = 0 ; j<N; j++) {
				map[i][j] = new ArrayList<FireBall>();
			}
		}
		int M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		for(int i =0 ; i < M ; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			map[r][c].add(new FireBall(m,d,s));
		}
		for(int i =0 ; i < K ; i++) {
			moveFireBall(); // 일단 파이어볼 다 옮겨
			splitFireBall(); // 2개 이상있는 놈들 분리시키는 메서드
		}
		System.out.println(count());
	}
	
	private static void moveFireBall() {
		tmp = new ArrayList[N][N]; // 새롭게 담을 map 신규 생성
		for(int i =0 ; i < N; i++) {
			for(int j = 0 ; j<N; j++) {
				tmp[i][j] = new ArrayList<FireBall>();
			}
		}
		for(int i = 0 ; i < N ; i++) {
			for(int j = 0; j < N ; j++) {
				if(map[i][j].size() == 0) continue; // 0이면 패스
				else if(map[i][j].size() >= 1) { // 파이어볼이 1개 이상 있었으면
					for(int k = 0 ; k < map[i][j].size(); k++) {// size 만큼 반복해서 다른 곳으로 보내
						int newD = map[i][j].get(k).d; // 그대로 가져옴
						int newS = map[i][j].get(k).s; 
						int newM = map[i][j].get(k).m;
						int nx = (i+dx[newD]*newS) % N; // 자신의 위치에서 dx(방향)으로 newS만큼 가 
						int ny = (j+dy[newD]*newS) % N; // (근데 N보다 커질 수 있으니 %N)
						if (nx < 0 ) nx = N+nx; // 또 음수가 될수도 있으니 음수면 +N
						if (ny < 0 ) ny = N+ny;
						tmp[nx][ny].add(new FireBall(newM, newD, newS)); // 새로운맵 좌표에 정보 넣어줘
					}
				}
			}
		}
		map = tmp; // 맵 갱신
	}
	
	private static void splitFireBall() { // 파이어볼 2개 이상 뭉친곳을 분리해주는 메서드
		for(int i =0 ; i< N ; i++) {
			for(int j = 0 ; j < N ; j++) {
				int size = map[i][j].size();
				if(size >= 2) { // 파이어볼이 2개 이상 들어가 있으면
					int newS=0, newM= 0; // 새로운 속도, 새로운 질량 위한 변수
					int[] newD = new int[size]; // 모두 짝수/홀수 : 0246 이고 아니면 1357
					int odd=0, even=0;
					for(int k = 0 ; k < size; k++) { // 모든 파이어볼에 대해 
						newS += map[i][j].get(k).s; // 속도 누적
						newM += map[i][j].get(k).m; // 질량 누적
						newD[k] = map[i][j].get(k).d; // 방향은 배열에 담아
					}
					map[i][j] = new ArrayList<FireBall>(); // 기존정보 필요없으니(분리되었으니) 초기화
					newM /= 5; // 질량은 5분의 1만큼
					if(newM == 0) continue; // 근데 질량이 0되면 사라지니까 continue
					newS /= size; // 속도는 파이어볼의 갯수만큼 나눠줘
					for(int k = 0 ; k < size; k++) { // 모든 파이어볼에 대해 
						if(newD[k] %2 == 0) even++; // 방향이 짝수면 even++
						else odd++; // 홀수면 odd++
					}
					
					if( even == 0 || odd == 0) { // 하나라도 0이면 모두 짝수이거나 홀수라는 뜻 
						for(int k = 0 ; k < 4; k++) {
							map[i][j].add(new FireBall(newM, 2*k, newS)); // 0, 2, 4, 6
						}
					}else { // 1357
						for(int k = 0 ; k < 4; k++) {
							map[i][j].add(new FireBall(newM, 2*k+1, newS));
						}
					}
				}
			}
		}
	}
	
	private static int count() {
		int sum = 0;
		for(int i =0  ; i< N ; i++) {
			for(int j = 0 ; j< N ; j++) {
				if(map[i][j].size() > 0) {
					for(int k =0 ; k < map[i][j].size(); k++) {
						sum += map[i][j].get(k).m;
					}
				}
			}
		}
		return sum;
	}
	
	static class FireBall{
		int m, d, s;
		public FireBall(int m, int d, int s) {
			this.m = m;
			this.d = d;
			this.s = s;
		}
		
	}
}
