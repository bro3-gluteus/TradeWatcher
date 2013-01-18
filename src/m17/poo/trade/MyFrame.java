package m17.poo.trade;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

//共通のセッティング

@SuppressWarnings("serial")
public class MyFrame extends JFrame{
	MyFrame(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		//Look&FeelをOSの物に。
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	  } catch (ClassNotFoundException e) {
	  } catch (InstantiationException e) {
	  } catch (IllegalAccessException e) {
	  } catch (UnsupportedLookAndFeelException e) {
	  }
	  SwingUtilities.updateComponentTreeUI(this);
	}
}
