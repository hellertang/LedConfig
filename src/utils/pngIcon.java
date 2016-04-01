package utils;

import javax.swing.ImageIcon;

public class pngIcon {
	
	public static ImageIcon create(String path){
		if(path!=null)
			return new ImageIcon(path);
		else{
			System.out.println("没有这样的文件");
			return null;
		}
	}

}
