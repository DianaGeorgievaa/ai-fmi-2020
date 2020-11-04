package com.fmi.ai.puzzle;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int size = (int) Math.sqrt(n + 1);
        int[][] initialState = new int[size][size];
        int finalPositionOfZero = scanner.nextInt();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print("Type board[" + i + "][" + j + "] = ");
                int number = scanner.nextInt();
                initialState[i][j] = number;
            }
        }

        Node initialNode = new Node(null, initialState);
        Node finalNode = new Node();
        IterativeDeepeningAStarSearch searcher = new IterativeDeepeningAStarSearch(initialNode, finalNode);
        int[][] goalState = searcher.calculateGoalState(size, finalPositionOfZero);
        finalNode.setState(goalState);

        if (!initialNode.isSolvable()) {
            System.out.println("The board is unsolvable");
            return;
        }

        Node goalNode = searcher.findGoalNode();
        List<Node> path = searcher.getPathToGoalState(goalNode);
        List<String> actions = searcher.getMoves(path);
        System.out.println(actions.size());
        for (String action : actions) {
            System.out.println(action);
        }
    }
}
