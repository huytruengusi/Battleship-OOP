package com.example;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;

public class ShipList implements ListModel  {
	private ArrayList<String> ships = new ArrayList<String>();
	public ShipList() {

	}

	@Override
	public void addListDataListener(ListDataListener l) {
		
	}
	
	@Override
	public void removeListDataListener(ListDataListener l) {
		
	}

	@Override
	public String getElementAt(int index) {
		return ships.get(index);
	}

	@Override
	// trả về độ dài của shipList
	public int getSize() {
		return ships.size();
	}

	// xóa thành phần tại vị trí index
	public void remove (int index) {
		if (index < ships.size())
			ships.remove(index);
	}

	// xóa thành phần với 
	public void remove (String element) {
		if (ships.contains(element))
			ships.remove(element);
	}

	// thêm thành phần mới vào trong shipList
	public void add(String element) {
		ships.add(element);
	}

	// thiết lập shipList mới
	public void empty () {
		ships = new ArrayList<String>();
	}
}
