import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

/**
 * brute-force implementation
 *
 * Created by XINGXu on 2017/8/25.
 */
public class PointSET {

    private SET<Point2D> set;

    public PointSET() {
        // construct an empty set of points
    }

    public boolean isEmpty() {
        // is the set empty?
        return set.isEmpty();
    }

    public int size() {
        // number of points in the set
        return set.size();
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        set.add(p);
    }

    public boolean contains(Point2D p) {
        // does the set contain point p?
        return set.contains(p);
    }

    public void draw() {
        // draw all points to standard draw
        for (Point2D p : set) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary)
        double x = 0;
        double y = 0;
        for (Point2D p : set) {
            x = p.x();
            y = p.y();
            if (x > rect.xmax() || x < rect.xmin() || y > rect.ymax() || y < rect.ymin()){
                continue;
            }
        }
        return null;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        return null;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
        StdDraw.line(100,100,2.0,2.0);
    }
}
