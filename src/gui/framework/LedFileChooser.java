package gui.framework;

import gui.designtab.DesignTab;

import java.awt.*;
import java.beans.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import utils.FileChooserUtils;

public class LedFileChooser extends JFileChooser {
	private static final LedFileChooser fileChooser = new LedFileChooser();
	private LedFileFilter filter = null;
	private ImagePreview preview = null;

	private LedFileChooser() {
		super();

		filter = new LedFileFilter();
		preview = new ImagePreview();

		setAcceptAllFileFilterUsed(false);
		addChoosableFileFilter(filter);
		addPropertyChangeListener(preview);
		setAccessory(preview);
	}

	public static LedFileChooser getFileChooser() {
		return fileChooser;
	}

	public void openFileChooser(Component parent, String actionCmd) {
//		setSelectedFile(null);
//		rescanCurrentDirectory();
		if (actionCmd == FileChooserUtils.bAddPicActionCmd) {
			filter.chooseFilter(LedFileFilter.ImageFilter);
			openFileAction(parent, actionCmd, LedFileFilter.ImageFilter);
			
		} else if (actionCmd == FileChooserUtils.bAddVidActionCmd) {
			filter.chooseFilter(LedFileFilter.VideoFilter);
			openFileAction(parent, actionCmd, LedFileFilter.VideoFilter);
			
		} else if (actionCmd == FileChooserUtils.bAddTxtActionCmd) {
			filter.chooseFilter(LedFileFilter.TextFilter);
			openFileAction(parent, actionCmd, LedFileFilter.TextFilter);
			
		}else if(actionCmd == FileChooserUtils.bOpenActionCmd){
			System.out.println("open todo ");
			filter.chooseFilter(LedFileFilter.LedConfigFilter);
			openFileAction(parent, actionCmd, LedFileFilter.LedConfigFilter);
		}else if(actionCmd ==FileChooserUtils.bSaveActionCmd){
			filter.chooseFilter(LedFileFilter.LedConfigFilter);
			openFileAction(parent, actionCmd, LedFileFilter.LedConfigFilter);
		}

	}

	private void openFileAction(Component parent, String actionCmd, int type) {
		int val = showDialog(parent, actionCmd);
		if (val == JFileChooser.APPROVE_OPTION) {
			System.out.println("choose " + getSelectedFile());
			File file = getSelectedFile();
			// TODO should use swingworker
			switch (type) {
			case LedFileFilter.ImageFilter://parent = designtab
				((DesignTab)parent).getEdit().addNewImage(file);
				break;
			case LedFileFilter.TextFilter:
				((DesignTab)parent).getEdit().addNewText(file);
				break;
			case LedFileFilter.VideoFilter:
				((DesignTab)parent).getEdit().addNewVideo(file);
				break;
			case LedFileFilter.LedConfigFilter:
				((Framework)parent).getTabbedPane().getSendTab().setText(file);
			default:
				break;
			}
		} else if (val == JFileChooser.CANCEL_OPTION) {
			System.out.println("cancel..");
		}
	}
}



class LedFileFilter extends FileFilter {

	public static final int ImageFilter = 0;
	public static final int VideoFilter = 1;
	public static final int TextFilter = 2;
	public static final int XMLFilter  = 3;
	public static final int LedConfigFilter = 4;

	private int currentFilter = 0;

	public void chooseFilter(int choose) {
		if (choose >= 0 && choose < 5) {
			currentFilter = choose;
		}
	}

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = FileChooserUtils.getExtension(f);
		if (extension != null) {
			switch (currentFilter) {
			case ImageFilter:
				if (extension.equals(FileChooserUtils.tJPEG)
						|| extension.equals(FileChooserUtils.tJPG)
						|| extension.equals(FileChooserUtils.tPNG)) {
					return true;
				}
				break;
			case VideoFilter:
				if (extension.equals(FileChooserUtils.tAVI)
						|| extension.equals(FileChooserUtils.tRMVB)
						|| extension.equals(FileChooserUtils.tMKV)
						|| extension.equals(FileChooserUtils.tWMV)) {
					return true;
				}
				break;
			case TextFilter:
				if (extension.equals(FileChooserUtils.tTXT)) {
					return true;
				}
				break;
			case LedConfigFilter:
				if(extension.equals(FileChooserUtils.tLED)){
					return true;
				}
				break;
			default:
				return false;
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		String tmp = null;
		switch (currentFilter) {
		case ImageFilter:
			tmp = new String("*.jpg, *.jpeg, *.png");
			break;
		case VideoFilter:
			tmp = new String("*.avi, *.mkv, *.rmvb, *.wmv");
			break;
		case TextFilter:
			tmp = new String("*.txt");
			break;
		case LedConfigFilter:
			tmp = new String("*.led");
			break;
		}
		return tmp;
	}

}

class ImagePreview extends JComponent implements PropertyChangeListener {
	private ImageIcon thumbnail = null;
	private File file = null;
	private final int height = 100;
	private final int width = 150;

	public ImagePreview() {
		setPreferredSize(new Dimension(200, 100));
	}

	public void loadImage() {
		if (file == null) {
			thumbnail = null;
			return;
		}

		// Don't use createImageIcon (which is a wrapper for getResource)
		// because the image we're trying to load is probably not one
		// of this program's own resources.
		ImageIcon tmpIcon = new ImageIcon(file.getPath());
		if (tmpIcon != null) {
			if (tmpIcon.getIconWidth() > width - 10) {
				thumbnail = new ImageIcon(tmpIcon.getImage().getScaledInstance(
						width - 10, -1, Image.SCALE_DEFAULT));
			} else { // no need to miniaturize
				thumbnail = tmpIcon;
			}
		}
	}

	public void propertyChange(PropertyChangeEvent e) {
		boolean update = false;
		String prop = e.getPropertyName();

		// If the directory changed, don't show an image.
		if (JFileChooser.DIRECTORY_CHANGED_PROPERTY.equals(prop)) {
			file = null;
			update = true;

			// If a file became selected, find out which one.
		} else if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(prop)) {
			file = (File) e.getNewValue();
			update = true;
		}

		// Update the preview accordingly.
		if (update) {
			thumbnail = null;
			if (isShowing()) {
				loadImage();
				repaint();
			}
		}
	}

	protected void paintComponent(Graphics g) {
		if (thumbnail == null) {
			loadImage();
		}
		if (thumbnail != null) {
			int x = getWidth() / 2 - thumbnail.getIconWidth() / 2;
			int y = getHeight() / 2 - thumbnail.getIconHeight() / 2;

			if (y < 0) {
				y = 0;
			}

			if (x < 5) {
				x = 5;
			}
			thumbnail.paintIcon(this, g, x, y);
		}
	}
}
