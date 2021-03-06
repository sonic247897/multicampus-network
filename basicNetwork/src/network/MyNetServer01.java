package network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MyNetServer01 {
	public static void main(String[] args) {
		// 네트워크 - 서버~~클라이언트 쌍으로 작업!! [하나로만 작업 못한다]
		// 네트워크 - 패킷(바이트) 단위로 송수신하므로 IOException
		try {
			// 클라이언트와 통신할 수 있도록 준비 - 포트를 열어둠
			// (port: 사용 안하는 포트 사용)
			ServerSocket server = new ServerSocket(12345);
			System.out.println("서버준비완료....클라이언트의 접속을 기다림");
			// 클라이언트가 접속할 때까지 대기
			// - 리스닝하면서 대기하다가 클라이언트가 접속하면 
			// 	접속한 클라이언트의 소켓객체를 읽어서 리턴(상대방의 소켓을 알아야 통신가능)
			Socket client = server.accept();
			// InetAddress: IP를 모델링한 객체
			InetAddress clientInfo = client.getInetAddress();
			
			System.out.println("접속한 클라이언트: "+clientInfo.getHostAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
