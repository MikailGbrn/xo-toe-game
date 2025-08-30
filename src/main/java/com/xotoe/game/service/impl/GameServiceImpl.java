package com.xotoe.game.service.impl;

import com.xotoe.game.service.GameService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GameServiceImpl implements GameService {

    private String[][] board;
    private String currentPlayer = "X";

    @Override
    public void setBoard(Integer size) {
        board = new String[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = " "; // empty
            }
        }
    }

    @Override
    public String[][] getBoard() {
        return board;
    }

    @Override
    public boolean makeMove(int row, int col, String player) {
        if (Objects.equals(board[row][col], " ")) {
            board[row][col] = player;
            switchPlayer();
            return true;
        }
        return false; // invalid move
    }

    @Override
    public String getNextPlayer() {
        return currentPlayer;
    }

    private void switchPlayer() {
        this.currentPlayer = (Objects.equals(this.currentPlayer, "X")) ? "O" : "X";
    }

    public String checkWinner(Integer size, Integer winCondition) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                String player = board[row][col];
                if (player == " ") continue;

                if (checkDirection(row, col, 1, 0, player, size, winCondition)) return player; // horizontal
                if (checkDirection(row, col, 0, 1, player, size, winCondition)) return player; // vertical
                if (checkDirection(row, col, 1, 1, player, size, winCondition)) return player; // diagonal ↘
                if (checkDirection(row, col, 1, -1, player, size, winCondition)) return player; // diagonal ↙
            }
        }
        return null; // no winner yet
    }

    private boolean checkDirection(
            int row,
            int col,
            int dRow,
            int dCol,
            String player,
            Integer size,
            Integer winCondition
    ) {
        int count = 0;
        for (int i = 0; i < winCondition; i++) {
            int r = row + i * dRow;
            int c = col + i * dCol;

            if (r < 0 || r >= size || c < 0 || c >= size) {
                return false; // out of bounds
            }

            if (board[r][c] == player) {
                count++;
            } else {
                return false;
            }
        }
        return (count == winCondition);
    }

    public boolean isDraw(Integer size, Integer winCondition) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Objects.equals(board[i][j], " ")) {
                    return false; // still empty spots
                }
            }
        }
        return (checkWinner(size, winCondition) == null); // full but no winner
    }
}
