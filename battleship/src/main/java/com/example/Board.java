package com.example;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToggleButton;
import javax.swing.text.DefaultCaret;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLayeredPane;

import java.util.ArrayList;
import java.util.HashMap;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Toolkit;

public class Board extends JFrame {
	private HashMap<String,JToggleButton > btns = new HashMap <String,JToggleButton> ();
	private HashMap<String,Node> NodeIndex = new HashMap <String,Node> ();
	private final ArrayList <JToggleButton> btnsEnemy = new ArrayList <JToggleButton> ();
	private JPanel panel;
	private JLabel lblTurn;

	private JLabel playerAvatar;
	private JLabel playerScore;
	private JLabel playerHP;
	private JLabel playerShips;

	private JLabel computerAvatar;
	private JLabel computerDifficult;
	private JLabel computerHP;
	private JLabel computerShips;

	private JPanel panelSelf;
	private JPanel contentPane;
	private JButton mainMenu;
	private JButton musicOnOff;
	private JTextArea textArea;
	private JLayeredPane layeredPane_1;
	private JButton close;
	
	public Board(ArrayList<String> list, HashMap<String,Node> node) {
		NodeIndex = node;
		// Thiết lập cấu hình cho Board
		// Tạo Logo
		setIconImage(Toolkit.getDefaultToolkit().getImage(Board.class.getResource("/com/img/Logo.png")));
		setBounds(100, 100, 1920, 1080);

		// khởi tạo contentPane
		contentPane = new JPanel();
		contentPane.setForeground(new Color(153, 102, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(0, 0, 1920, 1080);
		layeredPane_1.setLayout(null);
		contentPane.add(layeredPane_1);
		addElements(layeredPane_1);
		addButtons(layeredPane_1);
		
		panel = new JPanel();
		layeredPane_1.setLayer(panel, 1);
		panel.setBounds(170, 290, 521, 521);
		layeredPane_1.add(panel);
		panel.setOpaque(false);
		panel.setLayout(new GridLayout(10, 10, 0, 0));
		setEnemyGrid(panel);
		
		panelSelf = new JPanel();
		layeredPane_1.setLayer(panelSelf, 1);
		panelSelf.setBounds(780, 290, 521, 521);
		layeredPane_1.add(panelSelf);
		panelSelf.setOpaque(false);
		panelSelf.setLayout(new GridLayout(10, 10, 0, 0));
		setPlayerGrid(panelSelf,list);

		JScrollPane scrollPane = new JScrollPane();
		
		layeredPane_1.setLayer(scrollPane, 1);
		scrollPane.setBounds(1393, 345, 406, 399);
		layeredPane_1.add(scrollPane);

		textArea= new JTextArea();
		textArea.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 20));
		textArea.setEditable(false);
		textArea.setBackground(new Color(135,206,235));
		scrollPane.setViewportView(textArea);

		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}
	
	private void setEnemyGrid (JPanel panel) {
		// khởi tạo các ToggleButton trong bảng máy
		char loopy = 'A';
		for (int i = 0; i <10; i++) {
			for (int j =0; j<10;j++) {
				JToggleButton button = new JToggleButton("");
				button.setName((char)loopy + "" + (j));
				// thêm button vào trong hashMap của bảng máy
				btnsEnemy.add(button);
				panel.add(button);
			}	
			loopy ++;
		}
	}
	private void setPlayerGrid (JPanel panelSelf,ArrayList <String> shipsLocation) {
		// khởi tạo các ToggleButton trong bảng người chơi nếu như vị trí đó có mảnh thuyền thì vẽ icon đánh dấu
		char loopy = 'A';
		for (int i = 0; i<10; i++) {
			for (int j =0; j<10; j++) {
				JToggleButton button = new JToggleButton("");
				button.setName((char)loopy + "" + (j));
				// thêm button vào trong hashMap của bảng người chươi 
				btns.put(button.getName(),button);
				panelSelf.add(button);
				button.setEnabled(false);
				if (shipsLocation.contains(button.getName())) {
					String str = new String(NodeIndex.get(button.getName()).getImgURL()+".png");
					button.setDisabledIcon(new ImageIcon(Board.class.getResource(str)));
					button.setIcon(new ImageIcon(Board.class.getResource(str)));	
				}
			}
			loopy ++;
		}
	}
	private void addElements (JLayeredPane layeredPane) {

		// Thiết lập background cho Board
		JLabel BackGround = new JLabel("");
		layeredPane.setLayer(BackGround, 0);
		BackGround.setBounds(0, 0, 1920, 1080);
		BackGround.setIcon(new ImageIcon(Board.class.getResource("/com/img/BackGround.jpg")));
		layeredPane.add(BackGround);

		// Thiết lập thanh trạng thái
		lblTurn = new JLabel("TURN: " +StartGame.getNumberofTurn());
		lblTurn.setBounds(680,120,500,100);
		layeredPane.setLayer(lblTurn, 1);
		lblTurn.setForeground(Color.BLACK);
		lblTurn.setFont(new Font("Snap ITC", Font.BOLD, 35));
		layeredPane.add(lblTurn);

		// Thiết lập thanh trạng thái người chơi
		playerAvatar = new JLabel("");
		playerAvatar.setBounds(80,40,180,180);
		layeredPane.setLayer(playerAvatar, 1);
		playerAvatar.setIcon(new ImageIcon(SetShips.class.getResource("/com/img/luffy.png")));
		layeredPane.add(playerAvatar);
		
		playerScore = new JLabel("SCORE: " + StartGame.getPlayerScore());
		playerScore.setBounds(280,25,700,100);
		layeredPane.setLayer(playerScore, 1);
		playerScore.setForeground(Color.BLACK);
		playerScore.setFont(new Font("Snap ITC", Font.BOLD, 30));
		layeredPane.add(playerScore);

		playerHP = new JLabel("HP: 17");
		playerHP.setBounds(280,85,700,100);
		layeredPane.setLayer(playerHP, 1);
		playerHP.setForeground(Color.BLACK);
		playerHP.setFont(new Font("Snap ITC", Font.BOLD, 30));
		layeredPane.add(playerHP);
		
		playerShips = new JLabel("SHIPS: 5");
		playerShips.setBounds(280,145,700,100);
		layeredPane.setLayer(playerShips, 1);
		playerShips.setForeground(Color.BLACK);
		playerShips.setFont(new Font("Snap ITC", Font.BOLD, 30));
		layeredPane.add(playerShips);

		// Thiết lập thanh trạng thái máy
		computerAvatar = new JLabel("");
		computerAvatar.setBounds(1650,40,180,180);
		layeredPane.setLayer(computerAvatar, 1);
		computerAvatar.setIcon(new ImageIcon(SetShips.class.getResource("/com/img/fujitora.png")));
		layeredPane.add(computerAvatar);
		
		computerDifficult = new JLabel(StartGame.isDifficult() ? "Level: Hard" : "Level: Easy");
		computerDifficult.setBounds(1400,25,700,100);
		layeredPane.setLayer(computerDifficult, 1);
		computerDifficult.setForeground(Color.BLACK);
		computerDifficult.setFont(new Font("Snap ITC", Font.BOLD, 30));
		layeredPane.add(computerDifficult);

		computerHP = new JLabel("HP: 17");
		computerHP.setBounds(1400,85,700,100);
		layeredPane.setLayer(computerHP, 1);
		computerHP.setForeground(Color.BLACK);
		computerHP.setFont(new Font("Snap ITC", Font.BOLD, 30));
		layeredPane.add(computerHP);
		
		computerShips = new JLabel("SHIPS: 5");
		computerShips.setBounds(1400,145,700,100);
		layeredPane.setLayer(computerShips, 1);
		computerShips.setForeground(Color.BLACK);
		computerShips.setFont(new Font("Snap ITC", Font.BOLD, 30));
		layeredPane.add(computerShips);

		// Các cột trong bảng
		JLabel lblAAA = new JLabel("0  1  2  3  4  5  6  7  8  9");
		lblAAA.setBounds(190, 244, 577, 43);
		layeredPane.setLayer(lblAAA, 1);
		layeredPane.add(lblAAA);
		lblAAA.setForeground(Color.WHITE);
		lblAAA.setFont(new Font("Snap ITC", Font.BOLD, 31));
		
		// Hàng A
		JLabel lblA = new JLabel("A");
		layeredPane.setLayer(lblA, 1);
		lblA.setBounds(129, 287, 32, 52);
		layeredPane.add(lblA);
		lblA.setForeground(Color.WHITE);
		lblA.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng B
		JLabel lblB = new JLabel("B");
		layeredPane.setLayer(lblB, 1);
		lblB.setBounds(129, 339, 32, 52);
		layeredPane.add(lblB);
		lblB.setForeground(Color.WHITE);
		lblB.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng C
		JLabel lblC = new JLabel("C");
		layeredPane.setLayer(lblC, 1);
		lblC.setBounds(129, 391, 32, 52);
		layeredPane.add(lblC);
		lblC.setForeground(Color.WHITE);
		lblC.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng D
		JLabel lblD = new JLabel("D");
		layeredPane.setLayer(lblD, 1);
		lblD.setBounds(129, 443, 32, 52);
		layeredPane.add(lblD);
		lblD.setForeground(Color.WHITE);
		lblD.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng E
		JLabel lblE = new JLabel("E");
		layeredPane.setLayer(lblE, 1);
		lblE.setBounds(129, 495, 32, 52);
		layeredPane.add(lblE);
		lblE.setForeground(Color.WHITE);
		lblE.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng F
		JLabel lblF = new JLabel("F");
		layeredPane.setLayer(lblF, 1);
		lblF.setBounds(129, 547, 32, 52);
		layeredPane.add(lblF);
		lblF.setForeground(Color.WHITE);
		lblF.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng G
		JLabel lblG = new JLabel("G");
		layeredPane.setLayer(lblG, 1);
		lblG.setBounds(129, 599, 32, 52);
		layeredPane.add(lblG);
		lblG.setForeground(Color.WHITE);
		lblG.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng H
		JLabel lblH = new JLabel("H");
		layeredPane.setLayer(lblH, 1);
		lblH.setBounds(129, 651, 32, 52);
		layeredPane.add(lblH);
		lblH.setForeground(Color.WHITE);
		lblH.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng I
		JLabel lblI = new JLabel("I");
		layeredPane.setLayer(lblI, 1);
		lblI.setBounds(129, 703, 32, 52);
		layeredPane.add(lblI);
		lblI.setForeground(Color.WHITE);
		lblI.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng J
		JLabel lblJ = new JLabel("J");
		layeredPane_1.setLayer(lblJ, 1);
		layeredPane.setLayer(lblB, 1);
		lblJ.setBounds(129, 755, 32, 52);
		layeredPane.add(lblJ);
		lblJ.setForeground(Color.WHITE);
		lblJ.setFont(new Font("Snap ITC", Font.BOLD, 33));

		// Commentary Box
		JLabel lblCommentaryBox = new JLabel("Commentary Box");
		layeredPane.setLayer(lblCommentaryBox, 1);
		lblCommentaryBox.setBounds(1383, 287, 310, 40);
		layeredPane.add(lblCommentaryBox);
		lblCommentaryBox.setForeground(Color.WHITE);
		lblCommentaryBox.setFont(new Font("Snap ITC", Font.BOLD, 30));

	}
	
	private void addButtons (JLayeredPane layeredPane) {	
		// musicOnOff Button
		musicOnOff = new JButton(StartGame.isMusicOn() ? "Sound On" : "Sound Off");
		layeredPane.setLayer(musicOnOff, 1);
		musicOnOff.setBounds(1562, 774, 237, 41);
		layeredPane.add(musicOnOff);
		musicOnOff.setBackground(new Color(135,206,235));
		musicOnOff.setFont(new Font("Snap ITC", Font.PLAIN, 25));

		// mainMenu Button
		mainMenu = new JButton("Main Menu");
		layeredPane.setLayer(mainMenu, 1);
		mainMenu.setBounds(1562, 824, 237, 41);
		layeredPane.add(mainMenu);
		mainMenu.setBackground(new Color(135,206,235));
		mainMenu.setFont(new Font("Snap ITC", Font.PLAIN, 25));
		
		// close Button
		close = new JButton("Close");
		layeredPane.setLayer(close, 1);
		close.setBounds(1562, 874, 237, 41);
		close.setBackground(new Color(135,206,235));
		close.setFont(new Font("Snap ITC", Font.PLAIN, 25));
		// thêm thành phần nhận sự kiện vào Button close (Board), đóng chương trình khi bấm vào
		close.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		layeredPane.add(close);
	}

	public void setTextPlayerScore(String str){
		playerScore.setText(str);
	}
	
	public void setTextPlayerHP(String str){
		playerHP.setText(str);
	}

	public void setTextPlayerShips(String str){
		playerShips.setText(str);
	}

	public void setTextComputerHP(String str){
		computerHP.setText(str);
	}

	public void setTextComputerShips(String str){
		computerShips.setText(str);
	}

	public void appendTextArea(String str){
		textArea.append(str);
	}

	public String getTextArea(){
		return textArea.getText();
	}

	public final ArrayList<JToggleButton> getBtnsEnemy() {
		return btnsEnemy;
	}

	public void setTextMusicOnOff(String str){
		musicOnOff.setText(str);
	}

	public JButton getMusicOnOff() {
		return musicOnOff;
	}

	public JButton getMainMenu() {
		return mainMenu;
	}

	public void setTextlblTurn(String str){
		lblTurn.setText(str);
	}
	public HashMap<String, JToggleButton> getBtns() {
		return btns;
	}
}
