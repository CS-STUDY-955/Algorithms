package com.baekjoon.algorithm;
/**
 * 미 완 성
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.lang.StringBuilder;

class Node {
	int parent;
	ArrayList<Integer> child;
	
	Node() {
		this.parent = -1;
		this.child = new ArrayList<Integer>();
	}
	
	void add(int node) {
		this.child.add(node);
	}
}

public class P15681 {

	public static void main(String[] arg) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
        
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken()); // 트리의 정점의 수
		int R = Integer.parseInt(st.nextToken()); // 루트의 번호
		int Q = Integer.parseInt(st.nextToken()); // 쿼리의 수
		
		ArrayList<ArrayList<Integer>> UV = new ArrayList<ArrayList<Integer>>(N-1);

		// 노드 초기화
		ArrayList<Node> nodes = new ArrayList<Node>(N);
		for(int i = 0; i < N - 1; i++) {
			UV.add(new ArrayList<Integer>(2));
			
			st = new StringTokenizer(br.readLine());
			UV.get(i).add(Integer.parseInt(st.nextToken()));
			UV.get(i).add(Integer.parseInt(st.nextToken()));
			nodes.add(new Node());
		}
        nodes.add(new Node());
		
		ArrayList<Integer> U = new ArrayList<Integer>(Q); // 루트로 지정할 정점 U
		for(int i = 0; i < Q; i++) {
			U.add(Integer.parseInt(br.readLine()));
		}
		
		makeTree(R, -1, UV, nodes);
		
		int[] size = new int[N];
		countSubtreeNodes(R, nodes, size);
		
		for (int u: U) {
            sb.append(size[u - 1]).append("\n");
		}
        System.out.println(sb);
	}
	
	public static void makeTree(int currentNode, int parent, ArrayList<ArrayList<Integer>> UV, ArrayList<Node> nodes) {
		nodes.get(currentNode - 1).parent = parent;
		for(ArrayList<Integer> uv_ : UV) {
			if (uv_.get(0) == currentNode && uv_.get(1) != parent) {
				nodes.get(currentNode - 1).add(uv_.get(1));
				makeTree(uv_.get(1), uv_.get(0), UV, nodes);
			}
			if (uv_.get(1) == currentNode && uv_.get(0) != parent) {
				nodes.get(currentNode - 1).add(uv_.get(0));
				makeTree(uv_.get(0), uv_.get(1), UV, nodes);
			}
		}
	}
	
	public static void countSubtreeNodes(int currentNode, ArrayList<Node> nodes, int[] size) {
		size[currentNode - 1] = 1;;
		for (int c : nodes.get(currentNode - 1).child) {
			countSubtreeNodes(c, nodes, size);
			size[currentNode - 1] += size[c - 1];
		}
	}
}
