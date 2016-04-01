package gui.framework;

import java.awt.Container;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SpringLayout.Constraints;

/**
 * @author Big_Tang 功能：实现文件发送，便于用户反馈信息 任务：支持主流邮箱(如qq邮箱，163邮箱等) 支持信息的发送 支持附件的发送
 */
public class sendEmail extends JFrame {
	private JLabel fromLabel = new JLabel("发件人: ");
	private JTextField fromAd = new JTextField();
	private JLabel mailLabel = new JLabel("您的邮箱: ");
	private JTextField mailAd = new JTextField();
	private final String smtp = "smtp.qq.com"; // 邮件服务器主机名
	private final String protocol = "smtp"; // 邮件传输协议
	private final String username = "526439885@qq.com"; // 默认邮箱
	private final String password = "225035cherrywong"; // 默认密码
	private final String to = "wsthl19920901@163.com"; // 收件人地址
	private JLabel subLabel = new JLabel("主题: "); // 邮件主题
	private JTextField subject = new JTextField();
	private JLabel bodyLabel = new JLabel("正文: ");
	private JTextArea body = new JTextArea(); // 邮件内容
	private JLabel attach = new JLabel("附件: ");
	private Constraints constraint;
	private JFileChooser chooser = new JFileChooser();
	private JLabel text_attach = new JLabel();

	public sendEmail() {
		setTitle("意见反馈");
		setSize(Framework.screensize.width >> 1, Framework.screensize.height >> 1);
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		setLocationRelativeTo(null);
		body.setLineWrap(true);
		Container content = getContentPane();
		fromAd.requestFocus();

		layout.putConstraint(SpringLayout.NORTH, fromLabel, 10, SpringLayout.NORTH, content);
		layout.putConstraint(SpringLayout.NORTH, mailLabel, 10, SpringLayout.NORTH, content);
		layout.putConstraint(SpringLayout.NORTH, mailAd, 5, SpringLayout.NORTH, content);
		layout.putConstraint(SpringLayout.WEST, fromLabel, 20, SpringLayout.WEST, content);
		constraint = new SpringLayout.Constraints();
		Spring centerX = Spring.scale(layout.getConstraint(SpringLayout.EAST, content), 0.5f);
		constraint.setConstraint(SpringLayout.NORTH, Spring.constant(5));
		Spring pad = Spring.sum(layout.getConstraint(SpringLayout.EAST, fromLabel), Spring.constant(5));
		constraint.setConstraint(SpringLayout.WEST, pad);
		constraint.setConstraint(SpringLayout.EAST, centerX);

		layout.putConstraint(SpringLayout.WEST, mailLabel, 5, SpringLayout.EAST, fromAd);
		layout.putConstraint(SpringLayout.WEST, mailAd, 5, SpringLayout.EAST, mailLabel);
		layout.putConstraint(SpringLayout.EAST, mailAd, -20, SpringLayout.EAST, content);
		layout.putConstraint(SpringLayout.WEST, subLabel, 20, SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.NORTH, subLabel, 25, SpringLayout.SOUTH, fromLabel);
		layout.putConstraint(SpringLayout.NORTH, subject, 20, SpringLayout.SOUTH, fromLabel);
		layout.putConstraint(SpringLayout.WEST, subject, 5, SpringLayout.EAST, subLabel);
		layout.putConstraint(SpringLayout.EAST, subject, -20, SpringLayout.EAST, content);
		layout.putConstraint(SpringLayout.NORTH, bodyLabel, 25, SpringLayout.SOUTH, subLabel);
		layout.putConstraint(SpringLayout.WEST, bodyLabel, 20, SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.WEST, bodyLabel, 20, SpringLayout.WEST, content);
		JScrollPane scrollPane = new JScrollPane(body);
		layout.putConstraint(SpringLayout.NORTH, scrollPane, 20, SpringLayout.SOUTH, subLabel);
		layout.putConstraint(SpringLayout.WEST, scrollPane, 5, SpringLayout.EAST, bodyLabel);
		layout.putConstraint(SpringLayout.EAST, scrollPane, -20, SpringLayout.EAST, content);
		JButton send = new JButton("发送");

		send.addActionListener((e) -> send());

		JButton clear = new JButton("清空");
		clear.addActionListener((e) -> {
			int val = JOptionPane.showConfirmDialog(this, "清空所有内容？", "确认", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (val == JOptionPane.OK_OPTION) {
				reset();
			}
		});

		JButton attachFile = new JButton("导入文件");
		attachFile.addActionListener((e) -> attachFile());

		layout.putConstraint(SpringLayout.SOUTH, scrollPane, -20, SpringLayout.NORTH, clear);
		layout.putConstraint(SpringLayout.EAST, clear, -20, SpringLayout.EAST, content);
		layout.putConstraint(SpringLayout.SOUTH, clear, -10, SpringLayout.SOUTH, content);
		layout.putConstraint(SpringLayout.EAST, send, -10, SpringLayout.WEST, clear);
		layout.putConstraint(SpringLayout.SOUTH, send, -10, SpringLayout.SOUTH, content);
		layout.putConstraint(SpringLayout.WEST, attach, 20, SpringLayout.WEST, content);
		layout.putConstraint(SpringLayout.SOUTH, attach, -20, SpringLayout.SOUTH, content);
		layout.putConstraint(SpringLayout.WEST, attachFile, 5, SpringLayout.EAST, attach);
		layout.putConstraint(SpringLayout.SOUTH, attachFile, -10, SpringLayout.SOUTH, content);
		layout.putConstraint(SpringLayout.SOUTH, text_attach, -20, SpringLayout.SOUTH, content);
		layout.putConstraint(SpringLayout.WEST, text_attach, 5, SpringLayout.EAST, attachFile);

		add(fromLabel);
		add(fromAd, constraint);
		add(mailLabel);
		add(mailAd);
		add(subLabel);
		add(subject);
		add(bodyLabel);
		add(scrollPane);
		add(send);
		add(clear);
		add(attach);
		add(attachFile);
		text_attach.setText(null);
		add(text_attach);

		setVisible(true);
	}

	private void attachFile() {
		File file = null;
		int state = chooser.showOpenDialog(null);
		file = chooser.getSelectedFile();
		if (state == JFileChooser.APPROVE_OPTION && file != null) {
			text_attach.setText(file.getAbsolutePath());
		}
	}

	private void reset() {
		fromAd.setText(null);
		body.setText(null);
		mailAd.setText(null);
		subject.setText(null);
		text_attach.setText(null);
	}

	private void send() {

		try {
			Properties prop = new Properties();
			prop.setProperty("mail.host", smtp);
			prop.setProperty("mail.transport.protocol", protocol);
			prop.setProperty("mail.smtp.auth", "true");

			// 使用JavaMail发送邮件的5个步骤
			// 1、创建session
			Session session = Session.getDefaultInstance(prop);
			// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
			// session.setDebug(true); debug专用!!!
			// 2、通过session得到transport对象
			Transport ts = session.getTransport();
			// 3、连上邮件服务器
			ts.connect(smtp, username, password);
			// 4、创建邮件
			Message message = createMail(session);
			// 5、发送邮件
			ts.sendMessage(message, message.getAllRecipients());
			ts.close();

			JOptionPane.showMessageDialog(this, "发送成功!", "发送完成", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {

			JOptionPane.showMessageDialog(this, "发送失败!", "发送失败", JOptionPane.WARNING_MESSAGE);
		}

	}

	private MimeMessage createMail(Session session) throws Exception {

		MimeMessage mailMessage = new MimeMessage(session);
		// 创建邮件发送者地址

		mailMessage.setFrom(new InternetAddress(username));

		// Message.RecipientType.TO属性表示接收者的类型为TO
		mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		// 设置邮件消息的主题
		mailMessage.setSubject(subject.getText());
		// 设置邮件消息发送的时间
		mailMessage.setSentDate(new Date());

		// 创建一个包含HTML内容的MimeBodyPart
		MimeBodyPart html = new MimeBodyPart();
		// 设置HTML内容

		StringBuilder text = new StringBuilder(body.getText().replaceAll("\n", "<br>"));// 换行
		text.append("<br><br> from:" + fromAd.getText() + "   " + mailAd.getText());
		html.setContent(text.toString(), "text/html; charset=utf-8");

		// 将MiniMultipart对象设置为邮件内容
		// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
		MimeMultipart mainPart = new MimeMultipart();
		// 创建容器描述数据关系
		mainPart.addBodyPart(html);

		if (text_attach.getText() != null) {
			MimeBodyPart attach = new MimeBodyPart();
			System.out.println(text_attach.getText());
			DataHandler dh = new DataHandler(new FileDataSource(text_attach.getText()));
			attach.setDataHandler(dh);
			attach.setFileName(dh.getName());
			mainPart.addBodyPart(attach);
		}

		mainPart.setSubType("mixed");
		mailMessage.setContent(mainPart);

		mailMessage.saveChanges();
		// 将创建的Email写入到E盘存储
		mailMessage.writeTo(new FileOutputStream("E:\\attachMail.txt"));
		return mailMessage;

	}

}
