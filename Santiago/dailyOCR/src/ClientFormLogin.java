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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

/**
 * A example that demonstrates how HttpClient APIs can be used to perform
 * form-based logon.
 */
public class ClientFormLogin {

    public static void main(String[] args) throws Exception {

        DefaultHttpClient httpclient = new DefaultHttpClient();
        BasicClientCookie cookie  =  new BasicClientCookie("JSESSIONID","292E82337F956A043C63CB80051101BF"); 
        BasicClientCookie cookie1 = new BasicClientCookie(" s_cc","true"); 
        BasicClientCookie cookie2 = new BasicClientCookie("s_PVnumber","4"); 
        BasicClientCookie cookie3 = new BasicClientCookie("s_sq","%5B%5BB%5D%5D");
        BasicClientCookie cookie4 = new BasicClientCookie("JSESSIONIDSSO","4561D459B17399D52D1F9E60EC0CAE38");
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

        

        HttpGet httpget = new HttpGet("https://plus.dailyfx.com/login/loginForm.jsp");

        HttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();

        System.out.println("Login form get: " + response.getStatusLine());
        if (entity != null) {
            entity.consumeContent();
        }
        System.out.println("Initial set of cookies:");
        List<Cookie> cookies = httpclient.getCookieStore().getCookies();
        if (cookies.isEmpty()) {
            System.out.println("None");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
                System.out.println("- " + cookies.get(i).toString());
            }
        }

        HttpGet httget2 = new HttpGet("https://fxsignals.dailyfx.com/fxsignals-ds/json/all.do");

        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("j_username", "1601040403"));
        nvps.add(new BasicNameValuePair("j_password", "33796541"));

      //  httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

        response = httpclient.execute(httget2);
        entity = response.getEntity();

        System.out.println("Login form get: " + response.getStatusLine());
        if (entity != null) {
        		InputStream instream = entity.getContent();
        	    int l;
        	    byte[] tmp = new byte[2048];
        	    while ((l = instream.read(tmp)) != -1) 
        	    {
        	    	System.out.println(l);
        	    	for(int i = 0; i < 2048; i++)
        	    	{
        	    		tmp[i] &= 127;
        	    	}
        	    	System.out.print(new String(tmp));
        	    }
                System.out.println(new String(tmp));
                //file writing
                try{
                    // Create file 
                    FileWriter fstream = new FileWriter("file.txt");
                        BufferedWriter out = new BufferedWriter(fstream);
                    out.write(new String (tmp));
                    //Close the output stream
                    out.close();
                    }catch (Exception e){//Catch exception if any
                      System.err.println("Error: " + e.getMessage());
                    }
                
                ////

        }
        
        
        
        System.out.println("Post logon cookies:");
        cookies = httpclient.getCookieStore().getCookies();
        if (cookies.isEmpty()) {
            System.out.println("None");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
                System.out.println("- " + cookies.get(i).toString());
            }
        }

        // When HttpClient instance is no longer needed, 
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown();        
    }
}
