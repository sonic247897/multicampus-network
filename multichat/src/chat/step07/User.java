package chat.step07;

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
	
	// 3. User객체에 기존 사용자의 정보를 액세스 할 수 있도록 변수 정의 ===========================
	Vector<User> userlist;
	// ===========================================================================
	
	public User() {
		
	}
	
	// 4. 서버가 접속한 클라이언트의 정보를 User객체로 만들 때 접속한 User의 소켓객체와 서버뷰, 
	// 	 userlist를 전달
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
			
			// 6. 새로운 클라이언트가 입장을 하면 벡터에 저장되어 있는 모든 클라이언트에게 입장을 알리는 메시지를 보낸다.
			int size = userlist.size(); // 기존 접속자 인원수
			for(int i=0; i<size ;++i) {
				User user = userlist.get(i);
				// 그냥 sendMsg()를 쓰면 통신하고 있는 서버한테만 감
				// 기존 사용자에게 보내야 함 - 기존 사용자 정보는 userlist에 들어있다!
				// @ '/'를 넣으면 토큰 하나. push알림이면 push/ (클라이언트와 서버의 동작을 정의)
				// 	':아이디'로 토큰을 또 다시 분리해서 특정 사용자에게만 메시지를 보낼 수도 있다.
				user.sendMsg("new/"+nickname); // 이미 접속한 사용자들한테 안내 - 새로 접속한 사용자의 nickname을 보낸다.
			}
			
			// 8. 접속자 리스트에 현재 새로 접속한 클라이언트 정보도 저장 =====================================
			userlist.add(this);
			// ================================================================================
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	// 7. 클라이언트에게 메시지를 보내는 메소드를 정의 ==================================================
	public void sendMsg(String message) {
		pw.println(message);
	}
	// ======================================================================================
	
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
