package bg.sofia.uni.fmi.ai.travelling.salesman;

import java.util.List;

public class GeneticAlgorithm {
    private static final double MUTATION_RATE = 0.015;
    private static final int TOURNAMENT_SIZE = 5;

    public static Population evolvePopulation(Population population, List<City> cities) {
        int elitism = (int) (population.size() * 0.2);
        Population newPopulation = new Population(population.size(), cities, false);
        Individual[] bestIndividuals = population.getBestIndividuals(elitism);
        for (int i = 0; i < elitism; i++) {
            newPopulation.saveIndividual(i, bestIndividuals[i]);
        }

        for (int i = elitism; i < newPopulation.size(); i++) {
            Individual firstParent = selectParent(population, cities);
            Individual secondParent = selectParent(population, cities);
            Individual child = crossover(firstParent, secondParent, cities.size());
            newPopulation.saveIndividual(i, child);
        }

        for (int i = elitism; i < newPopulation.size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }

    private static Individual selectParent(Population population, List<City> cities) {
        Population tournament = new Population(TOURNAMENT_SIZE, cities, false);
        for (int i = 0; i < TOURNAMENT_SIZE; i++) {
            int randomId = (int) (Math.random() * population.size());
            tournament.saveIndividual(i, population.getIndividual(randomId));
        }
        return tournament.getTheBestIndividual();
    }

    private static Individual crossover(Individual firstParent, Individual secondParent, int numberOfCities) {
        Individual child = new Individual(numberOfCities);
        int endIndex = (int) (Math.random() * firstParent.size());

        for (int i = 0; i < endIndex; i++) {
            child.setCity(i, firstParent.getCity(i));
        }

        for (int i = 0; i < secondParent.size(); i++) {
            if (!child.containsCity(secondParent.getCity(i))) {
                child.setCity(endIndex, secondParent.getCity(i));
                endIndex++;
            }
        }
        return child;
    }

    private static void mutate(Individual individual) {
        for (int i = 0; i < individual.size(); i++) {
            if (Math.random() < MUTATION_RATE) {
                int newIndex = (int) (individual.size() * Math.random());
                City firstCity = individual.getCity(i);
                City secondCity = individual.getCity(newIndex);
                individual.setCity(newIndex, firstCity);
                individual.setCity(i, secondCity);
            }
        }
    }
}
