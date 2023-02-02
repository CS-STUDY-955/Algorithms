package com.baekjoon.algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

/** 백준 9205. 맥주 마시면서 걸어가기
 * 1. 상근이의 집에서 락 페스티벌까지 맥주를 마시면서 걸어감
 * 2. 맥주는 최대 20병까지 들 수 있고 50m마다 1병을 마셔야함
 * 3. 맥주는 편의점에서 재충전할 수 있고 편의점에서 나오면 1병을 마셔야함
 * 4. 거리는 맨해튼 거리
 * 5. 락 페스티벌을 갈수 있는가 없는가?
 * 
 * 풀이전 풀이법 생각
 * 1. 현재 위치에서 갈 수 있는 편의점들에 대해 모든 경우의 수를 생각해야 할것 같다.
 * 2. 각 편의점 별로 락페스티벌과의 거리가 x와 y값 모두 작은 편의점은 생각하지 않는다. // => 틀림
 * 3. 20병과 50m를 굳이 생각해야하나? 집에서 출발시, 편의점 도착시 1000m 이동 가능만 생각.
 *
 * 풀이전 생각한 알고리즘들
 * 1. DFS & 그리디
 *
 * 풀면서 생각한 풀이법1 (DFS & 그리디)
 * 1. 현재 위치에서 갈 수 있는 편의점들을 구하고
 * 2. 그 편의점들을 목적지와 가까운 순으로 정렬
 * 3. 정렬 순으로 DFS (1~3 반복)
 * > 정렬에 시간이 많이 걸릴거 같음
 * 
 * 풀면서 생각한 풀이법2 (트리 & BFS)
 * 1. 각 편의점마다 갈 수 있는 포인트를 구해서 출발지와 도착지가 연결되어있으면 ok
 * 2. 갈 수 있는 포인트들을 자식으로 하는 트리를 만들고
 * 3. BFS 탐색으로 도착지를 찾으면 happy 아니면 sad => Tree가 탐색이 더 빠름
 * > 각 포인트마다 모든 포인트를 비교하면 구하는데에 시간복잡도 O(n^2)...
 * > 트리를 만들때 부모 노드들은 제외하고 만드려고 했으나 -> 제외해도 시간복잡도는 여전히 O(n^2).. (O((n^2)/2))
 * 
 * 푼 방법
 * 1. 각 편의점마다 갈 수 있는 포인트를 구해서 저장
 * 2. 목적지(인덱스 N+1부터)를 루트로 BFS 탐색
 * 3. 중간에 0을 만나면 happy, 다 탐색해도 0을 만나지 못하면 sad
 * 
 * 어려웠던점
 * 1. 어떻게 풀어야 할지 (풀이법 생각만 3시간정도 걸린듯)
 * 2. 어떤 알고리즘을 선택해야 하는지 
 * 3. 어떤 자료 구조를 사용해야 하는지 (어떤 자료구조가 효율적인지)
 * @author 양진형
 */

public class P9205 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc = 1; tc <= T; tc++) {
			int N = Integer.parseInt(br.readLine());
			
			ArrayList<int[]> points = new ArrayList<int[]>(N+2);
			ArrayList<HashSet<Integer>> reachable = new ArrayList<HashSet<Integer>>(N+2);
			for (int i = 0; i < N + 2; i++) {
				int[] tmp_p = new int[2];
				HashSet<Integer> tmp_r = new HashSet<Integer>();
				tmp_p = Stream.of(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
				for(int j = 0; j < i; j++) {
					if(getDist(points.get(j), tmp_p) <= 1000) {
						tmp_r.add(j);
						reachable.get(j).add(i);
					}
				}
				points.add(tmp_p);
				reachable.add(tmp_r);
			}

			// BFS
			boolean end_flag = false;
			boolean[] visited = new boolean[N+2];
			Queue<Integer> q = new LinkedList<Integer>();
			int v = N + 1;
			visited[v] = true;
			q.add(v);
			while(q.size() != 0) {
				v = q.poll();
				
				Iterator<Integer> iter = reachable.get(v).iterator();
				while(iter.hasNext()) {
					int w = iter.next();
					if(w == 0) {
						end_flag = true;
						break;
					}
					if(!visited[w]) {
						visited[w] = true;
						q.add(w);
					}
				}
				if(end_flag) {
					break;
				}
			} // BFS end
			
			if(end_flag) {
				System.out.println("happy");
			} else {
				System.out.println("sad");
			}
			
		}
	}
	
	public static int getDist(int[] A, int[] B) {
		return Math.abs(A[0] - B[0]) + Math.abs(A[1] - B[1]);
	}
}
