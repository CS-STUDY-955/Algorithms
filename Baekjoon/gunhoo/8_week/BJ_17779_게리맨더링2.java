package Gold.Gold3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 브루트포스로 완탐
 * 인구가 가장 많은 선거구와 가장 적은 선거구의 인구 차이의 최솟값
 * @author Gunhoo
 *
 */
public class BJ_17779_게리맨더링2 {
	static int N;
	static int map[][], district[][];
	static int min = Integer.MAX_VALUE;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		
		
		for(int i =0 ;i < N ;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0 ; j < N ; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for(int i = 0; i < N ; i++) {
			for(int j =0 ; j < N ; j++) {
				for(int d1= 1; d1< N/2 ; d1++) {
					for(int d2=1; d2<N/2; d2++) {
						if(i-d1<0 || j+d1<0 || i-d1>=N || j+d1>=N) continue;
						if(i-d1+d2<0 || j+d1+d2<0 || i-d1+d2>=N || j+d1+d2>=N) continue;
						if(i+d2<0 || j+d2<0 || i+d2>=N || j+d2>=N) continue;
						district = new int[N][N];
						district5(i,j,d1,d2);
						split(i, j, d1, d2);
					}
				}
			}
		}
		System.out.println(min);
		
	}
	private static void split(int a, int b, int d1, int d2) {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(district[i][j] != 0) continue;
				if(i<a && j<=b+d1) {
					district[i][j] = 1;
				}else if(i<=a-d1+d2 && j>b+d1) {
					district[i][j] = 2;
				}else if(i>=a && j<b+d2) {
					district[i][j] = 3;
				}else {
					district[i][j] = 4;
				}
			}
		}
		calculate();
	}
	private static void district5(int a, int b, int d1, int d2) {
		for(int i=a-d1; i<=a+d2; i++) { 
			for(int j=b; j<=b+d1+d2; j++) { 
				district[i][j] = 5; // 일단 최대범위로 다 5로 칠해
			}	
		}
		int td = d1;
		for(int i=a-d1; i<a; i++) { // 좌상을 0으로 만들어
			for(int j=0; j<td; j++) {
				district[i][b+j] = 0;
			}
			td--;
		}
		td = d2;
		for(int i=a-d1; i<a-d1+d2; i++) { // 우상을 0으로 만들어
			for(int j=0; j<td; j++) {
				district[i][b+d1+d2-j] = 0;
			}
			td--;
		}
		td = 0;
		for(int i=a+1; i<=a+d2; i++) { // 좌하를 0으로 만들어
			for(int j=0; j<=td; j++) {
				district[i][b+j] = 0;
			}
			td++;
		}
		td = 0;
		for(int i=a-d1+d2+1; i<=a+d2; i++) { // 우하를 0으로 만들어
			for(int j=0; j<=td; j++) {
				district[i][b+d1+d2-j] = 0;
			}
			td++;
		}
	}
	private static void calculate(){
		int sum[] = {0,0,0,0,0};
		for(int i =0 ; i < N ;i ++) {
			for(int j =0 ; j < N ; j++) {
				sum[district[i][j]-1]+= map[i][j];
			}
		}
		Arrays.sort(sum);
		min = Math.min(min, sum[4]-sum[0]);
	}

}
