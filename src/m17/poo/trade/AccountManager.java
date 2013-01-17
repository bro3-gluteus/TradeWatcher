package m17.poo.trade;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AccountManager extends myFrame implements ActionListener{

	private String mixiEmail;
	private String mixiPassword;
	
	AccountManager(){
		
		IOUtil<String[]> ioutil = new IOUtil<String[]>();
		String[] account = {"",""};
		try {
			account = ioutil.loadZippedData(new File("config/account.zip"));
		} catch (Exception e) {
		}
		mixiEmail = account[0].trim();
		mixiPassword = account[1].trim();
		if (mixiEmail.length()==0) mixiEmail="未設定";
		if (mixiPassword.length()==0) mixiPassword="未設定";
		
		Screen screen = new Screen();
		int sizeX = 400;
		int sizeY = 100;
		screen.setFrameSize(sizeX, sizeY);
		int[] xy = screen.getFrameCenter();
		setBounds(xy[0],xy[1],sizeX, sizeY);

		setTitle("mixiアカウント設定");
		
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
		
		JLabel emailContent = new JLabel(": "+mixiEmail);
		panel.add(emailContent);
		JLabel passContent = new JLabel(": "+mixiPassword);
		panel.add(passContent);
		
		//OKボタン
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
		});
		panel2.add(okButton);
		
		//編集ボタン
		JButton editButton = new JButton("編集");
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
	}
	
	public static void main(String[] args){
		AccountManager frame = new AccountManager();
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e){
		AccountEditor editor = new AccountEditor();
		editor.editor(mixiEmail,mixiPassword);
		dispose();
	}
	
}
