package gui.designtab;

import gui.framework.Framework;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import utils.MyColor;
import utils.MyImage;
import utils.PlayerPanel;

public class EditPane extends JScrollPane {

	public static DesignTab thisTab = null;
	private JPanel basePane = null;// as the view of scroll pane
	private CardLayout layout = null;
	private LinkedList<EditArea> areaList = null;
	private int currentIndex = 0;

	public EditPane(DesignTab designTab) {
		super();
		thisTab = designTab;
		basePane = new JPanel();
		areaList = new LinkedList<EditArea>();
		layout = new CardLayout();
		basePane.setLayout(layout);
		basePane.setBackground(MyColor.GRAY_BACKGROUND);
		setViewportView(basePane);
	}

	public static void releasePlayer() {
		EditContant.release();
	}

	public void showArea(String name) {
		layout.show(basePane, name);

	}
	
	public  LinkedList<EditArea> getEditAreaList(){
		return this.areaList;
	}

	public void showArea(int index) {
		if (currentIndex != index) {
			EditArea old = areaList.get(currentIndex);
			if (old.getType() == EditAreaProperty.VIDEO_EDIT) {
				old.getEditContant().leaveVideoEdit();
			}
			System.out.println("last show " + currentIndex);
			currentIndex = index;
			EditArea e = areaList.get(currentIndex);
			showArea(e.toString());
			e.getEditContant().refresh();
			changeSettingPane(e);
			if (e.getType() == EditAreaProperty.VIDEO_EDIT) {
				e.getEditContant().enterVideoEdit();
			}
			System.out.println("edit area show " + index);
		}
	}

	public void refreshCurrentEditContant() {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.getEditContant().refresh();
			thisTab.getPreview().loadThumb(e.getEditContant(),
					(EditContant.imgHeight > EditContant.imgWidth));
		}
	}

	private void refreshCurrentEditContant(EditArea e) {
		e.getEditContant().refresh();
		thisTab.getPreview().loadThumb(e.getEditContant(),
				(EditContant.imgHeight > EditContant.imgWidth));
	}

	public void setCurrentEditDrawRatio(int ratio) {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.setDrawRatio(ratio);
			refreshCurrentEditContant(e);
		}
	}
	
	public void setCurrentVideoShowName(boolean f){
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.getEditContant().showVideoName(f);
			refreshCurrentEditContant(e);
		}
	}

	public void setCurrentVideoRatio(int ratio) {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.setVideoRatio(ratio);
			refreshCurrentEditContant(e);
		}
	}

	public void setCurrentVideoTimes(int times) {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.setVideoTimes(times);
			refreshCurrentEditContant(e);
		}
	}

	public void setCurrentEditDuration(int type, int value) {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.setDuration(type, value);
		}

	}

	public void setCurrentEditBgColor(Color c) {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			if (e.getType() == EditAreaProperty.IMAGE_EDIT) {
				e.setContantBgColor(c);
			} else if (e.getType() == EditAreaProperty.TEXT_EDIT) {
				e.setTextBgColor(c);
			} else {
				// do nothing if video or default
			}
			refreshCurrentEditContant(e);
		}
	}

	public void setCurrentEditFontSize(int size) {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.setFontSize(size);
			refreshCurrentEditContant(e);
		}
	}

	public void setCurrentEditFontFamily(String font) {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.setFontFamily(font);
			refreshCurrentEditContant(e);
		}
	}

	public void setCurrentEditFontItalic(boolean isItalic) {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.setItalic(isItalic);
			refreshCurrentEditContant(e);
		}
	}

	public void setCurrentEditFontBold(boolean isBold) {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.setBold(isBold);
			refreshCurrentEditContant(e);
		}
	}

	public void setCurrentEditFontUnderline(boolean isUnderline) {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.setUnderline(isUnderline);
			refreshCurrentEditContant(e);
		}
	}

	public void setCurrentEditFontColor(Color c) {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.setFontColor(c);
			refreshCurrentEditContant(e);
		}
	}

	public void setCurrentTxtAligment(int x) {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.setTextAlignment(x);
			refreshCurrentEditContant(e);
		}
	}

	public void setCurrentTxtLineSpace(int x) {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.setTextLineSpace(x);
			refreshCurrentEditContant(e);
		}
	}

	public void setCurrentTxtSpaceAbove(int x) {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.setTextSpaceAbove(x);
			refreshCurrentEditContant(e);
		}
	}

	public void setCurrentTxtSpaceBelow(int x) {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.setTextSpaceBelow(x);
			refreshCurrentEditContant(e);
		}
	}

	public void setCurrentVideoPlay(boolean play) {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.setVideoPlay(play);
			refreshCurrentEditContant(e);
		}
	}

	public void setCurrentVideoPause() {
		if (!areaList.isEmpty()) {
			EditArea e = areaList.get(currentIndex);
			e.setVideoPause();
			refreshCurrentEditContant(e);
		}
	}

	private void changeSettingPane(EditArea e) {
		SetPane s = thisTab.getSetPane();
		int type = e.getType();
		switch (type) {
		case EditAreaProperty.DEFAULT_EDIT:
			s.changeToDefaultSetting();
			break;
		case EditAreaProperty.IMAGE_EDIT:
			s.changeToImageSetting(e.getProperty());
			break;
		case EditAreaProperty.VIDEO_EDIT:
			s.changeToVideoSetting(e.getProperty());
			break;
		case EditAreaProperty.TEXT_EDIT:
			s.changeToTextSetting(e.getProperty());
			break;
		default:
			break;
		}
	}

	private void leaveCurrentVideo() {
		if (!areaList.isEmpty()) {
			EditArea old = areaList.get(currentIndex);
			if (old.getType() == EditAreaProperty.VIDEO_EDIT) {
				old.getEditContant().leaveVideoEdit();
			}
		}
	}

	private void addNewEditArea(EditArea e) {
		leaveCurrentVideo();
		basePane.add(e, e.toString());
		areaList.add(e);
		currentIndex = areaList.size() - 1;
	}

	private void addNewEditArea(EditArea e, int index) {
		leaveCurrentVideo();
		basePane.add(e, e.toString());
		areaList.add(index, e);
		currentIndex = index;
	}

	public void removeEditArea(int index) {
		EditArea e = areaList.get(index);
		if (e.getType() == EditAreaProperty.VIDEO_EDIT) {
			e.getEditContant().leaveVideoEdit();
		}

		basePane.remove(e);
		areaList.remove(index);

	}

	public void createNewAreaDialog() {
		EditArea e = new EditArea();
		addNewEditArea(e);
		layout.last(basePane);
		// TODO add to preview
		thisTab.getPreview().createNewPreview(null, e.getEditContant(),
				EditContant.imgHeight > EditContant.imgWidth);
		thisTab.getSetPane().changeToDefaultSetting();
	}

	public void insertNewArea() {
		EditArea e = new EditArea();
		addNewEditArea(e, currentIndex + 1);
		showArea(e.toString());
		thisTab.getPreview().insertNewPreview(null, e.getEditContant(),
				EditContant.imgHeight > EditContant.imgWidth);
		thisTab.getSetPane().changeToDefaultSetting();
	}

	public void setContantSzieDialog() {
		Box base = Box.createVerticalBox();
		Box widthBox = Box.createHorizontalBox();
		Box heightBox = Box.createHorizontalBox();

		SpinnerModel widthModel = new SpinnerNumberModel(EditContant.imgWidth,
				EditContant.MIN_WIDTH, EditContant.MAX_WIDTH, 1);
		SpinnerModel heightModel = new SpinnerNumberModel(
				EditContant.imgHeight, EditContant.MIN_HEIGHT,
				EditContant.MAX_HEIGTH, 1);
		JSpinner widthSpinner = new JSpinner(widthModel);
		JSpinner heightSpinner = new JSpinner(heightModel);
		JLabel widthLabel = new JLabel("宽度(W):");
		JLabel heightLabel = new JLabel("高度(H):");

		widthBox.add(Box.createHorizontalGlue());
		widthBox.add(widthLabel);
		widthBox.add(Box.createHorizontalStrut(20));
		widthBox.add(widthSpinner);
		widthBox.add(Box.createHorizontalGlue());

		heightBox.add(Box.createHorizontalGlue());
		heightBox.add(heightLabel);
		heightBox.add(Box.createHorizontalStrut(20));
		heightBox.add(heightSpinner);
		heightBox.add(Box.createHorizontalGlue());

		base.add(Box.createVerticalGlue());
		base.add(widthBox);
		base.add(Box.createVerticalStrut(25));
		base.add(heightBox);
		base.add(Box.createVerticalGlue());
		base.setBorder(BorderFactory.createTitledBorder("窗口大小"));

		int val = JOptionPane.showConfirmDialog(thisTab, base, "设置播放窗口",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (val == JOptionPane.OK_OPTION) {
			if ((Integer) widthSpinner.getValue() != EditContant.imgWidth
					|| (Integer) heightSpinner.getValue() != EditContant.imgHeight) {

				EditContant.setWH((Integer) widthSpinner.getValue(),
						(Integer) heightSpinner.getValue());

				if (!isEmpty()) {
					EditArea tmp = areaList.get(currentIndex);
					tmp.getEditContant().refresh();

					thisTab.getPreview().reloadAllThumb(areaList,
							EditContant.imgHeight > EditContant.imgWidth);
				}
			}
			System.out.println("size set");
		} else {
			System.out.println("size cancel");
		}

	}

	public boolean isEmpty() {
		return areaList.isEmpty();
	}

	// add image, video, txt
	public void addNewImage(File file) {
		EditArea tmp = areaList.get(currentIndex);
		tmp.setFile(file, EditAreaProperty.IMAGE_EDIT);

		thisTab.getPreview().loadThumb(tmp.getEditContant(),
				EditContant.imgHeight > EditContant.imgWidth);
		// thisTab.getSetPane().changeToImageSetting(tmp.getDr);
		changeSettingPane(tmp);

	}

	public void addNewVideo(File file) {
		EditArea tmp = areaList.get(currentIndex);
		tmp.setFile(file, EditAreaProperty.VIDEO_EDIT);
		thisTab.getPreview().loadThumb(tmp.getEditContant(),
				EditContant.imgHeight > EditContant.imgWidth);
		// thisTab.getSetPane().changeToVideoSetting();
		changeSettingPane(tmp);
	}

	public void addNewText(File file) {
		EditArea tmp = areaList.get(currentIndex);
		tmp.setType(EditAreaProperty.TEXT_EDIT);
		thisTab.getPreview().loadThumb(tmp.getEditContant(),
				EditContant.imgHeight > EditContant.imgWidth);
		// thisTab.getSetPane().changeToTextSetting();
		changeSettingPane(tmp);
	}

	public void addNewText() {
		EditArea tmp = areaList.get(currentIndex);
		tmp.setType(EditAreaProperty.TEXT_EDIT);
		thisTab.getPreview().loadThumb(tmp.getEditContant(),
				EditContant.imgHeight > EditContant.imgWidth);
		// thisTab.getSetPane().changeToTextSetting();
		changeSettingPane(tmp);
	}

}

// TODO change to border layout and box or null layout
class EditArea extends JPanel {

	public static final String NAME = "EDIT_AREA_";

	private EditContant editContant = null;

	private int width = 160;
	private int height = 100;

	// count
	private static long count = 0;
	// property
	private EditAreaProperty property = null;

	private static DesignTab thisTab = null;

	public EditArea() {
		super();

		property = new EditAreaProperty(count);
		count++;
		System.out.println("index: " + property.getId() + " ,count: " + count);
		System.out.println(this.toString());

		createEditContant();

		setLayout(new GridBagLayout());
		setBackground(MyColor.GRAY_BACKGROUND);
		add(editContant, EditAreaConstraints.get());

	}

	public void setTextBgColor(Color c) {
		this.property.setBgColor(c);
		this.getEditContant().setTextAreaBgColor(c);
	}

	public void setType(int type) {
		this.property.setType(type);
		refresh();
	}

	public void setDrawRatio(int ratio) {
		this.property.setDrawRatio(ratio);
	}

	public void setVideoRatio(int ratio) {
		this.property.setVideoRatio(ratio);
		this.editContant.setVideoRatio(ratio);
	}

	public void setVideoTimes(int times) {
		this.property.setVideoTimes(times);
	}

	public void setContantBgColor(Color c) {
		this.property.setBgColor(c);
		this.editContant.setBackground(c);
	}

	public void setFontSize(int size) {
		this.property.setFontSize(size);
		this.editContant.setCharAttr();
	}

	public void setFontFamily(String font) {
		this.property.setFontFamily(font);
		this.editContant.setCharAttr();
	}

	public void setItalic(boolean isItalic) {
		this.property.setItalic(isItalic);
		this.editContant.setCharAttr();
	}

	public void setBold(boolean isBold) {
		this.property.setBold(isBold);
		this.editContant.setCharAttr();
	}

	public void setUnderline(boolean isUnderline) {
		this.property.setUnderline(isUnderline);
		this.editContant.setCharAttr();
	}

	public void setFontColor(Color c) {
		this.property.setFontColor(c);
		this.editContant.setCharAttr();
	}

	public void setTextAlignment(int x) {
		this.property.setTextAlignment(x);
		this.editContant.setCharAttr();
	}

	public void setTextLineSpace(int x) {

		this.property.setTextLineSpace(x);
		this.editContant.setCharAttr();
	}

	public void setTextSpaceAbove(int i) {
		this.property.setTextSpaceAbove(i);
		this.editContant.setCharAttr();
	}

	public void setTextSpaceBelow(int i) {
		this.property.setTextSpaceBelow(i);
		this.editContant.setCharAttr();
	}

	public void setDuration(int type, int value) {
		switch (type) {
		case Duration.HOUR_TYPE:
			property.setDurationHour(value);
			break;
		case Duration.MIN_TYPE:
			property.setDurationMin(value);
			break;
			
		case Duration.SEC_TYPE:
			property.setDurationSec(value);
			break;
		}
	}

	public void setVideoPause() {
		this.editContant.pauseVideo();
	}

	public void setVideoPlay(boolean play) {
		if (play) {
			this.editContant.playVideo();
		} else {
			this.editContant.stopVideo();
		}
	}

	public int getType() {
		return this.property.getType();
	}

	public EditContant getEditContant() {
		return this.editContant;
	}

	public int getDrawRatio() {
		return this.property.getDrawRatio();
	}

	public EditAreaProperty getProperty() {
		return property;
	}

	// TODO
	public void setFile(File file, int type) {

		this.property.setType(type);
		refresh();
		switch (type) {
		case EditAreaProperty.DEFAULT_EDIT:
			break;
		case EditAreaProperty.IMAGE_EDIT:
			this.property.setImgFile(file);
			this.editContant.setImage(new MyImage(file).getAsBufferedImage());
			break;
		case EditAreaProperty.VIDEO_EDIT:
			this.property.setVideoFile(file);
			this.editContant.setVideo(file);
			break;
		case EditAreaProperty.TEXT_EDIT:
			break;
		}
	}

	private void createEditContant() {
		editContant = new EditContant(this.property);

		editContant.setBackground(Color.white);
		editContant.setBorder(BorderFactory.createEmptyBorder());

		// DEBUG
		// editContant.setBorder(BorderFactory.createTitledBorder(this.toString()));
		System.out.println(">>>>>>>>>>" + this.toString());
	}

	public void refresh() {
		revalidate();
		editContant.refresh();
	}

	public String toString() {
		return new String(NAME + property.getId());
	}

}

class EditContant extends JPanel {

	public static final int DEFAULT_WIDTH = 384;
	public static final int DEFAULT_HEIGHT = 256;
	public static final int MIN_WIDTH = 16;
	public static final int MIN_HEIGHT = 16;
	public static final int MAX_WIDTH = 1920;
	public static final int MAX_HEIGTH = 1080;

	private BufferedImage image = null;
	private JTextPane textArea = null;
	// private static EmbeddedMediaPlayerComponent player = new
	// EmbeddedMediaPlayerComponent();
	private StyledDocument doc = null;

	// video stuff
	private static PlayerPanel playerPanel = new PlayerPanel();
	private static DirectMediaPlayerComponent player = playerPanel.getPlayer();

	// TODO add video && txt
	private int imgX = 0;
	private int imgY = 0;
	private int currentType = EditAreaProperty.DEFAULT_EDIT;

	public static int imgWidth = DEFAULT_WIDTH;
	public static int imgHeight = DEFAULT_HEIGHT;

	// property
	private EditAreaProperty property = null;

	public EditContant(EditAreaProperty property) {
		super();
		this.property = property;
		Dimension size = new Dimension(imgWidth, imgHeight);
		this.setPreferredSize(size);
		this.setSize(size);// without this setting, MyImage.createImage() will
							// not work
		this.currentType = property.getType();
		this.setLayout(new GridLayout(1, 1));
		initTextArea();
	}

	private void initTextArea() {
		textArea = this.property.getTextPane();
		textArea.setEditable(true);
		textArea.setBackground(Color.WHITE);
		// textArea.setContentType("text/html; charset=gb2312");

		doc = textArea.getStyledDocument();
		doc.addDocumentListener(new TextAreaThumbLoadListener());
		setCharAttr();
	}

	public void setVideoRatio(int ratio) {
		playerPanel.setRatio(ratio);
		playerPanel.refresh();
	}

	public static void release() {
		player.release();
	}

	public void setVideo(File file) {
		// player.getMediaPlayer().playMedia(file.getAbsolutePath());
		setBackground(Color.BLACK);
		playerPanel.prepareMedia(file);
	}

	public void showVideoName(boolean f){
		playerPanel.showName(f);
	}
	
	public void playVideo() {
		player.getMediaPlayer().play();
	}

	public void stopVideo() {
		player.getMediaPlayer().stop();
		System.out.println("stop the video");
	}

	public void pauseVideo() {
		player.getMediaPlayer().pause();
	}

	public void leaveVideoEdit() {
		stopVideo();
		this.remove(playerPanel);
	}

	public void enterVideoEdit() {
		this.add(playerPanel);
		if (property.getVideoFile() != null) {
			setBackground(Color.black);
			playerPanel.prepareMedia(property.getVideoFile());
		}
	}

	public BufferedImage getThumb() {
		if (property.getType() == EditAreaProperty.VIDEO_EDIT) {
			// if(player.getMediaPlayer().isPlaying()) {
			// playVideo();
			// BufferedImage img = player.getMediaPlayer()
			// .getVideoSurfaceContents();
			// return img == null ? new
			// MyImage("d:/video.jpg").getAsBufferedImage() : img;
			// }else{
			return new MyImage("d:/video.png").getAsBufferedImage();
			// }
		} else {
			return MyImage.createImage(this);
		}
	}

	public void setCharAttr() {
		doc.setCharacterAttributes(0, doc.getLength(),
				property.getTxtAttrSet(), true);
		doc.setParagraphAttributes(0, doc.getLength(),
				property.getTxtAttrSet(), true);
		textArea.setCharacterAttributes(property.getTxtAttrSet(), true);
		textArea.setParagraphAttributes(property.getTxtAttrSet(), true);
	}

	public void setTextAreaBgColor(Color c) {
		textArea.setBackground(c);
	}

	public void setImage(BufferedImage image) {
		this.image = image;

	}

	public void setXY(int x, int y) {
		this.imgX = x;
		this.imgY = y;
	}

	public static void setWH(int width, int height) {
		imgWidth = width;
		imgHeight = height;
		playerPanel.setSize(width, height);
		playerPanel.refresh();
		System.out.println("set w:" + width + ", h:" + height);
	}

	public void refresh() {

		setSize(imgWidth, imgHeight);// without this setting,
										// MyImage.createImage() will not work

		if (property.getType() == EditAreaProperty.TEXT_EDIT) {
			textArea.setSize(imgWidth, imgHeight);
		}

		setPreferredSize(new Dimension(imgWidth, imgHeight));

		if (currentType != property.getType()) {
			switch (currentType) {
			case EditAreaProperty.DEFAULT_EDIT:
				break;
			case EditAreaProperty.IMAGE_EDIT:
				image = null;
			case EditAreaProperty.VIDEO_EDIT:
				leaveVideoEdit();
				break;// TODO
			case EditAreaProperty.TEXT_EDIT:
				this.remove(textArea);
				break;
			}
			currentType = property.getType();
			switch (currentType) {
			case EditAreaProperty.DEFAULT_EDIT:
				break;
			case EditAreaProperty.IMAGE_EDIT:
				break;// TODO
			case EditAreaProperty.VIDEO_EDIT:
				enterVideoEdit();
				break;// TODO
			case EditAreaProperty.TEXT_EDIT:
				this.add(textArea);
				break;
			}
		}

		revalidate();
		repaint();

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		int type = property.getType();

		if (type == EditAreaProperty.VIDEO_EDIT) {
			// Graphics2D g2 = (Graphics2D) g;
			// g2.drawImage(bufferedImage, 0, 0, imgWidth, imgHeight, this);
			// selRatio(property.getVideoRatio(), g, bufferedImage);
		} else if (type == EditAreaProperty.IMAGE_EDIT) {
			if (null == image) {
				return;
			}
			selRatio(property.getDrawRatio(), g, image);
		} else {
			return;
		}
	}

	private void selRatio(int ratio, Graphics g, BufferedImage image) {
		switch (ratio) {
		case 0:// 铺满
			resizeFillIn(image, g);
			break;
		case 1:// 原始
			resizeOrignalScale(image, g);
			break;
		case 2:// 4:3
			resizeRatioScale(image, g, 4.0, 3.0);
			break;
		case 3:// 5:4
			resizeRatioScale(image, g, 5.0, 4.0);
			break;
		case 4:// 16:9
			resizeRatioScale(image, g, 16.0, 9.0);
			break;
		default:
			resizeFillIn(image, g);
			break;
		}

	}

	private void resizeFillIn(BufferedImage src, Graphics g) {
		Image tmp = MyImage.resize(src, imgWidth, imgHeight);
		g.drawImage(tmp, 0, 0, imgWidth, imgHeight, this);
	}

	private void resizeOrignalScale(BufferedImage src, Graphics g) {

		resizeRatioScale(src, g, (double) src.getWidth(),
				(double) src.getHeight());

	}

	private void resizeRatioScale(BufferedImage src, Graphics g, double w,
			double h) {

		int newWidth1 = imgWidth;
		int newHeight1 = (int) ((h / w) * newWidth1);
		int x1 = 0;
		int y1 = (imgHeight - newHeight1) / 2;

		int newHeight2 = imgHeight;
		int newWidth2 = (int) ((w / h) * newHeight2);
		int y2 = 0;
		int x2 = (imgWidth - newWidth2) / 2;

		if (x2 < 0 || (imgWidth > imgHeight && y1 >= 0)) {
			Image tmp = MyImage.resize(src, newWidth1, newHeight1);
			g.drawImage(tmp, x1, y1, newWidth1, newHeight1, this);
		} else {
			Image tmp = MyImage.resize(src, newWidth2, newHeight2);
			g.drawImage(tmp, x2, y2, newWidth2, newHeight2, this);
		}

	}

	class TextAreaThumbLoadListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			reloadThumb(e);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			reloadThumb(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			reloadThumb(e);
		}

		private void reloadThumb(DocumentEvent e) {
			// Document document = e.getDocument();
			// int changeLength = e.getLength();

			if (property.getType() == EditAreaProperty.TEXT_EDIT) {
				System.out.println("txt reload thumb");
				EditPane.thisTab.getPreview().loadThumb(EditContant.this,
						imgHeight > imgWidth);
			}
		}
	}
}

class EditAreaProperty {

	public static final int DEFAULT_EDIT = 0;
	public static final int IMAGE_EDIT = 1;
	public static final int VIDEO_EDIT = 2;
	public static final int TEXT_EDIT = 3;

	public static final int FILL = 0;
	public static final int ORIGNAL = 1;
	public static final int R4_3 = 2;
	public static final int R5_4 = 3;
	public static final int R16_9 = 4;

	private int type;
	private File imgFile;
	private File videoFile;
	private int drawRatio;
	private int videoRatio;
	private int videoTimes;
	private Duration duration;
	private Color bgColor;

	private JTextPane textArea = null;
	private SimpleAttributeSet txtAttrSet;
	private int lineSpacePixel = 0;
	//
	private int spaceAbovePixel = 0;
	private int spaceBelowPixel = 0;

	private final long id;

	public EditAreaProperty(long index) {
		type = DEFAULT_EDIT;
		imgFile = null;
		videoFile = null;
		drawRatio = FILL;
		videoRatio = R16_9;
		videoTimes = 1;
		duration = new Duration(0, 0, 0);
		bgColor = Color.WHITE;
		textArea = new JTextPane();
		txtAttrSet = new SimpleAttributeSet();

		this.id = index;

		initTxtAttr();
	}

	private void initTxtAttr() {
		setFontSize(16);
		setFontFamily("宋体");
		setItalic(false);
		setBold(false);
		setUnderline(false);
		setFontColor(Color.BLACK);
		setTextAlignment(StyleConstants.ALIGN_CENTER);
		setTextLineSpace(0);
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setDrawRatio(int drawRatio) {
		this.drawRatio = drawRatio;
	}

	public void setVideoRatio(int ratio) {
		this.videoRatio = ratio;
	}

	public void setImgFile(File file) {
		this.imgFile = file;
	}

	public void setVideoFile(File file) {
		this.videoFile = file;
	}

	public void setVideoTimes(int times) {
		this.videoTimes = times;
	}

	public void setDuration(Duration d) {
		this.duration = d;
	}

	public void setDuration(int h, int m, int s) {
		this.duration = new Duration(h, m, s);
	}

	public void setDurationHour(int h) {
		this.duration.hour = h;
	}

	public void setDurationMin(int m) {
		this.duration.min = m;
	}

	public void setDurationSec(int s) {
		this.duration.sec = s;
	}

	public void setBgColor(Color c) {
		this.bgColor = c;
	}

	public void setFontSize(int size) {
		StyleConstants.setFontSize(txtAttrSet, size);
		setTextLineSpace(this.lineSpacePixel);// 重新计算
		// setTextSpaceBelow(this.spaceBelowPixel);
		// setTextSpaceAbove(this.spaceAbovePixel);
	}

	public void setFontFamily(String font) {
		StyleConstants.setFontFamily(txtAttrSet, font);
	}

	public void setItalic(boolean isItalic) {
		StyleConstants.setItalic(txtAttrSet, isItalic);
	}

	public void setBold(boolean isBold) {
		StyleConstants.setBold(txtAttrSet, isBold);
	}

	public void setUnderline(boolean isUnderline) {
		StyleConstants.setUnderline(txtAttrSet, isUnderline);
	}

	public void setFontColor(Color c) {
		StyleConstants.setForeground(txtAttrSet, c);
	}

	public void setTextAlignment(int x) {
		StyleConstants.setAlignment(txtAttrSet, x);
	}

	public void setTextLineSpace(int i) {
		lineSpacePixel = i;
		int base = (Integer) txtAttrSet.getAttribute(StyleConstants.FontSize);
		float tmp = ((float) i) / ((float) base);

		StyleConstants.setLineSpacing(txtAttrSet, tmp);
	}

	public void setTextSpaceAbove(int i) {
		spaceAbovePixel = i;
		// int base = (Integer)
		// txtAttrSet.getAttribute(StyleConstants.FontSize);
		// float tmp = ((float)i)/((float)base);

		StyleConstants.setSpaceAbove(txtAttrSet, (float) i);
	}

	public void setTextSpaceBelow(int i) {
		spaceBelowPixel = i;
		// int base = (Integer)
		// txtAttrSet.getAttribute(StyleConstants.FontSize);
		// float tmp = ((float)i)/((float)base);

		StyleConstants.setSpaceBelow(txtAttrSet, (float) i);
	}

	public long getId() {
		return this.id;
	}

	public int getType() {
		return this.type;
	}

	public int getDrawRatio() {
		return this.drawRatio;
	}

	public int getVideoRatio() {
		return this.videoRatio;
	}

	public Duration getDuration() {
		return this.duration;
	}

	public int getVideoTimes() {
		return this.videoTimes;
	}

	public Color getBgColor() {
		return this.bgColor;
	}

	public SimpleAttributeSet getTxtAttrSet() {
		return this.txtAttrSet;
	}
    
	public int getLineSpacePixel() {
		return this.lineSpacePixel;
	}

	public int getSpaceAbovePixel() {
		return this.spaceAbovePixel;
	}

	public int getSpaceBelowPixel() {
		return this.spaceBelowPixel;
	}

	public File getVideoFile() {
		return this.videoFile;
	}

	public File getImgFile() {
		return this.imgFile;
	}
	
	public JTextPane getTextPane(){
		return this.textArea;
	}
	
	public String getTxt(){
		String tmp = null;
		try{
			tmp = this.textArea.getText();
		}catch (NullPointerException e){
			tmp = null;
		}
		return tmp;
	}
}
  
class Duration {

	public static final int HOUR_TYPE = 0;
	public static final int MIN_TYPE = 1;
	public static final int SEC_TYPE = 2;

	public int hour = 0;
	public int min = 0;
	public int sec = 0;

	public Duration(int h, int m, int s) {
		hour = h;
		min = m;
		sec = s;
	}
}

class EditAreaConstraints extends GridBagConstraints {
	private static final EditAreaConstraints cons = new EditAreaConstraints();

	private EditAreaConstraints() {
		super();
		gridx = 1;
		gridy = 1;
		gridheight = 6;
		gridwidth = 6;
		weightx = 10;
		weighty = 10;
		insets = new Insets(30, 30, 30, 30);
		fill = GridBagConstraints.NONE;
	}

	public static EditAreaConstraints get() {
		return cons;
	}
}
