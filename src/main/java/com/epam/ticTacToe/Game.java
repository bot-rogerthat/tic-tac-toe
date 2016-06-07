package com.epam.ticTacToe;

public class Game {
    private final int rule;

    public Game(int rule) {
        this.rule = rule;
    }

    public int getRule() {
        return rule;
    }

    public String init(Sector sector) {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < sector.getHeight(); x++) {
            for (int y = 0; y < sector.getWidth(); y++) {
                sb.append(" - ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void doStep(int x, int y, Cell cell, Sector sector) {
        sector.setElement(x, y, cell);
    }

    public String showStep(Sector sector) {
        StringBuilder sb = new StringBuilder();
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
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}
