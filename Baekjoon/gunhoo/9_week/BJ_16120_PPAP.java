package Gold.Gold4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
/**
 * Deque에 넣어 
 * index증가하면서 P인데 peek()가 A면 poll() 3번해(APP여야해, 아니면 false), 그리고 push해
 * 
 * @author Gunhoo
 *
 */
public class BJ_16120_PPAP {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		char[] input = br.readLine().toCharArray();
		ArrayDeque<Character> q = new ArrayDeque<>();
		boolean tf = false;
		
		for(int i =0 ; i < input.length ; i++) {
			if(q.isEmpty()) {
				if(input[i] == 'A') {
					tf = true;
				}else {
					q.add(input[i]);
				}
				continue;
			}
			if(input[i] == 'P') {
				if(q.peekLast() == 'A') {
					q.pollLast();
					if(q.size() < 2) {
						tf = true;
						break;
					}
					char before = q.pollLast();
					char bbefore = q.pollLast();
					if(before == 'P' && bbefore=='P') {
						q.add(input[i]);
					}else {
						tf = true;
						break;
					}
				}else {
					q.add(input[i]);
				}
			}else {
				q.add(input[i]);
			}
		}
		
		if(!tf && q.size() < 2 ) {
			System.out.println("PPAP");
		}else {
			System.out.println("NP");
		}
			
	}

}
