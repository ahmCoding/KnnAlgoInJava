package org.example.helperClasses;

import org.example.datastructure.BankMarketingDataPoint;
import org.example.datastructure.DataPoint;

/**
 * Klasse zur Berechnung der Euklidscher Abstand
 */
public class EuclideanDistance {
    /**
     * Berechnet den Abstand zwischen zwei Datenpunkte
     *
     * @param point1 erste Datenpunkt
     * @param point2 zweite Datempunkt
     * @return der euklidische Abstand
     */
    public static double calcDistance(DataPoint point1, DataPoint point2) {
        if (point1.getAttributes().length != point2.getAttributes().length) {
            throw new IllegalArgumentException(" data points given to calculate Distance don't have the equal dimension");
        }
        double result = 0;
        double[] p1 = point1.getAttributes();
        double[] p2 = point2.getAttributes();
        for (int i = 0; i < p1.length; i++) {
            result += Math.pow(p1[i] - p2[i], 2);
        }
        return Math.sqrt(result);
    }
}
