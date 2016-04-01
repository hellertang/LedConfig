package gui.designtab;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import utils.MyColor;
import utils.MyImage;

public class PreviewPane extends JScrollPane{
	
	private DesignTab thisTab = null;
	//use model
	private JList<ImageIcon> previewList = null;
	private DefaultListModel<ImageIcon> previewModel = null;
	
	
	public PreviewPane(DesignTab designTab){
		super();
		thisTab = designTab;
		createList();
		
		setViewportView(previewList);
		this.getVerticalScrollBar().setUnitIncrement(50);
		//debugPreview();
	}
	
	public void createNewPreview(ImageIcon img, EditContant panel, boolean heightBigger){
		previewModel.addElement(new ImageIcon());
		previewList.setSelectedIndex(previewModel.size()-1);
		loadThumb(panel, heightBigger);
	}
	
	public void insertNewPreview(ImageIcon img, EditContant panel, boolean heightBigger){
		int tmp = previewList.getSelectedIndex()+1;
		previewModel.add(tmp, new ImageIcon());
		previewList.setSelectedIndex(tmp);
		loadThumb(panel, heightBigger);
	}
	
	public void removeCurrentPreview(){
		
		int curIndex = previewList.getSelectedIndex();
		System.out.println("index :"+curIndex+" to del");
		
		
		previewModel.remove(curIndex);
		thisTab.getEdit().removeEditArea(curIndex);
		
		if( previewModel.isEmpty() ){
			thisTab.getEdit().createNewAreaDialog();
		}
		if(curIndex > previewModel.size()-1){
			curIndex = previewModel.size()-1;
		}
		previewList.setSelectedIndex(curIndex);
		//删除当前选中的list项 会导致setSelectedIndex(-1)
	}
	
	public void loadThumb(EditContant panel, boolean heightBigger){
		int index = previewList.getSelectedIndex();
		if(index < 0){
			return;
		}
//		BufferedImage image = MyImage.createImage(panel);
		BufferedImage image = panel.getThumb();
		
		if(heightBigger){
			previewModel.set(index, new ImageIcon(MyImage.resize(image, -1, 130)));
		}else{
			previewModel.set(index, new ImageIcon(MyImage.resize(image, 130, -1)));
		}
	}
	
	public void reloadAllThumb(LinkedList<EditArea> list, boolean heightBigger){
		EditContant e = null;
		BufferedImage image = null;
		if(heightBigger){
			for(int i = 0; i < list.size(); i++){
				e = list.get(i).getEditContant();
				e.refresh();
				image = e.getThumb();
				previewModel.set(i, new ImageIcon(MyImage.resize(image,-1, 130)));
			}
		}else{
			for(int i = 0; i < list.size(); i++){
				e = list.get(i).getEditContant();
				e.refresh();
				image = e.getThumb();
				previewModel.set(i, new ImageIcon(MyImage.resize(image,130, -1)));
			}
		}
		
	}
	
	
	private void createList(){
		previewModel = new DefaultListModel<ImageIcon>();
		previewList  = new JList<ImageIcon>(previewModel);

		previewList.setVisibleRowCount(2);
		previewList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		previewList.setBorder(BorderFactory.createTitledBorder("预览"));
		
		PreviewCellRenderer renderer = new PreviewCellRenderer();
		renderer.setPreferredSize(new Dimension(250,200));
		renderer.setSize(250, 200);
		
		previewList.setCellRenderer(renderer);
		previewList.setFixedCellHeight(150);
		previewList.addListSelectionListener(new PreviewSelListener());
		previewList.setBackground(MyColor.GRAY_BACKGROUND);
		
		previewList.setDragEnabled(true);
	}
	

	
	class PreviewCellRenderer extends JLabel 
							  implements ListCellRenderer<ImageIcon>{

		
		public PreviewCellRenderer(){
			setHorizontalAlignment(JLabel.CENTER);
			setVerticalAlignment(JLabel.CENTER);
			setHorizontalTextPosition(JLabel.LEFT);
			setVerticalTextPosition(JLabel.TOP);
		}
		@Override
		public Component getListCellRendererComponent(
				JList<? extends ImageIcon> list, ImageIcon value, int index,
				boolean isSelected, boolean cellHasFocus) {
			
			if(isSelected){
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			}else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}
			ImageIcon pic = list.getModel().getElementAt(index);
			if(pic == null){
				pic = new ImageIcon("d:/null_pic.png");
			}
			setIcon(pic);
			setText(new Integer(index+1).toString());
			setEnabled(list.isEnabled());
			setIconTextGap(15);
			setOpaque(true);
			return this;
		}

		
	}
	
	class PreviewSelListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(e.getValueIsAdjusting()){
				//TODO 修改模型时 这边对应的反应
			}else{
				//TODO 选中时 对应的反应
				System.out.println("user choose preview "+previewList.getSelectedIndex());
				if(previewList.getSelectedIndex()==-1){
					return;
				}
				thisTab.getEdit().showArea(previewList.getSelectedIndex());
			}
		}
		
	}
}