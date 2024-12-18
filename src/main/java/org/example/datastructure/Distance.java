package org.example.datastructure;

/**
 * Klasse zur Beschreibung der Distanz (Eukedische) von zwei
 * Datenpunkten des Datensatzes
 */
public class Distance implements Comparable<Distance> {
    // Beschreibt der index von Datenpunkt in Datensatz
    private int index;
    // Beschreibt der Distanz
    private double distance;

    public int getIndex() {
        return index;
    }

    public double getDistance() {
        return distance;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Distance other) {
        if (this.getDistance() < other.getDistance()) return -1;
        if (this.getDistance() > other.getDistance()) return 1;
        return 0;
    }
}
