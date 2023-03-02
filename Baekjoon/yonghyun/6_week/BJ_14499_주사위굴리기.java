package 백준.골드4.주사위_굴리기;

import java.io.*;
import java.util.*;

/**
 * 주사위 굴리기
 * https://www.acmicpc.net/problem/14499
 *
 * 1. 주사위 클래스를 만들어 각 방향으로 굴리는 메서드를 구현한다.
 * 2. 명령을 순회하면서 굴리는 메서드를 호출한다.
 * 3. 굴린 이후에는 주사위와 맵에 변화가 일어나는 메서드를 실행한다.
 * 4. 모든 명령을 수행했을때 최종적으로 위쪽을 가리키는 눈이 얼마인지 출력한다.
 *
 * @author 배용현
 */
public class Main {
    static class Dice {
        int top = 0, bottom = 0, east = 0, west = 0, north = 0, south = 0;      // 처음엔 모든면이 0

        public void rollEast(int[][] map, int x, int y) {
            int temp = bottom;
            bottom = east;
            east = top;
            top = west;
            west = temp;

            change(map, x, y);
        }

        public void rollWest(int[][] map, int x, int y) {
            int temp = bottom;
            bottom = west;
            west = top;
            top = east;
            east = temp;

            change(map, x, y);
        }

        public void rollNorth(int[][] map, int x, int y) {
            int temp = bottom;
            bottom = north;
            north = top;
            top = south;
            south = temp;

            change(map, x, y);
        }

        public void rollSouth(int[][] map, int x, int y) {
            int temp = bottom;
            bottom = south;
            south = top;
            top = north;
            north = temp;

            change(map, x, y);
        }

        private void change(int[][] map, int x, int y) {        // 맵과 주사위 갱신 메서드
            if(map[x][y]==0)      // 바닥이 0이었으면
                map[x][y] = bottom;     // 주사위 아래 숫자 찍음
            else {      // 0이 아니었으면
                bottom = map[x][y];     // 주사위 아래 숫자 변경하고
                map[x][y] = 0;      // 바닥 0으로 찍음
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());	// 주사위의 y좌표
        int y = Integer.parseInt(st.nextToken());	// 주사위의 x좌표
        int K = Integer.parseInt(st.nextToken());	// 1: 동, 2: 서, 3: 북, 4: 남

        int[][] map = new int[N][M];        // x가 행, y가 열
        int[] order = new int[K];
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for(int i=0; i<K; i++)
            order[i] = Integer.parseInt(st.nextToken());

        Dice dice = new Dice();     // 주사위 생성

        for(int i=0; i<order.length; i++) {     // 각 명령에 대해 실행
            if(order[i]==1) {       // 동쪽
                if(y==M-1)      // 가능여부 확인
                    continue;

                dice.rollEast(map, x, ++y);        // 굴림
            }
            else if(order[i]==2) {      // 서쪽
                if(y==0)
                    continue;

                dice.rollWest(map, x, --y);
            }
            else if(order[i]==3) {      // 북쪽
                if(x==0)
                    continue;

                dice.rollNorth(map, --x, y);
            }
            else {      // 남쪽
                if(x==N-1)
                    continue;

                dice.rollSouth(map, ++x, y);
            }

            System.out.println(dice.top);
        }
    }
}