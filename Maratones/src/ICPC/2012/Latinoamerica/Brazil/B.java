/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author utp
 */
import java.awt.geom.Line2D;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author utp
 */
public class B {
    static int N;
    static double L,H;
    
    static Line2D.Double[] flaps=new Line2D.Double[1010];
    
    public static void main(String[] args) throws IOException {
         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.setOut(new PrintStream(new BufferedOutputStream(System.out)));
        while(true){
            String linea = br.readLine();
            if(linea == null)
            {
                System.out.flush();
                return;
            }
            N=Integer.parseInt(linea.trim());
            StringTokenizer pedazos = new StringTokenizer(br.readLine().trim());
            L=Double.parseDouble(pedazos.nextToken());
            H=Double.parseDouble(pedazos.nextToken());
            for(int i=0;i<N;i++){
                pedazos = new StringTokenizer(br.readLine().trim());
                double yi=Double.parseDouble(pedazos.nextToken());
                double xf=Double.parseDouble(pedazos.nextToken());
                double yf=Double.parseDouble(pedazos.nextToken());
                if ((i+1)%2==1)
                    flaps[i]=new Line2D.Double(0.0,yi,xf,yf);
                else
                    flaps[i]=new Line2D.Double(L,yi,xf,yf);
            }
            double sol=Double.MAX_VALUE;
            for(int i=0;i<N;i++)
                if ((i+1)%2==1)
                    sol=Math.min(L-flaps[i].x2, sol);
                else
                    sol=Math.min(flaps[i].x2, sol);
            for(int i=0;i<N-1;i++)
                sol=Math.min(sol, flaps[i+1].ptSegDist(flaps[i].getP2()));
            System.out.printf("%.2f\n", sol);
        }
        
    }
}
