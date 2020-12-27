package bg.sofia.uni.fmi.ai;

import java.util.*;

public class Algorithm {
    private static final int K = 10;
    private static final int FOLDS = 10;
    private static final int CLASS_INDEX = 0;
    private static final String NO_RECURRENCE = "no-recurrence-events";
    private static final String RECURRENCE = "recurrence-events";

    private List<Attribute> attributes = Arrays.asList(Attribute.values());

    public void crossValidate(List<List<Entity>> folds) {
        double meanAccuracy = 0;
        for (int foldIndex = 0; foldIndex < FOLDS; foldIndex++) {
            List<Entity> trainingSet = getTrainingData(folds, foldIndex);
            Node root = constructDecisionTree(trainingSet, new HashSet<>());

            int counter = 0;
            for (int i = 0; i < folds.get(foldIndex).size(); i++) {
                String prediction = predict(root, folds.get(foldIndex).get(i));
                if (prediction.equals(folds.get(foldIndex).get(i).getValues().get(CLASS_INDEX))) {
                    counter++;
                }
            }
            double accuracy = (double) counter / folds.get(foldIndex).size();
            System.out.println(accuracy);
            meanAccuracy += accuracy;
        }

        meanAccuracy /= FOLDS;
        System.out.println("Mean accuracy: " + meanAccuracy);
    }

    private Node constructDecisionTree(List<Entity> data, Set<Integer> branchAttributes) {
        Node node = new Node();
        if (calculateEntropyForAttribute(data, 0) == 0) {
            node.setLeaf(true);
            node.setValue(data.get(0).getValues().get(CLASS_INDEX));
            return node;
        }

        if (data.size() < K || branchAttributes.size() == 9) {
            node.setLeaf(true);
            Map<String, Integer> values = getValues(data, 0);
            if (values.get(NO_RECURRENCE) >= values.get(RECURRENCE)) {
                node.setValue(NO_RECURRENCE);
            } else {
                node.setValue(RECURRENCE);
            }
            return node;
        }

        int maxIndex = getIndexWithMaxGain(data, branchAttributes);
        node.setAttribute(maxIndex);
        node.setValue(attributes.get(maxIndex).name());
        node.setLeaf(false);
        node.setData(data);

        for (String value : getValuesForSpecificAttribute(data, maxIndex)) {
            List<Entity> subData = new ArrayList<>();
            for (Entity entity : data) {
                if (entity.getValues().get(maxIndex).equals(value)) {
                    subData.add(entity);
                }
            }

            branchAttributes.add(maxIndex);
            node.getChildren().put(value, constructDecisionTree(subData, branchAttributes));
        }

        return node;
    }

    private List<Entity> getTrainingData(List<List<Entity>> folds, int currentFoldIndex) {
        List<Entity> data = new ArrayList<>();
        for (int i = 0; i < FOLDS; i++) {
            if (i == currentFoldIndex) {
                continue;
            }

            for (int j = 0; j < folds.get(i).size(); j++) {
                data.add(folds.get(i).get(j));
            }
        }
        return data;
    }

    private String predict(Node node, Entity entity) {
        Node parent = new Node();
        while (node != null && !node.isLeaf()) {
            String attributeValue = entity.getValues().get(node.getAttributeIndex());
            parent = node;
            node = node.getChildren().get(attributeValue);
        }

        Map<String, Integer> frequency = new HashMap<>();
        for (int i = 0; i < parent.getData().size(); i++) {
            String value = parent.getData().get(i).getValues().get(0);
            if (frequency.get(value) != null) {
                int v = frequency.get(value) + 1;
                frequency.put(value, v);
            } else {
                frequency.put(value, 1);
            }
        }

        return (frequency.get(NO_RECURRENCE) >= frequency.get(RECURRENCE) ?
                NO_RECURRENCE : RECURRENCE);
    }

    private double calculateEntropyForAttribute(List<Entity> data, int index) {
        double entropy = 0;
        for (Map.Entry<String, Integer> entry : getValues(data, index).entrySet()) {
            double p = (double) entry.getValue() / data.size();
            entropy += (-1) * p * Math.log(p);
        }

        return entropy;
    }

    private double calculateEntropyForClass(List<Entity> data, int index) {
        double entropy = 0;
        for (Map.Entry<String, Integer> entry : getValues(data, index).entrySet()) {
            List<Entity> splitedData = new ArrayList<>();
            for (Entity entity : data) {
                if (entity.getValues().get(index).equals(entry.getKey())) {
                    splitedData.add(entity);
                }
            }
            double p = (double) entry.getValue() / data.size();
            entropy += p * calculateEntropyForAttribute(splitedData, 0);
        }

        return entropy;
    }

    private double calculateGain(List<Entity> data, int index) {
        return calculateEntropyForAttribute(data, 0) - calculateEntropyForClass(data, index);
    }

    private Map<String, Integer> getValues(List<Entity> data, int index) {
        Map<String, Integer> values = new HashMap<>();
        for (Entity entity : data) {
            String value = entity.getValues().get(index);
            if (values.get(value) != null) {
                int c = values.get(value) + 1;
                values.put(value, c);
            } else {
                values.put(value, 1);
            }
        }

        return values;
    }

    private int getIndexWithMaxGain(List<Entity> data, Set<Integer> branchAttributes) {
        double maxGain = -1;
        int maxIndex = -1;
        for (int i = 1; i < 10; i++) {
            if (branchAttributes.contains(i)) {
                continue;
            }

            double currentGain = calculateGain(data, i);
            if (currentGain > maxGain) {
                maxGain = currentGain;
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private Set<String> getValuesForSpecificAttribute(List<Entity> data, int attributeIndex) {
        Set<String> values = new HashSet<>();
        for (Entity entity : data) {
            String value = entity.getValues().get(attributeIndex);
            values.add(value);
        }
        return values;
    }
}
