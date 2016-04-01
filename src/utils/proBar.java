package utils;

import java.awt.Dimension;

import javax.swing.JProgressBar;

public class proBar extends JProgressBar {

	private static proBar bar = new proBar();

	private proBar() {
		super();
		this.setMaximum(30);
		this.setBorderPainted(true);
		this.setStringPainted(true);
		this.setPreferredSize(new Dimension(400, 40));
	}

	public static proBar get() {
		return bar;
	}

}
