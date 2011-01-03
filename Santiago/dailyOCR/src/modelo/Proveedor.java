package modelo;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import modelo.Estrategia.IdEstrategia;
import control.AdministradorHilos;
import control.Error;
import control.HiloDaily;
import control.RunnableDaily;
import control.conexion.ConexionMySql;

public class Proveedor 
{
	public enum IdProveedor
	{
		HFT("HFT/"), LFT("LFT/"), PREMIUM("Premium/");
		
		Proveedor este = null;
		String path;
		
		private IdProveedor(String p) 
		{
			path = p;
		}
		
		public Proveedor darProveedor()
		{
			if(este == null)
				Error.agregar("Proveedor " + this + " fue llamado antes de ser registrado.");
			return este;
		}
		
		public void iniciarProveedor()
		{
			este = Proveedor.leer(this);
			if(este == null)
				este = new Proveedor(this);
			este.escritor = new Escritor(path);
			este.iniciarHiloPersistencia();
		}
		
	}
	
	private IdProveedor id;
	private boolean[][] activos = new boolean[IdEstrategia.values().length][Par.values().length];
	private boolean[] cambios = new boolean[IdEstrategia.values().length];
	private SenalProveedor[][] senales = new SenalProveedor[IdEstrategia.values().length][Par.values().length];;
	private Escritor escritor;
	private AtomicBoolean cambio = new AtomicBoolean(false);
	
	public Proveedor()
	{
	}
	
	public Proveedor(IdProveedor i)
	{
		id = i;
	}
	
	private void iniciarHiloPersistencia()
	{
		final Proveedor este = this;
		HiloDaily hiloPersistencia = new HiloDaily(new RunnableDaily()
		{
			boolean mensajeEnviado = false, chequeoRealizado = false;
			public void run() 
			{
				while(true)
				{
					synchronized(cambio)
					{
						long tiempoAnterior = System.currentTimeMillis();
						cambio.set(false);
						while(!cambio.get() && ((System.currentTimeMillis() - tiempoAnterior) < 120000))
						{
							try 
							{	
								cambio.wait(120000);
							} 
							catch (InterruptedException e) 
							{
								Error.agregar("Excepcion de interrupcion esperando para grabar en: " + id);
							}
						}
						if((System.currentTimeMillis() - tiempoAnterior) > 300000)
							Error.agregar("Error, ultima persistencia fue hace mas de 5 minutos");
					}
					escribir();
					Calendar c = Calendar.getInstance();
					int hora = c.get(Calendar.HOUR_OF_DAY);
					int minuto = c.get(Calendar.MINUTE);
					if(minuto > 10)
					{
						mensajeEnviado = chequeoRealizado = false;
					}
					else
					{
						if(!mensajeEnviado && (hora == 4 || hora == 10 || hora == 16 || hora == 22))
						{
							este.chequearSenales(true);
							mensajeEnviado = true;
						}
						if(!chequeoRealizado)
						{
							este.chequearSenales(true);
							chequeoRealizado = true;
						}
					}
					ultimaActualizacion = System.currentTimeMillis();
				}
			}
			
		}, 1000000L);
		hiloPersistencia.setName("Presistencia " + id);
		AdministradorHilos.agregarHilo(hiloPersistencia);
	}
	
	protected synchronized void chequearSenales(boolean enviarMensaje) 
	{
		try 
		{ 
			ArrayList <SenalProveedor> senalesEste = new ArrayList <SenalProveedor> (); 
			for(int i = 0; i < senales.length; i++)
				for(int j = 0; senales[0] != null && j < senales[0].length;j++)
					if(senales[i][j] != null)
						senalesEste.add(senales[i][j]);
			String mensaje = id + " OK\n"; 
			String mensajeError = "";

			class ParMagico 
			{ 
				Par par; 
				int magico; 
				IdEstrategia id;
				boolean esCompra;

				public ParMagico(Par p, int m, IdEstrategia i, boolean eC) 
				{ 
					par = p; 
					magico = m; 
					id = i;
					esCompra = eC; 
				} 
				
				@Override
				public boolean equals(Object obj)  
				{ 
					ParMagico otro = (ParMagico) obj; 
					return par.equals(otro.par) && magico == otro.magico && esCompra == otro.esCompra; 
				} 
				
				@Override
				public String toString() 
				{
					SenalProveedor posible = id == null ? null : senales[id.ordinal()][par.ordinal()];
					double precioEntrada = posible == null ? 0 : posible.darPrecioEntrada();
					double trailingStop = posible == null ? 0 : posible.darStop();
					double stopDaily = posible == null ? 0 : posible.darStopDaily();
					double precioActual = par.darPrecioActual(esCompra);
					double precioParActual = esCompra ? precioActual - precioEntrada : precioEntrada - precioActual; 
					int resultado = precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000); 
					String salida = id + " " + par + " " + magico + " " + esCompra + " Entrada: " + precioEntrada + " Actual: " + precioActual + " P/L: " + resultado;
					if(stopDaily != -1)
						salida += " Trailing stop: " + trailingStop + " Stop daily: " + stopDaily;
					return salida;
				}
			} 
			
			ArrayList <ParMagico> parMagicosEste = new ArrayList <ParMagico> ();
			ArrayList <ParMagico> parMagicosEsteNoAbiertos = new ArrayList <ParMagico> ();
			for(SenalProveedor s : senalesEste) 
				if(s.getMagico() != 0) 
					parMagicosEste.add(new ParMagico(s.getPar(), s.getMagico(), s.getEstrategia(), s.isCompra())); 
				else 
					parMagicosEsteNoAbiertos.add(new ParMagico(s.getPar(), s.getMagico(), s.getEstrategia(), s.isCompra())); 
			ArrayList <ParMagico> parMagicosRealesEste = new ArrayList <ParMagico> ();
			for(String s : escritor.chequearSenales()) 
			{ 
				Scanner sc = new Scanner(s); 
				sc.useDelimiter("\\Q;\\E"); 
				Par par = Par.convertirPar(sc.next()); 
				int magico = sc.nextInt(); 
				boolean compra = sc.nextInt() == 1; 
				sc.close(); 
				parMagicosRealesEste.add(new ParMagico(par, magico, null, compra)); 
			}
			
			ArrayList <ParMagico> parMagicosEsteCopia = new ArrayList <ParMagico> (parMagicosEste);
			for(ParMagico pm : parMagicosRealesEste) 
				parMagicosEsteCopia.remove(pm); 
			for(ParMagico pm : parMagicosEste) 
				if(parMagicosRealesEste.remove(pm)) 
					mensaje += pm + " OK\n"; 
			for(ParMagico pm : parMagicosEsteCopia) 
				if(activos[pm.id.ordinal()][pm.par.ordinal()])
				{
					mensaje += pm + " CERRADO_PREMATURAMENTE\n";
					mensajeError += pm + " CERRADO_PREMATURAMENTE\n";
				}
			for(ParMagico pm : parMagicosEsteNoAbiertos) 
			{ 
				if(activos[pm.id.ordinal()][pm.par.ordinal()])
				{
					mensaje += pm + " NO_ABIERTO\n";
					mensajeError += pm + " NO_ABIERTO\n";
				}
			}
			for(ParMagico pm : parMagicosRealesEste) 
			{ 
				SenalProveedor s = null; 
				for(int i = 0; i < IdEstrategia.values().length; i++)
				{
					if(activos[i][pm.par.ordinal()] && senales[i][pm.par.ordinal()] != null && senales[i][pm.par.ordinal()].isCompra() == pm.esCompra && senales[i][pm.par.ordinal()].getMagico() == 0)
					{
						s = senales[i][pm.par.ordinal()];
					}
				}
				if(s != null)
				{ 
					s.setMagico(pm.magico); 
					mensajeError += "Asignando magico tentativamente: " + id + " " + s.getPar() + " " + pm.magico; 
				} 
				else 
				{ 
					escritor.agregarLinea(pm.par + ";SELL;CLOSE;" + pm.magico); 
					mensajeError += pm + " no existe en la bd, eliminado\n";       
				} 
			} 
			escritor.terminarCiclo(); 
			for(Par p : Par.values())
			{
				mensaje += p.debugSenales();
			}
			if(!mensajeError.equals("")) 
				Error.agregar(mensajeError); 
			if(enviarMensaje) 
				Error.agregarInfo(mensaje);
		} 
		catch(Exception e) 
		{ 
			Error.agregar("Error en el metodo " + e.getMessage()); 
		} 
	}

	public synchronized void agregar(SenalEstrategia s, boolean hit)
	{
		if(activos[s.getEstrategia().ordinal()][s.getPar().ordinal()])
		{
			cambios[s.getEstrategia().ordinal()] = true;
			cambio.set(true);
			if(hit)
			{
				SenalProveedor afectada = senales[s.getEstrategia().ordinal()][s.getPar().ordinal()];
				if(afectada == null)
					Error.agregar("Senal con par: " + s.getPar() + ", estrategia: " + s.getEstrategia() + ", proveedor " + id + " no existe y se intento cerrar.");
				else
				{
					senales[s.getEstrategia().ordinal()][s.getPar().ordinal()] = null;
					if(!s.isTocoStop())
						escritor.cerrar(afectada);
				}
			}
			else
			{
				SenalProveedor afectada = senales[s.getEstrategia().ordinal()][s.getPar().ordinal()];
				if(afectada != null)
					Error.agregar("Senal con par: " + s.getPar() + ", estrategia: " + s.getEstrategia() + ", proveedor " + id + " ya existe y se intento abrir otra vez.");
				else
				{
					afectada = new SenalProveedor(id, s.getEstrategia(), s.getPar(), s.isCompra());
					senales[s.getEstrategia().ordinal()][s.getPar().ordinal()] = afectada;
					escritor.abrir(afectada);
				}
			}
		}
	}
	
	public synchronized void tocoStop(SenalEstrategia s) 
	{
		if(activos[s.getEstrategia().ordinal()][s.getPar().ordinal()] && senales[s.getEstrategia().ordinal()][s.getPar().ordinal()] != null)
			escritor.cerrar(senales[s.getEstrategia().ordinal()][s.getPar().ordinal()]);
		else if(activos[s.getEstrategia().ordinal()][s.getPar().ordinal()] && senales[s.getEstrategia().ordinal()][s.getPar().ordinal()] == null)
			Error.agregar("Senal con par: " + s.getPar() + ", estrategia: " + s.getEstrategia() + ", proveedor " + id + " no existe y se intento cerrar (toco stop).");
	}
	
	public synchronized void terminarCiclo(IdEstrategia[] estrategias)
	{
		boolean cambioA = false;
		for(IdEstrategia id : estrategias)
			cambioA = cambioA || cambios[id.ordinal()];
		if(cambioA)
		{
			escritor.terminarCiclo();
			synchronized(cambio)
			{
				cambio.notifyAll();
			}
		}
		for(IdEstrategia id : estrategias)
			cambios[id.ordinal()] = false;
	}
	
	public synchronized boolean darActivo(IdEstrategia id, Par p)
	{
		return activos[id.ordinal()][p.ordinal()];
	}
	
	public synchronized void cambiarActivo(IdEstrategia id, Par p, boolean b)
	{
		activos[id.ordinal()][p.ordinal()] = b;
	}
	
	public synchronized void escribir()
	{
    	try
    	{
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        XMLEncoder encoder = new XMLEncoder(baos);
	        encoder.writeObject(this);
	        encoder.close();
	        String salida = new String(baos.toByteArray());
	        ConexionMySql.guardarPersistencia(id, salida);
    	}
    	catch(Exception e)
    	{
    		Error.agregar("Error en la escritura en la base de datos: " + id.name() + " " + e.getMessage());
    	}
	}
	
	public static Proveedor leer(IdProveedor id)
	{
    	try
    	{
	    	String entrada = ConexionMySql.cargarPersistencia(id);
	    	if(entrada.length() < 10)
	    		return null;
	    	char[] entradaChar = entrada.toCharArray();
	    	byte[] entradaByte = new byte[entradaChar.length];
	    	int i = 0;
	    	for(char c : entradaChar)
	    	{
	    		entradaByte[i++] = (byte) c;
	    	}
	    	XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(entradaByte));
	    	Proveedor p = (Proveedor) (decoder.readObject());
	    	decoder.close();
	    	return p;
    	}
    	catch(Exception e)
    	{
    		Error.agregar("Error de lectura de base de datos: " + id.name());
    		Error.reiniciarSinPersistir();
    		return null;
    	}
	}
	
	private SenalProveedor[][] chequearTamanos(SenalProveedor[][] arreglo)
	{
		int tamX = IdEstrategia.values().length;
		int tamY = Par.values().length;
		if(arreglo.length != tamX || arreglo.length == 0 || (arreglo.length != 0 && arreglo[0].length != tamY))
		{
			SenalProveedor[][] arregloNuevo = new SenalProveedor[tamX][tamY];
			for(int i = 0; i < Math.min(arreglo.length, arregloNuevo.length); i++)
				if(arregloNuevo.length != 0 && arreglo.length != 0)
					for(int j = 0; j < Math.min(arreglo[0].length, arregloNuevo[0].length); j++)
						arregloNuevo[i][j] = arreglo[i][j];
			return arregloNuevo;
		}
		else
			return arreglo;
	}
	
	private boolean[][] chequearTamanos(boolean[][] arreglo)
	{
		int tamX = IdEstrategia.values().length;
		int tamY = Par.values().length;
		if(arreglo.length != tamX || arreglo.length == 0 || arreglo[0].length != tamY)
		{
			boolean[][] arregloNuevo = new boolean[tamX][tamY];
			for(int i = 0; i < Math.min(arreglo.length, arregloNuevo.length); i++)
				if(arregloNuevo.length != 0 && arreglo.length != 0)
					for(int j = 0; j < Math.min(arreglo[0].length, arregloNuevo[0].length); j++)
						arregloNuevo[i][j] = arreglo[i][j];
			return arregloNuevo;
		}
		else
			return arreglo;
	}

	public boolean[][] getActivos() 
	{
		return activos;
	}

	public void setActivos(boolean[][] activos) 
	{
		this.activos = chequearTamanos(activos);
	}

	public synchronized List <SenalProveedor> darSenales()
	{
		LinkedList <SenalProveedor> todas = new LinkedList <SenalProveedor> ();
		for(int i = 0; i < senales.length; i++)
			for(int j = 0; senales[0] != null && j < senales[0].length; j++)
				if(senales[i][j] != null)
					todas.add(senales[i][j]);
		return todas;
	}
	
	public SenalProveedor[][] getSenales() 
	{
		return senales;
	}

	public void setSenales(SenalProveedor[][] senales) 
	{
		this.senales = chequearTamanos(senales);
	}

	public IdProveedor getId() 
	{
		return id;
	}
	
	public void setId(IdProveedor id) 
	{
		this.id = id;
	}
}
