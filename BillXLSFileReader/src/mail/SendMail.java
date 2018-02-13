package mail;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import beans.mail.MailConfig;
import com.wutka.jox.JOXBeanInputStream;
import dao.LoadProperty;

public class SendMail  extends LoadProperty{
	MailConfig mailConfig=null;
	public void sendMail(String dbname,String path, String fileName, String subject,String responseMsg){
		Properties objmProperties=null;
		try {
			File objmFile=new File("Mail.xml");
		    JOXBeanInputStream joxIn = new JOXBeanInputStream(new FileInputStream(objmFile));
		    mailConfig= (MailConfig) joxIn.readObject(MailConfig.class);
			Properties props = System.getProperties();
			//props.put("mail.smtp.starttls.enable","false");
			props.put("mail.smtp.starttls.enable","true");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.host", mailConfig.getHost());
			props.put("mail.smtp.port", mailConfig.getPort());  
			
			System.out.println("Authentication is in progress");
			Authenticator auth = new SMTPAuthenticator();
			
			Session session = Session.getDefaultInstance(props, auth);
			System.out.println("Session created");
				try {
					objmProperties=loadProperty();
					String[] to=objmProperties.get(dbname).toString().split(":");
					MimeMessage message = new MimeMessage(session);

			         // Set From: header field of the header.
			         message.setFrom(new InternetAddress(mailConfig.getFrom()));

			         // Set To: header field of the header.
			         
			         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to[0]));
			         for (int i = 1; i < to.length; i++) {
			        	 message.addRecipient(Message.RecipientType.CC,new InternetAddress(to[i]));
					}

			         // Set Subject: header field
			         message.setSubject(subject);

			         // Create the message part 
			         BodyPart messageBodyPart = new MimeBodyPart();

			         // Fill the message
			         messageBodyPart.setText("File Name :"+fileName+"\n"+responseMsg);
			         
			         // Create a multipar message
			         Multipart multipart = new MimeMultipart();

			         // Set text message part
			         multipart.addBodyPart(messageBodyPart);

			         // Part two is attachment
			         //messageBodyPart = new MimeBodyPart();
			         //  String filename = "file.txt";
			         //DataSource source = new FileDataSource(path);
			         //messageBodyPart.setDataHandler(new DataHandler(source));
			         //messageBodyPart.setFileName(fileName);
			         //multipart.addBodyPart(messageBodyPart);

			         // Send the complete message parts
			         message.setContent(multipart );

			         // Send message
			         Transport.send(message);
			         System.out.println("Sent message successfully....");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Exception while sending mail");
				}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(mailConfig.getFrom(),mailConfig.getPassword());
		}
	}
}
