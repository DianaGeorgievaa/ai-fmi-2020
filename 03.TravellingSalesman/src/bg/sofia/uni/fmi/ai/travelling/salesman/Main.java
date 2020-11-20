package bg.sofia.uni.fmi.ai.travelling.salesman;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int POPULATION_SIZE = 100;
    private static final int GENERATIONS = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfCities = scanner.nextInt();
        List<City> allCities = new ArrayList<>();
        for (int i = 0; i < numberOfCities; i++) {
            allCities.add(new City());
        }

        Population population = new Population(POPULATION_SIZE, allCities, true);
        int startDistance = population.getTheBestIndividual().getDistance();
        System.out.println("Start distance: " + startDistance);
        population = GeneticAlgorithm.evolvePopulation(population, allCities);

        for (int i = 0; i < GENERATIONS; i++) {
            population = GeneticAlgorithm.evolvePopulation(population, allCities);

            if (i == 10 || i == 40 || i == 70 || i == 90) {
                System.out.println(i + "th population: " + population.getTheBestIndividual());
                System.out.println("Distance: " + population.getTheBestIndividual().getDistance());
            }
        }

        System.out.println("Final population:" + population.getTheBestIndividual());
        System.out.println("Final distance: " + population.getTheBestIndividual().getDistance());
    }
}