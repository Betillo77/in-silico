import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;


public class dailyOCR
{
	public static final String pathPrincipal = "";
	public static final String pathSecundario = "\\\\SANTIAGO-PC\\Users\\Public\\dailyOCR\\";
	
	public static long tiempo = 0;
	public static ArrayList <Senal> actuales = new ArrayList <Senal> ();
	public static ArrayList <BidAsk> preciosActuales;
	public static int errores = 0;
	
	static Estrategia breakout1;
	static File b1 = new File(pathPrincipal + "breakout1.o");
	
	static Estrategia breakout2;
	static File b2 = new File(pathPrincipal + "breakout2.o");
	
	static Estrategia range1;
	static File r1 = new File(pathPrincipal + "range1.o");

	public static Estrategia range2;
	static File r2 = new File(pathPrincipal + "range2.o");
	
	static Estrategia momentum1;
	static File m1 = new File(pathPrincipal + "momentum1.o");
	
	static Estrategia momentum2;
	static File m2 = new File(pathPrincipal + "momentum2.o");
	
	static Estrategia technical;
	static File t = new File(pathPrincipal + "technical.o");
	
	static Estrategia joel;
	static File j = new File(pathPrincipal + "joel.o");
	
	static File log = new File(pathPrincipal + "log.txt");
	
	static ArrayList <Estrategia> estrategias;
	
	static Escritor escritorSSI = new Escritor("C:/Program Files (x86)/dailyFX/experts/files/");
	static Escritor escritorTechnical = new Escritor("");
	static Escritor escritorJoel = new Escritor("");
	
	public static void cargarEstrategias()
	{
		if(b1.exists())
		{
			breakout1 = Estrategia.leer(b1);
		}
		else
		{
			breakout1 = new Estrategia(IdEstrategia.BREAKOUT1);
		}
		breakout1.escritor = escritorSSI;
		if(b2.exists())
		{
			breakout2 = Estrategia.leer(b2);
		}
		else
		{
			breakout2 = new Estrategia(IdEstrategia.BREAKOUT2);
		}
		breakout2.escritor = escritorSSI;
		if(r1.exists())
		{
			range1 = Estrategia.leer(r1);
		}
		else
		{
			range1 = new Estrategia(IdEstrategia.RANGE1);
		}
		range1.escritor = escritorSSI;
		if(r2.exists())
		{
			range2 = Estrategia.leer(r2);
		}
		else
		{
			range2 = new Estrategia(IdEstrategia.RANGE2);
		}
		range2.escritor = escritorSSI;
		if(m1.exists())
		{
			momentum1 = Estrategia.leer(m1);
		}
		else
		{
			momentum1 = new Estrategia(IdEstrategia.MOMENTUM1);
		}
		momentum1.escritor = escritorSSI;
		if(m2.exists())
		{
			momentum2 = Estrategia.leer(m2);
		}
		else
		{
			momentum2 = new Estrategia(IdEstrategia.MOMENTUM2);
		}
		momentum2.escritor = escritorSSI;
		if(t.exists())
		{
			technical = Estrategia.leer(t);
		}
		else
		{
			technical = new Estrategia(IdEstrategia.TECHNICAL);
		}
		technical.escritor = escritorTechnical;
		if(j.exists())
		{
			joel = Estrategia.leer(t);
		}
		else
		{
			joel = new Estrategia(IdEstrategia.JOEL);
		}
		joel.escritor = escritorJoel;
		estrategias = new ArrayList <Estrategia> ();
		estrategias.add(breakout1);
		estrategias.add(breakout2);
		estrategias.add(momentum1);
		estrategias.add(momentum2);
		estrategias.add(range1);
		estrategias.add(range2);
	}
	
	public static void inicio()
	{
		int numeroErrores = 0;
		while(true)
		{
			try
			{
				if(breakout1 == null || breakout2 == null || range1 == null || range2 == null || momentum1 == null || momentum2 == null)
				{
					cargarEstrategias();
				}
				Thread.sleep(1000);
				try
				{
					leerSSI(ConexionServidor.leerServidorSSI());
					leerTechnical(ConexionServidor.leerServidorTechnical());
					leerJoel(ConexionServidor.leerServidorJoel());
					escritorSSI.escribir();
					escritorSSI.leerMagicos();
					escritorTechnical.escribir();
					escritorTechnical.leerMagicos();
					escritorJoel.escribir();
					escritorJoel.leerMagicos();
					if(breakout1 == null || breakout2 == null || range1 == null || range2 == null || momentum1 == null || momentum2 == null || joel == null || technical == null)
					{
						cargarEstrategias();
						//TODO Manejo de errores
					}
					else
					{
						breakout1.escribir(b1);
						breakout2.escribir(b2);
						range1.escribir(r1);
						range2.escribir(r2);
						momentum1.escribir(m1);
						momentum2.escribir(m2);
						technical.escribir(t);
						joel.escribir(j);
						numeroErrores = 0;
					}
				}
				catch(Exception e)
				{	
					numeroErrores++;
					if(numeroErrores == 60)
					{
						Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
						{

							@Override
							public void run() 
							{
								try 
								{
									Runtime.getRuntime().exec("reiniciar.bat");
								} 
								catch (IOException e)
								{
									// TODO Manejo de errores
								}
							}
						}));
						System.exit(0);
					}
				}
			}
			catch(Exception e)
			{
				//TODO Manejo de errores
			}
		}
	}
	
	private static void procesarSSI(ArrayList <Senal> senalesLeidas)
	{
		try
		{
			for(Senal senal : senalesLeidas)
			{
				Estrategia actual = darEstrategiaSenal(senal);
				Senal afectada = null;
				if((afectada = actual.tienePar(senal.par)) != null)
				{
					if(senal.compra != afectada.compra)
					{
						actual.agregar(new SenalEntrada(senal.par, TipoSenal.HIT, false, afectada.numeroLotes, 0), afectada, false);
						actual.agregar(new SenalEntrada(senal.par, TipoSenal.TRADE, senal.compra, senal.numeroLotes, senal.precioEntrada), afectada, false);
					}
					if(afectada.numeroLotes > senal.numeroLotes)
					{
						actual.agregar(new SenalEntrada(senal.par, TipoSenal.HIT, false, afectada.numeroLotes - senal.numeroLotes, 0), afectada, false);
					}
					else if(afectada.numeroLotes < senal.numeroLotes)
					{
						//TODO Manejo de errores
					}
				}
				else
				{
					actual.agregar(new SenalEntrada(senal.par, TipoSenal.TRADE, senal.compra, senal.numeroLotes, senal.precioEntrada), afectada, false);
				}
			}
			for(Estrategia actual : estrategias)
			{
				Thread.sleep(1000);
				synchronized(actual.senales)
				{
					for(int i = 0; i < actual.senales.size(); i++)
					{
						Senal senal = null;
						try
						{
							senal = actual.senales.get(i);
						}
						catch(Exception e)
						{
							break;
						}
						boolean encontrada = false;
						for(Senal nueva : senalesLeidas)
						{
							if(actual.id.equals(nueva.estrategia) && senal.par.equals(nueva.par))
							{
								encontrada = true;
								break;
							}
						}
						if(!encontrada)
						{
							if(!senal.manual)
								actual.agregar(new SenalEntrada(senal.par, TipoSenal.HIT, false, senal.numeroLotes, 0), senal, false);
							if(senal.manual && senal.numeroLotes == 0)
								actual.senales.remove(senal);
							i = -1;
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			//TODO Manejo de errores
		}
	}
	
	private static void leerSSI(String entrada)
	{
		String entrada1 = entrada.substring(entrada.indexOf("\"Signal\":["));
		ArrayList <Integer> curoplots = new ArrayList <Integer> ();
		ArrayList <Integer> strategyid = new ArrayList <Integer> ();
		ArrayList <String> symbol = new ArrayList <String> ();
		ArrayList <String> direction = new ArrayList <String> ();
		ArrayList <Double> entryprice = new ArrayList <Double> ();
		ArrayList <Double> bid = new ArrayList <Double> ();
		ArrayList <Double> ask = new ArrayList <Double> ();
		ArrayList <String> currency = new ArrayList <String> ();
		Pattern pattern = Pattern.compile("\\d+,\"strategyId\":\\d+");
		Pattern pattern2 = Pattern.compile("\"strategyId\":\\d+");
		Pattern pattern3 = Pattern.compile("\"symbol\":\"\\w+\"");
		Pattern pattern4 = Pattern.compile("\"direction\":\"\\w+\"");
		Pattern pattern5 = Pattern.compile("\"entryPrice\":\\d+.\\d+");
		Pattern pattern6 = Pattern.compile("\"bid\":\\d+.\\d+");
		Pattern pattern7 = Pattern.compile("\"ask\":\\d+.\\d+");
		Pattern pattern8 = Pattern.compile("\"currency\":\"\\w+\"");
		Matcher matcher = pattern.matcher(entrada1);
		Matcher matcher2 = pattern2.matcher(entrada1);
		Matcher matcher3 = pattern3.matcher(entrada1);
		Matcher matcher4 = pattern4.matcher(entrada1);
		Matcher matcher5 = pattern5.matcher(entrada1);
		Matcher matcher6 = pattern6.matcher(entrada);
		Matcher matcher7 = pattern7.matcher(entrada);
		Matcher matcher8 = pattern8.matcher(entrada);
		while(matcher.find()) 
		{  
			String S = matcher.group();
			S = S.substring(0, 1);
			curoplots.add(Integer.parseInt(S));
		} 
		while(matcher2.find())
		{
			String S = matcher2.group();
			S = S.substring(13);
			strategyid.add(Integer.parseInt(S));  
		}
		while(matcher3.find()) 
		{
			String S = matcher3.group();
			S = S.substring(10);
			S = S.replace("\"", "");
			symbol.add(S);
  		}
		while(matcher4.find()) 
		{
	  		String S = matcher4.group();
	  		S = S.substring(13);
	  		S = S.replace("\"", "");
	  		direction.add(S);
		}
		while (matcher5.find()) 
		{
			String S = matcher5.group();
			S = S.substring(13);
			entryprice.add(Double.parseDouble(S));	
		}
		while (matcher6.find())
		{
			String S = matcher6.group();
			S = S.substring(6);
			bid.add(Double.parseDouble(S));	
		}
		while (matcher7.find())
		{
			String S = matcher7.group();
			S = S.substring(6);
			ask.add(Double.parseDouble(S));	
		}
		while (matcher8.find())
		{
			String S = matcher8.group();
			S = S.substring(12);
	  		S = S.replace("\"", "");
	  		currency.add(S);
		}
		ArrayList <Senal> nuevasSenales = new ArrayList <Senal> ();
		for(int i = 0; i<curoplots.size(); i++)
		{
			Senal actual = new Senal(IdEstrategia.darEstrategia(strategyid.get(i)), direction.get(i).equals("Buy"), Par.convertirPar(symbol.get(i)), curoplots.get(i), entryprice.get(i));
			nuevasSenales.add(actual);
		}
		ArrayList <BidAsk> precio = new ArrayList <BidAsk>();
		for(int i=0;i<bid.size();i++)
		{
			if(Par.convertirPar(currency.get(i)) == null)
			{
				continue;
			}
			BidAsk actual = new BidAsk(bid.get(i), ask.get(i), Par.convertirPar(currency.get(i)));
			precio.add(actual);
		}
		preciosActuales = precio;
		procesarSSI(nuevasSenales);
	}
	

	private static void procesarTechnical(ArrayList <Senal> senalesLeidas)
	{
		//TODO Hacer metodo		
	}
	
	private static void leerTechnical(String entrada)
	{
		procesarTechnical(null);
		//TODO Hacer metodo
	}
	
	private static void procesarJoel(ArrayList <Senal> senalesLeidas)
	{
		//TODO Hacer metodo
	}
	
	private static void leerJoel(String entrada)
	{
		procesarJoel(null);
		//TODO Hacer metodo
	}
	
	public static boolean chequearConexion()
	{
		try
		{
			URL url = new URL("http://www.dailyfx.com");
			URLConnection urlConnection = url.openConnection();
			InputStream inputStream = urlConnection.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String s = "";
			String contents = "";
			while((s = bufferedReader.readLine()) != null)
			{
				contents = contents.concat(s);
			}
			inputStream.close();
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	} 
	
	public static void arreglarConexion()
	{
		try
		{
			Error.agregar("Error de conexion, intentando arreglar");
			Runtime.getRuntime().exec("cmd /c start " + "C:\\" + "primero.bat");
			Thread.sleep(60000);
			if(!chequearConexion())
			{
				Runtime.getRuntime().exec("cmd /c start " + "C:\\" + "segundo.bat");
				Thread.sleep(60000);
			}
		} 
		catch (Exception e)
		{
		}
	}
	
	public static void apagarEquipo()
	{
		try
		{
			Runtime.getRuntime().exec("cmd /c start " + "C:\\" + "tercero.bat");
			Runtime.getRuntime().exit(0);
		}
		catch(Exception e)
		{
			Error.agregar("Error apagando el equipo");
		}
	}

	public static List <Senal> darSenalesEstrategia(IdEstrategia estrategia) 
	{
		if(estrategia.equals(IdEstrategia.BREAKOUT1))
			return breakout1.senales;
		if(estrategia.equals(IdEstrategia.BREAKOUT2))
			return breakout2.senales;
		if(estrategia.equals(IdEstrategia.MOMENTUM1))
			return momentum1.senales;
		if(estrategia.equals(IdEstrategia.MOMENTUM2))
			return momentum2.senales;
		if(estrategia.equals(IdEstrategia.RANGE1))
			return range1.senales;
		if(estrategia.equals(IdEstrategia.RANGE2))
			return range2.senales;
		return null;
	}

	public static Estrategia darEstrategiaSenal(Senal senal)
	{
		Estrategia actual = null;
		if(senal.estrategia.equals(IdEstrategia.BREAKOUT1))
		{
			actual = breakout1;
		}
		if(senal.estrategia.equals(IdEstrategia.BREAKOUT2))
		{
			actual = breakout2;
		}
		if(senal.estrategia.equals(IdEstrategia.RANGE1))
		{
			actual = range1;
		}
		if(senal.estrategia.equals(IdEstrategia.RANGE2))
		{
			actual = range2;
		}
		if(senal.estrategia.equals(IdEstrategia.MOMENTUM1))
		{
			actual = momentum1;
		}
		if(senal.estrategia.equals(IdEstrategia.MOMENTUM2))
		{
			actual = momentum2;
		}
		return actual;
	}
	
	public static synchronized void cerrarSenalManual(Senal senal) 
	{
		synchronized(darEstrategiaSenal(senal).senales)
		{
			Estrategia estrategiaSenal = darEstrategiaSenal(senal);
			estrategiaSenal.agregar(new SenalEntrada(senal.par, TipoSenal.HIT, false, senal.numeroLotes, 0), senal, true);
			estrategiaSenal.escritor.escribir();
			estrategiaSenal.escritor.leerMagicos();
		}
	}
	
	public static synchronized void abrirSenalManual(Senal senal) 
	{
		synchronized(darEstrategiaSenal(senal).senales)
		{
			Estrategia estrategiaSenal = darEstrategiaSenal(senal);
			estrategiaSenal.agregar(new SenalEntrada(senal.par, TipoSenal.TRADE, senal.compra, senal.numeroLotes, senal.precioEntrada), senal, true);
			estrategiaSenal.escritor.escribir();
			estrategiaSenal.escritor.leerMagicos();
		}
	}
	
	public static void main(String [] args)
	{
		ParteGrafica pg = new ParteGrafica();
		JFrame framePrincipal = new JFrame();
		framePrincipal.setMinimumSize(new Dimension(259, 244));
		framePrincipal.setSize(259, 244);
		framePrincipal.add(pg);
		framePrincipal.pack();
		framePrincipal.setVisible(true);
		framePrincipal.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		cargarEstrategias();
		new Thread(new Runnable()
					{

						@Override
						public void run() 
						{
							inicio();
						}
						
					}).start();
	}
}	
