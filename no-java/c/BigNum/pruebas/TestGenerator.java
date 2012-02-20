import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;


public class TestGenerator 
{
	static Random r = new Random();
	
	static BigInteger generarBigInteger(int n)
	{
		int tamA = n;
		byte[] bytes = new byte[tamA];
		r.nextBytes(bytes);
		return new BigInteger(1, bytes);
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		System.setOut(new PrintStream("testBig.in"));
		for(int i = 0; i < 100; i++)
		{
			BigInteger a = generarBigInteger(128);
			BigInteger b = generarBigInteger(128);
			int numero = r.nextInt(6);
			char signo = ' ';
			switch(numero)
			{
				case 0: signo = '+'; break;
				case 1: signo = '-'; break;
				case 2: signo = '*'; break;
				case 3: signo = '/'; break;
				case 4: signo = '%'; break;
				case 5: signo = '^'; break;
			}
			signo = '^';
			BigInteger menor = a.min(b);
			BigInteger mayor = a.max(b);
			if(signo == '^')
			{
				BigInteger c = generarBigInteger(128);
				BigInteger[] tres = new BigInteger[] {a, b, c};
				Arrays.sort(tres);
				if(tres[2].equals(BigInteger.ZERO) || tres[2].equals(BigInteger.ONE) || tres[1].equals(tres[2]))
				{
					i--;
					continue;
				}
				if(r.nextBoolean())
				{
					BigInteger temp = tres[0];
					tres[0] = tres[1];
					tres[1] = temp;
				}
				System.out.println(signo + " " + tres[0] + " " + tres[1] + " " + tres[2]);
				continue;
			}
			else if((signo == '/' || signo == '%') && (menor.equals(BigInteger.ZERO) || menor.equals(BigInteger.ONE)))
			{
				i--;
				continue;
			}
			System.out.println(signo + " " + mayor + " " + menor);
		}
	}
}
