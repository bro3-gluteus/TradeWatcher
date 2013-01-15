package m17.poo.trade;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AccountEditor implements ActionListener{
	
	private JFrame frame;
	private JTextField emailContent;
	private JTextField passContent;
	
	public void editor(){

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setSize(400, 100);
		frame.setTitle("mixiアカウント設定");
		frame.setVisible(true);
		
		//親パネル
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		JPanel panel2 = new JPanel();
		frame.getContentPane().add(panel2);
		
		// GroupLayout の生成
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		// 自動的にコンポーネント間のすき間をあけるようにする
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		JLabel emailLabel = new JLabel("Email");
		panel.add(emailLabel);
		JLabel passLabel = new JLabel("Pass");
		panel.add(passLabel);
		
		emailContent = new JTextField(25);
		panel.add(emailContent);
		passContent = new JTextField(25);
		panel.add(passContent);
		
		JButton editButton = new JButton("確定");
		editButton.addActionListener(this);
    panel2.add(editButton);
		
    // 水平方向のグループ
    GroupLayout.SequentialGroup hGroup
        = layout.createSequentialGroup();
      
    // ラベルを含むパラレルグループを追加
    hGroup.addGroup(layout.createParallelGroup()
                    .addComponent(emailLabel)
                    .addComponent(passLabel));

    hGroup.addGroup(layout.createParallelGroup()
        .addComponent(emailContent)
        .addComponent(passContent));
    

    layout.setHorizontalGroup(hGroup);

    // 垂直方向のグループ
    GroupLayout.SequentialGroup vGroup
        = layout.createSequentialGroup();

    // 名字のラベル、テキストフィールドを含む
    // パラレルグループを追加
    vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLabel)
                    .addComponent(emailContent));

    // 名前のラベル、テキストフィールドを含む
    // パラレルグループを追加
    vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(passLabel)
                    .addComponent(passContent));

    layout.setVerticalGroup(vGroup);
    
    frame.add(panel,BorderLayout.CENTER);
    frame.add(panel2,BorderLayout.SOUTH);

    frame.pack();
    frame.setVisible(true);
	
	}

	
	public void actionPerformed(ActionEvent e) {
		String[] saveData = {emailContent.getText().trim(),passContent.getText().trim()};
		IOUtil<String[]> ioutil = new IOUtil<String[]>();
		try {
			ioutil.saveZippedData(saveData, new File("config/account.zip"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		frame.dispose();
	}
}
