package single.console.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// 서버에서 클라이언트로 데이터를 전송하는 작업을 수행하는 스레드
public class ServerSenderThread implements Runnable{
	Socket client;
	
	public ServerSenderThread(Socket client) {
		super();
		this.client = client;
	}
	
	@Override
	public void run() {
		PrintWriter out = null;
		BufferedReader keyin = null;
		try {
			out = new PrintWriter(client.getOutputStream(), true);
			keyin = new BufferedReader(
						new InputStreamReader(System.in));
			out.println("안녕하세요 클라이언트님?");
			String sendMsg=""; // 클라이언트로 보낼 메시지
			while(true) {
				sendMsg = keyin.readLine();
				if(sendMsg == null) {
					break;
				}
				out.println(sendMsg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
