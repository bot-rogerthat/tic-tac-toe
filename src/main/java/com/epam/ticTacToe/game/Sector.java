package com.epam.ticTacToe.game;

public class Sector {
    private Cell[][] cells;

    public Sector(int height, int width) {
        if (height > 1 || width > 1) {
            this.cells = new Cell[height][width];
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int getHeight() {
        return cells.length;
    }

    public int getWidth() {
        return cells[0].length;
    }

    public Cell getElement(int x, int y) {
        if (x < 0 || x >= getHeight() || y < 0 || y >= getWidth()) {
            throw new IllegalArgumentException();
        }
        return cells[x][y];
    }

    public void setElement(int x, int y, Cell element) {
        if (x > 0 || x < getHeight() || y > 0 || y < getWidth()) {
            cells[x][y] = element;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Sector clone() {
        Sector clone = new Sector(cells.length, cells[0].length);
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[0].length; y++) {
                clone.setElement(x, y, this.getElement(x, y));
            }
        }
        return clone;
    }
}
