package pl.lodz.p.it.wks.wksrecruiter.utils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import pl.lodz.p.it.wks.wksrecruiter.collections.Test;

import java.io.IOException;

@Component
public class PdfGeneratorUtil {
	private static String TEMPLATE_NAME = "testPdfTemplate";
	private static String[] fonts = new String[]{
			"Roboto-Bold.ttf",
			"Roboto-BoldItalic.ttf",
			"Roboto-Italic.ttf",
			"Roboto-Light.ttf",
			"Roboto-LightItalic.ttf",
			"Roboto-Regular.ttf",
			"Roboto-Thin.ttf",
			"Roboto-ThinItalic.ttf",
			"RobotoCondensed-Bold.ttf",
			"RobotoCondensed-BoldItalic.ttf",
			"RobotoCondensed-Italic.ttf",
			"RobotoCondensed-Regular.ttf",
			"fa-brands-400.ttf",
			"fa-regular-400.ttf",
			"fa-solid-900.ttf",
	};
	
	@Autowired
	private TemplateEngine templateEngine;
	
	public byte[] generate(Test test) throws DocumentException, IOException {
		byte[] out;
		ByteOutputStream os = new ByteOutputStream();
		Context ctx = new Context();
		ctx.setVariable("test",test);
		String processedHtml = templateEngine.process(TEMPLATE_NAME,ctx);
		ITextRenderer renderer = new ITextRenderer();
		ITextFontResolver iTextFontResolver = renderer.getFontResolver();
		this.initFonts(iTextFontResolver);
		renderer.setDocumentFromString(processedHtml);
		renderer.layout();
		renderer.createPDF(os);
		out = os.getBytes();
		os.close();
		return out;
	}
	
	private void initFonts(ITextFontResolver iTextFontResolver) throws IOException, DocumentException {
		for(String font : fonts) {
			ClassPathResource cpr = new ClassPathResource("/static/fonts/"+font);
			iTextFontResolver.addFont(cpr.getURL().toString(), BaseFont.IDENTITY_H, true);
		}
	}
}
