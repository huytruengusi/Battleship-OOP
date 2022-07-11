package com.example;

import javax.swing.JToggleButton;
import javax.swing.ImageIcon;

public class Node {
    private String imgURL;
    private String pieceOfShips;
    public Node(){
    }
    
    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getPieceOfShips() {
        return pieceOfShips;
    }

    public void setPieceOfShips(String pieceOfShips) {
        this.pieceOfShips = pieceOfShips;
    };
}
