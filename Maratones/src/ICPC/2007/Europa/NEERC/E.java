import java.math.BigInteger;
import java.util.Scanner;
import java.util.Stack;

public class Eq
{
    public static class Numero
    {
        BigInteger a;
        BigInteger b;
        
        Numero(BigInteger aa, BigInteger bb)
        {
            a = aa;
            b = bb;
        }
       
        Numero multiplicar(Numero otro)
        {
            return new Numero(a.multiply(otro.b).add(b.multiply(otro.a)), b.multiply(otro.b));
        }
        
        Numero sumar(Numero otro)
        {
            return new Numero(a.add(otro.a), b.add(otro.b));
        }

        private Numero restar(Numero otro)
        {
            return new Numero(a.subtract(otro.a), b.subtract(otro.b));
        }
    }
    
    public static class Fraccion
    {
        Numero numerador;
        Numero denominador;
        
        Fraccion(Numero a, Numero b)
        {
            numerador = a;
            denominador = b;
        }
        
        Fraccion multipicar(Fraccion otra)
        {
            return new Fraccion(numerador.multiplicar(otra.numerador), denominador.multiplicar(otra.denominador));
        }
        
        Fraccion dividir(Fraccion otra)
        {
            return new Fraccion(numerador.multiplicar(otra.denominador), denominador.multiplicar(otra.numerador));
        }
        
        Fraccion sumar(Fraccion otra)
        {
            return new Fraccion(numerador.multiplicar(otra.denominador).sumar(denominador.multiplicar(otra.numerador)), denominador.multiplicar(otra.denominador));
        }
        
        Fraccion restar(Fraccion otra)
        {
            return new Fraccion(numerador.multiplicar(otra.denominador).restar(denominador.multiplicar(otra.numerador)), denominador.multiplicar(otra.denominador));
        }
    }
    
    static Fraccion simular(String linea, Fraccion x)
    {
    	@SuppressWarnings("resource")
		Scanner sc2 = new Scanner(linea);
        Stack <Fraccion> actuales = new Stack <Fraccion> ();
        while(sc2.hasNext())
        {
            String actual = sc2.next();
            if(actual.equals("X"))
            {
            	if(x == null)
            		actuales.push(new Fraccion(new Numero(BigInteger.ONE, BigInteger.ZERO), new Numero(BigInteger.ZERO, BigInteger.ONE)));
            	else
            		actuales.push(x);
            }
            else if(actual.equals("/"))
            {
                Fraccion b = actuales.pop();
                Fraccion a = actuales.pop();
                if(x != null && b.numerador.a.equals(BigInteger.ZERO) && b.numerador.b.equals(BigInteger.ZERO))
                	return null;
                actuales.push(a.dividir(b));
            }
            else if(actual.equals("*"))
            {
                Fraccion b = actuales.pop();
                Fraccion a = actuales.pop();
                actuales.push(a.multipicar(b));
            }
            else if(actual.equals("+"))
            {
                Fraccion b = actuales.pop();
                Fraccion a = actuales.pop();
                actuales.push(a.sumar(b));
            }
            else if(actual.equals("-"))
            {
                Fraccion b = actuales.pop();
                Fraccion a = actuales.pop();
                actuales.push(a.restar(b));
            }
            else
                 actuales.push(new Fraccion(new Numero(BigInteger.ZERO, new BigInteger(actual)), new Numero(BigInteger.ZERO, BigInteger.ONE)));
        }
        return actuales.pop();
    }
    
    public static void main(String[] args)
    {
        @SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine())
        {
        	String linea = sc.nextLine();
            Fraccion ultima = simular(linea, null);
            Numero ultimo = ultima.numerador;
            if(ultimo.a.equals(BigInteger.ZERO) && ultimo.b.equals(BigInteger.ZERO))
                System.out.println("MULTIPLE");
            else if(ultimo.a.equals(BigInteger.ZERO) && (!ultimo.b.equals(BigInteger.ZERO)))
                System.out.println("NONE");
            else
            {
            	BigInteger a = ultimo.b.negate().divide(ultimo.b.gcd(ultimo.a));
            	BigInteger b = ultimo.a.divide(ultimo.b.gcd(ultimo.a));
            	if(b.compareTo(BigInteger.ZERO) < 0)
            	{
            		b = b.negate();
            		a = a.negate();
            	}
            	if(simular(linea, new Fraccion(new Numero(BigInteger.ZERO, a), new Numero(BigInteger.ZERO, b))) == null)
            		System.out.println("NONE");
            	else
            		System.out.println("X = " + a + "/" + b);
            }
        }
    }
}