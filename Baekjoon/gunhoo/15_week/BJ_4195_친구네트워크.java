package Gold.Gold2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * hashmap 써서 key=value값
 * @author Gunhoo
 *
 */
public class BJ_4195_친구네트워크 {
	private static int F, parents[], count[];
	public static void main(String[] args)throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int t = 1; t<=tc;t++) {
			F = Integer.parseInt(br.readLine());
			HashMap<String,Integer> map=new HashMap<>();
			int num = 0;
			parents = new int[F*2];
			count = new int[F*2];
			for(int i = 0; i < F; i++) {
				String[] input = br.readLine().split(" ");
				if(!map.containsKey(input[0])) {
					map.put(input[0], num);
					parents[num] = num;
					count[num] = 1;
					num+=1;
				}
				if(!map.containsKey(input[1])) {
					map.put(input[1], num);
					parents[num] = num;
					count[num] = 1;
					num+=1;
				}
				union(map.get(input[0]), map.get(input[1]));
				int now = find(map.get(input[0]));
				sb.append(count[now]).append("\n");
			}
		}
		System.out.println(sb);
	}
	
	private static int find(int num) {
		if(parents[num] == num) return num;
		return parents[num]=find(parents[num]);
	}
	
	private static void union(int a, int b) {
		a = find(a);
		b = find(b);
		if(a == b) return;
		if(a > b) {
			parents[a] = b;
			count[b] += count[a];
		}else {
			parents[b] = a;
			count[a] += count[b];
		}
	}

}
