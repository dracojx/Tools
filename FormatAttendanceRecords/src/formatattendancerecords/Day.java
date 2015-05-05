package formatattendancerecords;

import java.util.Set;

public class Day {
	private String date;
	private Set<String> times;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Set<String> getTimes() {
		return times;
	}
	public void setTimes(Set<String> times) {
		this.times = times;
	}
	
	
}
