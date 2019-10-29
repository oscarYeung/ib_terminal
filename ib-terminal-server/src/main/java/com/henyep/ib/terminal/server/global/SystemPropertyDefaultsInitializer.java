package com.henyep.ib.terminal.server.global;

import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;

public class SystemPropertyDefaultsInitializer implements WebApplicationInitializer {

	// private static final Logger logger = Logger
	// .getLogger(SystemPropertyDefaultsInitializer.class);

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// logger.info("SystemPropertyWebApplicationInitializer onStartup
		// called");

		// can be set runtime before Spring instantiates any beans
		// TimeZone.setDefault(TimeZone.getTimeZone("GMT+00:00"));
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

		// cannot override encoding in Spring at runtime as some strings have
		// already been read
		// however, we can assert and ensure right values are loaded here

		// verify system property is set
		// Assert.isTrue("UTF-8".equals(System.getProperty("file.encoding")));

		// and actually verify it is being used
		// Charset charset = Charset.defaultCharset();
		// Assert.isTrue(charset.equals(Charset.forName("UTF-8")));

		// locale
		// set and verify language

	}
}
