package gui.termmantab;

import gui.framework.Framework;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.*;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import sql.daofactory.DaoFactory;
import sql.entity.User;
import utils.ConnectionFactory;
import utils.pngIcon;

import com.mysql.jdbc.Connection;

public class TermManTab extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DefaultMutableTreeNode root = null;
	private JTree treeRoot = null;

	private DefaultTreeModel model = null;
	private DefaultTableModel tableModel = null;
	private DefaultMutableTreeNode chosen = null;
	private JTable table = null;
	private JLabel Lab_num = null;

	private CreateUser user = null;
	private CreateInfo info = null;

	public TermManTab() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(createToolBar());
		add(Box.createVerticalStrut(5));
		add(createWorkspace());
	}

	// getters and setters
	public DefaultMutableTreeNode getRoot() {
		return root;
	}

	public void setRoot(DefaultMutableTreeNode root) {
		this.root = root;
	}

	public JTree getTreeRoot() {
		return treeRoot;
	}

	public void setTreeRoot(JTree treeRoot) {
		this.treeRoot = treeRoot;
	}

	public DefaultTreeModel getModel() {
		return model;
	}

	public void setModel(DefaultTreeModel model) {
		this.model = model;
	}

	// 创建工具条
	private JComponent createToolBar() {
		JToolBar toolBar = new JToolBar("LED Manager: 终端管理");

		JButton bSearch = new JButton("搜索局域网终端");
		JButton bNew = new JButton("新建组");
		JButton bDel = new JButton("导出组");
		JButton bImport = new JButton("导入终端");
		JButton bExport = new JButton("导出终端");
		bSearch.setIcon(pngIcon.create("image//termMge//1.png"));
		bNew.setIcon(pngIcon.create("image//termMge//2.png"));
		bDel.setIcon(pngIcon.create("image//termMge//3.png"));
		bImport.setIcon(pngIcon.create("image//termMge//4.png"));
		bExport.setIcon(pngIcon.create("image//termMge//5.png"));

		toolBar.add(Box.createHorizontalStrut(5));
		// 搜索局域网终端
		toolBar.add(bSearch);
		bSearch.addActionListener(new ActionSearch());
		toolBar.add(Box.createHorizontalStrut(5));
		// 新建组
		toolBar.add(bNew);
		bNew.addActionListener(new ActionNew());
		toolBar.add(Box.createHorizontalStrut(5));
		// 删除组
		toolBar.add(bDel);
		bDel.addActionListener(new ActionDel());
		toolBar.add(Box.createHorizontalStrut(5));
		// 导入终端
		toolBar.add(bImport);
		bImport.addActionListener(new ActionImport());
		toolBar.add(Box.createHorizontalStrut(5));
		// 导出终端
		toolBar.add(bExport);
		bExport.addActionListener(new ActionExport());
		toolBar.add(Box.createHorizontalStrut(5));

		JPanel toolBarPane = new JPanel(new GridLayout(1, 1));
		toolBarPane.add(toolBar);
		toolBarPane
				.setMaximumSize(new Dimension(Framework.screensize.width, 10));

		return toolBarPane;
	}

	// 创建工作空间
	private Component createWorkspace() {
		JPanel workPane = new JPanel();
		JSplitPane vSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				createJTree(), createJTable());
		workPane.setLayout(new GridLayout(1, 1));
		workPane.add(vSplitPane);
		return workPane;
	}

	// 创建节点树
	private Component createJTree() {
		JPanel treePane = new JPanel();
		treePane.setLayout(new BorderLayout());
		root = new DefaultMutableTreeNode("所有终端");
		DefaultMutableTreeNode nodefirst = new DefaultMutableTreeNode("默认");
		root.add(nodefirst);
		treeRoot = new JTree(root);
		treeRoot.setEditable(true);
		model = (DefaultTreeModel) treeRoot.getModel();
		JButton btreset = new JButton("刷新");
		btreset.setIcon(pngIcon.create("image//termMge//update.png"));
		btreset.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.red));
		btreset.addActionListener(new ActionResetTree());
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createTitledBorder("工具"));
		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
		pane.add(Box.createHorizontalGlue());
		pane.add(btreset);
		treeRoot.setBorder(BorderFactory.createTitledBorder("用户区"));
		treePane.add(treeRoot, BorderLayout.WEST);
		treePane.add(pane, BorderLayout.NORTH);
		return treePane;
	}

	// 创建表格
	private JComponent createJTable() {
		JPanel tablePane = new JPanel();
		tablePane.setLayout(new BoxLayout(tablePane, BoxLayout.Y_AXIS));
		JPanel bpane = new JPanel();
		bpane.setLayout(new BoxLayout(bpane, BoxLayout.X_AXIS));
		bpane.setBorder(BorderFactory.createTitledBorder("工具"));

		JButton btReset = new JButton("刷新");
		btReset.addActionListener(new ActionResetTable());
		btReset.setIcon(pngIcon.create("image//termMge//update.png"));
		btReset.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.green));
		JButton btClear = new JButton("清空");
		btClear.addActionListener(new ActionClear());
		btClear.setIcon(pngIcon.create("image//termMge//del.png"));
		btClear.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.blue));
		JLabel label = new JLabel("终端个数:");
		Lab_num = new JLabel("0");
		bpane.add(label);
		bpane.add(Box.createHorizontalStrut(5));
		bpane.add(Lab_num);
		bpane.add(Box.createHorizontalGlue());
		bpane.add(btReset);
		bpane.add(Box.createHorizontalStrut(5));
		bpane.add(btClear);
		String[] coloumnNames = { "终端名称", "IP", "Port", "显示屏大小", "扫描方式", "状态" };

		String[][] rowData = null;
		tableModel = new DefaultTableModel(rowData, coloumnNames);
		table = new JTable(tableModel);
		table.setRowSorter(new TableRowSorter<>(tableModel));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createTitledBorder("信息区"));
		scrollPane.setViewportView(table);
		tablePane.add(bpane);
		tablePane.add(scrollPane);
		return tablePane;
	}

	public class ActionSearch implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int i = 0;
			while (tableModel.getRowCount() > 0) {
				i = tableModel.getRowCount() - 1;
				tableModel.removeRow(i);
			}

			Connection conn = null;
			try {
				conn = (Connection) ConnectionFactory.getInstance()
						.makeConnection();
				conn.setAutoCommit(false);
				ResultSet rs = DaoFactory.getUserDAOInstance().get(conn);
				conn.commit();
				while (rs.next()) {
					String[] rowValues = { rs.getString("name"),
							rs.getString("ip"), rs.getString("port"),
							rs.getString("size"), rs.getString("method"),
							rs.getString("state") };
					tableModel.addRow(rowValues);
				}
				System.out.println("想表格插入了" + table.getRowCount() + "条数据");
				Lab_num.setText(String.valueOf(table.getRowCount()));
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

	public class ActionNew implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			user = new CreateUser();
		}
	}

	public class ActionDel implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeRoot
					.getLastSelectedPathComponent();
			if (selectedNode == null || selectedNode.getParent() == null) {
				JOptionPane.showMessageDialog(TermManTab.this, "请选择组!");
			} else {
				model.removeNodeFromParent(selectedNode);
			}
		}

	}

	public class ActionImport implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			info = new CreateInfo();
		}

	}

	public class ActionExport implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedRow = table.getSelectedRow();
			System.out.println("Selected===========>" + selectedRow);
			Connection conn = null;
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(TermManTab.this, "请选择要删除的行!");
			} else {
				// 表格数据删除
				tableModel.removeRow(selectedRow);
				Lab_num.setText(String.valueOf(table.getRowCount()));
				// 数据库数据删除
				try {
					conn = (Connection) ConnectionFactory.getInstance()
							.makeConnection();
					conn.setAutoCommit(false);
					User Tom = new User();
					Tom.setId(selectedRow + 1);
					DaoFactory.getUserDAOInstance().delete(conn, Tom);
					conn.commit();
					System.out.println("向用户表删除了一条信息");
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
	}

	public class ActionResetTree implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (user == null) {
				JOptionPane.showMessageDialog(TermManTab.this, "请先导入组");
			} else {
				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
						user.getStr());
				DefaultMutableTreeNode chosen = root;
				model.insertNodeInto(newNode, chosen, 0);
				user=null;
			}
		}

	}

	public class ActionResetTable implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (info == null) {
				JOptionPane.showMessageDialog(TermManTab.this, "请先导入终端!");
			} else {
				Term_Info config = info.getConfig();
				chosen = (DefaultMutableTreeNode) treeRoot
						.getLastSelectedPathComponent();
				Connection conn = null;
				if (chosen == null) {
					JOptionPane.showMessageDialog(TermManTab.this, "请选择组!");
				} else {
					// 表格的保存
					String[] rowValues = { chosen.toString(), config.getIp(),
							config.getPort(), config.getSize(),
							config.getMethod(), config.getState() };
					tableModel.addRow(rowValues);
					Lab_num.setText(String.valueOf(table.getRowCount()));

					// 数据库保存
					try {
						conn = (Connection) ConnectionFactory.getInstance()
								.makeConnection();
						conn.setAutoCommit(false);
						User Tom = new User();
						Tom.setName(chosen.toString());
						Tom.setIp(config.getIp());
						Tom.setPort(config.getPort());
						Tom.setSize(config.getSize());
						Tom.setMethod(config.getMethod());
						Tom.setState(config.getState());
						DaoFactory.getUserDAOInstance().save(conn, Tom);
						conn.commit();
						System.out.println("向用户表插入了一条信息");
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
		}
	}

	public class ActionClear implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			while (tableModel.getRowCount() > 0) {
				int i = tableModel.getRowCount() - 1;
				tableModel.removeRow(i);
			}
			Lab_num.setText("0");
		}

	}

}
