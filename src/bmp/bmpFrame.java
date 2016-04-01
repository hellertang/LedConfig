package bmp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class bmpFrame extends JFrame{
	
	public static final Dimension screensize = Toolkit.getDefaultToolkit()
			.getScreenSize();
	private JMenuBar menuBar;
	private JTabbedPane tabPane;
	
	public bmpFrame(){
		this.setTitle("图片编辑器");
		this.setLocation(300, 150);
		this.setSize(800, 500);;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setJMenuBar(InitMenueBar());
		this.getContentPane().add(InitTabPane(), BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	public JMenuBar InitMenueBar(){
	    menuBar=new JMenuBar();
		JMenu fileMenu=new JMenu("File");
		JMenuItem open=new JMenuItem("open");
		JMenuItem save=new JMenuItem("save");
		fileMenu.add(open);
		fileMenu.add(save);
		menuBar.add(fileMenu);
		JMenu editMenu=new JMenu("Edit");
		JMenuItem cut4=new JMenuItem("4等分");
		JMenuItem cut8=new JMenuItem("8等分");
		editMenu.add(cut4);
		editMenu.add(cut8);
		menuBar.add(editMenu);
		JMenu helpMenu=new JMenu("Help");
		JMenuItem aboutMenu=new JMenuItem("关于");
		helpMenu.add(aboutMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}
	
	public JTabbedPane InitTabPane(){
		ImageIcon icon1=createImageIcon("E://picture//3.png");
		ImageIcon icon2=createImageIcon("E://picture//5.png");
		tabPane=new JTabbedPane();
		JPanel pane=new JPanel();
		JPanel pane1=new JPanel();
		JButton hel=new JButton();
		hel.setIcon(icon2);
		hel.setVerticalTextPosition(SwingConstants.BOTTOM);
		hel.setHorizontalTextPosition(SwingConstants.CENTER);
		hel.setText("Hello");
		hel.setPreferredSize(new Dimension(70,50));
		JLabel lab=new JLabel();
		lab.setIcon(icon1);
		pane1.add(hel);
		JPanel pane2=new JPanel();
		pane2.add(lab);
		
		tabPane.addTab("normal",icon1, pane,"eqweqwe");
		tabPane.addTab("four", pane1);
		tabPane.addTab("eight", pane2);
		return tabPane;
	}

	public  ImageIcon createImageIcon(String path){
		if(path!=null)
			return new ImageIcon(path);
		else{
			System.out.println("没有这样的文件");
			return null;
		}
	}
	public static void main(String[] args) {
		try
	    {
			UIManager.put("RootPane.setupButtonVisible", false);
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle. generalNoTranslucencyShadow ;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
	  
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		new bmpFrame();
	}
	
	
	
	
	
	
	

}
