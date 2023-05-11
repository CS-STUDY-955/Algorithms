package Gold.Gold2;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;
import java.util.StringTokenizer;

/** bfs로 가장 가까운 승객 찾아  
// 그게 여러명일 때 y가 가장 작은 놈 골라, 그게 여러명이면 x가 가장 작은 놈 골라
// bfs니까 거기까지 가는 거리 알겠지? 그걸 fuel에서 빼
// 도착지까지 이동하는데 bfs로 이동하면서 찾아가
// 근데 그게 남아있는 연료보다 크면 return 하고 종료
// 목적지에 도착했다면 bfs로 찾은 거리 x2 만큼 fuel에 더해줘
// 승객 수(M) 하나 없애 -> M이 0이면 종료 -> fuel 출력
// 남은 승객 수 가 1이상이면 
 *
 * @author gunhoo
 *
 */
public class BJ_1923_스타트택시 {
	private static int N, M, remainM, fuel, map[][], directions[][] = {{1,0}, {-1,0}, {0,1}, {0,-1}};
	private static Point taxi;
	private static Person passengers[];
	private static boolean visited[][];
	
	private static class Person{
		int x, y, desX, desY;
		boolean finished = false;
		public Person( int x, int y, int desX, int desY) {
			this.x = x;
			this.y = y;
			this.desX = desX;
			this.desY = desY;
		}
	}
	
	private static class Node{
		int x, y, dis;
		public Node(int x, int y, int dis) {
			this.x = x;
			this.y = y;
			this.dis = dis;
		}
	}
	
	public static void main(String[] args) throws Exception{
		init();
		while(remainM > 0 && fuel > 0) {
//			System.out.println("----남은 승객 수 : "+remainM+"----------");
//			System.out.println("현재 택시 위치 : " +taxi.x +","+taxi.y);
			find();
		}
		System.out.println(fuel<=0?-1:fuel);
	}
	
	private static void find() {
		Person p = null;
		// 택시의 처음 위치에 사람이 있는 경우의 수 처리 해줘야해 !
		for(int i =0 ;i < M; i++) { // 모든 승객에 대해, 현재 택시 위치와 승객의 위치가 같으면
			if(taxi.x == passengers[i].x && taxi.y==passengers[i].y && !passengers[i].finished) {
				p = passengers[i]; // 바로 태운다
			}
		}
		if(p != null) {
			ride(p);
			return;
		}
		visited = new boolean[N][N];
		Queue<Node> q = new ArrayDeque<>();
		ArrayList<Node> cand = new ArrayList<>();
		q.offer(new Node(taxi.x, taxi.y, 0));
		visited[taxi.x][taxi.y] = true;
		int minDis = Integer.MAX_VALUE;
		while(!q.isEmpty()) {
			Node next = q.poll();
			if(next.dis > minDis || next.dis > fuel) continue;
			for(int i =0 ;i < 4; i++) {
				int nx = next.x + directions[i][0];
				int ny = next.y + directions[i][1];
				if(nx<0 || nx >= N || ny<0 || ny>=N || visited[nx][ny] || map[nx][ny] == 1) continue;
				visited[nx][ny] = true;
				q.offer(new Node(nx, ny, next.dis+1));
				for(int j =0 ; j < M ; j++) {
					if(passengers[j].x == nx && passengers[j].y == ny && !passengers[j].finished) {
						cand.add(new Node(nx, ny, next.dis+1));
						minDis = next.dis+1;
						break;
					}
				}
			}
		}
		Collections.sort(cand, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				if(o1.dis == o2.dis) {
					if(o1.x == o2.x) return o1.y-o2.y;
					return o1.x-o2.x;
				}
				return o1.dis-o2.dis;
			}
		});
		if(cand.size() == 0) {
			fuel = -1; return;
		}
		Node tmp = cand.get(0);
		for(int i =0 ;i < M ;i++) {
			if(passengers[i].x == tmp.x && passengers[i].y == tmp.y) {
				p = passengers[i];
				fuel -= tmp.dis;
				break;
			}
		}
		if(p != null) {
			ride(p);
			return;
		}
	}
	
	/**
	 * 승객을 도착지까지 태워주는 메서드
	 * @param p : 탑승할 승객
	 */
	private static void ride(Person p) {
		visited = new boolean[N][N];
		Queue<Node> q = new ArrayDeque<>();
		q.offer(new Node(p.x, p.y, 0));
		visited[p.x][p.y] = true;
		while(!q.isEmpty()) {
			Node next =  q.poll();
			if(next.x == p.desX && next.y == p.desY) { // 만약 도착지에 도착했다면,
//				System.out.println("도착지 도착!! ("+p.x+","+p.y+") 에서 ("+p.desX+","+p.desY+") 으로");
				taxi.x = p.desX; taxi.y = p.desY; // 택시 위치 갱신
				fuel -= next.dis; // 여기까지 온 경로만큼 연로 소진
//				System.out.println("소진 후 연료 : "+fuel);
				if(fuel < 0) return; // 근데 그게 음수면 오지 못하는 것이었으므로 그냥 종료
				fuel += next.dis*2; // 양수였으면 충전할 수 있으므로 충전해
//				System.out.println("충전 후 연료 : "+fuel);
				remainM--; // 남아있는 승객 수 줄이고
				p.finished = true; // 태웠던 승객을 죽임 처리
				return;
			}
			for(int i =0; i < 4; i++) {
				int nx = next.x + directions[i][0];
				int ny = next.y + directions[i][1];
				if(nx<0 || nx>=N || ny<0 || ny>=N || visited[nx][ny] || map[nx][ny] == 1) continue;
				visited[nx][ny]=true;
				q.offer(new Node(nx, ny, next.dis+1));
			}
		}
		fuel = -1; // 갈 수 있는 모든 곳을 갔는데 도착지를 못가는 곳이었다면 -1로 반환 
	}
	
	private static void init() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st= new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		fuel = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		passengers = new Person[M];
		for(int i =0 ;i < N ; i++) {
			st= new StringTokenizer(br.readLine());
			for(int j =0 ;j < N ;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		st= new StringTokenizer(br.readLine());
		taxi = new Point(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1);
		for(int i =0 ;i < M; i++) {
			st= new StringTokenizer(br.readLine());
			passengers[i] = new Person(Integer.parseInt(st.nextToken())-1,Integer.parseInt(st.nextToken())-1,Integer.parseInt(st.nextToken())-1,Integer.parseInt(st.nextToken())-1);
		}
		remainM = M;
	}

}
