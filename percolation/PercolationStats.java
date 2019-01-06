/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_FACTOR = 1.96;

    private final double[] probabilities;
    private final int t;

    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
        t = trials;
        probabilities = new double[trials];

        for (int i = 0; i < trials; i++) {
            probabilities[i] = getVacancyProbability(n);
        }
    }

    private double getVacancyProbability(int n) {
        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            int row = StdRandom.uniform(1, n+1);
            int col = StdRandom.uniform(1, n+1);
            p.open(row, col);
        }
        return (double) p.numberOfOpenSites() / (double) (n * n);
    }


    public double mean() {
        return StdStats.mean(probabilities);
    }

    public double stddev() {
        return StdStats.stddev(probabilities);
    }

    public double confidenceLo() {
        return mean() - CONFIDENCE_FACTOR * stddev() / Math.sqrt(t);
    }


    public double confidenceHi() {
        return mean() + CONFIDENCE_FACTOR * stddev() / Math.sqrt(t);
    }

    public static void main(String[] args)        // test client (described below)
    {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, t);
        System.out.printf("%-30s = %f\n", "mean", ps.mean());
        System.out.printf("%-30s = %f\n", "stddev", ps.stddev());
        System.out.printf("%-30s = [%f, %f]\n", "95% confidence interval", ps.confidenceLo(), ps.confidenceHi());
    }
}
