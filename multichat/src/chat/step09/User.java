package chat.step09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;

public class User extends Thread{
	// ChatServerView에서 넘겨받을 데이터
	Socket client;
	// @원래는 별도의 서버 클래스가 있어야 하는데 우리는 귀찮으므로 서버뷰로 대체했다.
	ChatServerView serverView;
	
	// 서버에서 사용했던 스트림들 가져온다. => User는 서버의 데이터 보내기, 받기 스레드
	InputStream is;
	InputStreamReader ir;
	BufferedReader br; // 성능면에서 일반적으로 쓰임
	 
	OutputStream os;
	PrintWriter pw; // 성능면에서 일반적으로 쓰임
	
	String nickname;
	StringTokenizer st;
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
			// ClientChatView의 113라인 *한 번만 보내주므로 성격이 달라서 이렇게 받는다. 
			nickname = br.readLine();
			System.out.println("nickname: "+nickname);
			serverView.taclientlist.append("***********"+nickname+"님이 입장***********\n");
			
			broadCast("new/"+nickname);
			int size = userlist.size(); // 기존 접속자 인원수
			for(int i=0; i<size ;++i) {
				User user = userlist.get(i);
				// 그냥 sendMsg()를 쓰면 통신하고 있는 서버한테만 감
				sendMsg("old/"+user.nickname);
			}
			userlist.add(this);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	//	서버로 들어오는 데이터를 받으면 바로 보냄
	// 	=> 제약이 높다! : / 개수 제한
	private void filteringMsg(String msg) {
		System.out.println("서버가 받은 클라이언트의 메시지: "+msg);
		st = new StringTokenizer(msg, "/");
		String protocol = st.nextToken();
		if(protocol.equals("chatting")) {
			String message = st.nextToken();
			String nickname = st.nextToken();
			// 정해놓은 양식대로 모든 유저들에게 보냄
			// 클라이언트가 메시지를 보내면 연결되어 있는 모든 다른 클라이언트에게 메시지를 전송
			broadCast("chatting/"+message+"/"+nickname);
		}
	}
	
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
				// @ 받으면 찍고 보내고 무한반복
				String msg = br.readLine(); // 이 user가 보내는 메시지만 읽는다.
				serverView.taclientlist.append(nickname+"이 전송한 메시지: "+msg+"\n");
				
				filteringMsg(msg);
			} catch (IOException e) {
				//e.printStackTrace();
				// 3. 클라이언트 접속이 끊어지면 처리 ========================================
				serverView.taclientlist.append(nickname+"클라이언트와의 접속이 끊어짐\n");
				try {
					// 자원 반납
					if(is != null) is.close();
					if(ir != null) ir.close();
					if(br != null) br.close();
					if(os != null) os.close();
					if(pw != null) pw.close();
					if(client != null) client.close();
					userlist.remove(this); // 서버단 - 클라이언트 목록에서 this를 제거
					broadCast("out/"+nickname); // 다른 클라이언트에게 알려주는 작업
					// ===============================================================
				} catch (IOException e1) {
				}
				break;
			}
		}
	}
	
	
}
