package gui.termmantab;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CreateInfo extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar CreateMenu;
	private String s1=null;
	
	private JTextField text_IP=null;
	private JTextField text_Port=null;
	private JComboBox<String> text_Size=null;
	private JComboBox<String> text_Method=null;
	private JComboBox<String> text_State=null;
	
	private Term_Info config=null;
	
	public String getS1() {
		return s1;
	}

	public void setS1(String s1) {
		this.s1 = s1;
	}

	public Term_Info getConfig() {
		return config;
	}

	public void setConfig(Term_Info config) {
		this.config = config;
	}

	public CreateInfo() {
		this.setTitle("配置");
		this.setSize(450, 170);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setJMenuBar((JMenuBar) createMenu());
		this.add(createPane());
		this.setVisible(true);
		updateJLocation();

	}

	public JComponent createMenu() {
		final JMenu fileMenue = new JMenu("开始");
		final JMenuItem exit  = new JMenuItem("退出");
		final JMenuItem add   = new JMenuItem("添加");
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
				config=new Term_Info();
				config.setIp(text_IP.getText());
				config.setPort(text_Port.getText());
				config.setSize(text_Size.getSelectedItem().toString());
				config.setMethod(text_Method.getSelectedItem().toString());
				config.setState(text_State.getSelectedItem().toString());
				dispose();
			}
		});
		reset.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				text_IP.setText("");
				text_Port.setText("");
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
		final JPanel p2 =new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		
		final JLabel IP  =new JLabel("IP:");
		text_IP=new JTextField(10);
		final JLabel port= new JLabel("Port:");
		text_Port = new JTextField(10);
		final JLabel size=new JLabel("显示屏大小:");
		text_Size = new JComboBox<String>();
		text_Size.addItem("300*400");
		text_Size.addItem("400*600");
		text_Size.addItem("800*1200");
		final JLabel method=new JLabel("扫描方式:");
		text_Method=new JComboBox<String>();
		text_Method.addItem("1/4");
		text_Method.addItem("1/8");
		text_Method.addItem("1/16");
		final JLabel state =new JLabel("状态:");
		text_State=new JComboBox<String>();
		text_State.addItem("connect");
		text_State.addItem("disconnect");
		p1.add(Box.createHorizontalStrut(50));
		p1.add(IP);
		p1.add(Box.createHorizontalStrut(5));
		p1.add(text_IP);
		p1.add(Box.createHorizontalStrut(5));
		p1.add(port);
		p1.add(Box.createHorizontalStrut(5));
		p1.add(text_Port);
		p1.add(Box.createHorizontalStrut(50));
		
		p2.add(Box.createHorizontalStrut(20));
		p2.add(size);
		p2.add(Box.createHorizontalStrut(5));
		p2.add(text_Size);
		p2.add(Box.createHorizontalStrut(5));
		p2.add(method);
		p2.add(Box.createHorizontalStrut(5));
		p2.add(text_Method);
		p2.add(Box.createHorizontalStrut(5));
		p2.add(state);
		p2.add(Box.createHorizontalStrut(5));
		p2.add(text_State);
		p2.add(Box.createHorizontalStrut(20));
		
		pane.add(Box.createVerticalStrut(10));
		pane.add(p1);
		pane.add(Box.createVerticalStrut(10));
		pane.add(p2);
		pane.add(Box.createVerticalStrut(25));
		pane.setBorder(BorderFactory.createTitledBorder("配置"));
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
