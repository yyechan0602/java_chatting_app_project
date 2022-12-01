package client;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.JFrame;

import function.Function;

public class ClientMainForm extends JFrame implements ActionListener, Runnable {
	CardLayout card = new CardLayout();
	Login login = new Login();
	WaitRoom wr = new WaitRoom();
	// 네트워크
	Socket s; // 전화기
	BufferedReader in; // 수신
	OutputStream out; // 송신
	
	public ClientMainForm() {
		setLayout(card);
		add("LOGIN", login);
		add("WR", wr);
		setSize(1024, 950);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		login.b1.addActionListener(this);
		login.b2.addActionListener(this);
		login.b3.addActionListener(this);
		
		wr.tf.addActionListener(this);
	}

	public static void main(String[] args) {
		new ClientMainForm();
	}

	public void connection(String id, String name, String sex) {
		// 서버연결 => 로그인 요청
		try {
			s = new Socket("localhost", 1120); // localhost=> 본인꺼 , 남들꺼는 남들 IP주소 써야함
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = s.getOutputStream();
			// 연결이 되면 로그인 요청
			out.write((Function.LOGIN + "|" + id + "|" + name + "|" + sex + "\n").getBytes());
		} catch (Exception ex) {
		}
		// 연결이 되면 지시를 받는다
		new Thread(this).start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login.b1) {
			String id = login.tf.getText();
			String name = login.pf.getText();
			String sex = "";
			if (login.rb1.isSelected())
				sex = "남자";
			else
				sex = "여자";
			connection(id, name, sex);
		} 
		else if (e.getSource() == login.b2) {
		}
		else if (e.getSource() == login.b3) {
			setVisible(false);
		}
		
		else if (e.getSource() == wr.tf) {
			// 입력된 데이터 읽기
			String msg = wr.tf.getText();
			if (msg.length() < 1)
				return;
			try {
				out.write((Function.WAITCHAT + "|" + msg + "\n").getBytes());
			} catch (Exception ex) { }
			wr.tf.setText("");
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (true) {
				String msg = in.readLine();
				StringTokenizer st = new StringTokenizer(msg, "|");
				int protocol = Integer.parseInt(st.nextToken());
				switch (protocol) {
					case Function.ANOTHER_LOGIN: {
						String[] data = { st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken() };
						wr.model2.addRow(data);
						break;
					}
					case Function.PERMIT_LOGIN: {
						setTitle(st.nextToken());
						card.show(getContentPane(), "WR");
						break;
					}
					case Function.WAITCHAT: {
						wr.bar.setValue(wr.bar.getMaximum());
						wr.ta.append(st.nextToken() + "\n");
					}
				}

			}
		} catch (Exception e) {}
	}
}