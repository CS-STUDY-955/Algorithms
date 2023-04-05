package Gold.Gold2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 가방크기가 작은얘들부터 보면서
 * 무게가 가방 이하인 얘들중에 가장 무거운 놈 선택
 * @author gunhoo
 *
 */
public class BJ_1202_보석도둑 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		long answer =0;
		
		Node[] list = new Node[N];
		for(int i =0; i < N ;i++) {
			st = new StringTokenizer(br.readLine());
			list[i] = new Node(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));
		}
		Arrays.sort(list, new Comparator<Node>(){
			@Override
			public int compare(Node o1, Node o2) {
				if(o1.weight==o2.weight) return o2.value-o1.value;
				return o1.weight-o2.weight;
			}
		});
		
		int[] bags= new int[K];
		for(int i =0; i < K;i++) {
			bags[i] = Integer.parseInt(br.readLine()); 
		}
		Arrays.sort(bags);
		
		PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
		for(int i =0, j = 0; i < K ;i++) {
			while(j<N && list[j].weight <= bags[i]) {
				pq.offer(list[j++].value);
			}
			if(!pq.isEmpty()) answer += pq.poll();
		}
		System.out.println(answer);
	}
	
	static class Node{
		int weight, value;
		public Node(int weight, int value) {
			this.weight = weight;
			this.value = value;
		}
	}
}
