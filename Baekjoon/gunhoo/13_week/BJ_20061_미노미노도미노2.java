package Gold.Gold2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_20061_미노미노도미노2 {
	static int N, green[][], blue[][], blue_cnt, green_cnt, score;

	public static void main(String[] args) throws Exception {
		init();
		count_box();
		System.out.println(score);
		System.out.println(blue_cnt+green_cnt);
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.valueOf(st.nextToken());
		int r, c, t;
		green = new int[7][4];
		blue = new int[7][4];
		blue_cnt = 0;
		green_cnt = 0;
		score = 0;
		// 끝줄은 1로 초기화
		for (int j = 0; j < 4; j++) {
			green[6][j] = 1;
			blue[6][j] = 1;
		}
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			t = Integer.valueOf(st.nextToken());
			r = Integer.valueOf(st.nextToken());
			c = Integer.valueOf(st.nextToken());
			green(t, r, c);
			blue(t, r, c);
			line_check(green);
			line_check(blue);
			line_top_check(green);
			line_top_check(blue);
		}
	}

	static void green(int t, int r, int c) {
		down(green, t, c);
	}

	static void blue(int t, int r, int c) {
		if (t == 2)
			down(blue, 3, r);
		else if (t == 3)
			down(blue, 2, r);
		else
			down(blue, t, r);
	}

	static void down(int map[][], int t, int c) {
		if (t == 1) {
			for (int i = 1; i + 1 < 7; i++) {
				if (map[i + 1][c] == 1) {
					map[i][c] = 1;
					break;
				}
			}
		} else if (t == 2) {
			for (int i = 1; i + 1 < 7; i++) {
				if (map[i + 1][c] == 1 || map[i + 1][c + 1] == 1) {
					map[i][c] = 1;
					map[i][c + 1] = 1;
					break;
				}
			}
		} else if (t == 3) {
			for (int i = 0; i + 2 < 7; i++) {
				if (map[i + 2][c] == 1) {
					map[i][c] = 1;
					map[i + 1][c] = 1;
					break;
				}
			}
		}
	}

	static void line_check(int map[][]) {
		for (int i = 0; i<6; i++) {
			int cnt = 0;
			for (int j = 0; j < 4; j++) {
				if (map[i][j] == 1)
					cnt++;
				else
					break;
			}
			if (cnt == 4) {
				delete_line(map, i);
				score++;
			}
		}
	}

	static void delete_line(int map[][], int line_num) {
		for (int i = line_num; i - 1 >= 0; i--) {
			for (int j = 0; j < 4; j++) {
				map[i][j] = map[i-1][j];
			}
		}
		for(int j=0; j<4; j++) {
			map[0][j]= 0;
		}
	}

	static void line_top_check(int map[][]) {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				if (map[1][j] == 1) {
					delete_line(map, 5);
					break;
				}
			}
		}
	}

	static void count_box() {
		for (int i = 2; i < 6; i++) {
			for (int j = 0; j < 4; j++) {
				if (green[i][j] == 1) green_cnt++;
				if (blue[i][j] == 1) blue_cnt++;
			}
		}
	}
	
}