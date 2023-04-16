package Platinum;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_5373_큐빙{
	public static void main(String[] args)throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		char[]color=new char[] {'w','g','r','b','o','y'};
		int tc = pint(br.readLine());
		for (int testcase = 1; testcase <= tc; testcase++) {
			//주사위 만들기
			cube=new char[6][3][3];
			
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 3; j++) {
					Arrays.fill(cube[i][j], color[i]);
				}
			}
			int N = pint(br.readLine());
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			for (int i = 0; i < N; i++) {
				String oper = st.nextToken();
				//1:시계, 0:반시계
				int dir = oper.charAt(1)=='+'?1:0;
				
				switch (oper.charAt(0)) {
				case 'U':
					rotate_0();
					if(dir==0) {
						rotate_0();
						rotate_0();
					}
					break;
				case 'L':
					rotate_1();
					if(dir==0) {
						rotate_1();
						rotate_1();
					}
					break;
				case 'F':
					rotate_2();
					if(dir==0) {
						rotate_2();
						rotate_2();
					}
					break;
				case 'R':
					rotate_3();
					if(dir==0) {
						rotate_3();
						rotate_3();
					}
					break;
				case 'B':
					rotate_4();
					if(dir==0) {
						rotate_4();
						rotate_4();
					}
					break;
				case 'D':
					rotate_5();
					if(dir==0) {
						rotate_5();
						rotate_5();
					}
					break;
				default:
					break;
				}
				
				
			}

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					System.out.print(cube[0][i][j]);
				}
				System.out.println();
			}
		}
		
	}
	static char[][][]cube;
	static int[][]pos = new int[][] {
		{0,0},{0,1},{0,2},{1,2},{2,2},{2,1},{2,0},{1,0}
	}; 
	static void rotate(int n) {
		//자기 판 회전
		char[] backup=new char[] {cube[n][0][0],cube[n][0][1]};
		for (int i = 7; i >=0; i--) {
			cube[n][ pos[(i+2)%8][0] ][ pos[(i+2)%8][1] ] =
					cube[n][ pos[i][0] ][ pos[i][1] ];
		}
		cube[n][0][2]=backup[0];
		cube[n][1][2]=backup[1];
	}
	
	static void rotate_0() {
		//1234 다 위쪽
		rotate(0);
		char[]backup=cube[1][0].clone();
		//4개의 주변 판 회전
		cube[1][0]=cube[2][0].clone();
		cube[2][0]=cube[3][0].clone();
		cube[3][0]=cube[4][0].clone();
		cube[4][0]=backup.clone();
	}
	
	static void rotate_1() {
		//0왼 2왼 5왼 4오
		rotate(1);
		char[]backup=new char[] {cube[0][0][0],cube[0][1][0],cube[0][2][0]};
		//2->5->4->0
		cube[0][0][0]=cube[4][2][2];
		cube[0][1][0]=cube[4][1][2];
		cube[0][2][0]=cube[4][0][2];
		
		cube[4][2][2]=cube[5][0][0];
		cube[4][1][2]=cube[5][1][0];
		cube[4][0][2]=cube[5][2][0];
		
		cube[5][0][0]=cube[2][0][0];
		cube[5][1][0]=cube[2][1][0];
		cube[5][2][0]=cube[2][2][0];
		
		cube[2][0][0]=backup[0];
		cube[2][1][0]=backup[1];
		cube[2][2][0]=backup[2];

	}
	static void rotate_2() {
		//0아 3왼 5위 1오
		rotate(2);
		char[]backup=new char[] {cube[0][2][0],cube[0][2][1],cube[0][2][2]};
		//3-5-1-0
		cube[0][2][0]=cube[1][2][2];
		cube[0][2][1]=cube[1][1][2];
		cube[0][2][2]=cube[1][0][2];
		
		cube[1][0][2]=cube[5][0][0];
		cube[1][1][2]=cube[5][0][1];
		cube[1][2][2]=cube[5][0][2];
		
		cube[5][0][0]=cube[3][2][0];
		cube[5][0][1]=cube[3][1][0];
		cube[5][0][2]=cube[3][0][0];
		
		cube[3][2][0]=backup[2];
		cube[3][1][0]=backup[1];
		cube[3][0][0]=backup[0];
	}
	static void rotate_3() {
		//0오 5왼 6오 3오
		rotate(3);
		char[]backup=new char[] {cube[0][0][2],cube[0][1][2],cube[0][2][2]};
		//4-5-2-0
		cube[0][0][2]=cube[2][0][2];
		cube[0][1][2]=cube[2][1][2];
		cube[0][2][2]=cube[2][2][2];

		cube[2][0][2]=cube[5][0][2];
		cube[2][1][2]=cube[5][1][2];
		cube[2][2][2]=cube[5][2][2];

		cube[5][0][2]=cube[4][2][0];
		cube[5][1][2]=cube[4][1][0];
		cube[5][2][2]=cube[4][0][0];
		
		cube[4][2][0]=backup[0];
		cube[4][1][0]=backup[1];
		cube[4][0][0]=backup[2];
	}
	static void rotate_4() {
		//1위 2왼 6아 4오
		rotate(4);
		char[]backup=new char[] {cube[0][0][0],cube[0][0][1],cube[0][0][2]};
		cube[0][0][0]=cube[3][0][2];
		cube[0][0][1]=cube[3][1][2];
		cube[0][0][2]=cube[3][2][2];
		
		cube[3][0][2]=cube[5][2][2];
		cube[3][1][2]=cube[5][2][1];
		cube[3][2][2]=cube[5][2][0];
		
		cube[5][2][2]=cube[1][2][0];
		cube[5][2][1]=cube[1][1][0];
		cube[5][2][0]=cube[1][0][0];
		
		cube[1][2][0]=backup[0];
		cube[1][1][0]=backup[1];
		cube[1][0][0]=backup[2];
	}
	static void rotate_5() {
		rotate(5);
		char[]backup=cube[1][2].clone();
		cube[1][2]=cube[4][2].clone();
		cube[4][2]=cube[3][2].clone();
		cube[3][2]=cube[2][2].clone();
		cube[2][2]=backup.clone();
	}
	
	static int pint(String s) {
		return Integer.parseInt(s);
	}
}