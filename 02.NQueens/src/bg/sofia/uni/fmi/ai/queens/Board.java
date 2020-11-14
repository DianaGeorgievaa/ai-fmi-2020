package bg.sofia.uni.fmi.ai.queens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private int[] queens;
    private int[] queensPerRow;
    private int[] queensPerMainDiagonal;
    private int[] queensPerSecondaryDiagonals;

    public Board(int numberQueens) {
        this.queens = new int[numberQueens];
        this.queensPerRow = new int[numberQueens];
        this.queensPerMainDiagonal = new int[2 * numberQueens - 1];
        this.queensPerSecondaryDiagonals = new int[2 * numberQueens - 1];

        initialize(numberQueens);
        setQueensOnRowsWithMinConflicts(numberQueens);
    }

    public void findSolution(int numberOfQueens) {
        int iterator = 0;
        boolean isSolution = false;
        while (iterator++ <= 3 * numberOfQueens) {
            int column = getColumnWithMaxConflicts(numberOfQueens);
            if (column == -1) {
                isSolution = true;
                break;
            }

            int previousRow = queens[column];
            int row = getRowWithMinConflicts(column, numberOfQueens);
            queens[column] = row;
            // Recalculate conflicts after the move
            queensPerRow[previousRow] = queensPerRow[previousRow] - 1;
            queensPerRow[row] = queensPerRow[row] + 1;

            queensPerMainDiagonal[column - previousRow + numberOfQueens - 1] =
                    queensPerMainDiagonal[column - previousRow + numberOfQueens - 1] - 1;
            queensPerMainDiagonal[column - row + numberOfQueens - 1] = queensPerMainDiagonal[column - row + numberOfQueens - 1] + 1;

            queensPerSecondaryDiagonals[column + previousRow] = queensPerSecondaryDiagonals[column + previousRow] - 1;
            queensPerSecondaryDiagonals[column + row] = queensPerSecondaryDiagonals[column + row] + 1;
        }

        if (!isSolution) {
            initialize(numberOfQueens);
            setQueensOnRowsWithMinConflicts(numberOfQueens);
            findSolution(numberOfQueens);
        }
    }

    public void print(int numberOfQueens) {
        for (int i = 0; i < numberOfQueens; i++) {
            for (int j = 0; j < numberOfQueens; j++) {
                System.out.print(queens[j] == i ? "*\t" : "_\t");
            }
            System.out.println();
        }
    }

    private int getColumnWithMaxConflicts(int numberOfQueens) {
        int maxConflicts = 0;
        List<Integer> columnsWithMaxConflicts = new ArrayList<>();

        for (int column = 0; column < numberOfQueens; column++) {
            int row = queens[column];
            int mainDiagonalIndex = column - row + numberOfQueens - 1;
            int secondaryDiagonalIndex = column + row;
            int conflicts =
                    queensPerRow[row] + queensPerMainDiagonal[mainDiagonalIndex] + queensPerSecondaryDiagonals[secondaryDiagonalIndex] - 3;
            if (conflicts == maxConflicts) {
                columnsWithMaxConflicts.add(column);
            } else if (conflicts > maxConflicts) {
                maxConflicts = conflicts;
                columnsWithMaxConflicts.clear();
                columnsWithMaxConflicts.add(column);
            }
        }

        if (maxConflicts == 0) {
            return -1;
        }

        return columnsWithMaxConflicts.get(new Random()
                .nextInt(columnsWithMaxConflicts.size()));
    }

    private int getRowWithMinConflicts(int column, int numberOfQueens) {
        int minConflicts = numberOfQueens;
        List<Integer> queensWithMinConflicts = new ArrayList<>();

        for (int i = 0; i < numberOfQueens; i++) {
            int row = queens[column];
            if (i != row) {
                int mainDiagonalIndex = column - i + numberOfQueens - 1;
                int secondaryDiagonalIndex = column + i;
                int conflicts =
                        queensPerRow[i] + queensPerMainDiagonal[mainDiagonalIndex] + queensPerSecondaryDiagonals[secondaryDiagonalIndex];
                if (conflicts == minConflicts) {
                    queensWithMinConflicts.add(i);
                } else if (conflicts < minConflicts) {
                    minConflicts = conflicts;
                    queensWithMinConflicts.clear();
                    queensWithMinConflicts.add(i);
                }
            }
        }

        if (queensWithMinConflicts.isEmpty()) {
            return 0;
        }
        return queensWithMinConflicts.get(new Random()
                .nextInt(queensWithMinConflicts.size()));
    }

    private void initialize(int numberQueens) {
        for (int i = 0; i < 2 * numberQueens - 1; i++) {
            this.queensPerSecondaryDiagonals[i] = 0;
            this.queensPerMainDiagonal[i] = 0;
            if (i < numberQueens) {
                queensPerRow[i] = 0;
            }
        }
    }

    private void setQueensOnRowsWithMinConflicts(int numberQueens) {
        for (int column = 0; column < numberQueens; column++) {
            int row = getRowWithMinConflicts(column, numberQueens);
            queens[column] = row;
            queensPerRow[row] += 1;
            queensPerMainDiagonal[(column - row) + numberQueens - 1] += 1;
            queensPerSecondaryDiagonals[column + row] += 1;
        }

    }
}