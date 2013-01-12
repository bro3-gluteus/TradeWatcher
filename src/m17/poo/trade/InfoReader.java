package m17.poo.trade;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoReader {
	
	private IOUtil<Map<String, String[]>> ioutil = new IOUtil<Map<String, String[]>>();
	private Map<String,String[]> cardMap;
	
	InfoReader(){
    try {
			cardMap = ioutil.loadZippedData(new File("data/cardinfo.zip"));
		} catch (Exception e) {
			cardMap = new HashMap<String,String[]>();
		}
	}
	
	public List<String> getIdList(){
    List<String> idList = new ArrayList<String>();
    for (String id : cardMap.keySet()){
    	idList.add(id);
    }
    return idList;
	}
	
	public List<String> getIdListByRarity(String rarity){
		List<String> idList = new ArrayList<String>();
    for (String id : cardMap.keySet()){
    	if (cardMap.get(id)[0].equals(rarity)) idList.add(id);
    }
		return idList;
	}
	
	public List<String> getIdListByCost(String cost){
		List<String> idList = new ArrayList<String>();
    for (String id : cardMap.keySet()){
    	if (cardMap.get(id)[1].equals(cost)) idList.add(id);
    }
		return idList;
	}
}
