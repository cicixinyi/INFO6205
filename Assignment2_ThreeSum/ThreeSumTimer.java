package edu.neu.coe.info6205.threesum;

import edu.neu.coe.info6205.util.Stopwatch;

public class ThreeSumTimer {
    public static void main(String[] args) {
        // Run the timing test for at least five values of N, doubling N each time
        int[] sizes = {50, 100, 200, 400, 800, 1600, 3200};

        for (int N : sizes) {
            int[] sortedArray = generateSortedArray(N);

            // Timing for ThreeSumCubic
            ThreeSumCubic threeSumCubic = new ThreeSumCubic(sortedArray);
            long cubicTime = timeAlgorithm(threeSumCubic, N);
            System.out.println("N = " + N + ", ThreeSumCubic: " + cubicTime + " ms");

            // Timing for ThreeSumQuadrithmic
            ThreeSumQuadrithmic threeSumQuadrithmic = new ThreeSumQuadrithmic(sortedArray);
            long quadrithmicTime = timeAlgorithm(threeSumQuadrithmic, N);
            System.out.println("N = " + N + ", ThreeSumQuadrithmic: " + quadrithmicTime + " ms");

            // Timing for ThreeSumQuadratic
            ThreeSumQuadratic threeSumQuadratic = new ThreeSumQuadratic(sortedArray);
            long quadraticTime = timeAlgorithm(threeSumQuadratic, N);
            System.out.println("N = " + N + ", ThreeSumQuadratic: " + quadraticTime + " ms");
           
            // Timing for ThreeSumQuadraticWithCalipers
            ThreeSumQuadraticWithCalipers threeSumCalipers = new ThreeSumQuadraticWithCalipers(sortedArray);
            long calipersTime = timeAlgorithm(threeSumCalipers, N);
            System.out.println("N = " + N + ", ThreeSumQuadraticWithCalipers: " + calipersTime + " ms");

            System.out.println();
        }
    }

    /**
     * Measure the time taken to execute the 3-SUM algorithm.
     */
    private static long timeAlgorithm(ThreeSum algorithm, int N) {
        try (Stopwatch stopwatch = new Stopwatch()) {
            algorithm.getTriples();
            return stopwatch.lap(); // Return the time taken in milliseconds
        }
    }

    /**
     * Generate a sorted array of size N, containing integers from -N/2 to N/2.
     */
    private static int[] generateSortedArray(int N) {
        int[] array = new int[N];
        for (int i = 0; i < N; i++) {
            array[i] = i - N / 2;
        }
        return array;
    }
}
