import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

/**
 * Created by 695513639 on 2016/12/16.
 */
public class Point implements Comparable<Point> {

    private int x;
    private int y;

    public Point(int x, int y) {
        // constructs the point (x, y)
        this.x = x;
        this.y = y;
    }

    public void draw() {
        // draws this point
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        // draws the line segment from this point to that point
        StdDraw.line(x, y, that.x, that.y);
    }

    public String toString() {
        // string representation
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(Point o) {
        // 先比较y，y相同再比较x
        if (this.y < o.y || (this.y == o.y && this.x < o.x)) {
            return -1;
        } else if (this.y == o.y && this.x == o.x) {
            return 0;
        } else {
            return 1;
        }
    }

    public double slopeTo(Point that) {
        // the slope between this point and that point
        if (this.y == that.y && this.x != that.x) {
            // horizontal line
            return 0;
        } else if (this.x == that.x && this.y != that.y) {
            // vertical line
            return Double.POSITIVE_INFINITY;
        } else if (this.x == that.x && this.y == that.y) {
            // degenerate line
            return Double.NEGATIVE_INFINITY;
        } else {
            // normal segment
            return (that.y - this.y) * 1.0 / (that.x - this.x);
        }
    }

    public Comparator<Point> slopeOrder() {
        // compare two points by slopes they make with this point
        return new PointComparator();
    }

    private class PointComparator implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            double s1 = slopeTo(o1);
            double s2 = slopeTo(o2);
            if(s1 == s2){
                return 0;
            }else if(s1 > s2){
                return 1;
            }else{
                return -1;
            }
        }
    }

    public static void main(String[] args) {
        Point p1 = new Point(15, 15);
        Point p2 = new Point(30, 30);

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 40);
        StdDraw.setYscale(0, 40);
        p1.draw();
        p2.draw();
        p1.drawTo(p2);
        StdDraw.show();
    }
}
