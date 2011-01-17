package dailyBot.control;
import java.io.File;
import java.io.IOException;

public class RobotInicio 
{
	public static void main(String [] args) throws InterruptedException, IOException
	{
		ProcessBuilder pb = new ProcessBuilder("sudo", "java", "-server", "-jar", "dailyBot.jar", "-Xmx1536m", "-Xms512m");
		pb.directory(new File("/home/santiago/Desktop/dailyBot/"));
		pb.start();
	}
}
