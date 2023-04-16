import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_1202_보석_도둑 {
  private static class Jewel implements Comparable<Jewel>{
    int m; // 무게
    int v; // 가치

    Jewel(int m, int v){
      this.m = m;
      this.v = v;
    }

    @Override
    public int compareTo(Jewel o) {
      return m - o.m;
    }
  }
  
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(st.nextToken());
    int k = Integer.parseInt(st.nextToken());

    Jewel[] jewels = new Jewel[n];
    for(int i = 0; i<n; i++){
      st = new StringTokenizer(br.readLine());
      jewels[i] = new Jewel(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
    }

    int[] bags = new int[k];
    for(int i = 0; i<k; i++){
      bags[i] = Integer.parseInt(br.readLine());
    }

    PriorityQueue<Jewel> pq = new PriorityQueue<Jewel>(new Comparator<Jewel>() {
      @Override
      public int compare(Jewel o1, Jewel o2) {
        return o2.v - o1.v;
      }
    });

    Arrays.sort(jewels);
    Arrays.sort(bags);
    int jewelsIdx = 0;
    long sum = 0;
    for(int i = 0; i<k; i++){
      // 1. 현재 가방에 들어갈 수 있는 보석들을 전부 pq에 넣는다
      //     (무게순으로 정렬되어 있으니 순서대로 넣으면 된다.)
      // 2. pq안에서는 보석의 가치 순으로 정렬되어 있으니 가장 앞의 보석을 꺼낸다
      // 3. pop시켜서 sum에 더해준다.
      while(jewelsIdx < n && jewels[jewelsIdx].m <= bags[i]){
        pq.add(jewels[jewelsIdx++]);
      } 
      if (!pq.isEmpty()){
        Jewel popped = pq.poll();
        sum += popped.v;
      }
    }
    System.out.println(sum);
  }
}
