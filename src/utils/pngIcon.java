package utils;

import javax.swing.ImageIcon;

public class pngIcon {
	
	public static ImageIcon create(String path){
		if(path!=null)
			return new ImageIcon(path);
		else{
			System.out.println("û���������ļ�");
			return null;
		}
	}

}
