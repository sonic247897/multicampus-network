package chat.step09;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

// JFrame상속 - GUI창
public class ClientChatView extends JFrame {
	 JPanel contentPane;
	 JTextField txtinput;
	 JTextArea taChat;
	 JButton btnsend;
	 JList lstconnect;
	 String ip;
	 int port;
	 String nickname;
	 Socket socket;
	
	InputStream is;
	InputStreamReader ir;
	BufferedReader br; // 성능면에서 일반적으로 쓰임
		 
	OutputStream os;
	PrintWriter pw; // 성능면에서 일반적으로 쓰임
		 
	
	Vector<String> nicknamelist = new Vector<String>();
	StringTokenizer st;
	
	public ClientChatView(String ip, int port, String nickname) {
		this.ip = ip;
		this.port = port;
		this.nickname = nickname;
		// x버튼 누르면 닫힘
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 창 사이즈
		setBounds(100, 100, 758, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// setBounds, add: 붙이는 메소드
		taChat = new JTextArea();
		taChat.setFont(new Font("HY견고딕", Font.BOLD, 16));
		taChat.setBounds(12, 10, 501, 375);
		//contentPane.add(taChat);
		
		// text area를 스크롤에 붙임
		JScrollPane scroll = new JScrollPane(taChat);
		scroll.setBounds(12, 10, 501, 375); // text area와 같은 크기
		contentPane.add(scroll);
		
		txtinput = new JTextField();
		txtinput.setBounds(12, 395, 378, 35);
		contentPane.add(txtinput);
		txtinput.setColumns(10);
		
		btnsend = new JButton("\uC11C\uBC84\uB85C\uC804\uC1A1");
		btnsend.setFont(new Font("HY견고딕", Font.BOLD, 14));
		btnsend.setBounds(402, 395, 109, 35);
		contentPane.add(btnsend);
		
		JLabel lblNewLabel = new JLabel("\uC811\uC18D\uC790:");
		lblNewLabel.setFont(new Font("HY견고딕", Font.BOLD, 14));
		lblNewLabel.setBounds(519, 10, 120, 35);
		contentPane.add(lblNewLabel);
		
		lstconnect = new JList();
		lstconnect.setBounds(525, 47, 205, 108);
		contentPane.add(lstconnect);
		// 접속한 사용자의 정보가 nicknamelist에 저장되어 있고 그 벡터를 JList에 출력
		lstconnect.setListData(nicknamelist);
		
		setVisible(true); // 화면에 JFrame을 보이도록 설정
		
		// 이벤트 연결하기
		ClientChatListener listener = new ClientChatListener(this);
		txtinput.addActionListener(listener);
		btnsend.addActionListener(listener);
		
		connectServer(); // 서버에 접속
	}
	
	public void connectServer() {
		try {
			// 서버에 접속
			socket = new Socket(ip, port);
			
			if(socket != null) {
				// 서버와 통신할 수 있는 io 만들기
				ioWork();
			}
			
			// 서버한테 nickname 보내기
			sendMsg(nickname);
			// 내 벡터에 저장
			nicknamelist.add(nickname);
			// 서버가 보내오는 메시지를 받는 스레드
			Thread receiveThread = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						String msg = "";
						try {
							msg = br.readLine();
							System.out.println("서버가 전달한 메시지>>"+msg);
							filteringMsg(msg);
						} catch (IOException e) {
							// 개발 전용
							//e.printStackTrace();
							// 2. 서버쪽에서 연결이 끊어지는 경우 사용자는 자원을 반납 ==========
							// - null체크 필요
							try {
								if(is != null) is.close();
								if(ir != null) ir.close();
								if(br != null) br.close();
								if(os != null) os.close();
								if(pw != null) pw.close();
								if(socket != null) socket.close();
								JOptionPane.showMessageDialog(null, 
										"서버와 접속이 끊어짐", "알림",
										JOptionPane.ERROR_MESSAGE);
								
							} catch (IOException e1) {
							}
							break; // 오류 발생하면 반복문 빠져나가도록 설정
						}
					}
				}
			});
			receiveThread.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 서버가 보내오는 메시지를 분석하는 메소드
	private void filteringMsg(String msg) {
		st = new StringTokenizer(msg, "/");
		String protocol = st.nextToken(); // 어떤 작업을 했는지를 알 수 있는 keyword
		// ex) new, old, chatting, ...
		String message = st.nextToken();
		System.out.println("프로토콜: "+protocol+", 메시지: "+message);
		if(protocol.equals("new")) {
			// 새로운 사용자가 접속하면 실행되는 부분
			// nicknamelist에 추가
			nicknamelist.add(message); //닉네임 추가
			// 벡터의 데이터를 새로고침
			lstconnect.setListData(nicknamelist);
			// 클라이언트 창에 메시지 출력
			taChat.append("****************"+message+"님이 입장하셨습니다.***************\n");
		}else if(protocol.equals("old")) {
			nicknamelist.add(message); // 호출될 때마다 nickname을 vector에 저장
			lstconnect.setListData(nicknamelist);
		} 
		else if(protocol.equals("chatting")) {
			String nickname = st.nextToken();
			taChat.append(nickname+">>"+message+"\n");
		} // 1. 서버가 전달한 메시지의 프로토콜이 out이면 nickname리스트에서 해당 nickname을 제거============
		else if(protocol.equals("out")) {
			nicknamelist.remove(message); // /가 두 개밖에 없을때는 message가 닉네임
			lstconnect.setListData(nicknamelist);
			taChat.append("**************"+message+"님이 퇴장하셨습니다.******************\n");
		}
		// ================================================================================
	}
	
	
	
	
	
	public void ioWork() {
		try {
			// InputStream()은 한글 읽을 수 X
			is = socket.getInputStream();
			ir = new InputStreamReader(is);
			br= new BufferedReader(ir);
			
			os = socket.getOutputStream();
			// @ auto-flush속성 true로 설정!!
			pw = new PrintWriter(os, true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMsg(String message) {
		System.out.println("클라이언트가 서버에게 메시지 전송하기: "+message);
		pw.println(message); // print() 쓰면 안 가므로 println() 써야한다!
	}
}
