package formatattendancerecords.v1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFReader {

	public static void main(String[] args) {
		String filename = "考勤记录表.pdf";
		String content = getText(filename);
		ExcelWriter.writeWorkbook(content, "考勤.xlsx");
	}

	public static String getText(String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			PDFParser parser = new PDFParser(fis);
			parser.parse();
			PDDocument pd = parser.getPDDocument();
			PDFTextStripper stripper = new PDFTextStripper();
			String str = stripper.getText(pd);
			return str;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
