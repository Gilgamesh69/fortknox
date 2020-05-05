package cipher;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class Mail_test {

	private static Properties mailServerProperties;
	private static Session getMailSession;
	private static MimeMessage generateMailMessage;
	private final static String username = "logancollier04@gmail.com";
    //final static String password = "o32Ln(0-9*~7Tx~Zfw+HL)U9~nf6u$";
    private final static String password = "rpwxywrliembxrfg"; //google app password made for mail
    private static String host = "pop.gmail.com";// change accordingly
    private String mailStoreType = "pop3";
    
    
	public static void main(String[] args) {
		//send_updated_codex();
		retrieve_updated_codex();
	}
	public static void retrieve_updated_codex() {
		try {
			
         // create properties field
         Properties prop = new Properties();
         
 		//prop.put("mail.smtp.host", "smtp.gmail.com");
        //prop.put("mail.smtp.port", "465");
        //prop.put("mail.smtp.auth", "true");
        //prop.put("mail.smtp.socketFactory.port", "465");
        //prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
         
         
         prop.put("mail.store.protocol", "pop3");
         prop.put("mail.pop3.host", host);
         prop.put("mail.pop3.port", "995");
         prop.put("mail.pop3.starttls.enable", "true");
         Session emailSession = Session.getDefaultInstance(prop);
         // create the POP3 store object and connect with the pop server
         Store store = emailSession.getStore("imaps");
         store.connect(host, username, password);

         // create the folder object and open it
         Folder emailFolder = store.getFolder("INBOX");
         emailFolder.open(Folder.READ_ONLY);

         // retrieve the messages from the folder in an array and print it
         Message[] messages = emailFolder.getMessages();
         System.out.println("messages.length---" + messages.length);
         // iterate through inbox to find the last fortknox message
         for (int i = messages.length-1; i >= 0; i--) {
	            Message message = messages[i]; //get latest email
	            if(message.getSubject().contentEquals("FORTKNOX UPDATER")) {
		            //System.out.println(message.getDescription());
		            System.out.println(message.getSentDate());
		            System.out.println(message.getSubject());
		            System.out.println("---------------------------------");
		            //writePart(message);
		            System.out.println("CONTENT-TYPE: " + message.getContentType());
		            
		          //check if the content has attachment aka will be of type multipart
		           if (message.isMimeType("multipart/*")) {
		               System.out.println("This is a Multipart");
		               System.out.println("---------------------------");
		               Multipart mp = (Multipart) message.getContent();
		               /**
		                *  iterate through each part in the multipart to identify which part contains the attachment
		                *  
		                *  The MimeBodyPart class provides methods for retrieving and storing attached file,
		                *  but the way is different between JavaMail 1.3 and JavaMail 1.4.
		                *  
		                *  In JavaMail 1.3, we have to read bytes from an InputStream which can be obtained from 
		                *  the method getInputStream() of the class MimeBodyPart, and write those bytes into an OutputStream,
		                *  
		                */
		               for (int j = 0; j < mp.getCount(); j++) {
		            	    MimeBodyPart part = (MimeBodyPart) mp.getBodyPart(j);
		            	    if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
		            	        // this part is attachment
		            	        // code to save attachment...
		            	    	// save an attachment from a MimeBodyPart to a file
		            	    	String destFilePath = "newCodex.ser";
		            	    	 
		            	    	FileOutputStream output = new FileOutputStream(destFilePath);
		            	    	 
		            	    	InputStream input = part.getInputStream();
		            	    	 
		            	    	byte[] buffer = new byte[4096];
		            	    	 
		            	    	int byteRead;
		            	    	 
		            	    	while ((byteRead = input.read(buffer)) != -1) {
		            	    	    output.write(buffer, 0, byteRead);
		            	    	}
		            	    	output.close();
		            	    }
		            	}
		               FileInputStream fileIn = new FileInputStream("newCodex.ser");
		               ObjectInputStream in = new ObjectInputStream(fileIn);
		               HashMap<String, ArrayList<byte[]>> codex = (HashMap<String,ArrayList<byte[]>>) in.readObject();
		               in.close();
		               System.out.println(codex.keySet());
		            } 
	            }else {
	            	continue;
	            }

	        }

	         // close the store and folder objects
	         emailFolder.close(false);
	         store.close();

	      } catch (NoSuchProviderException e) {
	         e.printStackTrace();
	      } catch (MessagingException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	}
	/**
	 * constructs an email with the codex as an attachment
	 * uses SSL 
	 */
	public static void send_updated_codex() {
		
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(username)
            );
            message.setSubject("FORTKNOX UPDATER");
            message.setText("Dear Mail Crawler,"
                    + "\n\n Please do not spam my email!");
            MimeBodyPart messageBody = new MimeBodyPart();
            String filename = "newCodex.ser";
            DataSource source = new FileDataSource(filename);
            messageBody.setDataHandler(new DataHandler(source));
            messageBody.setFileName(filename);
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBody);
            message.setContent(multipart);

            Transport.send(message);
            System.out.println("sent...");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
		
	}
	

}
