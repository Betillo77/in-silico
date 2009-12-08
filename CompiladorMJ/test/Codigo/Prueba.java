/* Nota: Se prueba todo menos las funciones de biblioteca,
   que ya han sido probadas en los otros ejemplos. */

public class Prueba
{
	int var1;
	int var2;
	int var3;
	boolean var4;

	public static void main(String[] args)
	{
		Prueba p = new Prueba();
		p.iniciar();
	}

	void iniciar()
	{
		int cuenta = 0;
		cuenta = cuenta + probarWhileIfBreakContinue();
		cuenta = cuenta + probarOcultamiento();
		cuenta = cuenta + probarAssigment();
		cuenta = cuenta + probarLiteral();
		cuenta = cuenta + probarOperaciones();
		cuenta = cuenta + Prueba.probarStatic(1, 2, true, 2 == 2, this);
		cuenta = cuenta + padre.probarHerencia();
		Library.println(Library.itos(cuenta));
	}
	
	static int probarStatic(int a, int b, boolean c, boolean d, Prueba e)
	{
		int cuenta = 0;
		cuenta = cuenta + a;
		cuenta = cuenta + b;
		if(c)
			cuenta = cuenta + e.var1;
		else
			cuenta = cuenta * e.var1;
		if(d)
			cuenta = cuenta + 32;
		else
			cuenta = cuenta * e.var1;
		return cuenta;
			
	}

	int probarWhileIfBreakContinue()
	{
		int cuenta = 0;
		int i = 0;
		while(i < 10)
		{
			int j = 0;
			if(i == 6)
			{
				i = i + 1;
				continue;
			}
			else
				cuenta = cuenta + i;
			while(j < 100)
			{
				if(j == 6)
				{
					j = j + 2;
					continue;
				}
				else
					cuenta = cuenta + 2 * i;
				if(j == 99)
					break;
				else
					j = j + 2;
			}
			if(i == 8)
				break;
			else
				i = i + 1;
		}
		return cuenta + 2 + i * 3 / 2;
	}

	int probarOcultamiento()
	{
		int cuenta = 0;
		int i = 2;
		cuenta = cuenta + i;
		{
			int j = 10;
			cuenta = cuenta + j;
			if(true)
			{
				int k = 120;
				cuenta = cuenta + k;
			}
			cuenta = cuenta + j; 
		}
		cuenta = cuenta + i;
		return cuenta;
	}

	int probarAssigment()
	{
		Prueba a = new Prueba();
		int cuenta = 0;
		int[] s = new int[23];
		a.var4 = false;
		if(a.var4)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		a.var4 = true;
		if(a.var4)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		a.var1 = 23;
		a.var2 = 32;
		s[22] = 2;
		s[1] = 32;
		a.var3 = s[22];
		this.var1 = 2;
		return cuenta + a.var1 / a.var3 * a.var2 + s[1] + s.length + this.var1 + var1;
	}

	int probarLiteral()
	{
		int cuenta = 0;
		String a = "El valor de este programa en MJ fue: 23044, en Java es:";
		boolean c = true;
		Library.println(a);
		if(c)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		c = false;
		if(c)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		return cuenta + a.length();
	}

	int probarOperaciones()
	{
		int cuenta = -2;
		boolean prueba = !true;
		cuenta = cuenta % 11 * 4 + 5 / 6 - 3;
		if(prueba)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		if(2 < 3)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		if(2 > 3)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		if(2 <= 3)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		if(2 >= 3)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		if(2 == 3)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		if(2 != 3)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		if(2 != 3 && 2 == 3)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		if(2 != 3 && 2 <= 3)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		if(2 == 3 && 2 != 3)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		if(2 < 3 && 2 == 3)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		if(2 != 3 || 2 == 3)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		if(2 != 3 || 2 <= 3)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		if(2 == 3 || 2 != 3)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		if(2 < 3 || 2 == 3)
			cuenta = cuenta + 1;
		else
			cuenta = cuenta + 2;
		return cuenta;
	}
}

class padre
{
	static int probarHerencia()
	{
		padre p1 = new padre();
		padre p2 = new hijo();
		padre p3 = new nieto();
		hijo h1 = new hijo();
		hijo h2 = new nieto();
		nieto n1 = new nieto();
		int cuenta = 0;
		p1.datos();
		p2.datos();
		p3.datos();
		h1.datos();
		h2.datos();
		n1.datos();
		cuenta = cuenta + (p1.nombre + " " + p1.apellido + "  el hijo " + p2.nombre + " " + p2.apellido).length();
		cuenta = cuenta + ("Nietos: " + p3.nombre + " " + h1.nombre + " " + h2.nombre + " " + n1.nombre).length();
		p1.labora(300);
		p2.labora(200);
		p3.labora(2);
		h1.labora(22);
		h2.labora(23);
		n1.labora(23);
		h1.dararray()[1] = new hijo[2];
		h1.h = h1;
		h1.h.h.h.h.h.h.dararray()[1][1]=new hijo();
		h1.array[1][1].novia = "prueba";
		cuenta = cuenta + (h1.dararray()[1][1].minovia()).length();
		cuenta = cuenta + (h2.minovia() + n1.minovia()).length(); 
		cuenta = cuenta + n1.universidad("Pereira").length();
		return p1.ingresos + p2.ingresos + p3.ingresos + h1.ingresos + h2.ingresos + n1.ingresos + cuenta;
	}

	String nombre;
	String apellido;
	int edad;
	int ingresos;

	void datos()
	{
		nombre="fulano";
		apellido = "de tal";
		edad = 30;
	}

	void labora(int sueldo)
	{
		ingresos = sueldo * edad;
	}

	int ganacias(int salario)
	{
		labora (salario);
		return ingresos;
	}
}

class hijo extends padre
{
	String novia;
	hijo h;
	hijo [][] array;
	void datos()
	{
		nombre = "hijo de fulano";
		apellido = "de tal";
		novia = "ella";
		edad = 19;
		array = new hijo[2][2];
	}

	void labora(int d)
	{
		if (edad > 18)
			ingresos = d * edad;
		else
			ingresos = 0;
	}

	String minovia()
	{
		h = this;
		h.h.h.h.h.h.h.h.h.h.edad=this.edad + 1;
		return novia;
	}

	hijo[][] dararray()
	{
		return array;
	}
}

class nieto extends hijo
{
	String amante;
	void datos()
	{
		nombre = "nieto de fulano";
		apellido = "de tal";
		novia = "ella";
		amante = "la otra";
		edad = 18;
	}

	void labora(int d)
	{
		if (edad > 18)
			ingresos = d * edad;
		else
			ingresos = 2000;
	}

	String minovia()
	{
		h = this;
		h.h.h.h.h.h.h.h.h.h.edad=this.edad + 1;
		
		return novia + " " + amante;
	}

	String universidad(String ciudad)
	{
		return "Universidad Tecnologica de " + ciudad;
	}
}

class Library
{
	static void println(String a){System.out.println(a);}
	static String itos(int a){return a + "";}
}
	
