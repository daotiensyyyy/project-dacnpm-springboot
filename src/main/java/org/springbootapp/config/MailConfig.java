package org.springbootapp.config;

import java.util.Properties;

import org.springbootapp.constant.MailConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(MailConstants.HOST_NAME);
		javaMailSender.setPort(587);

		javaMailSender.setUsername(MailConstants.APP_EMAIL);
		javaMailSender.setPassword(MailConstants.APP_PASSWORD);

		Properties properties = javaMailSender.getJavaMailProperties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.debug", "true");

		return javaMailSender;
	}

}
