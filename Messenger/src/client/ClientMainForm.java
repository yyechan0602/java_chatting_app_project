package client;

import java.awt.*;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
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
	SignUp su = new SignUp();
	CreateRoom cr = new CreateRoom();
	GridLayout grid = new GridLayout();
	JFrame j1 = new JFrame();
	JFrame j2 = new JFrame();
	
	// 네트워크
	Socket s; // 전화기
	BufferedReader in; // 수신
	OutputStream out; // 송신

	public ClientMainForm() {
		System.out.println("clients start...");
		setLayout(card);
		add("LOGIN", login);
		add("WR", wr);
		add("SU",su);
		
		j1.setLayout(grid);
		j1.add("CR",cr);
		
		setSize(1024, 950);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		j1.setSize(300,250);
		
		
		login.b1.addActionListener(this);
		login.b2.addActionListener(this);
		login.b3.addActionListener(this);
		su.b4.addActionListener(this);

		wr.tf.addActionListener(this);
		wr.b7.addActionListener(this);
		wr.b8.addActionListener(this);
		wr.b9.addActionListener(this);
		wr.b10.addActionListener(this);
		
		cr.b5.addActionListener(this);
		cr.b6.addActionListener(this);
		
	}

	public static void main(String[] args) {
		new ClientMainForm();
	}

	// 로그인 연결
	public void login(String id, String pw, String sex) {
		try {
			s = new Socket("localhost", 1120); // localhost=> 본인꺼 , 남들꺼는 남들 IP주소 써야함
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = s.getOutputStream();
			out.write((Function.LOGIN + "|" + id + "|" + pw + "|" + sex +"\n").getBytes());
			// 연결이 되면 로그인 요청
		} catch (Exception ex) {
		}
		new Thread(this).start();
	}

	// 회원가입 연결
	public void sign_up(String id, String sex, String pw, String name) {
		// 서버연결 => 회원가입 요청
		try {
			s = new Socket("localhost", 1120); // localhost=> 본인꺼 , 남들꺼는 남들 IP주소 써야함
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = s.getOutputStream();
			
			out.write((Function.SIGNUP + "|" + id + "|" + pw + "|" + name + "|" + sex + "\n").getBytes());
		} catch (Exception ex) {
		}
		// 연결이 되면 지시를 받는다

	}
	
	// 방 생성 
	public void makeroom(String RoomName, String open, String num)
	{
		try {
			out.write((Function.MAKEROOM + "|" + RoomName + "|" + open + "|" + num + "\n").getBytes());
		} catch (Exception ex) {
		}
	}
	
	// 로그아웃 
	public void logout(String id, String pw)
	{
		try {
			out.write((Function.LOGOUT + "|" + id + "|" + pw + "\n").getBytes());
		} catch (Exception ex) {
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// 로그인 버튼
		if (e.getSource() == login.b1) {
			String id = login.tf.getText();
			String pw = login.pf.getText();
			String sex = "";
			if (login.rb1.isSelected())
				sex = "남자";
			else
				sex = "여자";
			login(id, pw, sex);
		}

		// 회원가입 버튼
		else if (e.getSource() == login.b2) {
			card.show(getContentPane(), "SU"); // 회원가입 창으로 넘어감 
			su.tf.setText("");
			su.pf.setText("");
			su.tf2.setText("");
		}
		
		// 회원가입 취소 버튼  
		else if (e.getSource() == login.b3) {
			setVisible(false);
		}

		// 회원가입 창에서 회원가입 완료 버튼
		else if (e.getSource() == su.b4) {
			String id = su.tf.getText();
			String pw = su.pf.getText();
			String sex = "";
			if (su.rb1.isSelected())
				sex = "남자";
			else
				sex = "여자";
			String name = su.tf2.getText();
			sign_up(id, sex, pw, name);
			card.show(getContentPane(), "LOGIN"); // 다시 로그인 화면으로 넘어감
		}
		
		// 방 만들기 
		else if (e.getSource() == wr.b7) {
			card.show(getContentPane(), "CR");
			j1.setVisible(true);
			
		}
		
		// 방 만들기 창에서 방 생성 버튼 
		else if (e.getSource() == cr.b5) {
			String RoomName = cr.tf.getText();
			String open = "";
			String num = "";
			
			if (cr.rb1.isSelected())
				open = "공개";
			else
				open = "비공개";
			
			for (int i=0; i<cr.radio.length; i++) {
				if (cr.radio[i].isSelected())
					num = Integer.toString(i+1);
			}
			makeroom(RoomName, open, num);
			
			j1.setVisible(false);
			cr.tf.setText("");
			
		}
		
		else if (e.getSource() == cr.b6) {
			j1.setVisible(false);
		}
		
		// 입장하기 
		else if (e.getSource() == wr.b8) {
			
		}
		
		// 옵션
		else if (e.getSource() == wr.b9) {
			
		}
		
		// 로그아웃 
		else if (e.getSource() == wr.b10) {
			String id = login.tf.getText();
			String pw = login.pf.getText();
			logout(id,pw);
			card.show(getContentPane(), "LOGIN");
		}
		
		else if (e.getSource() == wr.tf) {
			// 입력된 데이터 읽기
			String msg = wr.tf.getText();
			if (msg.length() < 1)
				return;
			try {
				out.write((Function.CHATTING + "|" + msg + "\n").getBytes());
			} catch (Exception ex) {
			}
			wr.tf.setText("");
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (true) {
				String msg = in.readLine();
				System.out.println(msg);
				StringTokenizer st = new StringTokenizer(msg, "|");
				int protocol = Integer.parseInt(st.nextToken());
				switch (protocol) {
					case Function.ANOTHER_LOGIN: {
						String[] data = { st.nextToken(), st.nextToken(), st.nextToken()};
						//System.out.println(data);
						wr.model2.addRow(data);
						break;
					}
					case Function.PERMIT_LOGIN: {
						setTitle(st.nextToken());
						card.show(getContentPane(), "WR");
						break;
					}
					case Function.CHATTING: {
						wr.bar.setValue(wr.bar.getMaximum());
						wr.ta.append(st.nextToken() + "\n");
					}
					case Function.MAKEROOM:{
						String[] data2 = {st.nextToken(),st.nextToken(),st.nextToken()};
						wr.model1.addRow(data2);
						break;
					}
				}

			}
		} catch (Exception e) {
		}
	}
}