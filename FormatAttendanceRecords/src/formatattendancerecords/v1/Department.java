package formatattendancerecords.v1;

import java.util.Map;

public class Department {
	private String departName;
	private Map<String, Staff> staffs;
	
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
	
}
