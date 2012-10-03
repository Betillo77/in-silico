
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author utp
 */
public class party 
{
    static class vector
    {
        double x;
        double y;
        
        public vector(double a,double b){
            x=a;
            y=b;
        }
        
        vector sub(vector b){
            vector ret=new vector(0,0);
            ret.x=this.x - b.x;
            ret.y=this.y - b.y;
            return ret;
        }
        vector add(vector b){
            vector ret=new vector(0,0);
            ret.x=this.x + b.x;
            ret.y=this.y + b.y;
            return ret;
        }
        
        vector getperpendicular(){
            vector ret=new vector(0,0);
            ret.x=-y;
            ret.y=x;
            return ret;
        }
        
        vector multbyscalar(double a){
            vector ret=new vector(0,0);
            ret.x=this.x*a;
            ret.y=this.y*a;
            return ret;
        }
        
        double norm(){
            return Math.sqrt(x*x + y*y);
        }
        
        vector normalize(){
            vector ret=new vector(0,0);
            double n=norm();
            ret.x=x*(1/n);
            ret.y=y*(1/n);
            return ret;
        }  
        
        double distancia(vector b)
        {
            return sub(b).norm();
        }
    }
    
    
    
    static double RA=2.5;
    static double eps=1e-7;
    
    static vector getcircle1(vector a,vector b){
        vector dist=a.sub(b);
        if (dist.norm()>2*RA+eps)
            return null;
        if (dist.norm()>2*RA-eps)
            return dist.multbyscalar(0.5).add(b);
        double cateto=dist.multbyscalar(0.5).norm();
        double perpend=Math.sqrt(RA*RA - cateto*cateto);

        vector p=dist.getperpendicular();
        p=p.normalize();
        p=p.multbyscalar(perpend);
        p=p.add(dist.multbyscalar(0.5));
        p=p.add(b);
        return p;  
    }
    
    static vector getcircle2(vector a,vector b){
        vector dist=a.sub(b);
        if (dist.norm()>2*RA+eps)
            return null;
        if (dist.norm()>2*RA-eps)
            return dist.multbyscalar(0.5).add(b);
        double cateto=dist.multbyscalar(0.5).norm();
        double perpend=Math.sqrt(RA*RA - cateto*cateto);
        vector p=dist.getperpendicular();
        p=p.normalize();
        p=p.multbyscalar(-perpend);
        p=p.add(dist.multbyscalar(0.5));
        p=p.add(b);
        return p;
    }
    
    static vector[] array=new vector[210];
    
    static int chequear(vector centro, int cuantos)
    {
        int cuenta = 0;
        for(int i = 0; i < cuantos; i++)
        {
            double distancia = centro.distancia(array[i]);
            if(distancia < RA + eps)
                cuenta++;
        }
        return cuenta;
    }
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int casos = Integer.parseInt(br.readLine().trim());
        for(int caso = 0; caso < casos; caso++)
        {
            int actual = 0;
            while(true)
            {
                String linea = br.readLine();
                if(linea == null || linea.trim().isEmpty())
                    break;
                linea = linea.trim();
                StringTokenizer st = new StringTokenizer(linea);
                array[actual++] = new vector(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
            }
            int mejor = 1;
            for(int i = 0; i < actual; i++)
                for(int j = i + 1; j < actual; j++)
                {
                    vector uno = getcircle1(array[i], array[j]);
                    if(uno != null)
                        mejor = Math.max(mejor, chequear(uno, actual));
                    vector dos = getcircle2(array[i], array[j]);
                    if(dos != null)
                        mejor = Math.max(mejor, chequear(dos, actual));
                }
            System.out.println(mejor);
        }
    }
}
