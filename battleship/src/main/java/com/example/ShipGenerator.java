package com.example;

import java.util.ArrayList;
import java.util.HashMap;

public class ShipGenerator {
	private static ArrayList <String> shipsLocation;
	private static int [] shipSizes = {5,4,3,3,2};
	private static String[] shipNames = {"Carrier","Battleship","Cruiser","Submarine","Destroyer"};
	private static HashMap<String,Node> NodeIndex ;
	
	// hàm lấy ngẫu nhiên giá trị nếu lớn hơn 5 thì đặt thuyền theo chiều ngang, nếu không đặt theo chiều dọc
	private static boolean toss () {
		 if ((int)( Math.random()*10+1)>5)
			 return true;
		 else {
			 return false;
		}
	}
	
	protected static ArrayList<String> generateShip(){
		// tạo một ArrayList lưu vị trí các thuyền ngăn cách các vị trí bởi "/"
		shipsLocation = new ArrayList <String> ();
		NodeIndex = new HashMap <String,Node> ();
		shipsLocation.add("/");

		// từ các phân tử trong shipSizes
		for (int i=0; i<5; i++ ) {
			// khởi tạo String mới với kích thước bằng phần tử trong shipSizes
			String[] currentShip = new String[shipSizes[i]];
			boolean flag  = false;
			while (!flag) {
				flag = true ;
				char l;
				int n;
				Boolean horizontal = toss();
				if (horizontal) {
					// Gọi hàm random lấy ra một ký tự trong khoảng A-J
					// và lấy ra một số trong khoảng từ 0-(10-i)
					l = (char) (int) (Math.random()*10 +65);
					n = (int)(Math.random() *(10-shipSizes[i]) );
					// thêm tọa độ vị trí của các mảnh của thuyền vào currentShip 
					for(int j=0; j<shipSizes[i]; j++) {
						currentShip[j] = l+""+(n+j);
					}
				}
				else {
					// Gọi hàm random lấy ra một ký tự trong khoảng A-(J-i)
					// và lấy ra một số trong khoảng từ 0-10
					l = (char) (int) (Math.random()*(10-shipSizes[i]) +65);
					n = (int)(Math.random() *(10));
					// thêm tọa độ vị trí của các mảnh của thuyền vào currentShip 
					for(int j=0; j<shipSizes[i]; j++) {
						currentShip[j] = (char)(l+j)+""+n;
					}
				}
				// Lấy ra từng thành phần của currentShip xem xét nó đã có mặt trong shipsLocation chưa. Nếu có đặt lại flag = false và hủy vòng lặp
				for (String s:currentShip) {
					if (shipsLocation.contains(s)) {
						flag = false;
						break;
					}
				}

				// nếu như mà không bị trùng lặp thì add vào trong shipsLocation
				if (flag) {
					for (String s:currentShip) {
						shipsLocation.add(s);
					}
					for (int j = 0; j < shipSizes[i]; j++) {
						// thiết lập lại các node của button chứa thuyền
						Node node = new Node();
						String str;
						if(horizontal){
							str = new String("/com/img/Horizontal/" + shipNames[i]+ "/" + shipNames[i] + "-" + (j+1));
						}else{
							str = new String("/com/img/Vertical/" + shipNames[i]+ "/" + shipNames[i] + "-" + (j+1));
						}
						node.setPieceOfShips(shipNames[i]);
						node.setImgURL(str);
						NodeIndex.put(currentShip[j],node);
					}
				}
			}
			shipsLocation.add("/");
		}
		return shipsLocation;
	}

	public static HashMap<String, Node> getNodeIndex() {
		return NodeIndex;
	}
}
