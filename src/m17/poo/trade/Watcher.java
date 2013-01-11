package m17.poo.trade;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

public class Watcher {
	
	private static int threadLife = 2*60*60;//スレッドの寿命(秒)
	static DataPool datapool = new DataPool();
	private static int threadNumber = 6;
	
	public static void main(String[] args){
    StopWatch sw = new StopWatch();
    
    
    //カードIDから即落価格を得る
    String[] card_id = {"1001","1002","1003","1004","1005","1006","1007","1008","1009","1010","1011","1012","1013","1014","1015","1016","1017","1018","1019","1020","1021","1022","1023","1024","1025","1026","1027","1028","1029","1030","1031","1032","1033","1034","1035","1036","1037","1038","1039","1040","1041","1042","1043","1046","1047","1048","1049","1050","1051","1052","1053","1054","1055","1056","1057","1058","1059","1060","1061","1062","1063","1066","1067","1068","1069","1070","1071","1072","1073","1074","1075","1076","1078","1079","1080","1081","1082","1083","1084","1085","1086","1087","1088","1089","1090","1091","1092","1093","1094","1095","1096","1097","1098","1099","1100","1101","1102","1103","1104","1105","1106","1107","1108","1109","1110","1111","1112","1113","1114","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035","2036","2037","2038","2039","2040","2041","2042","2043","2044","2045","2046","2047","2048","2049","2050","2051","2052","2056","2057","2058","2059","2060","2061","2062","2063","2064","2065","2066","2067","2068","2069","2070","2071","2072","2073","2074","2076","2077","2078","2079","2080","2081","2082","2083","2084","2085","2086","2087","2088","2089","2090","2091","2092","2093","2094","2095","2096","2097","2098","2099","2100","2101","2102","2103","2104","2106","2107","2108","2109","2110","2111","2112","2113","2114","2115","2116","2117","2118","2120","2121","2122","2123","2124","2125","3001","3002","3003","3004","3005","3006","3007","3008","3009","3010","3011","3012","3013","3014","3015","3016","3017","3018","3019","3020","3021","3022","3023","3024","3025","3026","3027","3028","3029","3030","3031","3032","3033","3034","3035","3036","3037","3038","3039","3040","3041","3042","3043","3044","3045","3046","3047","3048","3049","3050","3051","3052","3053","3054","3056","3057","3058","3059","3060","3061","3062","3063","3064","3065","3066","3067","3068","3069","3070","3071","3072","3073","3074","3075","3076","3077","3078","3079","3080","3081","3082","3083","3084","3085","3086","3087","3088","3089","3090","3091","3092","3093","3094","3095","3096","3097","3098","3099","3100","3101","3102","3103","3104","3105","3107","3108","3109","3110","4001","4002","4003","4004","4005","4006","4007","4008","4009","4010","4011","4012","4013","4014","4015","4016","4017","4018","4019","4020","4021","4022","4023","4024","4025","4026","4027","4028","4029","4030","4031","4032","4033","4034","4035","4036","4037","4038","4039","4040","4042","4043","4044","4045","4046","4047","4048","4049","4050","4051","4052","4053","4054","4055","4056","4057","4058","4059","4060","4061","4062","4063","4064","4066","4067","4068","4069","4070","4071","4072","4073","4074","4075","4076","4077","4078","4079","4080","4081","4082","4083","4084","4085","4086","4087","4088","4089","4090","4091","4092","4093","4094"
};
    
    List<List<String>> cardIdperThread= new ArrayList<List<String>>();
    for ( int i=0; i<threadNumber; i++ ) {
    	cardIdperThread.add( new ArrayList<String>() ); 
    }
    for (int i= 0;i<card_id.length;i++){
    	cardIdperThread.get(i % threadNumber).add(card_id[i]);
    }
    ExecutorService exec = Executors.newFixedThreadPool(threadNumber);
    for (int i=0; i<threadNumber; i++) {
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
    
    datapool.saveData();
    sw.stop("全処理完了");
	}
}

class TradeRateThread implements Runnable {
	private List<String> cards;
	public TradeRateThread(List<String> cards){
		this.cards = cards;
	}
	public void run() {
		WebDriver d = CommonFlow.getBro3WebDriver(CommonSettings.USE_FIREFOX);

		for (String cardId:cards){
    	StopWatch sw1 = new StopWatch();
  		Price sokuraku = new Price(d,cardId);
	    String[] rate = sokuraku.getRate(); 
	    Watcher.datapool.poolData(cardId, rate);
	    sw1.stop(rate[0]+" "+cardId+": "+rate[1]+"TP/"+rate[2]+"TP");
		}
	}

}