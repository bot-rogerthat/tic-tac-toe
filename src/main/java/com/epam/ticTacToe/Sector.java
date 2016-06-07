package com.epam.ticTacToe;

public class Sector {
    private Cell[][] cells;

    public Sector(int height, int width) {
        if (height < 1 || width < 1) {
            throw new IllegalArgumentException();
        }
        this.cells = new Cell[height][width];
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
        if (x < 0 || x >= getHeight() || y < 0 || y >= getWidth()) {
            throw new IllegalArgumentException();
        }
        cells[x][y] = element;
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
