package m17.poo.trade;

import java.awt.BorderLayout;

import javax.swing.JFrame;

//共通のセッティング

@SuppressWarnings("serial")
public class MyFrame extends JFrame{
	MyFrame(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
	}
}
