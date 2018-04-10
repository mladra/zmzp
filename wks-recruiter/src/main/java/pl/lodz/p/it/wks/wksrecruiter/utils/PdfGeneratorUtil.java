package pl.lodz.p.it.wks.wksrecruiter.utils;

import com.lowagie.text.DocumentException;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;
import pl.lodz.p.it.wks.wksrecruiter.collections.Test;

@Component
public class PdfGeneratorUtil {
	private static String TEMPLATE_NAME = "testPdfTemplate";
	
	@Autowired
	private TemplateEngine templateEngine;
	
	public byte[] generate(Test test) throws DocumentException {
		byte[] out;
		ByteOutputStream os = new ByteOutputStream();
		Context ctx = new Context();
		ctx.setVariable("test",test);
		String processedHtml = templateEngine.process(TEMPLATE_NAME,ctx);
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocumentFromString(processedHtml);
		renderer.layout();
		renderer.createPDF(os);
		out = os.getBytes();
		os.close();
		return out;
	}
}
