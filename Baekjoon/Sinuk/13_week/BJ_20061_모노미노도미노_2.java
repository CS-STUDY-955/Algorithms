import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_20061_모노미노도미노_2 {
    // 1. 블록을 놓을 위치를 찾는다.
    // 2. 블록을 놓은 위치가 꽉찼는지 확인한다.
    // 2.1. 꽉찼다면 삭제하고 점수를 올리고 당긴다.
    // 3. 인덱스 0~1에 블록이 있다면 당긴다.

    // 1칸짜리 블록
    private static int smallBlock(int y, int[][] target) {
        int removed = 0;
        int x = 0;
        // 놓을 수 있는 x를 찾기
        while (x < 5) {
            if (target[x + 1][y] == 1) {
                break;
            }
            x++;
        }
        // 찾은 위치에 블럭을 표시
        target[x][y] = 1;

        // 만약 한줄이 가득 찼다면 점수 획득을 표시하고 삭제
        if (target[x][0] + target[x][1] + target[x][2] + target[x][3] == 4) {
            removed++;
            deleteLine(x, target);
        }
        // 만약 놓인 위치가 1이라면 맨 밑줄 삭제
        if (x == 1){
            deleteLine(5, target);
        }
        return removed;
    }

    // 자신에겐 가로로 놓인 블록
    private static int oneByTwoBlock(int y, int[][] target) {
        int removed = 0;
        int x = 0;
        while (x < 5) {
            if (target[x + 1][y] == 1 || target[x+1][y+1] == 1) {
                break;
            }
            x++;
        }
        target[x][y] = 1;
        target[x][y+1] = 1;
        if (target[x][0] + target[x][1] + target[x][2] + target[x][3] == 4) {
            removed++;
            deleteLine(x, target);
        }
        if (x == 1){
            deleteLine(5, target);
        }
        return removed;
    }

    // 자신에겐 세로로 놓인 블록
    private static int twoByOneBlock(int y, int[][] target) {
        int removed = 0;
        int x = 0;
        while (x < 4) {
            if (target[x + 2][y] == 1) {
                break;
            }
            x++;
        }
        target[x][y] = 1;
        target[x+1][y] = 1;
        boolean anyDelete = false;
        if (target[x][0] + target[x][1] + target[x][2] + target[x][3] == 4) {
            removed++;
            deleteLine(x, target);
            anyDelete = true;
        }
        if (target[x+1][0] + target[x+1][1] + target[x+1][2] + target[x+1][3] == 4) {
            removed++;
            deleteLine(x+1, target);
            anyDelete = true;
        }
        // 블록의 윗부분이 1이라면 한줄이 지워졌다면 안지워도 되지만, 지워지지 않았다면 맨 밑줄을 삭제 해야 한다.
        if (x == 1 && !anyDelete){
            deleteLine(5, target);
        // 반면, 0이라면 절대 점수를 얻을 수 없으므로 위와 같은 처리가 필요없다
        } else if (x == 0){
            deleteLine(5, target);
            deleteLine(5, target);
        }
        return removed;
    }

    // 주어진 줄을 지우고 당겨주는 메서드
    private static void deleteLine(int x, int[][] target) {
        for(int i = x; i>0; i--){
            for(int j = 0; j<4; j++){
                target[i][j] = target[i-1][j];
            }
        }
        for(int j = 0; j<4; j++){
            target[0][j] = 0;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int score = 0;
        int[][] green = new int[6][4];
        int[][] blue = new int[6][4];

        while (n-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            // 주어진 블럭을 각 영역에 맞게 내림
            if (t == 1) {
                score += smallBlock(y, green);
                score += smallBlock(3 - x, blue);
            } else if (t == 2) {
                score += oneByTwoBlock(y, green);
                score += twoByOneBlock(3 - x, blue);
            } else if (t == 3) {
                score += twoByOneBlock(y, green);
                score += oneByTwoBlock(3 - x - 1, blue);
            }
        }

        // 출력부
        System.out.println(score);
        int count = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                count += green[i][j] + blue[i][j];
            }
        }
        System.out.println(count);
    }
}
