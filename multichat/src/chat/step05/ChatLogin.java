package chat.step05;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

// Android가 자바 기반으로 만들어졌기 때문에 swing은 안드로이드와 유사하다!
public class ChatLogin extends JFrame {

	 JPanel contentPane;
	 JTextField txtId;
	 JComboBox cboServer;
	 JComboBox cboPort;
	 JButton btnConnect;
	 String[] ip = {"70.12.116.71","127.0.0.1"};
	 String[] port = {"12345"};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatLogin frame = new ChatLogin();
					// 화면에 추가
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
	public ChatLogin() {
		// x버튼 눌렀을 때 종료
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 529);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		// 레이아웃 셋팅 - null(레이아웃 셋팅하지 않고 내 맘대로 만듬)
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Server_IP");
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 18));
		// X, Y, width, height (위치와 크기)
		lblNewLabel.setBounds(38, 107, 152, 30);
		// 레이아웃에 붙임
		contentPane.add(lblNewLabel);
		
		// 콤보박스
		cboServer = new JComboBox(ip);
		cboServer.setBounds(37, 141, 238, 30);
		contentPane.add(cboServer);
		
		JLabel lblServerport = new JLabel("Server_port");
		lblServerport.setFont(new Font("Arial Black", Font.BOLD, 18));
		lblServerport.setBounds(38, 196, 152, 30);
		contentPane.add(lblServerport);
		// 콤보박스 - port배열이 메뉴
		cboPort = new JComboBox(port);
		cboPort.setBounds(38, 228, 238, 30);
		contentPane.add(cboPort);
		// 버튼
		btnConnect = new JButton("\uC11C\uBC84\uC811\uC18D");
		btnConnect.setFont(new Font("HY견고딕", Font.BOLD, 18));
		btnConnect.setBounds(103, 371, 172, 51);
		contentPane.add(btnConnect);
		// 라벨
		JLabel lblId = new JLabel("ID");
		lblId.setFont(new Font("Arial Black", Font.BOLD, 18));
		lblId.setBounds(38, 275, 152, 30);
		contentPane.add(lblId);
		// editText
		txtId = new JTextField();
		txtId.setBounds(38, 302, 238, 38);
		contentPane.add(txtId);
		txtId.setColumns(10);
		// 뷰와 리스너 연결
		startevent();
	}

	private void startevent() {
		ChatLoginListener listener = new ChatLoginListener(this);
		// setXXXListener(listener)와 같다
		btnConnect.addActionListener(listener);
		
	}
	
}
