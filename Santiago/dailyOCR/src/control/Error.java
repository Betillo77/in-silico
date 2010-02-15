package control;
import java.io.File;
import java.io.FileWriter;



public class Error 
{
	public static void agregar(String error)
	{
		try
		{
			FileWriter fw = new FileWriter(new File(dailyOCR.pathPrincipal + "Error.txt"), true);
			fw.write(error);
			fw.write(System.getProperty("line.separator"));
			fw.close();
		} 
		catch (Exception e)
		{
			System.out.println("Error en el manejador de errores");
		}
	}

}
