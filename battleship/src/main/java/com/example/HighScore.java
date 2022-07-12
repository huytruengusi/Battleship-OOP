package com.example;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class HighScore extends JFrame {
    private JPanel contentPane;
    private JButton btnMainMenu;
    private JButton btnReset;
    private JButton btnDifficult;
	private boolean difficult = false;
	private ArrayList<Integer> list = new ArrayList<Integer>();
	private ArrayList<JLabel> listLabel = new ArrayList<JLabel>();
	private JLabel lblDifficult;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;

	// Constructor khởi tạo màn hình HighScore
    public HighScore(){
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
		
		// panel chứa các button của HighScore
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		layeredPane.setLayer(panel, 1);
		panel.setBounds(0, 0, 1920, 1080);
		layeredPane.add(panel);
		panel.setLayout(null);

		// khởi tạo Label heading của HighScore
		JLabel heading = new JLabel("HIGHSCORE",JLabel.CENTER);
		heading.setBounds(700, 100, 520, 150);
		heading.setFont(new Font("Snap ITC", Font.BOLD, 45));
		panel.add(heading);

		// thiết lập các Label thể hiện bảng điểm
		lblDifficult = new JLabel("Easy:");
		lblDifficult.setBounds(500, 250, 240, 100);
		lblDifficult.setFont(new Font("Snap ITC", Font.BOLD, 35));
		panel.add(lblDifficult);

		label1 = new JLabel("");
		label1.setBounds(700, 250, 240, 100);
		label1.setFont(new Font("Snap ITC", Font.BOLD, 35));
		listLabel.add(label1);
		panel.add(label1);

		label2 = new JLabel("");
		label2.setBounds(700, 350, 240, 100);
		label2.setFont(new Font("Snap ITC", Font.BOLD, 35));
		listLabel.add(label2);
		panel.add(label2);

		label3 = new JLabel("");
		label3.setBounds(700, 450, 240, 100);
		label3.setFont(new Font("Snap ITC", Font.BOLD, 35));
		listLabel.add(label3);
		panel.add(label3);

		label4 = new JLabel("");
		label4.setBounds(700, 550, 240, 100);
		label4.setFont(new Font("Snap ITC", Font.BOLD, 35));
		listLabel.add(label4);
		panel.add(label4);

		label5 = new JLabel("");
		label5.setBounds(700, 650, 240, 100);
		label5.setFont(new Font("Snap ITC", Font.BOLD, 35));
		listLabel.add(label5);
		panel.add(label5);

		difficult = false;
		try{
			readEasyFile();
		}catch(Exception e){
			e.printStackTrace();
		}
		paintElement();

		// btnDifficult Button
		btnDifficult = new JButton("Hard");
		btnDifficult.setBounds(1562, 774, 237, 41);
		btnDifficult.setBackground(new Color(135,206,235));
		btnDifficult.setFont(new Font("Snap ITC", Font.BOLD, 25));
		btnDifficult.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if (difficult){
					difficult = false;
					try{
						readEasyFile();
					}catch(Exception ex){
						ex.printStackTrace();
					}
					paintElement();
					btnDifficult.setText("Hard");
				}else{
					difficult = true;
					try{
						readHardFile();
					}catch(Exception ex){
						ex.printStackTrace();
					}
					paintElement();
					btnDifficult.setText("Easy");
				}
				lblDifficult.setText(difficult ? "Hard:" : "Easy:");
			}
		});
		panel.add(btnDifficult);

        // Button reset điểm cao
        btnReset = new JButton("Reset");
		btnReset.setBounds(1562, 824, 237, 41);
		btnReset.setFont(new Font("Snap ITC", Font.BOLD, 25));
		btnReset.setBackground(new Color(135,206,235));
		btnReset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					CharArrayWriter out = new CharArrayWriter();
					FileWriter f1 = new FileWriter("battleship/src/main/java/com/HistoryHard.txt");
					FileWriter f2 = new FileWriter("battleship/src/main/java/com/HistoryEasy.txt");
					out.write(""); 
					out.writeTo(f1);
					out.writeTo(f2);
					f1.close();
					f2.close();
				}catch (Exception ex){
					ex.printStackTrace();
				}

				if (difficult){
					try{
						readEasyFile();
					}catch(Exception ex){
						ex.printStackTrace();
					}
					paintElement();
				}else{
					try{
						readHardFile();
					}catch(Exception ex){
						ex.printStackTrace();
					}
					paintElement();
				}
				
			}
		});
		panel.add(btnReset);

		// button Back về Menu
		btnMainMenu = new JButton("MainMenu");
		btnMainMenu.setBounds(1562, 874, 237, 41);
		btnMainMenu.setFont(new Font("Snap ITC", Font.BOLD, 25));
		btnMainMenu.setBackground(new Color(135,206,235));
		btnMainMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				StartGame.main(null);
				dispose();
			}
		});
		panel.add(btnMainMenu);
    }
	// đọc file HistoryEasy lưu dữ liệu đọc được và sắp xếp vào trong list
	private void readEasyFile() throws Exception {
		list.clear();;
		File file = new File("battleship/src/main/java/com/HistoryEasy.txt");
		Scanner scanner = new Scanner(file);
		while(scanner.hasNext()){
			list.add(scanner.nextInt());
		}
		Collections.sort(list);
	}
	
	// đọc file HistoryHard lưu dữ liệu đọc được và sắp xếp vào trong list
	private void readHardFile() throws Exception {
		list.clear();;
		File file = new File("battleship/src/main/java/com/HistoryHard.txt");
		Scanner scanner = new Scanner(file);
		while(scanner.hasNext()){
			list.add(scanner.nextInt());
		}
		Collections.sort(list);
	}
	
	// thiết lập các dữ liệu lên trên màn hình
	private void paintElement(){
		int i=0;
		int k = list.size();
		for (i=0; i< k; i++){
			listLabel.get(i).setText("" + (i+1) +". " + list.get(k-i-1));
			if (i==4){
				i++;
				break;
			}
		}

		while(i<5){
			listLabel.get(i).setText((i+1) +". 0" );
			i++;
		}
	}
}
