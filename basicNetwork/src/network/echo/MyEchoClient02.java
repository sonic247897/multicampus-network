package network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyEchoClient02 {
	public static void main(String[] args) {
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		// 키보드 입력을 위한 BufferedReader가 하나 더 있어야 한다.
		BufferedReader keyin = null;
		
		try {
			socket = new Socket("70.12.115.55",12345);
			System.out.println("서버접속완료....");
			in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));
			// PrintWriter(Writer out, boolean autoFlush)
			// : 생성할 때 autoFlush 옵션을 설정할 수도 있다.
			out = new PrintWriter(socket.getOutputStream(), true);
			
			// 키보드로 입력한 데이터를 읽기 위한 스트림 생성
			keyin = new BufferedReader(
						new InputStreamReader(System.in));
			
			// ******************통신시작*******************
			System.out.println(in.readLine());
			
			// 키보드로 입력한 데이터를 서버로 지속적으로 전송
			// 서버가 다시 보내오는 데이터를 콘솔에 출력
			String sendMsg = ""; // 서버로 보낼 메시지
			String resMsg = ""; // 서버에서 받는 메시지
			
			
			while((sendMsg = keyin.readLine()) != null) {
				// 1. 클라이언트 -> 서버 (키보드로 입력하는 데이터를 서버로 전송)
				out.println(sendMsg);
				// 2. 클라이언트 <- 서버
				resMsg = in.readLine();
				System.out.println("서버가 보내는 메시지>> "+resMsg);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

