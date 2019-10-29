package com.henyep.ib.terminal.server.util;

import java.util.Map;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.global.EmailConfig;

@SuppressWarnings("deprecation")
@Component("EmailUtil")
public class EmailUtil {
	protected final transient Log logger = LogFactory.getLog(getClass());

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Resource
	EmailConfig emailConfig;

	private static final String TEMPLATE_FOLDER = "templates/";	

	public void sendEmail(Map<String, Object> model) {
		int max_retry = 3;
		int tried = 1;
		boolean isFinish = false;
		MimeMessagePreparator preparator = getMessagePreparator(model);
		
		while (tried <= max_retry && isFinish == false){
			try{
				if(tried > 1){
					Thread.sleep(1000);
				}
			}
			catch (Exception e){
				logger.error(e, e);
			}
			
			try {
				mailSender.send(preparator);
				System.out.println("Try " + tried + " times");
				logger.debug("Message has been sent.............................");
				isFinish = true;
			} catch (Exception e) {
				logger.error(e, e);
				isFinish = false;				
				tried ++;
			}
		}
	}

	private MimeMessagePreparator getMessagePreparator(final Map<String, Object> model) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {
				try{
					MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	
					Object subject = model.get(Constants.EMAIL_SUBJECT);
					Object receiver = model.get(Constants.EMAIL_RECEIVER);
					Object template = model.get(Constants.EMAIL_TEMPLATE);
					Object sender = model.get(Constants.EMAIL_SENDER);
					Object bcc = model.get(Constants.EMAIL_BCC);
					
					String[] bccs = new String[]{bcc.toString()};
					if(StringUtils.contains(bcc.toString(), ",")){
						bccs = StringUtils.split(bcc.toString(), ',');
					}
					
					if (subject != null && receiver != null && template != null) {
						helper.setSubject(subject.toString());
						helper.setFrom(sender.toString());
						helper.setBcc(bccs);
						
						if(receiver.toString().contains(",")){
							String[] receivers = receiver.toString().split(",");
							helper.setTo(receivers);
						}
						else{
							helper.setTo(receiver.toString());
						}
						
						String text = geVelocityTemplateContent(model, template.toString());
						System.out.println("Template content : " + text);
	
						// use the true flag to indicate you need a multipart
						// message
						helper.setText(text, true);
	
						// Additionally, let's add a resource as an attachment as
						// well.
						// helper.addAttachment("cutie.png", new
						// ClassPathResource("linux-icon.png"));
					}
					
					if(model.containsKey(Constants.EMAIL_ATTACHMENT) && model.containsKey(Constants.EMAIL_ATTACHMENT_NAME)){
						
						Object attachment = model.get(Constants.EMAIL_ATTACHMENT);
						Object attachment_name = model.get(Constants.EMAIL_ATTACHMENT_NAME);
						System.out.println(attachment_name);
						helper.addAttachment(attachment_name.toString(), (ByteArrayResource)attachment);
					}
					
				}catch(Exception ex){
					logger.error(ex,ex);
				}
			}
		};
		return preparator;
	}

	public String geVelocityTemplateContent(final Map<String, Object> model, String template) {
		StringBuffer content = new StringBuffer();
		// "/vmtemplates/velocity_mailTemplate.vm"
		if (template != null)
			try {
				content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, TEMPLATE_FOLDER + template.toString(),"UTF-8", model));
				return content.toString();
			} catch (Exception e) {
				logger.error(e, e);
			}
		return "";
	}

	//
	// public void sendSuggestPodcastNotification(final SuggestedPodcast
	// suggestedPodcast) {
	// MimeMessagePreparator preparator = new MimeMessagePreparator() {
	// @SuppressWarnings({ "rawtypes", "unchecked" })
	// public void prepare(MimeMessage mimeMessage) throws Exception {
	// MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
	// message.setTo(configService.getValue("EMAIL_TO_SUGGEST_PODCAST"));
	// message.setBcc("adrianmatei@gmail.com");
	// message.setFrom(new InternetAddress(suggestedPodcast.getEmail()) );
	// message.setSubject("New suggested podcast");
	// message.setSentDate(new Date());
	// Map model = new HashMap();
	// model.put("newMessage", suggestedPodcast);
	//
	// String text = VelocityEngineUtils.mergeTemplateIntoString(
	// velocityEngine, "velocity/suggestPodcastNotificationMessage.vm", "UTF-8",
	// model);
	// message.setText(text, true);
	// }
	// };
	// mailSender.send(preparator);
	// }

}
