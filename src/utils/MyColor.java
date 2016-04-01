package utils;

import java.awt.Color;

public class MyColor extends Color{
	public static Color GRAY_BACKGROUND = new Color(0.9f, 0.9f, 0.9f);
	
	
	//not used, should not implements this class, just a static color chooser
	private MyColor(int x){
		super(x);
	}
}
