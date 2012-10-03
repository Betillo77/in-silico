
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author utp
 */
public class numbers 
{

    private static boolean palindrome(String numero)
    {
        LinkedList <Character> l = new LinkedList <Character> ();
        for(char c : numero.toCharArray())
            l.add(c);
        while(l.size() > 1)
        {
            char a = l.pollFirst();
            char b = l.pollLast();
            if(a != b)
                return false;
        }
        return true;
    }
    
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
    
    static int valor(char letra)
    {
        if("abc".contains(letra + ""))
            return 2;
        if("def".contains(letra + ""))
            return 3;
        if("ghi".contains(letra + ""))
            return 4;
        if("jkl".contains(letra + ""))
            return 5;
        if("mno".contains(letra + ""))
            return 6;
        if("pqrs".contains(letra + ""))
            return 7;
        if("tuv".contains(letra + ""))
            return 8;
        else
            return 9;
    }
    public static void main(String[] args)
    {
        Scanner sc = new Scanner();
        int n = sc.nextInt();
        for(int i = 0; i < n; i++)
        {
            String linea = sc.next().toLowerCase();
            String numero = "";
            for(char c : linea.toCharArray())
            {
                int val = valor(c);
                numero += (char) (val + '0');
            }
            if(palindrome(numero))
                System.out.println("YES");
            else
                System.out.println("NO");
                
        }
    }
    
}
