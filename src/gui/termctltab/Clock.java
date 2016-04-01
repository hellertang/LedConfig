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
	// 今天的日期对象
	private GregorianCalendar now = new GregorianCalendar();
	// 时钟标签，上面画的是圆形时钟
	private ClockLabel clockLabel = new ClockLabel();
	// 星期标签，指示星期
	private JLabel weekLabel = new JLabel();
	// 日期标签，指示日期
	private JLabel dateLabel = new JLabel();
	// 品牌标签
	private JLabel remarkLabel = new JLabel();
	// 时间标签，指示时间
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

		// 初始化品牌标签
		remarkLabel.setText("VLSI-NJU");
		remarkLabel.setLocation(205, 80);
		remarkLabel.setSize(100, 30);
		remarkLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
		remarkLabel.setForeground(Color.orange);

		// 初始化星期标签
		weekLabel.setSize(60, 20);
		weekLabel.setLocation(315, 190);
		weekLabel.setForeground(Color.pink);
		weekLabel.setFont(new Font("Arial Narrow", Font.BOLD, 12));
		// 为星期标签赋值
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

		// 初始化日期标签
		dateLabel.setSize(20, 20);
		dateLabel.setLocation(375, 190);
		dateLabel.setForeground(Color.pink);
		dateLabel.setFont(new Font("Fixedsys", Font.BOLD, 12));
		dateLabel.setText("" + now.get(Calendar.DATE));

		// 初始化时间标签
		timeLabel.setSize(500, 30);
		timeLabel.setLocation(100, 400);
		timeLabel.setForeground(new Color(0, 64, 128));
		timeLabel.setFont(new Font("Fixedsys", Font.PLAIN, 15));
	}
	// 自定义时钟标签，画一个圆形的时钟
	class ClockLabel extends JLabel implements Runnable {
		// 时钟标签的宽度和高度
		private final int WIDTH = 500;
		private final int HEIGHT = 440;

		// 圆形时钟的X半径和Y半径
		private final int CIRCLE_X_RADIUS = 150;
		private final int CIRCLE_Y_RADIUS = 155;

		// 圆形时钟的原点
		private final int CIRCLE_X = 250;
		private final int CIRCLE_Y = 200;

		// 圆形时钟指针的长度
		private final int HOUR_LENGTH = 70;
		private final int MIN_LENGTH = 100;
		private final int SEC_LENGTH = 135;

		// 当前时针所处的角度
		double arcHour = 0;
		// 当前分针所处的角度
		double arcMin = 0;
		// 当前秒针所处的角度
		double arcSec = 0;

		// 颜色的透明度，
		int alpha = 60;
		// 标识颜色透明度变化的方向，为true时表示越来越透明，为false时表示月来越不透明
		boolean flag = false;
		// 背景图片的id，轮换显示背景图片时使用
		int imageID = 0;
		// 时钟线程
		Thread clockThread = null;
		// 计数器
		int count = 0;

		public ClockLabel() {

			// 设置时钟标签的大小
			this.setSize(WIDTH, HEIGHT);

			// 获取时针、分针、秒针当前的角度
			arcHour = now.get(Calendar.HOUR) * (360.0 / 12)
					+ now.get(Calendar.MINUTE) * (360.0 / 12 / 60)
					+ now.get(Calendar.SECOND) * (360.0 / 12 / 60 / 60);
			arcMin = now.get(Calendar.MINUTE) * (360.0 / 60)
					+ now.get(Calendar.SECOND) * (360.0 / 60 / 60);
			arcSec = now.get(Calendar.SECOND) * (360.0 / 60);

			// 启动线程
			clockThread = new Thread(this);
			clockThread.start();
		}

		public void paint(Graphics g1) {
			// Graphics2D继承Graphics，比Graphics提供更丰富的方法
			Graphics2D g = (Graphics2D) g1;

			/** ***画圆形时钟的刻度，每6度便有一个刻度**** */
			for (int i = 0; i < 360; i = i + 6) {
				g.setColor(Color.gray);
				// 设置画笔的宽度为2
				g.setStroke(new BasicStroke(2));

				// 画刻度
				if (i % 90 == 0) {
					// 对于0，3，6，9点位置，使用一个大的刻度
					g.setColor(Color.pink);
					g.setStroke(new BasicStroke(7));// 画笔宽度为5
					// 当起点和终点一样时，画的就是点
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
					// 如果角度处于小时的位置，而且还不在0，3，6，9点时，画红色的小刻度
					g.setColor(Color.orange);
					g.setStroke(new BasicStroke(3));// 画笔宽度为3
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
					// 其他位置就画小刻度
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

			/** ****** 画时钟的指针 ******** */
			// 画时针
			Line2D.Double lh = new Line2D.Double(CIRCLE_X, CIRCLE_Y, CIRCLE_X
					+ Math.cos((arcHour - 90) * Math.PI / 180) * HOUR_LENGTH,
					CIRCLE_Y + Math.sin((arcHour - 90) * Math.PI / 180)
							* HOUR_LENGTH);
			// 设置画笔宽度和颜色
			g.setStroke(new BasicStroke(8));
			g.setColor(Color.pink);
			// 利用Graphics2D的draw方法画线
			g.draw(lh);

			// 画分针
			Line2D.Double lm = new Line2D.Double(CIRCLE_X, CIRCLE_Y, CIRCLE_X
					+ Math.cos((arcMin - 90) * Math.PI / 180) * MIN_LENGTH,
					CIRCLE_Y + Math.sin((arcMin - 90) * Math.PI / 180)
							* MIN_LENGTH);
			g.setStroke(new BasicStroke(4));
			g.setColor(Color.orange);
			g.draw(lm);

			// 画秒针
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

					// 计数
					count++;
					// 获得完整的日期格式
					DateFormat df = DateFormat.getDateTimeInstance(
							DateFormat.FULL, DateFormat.FULL);
					// 格式化当前时间
					String s = df.format(new Date());
					timeLabel.setText(s);
					// 每动一次对时钟指针的角度进行调整
					arcSec += 360.0 / 60 / 10; // 每秒转6度
					arcMin += 360.0 / 60 / 60 / 10; // 每60秒转6度
					arcHour += 360.0 / 12 / 60 / 60 / 10; // 每3600秒转30度

					// 当角度满一圈时，归0
					if (arcSec >= 360) {
						arcSec = 0;
					}
					if (arcMin >= 360) {
						arcMin = 0;
					}
					if (arcHour >= 360) {
						arcHour = 0;
					}
					// 重画时钟标签
					repaint();
					// 等待0.1秒钟
					Thread.sleep(100);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}