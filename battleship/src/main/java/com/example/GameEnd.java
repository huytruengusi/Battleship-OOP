package com.example;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import java.net.URL;
import javax.sound.sampled.*;

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
		StartGame.saveGame();
		if (StartGame.isMusicOn()){
			StartGame.getClip().stop();
			if (isPlayWin){
				winSound();
			}else{
				loseSound();
			}
		}

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

		JLabel playerScore = new JLabel("SCORE: " + StartGame.getPlayerScore());
		playerScore.setBounds(780,600,500,200);
		playerScore.setFont(new Font("Snap ITC", Font.BOLD, 50));
		playerScore.setForeground(Color.white);
		panel.add(playerScore);

         // button Back về Menu
		playAgain = new JButton("Play Again");
		playAgain.setBounds(200,900,300,100);
		playAgain.setFont(new Font("Snap ITC", Font.BOLD, 30));
		playAgain.setBackground(new Color(135,206,235));
        playAgain.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
				StartGame.play(StartGame.class.getResource("/com/sound/Automation.wav"));
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

	// Hàm gọi ra tiếng nổ khi bắn trúng
	public void winSound (){
		Clip clip2;
		try{
			URL url = StartGame.class.getResource("/com/sound/win.wav");
			AudioInputStream sound = AudioSystem.getAudioInputStream(url);	
			clip2 = AudioSystem.getClip();
			clip2.open(sound);
			clip2.start();
			Thread.sleep(2000);	
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	// Hàm gọi ra tiếng nổ khi bắn trúng
	public void loseSound (){
		Clip clip2;
		try{
			URL url = StartGame.class.getResource("/com/sound/lose.wav");
			AudioInputStream sound = AudioSystem.getAudioInputStream(url);	
			clip2 = AudioSystem.getClip();
			clip2.open(sound);
			clip2.start();
			Thread.sleep(2000);	
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
