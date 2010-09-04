package control.conexion.dailyFx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modelo.Par;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.params.CookieSpecPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.params.BasicHttpParams;

import control.Error;
import control.conexion.ConexionServidor;

public class ConexionServidorDailyFx extends ConexionServidor
{
	private static volatile double VIX = 0;
	private static volatile String cacheSSI = "";
	
    public static String [] leerServidorDailyFX()
    {
    	int j = 0;
    	while(j++ < 20)
    	{
	    	try
	    	{ 	
		        DefaultHttpClient clienteHttp = new DefaultHttpClient();
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
		        HttpGet peticionGet = new HttpGet("https://fxsignals.dailyfx.com/fxsignals-ds/json/all.do");
		        HttpResponse respuesta = clienteHttp.execute(peticionGet);
		        HttpEntity entidadHttp = respuesta.getEntity();
		        respuesta.getStatusLine();
		        StringBuilder sb = new StringBuilder("");
		        if (entidadHttp != null)
		        {
		        	InputStream instream = entidadHttp.getContent();
		        	int numeroLeidos;
		        	byte[] lectura = new byte[2048];
		        	while ((numeroLeidos = instream.read(lectura)) != -1) 
		        	{
		        		for(int i = 0; i < numeroLeidos; i++)
		        		{
		        			sb.append((char) lectura[i]);
		        		}
		        	}
		            BufferedWriter bw = new BufferedWriter(new FileWriter("salidaDailyFX.txt"));
		            bw.write(sb.toString());
		            bw.close();
		        }
		        clienteHttp.getConnectionManager().shutdown();
		        String [] resultado = new String[1];
		        resultado[0] = sb.toString();
		        return resultado;
	    	}
	    	catch(Exception e)
	    	{	
	    		try 
	    		{
					Thread.sleep(5000);
				} 
	    		catch (InterruptedException e1) 
	    		{
	    			Error.agregar("Error en Thread.sleep en leer del servidor dailyFX " + e1.getMessage());
				}
	    	}
    	}
		Error.agregar("Error en leer del servidor dailyFX");
		return null;
    }
    
    public static synchronized void cargarVIX()
    {
    	String error = "";
    	for(int j = 0; j < 100; j++)
    	{
	    	try
	    	{ 	
		        DefaultHttpClient clienteHttp = new DefaultHttpClient();
		        HttpGet peticionGet = new HttpGet(new URI("http://finance.yahoo.com/q?s=%5EVIX"));
		        BasicHttpParams params = new BasicHttpParams();
		        params.setParameter(CookieSpecPNames.DATE_PATTERNS, Arrays.asList("EEE, dd MMM-yyyy-HH:mm:ss z", "EEE, dd MMM yyyy HH:mm:ss z"));
		        peticionGet.setParams(params);
		        HttpResponse respuesta = clienteHttp.execute(peticionGet);
		        HttpEntity entidadHttp = respuesta.getEntity();
		        respuesta.getStatusLine();
		        StringBuilder sb = new StringBuilder("");
		        if (entidadHttp != null)
		        {
		        	InputStream instream = entidadHttp.getContent();
		        	int numeroLeidos;
		        	byte[] lectura = new byte[2048];
		        	while ((numeroLeidos = instream.read(lectura)) != -1) 
		        	{
		        		for(int i = 0; i < numeroLeidos; i++)
		        		{
		        			sb.append((char) lectura[i]);
		        		}
		        	}
		        }
		        clienteHttp.getConnectionManager().shutdown();
		        String salida = sb.toString();
		        Pattern pattern = Pattern.compile("Index Value");
		        Pattern pattern2 = Pattern.compile("\\d+.\\d+<");
				Matcher matcher = pattern.matcher(salida);
				if(matcher.find()) 
				{  
					salida = salida.substring(matcher.end());
				} 
				Matcher matcher2 = pattern2.matcher(salida);
				if(matcher2.find()) 
				{ 
					String temp = matcher2.group();
					temp = temp.substring(0, temp.length() - 1);
					VIX = Double.parseDouble(temp);
					return;
				}
	    	}
	    	catch(Exception e)
	    	{
				error = e.getMessage();
	    	}
    	}
    	Error.agregar(error + " Imposible leer el VIX");
    }
    
    private static void loggear(DefaultHttpClient clienteHttp)
    {
    	try
    	{
    		Scanner sc = new Scanner(new File("js.txt"));
			HttpGet peticionGet1 = new HttpGet("https://plus.dailyfx.com/login/loginForm.jsp");
			HttpGet peticionGet = new HttpGet("https://plus.dailyfx.com/login/j_security_check?" + sc.next());
			clienteHttp.execute(peticionGet1);
			peticionGet1.abort();
			clienteHttp.execute(peticionGet);
			peticionGet.abort();
    	}
    	catch(Exception e)
    	{
    	}
    }
    
    public static String leerPagina(String url, DefaultHttpClient clienteHttp)
    {
    	try
    	{
    		HttpGet peticionGet = new HttpGet(url);
    		HttpResponse respuesta = clienteHttp.execute(peticionGet);
    		HttpEntity entidadHttp = respuesta.getEntity();
    		respuesta.getStatusLine();
    		StringBuilder sb = new StringBuilder("");
    		if (entidadHttp != null)
    		{
    			InputStream instream = entidadHttp.getContent();
    			int numeroLeidos;
    			byte[] lectura = new byte[2048];
    			while ((numeroLeidos = instream.read(lectura)) != -1) 
    			{
    				for(int i = 0; i < numeroLeidos; i++)
    				{
    					sb.append((char) lectura[i]);
    				}
    			}
    		}
    		peticionGet.abort();
    		return sb.toString();
    	}
    	catch(Exception e)
    	{		
    		Error.agregar(e.getMessage() + " Error al leer SSI en lectura de la pagina");
    		return null;
    	}
    }

    private static String url(String pagina)
    {
    	Pattern pattern = Pattern.compile("href='.*\\.html'");
    	Matcher matcher = pattern.matcher(pagina);
    	matcher.find();
    	String encontrado = matcher.group();
    	return encontrado.substring(6, encontrado.length() - 1);
    }

    private static void datos(String pagina)
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

    public static boolean cargarSSI()
    {
    	String error = "";
    	for(int i = 0; i < 100; i++)
    	{
	    	try
		    {
	    		DefaultHttpClient clienteHttp = new DefaultHttpClient();
	    		loggear(clienteHttp);
		    	String pagina = leerPagina("https://plus.dailyfx.com/fxcmideas/intraday-list.do", clienteHttp);
		    	String direccion = "https://plus.dailyfx.com/fxcmideas/" + url(pagina);
		    	if(direccion.equals(cacheSSI))
		    	{
		    		clienteHttp.getConnectionManager().shutdown();
		    		return false;
		    	}
		    	String pagina2 = leerPagina(direccion, clienteHttp);
		    	datos(pagina2);
		    	cacheSSI = direccion;
	    		clienteHttp.getConnectionManager().shutdown();
		    	return true;
		    }
	    	catch(Exception e)
	    	{
	    		error = e.getMessage();
	    	}
    	}
    	Error.agregar(error + " Error al leer SSI en cargarSSI");
    	return true;
    }

    public static synchronized double darVIX()
    {
    	return VIX;
    }
}
