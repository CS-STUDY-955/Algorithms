import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
/**
 * 풀이시간 15:20~15:36 (틀림) int[n+2][2] 배열 만들어서 단순비교 풀이 (틀림) (16분)
 * ~16:08 Node class 구현, Arrays.sort()로 x,y기준 정렬후 비교하는 것으로 구현하였으나 (틀림) (32분/48분)
 * 간과한 점 : 굳이 모든 편의점을 안들려도 됨 / 편의점 끼리 거리가 1000넘어도 상관이 없음 / 
 * ~16:25 집과 목적지 사이의 편의점만 비교하게 추가(틀림)  (17분/1시간5분)
 * 16:30~ 문제 유형 확인: graph(BFS)
 * 자바 BFS 구현 방법 search...
 * ~17:02 BFS 구현(틀림)
 * 수정수정~~ 1730(1시간/2시간10분)
 * 사실 1730 이후에도 계속 틀려서 더 수정했으나 계속 틀리길래 이상하다 싶었는데
 * 츨력 양식 haapy 로 출력해서 틀린 것이었음...
 * @author Gunhoo
 *
 */

public class BJ_9205 {
	static int homeX, homeY, desX, desY;
	static Node[] con2;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int t = Integer.parseInt(st.nextToken());
		
		for(int test_case = 1; test_case <= t ; test_case++) {
			// n 입력
			int n = Integer.parseInt(br.readLine());
			con2 = new Node[n];
			
			// 집 주소 입력
			st = new StringTokenizer(br.readLine());
			homeX = Integer.parseInt(st.nextToken());
			homeY = Integer.parseInt(st.nextToken());
			
			// 편의점 수 입력
			for( int i = 0; i< n; i++) {
				st = new StringTokenizer(br.readLine());
				con2[i] = new Node(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			}
			
			// 목적지 입력
			st = new StringTokenizer(br.readLine());
			desX = Integer.parseInt(st.nextToken());
			desY = Integer.parseInt(st.nextToken());

			// 최조 집-축제 거리가 1000 아래면 happy해 
			if(Math.abs(desY-homeY)+Math.abs(desX-homeX)<=1000 ){
				System.out.println("happy");
			}else { // 그렇지 않으면				
				if (bfs()) {
					System.out.println("happy");
				}else {
					System.out.println("sad");
				}
				
			}
			
		}

	}
	
	public static boolean bfs() {
		Queue<Node> q = new LinkedList<>();
		boolean[] visited = new boolean[con2.length];
		
		q.add(new Node(homeX, homeY));
		
		while (!q.isEmpty()) {
			// n은 q에서 꺼낸 노드(현재노드)
			Node n = q.poll();
			// 현재 노드에서 도착지점까지 남은 거리가 1000이하면 true 반환
			if( Math.abs( n.getX()-desX)+Math.abs( n.getY()-desY) <= 1000) {
				return true;
			}else {
				for(int i = 0; i< con2.length; i++) { // 모든 노드 탐색 
					if(!visited[i]) { // 근데 이제 방문하지 않은,,
						// 만약 현재노드에서 방문하지 않은 편의점 중 거리가 1000이하인 node를 전부 q에 넣는다.
						if( Math.abs( con2[i].getX() - n.getX())+Math.abs( con2[i].getY() - n.getY()) <= 1000) {
							visited[i] = true;
							q.add(con2[i]);
						}
					}
				}
			}
		}
		return false;
	}

}


class Node{
	private int x;
	private int y;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}
