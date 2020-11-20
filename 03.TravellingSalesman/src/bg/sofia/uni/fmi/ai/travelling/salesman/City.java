package bg.sofia.uni.fmi.ai.travelling.salesman;

import java.util.Objects;

public class City {
    private int x;
    private int y;

    public City() {
        this.x = (int) (Math.random() * 500);
        this.y = (int) (Math.random() * 500);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double calculateDistance(City toCity) {
        int xDistance = Math.abs(x - toCity.getX());
        int yDistance = Math.abs(y - toCity.getY());
        return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return x == city.x &&
                y == city.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return " (" + getX() + ", " + getY() + ") ";
    }
}
