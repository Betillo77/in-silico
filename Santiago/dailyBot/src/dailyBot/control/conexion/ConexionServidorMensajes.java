package dailyBot.control.conexion;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dailyBot.control.Error;
import dailyBot.control.Propiedades;
 
public class ConexionServidorMensajes
{
	private static final String SMTP_HOST_NAME = "gmail-smtp.l.google.com";
	private static final String SMTP_AUTH_USER = "dailyfxstatus@gmail.com";
	private static final String emailFromAddress = "dailyfxstatus@gmail.com";
	private static final String[] emailList = {"santigutierrez1@gmail.com"};
	private static final Session session = cargarSession();
	private static final ExecutorService executor = Executors.newFixedThreadPool(1);
	
	private static Session cargarSession() 
	{
		java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, new Authenticator()
															{
																@Override
																public PasswordAuthentication getPasswordAuthentication() 
																{
																	return new PasswordAuthentication(SMTP_AUTH_USER, Propiedades.darPropiedad("dailyBot.control.conexion.ConexionServidorMensajes.claveGmail"));
																}
															}
													);
		return session;
	}
	
	private static class AyudanteEnviarMensaje implements Runnable
	{
		String subject;
		String message;
		
		private AyudanteEnviarMensaje(String s, String m) 
		{
			subject = s;
			message = m;
		}
		
		public void run() 
		{
			try
			{		
				session.setDebug(false);
			 	Message msg = new MimeMessage(session);
			 	InternetAddress addressFrom = new InternetAddress(emailFromAddress);
				msg.setFrom(addressFrom);
			 	InternetAddress[] addressTo = new InternetAddress[emailList.length];
			 	for (int i = 0; i < emailList.length; i++) 
			 		addressTo[i] = new InternetAddress(emailList[i]);
			 	msg.setRecipients(Message.RecipientType.TO, addressTo);
			 	msg.setSubject(subject);
				msg.setContent(message, "text/plain");
				Transport.send(msg);
			}
			catch(MessagingException e)
			{		
				Error.agregarSinCorreo("Error al enviar el correo " + e.getMessage());	
			}
		}
	}

	public static void enviarMensaje(String subject, String message)
	{
		executor.submit(new AyudanteEnviarMensaje(subject, message));
	}
}