import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BJ_10775_공항 {
  private static int[] parents;

  // private static class Gate{
  //   int num;
  //   boolean available;
  //   Gate link;

  //   public Gate(int num){
  //     this.num = num;
  //     available = true;
  //     link = null;
  //   }

  //   public Gate getRoot(){
  //     if (link == null){
  //       return this;
  //     }
  //     Gate root = link.getRoot();
  //     link = root;
  //     return link;
  //   }
  // }

  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int g = Integer.parseInt(br.readLine());
    int p = Integer.parseInt(br.readLine());

    int[] plane = new int[p];
    for(int i = 0; i<p; i++){
      plane[i] = Integer.parseInt(br.readLine());
    }
    // Gate[] gates = new Gate[g+1];
    // for(int i = 0; i<=g; i++){
    //   gates[i] = new Gate(i);
    // }
    // gates[0].available = false;

    parents = new int[g+1];
    for(int i = 1; i<=g; i++){
      parents[i] = i;
    }

    // int count = 0;
    // for(int i = 0; i<p; i++){
    //   Gate target = gates[plane[i]].getRoot();
    //   if (!target.available){
    //     break;
    //   }
    //   count++;
    //   target.available = false;
    //   target.link = gates[gates[plane[i]].getRoot().num - 1];
    // }
    // System.out.println(count);
    
    for(int i = 0; i<p; i++){
      if (find(plane[i]) == -1){
        System.out.println(i);
        return;
      }
    }
    System.out.println(p);
  }

  public static int find(int a){
    if (parents[a] == a){
      parents[a]--;
      return parents[a];
    }
    parents[a] = find(parents[a]);
    return parents[a];
  }
}
