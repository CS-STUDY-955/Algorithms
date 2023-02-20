import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
//import java.util.Comparator;
import java.util.HashMap;

// 문제에서 좋아하고 싫어하는 게 같은 집단끼리 묶여야 한다고 했으니
// 해당 정보를 담은 스트링끼리 같으면 같은 집단으로 묶일 것이고
// 그럼 그 정보를 담은 스트링을 Key로 하는 Map을 구현하면 쉽게 풀릴거라 생각

// 안정된 집단
public class BJ_2653_안정된_집단 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		// String[] aa = new String[n];
		char[][] like = new char[n][n];
		// 입력 사이사이에 띄어쓰기가 들어가 있으므로 모두 제거하고 한번에 저장
		for (int i = 0; i < n; i++) {
			like[i] = br.readLine().replaceAll(" ", "").toCharArray();
			// aa[i] = br.readLine();
		}

		// Key: 호불호 정보가 담긴 스트링
		// Value: 해당 호불호를 가진 사람들의 인덱스를 담은 arraylist
		HashMap<String, ArrayList<Integer>> hobulho = new HashMap<>();
		for (int i = 0; i < n; i++) {
			// char[]를 String으로 변경
			String temp = String.valueOf(like[i]);
			// 해당 키값이 있으면 그 list에 저장
			if (hobulho.get(temp) != null) {
			// if (hobulho.contains(temp)) {
				hobulho.get(temp).add(i + 1);
				continue;
			}
			// 없다면 새로 list를 생성하고 저장
			hobulho.put(temp, new ArrayList<>());
			hobulho.get(temp).add(i + 1);
		}

		// 순서를 부여하는 겸, 사이즈가 1인 list가 있는지 체크
		// 사이즈가 1인 list가 있으면 불안정한 집단이므로 0 출력하고 종료
		ArrayList<ArrayList<Integer>> result = new ArrayList<>();

		for (ArrayList<Integer> arr : hobulho.values()) {
			if (arr.size() <= 1) {
				System.out.println(0);
				return;
			}
			result.add(arr);
		}

		// result를 문제에서 요구한대로 정렬
		// 람다 표현식을 사용하는 경우
		Collections.sort(result, (o1, o2) -> o1.get(0) - o2.get(0));
//		익명 메서드를 사용하는 경우
//		Collections.sort(result, new Comparator<ArrayList<Integer>>() {
//			@Override
//			public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
//				return o1.get(0) - o2.get(0);
//			}
//		});
		
		// 문제에서 요구한대로 출력
		System.out.println(result.size());
		for (int i = 0; i < result.size(); i++) {
			for (int j = 0; j < result.get(i).size(); j++) {
				System.out.print(result.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}
}