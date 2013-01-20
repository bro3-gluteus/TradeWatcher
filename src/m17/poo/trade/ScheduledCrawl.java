package m17.poo.trade;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextArea;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;

public class ScheduledCrawl {
	
	static DateTime dt2;
	
	ScheduledCrawl() {
		JTextArea outputArea = TradeWatcher.outputArea;
		//タスク用意
		TimerTask task1 = new Task();
		TimerTask task2 = new Task();
		
		//現在時刻と今日の9:50と次の00分を取得
		DateTime nowTime = new DateTime(DateTimeZone.forID("Asia/Tokyo"));
		int hours = nowTime.getHourOfDay()+1;
		if (hours==24) hours = 0;
		DateTime dt1 = nowTime.withTime(hours, 0, 0, 0);
		dt2 = nowTime.withTime(9, 50, 0, 0);
		
		//もし指定時間が過ぎていたら明日に置き換え
		if (!dt1.isAfterNow()) dt1 = dt1.plusDays(1);
		if (!dt2.isAfterNow()) dt2 = dt2.plusDays(1);
		
		outputArea.append(dt1.toString("MM/dd HH:mm")+"にCrawler予約中\n");
		outputArea.append(dt2.toString("MM/dd HH:mm")+"にCrawler予約中\n");
		
		//指定時刻までの時間を取得
		Duration duration1 = new Duration(nowTime, dt1);
		Duration duration2 = new Duration(nowTime, dt2);
		
		//ミリ秒に変換
		long delay1 = duration1.getMillis();
		long delay2 = duration2.getMillis();
		
		//指定時間後に起動→24時間間隔で再呼び出し
		Timer timer = new Timer();
		timer.schedule(task1, delay1, TimeUnit.HOURS.toMillis(1)); 
		timer.schedule(task2, delay2, TimeUnit.DAYS.toMillis(1)); 
	}

}

class Task extends TimerTask{
	public void run() {
		new Crawler();
	}
}