package gold3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class BJ_11779_최소비용구하기2 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		int n = Integer.parseInt(br.readLine());
		int m = Integer.parseInt(br.readLine());
		
		// 인접 행렬 -1로 초기화
		int[][] adjMatrix = new int[n+1][n+1];
		for(int i = 1; i <= n; i++) {
			for(int j = 1; j <= n; j++) {
				adjMatrix[i][j] = -1;
			}
		}
		
		// 인접 행렬 입력 받기
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int dist = Integer.parseInt(st.nextToken());
			if(adjMatrix[from][to] == -1 || adjMatrix[from][to] > dist) {
				adjMatrix[from][to] = dist;
			}
		}
		// 시작 끝 입력 받기
		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());
		
		int[] distance = new int[n+1]; // distance 배열
		int[] before = new int[n+1]; // 이전 노드 번호 저장
		boolean[] visited = new boolean[n+1]; // 방문 배열
		Arrays.fill(distance, Integer.MAX_VALUE); // 모두 갈수 없음으로 초기화
		distance[start] = 0; // 시작 노드 거리 0으로 초기화
		
		int min, current; // 최소거리, 현재 노드
		for(int c = 1; c <= n; c++) {
			current = -1;
			min = Integer.MAX_VALUE;
			
			for(int i = 1; i <= n; i++) { // 방문하지 않은 노드 중 최소 거리 갱신
				if(!visited[i] && min > distance[i]) {
					min = distance[i];
					current = i;
				}
			}
			
			if(current == -1) break; // 더이상 방문할수 있는 노드가 없다면 탈출
			visited[current] = true; // 방문처리
			
			
			for(int j = 1; j <= n; j++) { // distance, before 배열 갱신
				if(!visited[j] && adjMatrix[current][j] != -1 && distance[j] > min+adjMatrix[current][j]) {
					distance[j] = min+adjMatrix[current][j];
					before[j] = current;
				}
			}
		}
		Stack<Integer> stack = new Stack<>();
		int k = end; // 도착점부터 역순으로 탐색
		while(true) {
			stack.push(k);
			if(k == start) break; // 시작지점이라면 탈출
			k = before[k];
		}
		// 출력
		sb.append(distance[end]).append("\n").append(stack.size()).append("\n");
		while(!stack.isEmpty()) sb.append(stack.pop()).append(" ");
		System.out.println(sb);
	}
}
