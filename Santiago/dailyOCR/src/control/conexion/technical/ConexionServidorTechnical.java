package control.conexion.technical;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;

import control.Error;
import control.conexion.ConexionServidor;

public class ConexionServidorTechnical extends ConexionServidor 
{
    private static String leerTechnical()
    {
        try
        {      
                DefaultHttpClient clienteHttp = new DefaultHttpClient();
                BasicClientCookie galleta = new BasicClientCookie("JSESSIONIDSSO", "1D81C3BEE2EA8CDB1E86E50F5216D2F6");
                galleta.setVersion(0);
                galleta.setDomain("plus.dailyfx.com");
                galleta.setPath("/");
                clienteHttp.getCookieStore().addCookie(galleta);
                HttpGet peticionGet = new HttpGet("https://plus.dailyfx.com/tnews/fxcentral/INDEX_TA_RECENT.HTM");
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
                    BufferedWriter bw = new BufferedWriter(new FileWriter("salidaTechnical.txt"));
                    bw.write(sb.toString());
                    bw.close();
                }
                clienteHttp.getConnectionManager().shutdown();
                return sb.toString();
        }
        catch(Exception e)
        {		
    		Error.agregar(e.getMessage() + " Error al leer el analisis tecnico");
            return null;
        }
    }
    
    private static String[] direcciones(String datos)
    {
    	String[] direcciones = new String[7];	
    	Pattern pattern = Pattern.compile("HTM\\w*.htm");
        Matcher matcher = pattern.matcher(datos);
        matcher.find();
        direcciones[0] = matcher.group();
        matcher.find();
        direcciones[1] = matcher.group();
        matcher.find(); 
        direcciones[2] = matcher.group();
        matcher.find();
        direcciones[3] = matcher.group();
        Pattern pattern2 = Pattern.compile(";CAD");
        Matcher matcher2 = pattern2.matcher(datos);
        matcher2.find();
        Pattern pattern3 = Pattern.compile("HTM\\w*.htm");
        Matcher matcher3 = pattern3.matcher(datos.substring(matcher2.end()));
        matcher3.find();
        direcciones[4] = matcher3.group();
        Pattern pattern4 = Pattern.compile(";AUD");
        Matcher matcher4 = pattern4.matcher(datos);
        matcher4.find();
        Pattern pattern5 = Pattern.compile("HTM\\w*.htm");
        Matcher matcher5 = pattern5.matcher(datos.substring(matcher4.end()));
        matcher5.find();
        direcciones[5] = matcher5.group();
        Pattern pattern6 = Pattern.compile(";NZD");
        Matcher matcher6 = pattern6.matcher(datos);
        matcher6.find();
        Pattern pattern7 = Pattern.compile("HTM\\w*.htm");
        Matcher matcher7 = pattern7.matcher(datos.substring(matcher6.end()));
        matcher7.find();
        direcciones[6] = matcher7.group();
    	return direcciones;
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
    
    public static String[] leerServidorTechnical()
    {
    	String[] direcciones = direcciones(leerTechnical());
    	String[] html = new String[7];
    	String[] constantes = {"EURUSD", "USDJPY", "GBPUSD", "USDCHF", "USDCAD", "AUDUSD", "NZDUSD"};
        DefaultHttpClient clienteHttp = new DefaultHttpClient();
        loggear(clienteHttp);
    	for(int j = 0; j < html.length; j++)
    	{
	    	String lectura = "";
	        try
	        {      
	            HttpGet peticionGet = new HttpGet("https://plus.dailyfx.com/tnews/fxcentral/" + direcciones[j]);
	            HttpResponse respuesta = clienteHttp.execute(peticionGet);
	            HttpEntity entity = respuesta.getEntity();
	            respuesta.getStatusLine();
	            StringBuilder sb = new StringBuilder("");
	            if (entity != null)
	            {
	                    InputStream instream = entity.getContent();
	                    int l;
	                    byte[] tmp = new byte[2048];
	                    while ((l = instream.read(tmp)) != -1)
	                    {
	                            for(int i = 0; i < l; i++)
	                            {
	                                    sb.append((char)tmp[i]);
	                            }
	                    }
	                BufferedWriter bw = new BufferedWriter(new FileWriter("salida.txt"));
	                bw.write(sb.toString());
	                bw.close();
	            }
	            lectura = sb.toString();
	            peticionGet.abort();
	        }
	        catch(Exception e)
	        {
	    		Error.agregar(e.getMessage() + " Error al leer los valores del analisis tecnico");
	        }
	        html[j] = constantes[j] + " " + lectura;
    	}
        clienteHttp.getConnectionManager().shutdown();
    	return html;	
    }
}
