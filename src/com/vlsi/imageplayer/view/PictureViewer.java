package com.vlsi.imageplayer.view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
public class PictureViewer implements ActionListener{
		private JFrame frame;
		private MyCanvas mc ;
		private String fpath;
		private String fname;
		private File[] files;
		private int findex ;
		private FileDialog fd_load; 
		private MyFilter filter;
		private JButton previous ;
		private JButton next ;
		/*public static void main( String args[]) throws Exception {
				try
				{
					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
				}
				catch(Exception e)
				{
				}
				new PictureViewer().init();
		}*/
		public void init(){
				frame = new JFrame("PictureViewer");
			    InitJmenu();
				JPanel pb = new JPanel();
			//	JPanel image=new JPanel();
				JButton select = new JButton("选择图片");
				previous = new JButton("上一张");
				next = new JButton("下一张");
				select.addActionListener(this);
				previous.addActionListener(this);
				next.addActionListener(this);
				pb.add(select);
				pb.add(previous);
				pb.add(next); 
				mc = new MyCanvas();
				mc.setBackground(new Color(200,210,230));
				mc.addComponentListener(mc);
				frame.add(pb,"North");
				frame.add(mc,"Center");
				//frame.add(image,"Center");
				frame.setSize(760,760);
				frame.setLocation(400,200);
				frame.addWindowListener(new WindowAdapter(){
					public void windowClosing(WindowEvent e){
						System.exit(0); 
					} 
				});  
				frame.setVisible(true); 
				this.validateButton();
				filter = new MyFilter();
				fd_load = new FileDialog(frame,"打开文件",FileDialog.LOAD);
				fd_load.setFilenameFilter(filter);
 }
 
		public void actionPerformed(ActionEvent e){
				String command = e.getActionCommand();
				if(command.equals("选择图片")){
						fd_load.setVisible(true);
						fpath = fd_load.getDirectory();
						fname = fd_load.getFile();
						if((fpath != null) && (fname != null)){
							this.display(new File(fpath + fname)); 
							files = new File(fpath).listFiles(filter);
							this.setIndex();
						}   
					}else if(command.equals("上一张")){
						findex--;
						if(findex<0)
							findex = 0;
						this.display(files[findex]);
					}else if(command.equals("下一张")){
							findex++;
						if(findex >= files.length)
							findex = files.length-1;
						this.display(files[findex]);
					}
				this.validateButton();
		} 
		public void display(File f){
	 	try{
	 			BufferedImage bi = ImageIO.read(f);
	 			mc.setImage(bi);
	 			frame.setTitle("PictureViewer - [" +  f.getName() + "]");
	 	}catch(Exception e){
	 			e.printStackTrace(); 
	 	}
	 	mc.repaint();
 }
		public void setIndex(){
	 	File current = new File(fpath + fname); 
	 	if(files != null){
	 		for(int i=0;i<files.length;i++){
	 			if(current.equals(files[i])){
	 				findex = i; 
	 			} 
	 		}   
	 	}
 }
		public void validateButton(){
	 	previous.setEnabled((files!=null) && (findex > 0));
	 	next.setEnabled((files!=null) && (findex<(files.length-1))); 
}
 
 public void InitJmenu(){
		final JMenu file=new JMenu("File");
	
		final JMenuItem exit   =new JMenuItem("Exit");
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				 System.exit(0);
			}
		});
	
		file.add(exit);
    	
		final JMenu help=new JMenu("Help");
		final JMenuItem about= new JMenuItem("About");
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(frame, 
					    "@author:big_tang", 
					    "Author",
					    JOptionPane.PLAIN_MESSAGE);
			}
		});
	    help.add(about);
	    final JMenuBar bar=new JMenuBar();
	    bar.add(file);
	    bar.add(help);
	    frame.setJMenuBar(bar);
	}

}


