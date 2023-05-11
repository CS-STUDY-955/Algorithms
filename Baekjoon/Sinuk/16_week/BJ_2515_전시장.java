import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class BJ_2515_전시장 {
  private static ArrayList<Paint> paints;

  private static class Paint implements Comparable<Paint> {
    int h;
    int c;

    public Paint(int h, int c) {
      this.h = h;
      this.c = c;
    }

    @Override
    public int compareTo(Paint o) {
      if (this.h == o.h) {
        return o.c - this.c;
      }
      return this.h - o.h;
    }
  }

  private static int binarySearch(int tail, int target) {
    int head = 1;
    while (head < tail) {
      int mid = (head + tail) / 2;
      if (paints.get(mid).h <= target) {
        head = mid + 1;
      } else {
        tail = mid;
      }
    }
    return tail;
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(st.nextToken());
    int s = Integer.parseInt(st.nextToken());
    paints = new ArrayList<>();
    paints.add(new Paint(0, 0));
    for (int i = 1; i <= n; i++) {
      st = new StringTokenizer(br.readLine());
      int h = Integer.parseInt(st.nextToken());
      int c = Integer.parseInt(st.nextToken());
      paints.add(new Paint(h, c));
    }
    Collections.sort(paints);

    int[][] memo = new int[n + 1][2];
    memo[1][0] = paints.get(1).c;
    for (int i = 2; i <= n; i++) {
      int idx = binarySearch(i, paints.get(i).h - s);
      if (idx - 1 == i) {
        memo[i][0] = paints.get(i).c;
      } else {
        memo[i][0] = Math.max(memo[idx - 1][1], memo[idx - 1][0]) + paints.get(i).c;
      }
      memo[i][1] = Math.max(memo[i - 1][0], memo[i - 1][1]);
    }
    System.out.println(Math.max(memo[n][0], memo[n][1]));
  }
}
