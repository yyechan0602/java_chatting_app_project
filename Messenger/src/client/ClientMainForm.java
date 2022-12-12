package client;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.JFrame;

import function.Function;

public class ClientMainForm extends JFrame implements ActionListener, Runnable, MouseListener {
	CardLayout card = new CardLayout();
	Login login = new Login();
	WaitRoom wr = new WaitRoom();
	SignUp su = new SignUp();
	CreateRoom cr = new CreateRoom();
	GridLayout grid = new GridLayout();
	PwEnter pe = new PwEnter();
	PwEnter pe2 = new PwEnter();
	String id;
	Chatting chatting;
	public String room_pw = "";
	int row;
	String RoomName;

	public static final Color KAKAO_YELLOW = new Color(250,225,0);
	public static final Color KAKAO_BROWN = new Color(82,55,56);
	public static final Color KAKAO_WHITE_YELLOW = new Color(250,225,204);
	
	JFrame j1 = new JFrame();	// 방 생성 
	JFrame j2 = new JFrame();	// 로그인 화면 
	JFrame j3 = new JFrame();	// 회원가입 화면
	JFrame j4 = new JFrame(); 	// 대기방 화면 
	//JFrame j5 = new JFrame();	// 채팅방 비밀번호 입력 화면 
	error err;
	
	// 네트워크
	Socket s; // 전화기
	BufferedReader in; // 수신
	OutputStream out; // 송신

	public ClientMainForm() {
		chatting = new Chatting();
		err = new error();
		setLayout(card);
		//add("LOGIN", login);
		
		//j5.setLayout(grid);
		//j5.add("PE",pe);
		
		j4.setLayout(null);
		j4.add("WR", wr);
		//add("SU",su);
			
		j2.setLayout(grid);
		j2.add("LOGIN",login);
		
		
		j1.setLayout(grid);
		j1.add("CR",cr);
		
		j3.setLayout(grid);
		j3.add("SU",su);
		
		j1.setSize(300,250);
		j2.setSize(350,550);
		j3.setSize(350,200);
		j4.setSize(440,800);
		//j5.setSize(200,140);
		//setSize(1024, 950);
		j2.setVisible(true);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		wr.table1.addMouseListener(this);
		
		login.b1.addActionListener(this);
		login.b2.addActionListener(this);
		login.b3.addActionListener(this);
		
		su.b4.addActionListener(this);
		su.b55.addActionListener(this);
		
		chatting.tf.addActionListener(this);
		wr.b7.addActionListener(this);
		wr.b8.addActionListener(this);
		wr.b9.addActionListener(this);
		wr.b10.addActionListener(this);
		
		cr.b5.addActionListener(this);
		cr.b6.addActionListener(this);
		
		pe.b11.addActionListener(this);
		pe.b22.addActionListener(this);
		
		pe2.b11.addActionListener(this);
		pe2.b22.addActionListener(this);
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
			System.out.println("clients login...");
			// 연결이 되면 로그인 요청
		} catch (Exception ex) {
			System.out.println("not connected...");
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
			// 연결이 되면 로그인 요청
		} catch (Exception ex) {
			System.out.println("not connected...");
		}
		new Thread(this).start();
	}
	
	// 방 생성 
	public void makeroom(String RoomName, String password, String open, String num)
	{
		try {
			out.write((Function.MAKEROOM + "|" + RoomName + "|" + password + "|" + open + "|" + num + "\n").getBytes());
			System.out.println("방 생성 ::" + password);
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
	
	// 채팅방 입장 
	public void enterroom(String room_id, String open, String id, String password)
	{
		try {
			if (open.equals("공개")) {
				password = "NA";
				out.write((Function.ENTERROOM + "|" + room_id + "|" + id + "|" + password + "\n").getBytes());
				System.out.println("공개 ||" +room_id + open + id + password);
			}
			else if (open.equals("비공개")){
				//out.write((Function.ENTERROOM + "|" + room_id + "|" + id + "|" + password + "\n").getBytes());
				System.out.println("비공개 ||" +room_id + open + id + password);
			}
		} catch (Exception ex) {
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		// 로그인 버튼
		if (e.getSource() == login.b1) {
			id = login.tf.getText();
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
			
			j3.setVisible(true);
			//card.show(getContentPane(), "SU"); // 회원가입 창으로 넘어감 
			su.tf.setText("");
			su.pf.setText("");
			su.tf2.setText("");
		}
		
		// 회원가입 취소 버튼  
		else if (e.getSource() == login.b3) {
			j2.setVisible(false);
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
			j3.setVisible(false);
			//card.show(getContentPane(), "LOGIN"); // 다시 로그인 화면으로 넘어감
		}
		
		else if (e.getSource() == su.b55) {
			j3.setVisible(false);
		}
		
		// 방 만들기 
		else if (e.getSource() == wr.b7) {
			card.show(getContentPane(), "CR");
			j1.setVisible(true);
			
		}
		
		// 방 만들기 창에서 방 생성 버튼 
		else if (e.getSource() == cr.b5) {
			RoomName = cr.tf.getText();
			String open = "";
			String num = "";
			String password = "";

			for (int i=0; i<cr.radio.length; i++) {
				if (cr.radio[i].isSelected())
					num = Integer.toString(i+1);
			}
			
			if (cr.rb1.isSelected()) {
				open = "공개";
				makeroom(RoomName, password, open, num);
			}
			else if (cr.rb2.isSelected()){
				open = "비공개";
				pe.view();
				
			
			}
			
			
			//System.out.println("방 생성 버튼 :: " + RoomName + "|" + room_pw +"|" + open);
			
			
			j1.setVisible(false);
			cr.tf.setText("");
			
		}
		
		// 방 생성 비밀번호 확인 버튼 
		else if (e.getSource() == pe.b11) {
			//String RoomName = cr.tf.getText();
			String open = "비공개";
			String num = "";
			String password = pe.tf1.getText();
			
			for (int i=0; i<cr.radio.length; i++) {
				if (cr.radio[i].isSelected())
					num = Integer.toString(i+1);
			}
			System.out.println(password);
			makeroom(RoomName, password, open, num);
			
			//System.out.println("방 시작");
			pe.no_view();
			//System.out.println("방 종료");
		}
		
		else if (e.getSource() == pe.b22) {
			pe.no_view();
		}
		
		else if (e.getSource() == cr.b6) {
			j1.setVisible(false);
		}
//		
		// 입장하기 
		else if (e.getSource() == wr.b8) {
			//int row = wr.row;
			
			
			String room_id = wr.table1.getValueAt(row, 0).toString();
			String open = wr.table1.getValueAt(row, 1).toString();
			
			String id = login.tf.getText();
			String password ="";//= pe.tf1.getText();
			
			if (open.equals("비공개")) {
				pe2.tf1.setText("");
				pe2.view();
				
			}
			else
				enterroom(room_id, open, id, password);
			//System.out.println("입장하기버튼  "+room_id + open + id + password);
			
			
			//System.out.println(room_id+open);
			
		}
		
		// 방 입장 비밀번호 확인 버튼 
		else if (e.getSource() == pe2.b11) {
			String room_id = wr.table1.getValueAt(row, 0).toString();
			String open = wr.table1.getValueAt(row, 1).toString();
			
			String id = login.tf.getText();
			String pw = pe2.tf1.getText();
			
			enterroom(room_id, open, id, pw);
			pe2.no_view();
			
		}
		else if (e.getSource() == pe2.b22) {
			pe2.no_view();
		}
		// 옵션
		else if (e.getSource() == wr.b9) {
			
		}
		
		// 로그아웃 
		else if (e.getSource() == wr.b10) {
			String id = login.tf.getText();
			String pw = login.pf.getText();
			logout(id,pw);
			wr.erase_Members();
			
			j2.setVisible(true);
			j4.setVisible(false);
			//card.show(getContentPane(), "LOGIN");
		}
		// 채팅하기
		else if (e.getSource() == chatting.tf) {
			// 입력된 데이터 읽기
			String msg = chatting.tf.getText();
			if (msg.length() < 1)
				return;
			try {
				out.write((Function.CHATTING + "|" + id  + "|" +  msg + "\n").getBytes());
			} catch (Exception ex) {
			}
			chatting.tf.setText("");
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		row = wr.table1.getSelectedRow();
		//System.out.println("행 " + row);
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		
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
				if (protocol != Function.MEMBERS) {
					System.out.println("=============");
				}
				switch (protocol) {
					// 다른사람이 로그인
					case Function.MEMBERS: {	
						String[] data1 = { st.nextToken(), st.nextToken(), st.nextToken()};
						wr.model2.addRow(data1);
						break;
					}
					case Function.RESET_MEMBERS:{
						wr.erase_Members();
						break;
					}
					
					// 채팅방 목록 
					case Function.ROOMS:{
						String[] data4 = {st.nextToken(), st.nextToken(), st.nextToken()};
						wr.model1.addRow(data4);
						break;
					}
					
					case Function.RESET_ROOMS:{
						wr.erase_rooms();
						break;
					}
					
					// 채팅방 입장 
					case Function.PERMIT_ENTER_ROOM:{
						err.view("채팅방 입장 성공");
						break;
					}
					
					case Function.REJECT_ENTER_ROOM:{
						err.view("채팅방 입장 실패");
						break;
					}
					
					// 로그인
					case Function.PERMIT_LOGIN: {
						setTitle(st.nextToken());
						j2.setVisible(false);
						j4.setVisible(true);
						chatting.setVisible(true);
						//card.show(getContentPane(), "WR");
						break;
					}
					
					// 로그인 거절
					case Function.REJECT_LOGIN: {
						err.view("로그인 실패");
//						System.out.println("test");
						break;
					}
					
					case Function.REJECT_SIGNUP: {
						err.view("회원가입 실패");
						break;
					}
					
					case Function.CHATTING: {
						chatting.bar.setValue(chatting.bar.getMaximum());
						chatting.ta.append(" [ " + st.nextToken() + " ] " + st.nextToken() + "\n");
						break;
					}
					case Function.PERMIT_MAKE_ROOM:{
						String[] data2 = {st.nextToken(),st.nextToken(),st.nextToken()};
						wr.model1.addRow(data2);
						err.view("방이 만들어졌습니다.");
						break;
					}
					case Function.REJECT_MAKE_ROOM:{
						err.view("방 생성 실패");
						break;
					}
				}

			}
		} catch (Exception e) {
		}
	}
}