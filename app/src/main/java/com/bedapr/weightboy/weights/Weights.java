/*
Class to determine best way to rack a bar to achieve a given weight.
TODO: Create another class to manage rack calculation for multiple weight targets

Author: Benjamin Price
Version: 0.1
 */
package com.bedapr.weightboy.weights;

import java.util.Arrays;

public class Weights {
    // index is same for both arrays, so for one index you get both the size of that weight and
    // how many of that size you need for the specific weight
    private double[] weightSizes;
    private int[] weightCounts;
    private final int size;
    private double remainder;
    private final double amount;

    /*
     * Called with array of weight sizes in a set. Index 0 should always be the weight of the bar.
     */
    public Weights(double amount, double[] sizes) {
        this.amount = amount;
        size = sizes.length;
        weightSizes = doubleCopy(sizes);
        Arrays.sort(weightSizes, 1, size);
        weightCounts = new int[size];
        Arrays.fill(weightCounts, 0, size, 0);

        remainder = amount - Math.floor(amount);
        remainder = 2 * (remainder += calcWeights(Math.floor(amount)));
    }

    private void incrementWeight(int index) {
        weightCounts[index]++;
    }

    @Override
    public String toString() {
        String result = "==WEIGHT AMOUNTS PER SIDE==";
        for (int i = size - 1; i > 0; i--) {
            if (weightCounts[i] ==0) continue;
            result += "\n" + weightSizes[i] + ": Rack " + weightCounts[i];
        }
        if (remainder != 0) result += "\nTotal Weight: " + (amount - remainder) +
                                        "\n" + remainder + " lbs could not be added";
        return result;
    }

    /* Subtracts goal weight by bar weight, then divides by two to ensure balanced bar
     * @param amount weight to reach with fewest weights
     * @returns remainder that couldn't be divided
     */
    private double calcWeights(double amount) {
        amount = (amount - weightSizes[0])/2; // if this returns negative, bar is too heavy :(
        for (int i = size - 1; i > 0; i--) {
            double weight = weightSizes[i];
            while (amount >= weight) {
                incrementWeight(i);
                amount -= weight;
            }
        }
        return amount;
    }

    private double[] doubleCopy(double[] array) {
        double[] newArray = new double[size];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        return newArray;
    }
}
