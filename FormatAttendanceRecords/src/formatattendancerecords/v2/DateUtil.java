package formatattendancerecords.v2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static String formatDate(Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	public static String formatDate(Calendar calendar, String format) {
		return formatDate(calendar.getTime(), format);
	}
	
	public static String formatDate(String dateStr, String formatFrom, String formatTo) {
		return formatDate(parseDate(dateStr, formatFrom), formatTo);
	}

	public static Date parseDate(String dateStr, String format) {
		DateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Calendar parseCalendar(String dateStr, String format) {
		DateFormat df = new SimpleDateFormat(format);
		try {
			Date date = df.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void setFirstDayOfMonth(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, 1);
	}
	
	public static void setLastDayOfMonth(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DATE, -1);
	}
}
