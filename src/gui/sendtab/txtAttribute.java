package gui.sendtab;

import java.awt.Color;

public class txtAttribute {

	private String fontFamily;
	private int fontSize;
	private Color fontColor;

	private boolean isBold;
	private boolean isItalic;
	private boolean isUnderLine;

	private boolean isLeft;
	private boolean isCenter;
	private boolean isRight;
	private int TextLineSpace;

	private Color bgColor;

	private Duration duration;

	public txtAttribute() {
		super();
		initTxtAttr();
	}

	private void initTxtAttr() {
		setFontFamily("ËÎÌו");
		setFontSize(16);
		setFontColor(Color.BLACK);
		setBold(false);
		setItalic(false);
		setUnderLine(false);

		setLeft(false);
		setRight(false);
		setCenter(false);
		setTextLineSpace(1);

		setBgColor(Color.WHITE);

		setDuration(new Duration(0, 0, 0));
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		this.fontFamily = fontFamily;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public Color getFontColor() {
		return fontColor;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	public boolean isBold() {
		return isBold;
	}

	public void setBold(boolean isBold) {
		this.isBold = isBold;
	}

	public boolean isItalic() {
		return isItalic;
	}

	public void setItalic(boolean isItalic) {
		this.isItalic = isItalic;
	}

	public boolean isUnderLine() {
		return isUnderLine;
	}

	public void setUnderLine(boolean isUnderLine) {
		this.isUnderLine = isUnderLine;
	}

	public boolean isLeft() {
		return isLeft;
	}

	public void setLeft(boolean isLeft) {
		this.isLeft = isLeft;
	}

	public boolean isCenter() {
		return isCenter;
	}

	public void setCenter(boolean isCenter) {
		this.isCenter = isCenter;
	}

	public boolean isRight() {
		return isRight;
	}

	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}

	public int getTextLineSpace() {
		return TextLineSpace;
	}

	public void setTextLineSpace(int textLineSpace) {
		TextLineSpace = textLineSpace;
	}

	public Color getBgColor() {
		return bgColor;
	}

	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public void setHour(int h) {
		this.duration.hour = h;
	}

	public void setMin(int m) {
		this.duration.min = m;
	}

	public void setSec(int s) {
		this.duration.sec = s;
	}

	public class Duration {

		public static final int HOUR_TYPE = 0;
		public static final int MIN_TYPE = 1;
		public static final int SEC_TYPE = 2;

		public int hour = 0;
		public int min = 0;
		public int sec = 0;

		public Duration(int h, int m, int s) {
			hour = h;
			min = m;
			sec = s;
		}
	}

}
