package single.console.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

// 서버에서 클라이언트가 전송하는 데이터를 읽는 작업을 수행하는 스레드
public class ServerReceiveThread implements Runnable {
	Socket client;
	
	public ServerReceiveThread(Socket client) {
		super();
		this.client = client;
	}

	// 클라이언트 전달한 데이터를 읽어서 출력
	@Override
	public void run() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String resMsg = "";
			while(true) {
				resMsg = in.readLine();
				if(resMsg==null) {
					break;
				}
				System.out.println("클라이언트>>"+resMsg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
