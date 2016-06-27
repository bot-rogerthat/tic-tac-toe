package com.epam.ticTacToe.server;

import com.epam.ticTacToe.command.Command;
import com.epam.ticTacToe.game.Cell;
import com.epam.ticTacToe.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static com.epam.ticTacToe.command.Command.*;

public class ServerThread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(ServerThread.class);
    private final Socket socket;
    private final Game game;
    private final Cell cell;
    private ServerThread opponent;
    private BufferedReader input;
    private PrintWriter output;

    public ServerThread(Socket socket, Game game, Cell cell) {
        this.socket = socket;
        this.game = game;
        this.cell = cell;
        sendGreeting();
    }

    @Override
    public void run() {
        try {
            sendMsgToStartGame();
            while (true) {
                String response = input.readLine();
                Command command = Command.getCmd(response);
                if (!command.apply(response, null, output, game, this)) {
                    break;
                }
            }
        } catch (IOException e) {
            log.error("Client is disconnected: ", e);
            System.out.println("Player died: " + e);
        }
    }

    private void sendMsgToStartGame() {
        output.println(MESSAGE + " All players connected");
        output.println(MESSAGE + " Game start!");
        output.println(SHOW + " " + game.convertBoardToString());
        if (cell == Cell.CROSS) {
            output.println(STEP + " Your move");
        } else {
            output.println(MESSAGE + " Opponent moved, please wait");
        }
    }

    private void sendGreeting() {
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            char value = cell == Cell.CROSS ? 'X' : 'O';
            output.println("WELCOME " + value);
            output.println(MESSAGE + " Waiting for opponent to connect");
        } catch (IOException e) {
            log.error("Client is disconnected: ", e);
        }
    }

    public void otherPlayerMoved(int location) {
        output.println(SHOW + " " + game.convertBoardToString());
        output.println(game.hasWinner() ? DEFEAT : game.isFullBoard() ? TIE : "");
        output.println(OPPONENT_MOVED + " " + location);
    }

    public void setOpponent(ServerThread opponent) {
        this.opponent = opponent;
    }

    public ServerThread getOpponent() {
        return opponent;
    }

    public Cell getCell() {
        return cell;
    }
}
