import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class H 
{
	static class Scanner
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		String nextLine()
		{
			String next;
			try 
			{
				next = br.readLine();
			} 
			catch (IOException ex) 
			{
				throw(new RuntimeException(ex));
			}
			return next;
		}

		String next()
		{
			while(st == null || !st.hasMoreTokens())
			{
				String linea = nextLine();
				if(linea == null)
					return null;
				st = new StringTokenizer(linea);
			}
			return st.nextToken();
		}

		int nextInt()
		{
			return Integer.parseInt(next());
		}

		public double nextDouble()
		{
			return Double.parseDouble(next());
		}
	}

	static class Polygon
	{
		ArrayList <Line2D> lines;
		Point2D anterior = null;

		public Polygon(int minSize)
		{
			lines = new ArrayList <Line2D> (minSize); 
		}

		public void add(Point2D nuevo)
		{
			if(anterior != null)
			{
				lines.add(new Line2D.Double(anterior, nuevo));
			}
			anterior = nuevo;
		}

		public void close()
		{
			lines.add(new Line2D.Double(anterior, lines.get(0).getP1()));
		}

		public static Point2D subtract(Point2D a, Point2D b)
		{
			return new Point2D.Double(a.getX() - b.getX(), a.getY() - b.getY());
		}

		static double abs(Point2D a)
		{
			return Math.sqrt(a.getX() * a.getX() + a.getY() * a.getY());
		}

		static double cross(Point2D a, Point2D b) 
		{
			return (a.getX() * b.getY()) - (a.getY() * b.getX());
		}

		static boolean is_point_online(Point2D a, Point2D b, Point2D c)
		{
			return abs(subtract(a, c)) + abs(subtract(b, c)) <= abs(subtract(a, b));
		}

		// Ya esta implementado en ((Line2D) lineA).intersectsLine(lineB)
		public static boolean lines_intersect(Line2D a, Line2D b)
		{
			return cross(subtract(a.getP2(), a.getP1()), subtract(b.getP1(), a.getP1())) *
					cross(subtract(a.getP2(), a.getP1()), subtract(b.getP2(), a.getP1())) < 0 && 
					cross(subtract(b.getP2(), b.getP1()), subtract(a.getP1(), b.getP1())) *
					cross(subtract(b.getP2(), b.getP1()), subtract(a.getP2(), b.getP1())) < 0;
		}

		// Funciona para cualquier poligono excepto si es self-intersecting
		public double area() 
		{
			double area = 0;
			for (Line2D line : lines) 
			{
				area += line.getP1().getX() * line.getP2().getY();
				area -= line.getP2().getX() * line.getP1().getY();
			}
			area /= 2.0;
			return Math.abs(area);
		}

		// Funciona para cualquier poligono excepto si es self-intersecting
		public double areaUnsigned() 
		{
			double area = 0;
			for (Line2D line : lines) 
			{
				area += line.getP1().getX() * line.getP2().getY();
				area -= line.getP2().getX() * line.getP1().getY();
			}
			area /= 2.0;
			return area;
		}

		// Funciona para cualquier poligono excepto si es self-intersecting
		public Point2D centerOfMass() 
		{
			double cx = 0, cy = 0;
			double area = areaUnsigned();
			double factor = 0;
			for (Line2D line : lines) 
			{
				factor = line.getP1().getX() * line.getP2().getY() - line.getP2().getX() * line.getP1().getY();
				cx += (line.getP1().getX() + line.getP2().getX()) * factor;
				cy += (line.getP1().getY() + line.getP2().getY()) * factor;
			}
			area *= 6.0d;
			factor = 1 / area;
			cx *= factor;
			cy *= factor;
			return new Point2D.Double(cx, cy);
		}

		public boolean intersects(Polygon other)
		{
			for(Line2D lineA : lines)
			{
				if(other.contains(lineA.getP1()))
					return true;
				for(Line2D lineB : other.lines)
				{
					if(lineA.intersectsLine(lineB))
						return true;
				}
			}
			for(Line2D line : other.lines)
			{
				if(contains(line.getP1()))
					return true;
			}
			return false;
		}

		public boolean contains(Point2D p)
		{
			int cnt = 0;
			for(Line2D line : lines)
			{
				Point2D curr = subtract(line.getP1(), p);
				Point2D next = subtract(line.getP2(), p);
				if(curr.getY() > next.getY())
				{
					Point2D temp = curr;
					curr = next;
					next = temp;
				}
				if (curr.getY() < 0 && 0 <= next.getY() && cross(next, curr) >= 0)
				{
					cnt++;
				}
				if (is_point_online(line.getP1(), line.getP2(), p))
					return true;
			}
			return  cnt % 2 == 1;
		}
	}

	private static Point2D medio(Point2D a, Point2D b)
	{
		return new Point2D.Double(((a.getX() + b.getX()) / 2), ((a.getY() + b.getY()) / 2));
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int caso = 1;
		while(true)
		{
			double[] valores = new double[8];
			for(int i = 0; i < 8; i++)
				valores[i] = sc.nextDouble();
			boolean paila = true;
			for(int i = 0; i < 8; i++)
				if(valores[i] != 0)
					paila = false;
			if(paila)
				return;
			ArrayList <Point2D> puntosT = new ArrayList <Point2D> ();
			for(int i = 0; i < 4; i++)
				puntosT.add(new Point2D.Double(valores[2 * i], valores[2 * i + 1]));
			ArrayList <Point2D> puntos = new ArrayList <Point2D> ();
			for(int i = 0; i < 4; i++)
			{
				puntos.add(puntosT.get(i));
				puntos.add(medio(puntosT.get(i), puntosT.get((i + 1) % 4)));
			}
			Polygon inicial = darPoligono(puntos);
			double area = inicial.area();
			double bestDifference = Double.MAX_VALUE;
			double areaBest = 0;
			for(int i = 0; i < puntos.size(); i++)
			{
				ArrayList <Point2D> temp = new ArrayList <Point2D> ();
				temp.add(puntos.get(i));
				for(int j = (i + (((i & 1) == 0) ? 2 : 1)) % puntos.size(); j != i; j = (j + 1) % puntos.size())
				{
					j = j % puntos.size();
					temp.add(puntos.get(j));
					if(puntos.size() > 2)
					{
						Polygon poly = darPoligono(temp);
						double areaEste = poly.area();
						double areaOtro = area - areaEste;
						if(Math.abs(areaEste - areaOtro) < bestDifference)
						{
							areaBest = areaEste;
							bestDifference = Math.abs(areaEste - areaOtro);
						}
					}
				}
				
			}
			double a1 = areaBest;
			double a2 = area - a1;
			
			System.out.printf("Cake %d: %.3f %.3f\n", caso++, (a1 < a2 ? a1 : a2), (a1 < a2 ? a2 : a1));
		}
	}

	private static Polygon darPoligono(ArrayList<Point2D> puntos) 
	{
		Polygon nuevo = new Polygon(puntos.size());
		for(Point2D punto : puntos)
			nuevo.add(punto);
		nuevo.close();
		return nuevo;
	}
}
