package implementacion.hormigas;

import implementacion.hormigas.ModeloHormigasTSP.HiloHormiga;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JRadioButton;

import java.util.ArrayList;

public class Grafico extends Canvas implements MouseListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	public ArrayList <Integer> mejoresActual = new ArrayList <Integer> ();
	public double solucionActual = 0;
	public ArrayList <Ciudad> ciudades;
	public boolean modoEdicion = true;
	public static Grafico este;
	public boolean terminar = false;
	
	public Grafico()
	{
		addMouseListener(this);
	}
	
	@Override
	public synchronized void paint(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 500, 500);
		g.setColor(Color.BLACK);
		for(Ciudad a : ciudades)
		{
			g.fillOval(a.x, a.y, 6, 6);
		}
		if(!modoEdicion)
		{
			g.drawString((int)solucionActual  + "", 400, 400);
			for(int i = 0; i < mejoresActual.size() - 1; i++)
			{
				if(i != 0)
					g.drawLine(ciudades.get(mejoresActual.get(i - 1)).x + 3, ciudades.get(mejoresActual.get(i - 1)).y + 3, ciudades.get(mejoresActual.get(i)).x + 3, ciudades.get(mejoresActual.get(i)).y + 3);
				g.setColor(Color.RED);
				g.drawLine(ciudades.get(mejoresActual.get(i)).x + 3, ciudades.get(mejoresActual.get(i)).y + 3,ciudades.get(mejoresActual.get(i + 1)).x + 3, ciudades.get(mejoresActual.get(i + 1)).y + 3);
				g.setColor(Color.BLACK);
				try 
				{
					Thread.yield();
					Thread.sleep(150);
				}
				catch (InterruptedException e) 
				{
				}
			}
			if(mejoresActual.size() > 1)
				g.drawLine(ciudades.get(mejoresActual.get(mejoresActual.size() - 2)).x + 3, ciudades.get(mejoresActual.get(mejoresActual.size() - 2)).y + 3,ciudades.get(mejoresActual.get(mejoresActual.size() - 1)).x + 3, ciudades.get(mejoresActual.get(mejoresActual.size() - 1)).y + 3);
		}
	}
	
	public synchronized void terminar()
	{
		terminar = true;
	}
	
	public synchronized void iniciar()
	{
		terminar = false;
	}
	
	public synchronized boolean termino()
	{
		return terminar;
	}

	public synchronized void establecerMejorActual(ArrayList<Integer> mejorSolucion, double pesoMejor) 
	{
		mejoresActual = mejorSolucion;
		solucionActual = pesoMejor;
		new Thread(new Runnable()
								{

									@Override
									public void run() 
									{
										paint(getGraphics());
									}
			
								}).start();
	}
	
	public void modoEdicion()
	{
		modoEdicion = true;
		ciudades.clear();
		solucionActual = 0;
		mejoresActual.clear();
		terminar();
		repaint();
	}

	public void modoSolucion()
	{
		modoEdicion = false;
		HiloHormiga hh = new HiloHormiga();
		hh.matrizDistancias = Lectura.generarDistancias(ciudades);
		ModeloHormigasTSP modelo = new ModeloHormigasTSP();
		hh.modelo = modelo;
		iniciar();
		new Thread(hh).start();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
	}
	
	long anterior = 0;
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if(e.getWhen() != anterior && modoEdicion)
		{
			anterior = e.getWhen();
			Graphics g = getGraphics();
			g.fillOval(e.getX(), e.getY(), 6, 6);
			Ciudad nueva = new Ciudad(e.getX(), e.getY());
			ciudades.add(nueva);
			repaint();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JRadioButton boton = (JRadioButton) e.getSource();
		boton.getModel().setSelected(true);
		if(boton.getText().equals("Modo edicion"))
		{
			modoEdicion();
		}
		else
		{
			modoSolucion();
		}
	}
}
