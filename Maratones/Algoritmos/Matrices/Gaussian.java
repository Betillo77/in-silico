import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;


public class Gaussian
{
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
}
