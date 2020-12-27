package bg.sofia.uni.fmi.ai;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final int FOLDS = 10;

    public static void main(String[] args) throws FileNotFoundException {
        List<Entity> data = new ArrayList<>();
        try (Scanner scanner = new Scanner(
                new File("src/breast-cancer.data"))) {
            while (scanner.hasNext()) {
                Entity entity = Entity.parseEntity(scanner.nextLine());
                if (entity != null) {
                    data.add(entity);
                }
            }
        }

        List<List<Entity>> folds = new ArrayList<>();
        for (int i = 0; i < FOLDS; i++) {
            folds.add(new ArrayList<>());
        }

        Algorithm algorithm = new Algorithm();
        for (Entity entity : data) {
            Random random = new Random();
            int randomIndex = random.nextInt() % 10;
            if (randomIndex < 0) {
                randomIndex *= -1;
            }

            folds.get(randomIndex).add(entity);
        }

        algorithm.crossValidate(folds);
    }
}
