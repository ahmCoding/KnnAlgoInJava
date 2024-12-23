package org.example.datastructure;

import org.example.helperClasses.EuclideanDistance;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TasksCoarse implements Runnable {
    private final List<? extends DataPoint> dataset;
    private final DataPoint pTest;
    private Distance[] distances;
    private final int idxStart, idxEnd;
    private final CountDownLatch countDownLatch;

    public TasksCoarse(Distance[] distances, List<? extends DataPoint> dataset, DataPoint pTest, int idxStart, int idxEnd, CountDownLatch countDownLatch) {
        this.pTest = pTest;
        this.dataset = dataset;
        this.distances = distances;
        this.countDownLatch = countDownLatch;
        this.idxStart = idxStart;
        this.idxEnd = idxEnd;
    }

    @Override
    public void run() {
        for (int idx = this.idxStart; idx < this.idxEnd; idx++) {
            DataPoint currentTrain = this.dataset.get(idx);
            distances[idx] = new Distance();
            distances[idx].setIndex(idx);
            distances[idx].setDistance(EuclideanDistance.calcDistance(currentTrain, this.pTest));
        }
        this.countDownLatch.countDown();
    }
}
