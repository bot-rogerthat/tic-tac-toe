package com.epam.ticTacToe.command;

import com.epam.ticTacToe.game.Game;
import com.epam.ticTacToe.server.ServerThread;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public enum Command {
    VALID_MOVE {
        @Override
        public boolean apply(String val, Scanner sc, PrintWriter out, Game game, ServerThread currentThread) {
            System.out.println("Valid move, please wait");
            return true;
        }
    },
    OPPONENT_MOVED {
        @Override
        public boolean apply(String val, Scanner sc, PrintWriter out, Game game, ServerThread currentThread) throws IOException {
            System.out.println("Opponent moved, your turn");
            System.in.read(new byte[System.in.available()]);
            String step = sc.nextLine();
            out.println(MOVE + " " + step);
            return true;
        }
    },
    VICTORY {
        @Override
        public boolean apply(String val, Scanner sc, PrintWriter out, Game game, ServerThread currentThread) {
            System.out.println("You win");
            return false;
        }
    },
    DEFEAT {
        @Override
        public boolean apply(String val, Scanner sc, PrintWriter out, Game game, ServerThread currentThread) {
            System.out.println("You lose");
            return false;
        }
    },
    TIE {
        @Override
        public boolean apply(String val, Scanner sc, PrintWriter out, Game game, ServerThread currentThread) {
            System.out.println("You tied");
            return false;
        }
    },
    MESSAGE {
        @Override
        public boolean apply(String val, Scanner sc, PrintWriter out, Game game, ServerThread currentThread) {
            System.out.println(val.substring(8));
            return true;
        }
    },
    SHOW {
        @Override
        public boolean apply(String val, Scanner sc, PrintWriter out, Game game, ServerThread currentThread) {
            System.out.println(val.substring(5).replaceAll("&", "\n"));
            return true;
        }
    },
    ERROR {
        @Override
        public boolean apply(String val, Scanner sc, PrintWriter out, Game game, ServerThread currentThread) throws IOException {
            System.out.println(val.substring(6));
            System.in.read(new byte[System.in.available()]);
            String step = sc.nextLine();
            out.println(MOVE + " " + step);
            return true;
        }
    },
    STEP {
        @Override
        public boolean apply(String val, Scanner sc, PrintWriter out, Game game, ServerThread currentThread) throws IOException {
            System.out.println(val.substring(5));
            System.in.read(new byte[System.in.available()]);
            String step = sc.nextLine();
            out.println(MOVE + " " + step);
            return true;
        }
    },
    MOVE {
        @Override
        public boolean apply(String val, Scanner sc, PrintWriter out, Game game, ServerThread currentThread) {
            int location = Integer.parseInt(val.substring(5));
            if (game.doStepIsValid(location, currentThread)) {
                out.println(VALID_MOVE);
                game.printBoard();
                out.println(SHOW + " " + game.convertBoardToString());
                out.println(game.hasWinner() ? VICTORY : game.isFullBoard() ? TIE : "");
            } else {
                out.println(ERROR + " ?");
            }
            return true;
        }
    },
    QUIT {
        @Override
        public boolean apply(String val, Scanner sc, PrintWriter out, Game game, ServerThread currentThread) {
            return false;
        }
    },
    EMPTY {
        @Override
        public boolean apply(String val, Scanner sc, PrintWriter out, Game game, ServerThread currentThread) {
            return true;
        }
    };

    public abstract boolean apply(String val, Scanner sc, PrintWriter out, Game game, ServerThread currentThread) throws IOException;

    public static Command getCmd(String response) {
        for (Command cmd : Command.values()) {
            if (response.startsWith(cmd.toString())) {
                return cmd;
            }
        }
        return EMPTY;
    }
}
