package client;

import java.awt.*;
import javax.swing.*;

public class PwEnter extends JPanel{

	JLabel la1;
	JTextField tf1;
	JButton b11,b22;
	
	GridLayout grid = new GridLayout(3,1,5,5);
	JFrame j1 = new JFrame();
	
	public PwEnter() {
		j1.setLayout(grid);
		j1.setSize(200,140);
		setLayout(null);
		
		la1 = new JLabel("비밀번호를 입력하세요",JLabel.CENTER);
		j1.add(la1);
		
		tf1 = new JTextField(10);
		j1.add(tf1);
		
		JPanel p = new JPanel();
		b11 = new JButton("확인");
		b22 = new JButton("취소");
		p.add(b11);
		p.add(b22);
		
		j1.add(p);
		
		
	}
	public void view() {
		j1.setVisible(true);
	}
	
	public void no_view() {
		j1.setVisible(false);
	}
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new PwEnter();
//	}

}
