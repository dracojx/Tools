package formatattendancerecords.v3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFWriter {
	
	public static final int COLUMNS_OF_TABLE = 7;

	private final Calendar calendar;
	private Collection<Department> departments;
	private String filename;
	private BaseFont baseFont;

	public PDFWriter(Calendar calendar, Collection<Department> departments,
			String filename) throws DocumentException, IOException {
		this.calendar = calendar;
		this.departments = departments;
		this.filename = filename;
		baseFont = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF",
				BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
	}

	private Paragraph getParaTitle() {
		DateUtil.setFirstDayOfMonth(calendar);
		String begin = DateUtil.formatDate(calendar, "MM.dd");
		DateUtil.setLastDayOfMonth(calendar);
		String end = DateUtil.formatDate(calendar, "MM.dd");
		String title = "考勤记录表 " + begin + " - " + end;
		
		Font font = new Font(baseFont);
		font.setStyle(Font.BOLD);
		font.setSize(20);
		
		Paragraph para = new Paragraph(title, font);
		para.setAlignment(Paragraph.ALIGN_CENTER);
		return para;
	}
	
	private Paragraph getParaStaff(String depName, Staff staff) {
		String id = staff.getId();
		String name = staff.getName();
		String title = "\n部门:" + depName + " 考勤号码:" + id + " 姓名:" + name;

		Font font = new Font(baseFont);
		font.setStyle(Font.BOLD);
		font.setSize(14);
		
		Paragraph para = new Paragraph(title, font);
		
		return para;
	}
	
	private PdfPTable getTable(Map<String, Day> days) {
		Font fWeek = new Font(baseFont);
		Font fDay = new Font(baseFont);
		fWeek.setStyle(Font.BOLD);
		fWeek.setSize(12);
		fDay.setSize(9);
		
		List<PdfPCell> cells = new ArrayList<PdfPCell>();
	
		for(int i = 0; i < COLUMNS_OF_TABLE; i++) {
			PdfPCell cell = new PdfPCell(new Phrase(DateUtil.WEEK[i], fWeek));
			cells.add(cell);
		}
		
		PdfPTable table = new PdfPTable(COLUMNS_OF_TABLE);
		
		for(PdfPCell cell : cells) {
			table.addCell(cell);
		}
		
		return table;
	}

	public void write() throws DocumentException, FileNotFoundException {
		Document document = new Document(PageSize.A4);
		try {
			PdfWriter.getInstance(document, new FileOutputStream(filename));
		} catch (FileNotFoundException e) {
			File file = new File(filename);
			file.getParentFile().mkdirs();
			PdfWriter.getInstance(document, new FileOutputStream(filename));
		}
		
		document.open();
		document.add(getParaTitle());
		
		Iterator<Department> depIt = departments.iterator();
		while(depIt.hasNext()) {
			Department dep = depIt.next();
			String depName = dep.getDepartName();
			Collection<Staff> staffs = dep.getStaffs().values();
			Iterator<Staff> staffIt = staffs.iterator();
			while(staffIt.hasNext()) {
				Staff staff = staffIt.next();
				document.add(getParaStaff(depName, staff));
				document.add(getTable(staff.getDays()));
			}
		}
		
		document.close();
	}
}
