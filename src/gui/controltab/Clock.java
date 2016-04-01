package gui.controltab;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Clock {
	private Timer timer;
	private TimerTask task;
	private boolean flag = true;
	private int hour, minute, second;
	private String hh, mm, ss;
	private JTextField lab;
	private int Counts = 0;

	private JButton bPlay;
	private JButton bPause;
	private JButton bStop;

	public Clock(JButton bPlay, JButton bPause, JButton bStop) {
		this.timer = new Timer();
		this.task = new timeTask();
		this.lab = new JTextField();
		this.bPlay = bPlay;
		this.bPause = bPause;
		this.bStop = bStop;
		timer.scheduleAtFixedRate(task, 0, 1000);

	}

	public Component createClockPane() {
		JPanel timePane = new JPanel();
		lab.setText("00:00:00");
		lab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
		lab.setForeground(Color.GRAY);
		lab.setEditable(false);
		timePane.setLayout(new FlowLayout());
		timePane.setBorder(BorderFactory.createTitledBorder("¼ÆÊ±"));
		timePane.add(lab);
		bPlay.addActionListener(new playListener());
		bPause.addActionListener(new pauseListener());
		bStop.addActionListener(new stopListener());
		return timePane;
	}

	public class timeTask extends TimerTask {

		private String date;

		@Override
		public void run() {
			if (!flag) {
				Counts++;
				hour = Counts / 3600;
				minute = (Counts % 3600) / 60;
				second = Counts % 60;
				if (second < 10)
					ss = "0" + second;
				else
					ss =String.valueOf(second);
				if (minute < 10)
					mm = "0" + minute;
				else
					mm=String.valueOf(minute);
				if (hour < 10)
					hh = "0" + hour;
				else
					hh=String.valueOf(hour);
				date = hh + ":" + mm + ":" + ss;
				lab.setText(date);
			}
		}
	}

	public class playListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			flag = false;
		}

	}

	public class pauseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			flag = true;
		}
	}

	public class stopListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			flag = true;
			lab.setText("00:00:00");
			Counts = 0;
		}

	}

}
