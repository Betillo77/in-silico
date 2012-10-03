
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author utp
 */
public class C 
{
    
    public static long calcularDP(long[] numeros)
    {
        final int n = numeros.length;
        long[] anterior = new long[n];
        long[] actual = new long[n];
        for(int i = n - 1; i >= 0; i--)
        {
            for(int j = 0; j < n; j++)
                actual[j] = 0;
            for(int j = i + 1; j < n; j++)
            {
                int t = (j - i) % 2;
                long s1 = anterior[j] + t * numeros[i];
                long s2 = actual[j - 1] + t * numeros[j];
                if(t == 1)
                    actual[j] = Math.max(s1, s2);
                else
                    actual[j] = Math.min(s1, s2);
            }
            long[] temp = anterior;
            anterior = actual;
            actual = temp;
        }
        return anterior[numeros.length - 1];
    }
    
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true)
        {
            String li = br.readLine();
            if(li == null)
                return;
            int n = Integer.parseInt(li.trim());
            long[] entrada = new long[n];
            String[] pedazos = br.readLine().trim().split(" ");
            for(int i = 0; i < n; i++)
                entrada[i] = Long.parseLong(pedazos[i].trim());
            System.out.println(calcularDP(entrada));
        }
    }
}
