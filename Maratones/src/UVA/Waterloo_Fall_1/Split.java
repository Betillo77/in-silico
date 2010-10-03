import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;


public class Split
{
	private static Hashtable <Integer, Integer> generarSumas(List <Integer> lista)
	{
		Hashtable <Integer, Integer> mapa = new Hashtable <Integer, Integer> (1 << lista.size());
		Hashtable <Integer, Integer> mapaNuevo = new Hashtable <Integer, Integer> (1 << lista.size());
		int numMax = 0;
		int numTotal = 0;
		for(int actual : lista)
		{
			if(mapa.isEmpty())
			{
				mapa.put(actual, actual);
				mapa.put(0, 0);
				mapa.put(-actual, actual);
				continue;
			}
			numMax = Math.max(numMax, mapa.size());
			numTotal += mapa.size();
			mapaNuevo.clear();
			for(Entry <Integer, Integer> entrada : mapa.entrySet())
			{
				int suma = entrada.getKey() + actual;
				int resta = entrada.getKey() - actual;
				if(mapaNuevo.containsKey(suma))
				{
						mapaNuevo.put(suma, Math.max(mapaNuevo.get(suma), entrada.getValue() + actual));
				}
				else
				{
						mapaNuevo.put(suma, entrada.getValue() + actual);
				}
				if(mapaNuevo.containsKey(resta))
				{
						if(resta >= 0)
						{
							mapaNuevo.put(resta, Math.max(mapaNuevo.get(resta), entrada.getValue() + actual));
						}
				}
				else
				{
						if(resta >= 0)
						{
							mapaNuevo.put(resta, entrada.getValue() + actual);
						}
				}
				if(mapaNuevo.containsKey(entrada.getKey()))
				{
						mapaNuevo.put(entrada.getKey(), Math.max(mapaNuevo.get(entrada.getKey()), entrada.getValue()));
				}
				else
				{
						mapaNuevo.put(entrada.getKey(), entrada.getValue());
				}
			}
			Hashtable <Integer, Integer> temp = mapa;
			mapa = mapaNuevo;
			mapaNuevo = temp;
		}
		return mapa;
	}
	
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true)
		{
			int n = Integer.parseInt(br.readLine());
			if(n == 0)
				return;
			ArrayList <Integer> numeros = new ArrayList <Integer> ();
			int acumTotal = 0;
			for(int i = 0; i < n; i++)
			{
				int valorNuevo = Integer.parseInt(br.readLine());
				numeros.add(valorNuevo);
				acumTotal += valorNuevo;
			}
			Hashtable <Integer, Integer> mapaUno = generarSumas(numeros.subList(0, n / 2));
			Hashtable <Integer, Integer> mapaDos = generarSumas(numeros.subList(n / 2, n));
			int mejor = -1;
			if(mapaUno.containsKey(0))
				mejor = Math.max(mejor, mapaUno.get(0));
			if(mapaDos.containsKey(0))
				mejor = Math.max(mejor, mapaDos.get(0));
			for(Entry <Integer, Integer> entrada : mapaUno.entrySet())
			{
				if(mapaDos.containsKey(entrada.getKey()))
				{
					mejor = Math.max(mejor, entrada.getValue() + mapaDos.get(entrada.getKey()));
				}
			}
			System.out.println((acumTotal - mejor));
		}
	}
}