package m17.poo.trade;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Screen {
	
	private int frameX;
	private int frameY;
	
	public void setFrameSize(int frameX, int frameY){
		this.frameX = frameX;
		this.frameY = frameY;
	}
	
	public int[] getFrameCenter(){
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		int x=(int)(screenSize.width/2);
		int y=(int)(screenSize.height/2);
		int[] xy = {x-frameX/2,y-frameY/2};
		return xy;
	}
	
	public int[] getCenter(){
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		int x=(int)(screenSize.width/2);
		int y=(int)(screenSize.height/2);
		int[] xy = {x,y};
		return xy;
	}
}
