package com.epam.ticTacToe.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static com.epam.ticTacToe.client.Client.ServerCommand.*;

public class Client {
    private static final Logger log = LoggerFactory.getLogger(Client.class);
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public static void main(String[] args) {
        new Client("localhost", 4444).run();
    }

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            log.error("Error to init: ", e);
        }
    }

    public void run() {
        String response;
        try {
            response = in.readLine();
            System.out.println(response);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                response = in.readLine();
                if (response.startsWith(VALID_MOVE.toString())) {
                    System.out.println("Valid move, please wait");
                } else if (response.startsWith(OPPONENT_MOVED.toString())) {
                    System.out.println("Opponent moved, your turn");
                    System.in.read(new byte[System.in.available()]);
                    String step = scanner.nextLine();
                    out.println("MOVE " + step);
                } else if (response.startsWith(VICTORY.toString())) {
                    System.out.println("You win");
                    break;
                } else if (response.startsWith(DEFEAT.toString())) {
                    System.out.println("You lose");
                    break;
                } else if (response.startsWith(TIE.toString())) {
                    System.out.println("You tied");
                    break;
                } else if (response.startsWith(MESSAGE.toString())) {
                    System.out.println(response.substring(8));
                } else if (response.startsWith(SHOW.toString())) {
                    System.out.println(response.substring(5).replaceAll("&", "\n"));
                } else if (response.startsWith(STEP.toString())) {
                    System.out.println(response.substring(5));
                    System.in.read(new byte[System.in.available()]);
                    String step = scanner.nextLine();
                    out.println("MOVE " + step);
                } else if (response.startsWith(ERROR.toString())) {
                    System.out.println(response.substring(6));
                    System.in.read(new byte[System.in.available()]);
                    String step = scanner.nextLine();
                    out.println("MOVE " + step);
                }
            }
            out.println("QUIT");
        } catch (Exception e) {
            log.error("Play error: ", e);
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                System.out.println("Server not start!");
                log.error("Socket close error: ", e);
            }
        }
    }

    enum ServerCommand {
        VALID_MOVE,
        OPPONENT_MOVED,
        VICTORY,
        DEFEAT,
        TIE,
        MESSAGE,
        SHOW,
        ERROR,
        STEP
    }
}
