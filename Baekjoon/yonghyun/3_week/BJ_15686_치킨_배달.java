import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.ArrayList;

/**
 * 치킨 배달
 * https://www.acmicpc.net/problem/15686
 *
 * 1. 치킨집을 어디를 폐업시킬지 골라낸다. (조합)
 *   - 몇번째 치킨집을 살릴건지 치킨집의 개수를 크기로 가지는 배열로 만들고, 순회할때 인덱스를 계산해서 폐업시킨 치킨집은 건너뛴다.
 *   - 위의 과정을 통해 폐업한 치킨집을 0으로 바꾼 맵을 새로 만든다.
 *   - 조합을 완성했을 때 만든 맵을 이용해 bfs수행 로직을 넣으면 될 것 같다.
 * 2. 폐업이 진행된 마을에서 각 집의 치킨거리를 구한다.
 * 3. 가장 작은 도시의 치킨 거리를 저장한다.
 * 4. 치킨거리를 구하는 도중에 저장된 도시의 치킨 거리를 넘을 경우, 중단하고 1로 넘어간다.
 * 5. 1~4를 모든 경우의 수에 대해 진행한 뒤 저장된 가장 작은 도시의 치킨 거리를 출력한다.
 * 6. 치킨집을 고를 때 O(2^m), bfs를 수행할 때 O(n^2)이 소요된다.
 * 7. n=50, m=13을 대입하면 약 20,000,000으로 백트래킹을 구현하지 않아도 통과할 수 있을 것 같다.
 * ---------------------------------------------------
 * 1. 치킨집과 가정집을 배열이 아닌 List로 저장
 * 2. 각 치킨집을 기준으로 구한 각 가정집 까지의 치킨 거리를 2차원 배열로 저장
 * 3. 조합을 이용해 유효한 치킨 거리만 선정하여 계산
 * 4. 치킨집을 고르는 조합은 O(2^m), 치킨 거리를 구하는 완전탐색은 O(n*m)이므로 약 5,000,000번의 연산을 수행한다.
 */
public class BJ_15686_치킨_배달 {
    static class Point {       // Point 클래스를 만들어 좌표를 저장
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static int M;       // 살아남을 치킨집의 개수
    static ArrayList<Point> stores = new ArrayList<>();     // 치킨집 좌표 저장
    static ArrayList<Point> homes = new ArrayList<>();      // 집 좌표 저장
    static int[][] dist;        // 각 치킨집에서 모든 집까지의 치킨 거리 저장
    static boolean[] visited;       // 재귀호출을 위한 방문체크 배열
    static int answer = Integer.MAX_VALUE;      // 도시의 최소 치킨 거리

    public static void main(String[] args) throws Exception {
        // 입력 처리 & 지역 변수 선언
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        for(int i=0; i<N; i++) {
            String input = br.readLine();
            for(int j=0; j<N; j++) {
                switch (input.charAt(j * 2)) {        // 홀수번째 인덱스는 공백이 등장함
                    case '1':       // 1은 집
                        homes.add(new Point(j, i));
                        break;
                    case '2':       // 2는 치킨집
                        stores.add(new Point(j, i));
                        break;
                }
            }
        }

        dist = new int[stores.size()][homes.size()];        // dist 초기화
        visited = new boolean[stores.size()];       // visited 초기화

        // 로직 구현
        for(int i=0; i<stores.size(); i++) {
            for(int j=0; j<homes.size(); j++)
                dist[i][j] = Math.abs(stores.get(i).x-homes.get(j).x) + Math.abs(stores.get(i).y-homes.get(j).y);       // 각 치킨집을 기준으로 각 집의 치킨거리
        }

        combination(0, 0);      // 살아남은 치킨집 선정 로직

        // 출력 처리
        System.out.println(answer);
    }

    private static void combination(int idx, int start) {       // 살아남을 치킨집은 조합으로 선정
        if(idx==M) {        // 조합이 완성되었을 경우
            int chDis = 0;      // 도시의 치킨 거리 초기화
            for(int i=0; i<homes.size(); i++) {     // 각 가정집의 최소 치킨 거리 구하기
                int minDis = Integer.MAX_VALUE;     // 최소 치킨 거리
                for(int j=0; j<stores.size(); j++) {        // 순회하면서 살아남은 치킨집이면 치킨 거리 비교
                    if(visited[j])
                        minDis = Math.min(minDis, dist[j][i]);      // 더 작으면 업데이트
                }
                chDis += minDis;        // 도시의 치킨 거리는 각 가정집 치킨 거리의 합이므로 더해줌
            }
            answer = Math.min(answer, chDis);       // 도시의 치킨 거리가 최소일 때가 정답이므로 작은 것으로 업데이트
            return;
        }

        for(int i=start; i<stores.size(); i++) {        // 치킨집을 순회하며 선택함
            visited[i] = true;
            combination(idx+1, i+1);
            visited[i] = false;
        }
    }
}