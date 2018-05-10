package pl.lodz.p.it.wks.wksrecruiter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.wks.wksrecruiter.collections.TestAttempt;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;

@Service
public class MailService {
	private JavaMailSender mailSender;
	
	@Autowired
	private MailBuilder mailBuilder;
	
	@Autowired
	public MailService(JavaMailSender javaMailSender) {
		this.mailSender = javaMailSender;
	}
	
	public void sendEmail(String emailAddress, TestAttempt testAttempt) throws WKSRecruiterException {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("sample@dolszewski.com");
			messageHelper.setTo(emailAddress);
			messageHelper.setSubject("Sample mail subject");
			String content = mailBuilder.buildMail(testAttempt);
			messageHelper.setText(content, true);
		};
		try {
			mailSender.send(messagePreparator);
		} catch (MailException e) {
			throw WKSRecruiterException.of(e);
		}
	}
}
