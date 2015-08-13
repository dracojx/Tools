package formatattendancerecords.v3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Collection;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.itextpdf.text.DocumentException;

public class PDFReader {

	public static void main(String[] args) throws DocumentException, IOException {
		long start = System.currentTimeMillis();
		String[] filenames = FileUtil.getFilenames();
		for(int i = 0; i < filenames.length; i++) {
			String filename = filenames[i];
			String content = getText(filename);
			String[] lines = getLines(content);
			Data data = new Data(lines);
			Calendar calendar = data.getCalendar();
			Collection<Department> departments = data.getDepartments();
			String file = "我选周杰伦\\" + filename;
			PDFWriter writer = new PDFWriter(calendar, departments, file);
			writer.write();
		}
		long finish = System.currentTimeMillis();
		System.out.println("用时:" + (finish - start) + "毫秒");
	}

	private static String getText(String filename) {
		try {
			FileInputStream fis = new FileInputStream(filename);
			PDFParser parser = new PDFParser(fis);
			parser.parse();
			PDDocument pd = parser.getPDDocument();
			PDFTextStripper stripper = new PDFTextStripper();
			String str = stripper.getText(pd);
			pd.close();
			return str;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String[] getLines(String content) {
		String[] lines = content.split("\n");
		return lines;
	}
}
