
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author utp
 */
public class A
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true)
        {
            String li = br.readLine();
            if(li == null)
                return;
            int l = Integer.parseInt(li.split(" ")[1]);
            int c = Integer.parseInt(li.split(" ")[2]);
            ArrayList <Integer> palabras = new ArrayList <Integer> ();
            for(String s : br.readLine().trim().split(" "))
                palabras.add(s.trim().length());
            int paginaActual = 1;
            int lineaActual = 1;
            int tamLinea = 0;
            for(int i : palabras)
            {
                if(tamLinea == 0)
                    tamLinea += i;
                else if(tamLinea + (i + 1) > c)
                {
                    lineaActual++;
                    if(lineaActual == l + 1)
                    {
                        paginaActual++;
                        lineaActual = 1;
                    }
                    tamLinea = i;
                }
                else
                    tamLinea += (i + 1);
            }
            System.out.println(paginaActual);
        }
    }
    
}
