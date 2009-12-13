package Colonia;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;

public class Grafico extends JFrame
{
	private static final long serialVersionUID = 1L;
	public ArrayList <Integer> mejoresActual = new ArrayList <Integer> ();
	public double solucionActual = 0;
	public double iteracionActual = 0;
	public boolean soloNumero = true;
	public Grafico()
	{
		setSize(new Dimension(500, 500));
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 500, 500);
		g.setColor(Color.BLACK);
		ArrayList <Ciudad> ciudades = Lectura.leer(2);
		for(Ciudad a : ciudades)
		{
			g.fillOval(a.x + 20, a.y + 20, 6, 6);
		}
		g.drawString((int)solucionActual  + "", 460, 460);
		for(int i = 0; i < mejoresActual.size() - 1; i++)
			g.drawLine(ciudades.get(mejoresActual.get(i)).x + 23, ciudades.get(mejoresActual.get(i)).y + 23,ciudades.get(mejoresActual.get(i + 1)).x + 23, ciudades.get(mejoresActual.get(i + 1)).y + 23);
	}
	static double mejor;
	public static void main(String [] args)
	{
		Hormigas atomicas = new Hormigas();
		double [][] prueba = Lectura.leer();
	    Grafico g = new Grafico();
		g.setVisible(true);
		atomicas.solucionar(prueba, g);
	}

}
