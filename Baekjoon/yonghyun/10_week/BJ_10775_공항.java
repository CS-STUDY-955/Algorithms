import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;

/**
 *  공항
 *  https://www.acmicpc.net/problem/10775
 *
 *  1. 1부터 10,000까지 순회하며 가장 들어갈 자리의 여유가 없는 비행기부터 현재 자리에 도킹하면 될 것 같다.
 *  2. 최대로 들어갈 수 있는 게이트를 기준으로 오름차순 정렬한다.
 *  3. 각 게이트를 순회하며 순서대로 비행기를 도킹시키고, 다음 게이트를 확인한다.
 *  4. 비행기가 도킹할 수 있는 최대 게이트가 현재 게이트보다 작으면, 다음 비행기를 체크한다.
 *  5. 모든 게이트를 확인했거나 모든 비행기를 확인한 경우 로직을 종료하고 도킹된 비행기의 수를 출력한다.
 *
 *  ---------------------------------
 *  1. 비행기가 순서대로 도착하므로 정렬할 수 없다.
 *  2. 비행기가 도착할 때마다 도킹가능한 게이트 중에서 가장 번호가 큰 게이트부터 도킹해나간다.
 *  3. 비행기가 1번게이트까지 도킹할 수 없으면 종료한다.
 *
 *  ----------------------------------
 *  1. 단순 그리디로는 풀 수 없다. (약 10억번의 연산이 요구된다.)
 *  2. 각 게이트를 기준으로 가장 가까운 가용 게이트가 어디인지를 파악할 때 union-find 알고리즘을 사용할 수 있다.
 *  3. 자기 자신을 소모했다면 번호가 1낮은 게이트를 자신의 부모로 지정하는 방식으로 구현한다.
 *
 */
public class BJ_10775_공항 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int G, P, answer = 0;
    static int[] parent;

    public static void main(String[] args) throws IOException {
        G = parseInt(br.readLine());
        P = parseInt(br.readLine());
        parent = new int[G+1];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < P; i++) {
            int maxGate = parseInt(br.readLine());
            maxGate = find(maxGate);
            if(maxGate==0)
                break;

            answer++;

            union(maxGate, maxGate-1);
        }

        System.out.print(answer);
    }

    private static void union(int cur, int next) {
        int curParent = find(cur);
        int nextParent = find(next);

        parent[curParent] = nextParent;
    }

    private static int find(int cur) {
        if (parent[cur] == cur)
            return cur;
        else
            return parent[cur] = find(parent[cur]);
    }

    private void pureGreedy() throws IOException {

        boolean[] unavailable = new boolean[G];

        loop:
        for (int i = 0; i < P; i++) {       // 모든 비행기에 대해 수행
            int gate = parseInt(br.readLine());
            while(gate-- > 0) {
                if(!unavailable[gate]) {
                    unavailable[gate] = true;
                    answer++;
                    continue loop;
                }
            }

            break;
        }

    }
}
