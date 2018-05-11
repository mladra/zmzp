package pl.lodz.p.it.wks.wksrecruiter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Properties;

@Configuration
public class ThymeleafConfig {
	@Bean
	public ClassLoaderTemplateResolver pdfTemplateResolver() {
		ClassLoaderTemplateResolver pdfTemplateResolver = new ClassLoaderTemplateResolver();
		pdfTemplateResolver.setPrefix("templates/");
		pdfTemplateResolver.setTemplateMode("HTML5");
		pdfTemplateResolver.setSuffix(".html");
		pdfTemplateResolver.setTemplateMode("XHTML");
		pdfTemplateResolver.setCharacterEncoding("UTF-8");
		pdfTemplateResolver.setOrder(1);
		return pdfTemplateResolver;
	}
	
	@Bean
	public JavaMailSender mailSender(Environment env) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(env.getProperty("spring.mail.host"));
		mailSender.setPort(Integer.parseInt(env.getProperty("spring.mail.port")));
		mailSender.setDefaultEncoding(env.getProperty("spring.mail.defaultEncoding"));
		mailSender.setProtocol(env.getProperty("spring.mail.protocol"));
		mailSender.setUsername("wks.recruiter@gmail.com");
		mailSender.setPassword("zwinne1234");
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");
		
		return mailSender;
	}
}
