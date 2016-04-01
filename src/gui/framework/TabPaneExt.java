package gui.framework;

import gui.controltab.ControlTab;
import gui.designtab.DesignTab;
import gui.sendtab.SendTab;
import gui.termctltab.TermCtlTab;
import gui.termmantab.TermManTab;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utils.pngIcon;

public class TabPaneExt extends JTabbedPane {
	private JPanel designPane = null;
	private JPanel sendPane = null;
	private JPanel playCtrlPane = null;
	private JPanel termCtrlPane = null;
	private JPanel termManagePane = null;

	private Framework framework = null;

	private LedFileChooser fileChooser = LedFileChooser.getFileChooser();

	public TabPaneExt(Framework framework) {
		super();
		this.framework = framework;
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		designPane = new DesignTab();
		sendPane = new SendTab();
		playCtrlPane = new ControlTab();
		termCtrlPane = new TermCtlTab();
		termManagePane = new TermManTab();
		addTab(MenuBarExt.PLAN_DESIGN, pngIcon.create("image//1.png"),
				designPane);
		addTab(MenuBarExt.PLAN_SEND, pngIcon.create("image//2.png"), sendPane);
		addTab(MenuBarExt.PLAY_CTRL, pngIcon.create("image//3.png"),
				playCtrlPane);
		addTab(MenuBarExt.TERMINAL_CTRL, pngIcon.create("image//4.png"),
				termCtrlPane);
		addTab(MenuBarExt.TERMINAL_MAN, pngIcon.create("image//5.png"),
				termManagePane);

		addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JTabbedPane tp = (JTabbedPane) e.getSource();
				int index = tp.getSelectedIndex();
				JMenu fileMenu = framework.getMenuBarExt().getFileMenu();
				switch (index) {
				case 0:
					setEnableMenuItems(fileMenu, 0, 4, true);
					break;
				case 1:
					setEnableMenuItems(fileMenu, 0, 1, true);
					setEnableMenuItems(fileMenu, 1, 3, false);
					break;
				case 2:
					setEnableMenuItems(fileMenu, 0, 4, false);
					break;
				case 3:
					setEnableMenuItems(fileMenu, 0, 4, false);
					break;
				case 4:
					setEnableMenuItems(fileMenu, 0, 4, false);
					break;
				default:
					break;
				}
			}

			private void setEnableMenuItems(JMenu menu, int start, int count,
					boolean setting) {
				for (int i = 0; i < count; i++) {
					try {
						menu.getItem(start + i).setEnabled(setting);
					} catch (NullPointerException e) {
						System.out.println("null pointer : start>" + start
								+ " count> " + i);
					}
				}
			}

		});

	}

	public DesignTab getDesignTab() {
		return (DesignTab) designPane;
	}

	public SendTab getSendTab() {
		return (SendTab) sendPane;
	}

	public ControlTab getControlTab() {
		return (ControlTab) playCtrlPane;
	}

	public TermCtlTab getTermCtlTab() {
		return (TermCtlTab) termCtrlPane;
	}

	public TermManTab getTermManTan() {
		return (TermManTab) termManagePane;
	}

	public LedFileChooser getFileChooser() {
		return fileChooser;
	}

	public ImageIcon createImageIcon(String path) {
		if (path != null)
			return new ImageIcon(path);
		else {
			System.out.println("没有这样的文件");
			return null;
		}
	}

}
