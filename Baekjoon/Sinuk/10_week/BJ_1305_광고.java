import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BJ_1305_광고 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int l = Integer.parseInt(br.readLine());
        char[] str = new char[l];
        str = br.readLine().toCharArray();

        int[] failure = new int[l];
        for (int i = 1, j = 0; i < l; i++) {
            while (j > 0 && str[i] != str[j]) {
                j = failure[j - 1];
            }
            if (str[i] == str[j]) {
                failure[i] = ++j;
            } else {
                failure[i] = 0;
            }
        }

        System.out.println(l - failure[l - 1]);
    }
}
