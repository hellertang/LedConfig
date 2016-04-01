package gui.controltab;

import gui.framework.Framework;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import sql.daofactory.DaoFactory;
import sql.entity.Log;
import utils.ConnectionFactory;
import utils.pngIcon;
import utils.proBar;

import com.mysql.jdbc.Connection;

public class ControlTab extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String Control_Edit = "Control_Edit";
	private static final String Log_Edit = "Log_Edit";
	private static final String Save_Edit = "Save_Edit";
	private static final String Default_Edit = "Control_Edit";

	private CardLayout layout = null;
	private JToolBar toolBar = null;

	private JPanel container = null;
	private JPanel termPane = null;
	private JPanel ctlPane = null;
	private JPanel tablePane = null;

	private JPanel infoPane = null;
	private JPanel logPane = null;
	private JPanel savePane = null;

	private JPanel workPane = null;
	private JSplitPane vSplitPane1 = null;

	private JComboBox<String> rowBox = null;
	private DefaultComboBoxModel<String> boxModel = null;

	private JTable userTable = null;
	private JTable logTable = null;
	private JLabel lab = null;

	private JProgressBar bar = null;
	private DefaultTableModel userModel = null;
	private DefaultTableModel logModel = null;
	private Connection conn = null;

	public ControlTab() {
		super();
		setLayout(new GridLayout(1, 1));
		add(createWorkspace());
	}

	private Component createWorkspace() {
		workPane = new JPanel();

		vSplitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				createTermPane(), createCtlPane());
		workPane.setLayout(new GridLayout(1, 1));
		workPane.add(vSplitPane1);
		workPane.setMaximumSize(new Dimension(Framework.screensize.width, 400));
		workPane.setPreferredSize(new Dimension(500, 500));
		return workPane;
	}

	private Component createTermPane() {
		termPane = new JPanel();
		termPane.setLayout(new BoxLayout(termPane, BoxLayout.Y_AXIS));
		JPanel rowPane = new JPanel();
		rowPane.setLayout(new BoxLayout(rowPane, BoxLayout.X_AXIS));
		JLabel rowLab = new JLabel("当前组:");
		boxModel = new DefaultComboBoxModel<String>();
		boxModel.addElement("默认   ");
		rowBox = new JComboBox<String>(boxModel);
		rowBox.addActionListener(new rowBoxListener());
		JButton reset = new JButton("刷新");
//		reset.setUI(new BEButtonUI()
//				.setNormalColor(BEButtonUI.NormalColor.green));
		reset.setIcon(pngIcon.create("image//control//update.png"));
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
		termPane.add(Box.createVerticalStrut(5));
		termPane.add(rowPane);
		termPane.add(Box.createVerticalStrut(5));
		termPane.add(termList);
		termPane.add(Box.createGlue());
		return termPane;
	}

	private Component createCtlPane() {
		ctlPane = new JPanel();
		ctlPane.setLayout(new BorderLayout());
		ctlPane.add(createToolBar(), BorderLayout.NORTH);
		layout = new CardLayout();
		container = new JPanel(layout);

		container.add(createInfoPane(), Control_Edit);
		container.add(createLogPane(), Log_Edit);
		container.add(createSavePane(), Save_Edit);
		container.add(createInfoPane(), Default_Edit);
		layout.show(container, Default_Edit);

		ctlPane.add(container, BorderLayout.CENTER);
		ctlPane.add(createTagPane(), BorderLayout.SOUTH);
		return ctlPane;
	}

	private Component createInfoPane() {
		infoPane = new JPanel();
		infoPane.setLayout(new BorderLayout());
		JPanel playPane = new JPanel();
		playPane.setBorder(BorderFactory.createTitledBorder("控制播放"));
		playPane.setLayout(new FlowLayout());
		JButton bPlay = new JButton("播放");
		JButton bPause = new JButton("暂停");
		JButton bStop = new JButton("停止");
		bPlay.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.lightBlue));
		bPause.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.green));
		bStop.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
		bPlay.setIcon(pngIcon.create("image//control//play.png"));
		bPause.setIcon(pngIcon.create("image//control//pause.png"));
		bStop.setIcon(pngIcon.create("image//control//stop.png"));
		bPlay.addActionListener(new playListener());
		bPause.addActionListener(new pauseListener());
		bStop.addActionListener(new stopListener());
		playPane.add(bPlay);
		playPane.add(bPause);
		playPane.add(bStop);
		infoPane.add(playPane, BorderLayout.NORTH);
		infoPane.add(new Clock(bPlay,bPause,bStop).createClockPane(),BorderLayout.CENTER);
		return infoPane;
	}

	private Component createLogPane() {
		logPane = new JPanel();
		logPane.setLayout(new BorderLayout());

		JPanel playPane = new JPanel();
		playPane.setBorder(BorderFactory.createTitledBorder("日志文件"));
		playPane.setLayout(new BorderLayout());
		// btPane
		JPanel btPane = new JPanel();
		btPane.setLayout(new FlowLayout());
		JButton bRead = new JButton("读取");
		bRead.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.green));
		bRead.setIcon(pngIcon.create("image//control//read.png"));
		JButton bDel = new JButton("删除");
		bDel.setIcon(pngIcon.create("image//control//delete.png"));;
		bDel.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
		JButton bContrl = new JButton("管理");
		bContrl.setIcon(pngIcon.create("image//control//control.png"));
		bContrl.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.blue));
		bRead.addActionListener(new bReadListener());
		bDel.addActionListener(new bDelListener());
		bContrl.addActionListener(new bContolListener());
		btPane.add(bRead);
		btPane.add(bDel);
		btPane.add(bContrl);
		// tabPane
		JPanel tabPane = (JPanel) createLogTable();
		tabPane.setBorder(BorderFactory.createTitledBorder("Info"));

		playPane.add(btPane, BorderLayout.NORTH);
		playPane.add(tabPane, BorderLayout.CENTER);
		logPane.add(playPane, BorderLayout.CENTER);
		return logPane;
	}

	private Component createTagPane() {
		// tagPane
		JPanel tagPane = new JPanel();
		tagPane.setLayout(new BorderLayout());
		// lab
		JPanel labPane = new JPanel();
		labPane.setLayout(new FlowLayout());
		lab = new JLabel("状态:  未连接", JLabel.CENTER);
		lab.setForeground(Color.blue);
		labPane.add(lab);
		// bar
		JPanel barPane = new JPanel();
		barPane.setLayout(new FlowLayout());
		bar=proBar.get();
		barPane.add(bar);
		tagPane.add(labPane, BorderLayout.NORTH);
		tagPane.add(barPane, BorderLayout.CENTER);
		return tagPane;
	}

	private Component createSavePane() {
		savePane = new JPanel();
		savePane.setLayout(new BorderLayout());
		JPanel playPane = new JPanel();
		playPane.setBorder(BorderFactory.createTitledBorder("存储文件"));
		playPane.setLayout(new FlowLayout());
		JButton bRead = new JButton("读取");
		JButton bDel = new JButton("删除");
		JButton bContrl = new JButton("导出");
		playPane.add(bRead);
		playPane.add(bDel);
		playPane.add(bContrl);
		savePane.add(playPane, BorderLayout.CENTER);
		return savePane;
	}

	private JComponent createToolBar() {
		toolBar = new JToolBar("LED Manager: 播放管理");
		JButton bControl = new JButton("播放控制");
		JButton bLog = new JButton("日志管理");
		JButton bsave = new JButton("存储管理");
		bControl.setIcon(pngIcon.create("image//control//1.png"));
		bLog.setIcon(pngIcon.create("image//control//2.png"));
		bsave.setIcon(pngIcon.create("image//control//3.png"));
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(bControl);
		toolBar.add(Box.createHorizontalStrut(5));

		bControl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layout.show(container, Control_Edit);
				System.out.println("播放控制界面");
			}
		});

		toolBar.add(bLog);
		toolBar.add(Box.createHorizontalStrut(5));
		bLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layout.show(container, Log_Edit);
				System.out.println("日志文件界面");
			}
		});

		toolBar.add(bsave);
		toolBar.add(Box.createHorizontalStrut(5));
		bsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layout.show(container, Save_Edit);
				System.out.println("保存文件界面");

			}
		});

		JPanel toolBarPane = new JPanel(new GridLayout(1, 1));
		toolBarPane.add(toolBar);
		toolBarPane
				.setMaximumSize(new Dimension(Framework.screensize.width, 10));
		return toolBarPane;
	}

	private JComponent createUserTable() {
		tablePane = new JPanel();
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
					new PopupMenu(userTable, bar, lab).createMenu().show(
							e.getComponent(), e.getX(), e.getY());
			}
		});
		tablePane.add(scrollPane);
		return tablePane;
	}

	private JComponent createLogTable() {
		tablePane = new JPanel();
		tablePane.setLayout(new GridLayout(1, 1));
		String[] coloumnNames = { "用户名", "日志名", "读取时间" };
		String[][] rowData = null;
		logModel = new DefaultTableModel(rowData, coloumnNames);
		logTable = new JTable(logModel);
		logTable.setRowSorter(new TableRowSorter<>(logModel));
		logTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(logTable);
		tablePane.add(scrollPane);
		return tablePane;
	}
	
	/*
	 * 动作类：
	 * 		1、实现播放，暂停，停止等功能
	 * 		2、实现用户表的刷新和倒入功能
	 * 		3、实现鼠标右键弹出菜单等功能
	 * 		4、实现日志表的保存，删除和刷新等功能
	 * 
	 */
	
	public class playListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("开始播放");
		}
		
	}
	
	public class pauseListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class stopListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
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

	public class resetListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			while(boxModel.getSize()>0){
				boxModel.removeAllElements();
			}
			
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

	public class bContolListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// 清空表格
			while (logModel.getRowCount() > 0) {
				int i = logModel.getRowCount() - 1;
				logModel.removeRow(i);
			}
			// 向表格中添加数据
			Connection conn = null;
			try {
				conn = (Connection) ConnectionFactory.getInstance()
						.makeConnection();
				conn.setAutoCommit(false);
				ResultSet rs = DaoFactory.getLogDAOInstance().get(conn);
				conn.commit();
				while (rs.next()) {
					String[] rowValues = { rs.getString("userName"),
							rs.getString("logName"), rs.getString("sendTime") };
					logModel.addRow(rowValues);
				}
				System.out.println("向表格插入了" + logTable.getRowCount());

			} catch (Exception e2) {
				e2.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (Exception e3) {
					e3.printStackTrace();
				}

			}

		}

	}

	public class bDelListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedRow = logTable.getSelectedRow();
			Connection conn = null;
			String userName = null;
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(logTable, "请选中删除的行");
			} else {
				userName = (String) logTable.getValueAt(selectedRow, 0);
				System.out.println(userName);
				logModel.removeRow(selectedRow);
				System.out.println("向表格中删除了一行数据");
				try {
					conn = (Connection) ConnectionFactory.getInstance()
							.makeConnection();
					conn.setAutoCommit(false);
					Log logger = new Log();
					logger.setUserName(userName);
					DaoFactory.getLogDAOInstance().delete(conn, logger);
					conn.commit();
					System.out.println("向数据库中删除了一条信息");
				} catch (Exception e2) {
					e2.printStackTrace();
				} finally {
					try {
						conn.close();
					} catch (Exception e3) {
						e3.printStackTrace();
					}
				}
			}

		}
	}

	public class bReadListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Connection conn = null;
			int selectedRow = userTable.getSelectedRow();
			if (selectedRow == -1)
				JOptionPane.showMessageDialog(logTable, "未选中指定用户");
			String userName = (String) userTable.getValueAt(selectedRow, 0);
			String logName = "log.txt";
			// 向logTable中插入数据
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String receTime = df.format(new Date());
			String[] rowValues = { userName, logName, receTime };
			logModel.addRow(rowValues);
			System.out.println("向日志表格中插入了一条信息");
			// 向数据库中日志表中插入数据
			try {
				conn = (Connection) ConnectionFactory.getInstance()
						.makeConnection();
				conn.setAutoCommit(false);
				Log logger = new Log();
				logger.setUserName(userName);
				logger.setLogName(logName);
				DaoFactory.getLogDAOInstance().save(conn, logger);
				conn.commit();
				System.out.println("向日志表插入了一条信息");
			} catch (Exception e2) {
				e2.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		}
	}

}
