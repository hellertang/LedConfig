package gui.framework;

import javax.swing.*;


import java.awt.event.*;

public class MenuBarExt extends JMenuBar {
	
	private Framework framework = null;
	public static final String PLAN_DESIGN 	= "��������";
	public static final String PLAN_SEND		= "��������";
	public static final String PLAY_CTRL		= "���Ź���";
	public static final String TERMINAL_CTRL	= "�ն˿���";
	public static final String TERMINAL_MAN	= "�ն˹���";
	
	private JMenu	fileMenu = null;
	
	private LedFileChooser fileChooser = LedFileChooser.getFileChooser();
	
	public MenuBarExt(Framework framework){
		super();
		this.framework = framework;
		//make menu to the right side
		add(Box.createHorizontalGlue());
		//create the menus 
		createMenu();
		//add paddings
		add(Box.createHorizontalStrut(20));
		
	}
	
	private void createMenu(){
		JMenu 		menu = null;
		JMenuItem	menuItem = null;
		
		SelListener selListener = new SelListener();
		/**
		 * wenjain
		 */
		fileMenu	= new JMenu("�ļ�(F)");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		this.add(fileMenu);

		//menu items below
		menuItem = new JMenuItem("��");
		
		menuItem.setActionCommand(utils.FileChooserUtils.bOpenActionCmd);
		menuItem.setHorizontalTextPosition(SwingConstants.CENTER);
		
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				LedFileChooser.getFileChooser().openFileChooser(framework, utils.FileChooserUtils.bOpenActionCmd);
			}
			
		});
		fileMenu.add(menuItem);
		
		
		menuItem = new JMenuItem("�½�(N)", KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK));
		menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		menuItem.setHorizontalTextPosition(SwingConstants.CENTER);
		fileMenu.add(menuItem);
		
		menuItem = new JMenuItem("���");
		menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				framework.getTabbedPane().getDesignTab().getEdit().createNewAreaDialog();
			}
			
		});
		menuItem.setHorizontalTextPosition(SwingConstants.CENTER);
		fileMenu.add(menuItem);
		
		menuItem = new JMenuItem("����(S)", KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(null);//TODO
		menuItem.setHorizontalTextPosition(SwingConstants.CENTER);
		fileMenu.add(menuItem);
		
		menuItem = new JMenuItem("���..");
		menuItem.addActionListener(null);//TODO
		menuItem.setHorizontalTextPosition(SwingConstants.CENTER);
		fileMenu.add(menuItem);
		
		fileMenu.addSeparator();
		
		menuItem = new JMenuItem(PLAN_DESIGN, KeyEvent.VK_1);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.addActionListener(selListener);
		menuItem.setHorizontalTextPosition(SwingConstants.CENTER);
		fileMenu.add(menuItem);
		
		menuItem = new JMenuItem(PLAN_SEND, KeyEvent.VK_2);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		menuItem.addActionListener(selListener);
		menuItem.setHorizontalTextPosition(SwingConstants.CENTER);
		fileMenu.add(menuItem);
		
		menuItem = new JMenuItem(PLAY_CTRL, KeyEvent.VK_3);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
		menuItem.addActionListener(selListener);
		menuItem.setHorizontalTextPosition(SwingConstants.CENTER);
		fileMenu.add(menuItem);
		
		menuItem = new JMenuItem(TERMINAL_CTRL, KeyEvent.VK_4);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.ALT_MASK));
		menuItem.addActionListener(selListener);
		menuItem.setHorizontalTextPosition(SwingConstants.CENTER);
		fileMenu.add(menuItem);
		
		menuItem = new JMenuItem(TERMINAL_MAN, KeyEvent.VK_5);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.ALT_MASK));
		menuItem.addActionListener(selListener);
		menuItem.setHorizontalTextPosition(SwingConstants.CENTER);
		fileMenu.add(menuItem);
		
		fileMenu.addSeparator();
		
		menuItem = new JMenuItem("�˳�");
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		menuItem.setHorizontalTextPosition(SwingConstants.CENTER);
		fileMenu.add(menuItem);
		
		/**
		 * shezhi
		 */
		menu 	= new JMenu("����(S)");
		menu.setMnemonic(KeyEvent.VK_S);
		this.add(menu);
		
		//menuItems
		menuItem = new JMenuItem("���Ŵ��ڴ�С   ");
		menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				framework.getTabbedPane().getDesignTab().getEdit().setContantSzieDialog();
			}
			
		});
		menu.add(menuItem);
		
		
		/**
		 * bangzhu
		 */
		menu	= new JMenu("����(H)");
		menu.setMnemonic(KeyEvent.VK_H);
		this.add(menu);
		
		menuItem = new JMenuItem("����   ");
		menuItem.addActionListener(new AboutListener());
		menuItem.setHorizontalAlignment(SwingConstants.CENTER);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("��ϵ����  ");
		menuItem.addActionListener(new ContactListener());
		menuItem.setHorizontalAlignment(SwingConstants.CENTER);
		menu.add(menuItem);
		
		menu.addSeparator();
		
	}
	 
	public JMenu getFileMenu(){
		return fileMenu;
	}
	
	private class AboutListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(framework, "Version 0.3", "����", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	private class ContactListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new sendEmail();
		}
		
		
	}
	
	private class SelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem mi = (JMenuItem)(e.getSource());
			String txt = mi.getText();
			if(txt.equals(PLAN_DESIGN)){//1
				System.out.println("1");
				framework.getTabbedPane().setSelectedIndex(0);
			}else if(txt.equals(PLAN_SEND)){//2
				System.out.println("2");
				framework.getTabbedPane().setSelectedIndex(1);
			}else if(txt.equals(PLAY_CTRL)){//3
				System.out.println("3");
				framework.getTabbedPane().setSelectedIndex(2);
			}else if(txt.equals(TERMINAL_CTRL)){//4
				System.out.println("4");
				framework.getTabbedPane().setSelectedIndex(3);
			}else if(txt.equals(TERMINAL_MAN)){//5
				System.out.println("5");
				framework.getTabbedPane().setSelectedIndex(4);
			}
		}
		
	}

}
