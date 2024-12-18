package org.example.helperClasses;

import org.example.datastructure.BankMarketingDataPoint;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse zum Laden des Datensatzes
 */
public class DatasetLoader {

    /**
     * Lädt den Datensatz
     *
     * @param pathToLoad Pfad zur Dataei (jede Zeile stellt einen Datenpunkt dar und die
     *                   Eigenschaften sind durch ';' getrennt)
     * @return Datensatz in Form einer Liste
     */
    public List<BankMarketingDataPoint> loadDataset(String pathToLoad) {
        List<BankMarketingDataPoint> dataset = new ArrayList<>();
        // load data
        try (
                InputStream inFile = Files.newInputStream(Paths.get(pathToLoad));
                BufferedReader reader = new BufferedReader(new InputStreamReader(inFile));
        ) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] attributes = line.split(";");
                BankMarketingDataPoint oneDatapoint = new BankMarketingDataPoint();
                oneDatapoint.setData(attributes);
                dataset.add(oneDatapoint);
            }
        } catch (IOException io) {
            io.getStackTrace();
        } catch (Exception e) {
            e.getStackTrace();
        }
        return dataset;

    }
}
