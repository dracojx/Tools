package exceltoxsd.v5;

import java.util.LinkedHashMap;
import java.util.Map;

public class DataType {
	private String name;
	private String namespace;
	private String remark;
	private Map<String, Element> elements = new LinkedHashMap<String, Element>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Map<String, Element> getElements() {
		return elements;
	}
	public void setElements(Map<String, Element> elements) {
		this.elements = elements;
	}
	public Element getElement(String name) {
		Element element = elements.get(name);
		if(element == null) {
			element = new Element(name);
			elements.put(element.getName(), element);
		}
		return element;
	}
	
	public void addElement(Element element, String[] parents) {
		if(parents.length > 0) {
			Element parent = this.getElement(parents[0]);
			for(int i = 1; i < parents.length; i++) {
				parent = parent.getElement(parents[i]);
			}
			parent.addElement(element);
		} else {
			elements.put(element.getName(), element);
		}
	}
	
	public void addAttribute(Attribute attribute, String[] parents) {
		if(parents.length > 0) {
			Element parent = this.getElement(parents[0]);
			for(int i = 1; i < parents.length; i++) {
				parent = parent.getElement(parents[i]);
			}
			parent.addAttribute(attribute);
		}
	}
}
