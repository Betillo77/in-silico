package UVA;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Random;

public class prueba
{
	
	public static void main(String [] args) throws FileNotFoundException
	{
		System.setOut(new PrintStream("a.txt"));
		Random r = new Random();
		for(int i = 0; i < 10; i++)
		{
			System.out.println("1000 ");
			for(int j = 0; j < 1000; j++)
			{
				System.out.print((r.nextInt(1000000) + 1) + " ");
				System.out.print((r.nextInt(1000000) + 1) + " ");
			}
			System.out.println();
		}
	}

}
