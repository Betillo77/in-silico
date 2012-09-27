import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;
import java.util.StringTokenizer;


public class F 
{
	 static class Scanner
     {
             BufferedReader br;
             StringTokenizer st;
             
             public Scanner()
             {
                    br = new BufferedReader(new InputStreamReader(System.in));
             }
             
             public String next()
             {

                     while(st == null || !st.hasMoreTokens())
                     {
                             try { st = new StringTokenizer(br.readLine()); }
                             catch(Exception e) { throw new RuntimeException(); }
                     }
                     return st.nextToken();
             }

             public int nextInt()
             {
                     return Integer.parseInt(next());
             }
             
             public double nextDouble()
             {
                     return Double.parseDouble(next());
             }
             
             public String nextLine()
             {
                     st = null;
                     try { return br.readLine(); }
                     catch(Exception e) { throw new RuntimeException(); }
             }
             
             public boolean endLine()
             {
                     try 
                     {
                             String next = br.readLine();
                             while(next != null && next.trim().isEmpty())
                                     next = br.readLine();
                             if(next == null)
                                     return true;
                             st = new StringTokenizer(next);
                             return st.hasMoreTokens();
                     }
                     catch(Exception e) { throw new RuntimeException(); }
             }
     }

	static class CC
	{
		int n;
		int k;
		int[] vals;
		
		public CC(int n, int k, int[] anterior)
		{
			vals = new int[n];
			for(int i = 0; i < anterior.length; i++)
				vals[i] = anterior[i];
			this.n = n;
			this.k = k;
		}
		
		public ArrayList <CC> generarHijos()
		{
			ArrayList <CC> respuesta = new ArrayList <CC> ();
			CC primero = new CC(n + 1, k + 1, vals);
			primero.vals[n] = k;
			respuesta.add(primero);
			for(int i = 0; i < k; i++)
			{
				CC siguiente = new CC(n + 1, k, vals);
				siguiente.vals[n] = i;
				respuesta.add(siguiente);
			}
			return respuesta;
		}
	}
	
	@SuppressWarnings("unchecked")
	static ArrayList <CC> [] todos = new ArrayList[10];
	
	public static void generarTodos()
	{
		todos[0] = new ArrayList <CC> ();
		todos[0].add(new CC(0, 0, new int[0]));
		for(int i = 1; i < 10; i++)
		{
			ArrayList <CC> nuevo = new ArrayList <CC> ();
			for(CC actual : todos[i - 1])
				nuevo.addAll(actual.generarHijos());
			todos[i] = nuevo;
		}
	}
	
    static int ccw(Point2D a, Point2D b, Point2D c) {
        double area2 = (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);
        if      (area2 < 0) return -1;
        else if (area2 > 0) return +1;
        else                return  0;
    }
  
    static class Point2D implements Comparable<Point2D> 
    {
        public final Comparator<Point2D> X_ORDER = new XOrder();
        public final Comparator<Point2D> Y_ORDER = new YOrder();
        public final Comparator<Point2D> R_ORDER = new ROrder();

        public final Comparator<Point2D> POLAR_ORDER = new PolarOrder();
        public final Comparator<Point2D> ATAN2_ORDER = new Atan2Order();
        public final Comparator<Point2D> DISTANCE_TO_ORDER = new DistanceToOrder();

        private final double x;    // x coordinate
        private final double y;    // y coordinate

        // create a new point (x, y)
        public Point2D(double x, double y) {
            this.x = x;
            this.y = y;
        }

        // return the x-coorindate of this point
        public double x() { return x; }

        // return the y-coorindate of this point
        public double y() { return y; }

        // return the radius of this point in polar coordinates
        public double r() { return Math.sqrt(x*x + y*y); }

        // return the angle of this point in polar coordinates
        // (between -pi/2 and pi/2)
        public double theta() { return Math.atan2(y, x); }

        // return the polar angle between this point and that point (between -pi and pi);
        // (0 if two points are equal)
        private double angleTo(Point2D that) {
            double dx = that.x - this.x;
            double dy = that.y - this.y;
            return Math.atan2(dy, dx);
        }

        // is a->b->c a counter-clockwise turn?
        // -1 if clockwise, +1 if counter-clockwise, 0 if collinear
  

        // twice signed area of a-b-c
        double area2(Point2D a, Point2D b, Point2D c) {
            return (b.x-a.x)*(c.y-a.y) - (b.y-a.y)*(c.x-a.x);
        }

        // return Euclidean distance between this point and that point
        double distanceTo(Point2D that) {
            double dx = this.x - that.x;
            double dy = this.y - that.y;
            return Math.sqrt(dx*dx + dy*dy);
        }

        // return square of Euclidean distance between this point and that point
        double distanceSquaredTo(Point2D that) {
            double dx = this.x - that.x;
            double dy = this.y - that.y;
            return dx*dx + dy*dy;
        }

        // compare by y-coordinate, breaking ties by x-coordinate
        public int compareTo(Point2D that) {
            if (this.y < that.y) return -1;
            if (this.y > that.y) return +1;
            if (this.x < that.x) return -1;
            if (this.x > that.x) return +1;
            return 0;
        }

        // compare points according to their x-coordinate
        class XOrder implements Comparator<Point2D> {
            public int compare(Point2D p, Point2D q) {
                if (p.x < q.x) return -1;
                if (p.x > q.x) return +1;
                return 0;
            }
        }

        // compare points according to their y-coordinate
        class YOrder implements Comparator<Point2D> {
            public int compare(Point2D p, Point2D q) {
                if (p.y < q.y) return -1;
                if (p.y > q.y) return +1;
                return 0;
            }
        }

        // compare points according to their polar radius
        class ROrder implements Comparator<Point2D> {
            public int compare(Point2D p, Point2D q) {
                double delta = (p.x*p.x + p.y*p.y) - (q.x*q.x + q.y*q.y);
                if (delta < 0) return -1;
                if (delta > 0) return +1;
                return 0;
            }
        }
     
        // compare other points relative to atan2 angle (bewteen -pi/2 and pi/2) they make with this Point
        private class Atan2Order implements Comparator<Point2D> {
            public int compare(Point2D q1, Point2D q2) {
                double angle1 = angleTo(q1);
                double angle2 = angleTo(q2);
                if      (angle1 < angle2) return -1;
                else if (angle1 > angle2) return +1;
                else                      return  0;
            }
        }

        // compare other points relative to polar angle (between 0 and 2pi) they make with this Point
        private class PolarOrder implements Comparator<Point2D> {
            public int compare(Point2D q1, Point2D q2) {
                double dx1 = q1.x - x;
                double dy1 = q1.y - y;
                double dx2 = q2.x - x;
                double dy2 = q2.y - y;

                if      (dy1 >= 0 && dy2 < 0) return -1;    // q1 above; q2 below
                else if (dy2 >= 0 && dy1 < 0) return +1;    // q1 below; q2 above
                else if (dy1 == 0 && dy2 == 0) {            // 3-collinear and horizontal
                    if      (dx1 >= 0 && dx2 < 0) return -1;
                    else if (dx2 >= 0 && dx1 < 0) return +1;
                    else                          return  0;
                }
                else return -ccw(Point2D.this, q1, q2);     // both above or below

                // Note: ccw() recomputes dx1, dy1, dx2, and dy2
            }
        }

        // compare points according to their distance to this point
        private class DistanceToOrder implements Comparator<Point2D> {
            public int compare(Point2D p, Point2D q) {
                double dist1 = distanceSquaredTo(p);
                double dist2 = distanceSquaredTo(q);
                if      (dist1 < dist2) return -1;
                else if (dist1 > dist2) return +1;
                else                    return  0;
            }
        }


        // does this point equal y?
        public boolean equals(Object other) {
            if (other == this) return true;
            if (other == null) return false;
            if (other.getClass() != this.getClass()) return false;
            Point2D that = (Point2D) other;
            return this.x == that.x && this.y == that.y;
        }

        // convert to string
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
    
    static class GrahamScan
    {
        private Stack<Point2D> hull = new Stack<Point2D>();

    public GrahamScan(ArrayList <Point2D> pts) 
    {
        int N = pts.size();
        Point2D[] points = new Point2D[N];
        for (int i = 0; i < N; i++)
            points[i] = pts.get(i);

        // preprocess so that points[0] has lowest y-coordinate; break ties by x-coordinate
        // points[0] is an extreme point of the convex hull
        // (alternatively, could do easily in linear time)
        Arrays.sort(points);

        // sort by polar angle with respect to base point points[0],
        // breaking ties by distance to points[0]
        Arrays.sort(points, 1, N, points[0].POLAR_ORDER);

        hull.push(points[0]);       // p[0] is first extreme point

        // find index k1 of first point not equal to points[0]
        int k1;
        for (k1 = 1; k1 < N; k1++)
            if (!points[0].equals(points[k1])) break;
        if (k1 == N) return;        // all points equal

        // find index k2 of first point not collinear with points[0] and points[k1]
        int k2;
        for (k2 = k1 + 1; k2 < N; k2++)
            if (ccw(points[0], points[k1], points[k2]) != 0) break;
        hull.push(points[k2-1]);    // points[k2-1] is second extreme point

        // Graham scan; note that points[N-1] is extreme point different from points[0]
        for (int i = k2; i < N; i++) {
            Point2D top = hull.pop();
            while (ccw(hull.peek(), top, points[i]) <= 0) {
                top = hull.pop();
            }
            hull.push(top);
            hull.push(points[i]);
        }

        assert isConvex();
    }

    // return extreme points on convex hull in counterclockwise order as an Iterable
    public Iterable<Point2D> hull() {
        Stack<Point2D> s = new Stack<Point2D>();
        for (Point2D p : hull) s.push(p);
        return s;
    }

    // check that boundary of hull is strictly convex
    private boolean isConvex() {
        int N = hull.size();
        if (N <= 2) return true;

        Point2D[] points = new Point2D[N];
        int n = 0;
        for (Point2D p : hull()) {
            points[n++] = p;
        }

        for (int i = 0; i < N; i++) {
            if (ccw(points[i], points[(i+1) % N], points[(i+2) % N]) <= 0) {
                return false;
            }
        }
        return true;
    }
    }
    

	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		generarTodos();
		int caso = 1;
		while(true)
		{
			int n = sc.nextInt();
			int r = sc.nextInt();
			if(n == 0 && r == 0)
				return;
			Point2D[] puntos = new Point2D[n];
			for(int i = 0; i < n; i++)
				puntos[i] = new Point2D(sc.nextInt(), sc.nextInt());
			System.out.printf("Case %d: length = %s\n", caso++, poner(new BigDecimal(solve(n, r, puntos)).divide(BigDecimal.ONE, 2, BigDecimal.ROUND_HALF_DOWN).toPlainString()));
		}
	}


	private static Object poner(String plainString) 
	{
		if(!plainString.contains("."))
			return plainString + ".00";
		int indice = plainString.indexOf('.');
		while(plainString.length() < indice + 3)
			plainString += "0";
		return plainString;
	}

	static double calcular(int mascara, Point2D[] puntos, int r) 
	{
		ArrayList <Point2D> arreglo = new ArrayList <Point2D> ();
		int indice = 0;
		while(mascara > 0)
		{
			if((mascara & 1) == 1)
				arreglo.add(puntos[indice]);
			mascara >>= 1;
        	indice++;
		}
		if(arreglo.size() == 1)
			return 2 * Math.PI * r;
		ArrayList <Point2D> cHull = new ArrayList <Point2D> ();
		if(arreglo.size() != 2)
		{
			GrahamScan gs = new GrahamScan(arreglo);
			cHull.addAll(gs.hull);
		}
		else
			cHull.addAll(arreglo);
		double result = 0;
		for(int i = 0; i < cHull.size(); i++)
			result += cHull.get(i).distanceTo(cHull.get((i + 1) % cHull.size()));
		result += 2 * Math.PI * r;
		return result;
	}
	
	static double[] dp;
	
	
	static double solve(int n, int r, Point2D[] puntos)
	{
		dp = new double[1 << n];
		for(int i = 1; i < dp.length; i++)
			dp[i] = calcular(i, puntos, r);
		double mejor = Double.MAX_VALUE;
		for(CC cConjunto : todos[n])
		{
			double total = 0;
			for(int i = 0; i < cConjunto.k; i++)
			{
				int mascara = 0;
				for(int j = 0; j < cConjunto.n; j++)
					if(cConjunto.vals[j] == i)
						mascara |= 1 << j;
				total += dp[mascara];
			}
			mejor = Math.min(mejor, total);
		}
		return mejor;
	}
}
