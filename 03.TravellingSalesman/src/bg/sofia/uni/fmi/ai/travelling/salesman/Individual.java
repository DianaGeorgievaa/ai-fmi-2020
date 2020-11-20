package bg.sofia.uni.fmi.ai.travelling.salesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Individual {
    private double fitness = 0;
    private int tourDistance = 0;
    private List<City> cities = new ArrayList<>();

    public Individual(int numberOfCities) {
        for (int i = 0; i < numberOfCities; i++) {
            cities.add(null);
        }
    }

    public double getFitness() {
        if (fitness == 0) {
            fitness = 1 / (double) getDistance();
        }
        return fitness;
    }

    public int getDistance() {
        if (tourDistance == 0) {
            int sum = 0;
            for (int i = 0; i < cities.size(); i++) {
                City fromCity = cities.get(i);
                if (i + 1 < cities.size()) {
                    City toCity = cities.get(i + 1);
                    sum += fromCity.calculateDistance(toCity);

                }
            }
            tourDistance = sum;
        }
        return tourDistance;
    }

    public void generateIndividual(List<City> allCities) {
        for (int i = 0; i < allCities.size(); i++) {
            setCity(i, allCities.get(i));
        }
        Collections.shuffle(cities);
    }

    public City getCity(int index) {
        return cities.get(index);
    }

    public void setCity(int position, City city) {
        cities.set(position, city);
        fitness = 0;
        tourDistance = 0;
    }

    public int size() {
        return cities.size();
    }

    public boolean containsCity(City city) {
        return cities.contains(city);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < cities.size(); i++) {
            stringBuilder.append(getCity(i));
        }
        return new String(stringBuilder);
    }
}
