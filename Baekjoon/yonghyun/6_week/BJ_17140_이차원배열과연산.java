import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 이차원 배열과 연산
 * https://www.acmicpc.net/problem/17140
 *
 * 1. 행과 열의 개수를 체크하고 연산의 종류를 정한다.
 * 2. 한 줄에 각 수가 몇번 나왔는지 센다. -> 변수로 관리
 * 3. 숫자와 등장횟수를 배열로 묶어 ArrayList에 넣는다.
 * 4. 등장횟수, 숫자의 오름차순으로 ArrayList를 정렬한다.
 * 5. ArrayList에 들어가있는 순서대로 배열의 값을 갱신한다. (나머지 공간은 0으로 채운다.)
 * 6. 모든 행 또는 열에 대해 2~5를 수행한다.
 * 7. cnt가 최대 100에 도달할때까지 수행하고, 정답이 나오면 k를, 정답이 나오지 않으면 -1을 출력한다.
 *
 * @author 배용현
 */
public class BJ_17140_이차원배열과연산 {
    // 클래스를 만들어 사용하려면 여기에 선언한다.
    static class MyComparator implements Comparator<int[]> {        // Comparator 2번 사용하므로 따로 선언

        @Override
        public int compare(int[] o1, int[] o2) {
            if(o1[1]==o2[1])        // 등장횟수가 같으면
                return o1[0] - o2[0];       // 숫자 오름차순
            else        // 등장횟수가 다르면
                return o1[1] - o2[1];       // 등장횟수 오름차순
        }
    }

    // 공통으로 사용될 것 같은 변수는 static 멤버변수로 작성한다.
    static int r, c, k, rowSize = 3, colSize = 3;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] map = new int[100][100];

    public static void main(String[] args) throws IOException {
        // 입력 처리 & 지역 변수 선언
        st = new StringTokenizer(br.readLine());
        r = parseInt(st.nextToken()) - 1;
        c = parseInt(st.nextToken()) - 1;
        k = parseInt(st.nextToken());
        for (int i = 0; i < 3; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++)
                map[i][j] = parseInt(st.nextToken());
        }

        // 로직 구현
        for (int sec = 0; sec < 101; sec++) {       // 100까지 만족하지 못하면 -1 출력
            if (map[r][c] == k) {
                System.out.println(sec);
                return;
            }

            if(rowSize>=colSize) {      // 행의 크기가 더 크거나 같으면
                colSize = 0;
                for (int i = 0; i < map.length; i++) {      // 모든 행에 대해 수행

                    ArrayList<int[]> nums = calNums(true, i);      // 행 여부와 인덱스를 넘겨 숫자 변경 정보를 얻음
                    nums.sort(new MyComparator());

                    int newSize = setRow(nums, i);       // 배열 갱신
                    colSize = Math.max(colSize, newSize);       // 배열 크기 저장
                }
            } else {      // 열의 크기가 더 크면
                rowSize = 0;
                for (int i = 0; i < map.length; i++) {      // 모든 열에 대해 수행

                    ArrayList<int[]> nums = calNums(false, i);      // 열 여부와 인덱스를 넘겨 숫자 변경 정보를 얻음
                    nums.sort(new MyComparator());

                    int newSize = setCol(nums, i);       // 배열 갱신
                    rowSize = Math.max(rowSize, newSize);       // 배열 크기 저장
                }
            }
        }

        // 출력 처리
        System.out.println(-1);

    }

    private static int setCol(ArrayList<int[]> nums, int idx) {
        for (int i = 0, j = 0; i < map.length; i++) {       // i: 배열 값 갱신용, j: ArrayList 원소 접근용
            if (j<nums.size()) {
                map[i++][idx] = nums.get(j)[0];
                map[i][idx] = nums.get(j++)[1];
            } else {
                map[i][idx] = 0;
            }
        }

        return Math.min(nums.size() * 2, 100);      // 열에 들어있는 데이터의 수 리턴
    }

    private static int setRow(ArrayList<int[]> nums, int idx) {
        for (int i = 0, j = 0; i < map.length; i++) {       // i: 배열 값 갱신용, j: ArrayList 원소 접근용
            if (j<nums.size()) {
                map[idx][i++] = nums.get(j)[0];
                map[idx][i] = nums.get(j++)[1];
            } else {
                map[idx][i] = 0;
            }
        }

        return Math.min(nums.size() * 2, 100);      // 행에 들어있는 데이터의 수 리턴
    }

    private static ArrayList<int[]> calNums(boolean isRow, int idx) {        // 각 숫자의 등장횟수를 세는 메서드
        ArrayList<int[]> nums = new ArrayList<>();
        HashMap<Integer, Integer> hashMap = new HashMap<>();        // 각 숫자가 얼마나 등장했는지 체크
        if(isRow) {     // 행을 세야하면
            for (int i = 0; i < map.length; i++) {
                if(map[idx][i]==0)        // 행 값이 0이면 무시
                    continue;

                hashMap.put(map[idx][i], hashMap.getOrDefault(map[idx][i], 0)+1);     // 해시맵에 기록
            }
        } else {        // 열을 세야하면
            for (int i = 0; i < map.length; i++) {
                if(map[i][idx]==0)        // 열 값이 0이면 무시
                    continue;

                hashMap.put(map[i][idx], hashMap.getOrDefault(map[i][idx], 0)+1);     // 해시맵에 기록
            }
        }

        hashMap.forEach((k, v) -> nums.add(new int[]{k, v}));       // hashMap에 정리했던 데이터를 arrayList로 변경

        return nums;
    }
}

