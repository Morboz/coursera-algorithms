import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
	private double[] p_results;
	private int trials;

	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}
		this.trials = trials;
		p_results = new double[trials];
		int row;
		int col;
		for (int i = 0; i < trials; i++) {
			Percolation p = new Percolation(n);
			int open_num = 0;
			while (!p.percolates()) {
				row = StdRandom.uniform(n) + 1;
				col = StdRandom.uniform(n) + 1;
				if (!p.isOpen(row, col)) {
					p.open(row, col);
					open_num++;
				}
			}
			double p_each = open_num * 1.0 / n / n;
			p_results[i] = p_each;
		}
	}

	public double mean() {
		return StdStats.mean(p_results);
	}

	public double stddev() {
		return StdStats.stddev(p_results);
	}

	public double confidenceLo() {
		return mean() - 1.96 * stddev() / (Math.sqrt(trials));
	}

	public double confidenceHi() {
		return mean() + 1.96 * stddev() / (Math.sqrt(trials));
	}

	public static void main(String[] args) {
		Stopwatch stopwatch = new Stopwatch();
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		PercolationStats ps = new PercolationStats(n, trials);
		//PercolationStats ps = new PercolationStats(128, 200);
		System.out.println("mean                    = " + ps.mean());
		System.out.println("stddev                  = " + ps.stddev());
		System.out.println("95% confidence interval = " + ps.confidenceLo()
				+ ps.confidenceHi());
		System.out.println(stopwatch.elapsedTime());
	}
}
