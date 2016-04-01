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
 * @author Big_Tang ���ܣ�ʵ���ļ����ͣ������û�������Ϣ ����֧����������(��qq���䣬163�����) ֧����Ϣ�ķ��� ֧�ָ����ķ���
 */
public class sendEmail extends JFrame {
	private JLabel fromLabel = new JLabel("������: ");
	private JTextField fromAd = new JTextField();
	private JLabel mailLabel = new JLabel("��������: ");
	private JTextField mailAd = new JTextField();
	private final String smtp = "smtp.qq.com"; // �ʼ�������������
	private final String protocol = "smtp"; // �ʼ�����Э��
	private final String username = "526439885@qq.com"; // Ĭ������
	private final String password = "225035cherrywong"; // Ĭ������
	private final String to = "wsthl19920901@163.com"; // �ռ��˵�ַ
	private JLabel subLabel = new JLabel("����: "); // �ʼ�����
	private JTextField subject = new JTextField();
	private JLabel bodyLabel = new JLabel("����: ");
	private JTextArea body = new JTextArea(); // �ʼ�����
	private JLabel attach = new JLabel("����: ");
	private Constraints constraint;
	private JFileChooser chooser = new JFileChooser();
	private JLabel text_attach = new JLabel();

	public sendEmail() {
		setTitle("�������");
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
		JButton send = new JButton("����");

		send.addActionListener((e) -> send());

		JButton clear = new JButton("���");
		clear.addActionListener((e) -> {
			int val = JOptionPane.showConfirmDialog(this, "����������ݣ�", "ȷ��", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (val == JOptionPane.OK_OPTION) {
				reset();
			}
		});

		JButton attachFile = new JButton("�����ļ�");
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

			// ʹ��JavaMail�����ʼ���5������
			// 1������session
			Session session = Session.getDefaultInstance(prop);
			// ����Session��debugģʽ�������Ϳ��Բ鿴��������Email������״̬
			// session.setDebug(true); debugר��!!!
			// 2��ͨ��session�õ�transport����
			Transport ts = session.getTransport();
			// 3�������ʼ�������
			ts.connect(smtp, username, password);
			// 4�������ʼ�
			Message message = createMail(session);
			// 5�������ʼ�
			ts.sendMessage(message, message.getAllRecipients());
			ts.close();

			JOptionPane.showMessageDialog(this, "���ͳɹ�!", "�������", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {

			JOptionPane.showMessageDialog(this, "����ʧ��!", "����ʧ��", JOptionPane.WARNING_MESSAGE);
		}

	}

	private MimeMessage createMail(Session session) throws Exception {

		MimeMessage mailMessage = new MimeMessage(session);
		// �����ʼ������ߵ�ַ

		mailMessage.setFrom(new InternetAddress(username));

		// Message.RecipientType.TO���Ա�ʾ�����ߵ�����ΪTO
		mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		// �����ʼ���Ϣ������
		mailMessage.setSubject(subject.getText());
		// �����ʼ���Ϣ���͵�ʱ��
		mailMessage.setSentDate(new Date());

		// ����һ������HTML���ݵ�MimeBodyPart
		MimeBodyPart html = new MimeBodyPart();
		// ����HTML����

		StringBuilder text = new StringBuilder(body.getText().replaceAll("\n", "<br>"));// ����
		text.append("<br><br> from:" + fromAd.getText() + "   " + mailAd.getText());
		html.setContent(text.toString(), "text/html; charset=utf-8");

		// ��MiniMultipart��������Ϊ�ʼ�����
		// MiniMultipart����һ�������࣬����MimeBodyPart���͵Ķ���
		MimeMultipart mainPart = new MimeMultipart();
		// ���������������ݹ�ϵ
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
		// ��������Emailд�뵽E�̴洢
		mailMessage.writeTo(new FileOutputStream("E:\\attachMail.txt"));
		return mailMessage;

	}

}
