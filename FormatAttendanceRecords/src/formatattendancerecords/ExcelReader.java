package formatattendancerecords;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {

	public static void main(String[] args) {
		Workbook wb = ExcelReader.getWorkbook("考勤.xlsx");
		Calendar calendar = ExcelReader.getMonth(wb);
		Collection<Department> departs = ExcelReader.getDepartments(wb
				.getSheetAt(0));
		String file = "考勤记录表" + DateUtil.formatDate(new Date(), "yyyyMMdd") + ".xlsx";
		ExcelWriter.writeWorkbook(calendar, departs, file);
	}
	
	public static Workbook getWorkbook(String file) {
		try {
			InputStream is = new FileInputStream(file);
			return WorkbookFactory.create(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Calendar getMonth(Workbook wb) {
		String month = wb.getSheetName(0);
		Calendar calendar = Calendar.getInstance();
		Date date = DateUtil.parseDate(month, "yyyyMM");
		calendar.setTime(date);
		return calendar;
	}
	
	public static Collection<Department> getDepartments(Sheet sheet) {
		Map<String, Department> departs = new TreeMap<String, Department>();
		
		int lastRowNum = sheet.getLastRowNum();
		for(int i = 1; i <= lastRowNum; i++) {
			Row row = sheet.getRow(i);
			if(!row.getZeroHeight()) {
				getDepart(departs, row);
			}
		}
		
		return departs.values();
	}
	
	private static void getDepart(Map<String, Department> departs, Row row) {
		String departName = row.getCell(0).getStringCellValue();
		Department depart = departs.get(departName);
		if(depart == null) {
			depart = new Department();
			depart.setDepartName(departName);
			depart.setStaffs(new TreeMap<String, Staff>());
			departs.put(departName, depart);
		}
		getStaff(depart.getStaffs(), row);
	}
	
	private static void getStaff(Map<String, Staff> staffs, Row row) {
		String id = row.getCell(1).getStringCellValue();
		Staff staff = staffs.get(id);
		if(staff == null) {
			String name = row.getCell(2).getStringCellValue();
			staff = new Staff();
			staff.setId(id);
			staff.setName(name);
			staff.setDays(new TreeMap<String, Day>());
			staffs.put(id, staff);
		}
		getDay(staff.getDays(), row);
	}
	
	private static void getDay(Map<String, Day> days, Row row) {
		String dateStr = row.getCell(3).getStringCellValue();
		String date = DateUtil.formatDate(dateStr, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");
		String time = DateUtil.formatDate(dateStr, "yyyy-MM-dd HH:mm:ss", "HH:mm:ss");
		Day day = days.get(date);
		if(day == null) {
			day = new Day();
			day.setDate(date);
			day.setTimes(new TreeSet<String>());
			days.put(date, day);
		}
		day.getTimes().add(time);
	}

}
