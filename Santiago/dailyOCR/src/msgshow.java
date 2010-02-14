//PARA ACCEDER AL PROGRAMA USAR RUN, NO HE HECHO PRUEBAS EXHAUSTIVAS PERO CREO QUE FUNCIONA.
//DUVUELVE UN ARRAY LIST CON LAS SEÑALES NUEVAS QUE ENCUENTRE EN EL CORREO

import java.util.*;

import java.io.*;
import javax.mail.*;
import javax.mail.event.*;
import javax.mail.internet.*;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.SearchTerm;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


@SuppressWarnings("unused")
public class msgshow {

    static String protocol;
    static String host = null;
    static String user = null;
    static String password = null;
    static String mbox = null;
    static String url = null;
    static int port = -1;
    static boolean verbose = false;
    static boolean debug = false;
    static boolean showStructure = false;
    static boolean showMessage = false;
    static boolean showAlert = false;
    static boolean saveAttachments = false;
    static int attnum = 1;

    public static ArrayList<SenalJoel> run() 
    {
    String argv[]={"-T", "pop3s", "-H", "pop3.live.com", "-U", "jorgeadrianmartinez@hotmail.com", "-P", "calidadIngesis"};
    ArrayList <SenalJoel> ListaSenales = new ArrayList <SenalJoel> ( );
    int NumeroDeMensajesAnterior = 0;
	int msgnum = -1;
	int optind;
	InputStream msgStream = System.in;
	
	//este codigo no lo hice yo para que no me regañe xD
	for (optind = 0; optind < argv.length; optind++) {
	    if (argv[optind].equals("-T")) {
		protocol = argv[++optind];
	    } else if (argv[optind].equals("-H")) {
		host = argv[++optind];
	    } else if (argv[optind].equals("-U")) {
		user = argv[++optind];
	    } else if (argv[optind].equals("-P")) {
		password = argv[++optind];
	    } else if (argv[optind].equals("-v")) {
		verbose = true;
	    } else if (argv[optind].equals("-D")) {
		debug = true;
	    } else if (argv[optind].equals("-f")) {
		mbox = argv[++optind];
	    } else if (argv[optind].equals("-L")) {
		url = argv[++optind];
	    } else if (argv[optind].equals("-p")) {
		port = Integer.parseInt(argv[++optind]);
	    } else if (argv[optind].equals("-s")) {
		showStructure = true;
	    } else if (argv[optind].equals("-S")) {
		saveAttachments = true;
	    } else if (argv[optind].equals("-m")) {
		showMessage = true;
	    } else if (argv[optind].equals("-a")) {
		showAlert = true;
	    } else if (argv[optind].equals("--")) {
		optind++;
		break;
	    } else if (argv[optind].startsWith("-")) {
		System.out.println(
"Usage: msgshow [-L url] [-T protocol] [-H host] [-p port] [-U user]");
		System.out.println(
"\t[-P password] [-f mailbox] [msgnum] [-v] [-D] [-s] [-S] [-a]");
		System.out.println(
"or     msgshow -m [-v] [-D] [-s] [-S] [-f msg-file]");
		}
	    else
	    {
		break;
	    }
	}
	try
	{
	File file=new File("F:\\numeroDecorreos.txt");
	Scanner sc=new Scanner(file);
	NumeroDeMensajesAnterior=sc.nextInt();
	}
	catch(FileNotFoundException e){Error.agregar("Error no se encuentra el archivo numeroDecorreos.txt " + e.getMessage());}
	catch(InputMismatchException e){Error.agregar("Error de entrada revise el archivo numeroDecorreos.txt " + e.getMessage());}
	catch(Exception e){Error.agregar("Error desconocido al abrir o leer el archivo " + e.getMessage());}
	//TERMINA PRIMERA INSERCION
	
	
	try {
	    if (optind < argv.length)
		 msgnum = Integer.parseInt(argv[optind]);
	    // Get a Properties object
	    Properties props = System.getProperties();

	    // Get a Session object
	    Session session = Session.getInstance(props, null);
	    session.setDebug(debug);
	    
	    

	    // Get a Store object
	    Store store = null;
	    if (url != null) {
		URLName urln = new URLName(url);
		store = session.getStore(urln);
		if (showAlert) {
		    store.addStoreListener(new StoreListener() {
			public void notification(StoreEvent e) {
			    String s;
			    if (e.getMessageType() == StoreEvent.ALERT)
				s = "ALERT: ";
			    else
				s = "NOTICE: ";
			    System.out.println(s + e.getMessage());
			}
		    });
		}
		store.connect();
	    } else {
		if (protocol != null)		
		    store = session.getStore(protocol);
		else
		    store = session.getStore();

		// Connect
		if (host != null || user != null || password != null)
		    store.connect(host, port, user, password);
		else
		    store.connect();
	    }
	    

	    // Open the Folder

	    Folder folder = store.getDefaultFolder();
	    
	    
	    if (folder == null) 
	    {
						System.out.println("No default folder");
						System.exit(1);
					    }
		
					    if (mbox == null)
						mbox = "INBOX";
					    folder = folder.getFolder(mbox);
					    if (folder == null) {
						System.out.println("Invalid folder");
					   }
		
			    // Trata de abrir la carpeta como de Lectura/Escritura, sino la abre como solo lectura
					    try
					    {
					    folder.open(Folder.READ_WRITE);
					    }
					    catch (MessagingException ex)
					    {
						folder.open(Folder.READ_ONLY);
					    }
					    
		int totalMessages = folder.getMessageCount();
	
		if(totalMessages > NumeroDeMensajesAnterior)
	    {
		  
		try {
		        FileWriter fw = new FileWriter("F:\\numeroDecorreos.txt");
		        BufferedWriter bw = new BufferedWriter(fw);
		        PrintWriter salida = new PrintWriter(bw);
		        salida.println(totalMessages);
		        salida.close();
		    }
		    catch(java.io.IOException ioex) {
		      Error.agregar("Error al escribir en el archivo: "+ioex.getMessage());
		    }
		
					    if (totalMessages == 0) {
						System.out.println("Empty folder");
						folder.close(false);
						store.close();
						return ListaSenales;
						//Para salir de una vez
						}
			  
					    if (msgnum == -1)
					    {
						Message[] msgs = folder.getMessages();
				
							for (int i = ((totalMessages-(totalMessages-NumeroDeMensajesAnterior))-1); i < msgs.length; i++)
							{
								
								
								if(isJoel(msgs[i]))
								{				
								SenalJoel nueva=deducir(msgs[i].getSubject());
									if(nueva!=null)
									{
										ListaSenales.add(nueva);
									}
								
								}
							}
				
					    }
			    
					    else {
						System.out.println("Getting message number: " + msgnum);
						Message m = null;
						
						try 
						{
						  m = folder.getMessage(msgnum);
						}
						catch (IndexOutOfBoundsException iex) 
						{
						  	System.out.println("Message number out of range");
						}
						
					    }
		
					    folder.close(false);
					    store.close();
		}
	    
	
	}
			catch (Exception ex) 
			{
			    Error.agregar("Error en la lectura del correo, probablemente" +
			    		"de sobre uso, o esta caido el servicio en hotmail " + ex.getMessage());
			}
	
   return ListaSenales;
    }
    
    public static boolean isJoel(Message mensaje)
    {
    	try {
			if(mensaje.getFrom()[0].toString().equals("DailyFX <jskruger@fxcm.com>"))
			{	
				
				return true;
			}
		} catch (MessagingException e) {
			
			Error.agregar("Error al recibir el mensaje " + e.getMessage());
			
		}
    	return false;
    }
    
    public static SenalJoel deducir(String subject)
    {boolean recomendado = false;
    boolean compra = false;
    Par par = null;
    int NumeroDeLotes = 2;
    double PrecioDeEntrada = 0.0;
    double Parada = 0.0;
    double Limite = 0.1;
    SenalJoel nueva=new SenalJoel(IdEstrategia.JOEL, false, par, -1, -0.1,-0.1,false);
    
    	if(subject.contains("@"))
    	{

    		if(subject.contains("RECOMMENDATION"))
    		{
    			recomendado=true;
    			
    		}
    		if(subject.contains("BUY")||subject.contains("BOUGHT")||subject.contains("LONG"))
    		{
    		compra=true;	
    		}
    		//PARECE... MEJOR, SE QUE ES REDUNDANTE PERO ES MEJOR ASEGURARSE
    		else if(subject.contains("SELL")||subject.contains("SOLD")||subject.contains("SHORT"))
    		{
    			compra=false;
    		}
    		Pattern BuscaPares=Pattern.compile("[A-Z]*/[A-Z]*");
    		Matcher match=BuscaPares.matcher(subject);
    		if(match.find())
    		{	
    			String ConSlash = match.group();
    			String SinSlash = ConSlash.replace("/" , "");
    			Par a = Par.convertirPar( SinSlash );
    			if(a!=null)
    			{
    				par=a;
    			}
    			
    			
    		}
    		Pattern At = Pattern.compile( "@\\d+.?\\d*" );
    		Matcher match1 = At.matcher( subject );
    		if( match1.find() )
    		{
    		String conAt=match1.group();
    		String sinAt=conAt.substring(1);
    		PrecioDeEntrada=Double.parseDouble(sinAt);
    		}
    		Pattern Stop = Pattern.compile( "STOP\\s\\d+.?\\d*" );
    		Matcher match2 = Stop.matcher( subject );
    		
    		if(match2.find())
    		{
    			String conStop = match2.group();
    			String sinStop = conStop.substring(5);
    			Parada = Double.parseDouble( sinStop );
    			
    		}
    		if(subject.contains("LIMIT"))
    		{
    			Pattern Limit=Pattern.compile( "LIMIT\\s\\d+.?\\d*" );
    			Matcher match3 = Limit.matcher(subject);
    			
    			if(match3.find())
    			{
    				String conLimite=match3.group();
    				String sinLimite=conLimite.substring(6);
    				Limite=Double.parseDouble(sinLimite);
    				
    			}
    			
    			
    			
    		}
    		
    		if(par!=null && PrecioDeEntrada>0 && Parada>0)
    		{
    		nueva.compra = compra;
    		nueva.estrategia = IdEstrategia.JOEL;
    		nueva.par = par;
    		nueva.numeroLotes = NumeroDeLotes;
    		nueva.precioEntrada = PrecioDeEntrada;
    		nueva.limite = Limite;
    		nueva.stop = Parada;
    		nueva.recomendado = recomendado;
    		return nueva;
    		}
    		
    	}
    	
    	
    	
    	
    
    return null;	
    }
    
 
}
