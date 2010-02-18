package modelo;
public class Integrante implements Comparable <Integrante>
{
	String nombre;
	public String id;
	
	public Integrante(String nombre, String id) 
	{
		this.nombre = nombre;
		this.id = id;
	}

	public String toString() 
	{
		return nombre;
	}

	public int compareTo(Integrante otro) 
	{
		return id.compareTo(otro.id);
	}
}
