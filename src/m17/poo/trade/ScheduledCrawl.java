package m17.poo.trade;

import java.awt.FlowLayout;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;

public class ScheduledCrawl {

	static JFrame frame = new JFrame();
	static JLabel label = new JLabel();
	
	public static void main(String[] args) {
		
		//Swing
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("トレード情報収集ツール");
		frame.setSize(150, 50);
		frame.setVisible(true);
		ScheduledCrawl.label.setText("待機中...");

		//タスク用意
		TimerTask task1 = new Task();
		TimerTask task2 = new Task();
		
		//現在時刻と今日の9:50と10:02を取得
		DateTime nowTime = new DateTime(DateTimeZone.forID("Asia/Tokyo"));
		DateTime dt1 = nowTime.withTime(9, 50, 0, 0);
		DateTime dt2 = nowTime.withTime(10, 2, 0, 0);
		
		//もし指定時間が過ぎていたら明日に置き換え
		if (!dt1.isAfterNow()) dt1 = dt1.plusDays(1);
		if (!dt2.isAfterNow()) dt2 = dt2.plusDays(1);
		
		//指定時刻までの時間を取得
		Duration duration1 = new Duration(nowTime, dt1);
		Duration duration2 = new Duration(nowTime, dt2);
		
		//ミリ秒に変換
		long delay1 = duration1.getMillis();
		long delay2 = duration2.getMillis();
		
		//指定時間後に起動→24時間間隔で再呼び出し
		Timer timer = new Timer();
		timer.schedule(task1, delay1, TimeUnit.DAYS.toMillis(1)); 
		timer.schedule(task2, delay2, TimeUnit.DAYS.toMillis(1)); 
	}

}

class Task extends TimerTask{
	@Override
	public void run() {
		ScheduledCrawl.label.setText("データ取得中");
		Crawler.main(null);
		ScheduledCrawl.label.setText("待機中...");
	}
}