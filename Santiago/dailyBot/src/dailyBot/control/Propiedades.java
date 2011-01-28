package dailyBot.control;

import java.io.FileInputStream;
import java.util.Properties;

public class Propiedades 
{
	private static Properties propiedades = cargarPropiedades();
		
	private static Properties cargarPropiedades()
	{
		Properties propiedades = new Properties();
		try 
		{
			propiedades.load(new FileInputStream("propiedades.conf"));
		} 
		catch (Exception e) 
		{
			Error.agregar(e.getMessage() + " Error cargando las propiedades");
		}
		return propiedades;
	}
	
	public static String darPropiedad(String propiedad)
	{
		return propiedades.getProperty(propiedad);
	}
}
