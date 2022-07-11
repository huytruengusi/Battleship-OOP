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

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class HighScore extends JFrame {
    private JPanel contentPane;
    private JButton btnMainMenu;
    private JButton btnReset;
	private ArrayList<Integer> list = new ArrayList<>();

    public HighScore(){
		if (StartGame.isMusicOn()){
			StartGame.getClip().stop();
			// StartGame.getClip().play(menus);
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
		BackGround.setIcon(new ImageIcon(StartMenu.class.getResource("/com/img/highscore.png")));
		layeredPane.add(BackGround);
		
		// panel chứa các button của startMenu
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		layeredPane.setLayer(panel, 1);
		panel.setBounds(0, 0, 1920, 1080);
		layeredPane.add(panel);
		panel.setLayout(null);

        // Button reset điểm cao
        btnReset = new JButton("Reset");
		btnReset.setBounds(1562, 824, 237, 41);
		btnReset.setFont(new Font("Snap ITC", Font.BOLD, 25));
		panel.add(btnReset);

		// button Back về Menu
		btnMainMenu = new JButton("MainMenu");
		btnMainMenu.setBounds(1562, 874, 237, 41);
		btnMainMenu.setFont(new Font("Snap ITC", Font.BOLD, 25));
		btnMainMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				StartGame.main(null);
				dispose();
			}
		});
		panel.add(btnMainMenu);
    }
	private void readFile() throws Exception {
		FileReader fr = new FileReader("battleship/src/main/java/com/History.txt");
		BufferedReader br = new BufferedReader(fr);
		while (br.ready()){
			list.add((int)br.read());
		}
		br.close();
		fr.close();
	}

}
