package m17.poo.trade;

import java.io.File;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class DataPool {

	private Multimap<String, String[]> pool = ArrayListMultimap.create();

	public synchronized void poolData(String cardId,String[] rate){
		pool.put(cardId, rate);
	}
	
	public void saveData(){
		IOUtil<Multimap<String, String[]>> ioutil = new IOUtil<Multimap<String, String[]>>();
		Multimap<String, String[]> savedData;
		try {
			savedData = ioutil.loadZippedData(new File("data/trade.zip"));
		} catch (Exception e) {
			savedData = ArrayListMultimap.create();
		}
		try {
			savedData.putAll(pool);
			ioutil.saveZippedData(savedData, new File("data/trade.zip"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}	
}
