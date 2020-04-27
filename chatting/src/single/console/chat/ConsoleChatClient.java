package single.console.chat;

import java.io.IOException;
import java.net.Socket;

public class ConsoleChatClient {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("70.12.115.55",12345);
			System.out.println("서버접속완료...");
			
			new Thread(new ClientSenderThread(socket)).start();
			new Thread(new ClientReceiveThread(socket)).start();
				
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
