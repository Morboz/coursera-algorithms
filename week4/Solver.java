import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by XINGXu on 2016/12/24.
 */
public class Solver {

    private MinPQ<GameNode> minPQ = new MinPQ<GameNode>(new ManhattanPriority());
    private MinPQ<GameNode> twinMinPQ = new MinPQ<GameNode>(new ManhattanPriority());
    //    private FixSizedMinPQ<GameNode> minPQ = new FixSizedMinPQ<GameNode>(100, new ManhattanPriority());
//    private FixSizedMinPQ<GameNode> twinMinPQ = new FixSizedMinPQ<GameNode>(100, new ManhattanPriority());
    //    private List<Board> solution;
    private int moves;
    private GameNode solutionNode;
//    private Solution solution;

    private LinkedList<Board> solution = new LinkedList<Board>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) {
            throw new IllegalArgumentException();
        }

        Board twin = initial.twin();
//        solution = new ArrayList<Board>();
        moves = 0;
//        int priority = initial.manhattan() + 0;
//        Object[] gameNode = {priority,initial};
        GameNode node = new GameNode(initial, 0, null);
        GameNode twinNode = new GameNode(twin, 0, null);

        minPQ.insert(node);
        twinMinPQ.insert(twinNode);
//        while (!minPQ.min().getBoard().isGoal() && !twinMinPQ.min().getBoard().isGoal()) {
        while (true) {

            if (node.getBoard().isGoal()) {
                solutionNode = node;
//                solution = new Solution();
                GameNode cursor = solutionNode;
                while (cursor != null) {
                    solution.addFirst(cursor.b);
                    cursor = cursor.prev;
                }
                break;
            }
            if (twinNode.getBoard().isGoal()) {
                solutionNode = null;
                break;
            }

//            while (min_pq.min().getMoves() != moves) {
//                min_pq.delMin();
//            }
//            node = min_pq.min();
//            solution.add(node.getBoard());
//            moves++;
//                System.out.println("Moves " + moves + ". neighbour node, manhattan : " + neighbour.getBoard().manhattan()
//                        + ", hamming :" + neighbour.getBoard().hamming());

            if (minPQ.size() > 0) {
                node = minPQ.delMin();
            }
            for (Board b : node.b.neighbors()) {
                if (node.getPrev() != null && b.equals(node.getPrev().b)) {
                    continue;
                }
                minPQ.insert(new GameNode(b, node.moves + 1, node));
            }

            if (twinMinPQ.size() > 0) {
                twinNode = twinMinPQ.delMin();
            }
            for (Board b : twinNode.b.neighbors()) {
                if (twinNode.getPrev() != null && b.equals(twinNode.getPrev().b)) {
                    continue;
                }
                twinMinPQ.insert(new GameNode(b, twinNode.moves + 1, twinNode));
            }
        }

    }

    public boolean isSolvable() {
        return solutionNode != null;
    }

    public int moves() {
        // min number of moves to solve initial board; -1 if unsolvable
        return isSolvable() ? solutionNode.moves : -1;
    }

    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if unsolvable
        if (!isSolvable()) return null;
        return this.solution;
    }

    private class Solution implements Iterable<Board> {

        private SolutionIterator iterator;

        @Override
        public Iterator<Board> iterator() {
            if (iterator == null) {
                iterator = new SolutionIterator();
            }
            return iterator;
        }

        private class SolutionIterator implements Iterator<Board> {

            private GameNode pointer;

            public SolutionIterator() {
                pointer = solutionNode;
            }

            @Override
            public boolean hasNext() {
                return pointer != null;
            }

            @Override
            public Board next() {
                GameNode temp = pointer;
                pointer = pointer.prev;
                return temp.b;
            }

            @Override
            public void remove() {
            }
        }
    }


    private class GameNode {
        private Board b;
        private int moves;
        private GameNode prev;
        private int manhattan = -1;

        public GameNode(Board b, int moves, GameNode prev) {
            this.b = b;
            this.moves = moves;
            this.prev = prev;
            this.manhattan = b.manhattan() + moves;
        }
        public int manhattan() {
            return manhattan;
        }

        public GameNode getPrev() {
            return prev;
        }

        public Board getBoard() {
            return b;
        }

        public int getMoves() {
            return moves;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append(b);
            sb.append("priority: ").append(b.manhattan() + moves).append(';');
            return sb.toString();
        }

//        @Override
//        public int compareTo(GameNode that) {
//            int p1 = b.manhattan() + moves;
//            int p2 = that.getBoard().manhattan() + that.moves;
//            if (p1 == p2) {
////                return o1.b.hamming() - o2.b.hamming();
//                return 0;
//            } else {
//                return p1 - p2;
//            }
//        }
    }


    private class ManhattanPriority implements Comparator<GameNode> {

        @Override
        public int compare(GameNode o1, GameNode o2) {
            int p1 = o1.manhattan();
            int p2 = o2.manhattan();
            if (p1 == p2) {
//                return o1.b.hamming() - o2.b.hamming();
                return 0;
            } else {
                return p1 - p2;
            }
        }
    }

    public static void main(String[] args) {
        // solve a slider puzzle (given below)
//        int[][] b = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
//        int[][] b = {{0, 1, 4}, {7, 2, 3}, {5, 6, 8}};
//        int[][] b = {{1, 14, 3, 7}, {12, 10, 15, 5}, {6, 2, 8, 0}, {9, 4, 13, 11}};
//        int[][] b = {{2, 14, 1, 15}, {6, 5, 8, 7}, {9, 0, 10, 12}, {13, 3, 11, 4}};
//        int[][] b = {{4, 1, 0}, {2, 7, 3}, {5, 6, 8}};
//        int[][] b = {{4, 3, 1}, {6, 2, 0}, {7, 5, 8}};
//        Board board = new Board(b);
//        Solver solver = new Solver(board);
//        Iterable<Board> solution = solver.solution();
//        if (solver.isSolvable()) {
//            int num = 0;
//            for (Board board1 : solution) {
//
//                System.out.println(board1.toString() + ++num);
//            }
//        } else {
//            System.out.println("the board is not solvable.");
//        }
//        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }


    /*private class FixSizedMinPQ<Key> implements Iterable<Key> {
        private Key[] pq;                    // store items at indices 1 to n
        private int n;                       // number of items on priority queue
        private Comparator<Key> comparator;  // optional comparator
        private int capacity;

        public FixSizedMinPQ(int initCapacity, Comparator<Key> comparator) {
            this.comparator = comparator;
            this.capacity = initCapacity;
            pq = (Key[]) new Object[initCapacity + 2];
            n = 0;
        }


        public boolean isEmpty() {
            return n == 0;
        }

        public int size() {
            return n;
        }

        public Key min() {
            if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
            return pq[1];
        }


        public void insert(Key x) {
            // add x, and percolate it up to maintain heap invariant
            pq[++n] = x;
            swim(n);
            // 将末尾的哨兵置为空
            pq[capacity + 1] = null;
            n = n > capacity ? capacity : n;
        }


        public Key delMin() {
            if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
            exch(1, n);
            Key min = pq[n--];
            sink(1);
            pq[n + 1] = null;         // avoid loitering and help with garbage collection
            return min;
        }


        *//**
         * ************************************************************************
         * Helper functions to restore the heap invariant.
         * *************************************************************************
         *//*

        private void swim(int k) {
            while (k > 1 && greater(k / 2, k)) {
                exch(k, k / 2);
                k = k / 2;
            }
        }

        private void sink(int k) {
            while (2 * k <= n) {
                int j = 2 * k;
                if (j < n && greater(j, j + 1)) j++;
                if (!greater(k, j)) break;
                exch(k, j);
                k = j;
            }
        }

        *//**
         * ************************************************************************
         * Helper functions for compares and swaps.
         * *************************************************************************
         *//*
        private boolean greater(int i, int j) {
            if (comparator == null) {
                return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0;
            } else {
                return comparator.compare(pq[i], pq[j]) > 0;
            }
        }

        private void exch(int i, int j) {
            Key swap = pq[i];
            pq[i] = pq[j];
            pq[j] = swap;
        }


        *//**
         * Returns an iterator that iterates over the keys on this priority queue
         * in ascending order.
         * <p/>
         * The iterator doesn't implement {@code remove()} since it's optional.
         *
         * @return an iterator that iterates over the keys in ascending order
         *//*
        public Iterator<Key> iterator() {
            return new HeapIterator();
        }

        private class HeapIterator implements Iterator<Key> {
            // create a new pq
            private MinPQ<Key> copy;

            // add all items to copy of heap
            // takes linear time since already in heap order so no keys move
            public HeapIterator() {
                if (comparator == null) copy = new MinPQ<Key>(size());
                else copy = new MinPQ<Key>(size(), comparator);
                for (int i = 1; i <= n; i++)
                    copy.insert(pq[i]);
            }

            public boolean hasNext() {
                return !copy.isEmpty();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Key next() {
                if (!hasNext()) throw new NoSuchElementException();
                return copy.delMin();
            }
        }


    }*/

}