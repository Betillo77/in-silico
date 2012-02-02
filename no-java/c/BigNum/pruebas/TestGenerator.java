import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Random;


public class TestGenerator 
{
	static Random r = new Random();
	
	static BigInteger generarBigInteger()
	{
		int tamA = r.nextInt(256) + 1;
		byte[] bytes = new byte[tamA];
		r.nextBytes(bytes);
		return new BigInteger(1, bytes);
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		System.setOut(new PrintStream("testBig.in"));
		for(int i = 0; i < 100000; i++)
		{
			BigInteger a = generarBigInteger();
			BigInteger b = generarBigInteger();
			int numero = r.nextInt(5);
			char signo = ' ';
			switch(numero)
			{
				case 0: signo = '+'; break;
				case 1: signo = '-'; break;
				case 2: signo = '*'; break;
				case 3: signo = '/'; break;
				case 4: signo = '%'; break;
			}
			signo = r.nextInt(2) == 0 ? '/' : '%';
			BigInteger menor = a.min(b);
			BigInteger mayor = a.max(b);
			if((signo == '/' || signo == '%') && (menor.equals(BigInteger.ZERO) || menor.equals(BigInteger.ONE)))
			{
				i--;
				continue;
			}
			System.out.println(signo + " " + mayor + " " + menor);
		}
	}
}