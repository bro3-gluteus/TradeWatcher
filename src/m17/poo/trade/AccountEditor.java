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

@SuppressWarnings("serial")
public class AccountEditor extends JFrame implements ActionListener{
	
	private JTextField emailContent;
	private JTextField passContent;
	
	public void editor(String mixiEmail,String mixiPassword){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(400, 100);
		setTitle("mixiアカウント設定");
		setVisible(true);
		
		//親パネル
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		JPanel panel2 = new JPanel();
		getContentPane().add(panel2);
		
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
		emailContent.setText(mixiEmail);
		panel.add(emailContent);
		passContent = new JTextField(25);
		passContent.setText(mixiPassword);
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
    
    add(panel,BorderLayout.CENTER);
    add(panel2,BorderLayout.SOUTH);

    pack();
    setVisible(true);
	
	}

	
	public void actionPerformed(ActionEvent e) {
		String[] saveData = {emailContent.getText().trim(),passContent.getText().trim()};
		IOUtil<String[]> ioutil = new IOUtil<String[]>();
		try {
			ioutil.saveZippedData(saveData, new File("config/account.zip"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		AccountManager accountframe = new AccountManager();
		accountframe.setVisible(true);
		dispose();
	}
}
