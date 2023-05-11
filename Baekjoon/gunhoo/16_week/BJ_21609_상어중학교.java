package Gold.Gold2;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_21609_상어중학교 {
	private static int N, M, map[][], score, direction[][] = {{0,1}, {0,-1}, {1,0}, {-1,0}};
	private static boolean visited[][];
	
	public static void main(String[] args) throws Exception{
		init();
		while(find()) { // find메서드는 큰 블록을 찾고 모든 블록을 제거하고 점수를 더하는 메서드
			gravity(); // 중력이 작용한다
			spin(); // 90도 반시계 방향으로 회전한다
			gravity(); // 격자에 다시 중력이 작용한다
		}
		System.out.println(score);
	}
	
	private static boolean find() {
		ArrayList<Node> cand = new ArrayList<>();
		int max = 1;
		for(int num=1; num <= M; num++) { // num인 친구를 찾는데 작은놈부터 찾아야 큰놈이 나중에 저장돼
			for(int i =0 ;i < N ;i++) {
				for(int j = 0 ; j < N ; j++) {
					if(map[i][j] == num) {
						visited = new boolean[N][N];
						Node node = bfs(i, j, num); // i,j에서 연결된 블록 수 정보를 담는 Node return
						if( max <= node.blockSize) { // max size 구해
							max = node.blockSize; 
							cand.add(node); // 같은게 여러개 있을 수 있으니 일단 ArrayList에 넣어
						}
					}
				}
			}
		}
		
		if(max < 2) return false; // 2개 이하면 블록이 성립할 수 없으므로 false return하여 종료조건 생성
		score += max*max; // 2개 이상이면 max인 곳들의 블록을 다 지우고 점수 획득
		Collections.sort(cand, new Comparator<Node>() { // 후보군 중에 정렬
			@Override
			public int compare(Node o1, Node o2) {
				if(o1.blockSize == o2.blockSize) {
					if(o1.rainbowBlocksNums == o2.rainbowBlocksNums) { 
						if(o1.standardBlock.x == o2.standardBlock.x) return o2.standardBlock.y - o1.standardBlock.y; // y큰놈 앞으로
						return o2.standardBlock.x - o1.standardBlock.x; // x큰놈 앞으로
					}
					return o2.rainbowBlocksNums - o1.rainbowBlocksNums; // 무지개블록 젤 많은 놈 앞으로
				}
				return o2.blockSize-o1.blockSize; // 블록 크기 큰 놈 젤 앞으로
			}
			
		});
		deleteBlock(cand.get(0)); // 젤 앞에 있는 놈부터 시작으로 블록 삭제
		return true; // main의 while문 반복 위한 true return
	}
	
	private static void deleteBlock(Node point) { // 그 노드 받아서
		int num = map[point.standardBlock.x][point.standardBlock.y];
		Queue<Point> q = new ArrayDeque<Point>();
		q.offer(new Point(point.standardBlock.x, point.standardBlock.y)); // q에 넣고
		while(!q.isEmpty()) {
			Point p = q.poll();
			for(int i =0 ;i  < 4; i++) { // 4방향으로
				int nx = p.x+direction[i][0];
				int ny = p.y+direction[i][1];
				if(0>nx || N <= nx || 0>ny || ny>=N || map[nx][ny] == -1 ) continue;
				if(map[nx][ny] == 0 || map[nx][ny] == num) { // 무지개 노드거나 자신과 같은 번호의 노드를
					map[nx][ny] = -2; // 삭제시킨다(-2가 지도에서 삭제되었다고 설정)
					q.offer(new Point(nx, ny)); // bfs위한 q삽입
				}
			}
		}
	}
	
	private static Node bfs(int x, int y, int num) {
		Node node = new Node();
		Point lastPoint = new Point(x, y); // 스탠다드포인트
		int rainbows = 0; // 무지개블록 수 담는 수
		int cnt = 1; // 총 연결된 블록 수 
		Queue<Point> q = new ArrayDeque<Point>();
		q.offer(new Point(x, y));
		visited[x][y] = true; // 본인은 방문처리
		while(!q.isEmpty()) {
			Point point = q.poll();
			for(int i =0 ;i  < 4; i++) { // 4방탐색
				int nx = point.x+direction[i][0];
				int ny = point.y+direction[i][1];
				if(0>nx || N <= nx || 0>ny || ny>=N || map[nx][ny] == -1 || visited[nx][ny] ) continue;
				if(map[nx][ny] == 0 || map[nx][ny] == num) { // 0이거나 숫자 같으면 갈 수 있어
					if(map[nx][ny] == 0) rainbows++; // 0이면 무지개블록 수 증가
					else { // rainbow블락이 아닌 얘들중에 standard 블락 구해야해
						if(lastPoint.x > nx) { // 크면 갱신
							lastPoint = new Point(nx, ny);
						}else if(lastPoint.x == nx) { // 같으면
							if(lastPoint.y > ny) { // 열이 크면 갱신
								lastPoint = new Point(nx, ny);
							}
						}
					}
					visited[nx][ny] = true; // 방문처리
					q.offer(new Point(nx, ny)); // bfs처리 위한 q삽입
					cnt++; // 총 연결블록 수 증가
				}
			}
		}
		node.blockSize = cnt;
		node.rainbowBlocksNums = rainbows;
		node.standardBlock = lastPoint;
		return node;
	}
	
	private static void gravity() {
		for(int i = N-2 ; i >= 0 ;i--) {// 어차피 맨 밑은 안떨어지니까 N-2부터 시작
			for(int j = 0 ; j < N ;j++) {
				if(map[i][j] >= 0) { // 만약 남아있는 블록이라면,
					int lastEmptySpace = -1; // 자신과 바닥 사이에 빈칸 존재하는지 확인
					for(int k = i+1 ; k< N ; k++) { // 위에서부터
						if(map[k][j] == -2) lastEmptySpace = k; // 빈칸이면 값 갱신
						if(map[k][j] >= -1 && k == i+1) break; // 만약 바로 밑이 블록으로 채워져있다면 이동하지 못하므로 탈출
						if(map[k][j] >= -1 && k != i+1) { // 그게 아니고 블록을 만나면
							map[k-1][j] = map[i][j]; // 그 곳으로 보내고
							map[i][j] = -2; // 원래있던 곳은 없애
							break; // 탈출
						}else if(k == N-1 && lastEmptySpace > 0) { // 내려오는데 기둥이없었고, 마지막까지 내려왔는데 빈공간이 한번이라도 있었다면,
							map[lastEmptySpace][j] = map[i][j]; // 맨 마지막 빈칸으로 넣어
							map[i][j] = -2; // 원래있던자리는 없애 
							break;
						}
					}
				}
			}
		}
	}
	
	private static void spin() { // 반시계 90도 회전
		int[][] tmpMap = new int[N][N];
		for(int i =0; i < N ; i++) {
			for(int j =0 ; j < N ;j++) {
				tmpMap[i][j] = map[j][N-1-i];
			}
		}
		for(int i =0 ; i < N ; i++) {
			map[i] = Arrays.copyOf(tmpMap[i], N);
		}
		
	}
	
	private static void init() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map =new int[N][N];
		for(int i =0 ;i < N ; i++) {
			st= new StringTokenizer(br.readLine());
			for(int j =0 ; j < N ;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	}
	
	private static class Node{
		int blockSize, rainbowBlocksNums;
		Point standardBlock;
	}
	
	private static void print() { // 검증
		for(int i = 0 ; i < N ; i++) {
			for(int j =0 ; j < N ; j++) {
				if(map[i][j] ==-2) System.out.print(" x ");
				else System.out.printf("%2d ", map[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

}
