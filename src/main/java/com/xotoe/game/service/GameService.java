package com.xotoe.game.service;

public interface GameService {

    void setBoard(Integer tileSize);

    String[][] getBoard();

    String getNextPlayer();

    boolean makeMove(int row, int col, String player);

    String checkWinner(Integer size, Integer winCondition);

    boolean isDraw(Integer size, Integer winCondition);
}
