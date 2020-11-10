package bg.sofia.uni.fmi.ai.slidding.puzzle;

import java.util.*;

public class IDAStarSearch {
    private Node initialNode;
    private Node goalNode;

    public IDAStarSearch(Node initialNode, Node goalState
    ) {
        this.initialNode = initialNode;
        this.goalNode = goalState;
    }

    // Returns the last node on the optimal path and null if the path does not exist
    public Node findGoalNode() {
        int currentThreshold = calculateManhattanDistance(initialNode.getState(), goalNode.getState());
        List<Node> path = new ArrayList<>();
        path.add(0, initialNode);

        int smallestNewThreshold;
        do {
            smallestNewThreshold = findSmallestThreshold(path, 0, currentThreshold);
            if (smallestNewThreshold == 0) {
                return path.get(path.size() - 1);
            }
            currentThreshold = smallestNewThreshold;
        } while (currentThreshold != Integer.MAX_VALUE);

        return null;
    }

    private int findSmallestThreshold(List<Node> path, int graphCost, int currentThreshold) {
        Node currentNode = path.get(path.size() - 1);
        int h = calculateManhattanDistance(currentNode.getState(), goalNode.getState());
        int f = graphCost + h;

        if (f > currentThreshold) {
            return f;
        }

        if (currentNode.areEqual(goalNode.getState())) {
            return 0;
        }

        int minThreshold = Integer.MAX_VALUE;
        List<Node> children = currentNode.getSuccessors(currentNode);
        for (Node child : children) {
            if (!path.contains(child)) {
                path.add(child);
                int result = findSmallestThreshold(path, graphCost + child.distanceFromParent(), currentThreshold);
                if (result == 0) {
                    return 0;
                }
                if (result < minThreshold) {
                    minThreshold = result;
                }
                path.remove(path.size() - 1);
            }
        }

        return minThreshold;
    }

    public List<Node> getPathToGoalState(Node goalNode) {
        List<Node> path = new ArrayList<>();
        path.add(goalNode);

        while (goalNode.getParent() != null) {
            path.add(0, goalNode.getParent());
            goalNode = goalNode.getParent();
        }

        return path;
    }

    public List<String> getMoves(List<Node> pathBoards) {
        List<String> moves = new ArrayList<>();
        for (int i = 0; i < pathBoards.size() - 1; i++) {
            Node currentNode = pathBoards.get(i);
            Node nextNode = pathBoards.get(i + 1);
            int zeroRowIndex = currentNode.getZeroRow();
            int zeroColumnIndex = currentNode.getZeroColumn();
            int zeroRowIndexAfterSwapping = nextNode.getZeroRow();
            int zeroColumnIndexAfterSwapping = nextNode.getZeroColumn();
            if (zeroRowIndex + 1 == zeroRowIndexAfterSwapping) {
                moves.add("up");
            } else if (zeroRowIndex - 1 == zeroRowIndexAfterSwapping) {
                moves.add("down");
            } else if (zeroColumnIndex + 1 == zeroColumnIndexAfterSwapping) {
                moves.add("left");
            } else if (zeroColumnIndex - 1 == zeroColumnIndexAfterSwapping) {
                moves.add("right");
            }
        }
        return moves;
    }

    public int[][] calculateGoalState(int initialStateSize, int positionOfZero) {
        int[][] goalState = new int[initialStateSize][initialStateSize];
        int index = 1;
        int maxIndex = initialStateSize * initialStateSize - 1;
        if (positionOfZero == -1 || positionOfZero == maxIndex) {
            for (int i = 0; i < initialStateSize; i++) {
                for (int j = 0; j < initialStateSize; j++) {
                    goalState[i][j] = index;
                    index++;
                }
            }
            goalState[initialStateSize - 1][initialStateSize - 1] = 0;
            return goalState;
        }
        int tempIndex = 0;
        for (int i = 0; i < initialStateSize; i++) {
            for (int j = 0; j < initialStateSize; j++) {
                if (tempIndex == positionOfZero) {
                    goalState[i][j] = 0;
                } else {
                    goalState[i][j] = index;
                    index++;
                }
                tempIndex++;
            }
        }
        return goalState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IDAStarSearch idaStar = (IDAStarSearch) o;
        return Objects.equals(initialNode, idaStar.initialNode) &&
                Objects.equals(goalNode, idaStar.goalNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(initialNode, goalNode);
    }

    private int calculateManhattanDistance(int[][] currentState, int[][] goalState) {
        int distance = 0;
        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState.length; j++) {
                if (currentState[i][j] != goalState[i][j] && currentState[i][j] != 0) {
                    int currentDistance = Math.abs(i - getIndexForSpecificTile(goalState, currentState[i][j], true)) +
                            Math.abs(j - getIndexForSpecificTile(goalState, currentState[i][j], false));
                    distance += currentDistance;
                }
            }
        }
        return distance;
    }

    private int getIndexForSpecificTile(int[][] state, int tile, boolean isRowIndex) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                if (state[i][j] == tile && isRowIndex) {
                    return i;
                } else if (state[i][j] == tile && !isRowIndex) {
                    return j;
                }
            }
        }
        return -1;
    }
}
