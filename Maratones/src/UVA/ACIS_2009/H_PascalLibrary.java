import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;


public class H_PascalLibrary 
{
	
	public static void main(String[] args) throws IOException
	{
		System.setIn(new FileInputStream("PascalLibrary.in"));
		System.setOut(new PrintStream("PascalLibrary.out1"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean[] personas = new boolean[1000];
		while(true)
		{
			String[] pedazos = br.readLine().split("\\s+");
			int n = Integer.parseInt(pedazos[0]);
			int d = Integer.parseInt(pedazos[1]);
			if(n == 0 && d == 0)
				return;
			for(int i = 0; i < n; i++)
				personas[i] = true;
			for(int i = 0; i < d; i++)
			{
				pedazos = br.readLine().split("\\s+");
				for(int j = 0; j < n; j++)
				{
					personas[j] &= pedazos[j].equals("1");
				}
			}
			boolean si = false;
			for(int i = 0; i < n; i++)
				si |= personas[i];
			if(si)
				System.out.println("yes");
			else
				System.out.println("no");
		}
	}

}
