package m17.poo.trade;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class Viewer extends MyFrame implements ActionListener{
	
	private JTextField searchField;
	private JTextArea outputArea;
	private String title;
	
	public Viewer(String title){
		
		this.title = title;
		
		Screen screen = new Screen();
		int sizeX = 300;
		int sizeY = 300;
		screen.setFrameSize(sizeX, sizeY);
		int[] xy = screen.getFrameCenter();

		setBounds(xy[0],xy[1],sizeX, sizeY);
		setTitle(title);
		
		//検索窓部分
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		searchField = new JTextField(5);
		JLabel label = new JLabel("カードNo.");
		JButton button = new JButton("検索");
		button.addActionListener(this);
		searchPanel.add(label);
		searchPanel.add(searchField);
		searchPanel.add(button);
		add(searchPanel,BorderLayout.NORTH);
		
		//output部分
		outputArea = new JTextArea();
		outputArea.setForeground(Color.BLACK);
		outputArea.setBackground(Color.WHITE);
		outputArea.setEditable(false);
		outputArea.setMargin(new Insets(2, 5, 2, 2));
		outputArea.setBorder(new BevelBorder(BevelBorder.LOWERED));
		getContentPane().add(new JScrollPane(outputArea),BorderLayout.CENTER);
		
		setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		outputArea.setText("");
		String id = searchField.getText();
		setTitle(title+" (No."+id+")");
		PickUp pickUp = new PickUp();
		pickUp.setId(id);
		Map<String,String> map = null;
		if (title=="始値Viewer") {
			map = pickUp.getOpeningPrice();
		}
		try{
			map = new TreeMap<String,String>(map);
			for (Map.Entry<String, String> entry : map.entrySet()){
				outputArea.append(entry.getKey()+"\t"+entry.getValue()+"\n");
			}
		}catch (NullPointerException err){
			System.out.println(err);
		}
	}
}
