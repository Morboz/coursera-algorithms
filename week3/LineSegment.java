import edu.princeton.cs.algs4.StdDraw;

/**
 * Created by 695513639 on 2016/12/16.
 */
public class LineSegment {
    private Point p;
    private Point q;

    public LineSegment(Point p, Point q) {
        // constructs the line segment between points p and q
        this.p = p;
        this.q = q;
    }

    public void draw() {
        // draws this line segment
        p.drawTo(q);
    }

    public String toString() {
        // string representation
        return p.toString() + "," + q.toString();
    }

    public static void main(String[] args) {
        Point p1 = new Point(15, 15);
        Point p2 = new Point(30, 30);
        LineSegment lineSegment = new LineSegment(p1, p2);
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 40);
        StdDraw.setYscale(0, 40);
        p1.draw();
        p2.draw();
        lineSegment.draw();
        StdDraw.show();
    }
}
