import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;


public class GoodSolutions
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		String s;
		while((s = br.readLine()) != null)
		{
			StringTokenizer st = new StringTokenizer(s);
			String a = st.nextToken();
			switch(a.charAt(0))
			{
				case '/':
					bw.append(new BigInteger(st.nextToken()).divide(new BigInteger(st.nextToken())).toString());
					break;
				case '%':
					bw.append(new BigInteger(st.nextToken()).mod(new BigInteger(st.nextToken())).toString());
					break;
				case '*':
					bw.append(new BigInteger(st.nextToken()).multiply(new BigInteger(st.nextToken())).toString());
					break;
				case '+':
					bw.append(new BigInteger(st.nextToken()).add(new BigInteger(st.nextToken())).toString());
					break;
				case '-':
					bw.append(new BigInteger(st.nextToken()).subtract(new BigInteger(st.nextToken())).toString());
					break;
				case 't':
					bw.append(new BigInteger(st.nextToken()).shiftRight(Integer.parseInt(st.nextToken())).toString());
					break;
				default:
					bw.append(new BigInteger(st.nextToken()).modPow(new BigInteger(st.nextToken()), new BigInteger(st.nextToken())).toString());
					
			}
			bw.append('\n');
		}
		bw.flush();
	}

}
