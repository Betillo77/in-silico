import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class F 
{
	 static class Scanner
     {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        String nextLine()
        {
            String next;
            try 
            {
                next = br.readLine();
            } 
            catch (IOException ex) 
            {
                throw(new RuntimeException(ex));
            }
            return next;
        }
        
        String next()
        {
            while(st == null || !st.hasMoreTokens())
            {
                String linea = nextLine();
                if(linea == null)
                    return null;
                st = new StringTokenizer(linea);
            }
            return st.nextToken();
        }
        
        int nextInt()
        {
            return Integer.parseInt(next());
        }
    }
	 
	static void pushLeft(boolean[][] boxes, int howMany)
	{
		howMany = Math.min(howMany, boxes[0].length);
		outer:
		for(int i = 0; i < howMany; i++)
		{
			for(int j = 0; j < boxes.length; j++)
			{
				int count = 0;
				for(int k = 0; k < boxes[j].length; k++)
					if(boxes[j][k])
						count++;
				if(count == boxes[0].length - i)
					break outer;
			}
			for(int j = 0; j < boxes.length; j++)
			{
				int current = i;
				while(boxes[j][current])
					current++;
				while(current > i)
				{
					boxes[j][current] = boxes[j][current - 1];
					current--;
				}
				boxes[j][i] = false;
			}
		}
	}
	
	static void pushRight(boolean[][] boxes, int howMany)
	{
		howMany = Math.min(howMany, boxes[0].length);
		outer:
		for(int i = boxes[0].length - 1; i >= boxes[0].length - howMany; i--)
		{
			for(int j = 0; j < boxes.length; j++)
			{
				int count = 0;
				for(int k = 0; k < boxes[j].length; k++)
					if(boxes[j][k])
						count++;
				if(count == i + 1)
					break outer;
			}
			for(int j = 0; j < boxes.length; j++)
			{
				int current = i;
				while(boxes[j][current])
					current--;
				while(current < i)
				{
					boxes[j][current] = boxes[j][current + 1];
					current++;
				}
				boxes[j][i] = false;
			}
		}
	}
	
	static void pushDown(boolean[][] boxes, int howMany)
	{
		howMany = Math.min(howMany, boxes.length);
		outer:
		for(int i = 0; i < howMany; i++)
		{
			for(int j = 0; j < boxes[0].length; j++)
			{
				int count = 0;
				for(int k = 0; k < boxes.length; k++)
					if(boxes[k][j])
						count++;
				if(count == boxes.length - i)
					break outer;
			}
			for(int j = 0; j < boxes[0].length; j++)
			{
				int current = i;
				while(boxes[current][j])
					current++;
				while(current > i)
				{
					boxes[current][j] = boxes[current - 1][j];
					current--;
				}
				boxes[i][j] = false;
			}
		}
	}
	
	static void pushUp(boolean[][] boxes, int howMany)
	{
		howMany = Math.min(howMany, boxes.length);
		outer:
		for(int i = boxes.length - 1; i >= boxes.length - howMany; i--)
		{
			for(int j = 0; j < boxes[0].length; j++)
			{
				int count = 0;
				for(int k = 0; k < boxes.length; k++)
					if(boxes[k][j])
						count++;
				if(count == i + 1)
					break outer;
			}
			for(int j = 0; j < boxes[0].length; j++)
			{
				int current = i;
				while(boxes[current][j])
					current--;
				while(current < i)
				{
					boxes[current][j] = boxes[current + 1][j];
					current++;
				}
				boxes[i][j] = false;
			}
		}
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner();
		int caso = 1;
		while(true)
		{
			int n = sc.nextInt();
			int m = sc.nextInt();
			if(n == 0 && m == 0)
				return;
			boolean[][] tablero = new boolean[n][m];
			int cuantos = sc.nextInt();
			for(int i = 0; i < cuantos; i++)
				tablero[sc.nextInt()][sc.nextInt()] = true;
			while(true)
			{
				String s = sc.next();
				if(s.equals("done"))
					break;
				int number = sc.nextInt();
				if(s.equals("down"))
					pushDown(tablero, number);
				if(s.equals("up"))
					pushUp(tablero, number);
				if(s.equals("left"))
					pushRight(tablero, number);
				if(s.equals("right"))
					pushLeft(tablero, number);
			}
			System.out.print("Data set " + caso++ + " ends with boxes at locations");
			for(int i = 0; i < n; i++)
				for(int j = 0; j < m; j++)
					if(tablero[i][j])
						System.out.print(" (" + i + "," + j + ")");
			System.out.println(".");
		}
	}
}
