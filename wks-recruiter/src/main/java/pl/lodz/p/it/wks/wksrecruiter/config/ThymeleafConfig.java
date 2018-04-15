package pl.lodz.p.it.wks.wksrecruiter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

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
}
