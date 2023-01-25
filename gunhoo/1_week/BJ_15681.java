package Gold;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BJ_15681_트리와쿼리 {
/** 알고리즘 스터디 #1
 * 총 시간(1시간 50분)
 * 문제 이해 시간 : 17:52 ~ 18:04(12분)
 * 문제 풀이 시간 : 18:04 ~ 19:27(tree 찾아봄 : ArrayList 사용법)(1시간23분)
 * ~19:42 완료(메모리, 시간 너무 많음)(15분)
 * 
 * @param args
 * @Author 박건후
 */
	/* 
	private static Map removeParent(int parent, Map<Integer, Integer> tree) {
		int child = tree.get(parent);
		System.out.println("p: "+parent+" c :"+child);
		return tree;
	}*/
	static int[] size;
	static boolean[] visited;
	static ArrayList<Integer>[] tree;
	
	private static void countSubtreeNodes(int currentNode, int n) {
		size[currentNode] = 1;
		for(int node : tree[currentNode]){
			if(visited[node] == false) {
				visited[node] = true;
				countSubtreeNodes(node, n);
				size[currentNode] += size[node];
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int r = Integer.parseInt(st.nextToken());
		int q = Integer.parseInt(st.nextToken());
		tree = new ArrayList[n+1];
		
//		for(int i = 0; i<= n ; i++) {
//			tree[i] = new ArrayList<>();
//		}
		
		size = new int[n+1];
		visited = new boolean[n+1];
		
		for(int idx = 0; idx<n-1; idx++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			if(tree[u] != null) {
				tree[u].add(v);
				if(tree[v] != null) {
					tree[v].add(u);
				}
				else {
					tree[v] = new ArrayList<>();
					tree[v].add(u);
				}
			}
			else {
				tree[u] = new ArrayList<>();
				tree[u].add(v);
				if(tree[v] != null) {
					tree[v].add(u);
				}
				else {
					tree[v] = new ArrayList<>();
					tree[v].add(u);
				}
			}
			
			
			
		}
		visited[r] = true;
		countSubtreeNodes(r, n);
		
		for(int idx = 0; idx < q; idx++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			
			System.out.println(size[u]);
		}
	}

}
