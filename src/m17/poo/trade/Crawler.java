package m17.poo.trade;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JTextArea;
import org.openqa.selenium.WebDriver;

public class Crawler {
	
	private static int threadLife = 2*60*60;//スレッドの寿命(秒)
	static DataPool datapool = new DataPool();
	private static int numThread = 6;
	
	Crawler(){
		StopWatch sw = new StopWatch();
    TradeWatcher.outputArea.append("トレードデータの取得を開始してます...\n");
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
    sw.stop("トレード情報取得が完了しました\n");
	}
}

class TradeRateThread implements Runnable {
	private List<String> cards;
	public TradeRateThread(List<String> cards){
		this.cards = cards;
	}
	public void run() {
		WebDriver d = CommonFlow.getBro3WebDriver(CommonSettings.USE_FIREFOX);
		JTextArea outputArea = TradeWatcher.outputArea;
		for (String cardId:cards){
  		Price sokuraku = new Price(d,cardId);
	    String[] rate = sokuraku.getRate(); 
	    Crawler.datapool.poolData(cardId, rate);
	    outputArea.append(rate[0]+" "+cardId+": "+rate[1]+"TP/"+rate[2]+"TP\n");
	    outputArea.setCaretPosition(0); 
	    outputArea.setCaretPosition(outputArea.getDocument().getLength());
		}
	}

}