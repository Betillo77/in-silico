import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class Photo {
	
	static class Scanner
    {
            BufferedReader br;
            StringTokenizer st;
            
            public Scanner()
            {
                   br = new BufferedReader(new InputStreamReader(System.in));
            }
            
            public String next()
            {

                    while(st == null || !st.hasMoreTokens())
                    {
                            try { st = new StringTokenizer(br.readLine()); }
                            catch(Exception e) { throw new RuntimeException(); }
                    }
                    return st.nextToken();
            }

            public int nextInt()
            {
                    return Integer.parseInt(next());
            }
            
            public double nextDouble()
            {
                    return Double.parseDouble(next());
            }
            
            public String nextLine()
            {
                    st = null;
                    try { return br.readLine(); }
                    catch(Exception e) { throw new RuntimeException(); }
            }
            
            public boolean endLine()
            {
                    try 
                    {
                            String next = br.readLine();
                            while(next != null && next.trim().isEmpty())
                                    next = br.readLine();
                            if(next == null)
                                    return true;
                            st = new StringTokenizer(next);
                            return st.hasMoreTokens();
                    }
                    catch(Exception e) { throw new RuntimeException(); }
            }
    }
	
	static double angle(double x,double y){
		double tmp=Math.atan2(y,x);
		if (tmp<0) tmp+=Math.PI*2;
		return tmp;
	}
	
	public static void main(String args[]) throws IOException{
		Scanner sc=new Scanner();
		int caso=0;
		while(true){
			int N=sc.nextInt();
			double X=sc.nextDouble();
			double Y=sc.nextDouble();
			double F=sc.nextDouble();
			if (N==0 && X<1e-7 && Y<1e-7 && F<1e-7) break;
			F=(F/180.0)*Math.PI;
			double [] points=new double[N*2];
			for(int i=0;i<N;i++){
				double a=sc.nextDouble();
				double b=sc.nextDouble();
				points[i]=angle(a-X,b-Y);
			}
			Arrays.sort(points, 0, N);
			for(int i=0;i<N;i++){
				points[i+N]=points[i]+Math.PI*2;
			}
			int ans=Integer.MAX_VALUE;
			for(int inicio=0;inicio<N;inicio++){
				int actual=inicio+1;
				int shots=1;
				int begin=inicio;
				while(actual<N+inicio){
					if (points[actual] - points[begin]<F+1e-7)
						actual++;
					else{
						begin=actual;
						actual++;
						shots++;
					}
				}
				ans=Math.min(ans,shots);
			}
			System.out.println("Case "+ ++caso +": "+ans);
		}
		
	}

}
