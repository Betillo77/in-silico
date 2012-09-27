import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


public class you
{
	static class Scanner
    {
            BufferedReader br;
            StringTokenizer st;
            
            public Scanner()
            {
                    br = new BufferedReader(new InputStreamReader(System.in));
            }
            
            public String next()
            {

                    while(st == null || !st.hasMoreTokens())
                    {
                            try { st = new StringTokenizer(br.readLine()); }
                            catch(Exception e) { throw new RuntimeException(); }
                    }
                    return st.nextToken();
            }

            public int nextInt()
            {
                    return Integer.parseInt(next());
            }
            
            public double nextDouble()
            {
                    return Double.parseDouble(next());
            }
            
            public String nextLine()
            {
                    st = null;
                    try { return br.readLine(); }
                    catch(Exception e) { throw new RuntimeException(); }
            }
            
            public boolean endLine()
            {
                    try 
                    {
                            String next = br.readLine();
                            while(next != null && next.trim().isEmpty())
                                    next = br.readLine();
                            if(next == null)
                                    return true;
                            st = new StringTokenizer(next);
                            return st.hasMoreTokens();
                    }
                    catch(Exception e) { throw new RuntimeException(); }
            }
    }
	
	static class Arista
	{
		int costo;
		Ciudad otra;
		
		public Arista(int costo, Ciudad otra) 
		{
			this.costo = costo;
			this.otra = otra;
		}
	}
	
	static class Ciudad
	{
		int numero;
		ArrayList <Arista> aristas = new ArrayList <Arista> ();
		
		public Ciudad(int numero)
		{
			this.numero = numero;
		}
	}
	
	static class Estado
	{
		int arista1;
		int arista2;
		Respuesta mejorCosto = new Respuesta(Integer.MAX_VALUE);
		Ciudad ciudad;
		
		public Estado(int arista1, int arista2, Ciudad ciudad)
		{
			this.arista1 = arista1;
			this.arista2 = arista2;
			this.ciudad = ciudad;
		}
	}
	
	
	static Estado darEstado(int aristaUno, int aristaDos, int ciudad)
	{
		long estado = ciudad;
		estado <<= 16;
		estado |= aristaUno;
		estado <<= 16;
		estado |= aristaDos;
		if(!estados.containsKey(estado))
			estados.put(estado, new Estado(aristaUno, aristaDos, ciudades.get(ciudad)));
		return estados.get(estado);
	}

	static Ciudad darCiudad(int ciudad) 
	{
		while(ciudades.size() < ciudad + 1)
			ciudades.add(null);
		Ciudad esta = ciudades.get(ciudad);
		if(esta == null)
			ciudades.set(ciudad, new Ciudad(ciudad));
		return ciudades.get(ciudad);
	}

	
	static class Respuesta implements Comparable <Respuesta>
	{
		int costo;
		int tam = 1;
		int aristaUno = 0;
		int aristaDos = 0;
		String vals = "0";
		
		Respuesta()
		{
		}
		
		Respuesta(int costo)
		{
			this.costo = costo;
		}
		
		int darCosto()
		{
			if(tam == 2)
				return aristaDos;
			else if(tam == 3)
				return aristaUno;
			else
				return costo;
		}
		
		Respuesta darSiguiente(Arista a)
		{
			Respuesta nueva = new Respuesta();
			nueva.costo = costo;
			nueva.tam = tam + 1;
			nueva.aristaUno = aristaUno;
			nueva.aristaDos = aristaDos;
			if((" " + vals + " ").contains(" " + a.otra.numero + " "))
				nueva.costo = 1000000000;
			nueva.vals = vals + " " + a.otra.numero;
			if(a.costo > nueva.aristaDos)
			{
				nueva.costo += nueva.aristaUno;
				nueva.aristaUno = nueva.aristaDos;
				nueva.aristaDos = a.costo;
			}
			else if(a.costo > nueva.aristaUno)
			{
				nueva.costo += nueva.aristaUno;
				nueva.aristaUno = a.costo;
			}
			else
				nueva.costo += a.costo;
			return nueva;
		}

		@Override
		public int compareTo(Respuesta o) 
		{
			if(darCosto() == o.darCosto())
				if(tam == o.tam)
					return vals.compareTo(o.vals);
				else
					return tam - o.tam;
			else
				return darCosto() - o.darCosto();
		}
	}
	
	static class Entrada implements Comparable <Entrada>
	{
		Respuesta costo;
		Estado estado;
		
		public Entrada(Respuesta costo, Estado estado) 
		{
			this.costo = costo;
			this.estado = estado;
		}

		@Override
		public int compareTo(Entrada o) 
		{
			return costo.compareTo(o.costo);
		}
	}
	
	public static void solucionar()
	{
		Respuesta mejor = new Respuesta(Integer.MAX_VALUE);
		PriorityQueue <Entrada> pq = new PriorityQueue <Entrada> ();
		Estado inicial = darEstado(0, 0, 0);
		inicial.mejorCosto = new Respuesta();
		pq.add(new Entrada(inicial.mejorCosto, inicial));
		while(!pq.isEmpty())
		{
			Entrada esta = pq.poll();
			if(esta.estado.mejorCosto != esta.costo)
				continue;
			if(esta.estado.ciudad.numero == 1)
			{
				if(esta.costo.compareTo(mejor) < 0)
					mejor = esta.costo;
				continue;
			}
			for(Arista a : esta.estado.ciudad.aristas)
			{
				Respuesta nuevo = esta.costo.darSiguiente(a);
				Estado estado = darEstado(nuevo.aristaUno, nuevo.aristaDos, a.otra.numero);
				if(nuevo.compareTo(estado.mejorCosto) < 0)
				{
					estado.mejorCosto = nuevo;
					pq.add(new Entrada(nuevo, estado));
				}
			}
		}
		System.out.println(mejor.vals + " " + mejor.darCosto());
	}

	static HashMap <Long, Estado> estados;
	static ArrayList <Ciudad> ciudades;
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		while(true)
		{
			int n = sc.nextInt();
			if(n == 0)
				return;
			estados = new HashMap <Long, Estado> ();
			ciudades = new ArrayList <Ciudad> ();
			for(int i = 0; i < n; i++)
			{
				int a = sc.nextInt();
				int b = sc.nextInt();
				int costo = sc.nextInt();
				Ciudad cA = darCiudad(a);
				Ciudad cB = darCiudad(b);
				cA.aristas.add(new Arista(costo, cB));
				cB.aristas.add(new Arista(costo, cA));
			}
			solucionar();
		}
	}
}