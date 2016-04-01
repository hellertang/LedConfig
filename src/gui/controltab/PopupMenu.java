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
		JMenuItem link = new JMenuItem("连接");
		JMenuItem discon = new JMenuItem("断开");
		JMenuItem update = new JMenuItem("刷新");
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
			case "连接": {
				int selectedRow = userTable.getSelectedRow();
				if (Ping.get().getIP() != null) {
					JOptionPane.showMessageDialog(userTable, "请刷新，当前在连接状态");
				} else {
					if (selectedRow == -1) {
						JOptionPane.showMessageDialog(userTable, "请选中需连接终端");
					} else {
						addr.setIP((String) userTable
								.getValueAt(selectedRow, 1));
						addr.setPort((String) userTable.getValueAt(selectedRow,
								2));
						timer.start();
						System.out.println("连接成功");
					}
				}
				break;
			}
			case "断开":
				bar.setValue(0);
				lab.setText("状态:  未连接");
				addr.setIP(null);
				addr.setPort(null);
				System.out.println("断开成功");
				break;
			case "刷新":
				userTable.clearSelection();
				if (Ping.get().getIP() == null) {
					bar.setValue(0);
					lab.setText("状态:  未连接");
				} else {
					bar.setValue(100);
					lab.setText("连接成功:   IP:" + Ping.get().getIP() + "  Port:"
							+ Ping.get().getPort());
				}
				System.out.println("刷新成功");
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
				lab.setText("目前已完成进度：" + Integer.toString(value * 10 / 3) + "%");
			} else {
				lab.setText("连接成功:   IP:" + Ping.get().getIP() + "  Port:"
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
				System.out.println("计时器关闭成功");
			}
		}

	}

}
