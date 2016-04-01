package gui.termctltab;

import gui.controltab.PopupMenu;
import gui.controltab.ControlTab.resetListener;
import gui.controltab.ControlTab.rowBoxListener;
import gui.controltab.PopupMenu.PopMenuListener;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import sql.daofactory.DaoFactory;
import utils.ConnectionFactory;
import utils.Ping;

import com.mysql.jdbc.Connection;

public class tabLink extends JFrame {

	private DefaultComboBoxModel<String> boxModel;
	private JComboBox<String> rowBox;
	private DefaultTableModel userModel;
	private JTable userTable;
	private Connection conn;

	private JProgressBar bar;
	private JLabel lab;
	private JButton btLink;

	public tabLink(JProgressBar bar, JLabel lab, JButton btLink) {
		this.setLayout(new BorderLayout());
		this.add(createTabSpace(), BorderLayout.CENTER);
		this.setTitle("终端信息");
		this.setSize(500, 600);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.bar = bar;
		this.lab = lab;
		this.btLink = btLink;
		updateJLocation();
	}

	public Component createTabSpace() {
		JPanel termPane = new JPanel();
		termPane.setLayout(new BoxLayout(termPane, BoxLayout.Y_AXIS));
		JPanel rowPane = new JPanel();
		rowPane.setLayout(new BoxLayout(rowPane, BoxLayout.X_AXIS));
		JLabel rowLab = new JLabel("当前组:");
		boxModel = new DefaultComboBoxModel<String>();
		boxModel.addElement("默认   ");
		rowBox = new JComboBox<String>(boxModel);
		rowBox.addActionListener(new rowBoxListener());
		JButton reset = new JButton("刷新");
		reset.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.green));
		reset.addActionListener(new resetListener());

		rowPane.add(Box.createHorizontalStrut(5));
		rowPane.add(rowLab);
		rowPane.add(Box.createHorizontalStrut(5));
		rowPane.add(rowBox);
		rowPane.add(Box.createHorizontalGlue());
		rowPane.add(reset);
		rowPane.setBorder(BorderFactory.createTitledBorder("组信息"));
		JPanel termList = new JPanel();
		termList.setBorder(BorderFactory.createTitledBorder("终端列表"));
		termList.add(createUserTable());
		termPane.add(Box.createVerticalStrut(10));
		termPane.add(rowPane);
		termPane.add(Box.createVerticalStrut(10));
		termPane.add(termList);
		return termPane;
	}

	private Component createUserTable() {
		JPanel tablePane = new JPanel();
		tablePane.setLayout(new GridLayout(1, 1));
		String[] coloumnNames = { "终端名称", "IP", "Port" };
		String[][] rowData = null;
		userModel = new DefaultTableModel(rowData, coloumnNames);
		userTable = new JTable(userModel);
		userTable.setRowSorter(new TableRowSorter<>(userModel));
		userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(userTable);
		scrollPane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3)
					new PopupMenu().createMenu().show(e.getComponent(),
							e.getX(), e.getY());
			}
		});
		tablePane.add(scrollPane);
		return tablePane;
	}

	public void updateJLocation() {
		Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension fsize = this.getSize();
		if (fsize.height > scr.height)
			fsize.height = scr.height;
		if (fsize.width > scr.width)
			fsize.width = scr.width;
		this.setLocation((scr.width - fsize.width) / 2,
				(scr.height - fsize.height) / 2);
	}

	public class resetListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				conn = (Connection) ConnectionFactory.getInstance()
						.makeConnection();
				conn.setAutoCommit(false);
				ResultSet rs = DaoFactory.getUserDAOInstance().getName(conn);
				conn.commit();
				while (rs.next()) {
					String rowValues = rs.getString("name");
					boxModel.addElement(rowValues);
				}
				System.out.println("向表格中" + userTable.getRowCount() + "条数据");
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		}

	}

	public class rowBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			while (userModel.getRowCount() > 0) {
				int i = userModel.getRowCount() - 1;
				userModel.removeRow(i);
			}
			String selected = rowBox.getItemAt(rowBox.getSelectedIndex());
			try {
				conn = (Connection) ConnectionFactory.getInstance()
						.makeConnection();
				conn.setAutoCommit(false);
				ResultSet rs = DaoFactory.getUserDAOInstance().getPort(conn,
						selected);
				conn.commit();
				while (rs.next()) {
					String[] rowValues = { rs.getString("name"),
							rs.getString("ip"), rs.getString("port") };
					userModel.addRow(rowValues);
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}

	}

	public class PopupMenu implements ChangeListener, ActionListener {
		private JPopupMenu popup;

		private Timer timer;

		private Ping addr;

		public PopupMenu() {
			bar.addChangeListener(this);
			addr = Ping.get();
			timer = new Timer(100, this);
		}

		public JPopupMenu createMenu() {
			if (popup == null)
				popup = new JPopupMenu("Popup");
			JMenuItem link = new JMenuItem("连接");
			JMenuItem update = new JMenuItem("刷新");
			PopMenuListener listener = new PopMenuListener();
			link.addActionListener(listener);
			update.addActionListener(listener);
			popup.add(link);
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
					if (selectedRow == -1) {
						JOptionPane.showMessageDialog(userTable, "请选中需连接终端");
					} else {
						addr.setIP((String) userTable
								.getValueAt(selectedRow, 1));
						addr.setPort((String) userTable.getValueAt(selectedRow,
								2));
						timer.start();
						System.out.println("连接成功");
						btLink.setText("断开");
						dispose();
					}
					break;
				}
				case "刷新":
					userTable.clearSelection();
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
				if (value == 1) {
					lab.setText("状态:      未连接                ");
				} else if (value < 30 && value > 1) {
					lab.setText("目前已完成进度：" + Integer.toString(value * 10 / 3)
							+ "%");
				} else {
					lab.setText("连接成功:   IP:" + addr.getIP() + "  Port:"
							+ addr.getPort());
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

}
