package org.example;

import org.example.helperClasses.DatasetLoader;
import org.example.datastructure.BankMarketingDataPoint;
import org.example.helperClasses.EuclideanDistance;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DatasetLoader loader = new DatasetLoader();
        List<BankMarketingDataPoint> dataset = loader.loadDataset("./data/bank.data");
        double tst = EuclideanDistance.calcDistance(dataset.get(0), dataset.get(2));
        System.out.println(tst);
    }
}
