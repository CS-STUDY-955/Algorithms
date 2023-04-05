package Gold.Gold3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 빡구현문제
 * 원판돌리기 : 배열생성, 인덱스 설정해서 인덱스 돌려
 * 
 * @author Gunhoo
 *
 */
public class BJ_17822_원판돌리기 {
	static int N, M, T;
	static int[][] circles;
	static int[][] command;
	static int[] index;
	static int answer;
	static int beforeNum = 0;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		circles =new int[N][M]; // N개 원판, 각 원판마다 M개 숫자
		index = new int[N]; // N개 원판의 top을 가르키는 index변수
		command = new int[T][3]; // T개 명령어, 0은 x(숫자), 1은 d(방향), 2는 k(회전수)
		for(int i = 0; i < N ; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j =0 ; j < M ; j++) {
				circles[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		for(int i = 0; i < T; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j =0; j < 3; j++) {
				command[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		rotation();
		System.out.println(answer);
	}
	private static void rotation() {
		for(int tc = 0; tc<T; tc++) { // T번 명령 수행
			int x = command[tc][0]; // 배수찾기 
			int d = command[tc][1]; // 회전 0:시계, 1:반시계
			int k = command[tc][2]; // 회전수
			int delNum =0;
			for(int i = x-1; i < N ; i +=x) { // x의 배수인 원판 회전
				if( d == 1) { // 반시계방향이면
					index[i] += k; // k만큼 회전
					if(index[i] >= M) index[i] -= M; // 원판수
				}else{ // 시계방향이면
					index[i] -= k; // k만큼 회전
					if(index[i] < 0) index[i] = M + index[i];
				}
			}
			// 여기서부터 같은 방향에 같은 수 있는지 확인
			int[][] tmpCircles = new int[N][M];
			for(int i =0 ; i < N ; i++) {
				tmpCircles[i] = Arrays.copyOf(circles[i], circles[i].length);
			}
			
			for(int i =0 ; i < N ;i++) {
				boolean[] same = new boolean[M];
				for(int j =0 ; j < M ; j++) {
					if( tmpCircles[i][j] == tmpCircles[i][j+1>=M?j+1-M:j+1] || 
							tmpCircles[i][j] == tmpCircles[i][j-1>=0?j-1:M+j-1]) {
						same[j] = true;
					}
				}
				for(int j =0 ; j < M; j++) {
					if(same[j]) {
						tmpCircles[i][j] = 0;
					}
				}
			}

			for(int i = 0; i < M ; i++) { // 다른 원판끼리 같은 놈들 지워
				int before = circles[0][index[0]+i>=M?index[0]+i-M:index[0]+i];
				for(int j =1; j < N; j++) {
					if(circles[j][index[j]+i>=M?index[j]+i-M:index[j]+i] == before) { // 이전 원판이랑 같으면
						circles[j][index[j]+i>=M?index[j]+i-M:index[j]+i] = 0; // 현재원판 번호 없애
						circles[j-1][index[j-1]+i>=M?index[j-1]+i-M:index[j-1]+i] = 0; // 이전원판도 번호 없애
					}else { // 지금원판이 전원판이랑 다르면
						before = circles[j][index[j]+i>=M?index[j]+i-M:index[j]+i];
					}
				}
			}
			
			for(int i =0; i < N ;i++) {
				for(int j =0 ;j < M ; j++) {
					if(tmpCircles[i][j] ==0 ) circles[i][j] = 0;
				}
			}
			
			double sum =0.0;
			for(int i =0 ;i < N ;i++) {
				for(int j =0; j < M ; j++) {
					sum += circles[i][j];
					if(circles[i][j] == 0 )delNum++;
				}
			}
			if( delNum != beforeNum) {
				beforeNum = delNum;
				continue;
			}
			
			sum = sum / (N*M-delNum);
			
			for(int i =0 ;i < N ;i++) {
				for(int j =0; j < M ; j++) {
					if(circles[i][j] == 0)continue;
					if(circles[i][j] > sum) {
						circles[i][j]--;
					}else if(circles[i][j] < sum) {
						circles[i][j]++;
					}
				}
			}
			
		}
		for(int i =0 ; i < N ;i++) {
			for(int j =0; j < M ;j++) {
				answer += circles[i][j];
			}
		}
	}
}
