package com.example;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JFrame {
	private JPanel contentPane;
	private JButton play;
	private JButton highScore;
	private JButton btnDifficult;
	private JButton exit;
	private JButton btnMusicOnOff ; 
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	
	//  Constuctor khởi tạo StartMenu
	public StartMenu() {

		setIconImage(Toolkit.getDefaultToolkit().getImage(StartMenu.class.getResource("/com/img/NewLogo.png")));
		setBounds(0,0, 1920,1080 );
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// tạo một layeredPane 
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 1920, 1080);
		contentPane.add(layeredPane);
		layeredPane.setLayout(null);
		
		// thiết lập background
		JLabel BackGround = new JLabel("");
		BackGround.setBounds(0, 0, 1920, 1080);
		BackGround.setIcon(new ImageIcon(StartMenu.class.getResource("/com/img/BackGround.jpg")));
		layeredPane.add(BackGround);
		
		// panel chứa các button của startMenu
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		layeredPane.setLayer(panel, 1);
		panel.setBounds(0, 0, 1920, 1080);
		layeredPane.add(panel);
		panel.setLayout(null);
		
		// Nút Play
		play = new JButton("Play");
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		play.setBackground(new Color(135,206,235));
		play.setFont(new Font("Snap ITC", Font.BOLD, 30));
		play.setBounds(120, 359, 300, 75);
		panel.add(play);
		
		// Button điều chỉnh Độ khó
		btnDifficult = new JButton(StartGame.isDifficult() ? "Hard" : "Easy");
		btnDifficult.setBounds(120, 447, 300, 75);
		btnDifficult.setBackground(new Color(135,206,235));
		btnDifficult.setFont(new Font("Snap ITC", Font.BOLD, 30));
		panel.add(btnDifficult);
		
		// button ấn để hiện Bảng HighScore
		highScore = new JButton("HighScore");
		highScore.setBackground(new Color(135,206,235));
		highScore.setFont(new Font("Snap ITC", Font.BOLD, 30));
		highScore.setBounds(120, 535, 300, 75);
		highScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
                HighScore score = new HighScore();
                score.setExtendedState(JFrame.MAXIMIZED_BOTH);
				score.setUndecorated (true);
				score.setVisible(true);
			}
		});
		panel.add(highScore);
		
		// Nút thoát game
		exit = new JButton("Exit");
		exit.setBackground(new Color(135,206,235));
		exit.setFont(new Font("Snap ITC", Font.BOLD, 30));
		exit.setBounds(120, 711, 300, 75);
		panel.add(exit);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			System.exit (0);
			}
		});
		
		// Logo của trò chơi
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(StartMenu.class.getResource("/com/img/NewLogo.png")));
		lblNewLabel.setBounds(431, 13, 938, 291);
		panel.add(lblNewLabel);
		
		// Nút bật/ tắt âm lượng
		btnMusicOnOff = new JButton(StartGame.isMusicOn() ? "Sound: On" : "Sound: Off");
		btnMusicOnOff.setBounds(120, 623, 300, 75);
		btnMusicOnOff.setBackground(new Color(135,206,235));
		btnMusicOnOff.setFont(new Font("Snap ITC", Font.BOLD, 30));
		panel.add(btnMusicOnOff);
	}

	
	public void setTextMusicOnOff(String str){
		btnMusicOnOff.setText(str);
	}

	public void setTextBtnDifficult(String str){
		btnDifficult.setText(str);
	}

	public JButton getPlay() {
		return play;
	}

	public JButton getBtnDifficult() {
		return btnDifficult;
	}

	public JButton getBtnMusicOnOff() {
		return btnMusicOnOff;
	}
}
