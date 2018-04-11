package pl.lodz.p.it.wks.wksrecruiter.utils;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.wks.wksrecruiter.collections.Position;
import pl.lodz.p.it.wks.wksrecruiter.collections.Test;
import pl.lodz.p.it.wks.wksrecruiter.collections.questions.NumberQuestionParams;
import pl.lodz.p.it.wks.wksrecruiter.collections.questions.QuestionInfo;
import pl.lodz.p.it.wks.wksrecruiter.collections.questions.ScaleQuestionParams;
import pl.lodz.p.it.wks.wksrecruiter.collections.questions.SelectionQuestionParams;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

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
	CellStyle indicatorBlackAltStyle;
	CellStyle indicatorAltStyle;
	
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
		this.createHeader(sheet, test);
		row = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(row);
		for(QuestionInfo question : test.getQuestions()) this.renderQuestion(sheet, question);
		row = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(row);
		row = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderBackground(row);
		sheet.setColumnWidth(2,sheet.getColumnWidth(2)*2);
		return workbook;
	}
	
	private void renderQuestion(Sheet sheet, QuestionInfo question) {
		Row questionRow = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(questionRow);
		for(int i=2; i<10; i++) questionRow.createCell(i).setCellStyle(hrStyle);
		questionRow = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(questionRow);
		questionRow.getCell(9).setCellStyle(pointsStyle);
		questionRow.getCell(9).setCellValue("___  /  "+question.getMaxPoints()+"  ");
		questionRow = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(questionRow);
		questionRow.getCell(2).setCellStyle(questionStyle);
		questionRow.setHeightInPoints((short)16);
		questionRow.getCell(2).setCellValue(question.getQuestionNumber()+". "+question.getQuestionPhrase());
		questionRow = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(questionRow);
		switch (question.getType()) {
			case STRING: this.renderString(sheet); break;
			case MULTIPLE_CHOICE: this.renderMultipleChoice(sheet, question); break;
			case SINGLE_CHOICE: this.renderSingleChoice(sheet,question); break;
			case NUMBER: this.renderNumber(sheet,question); break;
			case SCALE: this.renderScale(sheet,question); break;
		}
	}
	
	private void renderScale(Sheet sheet, QuestionInfo question) {
		Row r = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(r);
		r.setHeight((short)(r.getHeight()*2));
		r.getCell(2).setCellStyle(indicatorBlackAltStyle);
		double min = ((ScaleQuestionParams)question.getParams()).getMinValue();
		double max = ((ScaleQuestionParams)question.getParams()).getMaxValue();
		r.getCell(2).setCellValue("("+min+" - "+max+")");
		for (int i=3; i<9; i++) r.getCell(i).setCellStyle(answerStyle);
		r.getCell(9).setCellStyle(answerAfterStyle);
		r = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(r);
		r = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(r);
	}
	
	private void renderMultipleChoice(Sheet sheet, QuestionInfo question) {
		Row r;
		Collection<String> options = ((SelectionQuestionParams)question.getParams()).getOptions();
		for(String s : options) {
			r = sheet.createRow(sheet.getLastRowNum()+1);
			this.renderContainer(r);
			r.setHeight((short)(r.getHeight()*2));
			r.getCell(2).setCellStyle(indicatorStyle);
			r.getCell(2).setCellValue("\uF06F");
			for (int i=3; i<9; i++) r.getCell(i).setCellStyle(answerGrayStyle);
			r.getCell(9).setCellStyle(answerGrayAfterStyle);
			r.getCell(3).setCellValue(s);
		}
		r = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(r);
		r = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(r);
	}
	
	private void renderSingleChoice(Sheet sheet, QuestionInfo question) {
		Row r;
		Collection<String> options = ((SelectionQuestionParams)question.getParams()).getOptions();
		for(String s : options) {
			r = sheet.createRow(sheet.getLastRowNum()+1);
			this.renderContainer(r);
			r.setHeight((short)(r.getHeight()*2));
			r.getCell(2).setCellStyle(indicatorAltStyle);
			r.getCell(2).setCellValue("O");
			for (int i=3; i<9; i++) r.getCell(i).setCellStyle(answerGrayStyle);
			r.getCell(9).setCellStyle(answerGrayAfterStyle);
			r.getCell(3).setCellValue(s);
		}
		r = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(r);
		r = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(r);
	}
	
	private void renderNumber(Sheet sheet, QuestionInfo question) {
		Row r = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(r);
		r.getCell(2).setCellStyle(indicatorBlackAltStyle);
		r.setHeight((short)(r.getHeight()*2));
		double min = ((NumberQuestionParams)question.getParams()).getMinValue();
		double max = ((NumberQuestionParams)question.getParams()).getMaxValue();
		r.getCell(2).setCellValue("("+min+" - "+max+")");
		for (int i=3; i<9; i++) r.getCell(i).setCellStyle(answerStyle);
		r.getCell(9).setCellStyle(answerAfterStyle);
		r = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(r);
		r = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(r);
	}
	
	private void renderString(Sheet sheet) {
		Row r = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(r);
		r.setHeight((short)(r.getHeight()*2));
		r.getCell(2).setCellStyle(indicatorBlackStyle);
		r.getCell(2).setCellValue("\uF021");
		for (int i=3; i<9; i++) r.getCell(i).setCellStyle(answerStyle);
		r.getCell(9).setCellStyle(answerAfterStyle);
		r = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(r);
		r = sheet.createRow(sheet.getLastRowNum()+1);
		this.renderContainer(r);
	}
	
	private void createHeader(Sheet sheet, Test test) {
		Row header = sheet.createRow(0);
		header.setHeight((short)(header.getHeight()*2));
		for(int i=0; i<12; i++) {
			Cell currentCell = header.createCell(i);
			if(i==1) currentCell.setCellValue(test.getName());
			if(i==8) currentCell.setCellValue("WKS Recruiter Test by "+test.getAuthor().getName()+" "+test.getAuthor().getSurname());
			currentCell.setCellStyle(headerStyle);
		}
		header = sheet.createRow(1);
		this.renderBackground(header);
		this.renderContainer(sheet.createRow(2));
		for(int i=3; i<7; i++) { this.renderCard(sheet.createRow(i)); }
		sheet.getRow(4).setHeightInPoints((short)16);
		sheet.getRow(4).getCell(3).setCellValue(test.getName());
		sheet.getRow(4).getCell(7).setCellStyle(pointsStyle);
		sheet.getRow(4).getCell(8).setCellStyle(pointsStyle);
		sheet.getRow(4).getCell(8).setCellValue("/ "+test.getQuestions().stream().mapToInt(l->l.getMaxPoints()).sum()+"  ");
		for (int i=3; i<9;i++) { sheet.getRow(6).getCell(i).setCellStyle(hrCardStyle); }
		header = sheet.createRow(7);
		this.renderCard(header);
		header.getCell(7).setCellStyle(header2Style);
		header.getCell(8).setCellStyle(header2Style);
		header.getCell(7).setCellValue("Positions:");
		header = sheet.createRow(8);
		int m = sheet.getLastRowNum();
		for (int i=m; i<m+test.getPositions().size(); i++) {
			this.renderCard(header);
			header.getCell(7).setCellValue(this.getPositionName(test.getPositions(), i-m+1));
			header.getCell(7).setCellStyle(containerStyle);
			header.getCell(8).setCellStyle(containerStyle);
			int next = sheet.getLastRowNum() + 1;
			header = sheet.createRow(next);
		}
		if (test.getPositions().size()<3) {
			for (int i=0; i<3-test.getPositions().size(); i++ ) {
				this.renderCard(header);
				header = sheet.createRow(header.getRowNum()+1);
			}
		}
		this.renderCard(header);
		for (int rowH=7; rowH<11; rowH++) {
			header = sheet.getRow(rowH);
			for(int colH = 3; colH<6; colH++) { header.getCell(colH).setCellStyle(containerStyle); }
		}
		sheet.getRow(7).getCell(3).setCellValue("Name: ");
		sheet.getRow(8).getCell(3).setCellValue("Surame: ");
		sheet.getRow(9).getCell(3).setCellValue("Email address: ");
		sheet.getRow(10).getCell(3).setCellValue("Chosen position: ");

	}
	
	private String getPositionName(Collection<Position> positions, int i) {
		String result = null;
		Iterator<Position> iter = positions.iterator();
		for(int j=0; j<i; j++) {
			if(iter.hasNext()) result = iter.next().getName();
		}
		return result;
	}
	
	private void renderCard(Row row) {
		for (int i=0; i<12; i++) {
			if (i==0 || i==11) row.createCell(i).setCellStyle(backgroundStyle);
			else if (i==1 || i==10) row.createCell(i).setCellStyle(containerStyle);
			else row.createCell(i).setCellStyle(cardStyle);
		}
	}
	
	private void renderContainer(Row row) {
		for (int i=0; i<12; i++) {
			if (i==0 || i==11) row.createCell(i).setCellStyle(backgroundStyle);
			else row.createCell(i).setCellStyle(containerStyle);
		}
	}
	
	private void renderBackground(Row row) {
		for (int i=0; i<12; i++) { row.createCell(i).setCellStyle(backgroundStyle); }
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
		this.initIndicatorBlackAltStyle(workbook);
		this.initIndicatorGrayAltStyle(workbook);
	}
	
	private void initIndicatorGrayAltStyle(Workbook workbook) {
		indicatorAltStyle = workbook.createCellStyle();
		indicatorAltStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		indicatorAltStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		indicatorAltStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		indicatorAltStyle.setAlignment(HorizontalAlignment.CENTER);
		indicatorAltStyle.setBorderTop(BorderStyle.THIN);
		indicatorAltStyle.setBorderBottom(BorderStyle.THIN);
		indicatorAltStyle.setTopBorderColor(HSSFColor.HSSFColorPredefined.GREY_50_PERCENT.getIndex());
		indicatorAltStyle.setBottomBorderColor(HSSFColor.HSSFColorPredefined.GREY_50_PERCENT.getIndex());
		Font font = workbook.createFont();
		font.setFontName("Liberation Sans");
		font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		font.setFontHeightInPoints((short)18);
		indicatorAltStyle.setFont(font);
	}
	
	private void initIndicatorBlackAltStyle(Workbook workbook) {
		indicatorBlackAltStyle = workbook.createCellStyle();
		indicatorBlackAltStyle = workbook.createCellStyle();
		indicatorBlackAltStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
		indicatorBlackAltStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		indicatorBlackAltStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		indicatorBlackAltStyle.setAlignment(HorizontalAlignment.CENTER);
		Font font = workbook.createFont();
		font.setFontName("Liberation Sans");
		font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		font.setFontHeightInPoints((short)12);
		indicatorBlackAltStyle.setFont(font);
	}
	
	private void initPointsStyle(Workbook workbook) {
		pointsStyle = workbook.createCellStyle();
		pointsStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		pointsStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
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
		answerGrayAfterStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
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
		answerAfterStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
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
		answerGrayStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
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
		answerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
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
		indicatorBlackStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
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
		indicatorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
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
		questionStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
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
		header2Style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
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
		hrStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		hrStyle.setBorderTop(BorderStyle.THIN);
		hrStyle.setTopBorderColor(HSSFColor.HSSFColorPredefined.ROYAL_BLUE.getIndex());
	}
	
	private void initHrCardStyle(Workbook workbook) {
		hrCardStyle = workbook.createCellStyle();
		hrCardStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.ROYAL_BLUE.getIndex());
		hrCardStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		hrCardStyle.setBorderTop(BorderStyle.THICK);
		hrCardStyle.setTopBorderColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
	}
	
	private void initCardStyle(Workbook workbook) {
		cardStyle = workbook.createCellStyle();
		cardStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.ROYAL_BLUE.getIndex());
		cardStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
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
		containerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
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
		backgroundStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	}
	
	private void initHeaderStyle(Workbook workbook) {
		headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		Font font = workbook.createFont();
		font.setFontName("Liberation Sans");
		font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
		font.setFontHeightInPoints((short)10);
		headerStyle.setFont(font);
	}
}
