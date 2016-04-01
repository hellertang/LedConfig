package gui.sendtab;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import sql.daofactory.DaoFactory;
import sql.entity.Filer;
import utils.ConnectionFactory;

import com.mysql.jdbc.Connection;

public class ButtonColumn extends AbstractCellEditor implements
		TableCellRenderer, TableCellEditor, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JButton renderButton;
	private JButton editButton;
	private String text;

	private Connection conn;

	public ButtonColumn(JTable table, int column) {
		super();
		this.table = table;
		renderButton = new JButton();
		editButton = new JButton();
		editButton.setFocusPainted(false);
		editButton.addActionListener(this);

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(column).setCellRenderer(this);
		columnModel.getColumn(column).setCellEditor(this);
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (hasFocus) {
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("Button.background"));
		} else if (isSelected) {
			renderButton.setForeground(table.getSelectionForeground());
			renderButton.setBackground(table.getSelectionBackground());
		} else {
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("Button.background"));
		}

		renderButton.setText((value == null) ? " " : value.toString());
		return renderButton;
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		text = (value == null) ? " " : value.toString();
		editButton.setText(text);
		return editButton;
	}

	public Object getCellEditorValue() {
		return text;
	}

	public void actionPerformed(ActionEvent e) {
		fireEditingStopped();
		int row = table.getSelectedRow();
		
		
		String name=(String) table.getValueAt(row, 0);
		String path = (String) table.getValueAt(row, 1);

		new OnlineSend(path);
		insert(name,path);
		System.out.println("文件名:"+name);
		System.out.println("文件路径"+path);
		
		System.out.println(e.getActionCommand() + "   :    "
				+ table.getSelectedRow());
	}

	public void insert(String Filename, String FilePath) {
		try {
			conn = (Connection) ConnectionFactory.getInstance()
					.makeConnection();
			conn.setAutoCommit(false);
			Filer File = new Filer();
			File.setFilename(Filename);
			File.setFilepath(FilePath);
			DaoFactory.getFileDAOInstance().save(conn, File);
			conn.commit();
			System.out.println("向文件表中插入了一条信息");

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

}
