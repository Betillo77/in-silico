import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Set;
import java.util.Map.Entry;


public class Iniciales
{
	public static void main(String[] args) throws IOException
	{
		System.setIn(new FileInputStream("iniciales.txt"));
		System.setOut(new PrintStream("salida.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String linea;
		Hashtable <String, ArrayList <Integer> > hash = new Hashtable <String, ArrayList <Integer> > (1000);
		while((linea = br.readLine()) != null)
		{
			if(linea.contains("<br/>"))
			{
				String[] componentes = linea.split("-");
				if(componentes.length > 3)
				{
					System.out.println("");
				}
				int numero = Integer.parseInt(componentes[0].replace("-","").replace(" ", ""));
				if(numero == 254)
					System.out.println("");
				String[] tipos = componentes[componentes.length - 1].substring(1).split(", ");
				for(int i = 0; i < tipos.length; i++)
				{
					tipos[i] = tipos[i].replace(".", "").replace("<br/>", "").toLowerCase();
				}
				for(String s : tipos)
				{
					if(s.contains("(") && s.contains(")"))
					{
						s = s.substring(0, s.indexOf('(')) + s.substring(s.indexOf(')') + 1, s.length());
						if(s.equals(""))
							s = "NADA";
					}
					if(hash.containsKey(s))
					{
						hash.get(s).add(numero);
					}
					else
					{
						ArrayList  <Integer> nuevo = new ArrayList <Integer> ();
						nuevo.add(numero);
						hash.put(s, nuevo);
					}
				}
			}
		}
		Set<Entry <String, ArrayList <Integer> > > s = hash.entrySet();
		ArrayList < Entry <String, ArrayList <Integer> > > a = new ArrayList<Entry < String, ArrayList < Integer > > > ();
		for(Entry<String, ArrayList <Integer> > actual : s)
		{
			a.add(actual);
		}
		Collections.sort(a, new Comparador());
		for(Entry<String, ArrayList <Integer> > actual : a)
		{
			String tipo = actual.getKey();
			ArrayList <Integer> problemas = actual.getValue();
			System.out.print(tipo + ";" + problemas.size()+";");
			for(int i = 0; i < problemas.size() - 1; i++)
			{
				System.out.print(problemas.get(i) + " ");
			}
			System.out.println(problemas.get(problemas.size() - 1));
		}
	}
	
	static class Comparador implements Comparator <Entry <String, ArrayList <Integer> > >
	{
		@Override
		public int compare(Entry <String, ArrayList <Integer> > o1, Entry< String, ArrayList< Integer> > o2) 
		{
			return new Integer(o2.getValue().size()).compareTo(o1.getValue().size());
		}
	}
}
