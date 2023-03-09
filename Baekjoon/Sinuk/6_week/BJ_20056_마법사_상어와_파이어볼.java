import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class BJ_20056_마법사_상어와_파이어볼 {
	private static int n;
	private static int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
	private static int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1 };

	// 파이어볼들의 정보를 담는 객체
	// sort 돌릴거라서 Comparable 구현
	private static class Fireball implements Comparable<Fireball> {
		int r;
		int c;
		int m;
		int s;
		int d;

		public Fireball(int r, int c, int m, int s, int d) {
			this.r = r;
			this.c = c;
			this.m = m;
			this.s = s;
			this.d = d;
		}

		@Override
		public int compareTo(Fireball o) {
			return this.r * n + this.c - o.r * n - o.c;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());

		// 파이어볼의 정보를 받아서 arraylist에 저장
		List<Fireball> fireballs = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int mi = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			fireballs.add(new Fireball(r, c, mi, s, d));
		}

		// 시뮬레이션 시작
		while (k-- > 0) {
			// 1. 모든 파이어볼 이동
			for (Fireball f : fireballs) {
				int tempR = f.r + f.s * dx[f.d];
				int tempC = f.c + f.s * dy[f.d];
				// 나가면 반대편으로 돌아와야 하므로 이렇게 처리
				tempR = (((tempR % n) + n) % n);
				tempC = (((tempC % n) + n) % n);
				f.r = tempR;
				f.c = tempC;
			}
			// 이동이 끝났으면 위치 순으로 정렬
			Collections.sort(fireballs);

			// 2. 2개 이상의 파이어볼이 있는 칸에서의 처리
			int idx = 0;
			// 합쳐진 후의 파이어볼들을 담는 임시 배열
			ArrayList<Fireball> tempFireballs = new ArrayList<>();
			while (idx < fireballs.size()) {
				// 2.1. 같은 칸의 파이어볼을 모두 하나로 합친다.
				// 마지막 인덱스면 나랑 같은 위치의 파이어볼이 있을 수가 없으니 바로 넣고 종료
				if (idx == fireballs.size() - 1) {
					tempFireballs.add(fireballs.get(idx));
					break;
				}
				// 현재 화염구랑 다음 화염구랑 위치가 다르면 temp에 넣고 다음 화염구로 넘어감
				if (fireballs.get(idx).compareTo(fireballs.get(idx + 1)) != 0) {
					tempFireballs.add(fireballs.get(idx++));
					continue;
				}
				// 위에서 안넘어 갔으면 현재 칸에 화염구가 2개 이상이므로
				// 질량의 합
				int mSum = 0;
				// 속력의 합
				int sSum = 0;
				// 해당위치의 파이어볼 갯수
				int ballCount = 1;
				boolean isOdd = false;
				boolean isEven = false;
				// 모든 파이어볼들을 조사하여
				while (idx++ < fireballs.size() - 1) {
					Fireball temp1 = fireballs.get(idx-1);
					Fireball temp2 = fireballs.get(idx);
					// 지금 파이어볼과 다음 파이어볼의 위치가 다르면 종료
					if (temp1.compareTo(temp2) != 0) {
						break;
					}
					// 아니면 mSum과 sSum에 값을 저장하고
					mSum += temp1.m;
					sSum += temp1.s;
					ballCount++;
					// d가 홀수인지 짝수인지 저장
					if (temp1.d % 2 == 0) {
						isEven = true;
					} else {
						isOdd = true;
					}
				}
				// 빠져나온 다음엔 남은 마지막 파이어볼에 대해 처리해주고
				idx--;
				mSum += fireballs.get(idx).m;
				sSum += fireballs.get(idx).s;
				if (fireballs.get(idx).d % 2 == 0) {
					isEven = true;
				} else {
					isOdd = true;
				}

				// 더해진 파이어볼들이 모두 짝수거나 홀수면 뭐시기 저시기 아니면 뭐시기 저시기
				int[] ds;
				if (isEven != isOdd) {
					ds = new int[] { 0, 2, 4, 6 };
				} else {
					ds = new int[] { 1, 3, 5, 7 };
				}
				// 만약 새 파이어볼 각각의 질량이 0이 아니라면 주어진 방향을 가진 파이어볼 4개 생성
				int newM = mSum / 5;
				if (newM != 0) {
					int newR = fireballs.get(idx).r;
					int newC = fireballs.get(idx).c;
					int newS = sSum / ballCount;
					for (int i = 0; i < 4; i++) {
						tempFireballs.add(new Fireball(newR, newC, newM, newS, ds[i]));
					}
				}
				idx++;
			}
			// 생성된 정보들을 기존 파이어볼 리스트로 옮김
			fireballs = new ArrayList<>(tempFireballs);
		}

		// 각 파이어볼의 질량 합쳐서 출력
		int sum = 0;
		for (Fireball fb : fireballs) {
			sum += fb.m;
		}
		System.out.println(sum);
	}
}
