package gold2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class BJ_2515_전시장 {
	
	static class Paint {
		int height, cost; // 그림의 높이와 가격

		public Paint(int height, int cost) {
			this.height = height;
			this.cost = cost;
		}
	}
	
	static Paint[] sorted; // 병합 정렬용 임시 배열
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // 그림 수
		int S = Integer.parseInt(st.nextToken()); // 보여야 하는 최소 길이
		
		Paint[] paints = new Paint[N]; // 그림 입력 받기
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int height = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			paints[i] = new Paint(height, cost);
		}
		
		sorted = new Paint[N];
		mergesort(paints, 0, N-1); // 병합 정렬(높이순 오름차순 /같다면 가격순 내림차순)

		// 높이 순 오름차순 정렬한 TreeSet
		// TreeSet 사용 이유
		// 1. 바로 앞에 놓일 수 있는 그림을 가져오기 위해
		// 2. 높이가 같은 그림 처리 (Set -> 높이가 같은 그림이 이미 들어가 있다면 무시)
		TreeSet<Paint> memo = new TreeSet<Paint>(new Comparator<Paint>() {
			@Override
			public int compare(Paint o1, Paint o2) {
				return o1.height - o2.height;
			}
		});

		int max = 0; // 판매 가능 그림들의 가격 합 최대
		for(int i = 0; i < N; i++) { // 높이가 낮은 그림부터 순서대로 선택
			Paint fp = memo.floor(paints[i]); // 선택한 그림(paints[i])을 보이게 놓을 수 있게하는 그림 중 가장 큰 그림 선택
			if(fp == null) { // 그런 그림이 없다면
				paints[i].height += S; // 다음 그림을 위해 S값 높이에 더해주기
				if(max < paints[i].cost) { // 선택한 그림의 가격이 현재 최대값보다 크다면
					max = paints[i].cost; // 최대값 갱신
					memo.add(paints[i]); // TreeSet에 넣어주기
				}
			} else { // fp가 있다면
				paints[i].height += S; // 다음 그림을 위해 S값 높이에 더해주기
				if(max < paints[i].cost + fp.cost) { // 선택한 그림 가격 + 그 앞의 그림들의 가격 합이 최대값보다 크다면
					paints[i].cost += fp.cost; // 선택한 그림에 가격 누적
					max = paints[i].cost; // 최대값 갱신
					memo.add(paints[i]); // TreeSet에 넣어주기
				}
			}	
		}
		System.out.println(max); // 출력
	}

	private static void mergesort(Paint[] paints, int left, int right) {
		int mid;
		
		if(left < right) {
			mid = (left+right)/2;
			mergesort(paints, left, mid);
			mergesort(paints, mid+1, right);
			merge(paints, left, mid, right); // 병합
		}
	}

	private static void merge(Paint[] paints, int left, int mid, int right) {
		int l = left;
		int r = mid + 1;
		int k = left;
		
		while(l <= mid && r <= right) {
			if(paints[l].height == paints[r].height) { // 높이가 같은 경우
				if(paints[l].cost >= paints[r].cost) { // 가격순으로 정렬
					sorted[k++] = paints[l++];
				} else {
					sorted[k++] = paints[r++];
				}
			}
			else if(paints[l].height <= paints[r].height) {
				sorted[k++] = paints[l++];
			} else {
				sorted[k++] = paints[r++];
			}
		}
		
		if(l > mid) {
			for(int i = r; i <= right; i++) {
				sorted[k++] = paints[i];
			}
		} else {
			for(int i = l; i <= mid; i++) {
				sorted[k++] = paints[i];
			}
		}
		
		// 임시 배열 값 옮겨주기
		for(int i = left; i <= right; i++) {
			paints[i] = sorted[i];
		}
	}
}
