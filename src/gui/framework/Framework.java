/**
 * 
 */
package gui.framework;

/**
 * @author K.Lee
 *
 */
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import org.jb2011.lnf.beautyeye.*;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.platform.wince.CoreDLL;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import gui.designtab.*;

public class Framework extends JFrame {

	/**
	 * @param args
	 */
	private JTabbedPane tabPane = null;
	private JPanel basePane = null;
	private JMenuBar menuBar = null;

	public static final Dimension screensize = Toolkit.getDefaultToolkit()
			.getScreenSize();

	public Framework(String title) {
		super(title);
		setResizable(false);
		basePane = new JPanel(new GridLayout(1, 1));
		createTabPane(screensize.width / 2, screensize.height /2);

		menuBar = new MenuBarExt(this);

		// add components

		// TODO 根据屏幕大小设置位置
		setLocation(screensize.width / 5, screensize.height / 7);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				EditPane.releasePlayer();
				System.exit(0);
			}
		});

		basePane.add(tabPane);
		setContentPane(basePane);
		setJMenuBar(menuBar);
		pack();
		setVisible(true);
	}

	public TabPaneExt getTabbedPane() {
		return (TabPaneExt) tabPane;
	}

	private void createTabPane(int x, int y) {
		tabPane = new TabPaneExt(this);
	}

	public MenuBarExt getMenuBarExt() {
		return (MenuBarExt) menuBar;
	}

	public static void main(String[] args) {

		System.setProperty("sun.java2d.noddraw", "true");
		System.setProperty("java.awt.im.style", "below-the-spot");

		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
				"D:\\VLC\\sdk\\lib");
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.put("RootPane.setupButtonVisible", false);
					BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
					BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
					BeautyEyeLNFHelper.launchBeautyEyeLNF();
				} catch (Exception e) {
					e.printStackTrace();
				}
				new Framework("LED Manger   ----NJU");
			}
		});

	}

}
