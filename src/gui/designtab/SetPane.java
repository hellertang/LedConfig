package gui.designtab;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import utils.MyColor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SetPane extends JPanel {
	private DesignTab thisTab = null;

	private JPanel imageSetting = null;
	private JPanel videoSetting = null;
	private JPanel textSetting = null;
	private JPanel defaultSetting = null;

	private static final String IMAGE_SET = "image_setting";
	private static final String VIDEO_SET = "video_setting";
	private static final String TEXT_SET = "text_setting";
	private static final String DEFAULT_SET = "default_setting";

	private static final int IMAGE_TYPE = 0;
	private static final int TXT_TYPE = 1;
	private static final int VIDEO_TYPE = 2;
	private static final int DEFAULT_TYPE = 3;

	private int currentType = DEFAULT_TYPE;
	// image setting
	JComboBox<String> ratio = null;
	JComboBox<String> videoRatio = null;
	JComboBox<String> fontSel = null;
	JComboBox<Integer> fontSizeSel = null;
	JColorChooser bgColorChooser = null;
	JDialog colorChooseDialog = null;
	JColorChooser fontColorChooser = null;
	JDialog fontColorChooseDialog = null;

	JPanel[] bgPreview = new JPanel[3];
	JPanel fontColorPreview = null;

	JToggleButton bBold = null;
	JToggleButton bItalic = null;
	JToggleButton bUnderline = null;

	JToggleButton bLeft = null;
	JToggleButton bCenter = null;
	JToggleButton bRight = null;

	SpinnerModel hourModel = null;
	SpinnerModel minModel = null;
	SpinnerModel secModel = null;
	
	SpinnerModel lineSpaceModel = null;
	SpinnerModel spaceAboveModel = null;
	SpinnerModel spaceBelowModel = null;
	SpinnerModel videoTimesModel = null;
	
	JLabel videoName = null;

	private CardLayout layout = null;

	public SetPane(DesignTab designTab) {
		super();

		thisTab = designTab;

		layout = new CardLayout();
		setLayout(layout);
		add(createImageSetting(), IMAGE_SET);
		add(createVideoSetting(), VIDEO_SET);
		add(createTextSetting(), TEXT_SET);
		add(createDefaultSetting(), DEFAULT_SET);
		layout.last(this);
	}

	public void changeToImageSetting(EditAreaProperty ep) {
		layout.show(this, IMAGE_SET);
		currentType = IMAGE_TYPE;
		setImageRatioSelection(ep.getDrawRatio());
		setDurationSelection(ep.getDuration());
		setBgColorSelection(ep.getBgColor());
	}

	public void changeToVideoSetting(EditAreaProperty ep) {
		layout.show(this, VIDEO_SET);
		currentType = VIDEO_TYPE;
		
		videoTimesModel.setValue(ep.getVideoTimes());
		videoRatio.setSelectedIndex(ep.getVideoRatio());
		videoName.setText(ep.getVideoFile().getName());
		videoName.setToolTipText(ep.getVideoFile().getName());

	}

	public void changeToTextSetting(EditAreaProperty ep) {
		layout.show(this, TEXT_SET);
		currentType = TXT_TYPE;
		setDurationSelection(ep.getDuration());
		setBgColorSelection(ep.getBgColor());
		// set font selection
		SimpleAttributeSet tmp = ep.getTxtAttrSet();
		Object o = tmp.getAttribute(StyleConstants.Bold);
		bBold.setSelected(o == null ? false : (boolean) o);
		o = tmp.getAttribute(StyleConstants.Italic);
		bItalic.setSelected(o == null ? false : (boolean) o);
		o = tmp.getAttribute(StyleConstants.Underline);
		bUnderline.setSelected(o == null ? false : (boolean) o);
		o = tmp.getAttribute(StyleConstants.FontFamily);
		fontSel.setSelectedItem(o);
		o = tmp.getAttribute(StyleConstants.FontSize);
		System.out.println(((Integer) o));
		fontSizeSel.setSelectedItem(((Integer) o));
		o = tmp.getAttribute(StyleConstants.Foreground);
		fontColorChooser.setColor((Color) o);
		fontColorPreview.setBackground((Color) o);

		o = tmp.getAttribute(StyleConstants.Alignment);
		switch ((Integer) o) {
		case StyleConstants.ALIGN_LEFT:
			bLeft.setSelected(true);
			bRight.setSelected(false);
			bCenter.setSelected(false);
			break;
		case StyleConstants.ALIGN_CENTER:
			bLeft.setSelected(false);
			bRight.setSelected(false);
			bCenter.setSelected(true);
			break;
		case StyleConstants.ALIGN_RIGHT:
			bLeft.setSelected(false);
			bRight.setSelected(true);
			bCenter.setSelected(false);
			break;
		}
		
		lineSpaceModel.setValue(ep.getLineSpacePixel());
		spaceAboveModel.setValue(ep.getSpaceAbovePixel());
		spaceBelowModel.setValue(ep.getSpaceBelowPixel());

	}

	public void changeToDefaultSetting() {
		layout.show(this, DEFAULT_SET);
		currentType = DEFAULT_TYPE;
	}

	private void setImageRatioSelection(int x) {
		ratio.setSelectedIndex(x);
	}

	private void setDurationSelection(Duration d) {
		hourModel.setValue(d.hour);
		minModel.setValue(d.min);
		secModel.setValue(d.sec);
	}

	private void setBgColorSelection(Color c) {
		bgColorChooser.setColor(c);
		bgPreview[currentType].setBackground(c);
	}

	// image
	private JScrollPane createImageSetting() {
		imageSetting = new JPanel();
		Dimension paneSize = new Dimension(250, 200);

		// ratio pane
		// time Pane
		// background pane

		// top
		BoxLayout boxLayout = new BoxLayout(imageSetting, BoxLayout.X_AXIS);
		imageSetting.setLayout(boxLayout);
		// add ratio setting pane
		imageSetting.add(Box.createHorizontalStrut(5));
		imageSetting.add(createImageRatioSettingPane(paneSize));
		// add time setting Pane
		imageSetting.add(Box.createHorizontalStrut(5));
		imageSetting.add(createImageDurationSettingPane(paneSize));
		// add background setting pane
		imageSetting.add(Box.createHorizontalStrut(5));
		imageSetting.add(createBgColorSettingPane(paneSize, IMAGE_TYPE));
		// add padding
		imageSetting.add(Box.createHorizontalStrut(5));

		JScrollPane tmp = new JScrollPane();
		tmp.setViewportView(imageSetting);
		tmp.getVerticalScrollBar().setUnitIncrement(50);
		return tmp;
	}

	private JPanel createImageRatioSettingPane(Dimension paneSize) {
		JPanel ratioPane = new JPanel();
		ratioPane.setPreferredSize(paneSize);
		ratioPane.setBorder(BorderFactory.createTitledBorder("显示比例"));

		String[] imageRatio = { "铺满", "原始比例", "4:3", "5:4", "16:9" };
		ratio = new JComboBox<String>(imageRatio);
		ratio.setSelectedIndex(0);

		ratio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selIndex = ratio.getSelectedIndex();
				thisTab.getEdit().setCurrentEditDrawRatio(selIndex);
				System.out
						.println("sel img ratio combobox " + imageRatio[selIndex]);
			}

		});
		ratioPane.add(ratio);
		return ratioPane;
	}

	private JPanel createImageDurationSettingPane(Dimension paneSize) {
		JPanel timePane = new JPanel();

		if (hourModel == null)
			hourModel = new SpinnerNumberModel(0, 0, 24, 1);
		if (minModel == null)
			minModel = new SpinnerNumberModel(0, 0, 60, 1);
		if (secModel == null)
			secModel = new SpinnerNumberModel(0, 0, 60, 1);

		JSpinner hourSpinner = new JSpinner(hourModel);
		JSpinner minSpinner = new JSpinner(minModel);
		JSpinner secSpinner = new JSpinner(secModel);

		hourSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int val = (Integer) hourSpinner.getValue();
				if (val >= 24 || val < 0) {
					val = 0;
					hourSpinner.setValue(val);
				}
				thisTab.getEdit().setCurrentEditDuration(Duration.HOUR_TYPE,
						val);
			}

		});

		minSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int val = (Integer) minSpinner.getValue();
				if (val >= 60 || val < 0) {
					val = 0;
					minSpinner.setValue(val);
				}
				thisTab.getEdit()
						.setCurrentEditDuration(Duration.MIN_TYPE, val);
			}

		});

		secSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int val = (Integer) secSpinner.getValue();
				if (val >= 60 || val < 0) {
					val = 0;
					secSpinner.setValue(val);
				}
				thisTab.getEdit()
						.setCurrentEditDuration(Duration.SEC_TYPE, val);
			}

		});

		timePane.setBorder(BorderFactory.createTitledBorder("播放时长"));
		timePane.setPreferredSize(paneSize);

		timePane.add(Box.createHorizontalStrut(5));
		timePane.add(hourSpinner);
		timePane.add(Box.createHorizontalStrut(2));
		timePane.add(new JLabel("时"));
		timePane.add(Box.createHorizontalStrut(2));
		timePane.add(minSpinner);
		timePane.add(Box.createHorizontalStrut(2));
		timePane.add(new JLabel("分"));
		timePane.add(Box.createHorizontalStrut(2));
		timePane.add(secSpinner);
		timePane.add(Box.createHorizontalStrut(2));
		timePane.add(new JLabel("秒"));
		timePane.add(Box.createHorizontalStrut(5));
		return timePane;
	}

	private JPanel createBgColorSettingPane(Dimension paneSize, int type) {
		JPanel bgPane = new JPanel();
		if (bgColorChooser == null) {
			bgColorChooser = new JColorChooser();
		}
		if (colorChooseDialog == null) {
			colorChooseDialog = JColorChooser.createDialog(thisTab, "请选择背景颜色",
					true, bgColorChooser, new BgColorChooserButtonListener(),
					null);
		}
		AbstractColorChooserPanel[] colorPanels = bgColorChooser
				.getChooserPanels();

		AbstractColorChooserPanel[] basic = { colorPanels[0] };
		AbstractColorChooserPanel[] advance = { colorPanels[3] };

		JButton bColorChoose = new JButton("选择背景颜色");
		bColorChoose.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.green));

		bColorChoose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				bgColorChooser.setChooserPanels(basic);
				colorChooseDialog.setVisible(true);
			}

		});

		JButton bAdvanceColor = new JButton("高级");
		bAdvanceColor.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.red));
		bAdvanceColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bgColorChooser.setChooserPanels(advance);
				colorChooseDialog.setVisible(true);
			}
		});

		bgPane.setBorder(BorderFactory.createTitledBorder("背景颜色"));
		bgPane.setPreferredSize(paneSize);
		bgPane.setLayout(new FlowLayout());
		bgPane.add(new JLabel("当前:"));

		bgPreview[type] = new JPanel();
		bgPreview[type].setBackground(bgColorChooser.getColor());
		bgPreview[type].setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bgPreview[type].setPreferredSize(new Dimension(20, 20));
		bgPane.add(bgPreview[type]);

		// bgPane.add(Box.createHorizontalStrut(15));
		bgPane.add(bColorChoose);
		bgPane.add(bAdvanceColor);

		return bgPane;
	}

	// video
	private JScrollPane createVideoSetting() {
		videoSetting = new JPanel();
		Dimension paneSize = new Dimension(250,200);
		//name
		videoSetting.add(Box.createHorizontalStrut(5));
		videoSetting.add(creatVideoPlayCtrlPane(paneSize));
		//ratio
		videoSetting.add(Box.createHorizontalStrut(5));
		videoSetting.add(createVideoRatioSettingPane(paneSize));
		//times
		videoSetting.add(Box.createHorizontalStrut(5));
		videoSetting.add(createVideoTimesSettingPane(paneSize));
		
		//padding
		videoSetting.add(Box.createHorizontalStrut(5));
		

		JScrollPane tmp = new JScrollPane();
		tmp.setViewportView(videoSetting);
		tmp.getVerticalScrollBar().setUnitIncrement(50);
		return tmp;
	}

	private Component creatVideoPlayCtrlPane(Dimension paneSize){
		JPanel labelPanel = new JPanel();
		labelPanel.setPreferredSize(paneSize);
		labelPanel.setBorder(BorderFactory.createTitledBorder("文件"));
		//name
		JPanel namePanel = new JPanel();
		videoName = new JLabel();
		videoName.setPreferredSize(new Dimension(150,30));
		videoName.setHorizontalAlignment(SwingConstants.CENTER);
		videoName.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.LIGHT_GRAY));
		
		JCheckBox nameCheckBox = new JCheckBox("名称:");
		nameCheckBox.setSelected(true);
		nameCheckBox.setToolTipText("在编辑区显示文件名，不会在LED屏上显示");
		nameCheckBox.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				thisTab.getEdit().setCurrentVideoShowName(e.getStateChange() == ItemEvent.SELECTED);
			}
			
		});
		namePanel.add(nameCheckBox);
		namePanel.add(videoName);
		//button
		JPanel buttonPanel = new JPanel();
		JButton bPlay = new JButton("播放");
		JButton bPause = new JButton("暂停");
		JButton bStop = new JButton("停止");
		
		bPlay.setUI(new BEButtonUI()
		.setNormalColor(BEButtonUI.NormalColor.green));
		bPause.setUI(new BEButtonUI()
		.setNormalColor(BEButtonUI.NormalColor.lightBlue));
		bStop.setUI(new BEButtonUI()
		.setNormalColor(BEButtonUI.NormalColor.red));
		
		bPlay.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				thisTab.getEdit().setCurrentVideoPlay(true);
			}
			
		});
		
		bStop.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				thisTab.getEdit().setCurrentVideoPlay(false);
			}
			
		});
		
		bPause.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				thisTab.getEdit().setCurrentVideoPause();
			}
			
		});
		
		buttonPanel.add(bPlay);
		buttonPanel.add(bPause);
		buttonPanel.add(bStop);
		
		//top add all above
		labelPanel.add(namePanel);
		labelPanel.add(buttonPanel);
		
		return labelPanel;
	}
	
	private Component createVideoTimesSettingPane(Dimension paneSize) {
		JPanel spinnerPanel = new JPanel();
		spinnerPanel.setPreferredSize(paneSize);
		spinnerPanel.setBorder(BorderFactory.createTitledBorder("播放次数"));
		
		videoTimesModel = new SpinnerNumberModel(0,0,100,1);
		JSpinner videoTimesSpinner = new JSpinner(videoTimesModel);
		
		videoTimesSpinner.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				thisTab.getEdit().setCurrentVideoTimes((Integer)videoTimesSpinner.getValue());
			}
			
		});
		
		spinnerPanel.add(videoTimesSpinner);
		spinnerPanel.add(new JLabel("次"));
		
		return spinnerPanel;
	}

	private Component createVideoRatioSettingPane(Dimension paneSize) {
		JPanel comboBoxPanel = new JPanel();
		comboBoxPanel.setPreferredSize(paneSize);
		comboBoxPanel.setBorder(BorderFactory.createTitledBorder("比例"));
		
		String [] ratioSelection = { "铺满", "原始比例", "4:3", "5:4", "16:9" };
		videoRatio = new JComboBox<String>(ratioSelection);
		videoRatio.setSelectedIndex(4);
		
		videoRatio.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selIndex = videoRatio.getSelectedIndex();
				thisTab.getEdit().setCurrentVideoRatio(selIndex);
				System.out.println("sel video ratio combobox "+ ratioSelection[selIndex]);
			}
			
		});
		
		comboBoxPanel.add(videoRatio);
		return comboBoxPanel;
	}

	// text
	private JScrollPane createTextSetting() {
		textSetting = new JPanel();
		Dimension paneSize = new Dimension(250, 200);

		BoxLayout boxLayout = new BoxLayout(textSetting, BoxLayout.X_AXIS);
		textSetting.setLayout(boxLayout);
		// font set
		textSetting.add(Box.createHorizontalStrut(5));
		textSetting.add(createTextFontSettingPane(paneSize));
		// text gap set
		textSetting.add(Box.createHorizontalStrut(5));
		textSetting.add(createTextGapSettingPane(paneSize));
		// duration set
		textSetting.add(Box.createHorizontalStrut(5));
		textSetting.add(createTextDurationSettingPane(paneSize));
		// bg color
		textSetting.add(Box.createHorizontalStrut(5));
		textSetting.add(createBgColorSettingPane(paneSize, TXT_TYPE));
		// padding
		textSetting.add(Box.createHorizontalStrut(5));

		JScrollPane tmp = new JScrollPane();
		tmp.setViewportView(textSetting);
		tmp.getVerticalScrollBar().setUnitIncrement(50);
		return tmp;
	}

	private Component createTextGapSettingPane(Dimension paneSize) {
		JPanel gapSetPane = new JPanel();

		gapSetPane.setBorder(BorderFactory.createTitledBorder("间距"));
		gapSetPane.setPreferredSize(paneSize);
		
		//left center right button panel
		JPanel buttonPanel = new JPanel();
		bLeft = new JToggleButton("靠左");
		bCenter = new JToggleButton("居中");
		bRight = new JToggleButton("靠右");
		bLeft.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.normal));
		bCenter.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.normal));
		bRight.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.normal));

		bLeft.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					bRight.setSelected(false);
					bCenter.setSelected(false);
					thisTab.getEdit().setCurrentTxtAligment(
							StyleConstants.ALIGN_LEFT);
				}
			}

		});

		bRight.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					bLeft.setSelected(false);
					bCenter.setSelected(false);
					thisTab.getEdit().setCurrentTxtAligment(
							StyleConstants.ALIGN_RIGHT);
				}
			}

		});

		bCenter.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					bRight.setSelected(false);
					bLeft.setSelected(false);
					thisTab.getEdit().setCurrentTxtAligment(
							StyleConstants.ALIGN_CENTER);
				}
			}

		});

		buttonPanel.add(bLeft);
		buttonPanel.add(bCenter);
		buttonPanel.add(bRight);
		
		//spinner panel 1
		JPanel spinnerPanel = new JPanel();
		lineSpaceModel = new SpinnerNumberModel(0, 0, 1080, 1);
		JSpinner lineSpaceSpinner = new JSpinner(lineSpaceModel);
		
		lineSpaceSpinner.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				thisTab.getEdit().setCurrentTxtLineSpace((Integer) lineSpaceSpinner.getValue());
			}
			
		});
		spinnerPanel.add(new JLabel("行间距"));
		spinnerPanel.add(lineSpaceSpinner);
		spinnerPanel.add(new JLabel("像素"));
		
		//spinner panel 2,3
		JPanel spinnerPanel2 = new JPanel();
		JPanel spinnerPanel3 = new JPanel();
		spaceAboveModel = new SpinnerNumberModel(0,0,1080,1);
		spaceBelowModel = new SpinnerNumberModel(0,0,1080,1);
		JSpinner spaceAboveSpinner = new JSpinner(spaceAboveModel);
		JSpinner spaceBelowSpinner = new JSpinner(spaceBelowModel);
		
		spaceAboveSpinner.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				thisTab.getEdit().setCurrentTxtSpaceAbove((Integer)spaceAboveSpinner.getValue());
			}
			
		});
		spaceBelowSpinner.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				thisTab.getEdit().setCurrentTxtSpaceBelow((Integer)spaceBelowSpinner.getValue());
			}
			
		});
		
		spinnerPanel2.add(new JLabel("段前距离"));
		spinnerPanel2.add(spaceAboveSpinner);
		spinnerPanel2.add(new JLabel("像素"));
		
		spinnerPanel3.add(new JLabel("段后距离"));
		spinnerPanel3.add(spaceBelowSpinner);
		spinnerPanel3.add(new JLabel("像素"));
		
		
		//top add all above
		gapSetPane.add(buttonPanel);
		gapSetPane.add(spinnerPanel);
//		gapSetPane.add(spinnerPanel2);
//		gapSetPane.add(spinnerPanel3);

		return gapSetPane;
	}

	private Component createTextDurationSettingPane(Dimension paneSize) {
		return createImageDurationSettingPane(paneSize);
	}

	private JPanel createTextFontSettingPane(Dimension paneSize) {
		JPanel fontSetPane = new JPanel();
		// combobox font size
		JPanel comboBoxPanel = new JPanel();
		String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();
		String[] chineseFont = new String[23];
		System.arraycopy(fontNames, fontNames.length - 23, chineseFont, 0, 23);

		fontSel = new JComboBox<String>(chineseFont);
		fontSel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String fontFamily = (String) fontSel.getSelectedItem();
				thisTab.getEdit().setCurrentEditFontFamily(fontFamily);
				System.out.println("sel font combobox " + fontFamily);
			}

		});

		Integer[] fontSize = { 8, 16, 24, 32, 40, 48, 56, 64, 72, 80 };
		fontSizeSel = new JComboBox<Integer>(fontSize);
		fontSizeSel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Integer size = (Integer) fontSizeSel.getSelectedItem();
				thisTab.getEdit().setCurrentEditFontSize(size);
				System.out.println("sel font size combobox " + size);
			}

		});

		comboBoxPanel.add(fontSel);
		comboBoxPanel.add(fontSizeSel);

		// bold italic underline button
		JPanel buttonPanel = new JPanel();

		bBold = new JToggleButton("<html><b>B</b></html>");
		bItalic = new JToggleButton("<html><i>I</i></html>");
		bUnderline = new JToggleButton("<html><u>U</u></html>");
		bBold.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.normal));
		bItalic.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.normal));
		bUnderline.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.normal));

		bBold.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				thisTab.getEdit().setCurrentEditFontBold(
						ItemEvent.SELECTED == e.getStateChange());
			}

		});

		bItalic.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				thisTab.getEdit().setCurrentEditFontItalic(
						ItemEvent.SELECTED == e.getStateChange());
			}

		});

		bUnderline.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				thisTab.getEdit().setCurrentEditFontUnderline(
						ItemEvent.SELECTED == e.getStateChange());
			}

		});

		buttonPanel.add(bBold);
		buttonPanel.add(bItalic);
		buttonPanel.add(bUnderline);

		// font color
		JPanel fontColorPanel = new JPanel();
		fontColorChooser = new JColorChooser();
		fontColorChooseDialog = JColorChooser.createDialog(thisTab, "请选择字体颜色",
				true, fontColorChooser, new FontColorChooserButtonListener(),
				null);
		AbstractColorChooserPanel[] colorPanels = fontColorChooser
				.getChooserPanels();

		AbstractColorChooserPanel[] basic = { colorPanels[0] };
		AbstractColorChooserPanel[] advance = { colorPanels[3] };

		JButton bFontColor = new JButton("选择字体颜色");
		bFontColor.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.green));
		bFontColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fontColorChooser.setChooserPanels(basic);
				fontColorChooseDialog.setVisible(true);
			}

		});

		JButton bAdvanceColor = new JButton("高级");
		bAdvanceColor.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.red));
		bAdvanceColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fontColorChooser.setChooserPanels(advance);
				fontColorChooseDialog.setVisible(true);
			}
		});

		fontColorPanel.add(new JLabel("当前:"));

		fontColorPreview = new JPanel();
		fontColorPreview.setBackground(fontColorChooser.getColor());
		fontColorPreview.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		fontColorPreview.setPreferredSize(new Dimension(20, 20));

		fontColorPanel.add(fontColorPreview);
		fontColorPanel.add(bFontColor);
		fontColorPanel.add(bAdvanceColor);

		// top add all above
		fontSetPane.add(comboBoxPanel);
		fontSetPane.add(buttonPanel);
		fontSetPane.add(fontColorPanel);

		fontSetPane.setBorder(BorderFactory.createTitledBorder("字体"));
		fontSetPane.setPreferredSize(paneSize);
		return fontSetPane;
	}

	private JPanel createDefaultSetting() {
		defaultSetting = new JPanel();
		// defaultSetting.setBackground(Color.GRAY);
		return defaultSetting;
	}

	class BgColorChooserButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			thisTab.getEdit().setCurrentEditBgColor(bgColorChooser.getColor());
			bgPreview[currentType].setBackground(bgColorChooser.getColor());
		}

	}

	class FontColorChooserButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			thisTab.getEdit().setCurrentEditFontColor(
					fontColorChooser.getColor());
			fontColorPreview.setBackground(fontColorChooser.getColor());
		}

	}

}
