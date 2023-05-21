import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_17779_게리맨더링_2 {
  private static int n;
  private static int diffMin = Integer.MAX_VALUE;
  // 중심점에서 시작해서 시계방향으로
  private static int[] dx = { 1, 1, -1, -1 };
  private static int[] dy = { 1, -1, -1, 1 };
  // 1,2,3,4선거구 표시용 dx, dy
  private static int[] dx2 = { -1, 0, 1, 0 };
  private static int[] dy2 = { 0, 1, 0, -1 };
  private static int[][] A;
  private static int[][] district;

  private static class Point {
    int x, y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  // 주어진 d1, d2에 따라 가능한 x, y의 범위 구하기
  // 해당 범위에 대해 반복문으로 5번 선거구의 경계선을 설치하고, 1,2,3,4 선거구를 표시
  private static void test(int d1, int d2) {
    // x, y 각각의 가능한 범위값들
    int xStart = 0;
    int xEnd = n - d1 - d2;
    int yStart = d2;
    int yEnd = n - d1;
    int[] ds = new int[4];
    ds[0] = d1;
    ds[1] = d2;
    ds[2] = d1;
    ds[3] = d2;

    for (int x = xStart; x < xEnd; x++) {
      for (int y = yStart; y < yEnd; y++) {
        district = new int[n][n];
        Point[] points = new Point[4];

        // 5번 선거구의 경계선 설치
        int nx = x;
        int ny = y;
        for (int i = 0; i < 4; i++) {
          points[i] = new Point(nx, ny);
          for (int j = 0; j < ds[i]; j++) {
            nx += dx[i];
            ny += dy[i];
            district[nx][ny] = 5;
          }
        }

        int[] mark = new int[4];
        mark[0] =1;
        mark[1] =2;
        mark[2] =4;
        mark[3] =3;
        // 각 꼭짓점으로부터 선거구 마킹 시작
        for (int i = 0; i < 4; i++) {
          nx = points[i].x;
          ny = points[i].y;
          while (true) {
            nx += dx2[i];
            ny += dy2[i];
            if (0 > nx || nx >= n || 0 > ny || ny >= n) {
              break;
            }
            district[nx][ny] = mark[i];
          }
        }

        // 마킹을 하는데 2중 for문 4개로 할거임
        // 1 선거구
        for(int i = 0; i<n; i++){
          // 맨 위에서부터 0이 아닌 숫자(즉, 5번 선거구의 경계선)을 만날때까지 쭉 가면서 표시해줌
          if (district[i][0] != 0){
            break;
          }
          for (int j = 0; j<n; j++){
            if(district[i][j] != 0){
              break;
            }
            district[i][j] = 1;
          }
        }
        // 2 선거구
        for(int i = 0; i<n; i++){
          if (district[i][n-1] != 0){
            break;
          }
          for (int j = n-1; j>=0; j--){
            if(district[i][j] != 0){
              break;
            }
            district[i][j] = 2;
          }
        }
        // 3 선거구
        for(int i = n-1; i>=0; i--){
          if (district[i][0] != 0){
            break;
          }
          for (int j = 0; j<n; j++){
            if(district[i][j] != 0){
              break;
            }
            district[i][j] = 3;
          }
        }
        // 4 선거구
        for(int i = n-1; i>=0; i--){
          if (district[i][n-1] != 0){
            break;
          }
          for (int j = n-1; j>=0; j--){
            if(district[i][j] != 0){
              break;
            }
            district[i][j] = 4;
          }
        }

        // 마킹 끝났으면 합산 구하기
        int[] sum = new int[6];
        for(int i = 0; i<n; i++){
          for(int j = 0; j<n; j++){
            int temp = district[i][j];
            sum[temp] += A[i][j];
          }
        }

        // 합산 구한걸로 최대최소 구하기
        int max = sum[1];
        int min = sum[1];
        sum[5] += sum[0];
        for(int i = 2; i<6; i++){
          max = Math.max(max, sum[i]);
          min = Math.min(min, sum[i]);
        }
        diffMin = Math.min(diffMin, max-min);
      }
    }
  }
    
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    n = Integer.parseInt(br.readLine());
    A = new int[n][n];
    for (int i = 0; i < n; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int j = 0; j < n; j++) {
        A[i][j] = Integer.parseInt(st.nextToken());
      }
    }

    // i: d1, d2의 합, j: d1, i - j = d2
    for (int i = 2; i < n; i++) {
      for (int j = 1; j < i; j++) {
        test(j, i - j);
      }
    }
    System.out.println(diffMin);
  }
}
