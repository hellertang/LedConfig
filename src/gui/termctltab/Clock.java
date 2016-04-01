package gui.termctltab;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

public class Clock extends JPanel  {
	// ��������ڶ���
	private GregorianCalendar now = new GregorianCalendar();
	// ʱ�ӱ�ǩ�����滭����Բ��ʱ��
	private ClockLabel clockLabel = new ClockLabel();
	// ���ڱ�ǩ��ָʾ����
	private JLabel weekLabel = new JLabel();
	// ���ڱ�ǩ��ָʾ����
	private JLabel dateLabel = new JLabel();
	// Ʒ�Ʊ�ǩ
	private JLabel remarkLabel = new JLabel();
	// ʱ���ǩ��ָʾʱ��
	private JLabel timeLabel = new JLabel();

	public Clock() {
		init();
		this.setBackground(Color.white);
		this.setLayout(null);
		this.add(weekLabel);
		this.add(dateLabel);
		this.add(remarkLabel);
		this.add(clockLabel);
	}
	
	private void init() {

		// ��ʼ��Ʒ�Ʊ�ǩ
		remarkLabel.setText("VLSI-NJU");
		remarkLabel.setLocation(205, 80);
		remarkLabel.setSize(100, 30);
		remarkLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
		remarkLabel.setForeground(Color.orange);

		// ��ʼ�����ڱ�ǩ
		weekLabel.setSize(60, 20);
		weekLabel.setLocation(315, 190);
		weekLabel.setForeground(Color.pink);
		weekLabel.setFont(new Font("Arial Narrow", Font.BOLD, 12));
		// Ϊ���ڱ�ǩ��ֵ
		int week = now.get(Calendar.DAY_OF_WEEK);
		switch (week) {
		case 1:
			weekLabel.setText("SUNDAY");
			break;
		case 2:
			weekLabel.setText("MONDAY");
			break;
		case 3:
			weekLabel.setText("TUESDAY");
			break;
		case 4:
			weekLabel.setText("WEDNESDAY");
			break;
		case 5:
			weekLabel.setText("THURSDAY");
			break;
		case 6:
			weekLabel.setText("FRIDAY");
			break;
		case 7:
			weekLabel.setText("SATURDAY");
			break;
		}

		// ��ʼ�����ڱ�ǩ
		dateLabel.setSize(20, 20);
		dateLabel.setLocation(375, 190);
		dateLabel.setForeground(Color.pink);
		dateLabel.setFont(new Font("Fixedsys", Font.BOLD, 12));
		dateLabel.setText("" + now.get(Calendar.DATE));

		// ��ʼ��ʱ���ǩ
		timeLabel.setSize(500, 30);
		timeLabel.setLocation(100, 400);
		timeLabel.setForeground(new Color(0, 64, 128));
		timeLabel.setFont(new Font("Fixedsys", Font.PLAIN, 15));
	}
	// �Զ���ʱ�ӱ�ǩ����һ��Բ�ε�ʱ��
	class ClockLabel extends JLabel implements Runnable {
		// ʱ�ӱ�ǩ�Ŀ�Ⱥ͸߶�
		private final int WIDTH = 500;
		private final int HEIGHT = 440;

		// Բ��ʱ�ӵ�X�뾶��Y�뾶
		private final int CIRCLE_X_RADIUS = 150;
		private final int CIRCLE_Y_RADIUS = 155;

		// Բ��ʱ�ӵ�ԭ��
		private final int CIRCLE_X = 250;
		private final int CIRCLE_Y = 200;

		// Բ��ʱ��ָ��ĳ���
		private final int HOUR_LENGTH = 70;
		private final int MIN_LENGTH = 100;
		private final int SEC_LENGTH = 135;

		// ��ǰʱ�������ĽǶ�
		double arcHour = 0;
		// ��ǰ���������ĽǶ�
		double arcMin = 0;
		// ��ǰ���������ĽǶ�
		double arcSec = 0;

		// ��ɫ��͸���ȣ�
		int alpha = 60;
		// ��ʶ��ɫ͸���ȱ仯�ķ���Ϊtrueʱ��ʾԽ��Խ͸����Ϊfalseʱ��ʾ����Խ��͸��
		boolean flag = false;
		// ����ͼƬ��id���ֻ���ʾ����ͼƬʱʹ��
		int imageID = 0;
		// ʱ���߳�
		Thread clockThread = null;
		// ������
		int count = 0;

		public ClockLabel() {

			// ����ʱ�ӱ�ǩ�Ĵ�С
			this.setSize(WIDTH, HEIGHT);

			// ��ȡʱ�롢���롢���뵱ǰ�ĽǶ�
			arcHour = now.get(Calendar.HOUR) * (360.0 / 12)
					+ now.get(Calendar.MINUTE) * (360.0 / 12 / 60)
					+ now.get(Calendar.SECOND) * (360.0 / 12 / 60 / 60);
			arcMin = now.get(Calendar.MINUTE) * (360.0 / 60)
					+ now.get(Calendar.SECOND) * (360.0 / 60 / 60);
			arcSec = now.get(Calendar.SECOND) * (360.0 / 60);

			// �����߳�
			clockThread = new Thread(this);
			clockThread.start();
		}

		public void paint(Graphics g1) {
			// Graphics2D�̳�Graphics����Graphics�ṩ���ḻ�ķ���
			Graphics2D g = (Graphics2D) g1;

			/** ***��Բ��ʱ�ӵĿ̶ȣ�ÿ6�ȱ���һ���̶�**** */
			for (int i = 0; i < 360; i = i + 6) {
				g.setColor(Color.gray);
				// ���û��ʵĿ��Ϊ2
				g.setStroke(new BasicStroke(2));

				// ���̶�
				if (i % 90 == 0) {
					// ����0��3��6��9��λ�ã�ʹ��һ����Ŀ̶�
					g.setColor(Color.pink);
					g.setStroke(new BasicStroke(7));// ���ʿ��Ϊ5
					// �������յ�һ��ʱ�����ľ��ǵ�
					g.drawLine(
							CIRCLE_X
									+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
							CIRCLE_Y
									+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS),
							CIRCLE_X
									+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
							CIRCLE_Y
									+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS));
				} else if (i % 30 == 0) {
					// ����Ƕȴ���Сʱ��λ�ã����һ�����0��3��6��9��ʱ������ɫ��С�̶�
					g.setColor(Color.orange);
					g.setStroke(new BasicStroke(3));// ���ʿ��Ϊ3
					g.drawLine(
							CIRCLE_X
									+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
							CIRCLE_Y
									+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS),
							CIRCLE_X
									+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
							CIRCLE_Y
									+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS));
				} else {
					// ����λ�þͻ�С�̶�
					g.setColor(Color.gray);
					g.drawLine(
							CIRCLE_X
									+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
							CIRCLE_Y
									+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS),
							CIRCLE_X
									+ (int) (Math.cos(i * Math.PI / 180) * CIRCLE_X_RADIUS),
							CIRCLE_Y
									+ (int) (Math.sin(i * Math.PI / 180) * CIRCLE_Y_RADIUS));
				}
			}

			/** ****** ��ʱ�ӵ�ָ�� ******** */
			// ��ʱ��
			Line2D.Double lh = new Line2D.Double(CIRCLE_X, CIRCLE_Y, CIRCLE_X
					+ Math.cos((arcHour - 90) * Math.PI / 180) * HOUR_LENGTH,
					CIRCLE_Y + Math.sin((arcHour - 90) * Math.PI / 180)
							* HOUR_LENGTH);
			// ���û��ʿ�Ⱥ���ɫ
			g.setStroke(new BasicStroke(8));
			g.setColor(Color.pink);
			// ����Graphics2D��draw��������
			g.draw(lh);

			// ������
			Line2D.Double lm = new Line2D.Double(CIRCLE_X, CIRCLE_Y, CIRCLE_X
					+ Math.cos((arcMin - 90) * Math.PI / 180) * MIN_LENGTH,
					CIRCLE_Y + Math.sin((arcMin - 90) * Math.PI / 180)
							* MIN_LENGTH);
			g.setStroke(new BasicStroke(4));
			g.setColor(Color.orange);
			g.draw(lm);

			// ������
			Line2D.Double ls = new Line2D.Double(CIRCLE_X, CIRCLE_Y, CIRCLE_X
					+ Math.cos((arcSec - 90) * Math.PI / 180) * SEC_LENGTH,
					CIRCLE_Y + Math.sin((arcSec - 90) * Math.PI / 180)
							* SEC_LENGTH);
			g.setStroke(new BasicStroke(1));

			g.setColor(Color.lightGray);
			g.draw(ls);

		}

		public void run() {
			try {

				while (clockThread != null) {

					// ����
					count++;
					// ������������ڸ�ʽ
					DateFormat df = DateFormat.getDateTimeInstance(
							DateFormat.FULL, DateFormat.FULL);
					// ��ʽ����ǰʱ��
					String s = df.format(new Date());
					timeLabel.setText(s);
					// ÿ��һ�ζ�ʱ��ָ��ĽǶȽ��е���
					arcSec += 360.0 / 60 / 10; // ÿ��ת6��
					arcMin += 360.0 / 60 / 60 / 10; // ÿ60��ת6��
					arcHour += 360.0 / 12 / 60 / 60 / 10; // ÿ3600��ת30��

					// ���Ƕ���һȦʱ����0
					if (arcSec >= 360) {
						arcSec = 0;
					}
					if (arcMin >= 360) {
						arcMin = 0;
					}
					if (arcHour >= 360) {
						arcHour = 0;
					}
					// �ػ�ʱ�ӱ�ǩ
					repaint();
					// �ȴ�0.1����
					Thread.sleep(100);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}