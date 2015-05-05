package exceltoxsd.v4.xsd;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Schema {
	private String xmlnsXsd = Constants.XMLNS_XSD;
	private String xmlns;
	private String target;
	private ComplexType complexType;
	private Map<String, SimpleType> simpleTypes = new LinkedHashMap<String, SimpleType>();
	
	public Schema(String xmlns, String target, String root) {
		this.xmlns = xmlns;
		this.target = target;
		complexType = new ComplexType(root, null);
	}
	
	public void addElement(Element element) {
		if(element.getSimpleType() != null && element.getComplexType()!= null) {
			SimpleType simpleType = element.getSimpleType();
			ComplexType complexType = element.getComplexType();
			String name = simpleType.getName();
			name = this.complexType.getName() + "." + name;
			simpleType.setName(name);
			complexType.setBase(name);
			addSimpleType(simpleType);
		}
		
		ComplexType complexType = this.complexType;
		String[] parents = element.getParents();
		for(int i = 0; i < parents.length; i++) {
			Element parent = complexType.getElement(parents[i]);
			if(parent == null) {
				parent = new Element(parents[i]);
				complexType.addElement(parent);
			}
			if(parent.getComplexType() == null) {
				parent.setComplexType(new ComplexType());
			}
			complexType = parent.getComplexType();
		}
		complexType.addElement(element);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<xsd:schema xmlns:xsd=\"").append(xmlnsXsd).append("\"");
		if(xmlns != null) {
			sb.append(" xmlns=\"").append(xmlns).append("\"");
		}
		if(target != null) {
			sb.append(" targetNamespace=\"").append(target).append("\"");
		}
		sb.append(">");
		Iterator<SimpleType> itSt = simpleTypes.values().iterator();
		while(itSt.hasNext()) {
			sb.append(itSt.next());
		}
		sb.append(complexType);
		
		sb.append("</xsd:schema>");
		return sb.toString();
	}

	private void addSimpleType(SimpleType simpleType) {
		simpleTypes.put(simpleType.getName(), simpleType);
	}
}
