import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {


    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {             // finds all line segments containing 4 points
        try {
            if (checkDuplicateEntries(points) || checkArrayForNull(points)) {
                throw  new IllegalArgumentException("exception");
            } else {
                ArrayList<LineSegment> foundSegments = new ArrayList<>();

                Point[] pointsCopy = Arrays.copyOf(points, points.length);
                Arrays.sort(pointsCopy);

                for (int p = 0; p < pointsCopy.length; p++) {
                    for (int q = p + 1; q < pointsCopy.length; q++) {
                        for (int r = q + 1; r < pointsCopy.length; r++) {
                            for (int s = r + 1; s < pointsCopy.length; s++) {
                                if (pointsCopy[p].slopeTo(pointsCopy[q]) == pointsCopy[p].slopeTo(pointsCopy[r]) &&
                                        pointsCopy[p].slopeTo(pointsCopy[q]) == pointsCopy[p].slopeTo(pointsCopy[s])) {
                                    foundSegments.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                                }
                            }
                        }
                    }
                }

                segments = foundSegments.toArray(new LineSegment[foundSegments.size()]);

            }

        } catch (NullPointerException n) {
            throw new IllegalArgumentException("");
        }

    }

    private LineSegment[] getSegments() {
        return segments;
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

    public int numberOfSegments() {                         // the number of line segments
        return getSegments().length;
    }

    public LineSegment[] segments() {                       // the line segments
        return Arrays.copyOf(getSegments(), numberOfSegments());
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }

}
