
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author utp
 */
public class L 
{
    
    static int[] ps;
    
    
    static void calcularPrimos()
    {
        int limite = (int) Math.ceil(Math.sqrt(Integer.MAX_VALUE)) + 1;
        ArrayList <Integer> primos = new ArrayList <Integer> ();
        primos.add(2);
        for(int i = 3; i <= limite; i++)
        {
            boolean paila = false;
            for(int p : primos)
            {
                if(i % p == 0)
                {
                    paila = true;
                    break;
                }
            }
            if(!paila)
                primos.add(i);
        }
        ps = new int[primos.size()];
        for(int i = 0; i < primos.size(); i++)
            ps[i] = primos.get(i);
    }
    
    static ArrayList <Integer> factorizar(int n)
    {
        ArrayList <Integer> factores = new ArrayList <Integer> ();
        int limite = Math.min(n, (int) Math.ceil(Math.sqrt(n)));
        for(int i : ps)
        {
            if(i > limite)
                break;
            if(n % i == 0)
                factores.add(i);
            while(n % i == 0)
            {
                n /= i;
                limite = Math.min(n, (int) Math.ceil(Math.sqrt(n)));
            }
        }
        if(n != 1)
            factores.add(n);
        return factores;
    }
    
    public static int totient(int n)
    {
        ArrayList <Integer> factores = factorizar(n);
        int b = n;
        int b1 = 1;
        for(int i : factores)
        {
            b1 = b1 * (i - 1);
            b /= i;
        }
        return b * b1;
    }
    
    public static void main(String[] args) throws IOException
    {
         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        calcularPrimos();
         while(true)
        {
            String li = br.readLine();
            if(li == null)
                return;
            int n = Integer.parseInt(li.trim());
            System.out.println(totient(n) / 2);
        }
    }
}
