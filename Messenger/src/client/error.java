
package client;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class error extends JFrame {
	private JLabel la1;
	GridLayout grid = new GridLayout();
	JFrame j1 = new JFrame();
	
	public error() {
		j1.setLayout(grid);
		j1.setSize(300,250);
		setLocation(500, 500);
		setLayout(null);
		
		la1 = new JLabel("",JLabel.CENTER);
		j1.add(la1);
		
	}
	
	public void view(String msg) {
		la1.setText(msg);
		j1.setVisible(true);
	}
//	public static void main(String[] args) {
//		err r = new err();
//	}
}
