// Ojo falta resolver casos donde hay infinitas soluciones. Tambien, esto es solo gaussian y no gauss-jordan

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Gaussian
{

	    private static final double EPSILON = 1e-10;

	    // Gaussian elimination with partial pivoting
	    public static double[] lsolve(double[][] A, double[] b) {
	        int N  = b.length;

	        for (int p = 0; p < N; p++) {

	            // find pivot row and swap
	            int max = p;
	            for (int i = p + 1; i < N; i++) {
	                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
	                    max = i;
	                }
	            }
	            double[] temp = A[p]; A[p] = A[max]; A[max] = temp;
	            double   t    = b[p]; b[p] = b[max]; b[max] = t;

	            // singular or nearly singular
	            if (Math.abs(A[p][p]) <= EPSILON) {
	                throw new RuntimeException("Matrix is singular or nearly singular");
	            }

	            // pivot within A and b
	            for (int i = p + 1; i < N; i++) {
	                double alpha = A[i][p] / A[p][p];
	                b[i] -= alpha * b[p];
	                for (int j = p; j < N; j++) {
	                    A[i][j] -= alpha * A[p][j];
	                }
	            }
	        }

	        // back substitution
	        double[] x = new double[N];
	        for (int i = N - 1; i >= 0; i--) {
	            double sum = 0.0;
	            for (int j = i + 1; j < N; j++) {
	                sum += A[i][j] * x[j];
	            }
	            x[i] = (b[i] - sum) / A[i][i];
	        }
	        return x;
	    }

    // sample client
    public static void main1(String[] args) {

    }
    
    public static void main(String[] args) throws IOException
    {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	while(true)
    	{
    		String[] pedazos = br.readLine().split(" ");
    		double x00 = Integer.parseInt(pedazos[0]);
    		double x10 = Integer.parseInt(pedazos[1]);
    		double x20 = Integer.parseInt(pedazos[2]);
    		if(x00 == 0 && x10 == 0 && x20 == 0)
    			return;
    		pedazos = br.readLine().split(" ");
    		double x01 = Integer.parseInt(pedazos[0]);
    		double x11 = Integer.parseInt(pedazos[1]);
    		double x21 = Integer.parseInt(pedazos[2]);
    		pedazos = br.readLine().split(" ");
    		double x02 = Integer.parseInt(pedazos[0]);
    		double x12 = Integer.parseInt(pedazos[1]);
    		double x22 = Integer.parseInt(pedazos[2]);
	        double[][] A = { { x00, x01, x02 },
	                         { x10, x11, x12 },
	                         { x20, x21, x22 }
	                       };
    		pedazos = br.readLine().split(" ");
    		double a0 = Integer.parseInt(pedazos[0]);
    		double a1 = Integer.parseInt(pedazos[1]);
    		double a2 = Integer.parseInt(pedazos[2]);
	        double[] b = { a0, a1, a2 };
	        boolean paila = false;
	        try
	        {
		        double[] x = lsolve(A, b);
		        for(double a : x)
		        {
		        	if(a <= 1e-10)
		        		paila = true;
		        }
	        }
	        catch(Exception e)
	        {
	        	paila = true;
	        }
	        if(paila)
	        	System.out.println("NO");
	        else
	        	System.out.println("YES");
	        br.readLine();
    	}
    }

}
