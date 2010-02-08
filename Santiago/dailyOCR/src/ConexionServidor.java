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

public class ConexionServidor
{
    public static String leerServidorSSI()
    {
    	try
    	{ 	
    		File file = new File("js.txt");
    		Scanner sc = new Scanner(file);
    		String js = sc.next();
    		
	        DefaultHttpClient httpclient = new DefaultHttpClient();
	        BasicClientCookie cookie  =  new BasicClientCookie("JSESSIONID","292E82337F956A043C63CB80051101BF"); 
	        BasicClientCookie cookie1 = new BasicClientCookie(" s_cc","true"); 
	        BasicClientCookie cookie2 = new BasicClientCookie("s_PVnumber", "4"); 
	        BasicClientCookie cookie3 = new BasicClientCookie("s_sq","%5B%5BB%5D%5D");
	        BasicClientCookie cookie4 = new BasicClientCookie("JSESSIONIDSSO", js);
	        
	        cookie.setVersion(0);
	        cookie1.setVersion(0);
	        cookie2.setVersion(0);
	        cookie3.setVersion(0);
	        cookie4.setVersion(0);
	        
	        cookie.setDomain("plus.dailyfx.com");
	        cookie1.setDomain(".plus.dailyfx.com");
	        cookie2.setDomain(".plus.dailyfx.com");
	        cookie3.setDomain(".plus.dailyfx.com");
	        cookie4.setDomain("plus.dailyfx.com");
	        
	        cookie.setPath("/fxsignals");
	        cookie1.setPath("/");
	        cookie2.setPath("/");
	        cookie3.setPath("/");
	        cookie4.setPath("/");
	
	        httpclient.getCookieStore().addCookie(cookie);
	        httpclient.getCookieStore().addCookie(cookie1);
	        httpclient.getCookieStore().addCookie(cookie2);
	        httpclient.getCookieStore().addCookie(cookie3);
	        httpclient.getCookieStore().addCookie(cookie4);
	
	
	     // HttpGet httpget = new HttpGet("https://plus.dailyfx.com/login/loginForm.jsp");
	     
	     // HttpResponse response = httpclient.execute(httpget);
	     // HttpEntity entity = response.getEntity();
	
	     // if (entity != null) 
	     // {
	     //     entity.consumeContent();
	     // }
	        
	     // List <Cookie> cookies = httpclient.getCookieStore().getCookies();
	        
	        HttpGet httget2 = new HttpGet("https://fxsignals.dailyfx.com/fxsignals-ds/json/all.do");
	
	      //  List <NameValuePair> nvps = new ArrayList <NameValuePair> ();
	      //  nvps.add(new BasicNameValuePair("j_username", "1601040403"));
	      //  nvps.add(new BasicNameValuePair("j_password", "33796541"));
	
	      //  httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
	
	        HttpResponse response = httpclient.execute(httget2);
	        HttpEntity entity = response.getEntity();
	        response.getStatusLine();
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
	        httpclient.getConnectionManager().shutdown();
	        return sb.toString();
    	}
    	catch(Exception e)
    	{
    		// TODO Manejo de errores
    		return "Error";
    	}
    }
    
    
    
    public static String leerTechnical()
    {
        try
        {      
                DefaultHttpClient httpclient = new DefaultHttpClient();
               
                BasicClientCookie cookie10 = new BasicClientCookie("JSESSIONIDSSO", "DD8561C6129E3E909C6E617163A9D5B7");
                
                cookie10.setVersion(0);
                
                cookie10.setDomain("plus.dailyfx.com");

                cookie10.setPath("/");

                httpclient.getCookieStore().addCookie(cookie10);
                
                HttpGet httget2 = new HttpGet("https://plus.dailyfx.com/tnews/fxcentral/INDEX_TA_RECENT.HTM");

                HttpResponse response = httpclient.execute(httget2);
                HttpEntity entity = response.getEntity();
                response.getStatusLine();
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
                    BufferedWriter bw = new BufferedWriter(new FileWriter("salida1.txt"));
                    bw.write(sb.toString());
                    bw.close();
                }
                httpclient.getConnectionManager().shutdown();
                return sb.toString();
        }
        catch(Exception e)
        {
                // TODO Manejo de errores
                return "Error";
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
    	String [] html = new String [7];
    	String [] constantes = {"EURUSD", "USDJPY", "GBPUSD", "USDCHF", "USDCAD", "AUDUSD", "NZDUSD"};
    	for(int j = 0; j < html.length; j++)
    	{
	    	String temp = "";
	        try
	        {      
	            DefaultHttpClient httpclient = new DefaultHttpClient();
	           
	            BasicClientCookie cookie10 = new BasicClientCookie("JSESSIONIDSSO", "DD8561C6129E3E909C6E617163A9D5B7");
	            
	            cookie10.setVersion(0);
	            
	            cookie10.setDomain("plus.dailyfx.com");
	
	            cookie10.setPath("/");
	
	            httpclient.getCookieStore().addCookie(cookie10);
	
	            HttpGet httget2 = new HttpGet("https://plus.dailyfx.com/tnews/fxcentral/" + direcciones[j]);
	         
	            HttpResponse response = httpclient.execute(httget2);
	            HttpEntity entity = response.getEntity();
	            response.getStatusLine();
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
	            httpclient.getConnectionManager().shutdown();
	            temp = sb.toString();
	        }
	        catch(Exception e)
	        {
	                // TODO Manejo de errores
	        }
	        html[j] = constantes[j] + " " + temp;
    	}
    	return html;	
    }

    public static String leerServidorJoel()
    {
    	return "";
		//TODO Hacer metodo
    }
}
