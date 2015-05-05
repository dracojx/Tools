package formatattendancerecords;

import java.util.Map;

public class Staff {
	private String id;
	private String name;
	private Map<String, Day> days;
	
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
	
}
