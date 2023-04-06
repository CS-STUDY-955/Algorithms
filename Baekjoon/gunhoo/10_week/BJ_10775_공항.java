package Gold.Gold2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 
 * @author 박건후
 *
 */
public class BJ_10775_공항 {
	static boolean[] gates;
	static int G, P;
	static int answer = 0;
	static int[] table;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		G = Integer.parseInt(br.readLine());
		P = Integer.parseInt(br.readLine());
		gates = new boolean[G+1]; // G개의 게이트 수
		table = new int[G+1];
		for(int i = 0; i <= G; i++) {
			table[i] = i;
		}
		
		for(int i =0; i < P; i++) {
			int num = Integer.parseInt(br.readLine());
			if(num > G) break;
			boolean tf = false;
			for(int ii= table[num]; ii >= 1; ii--) {
				if(gates[ii] == false) {
					gates[ii] = true;
					answer++;
					table[num] = ii-1;
					tf = true;
					break;
				}
			}
			if(!tf) break;
		}
		
		System.out.println(answer);
	}

}
