import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// AABAAC: 010120
public class BJ_1786_찾기 {
	// 주어진 문자열
	private static char[] text;
	// 주어진 패턴
	private static char[] pattern;
	// 실패 배열
	// lsp(Longest Proper Prefix which is Suffix)배열이라고도 함
	private static int[] failure;
	
	// 실패 함수. 실패 배열을 구한다.
	private static void setFailure(int Plen) {
		int len = 0; // lps의 길이
		int i = 1; // 패턴 하위 문자열에 대한 suffix의 인덱스
		failure[0] = 0; // 단일 문자에 대한 proper prefix가 없으므로 0번 인덱스는 항상 0

		while(i < Plen){
			if (pattern[i] == pattern[len]){
				len++;
				failure[i++] = len;
			} else {
				if (len != 0){
					len = failure[len - 1];
				} else {
					failure[i++] = 0;
				}
			}
		}
	}
		
	
	public static void main(String[] args) throws IOException {
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String T = br.readLine();
		String P = br.readLine();
		int Tlen = T.length();
		int Plen = P.length();
		text = new char[Tlen];
		pattern = new char[Plen];

		for(int i = 0; i< Tlen; i++){
			text[i] = T.charAt(i);
		}
		for(int i = 0; i< Plen; i++){
			pattern[i] = P.charAt(i);
		}
		
		failure = new int[Plen];
		setFailure(Plen);

		int count = 0;
		// 텍스트의 인덱스
		int i = 0;
		// 패턴의 인덱스
		int j = 0;
		// 비교 시작
		while(i<Tlen){
			if (pattern[j] == text[i]){
				i++;
				j++;

				if (j == Plen){
					count++;
					sb.append((i - j + 1) + " ");
					j = failure[j-1];
				}
			} else {
				if (j != 0){
					j = failure[j - 1];
				} else {
					i++;
				}
			}
		}
		
		
		System.out.println(count);
		System.out.println(sb);
	}
}
