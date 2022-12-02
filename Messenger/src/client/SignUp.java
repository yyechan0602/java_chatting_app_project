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

public class SignUp extends JPanel {

	private JLabel la1, la2, la3, la4;
	JTextField tf;
	JTextField pf;
	JTextField tf2;
	JButton b1, b2, b3, b4;
	JRadioButton rb1, rb2;

	public SignUp() {
		setLayout(null);

		// 아이디 입력 창
		la1 = new JLabel("아이디", JLabel.RIGHT);
		la1.setBounds(300, 100, 80, 30);
		tf = new JTextField();
		tf.setBounds(385, 100, 150, 30);
		add(la1);
		add(tf);

		// 비밀번호 입력 창
		la2 = new JLabel("비밀번호", JLabel.RIGHT);
		la2.setBounds(300, 135, 80, 30);
		pf = new JTextField();

		pf.setBounds(385, 135, 150, 30);
		pf.selectAll();
//		pf.setEchoChar('*');
		add(la2);
		add(pf);

		// 성별 선택 창
		la3 = new JLabel("성별", JLabel.RIGHT);
		rb1 = new JRadioButton("남자");
		rb2 = new JRadioButton("여자");
		ButtonGroup bg = new ButtonGroup();
		bg.add(rb1);
		bg.add(rb2);

		rb1.setSelected(true);
		la3.setBounds(300, 170, 80, 30);
		rb1.setBounds(385, 170, 70, 30);
		rb2.setBounds(445, 170, 70, 30);
		rb1.setOpaque(false);
		rb2.setOpaque(false);
		add(la3);
		add(rb1);
		add(rb2);

		// 이름 입력 창
		la4 = new JLabel("이름", JLabel.RIGHT);
		la4.setBounds(300, 205, 80, 30);
		tf2 = new JTextField();
		tf2.setBounds(385, 205, 150, 30);
		add(la4);
		add(tf2);

		// 회원가입 버튼 생성
		b4 = new JButton("회원가입");

		JPanel p = new JPanel();
		p.add(b4);
		p.setOpaque(false);
		p.setBounds(215, 245, 335, 35);
		add(p);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SignUp();
	}

}
