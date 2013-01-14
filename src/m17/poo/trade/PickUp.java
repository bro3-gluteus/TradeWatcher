package m17.poo.trade;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class PickUp {
	
	private String id;
	
	public void setId(String id){
		this.id = id;
	}
	
	public Map<String,String> getOpeningPrice(){
		
		IOUtil<Multimap<String, String[]>> ioutil = new IOUtil<Multimap<String, String[]>>();
		Multimap<String, String[]> savedData;
		try {
			savedData = ioutil.loadZippedData(new File("data/trade.zip"));
		} catch (Exception e) {
			savedData = ArrayListMultimap.create();
		}
		
		Map<String,String> priceMap = new HashMap<String,String>();
		
		Collection<String[]> dataCollection = savedData.get(id);
		for (String[] priceAry:dataCollection){
			String time  = priceAry[0];
			String price = priceAry[1];
			int HH = Integer.valueOf(time.split(" ")[1].split(":")[0]);
			int mm = Integer.valueOf(time.split(":")[1]);
			//10:00:00〜10:14:59
			if (HH==10&&mm<15){
				String key   = time.split(" ")[0];
				String value = price;
				priceMap.put(key, value);
			}
		}
		
		return priceMap;
	} 
	
}
