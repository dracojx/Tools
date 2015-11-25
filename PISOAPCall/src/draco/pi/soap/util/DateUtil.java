package draco.pi.soap.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String createDTSEND() {
		return format(new Date(), "yyyyMMddHHmmssSSS");
	}
	
	public static String format(Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
}
