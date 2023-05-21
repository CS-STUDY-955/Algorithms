import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
// import java.util.Iterator;

public class BJ_16120_PPAP {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String ppap = br.readLine();
    ArrayDeque<Character> stack = new ArrayDeque<>();

    boolean isPPAP = true;
    if (ppap.length() == 1) {
      if (!ppap.equals("P")) {
        isPPAP = false;
      }
    } else if (ppap.length() == 2 || ppap.length() == 3) {
      isPPAP = false;
    } else {
      char pFirst = ppap.charAt(0);
      char pSecond = ppap.charAt(1);
      stack.push(pFirst);
      stack.push(pSecond);
      for (int i = 2; i < ppap.length(); i++) {
        // 테스트용 출력
        // Iterator iter = stack.iterator();
        // System.out.println("i: "+i);
        // while(iter.hasNext()){
        //   System.out.print(iter.next()+" ");
        // }
        // System.out.println();

        if (ppap.charAt(i) == 'A') {
          if (i != ppap.length() - 1 && ppap.charAt(i + 1) == 'P') {
            if (pFirst == 'P' && pSecond == 'P') {
              stack.pop();
              pFirst = stack.pop();
              pSecond = 'P';
              stack.push('P');
              if (stack.size() == 1){
                pFirst = 0;
              }
              i++;
              // System.out.println("continue!");
              continue;
            }
          }
        }
        pFirst = pSecond;
        pSecond = ppap.charAt(i);
        stack.push(pSecond);
      }

      // System.out.println("size: "+stack.size());
      // Iterator iter = stack.iterator();
      // while(iter.hasNext()){
      //   System.out.print(iter.next()+" ");
      // }
      // System.out.println();
      if (stack.size() != 1 || stack.peek() != 'P') {
        isPPAP = false;
      }
    }

    // if (stack.size() == 1 && stack.peek() == 'P'){
    if (isPPAP) {
      System.out.println("PPAP");
    } else {
      System.out.println("NP");
    }
  }
}
