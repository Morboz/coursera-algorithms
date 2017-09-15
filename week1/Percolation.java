import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF uf2;
	private int n;
	private boolean[] sites;
	private int id;

	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		uf = new WeightedQuickUnionUF(n * n + 1);
		uf2 = new WeightedQuickUnionUF(n * n + 2);	//with bottom visual sites
		sites = new boolean[n * n];
		this.n = n;
	}

	private int getId(int row, int col) {
		if (row > n || row < 1 || col > n || col < 1) {
			throw new IndexOutOfBoundsException();
		}
		return (row - 1) * n + (col - 1);
	}

	public void open(int row, int col) {

		id = getId(row, col);
		sites[id] = true;
		if (row == 1) {
			uf.union(getId(row, col), n * n);
			uf2.union(getId(row, col), n * n);
		}
		if (row == n) {
			uf2.union(getId(row, col), n * n + 1);
		}

		if (row > 1) {
			if (sites[getId(row - 1, col)]) {
				uf.union(getId(row - 1, col), id);
				uf2.union(getId(row - 1, col), id);
			}
		}
		if (row < n) {
			if (sites[getId(row + 1, col)]) {
				uf.union(getId(row + 1, col), id);
				uf2.union(getId(row + 1, col), id);
			}
		}
		if (col > 1) {
			if (sites[getId(row, col - 1)]) {
				uf.union(getId(row, col - 1), id);
				uf2.union(getId(row, col - 1), id);
			}
		}
		if (col < n) {
			if (sites[getId(row, col + 1)]) {
				uf.union(getId(row, col + 1), id);
				uf2.union(getId(row, col + 1), id);
			}
		}
	}

	public boolean isOpen(int row, int col) {
		return sites[getId(row, col)];
	}

	public boolean isFull(int row, int col) {
		return uf.connected(getId(row, col), n * n);
	}

	public boolean percolates() {
		return uf2.connected(n * n + 1, n * n);
	}

}
