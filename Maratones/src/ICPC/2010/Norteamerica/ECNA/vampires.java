import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class vampires {
	
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
	
	static class pair{
		int x,y;
		public pair(int a,int b){
			x=a;
			y=b;
		}
	}
	
	static int V,O,M;
	static char[][] maze=new char[101][101];
	
	static void set_mirrors(char D,int x1,int y1,int x2,int y2){
		for(int i=Math.min(x1, x2);i<=Math.max(x1, x2);i++)
			for(int j=Math.min(y1, y2);j<=Math.max(y1, y2);j++)
				maze[i][j]=D;
		return;
	}
	
	static boolean mirror_north(int x,int y){
		for(;y<101;y++){
			if (maze[x][y]=='V' || maze[x][y]==0)continue;
			if (maze[x][y]=='S')return true;
			else return false;
		}
		return false;
	}
	
	static boolean mirror_east(int x,int y){
		for(;x<101;x++){
			if (maze[x][y]=='V' || maze[x][y]==0)continue;
			if (maze[x][y]=='W')return true;
			else return false;
		}
		return false;
	}
	
	static boolean mirror_south(int x,int y){
		for(;y>=0;y--){
			if (maze[x][y]=='V' || maze[x][y]==0)continue;
			if (maze[x][y]=='N')return true;
			else return false;
		}
		return false;
	}
	
	static boolean mirror_west(int x,int y){
		for(;x>=0;x--){
			if (maze[x][y]=='V' || maze[x][y]==0)continue;
			if (maze[x][y]=='E')return true;
			else return false;
		}
		return false;
	}
	
	public static void main(String args[]){
		Scanner sc=new Scanner();
		int caso=0;
		while(true){
			V=sc.nextInt();
			O=sc.nextInt();
			M=sc.nextInt();
			if (V==0 && O==0 && M==0) break;
			for(int i=0;i<101;i++)
				for(int j=0;j<101;j++)
					maze[i][j]=0;
			LinkedList<pair> vamps=new LinkedList<pair>();
			for(int i=0;i<V;i++){
				int x=sc.nextInt();
				int y=sc.nextInt();
				maze[x][y]='V';
				vamps.add(new pair(x,y));
			}
			for(int i=0;i<O;i++){
				int x=sc.nextInt();
				int y=sc.nextInt();
				maze[x][y]='H';
			}
			for(int i=0;i<M;i++)
				set_mirrors(sc.next().charAt(0),sc.nextInt(),sc.nextInt(),sc.nextInt(),sc.nextInt());
			int nv=0;
			boolean any=false;
			System.out.println("Case "+ ++caso +":");
			for(pair v: vamps){
				nv++;
				LinkedList<String> dirs=new LinkedList<String>();
				if (mirror_east(v.x,v.y))
					dirs.add("east");
				if (mirror_north(v.x,v.y))
					dirs.add("north");
				if (mirror_south(v.x,v.y))
					dirs.add("south");
				if (mirror_west(v.x,v.y))
					dirs.add("west");
				if (dirs.size()>0){
					System.out.print("vampire "+nv);
					for(String c: dirs)
						System.out.print(" "+c);
					System.out.println();
					any=true;
				}
			}
			if (!any)
				System.out.println("none");
		}
	}
}
