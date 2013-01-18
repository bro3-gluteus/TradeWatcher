package m17.poo.trade;


public class StopWatch {

  //static変数はグローバル変数のようなもので、オブジェクト間で共有されます。
  public static boolean PRINT_LOG = true;
  
  private long begin;
  
  public StopWatch() {
    begin = System.currentTimeMillis();
  }
  
  public void stop( String message ) {
    if ( PRINT_LOG ) {
      double time = (double)(System.currentTimeMillis() - begin) / (double)1000;
      String t = "["+String.format("%7.3f",time)+" 秒]";
      TradeWatcher.outputArea.append(t+" "+message);
      System.out.println(t+" "+message);
    }
  }
  
}
