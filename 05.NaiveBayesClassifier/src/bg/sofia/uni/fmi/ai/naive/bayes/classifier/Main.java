package bg.sofia.uni.fmi.ai.naive.bayes.classifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int FOLDS = 10;

    public static void main(String[] args) throws FileNotFoundException {
        double sumAccuracy = 0;
        List<List<Entity>> sets = createSets();

        for (int i = 0; i < FOLDS; ++i) {
            List<Entity> testSet = sets.get(i);
            sets.remove(i);
            Classifier classifier = new Classifier(sets, testSet);
            double accuracy = classifier.calculateAccuracy();
            System.out.println(accuracy);
            sets.add(i, testSet);
            sumAccuracy += accuracy;
        }

        System.out.println("Average accuracy: " + (sumAccuracy / FOLDS));
    }

    private static List<List<Entity>> createSets() throws FileNotFoundException {
        List<Entity> data = parseData();
        Collections.shuffle(data);
        List<List<Entity>> sets = new ArrayList<>();
        int setSize = data.size() / FOLDS;
        int startIndex = 0;
        int endIndex = setSize;
        for (int i = 0; i < FOLDS; i++) {
            if (endIndex > data.size()) {
                endIndex = data.size();
            }
            List<Entity> result = data.subList(startIndex, endIndex);
            sets.add(result);
            startIndex = endIndex;
            endIndex += setSize;
        }
        return sets;
    }

    private static List<Entity> parseData() throws FileNotFoundException {
        List<Entity> data = new ArrayList<>();
        try (Scanner scanner = new Scanner(
                new File("src/house-votes-84.data"))) {
            while (scanner.hasNext()) {
                data.add(Entity.parseEntity(scanner.nextLine()));
            }
        }
        return data;
    }

}