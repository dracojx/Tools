package exceltoxsd.v4.xsd;

public class Element {
	private String name;
	private String type;
	private Annotation annotation;
	private boolean required;
	private boolean multi;
	private SimpleType simpleType;
	private ComplexType complexType;
	private String[] parents = new String[0];

	public Element(String name, String type, String remark, boolean required, boolean multi) {
		this.name = name;
		this.type = type;
		annotation = remark == null ? null : new Annotation(remark);
		this.required = required;
		this.multi = multi;
	}
	
	public Element(String name, String type, String remark) {
		this(name, type, remark, Constants.ELEMENT_OCCURS_DEFALT_REQUIRED, Constants.ELEMENT_OCCURS_DEFALT_MULTI);
	}
	
	public Element(String name) {
		this(name, null, null, Constants.ELEMENT_OCCURS_DEFALT_REQUIRED, Constants.ELEMENT_OCCURS_DEFALT_MULTI);
	}
	
	public Element(String name, String type, String remark, Integer max, Integer min, boolean required, boolean multi) {
		this.name = name;
		annotation = remark == null ? null : new Annotation(remark);
		this.required = required;
		this.multi = multi;
		simpleType = new SimpleType(type, max, min);
	}
	
	public Element(String name, String type, String remark, Integer max, Integer min) {
		this(name, type, remark, max, min, Constants.ELEMENT_OCCURS_DEFALT_REQUIRED, Constants.ELEMENT_OCCURS_DEFALT_MULTI);
	}
	
	public Element(String name, String type, String remark, Integer max) {
		this(name, type, remark, max, null, Constants.ELEMENT_OCCURS_DEFALT_REQUIRED, Constants.ELEMENT_OCCURS_DEFALT_MULTI);
	}
	
	public String getName() {
		return name;
	}
	
	public SimpleType getSimpleType() {
		return simpleType;
	}

	public void setComplexType(ComplexType complexType) {
		this.complexType = complexType;
	}

	public ComplexType getComplexType() {
		return complexType;
	}

	public String[] getParents() {
		return parents;
	}

	public void setParents(String[] parents) {
		this.parents = parents;
	}
	
	public void addAttribute(Attribute attribute) {
		if(type != null && complexType == null) {
			complexType = new ComplexType(type);
			type = null;
		} else if (simpleType != null && complexType == null) {
			StringBuffer type = new StringBuffer();
			for(int i = 0; i < parents.length; i++) {
				type.append(parents[i]).append(".");
			}
			type.append(name).append(".Content");
			complexType = new ComplexType(type.toString());
			simpleType.setName(type.toString());
		}
		complexType.addAttribute(attribute);
	}
	
	public void addElement(Element element) {
		if(complexType == null) {
			complexType = new ComplexType();
		}
		complexType.addElement(element);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<xsd:element name=\"").append(name).append("\"");
		if (type != null) {
			sb.append(" type=\"").append(type).append("\"");
		}
		if (!required) {
			sb.append(" minOccurs=\"").append(Constants.ELEMENT_OCCURS_OPTION).append("\"");
		}
		if (multi) {
			sb.append(" maxOccurs=\"").append(Constants.ELEMENT_OCCURS_UNBOUNDED).append("\"");
		}
		if(annotation != null || simpleType != null || complexType != null) {
			sb.append(">");
			if(annotation != null) {
				sb.append(annotation);
			}
			if(simpleType != null && complexType == null) {
				sb.append(simpleType);
			}
			if(complexType != null) {
				sb.append(complexType);
			}
			sb.append("</xsd:element>");
		} else {
			sb.append(" />");
		}
		return sb.toString();
	}
}
