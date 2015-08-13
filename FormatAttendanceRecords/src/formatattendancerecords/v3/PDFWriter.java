package formatattendancerecords.v3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	private Calendar calendar;
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
	
	private void getWeekCells(Map<String, List<PdfPCell>> cells) {
		Font font = new Font(baseFont);
		font.setStyle(Font.BOLD);
		font.setSize(12);
		int row = 0;
		
		cells.put(Integer.toString(row), new ArrayList<PdfPCell>());
		for(int i = 0; i < COLUMNS_OF_TABLE; i++) {
			PdfPCell cell = new PdfPCell(new Phrase(DateUtil.WEEK[i], font));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cells.get(Integer.toString(row)).add(cell);
		}
	}
	
	private void getDateCells(Map<String, List<PdfPCell>> cells) {
		Font font = new Font(baseFont);
		font.setSize(9);
		int row = 1;
		
		DateUtil.setLastDayOfMonth(calendar);
		int end = calendar.get(Calendar.DAY_OF_MONTH);
		DateUtil.setFirstDayOfMonth(calendar);
		int begin = calendar.get(Calendar.DAY_OF_MONTH);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		cells.put(Integer.toString(row), new ArrayList<PdfPCell>());
		for(int i = 1; i < dayOfWeek; i++) {
			PdfPCell cell = new PdfPCell();
			cells.get(Integer.toString(row)).add(cell);
		}
		
		for(int i = begin; i <= end; i++) {
			String date = DateUtil.formatDate(calendar, "yyyy-MM-dd");
			
			PdfPCell cell = new PdfPCell(new Phrase(date, font));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cells.get(Integer.toString(row)).add(cell);
			
			if((i + dayOfWeek - 1) % COLUMNS_OF_TABLE == 0) {
				row += 2;
				cells.put(Integer.toString(row), new ArrayList<PdfPCell>());
			}
			
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		for(int i = dayOfWeek; i <= COLUMNS_OF_TABLE; i++) {
			PdfPCell cell = new PdfPCell();
			cells.get(Integer.toString(row)).add(cell);
		}
		
		calendar.add(Calendar.MONTH, -1);
	}

	private void getTimeCells(Map<String, List<PdfPCell>> cells, Map<String, Day> days) {
		Font font = new Font(baseFont);
		font.setSize(9);
		
		DateUtil.setLastDayOfMonth(calendar);
		int end = calendar.get(Calendar.DAY_OF_MONTH);
		DateUtil.setFirstDayOfMonth(calendar);
		int begin = calendar.get(Calendar.DAY_OF_MONTH);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		int row = 2;
		cells.put(Integer.toString(row), new ArrayList<PdfPCell>());
		for(int i = 1; i < dayOfWeek; i++) {
			PdfPCell cell = new PdfPCell();
			cells.get(Integer.toString(row)).add(cell);
		}
		
		for(int i = begin; i <= end; i++) {
			String date = DateUtil.formatDate(calendar, "yyyy-MM-dd");
			Day day = days.get(date);
			StringBuffer sb = new StringBuffer();
			if(day != null) {
				Set<String> times = day.getTimes();
				for(String time : times) {
					sb.append(time).append("\n");
				}
			} else {
				sb.append("\n\n");
			}
			PdfPCell cell = new PdfPCell(new Phrase(sb.toString(), font));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setVerticalAlignment(PdfPCell.ALIGN_BOTTOM);
			cells.get(Integer.toString(row)).add(cell);
			
			if((i + dayOfWeek - 1) % COLUMNS_OF_TABLE == 0) {
				row += 2;
				cells.put(Integer.toString(row), new ArrayList<PdfPCell>());
			}
			
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}

		calendar.add(Calendar.MONTH, -1);
		dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		for(int i = dayOfWeek; i <= COLUMNS_OF_TABLE; i++) {
			PdfPCell cell = new PdfPCell();
			cells.get(Integer.toString(row)).add(cell);
		}
		
	}
	
	private PdfPTable getTable(Map<String, Day> days) {
		Font fDay = new Font(baseFont);
		fDay.setSize(9);
		
		Map<String, List<PdfPCell>> cells = new HashMap<String, List<PdfPCell>>();
		getWeekCells(cells);
		getDateCells(cells);
		getTimeCells(cells, days);
		
		PdfPTable table = new PdfPTable(COLUMNS_OF_TABLE);
		table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);
		table.setSpacingBefore(5);
		
		for(int i = 0; i < cells.size(); i++) {
			List<PdfPCell> list = cells.get(Integer.toString(i));
			if(list != null) {
				for(PdfPCell cell : list) {
					table.addCell(cell);
				}
			}
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
