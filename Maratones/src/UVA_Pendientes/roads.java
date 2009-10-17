package UVA_Pendientes;

import java.util.ArrayList;
import java.util.Scanner;

public class roads 
{
	
	// BinaryHeap class
	//
	// CONSTRUCTION: empty or with initial array.
	//
	// ******************PUBLIC OPERATIONS*********************
	// void insert( x )       --> Insert x
	// Comparable deleteMin( )--> Return and remove smallest item
	// Comparable findMin( )  --> Return smallest item
	// boolean isEmpty( )     --> Return true if empty; else false
	// void makeEmpty( )      --> Remove all items
	// ******************ERRORS********************************
	// Throws UnderflowException for findMin and deleteMin when empty

	/**
	 * Implements a binary heap.
	 * Note that all "matching" is based on the compareTo method.
	 * @author Mark Allen Weiss
	 */
    @SuppressWarnings("unchecked")
	static class BinaryHeap implements PriorityQueue {
	    /**
	     * Construct the binary heap.
	     */
	    public BinaryHeap(int capacidad) {
	        currentSize = 0;
	        array = new Comparable[ capacidad + 1 ];
	    }
	    
	    /**
	     * Insert into the priority queue.
	     * Duplicates are allowed.
	     * @param x the item to insert.
	     * @return null, signifying that decreaseKey cannot be used.
	     */
	    public PriorityQueue.Position insert( Comparable x ) {
	        if( currentSize + 1 == array.length )
	            doubleArray( );
	        
	        // Percolate up
	        int hole = ++currentSize;
	        array[ 0 ] = x;
	        array[ 0 ].establecerIndice(0);
	        
	        for( ; x.compareTo( array[ hole / 2 ] ) < 0; hole /= 2 )
	        {
	            array[ hole ] = array[ hole / 2 ];
	            array[ hole ].establecerIndice(hole);
	        }
	        array[ hole ] = x;
	        array[ hole ].establecerIndice(hole);
	        
	        return null;
	    }
	    
	    /**
	     * @throws UnsupportedOperationException because no Positions are returned
	     * by the insert method for BinaryHeap.
	     */
		public void decreaseKey( PriorityQueue.Position p, Comparable newVal ) {
	        throw new UnsupportedOperationException( "Cannot use decreaseKey for binary heap" );
	    }
	    
	    /**
	     * Find the smallest item in the priority queue.
	     * @return the smallest item.
	     * @throws UnderflowException if empty.
	     */
	    public Comparable findMin( ) {
	        if( isEmpty( ) )
	            throw new UnderflowException( "Empty binary heap" );
	        return array[ 1 ];
	    }
	    
	    /**
	     * Remove the smallest item from the priority queue.
	     * @return the smallest item.
	     * @throws UnderflowException if empty.
	     */
	    public Comparable deleteMin( ) {
	        Comparable minItem = findMin( );
	        array[ 1 ] = array[ currentSize-- ];
	        array[ 1 ].establecerIndice(1);
	        percolateDown( 1 );
	        return minItem;
	    }
	    
	    /**
	     * Test if the priority queue is logically empty.
	     * @return true if empty, false otherwise.
	     */
	    public boolean isEmpty( ) {
	        return currentSize == 0;
	    }
	    
	    /**
	     * Returns size.
	     * @return current size.
	     */
	    public int size( ) {
	        return currentSize;
	    }
	    
	    /**
	     * Make the priority queue logically empty.
	     */
	    public void makeEmpty( ) {
	        currentSize = 0;
	    }
	    
	    
	    private int currentSize;      // Number of elements in heap
	    private Comparable [ ] array; // The heap array
	    
	    /**
	     * Internal method to percolate down in the heap.
	     * @param hole the index at which the percolate begins.
	     */
	    private void percolateDown( int hole ) {
	        int child;
	        Comparable tmp = array[ hole ];
	        
	        for( ; hole * 2 <= currentSize; hole = child ) {
	            child = hole * 2;
	            if( child != currentSize &&
	                    array[ child + 1 ].compareTo( array[ child ] ) < 0 )
	                child++;
	            if( array[ child ].compareTo( tmp ) < 0 )
	            {
	                array[ hole ] = array[ child ];
	                array[ hole ].establecerIndice(hole);
	            }
	            else
	                break;
	        }
	        array[ hole ] = tmp;
	        array[ hole ].establecerIndice(hole);
	    }
	    
	    /**
	     * Internal method to extend array.
	     */
	    private void doubleArray( ) {
	        Comparable [ ] newArray;
	        
	        newArray = new Comparable[ array.length * 2 ];
	        for( int i = 0; i < array.length; i++ )
	            newArray[ i ] = array[ i ];
	        array = newArray;
	    }
	    
	    public void actualizar(vertice a)
	    {
	        Comparable x = a;
	        int hole = a.indicePila;
	        if(x.compareTo( array[ hole / 2 ] ) > 0)
	        {
	        	percolateDown( a.indicePila );
	        }
	        else
	        {
	        	array[ 0 ] = x;
		        array[ hole ].establecerIndice(0);
		        for( ; x.compareTo( array[ hole / 2 ] ) < 0; hole /= 2 )
		        {
		            array[ hole ] = array[ hole / 2 ];
		            array[ hole ].establecerIndice(hole);
		        }
		        array[ hole ] = x;
		        array[ hole ].establecerIndice(hole);
	        }
	    }
	}

	// PriorityQueue interface
	//
	// ******************PUBLIC OPERATIONS*********************
	// Position insert( x )   --> Insert x
	// Comparable deleteMin( )--> Return and remove smallest item
	// Comparable findMin( )  --> Return smallest item
	// boolean isEmpty( )     --> Return true if empty; else false
	// void makeEmpty( )      --> Remove all items
	// int size( )            --> Return size
	// void decreaseKey( p, v)--> Decrease value in p to v
	// ******************ERRORS********************************
	// Throws UnderflowException for findMin and deleteMin when empty

	
	interface Comparable <E>
	{
		public int compareTo(E otro);
		public void establecerIndice(int a);
	}
	/**
	 * PriorityQueue interface.
	 * Some priority queues may support a decreaseKey operation,
	 * but this is considered an advanced operation. If so,
	 * a Position is returned by insert.
	 * Note that all "matching" is based on the compareTo method.
	 * @author Mark Allen Weiss
	 */
	@SuppressWarnings("unchecked")
	interface PriorityQueue {
	    /**
	     * The Position interface represents a type that can
	     * be used for the decreaseKey operation.
	     */
	    public interface Position {
	        /**
	         * Returns the value stored at this position.
	         * @return the value stored at this position.
	         */
			Comparable getValue( );
	    }
	    
	    /**
	     * Insert into the priority queue, maintaining heap order.
	     * Duplicates are allowed.
	     * @param x the item to insert.
	     * @return may return a Position useful for decreaseKey.
	     */
	    Position insert( Comparable x );
	    
	    /**
	     * Find the smallest item in the priority queue.
	     * @return the smallest item.
	     * @throws UnderflowException if empty.
	     */
	    Comparable findMin( );
	    
	    /**
	     * Remove the smallest item from the priority queue.
	     * @return the smallest item.
	     * @throws UnderflowException if empty.
	     */
	    Comparable deleteMin( );
	    
	    /**
	     * Test if the priority queue is logically empty.
	     * @return true if empty, false otherwise.
	     */
	    boolean isEmpty( );
	    
	    /**
	     * Make the priority queue logically empty.
	     */
	    void makeEmpty( );
	    
	    /**
	     * Returns the size.
	     * @return current size.
	     */
	    int size( );
	    
	    /**
	     * Change the value of the item stored in the pairing heap.
	     * This is considered an advanced operation and might not
	     * be supported by all priority queues. A priority queue
	     * will signal its intention to not support decreaseKey by
	     * having insert return null consistently.
	     * @param p any non-null Position returned by insert.
	     * @param newVal the new value, which must be smaller
	     *    than the currently stored value.
	     * @throws IllegalArgumentException if p invalid.
	     * @throws UnsupportedOperationException if appropriate.
	     */
	    void decreaseKey( Position p, Comparable newVal );
	}


	/**
	 * Exception class for access in empty containers
	 * such as stacks, queues, and priority queues.
	 * @author Mark Allen Weiss
	 */
	@SuppressWarnings("serial")
	static class UnderflowException extends RuntimeException {
	    /**
	     * Construct this exception object.
	     * @param message the error message.
	     */
	    public UnderflowException( String message ) {
	        super( message );
	    }
	}
	
	public static void main(String [] args)
	{
		Scanner sc = new Scanner(System.in);
		while(true)
		{
			int nodos = sc.nextInt();
			int vertices = sc.nextInt();
			if(nodos == 0 && vertices == 0)
				return;
			BinaryHeap pila = new BinaryHeap(vertices);
			vertice primero = new vertice();
			primero.n = 0;
			primero.min_distance = 0;
			pila.insert(primero);
			vertice [] listaV = new vertice[nodos];
			listaV[0] = primero;
			for(int i = 1; i < nodos; i++)
			{
				vertice actual = new vertice();
				actual.n = i;
				pila.insert(actual);
				listaV[i] = actual;
			}
			int [][] listaA = new int[nodos][nodos];
			int acum = 0;
			for(int i = 0; i < vertices; i++)
			{
				int a = sc.nextInt();
				int b = sc.nextInt();
				int peso = sc.nextInt();
				listaA[a][b] = peso;
				listaA[b][a] = peso;
				acum += peso;
			}
			while(!pila.isEmpty())
			{
				vertice latest_addition = (vertice) pila.deleteMin();
				if(!latest_addition.is)
					continue;
				latest_addition.is = false;
				if(latest_addition.padre != null)
				{
					latest_addition.padre.minimum.add(latest_addition);
					latest_addition.minimum.add(latest_addition.padre);
				}
				for(int i = 0; i < nodos; i++)
				{
					if(listaA[latest_addition.n][i] != 0)
					{
						if(listaV[i].is && listaA[latest_addition.n][i] < listaV[i].min_distance)
						{
							listaV[i].padre = latest_addition;
							listaV[i].min_distance = listaA[latest_addition.n][i];
							pila.actualizar(listaV[i]);
						}
					}
				}
			}
			int acum2 = 0;
			for(int i = 0; i < nodos; i++)
			{
				acum2 += listaV[i].min_distance;
			}
			System.out.println(acum - acum2);
		}
	}

	
	static class vertice implements Comparable <vertice>
	{
		int n;
		int min_distance = Integer.MAX_VALUE;
		vertice padre = null;
		ArrayList <vertice> minimum = new ArrayList <vertice> (10);
		boolean is = true;
		int indicePila = 0;
		
		public int compareTo(vertice otro) 
		{
			return min_distance - otro.min_distance;
		}

		public void establecerIndice(int i) 
		{
			indicePila = i;
		}
		
		
	}
}
