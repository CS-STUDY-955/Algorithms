package Gold.Gold4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * BJ 15684 사다리조작
 * 
 * 열을 2N개 만들고,(1~ 2N-1까지)ㅇ
 * 행은 M+1개
 * BFS로 좌우 탐색 후 없으면 밑으로 내려가게
 * 
 * 사다리를 놓는 위치는?
 * 2j위치에서 시작해서 
 * 		i= 0 ~ M까지 내려가면서(while문으로 설정해야할듯)
 * 			양옆에 연결 안되어있으면 연결해보고
 * 			결과 확인 > 맞으면 탈출
 * 			아니면 > 현재위치에서부터 맨 밑으로 내려가면서 양옆에 1있는 위치 찾고 그 밑으로 설정
 * 
 * @author 박건후
 *
 */
public class BJ_15684_사다리조작 {
	static int N, M, H;
	static int map[][];
	static int tmap[][];
	static boolean visited[][];
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		map = new int[H+2][2*N];
		
		for(int i =1 ; i < 2*N; i+=2) {
			map[0][i] = 1;
			map[H+1][i] = 1;
		}
		
		for(int i = 1 ; i <= M ; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			map[a][2*b] = 1;
		}
		for(int i = 1; i <= H ; i++) {
			for(int j =0; j < 2*N; j++) {
				if(j % 2 == 0) continue;
				map[i][j] = 1;
			}
		}
		
		int ans = -1;
		for(int i =0 ; i <= 3; i++) { // 0~3까지
			tmap = new int[H+2][2*N]; // 여러번 그리기위해서 원본은 유지해야하니까 복사  
			for(int ii =0; ii < H+2; ii++) {
				tmap[ii] = Arrays.copyOf(map[ii], map[ii].length);
			}
			if(drawLine(i)) { // i개 그려보고 유효하면
				ans = i; // 최솟값 저장
				break; // 바로 탈출
			}
		}
		System.out.println(ans);
	}
	
	private static boolean check(int[][] map) {
		for(int i = 1; i < 2*N ; i += 2) {
			int tmp = i; // 세로선 번호 
			for(int j = 1; j<H+2; j++) {
				if(map[j][tmp-1] == 1) { // 왼쪽이 연결되어있으면
					tmp-=2; // 2칸을 빼줘서 바로 해당 라인으로 넘어가
				}else if(tmp+1 != 2*N && map[j][tmp+1] == 1) { // 오른쪽이 연결되어있으면
					tmp+=2; // 2칸 더해줘서 바로 해당 라인으로 넘어가
				}
			}
			if( tmp != i) return false; // 마지막의 세로선 번호가 시작점과 같지 않다면 false 리턴
		}
		return true; // 모두 통과하면 true
	}

	private static boolean drawLine(int cnt) { // cnt만큼 선을 그리는 메서드
		if(cnt == 0) { // 남은 그려야하는 수 가 0 이면 다 그린것이므로
			return check(tmap); // 만약 성공했다면 true 리턴
		}
		for(int i = 2; i < 2*N-1; i+= 2) {
			for(int j = 1; j < H+1; j++) {
				if(tmap[j][i] == 0 && tmap[j][i-2] != 1 && tmap[j][i+2>=2*N?i:i+2] != 1) {
					tmap[j][i] = 1; // 그려보고 
					if(drawLine(cnt-1)) return true; // 재귀
					tmap[j][i] = 0; // 그린거 해제
				}
			}
		}
		return false;
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
