package gui.designtab;
import java.awt.Color;
import java.io.*;
import java.util.LinkedList;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class LedXmlFile  {
	private Element [] Node=null;
	private EditPane edit = null;

	public static  Element createVideoNode(File FILE,int Type,int VideoRatio){
		String type=String.valueOf(Type);
		String path=FILE.getPath();
		String ratio=String.valueOf(VideoRatio);
		
		Element node = new Element("node");
		node.setAttribute("mediatype",type).setAttribute("location", path).setAttribute("videoratio",ratio)
			.setText("node has attributes");
		return node;	
	}
	
	public static  Element createImageNode(File FILE,int Type,int DrawRatio,Color Bgcolor,Duration Dur){
		String type=String.valueOf(Type);
		String path=FILE.getPath();
		String ratio=String.valueOf(DrawRatio);
		String red=String.valueOf(Bgcolor.getRed());
		String blue=String.valueOf(Bgcolor.getBlue());
		String green=String.valueOf(Bgcolor.getGreen());
		String hour=String.valueOf(Dur.hour);
		String minute=String.valueOf(Dur.min);
		String second=String.valueOf(Dur.sec);
		Element node = new Element("node");
		node.setAttribute("mediatype",type).setAttribute("location", path).setAttribute("drawratio",ratio)
				.setAttribute("playtime",hour+":"+minute+":"+second)
					.setAttribute("bgcolor", "R="+red+",G="+green+",B"+blue).setText("node has attributes");
		return node;
	}

}