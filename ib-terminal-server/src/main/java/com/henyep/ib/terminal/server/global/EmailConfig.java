package com.henyep.ib.terminal.server.global;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
// @ComponentScan(basePackages = "com.henyep")
public class EmailConfig {

	@Resource(name = "config")
	private Properties config;

	private String host;
	private int port;
	private String username;
	private String password;
	private String sender;
	private String bcc;
	private String mailSubject_en;
	private String mailSubject_cn;
	private String cornJobFailSubject;
	private String cornJobFailSender;
	private String cornJobRecipientsBcc;
	private String cornJobRecipients;

	private String marginOutSubject_en;
	private String marginOutSubject_cn;
	private String marginOutSender;
	private String marginOutBcc;

	private String welcomeSubject_en;
	private String welcomeSubject_cn;
	private String welcomeSender;
	private String welcomeBcc;
	
	private String ibCommissionSummaryReportSender;
	private String ibCommissionSummaryReportSubject;
	private String ibCommissionSummaryReportAdminName;
	private String ibCommissionSummaryReportAdminEmail;
	

	public String getCornJobRecipientsBcc() {
		return cornJobRecipientsBcc;
	}

	public void setCornJobRecipientsBcc(String cornJobRecipientsBcc) {
		this.cornJobRecipientsBcc = cornJobRecipientsBcc;
	}

	public String getCornJobRecipients() {
		return cornJobRecipients;
	}

	public void setCornJobRecipients(String cornJobRecipients) {
		this.cornJobRecipients = cornJobRecipients;
	}

	public String getCornJobFailSubject() {
		return cornJobFailSubject;
	}

	public void setCornJobFailSubject(String cornJobFailSubject) {
		this.cornJobFailSubject = cornJobFailSubject;
	}

	public String getMarginOutSubject_en() {
		return marginOutSubject_en;
	}

	public void setMarginOutSubject_en(String marginOutSubject_en) {
		this.marginOutSubject_en = marginOutSubject_en;
	}

	public String getMarginOutSubject_cn() {
		return marginOutSubject_cn;
	}

	public void setMarginOutSubject_cn(String marginOutSubject_cn) {
		this.marginOutSubject_cn = marginOutSubject_cn;
	}

	public String getMailSubject_en() {
		return mailSubject_en;
	}

	public void setMailSubject_en(String mailSubject_en) {
		this.mailSubject_en = mailSubject_en;
	}

	public String getMailSubject_cn() {
		return mailSubject_cn;
	}

	public void setMailSubject_cn(String mailSubject_cn) {
		this.mailSubject_cn = mailSubject_cn;
	}

	public String getMarginOutBcc() {
		return marginOutBcc;
	}

	public void setMarginOutBcc(String marginOutBcc) {
		this.marginOutBcc = marginOutBcc;
	}

	public String getMarginOutSender() {
		return marginOutSender;
	}

	public void setMarginOutSender(String marginOutSender) {
		this.marginOutSender = marginOutSender;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getWelcomeSubject_en() {
		return welcomeSubject_en;
	}

	public void setWelcomeSubject_en(String welcomeSubject_en) {
		this.welcomeSubject_en = welcomeSubject_en;
	}

	public String getWelcomeSubject_cn() {
		return welcomeSubject_cn;
	}

	public void setWelcomeSubject_cn(String welcomeSubject_cn) {
		this.welcomeSubject_cn = welcomeSubject_cn;
	}

	public String getWelcomeSender() {
		return welcomeSender;
	}

	public void setWelcomeSender(String welcomeSender) {
		this.welcomeSender = welcomeSender;
	}

	public String getWelcomeBcc() {
		return welcomeBcc;
	}

	public void setWelcomeBcc(String welcomeBcc) {
		this.welcomeBcc = welcomeBcc;
	}
	

	public String getCornJobFailSender() {
		return cornJobFailSender;
	}

	public void setCornJobFailSender(String cornJobFailSender) {
		this.cornJobFailSender = cornJobFailSender;
	}

	
	public String getIbCommissionSummaryReportSender() {
		return ibCommissionSummaryReportSender;
	}

	public void setIbCommissionSummaryReportSender(String ibCommissionSummaryReportSender) {
		this.ibCommissionSummaryReportSender = ibCommissionSummaryReportSender;
	}

	public String getIbCommissionSummaryReportSubject() {
		return ibCommissionSummaryReportSubject;
	}

	public void setIbCommissionSummaryReportSubject(String ibCommissionSummaryReportSubject) {
		this.ibCommissionSummaryReportSubject = ibCommissionSummaryReportSubject;
	}
	
	

	public String getIbCommissionSummaryReportAdminName() {
		return ibCommissionSummaryReportAdminName;
	}

	public void setIbCommissionSummaryReportAdminName(String ibCommissionSummaryReportAdminName) {
		this.ibCommissionSummaryReportAdminName = ibCommissionSummaryReportAdminName;
	}

	public String getIbCommissionSummaryReportAdminEmail() {
		return ibCommissionSummaryReportAdminEmail;
	}

	public void setIbCommissionSummaryReportAdminEmail(String ibCommissionSummaryReportAdminEmail) {
		this.ibCommissionSummaryReportAdminEmail = ibCommissionSummaryReportAdminEmail;
	}

	@Bean
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		host = config.getProperty("email.host", null);
		String portString = config.getProperty("email.port", null);
		username = config.getProperty("email.username", null);
		password = config.getProperty("email.password", null);
		sender = config.getProperty("email.ib.sender", null);
		bcc = config.getProperty("email.ib.recipients.bcc", null);
		mailSubject_en = config.getProperty("email.ib.subject.en", null);
		mailSubject_cn = config.getProperty("email.ib.subject.cn", null);
		cornJobFailSender = config.getProperty("email.corn.job.sender", null);
		cornJobFailSubject = config.getProperty("email.corn.job.fail.subject", null);
		cornJobRecipientsBcc = config.getProperty("email.corn.job.recipients.bcc", null);
		cornJobRecipients = config.getProperty("email.corn.job.recipients", null);
		marginOutSubject_en = config.getProperty("email.margin.out.subject.en", null);
		marginOutSubject_cn = config.getProperty("email.margin.out.subject.cn", null);
		marginOutBcc = config.getProperty("email.margin.out.bcc", null);
		marginOutSender = config.getProperty("email.margin.sender", null);

		welcomeBcc = config.getProperty("email.welcome.bcc", null);
		welcomeSender = config.getProperty("email.welcome.sender", null);
		welcomeSubject_cn = config.getProperty("email.welcome.subject.cn", null);
		welcomeSubject_en = config.getProperty("email.welcome.subject.en", null);
		
		
		ibCommissionSummaryReportSender = config.getProperty("email.ib.commission.summary.report.sender", null);
		ibCommissionSummaryReportSubject = config.getProperty("email.ib.commission.summary.report.subject", null);
		ibCommissionSummaryReportAdminEmail = config.getProperty("email.ib.commission.summary.report.admin.email", null);
		ibCommissionSummaryReportAdminName = config.getProperty("email.ib.commission.summary.report.admin.name", null);

		if (host != null && portString != null) {
			port = Integer.parseInt(portString);
			mailSender.setHost(host);
			mailSender.setPort(port);
			mailSender.setUsername(username);
			mailSender.setPassword(password);
			

			Properties javaMailProperties = new Properties();
			javaMailProperties.put("mail.smtp.starttls.enable", "true");
			javaMailProperties.put("mail.smtp.auth", "true");
			javaMailProperties.put("mail.transport.protocol", "smtp");
			javaMailProperties.put("mail.debug", "true");
			javaMailProperties.put("mail.mime.charset", "UTF-8");
			javaMailProperties.put("mail.smtp.localhost", "ibadmin.hycm.com");
			mailSender.setJavaMailProperties(javaMailProperties);
			mailSender.setDefaultEncoding("UTF-8");
		}
		return mailSender;
	}

	@Bean
	public VelocityEngine getVelocityEngine() throws VelocityException, IOException {
		// VelocityEngineFactory factory = new VelocityEngineFactory();
		// Properties props = new Properties();
		// props.put("resource.loader", "class");
		// props.put("class.resource.loader.class",
		// "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		// factory.setVelocityProperties(props);
		// return factory.createVelocityEngine();
		//

		VelocityEngine velocityEngine = new VelocityEngine();
		// velocityEngin.setProperty(RuntimeConstants.RESOURCE_LOADER,
		// "classpath");
		// velocityEngin.setProperty("classpath.resource.loader.class",
		// ClasspathResourceLoader.class.getName());

		Properties props = new Properties();
		props.put(RuntimeConstants.RESOURCE_LOADER, "classpath");
		props.put("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		props.put("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
		props.put("runtime.log.logsystem.log4j.category", "velocity");
		props.put("runtime.log.logsystem.log4j.logger", "velocity");

		velocityEngine.init(props);
		// Template template =
		// velocityEngin.getTemplate("templates/IbForgotPassword.vm");
		return velocityEngine;
	}
}
