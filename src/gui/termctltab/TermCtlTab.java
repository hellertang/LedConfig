package gui.termctltab;

import gui.controltab.PopupMenu;
import gui.framework.Framework;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import com.mysql.jdbc.Connection;
import sql.daofactory.DaoFactory;
import utils.ConnectionFactory;
import utils.pngIcon;

public class TermCtlTab extends JPanel {
	private JToolBar toolBar = null;
	private JPanel termPane = null;
	private JPanel ctlPane = null;
	private JPanel tablePane = null;
	private JPanel infoPane = null;
	private JPanel workPane = null;
	private JSplitPane vSplitPane1 = null;

	private JButton update = null;

	private JTable table = null;
	private DefaultTableModel userModel;
	private DefaultComboBoxModel<String> boxModel = null;
	private Connection conn = null;
	private JComboBox<String> rowBox;
	
	private JLabel lab;
	private JProgressBar bar;
	private JScrollPane scrollPane;

	public TermCtlTab() {
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
		update = new JButton("刷新");
		update.setIcon(pngIcon.create("image//control//update.png"));
		update.addActionListener(new resetListener());
		rowPane.add(Box.createHorizontalStrut(5));
		rowPane.add(rowLab);
		rowPane.add(Box.createHorizontalStrut(5));
		rowPane.add(rowBox);
		rowPane.add(Box.createHorizontalGlue());
		rowPane.add(update);
		rowPane.setBorder(BorderFactory.createTitledBorder("组信息"));
		JPanel termList = new JPanel();
		termList.setBorder(BorderFactory.createTitledBorder("终端列表"));
		termList.add(createJTable());
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
		ctlPane.add(createInfoPane(), BorderLayout.CENTER);
		return ctlPane;
	}

	private Component createInfoPane() {
		infoPane = new JPanel();
		infoPane.setLayout(new BorderLayout());
		infoPane.add(new Clock(),BorderLayout.CENTER);
		infoPane.add(new timePane(scrollPane,table),BorderLayout.SOUTH);
		return infoPane;
	}

	private JComponent createToolBar() {
		toolBar = new JToolBar("LED Manager: 终端控制");
		JButton bControl = new JButton("系统配置");
		JButton bLog = new JButton("网络配置");
		JButton bsave = new JButton("服务器配置");
		bControl.setIcon(pngIcon.create("image//termCtl//1.png"));
		bLog.setIcon(pngIcon.create("image//termCtl//2.png"));
		bsave.setIcon(pngIcon.create("image//termCtl//3.png"));
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(bControl);
		toolBar.add(Box.createHorizontalStrut(5));
		
		toolBar.add(bLog);
		toolBar.add(Box.createHorizontalStrut(5));

		toolBar.add(bsave);
		toolBar.add(Box.createHorizontalStrut(5));

		JPanel toolBarPane = new JPanel(new GridLayout(1, 1));
		toolBarPane.add(toolBar);
		toolBarPane
				.setMaximumSize(new Dimension(Framework.screensize.width, 10));
		return toolBarPane;
	}

	private JComponent createJTable() {
		tablePane = new JPanel();
		tablePane.setLayout(new GridLayout(1, 1));
		String[] coloumnNames = { "终端名称", "IP", "Port" };
		String[][] rowData = null;
		userModel = new DefaultTableModel(rowData, coloumnNames);
		table = new JTable(userModel);
		table.setRowSorter(new TableRowSorter<>(userModel));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane = new JScrollPane(table);
		
		tablePane.add(scrollPane);
		return tablePane;
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

}
