package utils;

import gui.framework.Framework;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.direct.BufferFormat;
import uk.co.caprica.vlcj.player.direct.BufferFormatCallback;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.RenderCallback;
import uk.co.caprica.vlcj.player.direct.RenderCallbackAdapter;
import uk.co.caprica.vlcj.player.direct.format.RV32BufferFormat;

public class PlayerPanel extends JPanel {

	private static final int width = Framework.screensize.width;

	private static final int height = Framework.screensize.height;

	private final BufferedImage image;

	private final DirectMediaPlayerComponent mediaPlayerComponent;
	
	private  int ratio = 0;
	
	private File file = null;
	
	private JLabel fileNameLabel = null;

	public PlayerPanel() {
		super();

		image = GraphicsEnvironment.getLocalGraphicsEnvironment() 
				.getDefaultScreenDevice().getDefaultConfiguration()
				.createCompatibleImage(width, height);
		BufferFormatCallback bufferFormatCallback = new BufferFormatCallback() {
			@Override
			public BufferFormat getBufferFormat(int sourceWidth,
					int sourceHeight) {
				return new RV32BufferFormat(width, height);
			}
		};
		mediaPlayerComponent = new DirectMediaPlayerComponent(
				bufferFormatCallback) {
			@Override
			protected RenderCallback onGetRenderCallback() {
				return new TutorialRenderCallbackAdapter();
			}
		};
		
		fileNameLabel = new JLabel();
		fileNameLabel.setForeground(Color.WHITE);
		add(fileNameLabel);
		
		setBackground(Color.black);
		setOpaque(true);

	}
	
	public void showName(boolean f){
		fileNameLabel.setVisible(f);
	}
	
	public void prepareMedia(File file){
		this.file = file;
		mediaPlayerComponent.getMediaPlayer().prepareMedia(file.getAbsolutePath());
		fileNameLabel.setText(file.getName());
	}

	public DirectMediaPlayerComponent getPlayer(){
		return mediaPlayerComponent;
	}
	
	public void setRatio(int ratio){
		this.ratio = ratio;
	}
	
	public void refresh(){
		this.revalidate();
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(mediaPlayerComponent.getMediaPlayer().isPlaying()){
			selRatio(this.ratio, (Graphics2D)g, image);
		}else{
			super.paintComponent(g);
		}
	}
	
	private void selRatio(int ratio, Graphics2D g, BufferedImage image) {
		switch (ratio) {
		case 0:// ÆÌÂú
			resizeFillIn(image, g);
			break;
		case 1:// Ô­Ê¼
			resizeOrignalScale(image, g);
			break;
		case 2:// 4:3
			resizeRatioScale(image, g, 4.0, 3.0);
			break;
		case 3:// 5:4
			resizeRatioScale(image, g, 5.0, 4.0);
			break;
		case 4:// 16:9
			resizeRatioScale(image, g, 16.0, 9.0);
			break;
		default:
			resizeFillIn(image, g);
			break;
		}

	}

	private void resizeFillIn(BufferedImage src, Graphics2D g) {
		Image tmp = MyImage.resize(src, this.getWidth(), this.getHeight());
		g.drawImage(tmp, 0, 0, this.getWidth(), this.getHeight(), this);
	}

	private void resizeOrignalScale(BufferedImage src, Graphics2D g) {

		resizeRatioScale(src, g, (double) src.getWidth(),
				(double) src.getHeight());

	}

	private void resizeRatioScale(BufferedImage src, Graphics2D g, double w,
			double h) {

		int newWidth1 = this.getWidth();
		int newHeight1 = (int) ((h / w) * newWidth1);
		int x1 = 0;
		int y1 = (this.getHeight() - newHeight1) / 2;

		int newHeight2 = this.getHeight();
		int newWidth2 = (int) ((w / h) * newHeight2);
		int y2 = 0;
		int x2 = (this.getWidth() - newWidth2) / 2;

		if (x2 < 0 || (this.getWidth() > this.getHeight() && y1 >= 0)) {
			Image tmp = MyImage.resize(src, newWidth1, newHeight1);
			g.drawImage(tmp, x1, y1, newWidth1, newHeight1, this);
		} else {
			Image tmp = MyImage.resize(src, newWidth2, newHeight2);
			g.drawImage(tmp, x2, y2, newWidth2, newHeight2, this);
		}

	}

	private class TutorialRenderCallbackAdapter extends RenderCallbackAdapter {

		private TutorialRenderCallbackAdapter() {
			super(new int[width * height]);
		}

		@Override
		protected void onDisplay(DirectMediaPlayer mediaPlayer, int[] rgbBuffer) {
			// Simply copy buffer to the image and repaint
			image.setRGB(0, 0, width, height, rgbBuffer, 0, width);
			PlayerPanel.this.repaint();
		}
	}
}