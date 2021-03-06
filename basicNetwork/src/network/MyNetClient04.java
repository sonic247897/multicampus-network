package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyNetClient04 {
	public static void main(String[] args) {
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			socket = new Socket("70.12.115.55",12345);
			System.out.println("서버접속완료....");
			in = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));
			// PrintWriter(Writer out, boolean autoFlush)
			// : 생성할 때 autoFlush 옵션을 설정할 수도 있다.
			out = new PrintWriter(socket.getOutputStream(), true);
			
			// ******************통신시작*******************
			// 1. 클라이언트 <- 서버
			String msg = in.readLine();
			System.out.println("서버가 보내온 메시지: "+msg);
			
			// 2. 클라이언트 -> 서버
			out.println("서버야 안녕... 나는 클라이언트");
			//out.flush(); - autoFlush옵션
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}

