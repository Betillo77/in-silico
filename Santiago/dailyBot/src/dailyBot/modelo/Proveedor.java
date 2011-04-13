package dailyBot.modelo;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


import dailyBot.analisis.Rangos;
import dailyBot.analisis.RegistroHistorial;
import dailyBot.control.AdministradorHilos;
import dailyBot.control.Error;
import dailyBot.control.HiloDaily;
import dailyBot.control.RunnableDaily;
import dailyBot.control.conexion.ConexionMySql;
import dailyBot.modelo.Estrategia.IdEstrategia;

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
	
	protected IdProveedor id;
	protected Rangos[][] rangos = new Rangos[IdEstrategia.values().length][Par.values().length];
	protected boolean[][] activos = new boolean[IdEstrategia.values().length][Par.values().length];
	protected boolean[] cambios = new boolean[IdEstrategia.values().length];
	protected SenalProveedor[][] senales = new SenalProveedor[IdEstrategia.values().length][Par.values().length];;
	protected Escritor escritor;
	protected boolean cambio;
	protected final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(true);
	protected final Lock read = rwl.readLock();
	protected final Lock write = HiloDaily.darWriteLockSeguro(rwl);
	protected final Condition wait = write.newCondition();
	
	public Proveedor()
	{
		for(int i = 0; i < IdEstrategia.values().length; i++)
			for(int j = 0; j < Par.values().length; j++)
				rangos[i][j] = new Rangos();
	}
	
	public Proveedor(IdProveedor iid)
	{
		id = iid;
		for(int i = 0; i < IdEstrategia.values().length; i++)
			for(int j = 0; j < Par.values().length; j++)
				rangos[i][j] = new Rangos();
	}
	
	private void iniciarHiloPersistencia()
	{
		final Proveedor este = this;
		HiloDaily hiloPersistencia = new HiloDaily(new RunnableDaily()
		{
			boolean mensajeEnviado = false, chequeoRealizado = false;
			public void run() 
			{
				HiloDaily.sleep(120000);
				while(true)
				{
					if(id.darProveedor() == null || verificarConsistencia())
					{
						Error.agregar("Error de consistencia en " + id);
						id.iniciarProveedor();
						return;
					}
					esperarCambio();
					Calendar c = Calendar.getInstance();
					int hora = c.get(Calendar.HOUR_OF_DAY);
					int minuto = c.get(Calendar.MINUTE);
					if(minuto > 40)
					{
						mensajeEnviado = chequeoRealizado = false;
					}
					else
					{
						if(!mensajeEnviado && (hora == 7 || hora == 19))
						{
							este.chequearSenales(true);
							mensajeEnviado = true;
							chequeoRealizado = true;
						}
						if(!chequeoRealizado)
						{
							este.chequearSenales(false);
							chequeoRealizado = true;
						}
					}
					ponerUltimaActulizacion(System.currentTimeMillis());
				}
			}
			
		}, 1000000L);
		hiloPersistencia.setName("Presistencia " + id);
		AdministradorHilos.agregarHilo(hiloPersistencia);
	}
	
	public void agregar(SenalEstrategia s, boolean hit)
	{
		boolean activo = false;
		read.lock();
		try
		{
			activo = activos[s.getEstrategia().ordinal()][s.getPar().ordinal()];
		}
		finally
		{
			read.unlock();
		}
		if(activo)
		{
			write.lock();
			try
			{
				cambios[s.getEstrategia().ordinal()] = true;
				if(hit)
				{
					SenalProveedor afectada = senales[s.getEstrategia().ordinal()][s.getPar().ordinal()];
					if(afectada == null)
						Error.agregar("Senal con par: " + s.getPar() + ", estrategia: " + s.getEstrategia() + ", proveedor " + id + " no existe y se intento cerrar.");
					else
					{
						if(!s.isTocoStop() && afectada.getMagico() != 1000)
						{
							Error.agregarConTitulo("rangos", id + " cerrando " + s.getEstrategia() + ", " + s.getPar());
							escritor.cerrar(afectada);
						}
						senales[s.getEstrategia().ordinal()][s.getPar().ordinal()] = null;
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
						String mensaje = "Intentando abrir " + id.toString() + ", " + s.getEstrategia().toString() + ", " + s.getPar().toString();
						if(!rangos[s.getEstrategia().ordinal()][s.getPar().ordinal()].cumple(new RegistroHistorial(s.getPar(), s.isCompra()), true, mensaje))
							afectada.setMagico(1000);
						else
						{
							Error.agregarConTitulo("rangos", id + " abriendo " + s.getEstrategia() + ", " + s.getPar());
							escritor.abrir(afectada);
						}
						senales[s.getEstrategia().ordinal()][s.getPar().ordinal()] = afectada;
					}
				}
			}
			finally
			{
				write.unlock();
			}	
		}
	}
	
	public void tocoStop(SenalEstrategia s) 
	{
		read.lock();
		try
		{
			if(activos[s.getEstrategia().ordinal()][s.getPar().ordinal()] && senales[s.getEstrategia().ordinal()][s.getPar().ordinal()] != null)
				if(senales[s.getEstrategia().ordinal()][s.getPar().ordinal()].getMagico() != 1000)
				{
					Error.agregarConTitulo("escritor", id + " cerrando por stop " + s.getEstrategia() + ", " + s.getPar());
					escritor.cerrar(senales[s.getEstrategia().ordinal()][s.getPar().ordinal()]);
				}
			else if(activos[s.getEstrategia().ordinal()][s.getPar().ordinal()] && senales[s.getEstrategia().ordinal()][s.getPar().ordinal()] == null)
				Error.agregar("Senal con par: " + s.getPar() + ", estrategia: " + s.getEstrategia() + ", proveedor " + id + " no existe y se intento cerrar (toco stop).");
		}
		finally
		{
			read.unlock();
		}
	}
	
	private class ParMagico 
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
		
		public boolean isTocoStop()
		{
			SenalProveedor posible = id == null ? null : senales[id.ordinal()][par.ordinal()];
			if(posible == null)
				return false;
			return posible.darTocoStop();
		}
		
		public int darGanancia()
		{
			SenalProveedor posible = id == null ? null : senales[id.ordinal()][par.ordinal()];
			double precioEntrada = posible == null ? 0 : posible.darPrecioEntrada();
			double precioActual = par.darPrecioActual(esCompra);
			double precioParActual = esCompra ? precioActual - precioEntrada : precioEntrada - precioActual; 
			return precioEntrada > 10 ? (int) Math.round((precioParActual) * 100) : (int) Math.round((precioParActual) * 10000); 
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
			double trailingStop = posible == null ? 0 : posible.darStop();
			double stopDaily = posible == null ? 0 : posible.darStopDaily();
			double precioEntrada = posible == null ? 0 : posible.darPrecioEntrada();
			double precioActual = par.darPrecioActual(esCompra);
			String salida = id + " " + par + " " + magico + " " + esCompra + " Entrada: " + precioEntrada + " Actual: " + precioActual + " P/L: " + darGanancia();
			if(stopDaily != -1)
				salida += " Trailing stop: " + trailingStop + " Stop daily: " + stopDaily;
			return salida;
		}
	} 
	
	protected void chequearSenales(boolean enviarMensaje) 
	{
		read.lock();
		try 
		{ 
			ArrayList <SenalProveedor> senalesEste = new ArrayList <SenalProveedor> (); 
			for(int i = 0; i < senales.length; i++)
				for(int j = 0; senales[0] != null && j < senales[0].length; j++)
					if(senales[i][j] != null)
						senalesEste.add(senales[i][j]);
			String mensaje = id + " OK\n"; 
	        DateFormat df = new SimpleDateFormat("MM/dd/yy hh:mm");
	        Date hoy = Calendar.getInstance().getTime();
			String mensajeCorto = df.format(hoy) + " " + (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) > 11 ? "PM" : "AM") + " OK\n";
			String mensajeError = "";
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
			for(ParMagico pm : parMagicosEsteNoAbiertos) 
			{ 
				if(activos[pm.id.ordinal()][pm.par.ordinal()] && senales[pm.id.ordinal()][pm.par.ordinal()] != null)
				{
					int posibleMagico = escritor.chequearMagico(senales[pm.id.ordinal()][pm.par.ordinal()]);
					if(posibleMagico != -1)
					{
						if(posibleMagico == 0)
						{
							mensaje += pm + " NO_ABIERTO\n";
							mensajeError += pm + " NO_ABIERTO\n";
						}
						else
						{
							pm.magico = posibleMagico;
							parMagicosEste.add(pm);
						}
					}
					else
						mensaje += pm + " EN_COLA\n";
				}
			}
			ArrayList <ParMagico> parMagicosEsteCopia = new ArrayList <ParMagico> (parMagicosEste);
			for(ParMagico pm : parMagicosRealesEste) 
				parMagicosEsteCopia.remove(pm); 
			for(ParMagico pm : parMagicosEste) 
				if(parMagicosRealesEste.remove(pm)) 
				{
					mensaje += pm + " OK\n"; 
					if(id == IdProveedor.HFT)
					{
						mensajeCorto += pm.id.toString().charAt(0) + "";
						mensajeCorto += (char) pm.id.toString().charAt(pm.id.toString().length() - 1) + "";
						mensajeCorto += " " + pm.par;
						for(IdProveedor id : IdProveedor.values())
						{
							if(id.darProveedor().darActivo(pm.id, pm.par))
								mensajeCorto += " " + id.toString().charAt(0);
							else
								mensajeCorto += " -";
						}
						mensajeCorto += " " + (pm.darGanancia() >= 0 ? "+" : "") + pm.darGanancia();
						mensajeCorto += "\n";
					}
				}
			for(ParMagico pm : parMagicosEsteCopia) 
				if(activos[pm.id.ordinal()][pm.par.ordinal()] && pm.magico != 1000)
				{
					boolean tocoStop = pm.isTocoStop();
					mensaje += tocoStop ? pm + " TOCO STOP\n" : pm + " CERRADO_PREMATURAMENTE\n";
					if(!pm.isTocoStop())
						mensajeCorto += pm + " CERRADO_PREMATURAMENTE - ";
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
					if(escritor.chequearMagico(s) == 0)
					{
						s.setMagico(pm.magico); 
						mensajeError += "Asignando magico tentativamente: " + id + " " + s.getPar() + " " + pm.magico;
					}
				} 
				else 
				{
					Error.agregarConTitulo("escritor", id + " cerrando inexistente " + pm.par + ", " + pm.magico);
					escritor.agregarLinea(pm.par + ";SELL;CLOSE;" + pm.magico); 
					mensajeError += pm + " no existe en la bd, eliminado\n";       
				} 
			} 
			escritor.terminarCiclo(); 
			if(id == IdProveedor.HFT)
			{
				FileWriter fw = new FileWriter("/home/santiago/Dropbox/Public/dailyBot.txt", false);
				fw.write(mensajeCorto);
				fw.close();
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
		finally
		{
			read.unlock();
		}
	}

	public void terminarCiclo(IdEstrategia[] estrategias)
	{
		write.lock();
		read.lock();
		try
		{
			boolean cambioA = false;
			for(IdEstrategia id : estrategias)
				cambioA = cambioA || cambios[id.ordinal()];
			if(cambioA)
			{
				escritor.terminarCiclo();
				cambio = true;
				wait.signalAll();
			}
			for(IdEstrategia id : estrategias)
				cambios[id.ordinal()] = false;
		}
		finally
		{
			read.unlock();
			write.unlock();
		}
	}
	
	public void esperarCambio()
	{
		write.lock();
		try
		{
			long tiempoInicial = System.currentTimeMillis();
			while(!cambio && System.currentTimeMillis() - tiempoInicial < 100000)
				try 
				{
					wait.await(120000, TimeUnit.MILLISECONDS);
				}
				catch (InterruptedException e)
				{
					Error.agregar("Error de interrupcion en proveedor " + id);
				}
			cambio = false;
			escribir();
		}
		finally
		{
			write.unlock();
		}
	}
	
	public boolean verificarConsistencia() 
	{
		read.lock();
		try
		{
			return senales == null || id == null || activos == null || escritor == null || cambios == null;
		}
		finally
		{
			read.unlock();
		}
	}
	
	public boolean darActivo(IdEstrategia id, Par p)
	{
		read.lock();
		try
		{
			return activos[id.ordinal()][p.ordinal()];
		}
		finally
		{
			read.unlock();
		}
	}
	
	public void cambiarActivo(IdEstrategia id, Par p, boolean b)
	{
		write.lock();
		try
		{
			if(activos[id.ordinal()][p.ordinal()] && !b)
			{
				if(senales[id.ordinal()][p.ordinal()] != null)
				{
					if(senales[id.ordinal()][p.ordinal()].getMagico() != 1000)
					{
						escritor.cerrar(senales[id.ordinal()][p.ordinal()]);
						escritor.terminarCiclo();
						Error.agregar("Proveedor " + this.id + ", estrategia: " + id + ", par: " + p + " estaba abierta y se desactivo, cerrada manualmente.");
					}
					senales[id.ordinal()][p.ordinal()] = null;
				}
			}
			else if(!activos[id.ordinal()][p.ordinal()] && b)
			{
				SenalEstrategia s = id.darEstrategia().tienePar(p);
				if(s != null && senales[id.ordinal()][p.ordinal()] == null)
				{
					senales[id.ordinal()][p.ordinal()] = new SenalProveedor(this.id, id, p, s.isCompra());
					senales[id.ordinal()][p.ordinal()].setMagico(1000);
					Error.agregar("Proveedor " + this.id + ", estrategia: " + id + ", par: " + p + " estaba abierta y se activo.");
				}
			}
			activos[id.ordinal()][p.ordinal()] = b;
		}
		finally
		{
			write.unlock();
		}
	}
	
	public void escribir()
	{
		read.lock();
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
    	finally
    	{
    		read.unlock();
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

	public Rangos[][] getRangos() 
	{
		read.lock();
		try
		{
			return rangos;
		}
		finally
		{
			read.unlock();
		}
	}

	public void setRangos(Rangos[][] rangos) 
	{
		write.lock();
		try
		{
			this.rangos = rangos;
		}
		finally
		{
			write.unlock();
		}
	}
	
	public boolean[][] getActivos() 
	{
		read.lock();
		try
		{
			return activos;
		}
		finally
		{
			read.unlock();
		}
	}

	public void setActivos(boolean[][] activos) 
	{
		write.lock();
		try
		{
			this.activos = chequearTamanos(activos);
		}
		finally
		{
			write.unlock();
		}
	}

	public List <SenalProveedor> darSenales()
	{
		read.lock();
		try
		{
			LinkedList <SenalProveedor> todas = new LinkedList <SenalProveedor> ();
			for(int i = 0; i < senales.length; i++)
				for(int j = 0; senales[0] != null && j < senales[0].length; j++)
					if(senales[i][j] != null)
						todas.add(senales[i][j]);
			return todas;
		}
		finally
		{
			read.unlock();
		}
	}
	
	public SenalProveedor[][] getSenales() 
	{
		read.lock();
		try
		{
			return senales;
		}
		finally
		{
			read.unlock();
		}
	}

	public void setSenales(SenalProveedor[][] senales) 
	{
		write.lock();
		try
		{
			this.senales = chequearTamanos(senales);
		}
		finally
		{
			write.unlock();
		}
	}

	public IdProveedor getId() 
	{
		read.lock();
		try
		{
			return id;
		}
		finally
		{
			read.unlock();
		}
	}
	
	public void setId(IdProveedor id) 
	{
		write.lock();
		try
		{
			this.id = id;
		}
		finally
		{
			write.unlock();
		}
	}
	
	public void cerrar()
	{
		write.lock();
		try
		{
			escritor.cerrarProceso();
		}
		finally
		{
			write.unlock();
		}
	}
}
