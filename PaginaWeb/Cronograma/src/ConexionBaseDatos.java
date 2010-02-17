import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;


public class ConexionBaseDatos 
{
	
	private static final String SERVIDOR = "http://10.42.43.1/phptest/in-silico/applet_php/";
	private static final String SCRIPT1 = "cronJava.php";
	private static final String SCRIPT2 = "cronJava.php";
	private static final String SCRIPT3 = "cronJava.php";
	private static final String SCRIPT4 = "cronJava.php";
	private static final String SCRIPT5 = "contCronJava.php";
	private static final String SCRIPT6 = "contCronJava.php";
	private static final String SCRIPT7 = "contCronJava.php";
	private static final String SCRIPT8 = "userJava.php";
	
	private static String peticionPost(String direccion, List <NameValuePair> nvps)
	{   
		try
		{
	        DefaultHttpClient clienteHttp = new DefaultHttpClient();
	        HttpPost peticionPost = new HttpPost(SERVIDOR + direccion);
	        nvps.add(new BasicNameValuePair("IDToken2", "password"));
	        peticionPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
	        HttpResponse respuesta = clienteHttp.execute(peticionPost);
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
	        return sb.toString();
		}
		catch(Exception e)
		{
			//TODO Avisar usuario de error
			return "";
		}
	}
	
	public static ArrayList <Tarea> leerTareasActuales(String idProyecto, ArrayList <Integrante> integrantes) 
	{
		try
		{
			List <NameValuePair> nvps = new ArrayList <NameValuePair> ();
			nvps.add(new BasicNameValuePair("idProyecto", idProyecto));
			nvps.add(new BasicNameValuePair("command", "getCronByProject"));
			String resultado = peticionPost(SCRIPT1, nvps);
			String[] lineas = resultado.split("\n");
			ArrayList <Tarea> nuevasTareas = new ArrayList <Tarea> ();
			int ultimoId = -1;
			ArrayList <String> preRequisitos = new ArrayList <String> ();
			ArrayList <Integer> padres = new ArrayList <Integer> ();
			for(int i = 0; i < lineas.length; i++)
			{
				if(lineas[i].equals(""))
					continue;
				String[] actual = lineas[i].split(",");
				String concepto = actual[0];
				int id = Integer.parseInt(actual[1]);
				ArrayList <Integrante> integrantesActual = new ArrayList <Integrante> ();
				String nicks = actual[2].length() == 0 ? "" : actual[2].substring(0, actual[2].length() - 1);
				for(String nick : nicks.split(" "))
					if(!nick.equals(""))
						integrantesActual.add(integrantes.get(Collections.binarySearch(integrantes, new Integrante("", nick))));
				preRequisitos.add(actual[3]);
				if(actual[4].equals(""))
					padres.add(-1);
				else
					padres.add(Integer.parseInt(actual[4]));
				Estado estado = Estado.values()[Integer.parseInt(actual[5])];
				nuevasTareas.add(new Tarea(idProyecto, id, concepto, null, Integer.parseInt(actual[6]), estado, Integer.parseInt(actual[7]), null, integrantesActual));
				ultimoId = Math.max(ultimoId, id);
			}
			Tarea[] porIds = new Tarea[ultimoId + 1];
			for(Tarea t : nuevasTareas)
				porIds[t.id] = t;
			for(int i = 0; i < nuevasTareas.size(); i++)
			{
				if(padres.get(i) != -1)
				{
					nuevasTareas.get(i).padre = porIds[padres.get(i)];
					porIds[padres.get(i)].hijos.add(nuevasTareas.get(i));
				}
				ArrayList <Tarea> pre = new ArrayList <Tarea> ();
				for(String s : preRequisitos.get(i).split(" "))
					if(!s.equals(""))
						pre.add(porIds[Integer.parseInt(s)]);
				nuevasTareas.get(i).preRequisitos = pre;
			}
			return nuevasTareas;
		}
		catch(Exception e)
		{		
			//TODO Avisar usuario de error
			return null;
		}
	}
	
	public static void actualizarTarea(Tarea tarea)
	{
		try
		{
			List <NameValuePair> nvps = new ArrayList <NameValuePair> ();
			nvps.add(new BasicNameValuePair("command", "update"));
			nvps.add(new BasicNameValuePair("id", tarea.id + ""));
			nvps.add(new BasicNameValuePair("concepto", tarea.concepto));
			String integrantes = "";
			for(Integrante i : tarea.responsables)
				integrantes += i.id + ",";
			if(integrantes.length() > 0)
				integrantes = integrantes.substring(0, integrantes.length() - 1);
			nvps.add(new BasicNameValuePair("resp", integrantes));
			String preRequisitos = "";
			for(Tarea t : tarea.preRequisitos)
				preRequisitos += t.id + " ";
			if(preRequisitos.length() > 0)
				preRequisitos = preRequisitos.substring(0, preRequisitos.length() - 1);
			nvps.add(new BasicNameValuePair("prereq", preRequisitos));
			nvps.add(new BasicNameValuePair("padre", tarea.padre == null ? "" : tarea.padre.id + ""));
			nvps.add(new BasicNameValuePair("estado", tarea.estado.ordinal() + ""));
			nvps.add(new BasicNameValuePair("tiempo", tarea.tiempo + ""));
			peticionPost(SCRIPT2, nvps);
		}
		catch(Exception e)
		{		
			//TODO Avisar usuario de error
		}
	}
	
	public static void eliminarTarea(int id)
	{
		try
		{
			List <NameValuePair> nvps = new ArrayList <NameValuePair> ();
			nvps.add(new BasicNameValuePair("command", "delete"));
			nvps.add(new BasicNameValuePair("id", id + ""));
			peticionPost(SCRIPT3, nvps);
		}
		catch(Exception e)
		{
			//TODO Avisar usuario de error
		}
	}
	
	public static void crearTarea(Tarea tarea, String idProyecto)
	{
		try
		{
			List <NameValuePair> nvps = new ArrayList <NameValuePair> ();
			nvps.add(new BasicNameValuePair("command", "insert"));
			nvps.add(new BasicNameValuePair("idProyecto", idProyecto));
			nvps.add(new BasicNameValuePair("concepto", tarea.concepto));
			String integrantes = "";
			for(Integrante i : tarea.responsables)
				integrantes += i.id + ",";
			if(integrantes.length() > 0)
				integrantes = integrantes.substring(0, integrantes.length() - 1);
			nvps.add(new BasicNameValuePair("resp", integrantes));
			String preRequisitos = "";
			for(Tarea t : tarea.preRequisitos)
				preRequisitos += t.id + " ";
			if(preRequisitos.length() > 0)
				preRequisitos = preRequisitos.substring(0, preRequisitos.length() - 1);
			nvps.add(new BasicNameValuePair("prereq", preRequisitos));
			nvps.add(new BasicNameValuePair("padre", tarea.padre == null ? "" : tarea.padre.id + ""));
			nvps.add(new BasicNameValuePair("estado", tarea.estado.ordinal() + ""));
			nvps.add(new BasicNameValuePair("tiempo", tarea.tiempo + ""));
			System.out.println(peticionPost(SCRIPT4, nvps));
		}
		catch(Exception e)
		{		
			//TODO Avisar usuario de error
		}
	}
	
	public static ArrayList <Registro> darRegistros(int id, Integrante[] integrantes)
	{
		try
		{
			List <NameValuePair> nvps = new ArrayList <NameValuePair> ();
			nvps.add(new BasicNameValuePair("command", "getContCronByIdCron"));
			nvps.add(new BasicNameValuePair("id", id + ""));
			String respuesta = peticionPost(SCRIPT5, nvps);
			String[] lineas = respuesta.split("\n");
			ArrayList <Registro> registros = new ArrayList <Registro> ();
			for(int i = 0; i < lineas.length; i++)
			{
				String[] actual = lineas[i].split(",");
				String nick = actual[0];
				String[] fecha = actual[1].split(" ");
				Calendar calendario = Calendar.getInstance();
				calendario.set(Integer.parseInt(fecha[0]), Integer.parseInt(fecha[1]) - 1, Integer.parseInt(fecha[2]));
				int horas = Integer.parseInt(actual[2]);
				int idInterno = Integer.parseInt(actual[3]);
				registros.add(new Registro(integrantes[Arrays.binarySearch(integrantes, new Integrante("", nick))], calendario, horas, idInterno));
			}
			return registros;
		}
		catch(Exception e)
		{		
			//TODO Avisar usuario de error
			return null;
		}
	}
	
	public static void agregarRegistro(int id, Registro registro)
	{
		try
		{
			List <NameValuePair> nvps = new ArrayList <NameValuePair> ();

			nvps.add(new BasicNameValuePair("id", id + ""));
			nvps.add(new BasicNameValuePair("integrante", registro.integrante.id));
			nvps.add(new BasicNameValuePair("fecha", registro.fecha.get(Calendar.YEAR) + " " + (registro.fecha.get(Calendar.MONTH) + 1) + " " + registro.fecha.get(Calendar.DATE)));
			nvps.add(new BasicNameValuePair("horas", registro.horas + ""));
			peticionPost(SCRIPT6, nvps);
		}
		catch(Exception e)
		{		
			//TODO Avisar usuario de error
		}
	}
	
	public static void eliminarRegistro(int idInterno)
	{
		try
		{
			List <NameValuePair> nvps = new ArrayList <NameValuePair> ();
			nvps.add(new BasicNameValuePair("command", "getUsers"));
			nvps.add(new BasicNameValuePair("idinterno", idInterno + ""));
			peticionPost(SCRIPT7, nvps);
		}
		catch(Exception e)
		{		
			//TODO Avisar usuario de error
		}
	}
	
	public static ArrayList <Integrante> darIntegrantes()
	{
		try
		{
			List <NameValuePair> nvps = new ArrayList <NameValuePair> ();
			String respuesta = peticionPost(SCRIPT8, nvps);
			String[] lineas = respuesta.split("\n");
			ArrayList <Integrante> integrantes = new ArrayList <Integrante> ();
			for(String s : lineas)
			{
				if(s.equals(""))
					continue;
				String[] actual = s.split(",");
				integrantes.add(new Integrante(actual[0], actual[1]));
			}
			Collections.sort(integrantes);
			return integrantes;
		}
		catch(Exception e)
		{		
			//TODO Avisar usuario de error
			return null;
		}
	}
	
	public static void abrirReporte(int id, String idProyecto)
	{
	}
	
}
