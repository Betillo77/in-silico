package experimentacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

import Colonia.Hormigas;
import Colonia.Lectura;


public class pruebas {

	
	@SuppressWarnings("unchecked")
	public static void main(String args[]) throws FileNotFoundException
	{
		System.setOut(new PrintStream(new File("s.txt")));
		for(int a = 0; a < 11; a++)
		{
			for(int b = 0; b < 11; b++)
			{
				Hormigas atomicas = new Hormigas();
				ArrayList <Object> resultado = atomicas.solucionar(Lectura.leer(), 1000, 1, 3500, 1000, a, b, 0.001, 0.001);
				int mejor = ((Double)resultado.get(0)).intValue();
				ArrayList <Integer> ruta = (ArrayList<Integer>) resultado.get(1);
				System.out.println(a + "; " + b + "; " + mejor);
				System.out.println(ruta);
			}
		}
	}
	
}
