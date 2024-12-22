package org.example.classifier;

import org.example.datastructure.DataPoint;
import org.example.datastructure.Distance;
import org.example.datastructure.TasksFine;
import org.example.helperClasses.EuclideanDistance;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MultiThreadFineClassifier {
    private final List<? extends DataPoint> dataset;
    private final int k;
    private final ThreadPoolExecutor executor;

    public MultiThreadFineClassifier(List<? extends DataPoint> dataset, int k) {
        this.dataset = dataset;
        this.k = k;
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfThreads);
    }

    public String classify(DataPoint pointToClassify, boolean parallelSort) {
        Distance[] allDistances = new Distance[dataset.size()];
        int idx = 0;
        // für jedes Sample aus dem training-dataset ein Task
        CountDownLatch countDownLatch = new CountDownLatch(dataset.size());
        // Abstand vom gegebenen Punkt zu allen Punkten des Datensatzes Rechnen
        for (DataPoint currentPointOfDataset : dataset) {
            TasksFine newTask = new TasksFine(currentPointOfDataset, pointToClassify, allDistances, idx, countDownLatch);
            executor.execute(newTask);
            idx++;
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (parallelSort) Arrays.parallelSort(allDistances);
        else Arrays.sort(allDistances);
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
    public void destroy() {
        executor.shutdown();
    }
}
