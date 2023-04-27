package Gold.Gold2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 구현,
 * 상어가 먹는건 dfs? 먹을 수 있는 모든 경우의 수 : 먹고/먹지 않고 => 재귀
 * 1:상 / 2:좌상 / 3:좌 / 4:좌하 / 5:하 / 6:우하 / 7:우 / 8 우상
 * @author Gunhoo
 *
 */
public class BJ_19236_청소년상어 {
    static class Fish {
        int x, y, number, dir;
        boolean isAlive = true;
        public Fish() { }
        public Fish(int x, int y, int number, int dir, boolean isAlive) {
            this.x = x;
            this.y = y;
            this.number = number;
            this.dir = dir;
            this.isAlive = isAlive;
        }
    }
	static class Shark{
		int x, y, dir, size;

		public Shark(int x, int y, int dir, int size) {
			super();
			this.x = x;
			this.y = y;
			this.dir = dir;
			this.size = size;
		}
	}
	static int answer = 0;
	static List<Fish> fishes = new ArrayList<>();
	static Shark shark;
	static int[][] direction = {{0,0}, {-1,0}, {-1,-1}, {0,-1}, {1,-1}, {1,0}, {1,1}, {0,1}, {-1,1}};
	static int[][] numbers;
	
	public static void main(String[] args) throws Exception{
		init(); // 입력
		moveShark(numbers, shark, fishes); // dfs
		System.out.println(answer); // 출력
	}
	
	private static void init() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st =null;
		numbers = new int[4][4];
		for(int i =0 ; i < 4; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j =0 ; j < 4; j++) {
				Fish tmp = new Fish();
				tmp.number = Integer.parseInt(st.nextToken());
				tmp.dir = Integer.parseInt(st.nextToken());
				tmp.x = i;
				tmp.y = j;
				fishes.add(tmp);
				numbers[i][j] = tmp.number;
			}
		}
		Collections.sort(fishes, new Comparator<Fish>() {
			@Override
			public int compare(Fish o1, Fish o2) {
				return o1.number-o2.number;
			}
		});
		Fish eaten = fishes.get(numbers[0][0]-1);
		shark = new Shark(0,0, eaten.dir, 0);
		shark.size += eaten.number;
		eaten.isAlive = false;
		numbers[0][0] = -1;
	}
	
	private static void moveShark(int[][] numbers, Shark shark, List<Fish> fishes) {
		answer = Math.max(shark.size, answer); // answer 최댓값 갱신
		
		for(Fish f : fishes) { // 모든 물고기 이동
			moveFish(f, numbers, fishes);
		}
		for(int i =1 ; i <= 3; i++) { // 상어는 최대 3곳을 방문할 수 있다
			int nx = shark.x + direction[shark.dir][0]*i;
			int ny = shark.y + direction[shark.dir][1]*i;
			if(nx<0|| nx>3 || ny<0 || ny>3 || numbers[nx][ny]<=0) continue;
			
			/** 임시 배열 생성 (3곳 중 한곳을 가고, 그것을 dfs 하기 위해) */
			int[][] tmp = new int[4][4];
			for(int j = 0; j <4; j++) tmp[j] = Arrays.copyOf(numbers[j], numbers[j].length);
			List<Fish> tmpFishes = new ArrayList<>();
			for(Fish f : fishes) tmpFishes.add(new Fish(f.x, f.y, f.number, f.dir, f.isAlive));
			
			/** 물고기 냠냠 */
			tmp[shark.x][shark.y] = 0; // 원래 상어있던 곳은 0으로 
			Fish f = tmpFishes.get(numbers[nx][ny]-1); // 이동할 곳의 물고기 정보 get
			Shark movedShark = new Shark(f.x, f.y, f.dir, shark.size+f.number);//상어 위치이동, 먹은거 계산
			f.isAlive = false; // 이동한 곳의 물고기는 죽여
			tmp[f.x][f.y] = -1; // 이동한 곳은 -1로 만듦(상어가 있음을 표시)
			
			/** dfs */
			moveShark(tmp, movedShark, tmpFishes);
		}
	}
	
	private static void moveFish(Fish fish, int[][] numbers, List<Fish> fishes) {
		if(fish.isAlive == false) return;
		for (int i = 0; i < 8; i++) {
            int dir = fish.dir+i;
            if(dir>8) dir %= 8;
            int nx = fish.x + direction[dir][0];
            int ny = fish.y + direction[dir][1];

            if (0 <= nx && nx < 4 && 0 <= ny && ny < 4 && numbers[nx][ny] != -1) { // 범위안에 있거나, 상어가 아니면
                numbers[fish.x][fish.y] = 0; // 자신의 위치는 0으로 비워두고,
                if (numbers[nx][ny] == 0) {// 이동한 곳이 비어있었다면,
                    fish.x = nx; // 바로 이동
                    fish.y = ny;
                } else { // swap해줘야한다면,
                    Fish temp = fishes.get(numbers[nx][ny] - 1); // 해당 위치 얻어와서
                    temp.x = fish.x; 
                    temp.y = fish.y;
                    numbers[fish.x][fish.y] = temp.number; // 원래있던 곳 이동할 것으로 덮어써
                    fish.x = nx; // 이동
                    fish.y = ny;
                }
                numbers[nx][ny] = fish.number; // 이동한 곳은 기존 고기사이즈
                fish.dir = dir; // 방향 갱신
                break; // 한번만 이동하니 탈출
            }
        }
	}
	
	
	private static void print() {
		for(int i =0 ; i < 4; i++) {
			for(int j =0; j<4;j++) {
				System.out.print(numbers[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
