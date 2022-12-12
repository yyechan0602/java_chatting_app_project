package client;

import java.awt.*;

import javax.swing.*;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SignUp extends JPanel {

	private JLabel la1, la2, la3, la4;
	JTextField tf;
	JTextField pf;
	JTextField tf2;
	JButton b1, b2, b3, b4,b55;
	JRadioButton rb1, rb2;
	GridLayout grid;
	public static final Color KAKAO_YELLOW = new Color(250,225,0);
	public SignUp() {
		setLayout(grid);

		JPanel pnlMain = new JPanel();
		pnlMain.setBackground(KAKAO_YELLOW);
		
		JPanel pnlFirst = new JPanel(new GridLayout(2,2,5,5));
		JPanel pnlSecond = new JPanel(new GridLayout(1,3,5,5));
		JPanel pnlThird = new JPanel(new GridLayout(1,2,5,5));
		
		pnlFirst.setBackground(KAKAO_YELLOW);
		pnlSecond.setBackground(KAKAO_YELLOW);
		pnlThird.setBackground(KAKAO_YELLOW);
		
		// 아이디 입력 창
		la1 = new JLabel("아이디", JLabel.CENTER);
		//la1.setBounds(300, 100, 80, 30);
		tf = new JTextField(10);
		//tf.setBounds(385, 100, 150, 30);
		pnlFirst.add(la1);
		pnlFirst.add(tf);

		// 비밀번호 입력 창
		la2 = new JLabel("비밀번호", JLabel.CENTER);
		//la2.setBounds(300, 135, 80, 30);
		pf = new JTextField(10);

		//pf.setBounds(385, 135, 150, 30);
		pf.selectAll();
//		pf.setEchoChar('*');
		pnlFirst.add(la2);
		pnlFirst.add(pf);

		// 성별 선택 창
		la3 = new JLabel("성별", JLabel.LEFT);
		rb1 = new JRadioButton("남자");
		rb2 = new JRadioButton("여자");
		ButtonGroup bg = new ButtonGroup();
		bg.add(rb1);
		bg.add(rb2);

		rb1.setSelected(true);
		//la3.setBounds(300, 170, 80, 30);
		//rb1.setBounds(385, 170, 70, 30);
		//rb2.setBounds(445, 170, 70, 30);
		rb1.setOpaque(false);
		rb2.setOpaque(false);
		pnlSecond.add(la3);
		pnlSecond.add(rb1);
		pnlSecond.add(rb2);

		// 이름 입력 창
		la4 = new JLabel("이름", JLabel.CENTER);
		//la4.setBounds(300, 205, 80, 30);
		tf2 = new JTextField(10);
		//tf2.setBounds(385, 205, 150, 30);
		pnlThird.add(la4);
		pnlThird.add(tf2);

		// 회원가입 버튼 생성
		b4 = new JButton("회원가입");
		b55 = new JButton("취소");
		
		JPanel p = new JPanel();
		p.add(b4);
		p.add(b55);
		p.setOpaque(false);
		//p.setBounds(215, 245, 335, 35);
		//add(p);
		//pnlThird.add(b4);
		//pnlThird.add(b55);
		
		pnlMain.setSize(350,200);
		pnlMain.add(pnlFirst);
		pnlMain.add(pnlSecond);
		pnlMain.add(pnlThird);
		pnlMain.add(p);
		
		add(pnlMain);
		
		pnlMain.setVisible(true);
	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new SignUp();
//	}

}
