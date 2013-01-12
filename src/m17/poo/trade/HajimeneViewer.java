package m17.poo.trade;

import java.util.List;
import java.util.Map;

public class HajimeneViewer {
	public static void main(String[] args) {
		
		//InfoReader reader = new InfoReader();
		//List<String> idList = reader.getIdListByCost("4.0"); //４コス全部表示
		//List<String> idList = reader.getIdListByRarity("UR"); //UR全部表示
		String[] idList = {"1001","4002"}; //各カード指定
		
		PickUp pickUp = new PickUp();
		for (String id:idList){
			pickUp.setId(id);
			Map<String,String> priceMap = pickUp.getOpeningPrice();
			System.out.println("-----カードID:"+id+"の始値情報-----");
			for (Map.Entry<String, String> entry : priceMap.entrySet()){
				System.out.println(entry.getKey()+" "+entry.getValue()+"TP");
			}
		}
	}
}
