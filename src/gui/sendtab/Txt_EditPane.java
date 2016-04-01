package gui.sendtab;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

public class Txt_EditPane {
	private JEditorPane text;
	private txtAttribute thisAttr;

	private JPanel EditPane;

	private JComboBox<String> fontSel;
	private JComboBox<Integer> fontSizeSel;
	private JToggleButton bBold;
	private JToggleButton bItalic;
	private JToggleButton bUnderline;
	private JColorChooser fontColorChooser;
	private JColorChooser bgColorChooser;
	private JPanel fontColorPreview;

	private JDialog fontColorChooseDialog;
	private JToggleButton bLeft;
	private JToggleButton bCenter;
	private JToggleButton bRight;
	private SpinnerNumberModel lineSpaceModel;
	private SpinnerModel hourModel;
	private SpinnerModel minModel;
	private SpinnerModel secModel;
	private Object colorChooseDialog;
	private JPanel bgPreview;

	public Txt_EditPane(JEditorPane text, txtAttribute txtAttr) {
		this.text = text;
		this.thisAttr = txtAttr;
		Create_EditPane();

	}

	public JPanel getEditPane() {
		return EditPane;
	}

	public void setEditPane(JPanel editPane) {
		EditPane = editPane;
	}

	public Component Create_EditPane() {
		EditPane = new JPanel();
//		EditPane.setPreferredSize(new Dimension(100,50));
		EditPane.setLayout(new FlowLayout());
		EditPane.add(Create_FontPane());
		EditPane.add(Create_StylePane());
		EditPane.add(Create_TimePane());
		EditPane.add(Create_BgPane());
		return EditPane;
	}

	public Component Create_FontPane() {
		JPanel FontSetPane = new JPanel();
		FontSetPane.setBorder(BorderFactory.createTitledBorder("字体"));
		FontSetPane.setLayout(new BoxLayout(FontSetPane, BoxLayout.Y_AXIS));

		/*
		 * Font Size pane
		 */
		JPanel FontSizePane = new JPanel();
		String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();
		String[] chineseNames = new String[23];
		System.arraycopy(fontNames, fontNames.length - 23, chineseNames, 0, 23);
		fontSel = new JComboBox<String>(chineseNames);
		fontSel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontFamily(text, (String) fontSel.getSelectedItem());
			}
		});

		Integer[] fontSizes = { 8, 16, 24, 32, 40, 48, 56, 64, 72, 80 };
		fontSizeSel = new JComboBox<Integer>(fontSizes);
		fontSizeSel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(text,
						(Integer) fontSizeSel.getSelectedItem());
			}
		});
		FontSizePane.add(fontSel);
		FontSizePane.add(fontSizeSel);

		/*
		 * Font Style:bold italic underline button
		 */
		JPanel FontStylePane = new JPanel();
		bBold = new JToggleButton("<html><b>B</b></html>");
		bItalic = new JToggleButton("<html><i>I</i></html>");
		bUnderline = new JToggleButton("<html><u>U</u></html>");
		bBold.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.normal));
		bItalic.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.normal));
		bUnderline.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.normal));
		bBold.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontBold(text);
				thisAttr.setBold(true);

			}
		});
		bItalic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontItalic(text);
				thisAttr.setItalic(true);
			}
		});
		bUnderline.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontUnder(text);
				thisAttr.setUnderLine(false);
			}
		});
		FontStylePane.add(bBold);
		FontStylePane.add(bItalic);
		FontStylePane.add(bUnderline);

		/*
		 * font color
		 */
		JPanel FontColorPane = new JPanel();
		fontColorChooser = new JColorChooser();
		fontColorChooseDialog = JColorChooser.createDialog(text, "请选择字体颜色",
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
				((Component) fontColorChooseDialog).setVisible(true);
			}
		});

		JButton bAdvanceColor = new JButton("高级");
		bAdvanceColor.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.red));
		bAdvanceColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fontColorChooser.setChooserPanels(advance);
				((Component) fontColorChooseDialog).setVisible(true);
			}
		});

		FontColorPane.add(new JLabel("当前:"));
		fontColorPreview = new JPanel();
		fontColorPreview.setBackground(fontColorChooser.getColor());
		fontColorPreview.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		fontColorPreview.setPreferredSize(new Dimension(20, 20));

		FontColorPane.add(fontColorPreview);
		FontColorPane.add(bFontColor);
		FontColorPane.add(bAdvanceColor);

		FontSetPane.add(FontSizePane);
		FontSetPane.add(FontStylePane);
		FontSetPane.add(FontColorPane);

		return FontSetPane;
	}

	public Component Create_StylePane() {
		JPanel StylePane = new JPanel();
		StylePane.setBorder(BorderFactory.createTitledBorder("格式"));
		StylePane.setLayout(new BoxLayout(StylePane, BoxLayout.Y_AXIS));

		/*
		 * 靠左，居中，靠右显示
		 */
		JPanel buttonPane = new JPanel();
		bLeft = new JToggleButton("靠左");
		bCenter = new JToggleButton("居中");
		bRight = new JToggleButton("靠右");
		bLeft.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.normal));
		bCenter.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.normal));
		bRight.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.normal));
		bLeft.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontLocation(text, 0);
				thisAttr.setLeft(true);
				thisAttr.setRight(false);
				thisAttr.setCenter(false);
			}
		});
		bCenter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontLocation(text, 1);
				thisAttr.setCenter(true);
				thisAttr.setLeft(false);
				thisAttr.setRight(false);

			}
		});
		bRight.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setFontLocation(text, 2);
				thisAttr.setRight(true);
				thisAttr.setCenter(false);
				thisAttr.setLeft(false);
			}
		});

		buttonPane.add(bLeft);
		buttonPane.add(bCenter);
		buttonPane.add(bRight);

		/*
		 * 行间距
		 */
		JPanel spinnerPanel = new JPanel();
		lineSpaceModel = new SpinnerNumberModel(0, 0, 1080, 1);
		JSpinner lineSpinner = new JSpinner(lineSpaceModel);
		lineSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				setLineSpace(text, (int) lineSpinner.getValue());
				thisAttr.setTextLineSpace((int) lineSpinner.getValue());
			}

		});
		spinnerPanel.add(new JLabel("行间距:"));
		spinnerPanel.add(lineSpinner);

		StylePane.add(buttonPane);
		StylePane.add(Box.createVerticalStrut(5));
		StylePane.add(spinnerPanel);
		StylePane.add(Box.createVerticalGlue());
		return StylePane;

	}

	public Component Create_TimePane() {
		JPanel TimePane = new JPanel();
		TimePane.setBorder(BorderFactory.createTitledBorder("插播时长"));
		TimePane.setLayout(new BoxLayout(TimePane, BoxLayout.Y_AXIS));
		JPanel hourPanel = new JPanel();
		JPanel minPanel = new JPanel();
		JPanel secPanel = new JPanel();
		if (hourModel == null)
			hourModel = new SpinnerNumberModel(0, 0, 24, 1);
		if (minModel == null)
			minModel = new SpinnerNumberModel(0, 0, 60, 1);
		if (secModel == null)
			secModel = new SpinnerNumberModel(0, 0, 60, 1);

		JPanel ColorPaneh = new JPanel();
		ColorPaneh.setBackground(Color.red);
		ColorPaneh.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		ColorPaneh.setPreferredSize(new Dimension(10, 10));

		JPanel ColorPanem = new JPanel();
		ColorPanem.setBackground(Color.YELLOW);
		ColorPanem.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		ColorPanem.setPreferredSize(new Dimension(10, 10));

		JPanel ColorPanes = new JPanel();
		ColorPanes.setBackground(Color.BLUE);
		ColorPanes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		ColorPanes.setPreferredSize(new Dimension(10, 10));

		JSpinner hourSpinner = new JSpinner(hourModel);
		JSpinner minSpinner = new JSpinner(minModel);
		JSpinner secSpinner = new JSpinner(secModel);

		hourSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				thisAttr.setHour((int) hourSpinner.getValue());
			}
		});

		minSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				thisAttr.setMin((int) minSpinner.getValue());
			}
		});

		secSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				thisAttr.setSec((int) secSpinner.getValue());
			}
		});

		hourPanel.add(ColorPaneh);
		hourPanel.add(new JLabel(" 时: "));
		hourPanel.add(hourSpinner);
		minPanel.add(ColorPanem);
		minPanel.add(new JLabel(" 分: "));
		minPanel.add(minSpinner);
		secPanel.add(ColorPanes);
		secPanel.add(new JLabel(" 秒: "));
		secPanel.add(secSpinner);

		TimePane.add(hourPanel);
		TimePane.add(minPanel);
		TimePane.add(secPanel);
		return TimePane;
	}

	public Component Create_BgPane() {
		JPanel bgPane = new JPanel();
		bgPane.setBorder(BorderFactory.createTitledBorder("背景颜色"));
		bgPane.setLayout(new BoxLayout(bgPane, BoxLayout.Y_AXIS));

		if (bgColorChooser == null) {
			bgColorChooser = new JColorChooser();
		}
		if (colorChooseDialog == null) {
			colorChooseDialog = JColorChooser.createDialog(text, "请选择背景颜色",
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
				((Dialog) colorChooseDialog).setVisible(true);
			}

		});

		JButton bAdvanceColor = new JButton("高级");
		bAdvanceColor.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.red));
		bAdvanceColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bgColorChooser.setChooserPanels(advance);
				((Dialog) colorChooseDialog).setVisible(true);
			}
		});

		bgPane.setBorder(BorderFactory.createTitledBorder("背景颜色"));
		bgPane.setLayout(new FlowLayout());
		bgPane.add(new JLabel("当前:"));

		bgPreview = new JPanel();
		bgPreview.setBackground(bgColorChooser.getColor());
		bgPreview.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		bgPreview.setPreferredSize(new Dimension(20, 20));
		bgPane.add(bgPreview);

		bgPane.add(bColorChoose);
		bgPane.add(bAdvanceColor);

		return bgPane;
	}

	class FontColorChooserButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			setFontColor(text, fontColorChooser.getColor());
			fontColorPreview.setBackground(fontColorChooser.getColor());
			thisAttr.setFontColor(fontColorChooser.getColor());
		}

	}

	class BgColorChooserButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			text.setBackground(bgColorChooser.getColor());
			bgPreview.setBackground(bgColorChooser.getColor());
			thisAttr.setBgColor(bgColorChooser.getColor());
		}

	}

	/*
	 * 设置字体
	 */

	public void setFontFamily(JEditorPane editor, String family) {
		MutableAttributeSet attr = new SimpleAttributeSet();
		StyleConstants.setFontFamily(attr, family);
		setCharacterAttributes(editor, attr, false);
	}

	/*
	 * 设置字体大小
	 */

	public void setFontSize(JEditorPane editor, int size) {
		if (editor != null) {
			if ((size > 0) && (size < 512)) {
				MutableAttributeSet attr = new SimpleAttributeSet();
				StyleConstants.setFontSize(attr, size);
				setCharacterAttributes(editor, attr, false);
			} else {
				UIManager.getLookAndFeel().provideErrorFeedback(editor);
			}
		}
	}

	/*
	 * 设置放置的位置
	 */

	public void setFontLocation(JEditorPane editor, int flag) {
		MutableAttributeSet attr = new SimpleAttributeSet();
		StyleConstants.setAlignment(attr, flag);
		setParagraphAttributes(editor, attr, false);

	}

	/*
	 * 设置字体颜色
	 */

	public void setFontColor(JEditorPane editor, Color fg) {
		MutableAttributeSet attr = new SimpleAttributeSet();
		StyleConstants.setForeground(attr, fg);
		setCharacterAttributes(editor, attr, false);

	}

	/*
	 * 设置字体粗体
	 */
	public void setFontBold(JEditorPane editor) {
		StyledEditorKit kit = getStyledEditorKit(editor);
		MutableAttributeSet attr = kit.getInputAttributes();
		boolean bold = (StyleConstants.isBold(attr)) ? false : true;
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setBold(sas, bold);
		setCharacterAttributes(editor, sas, false);
	}

	/*
	 * 设置字体斜体
	 */
	public void setFontItalic(JEditorPane editor) {
		StyledEditorKit kit = getStyledEditorKit(editor);
		MutableAttributeSet attr = kit.getInputAttributes();
		boolean italic = (StyleConstants.isItalic(attr)) ? false : true;
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setItalic(sas, italic);
		setCharacterAttributes(editor, sas, false);

	}

	/*
	 * 设置字体下划线
	 */
	public void setFontUnder(JEditorPane editor) {
		StyledEditorKit kit = getStyledEditorKit(editor);
		MutableAttributeSet attr = kit.getInputAttributes();
		boolean underline = (StyleConstants.isUnderline(attr)) ? false : true;
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setUnderline(sas, underline);
		setCharacterAttributes(editor, sas, false);

	}

	/*
	 * 设置字体间距
	 */
	public void setLineSpace(JEditorPane editor, int t) {

		StyledDocument doc = ((JTextPane) editor).getStyledDocument();
		MutableAttributeSet attr = new SimpleAttributeSet();
		StyleConstants.setLineSpacing(attr, t);
		doc.setParagraphAttributes(editor.getSelectionStart(),
				editor.getSelectionEnd(), attr, false);

	}

	public static final void setCharacterAttributes(JEditorPane editor,
			AttributeSet attr, boolean replace) {
		int p0 = editor.getSelectionStart();
		int p1 = editor.getSelectionEnd();
		if (p0 != p1) {
			StyledDocument doc = getStyledDocument(editor);
			doc.setCharacterAttributes(p0, p1 - p0, attr, replace);
		}

		StyledEditorKit k = getStyledEditorKit(editor);
		MutableAttributeSet inputAttributes = k.getInputAttributes();
		if (replace) {
			inputAttributes.removeAttributes(inputAttributes);
		}
		inputAttributes.addAttributes(attr);
	}

	public static final void setParagraphAttributes(JEditorPane editor,
			AttributeSet attr, boolean replace) {
		int p0 = editor.getSelectionStart();
		int p1 = editor.getSelectionEnd();
		StyledDocument doc = getStyledDocument((JTextPane) editor);
		doc.setParagraphAttributes(p0, p1 - p0, attr, replace);
	}

	protected static final StyledDocument getStyledDocument(JEditorPane e) {
		Document d = e.getDocument();
		if (d instanceof StyledDocument) {
			return (StyledDocument) d;
		}
		throw new IllegalArgumentException("document must be StyledDocument");
	}

	protected static final StyledEditorKit getStyledEditorKit(JEditorPane e) {
		EditorKit k = e.getEditorKit();
		if (k instanceof StyledEditorKit) {
			return (StyledEditorKit) k;
		}
		throw new IllegalArgumentException("EditorKit must be StyledEditorKit");
	}

}
