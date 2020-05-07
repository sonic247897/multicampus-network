package chat.step02;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;


// 서버 프로그램
public class ChatServerView extends JFrame {
	 JPanel contentPane;
	 JTextArea taclientlist;
	 JButton btnchangeport;
	 JButton btnstartServer;
	 JButton btnstop;
	 // 메인 하나에서 전부 처리하던 것을 따로따로 나눠놓은 구조!!
	 // ============= 1. 소켓프로그래밍을 하기 위한 객체를 준비 =============
	 ServerSocket server;
	 Socket socket;
	 // ===========================================================
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatServerView frame = new ChatServerView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChatServerView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 673, 513);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		taclientlist = new JTextArea();
		taclientlist.setBounds(12, 50, 472, 415);
		contentPane.add(taclientlist);
		
		JLabel label = new JLabel("\uC811\uC18D\uC790:");
		label.setFont(new Font("HY견고딕", Font.BOLD, 14));
		label.setBounds(12, 10, 120, 35);
		contentPane.add(label);
		
		btnchangeport = new JButton("\uD3EC\uD2B8\uBCC0\uACBD");
		btnchangeport.setFont(new Font("HY견고딕", Font.BOLD, 14));
		btnchangeport.setBounds(516, 50, 129, 35);
		contentPane.add(btnchangeport);
		
		btnstartServer = new JButton("\uC11C\uBC84\uC2DC\uC791");
		btnstartServer.setFont(new Font("HY견고딕", Font.BOLD, 14));
		btnstartServer.setBounds(516, 95, 129, 35);
		contentPane.add(btnstartServer);
		
		btnstop = new JButton("\uC11C\uBC84\uC911\uC9C0");
		btnstop.setFont(new Font("HY견고딕", Font.BOLD, 14));
		btnstop.setBounds(516, 140, 129, 35);
		contentPane.add(btnstop);
		// 리스너 붙임
		btnstartServer.addActionListener(new ChatServerListener(this));
		btnstop.addActionListener(new ChatServerListener(this));
	}
	
	// 2, 3: 연결 부분만 따로 빼놓음(스레드로 따로 만들려고)
	//	=> accpet() 실행되면  GUI(event-driven) 방식이기 때문에 
	// 	    안드로이드처럼 서버 시작하면 화면이 멈춘것처럼 보인다.
	//	=> 계속 리스닝하는 메인스레드와, 사용자 대기를 기다리는 accept() 부분을 분리
	//	  [메인스레드와 분리하여 새로운 thread 추가!]
	
	// ======= 2. 클라이언트의 접속을 기다리면서 서비스를 시작할 수 있는 메소드를 정의 ===========
	public void serverStart(int port) {
		try {
			server = new ServerSocket(port);
			taclientlist.append("사용자 접속 대기중\n");
			if(server != null) {
				// 클라이언트의 접속을 기다리는 처리
				connection();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	// ======== 3. 사용자 접속을 위한 메소드 정의 ========================================
	public void connection() {
		try {
			socket = server.accept();
			taclientlist.append("사용자 접속!!!\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}






