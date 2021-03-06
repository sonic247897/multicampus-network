package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MyNetClient02 {
	public static void main(String[] args) {
		Socket socket;
		InputStream is = null; // 서버와 input할 수 있는 스트림(기본적으로 사용)
		// binary가 아닌 이상(이미지가 아니면) 좀더 효율적인 스트림 사용
		// BufferedREader, PrintWriter가 일반적
		DataInputStream dis = null; // 최종적으로 클라이언트와 DataInputStream을 이용해서
		// 클라이언트로부터 전송되는 데이터를 읽기
		OutputStream os = null; // 서버와 output할 수 있는 스트림
		DataOutputStream dos = null; 
		try {
			// @ 소켓 생성하는 것이 접속하는 것!!! 생성자 안에서 접속이 이루어짐
			socket = new Socket("70.12.115.55", 12345);
			System.out.println("서버접속완료..."+socket);
			
			// <상대방 소켓에서 스트림 얻어오기>
			// 클라이언트에서도 서버와 통신할 수 있는 소켓 객체로부터 input/output 스트림을 구한다.
			// InputStream 얻기
			is = socket.getInputStream();
			dis = new DataInputStream(is); //dis와 is 결합
			
			// OutputStream 얻기
			os = socket.getOutputStream();
			dos = new DataOutputStream(os); // dos와 os 결합
			
			// 1. 클라이언트 <- 서버 (서버가 보내는 데이터를 읽기 - 2번 연속해서 읽기)
			String data = dis.readUTF();
			System.out.println("서버가 전송하는 메시지1: "+data);
			int intdata = dis.readInt();
			System.out.println("서버가 전송하는 메시지2: "+intdata);
			
			// 2. 클라이언트 -> 서버
			dos.writeUTF("안녕하세요 서버님 클라이언트입니다.");
			
		} catch (UnknownHostException e) { 
			// UnknownHostException은 IOException의 하위라서 IOException catch문 하나로도 
			// 처리 가능하다.
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
