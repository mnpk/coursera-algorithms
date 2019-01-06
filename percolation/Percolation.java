/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] sites;
    private int count;
    private final WeightedQuickUnionUF uf;
    private final int firstNodeIdx;
    private final int lastNodeIdx;
    private final int n;

    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.n = n;
        sites = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sites[i][j] = false;
            }
        }
        count = 0;

        firstNodeIdx = 0;
        lastNodeIdx = n * n + 1;

        uf = new WeightedQuickUnionUF(n * n + 2);
        for (int col = 1; col <= n; col++) {
            uf.union(firstNodeIdx, getIdx(1, col));
            uf.union(lastNodeIdx, getIdx(n, col));
        }
    }

    public void open(int row, int col) {
        if (row < 1 || row > sites.length || col < 1 || col > sites.length) {
            throw new java.lang.IllegalArgumentException();
        }

        if (sites[row - 1][col - 1]) return;

        sites[row - 1][col - 1] = true;
        count++;

        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(getIdx(row, col), getIdx(row - 1, col));
        }

        if (row < n && isOpen(row + 1, col)) {
            uf.union(getIdx(row, col), getIdx(row + 1, col));
        }

        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(getIdx(row, col), getIdx(row, col - 1));
        }

        if (col < n && isOpen(row, col + 1)) {
            uf.union(getIdx(row, col), getIdx(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || row > sites.length || col < 1 || col > sites.length) {
            throw new java.lang.IllegalArgumentException();
        }

        return sites[row - 1][col - 1];

    }

    public boolean isFull(int row, int col) {
        if (row < 1 || row > sites.length || col < 1 || col > sites.length) {
            throw new java.lang.IllegalArgumentException();
        }

        return isOpen(row, col) && uf.connected(firstNodeIdx, getIdx(row, col));
    }

    public int numberOfOpenSites() {
        return count;
    }

    public boolean percolates() {
        return uf.connected(firstNodeIdx, lastNodeIdx);
    }

    private int getIdx(int row, int col) {
        return (row - 1) * n + col;
    }
}
