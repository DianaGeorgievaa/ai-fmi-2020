package bg.sofia.uni.fmi.ai.slidding.puzzle;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int size = (int) Math.sqrt(n + 1);
        int[][] initialState = new int[size][size];
        int finalPositionOfZero = scanner.nextInt();
        int zeroRow = 0;
        int zeroColumn = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print("[" + i + "][" + j + "] = ");
                int number = scanner.nextInt();
                initialState[i][j] = number;
                if (number == 0) {
                    zeroRow = i;
                    zeroColumn = j;
                }
            }

        }

        Node initialNode = new Node(null, initialState, zeroRow, zeroColumn);
        Node finalNode = new Node();
        IDAStarSearch searcher = new IDAStarSearch(initialNode, finalNode);
        int[][] goalState = searcher.calculateGoalState(size, finalPositionOfZero);
        finalNode.setState(goalState);
        Node goalNode = searcher.findGoalNode();
        List<Node> path = searcher.getPathToGoalState(goalNode);
        List<String> actions = searcher.getMoves(path);
        System.out.println(actions.size());
        for (String action : actions) {
            System.out.println(action);
        }
    }
}