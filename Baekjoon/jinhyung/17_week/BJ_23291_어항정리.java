package platinum5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_23291_어항정리 {
	
	static int N, diff, height, next, colstart; // 어항 수, 최대최소 차이, 현재 쌓은 어항 높이, 다음 어항의 열 인덱스, 현재 어항의 열 인덱스
	static int[][] fishbowl; // 어항
	
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, -1, 0, 1};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		fishbowl = new int[N][N];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++) { // 최대 최소 구하기
			fishbowl[0][i] = Integer.parseInt(st.nextToken());
			if(min > fishbowl[0][i]) min = fishbowl[0][i];
			if(max < fishbowl[0][i]) max = fishbowl[0][i];
		}
		int time = 0; // 정리한 횟수
		diff = max - min;
		while(diff > K) { // 최대최소 차이가 K 이하가 될때까지 arrange()
			time++;
			arrange();
		}
		
		System.out.println(time);
	}

	private static void arrange() {
		add(); // 물고기가 가장 적은 어항에 추가하기
		stacking(); // 공중부양시켜서 쌓기
		adjust(); // 물고기 조정 후 정렬하기
		stacking2(); // 어항 접기
		adjust(); // 물고기 조정 후 정렬하기
		
		// 최대 최소 차이 갱신
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for(int i = 0; i < N; i++) {
			if(min > fishbowl[0][i]) min = fishbowl[0][i];
			if(max < fishbowl[0][i]) max = fishbowl[0][i];
		}
		diff = max - min;
	}

	private static void add() {
		int min = Integer.MAX_VALUE;
		ArrayList<Integer> minidx = new ArrayList<>();
		for(int i = 0; i < N; i++) {
			if(fishbowl[0][i] > min) continue;
			if(fishbowl[0][i] < min) {
				min = fishbowl[0][i];
				minidx.clear();
			}
			minidx.add(i);
		}
		// 물고기가 가장 적은 어항들에 물고기 한마리씩 넣기
		for(int idx : minidx) fishbowl[0][idx]++;
	}
	
	private static void stacking() {
		// 맨 왼쪽 한개 쌓기
		fishbowl[1][1] = fishbowl[0][0];
		fishbowl[0][0] = 0;
		height = 2; // 현재 쌓아올린 어항의 높이
		int len = N-2; // 바닥으로 쓸 수 있는 남은어항 갯수
		
		next = 2; // 바닥으로 사용할 어항의 열 인덱스
		boolean getHigh = false;
		// stacked: 공중부양 시킬 어항 열 인덱스
		ArrayDeque<Integer> stacked = new ArrayDeque<>();
		stacked.add(1);
		while(len >= height) {
			colstart = next; // 현재 어항 시작 열 인덱스 저장
			int K = 0;
			while(!stacked.isEmpty()) { // 쌓기
				K++;
				int idx = stacked.pop();
				for(int i = 0; i < height; i++) {
					fishbowl[K][next+i] = fishbowl[i][idx];
					fishbowl[i][idx] = 0;
				}
			}
			// 다음 어항 추가하기
			for(int i = next; i < next+height; i++) stacked.push(i);
			next += height;
			len -= height;
			if(getHigh) height++; // 2번 실행할때마다 높이 1 증가
			getHigh = !getHigh;
		}
	}
	
	// 물고기 조정하기
	private static void adjust() {
		boolean[][] visited = new boolean[N][N];
		int[][] adjvalue = new int[N][N]; // 조정할 물고기 값
		for(int r = 0; r < height; r++) {
			for(int c = colstart; c < N; c++) {
				if(visited[r][c] || fishbowl[r][c] == 0) continue;
				visited[r][c] = true;
				
				for(int i = 0; i < 4; i++) {
					int nr = r + dx[i];
					int nc = c + dy[i];
					
					if(nr < 0 || nr >= N || nc < 0 || nc >= N || visited[nr][nc] || fishbowl[nr][nc] == 0) continue;
					int value = Math.abs(fishbowl[r][c] - fishbowl[nr][nc]) / 5;
					if(value == 0) continue;
					if(fishbowl[r][c] > fishbowl[nr][nc]) {
						adjvalue[r][c] -= value;
						adjvalue[nr][nc] += value;
					} else {
						adjvalue[r][c] += value;
						adjvalue[nr][nc] -= value;
					}
				}
			}
		}
		
		// 물고기 조정하기
		for(int r = 0; r < height; r++) {
			for(int c = colstart; c < N; c++) {
				fishbowl[r][c] += adjvalue[r][c];
			}
		}
		
		// 어항 한줄로 돌려놓기
		int k = 0;
		for(int c = colstart; c < next; c++) {
			for(int r = 0; r < height; r++) {
				fishbowl[0][k++] = fishbowl[r][c];
				fishbowl[r][c] = 0;
			}
		}
	}
	
	// 접기
	private static void stacking2() {
		next = N;
		height = 4;
		colstart = N - N / 4;
		for(int i = 0; i < N/4; i++) {
			fishbowl[1][colstart+i] = fishbowl[0][N/4-1-i];
			fishbowl[0][N/4-1-i] = 0;
			fishbowl[2][colstart+i] = fishbowl[0][N/4+i];
			fishbowl[0][N/4+i] = 0;
			fishbowl[3][colstart+i] = fishbowl[0][colstart-1-i];
			fishbowl[0][colstart-1-i] = 0;
		}
	}
}
