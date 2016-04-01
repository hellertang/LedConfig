package com.vlsi.imageplayer.main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import com.vlsi.imageplayer.model.*;
import com.vlsi.imageplayer.view.PictureViewer;

public class ImageProcessor extends JFrame {
			final JPanel South  =new JPanel();
			final JPanel Center=new JPanel();
			final JPanel North =new JPanel();
			final JPanel p1 = new JPanel();
			final JPanel p2 = new JPanel();
			final JSplitPane vSplitPane1=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			final JSplitPane vSplitPane2=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
			final JTextField textimage=new JTextField();
			final JTextField textheight=new JTextField();
			final JTextField textwidth= new  JTextField();
			final JTextField textratio  = new JTextField();
			final JTextField textscript=new JTextField();
			final JTextField startx=new JTextField();
			final JTextField starty=new JTextField();
			final JTextField endx =new JTextField();
			final JTextField endy =new JTextField();
			Font font=new Font("宋体",Font.ITALIC,12);
			
			String filename;
			String filePath;
			int height;
			int width;
			int ratio;
			String script;
			int x0;
			int y0;
			int x1;
			int y1;
			public ImageProcessor(){
				  Initialize();
				  this.setVisible(true);
			}
			public void Initialize(){			 
				  this.setTitle("ImageProcessor");
				  this.setSize(800,700);
				  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				  InitJmenu();
				  InitJButton();
				  InitJTextField();
				  InitJContentPane();
				  updateJLocation();
			}
//-------------------------------------------------------------------文本框初始化------------------------------------------------------------------			
	        public  void InitJTextField(){
	        	   final JLabel labelimage=new JLabel();
	        	   labelimage.setText("图片:");
	        	   labelimage.setFont(font);
	        	   North.add(labelimage);
	        	   textimage.setColumns(10);
	        	   North.add(textimage);
	        	   final JLabel labelheight=new JLabel();
	        	   labelheight.setText("高:");
	        	   labelheight.setFont(font);
	        	   p1.add(labelheight);
	        	   textheight.setColumns(5);
	        	   p1.add(textheight);
	        	   final JLabel labelwidth=new JLabel();
	        	   labelwidth.setText( "宽:");
	        	   labelwidth.setFont(font);
	        	   p1.add(labelwidth);
	        	   textwidth.setColumns(5);
	        	   p1.add(textwidth);
	        	   final JLabel labelratio=new JLabel();
	        	   labelratio.setText("比例:");
	        	   labelratio.setFont(font);
	        	   p1.add(labelratio);
	        	   textratio.setColumns(5);
	        	  p1.add(textratio);
	        	  final JLabel labelscript=new JLabel();
	        	  labelscript.setText("文本:");
	        	  labelscript.setFont(font);
	        	  p1.add(labelscript);
	        	  textscript.setColumns(20);
	        	  p1.add(textscript);
	        	  final JLabel labelsx=new JLabel();
	        	  labelsx.setText("起始  x:");
	        	  labelsx.setFont(font);
	        	  p2.add(labelsx);
	        	  startx.setColumns(5);
	        	  p2.add(startx);
	        	  final JLabel labelsy=new JLabel();
	        	  labelsy.setText("y:");
	              labelsy.setFont(font);
	        	  p2.add(labelsy);
	        	  starty.setColumns(5);
		          p2.add(starty);
		          final JLabel labelex=new JLabel();
	        	  labelex.setText("结束  x:");
	        	  labelex.setFont(font);
	        	  p2.add(labelex);
	        	  endx.setColumns(5);
	        	  p2.add(endx);
	        	  final JLabel labeley=new JLabel();
	        	  labeley.setText("y:");
	              labeley.setFont(font);
	        	  p2.add(labeley);
	        	  endy.setColumns(5);
		          p2.add(endy);
	        	  
	        	  
	        }
//------------------------------------------------------------------- 按钮初始化-----------------------------------------------------
     	public void InitJButton(){
     		JButton set =new JButton("设置");
     		 set.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green)); 
     			set.addActionListener(new ActionListener(){
     				public void actionPerformed(ActionEvent e){
     					 String ih=textheight.getText().trim();
     		        	   height=Integer.parseInt(ih);	
     		        	  String iw=textwidth.getText().trim();
     		        	   width=Integer.parseInt(iw);
     		        	  String ir=textratio.getText().trim();
     		        	   ratio=Integer.parseInt(ir);
     		        	   script=textscript.getText().trim();
     		        	   String ix0=startx.getText().trim();
     		        	   x0=Integer.parseInt(ix0);
     		        	   String iy0=starty.getText().trim();
     		        	   y0=Integer.parseInt(iy0);
     		        	   String ix1=endx.getText().trim();
     		        	   x1=Integer.parseInt(ix1);
     		        	   String iy1=endy.getText().trim();
     		        	   y1=Integer.parseInt(iy1);
     				       System.out.println(height+"\n"+width+"\n"+ratio+"\n"+script+"\n"+x0+"\n"+y0+"\n"+x1+"\n"+y1);
     		        	   appSucess();
     				}
     			});
     			
     			JButton scale=new JButton("比例放大");
     			 scale.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red)); 
			    scale.addActionListener(new ActionListener(){
			    	public void actionPerformed(ActionEvent e){
			    		ImageUtils.scale(filePath, "E://abc_scale.jpg",ratio, true);
			    		appSucess();
			    	}
			    });
				JButton scale2=new JButton("指定放大");
				 scale2.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.blue)); 
				 scale2.addActionListener(new ActionListener(){
				    public void actionPerformed(ActionEvent e){
				    	ImageUtils.scale2(filePath, "E://abc_scale2.jpg", height,width, true);
				    	appSucess();
				    	}
				    });
				 JButton cut=new JButton("切割");
				 cut.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue)); 
				 cut.addActionListener(new ActionListener(){
				    public void actionPerformed(ActionEvent e){
				    	ImageUtils.cut(filePath, "e:/abc_cut.jpg", x0, y0, x1, y1 );
				    	appSucess();
				    	}
				    });
				 JButton convert=new JButton("类型转换");
				 convert.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));  
				 convert.addActionListener(new ActionListener(){
				    public void actionPerformed(ActionEvent e){
				    	ImageUtils.convert(filePath, "GIF", "e:/abc_convert.gif");
				    	appSucess();
				    	}
				    });
				 JButton gray=new JButton("黑白转换");
				gray.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red)); 
				gray.addActionListener(new ActionListener(){
				    public void actionPerformed(ActionEvent e){
				    	ImageUtils.gray(filePath, "e:/abc_gray.jpg");
				    	appSucess();
				    	}
				    });
				JButton pressText=new JButton("水印文字");
				 pressText.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.lightBlue)); 
				pressText.addActionListener(new ActionListener(){
				    public void actionPerformed(ActionEvent e){
				    	ImageUtils.pressText(script,"e:/abc.jpg","e:/abc_pressText.jpg","宋体",Font.BOLD,Color.white,80, 0, 0, 0.5f);
				    	appSucess();
				    	}
				    });
				North.add(set);
			    South.add(scale);
			    South.add(scale2);
			    South.add(cut);
			    South.add(convert);
			    South.add(gray);
			    South.add(pressText);
			}
  //------------------------------------------------------------------菜单初始化--------------------------------------------------------------------------------
			public void InitJmenu(){
				final JMenu file=new JMenu("File");
				final JMenuItem open=new JMenuItem("Open local Image.....");
				open.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						appChoose();
					}
     	});
				final JMenuItem exit   =new JMenuItem("Exit");
				exit.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
							appExit();
					}
				});
				file.add(open);
				file.add(exit);
		    	final JMenu view=new JMenu("View");
		    	final JMenuItem imageview=new JMenuItem("Viewimage");
		    	imageview.addActionListener(new ActionListener(){
		    		public  void actionPerformed(ActionEvent e){
		    			new PictureViewer().init();
		    		}
		    	});
		       view.add(imageview);
				final JMenu help=new JMenu("Help");
				final JMenuItem about= new JMenuItem("About");
				about.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
							appAuthor();
					}
				});
			    help.add(about);
			    final JMenuBar bar=new JMenuBar();
			    bar.add(file);
			    bar.add(view);
			    bar.add(help);
			    this.setJMenuBar(bar);
			}
//---------------------------------------------------------------------界面排版------------------------------------------------------------------			
			public void InitJContentPane(){
				 South.setLayout(new FlowLayout());
				 Center.setLayout(new BorderLayout());
				 North.setLayout(new FlowLayout());
				 p1.setLayout(new FlowLayout());
				 vSplitPane1.setLeftComponent(North);
				 vSplitPane1.setRightComponent(vSplitPane2);
				 vSplitPane2.setLeftComponent(p1);
				 vSplitPane2.setRightComponent(p2);
				 this.getContentPane().add(Center, BorderLayout.CENTER);
				 this.getContentPane().add(South, BorderLayout.SOUTH);
				 //this.getContentPane().add(North,BorderLayout.NORTH);
				 //this.getContentPane().add(West,BorderLayout.NORTH);
				 this.getContentPane().add(vSplitPane1,BorderLayout.NORTH);
				 SetBackground();
			}
//------------------------------------------------------------------设置APP背景-----------------------------------------------------------------
			public void SetBackground(){
				 final String picture="12.jpg";
				 final Icon icon=new ImageIcon(picture);
				 final JLabel label=new JLabel (icon);
				 Center.add(label,BorderLayout.CENTER);
			}
//-------------------------------------------------------使APP显示在屏幕中间位置-------------------------------------------------------------
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
//----------------------------------------------------------------------按钮动作----------------------------------------------------------------------
			public void appChoose(){
				final JFileChooser fileChooser=new JFileChooser();
				int i = fileChooser.showOpenDialog(getContentPane());
				if (i == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					textimage.setText(selectedFile.getName());
					filename=selectedFile.getName();
					filePath = "E:\\"+filename;
				}
			}
			public void appExit(){
				 System.exit(0);
			}
			public void appAuthor(){
				 JOptionPane.showMessageDialog(this, 
						    "@author:big_tang", 
						    "Author",
						    JOptionPane.PLAIN_MESSAGE);
			}
			public void appSucess(){
				JOptionPane.showMessageDialog(this, 
					    " Success!!!!!", 
					    "ImageProcessor",
					    JOptionPane.PLAIN_MESSAGE);
			}
//-----------------------------------------------------------------------主函数-------------------------------------------------------------------------------
			
			public static void main(String args[]){
				try
			    {
					UIManager.put("RootPane.setupButtonVisible", false);
					BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle. generalNoTranslucencyShadow ;
			        org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			    }
			    catch(Exception e)
			    {
			        //TODO exception
			    }
				new ImageProcessor();
			}
}
