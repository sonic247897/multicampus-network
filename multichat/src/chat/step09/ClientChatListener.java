package chat.step09;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientChatListener implements ActionListener{
	ClientChatView view;

	public ClientChatListener(ClientChatView view) {
		super();
		this.view = view;
	}

	// ActionListener = onClick과 같음
	// 버튼을 누를 때, 텍스트 상자에서 엔터키를 입력할 때 actionPerformed가 호출된다.
	@Override
	public void actionPerformed(ActionEvent e) {
		// 1. 채팅대화를 전송하는 경우 프로토콜, 대화내용, nickname 저장 =========================
		if(e.getSource() == view.txtinput | e.getSource() == view.btnsend) {
			// 데이터만 보내므로 누가 보냈는지 알 수가 없다.
			// view.nickname: 클라이언트의 닉네임
			// 서버 -> 클라이언트: 서버가 전송한 메시지 분석
			// 클라이언트 -> 서버: 클라이언트가 전송한 메시지 분석
			// trim(): 엔터, 스페이스바 자름
			view.sendMsg("chatting/"+view.txtinput.getText().trim()+"/"+view.nickname);
		// =========================================================================
			view.txtinput.setText("");
		}
		
	}
	
	
}
