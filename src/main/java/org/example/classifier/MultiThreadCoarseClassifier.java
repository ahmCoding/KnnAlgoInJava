package org.example.classifier;

import org.example.datastructure.DataPoint;
import org.example.datastructure.Distance;
import org.example.datastructure.TasksCoarse;
import org.example.datastructure.TasksFine;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MultiThreadCoarseClassifier {
    private final List<? extends DataPoint> dataset;
    private final int k;
    private final ThreadPoolExecutor executor;
    private int numberOfThreads;

    public MultiThreadCoarseClassifier(List<? extends DataPoint> dataset, int k) {
        this.dataset = dataset;
        this.k = k;
        this.numberOfThreads = Runtime.getRuntime().availableProcessors();
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfThreads);
    }

    public String classify(DataPoint pointToClassify, boolean parallelSort) {
        Distance[] allDistances = new Distance[this.dataset.size()];
        // für jedes Sample aus dem training-dataset ein Task
        CountDownLatch countDownLatch = new CountDownLatch(numberOfThreads);
        int size = this.dataset.size() / numberOfThreads;
        int startIdx = 0, endIdx = size;
        // Abstand vom gegebenen Punkt zu allen Punkten des Datensatzes Rechnen
        for (int i = 0; i < numberOfThreads; i++) {
            TasksCoarse newTask = new TasksCoarse(allDistances, this.dataset, pointToClassify, startIdx, endIdx, countDownLatch);
            startIdx = endIdx;
            if (i < numberOfThreads - 2) endIdx += size;
            else endIdx = this.dataset.size();
            executor.execute(newTask);
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
