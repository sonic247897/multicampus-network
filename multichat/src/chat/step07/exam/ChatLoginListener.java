package chat.step07.exam;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

// ActionListener: 버튼을 클릭하는 이벤트를 받는 리스너 (onClickListener와 같다)
public class ChatLoginListener  implements ActionListener{
	ChatLogin view; // 이벤트를 발생시키는 화면 - ChatLogin
	String nickname;
	public ChatLoginListener(ChatLogin view) {
		super();
		this.view = view;
	}
	
	// swing에서 버튼이 클릭될 때 호출되는 메소드
	@Override
	public void actionPerformed(ActionEvent e) {
		// e.getSource() - e.getId()와 같음
		if(e.getSource()==view.btnConnect){
			// 닉네임으로 로그인
			nickname = view.txtId.getText().trim();
			if(nickname.isEmpty()) {
				JOptionPane.showMessageDialog(view,
						"대화명을 입력하세요", "대화명오류", JOptionPane.WARNING_MESSAGE);
				return;
			}
			String ip = view.cboServer.getSelectedItem()+"";
			int port = Integer.parseInt(view.cboPort.getSelectedItem()+"");
			System.out.println(ip+" "+port);
			ClientChatView client = new ClientChatView(ip, port, nickname); // 클라이언트 창으로 넘어감
			view.dispose(); // 로그인 닫는다.
		}
	}
}