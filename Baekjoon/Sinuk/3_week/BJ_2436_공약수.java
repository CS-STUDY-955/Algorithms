import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

// 방법 1. 에라토스테네스의 채를 통해 소수들을 구하고, 그걸로 소인수분해 하기
// 에라토스테네스의 채로 1억까지의 소수를 먼저 구한다고 하면
// 에라토스테네스의 시간복잡도가 nlog(logn))이고  n<=1억이니 시간초과

// 방법 2. 생각해보니 굳이 에라토스테네스의 채를 쓸 필요가 없음
// 구하려고 하는건 약수의 쌍이지, 소인수 분해 결과가 아님
// 약수의 쌍을 구하는 방식으로 하면 sqrt(lcm/gcd)까지만 구해도 됨

// 공약수
public class BJ_2436_공약수 {
	// 최대공약수를 구하기 위한 유클리드 호제법
	public static int getGCD(int a, int b) {
		if (b == 0) {
			return a;
		}
		return getGCD(b, a % b);
	}

	// 어짜피 마지막 값만 쓸거, 콜렉션은 왜 씀?
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stringTokenizer = new StringTokenizer(br.readLine());
		int gcd = Integer.parseInt(stringTokenizer.nextToken());
		int lcm = Integer.parseInt(stringTokenizer.nextToken());
		
		// 어짜피 마지막에 구한 값이 가장 적은 차를 가질 것이라는 추론이 가능하니, 배열로 안만들어도 됨
		int found = 0;
		int partner = 0;
		
		// i/gcd*i같은 부분에서 i*i/gcd로 하면 int값 범위를 넘어서니 주의
		for (int i = gcd; i / gcd * i <= lcm; i += gcd) {
			// 최소공배수의 약수이며, 둘 사이의  최대공약수가 우리가 받은 최대공약수와 같은지 체크
			// 두 수의 곱은 두 수의 최대공약수와 최대공배수의 곱과 같다는 점을 이용
			if (lcm % i == 0 && getGCD(i, lcm / i * gcd) == gcd) {
				found = i;
				partner = lcm / i * gcd;
			}
		}

		System.out.printf("%d %d\n", found, partner);
	}

	// 어짜피 찾아봐야 마지막에 찾은 쌍이 나올텐데 뭐하러 min값을 찾아?
	public static void main2(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stringTokenizer = new StringTokenizer(br.readLine());
		int gcd = Integer.parseInt(stringTokenizer.nextToken());
		int lcm = Integer.parseInt(stringTokenizer.nextToken());
		long mul = gcd * lcm;

//		int divided = lcm / gcd;
		ArrayList<Integer> found = new ArrayList<>();
		ArrayList<Integer> partner = new ArrayList<>();
		int count = -1;
//		int min = Integer.MAX_VALUE;
//		int minIdx = 0;
		for (int i = gcd; (long) i * i <= mul; i += gcd) {
			if (lcm % i == 0 && getGCD(i, lcm / i * gcd) == gcd) {
				count++;
				found.add(i);
				partner.add(lcm / i * gcd);
//				System.out.printf("하나 찾음: %d %d\n", found.get(found.size() - 1), partner.get(partner.size() - 1));
//				if (i + divided / i < min) {
//					min = i + divided / i;
//					minIdx = count;
//					System.out.printf("최솟값 %d %d으로 갱신됨\n", found.get(minIdx) * gcd, partner.get(minIdx) * gcd);
//				}
			}
		}

		System.out.printf("%d %d\n", found.get(count), partner.get(count));
	}

	// 정직하게 모든 약수의 쌍을 저장하고, 각각의 차가 가장 작은 값을 찾는 방식
	public static void main1(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer stringTokenizer = new StringTokenizer(br.readLine());
		int gcd = Integer.parseInt(stringTokenizer.nextToken());
		int lcm = Integer.parseInt(stringTokenizer.nextToken());

		int divided = lcm / gcd;
		ArrayList<Integer> found = new ArrayList<>();
		ArrayList<Integer> partner = new ArrayList<>();
		int count = -1;
		int min = Integer.MAX_VALUE;
		int minIdx = 0;
		for (int i = 1; i * i <= divided; i++) {
			if (divided % i == 0 && getGCD(i * gcd, divided / i * gcd) == gcd) {
				count++;
				found.add(i);
				partner.add(divided / i);
//				System.out.printf("하나 찾음: %d %d\n", found.get(found.size() - 1), partner.get(partner.size() - 1));
				if (i + divided / i < min) {
					min = i + divided / i;
					minIdx = count;
//					System.out.printf("최솟값 %d %d으로 갱신됨\n", found.get(minIdx) * gcd, partner.get(minIdx) * gcd);
				}
			}
		}

		System.out.printf("%d %d\n", found.get(minIdx) * gcd, partner.get(minIdx) * gcd);
	}
}
