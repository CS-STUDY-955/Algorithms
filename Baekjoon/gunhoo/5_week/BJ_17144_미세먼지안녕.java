package Gold;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 1. 구현방법
 * 	- 미세먼지있으면 arrayList에 넣어줘
 *  - arrayList에서 뽑아서 4방탐색으로 주변에 미세먼지 퍼트려 (퍼진곳 : 기존*0.2, 기존 : 기존- 0.2기존*퍼진곳) 즉, 기존에서 빼주고 퍼진곳에 더해줘
 *  - 미세먼지 전파가 끝났으면, 
 *  - 공기청정기에서 한칸씩 옆으로 미뤄, 공기청정기로 들어오는것은 0으로 바꿔
 * @author gunhoo
 *
 */
public class BJ_17144_미세먼지안녕 {
	static int r, c, t;
	static int[][] map, tmpMap;
	static int puriBot; // 공기청정기의 밑부분의 행 값 저장
	static int[] dx = {-1,0,1,0};
	static int[] dy = {0,1,0,-1};
//	static int totalDust = 0; // 총먼지량 저장
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		t = Integer.parseInt(st.nextToken());
		map = new int[r][c]; 
		tmpMap = new int[r][c]; 
		for(int i = 0 ; i < r ; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0 ; j < c ; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == -1) {
					puriBot = i; // 밑에있는 공기청정기의 행 값 저장
				}
//				else if(map[i][j] != 0) {// 공기청정기 아니고 빈공간아니면: 미세먼지있으면
//					totalDust += map[i][j];
//				}
			}
		}
		for(int i = 0; i< t ; i++) { // t초만큼 수행
			spreadDust();
//			System.out.println("*****spread******");
//			printMap();
			purifyUpper();
			purifyBelow();
//			System.out.println("*****purify******");
//			printMap();
		}
//		System.out.println(totalDust);
		System.out.println(sumDusts());
	}
	
	private static int sumDusts() {
		int sum = 0;
		for(int i = 0; i < r ; i++) {
			for(int j = 0 ; j< c ; j++) {
				if(map[i][j] != 0 && map[i][j] != -1) {
					sum += map[i][j];
				}
			}
		}
		return sum;
	}
	
	private static void purifyUpper() {
//		if(map[puriBot-2][0] != 0 ) { // 공기청정기 위쪽이 미세먼지면
//			totalDust -= map[puriBot-2][0];  // 미세먼지 냠냠
//		}
		for (int i = puriBot-2; i >= 1; i--) map[i][0] = map[i-1][0];
        for (int i = 0; i <= c - 2; i++) map[0][i] = map[0][i+1];
        for (int i = 0; i <= puriBot-2; i++) map[i][c-1] = map[i+1][c-1];
        for (int i = c - 1; i >= 2; i--) map[puriBot-1][i] = map[puriBot-1][i-1];
        map[puriBot-1][1] = 0;
	}
	private static void purifyBelow(){
//		if(map[puriBot+1][0] != 0 ) { // 아래쪽 미세먼지 존재하면
//			totalDust -=map[puriBot+1][0];  // 냠냠
//		}
        for (int i = puriBot + 1; i <= r - 2; i++) map[i][0] = map[i+1][0];
        for (int i = 0; i <= c - 2; i++) map[r-1][i] = map[r-1][i+1];
        for (int i= r-1; i >= puriBot + 1; i--) map[i][c-1] = map[i-1][c-1];
        for (int i = c-1; i >= 2; i--) map[puriBot][i] = map[puriBot][i-1];
        map[puriBot][1] = 0;
    }
	
	
	private static void spreadDust() {
		for(int i = 0; i< map.length; i++) { // map 정보 복사
			tmpMap[i] = Arrays.copyOf(map[i], map[i].length);
		}
		for(int i = 0; i< r ; i++) {
			for(int j = 0 ; j< c; j++) {
				if( map[i][j] <5 )continue; // 5보다 작으면 퍼지지 않아(볼필요없음)
				for(int k = 0 ; k< 4 ; k++) { // 상하좌우 봐서
					int nx = i + dx[k]; // 좌표이동
					int ny = j + dy[k];
					if( 0<= nx && nx<r && 0<=ny && ny<c && map[nx][ny] != -1) { // 이동한것이 범위내에 있고, 공기청정기 아니면
						tmpMap[nx][ny] += map[i][j]/5; // 이동한 새로운좌표에 기존의 5분의1만큼 추가하고
						tmpMap[i][j] -= map[i][j]/5; // 원래위치는 기존좌표의 5분의 1만큼 빼준다
					}
				}
			}
		}
		for(int i = 0; i< tmpMap.length; i++) { // 다 퍼트렸으면 map갱신
			map[i] = Arrays.copyOf(tmpMap[i], tmpMap[i].length);
		}
	}
}
