package Gold.Gold3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 완탐
 * 
 * @author Gunhoo
 *
 */
public class BJ_14890_경사로 {
	static int N, L;
	static int[][] map, tmap;
	static int answer = 0;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		tmap = new int[N][N];
		
		for(int i =0 ; i < N ; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j =0 ; j < N ; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		for(int i =0 ;i < N ;i++) {
			for(int j =0; j < N ; j++) {
				tmap[i][j] = map[j][i];
			}
		}
		execute();
		map = tmap;
		execute();
		System.out.println(answer);
	}
	private static void execute() {
		for(int i =0; i < N ; i++) {
			int cnt = 0;
			int before = 0;
			int h = map[i][0];
			boolean tf = true;
			out: for(int j = 0; j < N ;j++) {
				if(h == map[i][j]) continue; // 기존 높이와 동일하면 패스
				if(Math.abs(h-map[i][j])>=2) {
					tf = false;
					break out;
				}
				else if(map[i][j] == h-1) { // 한칸 줄어들었다면
					before = map[i][j];
					for(int k = 0; k < L; k++) {
						if( j + k >= N || before != map[i][j+k]) {
							tf = false;
							break out;
						}
						map[i][j+k] = 10;
					}
					cnt++;
					j += L-1;
					if( j >= N) break;
				}
				else if(map[i][j] == h+1) { /// 기존 높이보다 한칸 커졌다면,
					before = map[i][j];
					for(int k = 1; k <= L; k++) {
						if( j - k < 0 || before-1 != map[i][j-k]) {
							tf = false;
							break out;
						}
						map[i][j-k] = 10;
					}
					cnt++;
				}
				h = before;
			}
			if(tf) answer++;
		}
	}
}
