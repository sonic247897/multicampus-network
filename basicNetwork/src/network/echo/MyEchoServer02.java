package network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

// 바이너리 데이터를 읽고 쓰지 않는 경우 - 문자열
/* 주로 사용(성능면에서 효율적!)
 input - BufferedReader
 output - PrintWriter
 */
public class MyEchoServer02 {
	public static void main(String[] args) {
		Socket client = null;
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			ServerSocket server = new ServerSocket(12345);
			System.out.println("서버접속완료....");

			while(true) {
				client = server.accept();
				InetAddress clientInfo = client.getInetAddress();
				System.out.println("접속한 클라이언트: "+clientInfo.getHostAddress());
				in = new BufferedReader(
						new InputStreamReader(
								client.getInputStream()));
				out = new PrintWriter(client.getOutputStream(),true);
				
				// ******************통신시작*******************
				out.println("안녕하세요 클라이언트님?");
				
				
				// 클라이언트가 보내오는 데이터를 다시 클라이언트에게 전송
				String resMsg = ""; // 클라이언트가 보내오는 데이터를 저장
				while(true){
					// 1. 서버 <- 클라이언트
					resMsg = in.readLine();
					// 2. 서버 -> 클라이언트
					if(resMsg==null) {
						break;
					}else if(resMsg.startsWith("안녕하세요") | resMsg.startsWith("하이")){
						out.println(clientInfo.getHostAddress()+"님 반가워요");
					}else if(resMsg.equals("오늘 날짜는")) {
						out.println(new Date().toString());
					}else {
						out.println(clientInfo.getHostAddress()+"님 어여가~~~~");
					}
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
