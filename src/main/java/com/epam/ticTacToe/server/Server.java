package com.epam.ticTacToe.server;

import com.epam.ticTacToe.game.Cell;
import com.epam.ticTacToe.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;

public class Server {
    private static final Logger log = LoggerFactory.getLogger(Server.class);
    private final int PORT = 4444;
    private final Game game = new Game(3, 3, 3);


    public void runServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("Listening on port: " + serverSocket.getLocalPort());
            while (true) {
                ServerThread playerX = new ServerThread(serverSocket.accept(), game, Cell.CROSS);
                System.out.println("Connected: " + playerX.getName());
                ServerThread playerO = new ServerThread(serverSocket.accept(), game, Cell.ZERO);
                System.out.println("Connected: " + playerO.getName());
                game.clear();
                playerX.setOpponent(playerO);
                playerO.setOpponent(playerX);
                game.setCurrentPlayer(playerX);
                playerX.start();
                playerO.start();
                System.out.println("Game start!");
                System.out.println(game.showBoard().replaceAll("&", "\n"));
            }
        } catch (Exception e) {
            log.error("Can not start server: ", e);
        }
    }

    public static void main(String[] args) {
        new Server().runServer();
    }
}
