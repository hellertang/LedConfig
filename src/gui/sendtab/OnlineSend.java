package gui.sendtab;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class OnlineSend extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String SERVER_IP;
	public static int    SERVER_PORT;
	public static String  folder_path;
	private JMenuBar sendMenu;
	private JPanel   termSet;
	private JPanel   sendPane;
	private JTextField IpText;
	private  JTextField PortText;
	
	public OnlineSend( String FOLDER_PATH){
	   folder_path=FOLDER_PATH;
	   this.setTitle("配置");
	   this.setSize(400,200);
	   this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	   this.setLayout(new BorderLayout());
	   this.setJMenuBar((JMenuBar) createMenu());
	   this.add(createSetPane(),BorderLayout.NORTH);
	   this.add(createSendPane(),BorderLayout.SOUTH);
	   this.setVisible(true);
	   updateJLocation();
	}

   public static String getIP(){
	   return  SERVER_IP;
   }
   public static int getPort(){
	   return SERVER_PORT;
   }
   private Component createSetPane(){
	   termSet =new JPanel();
	   termSet.setBorder(BorderFactory.createTitledBorder("配置终端"));
	   termSet.setLayout(new BoxLayout(termSet,BoxLayout.Y_AXIS));
	   //
	   JPanel pane1=new JPanel();
	   JLabel IpLab=new JLabel("IP地址:");
	   IpText=new JTextField();
	   JLabel PortLab= new JLabel("端口:");
	   PortText=new JTextField();
	   pane1.setLayout(new BoxLayout(pane1,BoxLayout.X_AXIS));
	   pane1.add(Box.createHorizontalStrut(20));
	   pane1.add(IpLab);
	   pane1.add(Box.createHorizontalStrut(5));
	   pane1.add(IpText);
	   pane1.add(Box.createHorizontalStrut(5));
	   pane1.add(PortLab);
	   pane1.add(Box.createHorizontalStrut(5));
	   pane1.add(PortText);
	   pane1.add(Box.createHorizontalStrut(20));
	   //
	   JPanel pane2=new JPanel();
	   pane2.setLayout(new BoxLayout(pane2,BoxLayout.X_AXIS));
	   JButton bSure=new JButton("确定");
	   bSure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 setSucess();
				 SERVER_PORT=Integer.valueOf(PortText.getText());
				 SERVER_IP=IpText.getText();
				
			}
		});
	   JButton bNo = new JButton("取消");
	   bNo.addActionListener(new ActionListener(){
	   		public void actionPerformed(ActionEvent e){
	   			IpText.setText(null);
	   			PortText.setText(null);
	   		}
	   });
	   pane2.add(Box.createHorizontalGlue());
	   pane2.add(bSure);
	   pane2.add(Box.createHorizontalStrut(10));
	   pane2.add(bNo);
	   
	   termSet.add(Box.createVerticalStrut(10));
	   termSet.add(pane1);
	   termSet.add(Box.createVerticalStrut(10));
	   termSet.add(pane2);	   
	   return termSet;
   }
     
   private Component createSendPane(){
	   sendPane=new JPanel();
	   sendPane.setLayout(new BorderLayout());
	   JButton bSend=new JButton("发送");
	   sendPane.add(bSend,BorderLayout.CENTER);
	   bSend.addActionListener(new ActionListener(){
		   public void actionPerformed(ActionEvent e) {
			
				   new Client(OnlineSend.getIP(),OnlineSend.getPort(),folder_path);
				   dispose();
				   sendSucess();		
				
			}
	        
	   });
	   return sendPane;
   }

   private JComponent createMenu(){
	  final JMenu fileMenue = new JMenu("开始");
	  final JMenuItem  exit= new JMenuItem("退出");
	  exit.setHorizontalTextPosition(SwingConstants.CENTER);
	  exit.addActionListener(new ActionListener(){
		 public void actionPerformed(ActionEvent e){
			dispose();
		 }
	  });
	  fileMenue.add(exit);
	  final JMenu setMenu  = new JMenu("设置");
	  final JMenuItem defaultIP = new JMenuItem("默认");
	  defaultIP.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent e){
			  IpText.setText("192.168.1.1");
			  PortText.setText("5566");
			  	
			  
		  }
	  });
	  defaultIP.setHorizontalTextPosition(SwingConstants.CENTER);
	  setMenu.add(defaultIP);
	  final JMenu helpMenu  = new JMenu("帮助");
	  final JMenuItem aboutMenu = new JMenu("关于");
	  
	  aboutMenu.setHorizontalTextPosition(SwingConstants.CENTER);
	  aboutMenu.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent e){
			  appVersion();
		  }
	  });
	  
	  helpMenu.add(aboutMenu);
	  sendMenu=new JMenuBar();
	  sendMenu.setLayout(new BoxLayout(sendMenu,BoxLayout.X_AXIS));
	  sendMenu.add(Box.createHorizontalGlue());
	  sendMenu.add(fileMenue);
	  sendMenu.add(setMenu);
	  sendMenu.add(helpMenu);
	  return sendMenu;
   };
	
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
   public void setSucess(){
		JOptionPane.showMessageDialog(this, 
			    " 设置成功!", 
			    "配置",
			    JOptionPane.PLAIN_MESSAGE);
	}
   public void sendSucess(){
 		JOptionPane.showMessageDialog(this, 
 			    " 发送成功!", 
 			    "发送",
 			    JOptionPane.PLAIN_MESSAGE);
 	}
	public void appVersion(){
		 JOptionPane.showMessageDialog(this, 
				    "Version 0.3", 
				    "关于",
				    JOptionPane.PLAIN_MESSAGE);
	}

}