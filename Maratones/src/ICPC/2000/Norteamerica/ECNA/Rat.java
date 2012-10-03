import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Rat
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
		
		public long nextLong()
		{
			return Long.parseLong(next());
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
	}
	
	static class Rational implements Comparable <Rational> 
	{
		static Rational zero = new Rational(0, 1);

		private int num;
		private int den;

		public Rational(int numerator, int denominator)
		{
			int g = gcd(numerator, denominator);
			num = numerator   / g;
			den = denominator / g;
			if (den < 0) { den = -den; num = -num; }
		}

		public int numerator()   { return num; }
		public int denominator() { return den; }

		public double toDouble()
		{
			return (double) num / den;
		}

		public String toString()
		{ 
			if (den == 1) return num + "";
			else          return num + "/" + den;
		}

		public int compareTo(Rational b) 
		{
			Rational a = this;
			int lhs = a.num * b.den;
			int rhs = a.den * b.num;
			if (lhs < rhs) return -1;
			if (lhs > rhs) return +1;
			return 0;
		}

		public boolean equals(Object y)
		{
			if (y == null) return false;
			if (y.getClass() != this.getClass()) return false;
			Rational b = (Rational) y;
			return compareTo(b) == 0;
		}


		public int hashCode() 
		{
			return this.toString().hashCode();
		}

		public static Rational mediant(Rational r, Rational s) 
		{
			return new Rational(r.num + s.num, r.den + s.den);
		}

		private static int gcd(int m, int n) 
		{
			if (m < 0) m = -m;
			if (n < 0) n = -n;
			if (0 == n) return m;
			else return gcd(n, m % n);
		}

		public static int lcm(int m, int n) 
		{
			if (m < 0) m = -m;
			if (n < 0) n = -n;
			return m * (n / gcd(m, n));
		}

		public Rational times(Rational b) 
		{
			Rational a = this;
			Rational c = new Rational(a.num, b.den);
			Rational d = new Rational(b.num, a.den);
			return new Rational(c.num * d.num, c.den * d.den);
		}

		public Rational plus(Rational b)
		{
			Rational a = this;

			if (a.compareTo(zero) == 0) return b;
			if (b.compareTo(zero) == 0) return a;
			int f = gcd(a.num, b.num);
			int g = gcd(a.den, b.den);

			Rational s = new Rational((a.num / f) * (b.den / g) + (b.num / f) * (a.den / g),
					lcm(a.den, b.den));
			s.num *= f;
			return s;
		}

		public Rational negate() 
		{
			return new Rational(-num, den);
		}

		public Rational minus(Rational b) 
		{
			Rational a = this;
			return a.plus(b.negate());
		}


		public Rational reciprocal() { return new Rational(den, num);  }

		public Rational divides(Rational b) 
		{
			Rational a = this;
			return a.times(b.reciprocal());
		}

		public Rational abs() 
		{
			if(num < 0)
				return negate();
			else
				return this;
		}
	}

	static class Matrix 
	{
		Rational [][] data;
		int rows, cols;

		Matrix(int rows, int cols) 
		{
			this.rows = rows; 
			this.cols = cols;
			data = new Rational[rows][cols];
			for(int i = 0; i < rows; i++)
				for(int j = 0; j < cols; j++)
					data[i][j] = Rational.zero;
		}

		void clonar(Matrix a) 
		{
			Matrix nueva = new Matrix(rows, cols);
			for(int i = 0; i < rows; i++)
				for(int j = 0; j < cols; j++)
					nueva.data[i][j] = data[i][j];
		}

		void swapRow(int row1, int row2) 
		{
			Rational[] tmp = data[row2];
			data[row2] = data[row1];
			data[row1] = tmp;
		}

		void multRow(int row, Rational coeff) 
		{
			for(int j = 0; j < cols; j++) 
				data[row][j] = data[row][j].times(coeff);
		}

		void addRows(int destRow, int srcRow, Rational factor) 
		{
			for(int j = 0; j < cols; j++) 
				data[destRow][j] = data[destRow][j].plus(data[srcRow][j].times(factor));
		}

		void printMat() 
		{
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++)
					System.out.print(data[i][j] + " ");
				System.out.println();
			}
		}
	}

	static void gaussianElim(Matrix m) 
	{
		int rows = m.rows;
		for(int i = 0; i < rows; i++) 
		{
			int maxrow = i;
			Rational maxval = m.data[i][i];
			for(int k = i + 1; k < rows; k++) 
			{
				if (maxval.abs().compareTo(m.data[k][i].abs()) < 0)
				{
					maxval = m.data[k][i];
					maxrow = k;
				}
			}
			if(maxval.compareTo(Rational.zero) == 0)
				return;
			m.swapRow(maxrow, i);
			m.multRow(i, maxval.reciprocal());
			for(int k = 0; k < rows; k++) 
				if (k != i) 
					m.addRows(k, i, m.data[k][i].negate());
		}
	}	
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		boolean empezo = false;
		while(true)
		{
			int m = sc.nextInt();
			int n = sc.nextInt();
			if(m == 0 && n == 0)
				return;
			int[] coefs = new int[m + n];
			for(int i = 0; i < m + n; i++)
				coefs[i] = sc.nextInt();
			Matrix mat = new Matrix(n, n + 1);
			for(int i = 0; i < n; i++)
			{
				for(int j = 0; j < n; j++)
				{
					int index = m + n - 1 - i - j;
					if(index >= 0)
						mat.data[i][j] = new Rational(coefs[index], 1);
				}
			}
			mat.data[0][n] = new Rational(1, 1);
			gaussianElim(mat);
			Rational[] q = new Rational[n];
			for(int i = 0; i < n; i++)
				q[i] = mat.data[i][n];
			Rational[] p = new Rational[m];
			for(int i = 0; i < m; i++)
				p[i] = Rational.zero;
			for(int i = 0; i < m; i++)
			{
				for(int j = Math.min(n - 1, i); j >= 0; j--)
				{
					int index = i - j;
					p[i] = p[i].plus(q[j].times(new Rational(coefs[index], 1)));
				}
			}
			if(empezo)
				System.out.println();
			empezo = true;
			imprimirPoly(p);
			imprimirPoly(q);
		}
	}

	private static void imprimirPoly(Rational[] p) 
	{
		boolean zero = true;
		for(int i = 0; i < p.length; i++)
			zero = zero && p[i].equals(Rational.zero);
		if(zero)
			System.out.println("(0,0)");
		else
		{
			boolean empezo = false;
			for(int i = 0; i < p.length; i++)
			{
				if(!p[i].equals(Rational.zero))
				{
					if(empezo)
						System.out.print(" ");
					empezo = true;
					System.out.print("(" + p[i] + "," + i + ")");
				}
			}
			System.out.println();
		}
	}
}
