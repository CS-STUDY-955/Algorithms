package Platinum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 1. 모든 온풍기에서 바람이 나옴(0빈 1좌 2우 3상 4하 5조사해야하는칸)
 * 2. 온도 조절(4방향으로 퍼짐)
 * 3. 가장 바깥족 4면 온도 감소
 * 4. answer += 1;
 * 5. 조사해야하는 칸이 K이상인지 검사 > K이상이거나 answer가 100이면 종료
 * 6. answer 출력 
 * @author 박건후
 *
 */
public class BJ_23289_온풍기안녕 {
	static class Map{ // 지도 클래스
		int t=0, info; // t: 온도, info:  온풍기 정보
		boolean left=false, right=false, up=false, down=false; // 벽이 존재하는지 확인하는 메서드
		public Map(int info) { // 생성자
			this.info = info; // 온풍기 정보만 담는다
		}
	}
	static class Point{ // 온도 증가시키기 위해 만든 클래스
		int x, y, cnt; // 좌표정보와 온도가 몇 올라야하는지 저장하는 변수

		public Point(int x, int y, int cnt) { // 생성자
			super();
			this.x = x;
			this.y = y;
			this.cnt = cnt;
		}
		
	}
	static int R, C, K, W, answer = 0;
	static Map map[][];
	static boolean visited[][];
	
	public static void main(String[] args) throws Exception{
		init(); // 입력
		while(true) {
			upTemp(); // 온풍기 메서드
			manageTemp(); // 온도 조절 메서드
			deTemp(); // 온도 감소 메서드
			answer++; // 초콜릿 먹어
//			print();
			if(isGood() || answer == 101) { // 모든칸이 K이상이거나, 초콜릿 100번 넘어가면
				break;
			}
		}
		System.out.println(answer);
	}
	
	private static void upTemp() {
		for(int i =0 ;i < R; i++) {
			for(int j =0 ; j < C; j++) {
				if(map[i][j].info == 0 || map[i][j].info == 5)continue; // 온풍기가 있는
				visited = new boolean[R][C]; // 방문체크 
				Queue<Point> q = new ArrayDeque<>(); // 3방향으로 퍼지니까 이를 담기 위해 선언
				switch(map[i][j].info) { // 온풍기 정보에 따라
				case 1://우
					q.offer(new Point(i, j+1, 5)); // 오른쪽으로 1칸, 5부터 시작 
					while(!q.isEmpty()) {
						Point point = q.poll();
						if(point.cnt == 0) continue; // 0은 증가 안해도 돼
						if(point.x < 0 || point.x >= R || point.y < 0 || point.y >=C 
								|| visited[point.x][point.y]) continue; // 범위 밖이거나 방문했으면 패스
						visited[point.x][point.y] = true; // 방문처리
						map[point.x][point.y].t += point.cnt; // 온도 증가
						if(point.x-1 >= 0 && point.y+1<C 
								&& !map[point.x][point.y].up && !map[point.x-1][point.y+1].left) // 벽이 없으면
							q.offer(new Point(point.x-1, point.y+1, point.cnt-1));
						if(!map[point.x][point.y].right) // 벽이 없으면
							q.offer(new Point(point.x, point.y+1, point.cnt-1));
						if(point.x+1 < R && point.y+1<C 
								&& !map[point.x][point.y].down && !map[point.x+1][point.y+1].left) // 벽이 없으면
							q.offer(new Point(point.x+1, point.y+1, point.cnt-1));
					}
					break;
				case 2: // 좌
					q.offer(new Point(i, j-1, 5));
					while(!q.isEmpty()) {
						Point point = q.poll();
						if(point.cnt == 0) continue;
						if(point.x < 0 || point.x >= R || point.y < 0 || point.y >=C 
								|| visited[point.x][point.y]) continue;
						visited[point.x][point.y] = true;
						map[point.x][point.y].t += point.cnt;
						if(point.x-1 >= 0 && point.y-1>=0 
								&& !map[point.x][point.y].up && !map[point.x-1][point.y-1].right)
							q.offer(new Point(point.x-1, point.y-1, point.cnt-1));
						if(!map[point.x][point.y].left)
							q.offer(new Point(point.x, point.y-1, point.cnt-1));
						if(point.x+1 < R && point.y-1>=0 
								&& !map[point.x][point.y].down && !map[point.x+1][point.y-1].right)
							q.offer(new Point(point.x+1, point.y-1, point.cnt-1));
					}
					break;
				case 3://상
					q.offer(new Point(i-1, j, 5));
					while(!q.isEmpty()) {
						Point point = q.poll();
						if(point.cnt == 0) continue;
						if(point.x < 0 || point.x >= R || point.y < 0 || point.y >=C 
								|| visited[point.x][point.y]) continue;
						visited[point.x][point.y] = true;
						map[point.x][point.y].t += point.cnt;
						if(point.x-1 >= 0 && point.y-1>=0 
								&& !map[point.x][point.y].left && !map[point.x-1][point.y-1].down)
							q.offer(new Point(point.x-1, point.y-1, point.cnt-1));
						if(!map[point.x][point.y].up)
							q.offer(new Point(point.x-1, point.y, point.cnt-1));
						if(point.x-1 >= 0 && point.y+1<C 
								&& !map[point.x][point.y].right && !map[point.x-1][point.y+1].down)
							q.offer(new Point(point.x-1, point.y+1, point.cnt-1));
					}
					break;
				case 4://하
					q.offer(new Point(i+1, j, 5));
					while(!q.isEmpty()) {
						Point point = q.poll();
						if(point.cnt == 0) continue;
						if(point.x < 0 || point.x >= R || point.y < 0 || point.y >=C 
								|| visited[point.x][point.y]) continue;
						visited[point.x][point.y] = true;
						map[point.x][point.y].t += point.cnt;
						if(point.x+1 < R && point.y-1>=0 
								&& !map[point.x][point.y].left && !map[point.x+1][point.y-1].up)
							q.offer(new Point(point.x+1, point.y-1, point.cnt-1));
						if(!map[point.x][point.y].down)
							q.offer(new Point(point.x+1, point.y, point.cnt-1));
						if(point.x+1 < R && point.y+1<C 
								&& !map[point.x][point.y].right && !map[point.x+1][point.y+1].up)
							q.offer(new Point(point.x+1, point.y+1, point.cnt-1));
					}
					break;
				}
			}
		}
	}
	
	private static void manageTemp() { // 온도 조절 메서드: 큰쪽에서 작은쪽으로 (4방향) 값의 차이만큼 퍼진다
		int tmap[][] = new int[R][C];// 온도만 가지고 있으면 메모리 절약 가능
		for(int i =0 ; i < R ; i++) {
			for(int j =0; j  <C; j++) {
				tmap[i][j] = map[i][j].t; 
			}
		}
		for(int i =0 ; i < R; i++) {
			for(int j =0 ; j < C; j++) {
				if(map[i][j].t == 0)continue; // 온도 0이면 안퍼져, 값의 차이 / 4인 점 주의!
				if(!map[i][j].up && i-1 >=0 && 3 < map[i][j].t-map[i-1][j].t) { // 위로 조절
					int tmp = (map[i][j].t-map[i-1][j].t)/4;
					tmap[i-1][j] += tmp;
					tmap[i][j] -= tmp;
				}
				if(!map[i][j].down && i+1<R && 3 < map[i][j].t-map[i+1][j].t ) { // 밑으로 조절
					int tmp = (map[i][j].t-map[i+1][j].t)/4;
					tmap[i+1][j] += tmp;
					tmap[i][j] -= tmp;
				}
				if(!map[i][j].left && j-1 >= 0 && 3 < map[i][j].t-map[i][j-1].t) { // 좌로 조절
					int tmp = (map[i][j].t-map[i][j-1].t)/4;
					tmap[i][j-1] += tmp;
					tmap[i][j] -= tmp;
				}
				if(!map[i][j].right && j+1 < C && 3 < map[i][j].t-map[i][j+1].t) { // 우로 조절
					int tmp = (map[i][j].t-map[i][j+1].t)/4;
					tmap[i][j+1] += tmp;
					tmap[i][j] -= tmp;
				}
				
			}
		}
		for(int i =0 ; i < R ; i++) {
			for(int j =0; j<C; j++) {
				map[i][j].t = tmap[i][j]; // 맵의 온도 갱신
			}
		}
		
	}
	
	private static void deTemp() { // 가장자리 온도 떨어트리는 메서드
		for(int j =0 ; j <C; j++) { // 모든 행
			if(map[0][j].t != 0) // 0이 아닌것에만 1감소해야해, 아니면 마이너스
				map[0][j].t -= 1; // 1행 모든열 -1감소
			if(map[R-1][j].t != 0) // 마지막행 모든열 -1감소
				map[R-1][j].t -= 1;
		}
		for(int i = 1; i < R-1; i++) { // 모든 열
			if(map[i][0].t != 0) // 1열 모든 행
				map[i][0].t -= 1;
			if(map[i][C-1].t != 0) // 마지막열 모든행
				map[i][C-1].t -= 1;
		}
	}
	
	private static boolean isGood() { // 종료조건 만족하는지 확인하는 메서드
		for(int i =0 ; i < R; i++) {
			for(int j=0 ; j <C ; j++) {
				if(map[i][j].info != 5) continue; // 5만 조사하면 돼
				if(map[i][j].t < K )return false; // 온도가 K 미만인 얘가 존재하면 안돼
			}
		}
		return true; // 모두 통과하면 true
	}
	
	private static void init() throws Exception{ // input 메서드
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new Map[R][C];
		for(int i =0 ; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j =0 ; j < C; j++) {
				map[i][j] = new Map(Integer.parseInt(st.nextToken()));
			}
		}
		W = Integer.parseInt(br.readLine());
		for(int i =0 ;i < W; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int t = Integer.parseInt(st.nextToken());
			if(t == 0) {
				map[x][y].up = true;
				map[x-1][y].down = true;
			}else {
				map[x][y].right = true;
				map[x][y+1].left = true;
			}
		}
	}
	private static void print() { // 검증 메서드, 지도의 온도를 출력
		for(int i = 0; i < R; i++) {
			for(int j =0 ; j < C; j++) {
				System.out.print(map[i][j].t+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
