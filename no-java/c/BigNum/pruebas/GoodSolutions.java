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
					bw.append(new BigInteger(st.nextToken(), 16).divide(new BigInteger(st.nextToken(), 16)).toString(16));
					break;
				case '%':
					bw.append(new BigInteger(st.nextToken(), 16).mod(new BigInteger(st.nextToken(), 16)).toString(16));
					break;
				case '*':
					bw.append(new BigInteger(st.nextToken(), 16).multiply(new BigInteger(st.nextToken(), 16)).toString(16));
					break;
				case '+':
					bw.append(new BigInteger(st.nextToken(), 16).add(new BigInteger(st.nextToken(), 16)).toString(16));
					break;
				case '-':
					bw.append(new BigInteger(st.nextToken(), 16).subtract(new BigInteger(st.nextToken(), 16)).toString(16));
					break;
				case 't':
					bw.append(new BigInteger(st.nextToken(), 16).shiftRight(Integer.parseInt(st.nextToken(), 16)).toString(16));
					break;
				default:
					bw.append(new BigInteger(st.nextToken(), 16).modPow(new BigInteger(st.nextToken(), 16), new BigInteger(st.nextToken(), 16)).toString(16));
					
			}
			bw.append('\n');
		}
		bw.flush();
	}

}
