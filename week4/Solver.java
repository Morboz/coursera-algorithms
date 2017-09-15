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

}