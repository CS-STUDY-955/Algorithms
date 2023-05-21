package com.baekjoon.p5g12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_17825_주사위윷놀이 {
	
	static int max_score = 0; // 최대값
	static int[] dices; // 주사위
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		dices = new int[10]; // 주사위
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i < 10; i++) {
			dices[i] = Integer.parseInt(st.nextToken());
		}
		
		bfs(); // 주사위 굴리기
		System.out.println(max_score); // 출력
	}
	
	public static void bfs() {
		Queue<int[]> queue = new ArrayDeque<int[]>();
		
		// queue: 각 주사위의 위치, 점수, 다음 던질 주사위 번호(0~9)
		// 첫번째 말 나가기
		if(dices[0] == 5) // 주사위 5인 경우 110에 도착
			queue.offer(new int[] {110, 0, 0, 0, 10, 1});
		else // 나머지 경우 *2에 도착
			queue.offer(new int[] {dices[0]*2, 0, 0, 0, dices[0]*2, 1});
		
		while(!queue.isEmpty()) { // BFS
			int[] status = queue.poll();
			int d = status[5]; // 이번 턴에 던질 주사위 숫자
			
			for(int i = 0; i < 4; i++) { // 각 말에 대해서
				if(status[i] < 0) continue; // 이미 말이 도착한 경우(-1) 건너뛰기
				
				int[] nstatus = status.clone(); // 다음 상태
				int left = dices[d]; // left : 이동할 거리(주사위 숫자)
				if(nstatus[i] >= 400) { // 25, 30, 35, 40 라인인 경우 (400 라인)
					nstatus[i] += left * 5; // 숫자가 5씩 증가함
				}
				else if(nstatus[i] >= 300) { // 30, 28, 27, 26 라인인 경우 (300 라인)
					if(nstatus[i] == 330) nstatus[i]--; // 정확히 30인 경우 처음엔 2만큼 감소함
					nstatus[i] -= left; // 주사위 숫자만큼 감소
					if(nstatus[i] <= 325) { // 325 이하인 경우
						nstatus[i] = 425 + (325 - nstatus[i]) * 5; // 400라인으로 값 조정
					}
				}
				else if(nstatus[i] >= 200) { // 20, 22, 24 라인인 경우 (200 라인)
					nstatus[i] += left * 2; // 주사위 숫자*2만큼 증가
					if(nstatus[i] >= 226) { // 226 이상인 경우
						nstatus[i] = 425 + (nstatus[i] - 226) / 2 * 5; // 400라인으로 값 조정
					}
				}
				else if(nstatus[i] >= 100) { // 10, 13, 16, 19 라인인 경우 (100 라인)
					nstatus[i] += left * 3; // 주사위 숫자*3만큼 증가
					if(nstatus[i] >= 122) { // 122 이상인 경우
						nstatus[i] = 425 + (nstatus[i] - 122) / 3 * 5; // 400라인으로 값 조정
					}
				}
				else { // 그 밖 가장 바깥 라인
					nstatus[i] += left * 2; // 주사위 숫자*2만큼 증가
					if(nstatus[i] == 10 || nstatus[i] == 20 || nstatus[i] == 30 || nstatus[i] == 40)
						nstatus[i] += nstatus[i] * 100 / 10; // 100, 200, 300, 400 라인인 경우 값 조정
				}
				
				boolean overlap = false; // 중복 검사
				for(int j = 0; j < 4; j++) {
					if(i == j) continue;
					if(nstatus[i] == status[j]) {
						overlap = true; // 중복인 경우
						break;
					}
				}
				if(overlap) { // 중복인 경우 해당 말 건너뛰기
					if(status[i] == 0) break; // 해당 말이 출발점인 경우 반복문 탈출 (뒷 말도 다 출발점이므로)
					continue;
				}

				
				if(nstatus[i] % 100 > 40) nstatus[i] = -1; // 이동한 곳의 값이 40 이상인 경우 도착
				else nstatus[4] += nstatus[i] % 100; // 아니면 점수 더해주기
				if(++nstatus[5] <= 9) // 아직 다 안굴렸다면 굴리기
					queue.offer(nstatus);
				else // 다 굴린 경우 최대값 갱신
					max_score = max_score > nstatus[4] ? max_score : nstatus[4];
			}
		}
	}
}
