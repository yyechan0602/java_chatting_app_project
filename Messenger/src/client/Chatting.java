package src.client;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chatting extends JFrame {
	JLabel la1;
	JTextArea ta;
	JTextField tf;
	JScrollBar bar;
	JPanel p1, p2, p3;
	Font font;
	public Chatting() {
		setLayout(new BorderLayout());
		
		font = new Font("",Font.BOLD, 20);
		la1 = new JLabel("채팅방");
		la1.setFont(font);
		ta = new JTextArea(20 ,30);
		ta.setEditable(false);
		JScrollPane js3 = new JScrollPane(ta);
		bar = js3.getVerticalScrollBar();
		tf = new JTextField(30);
		

		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p1.add(la1);
		p2.add(ta);
		p3.add(tf);
		p1.setBackground(ClientMainForm.KAKAO_YELLOW);
		p2.setBackground(ClientMainForm.KAKAO_WHITE_YELLOW);
		p3.setBackground(ClientMainForm.KAKAO_YELLOW);
		
		add(p1, BorderLayout.PAGE_START);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.PAGE_END);
		
		this.setLocation(440,10);
		pack();
	}
	
	public static void main(String[] args) {
		Chatting chat = new Chatting();
		chat.setVisible(true);
	}
}
