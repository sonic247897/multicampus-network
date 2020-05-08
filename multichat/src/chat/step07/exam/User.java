package chat.step07.exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

public class User extends Thread{
	// ChatServerView에서 넘겨받을 데이터
	Socket client;
	// @원래는 별도의 서버 클래스가 있어야 하는데 우리는 귀찮으므로 서버뷰로 대체했다.
	ChatServerView serverView;
	
	// 서버에서 사용했던 스트림들 가져온다.
	InputStream is;
	InputStreamReader ir;
	BufferedReader br; // 성능면에서 일반적으로 쓰임
	 
	OutputStream os;
	PrintWriter pw; // 성능면에서 일반적으로 쓰임
	
	String nickname;
	
	Vector<User> userlist;
	
	public User() {
		
	}
	
	public User(Socket client, ChatServerView serverView, Vector<User> userlist) {
		super();
		this.client = client;
		this.serverView = serverView; // 서버와 통신할 수 있게 받아옴
		this.userlist = userlist;
		ioWork();
	}
	
	// 접속한 클라이언트와 서버가 통신할 수 있도록 스트림 객체 생성
	public void ioWork() { //처음 접속했을 때 한 번 실행되는 메소드
		try {
			// InputStream()은 한글 읽을 수 X
			is = client.getInputStream();
			ir = new InputStreamReader(is);
			br= new BufferedReader(ir);
			
			os = client.getOutputStream();
			// @ auto-flush속성 true로 설정!!
			pw = new PrintWriter(os, true);
			
			// 클라이언트와 통신할 수 있는 스트림을 생성하고 클라이언트가 입장하면
			// 클라이언트가 전송하는 nickname을 읽어서 서버창에 출력하기 
			nickname = br.readLine();
			System.out.println("nickname: "+nickname);
			serverView.taclientlist.append("***********"+nickname+"님이 입장***********\n");
			
			// 2. 기존 for문을 메소드 호출문으로 변경
			broadCast("new/"+nickname);
			// ====================================================================
			// 3. 새로운 접속자(this)에게 기존 클라이언트의 정보를 알려주기 ======================
			int size = userlist.size(); // 기존 접속자 인원수
			for(int i=0; i<size ;++i) {
				User user = userlist.get(i);
				// 그냥 sendMsg()를 쓰면 통신하고 있는 서버한테만 감
				sendMsg("old/"+user.nickname);
			}
			// =====================================================================
			userlist.add(this);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	// 1. 기존 사용자에게 메시지를 보내는 메소드를 정의 ========================================
	// 	1) 접속했을 때 호출
	// 	2) 채팅 메시지 들어올 때마다 호출
	private void broadCast(String msg) {
		int size = userlist.size(); // 기존 접속자 인원수
		for(int i=0; i<size ;++i) {
			User user = userlist.get(i);
			// 그냥 sendMsg()를 쓰면 통신하고 있는 서버한테만 감
			user.sendMsg(msg); 
		}
	}
	
	
	public void sendMsg(String message) {
		pw.println(message);
	}
	
	public void run() {
		// while문 밖에 try-catch: 오류가 발생하면 while문이 끝나버린다.
		while(true) {
			// while문 안에 try-catch: 오류가 발생해도 다음 while문을 실행한다.
			try {
				String msg = br.readLine(); // 이 user가 보내는 메시지만 읽는다.
				serverView.taclientlist.append(nickname+"이 전송한 메시지: "+msg+"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
