import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Genetico
{
	int peso [] = {20, 10, 15, 50, 30, 25, 35, 40};
	int utilidad [] = {10, 4, 8, 22, 16, 13, 17, 20};
	int pesoMaximo = 500;
	static final double FACTORMUTACION = 0.1; // factor de mutacion dado en porcentaje
	
	static Genetico g = new Genetico();
	
	// metodo que imprime cuantas soluciones hay con una utilidad mayor o igual a un numero dado. Con 266 (la utilidad mas alta posible, imprime que hay 15 soluciones).
	// nota: se demora 2 minutos (en mi computador) porque revisa todas las posibilidades.
	public void imprimirMejores(int numero)
	{
		int cuenta = 0;
		for(int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++)
		{
			if(validar(i) && calcularAptitud(i) >= numero)
			{
				cuenta++;
			}
		}
		System.out.println(cuenta);
	}
	
	public int azar(int a, int b)
	{
		return (int) (a + Math.round((Math.random() * (double)(b - a))));
	}
	
	public int [][] inicializar()
	{
		int [][] resultado = new int[64][2];
		for(int i = 0; i < 64; i++)
		{
			int posible = 0;
			do
			{
				posible = azar(0, Integer.MAX_VALUE);
			}
			while(!validar(posible));
			resultado[i][0] = posible;
			resultado[i][1] = calcularAptitud(posible);
		}
		return resultado;
	}
	
	public int cruzar(int c1, int c2)
	{
		int hijo;
		do
		{
			hijo = 0;
			for(int i = 0; i < 8; i++)
			{
				int d1 = (c1 >> i * 4) & 15;
				int d2 = (c2 >> i * 4) & 15;
				if(azar(0, 1) == 0)
				{
					hijo |= d1 << i * 4;
				}
				else
				{
					hijo |= d2 << i * 4;
				}
			}
			if(azar(0, 1000) < FACTORMUTACION * 10)
			{
				int a = azar(0, 31);
				if(((hijo >> a) & 1) == 1)
				{
					hijo -= 1 << a;
				}
				else
				{
					hijo += 1 << a;
				}
			}
		}
		while(!validar(hijo));
		return hijo;
	}
	
	public int calcularNumero(int solucion)
	{
		int ap = 0;
		for(int i = 0; i < 8; i++)
		{
			int nActual = solucion >> i * 4;
			nActual &= 15;
			System.out.print(" " +  (char)('a' + i) + ":" + nActual);
		}
		return ap;
	}
	
	public int calcularAptitud(int solucion)
	{
		int ap = 0;
		for(int i = 0; i < 8; i++)
		{
			int nActual = solucion >> i * 4;
			nActual &= 15;
			ap += utilidad[i] * nActual;
		}
		return ap;
	}

	public boolean validar(int solucion)
	{
		int ap = 0;
		for(int i = 0; i < 8; i++)
		{
			int nActual = solucion >> i * 4;
			nActual &= 15;
			ap += peso[i] * nActual;
			if(ap > pesoMaximo)
				return false;
		}
		return ap <= pesoMaximo;
	}
	
	public int [][] seleccion(int [][] pobInicial)
	{
		double media = 0;
		int mejor = 0;
		int mejorE = 0;
		for(int i = 0; i < pobInicial.length; i++)
		{
			media += pobInicial[i][1];
			if(pobInicial[i][1] > mejor)
			{
				mejor = pobInicial[i][1];
				mejorE = pobInicial[i][0];
			}
		}
		media /= pobInicial.length;
		System.out.print("media = " + media + " mejor = " + mejor);
		calcularNumero(mejorE);
		System.out.println();
		int [] nDescendientes = new int[pobInicial.length];
		for(int i = 0; i < pobInicial.length; i++)
		{
			nDescendientes[i] = (int) (pobInicial[i][1] * 64 / media);
		}
		ArrayList <Integer> descendientes = new ArrayList <Integer> (4096);
		for(int i = 0; i < pobInicial.length; i++)
		{
			for(int j = 0; j < nDescendientes[i]; j++)
			{
				int pareja = -1;
				do
				{
					pareja = azar(0, pobInicial.length - 1);
				}
				while(pareja == i);
				descendientes.add(cruzar(pobInicial[i][0], pobInicial[pareja][0]));
			}
		}
		Collections.sort(descendientes, new Comparador()); // este cambio fue hecho para que escogiera a los 64 mejores y no al azar y mejoro mucho
		ArrayList <Integer> elegidos = new ArrayList <Integer> (64);
		for(int i = 0; i < 64; i++)
		{
			elegidos.add(descendientes.get(descendientes.size() - 1 - i)); // este cambio fue hecho para que escogiera a los 64 mejores y no al azar y mejoro mucho
//			int d = azar(0, descendientes.size() - 1); // esta es la opcion al azar
//			elegidos.add(descendientes.remove(d)); // esta es la opcion al azar
		}
		int [][] nuevos = new int[64][2];
		for(int i = 0; i < 64; i++)
		{
			nuevos[i][0] = elegidos.get(i);
			nuevos[i][1] = calcularAptitud(nuevos[i][0]);
		}
		return nuevos;
	}
	
	public static void main(String [] args)
	{
		int [][] primero = g.inicializar();
		for(int i = 0; i < 1000; i++)
		{
			System.out.print("Generacion " + i + ": ");
			primero = g.seleccion(primero);
		}
	}
	
	// Comparador para ordenar la lista de hijos
	class Comparador implements Comparator <Integer>
	{
		@Override
		public int compare(Integer o1, Integer o2) 
		{
			int a = g.calcularAptitud(o1);
			int b = g.calcularAptitud(o2);
			return a - b;
		}
	}
}
