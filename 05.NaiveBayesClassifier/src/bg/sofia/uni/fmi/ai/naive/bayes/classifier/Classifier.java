package bg.sofia.uni.fmi.ai.naive.bayes.classifier;

import bg.sofia.uni.fmi.ai.naive.bayes.classifier.emum.Probability;
import bg.sofia.uni.fmi.ai.naive.bayes.classifier.emum.ClassType;

import java.util.*;

public class Classifier {
    private List<List<Entity>> data;
    private List<Entity> testData;
    private List<EnumMap<Probability, Double>> learntData;
    private double democratProbability;
    private double republicanProbability;

    public Classifier(List<List<Entity>> data, List<Entity> testData) {
        this.data = data;
        this.testData = testData;
        democratProbability = 0;
        republicanProbability = 0;
        learntData = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            learntData.add(new EnumMap<>(Probability.class));
        }
        train();
    }

    public double calculateAccuracy() {
        double accuracy = 0;
        for (Entity entity : testData) {
            double democrat = Math.log(democratProbability);
            double republican = Math.log(republicanProbability);
            for (int i = 0; i < learntData.size(); i++) {
                if (entity.getAttributes()[i] == null) {
                    continue;
                }

                if (entity.getAttributes()[i]) {
                    democrat += Math.log(learntData.get(i).get(Probability.DEMOCRAT_YES));
                    republican += Math.log(learntData.get(i).get(Probability.REPUBLICAN_YES));
                } else {
                    democrat += Math.log(learntData.get(i).get(Probability.DEMOCRAT_NO));
                    republican += Math.log(learntData.get(i).get(Probability.REPUBLICAN_NO));
                }
            }

            if (isPredictedClassCorrect(democrat, republican, entity.getClassType())) {
                accuracy++;
            }
        }
        return accuracy / testData.size();
    }

    private void train() {
        double democratCounter = 0;
        double republicanCounter = 0;
        Map<Probability, Double> votes = new EnumMap<>(Probability.class);
        for (int i = 0; i < Entity.ATTRIBUTES_SIZE; i++) {
            initializeVotes(votes);

            for (List<Entity> entities : data) {
                for (Entity entity : entities) {
                    if (entity.getClassType().equals(ClassType.DEMOCRAT)) {
                        democratCounter++;
                    } else {
                        republicanCounter++;
                    }

                    if (entity.getAttributes()[i] == null) {
                        continue;
                    }
                    updateVotes(votes, entity, i);
                }

                double yesDemocratVotes = votes.get(Probability.DEMOCRAT_YES);
                double noDemocratVotes = votes.get(Probability.DEMOCRAT_NO);
                double yesRepublicanVotes = votes.get(Probability.REPUBLICAN_YES);
                double noRepublicanVotes = votes.get(Probability.REPUBLICAN_NO);

                learntData.get(i).put(Probability.DEMOCRAT_YES, yesDemocratVotes / (yesDemocratVotes + noDemocratVotes + 1));
                learntData.get(i).put(Probability.DEMOCRAT_NO, noDemocratVotes / (yesDemocratVotes + noDemocratVotes + 1));
                learntData.get(i).put(Probability.REPUBLICAN_YES, yesRepublicanVotes / (yesRepublicanVotes + noRepublicanVotes + 1));
                learntData.get(i).put(Probability.REPUBLICAN_NO, noRepublicanVotes / (yesRepublicanVotes + noRepublicanVotes + 1));
            }
        }

        democratProbability = democratCounter / (democratCounter + republicanCounter);
        republicanProbability = republicanCounter / (democratCounter + republicanCounter);
    }

    private boolean isPredictedClassCorrect(double democratProbability, double republicanProbability, ClassType classType) {
        return (democratProbability > republicanProbability
                && classType.equals(ClassType.DEMOCRAT))
                || (democratProbability < republicanProbability
                && classType.equals(ClassType.REPUBLICAN));
    }

    private void updateVotes(Map<Probability, Double> votes, Entity entity, int index) {
        if (entity.getClassType().equals(ClassType.DEMOCRAT)) {
            if (entity.getAttributes()[index]) {
                double currentVotes = votes.get(Probability.DEMOCRAT_YES) + 1;
                votes.put(Probability.DEMOCRAT_YES, currentVotes);
            } else {
                double currentVotes = votes.get(Probability.DEMOCRAT_NO) + 1;
                votes.put(Probability.DEMOCRAT_NO, currentVotes);
            }
        } else {
            if (entity.getAttributes()[index]) {
                double currentVotes = votes.get(Probability.REPUBLICAN_YES) + 1;
                votes.put(Probability.REPUBLICAN_YES, currentVotes);
            } else {
                double currentVotes = votes.get(Probability.REPUBLICAN_NO) + 1;
                votes.put(Probability.REPUBLICAN_NO, currentVotes);
            }
        }
    }

    private void initializeVotes(Map<Probability, Double> votes) {
        votes.put(Probability.DEMOCRAT_YES, 1.0);
        votes.put(Probability.DEMOCRAT_NO, 1.0);
        votes.put(Probability.REPUBLICAN_YES, 1.0);
        votes.put(Probability.REPUBLICAN_NO, 1.0);
    }
}
