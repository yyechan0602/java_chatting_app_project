package client;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class Login extends JPanel{
	private Image back;
	private JLabel la1, la2, la3,lblImag;
	JTextField tf; // default로 잡힌 이유는 다른 클래스에서 사용해야 하기 때문에
	JTextField pf; // default로 잡힌 이유는 다른 클래스에서 사용해야 하기 때문에
	JButton b1, b2, b3; // default로 잡힌 이유는 다른 클래스에서 사용해야 하기 때문에
	JRadioButton rb1, rb2;
	private CardLayout card;
	private GridLayout grid;
	
	public Login() {
		// 이미지 정보 읽기
		setLayout(grid); // 직접 배치
		//setBackground(KAKAO_YELLOW);
		
		JPanel pnlFirst = new JPanel();
		pnlFirst.setBackground(ClientMainForm.KAKAO_YELLOW);
		
		back = Toolkit.getDefaultToolkit().getImage("src/client/kakao_image.png");
		Icon icon = new ImageIcon(back);
		lblImag = new JLabel(icon);
		lblImag.setBounds(95,80,150,150);
		pnlFirst.add(lblImag);
		
		JPanel pnlLogin = new JPanel(new GridLayout(2,2,5,5));
		pnlLogin.setBackground(ClientMainForm.KAKAO_YELLOW);
		
		// 로그인 부분 아이디와 아이디 칠 textField
		la1 = new JLabel("아이디", JLabel.CENTER);
		la1.setSize(2, 2);
		//la1.setBounds(745, 530, 80, 30);
		tf = new JTextField(10);
		//tf.setBounds(830, 530, 150, 30);
		//add(la1);
		//add(tf);
		pnlLogin.add(la1);
		pnlLogin.add(tf);
		
		// 로그인 부분 비밀번호와 비밀번호 칠 textField
		la2 = new JLabel("비밀번호", JLabel.CENTER);
		//la2.setBounds(745, 565, 80, 30);
		pf = new JTextField(10);
		//pf.setBounds(830, 565, 150, 30);
		//add(la2);
		//add(pf);
		pnlLogin.add(la2);
		pnlLogin.add(pf);
		
		rb1 = new JRadioButton("남자");
		rb2 = new JRadioButton("여자");
		ButtonGroup bg = new ButtonGroup();
//		bg.add(rb1);
//		bg.add(rb2);

		rb1.setSelected(true);
		la3 = new JLabel("성별", JLabel.RIGHT);
		//la3.setBounds(745, 600, 80, 30);
		//rb1.setBounds(830, 600, 70, 30);
		//rb2.setBounds(910, 600, 70, 30);
		rb1.setOpaque(false);
		rb2.setOpaque(false);
//		add(la3);
//		add(rb1);
//		add(rb2);

		b1 = new JButton("로그인");
		b2 = new JButton("회원가입");
		b3 = new JButton("취   소");

		b1.setForeground(Color.WHITE);
		b1.setBorderPainted(false);
		b1.setBackground(ClientMainForm.KAKAO_BROWN);
		
		b2.setForeground(Color.WHITE);
		b2.setBorderPainted(false);
		b2.setBackground(ClientMainForm.KAKAO_BROWN);
		
		b3.setForeground(Color.WHITE);
		b3.setBorderPainted(false);
		b3.setBackground(ClientMainForm.KAKAO_BROWN);
		
		pnlFirst.setSize(350,550);
		JPanel p = new JPanel(); // 패널을 배치하는 이유, 가운데 맞추기 어려워서 패널로 잡아준다
		p.add(b1);
		p.add(b2);
		p.add(b3);
		//pnlLogin.add(p);
//		pnlLogin.add(b1);
//		pnlLogin.add(b2);
//		pnlLogin.add(b3);
		//pnlLogin.setBounds(10,300,335,35);
		pnlFirst.add(pnlLogin);
		pnlFirst.add(p);
		
		add(pnlFirst);
		
//		//p.setBackground(KAKAO_BROWN);
//		p.setOpaque(false); // setOpaque -투명모드
//		//p.setBounds(680, 650, 335, 35);
//		add(p);
		pnlFirst.setVisible(true);
		
	}
	
//	@Override
//	protected void paintComponent(Graphics g) {
//		// TODO Auto-generated method stub
//		g.drawImage(back, 0, 0, getWidth(), getHeight(), this);
//	}

	public static void main(String[] args) {
		
	}
}