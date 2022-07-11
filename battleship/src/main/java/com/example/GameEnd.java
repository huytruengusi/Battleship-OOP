package com.example;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;

public class GameEnd extends JFrame {
	private JPanel contentPane;
    private JButton playAgain;
    private JButton mainMenu;
    private JButton highScore;

    public GameEnd(boolean isPlayWin) {
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
        BackGround.setIcon(new ImageIcon(StartMenu.class.getResource(isPlayWin ? "/com/img/Victory.png" : "/com/img/Defeat.png")));
		layeredPane.add(BackGround);
		
		// panel chứa các button của startMenu
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		layeredPane.setLayer(panel, 1);
		panel.setBounds(0, 0, 1920, 1080);
		layeredPane.add(panel);
		panel.setLayout(null);

         // button Back về Menu
		playAgain = new JButton("Play Again");
		playAgain.setBounds(200,900,300,100);
		playAgain.setFont(new Font("Snap ITC", Font.BOLD, 30));
		playAgain.setBackground(new Color(135,206,235));
        playAgain.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
				StartGame.playGame();
                dispose();
            }
        });
		panel.add(playAgain);

        // button Back về Menu
		mainMenu = new JButton("MainMenu");
		mainMenu.setBounds(810,900,300,100);
		mainMenu.setFont(new Font("Snap ITC", Font.BOLD, 30));
		mainMenu.setBackground(new Color(135,206,235));
        mainMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
				StartGame.main(null);
                dispose();
            }
        });
		panel.add(mainMenu);

        // Button hiện ra HighScore
        highScore = new JButton("HighScore");
		highScore.setBounds(1380,900,300,100);
		highScore.setFont(new Font("Snap ITC", Font.BOLD, 30));
		highScore.setBackground(new Color(135,206,235));
        highScore.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
				HighScore score = new HighScore();
                score.setExtendedState(JFrame.MAXIMIZED_BOTH);
				score.setUndecorated (true);
				score.setVisible(true);
                dispose();
            }
        });
		panel.add(highScore);
    }
}
