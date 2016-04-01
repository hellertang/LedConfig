package com.vlsi.videoplayer.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.swing.JButton;

import com.vlsi.videoplayer.main.PlayMain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.UIManager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	EmbeddedMediaPlayerComponent playerComponent;
	private JPanel panel;
	private JButton btnPlay;
	private JButton btnPause;
	private JButton btnStop;
	private JPanel controlPanel;
	private JProgressBar progress;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenu mnHelp;
	private JMenuItem mntmOpenVideo;
	private JMenuItem mntmExit;
	private JMenuItem mntmAbout;
	private JSlider slider;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		UIManager.put("RootPane.setupButtonVisible",true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 616, 466);
		setTitle("VideoPlayer");
	
		//----------------------------menu初始化---------------------------------------------------------------
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmOpenVideo = new JMenuItem("Open Video...");
		mnFile.add(mntmOpenVideo);
		
		mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		mnHelp=new JMenu("Help");
		menuBar.add(mnHelp);
		mntmAbout=new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		
		mntmOpenVideo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayMain.openVideo();
			}
		});
		

		mntmExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayMain.exit();
			}
		});
		
		mntmAbout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AppAuthor();
			}
		});
		//------------------------------------------------panel初始化-----------------------------------------------------------------------------
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());
		
		//new 
	    JPanel tmp = new JPanel();
		tmp.setLayout(new BorderLayout());
		tmp.add(contentPane, BorderLayout.CENTER);
		
		setContentPane(tmp);
		
		JPanel videopane = new JPanel();
		contentPane.add(videopane, BorderLayout.CENTER);
		videopane.setLayout(new BorderLayout());
		videopane.setOpaque(true);
		contentPane.setComponentZOrder(videopane, 0);
		contentPane.setOpaque(true);
		
		playerComponent = new EmbeddedMediaPlayerComponent();
		videopane.add(playerComponent);
	//	videopane.setBackground(Color.BLACK);
		videopane.setComponentZOrder(playerComponent, 0);
		
	/*	playerComponent = new EmbeddedMediaPlayerComponent();
		contentPane.add(playerComponent);
	//	contentPane.setBackground(Color.BLACK);
		contentPane.setComponentZOrder(playerComponent, 0);*/
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout());
		
		controlPanel = new JPanel();
		panel.add(controlPanel);
		
		btnStop = new JButton("Stop");
		controlPanel.add(btnStop);
		
		btnPlay = new JButton("Play");
		controlPanel.add(btnPlay);
		
		btnPause = new JButton("Pause");
		controlPanel.add(btnPause);
		
		slider = new JSlider();
		slider.setValue(100);
		slider.setMaximum(120);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				PlayMain.setVol(slider.getValue());
			}
		});
		controlPanel.add(slider);
		
		progress = new JProgressBar();
		progress.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				PlayMain.jumpTo((float)x/progress.getWidth());
			}
		});
		progress.setStringPainted(true);
		panel.add(progress, BorderLayout.NORTH);
		btnPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PlayMain.pause();
			}
		});
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				PlayMain.play();
			}
		});
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PlayMain.stop();
			}
		});
	}

	public EmbeddedMediaPlayer getMediaPlayer() {
		return playerComponent.getMediaPlayer();
	}
	
	public JProgressBar getProgressBar() {
		return progress;
	}
	public void AppAuthor(){
		 JOptionPane.showMessageDialog(this, 
				    "@author:big_tang", 
				    "Author",
				    JOptionPane.PLAIN_MESSAGE);
	}
}
