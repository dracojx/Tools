package exceltoxsd.v4.xsd;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class ComplexType {
	private String name;
	private String base;
	private Map<String, Element> elements;
	private Map<String, Attribute> attributes;
	
	public ComplexType() {
		
	}
	
	public ComplexType(String name, String base) {
		this.name = name;
		this.base = base;
	}
	
	public ComplexType(String base) {
		this(null, base);
	}
	
	public String getName() {
		return name;
	}
	
	public void setBase(String base) {
		this.base = base;
	}
	
	public Element getElement(String name) {
		return elements.get(name);
	}
	
	public void addAttribute(Attribute attribute) {
		if(attributes == null) {
			attributes = new LinkedHashMap<String, Attribute>();
		}
		attributes.put(attribute.getName(), attribute);
	}
	
	public void addElement(Element element) {
		if(elements == null) {
			elements = new LinkedHashMap<String, Element>();
		}
		elements.put(element.getName(), element);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<xsd:complexType");
		if(name != null) {
			sb.append(" name=\"").append(name).append("\"");
		}
		sb.append(">");
		
		if(elements != null && !elements.isEmpty()) {
			sb.append("<xsd:sequence>");
			Iterator<Element> it = elements.values().iterator();
			while(it.hasNext()) {
				sb.append(it.next());
			}
			sb.append("</xsd:sequence>");
		} else if(attributes != null && !attributes.isEmpty()) {
			sb.append("<xsd:simpleContent>");
			sb.append("<xsd:extension base=\"").append(base).append("\">");
			Iterator<Attribute> it = attributes.values().iterator();
			while(it.hasNext()) {
				sb.append(it.next());
			}
			sb.append("</xsd:extension>");
			sb.append("</xsd:simpleContent>");
		}
		
		sb.append("</xsd:complexType>");
		return sb.toString();
	}
}
