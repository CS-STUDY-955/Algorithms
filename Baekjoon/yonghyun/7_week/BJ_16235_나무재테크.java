import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 나무 재테크
 * https://www.acmicpc.net/problem/16235
 *
 * 1. 사계절을 각각 메서드로 만든다.
 *  - 봄에는 각 나무가 양분을 소비한다. food는 유지되어야 하기 때문에 배열을 복사하여 사용한다. 동일 위치라면 나이가 어린 나무가 먹으므로 정렬은 나이의 오름차순으로 한다.
 *  - 여름에는 죽은 나무를 골라 양분으로 변환한다. 이때는 리스트에서 아예 삭제한다.
 *  - 가을에는 각 나무의 나이를 확인하고 번식한다. for문과 d배열을 이용해 쉽게 구현할 수 있다.
 *  - 겨울에는 양분이 추가된다. 입력 받은 만큼의 food를 채워준다.
 * 2. 나무의 정보는 리스트로 따로 관리한다.
 *  - x, y좌표와 나이, 상태를 필드로 가진다.
 * 3. 양분의 정보는 배열로 저장한다.
 *
 * -------------------------- 구현 실수 -------------------------
 * 1. x와 y좌표를 입맛대로 바꾸려면 잘 봐야한다.
 *  - 문제에서 주어지는 건 x=r, y=c인데 몇가지만 y=r, x=c로 바꿨었다.
 *
 * -------------------------- 효율 문제 -------------------------
 * 1. 죽은 나무를 따로 저장하면 훨씬 빠르다.
 *  - 기존에는 나무의 상태를 죽은 나무로 바꿔서 전체 나무를 다시 탐색했다.
 *  - 죽은 나무를 따로 저장하면 죽은 나무만 탐색할 수 있다.
 * 2. 나무를 저장하는 ArrayList를 LinkedList로 변경했다.
 *  - 수정/삭제가 빈번히 일어나기 때문에 LinkedList가 빠르다.
 *  - LinkedList를 사용할 경우 for문보다 foreach문이 유의미하게 빠르다.
 *
 * @author 배용현
 *
 */
public class BJ_16235_나무재테크 {

	static class Tree implements Comparable<Tree> {
		int x, y, age;
		boolean isDead;

		public Tree(int x, int y, int age, boolean isDead) {
			this.x = x;
			this.y = y;
			this.age = age;
			this.isDead = isDead;
		}

		@Override
		public int compareTo(Tree o) {
			return age - o.age;
		}
	}

	static int N, M, K;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static int[][] refillFood, map;
	static List<Tree> trees = new LinkedList<>();
	static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
	static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};

	public static void main(String[] args) throws IOException {
		input();
		System.out.println(solution());
	}

	private static int solution() {
		while (K-- > 0) {		// K년동안 반복
			spring();
			summer();
			fall();
			winter();
		}

		return trees.size();		// 살아남은 나무의 수 리턴
	}

	private static void spring() {		// 봄에 일어나는 일
		trees.sort(null);		// 나이가 어린 나무부터 선택되도록 정렬
		for (Tree tree : trees) {
			if (map[tree.y][tree.x] >= tree.age) {			// 양분 먹을 수 있으면
				map[tree.y][tree.x] -= tree.age++;
			} else {		// 못먹으면
				tree.isDead = true;		// 주금
			}
		}
	}

	private static void summer() {		// 여름에 일어나는 일
		Iterator<Tree> it = trees.iterator();		// iterator를 이용하지 않으면 remove시 ConcurrentModificationException 발생
		while (it.hasNext()) {
			Tree next = it.next();
			if (next.isDead) {		// 나무가 죽었으면
				map[next.y][next.x] += next.age/2;		// 해당 칸에 양분이 생김
				it.remove();		// 리스트에서 삭제
			}
		}
	}

	private static void fall() {
		List<Tree> newTrees = new LinkedList<>();

		for (Tree tree : trees) {
			if (tree.age % 5 == 0) {		// 번식할 수 있는 나이의 나무면
				for (int i = 0; i < 8; i++) {		// 팔방으로 번식함
					int nx = tree.x + dx[i];
					int ny = tree.y + dy[i];
					if(isOut(nx, ny))		// 맵을 나가지만 않으면
						continue;

					newTrees.add(new Tree(nx, ny, 1, false));		// 나무 추가
				}
			}
		}

		trees.addAll(newTrees);
	}

	private static void winter() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] += refillFood[i][j];
			}
		}
	}

	private static boolean isOut(int nx, int ny) {
		return nx<0 || ny<0 || nx>=N || ny>=N;
	}

	private static void input() throws IOException {
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());
		M = parseInt(st.nextToken());
		K = parseInt(st.nextToken());
		refillFood = new int[N][N];
		map = new int[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				refillFood[i][j] = parseInt(st.nextToken());
				map[i][j] = 5;
			}
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int r = parseInt(st.nextToken()) - 1;
			int c = parseInt(st.nextToken()) - 1;
			int z = parseInt(st.nextToken());
			trees.add(new Tree(c, r, z, false));
		}
	}
}