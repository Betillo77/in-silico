import java.util.Scanner;


public class A 
{
	static String kString(String o, int k)
	{
		int[] cuenta = new int[26];
		for(char c : o.toCharArray())
			cuenta[c - 'a']++;
		for(int i : cuenta)
			if(i % k != 0)
				return null;
		StringBuilder sb = new StringBuilder();
		for(int v = 0; v < k; v++)
		{
			for(int i = 0; i < 26; i++)
			{
				for(int j = 0; j < (cuenta[i] / k); j++)
					sb.append((char)(i + 'a'));
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args)
	{
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int k = sc.nextInt();
		String r = kString(sc.next(), k);
		if(r == null)
			System.out.println("-1");
		else
			System.out.println(r);
	}

	
}
