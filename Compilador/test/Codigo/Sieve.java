import java.util.Scanner;

public class Sieve {
    
    static int[] initArray(int n) {
        int[] a = new int[n];
        int i = 0;
        while (i < a.length) {
            a[i] = i;
            i = i + 1;
        }
        return a;
    }
    
    static void sieveAll(int[] a) {
        int i = 2;
        while (i < a.length) {
            Sieve.sieve(a, i);
            i = i + 1; 
        }
    }
    
    static void sieve(int[] a, int n) {
        int i = 2*n;
        while (i < a.length) {
            a[i] = 0;
            i = i + n;
        }
    }
    
    static void printPrimes(int[] a) {
        int i = 2;
        Library.print("Primes less than ");
        Library.printi(a.length);
        Library.print(": ");
        while (i < a.length) {
            if (a[i] != 0) {
                Library.printi(a[i]);
                Library.print(" ");
            }
            i = i + 1;
        }
    }
    
    
    public static void main(String[] args) {
    	int n; 
    	int[] a;
        Library.println("Provide the number to sieve.");
        n = Library.readi();
        a = Sieve.initArray(n);
        Library.println("");
        Sieve.sieveAll(a);
        Sieve.printPrimes(a);
        Library.println("");
    }
}

class Library
{
	static Scanner sc = new Scanner(System.in);
	static void println(String a){System.out.println(a);}
	static void print(String b){System.out.print(b);}
	static void printi(int c){System.out.print(c);}
	static int readi(){return sc.nextInt();}
	static void exit(int a){}
}
