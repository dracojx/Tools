package formatattendancerecords;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {
	private static CellStyle titleCellStyle = null;
	private static CellStyle subTitleCellStyle = null;
	private static CellStyle dateCellStyle = null;
	private static CellStyle timeCellStyle = null;
	private static Sheet sheet = null;
	private static int rowNum = 0;

	public static void writeWorkbook(String content, String file) {
		Workbook wb = new XSSFWorkbook();
		int rowNum = 0;
		int eRowNum = 0;
		
		String[] lines = content.split("\n");
		if(lines.length > 0) {
			writeTitle(wb, lines[0]);
			rowNum++;
		}
		for(int i = 1; i < lines.length; i++) {
			if(writeLine(wb, rowNum, eRowNum, lines[i])) {
				rowNum++;
			} else {
				eRowNum++;
			}
		}
		
		try {
			OutputStream out = new FileOutputStream(file);
			wb.write(out);
			out.close();
			wb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeWorkbook(Calendar calendar,
			Collection<Department> departs, String file) {
		Workbook wb = new XSSFWorkbook();
		titleCellStyle = getTitleCellStyle(wb);
		subTitleCellStyle = getSubTitleCellStyle(wb);
		dateCellStyle = getDateCellStyle(wb);
		timeCellStyle = getTimeCellStyle(wb);
		sheet = wb.createSheet("sheet1");

		writeTitle(calendar);

		rowNum += 2;

		writeDeparts(departs, calendar);

		for (int i = 0; i < 7; i++) {
			sheet.autoSizeColumn(i);
		}

		try {
			OutputStream out = new FileOutputStream(file);
			wb.write(out);
			out.close();
			wb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean writeLine(Workbook wb, int rowNum, int eRowNum, String line) {
		String[] cells = line.split(" ");
		if(isCellsValid(cells)) {
			String departName;
			String id;
			String name;
			String date;
			if(cells.length == 5) {
				departName = cells[0];
				id = cells[1];
				name = cells[2];
				date = cells[3] + " " + cells[4];
			} else {
				departName = cells[0].replaceAll("\\d", "");
				id = cells[0].replaceAll(departName, "");
				name = cells[1];
				date = cells[2] + " " + cells[3];
			}
			Sheet sheet = wb.getSheetAt(0);
			Row row = sheet.createRow(rowNum);
			row.createCell(0).setCellValue(departName);
			row.createCell(1).setCellValue(id);
			row.createCell(2).setCellValue(name);
			row.createCell(3).setCellValue(date);
			return true;
		} else {
			Sheet sheet = wb.getNumberOfSheets() == 1 ? wb.createSheet("Errors") : wb.getSheetAt(1);
			Row row = sheet.createRow(eRowNum);
			for(int i = 0; i < cells.length; i++) {
				row.createCell(i).setCellValue(cells[i]);
			}
			return false;
		}
	}
	
	private static void writeTitle(Workbook wb, String line) {
		String[] cells = line.split(" ");
		Sheet sheet = wb.createSheet("YYYYMM");
		Row row = sheet.createRow(rowNum);
		for(int i = 0; i < cells.length; i++) {
			row.createCell(i).setCellValue(cells[i]);
		}
	}

	private static void writeTitle(Calendar calendar) {
		DateUtil.setFirstDayOfMonth(calendar);
		String begin = DateUtil.formatDate(calendar, "MM.dd");
		DateUtil.setLastDayOfMonth(calendar);
		String end = DateUtil.formatDate(calendar, "MM.dd");
		String value = "考勤记录表 " + begin + " - " + end;

		sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 6));
		Cell cell = sheet.createRow(rowNum).createCell(0);
		cell.setCellStyle(titleCellStyle);
		cell.setCellValue(value);
	}
	
	private static void writeDeparts(Collection<Department> departs,
			Calendar calendar) {
		Iterator<Department> it = departs.iterator();
		while (it.hasNext()) {
			Department depart = it.next();
			writeStaffs(depart.getDepartName(), depart.getStaffs().values(),
					calendar);
		}
	}

	private static void writeStaffs(String departName,
			Collection<Staff> staffs, Calendar calendar) {
		Iterator<Staff> it = staffs.iterator();
		while (it.hasNext()) {
			Staff staff = it.next();
			StringBuffer value = new StringBuffer();
			value.append("部门:").append(departName);
			value.append("  考勤号码:").append(staff.getId());
			value.append("  姓名:").append(staff.getName());

			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 6));
			Cell cell = sheet.createRow(rowNum).createCell(0);
			cell.setCellStyle(subTitleCellStyle);
			cell.setCellValue(value.toString());

			writeDayOfWeek();
			writeDays(staff.getDays(), calendar);
		}
	}
	
	private static void writeDayOfWeek() {
		rowNum++;
		sheet.createRow(rowNum);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		for(int i = 0; i < 7; i++) {
			Cell cell = sheet.getRow(rowNum).createCell(i);
			cell.setCellStyle(dateCellStyle);
			cell.setCellValue(DateUtil.formatDate(calendar, "EEE"));
			calendar.add(Calendar.DAY_OF_WEEK, 1);
		}
	}

	private static void writeDays(Map<String, Day> days, Calendar calendar) {
		rowNum--;
		
		DateUtil.setFirstDayOfMonth(calendar);
		int cellNum = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if(cellNum != 0) {
			rowNum += 2;
			sheet.createRow(rowNum);
			sheet.createRow(rowNum + 1);
			for(int i = 0; i < cellNum; i++) {
				Cell cell = sheet.getRow(rowNum).createCell(i);
				cell.setCellStyle(dateCellStyle);
				cell = sheet.getRow(rowNum + 1).createCell(i);
				cell.setCellStyle(timeCellStyle);
			}
		}
		
		DateUtil.setLastDayOfMonth(calendar);
		int lastDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		for (int i = 1; i <= lastDayOfMonth; i++) {
			if (cellNum % 7 == 0) {
				rowNum += 2;
				cellNum = 0;
				sheet.createRow(rowNum);
				sheet.createRow(rowNum + 1);
			}
			calendar.set(Calendar.DAY_OF_MONTH, i);
			String date = DateUtil.formatDate(calendar, "yyyy-MM-dd");
			Cell cell = sheet.getRow(rowNum).createCell(cellNum);
			cell.setCellStyle(dateCellStyle);
			cell.setCellValue(date);
			Day day = days.get(date);
			writeTimes(day, cellNum);
			cellNum++;
		}
		for (; cellNum < 7; cellNum++) {
			Cell cell = sheet.getRow(rowNum).createCell(cellNum);
			cell.setCellStyle(dateCellStyle);
			cell = sheet.getRow(rowNum + 1).createCell(cellNum);
			cell.setCellStyle(timeCellStyle);
		}
		rowNum += 3;
	}
	
	private static void writeTimes(Day day, int cellNum) {
		Cell cell = sheet.getRow(rowNum + 1).createCell(cellNum);
		cell.setCellStyle(timeCellStyle);
		if(day != null) {
			StringBuffer value = new StringBuffer();
			Iterator<String> it = day.getTimes().iterator();
			while(it.hasNext()) {
				String time = it.next();
				value.append(time).append("\r\n");
			}
			cell.setCellValue(value.toString().trim());
		}
	}

	private static boolean isCellsValid(String[] cells) {
		return cells.length == 5 || (cells.length == 4 && cells[0].matches("\\D+\\d+$"));
	}

	private static CellStyle getTitleCellStyle(Workbook wb) {
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 20);

		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

		return cellStyle;
	}

	private static CellStyle getSubTitleCellStyle(Workbook wb) {
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 14);

		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(font);

		return cellStyle;
	}

	private static CellStyle getDateCellStyle(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		return cellStyle;
	}

	private static CellStyle getTimeCellStyle(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		return cellStyle;
	}

}
