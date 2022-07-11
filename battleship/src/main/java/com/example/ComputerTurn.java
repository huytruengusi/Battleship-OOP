package com.example;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.JFrame;


public class ComputerTurn implements Runnable{
	private HashMap<String,JToggleButton > ButtonIndex;
	private HashMap<String,Node> NodeIndex ;
	private ArrayList <String> previousHit = new ArrayList<String>();
	private Board board;
	private ShipGuess brains = new ShipGuess();
	private String testHit;
	private String hit;
	private int conStrikes;

	public ComputerTurn(HashMap<String,JToggleButton> map, HashMap<String,Node> node, Board board) {
		ButtonIndex = map;
		NodeIndex = node;
		this.board = board;
	}

	@Override
	public void run() {
		previousHit = new ArrayList<String>();
		ArrayList <String> previousStrikes = new ArrayList<String>();
		while (true) {
			boolean flag = true;
			synchronized (this){
				if (!StartGame.isPlayerTurn()) {
				// khi possible còn điểm thì băn check theo các điểm trong possible
					while (!brains.getPossible().isEmpty() ){
						flag = true;
						testHit = brains. getPossible().get(0);
						brains. getPossible().remove(0);

						// nếu như testHit chưa bị bắn thì bắn vào, thêm hit vào trong previosStrikes
						if (!previousStrikes.contains(testHit) ) {
							hit = testHit;
							previousStrikes.add(hit);
							flag = false;
							break;
						}
					}

					if (flag) {
						// if (StartGame.isDifficult() && ((int)(Math.random()*10) >= 0)){
						if (StartGame.isDifficult() ){
							while(true){
								ArrayList<String> list = StartGame.getPlayerShips();
								int i = list.size();
								int location = (int)(Math.random()*i);
								if (list.get(location) != "/"){
									hit = list.get(location);
									previousStrikes.add(hit);
										conStrikes =0;
										break;
								}
							}
						}else{
							while (true){
								// testHit ở vị trí ngẫu nhiên
								testHit = (char) (int) (Math.random()*10 +65) + "" + ((int)(Math.random() *10 ));
								if (testHit.charAt(0)<'K' && testHit.charAt(0)>'@' && testHit.charAt(1)<':' && testHit.charAt(1)>'/') {
									if (!previousStrikes.contains(testHit) ) {
										hit = testHit;
										previousStrikes.add(hit);
										conStrikes =0;
										break;
									}
								}
							}
						}
					}
					try{
						Thread.sleep(1000);
					}catch (Exception ex){
						ex.printStackTrace();
					}

					// Lấy ra button với địa chỉ từ hit
					JToggleButton button = ButtonIndex.get(hit);

					// Viết vào textArea vị trí máy đã bắn
					board.appendTextArea ("Bot striked " + hit + ": ");

					// nếu địa chỉ của hit có trong địa chỉ của playerShips thì xóa địa chỉ hit khỏi playerShip
					// giảm máu người chơi
					if (StartGame.getPlayerShips().contains(hit)) {
						StartGame.getPlayerShips().remove(hit);
						previousHit.add(hit);
						StartGame.setPlayerHP(StartGame.getPlayerHP()-1);
						board.setTextPlayerHP("HP: " + StartGame.getPlayerHP());

						// nếu trước đó chưa bắn trúng thì thiết lập điểm vừa bắn trúng
						if (conStrikes == 0){
							brains.setHit(hit);
							brains.think(conStrikes++,hit);
						}
						else if (NodeIndex.get(hit).getPieceOfShips().contains(NodeIndex.get(brains.getHit()).getPieceOfShips())) {
							brains.think(conStrikes++, hit);
						}
						// thiết lập icon cho button
						String str = new String(NodeIndex.get(hit).getImgURL()+"-hit.png");
						button.setDisabledIcon(new ImageIcon(ComputerTurn.class.getResource(str)));
						button.setIcon(new ImageIcon(ComputerTurn.class.getResource(str)));	
						// Viết vào textArea báo đó là phát bắn chính xác
						board.appendTextArea("It was a hit \n");
						int Count = 0; 

						// cho hàm chạy qua tất cả các phần tử của enemyShips nếu phần tử này là "/" thì tăng Count
						for (int i = 0 ; i < StartGame.getPlayerShips().size();i++){
							if (StartGame.getPlayerShips().get(i).equals("/")){
								Count ++;
							}
							else{
								Count = 0;
							}
							// nếu có 2 "/" đứng cạnh nhau thì gọi ra hàm nổ và bỏ bớt một ngoặc
							// Viết vào textArea báo thuyền đã chìm, giảm số thuyền còn lại
							if (Count == 2) {
								String nameships =  new String(NodeIndex.get(hit).getPieceOfShips());
								for (int j=0; j<previousHit.size(); j++){
									if (NodeIndex.get(previousHit.get(j)).getPieceOfShips().contains(nameships)){
										String imgURL = NodeIndex.get(previousHit.get(j)).getImgURL() + "-death.png" ;
										ButtonIndex.get(previousHit.get(j)).setIcon(new ImageIcon(Board.class.getResource(imgURL)));
										ButtonIndex.get(previousHit.get(j)).setDisabledIcon(new ImageIcon(Board.class.getResource(imgURL)));
										previousHit.remove(j--);
									}
								}
								
								brains.clearPossible();
								if (!previousHit.isEmpty()){
									conStrikes = 0;
									brains.setHit(previousHit.get(0));
									brains.think(conStrikes++, previousHit.get(0));
								}
								StartGame.setNumberOfPlayerShips(StartGame.getNumberOfPlayerShips()-1);
								board.setTextPlayerShips("SHIPS: " + StartGame.getNumberOfPlayerShips());
								board.appendTextArea("Captin we lost " + nameships + " \n");
								if (StartGame.isMusicOn())
									StartGame.boom();
								StartGame.getPlayerShips().remove(i);
							}
						}
					} else {
						// Thiết lập lại icon bắn trượt cho button, chuyển lượt bắn cho người chơi
						// Viết vào textArea báo bắn trượt
						button.setIcon(new ImageIcon(Board.class.getResource("/com/img/miss.png")));
						button.setDisabledIcon(new ImageIcon(Board.class.getResource("/com/img/miss.png")));
						board.appendTextArea("It was a miss\n");
						StartGame.setPlayerTurn(true);
					}

					// nếu đã chiến thắng hiện ra dialog
					if (StartGame.getNumberOfPlayerShips() == 0) {
						GameEnd gameEnd = new GameEnd(false);
						gameEnd.setExtendedState(JFrame.MAXIMIZED_BOTH);
						gameEnd.setUndecorated (true);
						gameEnd.setVisible(true);
						board.dispose();
						break;
					}
				}
				if (StartGame.getNumberOfComputerShips() == 0) {
					break;
				}
			}
		}
	}
}
