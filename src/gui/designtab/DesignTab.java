package gui.designtab;

import utils.FileChooserUtils;
import utils.pngIcon;
import gui.framework.Framework;
import gui.framework.LedFileChooser;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.io.*;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

public class DesignTab extends JPanel {
	
	private String Path=null;
	private Element [] Node=null;
	private File  [] FILE=null;
	private int []Type=null;
	private int []DrawRatio=null;
	private Color [] BgColor=null;
	private Duration [] Dur=null;
	private int []VideoRatio=null;
	private int []VideoTimes=null;
	private int []LineSpace=null;
	private int []SpaceAbove=null;
	private int []SpaceBelow=null;
	private SimpleAttributeSet Attribute[]=null;
	
	
	private JToolBar toolBar = null;
	private JSplitPane workArea = null;

	private SetPane setPane = null;

	private PreviewPane preview = null;
	private EditPane edit = null;

	private LedFileChooser fc = LedFileChooser.getFileChooser();

	public DesignTab() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(createToolBar());
		add(createWorkArea());
		add(createSetPane());

		// init edit pane
		edit.createNewAreaDialog();
	}

	public PreviewPane getPreview() {
		return preview;
	}

	public EditPane getEdit() {
		return edit;
	}

	public SetPane getSetPane() {
		return setPane;
	}

	private Component createSetPane() {
		setPane = new SetPane(this);
		// TODO sth about card layoout
		setPane.setMaximumSize(new Dimension(Framework.screensize.width, 200));
		// setPane.setMinimumSize(new Dimension(500, 150));
		// setPane.setPreferredSize(new Dimension(800,150));
		return setPane;
	}

	private Component createWorkArea() {
		preview = new PreviewPane(this);
		edit = new EditPane(this);
		workArea = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, preview, edit);

		// workArea.
		JPanel tmpPane = new JPanel(new GridLayout(1, 1));
		tmpPane.add(workArea);
		this.setComponentZOrder(tmpPane, 0);
		// TODO sth about splitpane
		return tmpPane;
	}

	private JComponent createToolBar() {
		toolBar = new JToolBar("LED Manager: 方案设计");
		
		
		
		JButton bSave = new JButton("保存");
		bSave.setIcon(pngIcon.create("image//design//0.png"));
		JButton bAdd = new JButton("添加");
		bAdd.setIcon(pngIcon.create("image//design//1.png"));
		JButton bRemove = new JButton("删除");
		bRemove.setIcon(pngIcon.create("image//design//2.png"));
		JButton bSetting = new JButton("设置窗口");
		bSetting.setIcon(pngIcon.create("image//design//3.png"));
		JButton bAddPic = new JButton("添加图片");
		bAddPic.setIcon(pngIcon.create("image//design//4.png"));
		JButton bAddText = new JButton("添加文字");
		bAddText.setIcon(pngIcon.create("image//design//5.png"));
		JButton bAddVideo = new JButton("添加视频");
		bAddVideo.setIcon(pngIcon.create("image//design//6.png"));	

		// save
		toolBar.add(Box.createHorizontalStrut(5));
		bSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				/**
				 * 选择保存路径
				 */
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int i= fileChooser.showOpenDialog(getContentPane());
				if(i==JFileChooser.APPROVE_OPTION){
				    File Dic = fileChooser.getSelectedFile();
				    Path=Dic.getPath();
				}
				/**
				 * .LED文件创建
				 */
				try {
					CreateLedFile(Path);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				/**
				 * 创建节点
				 */
				LinkedList<EditArea> list = edit.getEditAreaList();
				FILE=new File[list.size()];
				Type=new int[list.size()];
				DrawRatio=new int[list.size()];
				BgColor=new Color[list.size()];
				Dur=new Duration[list.size()];
				VideoRatio=new int[list.size()];
				VideoTimes=new int[list.size()];
				LineSpace=new  int[list.size()];
				SpaceAbove=new int[list.size()];
				Attribute=new SimpleAttributeSet[list.size()];
				
				for (int index = 0; index < list.size(); index++){
					EditAreaProperty property = list.get(index).getProperty();
					System.out.println("id:"+index);
					System.out.println(property.getType());
					System.out.println(property.getDrawRatio());
					System.out.println(property.getBgColor());
					System.out.println(property.getTxtAttrSet());
					System.out.println(property.getDuration());
					if(property.getType()==EditAreaProperty.IMAGE_EDIT){
						FILE[index]=property.getImgFile();
						Type[index]=property.getType();
						DrawRatio[index]=property.getDrawRatio();
						BgColor[index]=property.getBgColor();
						Dur[index]=property.getDuration();
					}else if(property.getType()==EditAreaProperty.VIDEO_EDIT){
						FILE[index]=property.getVideoFile();
						Type[index]=property.getType();
						VideoRatio[index]=property.getVideoRatio();
						VideoTimes[index]=property.getVideoTimes();
						Attribute[index]=property.getTxtAttrSet();
					}else if(property.getType()==EditAreaProperty.TEXT_EDIT){
						try {
							FILE[index]=CreateTxtFile(property.getTxt());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						Type[index]=property.getType();
						LineSpace[index]=property.getLineSpacePixel();
						SpaceAbove[index]=property.getSpaceAbovePixel();
//						SpaceBelow[index]=property.getSpaceBelowPixel();
						Attribute[index]=property.getTxtAttrSet();
						System.out.println(property.getTxt());
					}
					
				
				 /**
				  * 制作方案保存 
				  */
			
					try {
						File savefile=null;
						BufferedInputStream fis=null;
						if(property.getType()==EditAreaProperty.IMAGE_EDIT){
							savefile=new File(Path+"/"+index+".jpg");
							fis=new BufferedInputStream(new FileInputStream(property.getImgFile()));
						}else if(property.getType()==EditAreaProperty.VIDEO_EDIT){
							savefile=new File(Path+"/"+index+".mkv");
							fis=new BufferedInputStream(new FileInputStream(property.getVideoFile()));
						}else if(property.getType()==EditAreaProperty.TEXT_EDIT){
							savefile=new File(Path+"/"+index+".txt");
							try {
								fis=new BufferedInputStream(new FileInputStream(CreateTxtFile(property.getTxt())));
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						BufferedOutputStream fos=new BufferedOutputStream(new FileOutputStream(savefile));
						byte[] b=new byte[1024];
						try {
							while(fis.read(b)!=-1){
								fos.write(b);
								fos.flush();
							}
						} catch (IOException e2) {
							e2.printStackTrace();
						}
					} catch (FileNotFoundException e2) {
						e2.printStackTrace();
				    } 
				}
				/**
				 * 生成XML文件
				 */
				Document document = new Document();
				Element root = new Element("root");
				document.addContent(root);
				Element node =new Element("node").setText(String.valueOf(list.size()));
				root.addContent(node);
				Node=new Element[10];
				System.out.println("list size: >" + list.size());
				for (int index = 0; index < list.size(); index++){
					if(Type[index]==EditAreaProperty.IMAGE_EDIT){
						Node[index]=createImageNode(FILE[index],Type[index],DrawRatio[index],BgColor[index],Dur[index]);
						System.out.print("create image node sucessfully!");
					}else if(Type[index]==EditAreaProperty.VIDEO_EDIT){
						Node[index]=createVideoNode(FILE[index],Type[index],VideoRatio[index],VideoTimes[index]);
						System.out.print("create image node sucessfully!");
					}else if(Type[index]==EditAreaProperty.TEXT_EDIT){
						Node[index]=createTxtNode(FILE[index],Type[index],LineSpace[index],SpaceAbove[index],Attribute[index]);
						System.out.println("create txt node sucessfully!");
						
					}
					root.addContent(Node[index]);
				}
				XMLOutputter out=new XMLOutputter();
				Format format=Format.getPrettyFormat();
				format.setIndent("");
				out.setFormat(format);
				try {
					out.output(document, new FileOutputStream(Path+"/"+"LedConfig.xml"));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				/**
				 * 保存成功提示
				 */
				SaveSucess();
			
			}
			
		});
		toolBar.add(bSave);
		toolBar.add(Box.createHorizontalStrut(5));

		// add
		bAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				edit.insertNewArea();
			}

		});
		toolBar.add(bAdd);
		toolBar.add(Box.createHorizontalStrut(5));
		// remove
		bRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (DesignTab.this.getEdit().isEmpty()) {
					return;
				}
				int val = JOptionPane.showConfirmDialog(DesignTab.this,
						"删除选中编辑？", "删除确认", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (val == JOptionPane.OK_OPTION) {

					preview.removeCurrentPreview();
				}
			}

		});
		toolBar.add(bRemove);
		toolBar.add(Box.createHorizontalStrut(5));
		// setting
		bSetting.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				edit.setContantSzieDialog();
			}

		});
		toolBar.add(bSetting);
		toolBar.add(Box.createHorizontalStrut(5));

		// pic
		bAddPic.setActionCommand(FileChooserUtils.bAddPicActionCmd);
		bAddPic.addActionListener(new AddTingButtonListener());

		toolBar.add(bAddPic);
		toolBar.add(Box.createHorizontalStrut(5));

		// text
		bAddText.setActionCommand(FileChooserUtils.bAddTxtActionCmd);
		bAddText.addActionListener(new AddTingButtonListener());
		toolBar.add(bAddText);
		toolBar.add(Box.createHorizontalStrut(5));

		// video
		bAddVideo.setActionCommand(FileChooserUtils.bAddVidActionCmd);
		bAddVideo.addActionListener(new AddTingButtonListener());
		toolBar.add(bAddVideo);
		toolBar.add(Box.createHorizontalStrut(5));

		// reset
		// toolBar.add(bReset);
		// toolBar.add(Box.createHorizontalStrut(5));

		JPanel toolBarPane = new JPanel(new GridLayout(1, 1));
		toolBarPane.add(toolBar);
		toolBarPane
				.setMaximumSize(new Dimension(Framework.screensize.width, 10));

		return toolBarPane;
	}

	protected Component getContentPane() {
		// TODO Auto-generated method stub
		return null;
	}

	class AddTingButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if (!edit.isEmpty()) {
				if (b.getActionCommand() == FileChooserUtils.bAddTxtActionCmd) {
					int val = JOptionPane.showConfirmDialog(DesignTab.this,
							"选择文本文件？", "添加文本",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (val == JOptionPane.YES_OPTION) {
						fc.openFileChooser(DesignTab.this, b.getActionCommand());
					} else if (val == JOptionPane.NO_OPTION) {
						System.out.println("键入文本");
						edit.addNewText();
					}

				} else {
					fc.openFileChooser(DesignTab.this, b.getActionCommand());
				}

			}
		}
	}
	/**
	 * 创建 .led 文件
	 * @param PATH  文件保存路径
	 * @throws IOException
	 */
    
	public void CreateLedFile(String PATH) throws IOException{
		String filename = "Manager.led";
		File file = new File(PATH,filename);
		if(file.exists()){
			file.delete();
			System.out.println("删除成功");
		}
		else{
			file.createNewFile();
			System.out.println("创建成功");
		}
		String Text="This is the LED Directory,You can find it by it!";
		FileWriter out=new FileWriter(file);
		out.write(Text);
		out.close();
		
	}
	
	public File CreateTxtFile(String TEXT)throws IOException{
		String filename="1.txt";
		String Path="E:\\";
		File file= new File(Path,filename);
		if(file.exists()){
			file.delete();
			System.out.println("删除成功");
		}else{
			file.createNewFile();
			System.out.println("创建成功");
		}
		FileWriter out= new FileWriter(file);
		out.write(TEXT);
		out.close();
		return file;		
	}
	
	/**
	 * 创建图片节点
	 * @param FILE 图片文件
	 * @param Type 文件类型	
	 * @param DrawRatio 图片比例
	 * @param Bgcolor	背景颜色
	 * @param Dur		播放时间
	 * @return
	 */
	public static  Element createImageNode(File FILE,int Type,int DrawRatio,Color Bgcolor,Duration Dur){
		String type=String.valueOf(Type);
		String path=FILE.getPath();
		String ratio=String.valueOf(DrawRatio);
		String red=String.valueOf(Bgcolor.getRed());
		String blue=String.valueOf(Bgcolor.getBlue());
		String green=String.valueOf(Bgcolor.getGreen());
		String hour=String.valueOf(Dur.hour);
		String minute=String.valueOf(Dur.min);
		String second=String.valueOf(Dur.sec);
		Element node = new Element("node");
		node.setAttribute("mediatype",type).setAttribute("location", path).setAttribute("drawratio",ratio)
				.setAttribute("playtime",hour+":"+minute+":"+second)
					.setAttribute("bgcolor", "R="+red+",G="+green+",B"+blue).setText("node has attributes");
		return node;
	}
	
	/**
	 * 创建视频节点
	 * @param FILE  视频文件
	 * @param Type  文件类型
	 * @param VideoRatio 视频比例
	 * @return
	 */
	public static  Element createVideoNode(File FILE,int Type,int VideoRatio,int VideoTimes){
		String type=String.valueOf(Type);
		String path=FILE.getPath();
		String ratio=String.valueOf(VideoRatio);
		String times=String.valueOf(VideoTimes);
	
		Element node = new Element("node");
			node.setAttribute("mediatype",type).setAttribute("location", path).setAttribute("videoratio",ratio)
					.setAttribute("videotimes",times).setText("node has attributes");
		return node;	
}
	
	/**
	 * 创建文本节点
	 * @param FILE	文本文件
	 * @param Type	文件类型
	 * @param LineSpace	下划线 
	 * @param SpaceAbove 空格线	
	 * @param Attribute	文本属性
	 * @return
	 */
	public static Element createTxtNode(File FILE, int Type,int LineSpace,int SpaceAbove,SimpleAttributeSet Attribute){
		String type=String.valueOf(Type);
		String path=FILE.getPath();
		String linespace=String.valueOf(Type);
		String spaceabove=String.valueOf(SpaceAbove);
		Element node = new Element("node");
		String attribute=Attribute.toString();
			node.setAttribute("mediatype",type).setAttribute("location",path)
	            .setAttribute("linespace",linespace).setAttribute("spaceabove",spaceabove)
	            .setAttribute("attribute",attribute)
			.setText("node has attributes");
        return node;	
	}
	/**
	 * 保存成功
	 */
	public void SaveSucess(){
	 	JOptionPane.showMessageDialog(this, 
	 	" 保存成功!", 
	 	"保存",
	 	JOptionPane.PLAIN_MESSAGE);
	}	
}
