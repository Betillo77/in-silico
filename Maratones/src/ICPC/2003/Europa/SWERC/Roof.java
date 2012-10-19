import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;


public class Roof 
{
	static class SuperScanner
    {
            char[] buffer;
            InputStreamReader isr;
            int p;
            int start;
            
            public SuperScanner()
            {
                    buffer = new char[6000001];
                    isr = new InputStreamReader(System.in);         
                    read(0);
            }
            
            void read(int init)
            {
                    try
                    {
                            int tam;
                            if((tam = isr.read(buffer, init, buffer.length - init)) < buffer.length - init)
                            {
                                    if(tam < 0)
                                            tam = 0;
                                    buffer[tam] = '\0';
                            }
                            else
                            	throw(new RuntimeException());
                    }
                    catch(Exception e)
                    {
                            throw(new RuntimeException());
                    }
            }
            
            public void readNext()
            {
                    int tam = buffer.length;
                    int pos = p;
                    while(pos < tam && buffer[pos] <= ' ')
                            pos++;
                    if(pos == tam)
                    {
                            read(0);
                            readNext();
                            return;
                    }
                    start = pos;
                    while(pos < tam && buffer[pos] > ' ')
                            pos++;
                    if(pos == tam)
                    {
                            System.arraycopy(buffer, start, buffer, 0, buffer.length - start);
                            read(buffer.length - start);
                            p = start;
                            readNext();
                            return;
                    }
                    p = pos;
            }
            
            public String next()
            {
                    readNext();
                    return new String(buffer, start, p - start);
            }
            
            
            public long nextLong()
            {
            	readNext();
            	int d = start;
            	int pos = p;
            	long r = 0;
            	boolean n = buffer[d] == '-';
            	if(n)
            		d++;
            	r -= buffer[d++] - '0';
            	while (d < pos && (r = (r << 1) + (r << 3)) <= 0)
            		r -= buffer[d++] - '0';
            	return n ? r : -r;
            }
            
            public int nextInt()
            {
                    return (int) nextLong();
            }
            
            public double nextDouble()
            {
                    return Double.parseDouble(next());
            }
            
            public String nextLine()
            {
                    int tam = buffer.length;
                    int pos = p;
                    while(pos < tam && buffer[pos] != '\n')
                            pos++;
                    if(pos == tam)
                    {
                            read(0);
                            return nextLine();
                    }
                    start = ++pos;
                    while(pos < tam && buffer[pos] != '\n')
                            pos++;
                    if(pos >= tam)
                    {
                            System.arraycopy(buffer, start, buffer, 0, buffer.length - start);
                            read(buffer.length - start);
                            return nextLine();
                    }
                    p = pos;
                    return new String(buffer, start, p - start);
            }
            
            public boolean EOF()
            {
                    int tam = buffer.length;
                    int pos = p;
                    while(pos < tam && buffer[pos] <= ' ')
                            if(buffer[pos++] == '\0')
                                    return true;
                    pos++;
                    if(pos >= tam)
                    {
                            if(p == 0)
                                    return false;
                            System.arraycopy(buffer, p, buffer, 0, buffer.length - p);
                            read(buffer.length - p);
                            p = 0;
                            return EOF();
                    }
                    return false;
            }
    }
    
    static class SuperWriter
    {
            char[] buffer;
            OutputStreamWriter os;
            int pos;
            
            public SuperWriter()
            {
                    buffer = new char[6000001];
                    os = new OutputStreamWriter(System.out);
                    pos = 0;
            }
            
            public void println(String s)
            {
                    print(s);
                    print("\n");
            }
            
            char[] buf = new char[19];
            
            public void writeLong(long i)
            {
                int size = (i < 0) ? stringSize(-i) + 1 : stringSize(i);
                char[] b = toString(i, size);
                for(int j = 0; j < size; j++)
                	buffer[pos++] = b[j];
                buffer[pos++] = '\n';
            }
            public char[] toString(long i, int size) 
            {
                getChars(i, size, buf);
                return buf;
            }

            final static char[] digits = {
                '0' , '1' , '2' , '3' , '4' , '5' ,
                '6' , '7' , '8' , '9' , 'a' , 'b' ,
                'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
                'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
                'o' , 'p' , 'q' , 'r' , 's' , 't' ,
                'u' , 'v' , 'w' , 'x' , 'y' , 'z'
            };
            final static char [] DigitTens = {
                '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
                '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
                '2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
                '3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
                '4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
                '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
                '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
                '7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
                '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
                '9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
                } ;

            final static char [] DigitOnes = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                } ;
            
            /**
             * Places characters representing the integer i into the
             * character array buf. The characters are placed into
             * the buffer backwards starting with the least significant
             * digit at the specified index (exclusive), and working
             * backwards from there.
             *
             * Will fail if i == Long.MIN_VALUE
             */
            void getChars(long i, int index, char[] buf) {
                long q;
                int r;
                int charPos = index;
                char sign = 0;

                if (i < 0) {
                    sign = '-';
                    i = -i;
                }

                // Get 2 digits/iteration using longs until quotient fits into an int
                while (i > Integer.MAX_VALUE) {
                    q = i / 100;
                    // really: r = i - (q * 100);
                    r = (int)(i - ((q << 6) + (q << 5) + (q << 2)));
                    i = q;
                    buf[--charPos] = DigitOnes[r];
                    buf[--charPos] = DigitTens[r];
                }

                // Get 2 digits/iteration using ints
                int q2;
                int i2 = (int)i;
                while (i2 >= 65536) {
                    q2 = i2 / 100;
                    // really: r = i2 - (q * 100);
                    r = i2 - ((q2 << 6) + (q2 << 5) + (q2 << 2));
                    i2 = q2;
                    buf[--charPos] = DigitOnes[r];
                    buf[--charPos] = DigitTens[r];
                }

                // Fall thru to fast mode for smaller numbers
                // assert(i2 <= 65536, i2);
                for (;;) {
                    q2 = (i2 * 52429) >>> (16+3);
                    r = i2 - ((q2 << 3) + (q2 << 1));  // r = i2-(q2*10) ...
                    buf[--charPos] = digits[r];
                    i2 = q2;
                    if (i2 == 0) break;
                }
                if (sign != 0) {
                    buf[--charPos] = sign;
                }
            }

            // Requires positive x
            static int stringSize(long x) {
                long p = 10;
                for (int i=1; i<19; i++) {
                    if (x < p)
                        return i;
                    p = (p << 3) + (p << 1);
                }
                return 19;
            }
            
            public void print(String s)
            {
                    int tam = s.length();
                    if(pos + tam > buffer.length)
                            tam = buffer.length - pos;
                    s.getChars(0, tam, buffer, pos);
                    pos += tam;
                    if(pos == buffer.length)
                    {
                            try
                            {
                                    os.write(buffer);
                                    pos = 0;
                                    print(s.substring(tam));
                            }
                            catch(Exception e)
                            {
                                    throw(new RuntimeException());
                            }
                    }
            }

            public void flush() 
            {
                    try 
                    {
                            os.write(buffer, 0, pos);
                            pos = 0;
                            os.flush();
                    }
                    catch (IOException e) 
                    {
                            throw(new RuntimeException());
                    }
            }
    }
	

    
	static class point{
		int x,y;
		public point(int x,int y){
			this.x=x;
			this.y=y;
		}
	}
	
	static int t;
	
	static class segment implements Comparable<segment>{
		int id;
		point p1,p2;
		boolean orient;
		int litros;
		int hijo;
		LinkedList<Integer> papas;
		int solucion;
		public segment(int dd,int x1,int y1,int x2,int y2){
			id=dd;
			p1=new point(x1,y1);
			p2=new point(x2,y2);
			orient=(y1<y2);
			litros=0;
			hijo=-1;
			papas=new LinkedList<Integer>();
			solucion=-1;
		}
		public double eval(double x){
			double m=((double)(p2.y - p1.y))/(p2.x - p1.x);
			double b=p1.y  - m*p1.x;
			return m*x + b;
			
		}
		@Override
		public int compareTo(segment o) {
			return Double.compare(o.eval(t), this.eval(t));
		}
	}
	
	static class event implements Comparable<event>{
		boolean opening;
		point p;
		int segment;
		public event(point pp, int s, boolean o){
			opening=o;
			p=pp;
			segment=s;
		}
		@Override
		public int compareTo(event o) {
			if (o.p.x == this.p.x)
				return (this.opening)?-1:1;
			return this.p.x - o.p.x;
		}
	}
	
	static segment[] array=new segment[40010];
	static int N;
	
	
	public static void main(String args[]) throws NumberFormatException, IOException{
		SuperScanner sc = new SuperScanner();
		SuperWriter sw = new SuperWriter();
		int T=sc.nextInt();
		boolean primero=true;
		for(int c=0;c<T;c++){
			N=sc.nextInt();
			event[] time=new event[2*N];
			int time_index=0;
			for(int i=0;i<N;i++){
		
				int x1=sc.nextInt();
				int y1=sc.nextInt();
				int x2=sc.nextInt();
				int y2=sc.nextInt();
				array[i]=new segment(i,x1,y1,x2,y2);
				time[time_index++]=new event(array[i].p1,i,true);
				time[time_index++]=new event(array[i].p2,i,false);
			}
			Arrays.sort(time);
			TreeMap<segment,Integer> tm=new TreeMap<segment,Integer>();
			t=0;
			int i=0;
			while(i<2*N){
				int nt=time[i].p.x;
				int dt=nt-t;
				if (tm.size()>0){
					segment tmp=tm.firstKey();
					tmp.litros+=dt;
				}
				t=nt;
				int k=i;
				while(k<2*N && time[k].p.x==nt && time[k].opening){
					tm.put(array[time[k].segment], 0);
					k++;
				}
				k=i;
				while(k<2*N && time[k].p.x==nt){
					if (!time[k].opening && !array[time[k].segment].orient){
						segment tmp= tm.higherKey(array[time[k].segment]);
						if (tmp!=null){
							array[time[k].segment].hijo=tmp.id;
							array[tmp.id].papas.add(time[k].segment);
						}
					}
					if (time[k].opening && array[time[k].segment].orient){
						segment tmp= tm.higherKey(array[time[k].segment]);
						if (tmp!=null){
							array[time[k].segment].hijo=tmp.id;
							array[tmp.id].papas.add(time[k].segment);
						}
					}
					k++;
				}
				k=i;
				while(k<2*N && time[k].p.x==nt){
					if (!time[k].opening)
						tm.remove(array[time[k].segment]);
					k++;
				}
				i=k;
			}
			/*
			for(int j=0;j<N;j++)
				System.out.println("p:"+array[j].id+" "+array[j].hijo+"agua:"+array[j].litros);
			*/
			
			for(int k=0;k<N;k++)
				visit(k);
			if (!primero)
				sw.println("");
			for(int k=0;k<N;k++)
				sw.println(array[k].solucion + "");
			primero=false;
		}
		sw.flush();
	}
	
	static int visit(int node){
		if (array[node].solucion!=-1)
			return array[node].solucion;
        int sol=0;
		for(int p: array[node].papas)
			sol+=visit(p);
		sol+=array[node].litros;
		array[node].solucion=sol;
		return sol;
	}

}
