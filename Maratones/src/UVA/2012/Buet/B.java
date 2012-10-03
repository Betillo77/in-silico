import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;


public class B
{
	static class SuperScanner
    {
            char[] buffer;
            InputStreamReader isr;
            int p;
            int start;
            
            public SuperScanner()
            {
                    buffer = new char[6000001];
                    isr = new InputStreamReader(System.in);         
                    read(0);
            }
            
            void read(int init)
            {
                    try
                    {
                            int tam;
                            if((tam = isr.read(buffer, init, buffer.length - init)) < buffer.length - init)
                            {
                                    if(tam < 0)
                                            tam = 0;
                                    buffer[tam] = '\0';
                            }
                            else
                            	throw(new RuntimeException());
                    }
                    catch(Exception e)
                    {
                            throw(new RuntimeException());
                    }
            }
            
            public void readNext()
            {
                    int tam = buffer.length;
                    int pos = p;
                    while(pos < tam && buffer[pos] <= ' ')
                            pos++;
                    if(pos == tam)
                    {
                            read(0);
                            readNext();
                            return;
                    }
                    start = pos;
                    while(pos < tam && buffer[pos] > ' ')
                            pos++;
                    if(pos == tam)
                    {
                            System.arraycopy(buffer, start, buffer, 0, buffer.length - start);
                            read(buffer.length - start);
                            p = start;
                            readNext();
                            return;
                    }
                    p = pos;
            }
            
            public String next()
            {
                    readNext();
                    return new String(buffer, start, p - start);
            }
            
            
            public long nextLong()
    {
                    readNext();
        int d = start;
        int pos = p;
        long r = 0;
        boolean n = buffer[d] == '-';
        if(n)
            d++;
        r -= buffer[d++] - '0';
        while (d < pos && (r = (r << 1) + (r << 3)) <= 0)
                r -= buffer[d++] - '0';
        return n ? r : -r;
    }
            
            public int nextInt()
            {
                    return (int) nextLong();
            }
            
            public double nextDouble()
            {
                    return Double.parseDouble(next());
            }
            
            public String nextLine()
            {
                    int tam = buffer.length;
                    int pos = p;
                    while(pos < tam && buffer[pos] != '\n')
                            pos++;
                    if(pos == tam)
                    {
                            read(0);
                            return nextLine();
                    }
                    start = ++pos;
                    while(pos < tam && buffer[pos] != '\n')
                            pos++;
                    if(pos >= tam)
                    {
                            System.arraycopy(buffer, start, buffer, 0, buffer.length - start);
                            read(buffer.length - start);
                            return nextLine();
                    }
                    p = pos;
                    return new String(buffer, start, p - start);
            }
            
            public boolean EOF()
            {
                    int tam = buffer.length;
                    int pos = p;
                    while(pos < tam && buffer[pos] <= ' ')
                            if(buffer[pos++] == '\0')
                                    return true;
                    pos++;
                    if(pos >= tam)
                    {
                            if(p == 0)
                                    return false;
                            System.arraycopy(buffer, p, buffer, 0, buffer.length - p);
                            read(buffer.length - p);
                            p = 0;
                            return EOF();
                    }
                    return false;
            }
    }
    
    static class SuperWriter
    {
            char[] buffer;
            OutputStreamWriter os;
            int pos;
            
            public SuperWriter()
            {
                    buffer = new char[6000001];
                    os = new OutputStreamWriter(System.out);
                    pos = 0;
            }
            
            public void println(String s)
            {
                    print(s);
                    print("\n");
            }
            
            char[] buf = new char[19];
            
            public void writeLong(long i)
            {
                int size = (i < 0) ? stringSize(-i) + 1 : stringSize(i);
                char[] b = toString(i, size);
                for(int j = 0; j < size; j++)
                	buffer[pos++] = b[j];
                buffer[pos++] = '\n';
            }
            public char[] toString(long i, int size) 
            {
                getChars(i, size, buf);
                return buf;
            }

            final static char[] digits = {
                '0' , '1' , '2' , '3' , '4' , '5' ,
                '6' , '7' , '8' , '9' , 'a' , 'b' ,
                'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
                'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
                'o' , 'p' , 'q' , 'r' , 's' , 't' ,
                'u' , 'v' , 'w' , 'x' , 'y' , 'z'
            };
            final static char [] DigitTens = {
                '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
                '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
                '2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
                '3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
                '4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
                '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
                '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
                '7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
                '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
                '9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
                } ;

            final static char [] DigitOnes = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                } ;
            
            /**
             * Places characters representing the integer i into the
             * character array buf. The characters are placed into
             * the buffer backwards starting with the least significant
             * digit at the specified index (exclusive), and working
             * backwards from there.
             *
             * Will fail if i == Long.MIN_VALUE
             */
            void getChars(long i, int index, char[] buf) {
                long q;
                int r;
                int charPos = index;
                char sign = 0;

                if (i < 0) {
                    sign = '-';
                    i = -i;
                }

                // Get 2 digits/iteration using longs until quotient fits into an int
                while (i > Integer.MAX_VALUE) {
                    q = i / 100;
                    // really: r = i - (q * 100);
                    r = (int)(i - ((q << 6) + (q << 5) + (q << 2)));
                    i = q;
                    buf[--charPos] = DigitOnes[r];
                    buf[--charPos] = DigitTens[r];
                }

                // Get 2 digits/iteration using ints
                int q2;
                int i2 = (int)i;
                while (i2 >= 65536) {
                    q2 = i2 / 100;
                    // really: r = i2 - (q * 100);
                    r = i2 - ((q2 << 6) + (q2 << 5) + (q2 << 2));
                    i2 = q2;
                    buf[--charPos] = DigitOnes[r];
                    buf[--charPos] = DigitTens[r];
                }

                // Fall thru to fast mode for smaller numbers
                // assert(i2 <= 65536, i2);
                for (;;) {
                    q2 = (i2 * 52429) >>> (16+3);
                    r = i2 - ((q2 << 3) + (q2 << 1));  // r = i2-(q2*10) ...
                    buf[--charPos] = digits[r];
                    i2 = q2;
                    if (i2 == 0) break;
                }
                if (sign != 0) {
                    buf[--charPos] = sign;
                }
            }

            // Requires positive x
            static int stringSize(long x) {
                long p = 10;
                for (int i=1; i<19; i++) {
                    if (x < p)
                        return i;
                    p = (p << 3) + (p << 1);
                }
                return 19;
            }
            
            public void print(String s)
            {
                    int tam = s.length();
                    if(pos + tam > buffer.length)
                            tam = buffer.length - pos;
                    s.getChars(0, tam, buffer, pos);
                    pos += tam;
                    if(pos == buffer.length)
                    {
                            try
                            {
                                    os.write(buffer);
                                    pos = 0;
                                    print(s.substring(tam));
                            }
                            catch(Exception e)
                            {
                                    throw(new RuntimeException());
                            }
                    }
            }

            public void flush() 
            {
                    try 
                    {
                            os.write(buffer, 0, pos);
                            pos = 0;
                            os.flush();
                    }
                    catch (IOException e) 
                    {
                            throw(new RuntimeException());
                    }
            }
    }
	
	static long calcular(int[] actual)
	{
		long cuantos = 1;
		for(int i = 0; i < factores.length; i++)
		{
			int diff = exponentes[i] - actual[i];
			if(diff > 0)
				cuantos *= (factores[i] - 1);
			diff--;
			while(diff > 0)
			{
				cuantos *= factores[i];
				diff--;
			}
		}
		return cuantos;
	}
	
	static long numero(int[] actual)
	{
		long cuanto = 1;
		for(int i = 0; i < factores.length; i++)
		{
			int diff = actual[i];
			while(diff > 0)
			{
				cuanto *= factores[i];
				diff--;
			}
		}
		return cuanto;
	}
	
	static Object[] factorizar(long numero)
	{
		ArrayList <Long> factores = new ArrayList <Long> ();
		int limite = (int) Math.min(Math.ceil(Math.sqrt(numero)), numero - 1);
		for(int i = 2; i <= limite; i++)
		{
			while(numero % i == 0)
			{
				factores.add((long) i);
				numero /= i;
				limite = (int) Math.min(Math.ceil(Math.sqrt(numero)), numero - 1);
			}
		}
		if(numero != 1)
			factores.add(numero);
		Collections.sort(factores);
		long anterior = 1;
		int cuentaActual = 0;
		ArrayList <Long> a = new ArrayList <Long> ();
		ArrayList <Integer> b = new ArrayList <Integer> ();
		for(long factor : factores)
		{
			if(factor == anterior)
				cuentaActual++;
			else
			{
				if(anterior != 1)
				{
					a.add(anterior);
					b.add(cuentaActual);
				}
				anterior = factor;
				cuentaActual = 1;
			}
		}
		a.add(anterior);
		b.add(cuentaActual);
		Object[] r = new Object[2];
		long[] fs = new long[a.size()];
		int[] es = new int[b.size()];
		for(int i = 0; i < a.size(); i++)
		{
			fs[i] = a.get(i);
			es[i] = b.get(i);
		}
		r[0] = fs;
		r[1] = es;
		return r;
	}
	
	static long[] factores;
	static int[] exponentes;
	
	static int tamDivisores;
	static long[] divisores = new long[10000];
	static long[] nDivisores = new long[10000];
	
	static void computar(int[] actual, int n)
	{
		if(n == actual.length)
		{
			divisores[tamDivisores] = numero(actual);
			nDivisores[tamDivisores++] = calcular(actual);
			return;
		}
		for(int i = 0; i <= exponentes[n]; i++)
		{
			actual[n] = i;
			computar(actual, n + 1);
		}
	}

	
    private static int med3(long x[][], int a, int b, int c) {
        return (x[a][0] < x[b][0] ?
                (x[b][0] < x[c][0] ? b : x[a][0] < x[c][0] ? c : a) :
                (x[b][0] > x[c][0] ? b : x[a][0] > x[c][0] ? c : a));
    }
    
    private static void swap(long[][] x, int a, int b) {
        long[] t = x[a];
        x[a] = x[b];
        x[b] = t;
    }
    
    private static void vecswap(long[][] x, int a, int b, int n) {
        for (int i=0; i<n; i++, a++, b++)
            swap(x, a, b);
    }
    
    static void sort1(long[][] x, int off, int len) {
        if (len < 7) {
            for (int i=off; i<len+off; i++)
                for (int j=i; j>off && x[j-1][0]>x[j][0]; j--)
                    swap(x, j, j-1);
            return;
        }
        // Choose a partition element, v
        int m = off + (len >> 1);       // Small arrays, middle element
        if (len > 7) {
            int l = off;
            int n = off + len - 1;
            if (len > 40) {        // Big arrays, pseudomedian of 9
                int s = len/8;
                l = med3(x, l,     l+s, l+2*s);
                m = med3(x, m-s,   m,   m+s);
                n = med3(x, n-2*s, n-s, n);
            }
            m = med3(x, l, m, n); // Mid-size, med of 3
        }
        long v = x[m][0];
        // Establish Invariant: v* (<v)* (>v)* v*
        int a = off, b = a, c = off + len - 1, d = c;
        while(true) {
            while (b <= c && x[b][0] <= v) {
                if (x[b][0] == v)
                    swap(x, a++, b);
                b++;
            }
            while (c >= b && x[c][0] >= v) {
                if (x[c][0] == v)
                    swap(x, c, d--);
                c--;
            }
            if (b > c)
                break;
            swap(x, b++, c--);
        }

        // Swap partition elements back to middle
        int s, n = off + len;
        s = Math.min(a-off, b-a  );  vecswap(x, off, b-s, s);
        s = Math.min(d-c,   n-d-1);  vecswap(x, b,   n-s, s);

        // Recursively sort non-partition-elements
        if ((s = b-a) > 1)
            sort1(x, off, s);
        if ((s = d-c) > 1)
            sort1(x, n-s, s);
    }

	public static void main(String[] args) throws FileNotFoundException
	{
		SuperScanner sc = new SuperScanner();
		SuperWriter sw = new SuperWriter();
		int casos = sc.nextInt();
		final long[] ans = new long[50000];
		final long[] sumas = new long[10000];
		final long[][] divs = new long[10000][2];
		final long[][] respuestas = new long[50000][2];
		for(int i = 0; i < casos; i++)
		{
			sw.println("Case " + (i + 1));
			tamDivisores = 0;
			Object[] temp = factorizar(sc.nextLong());
			factores = (long[]) temp[0];
			exponentes = (int[]) temp[1];
			computar(exponentes.clone(), 0);
			int limite = tamDivisores;
			for(int j = 0; j < limite; j++)
			{
				divs[j][0] = divisores[j];
				divs[j][1] = nDivisores[j];
			}
			sort1(divs, 0, limite);
			sumas[0] = divs[0][1];
			for(int j = 1; j < limite; j++)
				sumas[j] = sumas[j - 1] + divs[j][1];
			int q = sc.nextInt();
			for(int j = 0; j < q; j++)
			{
				long x = sc.nextLong();
				respuestas[j][0] = x;
				respuestas[j][1] = j;
			}
			sort1(respuestas, 0, q);
			int actual = 0;
			while(actual < q && divs[0][0] > respuestas[actual][0])
			{
				ans[(int) respuestas[actual][1]] = 0;
				actual++;
			}
			for(int j = 1; j < limite; j++)
			{
				while(actual < q && divs[j][0] > respuestas[actual][0])
				{
					ans[(int) respuestas[actual][1]] = sumas[j - 1];
					actual++;
				}
			}
			while(actual < q)
				ans[(int) respuestas[actual++][1]] = sumas[limite - 1];
			for(int j = 0; j < q; j++)
				sw.writeLong(ans[j]);
		}
		sw.flush();
	}
}