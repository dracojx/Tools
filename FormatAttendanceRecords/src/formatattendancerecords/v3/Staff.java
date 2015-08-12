package formatattendancerecords.v3;

import java.util.Map;
import java.util.TreeMap;

public class Staff {
	private String id;
	private String name;
	private Map<String, Day> days;
	
	public Staff(String id, String name) {
		this.id = id;
		this.name = name;
		days = new TreeMap<String, Day>();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Day> getDays() {
		return days;
	}
	public void setDays(Map<String, Day> days) {
		this.days = days;
	}
	public Day getDay(String date) {
		return days.get(date);
	}
	public void addDay(String date) {
		days.put(date, new Day(date));
	}
	
}
