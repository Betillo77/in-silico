package dailyBot.control.conexion;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dailyBot.control.Error;
 
public class ConexionServidorMensajes
{
	private static final String SMTP_HOST_NAME = "gmail-smtp.l.google.com";
	private static final String SMTP_AUTH_USER = "dailyfxstatus@gmail.com";
	private static final String emailFromAddress = "dailyfxstatus@gmail.com";
	private static final String passwordRoute = "clave.txt";
	private static final String[] emailList = {"santigutierrez1@gmail.com"};
	private static AtomicReference <String> SMTP_AUTH_PWD = new AtomicReference <String> ("");
	
	public static void enviarMensaje(String subject, String message)
	{
		File file = new File(passwordRoute);
		try
		{
			if(SMTP_AUTH_PWD.get().equals(""))
			{
				Scanner sc = new Scanner(file);
				SMTP_AUTH_PWD.set(sc.next());
				sc.close();
				java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			}
		} 
		catch(FileNotFoundException e) 
		{
			Error.agregarSinCorreo("No se encuentra el archivo con la clave del correo " + e.getMessage());
			e.printStackTrace();
		}
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
																	String username = SMTP_AUTH_USER;
																	String password = SMTP_AUTH_PWD.get();
																	return new PasswordAuthentication(username, password);
																}
															}
													);
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