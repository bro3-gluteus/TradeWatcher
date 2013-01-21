package m17.poo.trade;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UpdateCardInfo {
  
	private Map<String,String[]> cardMap = new HashMap<String,String[]>();
	private JTextArea outputArea = TradeWatcher.outputArea;;
	
	UpdateCardInfo() {
		outputArea.append("カード情報の更新を開始しています\n");
		outputArea.setCaretPosition(0); 
    outputArea.setCaretPosition(outputArea.getDocument().getLength());
  	StopWatch sw = new StopWatch();
		WebDriver d = CommonFlow.getBro3WebDriver(CommonSettings.USE_FIREFOX);
		d.navigate().to("http://m17.3gokushi.jp/card/card_search.php?name_kana=all&status=0&ability_type=attack&ability_value=&ability_sort=desc&search_mode=detail&view_mode=basic&search_start=1&search-btn=%E6%A4%9C%E7%B4%A2&p=1");
		collectInfo(d);
		IOUtil<Map<String, String[]>> ioutil = new IOUtil<Map<String, String[]>>();
		try {
			ioutil.saveZippedData(cardMap, new File("data/cardinfo.zip"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		sw.stop("------全処理終了------");
	}
	
	private void collectInfo(WebDriver d){
		List<WebElement> card = d.findElement(By.id("busyo-search-result-basic"))
				.findElements(By.tagName("tr"));
		int i;
		for (i=1;i<card.size();i++){
			List<WebElement> targetCard = card.get(i).findElements(By.tagName("td"));
			String id = targetCard.get(0).getText();
			String rarity = targetCard.get(1).getText();
			String cost = targetCard.get(5).getText();
			String[] info = {rarity,cost};
			cardMap.put(id,info);
			
			outputArea.append("id:"+id+" rarity:"+rarity+" cost:"+cost+"\n");
			outputArea.setCaretPosition(0); 
	    outputArea.setCaretPosition(outputArea.getDocument().getLength());
			
		}
		if(i==card.size()){
			try{
				d.findElement(By.linkText("＞")).click();
				collectInfo(d);
			}catch (NoSuchElementException e){}
		}	
	}
}
