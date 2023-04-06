import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 찾기
 * https://www.acmicpc.net/problem/1786
 *
 * 1. P를 확인하면서 시작 부분처럼 반복되는 문자열이
 *
 * @author 배용현
 *
 */
public class BJ_1786_찾기 {

    static int maxScore = 0;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static Node[] map;
    static int[] dice, selected;

    public static void main(String[] args) throws IOException {
        input();
        solution();
        print();
    }

    private static void solution() {

    }

    private static void print() {
        System.out.println(maxScore);
    }

    private static void input() throws IOException {

    }

}