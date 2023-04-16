import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BJ_17837_새로운_게임_2 {
    // 더미, 우 좌 상 하
    private static int[] dx = { 0, 0, 0, -1, 1 };
    private static int[] dy = { 0, 1, -1, 0, 0 };

    // 각각의 말들의 정보
    private static class Token {
        int x, y;
        int dir;

        public Token(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }

    // 바뀌는 방향을 정해주는 메서드
    private static int changeDir(int dir) {
        if (dir == 1) {
            return 2;
        } else if (dir == 2) {
            return 1;
        } else if (dir == 3) {
            return 4;
        } else {
            return 3;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[][] board = new int[n + 2][n + 2];
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        // 패딩 설정
        for (int i = 0; i < n + 2; i++) {
            board[0][i] = 2;
            board[n + 1][i] = 2;
            board[i][0] = 2;
            board[i][n + 1] = 2;
        }
        Token[] tokens = new Token[k];
        @SuppressWarnings("unchecked")
        // 각각의 칸에 말들이 어떻게 쌓여있는지 기록하는 list
        ArrayList<Token>[][] tokensOnBoard = new ArrayList[n + 2][n + 2];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                tokensOnBoard[i][j] = new ArrayList<Token>();
            }
        }
        // 말의 정보 입력받기
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());
            tokens[i] = new Token(x, y, dir);
            tokensOnBoard[x][y].add(tokens[i]);
        }

        boolean gameover = false;
        int turn = 0;
        while (turn++ < 1000) {
            for (Token t : tokens) {
                int nx = t.x + dx[t.dir];
                int ny = t.y + dy[t.dir];
                // 이동하려는 곳이 파란색일 경우
                if (board[nx][ny] == 2) {
                    t.dir = changeDir(t.dir);
                    nx = t.x + dx[t.dir];
                    ny = t.y + dy[t.dir];
                    // 뒤돌아도 파란색일 경우
                    if (board[nx][ny] == 2) {
                        continue;
                    }
                }
                // 1. 움직이려는 말이 어디의 몇번째 말이며, 위에 몇개가 있는지 파악한다
                // 2. 이동시킬때 역순인지 정순인지 파악한다.
                int start = tokensOnBoard[t.x][t.y].indexOf(t);
                int end = tokensOnBoard[t.x][t.y].size();
                // 해당 위치가 흰칸일 경우 정순으로 이동
                if (board[nx][ny] == 0) {
                    for (int i = start; i < end; i++) {
                        tokensOnBoard[nx][ny].add(tokensOnBoard[t.x][t.y].get(i));
                    }
                // 해당 위치가 빨간칸인 경우 역순으로 이동
                } else {
                    for (int i = end - 1; i >= start; i--) {
                        tokensOnBoard[nx][ny].add(tokensOnBoard[t.x][t.y].get(i));
                    }
                }
                // 원래 위치에 있던 리스트를 수정
                ArrayList<Token> tempList = new ArrayList<>();
                for (int i = 0; i < start; i++) {
                    tempList.add(tokensOnBoard[t.x][t.y].get(i));
                }
                tokensOnBoard[t.x][t.y] = tempList;
                for (Token t2 : tokensOnBoard[nx][ny]) {
                    t2.x = nx;
                    t2.y = ny;
                }
                // 만약 4개 이상 쌓여 있다면 게임 종료
                if (tokensOnBoard[t.x][t.y].size() >= 4) {
                    gameover = true;
                    break;
                }
            }
            if (gameover) {
                break;
            }
        }
        if (gameover) {
            System.out.println(turn);
        } else {
            System.out.println(-1);
        }
    }
}
