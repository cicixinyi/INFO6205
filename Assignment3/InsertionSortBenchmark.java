package edu.neu.coe.info6205.sort.elementary;

import java.util.Arrays;
import java.util.Random;

public class InsertionSortBenchmark {
    public static void main(String[] args) {
        int[] nValues = {100, 200, 400, 800, 1600}; // Test sizes
        InsertionSortBasic<Integer> sorter = InsertionSortBasic.create();
        System.out.printf("%n%-20s %-20s %-20s %-20s %-20s%n", "Array Size", "Random(ns)", "Ordered(ns)", "Partially Ordered(ns)", "Reverse Ordered(ns)");

        for (int n : nValues) {
            // Measure for random array
            long randomTime = benchmark(sorter, generateRandomArray(n));

            // Measure for ordered array
            long orderedTime = benchmark(sorter, generateOrderedArray(n));

            // Measure for partially ordered array
            long partiallyOrderedTime = benchmark(sorter, generatePartiallyOrderedArray(n));

            // Measure for reverse ordered array
            long reverseOrderedTime = benchmark(sorter, generateReverseOrderedArray(n));

            // Print results
            System.out.printf("%-20d %-20d %-20d %-20d %-20d%n", n, randomTime, orderedTime, partiallyOrderedTime, reverseOrderedTime);       
        }
        System.out.println();
    }

    private static long benchmark(InsertionSortBasic<Integer> sorter, Integer[] array) {
        // Warm up the JVM
        for (int i = 0; i < 10; i++) {
            sorter.sort(array.clone()); 
        }

        // Start timing
        long startTime = System.nanoTime();
        sorter.sort(array.clone());
        long endTime = System.nanoTime();

        return endTime - startTime;
    }

    private static Integer[] generateRandomArray(int size) {
        Random rand = new Random();
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(size); 
        }
        return array;
    }

    private static Integer[] generateOrderedArray(int size) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    private static Integer[] generatePartiallyOrderedArray(int size) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        Random rand = new Random();
        for (int i = 0; i < size / 10; i++) {
            int index1 = rand.nextInt(size);
            int index2 = rand.nextInt(size);
            swap(array, index1, index2);
        }
        return array;
    }

    private static Integer[] generateReverseOrderedArray(int size) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i - 1;
        }
        return array;
    }

    private static void swap(Integer[] array, int i, int j) {
        Integer temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
