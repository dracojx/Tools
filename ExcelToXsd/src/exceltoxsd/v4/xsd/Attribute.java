package exceltoxsd.v4.xsd;

public class Attribute {
	private String name;
	private String type;
	private Annotation annotation;
	private boolean required;
	private SimpleType simpleType;
	
	public Attribute(String name, String type, String remark, boolean required) {
		this.name = name;
		this.type = type;
		annotation = remark == null ? null : new Annotation(remark);
		this.required = required;
	}
	
	public Attribute(String name, String type, String remark) {
		this(name, type, remark, Constants.ATTRIBUTE_DEFALT_USE);
	}
	
	public Attribute(String name, String type, Integer max, Integer min, String remark, boolean required) {
		this.name = name;
		annotation = remark == null ? null : new Annotation(remark);
		this.required = required;
		simpleType = new SimpleType(type, max, min);
	}
	
	public Attribute(String name, String type, Integer max, Integer min, String remark) {
		this(name, type, max, min, remark, Constants.ATTRIBUTE_DEFALT_USE);
	}
	
	public Attribute(String name, String type, Integer max, String remark, boolean required) {
		this(name, type, max, null, remark, required);
	}
	
	public Attribute(String name, String type, Integer max, String remark) {
		this(name, type, max, null, remark, Constants.ATTRIBUTE_DEFALT_USE);
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<xsd:attribute name=\"").append(name).append("\"");
		if(type != null) {
			sb.append(" type=\"").append(type).append("\"");
		}
		if(required) {
			sb.append(" use=\"").append(Constants.ATTRIBUTE_REQUIRED).append("\"");
		}
		if(annotation != null || simpleType != null) {
			sb.append(">");
			if(annotation != null) {
				sb.append(annotation);
			}
			if(simpleType != null) {
				sb.append(simpleType);
			}
			sb.append("</xsd:attribute>");
		} else {
			sb.append(" />");
		}
		return sb.toString();
	}
}
