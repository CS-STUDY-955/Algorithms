package Gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
/**
 * 풀이시간 11:00 ~ 11:30 ?? 구현x 입력
 * 1. 문제풀이 방법
 *     - 1) 2D 배열? -> 배열 크기 고려하면 x
 *     - 2) 이분탐색
 *  
 *  11:40~ 문제 유형 확인 : 이분탐색, 누적합
 *  ~12:26 모두 더하고 ans 이분탐색? -> 시간초과
 *  ~12:33 정렬된 arr니까 leftIdx 구할필요 없어서 삭제 -> 시간초과
 *  ~12:47 2중 for문 시간 복잡도 높아서 삭제, ArraySort, 이분탐색 이용 확인 -> 정답
 *  총 풀이시간 : 1시간 47분 : 40분(헤맴) + 46분(문제유형확인, 헤맴) + 21분(효율성 향상)
 *  
 * 2. 개선사항
 *  	- 1) 누적합 유형 공부 필요
 *  	- 2) 이분탐색 구현 체득화
 *  	- 3) 
 * 
 * @author Gunhoo
 *
 */
public class BJ_3020 {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] line = br.readLine().split(" ");
		int n = Integer.parseInt(line[0]);
		int h = Integer.parseInt(line[1]);
		int[] sucksoon = new int[n/2];
		int[] jongyoo = new int[n/2];
		int wall = n;
		int cnt = 0;
		
		for(int t = 0; t<n/2; t++) {
			sucksoon[t] = Integer.parseInt(br.readLine());
			jongyoo[t] = Integer.parseInt(br.readLine());
		}
		
		int[] ans = new int[h];
		/* 최초 시도했던 풀이
		for(int i = 1 ; i <= h; i++) {
			for(int j = 0; j< n/2 ; j++) {
				if ((sucksoon[j]/i) >= 1) {
					ans[i-1] += 1;
				}
				if (((jongyoo[j]+i-1)/h) >= 1) {
					ans[i-1] += 1;
				}
			}
		}*/
		// 이분탐색은 정렬된 arr에서 적용 가능
		Arrays.sort(sucksoon);
		Arrays.sort(jongyoo);
		
		for(int i = 1 ; i <= h; i++) {
			// 각 층에는 우선 최대 n개 장애물에서 석순의 높이 이상(left)인 것들을 뺴주고, 종유석 높이가 낮아(left) 통과가 안되는 장애물을 빼준다.
			ans[i-1] = n-leftIdx(sucksoon, 0, sucksoon.length, i)-leftIdx(jongyoo,0,jongyoo.length, h-i+1);
		}
		// ans 정렬하고 이분탐색
		Arrays.sort(ans);
		System.out.println(ans[0]+" "+rightIdx(ans, 0, ans.length, ans[0]));
		
	}
	// 배열과 찾을 값을 받아 가장 왼쪽의 idx를 넘겨주는 메서드
	private static int leftIdx(int[] arr, int start, int end, int findingNum) {
		while(start<end) {
			int mid = (start+end)/2;
			if(arr[mid] >= findingNum) end = mid;
			else start = mid+1;
		}
		return end;
	}
	// 배열과 찾을 값을 받아 가장 오른쪽의 idx를 넘겨주는 메서드
	private static int rightIdx(int[] arr, int start, int end, int findingNum) {
		while(start<end) {
			int mid = (start+end)/2;
			if(arr[mid] > findingNum) end = mid;
			else start = mid+1;
		}
		return end;
	}

}
