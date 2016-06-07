package com.epam.ticTacToe;

public class App {
    public static void main(String[] args) {

        Server server = new Server();
        Sector sector = new Sector(3,3);
        Game game = new Game(3);
        System.out.println(game.init(sector));

        Locator locator = new Locator(game.getRule());

        game.doStep(0, 0, Cell.CROSS, sector);
        System.out.println(locator.findWinner(sector));
        System.out.println(game.showStep(sector));
        game.doStep(2, 2, Cell.ZERO, sector);
        System.out.println(locator.findWinner(sector));
        System.out.println(game.showStep(sector));
        game.doStep(1, 2, Cell.CROSS, sector);
        System.out.println(locator.findWinner(sector));
        System.out.println(game.showStep(sector));
        game.doStep(2, 1, Cell.ZERO, sector);
        System.out.println(locator.findWinner(sector));
        System.out.println(game.showStep(sector));
        game.doStep(2, 0, Cell.CROSS, sector);
        System.out.println(locator.findWinner(sector));
        System.out.println(game.showStep(sector));
        game.doStep(0, 1, Cell.ZERO, sector);
        System.out.println(locator.findWinner(sector));
        System.out.println(game.showStep(sector));
        game.doStep(1, 1, Cell.CROSS, sector);
        System.out.println(locator.findWinner(sector));
        System.out.println(game.showStep(sector));
        game.doStep(1, 0, Cell.ZERO, sector);
        System.out.println(locator.findWinner(sector));
        System.out.println(game.showStep(sector));
        game.doStep(0, 2, Cell.CROSS, sector);
        System.out.println(locator.findWinner(sector));
        System.out.println(game.showStep(sector));
    }
}
