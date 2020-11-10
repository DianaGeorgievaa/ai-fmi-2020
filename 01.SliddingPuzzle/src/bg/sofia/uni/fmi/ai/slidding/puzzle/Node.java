package bg.sofia.uni.fmi.ai.slidding.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
    public static final int PATH_COST = 1;
    private Node parent;
    private int[][] state;
    private int zeroRow;
    private int zeroColumn;

    public Node() {
        this.parent = null;
    }

    public Node(Node parent, int[][] state, int zeroRow, int zeroColumn) {
        this.parent = parent;
        this.state = state;
        this.zeroRow = zeroRow;
        this.zeroColumn = zeroColumn;
    }

    //  All nodes after one swapping (move)
    public List<Node> getSuccessors(Node parent) {
        List<Node> successors = new ArrayList<>();
        int zeroRowIndex = parent.getZeroRow();
        int zeroColumnIndex = parent.getZeroColumn();
        if (zeroRowIndex - 1 >= 0) {
            int[][] newState = getStateAfterSwappingUp(parent.getState(), zeroRowIndex, zeroColumnIndex);
            Node upStateNode = new Node(parent, newState, zeroRowIndex - 1, zeroColumnIndex);
            successors.add(upStateNode);
        }
        if (zeroRowIndex + 1 <= state.length - 1) {
            int[][] newState = getStateAfterSwappingDown(parent.getState(), zeroRowIndex, zeroColumnIndex);
            Node downStateNode = new Node(parent, newState, zeroRowIndex + 1, zeroColumnIndex);
            successors.add(downStateNode);
        }
        if (zeroColumnIndex + 1 <= state.length - 1) {
            int[][] newState = getStateAfterSwappingRight(parent.getState(), zeroRowIndex, zeroColumnIndex);
            Node rightStateNode = new Node(parent, newState, zeroRowIndex, zeroColumnIndex + 1);
            successors.add(rightStateNode);
        }
        if (zeroColumnIndex - 1 >= 0) {
            int[][] newState = getStateAfterSwappingLeft(parent.getState(), zeroRowIndex, zeroColumnIndex);
            Node leftStateNode = new Node(parent, newState, zeroRowIndex, zeroColumnIndex - 1);
            successors.add(leftStateNode);
        }
        return successors;
    }

    public boolean areEqual(int[][] secondState) {
        if (this.state == null) {
            return (secondState == null);
        }

        if (secondState == null) {
            return false;
        }

        if (this.state.length != secondState.length) {
            return false;
        }

        for (int i = 0; i < this.state.length; i++) {
            if (!Arrays.equals(this.state[i], secondState[i])) {
                return false;
            }
        }
        return true;
    }

    public Node getParent() {
        return this.parent;
    }

    public int[][] getState() {
        return this.state;
    }

    public void setState(int[][] state) {
        this.state = state;
    }

    public int getZeroRow() {
        return zeroRow;
    }

    public int getZeroColumn() {
        return zeroColumn;
    }

    public int distanceFromParent() {
        return PATH_COST;
    }

    private int[][] getStateAfterSwappingUp(int[][] currentState, int zeroRow, int zeroColumn) {
        int[][] stateAfterSwapping = copyState(currentState);
        stateAfterSwapping[zeroRow][zeroColumn] = stateAfterSwapping[zeroRow - 1][zeroColumn];
        stateAfterSwapping[zeroRow - 1][zeroColumn] = 0;
        return stateAfterSwapping;
    }

    private int[][] getStateAfterSwappingDown(int[][] currentState, int zeroRow, int zeroColumn) {
        int[][] stateAfterSwapping = copyState(currentState);
        stateAfterSwapping[zeroRow][zeroColumn] = stateAfterSwapping[zeroRow + 1][zeroColumn];
        stateAfterSwapping[zeroRow + 1][zeroColumn] = 0;
        return stateAfterSwapping;
    }

    private int[][] getStateAfterSwappingRight(int[][] currentState, int zeroRow, int zeroColumn) {
        int[][] stateAfterSwapping = copyState(currentState);
        stateAfterSwapping[zeroRow][zeroColumn] = stateAfterSwapping[zeroRow][zeroColumn + 1];
        stateAfterSwapping[zeroRow][zeroColumn + 1] = 0;
        return stateAfterSwapping;
    }

    private int[][] getStateAfterSwappingLeft(int[][] currentState, int zeroRow, int zeroColumn) {
        int[][] stateAfterSwapping = copyState(currentState);
        stateAfterSwapping[zeroRow][zeroColumn] = stateAfterSwapping[zeroRow][zeroColumn - 1];
        stateAfterSwapping[zeroRow][zeroColumn - 1] = 0;
        return stateAfterSwapping;
    }

    private static int[][] copyState(int[][] currentState) {
        int[][] copiedState = new int[currentState.length][currentState.length];
        for (int i = 0; i < currentState.length; i++) {
            System.arraycopy(currentState[i], 0, copiedState[i], 0, currentState.length);
        }
        return copiedState;
    }
}