import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by XINGXu on 2016/12/22.
 */
public class Board {

    private final int[][] blocks;
    private final int[] blankPosition = new int[2];
    private final int dim;

    public Board(int[][] blocks) {
        // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        this.blocks = blocks;
        dim = blocks.length;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] == 0) {
                    blankPosition[0] = i;
                    blankPosition[1] = j;
                }
            }
        }
    }

    private int[] getBlankPosition() {
        return blankPosition;
    }

    public int dimension() {
        // board dimension n
        return dim;
    }

    public int hamming() {
        // number of blocks out of place
        int hamming = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }
                if (i * dim + j + 1 != blocks[i][j]) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int manhattan = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }
                int n = blocks[i][j] - 1;
                int row = n / dim;
                int col = n % dim;
                manhattan += (Math.abs(row - i) + Math.abs(col - j));
            }
        }
//        for (int i = 0; i < N * N - 1; i++) {
//
//        }

        return manhattan;

    }

    public boolean isGoal() {
        // is this board the goal board?
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }
                if (i * dim + j + 1 != blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        // 交换任意一对方块
        Random random = new Random(47);
        int blank = blankPosition[0] * dim + blankPosition[1];
        int p1 = -1;
        int p2 = -1;
        while (p1 < 0 || p1 == blank) {
            p1 = random.nextInt(dim * dim - 1);
        }
        while (p2 < 0 || p2 == blank || p2 == p1) {
            p2 = random.nextInt(dim * dim - 1);
        }
        int i1 = p1 / dim;
        int j1 = p1 % dim;
        int i2 = p2 / dim;
        int j2 = p2 % dim;
        return new Board(exch(blocks, i1, j1, i2, j2));
    }

    public boolean equals(Object y) {
        if (y == null || !(y instanceof Board)) {
            return false;
        }
        if (((Board) y).dimension() != this.dimension()) {
            return false;
        }
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (blocks[i][j] != ((Board) y).blocks[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] copy(int[][] b) {
        int[][] copy = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                copy[i][j] = blocks[i][j];
            }
        }
        return copy;
    }

    public Iterable<Board> neighbors() {
        // all neighboring boards
        List<Board> neighbors = new ArrayList<>();
        // int[][] bak1 = copy(blocks);
        int b_x = blankPosition[0];
        int b_y = blankPosition[1];
        if (b_x > 0) {
            // 行索引大于0，交换空格与上面的方块
            Board exch_up = new Board(exch(copy(blocks), b_x, b_y, b_x - 1, b_y));
            neighbors.add(exch_up);
        }
        if (b_x < dim - 1) {
            // 行索引小于N-1，交换空格与下面的方块
            Board exch_down = new Board(exch(copy(blocks), b_x, b_y, b_x + 1, b_y));
            neighbors.add(exch_down);
        }
        if (b_y > 0) {
            // 列索引大于0，交换空格与左边的方块
            Board exch_left = new Board(exch(copy(blocks), b_x, b_y, b_x, b_y - 1));
            neighbors.add(exch_left);
        }
        if (b_y < dim - 1) {
            // 列索引小于N-1，交换空格与右边的方块
            Board exch_right = new Board(exch(copy(blocks), b_x, b_y, b_x, b_y + 1));
            neighbors.add(exch_right);
        }
        return neighbors;
    }

    private int[][] exch(int[][] _blocks, int i1, int j1, int i2, int j2) {
        int[][] copy = copy(_blocks);
        int temp = copy[i1][j1];
        copy[i1][j1] = copy[i2][j2];
        copy[i2][j2] = temp;
        return copy;
    }

    public String toString() {
        // string representation of this board (in the output format specified below)
        String boardStr = String.format("%d\n", dim);
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                boardStr = boardStr.concat(String.format("%3d ", blocks[i][j]));
            }
            boardStr = boardStr.concat("\n");
        }
        return boardStr;
        /*StringBuilder sb = new StringBuilder();
        if (dim <= 3) {
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    sb.append(blocks[i][j] + "  ");
                }
                // 换行
                sb.append("\r\n");
            }
        } else {
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    if (blocks[i][j] < 10) {
                        sb.append(" ");
                    }
                    sb.append(blocks[i][j] + " ");
                }
                // 换行
                sb.append("\r\n");
            }
        }
        return sb.toString();*/
    }

    public static void main(String[] args) {
        // unit tests (not graded)
        int[][] b = {{3, 1, 5}, {2, 0, 4}, {6, 7, 8}};
//        int[][] b = {{1, 14, 3, 7}, {12, 10, 15, 5}, {6, 2, 8, 0}, {9, 4, 13, 11}};
        Board board = new Board(b);

        System.out.println(board + "\r\n" + board.hamming() + "\r\n" + board.manhattan() + "\r\n" + board.isGoal());

        System.out.println("===================================");
        for (Board _b : board.neighbors()) {
            System.out.println(_b);
        }
    }
}