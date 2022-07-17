package com.example;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.border.MatteBorder;

import java.util.ArrayList;
import java.util.HashMap;

import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SetShips extends JFrame {
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPanel contentPane;
	private boolean horizontal = true ; 
	private int listIndex = 5;
	private int shipSize = 0;
	private String shipSelected;

	private JLabel playerAvatar;
	private JLabel playerScore;
	private JLabel playerHP;
	private JLabel playerShips;

	private JLabel computerAvatar;
	private JLabel computerDifficult;
	private JLabel computerHP;
	private JLabel computerShips;

	private HashMap<String,JToggleButton > ButtonIndex = new HashMap <String,JToggleButton> ();
	private HashMap<String,Node> NodeIndex = new HashMap <String,Node> ();
	private ArrayList<String> shipsLocation = new ArrayList<String>();
	private ShipList shipList = new ShipList ();
	private JLayeredPane layeredPane;
	private JLabel background;
	private JButton btnPlay;
	private JButton Back;
	private JButton Exit;
	
	public SetShips() {
		// thiết lập icon
		setIconImage(Toolkit.getDefaultToolkit().getImage(SetShips.class.getResource("/com/img/Logo.png")));
		setBounds(100, 100, 1920,1080 );
		
		// khởi tạo contentPane
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		// khởi tạo layeredPane chứa toàn bộ các phần trong setships
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 1920, 1080);
		layeredPane.setLayout(null);
	
		// khởi tạo panel
		JPanel panel = new JPanel();
		layeredPane.setLayer(panel, 1);
		panel.setBounds(780, 290, 521, 521);
		layeredPane.add(panel);
		panel.setOpaque(false);
		panel.setLayout(new GridLayout(10, 10, 0, 0));

		// gọi hàm khởi tạo thuyền
		initiateShips();
		setGrid (panel);
		addElements (layeredPane);
	}

	// kiểm tra vị trí bấm có đặt được thuyền không 
	private boolean valid (String location, boolean horizontal, int shipsize, ArrayList <String> shipsLocation) {
		
		if (horizontal) {
			// check xem trên độ dài của thuyền từ điểm muốn đặt theo chiều ngang có vị trí nào đã có thuyền không
			for (int i =0 ;i<shipsize;i++) {
				if (shipsLocation.contains(location.charAt(0) + "" + (char)(location.charAt(1) + i)))return false;
			}
			// trả về lệnh check xem vị trí ban đầu có thỏa mãn để cho thuyền nằm trong mảng không
			return((char)('9'-shipsize+1)-location.charAt(1) >=0);  
		}
		else{
			// check xem trên độ dài của thuyền từ điểm muốn đặt theo chiều dọc có vị trí nào đã có thuyền không
			for (int i =0 ;i<shipsize;i++) {
				if(shipsLocation.contains((char)(location.charAt(0) + i) + "" + (location.charAt(1))))return false;
			}
			// trả về lệnh check xem vị trí ban đầu có thỏa mãn để cho thuyền nằm trong mảng không
			return(((char)('J'- shipsize+1)-location.charAt(0)) >=0);
		}
	}

	// reset lại setShips
	private void reset () {
		// tái tạo lại dịa chỉ tọa độ của các thuyền
		shipsLocation = new ArrayList<String>();
		shipsLocation.add("/");
		// xóa hết các phần tử còn lại trong shipList
		shipList.empty();
		// thêm lại các phầm tử vào shipList
		shipList.add("Carrier");
		shipList.add("Battleship");
		shipList.add("Cruiser");
		shipList.add("Submarine");
		shipList.add("Destroyer");
		repaint();
		char loopy = 'A';
		Icon icon = new ImageIcon();

		// thiết lập lại các Button trong bảng nhận sự kiện click để đặt thuyền
		for (int i = 0; i <10; i++) {
			for (int j = 0 ; j<10;j++) {
				String index = loopy + "" + (j);
				JToggleButton current = ButtonIndex.get(index);
				current.setSelected(false);
				current.setEnabled(true);
				current.setIcon(icon);
			}
			loopy ++;
		}
	}

	// thiết lập Grid
	private void setGrid (JPanel panel) {
		char loopy = 'A';
		// Tạo button các vị trí trong bảng 
		for (int i = 0; i<10; i++) {	
			for (int j = 0 ; j<10;j++) {
				final JToggleButton button = new JToggleButton("");
				// đặt tên cho button
				String index = loopy + "" + (j);
				button.setName(index);

				// Cho phép button có thể nhìn xuyên thấu
				button.setOpaque(false);
				ButtonIndex.put(index , button);

				// thêm vào panel của bảng
				panel.add(button);	
				// button nhận một sự kiện lấy ra tên của button làm vị bắt đầu cho việc đặt thuyền
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						// Đặt mặc định của button là false
						button.setSelected(false);
						boolean valid = true;
						if (shipSize == 0)
							valid = false;
						char firstIndex = button.getName().charAt(0);
						char secondIndex = button.getName().charAt(1);
						valid = valid (firstIndex + "" + secondIndex,horizontal,shipSize,shipsLocation);
						
						if (valid) {
							for (int i = 0; i<shipSize; i++) {
								// thiết lập lại các button chứa thuyền
								shipsLocation.add(firstIndex + "" + secondIndex);
								Node node = new Node();
								JToggleButton current = ButtonIndex.get(firstIndex + "" +secondIndex);
								String str;
								if(horizontal){
									str = new String("/com/img/Horizontal/" + shipSelected+ "/" + shipSelected + "-" + (i+1));
								}else{
									str = new String("/com/img/Vertical/" + shipSelected+ "/" + shipSelected + "-" + (i+1));
								}

								current.setSelected(true);
								current.setIcon(new ImageIcon(Board.class.getResource(str + ".png")));
								current.setDisabledIcon(new ImageIcon(Board.class.getResource(str + ".png")));
								current.setEnabled(false);

								node.setPieceOfShips(shipSelected);
								node.setImgURL(str);
								NodeIndex.put(firstIndex + "" + secondIndex, node);
								if (horizontal) {
									secondIndex ++;
								}
								else {
									firstIndex++;
								}
							}
							// thiết lập lại trạng thái cũ và vẽ lại màn hình
							shipList.remove(listIndex);
							listIndex = 5;
							shipSize = 0;
							shipsLocation.add("/");
							repaint();
						}
					}
				});
			}
			loopy ++;
		}
	}
	private void addElements (JLayeredPane layeredPane) {
		
		// thiết lập background
		background = new JLabel("");
		background.setBounds(0, 0, 1920, 1080);
		background.setIcon(new ImageIcon(Board.class.getResource("/com/img/BackGround.jpg")));
		layeredPane.add(background);
		contentPane.setLayout(null);
		contentPane.add(layeredPane);

		// Thiết lập thanh trạng thái người chơi
		playerAvatar = new JLabel("");
		playerAvatar.setBounds(80,40,180,180);
		layeredPane.setLayer(playerAvatar, 1);
		playerAvatar.setIcon(new ImageIcon(SetShips.class.getResource("/com/img/player.png")));
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
		computerAvatar.setIcon(new ImageIcon(SetShips.class.getResource("/com/img/computer.png")));
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

		// Các cột trong bản đồ người chơi
		JLabel lblAAA1 = new JLabel("0  1  2  3  4  5  6  7  8  9");
		lblAAA1.setBounds(800, 244, 577, 43);
		layeredPane.setLayer(lblAAA1, 1);
		layeredPane.add(lblAAA1);
		lblAAA1.setForeground(Color.WHITE);
		lblAAA1.setFont(new Font("Snap ITC", Font.BOLD, 31));

		// Hàng A bản đồ của người chơi
		JLabel lblA1 = new JLabel("A");
		layeredPane.setLayer(lblA1, 1);
		lblA1.setBounds(739, 287, 32, 52);
		layeredPane.add(lblA1);
		lblA1.setForeground(Color.WHITE);
		lblA1.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng B bản đồ của người chơi
		JLabel lblB1 = new JLabel("B");
		layeredPane.setLayer(lblB1, 1);
		lblB1.setBounds(739, 339, 32, 52);
		layeredPane.add(lblB1);
		lblB1.setForeground(Color.WHITE);
		lblB1.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng C bản đồ của người chơi
		JLabel lblC1 = new JLabel("C");
		layeredPane.setLayer(lblC1, 1);
		lblC1.setBounds(739, 391, 32, 52);
		layeredPane.add(lblC1);
		lblC1.setForeground(Color.WHITE);
		lblC1.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng D bản đồ của người chơi
		JLabel lblD1 = new JLabel("D");
		layeredPane.setLayer(lblD1, 1);
		lblD1.setBounds(739, 443, 32, 52);
		layeredPane.add(lblD1);
		lblD1.setForeground(Color.WHITE);
		lblD1.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng E bản đồ của người chơi
		JLabel lblE1 = new JLabel("E");
		layeredPane.setLayer(lblE1, 1);
		lblE1.setBounds(739, 495, 32, 52);
		layeredPane.add(lblE1);
		lblE1.setForeground(Color.WHITE);
		lblE1.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng F bản đồ của người chơi
		JLabel lblF1 = new JLabel("F");
		layeredPane.setLayer(lblF1, 1);
		lblF1.setBounds(739, 547, 32, 52);
		layeredPane.add(lblF1);
		lblF1.setForeground(Color.WHITE);
		lblF1.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng G bản đồ của người chơi
		JLabel lblG1 = new JLabel("G");
		layeredPane.setLayer(lblG1, 1);
		lblG1.setBounds(739, 599, 32, 52);
		layeredPane.add(lblG1);
		lblG1.setForeground(Color.WHITE);
		lblG1.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng H bản đồ của người chơi
		JLabel lblH1 = new JLabel("H");
		layeredPane.setLayer(lblH1, 1);
		lblH1.setBounds(739, 651, 32, 52);
		layeredPane.add(lblH1);
		lblH1.setForeground(Color.WHITE);
		lblH1.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng I bản đồ của người chơi
		JLabel lblI1 = new JLabel("I");
		layeredPane.setLayer(lblI1, 1);
		lblI1.setBounds(739, 703, 32, 52);
		layeredPane.add(lblI1);
		lblI1.setForeground(Color.WHITE);
		lblI1.setFont(new Font("Snap ITC", Font.BOLD, 33));
		
		// Hàng J bản đồ của người chơi
		JLabel lblJ1 = new JLabel("J");
		layeredPane.setLayer(lblJ1, 1);
		lblJ1.setBounds(739, 755, 32, 52);
		layeredPane.add(lblJ1);
		lblJ1.setForeground(Color.WHITE);
		lblJ1.setFont(new Font("Snap ITC", Font.BOLD, 33));

		// khởi tạo Button Play cho phép bắt đầu trò chơi 
		btnPlay = new JButton("Play");
		layeredPane.setLayer(btnPlay, 1);
		btnPlay.setBounds(120, 359, 300, 75);
		layeredPane.add(btnPlay);
		btnPlay.setBackground(new Color(135,206,235));
		btnPlay.setFont(new Font("Snap ITC", Font.BOLD, 30));
		
		// khởi tạo Button Random cho phép đặt thuyền ngẫu nhiên
		JButton randomButton = new JButton("Random");
		layeredPane.setLayer(randomButton, 1);
		randomButton.setFont(new Font("Snap ITC", Font.BOLD, 30));
		randomButton.setBackground(new Color(135,206,235));
		randomButton.setBounds(120, 447, 300, 75);
		layeredPane.add(randomButton);	
		randomButton.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent arg0) {
				random ();	
			}
		});

		// Khởi tạo button reset để đặt lại thuyền
		JButton reset = new JButton("Reset");
		layeredPane.setLayer(reset, 1);
		reset.setBounds(120, 535, 300, 75);
		layeredPane.add(reset);
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();			
			}
		});
		reset.setFont(new Font("Snap ITC", Font.BOLD, 30));
		reset.setBackground(new Color(135,206,235));
		
		// Khởi tạo button Back để quay về StartMenu
		Back = new JButton("Back");
		layeredPane.setLayer(Back, 1);
		Back.setFont(new Font("Snap ITC", Font.BOLD, 30));
		Back.setBackground(new Color(135,206,235));
		Back.setBounds(120, 623, 300, 75);
		layeredPane.add(Back);

		// khởi tạo button Exit cho phép thoát trò chơi
		Exit = new JButton("Exit");
		Exit.setBackground(new Color(135,206,235));
		Exit.setFont(new Font("Snap ITC", Font.BOLD, 30));
		layeredPane.setLayer(Exit, 1);
		Exit.setBounds(120, 711, 300, 75);
		// thêm thành phần nhận sự kiện vào Button Exit(setShip), đóng chương trình khi bấm vào
		Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		layeredPane.add(Exit);

		// Tạo nhãn Help nhận sự kiện nhấp chuột hiện ra Message
		JLabel lblNewLabel_1 = new JLabel("Help");
		lblNewLabel_1.setForeground(Color.ORANGE);
		lblNewLabel_1.setFont(new Font("Snap ITC", Font.PLAIN, 30));
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "You should arrange your ships on the grid in prepration for the battle \n" + 
			            "The lieutenant says \n " + "The Carrier needs 5 spots on the grid \n" + 
						"The Battleship needs 4 spots onn the grid \n" 
						+ "the Cruiser and The Submarine need 3 spots on the grid \n" 
						+ " The Destroyer needs 2 spots on the grid \n" + 
						"Choose the formation wisely, May the force be with you", "Crew advice ", JOptionPane.INFORMATION_MESSAGE, new ImageIcon (Board.class.getResource("/com/img/madam.png")));		
			}
		});
		layeredPane.setLayer(lblNewLabel_1, 1);
		lblNewLabel_1.setIcon(new ImageIcon(SetShips.class.getResource("/com/img/help.png")));
		lblNewLabel_1.setBounds(27, 960, 131, 80);
		layeredPane.add(lblNewLabel_1);
		
		// Khởi tạo button nhận giá trị horizontal = true
		JRadioButton Horizontal = new JRadioButton("Horizontal");
		Horizontal.setBounds(1450, 616, 248, 30);
		
		// thêm vào trong layeredPane
		layeredPane.setLayer(Horizontal, 1);
		layeredPane.add(Horizontal);
		Horizontal.setBackground(new Color(135,206,235));
		Horizontal.setFont(new Font("Snap ITC", Font.PLAIN, 30));
		Horizontal.setForeground(Color.WHITE);
		Horizontal.setSelected(true);
		Horizontal.setOpaque(false);
		Horizontal.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent arg) {
				horizontal = true;	
			}
		});

		// cho button Horizontal vào trong buttonGroup
		buttonGroup.add(Horizontal);

		// Khởi tạo button nhận giá trị Vertical (horizontal = false)
		JRadioButton Vertical = new JRadioButton("Vertical");
		layeredPane.setLayer(Vertical, 1);
		Vertical.setBounds(1450, 659, 203, 25);
		
		// thêm vào trong layeredPane
		layeredPane.add(Vertical);
		Vertical.setOpaque(false);
		Vertical.setBackground(new Color(135,206,235));
		Vertical.setFont(new Font("Snap ITC", Font.PLAIN, 30));
		Vertical.setForeground(Color.WHITE);
		Vertical.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent arg) {
				horizontal = false;
			}
		});

		// cho button Vertical vào trong buttonGroup
		buttonGroup.add(Vertical);

		final JList list = new JList(shipList);
		list.setBounds(1450, 290, 203, 298);
		
		// thêm list vào trong layeredPane
		layeredPane.setLayer(list, 1);
		layeredPane.add(list);
	 	list.setForeground(Color.WHITE);
		list.setBackground(new Color(135,206,235));
		list.setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		list.setSelectedIndex(0);
		
		// nhận click chuột vào trong phần tử trong list từ đó lấy được độ dài thuyền cần đặt
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				shipSelected = shipList.getElementAt(list.getSelectedIndex());
				if (shipSelected.equals("Carrier"))
						shipSize = 5 ;
				else if (shipSelected.equals("Battleship"))
						shipSize = 4 ; 
				else if (shipSelected.equals("Cruiser")||shipSelected.equals("Submarine"))
						shipSize = 3;
				else if (shipSelected.equals("Destroyer"))
						shipSize = 2;
				listIndex = list.getSelectedIndex();
			
			}
		});
		
		list.setFont(new Font("Snap ITC", Font.PLAIN, 23));		
	}

	// khởi tạo giá trị ban đầu cho shipsLocation và shipList
	private void initiateShips () {
		shipsLocation.add("/");
		shipList.add("Carrier");
		shipList.add("Battleship");
		shipList.add("Cruiser");
		shipList.add("Submarine");
		shipList.add("Destroyer");
	}

	// cơ chế random đặt thuyền
	private void random () {
		// Xóa hết tất cả các thuyền đã được đặt trước đó
		reset();
		shipList.empty();
		shipsLocation = ShipGenerator.generateShip();
		NodeIndex = ShipGenerator.getNodeIndex();
		// Lấy tọa độ từ trong shipLocation đặt vào các vị trí các mảnh thuyền
		for (String location : shipsLocation) {
			if (!location.equals("/")) {
				String str = new String(NodeIndex.get(location).getImgURL() + ".png");
				ButtonIndex.get(location).setDisabledIcon(new ImageIcon(Board.class.getResource(str)));
				ButtonIndex.get(location).setIcon(new ImageIcon(Board.class.getResource(str)));	
			}
		}
	}

	public ArrayList<String> getShipsLocation() {
		return shipsLocation;
	}

	public ShipList getShipList() {
		return shipList;
	}

	public JButton getBtnPlay() {
		return btnPlay;
	}

	public HashMap<String, Node> getNodeIndex() {
		return NodeIndex;
	}

	public JButton getBtnBack() {
		return Back;
	}
	
}