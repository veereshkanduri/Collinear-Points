import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {

    private ArrayList<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        try {
            if (checkDuplicateEntries(points) || checkArrayForNull(points)) {
                throw new IllegalArgumentException("exception");
            } else {
                for (Point referPoint : points) {
                    Point[] copy = Arrays.copyOf(points, points.length);
                    Arrays.sort(copy, referPoint.slopeOrder());
                    int count = 0;
                    double prevSlope = 0;
                    ArrayList<Point> collinear = new ArrayList<>();
                    for (Point p : copy) {
                       if (count == 0 || p.slopeTo(referPoint) == prevSlope) {
                           count++;
                       } else {
                           if (collinear.size() >= 3) {
                               collinear.add(referPoint);
                               Collections.sort(collinear);
                               int lastPoint = collinear.size() - 1;
                               Point p1 = collinear.get(0);
                               Point p2 = collinear.get(lastPoint);
                               if (referPoint == collinear.get(0) && !checkSegmentExists(p1, p2)) {
                                   segments.add(new LineSegment(p1, p2));
                               }
                           }
                           collinear.clear();
                       }
                        collinear.add(p);
                        prevSlope = p.slopeTo(referPoint);
                        count++;
                        if (p == copy[copy.length-1]) {
                            if (collinear.size() >= 3) {
                                collinear.add(referPoint);
                                Collections.sort(collinear);
                                int lastPoint = collinear.size() - 1;
                                Point p1 = collinear.get(0);
                                Point p2 = collinear.get(lastPoint);
                                if (referPoint == collinear.get(0) && !checkSegmentExists(p1, p2)) {
                                    segments.add(new LineSegment(p1, p2));
                                }
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException n) {
            throw new IllegalArgumentException(" ");
        }
    }

    private boolean checkSegmentExists(Point p1, Point p2) {
        int size = segments.size();
        for (int i = 0; i < size; i++) {
            if (segments.get(i) == new LineSegment(p1, p2)) {
                return true;
            }
        }
        return false;
    }

    public int numberOfSegments() {       // the number of line segments
        return segments.size();
    }

    public LineSegment[] segments() {               // the line segments
        return segments.toArray(new LineSegment[numberOfSegments()]);
    }

    private boolean checkArrayForNull(Point[] points) {
        for (Point p : points) {
            if (p == null) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDuplicateEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}