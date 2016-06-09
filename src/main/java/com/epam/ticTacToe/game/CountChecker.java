package com.epam.ticTacToe.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CountChecker {
    private int winCount;

    public CountChecker(int winCount) {
        this.winCount = winCount;
    }

    public String findWinner(Sector sector) {
        int crossCount = 0;
        int zeroCount = 0;

        for (int x = 0; x < sector.getHeight(); x++) {
            for (int y = 0; y < sector.getWidth(); y++) {
                if (sector.getElement(x, y) == Cell.CROSS) {
                    crossCount = findMaxCount(x, y, Cell.CROSS, sector);
                } else if (sector.getElement(x, y) == Cell.ZERO) {
                    zeroCount = findMaxCount(x, y, Cell.ZERO, sector);
                }
            }
        }

        if (zeroCount == winCount) {
            return "winner - O";
        } else if (crossCount == winCount) {
            return "winner - X";
        } else {
            return "next";
        }
    }

    private int findMaxCount(int x, int y, Cell cell, Sector sector) {
        int count1 = checkVertLine(y, cell, sector);
        int count2 = checkHorizLine(x, cell, sector);
        int count3 = checkLeftDiag(cell, sector);
        int count4 = checkRightDiag(cell, sector);
        return Collections.max(Arrays.asList(count1, count2, count3, count4));
    }

    private int checkVertLine(int y, Cell cell, Sector sector) {
        List<Integer> counts = new ArrayList<>();
        int count = 0;
        int x = 0;
        while (x < sector.getHeight() && count != winCount) {
            if (sector.getElement(x, y) == cell) {
                count++;
            } else {
                count = 0;
            }
            counts.add(count);
            x++;
        }
        return counts.isEmpty() ? 0 : Collections.max(counts);
    }

    private int checkHorizLine(int x, Cell cell, Sector sector) {
        List<Integer> counts = new ArrayList<>();
        int count = 0;
        int y = 0;
        while (y < sector.getWidth() && count != winCount) {
            if (sector.getElement(x, y) == cell) {
                count++;
            } else {
                count = 0;
            }
            counts.add(count);
            y++;
        }
        return counts.isEmpty() ? 0 : Collections.max(counts);
    }

    private int checkLeftDiag(Cell cell, Sector sector) {
        List<Integer> counts = new ArrayList<>();
        int count = 0;
        int y = 0;
        int x = 0;
        while (y < sector.getWidth() && x < sector.getHeight() && count != winCount) {
            if (sector.getElement(x, y) == cell) {
                count++;
            } else {
                count = 0;
            }
            counts.add(count);
            x++;
            y++;
        }
        return counts.isEmpty() ? 0 : Collections.max(counts);
    }

    private int checkRightDiag(Cell cell, Sector sector) {
        List<Integer> counts = new ArrayList<>();
        int count = 0;
        int y = sector.getWidth() - 1;
        int x = 0;
        while ((y >= 0 && x < sector.getHeight()) && count != winCount) {
            if (sector.getElement(x, y) == cell) {
                count++;
            } else {
                count = 0;
            }
            counts.add(count);
            x++;
            y--;
        }
        return counts.isEmpty() ? 0 : Collections.max(counts);
    }
}
