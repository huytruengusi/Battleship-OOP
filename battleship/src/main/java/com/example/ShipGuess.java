package com.example;

import java.util.ArrayList;

public class ShipGuess {
	private String hit;
	protected ArrayList <String> possible = new ArrayList <String>();

	public ShipGuess() {
		
	}

	protected void think (int conStrikes, String hit){
		if (conStrikes==0) {
			// Lưu tọa độ của 4 điểm bên cạnh điểm vừa bắn vào trong possible
			String hit1 = (char) (hit.charAt(0) + 1) + "" +hit.charAt(1);
			String hit2= hit.charAt(0)+ "" +(char) (hit.charAt(1)+1);
			String hit3 = (char) (hit.charAt(0) - 1) + "" +hit.charAt(1);
			String hit4 = hit.charAt(0)+ "" +(char) (hit.charAt(1)-1);
			if (hit1.charAt(0) < 'K' && hit1.charAt(0) > '@' && hit1.charAt(1) < ':' && hit.charAt(1) > '/')
				this.possible.add(hit1);
			if (hit2.charAt(0) < 'K' && hit2.charAt(0) > '@' && hit2.charAt(1) < ':' && hit2.charAt(1) > '/')
				this.possible.add(hit2);
			if (hit3.charAt(0) < 'K' && hit3.charAt(0) > '@' && hit3.charAt(1) < ':' && hit3.charAt(1) > '/')
				this.possible.add(hit3);
			if (hit4.charAt(0) < 'K' && hit4.charAt(0) > '@' && hit4.charAt(1) < ':' && hit4.charAt(1) > '/')
				this.possible.add(hit4);
		}
		else {
			// bắn dọc theo 2 hit vừa trúng
			this.possible.clear();
			int num1 = hit.charAt(0) - this.hit.charAt(0);
			int num2 = hit.charAt(1) - this.hit.charAt(1);
			String hit1 = hit;
			String hit2 = hit;
			
			if (num1 > 0){
				hit1 = (char) (hit.charAt(0) - (conStrikes+1)) + "" +hit.charAt(1);
				hit2 = (char) (hit.charAt(0) + 1) + "" +hit.charAt(1);
			}else if (num1 < 0){
				hit1 = (char) (hit.charAt(0) - 1) + "" +hit.charAt(1);
				hit2 = (char) (hit.charAt(0) + (conStrikes+1)) + "" +hit.charAt(1);
			}

			if (num2 > 0){
				hit1 = hit.charAt(0) + "" +(char) (hit.charAt(1)-(conStrikes+1));	
				hit2 = hit.charAt(0) + "" +(char) (hit.charAt(1)+1);
			}else if (num2 < 0){
				hit1 = hit.charAt(0)+ "" +(char) (hit.charAt(1)-1);
				hit2 = hit.charAt(0)+ "" +(char) (hit.charAt(1)+(conStrikes+1)); 	
			}

			if (hit1.charAt(0)<'K' && hit1.charAt(0)>'@' && hit1.charAt(1)<':' && hit1.charAt(1)>'/')
				this.possible.add(hit1);
			if (hit2.charAt(0)<'K' && hit2.charAt(0)>'@' && hit2.charAt(1)<':' && hit2.charAt(1)>'/')
				this.possible.add(hit2);
		}
	}
	
	public ArrayList<String> getPossible() {
		return possible;
	}

	protected void setHit (String hit) {
		this.hit = hit;
	}

	public String getHit() {
		return hit;
	}

	public void clearPossible(){
		possible = new ArrayList<>();
	}
}
