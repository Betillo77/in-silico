package implementacion.reinforcement;

import genericos.reinforcement.Accion;

/**
 *
 * @author santiago, sebastian
 */
public class BJAction implements Accion 
{    
    public static Accion HIT = new BJAction(0);
    public static Accion STAND = new BJAction(1);
    public static Accion DOUBLE = new BJAction(2);
    public static Accion SPLIT = new BJAction(3);
    public static Accion SURRENDER = new BJAction(4);
    public static Accion BET1 = new BJAction(5);
    public static Accion BET2 = new BJAction(6);
    public static Accion BET3 = new BJAction(7);
    public static Accion BET4 = new BJAction(8);
    public static Accion BET5 = new BJAction(9);
    
    int number;

    private BJAction(int n)
    {
    	number = n;
    }
    
    public int compareTo(Accion o) 
    {
    	return number - ((BJAction) o).number; 
    }
}