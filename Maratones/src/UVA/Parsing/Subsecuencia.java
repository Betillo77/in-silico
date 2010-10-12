import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Set;


public class Subsecuencia
{
	public static void main(String[] args) throws IOException
	{
		System.setIn(new FileInputStream("iniciales.txt"));
		Hashtable<String, Integer > hash = new Hashtable<String, Integer > (1000);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String linea1;
		while((linea1 = br.readLine()) != null)
		{
			String linea = linea1.substring(0, linea1.indexOf(';'));
			hash.put(linea, 0);
		}
		Set <String> set = hash.keySet();
		String[] arreglo = new String[set.size()];
		set.toArray(arreglo);
		for(int i = 0; i < arreglo.length; i++)
		{
			String linea = arreglo[i];
			for(int j = i + 1; j < arreglo.length; j++)
			{
				String s = arreglo[j];
				if(s.contains(linea))
					System.out.println(linea + " --- " + s);
//				else
//				{
//					if(linea.length() >= 8)
//					{
//						for(int i = 0; i < linea.length() - 7; i++)
//						{
//							String n = linea.substring(i, i + 8);
//							if(s.contains(n))
//							{
//								System.out.println(linea + " --- " + s + " --- " + n);
//								break;
//							}
//						}
//					}
//				}
			}
		}
	}
}
