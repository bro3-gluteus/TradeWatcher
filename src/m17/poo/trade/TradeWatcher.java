package m17.poo.trade;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class TradeWatcher extends JFrame{

	public TradeWatcher (){
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
		JMenuItem crawlerItem2 = new JMenuItem("予約実行");
							crawlerItem2.addActionListener(new Action("ScheduledCrawl"));
		crawlerMenu.add(crawlerItem1);
		crawlerMenu.add(crawlerItem2);
		
		JMenuItem viewerItem1 = new JMenuItem("始値");
							viewerItem1.addActionListener(new Action("HajimeneViewer"));
		viewerMenu.add(viewerItem1);
		
		JMenuItem settingItem1 = new JMenuItem("アカウント");
							settingItem1.addActionListener(new Action("AccountManager"));
		settingMenu.add(settingItem1);
		
		setBounds(100, 100, 300, 250);
		setJMenuBar(menubar);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
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
		if(cmd.equals("Crawler")) Crawler.main(null);
		if(cmd.equals("ScheduledCrawl")) ScheduledCrawl.main(null);
		if(cmd.equals("HajimeneViewer")) HajimeneViewer.main(null);
		if(cmd.equals("AccountManager")) AccountManager.main(null);
  }
}
