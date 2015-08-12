package formatattendancerecords.v2;

import java.util.Set;
import java.util.TreeSet;

public class Day {
	private String date;
	private Set<String> times;
	
	public Day(String date) {
		this.date = date;
		times = new TreeSet<String>();
	}
	
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
	public void addTime(String time) {
		times.add(time);
	}
	
	
}
