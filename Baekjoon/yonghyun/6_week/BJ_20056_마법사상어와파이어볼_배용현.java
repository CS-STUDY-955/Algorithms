import java.io.*;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * 마법사 상어와 파이어볼
 * https://www.acmicpc.net/problem/20056
 *
 * 1. 각 파이어볼을 저장하는 리스트를 만들고 초기화한다.
 * 2. 각 파이어볼을 순회하며 지정된 방향으로 움직인다.
 * 3. 겹친 파이어볼을 찾아 모두 합친다.
 * 4. 합친 파이어볼을 주어진 연산에 따라 나눈다.
 * 5. 질량이 0인 파이어볼을 삭제한다.
 * 6. K만큼 이동한 후, 남은 파이어볼 질량의 합을 출력한다.
 * 
 * @author 배용현
 *
 */
public class BJ_20056_마법사상어와파이어볼_배용현 {
	static class Fireball {
		int r, c, m, s, d;

		public Fireball(int r, int c, int m, int s, int d) {
			this.r = r;
			this.c = c;
			this.m = m;
			this.s = s;
			this.d = d;
		}
	}

	static int N, M, K;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static List<Fireball> fireballs = new ArrayList<>();
	static Queue<Fireball>[][] fbArray;
	static int[] dx = {0, 1, 1, 1, 0, -1, -1, -1};
	static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
	
	public static void main(String[] args) throws IOException {
		input();

		for (int i = 0; i < K; i++) {
			move();
			fusionAndDivide();
		}

		int answer = 0;
		for (Fireball f : fireballs)
			answer += f.m;

		System.out.print(answer);
	}

	private static void fusionAndDivide() {
		for (int i = 0; i < N; i++) {		// 모든 위치에 대해 확인
			for (int j = 0; j < N; j++) {
				int size = fbArray[i][j].size();		// 현재 위치의 파이어볼 개수
				if (size >= 2) {		// 파이어볼이 현재 위치에 2개 이상 존재하면 다음과 같이 처리
					int mSum = 0, sSum = 0;		// 질량합, 속도합
					boolean odd = true, even = true;		// 전부 홀수 여부, 전부 짝수 여부

					while (!fbArray[i][j].isEmpty()) {		// 현재 위치의 파이어볼 전부 뽑아서 처리
						Fireball f = fbArray[i][j].poll();
						mSum += f.m;
						sSum += f.s;

						if(f.d%2==0)		// 짝수 방향이면
							odd = false;		// 홀수만 존재하는 조건 실패
						else 		// 홀수 방향이면
							even = false;		// 짝수만 존재하는 조건 실패

						fireballs.remove(f);		// 합쳐지므로 사라진다.
					}

					int nm = mSum / 5;		// 나뉜 파이어볼의 질량은 합/5
					if(nm==0)		// 질량이 0이면 패스
						continue;

					int ns = sSum / size;		// 나뉜 파이어볼의 속도는 합/합쳐진 크기

					if (odd || even) {		// 모두 홀수거나 짝수이면
						for (int k = 0; k < 8; k+=2)		// 0, 2, 4, 6의 방향을 가짐
							fireballs.add(new Fireball(i, j, nm, ns, k));
					} else {		// 아니면
						for (int k = 1; k < 8; k+=2)		// 1, 3, 5, 7의 방향을 가짐
							fireballs.add(new Fireball(i, j, nm, ns, k));
					}
				} else {		// 1개만 존재하는 곳은 다음 사용을 위해 별도로 초기화
					fbArray[i][j].clear();
				}
			}
		}
	}

	private static void move() {		// 파이어볼을 이동시키는 메서드
		for (Fireball f : fireballs) {		// 모든 파이어볼은 주어진 방식대로 이동한다.
			f.r = (N + f.r + dy[f.d] * (f.s % N)) % N;		// 각 좌표가 d방향으로 s만큼 움직인다.
			f.c = (N + f.c + dx[f.d] * (f.s % N)) % N;		// 단, N은 1과 연결되어 있으므로 N으로 모듈러연산을 수행해 이어준다.
			fbArray[f.r][f.c].add(f);		// 현재 위치 파이어볼 집합에 추가
		}
	}

	private static void input() throws IOException {		// 각종 변수 입력받고 세팅하는 메서드
		st = new StringTokenizer(br.readLine());
		N = parseInt(st.nextToken());
		M = parseInt(st.nextToken());
		K = parseInt(st.nextToken());
		fireballs = new ArrayList<>();
		fbArray = new Queue[N][N];

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			fireballs.add(new Fireball(
					parseInt(st.nextToken()) - 1,
					parseInt(st.nextToken()) - 1,
					parseInt(st.nextToken()),
					parseInt(st.nextToken()),
					parseInt(st.nextToken())
			));
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++)
				fbArray[i][j] = new ArrayDeque<>();
		}
	}
}