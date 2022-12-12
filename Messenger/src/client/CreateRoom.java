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

public class CreateRoom extends JPanel {

	private JLabel la1, la2, la3, la4;
	JTextField tf;
	JButton b5, b6;
	JRadioButton rb1, rb2;
	JRadioButton radio[] = new JRadioButton[5];
	GridLayout grid = new GridLayout();
	
	public CreateRoom ()
	{
		setLayout(null);
		
		String radio_name[] = {"1","2","3","4","5"};
		
		la1 = new JLabel("방 생성", JLabel.RIGHT);
		la1.setSize(350,100);
		la1.setBounds(80,10,80,30);
		add(la1);
		
		// 방 이름 입력 창 
		la2 = new JLabel("방 이름 ",JLabel.RIGHT);
		la2.setBounds(15,45,80,30);
		tf = new JTextField();
		tf.setBounds(100,45,150,30);
		add(la2); add(tf);
		
		// 공개/비공개 
		la3 = new JLabel("공개/비공개",JLabel.RIGHT);
		rb1 = new JRadioButton("공개");
		rb2 = new JRadioButton("비공개");
		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(rb1);
		bg1.add(rb2);
		
		rb1.setSelected(true);
	    la3.setBounds(15, 80,80,30);
	    rb1.setBounds(100,80,70,30);
	    rb2.setBounds(160,80,70,30);
	    rb1.setOpaque(false);
	    rb2.setOpaque(false);
	    add(la3); add(rb1); add(rb2);
	    
	    // 인원 선택
	    ButtonGroup bg2 = new ButtonGroup();
	    int j = 100;
	    for (int i=0; i<radio.length;i++) {
	    	radio[i] = new JRadioButton(radio_name[i]);
	    	bg2.add(radio[i]);
	    	radio[i].setBounds(j + (30*i),115,70,30);
	    	radio[i].setOpaque(false);
	    	add(radio[i]);
	    }
	    
	    radio[1].setSelected(false);
	    radio[0].setSelected(true);
	    la4 = new JLabel("인원선택",JLabel.RIGHT);
		la4.setBounds(15,115,80,30);
		add(la4); 
		
		// 방 생성, 취소 버튼 
		b5 = new JButton("방 생성");
		b6 = new JButton("취소");
		JPanel p = new JPanel();
		p.add(b5); p.add(b6);
		p.setOpaque(false);
		p.setBounds(15,155,265,35);
		b5.setBounds(50,155,265,35);
		add(p);
		
	}
}
