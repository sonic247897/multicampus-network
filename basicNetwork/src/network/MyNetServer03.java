package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyNetServer03 {
	public static void main(String[] args) {
		Socket client = null;
		InputStream is = null;
		DataInputStream dis = null;
		OutputStream os = null;
		DataOutputStream dos = null;
		
		try {
			ServerSocket server = new ServerSocket(12345);
			System.out.println("서버준비완료");
			while(true) {
				client = server.accept();
				System.out.println("접속한 클라이언트: "+client.getInetAddress());
				
				is = client.getInputStream();
				dis = new DataInputStream(is);
				os = client.getOutputStream();
				dos = new DataOutputStream(os);
				
				// 1. 서버 -> 클라이언트
				dos.writeUTF("안녕하세요 클라이언트님");
				int num = 2+ (int)(Math.random()*8);
				dos.writeInt(num);
				
				// 2. 서버 <- 클라이언트
				System.out.println(dis.readUTF());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
