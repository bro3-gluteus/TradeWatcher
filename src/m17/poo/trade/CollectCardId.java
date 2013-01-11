package m17.poo.trade;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.base.Joiner;

public class CollectCardId {
  private static List<String> cardIdList = new ArrayList<String>();
	public static void main(String[] args) {
		WebDriver d = CommonFlow.getBro3WebDriver(CommonSettings.USE_FIREFOX);
		d.navigate().to("http://m17.3gokushi.jp/card/card_search.php?name_kana=all&status=0&ability_type=attack&ability_value=&ability_sort=desc&search_mode=detail&view_mode=basic&search_start=1&search-btn=%E6%A4%9C%E7%B4%A2&p=1");
		getId(d);
	}
	
	private static void getId(WebDriver d){
		List<WebElement> card = d.findElement(By.id("busyo-search-result-basic"))
				.findElements(By.tagName("tr"));
		int i;
		for (i=1;i<card.size();i++){
			String id = card.get(i).findElements(By.tagName("td")).get(0).getText();
			cardIdList.add(id);
		}
		if(i==card.size()){
			try{
				d.findElement(By.linkText("＞")).click();
				getId(d);
			}catch (NoSuchElementException e){
				String joiner = Joiner.on("\",\"").skipNulls().join(cardIdList);
				System.out.println("\""+joiner+"\"");
			}
		}	
	}
}
