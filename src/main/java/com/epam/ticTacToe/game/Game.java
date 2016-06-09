package com.epam.ticTacToe.game;

import com.epam.ticTacToe.server.ServerThread;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private final int count;
    private Sector sector;
    private CountChecker countChecker;
    private ServerThread currentPlayer;

    public Game(int x, int y, int count) {
        this.sector = new Sector(x, y);
        this.count = count;
        this.countChecker = new CountChecker(this.count);
    }

    public void setCurrentPlayer(ServerThread player) {
        this.currentPlayer = player;
    }

    public synchronized boolean doStepIsValid(int numberOfCell, Cell cell, ServerThread player) {
        if (player == currentPlayer && numberOfCell > 0 && numberOfCell <= sector.getHeight() * sector.getHeight()) {
            int[] position = calcPosition(numberOfCell);
            int x = position[0];
            int y = position[1];
            if (sector.getElement(x, y) == null) {
                sector.setElement(x, y, cell);
            } else {
                return false;
            }
            currentPlayer = currentPlayer.getOpponent();
            currentPlayer.otherPlayerMoved(numberOfCell);
            return true;
        } else {
            return false;
        }
    }

    private int[] calcPosition(int numberOfCell) {
        Map<Integer, int[]> map = new HashMap<>();
        int target = 0;
        for (int x = 0; x < sector.getHeight(); x++) {
            for (int y = 0; y < sector.getWidth(); y++) {
                map.put(++target, new int[]{x, y});
            }
        }
        return map.get(numberOfCell);
    }

    public boolean hasWinner() {
        return countChecker.findWinner(sector).startsWith("winner");
    }

    public boolean isFullBoard() {
        for (int i = 0; i < sector.getHeight(); i++) {
            for (int j = 0; j < sector.getWidth(); j++) {
                if (sector.getElement(i, j) == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public synchronized String showBoard() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------&");
        for (int x = 0; x < sector.getHeight(); x++) {
            for (int y = 0; y < sector.getWidth(); y++) {
                if (sector.getElement(x, y) == Cell.CROSS) {
                    sb.append("|X|");
                } else if (sector.getElement(x, y) == Cell.ZERO) {
                    sb.append("|O|");
                } else {
                    sb.append(" - ");
                }
            }
            sb.append("&");
        }
        sb.append("---------");
        return sb.toString();
    }

    public void clear() {
        for (int i = 0; i < sector.getHeight(); i++) {
            for (int j = 0; j < sector.getWidth(); j++) {
                sector.setElement(i, j, null);
            }
        }
    }
}
