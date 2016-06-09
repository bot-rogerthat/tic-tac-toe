package com.epam.ticTacToe.server;

import com.epam.ticTacToe.game.Cell;
import com.epam.ticTacToe.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static com.epam.ticTacToe.server.ServerThread.ClientCommand.MOVE;
import static com.epam.ticTacToe.server.ServerThread.ClientCommand.QUIT;

public class ServerThread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(ServerThread.class);
    private Socket socket;
    private Game game;
    private Cell cell;
    private ServerThread opponent;
    private BufferedReader input;
    private PrintWriter output;

    public ServerThread(Socket socket, Game game, Cell cell) {
        this.socket = socket;
        this.game = game;
        this.cell = cell;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            char value = cell == Cell.CROSS ? 'X' : 'O';
            output.println("WELCOME " + value);
            output.println("MESSAGE Waiting for opponent to connect");
        } catch (IOException e) {
            log.error("Client is disconnected: ", e);
            System.out.println("Player died: " + e);
        }
    }

    @Override
    public void run() {
        try {
            output.println("MESSAGE All players connected");
            output.println("MESSAGE Game start!");
            output.println("SHOW " + game.showBoard());
            if (cell == Cell.CROSS) {
                output.println("STEP Your move");
            }
            while (true) {
                String command = input.readLine();
                if (command.startsWith(MOVE.toString())) {
                    int location = -1;
                    try {
                        location = Integer.parseInt(command.substring(5));
                    } catch (NumberFormatException e) {
                        log.error("Input error: ", e);
                    }

                    if (game.doStepIsValid(location, this.cell, this)) {
                        output.println("VALID_MOVE");
                        System.out.println(game.showBoard().replaceAll("&", "\n"));
                        output.println("SHOW " + game.showBoard());
                        output.println(game.hasWinner() ? "VICTORY" : game.isFullBoard() ? "TIE" : "");
                    } else {
                        output.println("ERROR ?");
                    }
                } else if (command.startsWith(QUIT.toString())) {
                    return;
                }
            }
        } catch (IOException e) {
            log.error("Client is disconnected: ", e);
            System.out.println("Player died: " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log.error("Socket close error: ", e);
            }
        }
    }

    public void otherPlayerMoved(int location) {
        output.println("SHOW " + game.showBoard());
        output.println(game.hasWinner() ? "DEFEAT" : game.isFullBoard() ? "TIE" : "");
        output.println("OPPONENT_MOVED " + location);
    }

    public void setOpponent(ServerThread opponent) {
        this.opponent = opponent;
    }

    public ServerThread getOpponent() {
        return opponent;
    }

    enum ClientCommand {
        MOVE,
        QUIT
    }
}
