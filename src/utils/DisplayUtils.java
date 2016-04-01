package utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DisplayUtils {
	
	public static void updateJLocation(JFrame obj) {
		Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension fsize =  obj.getSize();
		if (fsize.height > scr.height)
			fsize.height = scr.height;
		if (fsize.width > scr.width)
			fsize.width = scr.width;
		((Component) obj).setLocation((scr.width - fsize.width) / 2,
				(scr.height - fsize.height) / 2);
	}



}
