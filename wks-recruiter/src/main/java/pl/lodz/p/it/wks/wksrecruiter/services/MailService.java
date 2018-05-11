package pl.lodz.p.it.wks.wksrecruiter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.lodz.p.it.wks.wksrecruiter.collections.TestAttempt;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

	private final JavaMailSender mailSender;
	private final Environment env;
	private final TemplateEngine templateEngine;
	private final MailService mailService;

	@Autowired
	public MailService(JavaMailSender mailSender, Environment env, TemplateEngine templateEngine, MailService mailService) {
		this.mailSender = mailSender;
		this.env = env;
		this.templateEngine = templateEngine;
		this.mailService = mailService;
	}

	public void sendEmail(String emailAddress, TestAttempt testAttempt) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(env.getProperty("spring.mail.username"));
		helper.setTo(emailAddress);
		helper.setSubject("WKS-Recruiter: Results");
		Context ctx = new Context();
		ctx.setVariable("test", testAttempt);
		String content = templateEngine.process("mailTemplate",ctx);
		helper.setText(content, true);
		mailSender.send(message);
	}
}
