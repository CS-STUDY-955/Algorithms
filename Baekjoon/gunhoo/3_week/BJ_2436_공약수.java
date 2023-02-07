package Gold;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 * <pre>
 * 1. 문제 풀이 방법
 *      - 브루트포스? (30분)
 *          > 예제 2 입력시간 초과
 *      - GCD 구하는 재귀 조건 추가(1시간) : 14%에서 시간초과
 *      - j index 범위 조정(1시간) : 52%에서 시간초과
 * 2. 문제 복기(솔루션)
 *      - a와 b의 최대공약수와 최소공배수의 곱은 ab와 같다.
 *      - 따라서 최대 공약수와 최소 공배수의 곱을 multiple 변수로 저장한다
 * 
 * </pre>
 * @author 박건후
 * 
 */
public class BJ_2436_공약수 {
	
	    public static void main(String[] args)  throws Exception{
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        StringTokenizer st = new StringTokenizer(br.readLine());
	        int GCD = Integer.parseInt(st.nextToken());
	        int LCM = Integer.parseInt(st.nextToken());
	        long multiple = (long)GCD*LCM;
	        int a = 0, b = 0;
	        for(int i = GCD; i <= Math.sqrt(multiple); i += GCD){
	            if( multiple % i == 0 && calGCD2(i, multiple/i) == GCD){ // i와 multiple/i 의 최대공약수가 GCD라면,
	                a = i;
	                b = (int)(multiple/i);
	            }
	        }
	        System.out.println(a+" "+b);
	    }/*
	    public static void main2(String[] args) throws Exception{
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        StringTokenizer st = new StringTokenizer(br.readLine());
	        int GCD = Integer.parseInt(st.nextToken());
	        int LCM = Integer.parseInt(st.nextToken());
	        int a = GCD;
	        int b = GCD;
	        int min = Integer.MAX_VALUE; // 여러개일 경우 min값을 찾아야 함으로 이를 저장하기 위한 변수
	        int mina = GCD;     // 왼쪽값
	        int minb = LCM;     // 오른쪽값
	        
	        for(int i = 2; i< LCM/(GCD*2) ; i++){
	            a = GCD * i;
	            if( a*2+1 >= min) break; // i가 계속 증가하지 않고 탈출하게끔 하는 종료조건
	            for(int j = i+1; j <= LCM/a ; j++){
	                b = GCD * j;
	                if(a+b >= min){ // j가 계속 증가하지 않고 탈출하게끔 하는 종료조건
	                    break;
	                }
	                // i랑 j는 GCD 없어야해
	                if(calGCD2(i, j) != 1) continue;
	                // System.out.println("살아남은 "+i+" : "+j+"idx: "+idx);

	                if ((a*j == b*i) && b*i == LCM){
	                    min = a+b;
	                    mina = a;
	                    minb = b;
	                    // System.out.println(i+","+j+" idx: "+idx+" end:"+LCM/mina);
	                }
	            }
	        }
	        System.out.println(mina+" "+minb);
	    } */
	    /** 재귀 */
	    private static long calGCD2(long a, long b){
	        if( Math.max(a, b)%Math.min(a, b) == 0) return Math.min(a, b);
	        return calGCD2(Math.min(a, b), Math.max(a, b)%Math.min(a, b));
	    }
	    /** 반복*/ 
	    private static int calGCD(int a, int b){
	        int n;
	        if( a > b){
	            int tmp = b;
	            b = a;
	            a = tmp;
	        }
	        while ( a != 0){
	            n = b%a;
	            b = a;
	            a = n;
	        }
	        return b;
	    }
	    
	}
