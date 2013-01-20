package m17.poo.trade;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
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
		
		super("TradeWatcher");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new myListener());
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
		
		ExecutorService excec = Executors.newSingleThreadExecutor();
		excec.execute(new RunnableClass("ScheduledCrawl"));
		excec.shutdown();
		
	}
	
	public static void main(String[] args) {
		TradeWatcher tw = new TradeWatcher();
		tw.setVisible(true);
	}
	
	public class myListener extends WindowAdapter{
    public void windowClosing(WindowEvent e){
    	JFrame frame = new JFrame("データ保存中");
    	Screen screen = new Screen();
    	int[] xy = screen.getFrameCenter();
    	frame.setBounds(xy[0],xy[1],200,0);
    	frame.setVisible(true);
    	Crawler.datapool.saveData();
    }
  }

}

class Action extends Thread implements ActionListener{
	
	private String cmd;
	
	Action(String cmd){
		this.cmd = cmd;
	}
	
	public void actionPerformed(ActionEvent e) {
		ExecutorService exec = Executors.newSingleThreadExecutor();
		exec.execute(new RunnableClass(cmd));
		exec.shutdown();
	}
}

class RunnableClass implements Runnable{
	
	private String cmd;
	
	public RunnableClass(String cmd){
		this.cmd = cmd;
	}
	
	public void run() {
		
		if(cmd.equals("ScheduledCrawl")) new ScheduledCrawl();
		
		if(cmd.equals("Crawler")) new Crawler();
		if(cmd.equals("HajimeneViewer")) new Viewer("始値Viewer");
		if(cmd.equals("AccountManager")) new AccountManager();
		
	}
}
