package com.fmi.ai.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
    public static final int PATH_COST = 1;
    private Node parent;
    private int[][] state;

    public Node() {
        this.parent = null;
    }

    public Node(Node parent, int[][] state) {
        this.parent = parent;
        this.state = state;
    }

    //  All nodes after one swapping (move)
    public List<Node> getSuccessors(Node parent) {
        List<Node> successors = new ArrayList<>();
        for (int i = 0; i < parent.state.length; i++) {
            for (int j = 0; j < parent.state.length; j++) {
                if (parent.getState()[i][j] == 0) {
                    if (i - 1 >= 0) {
                        Node upStateNode = new Node(parent, getStateAfterSwappingUp(parent.getState()));
                        successors.add(upStateNode);
                    }
                    if (i + 1 <= state.length - 1) {
                        Node downStateNode = new Node(parent, getStateAfterSwappingDown(parent.getState()));
                        successors.add(downStateNode);
                    }
                    if (j + 1 <= state.length - 1) {
                        Node rightStateNode = new Node(parent, getStateAfterSwappingRight(parent.getState()));
                        successors.add(rightStateNode);
                    }
                    if (j - 1 >= 0) {
                        Node leftStateNode = new Node(parent, getStateAfterSwappingLeft(parent.getState()));
                        successors.add(leftStateNode);
                    }
                }
            }
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

    // Check if the board is solvable
    public boolean isSolvable() {
        if ((state.length * state.length) % 2 == 0) {
            return (numberInversions() + getIndexOfZero(this.state, true)) % 2 != 0;
        }
        return numberInversions() % 2 == 0;
    }

    public Node getParent() {
        return this.parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int[][] getState() {
        return this.state;
    }

    public void setState(int[][] state) {
        this.state = state;
    }

    public int distanceFromParent() {
        return PATH_COST;
    }

    private int numberInversions() {
        int inversions = 0;
        int[] tempArray = new int[state.length * state.length - 1];
        int tempIndex = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                if (state[i][j] != 0) {
                    tempArray[tempIndex] = state[i][j];
                    tempIndex++;
                    if (tempIndex > state.length * state.length - 1) {
                        return 0;
                    }
                }
            }
        }
        for (int i = 0; i < state.length * state.length - 2; i++) {
            for (int j = i + 1; j < state.length * state.length - 1; j++) {
                if (tempArray[i] > tempArray[j]) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    private int getIndexOfZero(int[][] state, boolean isRow) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                if (state[i][j] == 0 && isRow) {
                    return i;
                } else if (state[i][j] == 0 && !isRow) {
                    return j;
                }
            }
        }
        return -1;
    }

    private int[][] getStateAfterSwappingUp(int[][] currentState) {
        int[][] stateAfterSwapping = copyState(currentState);
        int zeroRow = getIndexOfZero(stateAfterSwapping, true);
        int zeroColumn = getIndexOfZero(stateAfterSwapping, false);
        stateAfterSwapping[zeroRow][zeroColumn] = stateAfterSwapping[zeroRow - 1][zeroColumn];
        stateAfterSwapping[zeroRow - 1][zeroColumn] = 0;
        return stateAfterSwapping;
    }

    private int[][] getStateAfterSwappingDown(int[][] currentState) {
        int[][] stateAfterSwapping = copyState(currentState);
        int zeroRow = getIndexOfZero(stateAfterSwapping, true);
        int zeroColumn = getIndexOfZero(stateAfterSwapping, false);
        stateAfterSwapping[zeroRow][zeroColumn] = stateAfterSwapping[zeroRow + 1][zeroColumn];
        stateAfterSwapping[zeroRow + 1][zeroColumn] = 0;
        return stateAfterSwapping;
    }

    private int[][] getStateAfterSwappingRight(int[][] currentState) {
        int[][] stateAfterSwapping = copyState(currentState);
        int zeroRow = getIndexOfZero(stateAfterSwapping, true);
        int zeroColumn = getIndexOfZero(stateAfterSwapping, false);
        stateAfterSwapping[zeroRow][zeroColumn] = stateAfterSwapping[zeroRow][zeroColumn + 1];
        stateAfterSwapping[zeroRow][zeroColumn + 1] = 0;
        return stateAfterSwapping;
    }

    private int[][] getStateAfterSwappingLeft(int[][] currentState) {
        int[][] stateAfterSwapping = copyState(currentState);
        int zeroRow = getIndexOfZero(stateAfterSwapping, true);
        int zeroColumn = getIndexOfZero(stateAfterSwapping, false);
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