package com.henyep.ib.terminal.server.mail;


import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Message.RecipientType;
import javax.mail.BodyPart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



/**
 * 项目名称：IBTerminalServer
 * <p>
 * 功能模块名称：
 * <p>
 * 文件名称为：SmtpClient.java
 * <p>
 * 文件功能简述：
 * <p>
 * 文件创建人：Administrator——Jm.zhang
 * @version v1.0
 * @time  2016-9-9 下午3:03:52
 * @copyright Hengyp
 */
public class SmtpClient {

	MailServerConfig mailServerCfg;
	
	private MailContextDto mailDto;
	
	SmtpClient(MailContextDto m){
		this.mailDto=m;
		this.mailServerCfg=m.getMailServerCfg();
	}
	
	@SuppressWarnings("unused")
	public void sendMail() throws Exception{
		Properties  mailProperties = this.getMailProperties();
		
		PwdAuthentificator passwordAuth= new PwdAuthentificator(mailServerCfg.getUser(), mailServerCfg.getPassword());
		
		Session mailSession = Session.getDefaultInstance(mailProperties, passwordAuth);
		
		if ((Boolean) mailProperties.get("mail.smtp.debug")) {
			mailSession.setDebug(true);
		} else {
			mailSession.setDebug(false);
		}
		
		InternetAddress inadd = new InternetAddress();
		inadd.setAddress(mailServerCfg.getFromAddress());
		inadd.setPersonal(mailServerCfg.getFormAddressAlias(), "UTF-8");
		
		MimeMessage mimessage = new MimeMessage(mailSession);
		mimessage.setFrom(inadd);
		
		InternetAddress[] toAddressList =  InternetAddress.parse(mailDto.getToAddressList());
		mimessage.setRecipients(RecipientType.TO, toAddressList);
		
		if(mailDto.getBccAddressList() !=null){
			InternetAddress[] bccAddressList = InternetAddress.parse(mailDto.getBccAddressList());
			mimessage.setRecipients(RecipientType.BCC, bccAddressList);
		}

		
		if(mailDto.getPriority_leve() == mailServerCfg.getPriority_leve_normal()){
			mimessage.setHeader("X-Priority", "3");
			mimessage.setHeader("X-MSMail-Priority", "Normal");
		}else if(mailDto.getPriority_leve() == mailServerCfg.getPriority_leve_high()){
			mimessage.setHeader("X-Priority", "1");
			mimessage.setHeader("X-MSMail-Priority", "High");			
		}
		
		mimessage.setSubject(mailDto.getSubject(), "UTF-8");
		mimessage.setSentDate(new Date());
		
		BodyPart bodyPart = new MimeBodyPart();
		bodyPart.setContent(mailDto.getContent(), "text/html; charset=utf-8");
		
		MimeMultipart mimeMutipart = new MimeMultipart();
		mimeMutipart.addBodyPart(bodyPart);
		
		mimessage.setContent(mimeMutipart);
		
		Transport transport = mailSession.getTransport("smtp");
		
		transport.connect(mailProperties.getProperty("mail.smtp.host"), mailProperties.getProperty("mail.smtp.user"),mailProperties.getProperty("mail.smtp.password"));
		
		transport.sendMessage(mimessage, mimessage.getAllRecipients());
		
		transport.close();
	}
	
	private Properties getMailProperties(){
		Properties prop = new Properties();
		prop.put("mail.smtp.host", mailServerCfg.getHost());
		prop.put("mail.smtp.connectiontimeout", mailServerCfg.getConnect_timeout());
		prop.put("mail.smtp.timeout", mailServerCfg.getSend_timeout());
		prop.put("mail.smtp.debug", mailServerCfg.isDebug());
		prop.put("mail.smtp.user", mailServerCfg.getUser());
		prop.put("mail.smtp.password", mailServerCfg.getPassword());
		prop.put("mail.smtp.auth", mailServerCfg.isAuth());
		prop.put("mail.smtp.localhost", "zqu.edu.cn");
		System.out.println(prop.toString());
		return prop;
		
	}

	public void setMailServerCfg(MailServerConfig mailServerCfg) {
		this.mailServerCfg = mailServerCfg;
	}
}
