package bg.sofia.uni.fmi.ai.tictactoe;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        play();
    }

    private static void play() {
        GameBoard gameBoard = new GameBoard();
        Game game = new Game();
        System.out.println("Enter 1 if you want to play first and 2 otherwise: ");
        Scanner scanner = new Scanner(System.in);
        if (getPlayerChoice() == 2) {
            gameBoard = game.play(gameBoard);
            gameBoard.print();
        }

        while (gameBoard.getWinner() == Integer.MIN_VALUE) {
            System.out.println("Enter your move:");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            Cell cell = new Cell(x, y);

            try {
                gameBoard.move(cell, PlayerMarker.HUMAN);
            } catch (IllegalArgumentException e) {
                System.out.println("You cannot make that move. Try again:");
                continue;
            }

            gameBoard.print();
            int winner = gameBoard.getWinner();
            if (winner != Integer.MIN_VALUE) {
                break;
            }

            System.out.println("Bot is making a move:");
            gameBoard = game.play(gameBoard);
            gameBoard.print();
        }

        gameBoard.printMessageForWinning();
    }

    private static int getPlayerChoice() {
        Scanner scanner = new Scanner(System.in);
        int playerChoice;
        while (true) {
            playerChoice = scanner.nextInt();
            if (playerChoice == 1 || playerChoice == 2) {
                break;
            }
            System.out.println("You should choose 1 or 2:");
        }
        return playerChoice;
    }
}
