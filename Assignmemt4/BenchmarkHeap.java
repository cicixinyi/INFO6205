package edu.neu.coe.info6205.util;

import edu.neu.coe.info6205.pq.PriorityQueue;
import edu.neu.coe.info6205.pq.QuadPriorityQueue;
import edu.neu.coe.info6205.util.Benchmark_Timer;

import java.util.Random;

public class BenchmarkHeap {

    private static final int M = 4095;
    private static final int insertions = 16000;
    private static final int removals = 4000;
    private static final Random rand = new Random();

    public static void main(String[] args) {
        // Benchmark Basic Binary Heap
        runBenchmark("Basic Binary Heap", () -> {
            PriorityQueue<Integer> pq = new PriorityQueue<>(M, true, Integer::compare);
            performInsertionsAndRemovals(pq);
        });

        // Benchmark Binary Heap with Floyd's Trick
        runBenchmark("Binary Heap with Floyd's Trick", () -> {
            PriorityQueue<Integer> pq = new PriorityQueue<>(M, true, Integer::compare, true);
            performInsertionsAndRemovals(pq);
        });

        // Benchmark 4-ary Heap
        runBenchmark("4-ary Heap", () -> {
            QuadPriorityQueue<Integer> pq = new QuadPriorityQueue<>(M, true, Integer::compare);
            performInsertionsAndRemovalsQuad(pq);
        });

        // Benchmark 4-ary Heap with Floyd's Trick
        runBenchmark("4-ary Heap with Floyd's Trick", () -> {
            QuadPriorityQueue<Integer> pq = new QuadPriorityQueue<>(M, true, Integer::compare, true);
            performInsertionsAndRemovalsQuad(pq);
        });

        // Benchmark Fibonacci Heap (bonus)
        // runBenchmark("Fibonacci Heap", () -> {
        //     FibonacciHeap<Integer> pq = new FibonacciHeap<>();
        //     performInsertionsAndRemovals(pq);
        // });
    }

    private static void runBenchmark(String description, Runnable runnable) {
        Benchmark_Timer<Void> benchmark = new Benchmark_Timer<>(
            description, 
            null, 
            v -> { runnable.run(); },
            null
        );
        double averageTime = benchmark.runFromSupplier(() -> null, 10); 
        System.out.println(description + " Average Time: " + averageTime + " ms");
    }

    private static void performInsertionsAndRemovals(PriorityQueue<Integer> pq) {
        try {
            for (int i = 0; i < insertions; i++) {
                pq.give(rand.nextInt()); 
            }
            Integer highestPriority = null;
            for (int i = 0; i < removals; i++) {
                Integer removed = pq.take();
                if (highestPriority == null || removed > highestPriority) {
                    highestPriority = removed; 
                }
            }
            System.out.println("Highest Priority Removed: " + highestPriority);
        } catch (Exception e) {
            System.err.println("Error in performInsertionsAndRemovals for PriorityQueue: " + e.getMessage());
        }
    }

    private static void performInsertionsAndRemovalsQuad(QuadPriorityQueue<Integer> pq) {
        try {
            for (int i = 0; i < insertions; i++) {
                pq.give(rand.nextInt()); 
            }
            Integer highestPriority = null;
            for (int i = 0; i < removals; i++) {
                Integer removed = pq.take(); 
                if (highestPriority == null || removed > highestPriority) {
                    highestPriority = removed;
                }
            }
            System.out.println("Highest Priority Removed: " + highestPriority);
        } catch (Exception e) {
            System.err.println("Error in performInsertionsAndRemovalsQuad for QuadPriorityQueue: " + e.getMessage());
        }
    }

    // private static void performInsertionsAndRemovals(FibonacciHeap<Integer> pq) {
    //     // Similar implementation for Fibonacci Heap if you have it
    //     // Insert and remove logic specific to Fibonacci Heap here
    // }
}
