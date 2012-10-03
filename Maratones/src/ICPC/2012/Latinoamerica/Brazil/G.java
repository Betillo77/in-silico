
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
public class G
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true)
        {
            String li = br.readLine();
            if(li == null)
                return;
            int n = Integer.parseInt(li.trim());
            int[] pos = new int[n + 1];
            int[] inicial = new int[n];
            String[] pedazos = br.readLine().trim().split(" ");
            for(int i = 0; i < n; i++)
                inicial[i] = Integer.parseInt(pedazos[i].trim());
            pedazos = br.readLine().trim().split(" ");
            for(int i = 0; i < n; i++)
                pos[Integer.parseInt(pedazos[i].trim())] = i;
            for(int i = 0; i < n; i++)
                inicial[i] = pos[inicial[i]];
            int swaps = 0;
            for(int i = 1; i < n; i++)
            {
                int j = i - 1;
                while(j >= 0)
                {
                    if(inicial[j + 1] < inicial[j])
                    {
                        int tmp = inicial[j + 1];
                        inicial[j + 1] = inicial[j];
                        inicial[j] = tmp;
                        swaps++;
                    }
                    j--;
                }
            }
            System.out.println(swaps);
        }
    }
    
}
