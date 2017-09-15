import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by 695513639 on 2016/12/16.
 */
public class FastCollinearPoints {
    private int N;
    private ArrayList<LineSegment> ls;

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        N = points.length;
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

        Point[] aux = points.clone();
        Arrays.sort(aux);
        ls = new ArrayList<LineSegment>();


        for (int i = 0; i < N; i++) {
            // slideOrder 的使用！！！！
            // 这一拍完，原来i的点肯定是在0，其他的点都按顺序排列在后面
            Arrays.sort(aux, points[i].slopeOrder());
            addLineSegments(aux);
        }
    }

    private void addLineSegments(Point[] aux) {
        // 下标初始化
        int i = 1;
        while (i < N - 1) {
            // 共线点数初始化
            int n = 0;
            LinkedList<Point> collinearPoints = new LinkedList<Point>();
            while (i + n + 1 < N && aux[0].slopeTo(aux[i + n]) == aux[0].slopeTo(aux[i + n + 1])) {
                // 计数+1
                n++;
            }
            if (n >= 2) {
                collinearPoints.add(aux[0]);
                for (int j = 0; j <= n; j++) {
                    collinearPoints.add(aux[i + j]);
                }
                // 共线的点排序
                Collections.sort(collinearPoints);
                if(collinearPoints.getFirst() == aux[0]){
                    // 取出首尾的点组成线段
                    LineSegment lineSegment = new LineSegment(collinearPoints.getFirst(), collinearPoints.getLast());
                    // addLs(lineSegment);
                    ls.add(lineSegment);
                }
            }
            i += (n + 1);
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return ls.size();
    }

    public LineSegment[] segments() {
        // the line segmentsl
        LineSegment[] segments = new LineSegment[numberOfSegments()];
        return ls.toArray(segments);
    }
}
