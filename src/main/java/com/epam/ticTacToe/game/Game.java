package com.epam.ticTacToe.game;

import com.epam.ticTacToe.server.ServerThread;

public class Game {
    private final Sector sector;
    private final CountChecker countChecker;
    private ServerThread currentPlayer;

    public Game(int height, int width, int winCount) {
        this.sector = new Sector(height, width);
        this.countChecker = new CountChecker(winCount);
    }

    public void setCurrentPlayer(ServerThread player) {
        this.currentPlayer = player;
    }

    public synchronized boolean doStepIsValid(int numberOfCell, ServerThread player) {
        if (player == currentPlayer && numberOfCell > 0 && numberOfCell <= sector.getHeight() * sector.getHeight()) {
            int[] position = calcPosition(numberOfCell);
            int x = position[0];
            int y = position[1];
            if (sector.getElement(x, y) == null) {
                sector.setElement(x, y, player.getCell());
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
        int[][] positions = new int[sector.getHeight() * sector.getWidth()][];
        int count = 0;
        for (int x = 0; x < sector.getHeight(); x++) {
            for (int y = 0; y < sector.getWidth(); y++) {
                positions[count] = new int[]{x, y};
                count++;
            }
        }
        return positions[--numberOfCell];
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

    public String convertBoardToString() {
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

    public void printBoard() {
        System.out.println(convertBoardToString().replaceAll("&", "\n"));
    }

    public void clear() {
        for (int i = 0; i < sector.getHeight(); i++) {
            for (int j = 0; j < sector.getWidth(); j++) {
                sector.setElement(i, j, null);
            }
        }
    }
}
