package Gold.Gold3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * 1~N map[N+1][N+1]
 * 양분은 5만큼 있다.
 * M개의 나무를 심는다(한칸에 여러 나무 심어져 있을 수 있다.) Tree 2차원 배열로 만들어야하나?
 * 봄 : 나이만큼 양분 먹어(나이1증가) 한칸에 나무 여러개면 나이 어린놈 먼저 양분먹어
 * 		나이만큼 양분 못먹으면 죽어
 * 여름 : 죽은 나무가 양분으로 변함 : 나이를 2로 나눈 값을 양분으로 추가돼
 * 가을 : 나무 번식 : 나이가 5의 배수인 나무 > 8방향으로 나이1인 나무 추가
 * 겨울 : S2D2가 양분추가 
 * K년도가 지난후 남아있는 나무의 개수
 * 
 * 2차원 Tree[][] 
 * Tree안에 arrayList생성
 * 
 * @author SSAFY
 *
 */
public class BJ_16235_나무재테크 {
	static int N, M, K;
	static int[][] A; // 로봇이 더할 양분을 저장할 변수
	static ArrayList<Tree>[][] tree; // 나무정보를 담는 변수
	static ArrayList<Tree> deadTrees = new ArrayList<>(); // 죽은 나무 정보 
	static int[][] map; // 지도의 양분을 저장하는 변수
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		A = new int[N+1][N+1];
		map = new int[N+1][N+1];
		tree = new ArrayList[N+1][N+1];
		
		for(int i =1 ; i <= N ; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
				tree[i][j] = new ArrayList<Tree>();
				map[i][j] = 5;
			}
		}
		
		for(int i = 1; i <= M ; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int age = Integer.parseInt(st.nextToken());
			tree[x][y].add(new Tree(x, y, age));
		}
		
		for(int i =0; i< K; i++) { // k번 반복후
			// 봄 : tree돌면서 모든 tree에 대해 나이만큼 양분먹고 나이++, 나이만큼 못먹으면 죽고  arrayList에 담아
			spring();
			// 여름 : 죽은 treeList 에 담겨있는놈들 다 꺼내서 나이를 2로나눈양만큼 map에 더해줘
			summer();
			// 가을 : tree돌면서 age%5==0이면 8방향으로 나이 1인나무 추가
			fall();
			// 겨울 : map + A
			winter();
		}
		
		int sum =0;
		for(int i =1; i <= N; i++) {
			for(int j = 1; j <= N ; j++ ) {
				if(tree[i][j].size() != 0) {
					sum += tree[i][j].size();
				}
			}
		}
		System.out.println(sum);
	}
	private static int[] dx= {-1,-1,-1,0,1,1,1,0};
	private static int[] dy= {-1,0,1,1,1,0,-1,-1};
	
	private static void spring() {
		for(int i = 1; i <= N ;i++) {
			for(int j = 1; j <= N ; j++) {
				Collections.sort(tree[i][j], (a, b)-> {
					return a.age-b.age;	
				});
				for(int k = 0; k < tree[i][j].size() ; k++) {
					if( map[i][j] >= tree[i][j].get(k).age) { // 양분이 나이보다 많이 남아있으면
						map[i][j] -= tree[i][j].get(k).age; // 빼주고
						tree[i][j].get(k).age += 1; // 나이 1 증가
					}else {
						deadTrees.add(tree[i][j].remove(k)); // 나무 죽여, 죽인 나무를 넣어줘
						k--;
					}
				}
			}
		}
	}
	private static void summer() {
		for( Tree tree : deadTrees) {
			map[tree.x][tree.y] += tree.age/2;
		}
		deadTrees = new ArrayList<>(); 
	}
	
	private static void fall() {
		for(int i = 1; i <= N ; i++) {
			for(int j =1; j <= N ; j++) {
				if(tree[i][j].size() > 0) { // 나무가 있으면
					for( Tree t : tree[i][j]) { // 모든 나무에 대해
						if(t.age % 5 == 0) { // 나이가 5의 배수이면
							for(int k = 0; k < 8 ; k++) { // 8방향으로
								int nx = t.x + dx[k]; // 이동하고
								int ny = t.y + dy[k];
								if( 0< nx && 0< ny && nx <= N && ny <= N) { // 범위내면,
									tree[nx][ny].add(new Tree(nx, ny, 1)); // 나무 번식됐으니 추가
								}
							}
						}
					}
				}
			}
		}
	}
	private static void winter() {
		for(int i = 1; i <= N ;i++) {
			for(int j = 1; j<=N ; j++) {
				map[i][j] += A[i][j];
			}
		}
	}
	
	static class Tree implements Comparable<Tree>{
		int x, y;
		int age;
		public Tree(int x, int y, int age) {
			super();
			this.x = x;
			this.y = y;
			this.age = age;
		}
		@Override
		public int compareTo(Tree o) {
			return this.age - o.age; // 오름차순 정렬 : 나이가 짧은 놈 먼저 양분 먹어야하므로
		}
		
	}

}
