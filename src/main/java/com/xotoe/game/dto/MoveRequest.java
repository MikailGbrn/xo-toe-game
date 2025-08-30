package com.xotoe.game.dto;

public class MoveRequest {
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    private int row;
    private int col;
    // getters + setters
}
