package org.example.datastructure;

import org.example.helperClasses.EuclideanDistance;

import java.util.concurrent.CountDownLatch;

public class TasksFine implements Runnable {
    private DataPoint pTest,pTrain;
    private Distance[] distances;
    private final int idx;
    private final CountDownLatch countDownLatch;

    public TasksFine(DataPoint pTest, DataPoint pTrain, Distance[] distances,int idx, CountDownLatch countDownLatch) {
        this.pTest = pTest;
        this.pTrain = pTrain;
        this.distances = distances;
        this.countDownLatch = countDownLatch;
        this.idx = idx;
    }

    @Override
    public void run() {
        distances[idx]=new Distance();
        distances[idx].setIndex(idx);
        distances[idx].setDistance(EuclideanDistance.calcDistance(pTrain,pTest));
        countDownLatch.countDown();
    }
}
