package org.example.sequentialClassifier;

import org.example.datastructure.DataPoint;
import org.example.datastructure.Distance;
import org.example.helperClasses.DatasetLoader;
import org.example.helperClasses.EuclideanDistance;

import java.util.*;
import java.util.stream.Collectors;

public class KnnClassifier {
    private final List<? extends DataPoint> dataset;
    private int k;

    /**
     * @param data   gedadener Datensatz
     * @param ktoSet Wert für K in KNN-Algorithm
     */
    public KnnClassifier(List<? extends DataPoint> data, int ktoSet) {
        this.dataset = data;
        this.k = ktoSet;
    }

    public String classify(DataPoint pointToClassify) {
        Distance[] allDistances = new Distance[dataset.size()];
        int idx = 0;
        // Abstand vom gegebenen Punkt zu allen Punkten des Datensatzes Rechnen
        for (DataPoint currentPointOfDataset : dataset) {
            allDistances[idx] = new Distance();
            allDistances[idx].setIndex(idx);
            allDistances[idx++].setDistance(EuclideanDistance.calcDistance(pointToClassify, currentPointOfDataset));
        }
        // Aufsteigend sortieren
        Arrays.sort(allDistances);
        // Die ersten K-Samples dann durch den gespeicherten Index
        // in Distances-Array aus dem Datensatz Lesen, die Häufigkeit deren Labels
        // abzählen. Der 'pointToClassify' bekommt das am meist abgezählte Label.
        Map<String, Integer> results = new HashMap<>();
        for (int i = 0; i < this.k; i++) {
            DataPoint sampleFromDataset = dataset.get(allDistances[i].getIndex());
            String label = sampleFromDataset.getLabel();
            // wenn zum Key(label) kein value , than 1, sonst 'value zu Key + 1' als neuer Wert für Value!
            results.merge(label, 1, Integer::sum);
        }
        // das meist vorkommende Label berechnen und zurückgeben
        return Collections.max(results.entrySet(), Map.Entry.comparingByKey()).getKey();

    }
}
