package com.henyep.ib.terminal.server.global;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class EmailSender {
	private @Value("${email.host}") String host;
	private @Value("${email.port}") int port;
	private @Value("${email.user}") String username;
	private @Value("${email.password}") String password;

	private JavaMailSenderImpl javaMailSender;
	private VelocityEngine velocityEngine;

	public EmailSender() {

		try {
			// Init mail sender
			javaMailSender = new JavaMailSenderImpl();
			javaMailSender.setHost(host);
			javaMailSender.setPort(port);
			javaMailSender.setUsername(username);
			javaMailSender.setPassword(password);
			Properties javaMailProperties = new Properties();
			// javaMailProperties.put("mail.smtp.starttls.enable", "true");
			javaMailProperties.put("mail.smtp.auth", "false");
			javaMailProperties.put("mail.transport.protocol", "smtp");
			javaMailProperties.put("mail.debug", "true");
			javaMailSender.setJavaMailProperties(javaMailProperties);

			// init velocity
			velocityEngine = new VelocityEngine();
			velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			velocityEngine.init();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public JavaMailSender getMailSender() {
		return javaMailSender;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}
}
