package gui.termctltab;

import gui.controltab.PopupMenu;
import gui.sendtab.tabLink;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import utils.Ping;
import utils.pngIcon;

public class timePane extends JPanel {

	private JPanel timeGetPane;
	private JPanel timeSetPane;

	private SpinnerModel hourModel;
	private SpinnerModel minModel;
	private SpinnerModel secModel;
	private JLabel lab;
	private JProgressBar bar;
	private JScrollPane scrollPane;
	private JTable table;
	
	
	
	public timePane(JScrollPane scrollPane,JTable table){
		init();
		this.scrollPane=scrollPane;
		this.table=table;
		scrollPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3)
					new PopupMenu(table, bar, lab).createMenu().show(
							e.getComponent(), e.getX(), e.getY());
			}
		});
		this.setLayout(new  BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(timeGetPane);
		this.add(timeSetPane);
		this.add(createTagPane());
	}
	
	public void init() {
		timeGetPane();
		timeSetPane();
	}

	public void timeGetPane() {
		timeGetPane = new JPanel();
		timeGetPane.setLayout(new FlowLayout());
		JPanel timerPane = new JPanel();
		timerPane.setBorder(BorderFactory.createTitledBorder("��ǰ�ն�ʱ��"));
		timerPane.setLayout(new BoxLayout(timerPane, BoxLayout.X_AXIS));
		JTextField timeText = new JTextField();
		timeText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		timeText.setText("00:00:00");
		timeText.setEditable(false);
		timerPane.add(timeText);
		JButton bRead = new JButton("��ȡ");
		bRead.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.green));
		bRead.setIcon(pngIcon.create("image//termCtl//read.png"));
		timeGetPane.add(timerPane);
		timeGetPane.add(bRead);
		timeGetPane.add(Box.createHorizontalGlue());
	}

	public void timeSetPane() {
		timeSetPane = new JPanel();
		timeSetPane.setLayout(new FlowLayout());
		JPanel timesetPane = new JPanel();
		timesetPane.setBorder(BorderFactory.createTitledBorder("�����ն�ʱ��"));
		timesetPane.setLayout(new BoxLayout(timesetPane, BoxLayout.X_AXIS));
		if (hourModel == null)
			hourModel = new SpinnerNumberModel(0, 0, 24, 1);
		if (minModel == null)
			minModel = new SpinnerNumberModel(0, 0, 60, 1);
		if (secModel == null)
			secModel = new SpinnerNumberModel(0, 0, 60, 1);

		JSpinner hourSpinner = new JSpinner(hourModel);
		hourSpinner.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		JSpinner minSpinner = new JSpinner(minModel);
		minSpinner.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		JSpinner secSpinner = new JSpinner(secModel);
		secSpinner.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		timesetPane.add(Box.createHorizontalStrut(5));
		JLabel hLab = new JLabel(" ʱ: ");
		hLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		hLab.setForeground(Color.gray);
		JLabel mLab = new JLabel(" ��: ");
		mLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		mLab.setForeground(Color.GRAY);
		JLabel sLab = new JLabel(" ��: ");
		sLab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		sLab.setForeground(Color.gray);
		timesetPane.add(hLab);
		timesetPane.add(Box.createHorizontalStrut(5));
		timesetPane.add(hourSpinner);
		timesetPane.add(Box.createHorizontalStrut(5));
		timesetPane.add(mLab);
		timesetPane.add(Box.createHorizontalStrut(5));
		timesetPane.add(minSpinner);
		timesetPane.add(Box.createHorizontalStrut(5));
		timesetPane.add(sLab);
		timesetPane.add(Box.createHorizontalStrut(5));
		timesetPane.add(secSpinner);
		timesetPane.add(Box.createHorizontalStrut(20));
		// ------
		JPanel setPane = new JPanel();
		setPane.setLayout(new BoxLayout(setPane, BoxLayout.X_AXIS));
		JButton bset = new JButton("����");
		bset.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
		bset.setIcon(pngIcon.create("image//termCtl//set.png"));
		setPane.add(Box.createGlue());
		setPane.add(bset);
		timeSetPane.add(timesetPane);
		timeSetPane.add(Box.createVerticalStrut(5));
		timeSetPane.add(setPane);
	}
	
	private JComponent createTagPane() {
		// tagPane
		JPanel tagPane = new JPanel();
		tagPane.setLayout(new BorderLayout());
		tagPane.setBorder(BorderFactory.createTitledBorder("�ն���Ϣ"));;
		// lab
		JPanel labPane = new JPanel();
		labPane.setLayout(new FlowLayout());
		lab = new JLabel("״̬:      δ����                ", JLabel.CENTER);
		lab.setForeground(Color.blue);
		JButton btLink = new JButton("����");
		btLink.setIcon(pngIcon.create("image//termCtl//connect.png"));
		btLink.setUI(new BEButtonUI()
		.setNormalColor(BEButtonUI.NormalColor.red));
		btLink.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (btLink.getText().equals("����")) {
					if (Ping.get().getIP() != null) {
						JOptionPane.showMessageDialog(btLink, "���ˢ�°�ť");
					} else {
						new tabLink(bar, lab, btLink);
					}

				} else if (btLink.getText().equals("�Ͽ�")) {
					if (Ping.get().getIP() == null) {
						JOptionPane.showMessageDialog(btLink, "���ˢ�°�ť");
					} else {
						lab.setText("״̬:      δ����                ");
						bar.setValue(0);
						btLink.setText("����");
						Ping.get().setIP(null);
						Ping.get().setIP(null);
					}
				} else {
					System.exit(1);
				}

			}
		});
		JButton btReset = new JButton("ˢ��");
		btReset.setIcon(pngIcon.create("image//termCtl//reset.png"));
		btReset.setUI(new BEButtonUI()
		.setNormalColor(BEButtonUI.NormalColor.green));
		btReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Ping.get().getIP() != null) {
					lab.setText("���ӳɹ�:   IP:" + Ping.get().getIP() + "  Port:"
							+ Ping.get().getPort());
					bar.setValue(30);
					btLink.setText("�Ͽ�");
				} else {
					lab.setText("״̬:      δ����                ");
					bar.setValue(0);
					btLink.setText("����");
				}

			}
		});
		
		labPane.add(lab);

		// bar
		JPanel barPane = new JPanel();
		barPane.setLayout(new FlowLayout());
		bar = new JProgressBar(1, 30);
		bar.setBorderPainted(true);
		bar.setStringPainted(true);
		bar.setPreferredSize(new Dimension(400,30));
		barPane.add(bar);
		tagPane.add(labPane, BorderLayout.NORTH);
		tagPane.add(barPane, BorderLayout.CENTER);
		return tagPane;
	}

}
