package utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * EasyImage lets you do all the basic image operations - converting, cropping,
 * resizing, rotating, flipping… Plus it let’s you do some really cool affects.
 * All is done super easily. Combining operations can produce some very cool
 * results.
 * 
 * Operations: Open image. Save image Convert image Re-size image Crop image
 * Convert to black and white image Rotate image Flip image Add color to image
 * Create image with multiple instance of the original Combining 2 images
 * together Emphasize parts of the image Affine transform image
 *
 * 
 * @author Avi Yehuda
 *
 */
public class MyImage {
	private BufferedImage bufferedImage;
	private String fileName;
	
	public static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}
	/**
	 * Constructor - loads from an image file.
	 * 
	 * @param imageFile
	 */
	public MyImage(File imageFile) {
		try {
			bufferedImage = ImageIO.read(imageFile);
			fileName = imageFile.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
			bufferedImage = null;
			imageFile = null;
		}
	}

	/**
	 * Constructor - loads from an image file.
	 * 
	 * @param imageFilePath
	 */
	public MyImage(String imageFilePath) {
		this(new File(imageFilePath));
	}

	/**
	 * Return image as java.awt.image.BufferedImage
	 * 
	 * @return image as java.awt.image.BufferedImage
	 */
	public BufferedImage getAsBufferedImage() {
		return bufferedImage;
	}

	/**
	 * Save the image as a new image file. Can also convert the image according
	 * to file type.
	 * 
	 * @param fileName
	 */
	public void saveAs(String fileName) {
		saveImage(new File(fileName));
		this.fileName = fileName;
	}

	/**
	 * Saves the image to the original file.
	 */
	public void save() {
		saveImage(new File(fileName));
	}

	/**
	 * Resizing the image by percentage of the original.
	 * 
	 * @param percentOfOriginal
	 */
	public void resize(int percentOfOriginal) {
		int newWidth = bufferedImage.getWidth() * percentOfOriginal / 100;
		int newHeight = bufferedImage.getHeight() * percentOfOriginal / 100;
		resize(newWidth, newHeight);
	}

	/**
	 * Resizing the image by width and height.
	 * 
	 * @param newWidth
	 * @param newHeight
	 */
	public void resize(int newWidth, int newHeight){
		this.bufferedImage = resize(this.bufferedImage, newWidth, newHeight);
	}
	
	public static BufferedImage resize(BufferedImage bi, int newWidth, int newHeight) {

		int oldWidth = bi.getWidth();
		int oldHeight = bi.getHeight();

		if (newWidth == -1 || newHeight == -1) {
			if (newWidth == -1) {
				if (newHeight == -1) {
					return null;
				}

				newWidth = newHeight * oldWidth / oldHeight;
			} else {
				newHeight = newWidth * oldHeight / oldWidth;
			}
		}

		BufferedImage result = new BufferedImage(newWidth, newHeight,
				BufferedImage.TYPE_INT_BGR);

		double widthSkip = new Double(oldWidth - newWidth)
				/ new Double(newWidth);
		double heightSkip = new Double(oldHeight - newHeight)
				/ new Double(newHeight);

		double widthCounter = 0;
		double heightCounter = 0;

		int newY = 0;

		boolean isNewImageWidthSmaller = widthSkip > 0;
		boolean isNewImageHeightSmaller = heightSkip > 0;

		for (int y = 0; y < oldHeight && newY < newHeight; y++) {

			if (isNewImageHeightSmaller && heightCounter > 1) { // new image
																// suppose to be
																// smaller -
																// skip row
				heightCounter -= 1;
			} else if (heightCounter < -1) { // new image suppose to be bigger -
												// duplicate row
				heightCounter += 1;

				if (y > 1)
					y = y - 2;
				else
					y = y - 1;
			} else {

				heightCounter += heightSkip;

				int newX = 0;

				for (int x = 0; x < oldWidth && newX < newWidth; x++) {

					if (isNewImageWidthSmaller && widthCounter > 1) { // new
																		// image
																		// suppose
																		// to be
																		// smaller
																		// -
																		// skip
																		// column
						widthCounter -= 1;
					} else if (widthCounter < -1) { // new image suppose to be
													// bigger - duplicate pixel
						widthCounter += 1;

						if (x > 1)
							x = x - 2;
						else
							x = x - 1;
					} else {

						int rgb = bi.getRGB(x, y);
						result.setRGB(newX, newY, rgb);

						newX++;

						widthCounter += widthSkip;
					}

				}

				newY++;
			}

		}

		return result;
	}

	/**
	 * Add color to the RGB of the pixel
	 * 
	 * @param numToAdd
	 */
	public void addPixelColor(int numToAdd) {
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = bufferedImage.getRGB(x, y);
				bufferedImage.setRGB(x, y, rgb + numToAdd);
			}
		}
	}

	/**
	 * Covert image to black and white.
	 */
	public void convertToBlackAndWhite() {
		ColorSpace gray_space = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp convert_to_gray_op = new ColorConvertOp(gray_space, null);
		convert_to_gray_op.filter(bufferedImage, bufferedImage);
	}

	/**
	 * Rotates image 90 degrees to the left.
	 */
	public void rotateLeft() {

		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		BufferedImage result = new BufferedImage(height, width,
				bufferedImage.TYPE_INT_BGR);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = bufferedImage.getRGB(x, y);
				result.setRGB(y, x, rgb);
			}
		}

		bufferedImage = result;

	}

	/**
	 * Rotates image 90 degrees to the right.
	 */
	public void rotateRight() {
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		BufferedImage result = new BufferedImage(height, width,
				bufferedImage.TYPE_INT_BGR);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = bufferedImage.getRGB(x, y);
				result.setRGB(height - y - 1, x, rgb);
			}
		}

		bufferedImage = result;

	}

	/**
	 * Rotates image 180 degrees.
	 */
	public void rotate180() {
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		BufferedImage result = new BufferedImage(width, height,
				bufferedImage.TYPE_INT_BGR);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = bufferedImage.getRGB(x, y);
				result.setRGB(width - x - 1, height - y - 1, rgb);
			}
		}

		bufferedImage = result;

	}

	/**
	 * Flips the image horizontally
	 */
	public void flipHorizontally() {
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		BufferedImage result = new BufferedImage(width, height,
				bufferedImage.TYPE_INT_BGR);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = bufferedImage.getRGB(x, y);
				result.setRGB(width - x - 1, y, rgb);
			}
		}

		bufferedImage = result;

	}

	/**
	 * Flips the image vertically.
	 */
	public void flipVertically() {
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		BufferedImage result = new BufferedImage(width, height,
				bufferedImage.TYPE_INT_BGR);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int rgb = bufferedImage.getRGB(x, y);
				result.setRGB(x, height - y - 1, rgb);
			}
		}

		bufferedImage = result;

	}

	/**
	 * Multiply the image.
	 * 
	 * @param timesToMultiplyVertically
	 * @param timesToMultiplyHorizantelly
	 */
	public void multiply(int timesToMultiplyVertically,
			int timesToMultiplyHorizantelly) {
		multiply(timesToMultiplyVertically, timesToMultiplyHorizantelly, 0);
	}

	/**
	 * Multiply the image and also add color each of the multiplied images.
	 * 
	 * @param timesToMultiplyVertically
	 * @param timesToMultiplyHorizantelly
	 */
	public void multiply(int timesToMultiplyVertically,
			int timesToMultiplyHorizantelly, int colorToHenhancePerPixel) {
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		BufferedImage result = new BufferedImage(width
				* timesToMultiplyVertically, height
				* timesToMultiplyHorizantelly, bufferedImage.TYPE_INT_BGR);

		for (int xx = 0; xx < timesToMultiplyVertically; xx++) {
			for (int yy = 0; yy < timesToMultiplyHorizantelly; yy++) {
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						int rgb = bufferedImage.getRGB(x, y);
						result.setRGB(width * xx + x, height * yy + y, rgb
								+ colorToHenhancePerPixel * (yy + xx));

					}
				}
			}
		}

		bufferedImage = result;
	}

	/**
	 * Combines the image with another image in an equal presence to both;
	 * 
	 * @param newImagePath
	 *            - image to combine with
	 */
	public void combineWithPicture(String newImagePath) {
		combineWithPicture(newImagePath, 2);
	}

	/**
	 * Combines the image with another image. jump = 2 means that every two
	 * pixels the new image is replaced. This makes the 2 images equal in
	 * presence. If jump=3 than every 3rd pixel is replaced by the new image. As
	 * the jump is higher this is how much the new image has less presence.
	 * 
	 * @param newImagePath
	 * @param jump
	 */
	public void combineWithPicture(String newImagePath, int jump) {
		try {
			BufferedImage bufferedImage2 = ImageIO.read(new File(newImagePath));
			combineWithPicture(bufferedImage2, jump, null);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void combineWithPicture(MyImage image2) {
		combineWithPicture(image2.getAsBufferedImage(), 2, null);
	}

	public void combineWithPicture(MyImage image2, int jump) {
		combineWithPicture(image2.getAsBufferedImage(), jump, null);
	}

	public void combineWithPicture(MyImage image2, Color ignoreColor) {
		combineWithPicture(image2.getAsBufferedImage(), 2, ignoreColor);
	}

	public void combineWithPicture(MyImage image2, int jump, Color ignoreColor) {
		combineWithPicture(image2.getAsBufferedImage(), jump, ignoreColor);
	}

	/**
	 * Combines the image with another image. jump = 2 means that every two
	 * pixels the new image is replaced. This makes the 2 images equal in
	 * presence. If jump=3 than every 3rd pixel is replaced by the new image. As
	 * the jump is higher this is how much the new image has less presence.
	 * 
	 * ignoreColor is a color in the new image that will not be copied - this is
	 * good where there is a background which you do not want to copy.
	 * 
	 * @param bufferedImage2
	 * @param jump
	 * @param ignoreColor
	 */
	private void combineWithPicture(BufferedImage bufferedImage2, int jump,
			Color ignoreColor) {
		checkJump(jump);

		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		int width2 = bufferedImage2.getWidth();
		int height2 = bufferedImage2.getHeight();

		int ignoreColorRgb = -1;

		if (ignoreColor != null) {
			ignoreColorRgb = ignoreColor.getRGB();
		}

		for (int y = 0; y < height; y++) {
			for (int x = y % jump; x < width; x += jump) {
				if (x >= width2 || y >= height2) {
					continue;
				}

				int rgb = bufferedImage2.getRGB(x, y);

				if (rgb != ignoreColorRgb) {
					bufferedImage.setRGB(x, y, rgb);
				}
			}
		}

	}

	public void crop(int startX, int startY, int endX, int endY) {
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		if (startX == -1) {
			startX = 0;
		}

		if (startY == -1) {
			startY = 0;
		}

		if (endX == -1) {
			endX = width - 1;
		}

		if (endY == -1) {
			endY = height - 1;
		}

		BufferedImage result = new BufferedImage(endX - startX + 1, endY
				- startY + 1, bufferedImage.TYPE_INT_BGR);

		for (int y = startY; y < endY; y++) {
			for (int x = startX; x < endX; x++) {
				int rgb = bufferedImage.getRGB(x, y);
				result.setRGB(x - startX, y - startY, rgb);
			}
		}
		bufferedImage = result;
	}

	private void saveImage(File file) {
		try {
			ImageIO.write(bufferedImage, getFileType(file), file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void emphasize(int startX, int startY, int endX, int endY) {
		emphasize(startX, startY, endX, endY, Color.BLACK, 3);
	}

	public void emphasize(int startX, int startY, int endX, int endY,
			Color backgroundColor) {
		emphasize(startX, startY, endX, endY, backgroundColor, 3);
	}

	public void emphasize(int startX, int startY, int endX, int endY, int jump) {
		emphasize(startX, startY, endX, endY, Color.BLACK, jump);
	}

	public void emphasize(int startX, int startY, int endX, int endY,
			Color backgroundColor, int jump) {

		checkJump(jump);

		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		if (startX == -1) {
			startX = 0;
		}

		if (startY == -1) {
			startY = 0;
		}

		if (endX == -1) {
			endX = width - 1;
		}

		if (endY == -1) {
			endY = height - 1;
		}

		for (int y = 0; y < height; y++) {
			for (int x = y % jump; x < width; x += jump) {

				if (y >= startY && y <= endY && x >= startX && x <= endX) {
					continue;
				}

				bufferedImage.setRGB(x, y, backgroundColor.getRGB());
			}
		}

	}

	private void checkJump(int jump) {
		if (jump < 1) {
			throw new RuntimeException("Error: jump can not be less than 1");
		}

	}

	public void addColorToImage(Color color, int jump) {
		addColorToImage(color.getRGB(), jump);
	}

	public void addColorToImage(int rgb, int jump) {
		checkJump(jump);

		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = y % jump; x < width; x += jump) {
				bufferedImage.setRGB(x, y, rgb);
			}
		}
	}

	public void affineTransform(double fShxFactor, double fShyFactor) {

		try {
			AffineTransform shearer = AffineTransform.getShearInstance(
					fShxFactor, fShyFactor);
			AffineTransformOp shear_op = new AffineTransformOp(shearer, null);
			bufferedImage = shear_op.filter(bufferedImage, null);
		} catch (Exception e) {
			System.out.println("Shearing exception = " + e);
		}
	}

	private String getFileType(File file) {
		String fileName = file.getName();
		int idx = fileName.lastIndexOf(".");
		if (idx == -1) {
			throw new RuntimeException("Invalid file name");
		}

		return fileName.substring(idx + 1);
	}

	public int getWidth() {
		return bufferedImage.getWidth();
	}

	public int getHeight() {
		return bufferedImage.getHeight();
	}

	/**
	 * 给图片添加文字水印
	 * 
	 * @param pressText
	 *            水印文字
	 * @param srcImageFile
	 *            源图像地址
	 * @param destImageFile
	 *            目标图像地址
	 * @param fontName
	 *            水印的字体名称
	 * @param fontStyle
	 *            水印的字体样式
	 * @param color
	 *            水印的字体颜色
	 * @param fontSize
	 *            水印的字体大小
	 * @param x
	 *            修正值
	 * @param y
	 *            修正值
	 * @param alpha
	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 */
	public final static void pressText(String pressText, String srcImageFile,
			String destImageFile, String fontName, int fontStyle, Color color,
			int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			// 在指定坐标绘制水印文字
			g.drawString(pressText, (width - (getLength(pressText) * fontSize))
					/ 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG",
					new File(destImageFile));// 输出到文件流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给图片添加文字水印
	 * 
	 * @param pressText
	 *            水印文字
	 * @param srcImageFile
	 *            源图像地址
	 * @param destImageFile
	 *            目标图像地址
	 * @param fontName
	 *            字体名称
	 * @param fontStyle
	 *            字体样式
	 * @param color
	 *            字体颜色
	 * @param fontSize
	 *            字体大小
	 * @param x
	 *            修正值
	 * @param y
	 *            修正值
	 * @param alpha
	 *            透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 */
	public final static void pressText2(String pressText, String srcImageFile,
			String destImageFile, String fontName, int fontStyle, Color color,
			int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(srcImageFile);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			// 在指定坐标绘制水印文字
			g.drawString(pressText, (width - (getLength(pressText) * fontSize))
					/ 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "JPEG",
					new File(destImageFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}
	
	
	public static BufferedImage createImage(JPanel panel) {

	    int w = panel.getWidth();
	    int h = panel.getHeight();
	    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g = bi.createGraphics();
	    panel.paint(g);
	    return bi;
	}

	public static void main(String[] args) {
		/*
		 * Image image = new Image("c:/pics/p1.jpg"); image.resize(10);
		 * image.multiply(5, 5, 11111);
		 * image.saveAs("c:/pics/multiply+color.jpg");
		 */
		/*
		 * Image image = new Image("c:/pics/israel_flag.gif"); Image image2 =
		 * new Image("c:/pics/palestine_flag.gif"); //image.resize(50);
		 * //image2.resize(50); //image.affineTransform(0, 0.3);
		 * //image2.affineTransform(0, -0.3); image.combineWithPicture(image2);
		 * image.saveAs("c:/pics/affineTransformAndCombine2.jpg");
		 */
		/*
		 * Image image = new Image("c:/pics/p1.jpg"); image.resize(50);
		 * image.affineTransform(0.0, 0.5);
		 * image.saveAs("c:/pics/affineTransform.jpg");
		 */
		/*
		 * Image image = new Image("c:/pics/heart.gif"); image.multiply(20, 20);
		 * Image image2 = new Image("c:/pics/p6.jpg"); image2.crop(800, 0, -1,
		 * -1); image2.resize(50);
		 * image2.combineWithPicture(image,3,Color.white);
		 * image2.saveAs("c:/pics/combineWithPictureWithoutBackground.jpg"); /*
		 * image.resize(5); image.multiply(20, 20);
		 * image.combineWithPicture("c:/p2.jpg");
		 * //image.addColorToImage(Color.yellow, 3);
		 * //image.addColorToImage(Color.red, 5);
		 * //image.combineWithPicture("c:/p2.jpg",3);
		 */

		MyImage image = new MyImage("c:/pics/p1.jpg");
		int width = image.getWidth();
		int height = image.getHeight();
		for (int i = 0, c = 0; i < height; c++, i += 50) {
			int x = width / 2 - i;
			int y = height / 2 - i;

			image.emphasize(x, y, width - 1 - x, height - 1 - y, Color.BLACK,
					12 - c / 4);
		}
		image.saveAs("c:/pics/emphesizeTrick.jpg");
		// */
		// image.saveAs("c:/xxx.jpg");
		/*
		 * Image image = new Image("c:/pics/p1.jpg");
		 * image.addColorToImage(Color.red, 5);
		 * image.saveAs("c:/pics/addColorToImage.jpg");
		 */
	}
}