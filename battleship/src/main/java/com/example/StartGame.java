package com.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
public class StartGame {
	private static ArrayList <String> playerShips ;
	private static HashMap<String,Node> enemyNodeShips = new HashMap <String,Node> ();
	private static HashMap<String,JToggleButton> enemyButton = new HashMap<String,JToggleButton>();
	private static ArrayList <String> previousHit = new ArrayList<>();
	private static boolean playerTurn = true;
	private static StartMenu startMenu;
	private static Board board;
	private static ArrayList <String> enemyShips ;
	private static boolean done = false;
	private static SetShips setShips;
	private static Thread computer;

	private static boolean musicOn = true;
	private static boolean difficult = false;
	
	private static int numberofTurn;

	private static int playerScore;
	private static int playerHP;
	private static int numberOfPlayerShips;

	private static int computerHP;
	private static int numberOfComputerShips;

	private static Clip clip;
	private static URL menus = StartGame.class.getResource("/com/sound/Automation.wav");
	private static URL gamePlay = StartGame.class.getResource("/com/sound/Machines.wav");

	public static void main (String[] args) {

		// khởi tạo StartMenu với kích thước 2 phần tối đa có khả năng trang trí trên đó
		startMenu = new StartMenu ();
		startMenu.setExtendedState(JFrame.MAXIMIZED_BOTH);
		startMenu.setUndecorated (true);
		startMenu.setVisible(true);

		// nếu game chưa kết thúc và musicOn = true thì phát nhạc
		if (!done && musicOn) {
			play (menus);
			done = true;
		}		

		// thêm thành phần nhận sự kiện vào Button difficult(startMenu)
		startMenu.getBtnDifficult().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				// nếu difficult = true thì chuyển thành Sound Off và chuyển thành Sound On nếu ngược lại
				if (difficult) {
					startMenu.setTextBtnDifficult("Easy");
					difficult = false;
				}
				else {
					startMenu.setTextBtnDifficult("Hard");
					difficult = true;
				}	
			}
		});

		// thêm thành phần nhận sự kiện vào Button musicOnOff(startMenu)
		startMenu.getBtnMusicOnOff().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// nếu musicOn = true thì chuyển thành Sound Off và chuyển thành Sound On nếu ngược lại
				if (musicOn) {
					startMenu.setTextMusicOnOff("Sound Off");
					musicOn = false;
					clip.stop();
				}
				else {
					clip.stop();
					startMenu.setTextMusicOnOff("Sound On");
					musicOn = true;
					play (menus);
				}	
			}
		});
		
		// thêm thành phần nhận sự kiện vào Button Play, khi bấm vào thì bắt đầu chạy game, tắt màn hình startMenu
		startMenu.getPlay().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				playGame ();
				startMenu.dispose();
			}
		});	
	}

	public static void playGame () {	
		// khởi tạo màn hình đặt thuyền với kích thước 2 phần tối đa có khả năng trang trí trên đó
		resetValue();
		setShips = new SetShips (); 
		setShips.setExtendedState(JFrame.MAXIMIZED_BOTH);
		setShips.setUndecorated (true);
		setShips.setVisible(true);
		// thêm thành phần nhận sự kiện vào Button Exit (setShip), đóng chương trình khi bấm vào
		setShips.getBtnBack().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				main (null);
				setShips.dispose();
			}
		});

		// thêm thành phần nhận sự kiện vào Button Play (setShip), 
		setShips.getBtnPlay().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setShips.getShipList().getSize()==0) {
					// nếu như trong shipList không còn phần tử nào thì khởi tạo ngẫu nhiên vị trí thuyền của máy
					enemyShips = ShipGenerator.generateShip();
					enemyNodeShips = ShipGenerator.getNodeIndex();
					
					// khởi tạo board với kích thước 2 phần tối đa có khả năng trang trí trên đó
					board = new Board (setShips.getShipsLocation(),setShips.getNodeIndex());
					board.setExtendedState(JFrame.MAXIMIZED_BOTH);
					board.setUndecorated (true);
					board.setVisible(true);
					
					// Gọi lời mở đầu cho game
					board.appendTextArea("Game Started \n \n");
					
					// thêm thành phần nhận sự kiện vào Button MainMenu (Board), Đóng Board và khởi tạo StartMenu
					board.getMainMenu().addMouseListener(new MouseAdapter()  {
						public void mouseClicked(MouseEvent e) {
							if  (musicOn){
								clip.stop();
							}
							numberOfComputerShips = 0;
							main (null);
							board.dispose();
						}
					});
					
					// thêm thành phần nhận sự kiện vào Button musicOnOff (Board)
					board.getMusicOnOff().addMouseListener(new MouseAdapter()  {
						public void mouseClicked(MouseEvent e) {
							if (musicOn) {
								board.setTextMusicOnOff("Sound Off");
								musicOn = false;
								clip.stop();

							}
							else {
								clip.stop();
								board.setTextMusicOnOff("Sound On");
								musicOn = true;
								play (gamePlay);
							}	
						}
					});
					

					// Đóng bảng setShips
					setShips.dispose();

					if (musicOn) {
						clip.stop();
						play (gamePlay);
						done = false;
					}
					// Tạo một luồng thực thi cho Computer
					computer = new Thread (new ComputerTurn (board.getBtns(), setShips.getNodeIndex(), board)) ;
					computer.start();
					playTurn (board.getBtnsEnemy(),enemyShips);
					playerShips = setShips.getShipsLocation();	
				}
		  		else
				//   hiện ra message yêu cầu người chơi đặt tất cả các thuyền
				  	JOptionPane.showMessageDialog(null, "Please add all the ships ");
			}
		});
	}

	private static void checkEnemy (JToggleButton button, ArrayList<String> enemyShips) {
		
		// thêm vị trí vừa bắn vào textArea
		board.appendTextArea("You striked " + button.getName()+ ": ");

		// kiểm tra button có tên trong enemyship không
		if (enemyShips.contains(button.getName())) {

			// Xóa tên button ra khỏi danh sách vị trí enemyShips thiết lập lại icon bắn trúng cho button
			// Viết vào textArea báo rằng đã bắn trúng tăng điểm người chơi, giảm HP máy
			String nameships =  enemyNodeShips.get(button.getName()).getPieceOfShips();
			switch(nameships){
				case "Carrier":	  playerScore += 10;break;
				case "Battleship":playerScore += 20;break;
				case "Submarine": playerScore += 50;break;
				case "Cruiser":	  playerScore += 50;break;
				case "Destroyer": playerScore += 100;break;
			}

			board.setTextPlayerScore("SCORE: " + playerScore);
			board.setTextComputerHP("HP: " + --computerHP);
			enemyShips.remove(button.getName());
			previousHit.add(button.getName());
			board.appendTextArea ("It was a hit\n");
			button.setIcon(new ImageIcon(Board.class.getResource("/com/img/hit.png")));
			button.setDisabledIcon(new ImageIcon(Board.class.getResource("/com/img/hit.png")));
			button.setEnabled(false);
			int Count = 0; 

			// cho hàm chạy qua tất cả các phần tử của enemyShips nếu phần tử này là "/" thì tăng Count
			for (int i = 0 ; i < enemyShips.size();i++) {
				if (enemyShips.get(i).equals("/"))
					Count ++;
				else 
					Count = 0;
					// nếu có 2 "/" đứng cạnh nhau thì gọi ra hàm nổ và bỏ bớt một ngoặc
					// Viết vào textArea báo rằng đã có thuyền bị phá hủy
				if (Count == 2) {
					for (int j=0; j<previousHit.size(); j++){
						if (enemyNodeShips.get(previousHit.get(j)).getPieceOfShips().contains(nameships)){
							String imgURL = enemyNodeShips.get(previousHit.get(j)).getImgURL() + "-death.png" ;
							enemyButton.get(previousHit.get(j)).setIcon(new ImageIcon(Board.class.getResource(imgURL)));
							enemyButton.get(previousHit.get(j)).setDisabledIcon(new ImageIcon(Board.class.getResource(imgURL)));
							previousHit.remove(j--);
						}
					}
					board.setTextComputerShips("SHIPS: " + --numberOfComputerShips);
					board.appendTextArea("We destroyed their "+ nameships +"\n");
					
					if (musicOn)
						boom();
					enemyShips.remove(i);
				}
			}			
		}else {
			// Thiết lập lại icon bắn trượt cho button, chuyển lượt bắn cho máy
			// Viết vào textArea báo rằng bắn trượt
			button.setIcon(new ImageIcon(Board.class.getResource("/com/img/miss.png")));
			button.setDisabledIcon(new ImageIcon(Board.class.getResource("/com/img/miss.png")));
			board.appendTextArea("It was a miss \n");
			playerTurn = false;
		}
		button.setEnabled(false);

		// nếu đã chiến thắng hiện ra dialog
		if (numberOfComputerShips == 0) {
			GameEnd gameEnd = new GameEnd(true);
			gameEnd.setExtendedState(JFrame.MAXIMIZED_BOTH);
			gameEnd.setUndecorated (true);
			gameEnd.setVisible(true);		
			board.dispose();
		}
	}

	private static void playTurn (ArrayList <JToggleButton> btnsEnemy, final ArrayList <String> enemy) {
		// Từ các Button trong btnsEnemy tạo thành phần nhận sự kiện bấm vào của chuột
		System.out.println(enemyShips);
		for (final JToggleButton button : btnsEnemy ) {
			enemyButton.put(button.getName(), button);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					synchronized (this) {
						// nếu là lượt của người chơi thì check xem button có nằm trong 
						// vị trí của các thuyền không và tắt nút
						if (playerTurn) {
							board.setTextlblTurn("TURN: " + ++numberofTurn);
							checkEnemy (button,enemy);
							button.setEnabled(false);
						}
						else {
							// Đặt lại trạng thái cũ của button
							button.setSelected(false);
						}
					}
				}
			});
		}
	}

	// Hàm cho phép bật nhạc nền liên tục
	public static void play (URL url) {
		try{
			AudioInputStream sound = AudioSystem.getAudioInputStream(url);	
			clip = AudioSystem.getClip();
			clip.open(sound);
			int loop = clip.LOOP_CONTINUOUSLY;
			clip.loop(loop);	
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	// Hàm gọi ra tiếng nổ khi bắn trúng
	public static void boom (){
		Clip clip2;
		try{
			URL url = StartGame.class.getResource("/com/sound/boom.wav");
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

	public static void saveGame(){
		try{
			FileWriter fw = new FileWriter(difficult ? "battleship/src/main/java/com/HistoryHard.txt":"battleship/src/main/java/com/HistoryEasy.txt",true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(playerScore+"\n");
			bw.close();
		}catch(IOException ioe){
			System.out.println("Exception occurred:");
			ioe.printStackTrace();
	   }
	}

	// hàm khởi tạo lại các giá trị
	private static void resetValue(){
		playerTurn = true;
		playerScore = 0;
		numberofTurn = 0;
		playerHP=17;
		computerHP=17;
		numberOfComputerShips=5;
		numberOfPlayerShips=5;
	}

	public static int getPlayerHP() {
		return playerHP;
	}

	public static void setPlayerHP(int playerHP) {
		StartGame.playerHP = playerHP;
	}

	public static int getNumberOfPlayerShips() {
		return numberOfPlayerShips;
	}

	public static void setNumberOfPlayerShips(int numberOfPlayerShips) {
		StartGame.numberOfPlayerShips = numberOfPlayerShips;
	}

	public static int getNumberOfComputerShips() {
		return numberOfComputerShips;
	}

	public static int getPlayerScore() {
		return playerScore;
	}

	public static void setPlayerTurn(boolean playerTurn) {
		StartGame.playerTurn = playerTurn;
	}

	public static boolean isPlayerTurn() {
		return playerTurn;
	}

	public static boolean isDifficult() {
		return difficult;
	}

	public static boolean isMusicOn() {
		return musicOn;
	}

	public static ArrayList<String> getPlayerShips() {
		return playerShips;
	}

	public static int getNumberofTurn() {
		return numberofTurn;
	}

	public static Clip getClip() {
		return clip;
	}
	
}