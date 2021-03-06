package pl.lodz.p.it.wks.wksrecruiter.services;

import pl.lodz.p.it.wks.wksrecruiter.collections.TestAttempt;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;

import javax.mail.MessagingException;

public interface MailService {
	void sendMail(String email, TestAttempt testAttempt) throws WKSRecruiterException;
}
