package control.conexion;
/*
 * ====================================================================
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more

 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */


import java.io.BufferedWriter;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.event.StoreEvent;
import javax.mail.event.StoreListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;


import control.Error;
import control.SistemaJoel;

public class ConexionServidor
{
	
	private static final String jsDailyFX = "0E6DACB1E886BC4A0DD46EB443DAF7D9";
	
	@SuppressWarnings("unused")
	private static final String jsTechnical = "DD8561C6129E3E909C6E617163A9D5B7";
	
    public static String [] leerServidorDailyFX()
    {
    	try
    	{ 	
	        DefaultHttpClient clienteHttp = new DefaultHttpClient();
	        BasicClientCookie galleta  =  new BasicClientCookie("JSESSIONID","292E82337F956A043C63CB80051101BF"); 
	        BasicClientCookie galleta1 = new BasicClientCookie(" s_cc","true"); 
	        BasicClientCookie galleta2 = new BasicClientCookie("s_PVnumber", "4"); 
	        BasicClientCookie galleta3 = new BasicClientCookie("s_sq","%5B%5BB%5D%5D");
	        BasicClientCookie galleta4 = new BasicClientCookie("JSESSIONIDSSO", jsDailyFX);
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
    		Error.agregar(e.getMessage() + " Error en leer del servidor");
    		return null;
    	}
    }
    
    public static String leerTechnical()
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
    
    public static String[] direcciones(String datos)
    {
    	String [] direcciones = new String[7];	
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
    
    public static String [] leerServidorTechnical()
    {
    	String [] direcciones = direcciones(leerTechnical());
    	String [] html = new String[7];
    	String [] constantes = {"EURUSD", "USDJPY", "GBPUSD", "USDCHF", "USDCAD", "AUDUSD", "NZDUSD"};
    	for(int j = 0; j < html.length; j++)
    	{
	    	String lectura = "";
	        try
	        {      
	            DefaultHttpClient clienteHttp = new DefaultHttpClient();
	            BasicClientCookie galleta = new BasicClientCookie("JSESSIONIDSSO", "DD8561C6129E3E909C6E617163A9D5B7");
	            galleta.setVersion(0);
	            galleta.setDomain("plus.dailyfx.com");
	            galleta.setPath("/");
	            clienteHttp.getCookieStore().addCookie(galleta);
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
	            clienteHttp.getConnectionManager().shutdown();
	            lectura = sb.toString();
	        }
	        catch(Exception e)
	        {
	    		Error.agregar(e.getMessage() + " Error al leer los valores del analisis tecnico");
	        }
	        html[j] = constantes[j] + " " + lectura;
    	}
    	return html;	
    }
    
    public static boolean isJoel(Message mensaje)
    {
    	try 
    	{
			if(mensaje.getFrom()[0].toString().equals("DailyFX <jskruger@fxcm.com>"))
			{		
				return true;
			}
		} 
    	catch (MessagingException e)
    	{	
    		Error.agregar("Error al recibir el mensaje " + e.getMessage());	
		}
    	return false;
    }

    public static String [] leerServidorJoel()
    {
        String protocol = null;
        String host = null;
        String user = null;
        String password = null;
        String mbox = null;
        String url = null;
        int port = -1;
        boolean debug = false;
        boolean showAlert = false;
		java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		File file = new File(ConexionServidorMensajes.passwordRoute);
		String clave = "";
		try
		{
			Scanner sc = new Scanner(file);
			clave = sc.next();
			sc.close();
		} 
		catch(FileNotFoundException e) 
		{
			Error.agregar("No se encuentra el archivo con la clave del correo " + e.getMessage());
			e.printStackTrace();
			return new String[0];
	    }
		
		//pop.gmail.com
    	String argv[] = {"-T", "pop3s", "-H", "pop3.live.com", "-U", "jorgeadrianmartinez@hotmail.com", "-P", clave};
    	ArrayList <String> listaSenales = new ArrayList <String> ( );
    	int NumeroDeMensajesAnterior = 0;
    	int msgnum = -1;
    	int optind;
    	for (optind = 0; optind < argv.length; optind++) 
    	{
    		if (argv[optind].equals("-T"))
    			protocol = argv[++optind];
    		else if (argv[optind].equals("-H"))
    			host = argv[++optind];
    		else if (argv[optind].equals("-U"))
    			user = argv[++optind];
    		else if (argv[optind].equals("-P"))
    			password = argv[++optind];
	     	else if (argv[optind].equals("-D"))
	     		debug = true;
	     	else if (argv[optind].equals("-f"))
	     		mbox = argv[++optind];
	     	else if (argv[optind].equals("-L"))
	     		url = argv[++optind];
	     	else if (argv[optind].equals("-p"))
	     		port = Integer.parseInt(argv[++optind]);
	     	else if (argv[optind].equals("-a"))
	     		showAlert = true;
	     	else if (argv[optind].equals("--"))
	     	{
	     		optind++;
    			break;
	     	}
	     	else if (!argv[optind].startsWith("-"))
	     		break;
    	}
    	NumeroDeMensajesAnterior = SistemaJoel.numeroCorreosAnterior;
    	try 
    	{
    		if (optind < argv.length)
    			msgnum = Integer.parseInt(argv[optind]);
    		Properties props = System.getProperties();
    		Session session = Session.getInstance(props, null);
    		session.setDebug(debug);
	  	    Store store = null;
	  	    if (url != null) 
	  	    {
	  	    	URLName urln = new URLName(url);
	  	    	store = session.getStore(urln);
	  	    	if (showAlert)
	  	    	{
	  	    		store.addStoreListener(new StoreListener() 
	  	    			   			      {
	  	    							  	  public void notification(StoreEvent e)
	  	    							  	  {
	  	    							  	  }
	  	    			   			      });
	  	    	}
	  	    	store.connect();
	  	    } 
	  	    else 
	  	    {
	  	    	if (protocol != null)		
	  	    		store = session.getStore(protocol);
	  	    	else
	  	    		store = session.getStore();
	  	    	if (host != null || user != null || password != null)
	  	    		store.connect(host, port, user, password);
	  	    	else
	  	    		store.connect();
	  	    }
	  	    Folder folder = store.getDefaultFolder();
	  	    if (folder == null) 
	  	    {
	  	    	Error.agregar("No se encontro la carpeta en el email.");
	  	    	return null;
	  	    }
		
	  	    if (mbox == null)
	  	    	mbox = "INBOX";
	  	    folder = folder.getFolder(mbox);
			if (folder == null)
				Error.agregar("Carpeta invalida en el email.");
			try
			{
				folder.open(Folder.READ_WRITE);
			}
			catch(MessagingException ex)
			{
				folder.open(Folder.READ_ONLY);
			}
			catch(Exception e)
			{
				Error.agregar("Error abriendo la carpeta en el email.");
				return null;
			}
			int totalMessages = folder.getMessageCount();
			if(totalMessages > NumeroDeMensajesAnterior)
			{
				SistemaJoel.numeroCorreosAnterior = totalMessages;
				if(totalMessages == 0)
				{
					folder.close(false);
					store.close();
					return new String[0];
				}	  
			    if (msgnum == -1)
			    {
			    	Message[] msgs = folder.getMessages();				
					for (int i =0; i < msgs.length; i++)
					{						
						if(isJoel(msgs[i]))
						{				
							listaSenales.add(msgs[i].getSubject());	
					        msgs[i].setFlag(Flags.Flag.DELETED, true);
						}
					}
			    }
			    else
			    {
					try 
					{
						folder.getMessage(msgnum);
					}
					catch (IndexOutOfBoundsException iex) 
					{
						Error.agregar("Error leyendo los mensajes del email.");
					}	
				}
				folder.close(true);
				store.close();
			}
    	}
		catch (Exception ex) 
		{
			//Error.agregar("Error en la lectura del correo, probablemente de sobre uso, o esta caido el servicio en hotmail " + ex.getMessage());
		}
		String[] aDevolver = new String[listaSenales.size()];
		listaSenales.toArray(aDevolver);
		return aDevolver;
    }
    
    public static void main(String []args)
    {
     	
    }
}
