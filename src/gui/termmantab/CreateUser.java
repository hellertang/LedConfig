package gui.termmantab;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import utils.DisplayUtils;

public class CreateUser extends JFrame {
	private JMenuBar CreateMenu;
	private String str = null;
	private JTextField text = null;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public CreateUser() {
		this.setTitle("配置");
		this.setSize(250, 120);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setJMenuBar((JMenuBar) createMenu());
		this.add(createPane());
		this.setVisible(true);
//		updateJLocation();
		DisplayUtils.updateJLocation(this);

	}

	public JComponent createMenu() {
		final JMenu fileMenue = new JMenu("开始");
		final JMenuItem exit = new JMenuItem("退出");
		final JMenuItem add = new JMenuItem("添加");
		final JMenuItem reset = new JMenuItem("重置");
		exit.setHorizontalTextPosition(SwingConstants.CENTER);
		add.setHorizontalTextPosition(SwingConstants.CENTER);
		reset.setHorizontalTextPosition(SwingConstants.CENTER);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setStr(text.getText());
				dispose();
			}
		});
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				text.setText("");
			}
		});
		fileMenue.add(add);
		fileMenue.add(reset);
		fileMenue.add(exit);
		final JMenu helpMenu = new JMenu("帮助");
		final JMenuItem aboutMenu = new JMenu("关于");

		aboutMenu.setHorizontalTextPosition(SwingConstants.CENTER);
		aboutMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appVersion();
			}
		});

		helpMenu.add(aboutMenu);
		CreateMenu = new JMenuBar();
		CreateMenu.setLayout(new BoxLayout(CreateMenu, BoxLayout.X_AXIS));
		CreateMenu.add(Box.createHorizontalGlue());
		CreateMenu.add(fileMenue);
		CreateMenu.add(helpMenu);
		return CreateMenu;
	};

	public JComponent createPane() {
		final JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		final JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.setBorder(BorderFactory.createTitledBorder("信息"));

		final JLabel labe = new JLabel();
		labe.setText("用户名:");
		text = new JTextField(10);
		p1.add(Box.createHorizontalStrut(50));
		p1.add(labe);
		p1.add(Box.createHorizontalStrut(5));
		p1.add(text);
		p1.add(Box.createHorizontalStrut(50));

		pane.add(p1);
		pane.add(Box.createVerticalStrut(10));
		return pane;
	}

	public void updateJLocation() {
		Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension fsize = this.getSize();
		if (fsize.height > scr.height)
			fsize.height = scr.height;
		if (fsize.width > scr.width)
			fsize.width = scr.width;
		this.setLocation((scr.width - fsize.width) / 2,
				(scr.height - fsize.height) / 2);
	}

	public void appVersion() {
		JOptionPane.showMessageDialog(this, "Version 0.3", "关于",
				JOptionPane.PLAIN_MESSAGE);
	}

}
