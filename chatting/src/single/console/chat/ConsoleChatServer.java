package single.console.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConsoleChatServer {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(12345);
			System.out.println("서버접속완료...");
			// 현재는 1:1 채팅이므로 while(true)문 필요없음
			// 멀티채팅: while문이 끝나면 client가 대체되지 않도록 저장해놔야 한다. 
			while(true) {
				Socket client = server.accept();
				
				new Thread(new ServerSenderThread(client)).start();
				new Thread(new ServerReceiveThread(client)).start();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
