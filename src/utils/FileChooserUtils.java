package utils;

import java.io.File;

public class FileChooserUtils {
	public static final String bAddPicActionCmd = "ѡ��ͼƬ";
	public static final String bAddVidActionCmd = "ѡ����Ƶ";
	public static final String bAddTxtActionCmd = "ѡ���ı�";

	public static final String bSaveActionCmd = "ȷ��";
	public static final String bOpenActionCmd = "ѡ���ļ���";

	public static final String tPNG = "png";
	public static final String tJPEG = "jpeg";
	public static final String tJPG = "jpg";
	public static final String tAVI = "avi";
	public static final String tRMVB = "rmvb";
	public static final String tMKV = "mkv";
	public static final String tWMV = "wmv";
	public static final String tTXT = "txt";
	
	public static final String tLED = "led";

	public static String getExtension(File file) {
		String extension = null;
		String name = file.getName();

		int i = name.lastIndexOf(".");
		if (i > 0 && i < name.length() - 1) {
			extension = name.substring(i + 1).toLowerCase();
		}
		return extension;
	}
}
