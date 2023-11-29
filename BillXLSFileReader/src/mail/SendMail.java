package mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
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
	public void sendMailOldMethod(String dbname,String path, String fileName, String subject,String responseMsg){
		Properties objmProperties=null;
		try {
			File objmFile=new File("Mail.xml");
		    JOXBeanInputStream joxIn = new JOXBeanInputStream(new FileInputStream(objmFile));
		    mailConfig= (MailConfig) joxIn.readObject(MailConfig.class);
			Properties props = System.getProperties();
			//props.put("mail.smtp.starttls.enable","false");
			
			//props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
			
			props.put("mail.smtp.host", mailConfig.getHost());
			props.put("mail.smtp.port", mailConfig.getPort());  
			props.put("mail.smtp.starttls.enable","true");
			props.put("mail.smtp.auth", "true");
			//props.put("mail.debug", "true");
			//System.out.println("emaill: "+mailConfig.getFrom()+" | "+mailConfig.getPassword());
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
	
	 public void sendMail(String dbname,String path, String fileName, String subject,String responseMsg) throws Exception {
	    	
	    	FileInputStream f1 = null;
	    	try {
	    		File objmFile=new File("Mail.xml");
	    		f1=new FileInputStream(objmFile);
	    		JOXBeanInputStream joxIn = new JOXBeanInputStream(f1);
	    		mailConfig= (MailConfig) joxIn.readObject(MailConfig.class);

	            String FROM = mailConfig.getFrom();
	            String FROMNAME = "ARTEMUS EDI ADMIN";
	            String TO = "tushar.waghmare@shrujansystems.com";
	           
	            //static final String [] TO = {"tushar.waghmare@shrujansystems.com", "tushar@giantleapsystems.com"};
	            
	            String SMTP_USERNAME = mailConfig.getSmtp_username();
	            String SMTP_PASSWORD = mailConfig.getSmtp_password();
	            //static final String HOST = "email-smtp.us-west-2.amazonaws.com";
	            String HOST = mailConfig.getHost();
	            String PORT = mailConfig.getPort();
	            
	            //CheckACEResponse objCheckACEResponse = new CheckACEResponse();
	            Properties objmProperties=null;
	            //objmProperties=objCheckACEResponse.getPropertyFile();
	            objmProperties=loadProperty();
				String[] to = objmProperties.get(dbname).toString().split(":");
				//String[] CC = objmProperties.getProperty("EMAIL_LIST").toString().split(":");
				
				//String SUBJECT = "TEST E-mail : "+"SCAC Code - "+dbname+" : "+subject;
	            String SUBJECT = "SCAC Code - "+dbname+" : "+subject;
	            //String BODY = "File Name :"+fileName+"\n"+responseMsg;
	            // Create the message part 
		         BodyPart messageBodyPart = new MimeBodyPart();
		         // Fill the message
		         messageBodyPart.setText("File Name :"+fileName+"\n"+responseMsg);
		         // Create a multipar message
		         Multipart multipart = new MimeMultipart();
		         // Set text message part
		         multipart.addBodyPart(messageBodyPart);

	        	//-------------------------------------------------------------------------/
	    		

	    		// Create a Properties object to contain connection configuration information.
		         System.setProperty("jdk.tls.version", "TLSv1.2");
		         Properties props = System.getProperties();
	    		props.put("mail.transport.protocol", "smtp");
	    		props.put("mail.smtp.port", PORT); 
	    		props.put("mail.smtp.starttls.enable", "true");
	    		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	    		props.put("mail.smtp.auth", "true");

	    		// Create a Session object to represent a mail session with the specified properties. 
	    		Session session = Session.getDefaultInstance(props);

	    		// Create a message with the specified information. 
	    		MimeMessage msg = new MimeMessage(session);
	    		msg.setFrom(new InternetAddress(FROM,FROMNAME));
	    		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to[0]));
	    		for (int i = 1; i < to.length; i++) {
		        	 msg.addRecipient(Message.RecipientType.CC,new InternetAddress(to[i]));
				}
	    		//msg.setRecipient(Message.RecipientType.CC, new InternetAddress(CC));
	    		msg.setSubject(SUBJECT);
	    		msg.setContent(multipart);

	    		// Add a configuration set header. Comment or delete the 
	    		// next line if you are not using a configuration set
	    		//msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

	    		// Create a transport.
	    		Transport transport = session.getTransport();

	    		// Send the message.
	    		try
	    		{
	    			System.out.println("Sending...");
	    			
	    			// Connect to Amazon SES using the SMTP username and password you specified above.
	    			transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

	    			// Send the email.
	    			transport.sendMessage(msg, msg.getAllRecipients());
	    			System.out.println("Email sent!");
	    		}
	    		catch (Exception ex) {
	    			System.out.println("The email was not sent.");
	    			System.out.println("Error message: " + ex.getMessage());
	    		}
	    		finally {
	    			// Close and terminate the connection.
	    			transport.close();
	    		}
	    	}
	        catch (Exception e) {
	    		e.printStackTrace();
	    		System.out.println("Exception while sending mail");
	    	}finally{
				try {
					if(f1!=null){
						f1.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    }
}
