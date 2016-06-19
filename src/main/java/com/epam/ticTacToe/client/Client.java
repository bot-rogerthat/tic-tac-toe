package com.epam.ticTacToe.client;

import com.epam.ticTacToe.command.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static com.epam.ticTacToe.command.Command.QUIT;

public class Client {
    private static final Logger log = LoggerFactory.getLogger(Client.class);
    private final String host;
    private final int port;

    public static void main(String[] args) {
        new Client("localhost", 4444).run();
    }

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        String response;
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            response = in.readLine();
            System.out.println(response);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                response = in.readLine();
                Command command = Command.getCmd(response);
                if (!command.apply(response, scanner, out, null, null)) {
                    break;
                }
            }
            out.println(QUIT);
        } catch (Exception e) {
            log.error("Play error: ", e);
        }
    }
}
