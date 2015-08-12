package formatattendancerecords.v2;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Data {
	private Map<String, Department> departments;
	private Calendar calendar;
	
	public Data(String[] lines) {
		departments = new TreeMap<String, Department>();
		getData(lines);
		getCalendar(lines);
	}

	public Collection<Department> getDepartments() {
		return departments.values();
	}
	public Calendar getCalendar() {
		return calendar;
	}
	
	private void getData(String[] lines) {
		for(int i = 1; i < lines.length; i++) {
			String line = lines[i];
			String[] fields = line.split(" ");
			String deptName = fields[0];
			String id = fields[1];
			String name = fields[2];
			String date = fields[3];
			String time = fields[4];
			
			Department dep = departments.get(deptName);
			if(dep == null) {
				departments.put(deptName, new Department(deptName));
				dep = departments.get(deptName);
			}
			
			Staff staff = dep.getStaff(id);
			if(staff == null) {
				dep.addStaff(id, name);
				staff = dep.getStaff(id);
			}
			
			Day day = staff.getDay(date);
			if(day == null) {
				staff.addDay(date);
				day = staff.getDay(date);
			}
			day.addTime(time);
		}
	}
	
	private void getCalendar(String[] lines) {
		String dateStr = lines[1].split(" ")[3];
		calendar = Calendar.getInstance();
		Date date = DateUtil.parseDate(dateStr, "yyyy-MM-dd");
		calendar.setTime(date);
	}
}
