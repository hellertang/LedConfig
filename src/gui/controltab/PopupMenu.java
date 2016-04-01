package gui.controltab;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utils.Ping;

public class PopupMenu implements ChangeListener, ActionListener {
	private JPopupMenu popup;
	private JTable userTable;
	private JProgressBar bar;

	private Timer timer;
	private JLabel lab;

	private Ping addr;

	public PopupMenu(JTable table, JProgressBar bar, JLabel lab) {
		this.bar = bar;
		this.userTable = table;
		this.lab = lab;
		bar.addChangeListener(this);
		addr = Ping.get();
		timer = new Timer(100, this);
	}

	public JPopupMenu createMenu() {
		if (popup == null)
			popup = new JPopupMenu("Popup");
		JMenuItem link = new JMenuItem("����");
		JMenuItem discon = new JMenuItem("�Ͽ�");
		JMenuItem update = new JMenuItem("ˢ��");
		PopMenuListener listener = new PopMenuListener();
		link.addActionListener(listener);
		discon.addActionListener(listener);
		update.addActionListener(listener);
		popup.add(link);
		popup.add(discon);
		popup.add(update);
		return popup;
	}

	public class PopMenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			switch (command) {
			case "����": {
				int selectedRow = userTable.getSelectedRow();
				if (Ping.get().getIP() != null) {
					JOptionPane.showMessageDialog(userTable, "��ˢ�£���ǰ������״̬");
				} else {
					if (selectedRow == -1) {
						JOptionPane.showMessageDialog(userTable, "��ѡ���������ն�");
					} else {
						addr.setIP((String) userTable
								.getValueAt(selectedRow, 1));
						addr.setPort((String) userTable.getValueAt(selectedRow,
								2));
						timer.start();
						System.out.println("���ӳɹ�");
					}
				}
				break;
			}
			case "�Ͽ�":
				bar.setValue(0);
				lab.setText("״̬:  δ����");
				addr.setIP(null);
				addr.setPort(null);
				System.out.println("�Ͽ��ɹ�");
				break;
			case "ˢ��":
				userTable.clearSelection();
				if (Ping.get().getIP() == null) {
					bar.setValue(0);
					lab.setText("״̬:  δ����");
				} else {
					bar.setValue(100);
					lab.setText("���ӳɹ�:   IP:" + Ping.get().getIP() + "  Port:"
							+ Ping.get().getPort());
				}
				System.out.println("ˢ�³ɹ�");
				break;
			default:
				System.exit(0);
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		int value = bar.getValue();
		if (e.getSource() == bar) {
			if (value < 30) {
				lab.setText("Ŀǰ����ɽ��ȣ�" + Integer.toString(value * 10 / 3) + "%");
			} else {
				lab.setText("���ӳɹ�:   IP:" + Ping.get().getIP() + "  Port:"
						+ Ping.get().getPort());
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			int value = bar.getValue();
			if (value < 30)
				bar.setValue(++value);
			else {
				timer.stop();
				System.out.println("��ʱ���رճɹ�");
			}
		}

	}

}
