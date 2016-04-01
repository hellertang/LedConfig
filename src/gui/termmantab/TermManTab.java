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

	// ����������
	private JComponent createToolBar() {
		JToolBar toolBar = new JToolBar("LED Manager: �ն˹���");

		JButton bSearch = new JButton("�����������ն�");
		JButton bNew = new JButton("�½���");
		JButton bDel = new JButton("������");
		JButton bImport = new JButton("�����ն�");
		JButton bExport = new JButton("�����ն�");
		bSearch.setIcon(pngIcon.create("image//termMge//1.png"));
		bNew.setIcon(pngIcon.create("image//termMge//2.png"));
		bDel.setIcon(pngIcon.create("image//termMge//3.png"));
		bImport.setIcon(pngIcon.create("image//termMge//4.png"));
		bExport.setIcon(pngIcon.create("image//termMge//5.png"));

		toolBar.add(Box.createHorizontalStrut(5));
		// �����������ն�
		toolBar.add(bSearch);
		bSearch.addActionListener(new ActionSearch());
		toolBar.add(Box.createHorizontalStrut(5));
		// �½���
		toolBar.add(bNew);
		bNew.addActionListener(new ActionNew());
		toolBar.add(Box.createHorizontalStrut(5));
		// ɾ����
		toolBar.add(bDel);
		bDel.addActionListener(new ActionDel());
		toolBar.add(Box.createHorizontalStrut(5));
		// �����ն�
		toolBar.add(bImport);
		bImport.addActionListener(new ActionImport());
		toolBar.add(Box.createHorizontalStrut(5));
		// �����ն�
		toolBar.add(bExport);
		bExport.addActionListener(new ActionExport());
		toolBar.add(Box.createHorizontalStrut(5));

		JPanel toolBarPane = new JPanel(new GridLayout(1, 1));
		toolBarPane.add(toolBar);
		toolBarPane
				.setMaximumSize(new Dimension(Framework.screensize.width, 10));

		return toolBarPane;
	}

	// ���������ռ�
	private Component createWorkspace() {
		JPanel workPane = new JPanel();
		JSplitPane vSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				createJTree(), createJTable());
		workPane.setLayout(new GridLayout(1, 1));
		workPane.add(vSplitPane);
		return workPane;
	}

	// �����ڵ���
	private Component createJTree() {
		JPanel treePane = new JPanel();
		treePane.setLayout(new BorderLayout());
		root = new DefaultMutableTreeNode("�����ն�");
		DefaultMutableTreeNode nodefirst = new DefaultMutableTreeNode("Ĭ��");
		root.add(nodefirst);
		treeRoot = new JTree(root);
		treeRoot.setEditable(true);
		model = (DefaultTreeModel) treeRoot.getModel();
		JButton btreset = new JButton("ˢ��");
		btreset.setIcon(pngIcon.create("image//termMge//update.png"));
		btreset.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.red));
		btreset.addActionListener(new ActionResetTree());
		JPanel pane = new JPanel();
		pane.setBorder(BorderFactory.createTitledBorder("����"));
		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
		pane.add(Box.createHorizontalGlue());
		pane.add(btreset);
		treeRoot.setBorder(BorderFactory.createTitledBorder("�û���"));
		treePane.add(treeRoot, BorderLayout.WEST);
		treePane.add(pane, BorderLayout.NORTH);
		return treePane;
	}

	// �������
	private JComponent createJTable() {
		JPanel tablePane = new JPanel();
		tablePane.setLayout(new BoxLayout(tablePane, BoxLayout.Y_AXIS));
		JPanel bpane = new JPanel();
		bpane.setLayout(new BoxLayout(bpane, BoxLayout.X_AXIS));
		bpane.setBorder(BorderFactory.createTitledBorder("����"));

		JButton btReset = new JButton("ˢ��");
		btReset.addActionListener(new ActionResetTable());
		btReset.setIcon(pngIcon.create("image//termMge//update.png"));
		btReset.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.green));
		JButton btClear = new JButton("���");
		btClear.addActionListener(new ActionClear());
		btClear.setIcon(pngIcon.create("image//termMge//del.png"));
		btClear.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.blue));
		JLabel label = new JLabel("�ն˸���:");
		Lab_num = new JLabel("0");
		bpane.add(label);
		bpane.add(Box.createHorizontalStrut(5));
		bpane.add(Lab_num);
		bpane.add(Box.createHorizontalGlue());
		bpane.add(btReset);
		bpane.add(Box.createHorizontalStrut(5));
		bpane.add(btClear);
		String[] coloumnNames = { "�ն�����", "IP", "Port", "��ʾ����С", "ɨ�跽ʽ", "״̬" };

		String[][] rowData = null;
		tableModel = new DefaultTableModel(rowData, coloumnNames);
		table = new JTable(tableModel);
		table.setRowSorter(new TableRowSorter<>(tableModel));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createTitledBorder("��Ϣ��"));
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
				System.out.println("���������" + table.getRowCount() + "������");
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
				JOptionPane.showMessageDialog(TermManTab.this, "��ѡ����!");
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
				JOptionPane.showMessageDialog(TermManTab.this, "��ѡ��Ҫɾ������!");
			} else {
				// �������ɾ��
				tableModel.removeRow(selectedRow);
				Lab_num.setText(String.valueOf(table.getRowCount()));
				// ���ݿ�����ɾ��
				try {
					conn = (Connection) ConnectionFactory.getInstance()
							.makeConnection();
					conn.setAutoCommit(false);
					User Tom = new User();
					Tom.setId(selectedRow + 1);
					DaoFactory.getUserDAOInstance().delete(conn, Tom);
					conn.commit();
					System.out.println("���û���ɾ����һ����Ϣ");
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
				JOptionPane.showMessageDialog(TermManTab.this, "���ȵ�����");
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
				JOptionPane.showMessageDialog(TermManTab.this, "���ȵ����ն�!");
			} else {
				Term_Info config = info.getConfig();
				chosen = (DefaultMutableTreeNode) treeRoot
						.getLastSelectedPathComponent();
				Connection conn = null;
				if (chosen == null) {
					JOptionPane.showMessageDialog(TermManTab.this, "��ѡ����!");
				} else {
					// ���ı���
					String[] rowValues = { chosen.toString(), config.getIp(),
							config.getPort(), config.getSize(),
							config.getMethod(), config.getState() };
					tableModel.addRow(rowValues);
					Lab_num.setText(String.valueOf(table.getRowCount()));

					// ���ݿⱣ��
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
						System.out.println("���û��������һ����Ϣ");
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
