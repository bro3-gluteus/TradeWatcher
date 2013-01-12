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
}
