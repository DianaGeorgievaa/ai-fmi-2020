package bg.sofia.uni.fmi.ai.travelling.salesman;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Population {
    private Individual[] individuals;

    public Population(int populationSize, List<City> cities, boolean isFirstPopulation) {
        individuals = new Individual[populationSize];
        if (isFirstPopulation) {
            for (int i = 0; i < individuals.length; i++) {
                Individual newIndividual = new Individual(cities.size());
                newIndividual.generateIndividual(cities);
                individuals[i] = newIndividual;
            }
        }
    }

    public Individual getTheBestIndividual() {
        Individual individual = individuals[0];
        for (int i = 1; i < individuals.length; i++) {
            if (individual.getFitness() <= individuals[i].getFitness()) {
                individual = individuals[i];
            }
        }
        return individual;
    }

    public Individual[] getBestIndividuals(int numberOfIndividuals) {
        return Arrays.stream(individuals)
                .sorted(Comparator.comparing(Individual::getFitness)).limit(numberOfIndividuals)
                .collect(Collectors.toList())
                .toArray(new Individual[numberOfIndividuals]);
    }

    public int size() {
        return individuals.length;
    }

    public void saveIndividual(int index, Individual individual) {
        individuals[index] = individual;
    }

    public Individual getIndividual(int index) {
        return individuals[index];
    }
}
