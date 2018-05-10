package pl.lodz.p.it.wks.wksrecruiter.services;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.lodz.p.it.wks.wksrecruiter.collections.TestAttempt;

@Service
public class MailBuilder {
	private TemplateEngine templateEngine;
	
	public MailBuilder(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}
	
	public String buildMail(TestAttempt testAttempt) {
		Context ctx = new Context();
		ctx.setVariable("test", testAttempt);
		return templateEngine.process("mailTemplate", ctx);
	}
}
