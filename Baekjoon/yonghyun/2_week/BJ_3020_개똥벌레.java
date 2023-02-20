import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 개똥벌레
 * https://www.acmicpc.net/problem/3020
 *
 * 1. 번갈아가면서 종유석(아래서 난것)과 석순(위에서 난것)이 등장하므로 이를 고려하여 각 구간마다 장애물이 몇 개 존재하는지 기록한다.
 * 2. 각 구간을 순회하며 최솟값과 그러한 구간의 개수를 저장한다.
 * 3. 입력을 처리하는데 O(N*H), 순회하며 최솟값을 구하는데 O(H)가 소요되므로 O(N*H)로 시간이 초과될 것이다.
 * ----------------------------------------------
 * 1. 종유석과 석순이 각각 크기별로 얼마나 등장했는지 배열로 저장한다. O(N)
 * 2. 배열을 순회하며 각 구간별로 몇 개의 장애물이 존재하는지 계산한다. O(H)
 * 3. 배열을 다시 순회하며 최소 장애물의 구간과 그 개수를 계산한다. O(H)
 * 4. 이를 이용하면 O(H)로 해결할 수 있다.
 * - 약 1시간 고민해서 못 풀었고, 찾아보니 누적합이라는 알고리즘을 사용했다.
 *
 */
public class BJ_3020_개똥벌레 {
    public static void main(String[] args) throws Exception {
        // 입력 처리 & 지역 변수 선언
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        int N = Integer.parseInt(input[0]);     // 동굴의 길이(장애물의 개수) N
        int H = Integer.parseInt(input[1]);     // 동굴의 높이(구간의 개수) H
        int[] top = new int[H+1];       // 종유석의 정보가 들어갈 배열
        int[] bot = new int[H+1];     // 석순의 정보가 들어갈 배열
        for(int i=0; i<N/2; i++) {        // N번의 입력동안 장애물의 정보가 주어진다.
            bot[Integer.parseInt(br.readLine())]++;     // 해당 위치까지 도달하는 석순의 개수를 센다.
            top[H-Integer.parseInt(br.readLine())+1]++;       // 해당 위치까지 도달하는 종유석의 개수를 센다.
        }
        int min = Integer.MAX_VALUE;        // 최솟값을 저장할 변수 min
        int cnt = 0;        // 최솟값을 가지는 구간의 개수를 저장할 cnt

        // 로직 구현
        for(int i=H-1; i>0; i--) {       // 각 구간마다 석순이 몇 개 존재하는지 구한다.
            bot[i] += bot[i+1];     // 석순이 위에 있으면 그 아래는 반드시 석순이 있다.
        }
        for(int i=2; i<=H; i++) {       // 각 구간마다 석순이 몇 개 존재하는지 구한다.
            top[i] += top[i-1];     // 종유석이 아래에 있으면 그 위는 반드시 종유석이 있다.
        }
        for(int i=1; i<=H; i++) {       // 각 구간을 순회하며 장애물의 정보를 확인한다.
            int num = top[i] + bot[i];      // 해당 위치에 존재하는 종유석과 석순을 더한 것이 장애물의 개수이다.
            if(num<min) {       // 현재 구간의 장애물의 수가 현재까지 중 가장 작으면
                min = num;      // 장애물 개수로 최소값을 갱신
                cnt = 1;        // 개수는 1로 갱신
            } else if(num==min) {       // 장애물의 수가 같으면
                cnt++;      // 개수 1추가
            }
        }

        // 출력 처리
        System.out.println(min + " " + cnt);
    }
}