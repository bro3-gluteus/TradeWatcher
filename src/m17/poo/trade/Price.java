package m17.poo.trade;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Price {
	/**
	 * 即落価格＆入札０最安値を探してくる
	 * 入札あり最高値はTP移動入札の判別を正しくできないのでpending
	 * constructorにdriverを渡してインスタンス化
	 * setter: カードID
	 * getter: {時間,即落価格,入札０最安値}
	 */
	
	private WebDriver d;
	private String cardId;
	private String sokurakuPrice="";
	private String saiyasuPrice="";
	private DateTime serverTime;
	
	public Price(WebDriver d){
		this.d=d;
	}
	
	public void setCardId(String cardId){
		this.cardId = cardId;
	}
	
	public String[] getRate(){
		/**
		 * {調査時刻,即落価格,入札0最安値}の配列を返す。
		 * 実際に価格を探すのはpriceFinder()
		 */
		
		//目的のカードのページに飛んでおく(価格順)
		d.navigate().to("http://m17.3gokushi.jp/card/trade.php?s=price&o=a&t=no&k="+cardId);
		//サーバー時間
		//serverTime = d.findElement(By.id("server_time")).getText();
		//サーバー時間がdisplay:noneで取得できない。いい方法はないだろうか？
		serverTime = new DateTime(DateTimeZone.forID("Asia/Tokyo"));

		//即落価格を見つける
		try{
			priceFinder();
		}catch (NoSuchElementException e){//出品なし
			sokurakuPrice = "";
			saiyasuPrice = "";
		}
		
		//配列組み立て
		String[] rate = {serverTime.toString("yyyy/MM/dd HH:mm:ss"),sokurakuPrice,saiyasuPrice};
		
		return rate;
	}
	
	
	private void priceFinder(){
		/**
		 * 即落価格,入札0最安値を見つけて、フィールド変数に代入する
		 */
		
		//出品されているカードを収集
  	List<WebElement> itemList =d.findElement(By.id("trade"))
												    		.findElement(By.className("tradeTables"))
												    		.findElements(By.tagName("tr"));

    int counter = 0; 
    boolean blnRaku = false; //即落が見つかった時にtrue
    boolean blnYasu = false; //最安入札0が見つかった時にtrue
    
    //落札価格を見つけるためのループ
    for (WebElement card:itemList){
    	counter++;
    	
    	List<WebElement> columnList = card.findElements(By.tagName("td"));//項目分け
    	
    	if (columnList.size()==0) continue;//一行目は項目数０なので飛ばす
    	
    	//強制公開期限が24時間以内のもので最安の入札０を見つける
    	String openLimit = columnList.get(8).getText();
    	if(!openLimit.equals("---")&&columnList.get(7).getText().equals("0")&&!blnYasu){ //入札０で即落で無い時
    		
    		Pattern pattern = Pattern.compile("([0-9]+)-([0-9]+)-([0-9]+)\n([0-9]+):([0-9]+)");
    		Matcher matcher = pattern.matcher(openLimit);
    		if (matcher.find()){
    			DateTime openLimitDate = new DateTime(Integer.valueOf(matcher.group(1)), 
    					Integer.valueOf(matcher.group(2)),
    					Integer.valueOf(matcher.group(3)), 
    					Integer.valueOf(matcher.group(4)), 
    					Integer.valueOf(matcher.group(5)), 0);

    			Duration duration = new Duration(serverTime, openLimitDate);//現在時刻との差
    			
    			if(duration.getMillis()<86400000){//24時間以内
    				saiyasuPrice = columnList.get(6).getText();
    				blnYasu = true;
        		//カンマ区切りの数値文字列はCSVにしたくなった時に邪魔なのでカンマを消す
            NumberFormat nf = NumberFormat.getInstance();
            try {
    					Number num = nf.parse(saiyasuPrice);
    					int IntValue = num.intValue();
    					saiyasuPrice = String.valueOf(IntValue);
    				} catch (ParseException e) {
    					e.printStackTrace();
    				}
    			}
    		}
    	}
    	
    	//即落を見つける
    	if(columnList.get(8).getText().equals("---")&&!blnRaku){
    		sokurakuPrice = columnList.get(6).getText();
    		blnRaku = true;
        NumberFormat nf = NumberFormat.getInstance();
        try {
					Number num = nf.parse(sokurakuPrice);
					int IntValue = num.intValue();
					sokurakuPrice = String.valueOf(IntValue);
				} catch (ParseException e) {
					e.printStackTrace();
				}
    	}	
    }
    //一番下のカードまで探して見つからなかった時は次のページへ
    if ((sokurakuPrice==""||saiyasuPrice=="")&&counter==itemList.size()){
	    try{	
	    	d.findElement(By.linkText("＞")).click();//次のページへ移動
		    priceFinder();//移動したページでもう一度探す
	    }catch(NoSuchElementException e){
	    	//e.printStackTrace();
	    	//無視
	    }
    }		
	}
}
