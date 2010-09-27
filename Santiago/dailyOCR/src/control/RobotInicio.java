package control;
import java.io.File;
import java.io.IOException;

public class RobotInicio 
{
	public static void main(String [] args) throws InterruptedException, IOException
	{
		ProcessBuilder pb = new ProcessBuilder("sudo", "java", "-jar", "dailyOCR.jar", "mx1024m", "-Xms512m");
		pb.directory(new File("/home/santiago/Desktop/dailyOCR/"));
		pb.start().waitFor();
		Error.agregar("El programa termino");
	}
}
