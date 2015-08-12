package formatattendancerecords.v2;

import java.util.Map;
import java.util.TreeMap;

public class Department {
	private String departName;
	private Map<String, Staff> staffs;
	
	public Department(String departName) {
		this.departName = departName;
		staffs = new TreeMap<String, Staff>();
	}
	
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public Map<String, Staff> getStaffs() {
		return staffs;
	}
	public void setStaffs(Map<String, Staff> staffs) {
		this.staffs = staffs;
	}
	public Staff getStaff(String id) {
		return staffs.get(id);
	}
	public void addStaff(String id, String name) {
		staffs.put(id, new Staff(id, name));
	}
	
}
