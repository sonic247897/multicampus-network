package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/*
 OutputStream, InputStream:
byte단위 입출력의 최상위 스트림 - 문자 뿐 아니라 이미지도 주고받기 때문
안드로이드  - 서버와 연결해서 데이터 가져오는 경우가 일반적
	
 1. HTTP: 한 번 요청이 가서 응답이 오면 끊어진다.
 	(카톡같은거 불가능 - 서버에서 연결을 지속해서 수신한 데이터를 모두에게 뿌려줘야 하므로)
	보완기술: HTTP에서 웹소켓 기술 사용하기
 2. TCP: 채팅 같은 거 구현할 때 사용
*/

public class MyNetServer02 {
	public static void main(String[] args) {
		Socket client = null;
		InputStream is = null; // 클라이언트와 input할 수 있는 스트림(기본적으로 사용)
		// binary가 아닌 이상(이미지가 아니면) 좀더 효율적인 스트림 사용한다:
		// BufferedReader, PrintWriter가 일반적
		DataInputStream dis = null; // 최종적으로 클라이언트와 DataInputStream을 이용해서
									// 클라이언트로부터 전송되는 데이터를 읽기[효율적]
		OutputStream os = null; // 클라이언트와 output할 수 있는 스트림
		DataOutputStream dos = null; 
		
		try {
			// 클라이언트와 통신할 수 있도록 준비 - 포트를 열어둠
			ServerSocket server = new ServerSocket(12345);
			System.out.println("서버준비완료....클라이언트의 접속을 기다림");
			
			// 여러 클라이언트에게 서비스를 하기 위해서 무한루프
			while(true) {
				client = server.accept();
				// InetAddress: IP를 모델링한 객체
				InetAddress clientInfo = client.getInetAddress();
				
				System.out.println("접속한 클라이언트: "+clientInfo.getHostAddress());
				
				// <상대방 소켓에서 스트림 얻어오기>
				// 클라이언트와 통신하기 위한 Input/Output Stream객체를 소켓으로부터 구한다.
				// InputStream 얻기
				is = client.getInputStream();
				dis = new DataInputStream(is); //dis와 is 결합
				
				// OutputStream 얻기
				os = client.getOutputStream();
				dos = new DataOutputStream(os); // dos와 os 결합
				
				// 1. 서버 -> 클라이언트(순서가 중요...output을 연속으로 두 번)
				dos.writeUTF(clientInfo.getHostAddress()+"님 접속을 환영합니다.");
				dos.writeInt(20000);
				
				// 2. 서버 <- 클라이언트
				String data = dis.readUTF();
				System.out.println("클라이언트가 보낸 메시지: "+data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
