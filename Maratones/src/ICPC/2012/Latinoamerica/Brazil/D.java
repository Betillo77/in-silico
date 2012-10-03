
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
public class D 
{
    static int respuesta(int[] entrada)
    {
        int suma = 0;
        for(int i : entrada)
            suma += i;
        if(suma % entrada.length != 0)
            return -1;
        suma /= entrada.length;
        int suma2 = 0;
        for(int i : entrada)
            suma2 += Math.abs(suma - i);
        suma2 /= 2;
        return suma2 + 1;
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
            int[] entrada = new int[n];
            String[] pedazos = br.readLine().trim().split(" ");
            for(int i = 0; i < n; i++)
                entrada[i] = Integer.parseInt(pedazos[i].trim());
            System.out.println(respuesta(entrada));
        }
    }
}
