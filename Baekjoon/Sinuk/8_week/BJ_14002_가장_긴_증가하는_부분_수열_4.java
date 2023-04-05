import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_14002_가장_긴_증가하는_부분_수열_4 {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.parseInt(br.readLine());
    int[] arr = new int[n];
    StringTokenizer st = new StringTokenizer(br.readLine());
    for(int i = 0; i<n; i++){
      arr[i] = Integer.parseInt(st.nextToken());
    }

    // 각 인덱스로 끝나는 가장 긴 증가하는 부분수열의 길이
    int[] memo = new int[n];
    // 각 인덱스가 가지는 이전 인덱스
    int[] prev = new int[n];
    memo[0] = 1;
    prev[0] = -1;

    // 만약 증가하고있고, 그 부분수열 길이가 현재 저장중인 길이보다 크면 업데이트
    for(int i = 1; i<n; i++){
      memo[i] = 1;
      for(int j = 0; j<i; j++){
        if (arr[j] < arr[i] && memo[j] >= memo[i]){
          memo[i] = memo[j] + 1;
          prev[i] = j;
        }
      }
    }

    // 최대 길이인 인덱스 찾기 
    int maxIdx = 0;
    int max = memo[0];
    for(int i = 1; i<n; i++){
      if (memo[i] > max){
        maxIdx = i;
        max = memo[i];
      }
    }

    // 이전 인덱스 값들 저장
    int[] result = new int[max];
    int idx = prev[maxIdx];
    result[0] = arr[maxIdx];
    for(int i = 1; i<max; i++){
      result[i] = arr[idx];
      idx = prev[idx];
    }

    // 역순으로 꺼내기
    StringBuilder sb = new StringBuilder();
    for(int i = max - 1; i>=0; i--){
      sb.append(result[i] + " ");
    }
    System.out.println(max);
    System.out.println(sb);
  }
}
