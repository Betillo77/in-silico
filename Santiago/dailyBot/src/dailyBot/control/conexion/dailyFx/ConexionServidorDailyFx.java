package dailyBot.control.conexion.dailyFx;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.params.CookieSpecPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.BasicHttpParams;

import dailyBot.control.Error;
import dailyBot.control.HiloDaily;
import dailyBot.control.Propiedades;
import dailyBot.control.conexion.ConexionServidor;
import dailyBot.modelo.Par;

public class ConexionServidorDailyFx extends ConexionServidor
{
	private static abstract class RespuestaHttp implements Callable <String>
	{
		protected DefaultHttpClient clienteHttp;
		protected HttpGet peticionGet;
		protected ExecutorService executor;
		protected StringBuilder sb;
		protected byte[] lectura;
		protected ReentrantLock lock = new ReentrantLock();
		
		@Override
		public String call() throws Exception
		{
			return leerRespuesta(clienteHttp.execute(peticionGet));
		}
		
		public String leerRespuesta(HttpResponse respuesta) throws IllegalStateException, IOException
		{
			sb.setLength(0);
	        HttpEntity entidadHttp = respuesta.getEntity();
	        respuesta.getStatusLine();
	        if (entidadHttp != null)
	        {
	        	InputStream instream = entidadHttp.getContent();
	        	int numeroLeidos;
	        	while ((numeroLeidos = instream.read(lectura)) != -1) 
	        	{
	        		for(int i = 0; i < numeroLeidos; i++)
	        		{
	        			sb.append((char) lectura[i]);
	        		}
	        	}
	        }
	        return sb.toString();
		}
	}
	
	private static class AyudanteLeerServidorDailyFX extends RespuestaHttp
	{
		private static AyudanteLeerServidorDailyFX instancia = new AyudanteLeerServidorDailyFX();
		
		private AyudanteLeerServidorDailyFX()
		{
			clienteHttp = new DefaultHttpClient();
	        BasicClientCookie galleta  =  new BasicClientCookie("JSESSIONID","292E82337F956A043C63CB80051101BF"); 
	        BasicClientCookie galleta1 = new BasicClientCookie(" s_cc","true"); 
	        BasicClientCookie galleta2 = new BasicClientCookie("s_PVnumber", "4"); 
	        BasicClientCookie galleta3 = new BasicClientCookie("s_sq","%5B%5BB%5D%5D");
	        BasicClientCookie galleta4 = new BasicClientCookie("JSESSIONIDSSO", "0E6DACB1E886BC4A0DD46EB443DAF7D9");
	        galleta.setVersion(0);
	        galleta1.setVersion(0);
	        galleta2.setVersion(0);
	        galleta3.setVersion(0);
	        galleta4.setVersion(0);
	        galleta.setDomain("plus.dailyfx.com");
	        galleta1.setDomain("plus.dailyfx.com");
	        galleta2.setDomain("plus.dailyfx.com");
	        galleta3.setDomain("plus.dailyfx.com");
	        galleta4.setDomain("plus.dailyfx.com");
	        galleta.setPath("/fxsignals");
	        galleta1.setPath("/");
	        galleta2.setPath("/");
	        galleta3.setPath("/");
	        galleta4.setPath("/");
	        clienteHttp.getCookieStore().addCookie(galleta);
	        clienteHttp.getCookieStore().addCookie(galleta1);
	        clienteHttp.getCookieStore().addCookie(galleta2);
	        clienteHttp.getCookieStore().addCookie(galleta3);
	        clienteHttp.getCookieStore().addCookie(galleta4);
	        peticionGet = new HttpGet("https://fxsignals.dailyfx.com/fxsignals-ds/json/all.do");
	        executor = Executors.newSingleThreadExecutor();
	        sb = new StringBuilder("");
	        lectura = new byte[32768];
		}
	}

    public static String [] leerServidorDailyFX()
    {
    	int j = 0;
    	while(j++ < 100)
    	{
        	AyudanteLeerServidorDailyFX.instancia.lock.lock();
	    	try
	    	{ 	
		        Future <String> future = AyudanteLeerServidorDailyFX.instancia.executor.submit(AyudanteLeerServidorDailyFX.instancia);
		        String respuesta;
		        try
		        {
		        	respuesta = future.get(10, TimeUnit.SECONDS);
		        }
		        catch(Exception e)
		        {
		        	if(j == 100)
		        	{
		        		Error.agregar("Error en lectura interna servidor DailyFX, reiniciando despues de 10 minutos");
		        		Error.reiniciar();
		        	}
		        	if(j % 10 == 0)
		        	{
			    		AyudanteLeerServidorDailyFX.instancia.lock.unlock();
			    		AyudanteLeerServidorDailyFX.instancia = new AyudanteLeerServidorDailyFX();
			    		AyudanteLeerServidorDailyFX.instancia.lock.lock();
			    		HiloDaily.sleep(9000);
		        	}
					HiloDaily.sleep(6000);
		    		continue;
		        }
		        String [] resultado = new String[1];
		        resultado[0] = respuesta;
		        return resultado;
	    	}
	    	catch(Exception e)
	    	{	
				HiloDaily.sleep(10000);
	    	}
	    	finally
	    	{
	    		AyudanteLeerServidorDailyFX.instancia.lock.unlock();
	    	}
    	}
		return null;
    }
    
	private static class AyudanteCargarVIX extends RespuestaHttp
	{
		private static AyudanteCargarVIX instancia = new AyudanteCargarVIX();
		private AtomicLong VIX = new AtomicLong();
		
		private AyudanteCargarVIX()
		{
        	clienteHttp = new DefaultHttpClient();
        	peticionGet = new HttpGet("http://finance.yahoo.com/q?s=%5EVIX");
		    BasicHttpParams params = new BasicHttpParams();
		    params.setParameter(CookieSpecPNames.DATE_PATTERNS, Arrays.asList("EEE, dd MMM-yyyy-HH:mm:ss z", "EEE, dd MMM yyyy HH:mm:ss z"));
		    peticionGet.setParams(params);
	        executor = Executors.newSingleThreadExecutor();
	        sb = new StringBuilder("");
	        lectura = new byte[2048];
		}
	}
    
    public static void cargarVIX()
    {
    	String error = "";
    	for(int j = 0; j < 100; j++)
    	{
    		AyudanteCargarVIX.instancia.lock.lock();
	    	try
	    	{
	    		Future <String> future = AyudanteCargarVIX.instancia.executor.submit(AyudanteCargarVIX.instancia);
		        String salida = future.get(10, TimeUnit.SECONDS);
		        Pattern pattern = Pattern.compile("Index Value");
		        Pattern pattern2 = Pattern.compile("\\d+.\\d+<");
				Matcher matcher = pattern.matcher(salida);
				if(matcher.find())
					salida = salida.substring(matcher.end());
				Matcher matcher2 = pattern2.matcher(salida);
				if(matcher2.find()) 
				{ 
					String temp = matcher2.group();
					temp = temp.substring(0, temp.length() - 1);
  				    AyudanteCargarVIX.instancia.VIX.getAndSet(Double.doubleToLongBits(Double.parseDouble(temp)));
					return;
				}
	    	}
	    	catch(Exception e)
	    	{
				error += " " + e.getMessage();
				HiloDaily.sleep(6000);
	    	}
	    	finally
	    	{
	    		AyudanteCargarVIX.instancia.lock.unlock();
	    	}
    	}
    	Error.agregar(error + " Imposible leer el VIX");
    }
    
    public static double darVIX()
    {
    	return Double.longBitsToDouble(AyudanteCargarVIX.instancia.VIX.getAndAdd(0));
    }
    
	private static class AyudanteCargarSSI extends RespuestaHttp
	{
		private static AyudanteCargarSSI instancia = new AyudanteCargarSSI();
		private String cacheSSI;
		
		private AyudanteCargarSSI()
		{
			clienteHttp = new DefaultHttpClient();
	        executor = Executors.newSingleThreadExecutor();
	        sb = new StringBuilder("");
	        lectura = new byte[2048];
		}
		
	    private void loggear()
	    {
	    	for(int i = 0; i < 10; i++)
	    	{
		    	try
		    	{
		    		String datos = Propiedades.darPropiedad("dailyBot.control.conexion.dailyFx.ConexionServidorDailyFx.js");
					peticionGet = new HttpGet("https://plus.dailyfx.com/login/loginForm.jsp");
		    		Future <String> future = executor.submit(this);
			        future.get(60, TimeUnit.SECONDS);
			        peticionGet.abort();
			        peticionGet = new HttpGet("https://plus.dailyfx.com/login/j_security_check?" + datos);
		    		future = executor.submit(this);
			        future.get(60, TimeUnit.SECONDS);
			        peticionGet.abort();
			        return;
		    	}
		    	catch(Exception e)
		    	{
		    		Error.agregarInfo(e.getClass() + " " + e.getMessage() + " error loggeando");
		    		HiloDaily.sleep(30000);
		    	}
	    	}
	    	Error.agregar("Error logeando a DailyFX despues de 10 intentos, reiniciando");
	    	Error.reiniciar();
	    }
	    
	    private String url(String pagina)
	    {
	    	Pattern pattern = Pattern.compile("href='.*\\.html'");
	    	Matcher matcher = pattern.matcher(pagina);
	    	matcher.find();
	    	String encontrado = matcher.group();
	    	return encontrado.substring(6, encontrado.length() - 1);
	    }
	    
	    public String leerPagina(String url)
	    {
	    	try
	    	{
	    		peticionGet = new HttpGet(url);
	    		Future <String> future = executor.submit(this);
	    		String salida = future.get(120, TimeUnit.SECONDS);
	    		peticionGet.abort();
	    		return salida;
	    	}
	    	catch(Exception e)
	    	{		
	    		Error.agregar(e.getMessage() + " Error al leer SSI en lectura de la pagina");
	    		return null;
	    	}
	    }
	    
	    private void datos(String pagina)
	    {	
	    	try
	    	{
		    	String [] constantes = {"EURUSD", "GBPUSD", "GBPJPY", "USDJPY", "USDCHF", "USDCAD", "AUDUSD", "NZDUSD"};
		    	for(int i = 0 ; i < constantes.length; i++)
		    	{
		    		Pattern pattern = Pattern.compile(constantes[i] + " stands at -?\\d+.\\d+");
		    		Matcher matcher = pattern.matcher(pagina);
		    		if(matcher.find())
		    		{
		    			Par actual = Par.stringToPar(constantes[i]);
		    			actual.ponerSSI(Double.parseDouble(matcher.group().substring(17)));
		    		}
		    	}
	    	}
	    	catch(Exception e)
	    	{
	    		Error.agregar(e.getMessage() + " Error al leer SSI en el parse");
	    	}
	    }
	}

    public static boolean cargarSSI()
    {
    	String error = "";
    	for(int i = 0; i < 100; i++)
    	{
    		AyudanteCargarSSI.instancia.lock.lock();
	    	try
		    {
	    		AyudanteCargarSSI.instancia.loggear();
		    	String pagina = AyudanteCargarSSI.instancia.leerPagina("https://plus.dailyfx.com/fxcmideas/intraday-list.do");
		    	String direccion = "https://plus.dailyfx.com/fxcmideas/" + AyudanteCargarSSI.instancia.url(pagina);
		    	if(direccion.equals(AyudanteCargarSSI.instancia.cacheSSI))
		    		return false;
		    	String pagina2 = AyudanteCargarSSI.instancia.leerPagina(direccion);
		    	AyudanteCargarSSI.instancia.datos(pagina2);
		    	AyudanteCargarSSI.instancia.cacheSSI = direccion;
		    	Error.agregarInfo("SSI cargado");
		    	return true;
		    }
	    	catch(Exception e)
	    	{
	    		error += " " + e.getMessage();
	    	}
	    	finally
	    	{
	    		AyudanteCargarSSI.instancia.lock.unlock();
	    	}
    	}
    	Error.agregar(error + " Error al leer SSI en cargarSSI");
    	return true;
    }
}
