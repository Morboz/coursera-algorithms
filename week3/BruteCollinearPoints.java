import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by 695513639 on 2016/12/16.
 */
public class BruteCollinearPoints {
    private Point[] ps;
    private ArrayList<LineSegment> lineSegmentList = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null) {
            throw new NullPointerException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            }
            for(int j = i + 1;j <points.length;j++){
                if(points[i].slopeTo(points[j])==Double.NEGATIVE_INFINITY){
                    throw new IllegalArgumentException();
                }
            }
        }
        ps = points.clone();
        // ps排序
        Arrays.sort(ps);
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {

                        double s1 = points[i].slopeTo(points[j]);
                        double s2 = points[i].slopeTo(points[k]);
                        double s3 = points[i].slopeTo(points[l]);
                        if (s1 == s2 && s1 == s3) {
                            // 这四个点都不相同，而且共线的时候，取这4个点中最大和最小的点组成线段。
                            Point[] segment = {points[i], points[j], points[k], points[l]};
                            Arrays.sort(segment);
                            LineSegment lineSegment = new LineSegment(segment[0], segment[3]);
                            lineSegmentList.add(lineSegment);
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        // the number of l=ine segments
        return lineSegmentList.size();
    }

    public LineSegment[] segments() {
        // the line segments
        LineSegment[] ls = new LineSegment[numberOfSegments()];
        return lineSegmentList.toArray(ls);
    }
}
