package com.baekjoon.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/** P15686. 치킨 배달
 * 문제 이해 : 13:53 ~ 14:00
 * 
 * 풀기 전 생각한 풀이법 (완전 탐색)
 * 1. 각 치킨집의 모든 집까지의 치킨 거리의 합을 저장
 * 2. 치킨 거리가 가장 큰 순서대로 삭제할 만큼 삭제
 * 3. 각 집의 치킨 거리 합 출력
 * 4. 어떤 알고리즘을 사용해야하는 지는 모르겟음
 * 
 * 풀면서 생각 한 것
 * 1. 지도의 최대 크기가 50x50 => 2500, 치킨집은 최대 13개
 * 	>> 치킨집의 삭제가 많이 일어날 것임
 * 	>> 모든 경우의 수를 비교하는 것은 시간이 오래 걸릴 것
 * 2. 치킨 집별로 거리가 1, 2, 3,.. 인 집의 개수를 정리해서 경우의 수를 비교하는 방법이 좋을 듯
 * 3. 전체 지도는 필요 없을 것 같음
 * 
 * 풀기전 생각한 풀이법으로 풀이
 * 1. 틀림
 * 2. 남길 치킨집을 구하는 알고리즘이 틀렸다고 생각됨
 * 3. 알고리즘 분류 확인 -> 구현/브루트포스/백트래킹 
 * 브루트 포스 : 해가 존재할 것으로 예상되는 모든 영역 전체 탐색
 * 	> DFS/BFS가 가장 기본적인 도구임
 * 백트래킹 : 해를 찾는 도중 해가 아니면 되돌아가서 해를 찾아가는 기법
 *  > 모든 경우의 수를 고려하는 알고리즘
 *  > 마찬가지로 DFS/BFS 사용
 ** 결론 : 모든 경우의 수를 고려하는 방식으로 다시 설계해야겠다
 * 
 * 틀리고 나서 생각한 풀이
 * 1. 치킨집을 M개만큼 남긴다.
 * 2. 치킨 거리를 계산
 * 3. 치킨 거리를 min 값으로 저장
 * 4. 다른 집합으로 치킨집을 M개만큼 남긴다.
 * 5. 치킨 거리를 계산
 * 6. 치킨 거리가 min보다 작으면 대입, 아니면 넘어감
 * 7. 모든 치킨집을 M개 남기는 모든 경우의 수에 4~6 반복
 * >> 근데 이렇게 하면 경우의 수가 너무 많아질 것 같음..
 * >> 치킨 거리가 가장 작은 치킨집부터 정렬하고 할까
 * >> 해당 치킨집이 최소 거리인 집의 수를 저장하고 그 순서대로 탐색
 * >> 그 값이 같다면 치킨집의 치킨거리 순서로 탐색
 * 
 * 문제 풀이 본 후 -> 조합이라는 키워드
 * 1. 최소거리와 치킨거리 순서 고려 안하고 전체 경우의 수(조합으로 뽑은)로 탐색
 * 
 * @author 양진형
 */
class Point {
	int x;
	int y;

	Point(int x, int y) {
		this.x = x;
		this.y = y;
	};
}

public class P15686 {

	static ArrayList<int[]> idxs; // 가능한 인덱스 조합 저장할 리스트
	static int idxN; // 총 idxN개에서
	static int idxM; // idxM개를 뽑을 것
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		// 집의 좌표
		ArrayList<Point> houses = new ArrayList<>();
		// 치킨집 좌표
		ArrayList<Point> chicken = new ArrayList<>();
		
		// 1. 집, 치킨집 좌표 입력 받기
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				int num = Integer.parseInt(st.nextToken());
				if(num == 1) { // 1은 집
					houses.add(new Point(i, j));
				} else if(num == 2) { // 2는 치킨집
					chicken.add(new Point(i, j));
				}
			}
		} // 1. end
		
		idxs = new ArrayList<int[]>();
		idxM = M;
		idxN = chicken.size();
		
		// 2. 치킨집 조합 고르기
		boolean[] visited = new boolean[idxN];
		getIdx(idxN, M, visited, 0);
		// 2. end
		
		int minDist = Integer.MAX_VALUE;
		
		// 3. 각 조합 별 치킨거리 계산하기
		for(int[] idx : idxs) { // 각 치킨집 조합 별로
			int total = 0;
			for(Point hp : houses) { // 각 집의 거리에서
				int minCD = Integer.MAX_VALUE;
				for(int i: idx) { // 가장 가까운 치킨집과의 치킨거리를 total에 더함
					int cd = chickenDist(chicken.get(i), hp);
					minCD = minCD < cd ? minCD : cd;
				}
				total += minCD;
			}
			minDist = minDist < total ? minDist : total; // 현재 조합과 여태 치킨거리중 최소값과 비교하여 최소값 저장
		} // 3. end
		
		System.out.println(minDist);
	}
	
	// 두 점의 치킨거리 계산
	public static int chickenDist(Point p1, Point p2) {
		return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y); 
	}
	
	// N개(치킨집 총 수)에서 M개 추출
	public static void getIdx(int N, int M, boolean[] visited, int idx) {
		if(M == 0) {
			int[] tmp = new int[idxM];
			int j = 0;
			for(int i = 0; i < visited.length; i++) {
				if(visited[i]) tmp[j++] = i;
				if(j == idxM) break;
			}
			idxs.add(tmp);
			
			return;
		}
		
		for(int i = idx; i < visited.length; i++) {
			visited[i] = true;
			getIdx(N - 1, M - 1, visited, ++idx);
			visited[i] = false;
		}
		
		return;
	}
}
