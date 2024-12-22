package org.example.mainClasses;

import org.example.helperClasses.DatasetLoader;
import org.example.datastructure.BankMarketingDataPoint;
import org.example.classifier.SerialKnnClassifier;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MainSerial {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        System.out.println("Value of k: " + k);
        DatasetLoader loader = new DatasetLoader();
        List<BankMarketingDataPoint> datasetTrain = loader.loadDataset("./data/bank.data");
        List<BankMarketingDataPoint> datasetTest = loader.loadDataset("./data/bank.test");
        System.out.println("Size of dataset train: " + datasetTrain.size() + " , test: " + datasetTest.size());
        double startTime=0, endTime=0;
        SerialKnnClassifier serialClassifier = new SerialKnnClassifier(datasetTrain,k);
        int success = 0;
        String prediction;
        for(BankMarketingDataPoint dataPoint: datasetTest) {
            startTime = System.nanoTime();
            prediction=serialClassifier.classify(dataPoint);
            if(prediction.equals(dataPoint.getLabel())) success++;
        }
        endTime = System.nanoTime();
        double elapsedTime = (endTime-startTime)/1000000;
        System.out.println("Elapsed time: "+elapsedTime +" Seconds" );
        System.out.println("Success rate: "+ 100 * success/ datasetTest.size() + " % ");
    }
}
