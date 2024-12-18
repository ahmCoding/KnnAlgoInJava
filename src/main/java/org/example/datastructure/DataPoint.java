package org.example.datastructure;

/**
 * Definiert die Klasse für einen Datenpunkt in Dataset <a href="http://archive.ics.uci.edu/ml/datasets/Bank+Marketing">...</a>
 */
public abstract class DataPoint {
    /**
     * Gibt alle OneHotEncoded features von dem Datenpunkt des Datensatzes zurück
     *
     * @return die Encoded-Features als double[]
     */
    public abstract double[] getAttributes();

    /**
     * der Tag/Label von dem Datenpunkt, hier 0/1, gekauft/nicht gekauft
     *
     * @return der Tag in Stringformatt
     */
    public abstract String getLabel();
}
