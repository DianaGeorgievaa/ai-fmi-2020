package bg.sofia.uni.fmi.ai.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private static final int BOARD_SIZE = 3;

    private PlayerMarker[][] state = new PlayerMarker[BOARD_SIZE][BOARD_SIZE];
    private int numberOfEmptyCells;

    public GameBoard() {
        this.numberOfEmptyCells = BOARD_SIZE * BOARD_SIZE;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.state[i][j] = PlayerMarker.EMPTY;
            }
        }
    }

    public GameBoard(GameBoard board) {
        this.numberOfEmptyCells = board.getNumberOfEmptyCells();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.state[i][j] = board.getState()[i][j];
            }
        }
    }

    public PlayerMarker[][] getState() {
        return state;
    }

    public int getNumberOfEmptyCells() {
        return numberOfEmptyCells;
    }

    public void move(Cell cell, PlayerMarker playerMarker) {
        if (state[cell.getX()][cell.getY()] != PlayerMarker.EMPTY) {
            throw new IllegalArgumentException("The cell is not empty");
        }

        state[cell.getX()][cell.getY()] = playerMarker;
        numberOfEmptyCells--;
    }

    public List<Cell> getPossibleMoves() {
        List<Cell> possibleMoves = new ArrayList<>();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (state[i][j] == PlayerMarker.EMPTY) {
                    Cell cell = new Cell(i, j);
                    possibleMoves.add(cell);
                }
            }
        }

        return possibleMoves;
    }

    public void printMessageForWinning() {
        int winner = getWinner();
        if (winner == 0) {
            System.out.println("Draw!");
        } else if (winner < 0) {
            System.out.println("Bot wins!");
        } else {
            System.out.println("You win!");
        }
    }

    public int getWinner() {
        PlayerMarker playerMarker = getHorizontalWinner();
        if (playerMarker != PlayerMarker.EMPTY) {
            return getWinningValue(playerMarker);
        }

        playerMarker = getVerticalWinner();
        if (playerMarker != PlayerMarker.EMPTY) {
            return getWinningValue(playerMarker);
        }

        playerMarker = getDiagonalWinner();
        if (playerMarker != PlayerMarker.EMPTY) {
            return getWinningValue(playerMarker);
        }

        if (getPossibleMoves().isEmpty()) {
            return 0;
        }

        return Integer.MIN_VALUE;
    }

    public void print() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(state[i][j].getValue() + " ");
            }
            System.out.println();
        }
    }

    private PlayerMarker getHorizontalWinner() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (state[0][i] != PlayerMarker.EMPTY) {
                if (state[0][i] == state[1][i] && state[0][i] == state[2][i]) {
                    return state[0][i];
                }
            }
        }

        return PlayerMarker.EMPTY;
    }

    private PlayerMarker getDiagonalWinner() {
        if (state[0][0] != PlayerMarker.EMPTY && state[0][0] == state[1][1] && state[0][0] == state[2][2]) {
            return state[0][0];
        }

        if (state[0][2] != PlayerMarker.EMPTY && state[0][2] == state[1][1] && state[0][2] == state[2][0]) {
            return state[0][2];
        }

        return PlayerMarker.EMPTY;
    }

    private PlayerMarker getVerticalWinner() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (state[i][0] != PlayerMarker.EMPTY) {
                if (state[i][0] == state[i][1] && state[i][0] == state[i][2]) {
                    return state[i][0];
                }
            }
        }

        return PlayerMarker.EMPTY;
    }

    private int getWinningValue(PlayerMarker playerMarker) {
        String value = playerMarker.getValue();
        if (value.equals(PlayerMarker.BOT.getValue())) {
            return -1 - numberOfEmptyCells;
        } else {
            return 1 + numberOfEmptyCells;
        }
    }
}

