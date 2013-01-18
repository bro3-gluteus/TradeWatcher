package m17.poo.trade;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class TradeWatcher extends JFrame{
	
	public static JTextArea outputArea;
	
	public TradeWatcher (){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		//情報表示部
		outputArea = new JTextArea();
		outputArea.setForeground(Color.BLACK);
		outputArea.setBackground(Color.WHITE);
		outputArea.setEditable(false);
		outputArea.setBorder(new BevelBorder(BevelBorder.LOWERED));
		getContentPane().add(new JScrollPane(outputArea),BorderLayout.CENTER);
		
		//メニューバー作成
		JMenuBar menubar = new JMenuBar();
		
		//メニュー作成
		JMenu crawlerMenu = new JMenu("Crawler");
		JMenu viewerMenu = new JMenu("Viewer");
		JMenu settingMenu = new JMenu("Setting");
		
		menubar.add(crawlerMenu);
		menubar.add(viewerMenu);
		menubar.add(settingMenu);
		
		//メニューアイテム作成
		JMenuItem crawlerItem1 = new JMenuItem("今すぐ実行");
							crawlerItem1.addActionListener(new Action("Crawler"));
		crawlerMenu.add(crawlerItem1);
		
		JMenuItem viewerItem1 = new JMenuItem("始値");
							viewerItem1.addActionListener(new Action("HajimeneViewer"));
		viewerMenu.add(viewerItem1);
		
		JMenuItem settingItem1 = new JMenuItem("アカウント");
							settingItem1.addActionListener(new Action("AccountManager"));
		settingMenu.add(settingItem1);
		
		setBounds(100, 100, 400, 400);
		setJMenuBar(menubar);
		
		new ScheduledCrawl();
		
	}
	
	public static void main(String[] args) {
		TradeWatcher tw = new TradeWatcher();
		tw.setVisible(true);

	}

}

class Action extends Thread implements ActionListener, Runnable{
	
	private String cmd;
	
	Action(String cmd){
		this.cmd = cmd;
	}
	
	public void actionPerformed(ActionEvent e) {
		Action thread = new Action(cmd);
		thread.start();	
	}
	public void run(){
		if(cmd.equals("Crawler")) new Crawler();
		if(cmd.equals("HajimeneViewer")) new Viewer("始値Viewer");
		if(cmd.equals("AccountManager")) new AccountManager();
  }
}
