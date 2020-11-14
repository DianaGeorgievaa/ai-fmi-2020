package bg.sofia.uni.fmi.ai.queens;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the queens number: ");
        int queensNumber = scanner.nextInt();
        long start = System.currentTimeMillis();
        Board board = new Board(queensNumber);
        board.findSolution(queensNumber);
        System.out.println("Time in millis: " + (System.currentTimeMillis() - start));
        if (queensNumber < 20) {
            board.print(queensNumber);
        }
    }
}
