import java.util.Scanner;


public class Roman 
{
	static int leerRomano(String r)
	{
		int result = 0;
		while(r.startsWith("M"))
		{
			result += 1000;
			r = r.substring(1);
		}
		if(r.startsWith("CM"))
		{
			result += 900;
			r = r.substring(2);
		}
		while(r.startsWith("D"))
		{
			result += 500;
			r = r.substring(1);
		}
		if(r.startsWith("CD"))
		{
			result += 400;
			r = r.substring(2);
		}
		if(r.startsWith("LD"))
		{
			result += 450;
			r = r.substring(2);
		}
		while(r.startsWith("C"))
		{
			result += 100;
			r = r.substring(1);
		}
		if(r.startsWith("XC"))
		{
			result += 90;
			r = r.substring(2);
		}
		while(r.startsWith("L"))
		{
			result += 50;
			r = r.substring(1);
		}
		if(r.startsWith("XL"))
		{
			result += 40;
			r = r.substring(2);
		}
		if(r.startsWith("VL"))
		{
			result += 45;
			r = r.substring(2);
		}
		while(r.startsWith("X"))
		{
			result += 10;
			r = r.substring(1);
		}
		if(r.startsWith("IX"))
		{
			result += 9;
			r = r.substring(2);
		}
		while(r.startsWith("V"))
		{
			result += 5;
			r = r.substring(1);
		}
		if(r.startsWith("IV"))
		{
			result += 4;
			r = r.substring(2);
		}
		while(r.startsWith("I"))
		{
			result += 1;
			r = r.substring(1);
		}
		return result;
	}
	
	
	static String aRomano(int n)
	{
		String result = "";
		for(int i = 0; i < n / 1000; i++)
			result += "M";
		n %= 1000;
		int cientos = n / 100;
		switch(cientos)
		{
			case 0: break;
			case 1: result += "C"; break;
			case 2: result += "CC"; break;
			case 3: result += "CCC"; break;
			case 4: result += "CD"; break;
			case 5: result += "D"; break;
			case 6: result += "DC"; break;
			case 7: result += "DCC"; break;
			case 8: result += "DCCC"; break;
			case 9: result += "CM"; break;
		}
		n %= 100;
		int decenas = n / 10;
		switch(decenas)
		{
			case 0: break;
			case 1: result += "X"; break;
			case 2: result += "XX"; break;
			case 3: result += "XXX"; break;
			case 4: result += "XL"; break;
			case 5: result += "L"; break;
			case 6: result += "LX"; break;
			case 7: result += "LXX"; break;
			case 8: result += "LXXX"; break;
			case 9: result += "XC"; break;
		}
		n %= 10;
		switch(n)
		{
			case 0: break;
			case 1: result += "I"; break;
			case 2: result += "II"; break;
			case 3: result += "III"; break;
			case 4: result += "IV"; break;
			case 5: result += "V"; break;
			case 6: result += "VI"; break;
			case 7: result += "VII"; break;
			case 8: result += "VIII"; break;
			case 9: result += "IX"; break;
		}
		return result;
	}

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		int caso = 1;
		while(true)
		{
			int n = sc.nextInt();
			if(n == 0)
				return;
			int total = 0;
			for(int i = 0; i < n; i++)
				total += leerRomano(sc.next());
			System.out.println("Case " + aRomano(caso++) + ": " + aRomano(total));
		}
	}
}
