import java.awt.Robot;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class dailyOCR
{
//	public static final String pathPrincipal = "D:/Documents and Settings/Admin/Escritorio/dailyOcr/";
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
	
	static File log = new File(pathPrincipal + "log.txt");
	
	static ArrayList <Estrategia> estrategias;
	
	static
	{
		if(b1.exists())
		{
			breakout1 = Estrategia.leer(b1);
		}
		else
		{
			breakout1 = new Estrategia(IdEstrategia.BREAKOUT1);
		}
		if(b2.exists())
		{
			breakout2 = Estrategia.leer(b2);
		}
		else
		{
			breakout2 = new Estrategia(IdEstrategia.BREAKOUT2);
		}
		if(r1.exists())
		{
			range1 = Estrategia.leer(r1);
		}
		else
		{
			range1 = new Estrategia(IdEstrategia.RANGE1);
		}
		if(r2.exists())
		{
			range2 = Estrategia.leer(r2);
		}
		else
		{
			range2 = new Estrategia(IdEstrategia.RANGE2);
		}
		if(m1.exists())
		{
			momentum1 = Estrategia.leer(m1);
		}
		else
		{
			momentum1 = new Estrategia(IdEstrategia.MOMENTUM1);
		}
		if(m2.exists())
		{
			momentum2 = Estrategia.leer(m2);
		}
		else
		{
			momentum2 = new Estrategia(IdEstrategia.MOMENTUM2);
		}
		estrategias = new ArrayList <Estrategia> ();
		estrategias.add(breakout1);
		estrategias.add(breakout2);
		estrategias.add(momentum1);
		estrategias.add(momentum2);
		estrategias.add(range1);
		estrategias.add(range2);
	}
	
	@SuppressWarnings("unchecked")
	public static void inicio()
	{
		while(true)
		{
			try
			{
				Thread.sleep(1000);
				Object[] lecturas = leer(ConexionServidor.leerServidor());
				ArrayList <Senal> senalesLeidas = (ArrayList <Senal>) lecturas[0];
				preciosActuales = (ArrayList <BidAsk>) lecturas[1];
				try
				{
					for(Senal senal : senalesLeidas)
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
						Senal afectada = null;
						if((afectada = actual.tienePar(senal.par)) != null)
						{
							if(afectada.numeroLotes < senal.numeroLotes)
							{
								// TODO Manejo de errores
							}
							if(afectada.numeroLotes > senal.numeroLotes)
							{
								actual.agregar(new SenalEntrada(senal.par, TipoSenal.HIT, false, afectada.numeroLotes - senal.numeroLotes, 0), afectada);
							}
						}
						else
						{
							actual.agregar(new SenalEntrada(senal.par, TipoSenal.TRADE, senal.compra, senal.numeroLotes, senal.precioEntrada), afectada);
						}
					}
					int estrategiaActual = 0;
					for(Estrategia actual : estrategias)
					{
						for(Senal senal : actual.senales)
						{
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
								actual.agregar(new SenalEntrada(senal.par, TipoSenal.HIT, false, senal.numeroLotes, 0), senal);
							}
						}
						estrategiaActual++;
					}			
					Escritor.escribir();
					Escritor.leerMagicos();
					breakout1.escribir(b1);
					breakout2.escribir(b2);
					range1.escribir(r1);
					range2.escribir(r2);
					momentum1.escribir(m1);
					momentum2.escribir(m2);
					registrarEstadoActual();
				}
				catch(Exception e)
				{	
					// TODO Manejo de excepciones
				}
			}
			catch(Exception e)
			{
				Error.agregar("Error en el ciclo");
			}
		}
	}
	
	private static void registrarEstadoActual() 
	{
		try 
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(log));
			String separador = System.getProperty("line.separator");
			for(Estrategia actual : estrategias)
			{
				bw.write(actual.id + ":" + separador);
				for(Senal senal : actual.senales)
				{
					bw.write(senal.toString() + separador);
				}
				bw.write(separador);
			}
			bw.close();
		} 
		catch (IOException e)
		{
		}
	}
/*			if(!chequearConexion())
			{
				arreglarConexion();
			}
*/
	
	private static void esperar() 
	{
		try
		{
			for(int i = 0; i < 90; i++)
			{
				Thread.sleep(1000);
				if(new File(pathPrincipal + tiempo + ".txt").exists())
				{
					return;
				}
			}
		}
		catch(Exception e)
		{
			Error.agregar("Error esperando");
		}
	}

	private static void eliminar(long tiempo) 
	{
		try
		{
			new File(pathPrincipal + tiempo + ".txt").delete();
			new File(pathPrincipal + "Io/" + tiempo + ".bmp").delete();
		}
		catch(Exception e)
		{
			Error.agregar("Error eliminando los archivos");
		}
	}
	

	
	public static void copiarArchivo(File entrada, File salida) 
	{
	    try 
	    {
	    	salida.createNewFile();
		    FileInputStream fis  = new FileInputStream(entrada);
		    FileOutputStream fos = new FileOutputStream(salida);
	        byte[] buf = new byte[1024];
	        int i = 0;
	        while ((i = fis.read(buf)) != -1) 
	        {
	            fos.write(buf, 0, i);
	        }
		    if (fis != null)
		    {
		    	fis.close();
		    }
		    if (fos != null)
		    {
		    	fos.close();
		    }
	    }
	    catch (Exception e) 
	    {
	    }
	}


	public static Object[] leer(String entrada)
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
		
		Pattern pattern = Pattern.compile("\"curOpLots\":\\d+");
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

		while(matcher.find()) {  
			String S = matcher.group();
			S = S.substring(12);
			curoplots.add(Integer.parseInt(S));
		}
		  
		while(matcher2.find()) {
			String S = matcher2.group();
			S = S.substring(13);
			strategyid.add(Integer.parseInt(S));  
		}
  
		while(matcher3.find()) {
			String S = matcher3.group();
			S = S.substring(10);
			S = S.replace("\"", "");
			symbol.add(S);
  		}
  
		while(matcher4.find()) {
	  		String S = matcher4.group();
	  		S = S.substring(13);
	  		S = S.replace("\"", "");
	  		direction.add(S);
		}
  
		while (matcher5.find()) {
			String S = matcher5.group();
			S = S.substring(13);
			entryprice.add(Double.parseDouble(S));	
		}
	
		while (matcher6.find()) {
			String S = matcher6.group();
			S = S.substring(6);
			bid.add(Double.parseDouble(S));	
		}
		while (matcher7.find()) {
			String S = matcher7.group();
			S = S.substring(6);
			ask.add(Double.parseDouble(S));	
		}
		while (matcher8.find()) {
			String S = matcher8.group();
			S = S.substring(12);
	  		S = S.replace("\"", "");
	  		currency.add(S);
		}
		
		ArrayList <Senal> nuevasSenales = new ArrayList <Senal> ();
		
		for(int i=0;i<curoplots.size();i++)
		{
			Senal actual = new Senal(IdEstrategia.darEstrategia(strategyid.get(i)), direction.get(i).equals("Buy"), convertirPar(symbol.get(i)), curoplots.get(i), entryprice.get(i));
			nuevasSenales.add(actual);
		}
		
		ArrayList <BidAsk> precio = new ArrayList <BidAsk>();
		for(int i=0;i<bid.size();i++)
		{
			if(convertirPar(currency.get(i)) == null)
			{
				continue;
			}
			BidAsk actual = new BidAsk(bid.get(i),ask.get(i),convertirPar(currency.get(i)));
			precio.add(actual);
		}
		
		Object[] resultado = new Object[2];
		resultado[0] = nuevasSenales;
		resultado[1] = precio;
		
		return resultado;
	}

	public static Par convertirPar(String cuerpo)
	{
		Par par;
		if(cuerpo.contains("EURUSD"))
		{
			par = Par.EURUSD;
		}
		else if(cuerpo.contains("USDJPY"))
		{
			par = Par.USDJPY;
		}
		else if(cuerpo.contains("GBPUSD"))
		{
			par = Par.GBPUSD;
		}
		else if(cuerpo.contains("USDCHF"))
		{
			par = Par.USDCHF;
		}
		else if(cuerpo.contains("EURCHF"))
		{
			par = Par.EURCHF;
		}
		else if(cuerpo.contains("AUDUSD"))
		{
			par = Par.AUDUSD;
		}
		else if(cuerpo.contains("USDCAD"))
		{
			par = Par.USDCAD;
		}
		else if(cuerpo.contains("NZDUSD"))
		{
			par = Par.NZDUSD;
		}
		else if(cuerpo.contains("EURJPY"))
		{
			par = Par.EURJPY;
		}
		else if(cuerpo.contains("GBPJPY"))
		{
			par = Par.GBPJPY;
		}
		else if(cuerpo.contains("CHFJPY"))
		{
			par = Par.CHFJPY;
		}
		else if(cuerpo.contains("GBPCHF"))
		{
			par = Par.GBPCHF;
		}
		else if(cuerpo.contains("EURAUD"))
		{
			par = Par.EURAUD;
		}
		else if(cuerpo.contains("AUDJPY"))
		{
			par = Par.AUDJPY;
		}
		else
		{
			par = null;
			// TODO Manejo de errores
		}
		return par;
	}
	
	
	public static boolean leerEstrategias(int par, String estrategia)
	{
		try
		{
			Thread.sleep(500);
			new Robot().mouseMove(1430, 820);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(500);
			new Robot().mouseMove(500, 300);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(500);
			new Robot().mouseMove(1430, 230);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(500);
			for(int i = 0; i < 14; i++)
			{
				new Robot().keyPress(KeyEvent.VK_UP);
				Thread.sleep(500);
			}
			for(int i = 0; i < par - 1; i++)
			{	
				new Robot().keyPress(KeyEvent.VK_DOWN);
				Thread.sleep(500);
			}
			BufferedImage screencapture = new Robot().createScreenCapture(new Rectangle(545, 250, 250, 600));
			Thread.sleep(500);
			new Robot().mouseMove(200, 885);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
			ImageIO.write(screencapture, "bmp", new File(pathPrincipal + "Io/estrategias.bmp"));
			boolean encontrado = false;
			for(int i = 0; i < 90; i++)
			{
				Thread.sleep(1000);
				if(new File(pathPrincipal + "estrategias.txt").exists())
				{
					encontrado = true;
					break;
				}
			}
			if(encontrado)
			{
				Scanner sc = new Scanner(new File(pathPrincipal + "estrategias.txt"));
				String linea = "";
				while(sc.hasNextLine())
				{
					linea += sc.nextLine();
				}
				sc.close();
				new File(pathPrincipal + "estrategias.txt").delete();
				new File(pathPrincipal + "Io/estrategias.bmp").delete();
				return linea.contains(estrategia);
			}
			else
			{
				new File(pathPrincipal + "estrategias.txt").delete();
				new File(pathPrincipal + "Io/estrategias.bmp").delete();
				return false;
			}
			
		}
		catch(Exception e)
		{
			new File(pathPrincipal + "estrategias.txt").delete();
			new File(pathPrincipal + "Io/estrategias.bmp").delete();
			return false;
		}
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
			Error.agregar("Error de conexinn, intentando arreglar");
			Runtime.getRuntime().exec("cmd /c start " + "C:\\" + "primero.bat");
			Thread.sleep(60000);
			actualizarPagina();
			if(!chequearConexion())
			{
				Runtime.getRuntime().exec("cmd /c start " + "C:\\" + "segundo.bat");
				Thread.sleep(60000);
				actualizarPagina();
			}
		} 
		catch (Exception e)
		{
		}
	}

	public static void actualizarPagina()
	{
		try
		{
			new Robot().mouseMove(1102, 35);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
			new Robot().mouseMove(820, 370);
			Thread.sleep(30000);
			new Robot().mousePress(InputEvent.BUTTON1_MASK);
			new Robot().mouseMove(820, 800);
			new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
			Thread.sleep(5000);
		}
		catch(Exception e)
		{
			Error.agregar("Error interno actualizando pagina");
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
	
	public static void main(String [] args)
	{
		inicio();
	}
}	
