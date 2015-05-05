package exceltoxsd.v5;

public class Attribute {
	private String name;
	private String type;
	private Restriction restriction;
	private String remark;
	private boolean required = Constants.ATTRIBUTE_DEFAULT_REQUIRED;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Restriction getRestriction() {
		return restriction;
	}
	public void setRestriction(Restriction restriction) {
		this.restriction = restriction;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	
	public boolean hasRestriction() {
		return restriction != null;
	}
}
