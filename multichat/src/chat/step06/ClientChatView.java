package chat.step06;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
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
		taChat.setBounds(12, 10, 501, 375);
		contentPane.add(taChat);
		
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
			
			// 1. 서버가 보내오는 데이터를 읽는 부분을 스레드로 처리 ============================
			Thread receiveThread = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						String msg = "";
						try {
							msg = br.readLine();
							System.out.println("서버가 전달한 메시지>>"+msg);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
			receiveThread.start();
			// ===================================================================
			//taChat.append(msg);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
