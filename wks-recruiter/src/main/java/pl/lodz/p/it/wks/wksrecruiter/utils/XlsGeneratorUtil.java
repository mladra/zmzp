package pl.lodz.p.it.wks.wksrecruiter.utils;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.wks.wksrecruiter.collections.Test;

import java.io.IOException;

@Component
public class XlsGeneratorUtil {
	CellStyle headerStyle;
	CellStyle backgroundStyle;
	CellStyle containerStyle;
	CellStyle cardStyle;
	CellStyle hrCardStyle;
	CellStyle hrStyle;
	CellStyle header2Style;
	CellStyle questionStyle;
	CellStyle indicatorStyle;
	CellStyle indicatorBlackStyle;
	CellStyle answerStyle;
	CellStyle answerGrayStyle;
	CellStyle answerAfterStyle;
	CellStyle answerGrayAfterStyle;
	CellStyle pointsStyle;
	
	public byte[] generate(Test test) throws IOException {
		byte[] out;
		ByteOutputStream os = new ByteOutputStream();
		Workbook workbook = this.createWorkbook(test);
		workbook.write(os);
		out = os.getBytes();
		os.close();
		return out;
	}
	
	private Workbook createWorkbook(Test test) {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet(test.getName());
		Row row = null;
		Cell col = null;
		this.initCellStyles(workbook);
		
		
		return workbook;
	}
	
	private void initCellStyles(Workbook workbook) {
		this.initHeaderStyle(workbook);
		this.initBackgroundStyle(workbook);
		this.initContainerStyle(workbook);
		this.initCardStyle(workbook);
		this.initHrCardStyle(workbook);
		this.initHrStyle(workbook);
		this.initHeader2Style(workbook);
		this.initQuestionStyle(workbook);
		this.initIndicatorStyle(workbook);
		this.initIndicatorBlackStyle(workbook);
		this.initAnswerStyle(workbook);
		this.initAnswerGrayStyle(workbook);
		this.initAnswerAfterStyle(workbook);
		this.initAnswerGrayAfterStyle(workbook);
		this.initPointsStyle(workbook);
	}
	
	private void initPointsStyle(Workbook workbook) {
		pointsStyle = workbook.createCellStyle();
		pointsStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		pointsStyle.setAlignment(HorizontalAlignment.RIGHT);
		pointsStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		Font font = workbook.createFont();
		font.setFontName("Liberation Sans");
		font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		font.setFontHeightInPoints((short)10);
		pointsStyle.setFont(font);
	}
	
	private void initAnswerGrayAfterStyle(Workbook workbook) {
		answerGrayAfterStyle = workbook.createCellStyle();
		answerGrayAfterStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		answerGrayAfterStyle.setAlignment(HorizontalAlignment.LEFT);
		answerGrayAfterStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		answerGrayAfterStyle.setBorderTop(BorderStyle.THIN);
		answerGrayAfterStyle.setBorderBottom(BorderStyle.THIN);
		answerGrayAfterStyle.setBorderRight(BorderStyle.THIN);
		answerGrayAfterStyle.setRightBorderColor(HSSFColor.HSSFColorPredefined.GREY_50_PERCENT.getIndex());
		answerGrayAfterStyle.setTopBorderColor(HSSFColor.HSSFColorPredefined.GREY_50_PERCENT.getIndex());
		answerGrayAfterStyle.setBottomBorderColor(HSSFColor.HSSFColorPredefined.GREY_50_PERCENT.getIndex());
		Font font = workbook.createFont();
		font.setFontName("Liberation Sans");
		font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		font.setFontHeightInPoints((short)12);
		answerGrayAfterStyle.setFont(font);
	}
	
	private void initAnswerAfterStyle(Workbook workbook) {
		answerAfterStyle = workbook.createCellStyle();
		answerAfterStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		answerAfterStyle.setAlignment(HorizontalAlignment.LEFT);
		answerAfterStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		answerAfterStyle.setBorderTop(BorderStyle.THIN);
		answerAfterStyle.setBorderBottom(BorderStyle.THIN);
		answerAfterStyle.setBorderRight(BorderStyle.THIN);
		answerAfterStyle.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		answerAfterStyle.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		answerAfterStyle.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		Font font = workbook.createFont();
		font.setFontName("Liberation Sans");
		font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		font.setFontHeightInPoints((short)12);
		answerAfterStyle.setFont(font);
	}
	
	private void initAnswerGrayStyle(Workbook workbook) {
		answerGrayStyle = workbook.createCellStyle();
		answerGrayStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		answerGrayStyle.setAlignment(HorizontalAlignment.LEFT);
		answerGrayStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		answerGrayStyle.setBorderTop(BorderStyle.THIN);
		answerGrayStyle.setBorderBottom(BorderStyle.THIN);
		answerGrayStyle.setTopBorderColor(HSSFColor.HSSFColorPredefined.GREY_50_PERCENT.getIndex());
		answerGrayStyle.setBottomBorderColor(HSSFColor.HSSFColorPredefined.GREY_50_PERCENT.getIndex());
		Font font = workbook.createFont();
		font.setFontName("Liberation Sans");
		font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		font.setFontHeightInPoints((short)12);
		answerGrayStyle.setFont(font);
	}
	
	private void initAnswerStyle(Workbook workbook) {
		answerStyle = workbook.createCellStyle();
		answerStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		answerStyle.setAlignment(HorizontalAlignment.LEFT);
		answerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		answerStyle.setBorderTop(BorderStyle.THIN);
		answerStyle.setBorderBottom(BorderStyle.THIN);
		answerStyle.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		answerStyle.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		Font font = workbook.createFont();
		font.setFontName("Liberation Sans");
		font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		font.setFontHeightInPoints((short)12);
		answerStyle.setFont(font);
	}
	
	private void initIndicatorBlackStyle(Workbook workbook) {
		indicatorBlackStyle = workbook.createCellStyle();
		indicatorBlackStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		indicatorBlackStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		indicatorBlackStyle.setAlignment(HorizontalAlignment.CENTER);
		Font font = workbook.createFont();
		font.setFontName("Wingdings");
		font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		font.setFontHeightInPoints((short)12);
		indicatorBlackStyle.setFont(font);
	}
	
	private void initIndicatorStyle(Workbook workbook) {
		indicatorStyle = workbook.createCellStyle();
		indicatorStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		indicatorStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		indicatorStyle.setAlignment(HorizontalAlignment.CENTER);
		indicatorStyle.setBorderTop(BorderStyle.THIN);
		indicatorStyle.setBorderBottom(BorderStyle.THIN);
		indicatorStyle.setTopBorderColor(HSSFColor.HSSFColorPredefined.GREY_50_PERCENT.getIndex());
		indicatorStyle.setBottomBorderColor(HSSFColor.HSSFColorPredefined.GREY_50_PERCENT.getIndex());
		Font font = workbook.createFont();
		font.setFontName("Wingdings");
		font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		font.setFontHeightInPoints((short)12);
		indicatorStyle.setFont(font);
	}
	
	private void initQuestionStyle(Workbook workbook) {
		questionStyle = workbook.createCellStyle();
		questionStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		questionStyle.setAlignment(HorizontalAlignment.LEFT);
		questionStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		Font font = workbook.createFont();
		font.setFontName("Liberation Sans");
		font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		font.setFontHeightInPoints((short)12);
		questionStyle.setFont(font);
	}
	
	private void initHeader2Style(Workbook workbook) {
		header2Style = workbook.createCellStyle();
		header2Style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_80_PERCENT.getIndex());
		header2Style.setVerticalAlignment(VerticalAlignment.CENTER);
		header2Style.setAlignment(HorizontalAlignment.LEFT);
		Font font = workbook.createFont();
		font.setFontName("Liberation Sans");
		font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		font.setFontHeightInPoints((short)10);
		header2Style.setFont(font);
	}
	
	private void initHrStyle(Workbook workbook) {
		hrStyle = workbook.createCellStyle();
		hrStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		hrStyle.setBorderTop(BorderStyle.THICK);
		hrStyle.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
	}
	
	private void initHrCardStyle(Workbook workbook) {
		hrCardStyle = workbook.createCellStyle();
		hrCardStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.ROYAL_BLUE.getIndex());
		hrCardStyle.setBorderTop(BorderStyle.THICK);
		hrCardStyle.setTopBorderColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
	}
	
	private void initCardStyle(Workbook workbook) {
		cardStyle = workbook.createCellStyle();
		cardStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.ROYAL_BLUE.getIndex());
		cardStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cardStyle.setAlignment(HorizontalAlignment.LEFT);
		Font font = workbook.createFont();
		font.setFontName("Liberation Sans");
		font.setBold(true);
		font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		font.setFontHeightInPoints((short)14);
		cardStyle.setFont(font);
	}
	
	private void initContainerStyle(Workbook workbook) {
		containerStyle = workbook.createCellStyle();
		containerStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		containerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		containerStyle.setAlignment(HorizontalAlignment.LEFT);
		Font font = workbook.createFont();
		font.setFontName("Liberation Sans");
		font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		font.setFontHeightInPoints((short)10);
		containerStyle.setFont(font);
	}
	
	private void initBackgroundStyle(Workbook workbook) {
		backgroundStyle = workbook.createCellStyle();
		backgroundStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
	}
	
	private void initHeaderStyle(Workbook workbook) {
		headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_80_PERCENT.getIndex());
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		Font font = workbook.createFont();
		font.setFontName("Liberation Sans");
		font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		font.setFontHeightInPoints((short)10);
		headerStyle.setFont(font);
	}
}
