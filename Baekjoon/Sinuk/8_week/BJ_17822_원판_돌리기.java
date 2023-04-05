import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_17822_원판_돌리기 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int t = Integer.parseInt(st.nextToken());

    // disk에 대한 정보 받음
		int[][] disk = new int[n][m];
		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < m; j++) {
				disk[i][j] = Integer.parseInt(st.nextToken());
			}
		}
    // 각 디스크의 맨 위 숫자를 1차원 배열로 관리
		int[] index = new int[n];

		int[] x = new int[t];
		int[] d = new int[t];
		int[] k = new int[t];
		for (int i = 0; i < t; i++) {
			st = new StringTokenizer(br.readLine());
			x[i] = Integer.parseInt(st.nextToken());
			d[i] = Integer.parseInt(st.nextToken());
			k[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < t; i++) {
			// 1. 번호가 x[i]의 배수인 원판을 d[i] 방향으로 k[i]칸 회전시킨다.
			for (int j = x[i] - 1; j < n; j += x[i]) {
				// 시계방향이면
				if (d[i] == 0) {
					index[j] = (index[j] - k[i] + m) % m;
				}
				// 반시계 방향이면
				else if (d[i] == 1) {
					index[j] = (index[j] + k[i]) % m;
				}
			}

			// 2. 원판에 인접한 같은 수가 있는지 체크한다.
			boolean anySame = false;
			boolean[][] deleted = new boolean[n][m];

      // 모든 원판위의 숫자들에 대해 2방 탐색을 진행
			for (int a = 0; a < n; a++) {
				for (int b = 0; b < m; b++) {
					// 같은 디스크에서 자신 바로 다음 인덱스가 같은 숫자면 삭제 체크
          // 이미 지워진 숫자면 패스
					if (disk[a][b] != -1 && disk[a][b] == disk[a][(b + 1) % m]) {
						deleted[a][b] = true;
						deleted[a][(b + 1) % m] = true;
						anySame = true;
					}
					// 다른 디스크에서 자신 바로 다음 디스크가 같은 숫자면 삭제 체크
          // 마지막 디스크라서 다음 디스크가 없으면 스킵
					if (a == n - 1) {
						continue;
					}
          // 이미 지워진 숫자면 패스
					if (disk[a][(b + index[a]) % m] != -1 && disk[a][(b + index[a]) % m] == disk[a + 1][(b + index[a + 1]) % m]) {
						deleted[a][(b + index[a]) % m] = true;
						deleted[a + 1][(b + index[a + 1]) % m] = true;
						anySame = true;
					}
				}
			}

			// 3. 인접한 같은 수가 있다면 모두 지우고(-1로 만들고)
			if (anySame) {
				for (int a = 0; a < n; a++) {
					for (int b = 0; b < m; b++) {
						if (deleted[a][b]) {
							disk[a][b] = -1;
						}
					}
				}
			}
			// 4. 없다면 원판에 적힌 수의 평균을 구하고, 평균보다 큰 수에서 1을 빼고, 평균보다 작은 수에는 1을 더한다.
			else {
				int sum = 0;
				int count = 0;
				for (int a = 0; a < n; a++) {
					for (int b = 0; b < m; b++) {
						if (disk[a][b] != -1) {
							sum += disk[a][b];
							count++;
						}
					}
				}

        // 만약 모든 숫자들이 지워진게 아니라면
				if (count != 0) {
					int less = 0;
					int greater = 0;
          // 나누어 떨어졌을 경우
					if (sum % count == 0) {
						less = sum / count - 1;
						greater = less + 2;
          // 나누어 떨어지지는 않는 경우
					} else {
						less = sum / count;
						greater = less + 1;
					}

          // 모든 숫자들에 대해 평균보다 크면 감소, 작으면 증가, 이미 지워진 숫자면 패스
					for (int a = 0; a < n; a++) {
						for (int b = 0; b < m; b++) {
							if (disk[a][b] == -1)
								continue;
							if (greater <= disk[a][b]) {
								disk[a][b]--;
							} else if (disk[a][b] <= less) {
								disk[a][b]++;
							}
						}
					}
				}
			}
		}

    // 끝난 뒤의 남은 숫자 총합 출력
		int sum = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (disk[i][j] != -1) {
					sum += disk[i][j];
				}
			}
		}
		System.out.println(sum);
	}
}
