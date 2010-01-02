import java.util.ArrayList;
import java.util.Scanner;


public class shell
{
	static Scanner sc = new Scanner(System.in);
	static ArrayList <String> usuarios = new ArrayList <String> ();
	
	public static void main(String [] args)
	{
		inicio();
		
	}

	
	public static void inicio()
	{
		System.out.println("\ningrese 1 para login, 2 para crear usuario, y 3 para salir\n");
		System.out.print("$ ");
		int n;
		try
		{
			n = Integer.parseInt(sc.nextLine());
		}
		catch(Exception e)
		{
			inicio();
			return;
		}
		if(n != 1 && n != 2 && n != 3)
		{
			inicio();
			return;
		}
		if(n == 1)
		{
			login();
			return;
		}
		else if(n == 2)
		{
			crear();
			return;
		}
		else
		{
			return;
		}

	}

	private static void login() 
	{
		System.out.println("ingrese el usuario");
		System.out.print("$ ");
		String nuevo = sc.nextLine();
		boolean existe = false;
		String s1 = "";
		for(String s : usuarios)
			if(nuevo.equals(s.substring(0, s.indexOf('@'))))
			{
				s1 = s;
				existe = true;
			}
		if(!existe)
		{
			System.out.println("usuario no existe");
			login();
			return;
		}
		System.out.println("ingrese la clave");
		System.out.print("$ ");
		if(!sc.nextLine().equals(s1.substring(s1.indexOf('@') + 1, s1.lastIndexOf('@'))))
		{
			System.out.println("clave incorrecta");
			login();
			return;
		}
		entrar(Integer.parseInt(s1.substring(s1.lastIndexOf('@') + 1, s1.length())));
	}


	private static void entrar(int num) 
	{
		System.out.println("\n ingreso exitoso al sistema \n use el comando exit para salir \n");
		System.out.print("$ ");
		while(sc.hasNextLine())
		{
			String linea = sc.nextLine();
			if(linea.equals("exit"))
			{
				inicio();
				return;
			}
			Scanner sc2 = new Scanner(linea);
			try
			{
				System.out.println("\nComando: " + sc2.next());
			}
			catch(Exception e)
			{
				continue;
			}
			ArrayList <String> opciones = new ArrayList <String> ();
			ArrayList <String> parametros = new ArrayList <String> ();
			while(sc2.hasNext())
			{
				String actual = sc2.next();
				if(actual.charAt(0) == '-')
				{
					opciones.add(actual.substring(1));
				}
				else
				{
					parametros.add(actual);
				}
			}
			if(opciones.size() != 0)
			{
				String sOpciones = opciones.remove(0);
				for(String actual : opciones)
				{
					sOpciones += " " + actual;
				}
				System.out.println("\nOpciones: " + sOpciones);
			}
			if(parametros.size() != 0)
			{
				String sParametros = parametros.remove(0);
				for(String actual : parametros)
				{
					sParametros += " " + actual;
				}
				System.out.println("\nParametros: " + sParametros);
			}
			if(num == 1)
			{
				System.out.println("\nUsted es administrador puede ejecutar cualquier comando\n"); 
			}
			else
			{
				System.out.println("\nUsted no es administrador, si este comando\nrequiere privilegios administrativos no lo puede ejecutar\n"); 
			}
			System.out.print("$ ");
		}
	}


	private static void crear() 
	{
		System.out.println("ingrese el usuario nuevo");
		System.out.print("$ ");
		String nuevo = sc.nextLine();
		for(String s : usuarios)
			if(nuevo.equals(s.substring(0, s.indexOf('@'))))
			{
				System.out.println("usuario ya existe");
				crear();
				return;
			}
		System.out.println("ingrese la clave");
		System.out.print("$ ");
		nuevo += "@" + sc.nextLine();
		System.out.println("ingrese 1 si es administrado o 0 si no");
		System.out.print("$ ");
		int a = 0;
		try
		{
			a = Integer.parseInt(sc.nextLine());
		}
		catch(Exception e)
		{
			crear();
			return;
		}
		if(a != 0 && a != 1)
		{
			crear();
			return;
		}
		nuevo += "@" + a;
		usuarios.add(nuevo);
		System.out.println("Usuario creado exitosamente");
		inicio();
	}
}
