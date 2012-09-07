import java.util.Arrays;

public class Fast extends Brute {

    protected static void output(Point origin, Point[] points, int start, int stop) {
        Point[] scratch = new Point[stop - start + 1];
        scratch[0] = origin;
        for (int i = start; i < stop; i++)
            scratch[i - start + 1] = points[i];
        Arrays.sort(scratch);
        printLineSegment(scratch);
        draw(scratch);
    }

    public static void main(String[] args) {
        setUpDrawing();

        Point[] points = readInput(args[0]);
        int n = points.length;

        int start, stop; // Pointers to the beginning and end of runs.
        double last, next; // To hold subsequent slopes.
        for (int i = 0; i < n - 1; i++) {
            Point[] scratch = new Point[n - i];
            for (int j = i; j < n; j++)
                scratch[j - i] = points[j];
            Arrays.sort(scratch, points[i].SLOPE_ORDER);
            stop = 0;
            do {
                // Invariant: We're looking at the subarray that begins right
                // after the last run.
                next = points[i].slopeTo(scratch[stop]);
                // Gallop the stop pointer rightward to look for a run.
                do {
                    stop += 1;
                    last = next;
                    next = points[i].slopeTo(scratch[stop]);
                } while (stop < n - i - 1 && last != next);
                start = stop - 1;
                while (stop < n - i && last == points[i].slopeTo(scratch[stop]))
                    stop++;
                if (stop - start + 1 >= MIN_POINTS) // Add one for points[i].
                    output(points[i], scratch, start, stop);
            } while (stop < n - i - 1);
        }
        StdDraw.show(0);
    }
}
