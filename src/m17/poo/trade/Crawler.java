package m17.poo.trade;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

public class Crawler {
	
	private static int threadLife = 2*60*60;//スレッドの寿命(秒)
	static DataPool datapool = new DataPool();
	private static int numThread = 6;
	
	public static void main(String[] args){
    StopWatch sw = new StopWatch();
    
    //カードIDのリスト取得
    InfoReader info = new InfoReader();
    List<String> cardList = info.getIdList();
    
    //カードをスレッドに振り分け
    List<List<String>> cardIdperThread= new ArrayList<List<String>>();
    for ( int i=0; i<numThread; i++ ) {
    	cardIdperThread.add( new ArrayList<String>() ); 
    }
    for (int i= 0;i<cardList.size();i++){
    	cardIdperThread.get(i % numThread).add(cardList.get(i));
    }
    
    //マルチスレッドで処理
    ExecutorService exec = Executors.newFixedThreadPool(numThread);
    for (int i=0; i<numThread; i++) {
      List<String> cards = cardIdperThread.get(i);
      exec.execute(new TradeRateThread(cards));
    }
    try {
      exec.shutdown();
      // (全てのタスクが終了した場合、trueを返してくれる)
      if (!exec.awaitTermination(threadLife, TimeUnit.SECONDS)) {
        // タイムアウトした場合、全てのスレッドを中断(interrupted)してスレッドプールを破棄する。
        exec.shutdownNow();
      }
    } catch (InterruptedException e) {
      // awaitTerminationスレッドがinterruptedした場合も、全てのスレッドを中断する
      System.out.println("awaitTermination interrupted: " + e);
      exec.shutdownNow();
    }
    
    //保存
    datapool.saveData();
    sw.stop("全処理完了");
	}
}

class TradeRateThread implements Runnable {
	private List<String> cards;
	public TradeRateThread(List<String> cards){
		this.cards = cards;
	}
	public void run() {
		WebDriver d = CommonFlow.getBro3WebDriver(CommonSettings.USE_FIREFOX);

		for (String cardId:cards){
    	StopWatch sw1 = new StopWatch();
  		Price sokuraku = new Price(d,cardId);
	    String[] rate = sokuraku.getRate(); 
	    Crawler.datapool.poolData(cardId, rate);
	    sw1.stop(rate[0]+" "+cardId+": "+rate[1]+"TP/"+rate[2]+"TP");
		}
	}

}