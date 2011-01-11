package control.conexion.joel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.event.StoreEvent;
import javax.mail.event.StoreListener;

import control.Error;

public class ConexionServidorJoel
{
    private static boolean isJoel(Message mensaje)
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

    public static String[] leerServidorJoel()
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
		File file = new File("clave.txt");
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
    	String argv[] = {"-T", "pop3s", "-H", "pop3.live.com", "-U", "jorgeadrianmartinez@hotmail.com", "-P", clave};
    	ArrayList <String> listaSenales = new ArrayList <String> ();
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
			if(totalMessages == 0)
			{
				folder.close(false);
				store.close();
				return new String[0];
			}	  
		    if (msgnum == -1)
		    {
		    	Message[] msgs = folder.getMessages();				
				for (int i = 0; i < msgs.length; i++)
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
		catch (Exception e) 
		{
			Error.agregar("Error en la lectura del correo, probablemente de sobre uso, o esta caido el servicio en hotmail " + e.getMessage());
		}
		String[] aDevolver = new String[listaSenales.size()];
		listaSenales.toArray(aDevolver);
		return aDevolver;
    }
}
