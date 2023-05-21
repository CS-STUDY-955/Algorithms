import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_1102_발전소 {
  // 큐 안에 들어갈 객체. 현재까지의 마스킹과 가동된 발전소의 수를 기록
  private static class Progress {
    int mask;
    int cnt;

    public Progress(int mask, int cnt) {
      this.mask = mask;
      this.cnt = cnt;
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.parseInt(br.readLine());
    int[][] adjMat = new int[n][n];
    // 인접 행렬 형태로 기록
    for (int i = 0; i < n; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      for (int j = 0; j < n; j++) {
        adjMat[i][j] = Integer.parseInt(st.nextToken());
      }
    }
    String temp = br.readLine();
    // 입력의 비트마스킹 결과
    int curr = 0;
    // 입력상 가동중인 발전소의 수
    int count = 0;
    for (int i = 0; i < n; i++) {
      if (temp.charAt(i) == 'Y') {
        curr += (1 << i);
        count++;
      }
    }

    int p = Integer.parseInt(br.readLine());
    // 이미 조건을 만족했다면 0 출력 후 종료
    if (p <= count) {
      System.out.println(0);
      return;
    }
    // 모든 발전소가 정지되어 있다면 해결 불가능처리
    if (count == 0) {
      System.out.println(-1);
      return;
    }

    // dp 준비
    Queue<Progress> queue = new ArrayDeque<>();
    queue.add(new Progress(curr, count));
    // 각 비트마스킹별 방문처리 및 최소비용
    boolean[] visited = new boolean[1 << n];
    int[] costs = new int[1 << n];
    for (int i = 0; i < (1 << n); i++) {
      costs[i] = Integer.MAX_VALUE;
    }
    // 시작점 비용 0으로 초기화
    costs[curr] = 0;
    // 깊이 초기화
    int depth = count;
    int minCost = Integer.MAX_VALUE;
    int tempCost = minCost;
    while (!queue.isEmpty()) {
      Progress cur = queue.poll();
      // 새로운 깊이로 들어갔다면
      if (depth < cur.cnt) {
        // 기준 깊이 도달했다면 종료
        if (depth == p) {
          break;
        }
        // 아니라면 깊이 증가, 같은 깊이중에서의 최소 비용 초기화
        depth++;
        minCost = tempCost;
        tempCost = Integer.MAX_VALUE;
      }
      // 어느 발전소를 가동시킬건지 고르기
      for (int i = 0; i < n; i++) {
        // 이미 가동된 발전소라면 패스
        if ((cur.mask & (1 << i)) != 0) {
          continue;
        }
        // 이 발전소가 가동된 이후의 비트마스크
        int after = cur.mask | (1 << i);
        // 어느 발전소로 가동시킬건지 고르기
        for (int j = 0; j < n; j++) {
          // 둘이 같은 발전소거나 가동되지 않은 발전소라면 패스
          if (((cur.mask & (1 << j)) == 0) || i == j) {
            continue;
          }
          // i를 j로 가동시킬 때, 그 값이 해당 비트마스크의 최솟값을 갱신한다면
          if (adjMat[j][i] + costs[cur.mask] < costs[after]) {
            costs[after] = adjMat[j][i] + costs[cur.mask];
            // 같은 깊이중에서의 최소 비용 업데이트
            if (costs[after] < tempCost) {
              tempCost = costs[after];
            }
          }
        }
        // 비트마스킹 결과가 아직 큐 안에 없다면 큐에 삽입 후 방문처리
        if (!visited[after]) {
          queue.add(new Progress(after, depth + 1));
          visited[after] = true;
        }
      }
    }
    System.out.println(minCost);
  }
}