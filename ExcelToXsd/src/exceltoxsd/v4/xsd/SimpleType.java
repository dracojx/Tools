package exceltoxsd.v4.xsd;

public class SimpleType {
	private String name;
	private String base;
	private Integer minLength;
	private Integer maxLength;
	private Integer totalDigits;
	private Integer fractionDigits;
	
	public SimpleType(String base, Integer max, Integer min) {
		this.base = base;
		if(base.equalsIgnoreCase(Constants.XSD_TYPE_STRING)) {
			minLength = min;
			maxLength = max;
		} else if(base.equalsIgnoreCase(Constants.XSD_TYPE_DECIMAL)) {
			fractionDigits = min;
			totalDigits = max;
		}
	}
	
	public SimpleType(String base, Integer max) {
		this(base, max, null);
	}

	public SimpleType(String name, String base, Integer max, Integer min) {
		this(base, min, max);
		this.name = name;
	}
	
	public SimpleType(String name, String base, Integer max) {
		this(name, base, max, null);
	}
		
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public Integer getMinLength() {
		return minLength;
	}
	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}
	public Integer getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	public Integer getTotalDigits() {
		return totalDigits;
	}
	public void setTotalDigits(Integer totalDigits) {
		this.totalDigits = totalDigits;
	}
	public Integer getFractionDigits() {
		return fractionDigits;
	}
	public void setFractionDigits(Integer fractionDigits) {
		this.fractionDigits = fractionDigits;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<xsd:simpleType");
		if(name != null) {
			sb.append(" name=\"").append(name).append("\"");
		}
		sb.append(">");
		
		sb.append("<xsd:restriction base=\"").append(base).append("\">");
		if(minLength != null) {
			sb.append("<xsd:minLength value=\"").append(minLength).append("\" />");
		}
		if(maxLength != null) {
			sb.append("<xsd:maxLength value=\"").append(maxLength).append("\" />");
		}
		if(totalDigits != null) {
			sb.append("<xsd:totalDigits value=\"").append(totalDigits).append("\" />");
		}
		if(fractionDigits != null) {
			sb.append("<xsd:fractionDigits value=\"").append(fractionDigits).append("\" />");
		}
		sb.append("</xsd:restriction>");
		sb.append("</xsd:simpleType>");
		return sb.toString();
	}
	
}
