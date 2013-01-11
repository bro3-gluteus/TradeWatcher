package m17.poo.trade;

import java.io.File;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class DataPool {

	private Multimap<String, String[]> pool;//Guava
	private IOUtil<Multimap<String, String[]>> ioutil = new IOUtil<Multimap<String, String[]>>();
	
	public DataPool(){
    try {
			pool = ioutil.loadZippedData(new File("data/trade.zip"));
		} catch (Exception e) {
			pool = ArrayListMultimap.create();
		}
	}
	
	public Multimap<String, String[]> getPool(){
		
		return pool;
	}
	
	public synchronized void poolData(String cardId,String[] rate){
		pool.put(cardId, rate);
	}
	
	public void saveData(){
		try {
			ioutil.saveZippedData(pool, new File("data/trade.zip"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
