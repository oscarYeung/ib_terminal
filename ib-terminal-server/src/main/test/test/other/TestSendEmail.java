package test.other;

import static org.junit.Assert.*;

import java.util.Date;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;


public class TestSendEmail {

	
	@Test
	public void sendEmail(){
		
		String userName = "abcd@gmail.com";
		String password = "testing1234";
		String host = "smtp.gmail.com";
		String port = "587";
		String subject = "Testing";
		String body = "Testing email";
		String emailTo = "fung8628@gmail.com";
		
		assertEquals(sendEmail(userName, password, host, port, subject, body, emailTo), true);
	}
	
	
	private boolean sendEmail(String userName, String password, String host, String port, String subject, String body, String emailTo){

        try
        {
            java.util.Properties props = null;
            props = System.getProperties();
            props.put("mail.smtp.user", userName);
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.debug", "true");

            if(!"".equals(port))
            {
                props.put("mail.smtp.port", port);
                props.put("mail.smtp.socketFactory.port", port);
            }

                props.put("mail.smtp.starttls.enable","true");

            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(userName));
            msg.setSubject(subject);                
            msg.setText(body, "ISO-8859-1");
            msg.setSentDate(new Date());
            msg.setHeader("content-Type", "text/html;charset=\"ISO-8859-1\"");
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            msg.saveChanges();

            Transport transport = session.getTransport("smtp");
            transport.connect(host, userName, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();

            return true;
        }
        catch (Exception mex)
        {
            mex.printStackTrace();
            return false;
        }
	}
}
