package gui.sendtab;

import gui.framework.Framework;
import gui.termmantab.TermManTab;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import sql.daofactory.DaoFactory;
import sql.entity.Filer;
import sql.entity.Txt;
import sql.entity.User;
import sql.entity.Video;
import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import utils.ConnectionFactory;
import utils.MyColor;
import utils.Ping;
import utils.PlayerPanel;
import utils.pngIcon;

import com.mysql.jdbc.Connection;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import utils.proBar;

/**
 * @author Big_Tang
 *
 */
public class SendTab extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String Default_Edit = "Normal";
	private static final String Normal_Edit = "Normal";
	private static final String Emergcy_Edit = "Emergcy";
	private static final String Imediate_Edit = "Imediate";
	private static final String Manage_Edit = "Manage";

	private JToolBar toolBar = null;
	private JPanel Container = null;
	private JPanel EmergPane = null;
	private JPanel ImediatePane = null;
	private JPanel EditPane = null;
	private JPanel ManagePane = null;

	private JTable table = null;

	private Txt_EditPane txtEdit_Pane = null;

	private CardLayout layout = null;

	private DefaultTableModel normalModel;

	public static JTextField filename = null;
	public static JTextField filepath = null;
	private JLabel numText = null;
	private int i;

	private JEditorPane text;

	private String path;
	private JProgressBar bar;
	private JLabel lab;
	public txtAttribute txtAttr;
	public videoAttribute videoAttr;
	private JPanel NormPane;

	private DefaultTableModel fileModel;
	private JTable fileTab;

	private DefaultTableModel videoModel;
	private JTable videoTab;

	private DefaultTableModel txtModel;
	private JTable txtTab;

	public SendTab() {
		super();
		txtAttr = new txtAttribute();
		videoAttr = new videoAttribute();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(createToolBar());
		// add(Box.createVerticalStrut(5));
		add(createTagPane());
		// add(Box.createVerticalStrut(5));
		layout = new CardLayout();
		Container = new JPanel(layout);
		Container.add(createNormArea(), Normal_Edit);
		Container.add(createEmergArea(), Emergcy_Edit);
		Container.add(createImediateArea(), Imediate_Edit);
		Container.add(createManageArea(), Manage_Edit);
		layout.show(Container, Default_Edit);
		add(Container);

	}

	public txtAttribute getTxtAttr() {
		return txtAttr;
	}

	public void setTxtAttr(txtAttribute txtAttr) {
		this.txtAttr = txtAttr;
	}

	public JEditorPane getText() {
		return text;
	}

	public void setText(JTextPane text) {
		this.text = text;
	}

	public void setText(File file) {
		String s1 = file.getPath();
		String s2 = file.getName();
		String s = s1.replace(s2, "");
		filename.setText(s2);
		filepath.setText(s);

	}

	// ��������ʾ���ִ���
	private JComponent createTagPane() {
		// tagPane
		JPanel tagPane = new JPanel();
		tagPane.setLayout(new BorderLayout());
		// lab
		JPanel labPane = new JPanel();
		labPane.setLayout(new FlowLayout());
		lab = new JLabel("״̬:      δ����                ", JLabel.CENTER);
		lab.setForeground(Color.blue);
		JButton btLink = new JButton("����");
		btLink.setIcon(pngIcon.create("image//link.png"));
		btLink.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (btLink.getText().equals("����")) {
					if (Ping.get().getIP() != null) {
						JOptionPane.showMessageDialog(btLink, "���ˢ�°�ť");
					} else {
						new tabLink(bar, lab, btLink);
					}

				} else if (btLink.getText().equals("�Ͽ�")) {
					if (Ping.get().getIP() == null) {
						JOptionPane.showMessageDialog(btLink, "���ˢ�°�ť");
					} else {
						lab.setText("״̬:      δ����                ");
						bar.setValue(0);
						btLink.setText("����");
						Ping.get().setIP(null);
						Ping.get().setIP(null);
					}
				} else {
					System.exit(1);
				}

			}
		});
		JButton btReset = new JButton("ˢ��");
		btReset.setIcon(pngIcon.create("image//update.png"));
		btReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Ping.get().getIP() != null) {
					lab.setText("���ӳɹ�:   IP:" + Ping.get().getIP() + "  Port:"
							+ Ping.get().getPort());
					bar.setValue(30);
					btLink.setText("�Ͽ�");
				} else {
					lab.setText("״̬:      δ����                ");
					bar.setValue(0);
					btLink.setText("����");
				}

			}
		});
		labPane.add(lab);
		labPane.add(Box.createHorizontalStrut(5));
		labPane.add(btLink);
		labPane.add(btReset);

		// bar
		JPanel barPane = new JPanel();
		barPane.setLayout(new FlowLayout());
		bar = new JProgressBar(1, 30);
		bar.setBorderPainted(true);
		bar.setStringPainted(true);
		bar.setPreferredSize(new Dimension(400, 40));
		barPane.add(bar);
		tagPane.add(labPane, BorderLayout.NORTH);
		tagPane.add(barPane, BorderLayout.CENTER);
		return tagPane;
	}

	// ����������
	private JComponent createToolBar() {
		toolBar = new JToolBar("LED Manager: ��������");

		JButton bNormal = new JButton("��������");
		JButton bEmergen = new JButton("�����岥");
		JButton bImediate = new JButton("��ʱ֪ͨ");
		JButton bManage = new JButton("��Ϣ����");
		bNormal.setIcon(pngIcon.create("image//send//1.png"));
		bEmergen.setIcon(pngIcon.create("image//send//2.png"));
		bImediate.setIcon(pngIcon.create("image//send//3.png"));
		bManage.setIcon(pngIcon.create("image//send//4.png"));

		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(bNormal);
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(bEmergen);
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(bImediate);
		toolBar.add(Box.createHorizontalStrut(5));
		toolBar.add(bManage);

		bNormal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layout.show(Container, Normal_Edit);
				System.out.println("Normal Frame");
			}
		});

		bEmergen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layout.show(Container, Emergcy_Edit);
				System.out.println("Emergcy Frame");
			}
		});

		bImediate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layout.show(Container, Imediate_Edit);
				System.out.println("Imediate Frame");
			}
		});

		bManage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				layout.show(Container, Manage_Edit);
				System.out.println("Manage Frame");
			}
		});

		JPanel toolBarPane = new JPanel(new GridLayout(1, 1));
		toolBarPane.add(toolBar);
		toolBarPane
				.setMaximumSize(new Dimension(Framework.screensize.width, 40));
		return toolBarPane;
	}

	/**
	 * @return Normal_Edit ���ܣ�ʵ�ֽ������Ź��ܣ�����������
	 */
	private Component createNormArea() {
		NormPane = new JPanel();
		NormPane.setLayout(new GridLayout(1, 1));
		// JSplitPane vsPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
		// tabPane.createNormTab(),new NormalPane().createNormalPane() );
		//
		// NormPane.add(vsPane);
		NormPane.add(new NormalPane().createNormalPane());
		return NormPane;
	}

	/**
	 * @return Emerg_Edit ���ܣ�ʵ�ֽ����岥���ܣ��岥��Ƶ��
	 */
	private Component createEmergArea() {
		EmergPane = new JPanel();
		EmergPane
				.setMaximumSize(new Dimension(Framework.screensize.width, 100));
		EmergPane.setLayout(new GridLayout(1, 1));
		// JSplitPane vsPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
		// tabPane.createVideoTab(),new VideoPane().createVideoPane());
		// EmergPane.add(vsPane,BorderLayout.CENTER);
		EmergPane.add(new VideoPane().createVideoPane());
		return EmergPane;
	}

	/**
	 * @return Imediate_Edit ���ܣ�ʵ�ּ�ʱ֪ͨ���ܣ��岥֪ͨ��
	 */
	private Component createImediateArea() {
		ImediatePane = new JPanel();
		ImediatePane.setMaximumSize(new Dimension(Framework.screensize.width,
				100));
		ImediatePane.setLayout(new GridLayout(1, 1));
		// JSplitPane vsPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
		// tabPane.createTxtTab(),new TxtPane().createTxtPane());
		// ImediatePane.add(vsPane);
		ImediatePane.add(new TxtPane().createTxtPane());
		return ImediatePane;
	}

	private Component createManageArea() {
		ManagePane = new JPanel();
		ImediatePane.setMaximumSize(new Dimension(Framework.screensize.width,
				100));
		ManagePane.add(new TabPane().createTabPane());

		return ManagePane;
	}

	public class TabPane {
		private JPanel normTab;
		private JPanel emergTab;
		private JPanel imediaTab;
		private CardLayout card;
		private JPanel Pane;

		private final Dimension screensize = Toolkit.getDefaultToolkit()
				.getScreenSize();

		private static final String file_Edit = "fileTab";
		private static final String video_Edit = "videoTab";
		private static final String txt_Edit = "txtTab";
		private static final String default_Edit = "fileTab";

		private Component createTabPane() {
			JPanel tabPane = new JPanel();
			tabPane.setLayout(new BoxLayout(tabPane, BoxLayout.Y_AXIS));
			Pane = new JPanel();
			card = new CardLayout();
			Pane.setLayout(card);
			Pane.add(createNormTab(), file_Edit);
			Pane.add(createVideoTab(), video_Edit);
			Pane.add(createTxtTab(), txt_Edit);
			Pane.add(createNormTab(), default_Edit);
			JPanel btPane = new JPanel();
			JButton btNorm = new JButton("��Ŀ��");
			JButton btEmerg = new JButton("�岥��");
			JButton btImediate = new JButton("��ʱ��");
			btNorm.setIcon(pngIcon.create("image//send//table.png"));
			btEmerg .setIcon(pngIcon.create("image//send//video.png"));
			btImediate.setIcon(pngIcon.create("image//send//txt.png"));
			btNorm.setUI(new BEButtonUI()
			.setNormalColor(BEButtonUI.NormalColor.red));
			btEmerg.setUI(new BEButtonUI()
			.setNormalColor(BEButtonUI.NormalColor.blue));
			btImediate.setUI(new BEButtonUI()
			.setNormalColor(BEButtonUI.NormalColor.green));
			btPane.setLayout(new FlowLayout());
			btPane.add(btNorm);
			btPane.add(btEmerg);
			btPane.add(btImediate);
			btNorm.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					card.show(Pane, file_Edit);

				}
			});
			btEmerg.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					card.show(Pane, video_Edit);
				}
			});

			btImediate.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					card.show(Pane, txt_Edit);
				}
			});

			tabPane.add(btPane);
			tabPane.add(Pane);
			return tabPane;

		}

		private Component createNormTab() {
			normTab = new JPanel();
			normTab.setPreferredSize(new Dimension(screensize.width / 2,
					screensize.height / 3));
			normTab.setBorder(BorderFactory.createTitledBorder("��Ŀ����"));
			// normTab.setLayout(new BorderLayout());
			normTab.setLayout(new BoxLayout(normTab, BoxLayout.Y_AXIS));

			JPanel btPane = new JPanel();
			btPane.setLayout(new FlowLayout());
			JButton bImport = new JButton("����");
//			bImport.setUI(new BEButtonUI()
//					.setNormalColor(BEButtonUI.NormalColor.green));
			bImport.setIcon(pngIcon.create("image//send//search.png"));
			bImport.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					while (fileModel.getRowCount() > 0) {
						int i = fileModel.getRowCount() - 1;
						fileModel.removeRow(i);
					}
					// �������������
					Connection conn = null;
					try {
						conn = (Connection) ConnectionFactory.getInstance()
								.makeConnection();
						conn.setAutoCommit(false);
						ResultSet rs = DaoFactory.getFileDAOInstance()
								.get(conn);
						conn.commit();
						while (rs.next()) {
							String[] rowValues = { rs.getString("userName"),
									rs.getString("fileName"),
									rs.getString("filePath"),
									rs.getString("sendTime") };
							fileModel.addRow(rowValues);
						}
						System.out.println("���������" + fileModel.getRowCount());

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
			});
			JButton bDel = new JButton("ɾ��");
//			bDel.setUI(new BEButtonUI()
//					.setNormalColor(BEButtonUI.NormalColor.red));
			bDel.setIcon(pngIcon.create("image//send//del.png"));
			bDel.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedRow = fileTab.getSelectedRow();
					Connection conn = null;
					String userName = null;
					if (selectedRow == -1) {
						JOptionPane.showMessageDialog(fileTab, "��ѡ��Ҫɾ������!");
					} else {
						// �������ɾ��
						userName = (String) fileTab.getValueAt(selectedRow, 0);
						fileModel.removeRow(selectedRow);
						System.out.println(userName);
						try {
							conn = (Connection) ConnectionFactory.getInstance()
									.makeConnection();
							conn.setAutoCommit(false);
							Filer file = new Filer();
							file.setUserName(userName);
							DaoFactory.getFileDAOInstance().delete(conn, file);
							conn.commit();
							System.out.println("�����ݿ���ɾ����һ����Ϣ");

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
			});
			btPane.add(bImport);
			btPane.add(bDel);

			JPanel tablePane = new JPanel();
			tablePane.setLayout(new GridLayout(1, 1));
			String[] coloumnNames = { "�û���", "�ļ���", "�ļ�·��", "��ȡʱ��" };
			String[][] rowData = null;
			fileModel = new DefaultTableModel(rowData, coloumnNames);
			fileTab = new JTable(fileModel);
			fileTab.setRowSorter(new TableRowSorter<>(fileModel));
			fileTab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scrollPane = new JScrollPane(fileTab);
			tablePane.add(scrollPane);
			normTab.add(btPane);
			normTab.add(tablePane);
			return normTab;

		}

		private Component createVideoTab() {
			emergTab = new JPanel();
			emergTab.setPreferredSize(new Dimension(200, 200));
			emergTab.setBorder(BorderFactory.createTitledBorder("�岥��"));
			emergTab.setLayout(new BoxLayout(emergTab, BoxLayout.Y_AXIS));

			JPanel btPane = new JPanel();
			btPane.setLayout(new FlowLayout());
			JButton bImport = new JButton("����");
			bImport.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					while (videoModel.getRowCount() > 0) {
						int i = videoModel.getRowCount() - 1;
						videoModel.removeRow(i);
					}
					
					Connection conn = null;
					try {
						conn = (Connection) ConnectionFactory.getInstance()
								.makeConnection();
						conn.setAutoCommit(false);
						ResultSet rs = DaoFactory.getVideoDaoInstance().get(conn);
						conn.commit();
						while (rs.next()) {
							String[] rowValues = { rs.getString("userName"),
									rs.getString("fileName"),
									rs.getString("filePath"),
									rs.getString("sendTime") };
							videoModel.addRow(rowValues);
						}
						System.out.println("���������" + videoModel.getRowCount());

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
			});
			bImport.setUI(new BEButtonUI()
					.setNormalColor(BEButtonUI.NormalColor.green));
			JButton bDel = new JButton("ɾ��");
			bDel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedRow = videoTab.getSelectedRow();
					Connection conn = null;
					String userName = null;
					if (selectedRow == -1) {
						JOptionPane.showMessageDialog(videoTab, "��ѡ��Ҫɾ������!");
					} else {
						// �������ɾ��
						userName = (String) videoTab.getValueAt(selectedRow, 0);
						videoModel.removeRow(selectedRow);
						System.out.println(userName);
						try {
							conn = (Connection) ConnectionFactory.getInstance()
									.makeConnection();
							conn.setAutoCommit(false);
							Video video = new Video();
							video.setUserName(userName);
							DaoFactory.getVideoDaoInstance().delete(conn, video);
							conn.commit();
							System.out.println("�����ݿ���ɾ����һ����Ϣ");

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
			});
			bDel.setUI(new BEButtonUI()
					.setNormalColor(BEButtonUI.NormalColor.red));
			btPane.add(bImport);
			btPane.add(bDel);
			
			

			JPanel tablePane = new JPanel();
			tablePane.setLayout(new GridLayout(1, 1));
			String[] coloumnNames = { "�û���", "�ļ���", "�ļ�·��", "����ʱ��" };
			String[][] rowData = null;
			videoModel = new DefaultTableModel(rowData, coloumnNames);
			videoTab = new JTable(videoModel);
			videoTab.setRowSorter(new TableRowSorter<>(videoModel));
			videoTab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scrollPane = new JScrollPane(videoTab);
			tablePane.add(scrollPane);
			emergTab.add(btPane);
			emergTab.add(tablePane);

			return emergTab;
		}

		private Component createTxtTab() {
			imediaTab = new JPanel();
			imediaTab.setPreferredSize(new Dimension(200, 200));
			imediaTab.setBorder(BorderFactory.createTitledBorder("��ʱ֪ͨ��"));
			imediaTab.setLayout(new BoxLayout(imediaTab, BoxLayout.Y_AXIS));

			JPanel btPane = new JPanel();
			btPane.setLayout(new FlowLayout());
			JButton bImport = new JButton("����");
			bImport.setUI(new BEButtonUI()
					.setNormalColor(BEButtonUI.NormalColor.green));
			bImport.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					while (txtModel.getRowCount() > 0) {
						int i = txtModel.getRowCount() - 1;
						txtModel.removeRow(i);
					}
					// �������������
					Connection conn = null;
					try {
						conn = (Connection) ConnectionFactory.getInstance()
								.makeConnection();
						conn.setAutoCommit(false);
						ResultSet rs = DaoFactory.getTxtDAOInstance()
								.get(conn);
						conn.commit();
						while (rs.next()) {
							String[] rowValues = { rs.getString("userName"),
									rs.getString("fileName"),
									rs.getString("filePath"),
									rs.getString("sendTime") };
							txtModel.addRow(rowValues);
						}
						System.out.println("���������" + txtModel.getRowCount());

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
			});
			JButton bDel = new JButton("ɾ��");
			bDel.setUI(new BEButtonUI()
					.setNormalColor(BEButtonUI.NormalColor.red));
			bDel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					int selectedRow = txtTab.getSelectedRow();
					Connection conn = null;
					String userName = null;
					if (selectedRow == -1) {
						JOptionPane.showMessageDialog(txtTab, "��ѡ��Ҫɾ������!");
					} else {
						// �������ɾ��
						userName = (String) txtTab.getValueAt(selectedRow, 0);
						txtModel.removeRow(selectedRow);
						System.out.println(userName);
						try {
							conn = (Connection) ConnectionFactory.getInstance()
									.makeConnection();
							conn.setAutoCommit(false);
							Txt	txt = new Txt();
							txt.setUserName(userName);
							DaoFactory.getTxtDAOInstance().delete(conn, txt);
							conn.commit();
							System.out.println("�����ݿ���ɾ����һ����Ϣ");

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
			});
			btPane.add(bImport);
			btPane.add(bDel);

			JPanel tablePane = new JPanel();
			tablePane.setLayout(new GridLayout(1, 1));
			String[] coloumnNames = { "�û���", "�ļ���", "�ļ�·��", "����ʱ��" };
			String[][] rowData = null;
			txtModel = new DefaultTableModel(rowData, coloumnNames);
			txtTab = new JTable(txtModel);
			txtTab.setRowSorter(new TableRowSorter<>(txtModel));
			txtTab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scrollPane = new JScrollPane(txtTab);
			tablePane.add(scrollPane);
			imediaTab.add(btPane);
			imediaTab.add(tablePane);
			return imediaTab;
		}
	}

	/*
	 * NormalPane ���������ࣺ ʵ�ֹ��ܣ� 1��������Ŀ������Ӻͷ��� 2����Ŀ���������ݵ���ӣ�ɾ���Ȳ�����
	 */

	public class NormalPane {
		private JPanel processPane;
		private JPanel tablePane;
		private JPanel viewPane;
		private JPanel normPane;

		public Component createNormalPane() {
			normPane = new JPanel();
			normPane.setLayout(new BoxLayout(normPane, BoxLayout.Y_AXIS));
			normPane.setBorder(BorderFactory.createTitledBorder("�����б�"));
			normPane.add(createViewPane());
			normPane.add(createJTable());
			normPane.add(createProcessPane());
			return normPane;

		}

		private Component createViewPane() {
			viewPane = new JPanel();
			viewPane.setLayout(new BoxLayout(viewPane, BoxLayout.X_AXIS));
			JLabel numLab = new JLabel("�ļ����� : ");
			numText = new JLabel("     ");

			JButton breset = new JButton("����");
			breset.setIcon(pngIcon.create("image//termMge//update.png"));
			breset.setUI(new BEButtonUI()
					.setNormalColor(BEButtonUI.NormalColor.green));
			breset.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int selectedRow = table.getSelectedColumn();
					if (selectedRow != -1)
						normalModel.removeRow(selectedRow);
				}

			});
			JButton bdelete = new JButton("���");
			bdelete.setIcon(pngIcon.create("image//termMge//del.png"));
			bdelete.setUI(new BEButtonUI()
			.setNormalColor(BEButtonUI.NormalColor.red));
			bdelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					while (normalModel.getRowCount() > 0) {
						i = normalModel.getRowCount() - 1;
						normalModel.removeRow(i);
					}
				}
			});
			viewPane.add(Box.createHorizontalStrut(5));
			viewPane.add(numLab);
			viewPane.add(numText);
			viewPane.add(Box.createGlue());
			viewPane.add(breset);
			viewPane.add(Box.createHorizontalStrut(5));
			viewPane.add(bdelete);
			return viewPane;
		}

		private JComponent createJTable() {
			tablePane = new JPanel();
			tablePane.setLayout(new GridLayout(1, 1));
			Object[][] Data = {};
			Object[] ColumnNames = { "�ļ���", "�ļ�·��", "����" };
			normalModel = new DefaultTableModel(Data, ColumnNames);
			table = new JTable(normalModel);
			table.setRowSorter(new TableRowSorter<>(normalModel));
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setViewportView(table);
			ButtonColumn buttonsColumn = new ButtonColumn(table, 2);
			tablePane.add(scrollPane);
			return tablePane;
		}

		private Component createProcessPane() {
			processPane = new JPanel();
			processPane.setLayout(new BoxLayout(processPane, BoxLayout.X_AXIS));
			JLabel nameLab = new JLabel("�ļ���:");
			filename = new JTextField();
			filename.setColumns(10);
			JLabel pathLab = new JLabel("·��:");
			filepath = new JTextField();
			filepath.setColumns(10);
			JButton bOk = new JButton("ȷ��");
			bOk.setIcon(pngIcon.create("image//send//sure.png"));
			bOk.setUI(new BEButtonUI()
			.setNormalColor(BEButtonUI.NormalColor.red));
			bOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String[] rowValues = { filename.getText(),
							filepath.getText(), "SEND" };
					normalModel.addRow(rowValues);
					int j = normalModel.getRowCount();
					String s = String.valueOf(j);
					numText.setText(s);
				}
			});
			JButton bNo = new JButton("ȡ��");
			bNo.setIcon(pngIcon.create("image//send//cancel.png"));
			bNo.setUI(new BEButtonUI()
			.setNormalColor(BEButtonUI.NormalColor.green));
			bNo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					filename.setText("");
					filepath.setText("");
				}
			});
			processPane.add(nameLab);
			processPane.add(Box.createHorizontalStrut(5));
			processPane.add(filename);
			processPane.add(Box.createHorizontalStrut(5));
			processPane.add(pathLab);
			processPane.add(Box.createHorizontalStrut(5));
			processPane.add(filepath);
			processPane.add(Box.createHorizontalStrut(5));
			processPane.add(bOk);
			processPane.add(Box.createHorizontalStrut(5));
			processPane.add(bNo);
			return processPane;
		}
	}

	/*
	 * VideoPane �����岥�ࣺ ʵ�ֹ��ܣ� 1����Ƶ�ļ��ı��棬��ȡ������ 2���ļ��Ĵ�С�����Ŵ��������� 3����Ƶ�ļ��Ŀ���
	 */

	public class VideoPane {
		private JPanel videoPane;
		private JPanel videoEdit;
		private JPanel videoSet;
		private JPopupMenu popup;
		private PlayerPanel playPane = new PlayerPanel();
		private File file;

		private Component createVideoPane() {
			videoPane = new JPanel();
			videoPane.setBorder(BorderFactory.createTitledBorder("Play"));
			videoPane.setLayout(new BoxLayout(videoPane, BoxLayout.Y_AXIS));
			videoPane.add(videoEditPane());
			videoPane.add(videoSetPane());
			return videoPane;
		}

		private Component videoEditPane() {
			videoEdit = new JPanel();
			videoEdit.setLayout(new FlowLayout());
			videoEdit.setBackground(MyColor.GRAY_BACKGROUND);
			JPanel vidPane = new JPanel();
			vidPane.setPreferredSize(new Dimension(500, 300));
			vidPane.setLayout(new BorderLayout());
			vidPane.setBackground(MyColor.BLACK);
			playPane.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						getPopup().show(e.getComponent(), e.getX(), e.getY());
					}
				}
			});
			vidPane.add(playPane, BorderLayout.CENTER);
			videoEdit.add(vidPane);
			return videoEdit;

		}

		private Component videoSetPane() {
			videoSet = (JPanel) new Video_EditPane(playPane, videoAttr)
					.createVideoPane();
			videoSet.setBorder(BorderFactory.createTitledBorder("default"));
			return videoSet;
		}

		private JPopupMenu getPopup() {
			if (popup == null) {
				popup = new JPopupMenu("Popup");

				JMenuItem open = new JMenuItem("��");
				open.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser chooser = new JFileChooser();
						chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						int v = chooser.showOpenDialog(null);
						if (v == JFileChooser.APPROVE_OPTION) {
							file = chooser.getSelectedFile();
						}
						if (file == null) {
							System.out.println("û��ѡ���ļ�");
						} else {
							playPane.prepareMedia(file);
						}
					}
				});
				popup.add(open);

				JMenuItem save = new JMenuItem("����");
				save.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser chooser = new JFileChooser();
						chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						int v = chooser.showOpenDialog(null);
						if (v == JFileChooser.APPROVE_OPTION) {
							path = chooser.getSelectedFile().getAbsolutePath();
						}
						if (path == null) {
							System.out.println("û��ѡ���ļ�");
						} else {
							try {
								createLedFile(path);
								if (file == null) {
									System.out.println("û��ѡ����Ƶ�ļ�");
								} else {
									saveVideoFile(path, file);
									createXmlFile(path, videoAttr);
								}

							} catch (IOException e1) {
								e1.printStackTrace();
							}

						}
					}
				});
				popup.add(save);

				JMenuItem send = new JMenuItem("����");
				send.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

					}
				});
				popup.add(send);
				popup.setInvoker(videoEdit);
			}
			return popup;
		}

		private void createLedFile(String path) throws IOException {
			String filename = "Manager.led";
			File file = new File(path, filename);
			if (file.exists()) {
				file.delete();
				System.out.println("ɾ���ɹ�");
			} else {
				file.createNewFile();
				System.out.println("�����ɹ�");
			}
			String Text = "This is the LED Directory,You can find it by it!";
			FileWriter out = new FileWriter(file);
			out.write(Text);
			out.close();
		}

		private void saveVideoFile(String path, File file) throws IOException {
			int bytesum = 0;
			int byteread = 0;
			try {
				path = path + "//" + file.getName();
				if (file.exists()) {
					InputStream inStream = new FileInputStream(file);
					FileOutputStream fs = new FileOutputStream(path);
					byte[] buffer = new byte[1444];
					while ((byteread = inStream.read(buffer)) != -1) {
						bytesum += byteread;
						fs.write(buffer, 0, byteread);
					}
					inStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		private void createXmlFile(String path, videoAttribute videoAttr)
				throws IOException {
			int times = videoAttr.getTimes();
			String ratio = videoAttr.getRatio();

			Document document = new Document();
			Element root = new Element("root");
			document.addContent(root);
			Element node = new Element("node").setText("1");
			root.addContent(node);

			Element attrNode = new Element("node");
			attrNode.setAttribute("mediatype", "2")
					.setAttribute("location", path)
					.setAttribute("times", String.valueOf(times))
					.setAttribute("ratio", ratio);

			root.addContent(attrNode);
			XMLOutputter out = new XMLOutputter();
			Format format = Format.getPrettyFormat();
			format.setIndent("");
			out.setFormat(format);
			out.output(document, new FileOutputStream(path + "/"
					+ "LedConfig.xml"));
			SaveSucess();
		}

		public void SaveSucess() {
			JOptionPane.showMessageDialog(playPane, " ����ɹ�!", "����",
					JOptionPane.PLAIN_MESSAGE);
		}

	}

	/*
	 * TxtPane ��ʱ֪ͨ�� ʵ�ֹ���:1���ı��ļ��Ķ�ȡ�����棬���͹��� 2���ı����Ե����ã���С����ɫ�����壬��ʽ��������ɫ������ʱ�䣩
	 */
	public class TxtPane {
		private JPanel txtPane;
		private JPanel txtEdit;
		private JPanel setEdit;
		private JPopupMenu popup;

		private Component createTxtPane() {
			txtPane = new JPanel();
			txtPane.setLayout(new BorderLayout());
			txtPane.add(txtEditPane(), BorderLayout.CENTER);
			txtPane.add(setEditPane(), BorderLayout.SOUTH);
			return txtPane;

		}

		private Component txtEditPane() {
			txtEdit = new JPanel();
			txtEdit.setBorder(BorderFactory.createTitledBorder("Edit"));
			txtEdit.setLayout(new FlowLayout());
			txtEdit.setBackground(MyColor.GRAY_BACKGROUND);
			text = new JTextPane();
			text.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						getPopup().show(e.getComponent(), e.getX(), e.getY());
					}
				}
			});
			Dimension size = new Dimension(500, 300);
			text.setPreferredSize(size);
			txtEdit.add(text);
			return txtEdit;

		}

		private Component setEditPane() {
			setEdit = new JPanel();
			setEdit.setBorder(BorderFactory.createTitledBorder("Default"));
			setEdit.setLayout(new GridLayout(1, 1));
			txtEdit_Pane = new Txt_EditPane(text, txtAttr);
			EditPane = txtEdit_Pane.getEditPane();
			setEdit.add(EditPane);
			return setEdit;
		}

		private JPopupMenu getPopup() {
			if (popup == null) {
				popup = new JPopupMenu("Popup");

				JMenuItem read = new JMenuItem("��ȡ");
				read.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						text.setText(readText());
					}
				});
				popup.add(read);

				JMenuItem save = new JMenuItem("����");
				save.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser chooser = new JFileChooser();
						chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						int v = chooser.showOpenDialog(null);
						if (v == JFileChooser.APPROVE_OPTION) {
							path = chooser.getSelectedFile().getAbsolutePath();
						}
						if (path == null) {
							System.out.println("û��ѡ���ļ�");
						} else {
							try {
								createLedFile(path);
								saveText(path, text.getText());
								createXmlFile(path, txtAttr);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				});
				popup.add(save);

				JMenuItem send = new JMenuItem("����");
				send.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						sendTxt(path);
					}
				});
				popup.add(send);
				popup.setInvoker(txtEdit);
			}
			return popup;
		}

		private String readText() {
			String str = null;
			JFileChooser chooser = new JFileChooser();
			int v = chooser.showOpenDialog(null);
			File file = null;
			if (v == JFileChooser.APPROVE_OPTION) {
				file = chooser.getSelectedFile();
				System.out.println(file.getAbsolutePath());
			}
			if (file == null) {
				System.out.println("û��ѡ���ļ�");
				str = null;
			} else {
				try {
					BufferedReader br = new BufferedReader(new FileReader(
							file.getAbsolutePath()));
					try {
						for (String s = br.readLine().trim(); s != null; s = br
								.readLine()) {
							str = str + s;
						}
						br.close();

					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			}

			return str;
		}

		private void saveText(String path, String text) throws IOException {
			String filename = "1.txt";
			File file = new File(path, filename);
			if (file.exists()) {
				file.delete();
				System.out.println("ɾ���ɹ�");
			} else {
				file.createNewFile();
				System.out.println("�����ɹ�");
			}

			BufferedWriter br = new BufferedWriter(new FileWriter(file));
			br.write(text);
			br.close();

		}

		private void createLedFile(String path) throws IOException {
			String filename = "Manager.led";
			File file = new File(path, filename);
			if (file.exists()) {
				file.delete();
				System.out.println("ɾ���ɹ�");
			} else {
				file.createNewFile();
				System.out.println("�����ɹ�");
			}
			String Text = "This is the LED Directory,You can find it by it!";
			FileWriter out = new FileWriter(file);
			out.write(Text);
			out.close();
		}

		private void createXmlFile(String path, txtAttribute txtAttr)
				throws FileNotFoundException, IOException {
			String fontFamily = txtAttr.getFontFamily();
			String fontSize = String.valueOf(txtAttr.getFontSize());
			String fontColor = String.valueOf(txtAttr.getFontColor());
			String bgColor = String.valueOf(txtAttr.getBgColor());
			String isBold = String.valueOf(txtAttr.isBold());
			String isItalic = String.valueOf(txtAttr.isItalic());
			String isUnderLine = String.valueOf(txtAttr.isUnderLine());
			String isLeft = String.valueOf(txtAttr.isLeft());
			String isRight = String.valueOf(txtAttr.isRight());
			String isCenter = String.valueOf(txtAttr.isCenter());
			String lineSpace = String.valueOf(txtAttr.getTextLineSpace());
			String hour = String.valueOf(txtAttr.getDuration().hour);
			String min = String.valueOf(txtAttr.getDuration().min);
			String sec = String.valueOf(txtAttr.getDuration().sec);

			Document document = new Document();
			Element root = new Element("root");
			document.addContent(root);
			Element node = new Element("node").setText("1");
			root.addContent(node);

			Element attrNode = new Element("node");
			attrNode.setAttribute("mediatype", "3")
					.setAttribute("location", path)
					.setAttribute("fontFamily", fontFamily)
					.setAttribute("fontSize", fontSize)
					.setAttribute("fontColor", fontColor)
					.setAttribute("bgColor", bgColor)
					.setAttribute("Bold", isBold)
					.setAttribute("Italic", isItalic)
					.setAttribute("UnderLine", isUnderLine)
					.setAttribute("Left", isLeft)
					.setAttribute("Right", isRight)
					.setAttribute("Center", isCenter)
					.setAttribute("LineSpace", lineSpace)
					.setAttribute("hour", hour).setAttribute("min", min)
					.setAttribute("sec", sec);

			root.addContent(attrNode);
			XMLOutputter out = new XMLOutputter();
			Format format = Format.getPrettyFormat();
			format.setIndent("");
			out.setFormat(format);
			out.output(document, new FileOutputStream(path + "/"
					+ "LedConfig.xml"));
			SaveSucess();

		}

		private void sendTxt(String path) {
			System.out.println(Ping.get().getIP());
			System.out.println(Ping.get().getPort());
		}

		public void SaveSucess() {
			JOptionPane.showMessageDialog(text, " ����ɹ�!", "����",
					JOptionPane.PLAIN_MESSAGE);
		}
	}

}
