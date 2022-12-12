package client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class WaitRoom extends JPanel{
	JTable table1, table2;
	DefaultTableModel model1, model2;
	JButton b7, b8, b9, b10;
	JScrollBar bar;
	String[][] row1 = null;
	String[][] row2 = null;
	public int row;
	Font font;
	
	public WaitRoom() {
		setBackground(ClientMainForm.KAKAO_YELLOW);
		
		String[] col1 = { "방이름", "공개/비공개", "인원" };
		row1 = new String[0][3];
		
		model1 = new DefaultTableModel(row1, col1);
		table1 = new JTable(model1);
		JScrollPane js1 = new JScrollPane(table1);
		
		String[] col2 = { "ID", "이름", "성별" };
		row2 = new String[0][2];

		model2 = new DefaultTableModel(row2, col2);
		table2 = new JTable(model2);
		JScrollPane js2 = new JScrollPane(table2);
		
		
		//tf = new JTextField();
		
		b7 = new JButton("방 만들기");
		b8 = new JButton("입장하기");
		b9 = new JButton("옵션");
		b10 = new JButton("로그아웃");
		
		font = new Font("", Font.BOLD, 15);
		b7.setForeground(Color.WHITE);
		b7.setBorderPainted(false);
		b7.setBackground(ClientMainForm.KAKAO_BROWN);
		b7.setFont(font);
		b8.setForeground(Color.WHITE);
		b8.setBorderPainted(false);
		b8.setBackground(ClientMainForm.KAKAO_BROWN);
		b8.setFont(font);
		b9.setForeground(Color.WHITE);
		b9.setBorderPainted(false);
		b9.setBackground(ClientMainForm.KAKAO_BROWN);
		b9.setFont(font);
		b10.setForeground(Color.WHITE);
		b10.setBorderPainted(false);
		b10.setBackground(ClientMainForm.KAKAO_BROWN);
		b10.setFont(font);
		// b5 = new JButton("일대일게임");
		// b6 = new JButton("나가기");

		// 배치
		setLayout(null);
		js1.setBounds(10, 10, 400, 340);
		js2.setBounds(10, 360, 400, 340);
		add(js1);
		add(js2);


		JPanel p = new JPanel();
		setBackground(ClientMainForm.KAKAO_YELLOW);
		p.setLayout(new FlowLayout());
		p.add(b7);
		p.add(b8);
		p.add(b9);
		p.add(b10);
		p.setBackground(ClientMainForm.KAKAO_WHITE_YELLOW);
		// p.add(b5);
		// p.add(b6);

		p.setBounds(10, 710, 390, 40);
		add(p);

		setSize(700,950);
		setVisible(true);
	}

	public void erase_Members() {
		for (int i = 0; i < model2.getRowCount(); i++) {
			model2.removeRow(0);
		}
	}
	
	public void erase_rooms() {
		for (int i=0; i<model1.getRowCount(); i++) {
			model1.removeRow(0);
		}
	}

}