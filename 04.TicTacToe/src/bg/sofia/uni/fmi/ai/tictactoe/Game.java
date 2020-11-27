package bg.sofia.uni.fmi.ai.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public GameBoard play(GameBoard currentBoard) {
        GameBoard bestMove = null;
        int bestResult = Integer.MAX_VALUE;

        for (GameBoard board : getPossibleNextBoards(currentBoard, PlayerMarker.BOT)) {
            int currentResult = maxValue(board, Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (currentResult < bestResult) {
                bestMove = board;
                bestResult = currentResult;
            }
        }

        return bestMove;
    }

    private List<GameBoard> getPossibleNextBoards(GameBoard gameBoard, PlayerMarker playerMarker) {
        List<GameBoard> possibleNextBoards = new ArrayList<>();
        for (Cell cell : gameBoard.getPossibleMoves()) {
            GameBoard board = new GameBoard(gameBoard);
            board.move(cell, playerMarker);

            possibleNextBoards.add(board);
        }

        return possibleNextBoards;
    }

    private int maxValue(GameBoard currentBoard, int alpha, int beta) {
        int boardResult = currentBoard.getWinner();
        if (boardResult != Integer.MIN_VALUE) {
            return boardResult;
        }

        int v = Integer.MIN_VALUE;
        for (GameBoard move : getPossibleNextBoards(currentBoard, PlayerMarker.HUMAN)) {
            v = Integer.max(v, minValue(move, alpha, beta));
            if (v >= beta) {
                return v;
            }
            alpha = Integer.max(alpha, v);
        }

        return v;
    }

    private int minValue(GameBoard currentBoard, int alpha, int beta) {
        int boardResult = currentBoard.getWinner();
        if (boardResult != Integer.MIN_VALUE) {
            return boardResult;
        }

        int v = Integer.MAX_VALUE;
        for (GameBoard move : getPossibleNextBoards(currentBoard, PlayerMarker.BOT)) {
            v = Integer.min(v, maxValue(move, alpha, beta));
            if (v <= alpha) {
                return v;
            }
            beta = Integer.min(beta, v);
        }

        return v;
    }
}
