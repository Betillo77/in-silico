package control;
import java.awt.AWTException;
import java.io.File;
import java.io.IOException;

public class RobotInicio 
{
	public static void main(String [] args) throws AWTException, InterruptedException, IOException
	{
		ProcessBuilder pb = new ProcessBuilder("sudo", "java", "-jar", "dailyOCR.jar");
		pb.directory(new File("/home/santiago/Desktop/dailyOCR/"));
		pb.start().waitFor();
		Error.agregar("El programa termino");
	}
}
