package network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyNetClient01 {
	public static void main(String[] args) {
		// 사내 IP사용 중일 때: 같은 네트워크 망에 묶여있어야 통신할 수 있다.(안드로이드도 마찬가지)
		// 서버와 통신할 수 있는 소켓객체를 생성
		// host: cmd > ipconfig의 IP주소
		Socket socket;
		try {
			socket = new Socket("70.12.115.55", 12345);
			System.out.println("서버접속완료..."+socket);
		} catch (UnknownHostException e) { 
			// UnknownHostException은 IOException의 하위라서 IOException catch문 하나로도 
			// 처리 가능하다.
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
