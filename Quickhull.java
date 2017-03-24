import java.awt.Point;
import java.util.*;

public class Quickhull {
    public ArrayList<Point> quickHull(ArrayList<Point> points) {
        if (points.size() < 3)
        	return new ArrayList<Point>(points);
        
        ArrayList<Point> hull = new ArrayList<Point>();
        int minPoint = -1, maxPoint = -1;
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).x < minX) {
                minX = points.get(i).x;
                minPoint = i;
            }
            if (points.get(i).x > maxX) {
                maxX = points.get(i).x;
                maxPoint = i;
            }
        }
        
        Point A = points.get(minPoint);
        Point B = points.get(maxPoint);
        hull.add(A); points.remove(A);
        hull.add(B); points.remove(B);
        ArrayList<Point> leftSet = new ArrayList<Point>();
        ArrayList<Point> rightSet = new ArrayList<Point>();
 
        // Split to left set and right set
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            if (orientation(A, B, p) == -1)
                leftSet.add(p);
            else if (orientation(A, B, p) == 1)
                rightSet.add(p);
        }
        
        hullSet(A, B, rightSet, hull);
        hullSet(B, A, leftSet, hull);
        return hull;
    }

    // Recursive method to form convex hull on each side of line AB
    public void hullSet(Point A, Point B, ArrayList<Point> set, ArrayList<Point> hull) {
        int insertPosition = hull.indexOf(B);
        if (set.size() == 0) // base case
            return;
        
        if (set.size() == 1) {
            Point p = set.get(0);
            set.remove(p);
            hull.add(insertPosition, p);
            return;
        }
        
        // Find the point that's furthest away from the line AB and add it to the hull
        int dist = Integer.MIN_VALUE;
        int furthestPoint = -1;
        for (int i = 0; i < set.size(); i++) {
            Point p = set.get(i);
            int distance = distance(A, B, p);
            if (distance > dist) {
                dist = distance;
                furthestPoint = i;
            }
        }
        Point P = set.get(furthestPoint);
        set.remove(furthestPoint);
        hull.add(insertPosition, P);

        // Split the remaining points into left and right sets. Discard the points in the triangle.
        ArrayList<Point> leftSetAP = new ArrayList<Point>();
        for (int i = 0; i < set.size(); i++) {
            Point M = set.get(i);
            if (orientation(A, P, M) == 1) {
                leftSetAP.add(M);
            }
        }
        ArrayList<Point> leftSetPB = new ArrayList<Point>();
        for (int i = 0; i < set.size(); i++) {
            Point M = set.get(i);
            if (orientation(P, B, M) == 1) {
                leftSetPB.add(M);
            }
        }
        hullSet(A, P, leftSetAP, hull);
        hullSet(P, B, leftSetPB, hull);
    }

    // Return the cross product |AB x AC|, which is proportional to the distance from point C to line AB
    public int distance(Point A, Point B, Point C) {
        return Math.abs(B.x - A.x) * (A.y - C.y) - (B.y - A.y) * (A.x - C.x); // cross product of AB and AC
    }
    
    // Return 0 if collinear
    // Return -1 if counterclockwise (slope AP < slope AB)
    // Return 1 if clockwise (slope AP > slope AB)
    public int orientation(Point A, Point B, Point P) {
        int cp1 = (B.x - A.x)*(P.y - A.y) - (B.y - A.y)*(P.x - A.x);
        if (cp1 > 0)
            return -1;
        else if (cp1 == 0)
            return 0;
        else
            return 1;
    }
 
    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the number of points and coordinates");
        int N = s.nextInt();
        ArrayList<Point> points = new ArrayList<Point>();
        for (int i = 0; i < N; i++) {
            int x = s.nextInt();
            int y = s.nextInt();
            points.add(new Point(x, y));
        }
        Quickhull qh = new Quickhull();
        ArrayList<Point> p = qh.quickHull(points);
        for (int i = 0; i < p.size(); i++)
            System.out.println("(" + p.get(i).x + ", " + p.get(i).y + ")");
        s.close();
    }
}