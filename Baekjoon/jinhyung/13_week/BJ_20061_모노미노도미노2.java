package gold2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_20061_모노미노도미노2 {
	
	static int idx, repeat, score = 0;
	static int[] b, g, btop, gtop;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int N = Integer.parseInt(br.readLine());
		b = new int[] {0, 0, 0, 0}; // 파란칸의 각 행 별 블록 상태
		g = new int[] {0, 0, 0, 0}; // 초록칸의 각 행 별 블록 상태
		btop = new int[] {0, 0, 0, 0}; // 파란칸의 각 행 별 블록 높이
		gtop = new int[] {0, 0, 0, 0}; // 초록칸의 각 행 별 블록 높이
		
		// 입력 받으며 블록 놓기
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int t = Integer.parseInt(st.nextToken()); // 블록 종류
			int x = Integer.parseInt(st.nextToken()); // 블록을 놓을 빨간 칸의 x좌표
			int y = Integer.parseInt(st.nextToken()); // y좌표
			blue(t, x); // 블록 놓기
			green(t, y);
		}
		
		int count = 0; // 총 개수 세기
		for(int i = 0; i < 4; i++) {
			count += Integer.bitCount(b[i]);
			count += Integer.bitCount(g[i]);
		}

		System.out.printf("%d\n%d", score, count); // 출력
	}

	private static void green(int t, int y) {
		idx = -1; repeat = 1; // 블록이 지워지는 경우 지워지는 열 번호(idx), 지워지는 열 개수(repeat)
		switch(t) {
		case 1: // 1x1 블록
			g[y] |= (1<<gtop[y]); // 블록 놓기
			check(g, gtop[y]); // 블록이 사라지는지 검사
			gtop[y]++; // 블록 높이 갱신
			break;
		case 2: // 1x2 블록
			int h = gtop[y] > gtop[y+1] ? y : y+1; // 두 행중 더 높은 행의 인덱스 h
			int hcol = gtop[h]; // 블록 놓을 열
			g[y] |= (1<<hcol); // 블록 놓기
			g[y+1] |= (1<<hcol); // 블록 놓기
			check(g, hcol); // 블록이 사라지는지 검사
			gtop[y] = hcol+1; // 블록 높이 갱신
			gtop[y+1] = hcol+1;
			break;
		case 3: // 2x1블록
			g[y] |= (3<<gtop[y]); // 블록 놓기
			check(g, gtop[y]); // 블록이 사라지는지 검사
			check(g, gtop[y]+1);
			gtop[y] += 2; // 블록 높이 갱신
			break;
		}
		
		// idx가 -1이 아니라면 사라짐
		if(idx != -1) remove(g, gtop);
		int top = 0; // 가장 높은 행 검사
		for(int i = 0; i < 4; i++) {
			if(gtop[i] >= 5) { // 높이가 5 이상인 경우만 검사 
				top = top > gtop[i] ? top : gtop[i];
			}
		}
		if(top != 0) { // 높이가 5 이상인 행이 있다면
			for(int i = 0; i < 4; i++) {
				int move = top - 4;
				g[i] >>= move; // 그 높이에서 4를 뺀만큼 이동
				gtop[i] = 0 > gtop[i] - move ? 0 : gtop[i] - move; // 모든 행의 top 갱신
			}
		}
	}

	private static void blue(int t, int x) {
		idx = -1; repeat = 1;
		switch(t) {
		case 1:
			b[x] |= (1<<btop[x]);
			check(b, btop[x]);
			btop[x]++;
			break;
		case 2:
			b[x] |= (3<<btop[x]);
			check(b, btop[x]);
			check(b, btop[x]+1);
			btop[x] += 2;
			break;
		case 3:
			int h = btop[x] > btop[x+1] ? x : x+1;
			int hcol = btop[h];
			b[x] |= (1<<hcol);
			b[x+1] |= (1<<hcol);
			check(b, hcol);
			btop[x] = hcol+1;
			btop[x+1] = hcol+1;
			break;
		}
		
		if(idx != -1) remove(b, btop);
		int top = 0;
		for(int i = 0; i < 4; i++) {
			if(btop[i] >= 5) {
				top = top > btop[i] ? top : btop[i];
			}
		}
		if(top != 0) {
			for(int i = 0; i < 4; i++) {
				int move = top - 4;
				b[i] >>= move;
				btop[i] = 0 > btop[i] - move ? 0 : btop[i] - move;
			}
		}
	}

	private static void check(int[] board, int col) {
		for(int i = 0; i < 4; i++) {
			// 검사할 열(col)중 하나라도 0이 있다면 종료
			if((board[i] & (1<<col)) == 0) return;
		}
		
		// 4행 모두 0이라면
		if(idx == -1) idx = col; // 처음인경우 col로 갱신
		else repeat = 2; // 두개가 한번에 사라지는 경우 repeat = 2
		
		score++; // 사라지는 경우이므로 score+1
	}

	private static void remove(int[] board, int[] top) { // 삭제
		for(int i = 0; i < 4; i++) {
			// 사라질 열 기준 앞 열(인덱스가 작은 열)은 그대로, 뒷 열은 repeat만큼 삭제
			board[i] = (board[i] % (1<<idx)) + ((board[i]>>(idx+repeat))<<(idx));
			for(int j = top[i]; j >= -1; j--) { // top 갱신
				if((board[i] & (1<<j)) != 0 || j == -1) {
					top[i] = j+1;
					break;
				}
			}
		}
	}
}