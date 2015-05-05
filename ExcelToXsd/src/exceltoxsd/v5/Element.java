package exceltoxsd.v5;

import java.util.LinkedHashMap;
import java.util.Map;

public class Element {
	private String name;
	private String type;
	private Restriction restriction;
	private boolean required;
	private boolean multi = Constants.ELEMENT_DEFAULT_MULTI;
	private String remark;
	private Map<String, Element> elements;
	private Map<String, Attribute> attributes;
	
	public Element(String name) {
		this.name = name;
	}
	
	public Element(String name, String type, Integer max, Integer min, boolean required, String remark) {
		this.name = name;
		this.type = type;
		this.required = required;
		this.remark = remark;
		if(max != null || min != null) {
			restriction = new Restriction(type, max, min);
		}
	}
	
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
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public boolean isMulti() {
		return multi;
	}
	public void setMulti(boolean multi) {
		this.multi = multi;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Map<String, Element> getElements() {
		if(elements == null) {
			elements = new LinkedHashMap<String, Element>();
		}
		return elements;
	}
	public void setElements(Map<String, Element> elements) {
		this.elements = elements;
	}
	public Map<String, Attribute> getAttributes() {
		if(attributes == null) {
			attributes = new LinkedHashMap<String, Attribute>();
		}
		return attributes;
	}
	public void setAttributes(Map<String, Attribute> attributes) {
		this.attributes = attributes;
	}
	public boolean hasRestriction() {
		return restriction != null;
	}
	public boolean hasAttributes() {
		return attributes != null;
	}
	public boolean hasElements() {
		return elements != null;
	}
	public Element getElement(String name) {
		Element element = this.getElements().get(name);
		if(element == null) {
			element = new Element(name);
			this.getElements().put(element.getName(), element);
		}
		return element;
	}
	public void addElement(Element element) {
		this.getElements().put(element.getName(), element);
	}
	public void addAttribute(Attribute attribute) {
		this.getAttributes().put(attribute.getName(), attribute);
	}
}
