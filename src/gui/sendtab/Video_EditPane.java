package gui.sendtab;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import utils.PlayerPanel;

public class Video_EditPane {

//	private final Dimension paneSize = new Dimension(260, 120);
//	private final Dimension paneSize = new Dimension(100, 30);
	private JPanel videoSetPane;

	private PlayerPanel playPane;
	private DirectMediaPlayerComponent player;

	private JLabel videoName;
	private videoAttribute videoAttr;

	public Video_EditPane(PlayerPanel playPane, videoAttribute videoAttr) {
		this.playPane = playPane;
		this.player = playPane.getPlayer();
		this.videoAttr = videoAttr;
	}

	public Component createVideoPane() {
		videoSetPane = new JPanel();
		videoSetPane.setLayout(new FlowLayout());
		videoSetPane.add(createControlPane());
		videoSetPane.add(createTimesPane());
		videoSetPane.add(createRatioPane());
		return videoSetPane;
	}

	public Component createControlPane() {
		JPanel controlPane = new JPanel();
		controlPane.setBorder(BorderFactory.createTitledBorder("control"));
		controlPane.setLayout(new BoxLayout(controlPane, BoxLayout.Y_AXIS));
		JPanel namePane = new JPanel();
		videoName = new JLabel();
		videoName.setHorizontalAlignment(SwingConstants.CENTER);
		videoName.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1,
				Color.LIGHT_GRAY));
		videoName.setText("Movie");

		JCheckBox nameCheckBox = new JCheckBox("名称:");
		nameCheckBox.setSelected(true);
		nameCheckBox.setToolTipText("在编辑区显示文件名，不会在LED屏上显示");
		nameCheckBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

			}

		});
		namePane.add(nameCheckBox);
		namePane.add(videoName);
		// button
		JPanel buttonPanel = new JPanel();
		JButton bPlay = new JButton("播放");
		JButton bPause = new JButton("暂停");
		JButton bStop = new JButton("停止");

		bPlay.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.green));
		bPause.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.lightBlue));
		bStop.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));

		bPlay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				player.getMediaPlayer().play();
			}

		});

		bStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				player.getMediaPlayer().stop();
			}

		});

		bPause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				player.getMediaPlayer().pause();
			}

		});

		buttonPanel.add(bPlay);
		buttonPanel.add(bPause);
		buttonPanel.add(bStop);

		controlPane.add(namePane);
		controlPane.add(buttonPanel);
		return controlPane;
	}

	public Component createTimesPane() {
		JPanel timesPane = new JPanel();
//		timesPane.setPreferredSize(paneSize);
		timesPane.setBorder(BorderFactory.createTitledBorder("times"));
		SpinnerNumberModel videoTimesModel = new SpinnerNumberModel(0, 0, 100,
				1);
		JLabel lab = new JLabel("播放次数: ");
		JSpinner videoTimesSpinner = new JSpinner(videoTimesModel);

		videoTimesSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				videoAttr.setTimes((int) videoTimesSpinner.getValue());
			}

		});
		timesPane.add(lab);
		timesPane.add(videoTimesSpinner);
		timesPane.add(new JLabel("次"));

		return timesPane;
	}

	public Component createRatioPane() {
		JPanel ratioPane = new JPanel();
//		ratioPane.setPreferredSize(paneSize);
		ratioPane.setBorder(BorderFactory.createTitledBorder("ratio"));
		JLabel lab = new JLabel("比例: ");

		String[] ratioSelection = { "铺满", "原始比例", "4:3", "5:4", "16:9" };
		JComboBox<String> videoRatio = new JComboBox<String>(ratioSelection);
		videoRatio.setSelectedIndex(4);

		videoRatio.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int selIndex = videoRatio.getSelectedIndex();
				switch (selIndex) {
				case 0:
					playPane.setPreferredSize(new Dimension(550, 320));
					playPane.refresh();
					videoAttr.setRatio("铺满");
					break;
				case 1:
					playPane.setPreferredSize(new Dimension(500, 300));
					playPane.refresh();
					videoAttr.setRatio("原始比例");
					break;
				case 2:
					playPane.setPreferredSize(new Dimension(500, 200));
					playPane.refresh();
					videoAttr.setRatio("4:3");
					break;
				case 3:
					playPane.setPreferredSize(new Dimension(500, 250));
					playPane.refresh();
					videoAttr.setRatio("5:4");
					break;
				case 4:
					playPane.setPreferredSize(new Dimension(500, 150));
					playPane.refresh();
					videoAttr.setRatio("16:9");
					break;
				default:
					System.exit(1);

				}
			}

		});
		ratioPane.add(lab);
		ratioPane.add(videoRatio);
		return ratioPane;
	}
}
